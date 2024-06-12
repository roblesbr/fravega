package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import utils.settings.Settings;

import java.util.List;

import static utils.selenium.Driver.browser;

public class BasePage extends Page {

    public WebDriver driver = browser();
    private String getTitle() { return driver.getTitle(); }
    private String getUrl() {return driver.getCurrentUrl(); }
    private String getPageSource() {return driver.getPageSource(); }

    public void navigateToBaseUrl() {
        String baseUrl = Settings.baseUrl;
        browser().navigate().to(baseUrl);
        System.out.println("Welcome to Product - Selenium Automation Framework");
    }

    public void scrollDownAndClick() throws InterruptedException {
        // Realizar el intento de hacer clic
        boolean addToCartClicked = false;
        try {
            WebElement addToCartButton = driver.findElement(By.xpath("//*/text()[normalize-space(.)='ADD TO CART']/parent::*"));
            addToCartButton.click();
            addToCartClicked = true;
        } catch (Exception e) {
            // El botón no se encontró, se realiza scroll down
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,600);");
            Thread.sleep(2000);
        }

        // Si el botón no fue clickeado, intenta hacer clic después del scroll down
        if (!addToCartClicked) {
            try {
                WebElement addToCartButton = driver.findElement(By.xpath("//*/text()[normalize-space(.)='ADD TO CART']/parent::*"));
                addToCartButton.click();
            } catch (Exception e) {
                // Si aún no se encuentra el botón, puedes manejar el error aquí
                System.out.println("El botón ADD TO CART no se encontró después del scroll.");
            }
        }
    }

    public void enter() {
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.ENTER).perform();
    }

    public void validatePageTitle(String expectedTitle) {
        Assert.assertTrue(getTitle().contains(expectedTitle));
        System.out.println(":: The title of the site is: " + getTitle());
    }

    public void validatePageUrl(String expectedUrl) {
        Assert.assertTrue(getUrl().contains(expectedUrl));
        System.out.println(":: The page Url is: " + getUrl());
    }

    public void validatePageSource(String expectedPageSource) {
        Assert.assertTrue(getPageSource().contains(expectedPageSource));
        System.out.println(":: The page source is: " + getPageSource());
    }

    public void validateMultipleInPageSource(List<String> table) {
        for (String row : table) {
            Assert.assertTrue(getPageSource().contains(row));
            System.out.println("The text " + row + " is in the PageSource");
        }
    }

    public void scrollDownToBottom() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    public void changeTab(){

    }

}