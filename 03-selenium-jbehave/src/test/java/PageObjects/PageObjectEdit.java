package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageObjectEdit {
    public WebDriver driver;
    private WebDriverWait wait;

    private static final String EDIT_PAGE = "http://automationpractice.com/index.php?controller=addresses";

    public PageObjectEdit(WebDriver webDriver) {
        this.driver = webDriver;
        wait = new WebDriverWait(driver,10);
    }

    public void refreshPage() {
        driver.get(EDIT_PAGE);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"center_column\"]/div[1]/div/div/ul/li[1]/h3")));
    }

    public void editAddressCorrectly() {
        refreshPage();

        driver.findElement(By.xpath("//*[@id=\"center_column\"]/div[1]/div/div[2]/ul/li[9]/a[1]/span")).click();
        wait.until(ExpectedConditions.titleIs("Address - My Store"));

        driver.findElement(By.id("alias")).clear();
        driver.findElement(By.id("alias")).sendKeys("My Edited Address");

        driver.findElement(By.id("submitAddress")).click();
        wait.until(ExpectedConditions.titleIs("Addresses - My Store"));
    }

    public void editAddressIncorrectly() {
        refreshPage();

        driver.findElement(By.xpath("//*[@id=\"center_column\"]/div[1]/div/div[2]/ul/li[9]/a[1]/span")).click();
        wait.until(ExpectedConditions.titleIs("Address - My Store"));

        driver.findElement(By.id("postcode")).clear();
        driver.findElement(By.id("postcode")).sendKeys("123");

        driver.findElement(By.id("submitAddress")).click();
        wait.until(ExpectedConditions.titleIs("Addresses - My Store"));
    }

    public String getSecondAddressAlias() {
        return driver.findElement(By.xpath("//*[@id=\"center_column\"]/div[1]/div/div[2]/ul/li[1]/h3")).getText();
    }

    public boolean assertErrorMessageIsDisplayed() {
        Boolean result = driver.findElement(By.cssSelector(".alert.alert-danger")).isDisplayed();
        return(result);
    }

    public void deleteEditedAddress() throws InterruptedException {
        refreshPage();
        driver.findElement(By.xpath("//*[@id=\"center_column\"]/div[1]/div/div[2]/ul/li[9]/a[2]/span")).click();
        Thread.sleep(1000);

        driver.switchTo().alert().accept();
        wait.until(ExpectedConditions.titleIs("Addresses - My Store"));
    }

}
