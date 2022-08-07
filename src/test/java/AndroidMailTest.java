
import core.SingletonDriverAndroid;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import listeners.TestListenersAndroid;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import pageObjects.AndroidGmailPage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@ExtendWith(TestListenersAndroid.class)

public class AndroidMailTest extends GeneralTest{
    static AndroidDriver androidDriver;
    AndroidGmailPage androidGmailPage;
    String email = "ghostey123456@gmail.com";
    String subject = "Subject " + System.currentTimeMillis();
    String body = "Letter Body";
    LocalDateTime localDateTime;

    @BeforeEach
    public void driverSetup() {
        androidDriver = SingletonDriverAndroid.getDriverInstance().androidDriver;
    }

    @TmsLink(value = "1T")
    @Description(value = "Send letter")
    @Test
    @Tag("android")
    @DisplayName("Send Letter and verify")
    public void sendLetterAndVerifyTest () {
        androidGmailPage = new AndroidGmailPage(androidDriver);
        localDateTime = LocalDateTime.now().plusMinutes(0); //TODO 60!!!!

        androidGmailPage.clickCompose();
        androidGmailPage.fillTo(email);
        androidGmailPage.fillSubject(subject);
        androidGmailPage.fillBody(body);
        androidGmailPage.clickSend();

        androidGmailPage.waitForLetterAndOpenIt(subject);

        Assertions.assertTrue(androidGmailPage.getSubject().contains(subject), "Incorrect subject");
        Assertions.assertTrue(androidGmailPage.getMail().contains(email), "incorrect mail");
        Assertions.assertTrue(androidGmailPage.getTime().contains(localDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))), "incorrect time");

        androidGmailPage.clckExpand();

        Assertions.assertEquals(androidGmailPage.getBody(), body);

    }

    @TmsLink(value = "2T")
    @Description(value = "Send letter and delete")
    @Test
    @Tag("android")
    @DisplayName("Send Letter and delete")
    public void sendLetterAndDeleteTest () {
        androidGmailPage = new AndroidGmailPage(androidDriver);
        localDateTime = LocalDateTime.now().plusMinutes(0); //TODO 60!!!!

        androidGmailPage.clickCompose();
        androidGmailPage.fillTo(email);
        androidGmailPage.fillSubject(subject);
        androidGmailPage.fillBody(body);
        androidGmailPage.clickSend();

        androidGmailPage.deleteLetter(subject);
        Assertions.assertTrue(androidGmailPage.letterDisappeared(subject));

        androidGmailPage.openTrash();
        Assertions.assertTrue(androidGmailPage.letterPresent(subject));

    }

    @TmsLink(value = "3T")
    @Description(value = "Send letter with no wi fi")
    @Test
    @Tag("android")
    @DisplayName("Send Letter with no wi fi")
    public void sendLetterDisabledWiFi () {
        androidGmailPage = new AndroidGmailPage(androidDriver);
        localDateTime = LocalDateTime.now().plusMinutes(0); //TODO 60!!!!

        turnWiFIOff();

        androidGmailPage.clickCompose();
        androidGmailPage.fillTo(email);
        androidGmailPage.fillSubject(subject);
        androidGmailPage.fillBody(body);
        androidGmailPage.clickSend();

        Assertions.assertTrue(androidGmailPage.waitQueuedAppears());

        turnWiFIOn();

        Assertions.assertTrue(androidGmailPage.waitQueuedDisappears());
    }

    @AfterEach
    public  void cleanUP ()  {
     SingletonDriverAndroid.getDriverInstance().closeDriver();
    }
}
