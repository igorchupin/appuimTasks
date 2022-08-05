package pageObjects;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.functions.ExpectedCondition;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class NotificationPageAndroid {
    AndroidDriver androidDriver;

    private final By notificationName = By.xpath("//android.widget.TextView[@text= 'Calendar']/../..//android.widget.TextView[@resource-id = 'android:id/title']");
    private final By notificationTime = By.xpath("//android.widget.TextView[@text= 'Calendar']/../..//android.widget.TextView[@resource-id = 'android:id/text']");

    public NotificationPageAndroid(AndroidDriver driver) {
        this.androidDriver = driver;
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
        WebDriverWait webDriverWait = new WebDriverWait(androidDriver, 90);
        return webDriverWait.until(elementPresents(by));
    }

    public void openNotification() {
        androidDriver.openNotifications();
    }

    public void closeNotifications() {
        androidDriver.pressKey(new KeyEvent(AndroidKey.BACK));
    }

    public String getNotificationText () {
        return findWithWait(notificationName).getText();
    }
    public String getNotificationTime () {
        return findWithWait(notificationTime).getText();
    }

}
