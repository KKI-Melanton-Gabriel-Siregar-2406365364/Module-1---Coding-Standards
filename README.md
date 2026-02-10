README for Module 1 - Coding Standards
1. Clean Code Principles In this module, I focused on applying the Single Responsibility Principle (SRP) by separating the responsibilities of the ProductController, ProductService, and ProductRepository. 
The controller handles HTTP requests, the service manages business logic, and the repository handles data storage. I also used meaningful variable names (like productId instead of just id) to make the code self-documenting.

2. Secure Coding Practices To ensure security, I used UUID for generating unique product IDs instead of sequential integers, which makes the IDs harder to guess (avoiding Insecure Direct Object References). 
I also used proper HTTP methods (GET for retrieval, POST for state changes) to stick to RESTful conventions.

3. Areas for Improvement One thing I noticed is that my ProductRepository currently uses a simple ArrayList to store data. In a real-world application, 
this should be replaced with a database connection to ensure data persistence and better concurrency handling. Additionally, I should add more input validation (e.g., preventing negative quantities) in the Product model to improve data integrity.