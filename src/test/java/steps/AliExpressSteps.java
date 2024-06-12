package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import pages.BasePage;

import java.util.Set;


public class AliExpressSteps extends BasePage {


    @Given("I am on the AliExpress website")
    public void goToAliExpressWebsite() {
        navigateToBaseUrl();
        System.out.println("Ali Express");
    }

    @When("I search for {string}")
    public void searchForProduct(String product) throws InterruptedException {
        Thread.sleep(1500);
        WebElement smallElement = null;
        WebElement bigScreenElement = null;
        try {
            smallElement = driver.findElement(By.xpath("/html/body/div[7]/div/div[2]/div[3]/div[2]"));
            bigScreenElement = driver.findElement(By.xpath("/html/body/div[6]/div/div[2]/div[3]/div[2]"));
        } catch (Exception ignored) {}


// Try to click on the first element
        if (smallElement != null) {
            smallElement.click();
        } else if (bigScreenElement != null) {
            // If it fails, try clicking on the second element
            bigScreenElement.click();
        }
        Thread.sleep(1500);
        WebElement searchBox = driver.findElement(By.id("search-words"));
        searchBox.sendKeys(product);
        enter();
    }

    @And("I navigate to the second results page")
    public void navigateToSecondResultsPage() throws InterruptedException {

        Thread.sleep(1500);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement nextPageButton = null;

        while (nextPageButton == null || !nextPageButton.isDisplayed()) {
            js.executeScript("window.scrollBy(0, 3500)");
            nextPageButton = driver.findElement(By.linkText("2"));
        }
        nextPageButton.click();
    }


    @And("I click on the second item")
    public void clickOnSecondItem() throws InterruptedException {
        Thread.sleep(2000);
        // Obtener el identificador de la ventana principal
        String mainWindowHandle = driver.getWindowHandle();

        // Hacer clic en el segundo elemento
        WebElement secondItem = driver.findElement(By.xpath("//div[@id='card-list']/div[2]/div/div/a/div/div/div"));
        secondItem.click();

        Thread.sleep(2000);

        // Obtener todos los identificadores de ventana
        Set<String> allWindowHandles = driver.getWindowHandles();
        // Iterar a través de los identificadores de ventana
        for (String windowHandle : allWindowHandles) {
            // Cambiar al identificador de ventana que no sea el de la ventana principal
            if (!windowHandle.equals(mainWindowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }


    }

    @Then("I should see at least 1 item available for purchase for the second item")
    public void verifyAvailabilityOfSecondItem() throws InterruptedException {

        WebElement availabilityLabel = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[3]/div/div[2]/div/div/div[7]/div[3]/div/span"));
        String label = availabilityLabel.getText();
        System.out.println("Availability label: " + label);

        int itemCount = Integer.parseInt(label.replaceAll("[^0-9]", ""));
        if (itemCount >= 1) {
            System.out.println("There is at least 1 item available for purchase.");
            Assert.assertTrue(true, "There is at least 1 item available for purchase.");
        } else {
            System.out.println("No items available for purchase.");
            Assert.fail("No items available for purchase.");
        }
    }
}
