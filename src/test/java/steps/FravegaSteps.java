package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.FravegaPage;

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
        clickCategorias();
        overTecnologias();
    }

    @And("I filter products by the brand {string}")
    public void filterByBrand(String brand) {
        clickSamsung();
    }

    @Then("I verify that all displayed products belong to the {string} brand")
    public void verifyProductsByBrand(String brand) {
        // Implementación del paso para verificar que todos los productos son de la marca
    }
}
