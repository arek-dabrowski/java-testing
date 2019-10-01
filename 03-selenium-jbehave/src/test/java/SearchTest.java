import static org.junit.jupiter.api.Assertions.*;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.junit.jupiter.api.extension.ExtendWith;
import io.github.bonigarcia.seljup.SeleniumExtension;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import java.util.concurrent.TimeUnit;


@ExtendWith(SeleniumExtension.class)
@EnabledOnOs(OS.WINDOWS)
public class SearchTest {
    private final static String SEARCH_PAGE = "http://automationpractice.com/index.php";
    private static WebDriver driver;

    @BeforeAll
    public static void setUp() throws InterruptedException {
        WebDriverManager.phantomjs().setup();
        driver = new PhantomJSDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(SEARCH_PAGE);
        Thread.sleep(1000);
    }

    @BeforeEach
    public void refreshPage() throws Exception {
        driver.get(SEARCH_PAGE);
        Thread.sleep(1000);
    }

    @Test
    public void searchDressesTest() throws Exception {
        driver.findElement(By.id("search_query_top")).sendKeys("dress");
        driver.findElement(By.className("button-search")).click();
        Thread.sleep(1000);

        assertEquals(7, driver.findElements(By.className("available-now")).size());
    }

    @Test
    public void searchBlouseTest() throws Exception {
        driver.findElement(By.id("search_query_top")).sendKeys("blouse");
        driver.findElement(By.className("button-search")).click();
        Thread.sleep(1000);

        assertEquals(1, driver.findElements(By.className("available-now")).size());
    }

    @Test
    public void searchPrintedTest() throws Exception {
        driver.findElement(By.id("search_query_top")).sendKeys("printed");
        driver.findElement(By.className("button-search")).click();
        Thread.sleep(1000);

        assertTrue(driver.getCurrentUrl().equalsIgnoreCase("http://automationpractice.com/index.php?controller=search&orderby=position&orderway=desc&search_query=printed&submit_search="));
    }

    @Test
    public void searchNoResultsTest() throws Exception {
        driver.findElement(By.id("search_query_top")).sendKeys("suit");
        driver.findElement(By.className("button-search")).click();
        Thread.sleep(1000);

        assertTrue(driver.findElement(By.cssSelector(".alert.alert-warning")).isDisplayed());
    }

    @AfterAll
    public static void tearDownAfterClass() throws Exception {
        driver.quit();
    }
}
