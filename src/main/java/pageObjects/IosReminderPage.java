package pageObjects;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.functions.ExpectedCondition;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static io.appium.java_client.MobileBy.*;
import static io.appium.java_client.touch.offset.ElementOption.element;

public class IosReminderPage {

        private IOSDriver iosDriver;
        private TouchAction touchAction;

        private By addListButton = MobileBy.AccessibilityId("Add List");
        private By listNameFiled = MobileBy.AccessibilityId("List title");
        private By redCircle = By.xpath("//XCUIElementTypeOther[@name=\"Color selection\"]/XCUIElementTypeButton[@value = \"Red\"]");
        private By greenCircle = MobileBy.iOSClassChain("**/XCUIElementTypeButton[`value == \"Green\"`]");
        private By doneButton = MobileBy.iOSClassChain("**/XCUIElementTypeButton[`label == \"Done\"`]");
        private By editButton = MobileBy.iOSClassChain("**/XCUIElementTypeButton[`label == \"Edit\"`]");

        private By listInTable = MobileBy.iOSClassChain("**/XCUIElementTypeCell[`label CONTAINS \"reminder\"`]");
        private By listName = MobileBy.iOSClassChain("**/XCUIElementTypeCell[`label CONTAINS 'reminder'`]/XCUIElementTypeStaticText[1]");
        private By remindersCount = MobileBy.iOSClassChain("**/XCUIElementTypeCell[`label CONTAINS 'reminder'`]/XCUIElementTypeStaticText[2]");

        private By newReminderButton = MobileBy.iOSClassChain("**/XCUIElementTypeStaticText[`label == \"New Reminder\"`]");
        private By reminderName = MobileBy.iOSNsPredicateString("type == \"XCUIElementTypeTextView\"");
        private By listsButton = MobileBy.iOSClassChain("**/XCUIElementTypeButton[`label == \"Lists\"`]");
        private By checkBox = MobileBy.iOSClassChain("**/XCUIElementTypeButton[`label == \"CompleteOff\"`]");

        private By editDetailsButton = MobileBy.iOSClassChain("**/XCUIElementTypeButton[`label == \"Edit Details\"`]");
        private By notesFiled = MobileBy.iOSClassChain("**/XCUIElementTypeTextView[`value == \"Notes\"`]");

        private By remindMeOnDaySwitch = MobileBy.iOSClassChain("**/XCUIElementTypeSwitch[`label == \"Remind me on a day\"`]");
        private By alarmElement = MobileBy.iOSClassChain("**/XCUIElementTypeCell[`label == \"Alarm\"`]");
        private By dayAndTimePeeker = MobileBy.iOSClassChain("**/XCUIElementTypePickerWheel[2]");

        private By remindMeAtTimeSwithc = MobileBy.iOSClassChain("**/XCUIElementTypeSwitch[`label == \"Remind me at a time\"`]");
        private By priorityButton = MobileBy.iOSClassChain("**/XCUIElementTypeStaticText[`label == \"Priority\"`]");
        private By highValue = MobileBy.iOSClassChain("**/XCUIElementTypeCell[`label == \"High\"`]");
        private By detailsButton = MobileBy.iOSClassChain("**/XCUIElementTypeButton[`label == \"Details\"`]");

        private By inNmeSymbols = MobileBy.iOSClassChain("**/XCUIElementTypeCell/XCUIElementTypeStaticText[1]");
        private By notes = MobileBy.iOSClassChain("**/XCUIElementTypeCell/XCUIElementTypeStaticText[3]");
        private By dateTimeReminder= MobileBy.iOSClassChain("**/XCUIElementTypeCell/XCUIElementTypeStaticText[2]");

        private By minusutton = MobileBy.iOSClassChain("**/XCUIElementTypeButton[`label BEGINSWITH \"Delete\"`]");
        private By deleteButton = MobileBy.iOSClassChain("**/XCUIElementTypeButton[`label == \"Delete\"`]");
        private By confirmDelete = MobileBy.AccessibilityId("Delete");


        public IosReminderPage (IOSDriver driver) {
            iosDriver = driver;
            touchAction = new TouchAction(driver);
        }


        public void addList (String name, String color) {
            findWithWait(addListButton).click();
            findWithWait(listNameFiled).sendKeys(name);

            if (color.equalsIgnoreCase("Red")) {
                iosDriver.findElement(redCircle).click();
            }
            else {
                iosDriver.findElement(greenCircle).click();
            }
            iosDriver.findElement(doneButton).click();
        }

        public ArrayList<MobileElement> getLists () {
            WebDriverWait webDriverWait = new WebDriverWait(iosDriver, 10);
            webDriverWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(listInTable));
            return (ArrayList)iosDriver.findElements(listInTable);
        }


        public int listCount () {
            return getLists().size();
        }


        public ArrayList <String> getOrdersName () {
            WebDriverWait webDriverWait = new WebDriverWait(iosDriver, 10);
            webDriverWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(listInTable));
            ArrayList<String> result = new ArrayList<>();

            ArrayList<MobileElement> elements = (ArrayList) iosDriver.findElements(listInTable);

            for (MobileElement element: elements) {
                result.add(element.findElement(MobileBy.iOSClassChain("**/XCUIElementTypeStaticText[1]")).getText());
            }
            return result;
        }

    public void changeOrdersName () {
        //findWithWait(editButton).click();
        WebDriverWait webDriverWait = new WebDriverWait(iosDriver, 10);
        webDriverWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(listInTable));
        ArrayList<MobileElement> elements = (ArrayList) iosDriver.findElements(listInTable);


        //touchAction.longPress(LongPressOptions.longPressOptions().withElement(element(elements.get(1))).withDuration(Duration.ofMillis(500))..perform();
        touchAction.longPress(new ElementOption().withElement(elements.get(2).findElement(MobileBy.iOSClassChain("**/XCUIElementTypeStaticText[1]")))).
                waitAction(WaitOptions.waitOptions(Duration.ofMillis(3000))).
                moveTo(new ElementOption().withElement(elements.get(0).findElement(MobileBy.iOSClassChain("**/XCUIElementTypeStaticText[1]")))).waitAction(WaitOptions.waitOptions(Duration.ofMillis(3000))).release().perform();

        touchAction.longPress(new ElementOption().withElement(elements.get(0).findElement(MobileBy.iOSClassChain("**/XCUIElementTypeStaticText[1]")))).
                waitAction(WaitOptions.waitOptions(Duration.ofMillis(3000))).
                moveTo(new ElementOption().withElement(elements.get(2).findElement(MobileBy.iOSClassChain("**/XCUIElementTypeStaticText[1]")))).waitAction(WaitOptions.waitOptions(Duration.ofMillis(3000))).release().perform();

        touchAction.longPress(new ElementOption().withElement(elements.get(2).findElement(MobileBy.iOSClassChain("**/XCUIElementTypeStaticText[1]")))).
                waitAction(WaitOptions.waitOptions(Duration.ofMillis(3000))).
                moveTo(new ElementOption().withElement(elements.get(1).findElement(MobileBy.iOSClassChain("**/XCUIElementTypeStaticText[1]")))).waitAction(WaitOptions.waitOptions(Duration.ofMillis(3000))).release().perform();
    }

    public void openList () {
            findWithWait(listInTable).click();
    }

    public void addReminder (String name) {
            findWithWait(newReminderButton).click();
            ArrayList<MobileElement> list = (ArrayList)iosDriver.findElements(reminderName);
            list.get(list.size()-1).sendKeys(name);
            findWithWait(doneButton).click();
    }

    public void checkReminders () {
        ArrayList<MobileElement> list = (ArrayList)iosDriver.findElements(checkBox);
        for (MobileElement element : list) {
            element.click();
        }
        findWithWait(listsButton).click();
    }




    public ArrayList <String> getRemindersName() {
        WebDriverWait webDriverWait = new WebDriverWait(iosDriver, 10);
        webDriverWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(reminderName));
        ArrayList<String> result = new ArrayList<>();

        ArrayList<MobileElement> elements = (ArrayList) iosDriver.findElements(reminderName);

        for (MobileElement element: elements) {
            result.add(element.getAttribute("value"));
        }
        return result;
    }

    public void openReminder () {
            findWithWait(reminderName).click();
    }

    public void clickInfo () {
            findWithWait(editDetailsButton).click();
    }

    public void fillNotes(String notes) {
            findWithWait(notesFiled).click();
            findWithWait(notesFiled).sendKeys(notes);
    }

    public void setDay (String day) {
            findWithWait(remindMeOnDaySwitch).click();
            findWithWait(alarmElement).click();
            findWithWait(dayAndTimePeeker).sendKeys(day);
    }

    public void setHour (LocalDateTime dateTime) {
        findWithWait(remindMeAtTimeSwithc).click();
        findWithWait(dayAndTimePeeker).sendKeys(dateTime.minusMinutes(60).format(DateTimeFormatter.ofPattern("HH")));
        findWithWait(dayAndTimePeeker).sendKeys(dateTime.minusMinutes(120).format(DateTimeFormatter.ofPattern("HH")));
        findWithWait(dayAndTimePeeker).sendKeys(dateTime.minusMinutes(180).format(DateTimeFormatter.ofPattern("HH")));

        findWithWait(MobileBy.iOSClassChain(String.format("**/XCUIElementTypeCell[`label CONTAINS '%s'`]", "2022"))).click();
    }

    public void setPriority() {
            findWithWait(priorityButton).click();
            findWithWait(highValue).click();
            findWithWait(detailsButton).click();
    }

    public void clickDone () {
            findWithWait(doneButton).click();
    }

    public void clickLists () {
            findWithWait(listsButton).click();
    }

    public String getSymbols () {
           return findWithWait(inNmeSymbols).getText();
    }

    public String getNameDetail () {
            return findWithWait(notes).getText();
    }

    public String getDateTime () {
            return findWithWait(dateTimeReminder).getText();
    }

    public String getSingleReminderName () {
           return findWithWait(MobileBy.iOSClassChain("**/XCUIElementTypeTextView[1]")).getAttribute("value");
    }


    public ArrayList <String> validateNoreminders() {
        WebDriverWait webDriverWait = new WebDriverWait(iosDriver, 10);
        webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(reminderName));
        ArrayList<String> result = new ArrayList<>();

        ArrayList<MobileElement> elements = (ArrayList) iosDriver.findElements(reminderName);

        for (MobileElement element: elements) {
            result.add(element.getAttribute("value"));
        }
        return result;
    }



    private ExpectedCondition<MobileElement> elementPresents (By by) {
        return driver -> {
            List<MobileElement> list;
            list = driver.findElements(by);
            if (list.size() > 0 && list.get(0).isDisplayed() && list.get(0).isEnabled()) {
                return list.get(0);
            } else return null;
        };
    }

    public MobileElement findWithWait (By by) {
        WebDriverWait webDriverWait = new WebDriverWait(iosDriver, 10);
        return webDriverWait.until(elementPresents(by));
    }


    public void longPress (By by) {
        touchAction.longPress(LongPressOptions.longPressOptions().withElement(element(findWithWait(by)))).release().perform();
    }

    public void deleteLists () {
        findWithWait(editButton).click();
        WebDriverWait webDriverWait = new WebDriverWait(iosDriver, 2);
        findWithWait(minusutton);
        int z = iosDriver.findElements(minusutton).size();
        for (int i = 0; i < z; i++) {
                findWithWait(minusutton).click();
            System.out.println("Minus Button Clicked");
                webDriverWait.until(ExpectedConditions.elementToBeClickable(deleteButton));
                iosDriver.findElement(deleteButton).click();
            System.out.println("Delete Button Clicked");

               if (!iosDriver.findElements(confirmDelete).isEmpty()) {
                iosDriver.findElement(confirmDelete).click(); }
            }
            findWithWait(doneButton).click();
    }

    }

