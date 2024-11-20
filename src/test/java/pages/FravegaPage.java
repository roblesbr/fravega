package pages;

import gherkin.lexer.Th;
import org.graalvm.compiler.lir.amd64.AMD64ArrayCompareToOp;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.settings.Settings;

import java.time.Duration;

import static java.time.Duration.ofSeconds;
import static utils.selenium.Driver.browser;

public class FravegaPage extends Page{


    public static WebDriver driver = browser();

    Actions acciones = new Actions(driver);
    // Localizadores
    private By categoriasLink = By.xpath("/html/body/div/div[2]/header/div[3]/div/div[1]/div[1]/span[1]");
    //private By tecnologiasMenu = By.xpath("xpath=//div[@id='__next']/div[2]/header/div[3]/div/div/div/div/div/div[2]/div/a");

    private By tecnologiasMenu = By.xpath("\"//div[@id='__next']/div[2]/div/div/div/div/ul/li[3]/div/a/div/div/picture[2]/img");

    private By samsungOption = By.xpath("//div[@id='__next']/div[2]/header/div[3]/div/div/div/div/div/div[2]/div[2]/div[2]/div[4]/div/a/span");
    private By searchBox = By.name("keyword");
    private By searchButton = By.xpath("/html/body/div/div[2]/header/div[2]/form/fieldset/div[1]/button");
    private By secondProduct = By.xpath("/html/body/div[1]/div[2]/div[1]/div[3]/div[5]/ul/li[2]/article/a/a/div/figure/picture[1]/img");
    private By buyButton = By.xpath("/html/body/div[1]/div[2]/div[1]/div[3]/div[2]/div/div[2]/div[9]/button");
    private By addToCartButton = By.xpath("/html/body/div/div/div[1]/main/div[1]/div[1]/div/div/div[2]/button[2]");
    private By continueShoppingButton = By.xpath("/html/body/div/div/div[1]/main/div[1]/div[2]/div[2]/div[3]/button[2]");


    public void clickCategorias() throws InterruptedException {

        Thread.sleep(6000);
        driver.findElement(categoriasLink).click();
        Thread.sleep(6000);
    }

    public void overTecnologias() throws InterruptedException {

        Thread.sleep(6000);
        acciones.moveToElement(driver.findElement(categoriasLink)).build().perform();
        Thread.sleep(6000);
        driver.findElement(categoriasLink).click();
        Thread.sleep(6000);
        acciones.moveToElement(driver.findElement(tecnologiasMenu)).build().perform();
        Thread.sleep(6000);
    }

    public void clickSamsung() throws InterruptedException {
        Thread.sleep(6000);
        acciones.moveToElement(driver.findElement(samsungOption)).build().perform();
        Thread.sleep(6000);
        driver.findElement(samsungOption).click();
    }

    public void searchForProduct(String product) {
        // Buscar el campo de texto y escribir el producto
        driver.findElement(searchBox).sendKeys(product);
        // Hacer clic en el botón de búsqueda
        driver.findElement(searchButton).click();
        System.out.println("Buscando el producto: " + product);
    }

    public void selectSecondProduct() {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        WebElement secondProductElement = wait.until(ExpectedConditions.visibilityOfElementLocated(secondProduct));
        secondProductElement.click();
        System.out.println("Producto seleccionado.");
    }

    public void checkProductStock() {
        WebDriverWait wait = new WebDriverWait(driver, 5);

        try {
            // Hacer scroll hacia la mitad de la página
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollTo(0, document.body.scrollHeight / 2);");

            // Esperar a que el botón "Comprar" sea visible
            WebElement buyButtonElement = wait.until(ExpectedConditions.visibilityOfElementLocated(buyButton));
            if (buyButtonElement.isDisplayed()) {
                buyButtonElement.click();
                System.out.println("El producto tiene stock y está disponible para compra.");
            }
        } catch (TimeoutException e) {
            System.out.println("No se pudo encontrar el botón de compra o el producto no tiene stock.");
        }
    }

    public void addToShoppingCart() {
        WebDriverWait wait = new WebDriverWait(driver, 5);

        try {
            // Hacer clic en el botón "Agregar al carrito"
            WebElement addToCartElement = wait.until(ExpectedConditions.visibilityOfElementLocated(addToCartButton));
            addToCartElement.click();
            System.out.println("Producto agregado al carrito.");

            // Hacer clic en "Continuar comprando"
            WebElement continueShoppingElement = wait.until(ExpectedConditions.visibilityOfElementLocated(continueShoppingButton));
            continueShoppingElement.click();
            System.out.println("Continuando con la compra.");
        } catch (TimeoutException e) {
            System.out.println("No se pudo agregar el producto al carrito o no se encontró el botón.");
        }
    }

    public void navigateToBaseUrl() {
        String baseUrl = Settings.baseUrl;
        browser().navigate().to(baseUrl);
        closePopUp();
        System.out.println("Selenium Automation Framework");
    }

    public void verifyProductInCart(){
        closePopUp();  // Primero cerramos el popup

        // Hacemos clic en el ícono del carrito
        driver.findElement(By.cssSelector("svg[alt=\"cart\"]")).click();

        // Esperamos 3 segundos para que cargue el mensaje
        try {
            Thread.sleep(3000); // Esperamos 3 segundos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Buscamos el mensaje que indica el estado del carrito con el nuevo XPath
        WebElement mensajeEmpty = driver.findElement(By.xpath("/html/body/div[1]/div[2]/header/div[2]/div[3]/div/div/div[1]/ul/li/div/a"));

        // Extraemos el texto del mensaje
        String mensajeTexto = mensajeEmpty.getText();

        // Verificamos si el carrito está vacío o si el producto fue agregado correctamente
        if (mensajeTexto.equals("Tu carrito está vacío")) {
            System.out.println("El carrito está vacío. No hay productos en el carrito.");
        } else {
            System.out.println("El producto fue agregado correctamente al carrito. Producto: " + mensajeTexto);
        }
    }


    public static void closePopUp() {
        try {
            //Espera explicita para verificar si el pop up esta visible
            WebDriverWait wait = new WebDriverWait(driver, 5);
            WebElement closePopup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div[1]/div[1]/button")));
            closePopup.click();
            System.out.println("Pop-up cerrado");
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("No se encontró el popup - Se agotó el tiempo de espera");
        }
    }
}
