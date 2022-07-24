import core.Platform;
import core.capabilities.CapabilitiesReader;
import io.appium.java_client.ios.IOSDriver;
import org.junit.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import pageObjects.IOSCalender;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Map;

public class IOSTest extends GeneralTest{

        IOSDriver iosDriver;
        IOSCalender iosCalender;
        String eventName = "New Event test";
        Map<String, String> capabilities;

        @Before
        public void driverSetup() throws IOException {
            capabilities = CapabilitiesReader.capabilitiesRead(Platform.iOS, false);
            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
            URL driverURL = new URL(capabilities.get("driverUrl"));

            for (String key : capabilities.keySet()) {
                if (!key.equals("driverUrl")) {
                    desiredCapabilities.setCapability(key, capabilities.get(key));
                }
            }

            iosDriver = new IOSDriver(driverURL, desiredCapabilities);
            iosCalender = new IOSCalender(iosDriver);
        }

        @Test
        public void calendarCreateEventTest () throws InterruptedException {
            LocalDateTime localDateTimeStart = LocalDateTime.now().plusMinutes(60);
            LocalDateTime localDateTimeEnd = localDateTimeStart.plusMinutes(90);
            iosCalender.clickContinueAndGrantPermission();
            iosCalender.clickToday();
            iosCalender.clickAdd();
            iosCalender.setName(eventName);
            iosCalender.setStartTime(localDateTimeStart.getHour(), localDateTimeStart.getMinute());
            iosCalender.setEndTime(localDateTimeEnd.getHour(), localDateTimeEnd.getMinute());
            iosCalender.saveEvent();
            iosCalender.openEvent(eventName);

            Assert.assertEquals("incorrect event name", eventName, iosCalender.getEventName());
            Assert.assertEquals("incorrect time", iosCalender.getTimes(localDateTimeStart, localDateTimeEnd), iosCalender.getEventTimes());

        }

        @After
        public void cleanUP () throws InterruptedException, IOException {
          iosCalender.deleteEvent();
          stopSimulator(Platform.iOS);
          iosDriver.quit();
        }
}
