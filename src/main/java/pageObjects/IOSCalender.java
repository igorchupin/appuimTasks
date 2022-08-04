package pageObjects;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;

import java.time.LocalDateTime;

import static io.appium.java_client.touch.offset.ElementOption.element;

public class IOSCalender extends GeneralPage{

    private final By continueButton = MobileBy.iOSClassChain("**/XCUIElementTypeOther[`name == \"SplashScreen\"`]/XCUIElementTypeOther/XCUIElementTypeOther[2]");
    private final By allowWhileUsing = MobileBy.AccessibilityId("Allow While Using App");
    private final By listButton = MobileBy.AccessibilityId("List");
    private final By todayButton = MobileBy.iOSClassChain("**/XCUIElementTypeButton[`label == \"Today\"`]");
    private final By addButton = MobileBy.iOSNsPredicateString("label == \"Add\"");
    private final By addEvent = MobileBy.iOSClassChain("**/XCUIElementTypeButton[`label == \"Add\"`][2]");
    private final By name = MobileBy.iOSClassChain("**/XCUIElementTypeWindow[1]/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[1]");
    private final By nameField = MobileBy.iOSNsPredicateString("value == \"Title\"");
    private final By dateFieldStart = MobileBy.iOSClassChain("**/XCUIElementTypeCell[`label CONTAINS 'Starts'`]/XCUIElementTypeOther[`label == \"Date and Time Picker\"`][1]/XCUIElementTypeButton[1]");
    private final By timeFieldStart = MobileBy.iOSClassChain("**/XCUIElementTypeStaticText[`label == \"Starts\"`]");
    private final By hoursValue = MobileBy.iOSClassChain("**/XCUIElementTypePickerWheel[`value CONTAINS \"o’clock\"`]");
    private final By minutesValue = MobileBy.iOSClassChain("**/XCUIElementTypePickerWheel[`value CONTAINS \"minutes\"`]");
    private final By dateFieldEnd = MobileBy.iOSClassChain("**/XCUIElementTypeCell[`label CONTAINS 'Starts'`]/XCUIElementTypeOther[`label == \"Date and Time Picker\"`][1]/XCUIElementTypeButton[3]");
    private final By timeFieldEnd = MobileBy.iOSClassChain("**/XCUIElementTypeStaticText[`label == \"Ends\"`]");
    private final String eventLocatorPattern = "**/XCUIElementTypeButton[`label CONTAINS \"%s\"`]";
    private final By eventName = MobileBy.iOSClassChain("**/XCUIElementTypeTable[`name == \"EventDetailsContainer\"`]/XCUIElementTypeCell/XCUIElementTypeStaticText[1]");
    private final By eventTime = MobileBy.iOSClassChain("**/XCUIElementTypeStaticText[`label CONTAINS \"from\"`]");
    private final By deleteButton = MobileBy.iOSClassChain("**/XCUIElementTypeStaticText[`label == \"Delete Event\"`]");
    private final By cofirmDelete = MobileBy.iOSClassChain("**/XCUIElementTypeButton[`label == \"Delete Event\"`][2]");
    private final String eventCellPattern = "**/XCUIElementTypeCell[`label BEGINSWITH \"%s\"`]";
    private final By deleteButtonFromCell = MobileBy.iOSClassChain("**/XCUIElementTypeButton[`label == \"Delete Event\"`]");
    private final By locationField = MobileBy.iOSClassChain("**/XCUIElementTypeStaticText[`label == \"Location\"`]");
    private final By locationInput = MobileBy.iOSClassChain("**/XCUIElementTypeSearchField[`label == \"Enter Location\"`]");
    private final By eventLocation = MobileBy.iOSClassChain("**/XCUIElementTypeTable[`name == \"EventDetailsContainer\"`]/XCUIElementTypeCell/XCUIElementTypeTextView");


    public IOSCalender(AppiumDriver driver) {
        super(driver);
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
    public void clickToday()  {
        findWithWait(todayButton).click();
    }

    public void clickAdd() {
        findWithWait(addButton).click();
    }

    public void setName(String name) {
        findWithWait(nameField).click();
        findWithWait(nameField).sendKeys(name);
    }

    public void setStartTime(int hours, int minutes) {
        findWithWait(timeFieldStart).click();
        findWithWait(hoursValue).sendKeys(String.valueOf(hours));
        driver.findElement(minutesValue).sendKeys(String.valueOf(minutes)); //sometimes do not works sometimes works. I do not know what to do =(
    }

    public void setEndTime(int hours, int minutes) {
        findWithWait(timeFieldEnd).click();
        findWithWait(hoursValue).sendKeys(String.valueOf(hours));
        driver.findElement(minutesValue).sendKeys(String.valueOf(minutes)); //sometimes do not works sometimes works. I do not know what to do =(
    }

    public void saveEvent() {
        findWithWait(addEvent).click();
    }

    public void openEvent(String name) {
        findWithWait(MobileBy.iOSClassChain(String.format(eventLocatorPattern, name))).click();
    }

    public String getEventName() {
        touchAction.press(PointOption.point(100, 100)).moveTo(PointOption.point(0, 0)).release().perform();
        return findWithWait(eventName).getAttribute("name");
    }

    public String getEventTimes() {
        return findWithWait(eventTime).getAttribute("name");
    }

    public void setLocation(String location) {
        findWithWait(locationField).click();
        findWithWait(locationInput).sendKeys(location);
        findWithWait(MobileBy.AccessibilityId(location)).click();
    }

    public String getLocationName() {
        return findWithWait(eventLocation).getText();
    }

    public void deleteEvent(String name) {
        findWithWait(MobileBy.iOSClassChain("**/XCUIElementTypeButton[1]")).click();
        findWithWait(listButton).click();
        touchAction.longPress(LongPressOptions.longPressOptions().withElement(element(findWithWait(MobileBy.iOSClassChain(String.format(eventCellPattern, name)))))).release().perform();
        findWithWait(deleteButtonFromCell).click();
        findWithWait(deleteButtonFromCell).click();
        findWithWait(listButton).click();

        /*
        driver.findElement(deleteButton).click();
        Thread.sleep(500);
        driver.findElement(cofirmDelete).click();
        Thread.sleep(500); */
    }

    public String getTimes(LocalDateTime start, LocalDateTime end) {
        return "from " + start.getHour() + ":" + "00" + " to " + end.getHour() + ":" + "00";
        // return "from " + time[0] + ":" + time[1] + " to " + time[2] + ":" + time[3];
    }
}
