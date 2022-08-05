package pageObjects;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.functions.ExpectedCondition;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class NotificationPageIos {
    IOSDriver iosDriver;

    private final By notification = MobileBy.iOSClassChain("**/XCUIElementTypeScrollView[`label BEGINSWITH \"CALENDAR\"`]");


    public NotificationPageIos(IOSDriver driver) {
        this.iosDriver = driver;
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
        WebDriverWait webDriverWait = new WebDriverWait(iosDriver, 60);
        return webDriverWait.until(elementPresents(by));
    }

    public void terminateApp (String bundle) {
    iosDriver.terminateApp(bundle);
    }

    public void activateApp (String bundle) {
        iosDriver.activateApp(bundle);
    }

    public void showNotifications() {
        manageNotifications(true);
    }

    public void hideNotifications() {
        manageNotifications(false);
    }

    public String getNotificationText () {
       return findWithWait(notification).getText();
    }

    private void manageNotifications(Boolean show) {
        Dimension screenSize = iosDriver.manage().window().getSize();
        int yMargin = 5;
        int xMid = screenSize.width / 2;
        PointOption top = PointOption.point(xMid, yMargin);
        PointOption bottom = PointOption.point(xMid, screenSize.height - yMargin);

        TouchAction action = new TouchAction(iosDriver);
        if (show) {
            action.press(top);
        } else {
            action.press(bottom);
        }
        action.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)));
        if (show) {
            action.moveTo(bottom);
        } else {
            action.moveTo(top);
        }
        action.perform();
    }
}
