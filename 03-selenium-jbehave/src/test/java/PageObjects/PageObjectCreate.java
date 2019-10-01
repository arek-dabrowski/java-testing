package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.*;

import java.util.concurrent.TimeUnit;

public class PageObjectCreate {
    public WebDriver driver;
    private FluentWait<WebDriver> wait;

    private static final String CREATE_PAGE = "http://automationpractice.com/index.php?controller=addresses";

    public PageObjectCreate(WebDriver webDriver){
        this.driver = webDriver;
        wait = new FluentWait<>(driver)
                .withTimeout(10, TimeUnit.SECONDS)
                .pollingEvery(2, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class);
    }

    public void refreshPage() throws InterruptedException {
        driver.get(CREATE_PAGE);
        wait.until(ExpectedConditions.titleIs("Addresses - My Store"));
        driver.findElement(By.xpath("//*[@id=\"center_column\"]/div[2]/a/span")).click();
        wait.until(ExpectedConditions.titleIs("Address - My Store"));
    }

    public void createIncorrectly(){
        driver.findElement(By.id("firstname")).clear();
        driver.findElement(By.id("firstname")).sendKeys("My Name");

        driver.findElement(By.id("submitAddress")).click();
    }

    public void createCorrectly() throws InterruptedException {
        driver.findElement(By.id("firstname")).clear();
        driver.findElement(By.id("firstname")).sendKeys("Name");

        driver.findElement(By.id("lastname")).clear();
        driver.findElement(By.id("lastname")).sendKeys("Lastname");

        driver.findElement(By.id("company")).clear();
        driver.findElement(By.id("company")).sendKeys("New Company");

        driver.findElement(By.id("address1")).sendKeys("4456 Mount Eden Road, Mount Eden");

        driver.findElement(By.id("city")).sendKeys("Auckland");

        Select state = new Select(driver.findElement(By.id("id_state")));
        state.selectByVisibleText("Texas");

        driver.findElement(By.id("postcode")).sendKeys("41125");

        driver.findElement(By.id("phone_mobile")).sendKeys("155266344");

        driver.findElement(By.id("alias")).clear();
        driver.findElement(By.id("alias")).sendKeys("My New Address");

        driver.findElement(By.id("other")).sendKeys("Other Informations");

        driver.findElement(By.id("submitAddress")).click();
        wait.until(ExpectedConditions.titleIs("Addresses - My Store"));
    }

    public boolean assertTitleIsInMyAddresses() throws Exception{
        Boolean result = driver.getTitle().contains("Addresses - My Store");

        deleteAddedAddress();

        return(result);
    }

    public boolean assertErrorMessageIsDisplayed() throws Exception{
        Boolean result = driver.findElement(By.cssSelector(".alert.alert-danger")).isDisplayed();
        return(result);
    }

    private void deleteAddedAddress() throws InterruptedException {
        driver.findElement(By.xpath("//*[@id=\"center_column\"]/div[1]/div/div[2]/ul/li[9]/a[2]/span")).click();
        Thread.sleep(1000);

        driver.switchTo().alert().accept();
        Thread.sleep(1000);

        wait.until(ExpectedConditions.titleIs("Addresses - My Store"));
    }
}
