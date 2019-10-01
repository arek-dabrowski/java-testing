package app.steps;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.jbehave.core.annotations.*;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CartSteps {

    private static WebDriver driver;

    @Given("a chrome browser")
    public void aChromeBrowser(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("http://automationpractice.com/index.php");
    }

    @When("I get to cart page")
    public void iGetToCartPage() throws InterruptedException {
        driver.findElement(By.xpath("//*[@id=\"header\"]/div[3]/div/div/div[3]/div/a/b")).click();
        Thread.sleep(1000);
    }

    @When("I add t shirt and proceed")
    public void iAddTShirtToCartAndProceed() throws InterruptedException {
        driver.findElement(By.xpath("//*[@id=\"block_top_menu\"]/ul/li[3]/a")).click();
        Thread.sleep(1000);

        driver.findElement(By.xpath("//*[@id=\"center_column\"]/ul/li/div/div[2]/div[2]/a[1]/span")).click();
        Thread.sleep(1000);

        driver.get("http://automationpractice.com/index.php");
        driver.findElement(By.xpath("//*[@id=\"header\"]/div[3]/div/div/div[3]/div/a/b")).click();
        Thread.sleep(1000);
    }

    @When("I add t shirt proceed and delete")
    public void iAddTShirtToCartProceedAndDelete() throws InterruptedException {
        iAddTShirtToCartAndProceed();

        driver.findElement(By.id("1_1_0_0")).click();
        Thread.sleep(1000);

        iGetToCartPage();
    }

    @When("I add t shirt and proceed to checkout")
    public void iAddTShirtToCartAndProceedToCheckout() throws InterruptedException {
        iAddTShirtToCartAndProceed();

        driver.findElement(By.xpath("//*[@id=\"center_column\"]/p[2]/a[1]")).click();
        Thread.sleep(1000);
    }

    @When("I get to category $link")
    public void whenIGetCategoryLink(String link) throws InterruptedException {
        driver.get(link);
        Thread.sleep(1000);
    }

    @Then("Cart is empty")
    @Aliases(values={"Cart has no items",
            "Cart is null"})
    public void cartIsEmpty(){
        assertTrue(driver.findElement(By.cssSelector(".alert.alert-warning")).isDisplayed());
    }

    @Then("T shirt is in cart")
    public void tshirtIsInCart(){
        assertTrue(driver.findElement(By.linkText("Faded Short Sleeve T-shirts")).isDisplayed());
    }

    @Then("Page title is login")
    public void pageTitleIsLogin(){
        assertTrue(driver.getTitle().equalsIgnoreCase("Login - My Store"));
    }

    @Then("Number of items will be $result")
    public void thenNumberWillBeResult(int result) {
        assertEquals(result, driver.findElements(By.className("available-now")).size());    }

    @AfterScenario(uponType=ScenarioType.ANY)
    public void tearDown(){
        System.out.println("Closing chrome driver");
        driver.quit();
    }

}
