package com.ecommerce.productservice.serviceImplementation;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecommerce.productservice.dto.ProductDto;
import com.ecommerce.productservice.entity.Product;
import com.ecommerce.productservice.exceptions.ProductNotException;
import com.ecommerce.productservice.mapper.ProductMapper;
import com.ecommerce.productservice.mapper.ProductMapperImpl;
import com.ecommerce.productservice.repository.ProductRepository;
import com.ecommerce.productservice.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductMapperImpl productMapperImpl;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductMapper productMapper;

    ProductServiceImpl(ProductMapperImpl productMapperImpl) {
        this.productMapperImpl = productMapperImpl;
    }

	@Override
	public ProductDto addProduct(ProductDto productDto) {
		Product product = productMapper.toEntity(productDto);
		product.setCreatedAt(LocalDate.now());
		Product savedProduct = productRepository.save(product);
		return productMapper.toDto(savedProduct);
	}

	@Override
	public List<ProductDto>  getProductByName(String productName) throws ProductNotException {
		 List<Product> products = productRepository.findAllByProductName(productName);

		    if (products.isEmpty()) {
		        throw new ProductNotException( "No products found with name: " + productName);
		    }

		    return products.stream()
		                   .map(productMapper::toDto)
		                   .toList();
	}

	@Override
	public ProductDto updateProduct(Long productId, ProductDto productDto) throws ProductNotException {
		Product productById = productRepository.findById(productId).
				orElseThrow(()-> new ProductNotException("ProductNotFound with id :"+productId));
		productById.setBrand(productDto.getBrand());
		productById.setProductName(productDto.getProductName());
		productById.setProductCode(productDto.getProductCode());
		productById.setPrice(productDto.getPrice());
		productById.setStatus(productDto.getStatus());
		
		Product savedProduct = productRepository.save(productById);
		return productMapper.toDto(savedProduct);
	}

	@Override
	public ProductDto getProductById(Long productId) throws ProductNotException {
		Product productById = productRepository.findById(productId).
		orElseThrow(()-> new ProductNotException("ProductNotFound with id :"+productId));
		return productMapper.toDto(productById);
	}

	@Override
	public ProductDto reduceQuantity(Long productId, int quantity) throws ProductNotException {
		Product productById = productRepository.findById(productId).
				orElseThrow(()-> new ProductNotException("ProductNotFound with id :"+productId));
		
		productById.setQuantity(productById.getQuantity()-quantity);
		Product save = productRepository.save(productById);
		return productMapper.toDto(save);
	}

}
