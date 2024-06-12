package utils.drivers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.nio.file.WatchEvent;

public class FirefoxWebDriver {

    public static WebDriver driver;

    private static void setupFirefoxDriver(){
        WebDriverManager.firefoxdriver().setup();
    }

    public static WebDriver loadFirefoxDriver (String firefoxArgument){
        setupFirefoxDriver();
        ////
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments(firefoxArgument);
        ////
        driver = new FirefoxDriver(options);
        return driver;
    }

}
