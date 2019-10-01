import static org.junit.jupiter.api.Assertions.*;

import PageObjects.PageObjectCreate;
import PageObjects.PageObjectDelete;
import PageObjects.PageObjectLogin;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import io.github.bonigarcia.seljup.SeleniumExtension;

import java.util.concurrent.TimeUnit;


@ExtendWith(SeleniumExtension.class)
@EnabledOnOs(OS.WINDOWS)
public class DeleteTest {
    private static WebDriver driver;
    private static PageObjectLogin loginPage;
    private static PageObjectCreate createPage;
    private static PageObjectDelete deletePage;

    @BeforeAll
    public static void setUp() throws InterruptedException {
        WebDriverManager.edgedriver().setup();
        driver = new EdgeDriver();

        loginPage = PageFactory.initElements(driver, PageObjectLogin.class);
        createPage = PageFactory.initElements(driver, PageObjectCreate.class);
        deletePage = PageFactory.initElements(driver, PageObjectDelete.class);

        loginPage.refreshPage();
        loginPage.login("testacc@test.com", "test123");
        createPage.refreshPage();
        createPage.createCorrectly();
    }

    @BeforeEach
    public void refreshPage() throws Exception {
        deletePage.refreshPage();
    }

    @Test
    public void deleteAddressTest() throws Exception {
        int numberOfAddresses = deletePage.getNumberOfAddresses();
        deletePage.deleteAddress();
        assertEquals(numberOfAddresses - 1, deletePage.getNumberOfAddresses());
    }

    @AfterAll
    public static void tearDownAfterClass() {
        driver.quit();
    }
}
