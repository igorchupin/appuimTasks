import core.Platform;
import core.capabilities.AndroidCapabilities;
import core.capabilities.CapabilitiesReader;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

public class AndroidTest {
    AndroidDriver androidDriver;

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
    }

    @Test
    public void calendarCreateEventTest () {

    }

    @After
    public void cleanUP () {
        //TODO Clear application
        androidDriver.quit();
    }
}
