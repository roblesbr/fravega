package pages;

import org.openqa.selenium.support.PageFactory;
import static utils.selenium.Driver.browser;

public class Page {
    public static <T extends BasePage> T instanceOf(Class<T> clazz) {
        return PageFactory.initElements(browser(), clazz);
    }
}