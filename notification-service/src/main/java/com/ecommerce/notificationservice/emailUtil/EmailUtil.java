package com.ecommerce.notificationservice.emailUtil;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class EmailUtil {

	@Autowired
	private JavaMailSender javaMailSender;
	
	public String accountCreationMail(String email) throws MessagingException, UnsupportedEncodingException {
	    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
	    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
	    mimeMessageHelper.setFrom("w3softech45@gmail.com", "Meesho");
	    mimeMessageHelper.setTo(email);
	    mimeMessageHelper.setSubject("Your Meesho Account Has Been Created Successfully!");

	    String body = String.format(
	            "<html>" +
	            "<head>" +
	            "<style>" +
	            "body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; }" +
	            ".container { max-width: 600px; background: #ffffff; margin: 20px auto; padding: 20px; border-radius: 8px; " +
	            "box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }" +
	            "h1 { color: #e91e63; text-align: center; }" +
	            "p { font-size: 16px; color: #333; }" +
	            ".details { background: #f9f9f9; padding: 10px; border-radius: 5px; }" +
	            ".footer { text-align: center; padding-top: 10px; font-size: 14px; color: #777; }" +
	            "strong { color: #333; }" +
	            "</style>" +
	            "</head>" +
	            "<body>" +
	            "<div class='container'>" +
	            "<h1>Welcome to Meesho 🎉</h1>" +
	            "<p>Dear Customer,</p>" +
	            "<p>We’re excited to inform you that your <strong>Meesho account</strong> has been successfully created.</p>" +
	            "<div class='details'>" +
	            "<p><strong>Registered Email:</strong> " + email + "</p>" +
	            "</div>" +
	            "<p>You can now start exploring Meesho to buy, sell, and grow your business with ease.</p>" +
	            "<p>If you need any help, our support team is always here to assist you.</p>" +
	            "<p class='footer'>Thank you for joining Meesho.<br><strong>— The Meesho Team</strong></p>" +
	            "</div>" +
	            "</body>" +
	            "</html>"
	    );


	    mimeMessageHelper.setText(body, true);
	    javaMailSender.send(mimeMessage);

	    return "Mail Sent Successfully..!!";
	}
	
	public String productAddToCart(String email,String productName,double price) throws MessagingException, UnsupportedEncodingException {
	    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
	    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
	    mimeMessageHelper.setFrom("w3softech45@gmail.com", "Meesho");
	    mimeMessageHelper.setTo(email);
	    mimeMessageHelper.setSubject("Your Meesho Account Has Been Created Successfully!");

	    String cartAddedBody = String.format(
	            "<html>" +
	            "<head>" +
	            "<style>" +
	            "body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; }" +
	            ".container { max-width: 600px; background: #ffffff; margin: 20px auto; padding: 20px; border-radius: 8px;" +
	            "box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }" +
	            "h1 { color: #e91e63; text-align: center; }" +
	            "p { font-size: 16px; color: #333; }" +
	            ".details { background: #f9f9f9; padding: 12px; border-radius: 5px; }" +
	            ".footer { text-align: center; padding-top: 10px; font-size: 14px; color: #777; }" +
	            "</style>" +
	            "</head>" +
	            "<body>" +
	            "<div class='container'>" +
	            "<h1>Added to Cart 🛒</h1>" +
	            "<p>Hi there,</p>" +
	            "<p>Your product has been successfully added to your cart.</p>" +
	            "<div class='details'>" +
	            "<p><strong>Product Name:</strong> " + productName + "</p>" +
	            "<p><strong>Price:</strong> ₹" + price + "</p>" +
	            "</div>" +
	            "<p>You can continue shopping or proceed to checkout anytime.</p>" +
	            "<p class='footer'>Happy Shopping!<br><strong>— The Meesho Team</strong></p>" +
	            "</div>" +
	            "</body>" +
	            "</html>"
	    );



	    mimeMessageHelper.setText(cartAddedBody, true);
	    javaMailSender.send(mimeMessage);

	    return "Mail Sent Successfully..!!";
	}
	
	public String orderSuccessMail(String email,Long orderId,BigDecimal totalAmount) throws MessagingException, UnsupportedEncodingException {
	    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
	    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
	    mimeMessageHelper.setFrom("w3softech45@gmail.com", "Meesho");
	    mimeMessageHelper.setTo(email);
	    mimeMessageHelper.setSubject("Your Meesho Account Has Been Created Successfully!");

	    String orderPlacedBody = String.format(
	            "<html>" +
	            "<head>" +
	            "<style>" +
	            "body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; }" +
	            ".container { max-width: 600px; background: #ffffff; margin: 20px auto; padding: 20px; border-radius: 8px;" +
	            "box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }" +
	            "h1 { color: #2e7d32; text-align: center; }" +
	            "p { font-size: 16px; color: #333; }" +
	            ".details { background: #f9f9f9; padding: 12px; border-radius: 5px; }" +
	            ".footer { text-align: center; padding-top: 10px; font-size: 14px; color: #777; }" +
	            "</style>" +
	            "</head>" +
	            "<body>" +
	            "<div class='container'>" +
	            "<h1>Order Placed Successfully 🎉</h1>" +
	            "<p>Dear Customer,</p>" +
	            "<p>Thank you for shopping with Meesho! Your order has been placed successfully.</p>" +
	            "<div class='details'>" +
	            "<p><strong>Order ID:</strong> " + orderId + "</p>" +
	            "<p><strong>Total Amount:</strong> ₹" + totalAmount + "</p>" +
	            "</div>" +
	            "<p>Your order will be processed and shipped soon.</p>" +
	            "<p>You can track your order anytime from the Meesho app.</p>" +
	            "<p class='footer'>Thank you for choosing Meesho.<br><strong>— The Meesho Team</strong></p>" +
	            "</div>" +
	            "</body>" +
	            "</html>"
	    );


	    mimeMessageHelper.setText(orderPlacedBody, true);
	    javaMailSender.send(mimeMessage);

	    return "Mail Sent Successfully..!!";
	}
	
}
