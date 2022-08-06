import core.FailedTestsTools;
import core.Platform;
import core.capabilities.CapabilitiesReader;
import io.appium.java_client.ios.IOSDriver;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;
import pageObjects.IOSCalender;
import pageObjects.NotificationPageIos;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class IOSNotificationTest extends GeneralTest {

    IOSDriver iosDriver;
    IOSCalender iosCalender;
    String eventName = "New Event test";
    String location = "Minsk";
    NotificationPageIos notificationPageIos;
    String bundleID;

    @Before
    public void driverSetup() throws MalformedURLException {
        Map<String, String> capabilities = CapabilitiesReader.capabilitiesRead(Platform.iOS);
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        URL driverURL = new URL(capabilities.get("driverUrl"));
        bundleID = capabilities.get("bundleId");

        for (String key : capabilities.keySet()) {
            if (!key.equals("driverUrl")) {
                desiredCapabilities.setCapability(key, capabilities.get(key));
            }
        }

        iosDriver = new IOSDriver(driverURL, desiredCapabilities);
        iosCalender = new IOSCalender(iosDriver);
        notificationPageIos = new NotificationPageIos(iosDriver);
    }

    @TmsLink(value = "2T")
    @Description(value = "Create event and check notification")
    @Test
    @Tag("ios")
    @DisplayName("Create event and check notification")
    public void calendarCreateEventTest ()  {
        LocalDateTime localDateTimeStart = LocalDateTime.now().plusMinutes(1);
        LocalDateTime localDateTimeEnd = localDateTimeStart.plusMinutes(90);
        DateTimeFormatter.ofPattern("hh-mm");

        iosCalender.clickToday();
        iosCalender.clickAdd();
        iosCalender.setName(eventName);
        iosCalender.setLocation(location);
        iosCalender.setStartTime(localDateTimeStart.getHour(), localDateTimeStart.getMinute());
        iosCalender.setEndTime(localDateTimeEnd.getHour(), localDateTimeEnd.getMinute());
        iosCalender.saveEvent();

        notificationPageIos.terminateApp(bundleID);
        notificationPageIos.showNotifications();
        System.out.println(notificationPageIos.getNotificationText());

        Assert.assertTrue("incorrect name" , notificationPageIos.getNotificationText().contains(eventName));

        Assert.assertTrue("incorrect time" , notificationPageIos.getNotificationText().contains(localDateTimeStart.format(DateTimeFormatter.ofPattern("hh-mm"))));

        notificationPageIos.hideNotifications();
        notificationPageIos.activateApp(bundleID);
    }

    @After
    public void cleanUP () {
        FailedTestsTools.attachAll(iosDriver);
        notificationPageIos.hideNotifications();
        notificationPageIos.activateApp(bundleID);
        iosCalender.deleteEvent(eventName);
        iosDriver.quit();
    }
}