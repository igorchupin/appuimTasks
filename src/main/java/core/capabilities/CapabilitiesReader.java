package core.capabilities;

import com.google.gson.Gson;
import core.Platform;
import core.capabilities.AndroidCapabilities;
import core.capabilities.Capabilities;
import core.capabilities.IOSCapabilities;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class CapabilitiesReader {
    private static String androidCapabilitiesPath = "src/main/resources/androidCapabilities.json";
    private static String iOSCapabilitiesPath = "src/main/resources/iosCapabilities.json";
    private static Gson gson = new Gson();
    private static Reader reader;

    public static Capabilities capabilitiesRead(Platform platform) {
       try {
           if (platform.equals(Platform.Android)) {
               AndroidCapabilities androidCapabilities =  new AndroidCapabilities();
               reader = new FileReader(androidCapabilitiesPath);
               androidCapabilities = gson.fromJson(reader, AndroidCapabilities.class);
               return androidCapabilities;
           }
           else {
               IOSCapabilities iosCapabilities = new IOSCapabilities();
               reader = new FileReader(iOSCapabilitiesPath);
               iosCapabilities = gson.fromJson(reader, IOSCapabilities.class);
               return iosCapabilities;
           }

       } catch (FileNotFoundException e) {
           e.printStackTrace();
           return null;
       }
    }
}
