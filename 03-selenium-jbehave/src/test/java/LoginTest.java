import static org.junit.jupiter.api.Assertions.*;

import PageObjects.PageObjectLogin;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import io.github.bonigarcia.seljup.SeleniumExtension;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;


@ExtendWith(SeleniumExtension.class)
public class LoginTest {
    private static WebDriver driver;
    private static PageObjectLogin loginPage;

    @BeforeAll
    public static void setUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        loginPage = PageFactory.initElements(driver, PageObjectLogin.class);
    }

    @BeforeEach
    public void refreshPage() throws Exception {
        loginPage.refreshPage();
    }

    @Test
    public void loginTest() throws Exception {
        loginPage.login("testacc@test.com", "test123");
        assertTrue(loginPage.assertTitleIsInMyAccount());
    }

    @Test
    public void loginWrongEmailTest() throws Exception {
        loginPage.login("testacc", "test123");
        assertTrue(loginPage.assertErrorMessageIsDisplayed());
    }

    @Test
    public void loginWrongPasswordTest() throws Exception {
        loginPage.login("testacc@test.com", "wrongpassword");
        assertTrue(loginPage.assertErrorMessageIsDisplayed());
    }

    @AfterAll
    public static void tearDownAfterClass() throws Exception {
        driver.quit();
    }
}
