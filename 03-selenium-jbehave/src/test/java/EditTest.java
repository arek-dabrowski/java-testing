import static org.junit.jupiter.api.Assertions.*;

import PageObjects.PageObjectCreate;
import PageObjects.PageObjectEdit;
import PageObjects.PageObjectLogin;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import io.github.bonigarcia.seljup.SeleniumExtension;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;


@ExtendWith(SeleniumExtension.class)
@EnabledOnOs(OS.WINDOWS)
public class EditTest {
    private static WebDriver driver;

    private static PageObjectEdit editPage;

    @BeforeAll
    public static void setUp() throws InterruptedException, MalformedURLException {
        OperaOptions options = new OperaOptions();
        options.setBinary("C:\\Users\\arekd\\AppData\\Local\\Programs\\Opera\\60.0.3255.106\\opera.exe");
        options.addArguments("--no-sandbox");
        WebDriverManager.operadriver().setup();
        driver = new OperaDriver(options);

        editPage = PageFactory.initElements(driver, PageObjectEdit.class);

        loginAndCreateAddress();
    }

    @BeforeEach
    public void refreshPage() throws Exception {
        editPage.refreshPage();
    }

    @Test
    public void editAddressCorrectlyTest() throws Exception {
        String nameBeforeEdit = editPage.getSecondAddressAlias();
        editPage.editAddressCorrectly();
        String nameAfterEdit = editPage.getSecondAddressAlias();

        assertFalse(nameBeforeEdit.equalsIgnoreCase(nameAfterEdit));
        assertTrue(nameAfterEdit.equalsIgnoreCase("MY EDITED ADDRESS"));
    }

    @Test
    public void editAddressIncorrectlyTest() throws Exception {
        editPage.editAddressIncorrectly();
        assertTrue(editPage.assertErrorMessageIsDisplayed());
    }

    @AfterEach
    public void tearDown() throws InterruptedException {
        editPage.deleteEditedAddress();
    }

    @AfterAll
    public static void tearDownAfterClass() throws Exception {
        driver.quit();
    }

    private static void loginAndCreateAddress() throws InterruptedException {
        PageObjectLogin loginPage = PageFactory.initElements(driver, PageObjectLogin.class);
        PageObjectCreate createPage = PageFactory.initElements(driver, PageObjectCreate.class);

        loginPage.refreshPage();
        loginPage.login("testacc@test.com", "test123");
        createPage.refreshPage();
        createPage.createCorrectly();
    }
}
