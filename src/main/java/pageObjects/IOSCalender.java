package pageObjects;

import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.By;

import java.time.LocalDateTime;

public class IOSCalender {

    IOSDriver driver;

    private By continueButton = MobileBy.iOSClassChain("**/XCUIElementTypeOther[`name == \"SplashScreen\"`]/XCUIElementTypeOther/XCUIElementTypeOther[2]");
    private By allowWhileUsing = MobileBy.AccessibilityId("Allow While Using App");
    private By listButton = MobileBy.AccessibilityId("List");
    private By todayButton = MobileBy.iOSClassChain("**/XCUIElementTypeButton[`label == \"Today\"`]");
    private By addButton = MobileBy.AccessibilityId("Add");
    private By addEvent = MobileBy.iOSClassChain("**/XCUIElementTypeButton[`label == \"Add\"`][2]");
    private By name = MobileBy.iOSClassChain("**/XCUIElementTypeWindow[1]/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[1]");
    private By nameField = MobileBy.iOSClassChain("**/XCUIElementTypeTextField[`value == \"Title\"`]");
    private By dateFieldStart = MobileBy.iOSClassChain("**/XCUIElementTypeCell[`label CONTAINS 'Starts'`]/XCUIElementTypeOther[`label == \"Date and Time Picker\"`][1]/XCUIElementTypeButton[1]");
    private By timeFieldStart = MobileBy.iOSClassChain("**/XCUIElementTypeStaticText[`label == \"Starts\"`]");
    private By hoursValue = MobileBy.iOSClassChain("**/XCUIElementTypePickerWheel[`value CONTAINS \"o’clock\"`]");
    private By minutesValue = MobileBy.iOSClassChain("**/XCUIElementTypePickerWheel[`value CONTAINS \"minutes\"`]");
    private By dateFieldEnd = MobileBy.iOSClassChain("**/XCUIElementTypeCell[`label CONTAINS 'Starts'`]/XCUIElementTypeOther[`label == \"Date and Time Picker\"`][1]/XCUIElementTypeButton[3]");
    private By timeFieldEnd = MobileBy.iOSClassChain("**/XCUIElementTypeStaticText[`label == \"Ends\"`]");
    private String eventLocatorPattern = "**/XCUIElementTypeButton[`label CONTAINS \"%s\"`]";
    private By eventName = MobileBy.iOSClassChain("**/XCUIElementTypeTable[`name == \"EventDetailsContainer\"`]/XCUIElementTypeCell/XCUIElementTypeStaticText[1]");
    private By eventTime = MobileBy.iOSClassChain("**/XCUIElementTypeStaticText[`label CONTAINS \"from\"`]");
    private By deleteButton = MobileBy.iOSClassChain("**/XCUIElementTypeStaticText[`label == \"Delete Event\"`]");
    private By cofirmDelete = MobileBy.iOSClassChain("**/XCUIElementTypeButton[`label == \"Delete Event\"`][2]");


    public IOSCalender (IOSDriver driver) {
        this.driver = driver;
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

    public void setStartTime (int hours, int minutes) throws InterruptedException {
        driver.findElement(timeFieldStart).click();
        Thread.sleep(500);
        driver.findElement(hoursValue).sendKeys(String.valueOf(hours));
        Thread.sleep(500);
        //driver.findElement(minutesValue).sendKeys(String.valueOf(minutes)); //sometimes do not works sometimes works. I do not know what to do =(
    }

    public void setEndTime (int hours, int minutes) throws InterruptedException {
        driver.findElement(timeFieldEnd).click();
        Thread.sleep(3500);
        driver.findElement(hoursValue).sendKeys(String.valueOf(hours));
        Thread.sleep(3500);
        //driver.findElement(minutesValue).sendKeys(String.valueOf(minutes)); //sometimes do not works sometimes works. I do not know what to do =(
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

    public String getTimes (LocalDateTime start, LocalDateTime end) {
        return "from " + start.getHour() + ":" + "00"  + " to " + end.getHour() + ":" + "00";
        // return "from " + time[0] + ":" + time[1] + " to " + time[2] + ":" + time[3];
    }
}
