package utils.selenium;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import utils.drivers.ChromeWebDriver;
import utils.drivers.FirefoxWebDriver;

public class DriverController {

    public static DriverController instance = new DriverController();
    ////
    private static final Logger log = LogManager.getLogger(DriverController.class.getName());
    ////
    WebDriver webDriver;

    ////
    public void startChrome(String arg) {
        if (instance.webDriver != null) return;
        instance.webDriver = ChromeWebDriver.loadChromeDriver(arg);
    }

    public void startFirefox(String arg) {
        if (instance.webDriver != null) return;
        instance.webDriver = FirefoxWebDriver.loadFirefoxDriver(arg);
    }

    public void stopWebDriver() {
        if (instance.webDriver == null) return;

        try {
            instance.webDriver.quit();
            instance.webDriver.close();
        } catch (Exception e) {
            log.error(e + "::WebDriver stop error");
        }

        instance.webDriver = null;
        log.debug("::WebDriver stopped");
    }
}
