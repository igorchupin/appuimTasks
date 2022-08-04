package pageObjects;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.functions.ExpectedCondition;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class GeneralPage {

    AppiumDriver driver;
    TouchAction touchAction;

    public GeneralPage(AppiumDriver driver) {
        this.driver = driver;
        touchAction = new TouchAction(driver);
    }

    private ExpectedCondition<MobileElement> elementPresents (By by) {
        return driver -> {
            List<MobileElement> list;
            list = driver.findElements(by);
            if (list.size() > 0 && list.get(0).isDisplayed() && list.get(0).isEnabled()) {
                return list.get(0);
            } else return null;
        };
    }

    public MobileElement findWithWait (By by) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 6);
        return webDriverWait.until(elementPresents(by));
    }
}
