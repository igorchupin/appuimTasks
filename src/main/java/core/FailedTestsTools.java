package core;

import core.capabilities.CapabilitiesReader;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class FailedTestsTools {
    private static IOSDriver iosDriver;
    private static AndroidDriver androidDriver;

    @Attachment (value = "ScreenShot", type = "image/png")
    public static byte[] attachScreenshot() throws IOException {
        iosDriver = SingletonDriver.getIosDriver();
        androidDriver = SingletonDriver.getAndroidDriver();

        System.out.println("!!!!!!!!! I am here");

        TakesScreenshot screenshot;
        if (iosDriver == null) {
           return androidDriver.getScreenshotAs(OutputType.BYTES);
        }
        else {
           return iosDriver.getScreenshotAs(OutputType.BYTES);
        }
    }

    @Attachment
    public static String attachPlatformVersion() throws IOException {
        Capabilities cap;

        /*if (iosDriver == null) {
           cap  =  androidDriver.getCapabilities();
        }

        else {
            cap = iosDriver.getCapabilities();
        } */

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


        cap = androidDriver.getCapabilities();

        String platformName = cap.getPlatform().name();
        String device = cap.getCapability("automationName").toString();
        Allure.addAttachment("Date time" , "Platform " + platformName + "automationName " + device);
        return "Platform " + platformName + "automationName " + device;
    }

    @Attachment
    public static String attachDateAndTime() {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        Allure.addAttachment("Date time" , "Date/time: " + timeStamp);
        return "Date/time: " + timeStamp;
    }
}
