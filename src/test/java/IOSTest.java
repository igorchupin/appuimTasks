import core.Platform;
import core.capabilities.AndroidCapabilities;
import core.capabilities.CapabilitiesReader;
import core.capabilities.IOSCapabilities;
import io.appium.java_client.ios.IOSDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;
import pageObjects.IOSCalender;

import java.net.MalformedURLException;
import java.net.URL;

public class IOSTest {

        IOSDriver iosDriver;
        IOSCalender iosCalender;
        String eventName = "New Event test";

        @Before
        public void driverSetup() throws MalformedURLException {
            IOSCapabilities iosCapabilities = (IOSCapabilities) CapabilitiesReader.capabilitiesRead(Platform.iOS);
            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
            URL driverURL = new URL(iosCapabilities.driverUrl);

            desiredCapabilities.setCapability("platformName", iosCapabilities.platformName);
            desiredCapabilities.setCapability( "automationName", iosCapabilities.automationName);
            desiredCapabilities.setCapability("udid", iosCapabilities.udid);
            desiredCapabilities.setCapability("bundleId", iosCapabilities.bundleId);
            desiredCapabilities.setCapability("deviceName", iosCapabilities.deviceName);

            iosDriver = new IOSDriver(driverURL, desiredCapabilities);
            iosCalender = new IOSCalender(iosDriver);
        }

        @Test
        public void calendarCreateEventTest () throws InterruptedException {
            iosCalender.clickToday();
            iosCalender.clickAdd();
            iosCalender.setName(eventName);
            iosCalender.setStartTime();
            iosCalender.setEndTime();
            iosCalender.saveEvent();
            iosCalender.openEvent(eventName);

            Assert.assertEquals("incorrect event name", eventName, iosCalender.getEventName());
            Assert.assertEquals("incorrect time", iosCalender.getTimes(), iosCalender.getEventTimes());

        }

        @After
        public void cleanUP () throws InterruptedException {
          iosCalender.deleteEvent();
          iosDriver.quit();
        }
}
