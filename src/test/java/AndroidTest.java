import core.Platform;
import core.capabilities.AndroidCapabilities;
import core.capabilities.CapabilitiesReader;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;
import pageObjects.AndroidCalendar;

import java.net.MalformedURLException;
import java.net.URL;

public class AndroidTest {
    AndroidDriver androidDriver;
    AndroidCalendar androidCalendar;
    String eventName = "New Event test";

    @Before
    public void driverSetup() throws MalformedURLException {
        AndroidCapabilities androidCapabilities = (AndroidCapabilities) CapabilitiesReader.capabilitiesRead(Platform.Android);
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        URL driverURL = new URL(androidCapabilities.driverUrl);

        desiredCapabilities.setCapability("platformName", androidCapabilities.platformName);
        desiredCapabilities.setCapability( "automationName", androidCapabilities.automationName);
        desiredCapabilities.setCapability("udid", androidCapabilities.udid);
        desiredCapabilities.setCapability("appPackage", androidCapabilities.appPackage);
        desiredCapabilities.setCapability("appActivity", androidCapabilities.appActivity);

        androidDriver = new AndroidDriver(driverURL, desiredCapabilities);
        androidCalendar = new AndroidCalendar(androidDriver);
    }

    @Test
    public void calendarCreateEventTest () throws InterruptedException {
      androidCalendar.clickPlusButton();
      androidCalendar.enterEventName(eventName);
      androidCalendar.setStartTime();
      androidCalendar.setEndTime();
      androidCalendar.saveEvent();

      androidCalendar.openEvent();
      Assert.assertEquals("incorrect event name", eventName, androidCalendar.getEventName());
      Assert.assertEquals("incorrect time", androidCalendar.getTimes(), androidCalendar.getEventTimes());
    }

    @After
    public void cleanUP () throws InterruptedException {
        androidCalendar.deleteEvent();
        androidDriver.quit();
    }
}
