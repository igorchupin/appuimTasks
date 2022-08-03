import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

    public class GeneralTest {
        private static String startServerCommand = "appium -g ./appium_log.log --debug-log-spacing --port 4725";
        private static String stopServerCommand = "pkill -9 -f appium --- kill server";

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
            System.out.println("Stopped Appium Server");
        }
    }