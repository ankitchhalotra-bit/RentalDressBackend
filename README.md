# Custom Dress Microservice

A Spring Boot application providing APIs for managing custom dresses.

## Getting Started

### Prerequisites
- JDK 17
- Maven
- MongoDB Instance
- Cloudinary Account

### Running the Application

1. **Verify `application.properties` settings**: Ensure your environment variables (`MONGO_URI`, `CLOUDINARY_CLOUD_NAME`, `CLOUDINARY_API_KEY`, `CLOUDINARY_API_SECRET`, `JWT_SECRET`) are configured or the default placeholder values are acceptable for local development.

2. **Build the application:**
   ```bash
   mvn clean install
   ```

3. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```
   Or via standard execution:
   ```bash
   java -jar target/demo-0.0.1-SNAPSHOT.jar
   ```

## API Documentation (Swagger UI)

This project uses OpenAPI (Swagger) for API documentation. 

Once the application is running (by default on port `8080`), you can access the Swagger UI interface here:

👉 **[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)**

### Securing Swagger UI Requests
For endpoints that require authentication (like `/api/admin/**`), you'll need a JWT token.
1. Use the `/api/auth/login` endpoint to receive a token.
2. Click the **Authorize** button in Swagger UI and insert your token format (e.g., `Bearer ey...`).

## Tech Stack
- Spring Boot 3
- Spring Security (JWT based)
- Spring Data MongoDB
- Cloudinary Integration
- OpenAPI / Springdoc
