import core.FailedTestsTools;
import core.Platform;
import core.SingletonDriver;
import core.TestListeners;
import core.capabilities.CapabilitiesReader;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.remote.DesiredCapabilities;
import pageObjects.AndroidCalendar;
import pageObjects.NotificationPageAndroid;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@ExtendWith(TestListeners.class)

public class AndroidNotificationTest extends GeneralTest {

    AndroidDriver androidDriver;
    AndroidCalendar androidCalendar;
    String eventName = "New Event test";
    String location = "Minsk";
    NotificationPageAndroid notificationPageAndroid;
    String packageName;
    LocalDateTime localDateTimeStart;


    @Before
    public void driverSetup() throws IOException {
        Map<String, String> capabilities = CapabilitiesReader.capabilitiesRead(Platform.Android);
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        URL driverURL = new URL(capabilities.get("driverUrl"));

        for (String key : capabilities.keySet()) {
            if (!key.equals("driverUrl")) {
                desiredCapabilities.setCapability(key, capabilities.get(key));
            }
        }

        //androidDriver = new AndroidDriver(driverURL, desiredCapabilities);
        androidDriver = SingletonDriver.getDriverInstance(Platform.Android, desiredCapabilities, driverURL).androidDriver;
        androidCalendar = new AndroidCalendar(androidDriver);
        notificationPageAndroid = new NotificationPageAndroid(androidDriver);
        packageName = capabilities.get("appPackage");
    }

    @TmsLink(value = "1T")
    @Description(value = "Create event and check notification")
    @Test
    @Tag("android")
    @DisplayName("Create event and check notification")
    public void calendarCreateEventTest () {
        localDateTimeStart = LocalDateTime.now().plusMinutes(1);
        LocalDateTime localDateTimeEnd = localDateTimeStart.plusMinutes(90);

        androidCalendar.clickPlusButton();
        androidCalendar.enterEventName(eventName);

        androidCalendar.setStartTime(localDateTimeStart.getHour(), localDateTimeStart.getMinute());
        androidCalendar.setEndTime(localDateTimeEnd.getHour(), localDateTimeEnd.getMinute());
        androidCalendar.setLocation(location);
        androidCalendar.saveEvent();

        androidDriver.runAppInBackground(Duration.ZERO);
        notificationPageAndroid.openNotification();

        Assert.assertTrue("incorrect name" , notificationPageAndroid.getNotificationText().contains(eventName));
        Assert.assertTrue("incorrect time" , notificationPageAndroid.getNotificationTime().contains(localDateTimeStart.format(DateTimeFormatter.ofPattern("HH:mm"))));
    }

    @After
    public void cleanUP ()  {
        FailedTestsTools.attachAll(androidDriver);
        notificationPageAndroid.closeNotifications();
        androidDriver.activateApp(packageName);
        androidCalendar.openEvent(localDateTimeStart);
        androidCalendar.deleteEvent();
        androidDriver.quit();
    }
}
