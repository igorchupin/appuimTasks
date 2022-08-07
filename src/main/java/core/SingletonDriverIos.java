package core;

import core.capabilities.CapabilitiesReader;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class SingletonDriverIos {

    private static SingletonDriverIos singleDriver = null;
    public IOSDriver iosDriver;

    public SingletonDriverIos() {
        createIosDriver();
    }

    public static SingletonDriverIos getDriverInstance() {
        if (singleDriver == null) {
            synchronized (SingletonDriverIos.class) {
                if (singleDriver == null) {
                    singleDriver = new SingletonDriverIos();
                }
            }
        }
        else {
            System.out.println("Driver already exist");
        }
        return singleDriver;
    }

    public  IOSDriver getIosDriver () {
        return iosDriver;
    }


    public void closeDriver() {
        if (singleDriver != null) {
            iosDriver.quit();
            singleDriver = null;
            System.out.println("Driver closed");
        }
    }

    public void createIosDriver() {
        Map<String, String> capabilities = CapabilitiesReader.capabilitiesRead(Platform.iOS);
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
        iosDriver = new IOSDriver(driverURL, desiredCapabilities);
        System.out.println("Driver created" + iosDriver.getSessionId().toString());
    }
}

