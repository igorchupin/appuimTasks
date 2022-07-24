import core.Platform;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GeneralTest {
    private static String startServerCommand = "appium -g ./appium_log.log --debug-log-spacing --port 4725";
    private static String stopServerCommand = "pkill -9 -f appium --- kill server";
    private static String startAndroidSimulator = "emulator -avd %s"; //Pixel_XL_API_30
    private static String stopAndroidSimulator = "adb emu kill";
    private static String startIosSimulator = "open -a Simulator --args -CurrentDeviceUDID %s"; //9AAF9ECE-2A9F-4A3C-8768-1F5678A86277
    private static String stopIosSimulator = "xcrun simctl shutdown booted";
    private static Process process;

    @BeforeClass
    public static void setUp () {
        startAppiumServer();
    }

    @AfterClass
    public static void clean () {
        stopAppiumServer();
    }

    private static void executeCommand (String command, String successMark) {
        Process process;
        try {
            process = Runtime.getRuntime().exec(command);
            printResult(process, successMark);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void executeCommand (String command) {
        try {
            process = Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printResult(Process process, String successMark) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        do {
            try {
                line = reader.readLine();
                System.out.println(line);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
            while (!(line.contains(successMark))) ;
    }

    private static void printResult(Process process) throws InterruptedException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        do {
            try {
                line = reader.readLine();
                System.out.println(line);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        while (!(process.waitFor()==1));
    }

    public static void startSimulator (Platform platform, String deviceIdentifier) throws IOException {
        switch (platform) {
            case Android:
                String successAndroid = "Advertising in:";
                System.out.println("Starting Android Simulator");
                executeCommand(String.format(startAndroidSimulator, deviceIdentifier), successAndroid);
                try {
                    System.out.println("Start sleep");
                    Thread.sleep(5000);
                    System.out.println("Wake up");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Android was started");
                break;
            case iOS:
                System.out.println("Starting IOS Simulator");
                process = Runtime.getRuntime().exec(String.format(startIosSimulator, deviceIdentifier));
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("IOS was started");
                break;
        }
    }

    public static void stopSimulator (Platform platform) {
        switch (platform) {
            case Android:
                String successAndroid = "OK";
                System.out.println("Stopping Android Simulator");
                executeCommand(stopAndroidSimulator, successAndroid);
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Android was stopped");
                break;
            case iOS:
                System.out.println("Stopping IOS Simulator");
                executeCommand(stopIosSimulator);
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("IOS was stopped");
                break;
        }
    }


    public static void startAppiumServer () {
        System.out.println("Starting Appium Server");
        String mark = "0.0.0.0:4725";
        executeCommand(startServerCommand, mark);
        System.out.println("Appium Server was started");
    }

    public static void stopAppiumServer ()  {
        System.out.println("Stopping Appium Server");
        executeCommand(stopServerCommand);
        System.out.println("Stopped Appium Server");
    }
}
