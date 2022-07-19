package pageObjects;

import core.TimeUtils;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

public class IOSCalender {

    IOSDriver driver;
    TimeUtils timeUtils = new TimeUtils();
    String[] time;

    public  By continueButton = MobileBy.iOSClassChain("**/XCUIElementTypeOther[`name == \"SplashScreen\"`]/XCUIElementTypeOther/XCUIElementTypeOther[2]");
    public By allowWhileUsing = MobileBy.AccessibilityId("Allow While Using App");
    public By listButton = MobileBy.AccessibilityId("List");
    public By todayButton = MobileBy.iOSClassChain("**/XCUIElementTypeButton[`label == \"Today\"`]");
    public By addButton = MobileBy.AccessibilityId("Add");
    public By addEvent = MobileBy.iOSClassChain("**/XCUIElementTypeButton[`label == \"Add\"`][2]");
    public By name = MobileBy.iOSClassChain("**/XCUIElementTypeWindow[1]/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[1]");
    public By nameField = MobileBy.iOSClassChain("**/XCUIElementTypeTextField[`value == \"Title\"`]");
    public By dateFieldStart = MobileBy.iOSClassChain("**/XCUIElementTypeCell[`label CONTAINS 'Starts'`]/XCUIElementTypeOther[`label == \"Date and Time Picker\"`][1]/XCUIElementTypeButton[1]");
    public By timeFieldStart = MobileBy.iOSClassChain("**/XCUIElementTypeStaticText[`label == \"Starts\"`]");
    public By hoursValue = MobileBy.iOSClassChain("**/XCUIElementTypePickerWheel[`value CONTAINS \"o’clock\"`]");
    public By minutesValue = MobileBy.iOSClassChain("**/XCUIElementTypePickerWheel[`value CONTAINS \"minutes\"`]");
    public By dateFieldEnd = MobileBy.iOSClassChain("**/XCUIElementTypeCell[`label CONTAINS 'Starts'`]/XCUIElementTypeOther[`label == \"Date and Time Picker\"`][1]/XCUIElementTypeButton[3]");
    public By timeFieldEnd = MobileBy.iOSClassChain("**/XCUIElementTypeStaticText[`label == \"Ends\"`]");
    public String eventLocatorPattern = "**/XCUIElementTypeButton[`label CONTAINS \"%s\"`]";
    public By eventName = MobileBy.iOSClassChain("**/XCUIElementTypeTable[`name == \"EventDetailsContainer\"`]/XCUIElementTypeCell/XCUIElementTypeStaticText[1]");
    public By eventTime = MobileBy.iOSClassChain("**/XCUIElementTypeStaticText[`label CONTAINS \"from\"`]");
    public By deleteButton = MobileBy.iOSClassChain("**/XCUIElementTypeStaticText[`label == \"Delete Event\"`]");
    public By cofirmDelete = MobileBy.iOSClassChain("**/XCUIElementTypeButton[`label == \"Delete Event\"`][2]");


    public IOSCalender (IOSDriver driver) {
        this.driver = driver;
        this.time = timeUtils.getTimeForEventStartAndEnd();
    }
/*
    public void clickContinueAndGrantPermission() throws InterruptedException {
        if (driver.findElement(continueButton).isDisplayed()) {
        driver.findElement(continueButton).click();}
        Thread.sleep(500);
        if (driver.findElement(allowWhileUsing).isDisplayed()) {
        driver.findElement(allowWhileUsing).click();
        Thread.sleep(500);
        }
    }
*/
    public void clickToday () throws InterruptedException {
        driver.findElement(todayButton).click();
        Thread.sleep(500);
    }

    public void clickAdd () throws InterruptedException {
        driver.findElement(addButton).click();
        Thread.sleep(1000);
    }

    public void setName (String name) throws InterruptedException {
        driver.findElement(nameField).click();
        driver.findElement(nameField).sendKeys(name);
        Thread.sleep(500);
    }

    public void setStartTime () throws InterruptedException {
        driver.findElement(timeFieldStart).click();
        Thread.sleep(500);
        driver.findElement(hoursValue).sendKeys(time[0]);
        Thread.sleep(500);
        //driver.findElement(minutesValue).sendKeys(time[1]); //sometimes do not works sometimes works. I do not know what to do =(
    }

    public void setEndTime () throws InterruptedException {
        driver.findElement(timeFieldEnd).click();
        Thread.sleep(3500);
        driver.findElement(hoursValue).sendKeys(time[2]);
        Thread.sleep(3500);
        //driver.findElement(minutesValue).sendKeys(time[3]); //sometimes do not works sometimes works. I do not know what to do =(
        Thread.sleep(3500);
    }

    public void saveEvent() {
        driver.findElement(addEvent).click();
    }

    public void openEvent(String name) {
        driver.findElement(MobileBy.iOSClassChain(String.format(eventLocatorPattern, name))).click();
    }

    public String getEventName () {
        return driver.findElement(eventName).getAttribute("name");
    }

    public String getEventTimes () {
        return driver.findElement(eventTime).getAttribute("name");
    }

    public void deleteEvent() throws InterruptedException {
        driver.findElement(deleteButton).click();
        Thread.sleep(500);
        driver.findElement(cofirmDelete).click();
        Thread.sleep(500);
    }

    public String getTimes () {
        return "from " + time[0] + ":" + "00"  + " to " + time[2] + ":" + "00";
        // return "from " + time[0] + ":" + time[1] + " to " + time[2] + ":" + time[3];
    }
}
