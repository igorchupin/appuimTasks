
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

    public class GeneralTest {
        private static String startServerCommand = "appium -g ./appium_log.log --debug-log-spacing --port 4725";
        private static String stopServerCommand = "pkill -9 -f appium --- kill server";
        public static final String ENABLE_MOBILE_WIFI = "adb shell svc wifi enable";
        public static final String DISABLE_MOBILE_WIFI = "adb shell svc wifi disable";

        @BeforeAll
        public static void setUp () {
            startAppiumServer();
        }

        @AfterAll
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
            Process process;
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

        public static void startAppiumServer () {
            System.out.println("Starting Appium Server");
            String mark = "0.0.0.0:4725";
            executeCommand(startServerCommand, mark);
            System.out.println("Appium Server was started");
        }

        public static void stopAppiumServer ()  {
            System.out.println("Stopping Appium Server");
            executeCommand(stopServerCommand);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Stopped Appium Server");
        }

        public static void turnWiFIOff () {
            System.out.println("Turning wi fi off");
            executeCommand(DISABLE_MOBILE_WIFI);
            System.out.println("Disabled");
        }

        public static void turnWiFIOn () {
            System.out.println("Turning wi fi on");
            executeCommand(ENABLE_MOBILE_WIFI);
            System.out.println("Enabled");
        }
    }