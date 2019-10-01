package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageObjectDelete {
    public WebDriver driver;
    private WebDriverWait wait;

    private static final String DELETE_PAGE = "http://automationpractice.com/index.php?controller=addresses";

    public PageObjectDelete(WebDriver webDriver){
        this.driver = webDriver;
        wait = new WebDriverWait(driver,10);
    }

    public void refreshPage() {
        driver.get(DELETE_PAGE);
        wait.until(ExpectedConditions.titleIs("Addresses - My Store"));
    }

    public void deleteAddress() throws InterruptedException {
        driver.findElement(By.xpath("//*[@id=\"center_column\"]/div[1]/div/div[2]/ul/li[9]/a[2]/span")).click();
        Thread.sleep(1000);

        driver.switchTo().alert().accept();
        wait.until(ExpectedConditions.titleIs("Addresses - My Store"));

        refreshPage();
    }

    public int getNumberOfAddresses(){
        return driver.findElements(By.className("box")).size();
    }

}
