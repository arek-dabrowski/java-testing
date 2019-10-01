import static org.junit.jupiter.api.Assertions.*;

import PageObjects.PageObjectCreate;
import PageObjects.PageObjectLogin;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import io.github.bonigarcia.seljup.SeleniumExtension;

import java.util.concurrent.TimeUnit;


@ExtendWith(SeleniumExtension.class)
public class CreateTest {
    private static WebDriver driver;
    private static PageObjectLogin loginPage;
    private static PageObjectCreate createPage;

    @BeforeAll
    public static void setUp() throws InterruptedException {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        loginPage = PageFactory.initElements(driver, PageObjectLogin.class);
        createPage = PageFactory.initElements(driver, PageObjectCreate.class);

        loginPage.refreshPage();
        loginPage.login("testacc@test.com", "test123");
    }

    @BeforeEach
    public void refreshPage() throws Exception {
        createPage.refreshPage();
    }

    @Test
    public void createWrongDataTest() throws Exception {
        createPage.createIncorrectly();
        assertTrue(createPage.assertErrorMessageIsDisplayed());
    }

    @Test
    public void createCorrectDataTest() throws Exception {
        createPage.createCorrectly();
        assertTrue(createPage.assertTitleIsInMyAddresses());
    }

    @AfterAll
    public static void tearDownAfterClass() throws Exception {
        driver.quit();
    }
}
