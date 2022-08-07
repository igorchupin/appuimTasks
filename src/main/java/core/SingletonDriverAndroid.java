package core;

import core.capabilities.CapabilitiesReader;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class SingletonDriverAndroid {

    private static SingletonDriverAndroid singleDriver = null;
    public AndroidDriver androidDriver;

    public SingletonDriverAndroid() {
                createAndroidDriver();
        }

    public static SingletonDriverAndroid getDriverInstance() {
        if (singleDriver == null) {
            synchronized (SingletonDriverAndroid.class) {
                if (singleDriver == null) {
                    singleDriver = new SingletonDriverAndroid();
                }
            }
        }
        else {
            System.out.println("Driver already exist");
        }
        return singleDriver;
    }

   public  AndroidDriver getAndroidDriver () {
        return androidDriver;
    }


    public void closeDriver() {
        if (singleDriver != null) {
            androidDriver.quit();
            singleDriver = null;
            System.out.println("Driver closed");
        }
    }

    public void createAndroidDriver () {
        Map<String, String> capabilities = CapabilitiesReader.capabilitiesRead(Platform.Android);
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        URL driverURL = null;
        try {
           driverURL = new URL(capabilities.get("driverUrl"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        for (String key : capabilities.keySet()) {
            if (!key.equals("driverUrl")) {
                desiredCapabilities.setCapability(key, capabilities.get(key));
            }
        }
        androidDriver = new AndroidDriver(driverURL, desiredCapabilities);
        System.out.println("Driver created" + androidDriver.getSessionId().toString());
    }
}
