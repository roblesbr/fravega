package utils.hooks;

import apis.BaseApiTest;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import utils.selenium.DriverController;

public class CucumberHooks {

    @Before("@Chrome")
    public void beforeChrome(){
        // This method starts the Chrome browser with extensions disabled.
        DriverController.instance.startChrome("--disable-extensions");
    }

    @Before("@Firefox")
    public void beforeFirefox() throws Exception {
        // This method starts the Firefox browser with extensions disabled.
        DriverController.instance.startFirefox("--disable-extensions");
    }

    @Before("@HeadlessChrome")
    public void beforeChromeHeadless() {
        // This method starts the Chrome browser in headless mode.
        DriverController.instance.startChrome("--headless");
    }

    @Before("@HeadlessFirefox")
    public void beforeHeadlessFirefox() throws Exception {
        // This method starts the Firefox browser in headless mode.
        DriverController.instance.startFirefox("--headless");
    }

    @After
    public void stopWebDriver() {
        // This method stops the WebDriver after the scenario is executed.
        DriverController.instance.stopWebDriver();
    }

    @Before("@Api")
    public void setBaseUri(){
        // This method sets the base URI for API tests.
        BaseApiTest.setBaseUri();
    }
}


