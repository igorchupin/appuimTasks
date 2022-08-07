
import core.SingletonDriverIos;
import io.appium.java_client.ios.IOSDriver;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import listeners.TestListenersIos;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import pageObjects.IosReminderPage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@ExtendWith(TestListenersIos.class)

public class IosReminderTest extends GeneralTest{
    static IOSDriver iosDriver;
    IosReminderPage iosReminderPage;
    String list1 = "List 1";
    String list2 = "List 2";
    String list3 = "List 3";
    String subject = "Subject " + System.currentTimeMillis();
    LocalDateTime localDateTime;
    String reminder1 = "Reminder 1";
    String reminder2 = "Reminder 2";
    String notes = "Reminder Notes";

    @BeforeEach
    public void driverSetup() {
        iosDriver = SingletonDriverIos.getDriverInstance().getIosDriver();
    }

    @TmsLink(value = "4T")
    @Description(value = "Create Lists")
    @Test
    @Tag("ios")
    @DisplayName("Ability create lists")
    public void creteListsTest () {
        iosReminderPage = new IosReminderPage(iosDriver);

        iosReminderPage.addList(list1, "green");
        iosReminderPage.addList(list2, "red");
        iosReminderPage.addList(list3, "red");

        Assertions.assertEquals(3, iosReminderPage.listCount(), "incorrect count");

        Assertions.assertEquals(list1, iosReminderPage.getOrdersName().get(0), "incorrect 1 item");
        Assertions.assertEquals(list2, iosReminderPage.getOrdersName().get(1), "incorrect 2 item");
        Assertions.assertEquals(list3, iosReminderPage.getOrdersName().get(2), "incorrect 3 item");

    }

    @TmsLink(value = "5T")
    @Description(value = "Create Lists and reverse")
    @Test
    @Tag("ios")
    @DisplayName("Ability create lists and reverse")
    public void creteListsTestAndReverse () {
        iosReminderPage = new IosReminderPage(iosDriver);

        iosReminderPage.addList(list1, "green");
        iosReminderPage.addList(list2, "red");
        iosReminderPage.addList(list3, "red");

        Assertions.assertEquals(3, iosReminderPage.listCount(), "incorrect count");

        Assertions.assertEquals(list1, iosReminderPage.getOrdersName().get(0), "incorrect 1 item");
        Assertions.assertEquals(list2, iosReminderPage.getOrdersName().get(1), "incorrect 2 item");
        Assertions.assertEquals(list3, iosReminderPage.getOrdersName().get(2), "incorrect 3 item");

        iosReminderPage.changeOrdersName();

        Assertions.assertEquals(list3, iosReminderPage.getOrdersName().get(0), "incorrect 1 item");
        Assertions.assertEquals(list2, iosReminderPage.getOrdersName().get(1), "incorrect 2 item");
        Assertions.assertEquals(list1, iosReminderPage.getOrdersName().get(2), "incorrect 3 item");


    }

    @TmsLink(value = "6T")
    @Description(value = "Add reminder")
    @Test
    @Tag("ios")
    @DisplayName("Ability add reminder")
    public void createReminder () {
        iosReminderPage = new IosReminderPage(iosDriver);
        localDateTime = LocalDateTime.now().plusMinutes(0); //TODO 60!!!!

        iosReminderPage.addList(list1, "green");

        iosReminderPage.openList();
        iosReminderPage.addReminder(reminder1);
        iosReminderPage.addReminder(reminder2);

        Assertions.assertEquals(reminder1, iosReminderPage.getRemindersName().get(0), "incorrect 1 item");
        Assertions.assertEquals(reminder2, iosReminderPage.getRemindersName().get(1), "incorrect 2 item");

        iosReminderPage.checkReminders();

        iosReminderPage.openList();
        Assertions.assertTrue(iosReminderPage.validateNoreminders().isEmpty(), "Reminders left");

        iosReminderPage.clickLists();

    }

    @TmsLink(value = "7T")
    @Description(value = "Add reminder with details")
    @Test
    @Tag("ios")
    @DisplayName("Ability add reminder with details")
    public void createReminderWithDetails () {
        iosReminderPage = new IosReminderPage(iosDriver);
        localDateTime = LocalDateTime.now(); //TODO 60!!!!

        System.out.println(localDateTime.plusDays(1).format(DateTimeFormatter.ofPattern("dd")) + " New Day");
        System.out.println(localDateTime.minusMinutes(120).format(DateTimeFormatter.ofPattern("HH")) + " New hours");

        iosReminderPage.addList(list1, "green");

        iosReminderPage.openList();
        iosReminderPage.addReminder(reminder1);

        iosReminderPage.openReminder();
        iosReminderPage.clickInfo();
        iosReminderPage.fillNotes(notes);
        iosReminderPage.setDay(localDateTime.plusDays(1).format(DateTimeFormatter.ofPattern("d")));
        iosReminderPage.setHour(localDateTime);
        iosReminderPage.setPriority();
        iosReminderPage.clickDone();

        System.out.println(iosReminderPage.getSingleReminderName());
        System.out.println(iosReminderPage.getSymbols());
        System.out.println(iosReminderPage.getNameDetail());
        System.out.println(iosReminderPage.getDateTime());

        Assertions.assertEquals(reminder1, iosReminderPage.getSingleReminderName(), "incorrect 1 name item");
        Assertions.assertTrue(iosReminderPage.getSymbols().contains("!!!"), "Symbols absent");
        Assertions.assertEquals(notes, iosReminderPage.getNameDetail(), "Incorrect notes");
        Assertions.assertTrue(iosReminderPage.getDateTime().contains(localDateTime.minusMinutes(120).format(DateTimeFormatter.ofPattern("HH"))));

        iosReminderPage.clickLists();
    }

    @AfterEach
    public  void cleanUP ()  {
        iosReminderPage.deleteLists();
        SingletonDriverIos.getDriverInstance().closeDriver();
    }
}
