package core.capabilities;

import com.google.gson.Gson;
import core.Platform;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.Map;

public class CapabilitiesReader {
    private static String androidCapabilitiesPath = "src/main/resources/androidCapabilities.json";
    private static String androidSimulatorCapabilitiesPath = "src/main/resources/androidSimulatorCapabilities.json";
    private static String iOSCapabilitiesPath = "src/main/resources/iosCapabilities.json";
    private static String iOSSimulatorCapabilitiesPath = "src/main/resources/iosSimulatorCapabilities.json";
    private static Gson gson = new Gson();
    private static Reader reader;
    private static Map<String, String> capabilities;

    public static Map<String, String> capabilitiesRead(Platform platform, boolean isReal) {
       try {

           if (platform.equals(Platform.Android) && isReal) {
               reader = new FileReader(androidCapabilitiesPath);
               }
               else if (platform.equals(Platform.Android) && !isReal) {
                   reader = new FileReader(androidSimulatorCapabilitiesPath);
               }

           else if (platform.equals(Platform.iOS) && isReal) {
               reader = new FileReader(iOSCapabilitiesPath);
           }
          else if (platform.equals(Platform.iOS) && !isReal) {
               reader = new FileReader(iOSSimulatorCapabilitiesPath);
           }

           capabilities = gson.fromJson(reader, Map.class);
           return capabilities;

       } catch (FileNotFoundException e) {
           e.printStackTrace();
           return null;
       }
    }
}
