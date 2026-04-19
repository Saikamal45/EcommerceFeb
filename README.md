# 🛒 Ecommerce Microservices Platform

## 🔧 Architecture
- API Gateway (Spring Cloud Gateway)
- Eureka Service Registry
- Config Server (Centralized configuration)
- Microservices:
  - User Service
  - Product Service
  - Order Service
  - Cart Service
  - Notification Service

## 🔗 Communication
- FeignClient (service-to-service communication)

## ⚙️ Features
- User Authentication (JWT)
- Product Management
- Order Processing
- Email Notifications

## 🧠 Resilience
- Circuit Breaker (Resilience4j)

## ▶️ How to Run
1. Start Config Server
2. Start Eureka Server
3. Start all services
4. Start API Gateway

## 📌 Future Improvements
- Kafka integration
- Redis caching
- Docker deployment
