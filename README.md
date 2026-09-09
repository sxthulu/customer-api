A REST API built with Java, Spring Boot, Spring Data JPA, and PostgreSQL for managing customers.

Features
Create customers
Get all customers
Get customer by ID
Update customers
Delete customers
Endpoints
GET    /customers
GET    /customers/{id}
POST   /customers
PUT    /customers/{id}
DELETE /customers/{id}
Tech Stack
Java
Spring Boot
PostgreSQL
Maven
Postman
Run

Start PostgreSQL, then run the Spring Boot application.


API:

http://localhost:8080

@Test
CustomerService
     |
     | save(customer)
     ↓
CustomerRepository
     |
     ↓
ArgumentCaptor catches it
     |
     ↓
capturedCustomer
     |
     ├── firstName → "Adriel" ✅
     ├── lastName  → "Rai"    ✅
     └── email     → "john@example.com" ✅
This is stronger than just checking the response because we're proving that the service built the Customer correctly before saving it.
     
     

