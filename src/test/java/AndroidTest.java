import core.FailedTestsTools;
import core.Platform;
import core.SingletonDriver;
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
import org.openqa.selenium.remote.DesiredCapabilities;
import pageObjects.AndroidCalendar;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Map;


public class AndroidTest extends GeneralTest {
    AndroidDriver androidDriver;
    AndroidCalendar androidCalendar;
    String eventName = "New Event test";
    String location = "Minsk";


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

        androidDriver = new AndroidDriver(driverURL, desiredCapabilities);
        androidCalendar = new AndroidCalendar(androidDriver);
    }

    @TmsLink(value = "4T")
    @Description(value = "Create event no check notification")
    @Test
    @Tag("android")
    @DisplayName("Create event and no check notification")
    public void calendarCreateEventTest () {
      LocalDateTime localDateTimeStart = LocalDateTime.now().plusMinutes(60);
      LocalDateTime localDateTimeEnd = localDateTimeStart.plusMinutes(90);

      androidCalendar.clickPlusButton();
      androidCalendar.enterEventName(eventName);

      androidCalendar.setStartTime(localDateTimeStart.getHour(), localDateTimeStart.getMinute());
      androidCalendar.setEndTime(localDateTimeEnd.getHour(), localDateTimeEnd.getMinute());
      androidCalendar.setLocation(location);
      androidCalendar.saveEvent();

      androidCalendar.openEvent(localDateTimeStart);

      Assert.assertEquals("incorrect event name", eventName, androidCalendar.getEventName());
      Assert.assertEquals("incorrect time", androidCalendar.getTimes(localDateTimeStart, localDateTimeEnd), androidCalendar.getEventTimes());
      Assert.assertEquals("incorrect location", androidCalendar.getLocationName(), location);

    }

    @After
    public void cleanUP () {
        FailedTestsTools.attachAll(androidDriver);
        androidCalendar.deleteEvent();
        androidDriver.quit();
    }
}
