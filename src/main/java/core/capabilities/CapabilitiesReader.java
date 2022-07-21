package core.capabilities;

import com.google.gson.Gson;
import core.Platform;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.Map;

public class CapabilitiesReader {
    private static String androidCapabilitiesPath = "src/main/resources/androidCapabilities.json";
    private static String iOSCapabilitiesPath = "src/main/resources/iosCapabilities.json";
    private static Gson gson = new Gson();
    private static Reader reader;
    private static Map<String, String> capabilities;

    public static Map<String, String> capabilitiesRead(Platform platform) {
       try {
           if (platform.equals(Platform.Android)) {
               reader = new FileReader(androidCapabilitiesPath);
           }
           else {
               reader = new FileReader(iOSCapabilitiesPath);
           }
           capabilities = gson.fromJson(reader, Map.class);
           return capabilities;

       } catch (FileNotFoundException e) {
           e.printStackTrace();
           return null;
       }
    }
}
