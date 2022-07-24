import core.Platform;
import core.capabilities.CapabilitiesReader;
import io.appium.java_client.android.AndroidDriver;
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

public class AndroidTest extends GeneralTest{
    AndroidDriver androidDriver;
    AndroidCalendar androidCalendar;
    String eventName = "New Event test";
    Map<String, String> capabilities;


    @Before
    public void driverSetup() throws IOException {
        capabilities = CapabilitiesReader.capabilitiesRead(Platform.Android, false);
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        URL driverURL = new URL(capabilities.get("driverUrl"));

        startSimulator(Platform.Android, capabilities.get("deviceName"));

        for (String key : capabilities.keySet()) {
            if (!key.equals("driverUrl")) {
                desiredCapabilities.setCapability(key, capabilities.get(key));
            }
        }

        androidDriver = new AndroidDriver(driverURL, desiredCapabilities);
        androidCalendar = new AndroidCalendar(androidDriver);
    }

    @Test
    public void calendarCreateEventTest () throws InterruptedException {
      LocalDateTime localDateTimeStart = LocalDateTime.now().plusMinutes(60);
      LocalDateTime localDateTimeEnd = localDateTimeStart.plusMinutes(90);

    /*  androidCalendar.clickPlusButton();
      androidCalendar.enterEventName(eventName);

      androidCalendar.setStartTime(localDateTimeStart.getHour(), localDateTimeStart.getMinute());
      androidCalendar.setEndTime(localDateTimeEnd.getHour(), localDateTimeEnd.getMinute());
      androidCalendar.saveEvent();

      androidCalendar.openEvent(localDateTimeStart);

      Assert.assertEquals("incorrect event name", eventName, androidCalendar.getEventName());
      Assert.assertEquals("incorrect time", androidCalendar.getTimes(localDateTimeStart, localDateTimeEnd), androidCalendar.getEventTimes()); */
    }

    @After
    public void cleanUP () throws InterruptedException {
        //androidCalendar.deleteEvent();
        androidDriver.quit();
        stopSimulator(Platform.Android);
    }
}
