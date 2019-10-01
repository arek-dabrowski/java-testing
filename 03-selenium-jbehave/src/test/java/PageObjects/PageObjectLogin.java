package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageObjectLogin {

    public WebDriver driver;

    private static final String LOGIN_PAGE = "http://automationpractice.com/index.php?controller=authentication&back=my-account";

    @FindBy(id = "email")
    private WebElement emailField;

    @FindBy(id = "passwd")
    private WebElement passwordField;

    @FindBy(id = "SubmitLogin")
    private WebElement submitLoginButton;

    @FindBy(css = ".alert.alert-danger")
    private WebElement error;

    @FindBy(linkText = "Sign out")
    private WebElement signOut;

    public PageObjectLogin(WebDriver webDriver){
        this.driver = webDriver;
    }

    public void login(String email, String password) throws InterruptedException {
        emailField.sendKeys(email);
        passwordField.sendKeys(password);
        submitLoginButton.click();
        Thread.sleep(1000);
    }

    public void refreshPage(){
        driver.get(LOGIN_PAGE);
    }

    public boolean assertTitleIsInMyAccount() throws Exception{
        Boolean result = driver.getTitle().contains("My account - My Store");
        signOut.click();
        return(result);
    }

    public boolean assertErrorMessageIsDisplayed() throws Exception{
        Boolean result = error.isDisplayed();
        return(result);
    }
}
