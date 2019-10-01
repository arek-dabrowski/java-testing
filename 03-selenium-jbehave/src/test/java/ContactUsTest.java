import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import io.github.bonigarcia.seljup.SeleniumExtension;
import org.openqa.selenium.support.ui.Select;


@ExtendWith(SeleniumExtension.class)
public class ContactUsTest {
    private final static String CONTACT_US_PAGE = "http://automationpractice.com/index.php?controller=contact";
    private static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        driver = new HtmlUnitDriver();
    }

    @BeforeEach
    public void refreshPage() throws Exception {
        driver.get(CONTACT_US_PAGE);
        Thread.sleep(1000);
    }

    @Test
    public void contactUsPageTest() {
        assertEquals("Contact us - My Store", driver.getTitle());
    }

    @Test
    public void doNotSelectAnyTest() throws InterruptedException {
        driver.findElement(By.id("email")).sendKeys("goodEmail@test.com");
        driver.findElement(By.id("id_order")).sendKeys("123456");
        driver.findElement(By.id("message")).sendKeys("My Message is here!");
        driver.findElement(By.id("submitMessage")).click();
        Thread.sleep(1000);

        assertTrue(driver.findElement(By.cssSelector(".alert.alert-danger")).isDisplayed());
    }

    @Test
    public void wrongEmailTest() throws InterruptedException {
        Select state = new Select(driver.findElement(By.id("id_contact")));
        state.selectByVisibleText("Webmaster");

        driver.findElement(By.id("email")).sendKeys("wrongEmail%com");
        driver.findElement(By.id("id_order")).sendKeys("123456");
        driver.findElement(By.id("message")).sendKeys("My Message is here!");
        driver.findElement(By.id("submitMessage")).click();
        Thread.sleep(1000);

        assertTrue(driver.findElement(By.cssSelector(".alert.alert-danger")).isDisplayed());
    }

    @Test
    public void correctMessageTest() throws InterruptedException {
        Select state = new Select(driver.findElement(By.id("id_contact")));
        state.selectByVisibleText("Customer service");

        driver.findElement(By.id("email")).sendKeys("good@test.com");
        driver.findElement(By.id("id_order")).sendKeys("411859");
        driver.findElement(By.id("message")).sendKeys("My Message is here!");
        driver.findElement(By.id("submitMessage")).click();
        Thread.sleep(1000);

        assertTrue(driver.findElement(By.cssSelector(".alert.alert-success")).isDisplayed());
    }

    @AfterAll
    public static void tearDownAfterClass() {
        driver.quit();
    }
}
