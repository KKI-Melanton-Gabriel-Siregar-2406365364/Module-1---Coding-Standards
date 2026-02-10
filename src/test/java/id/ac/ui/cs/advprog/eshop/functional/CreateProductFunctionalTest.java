package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class CreateProductFunctionalTest {

    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    @Test
    void createProduct_isCorrect(ChromeDriver driver) throws Exception {
        // 1. Navigate to the Product List Page
        driver.get(baseUrl + "/product/list");

        // 2. Click the "Create Product" button
        // (Make sure your HTML button text matches "Create Product")
        driver.findElement(By.linkText("Create Product")).click();

        // 3. Fill in the form
        // (Make sure your HTML input fields use name="productName" and name="productQuantity")
        driver.findElement(By.name("productName")).sendKeys("Sampo Cap Bambang");
        driver.findElement(By.name("productQuantity")).sendKeys("20");

        // 4. Click the Submit button
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // 5. Verify the product appears in the list
        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains("Sampo Cap Bambang"));
        assertTrue(pageSource.contains("20"));
    }
}
