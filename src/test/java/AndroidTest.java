import core.Platform;
import core.capabilities.CapabilitiesReader;
import io.appium.java_client.AppiumDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;
import pageObjects.AndroidCalendar;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Map;

public class AndroidTest extends GeneralTest {
    AppiumDriver androidDriver;
    AndroidCalendar androidCalendar;
    String eventName = "New Event test";
    String location = "Minsk";


    @Before
    public void driverSetup() throws MalformedURLException {
        Map<String, String> capabilities = CapabilitiesReader.capabilitiesRead(Platform.Android);
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        URL driverURL = new URL(capabilities.get("driverUrl"));

        for (String key : capabilities.keySet()) {
            if (!key.equals("driverUrl")) {
                desiredCapabilities.setCapability(key, capabilities.get(key));
            }
        }

        androidDriver = new AppiumDriver(driverURL, desiredCapabilities);
        androidCalendar = new AndroidCalendar(androidDriver);
    }

    @Test
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
        androidCalendar.deleteEvent();
        androidDriver.quit();
    }
}
