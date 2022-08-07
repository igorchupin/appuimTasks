package core;

import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;

import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FailedTestsToolsAndroid {
    static AndroidDriver androidDriver = SingletonDriverAndroid.getDriverInstance().getAndroidDriver();

    public static void attachDateAndTime() {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        Allure.addAttachment("Date time", timeStamp);
    }

    @Attachment(value = "ScreenShot", type = "image/png")
    public static byte[] attachScreenshot() throws IOException {
        return androidDriver.getScreenshotAs(OutputType.BYTES);
    }


    public static void attachInfo(AndroidDriver androidDriver) {
        Capabilities cap;
        cap = androidDriver.getCapabilities();
        String platformName = cap.getCapability("platformName").toString();
        String automationName = cap.getCapability("automationName").toString();
        String device = cap.getCapability("udid").toString();
        Allure.addAttachment("Platform ", platformName);
        Allure.addAttachment("Automation Name ", automationName);
        Allure.addAttachment("Device udid ", device);
    }

    public static void attachAll() {
        attachDateAndTime();
        try {
            attachScreenshot();
        } catch (IOException e) {
            e.printStackTrace();
        }
        attachInfo(androidDriver);
    }
}
