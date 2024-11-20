package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import pages.FravegaPage;

import java.util.List;

public class FravegaSteps extends FravegaPage {

    @Given("Im on the Fravega website")
    public void goToFravegaWebsite() {
        navigateToBaseUrl();
        System.out.println("Fravega");
    }

    @When("I search for {string} in the search bar")
    public void search_ForProduct(String product) {
    searchForProduct(product);
    }

    @And("I select the second product from the search results")
    public void select_SecondProduct() {
       selectSecondProduct();
    }

    @Then("I check that the product has stock")
    public void check_ProductStock() {
       checkProductStock();
    }

    @When("I add the product to the shopping cart")
    public void add_ToShoppingCart() {
    addToShoppingCart();
    }

    @Then("I verify that the product is in the shopping cart")
    public void verify_ProductInCart() {
        verifyProductInCart();
    }

    @When("I select the category {string}")
    public void select_Category(String category) throws InterruptedException {
        overTecnologias();
    }

    @And("I filter products by the brand {string}")
    public void filterByBrand(String brand) throws InterruptedException {
        clickSamsung();
    }

    @Then("I verify that all displayed products belong to the {string} brand")
    public void verifyProductsByBrand(String brand) throws InterruptedException {
        // Validar el título de categoría
        Thread.sleep(5000);
        WebElement categoryLabel = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/div[3]/div[3]/h1"));
        Assert.assertTrue(categoryLabel.getText().contains(brand),
                "La categoría no contiene la marca esperada: " + brand);

        // Validar los filtros aplicados
        WebElement appliedFilters = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/div[3]/div[4]/div[3]/div[2]/span"));
        Assert.assertTrue(appliedFilters.getText().contains(brand),
                "El filtro aplicado no contiene la marca esperada: " + brand);

        // Validar cada producto listado
        List<WebElement> products = driver.findElements(By.xpath("//span[@class='sc-ca346929-0 czeMAx']")); // XPath ajustado
        Assert.assertFalse(products.isEmpty(), "No se encontraron productos en la página."); // Validación de productos visibles

        for (WebElement product : products) {
            String productName = product.getText();
            Assert.assertTrue(productName.contains(brand),
                    "El producto no pertenece a la marca esperada: " + productName);
        }

        System.out.println("Todos los productos pertenecen a la marca " + brand);
    }


}
