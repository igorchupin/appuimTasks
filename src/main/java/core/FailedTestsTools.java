package core;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;

import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FailedTestsTools {

    public static void attachDateAndTime() {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        Allure.addAttachment("Date time", timeStamp);
    }

    @Attachment(value = "ScreenShot", type = "image/png")
    public static byte[] attachScreenshot(AndroidDriver androidDriver) throws IOException {
        return androidDriver.getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "ScreenShot", type = "image/png")
    public static byte[] attachScreenshot(IOSDriver iosDriver) throws IOException {
        return iosDriver.getScreenshotAs(OutputType.BYTES);
    }


    public static void attachInfo (AndroidDriver androidDriver) {
        Capabilities cap;
        cap = androidDriver.getCapabilities();
        String platformName = cap.getCapability("platformName").toString();
        String automationName = cap.getCapability("automationName").toString();
        String device = cap.getCapability("udid").toString();
        Allure.addAttachment( "Platform ", platformName);
        Allure.addAttachment("Automation Name ", automationName);
        Allure.addAttachment("Device udid ", device);
    }

    public static void attachInfo (IOSDriver iosDriver) {
        Capabilities cap;
        cap = iosDriver.getCapabilities();
        String platformName = cap.getCapability("platformName").toString();
        String automationName = cap.getCapability("automationName").toString();
        String device = cap.getCapability("udid").toString();
        Allure.addAttachment( "Platform ", platformName);
        Allure.addAttachment("Automation Name ", automationName);
        Allure.addAttachment("Device udid ", device);
    }

    public static void attachAll (AndroidDriver androidDriver) {
        attachDateAndTime();
        try {
            attachScreenshot(androidDriver);
        } catch (IOException e) {
            e.printStackTrace();
        }
        attachInfo(androidDriver);
    }

    public static void attachAll (IOSDriver iosDriver) {
        attachDateAndTime();
        try {
            attachScreenshot(iosDriver);
        } catch (IOException e) {
            e.printStackTrace();
        }
        attachInfo(iosDriver);
    }

}
