README for Module 1 - Coding Standards
Reflection 1
1. Clean Code Principles 
In this module, I focused on applying the Single Responsibility Principle (SRP) by separating the responsibilities of the ProductController, ProductService, and ProductRepository. 
The controller handles HTTP requests, the service manages business logic, and the repository handles data storage. I also used meaningful variable names (like productId instead of just id) to make the code self-documenting.

2. Secure Coding Practices 
To ensure security, I used UUID for generating unique product IDs instead of sequential integers, which makes the IDs harder to guess (avoiding Insecure Direct Object References). 
I also used proper HTTP methods (GET for retrieval, POST for state changes) to stick to RESTful conventions.

3. Areas for Improvement 
One thing I noticed is that my ProductRepository currently uses a simple ArrayList to store data. In a real-world application, 
this should be replaced with a database connection to ensure data persistence and better concurrency handling. Additionally, I should add more input validation (e.g., preventing negative quantities) in the Product model to improve data integrity.

Reflection 2
1. Unit Testing and Code Coverage
Writing unit tests for the Edit and Delete features has verified that my application handles both successful updates and invalid inputs gracefully, effectively preventing regression bugs. 
While I strive for high code coverage to ensure every line of code is executed during testing, I understand that even 100% coverage does not guarantee a bug-free application, as it cannot detect missing requirements or logical errors in the design itself.

2. Functional Testing Cleanliness
Creating a new functional test class by copying the setup procedures and instance variables from the previous class violates the DRY (Don't Repeat Yourself) principle, leading to code duplication and maintenance issues. 
To improve the code quality and cleanliness, I should refactor this common setup logic into a separate Base Test class or a utility method, allowing all functional tests to inherit the configuration without repeating the code.

README for Module 2 - CI/CD & DevOps
1. Code quality issue(s) that you fixed during the exercise
A common code quality issue flagged by SonarCloud in this project was having unnecessary public modifiers on JUnit 5 test classes and methods. My strategy to fix this was to simply go through the test files (like HomeControllerTest and ProductControllerTest) 
and remove the public keywords. JUnit 5 doesn't require test classes or methods to be public, so removing them makes the code cleaner and strictly follows SonarQube's best practices.

2. CI/CD workflows (GitHub)
Yes, I believe the current setup perfectly meets the definitions of both Continuous Integration (CI) and Continuous Deployment (CD). For the CI part, I created GitHub Actions that automatically trigger on every push to run the test suites and check code 
quality via SonarCloud, ensuring that any new code is always tested and verified. For the CD part, I connected my main GitHub branch to Koyeb using a pull-based PaaS approach. This means that whenever code is successfully merged into the main branch, Koyeb automatically builds the Docker image and deploys the live application to the internet without any manual intervention.