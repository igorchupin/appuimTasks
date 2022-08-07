package core;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.net.URL;

public class SingletonDriver {

  /*  private static SingletonDriver singleDriver = null;
    public static IOSDriver iosDriver;
    public static AndroidDriver androidDriver;
    public static AppiumDriver appiumDriver;

    private SingletonDriver (Platform platform, DesiredCapabilities desiredCaps, URL driverURL) {
        switch (platform) {
            case Android:
                androidDriver = new AndroidDriver(driverURL, desiredCaps);
                break;
            case iOS:
                iosDriver = new IOSDriver(driverURL, desiredCaps);
                break;
        }
    }

    public static SingletonDriver getDriverInstance(Platform platform, DesiredCapabilities desiredCaps, URL driverURL) throws IOException {
        if (singleDriver == null) {
            synchronized (SingletonDriver.class) {
                if (singleDriver == null) {
                    singleDriver = new SingletonDriver(platform, desiredCaps, driverURL);
                }
            }
        }
        return singleDriver;
    }

    public static AndroidDriver getAndroidDriver () {
        return androidDriver;
    }

    public static IOSDriver getIosDriver () {
        return iosDriver;
    }

    public static AppiumDriver getAppiumDriver () {
        return appiumDriver;
    }

    public void closeDriver(Platform platform) {
        if (singleDriver != null) {
            switch (platform){
                case Android:
                    androidDriver.quit();
                case iOS:
                    iosDriver.close();
            }
            singleDriver = null;
            appiumDriver = null;
        }
    } */
}
