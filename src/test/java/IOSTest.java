import core.Platform;
import core.capabilities.CapabilitiesReader;
import io.appium.java_client.AppiumDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;
import pageObjects.IOSCalender;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Map;

public class IOSTest extends GeneralTest {

        AppiumDriver iosDriver;
        IOSCalender iosCalender;
        String eventName = "New Event test";
        String location = "Minsk";

        @Before
        public void driverSetup() throws MalformedURLException {
            Map<String, String> capabilities = CapabilitiesReader.capabilitiesRead(Platform.iOS);
            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
            URL driverURL = new URL(capabilities.get("driverUrl"));

            for (String key : capabilities.keySet()) {
                if (!key.equals("driverUrl")) {
                    desiredCapabilities.setCapability(key, capabilities.get(key));
                }
            }

            iosDriver = new AppiumDriver(driverURL, desiredCapabilities);
            iosCalender = new IOSCalender(iosDriver);
        }

        @Test
        public void calendarCreateEventTest ()  {
            LocalDateTime localDateTimeStart = LocalDateTime.now().plusMinutes(60);
            LocalDateTime localDateTimeEnd = localDateTimeStart.plusMinutes(90);

            iosCalender.clickToday();
            iosCalender.clickAdd();
            iosCalender.setName(eventName);
            iosCalender.setLocation(location);
            iosCalender.setStartTime(localDateTimeStart.getHour(), localDateTimeStart.getMinute());
            iosCalender.setEndTime(localDateTimeEnd.getHour(), localDateTimeEnd.getMinute());
            iosCalender.saveEvent();
            iosCalender.openEvent(eventName);

            Assert.assertEquals("incorrect event name", eventName, iosCalender.getEventName());
            Assert.assertEquals("incorrect time", iosCalender.getTimes(localDateTimeStart, localDateTimeEnd), iosCalender.getEventTimes());
            Assert.assertTrue("incorrect place" , iosCalender.getLocationName().contains(location));
        }

        @After
        public void cleanUP () throws InterruptedException {
          iosCalender.deleteEvent(eventName);
          iosDriver.quit();
        }
}
