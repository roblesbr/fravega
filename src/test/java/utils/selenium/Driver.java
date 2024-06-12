package utils.selenium;

import org.openqa.selenium.WebDriver;

public class Driver {
    public static WebDriver browser() {
        return DriverController.instance.webDriver;
    }
}

