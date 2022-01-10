# Getting Started

### About the project
This project has been created as assignment task into the Java challenge procedure for Inditex

### How to proceed
Here's how to proceed to launch and test the application :
* Compile the application :  mvn clean install
* Launch the application :   mvn spring-boot:run
* Check the actuator to view project status :
  access to http://localhost:8080/actuator/health
* You can access to http://localhost:8080/swagger-ui/index.html to check the swagger integration
  The API methods available :

| Method API             | Description                           |
|------------------------|---------------------------------------|
| POST   /product        | Create a new product                  |
| POST   /product/publish | Create a new product in async mode    |
| PUT    /product/{id}   | Update an existing product            |
| DELETE /product/{id}   | Delete an existing product            |
| GET    /products       | Get the number of products available  |
| GET    /products/all   | Get a list of the products available  |
| POST   /product/search | Search products by product properties |
