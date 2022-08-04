package pageObjects;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.touch.LongPressOptions;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static io.appium.java_client.touch.offset.ElementOption.element;


public class AndroidCalendar extends GeneralPage {

    private final By plusButton = By.id("floating_action_button");
    private final By titleField = By.id("title");
    private final By start = By.id("start_label");
    private final By end = By.id("end_label");
    private final By hour = By.xpath("//android.widget.NumberPicker[1]/android.widget.EditText");
    private final By hourFiledInput = By.xpath("//android.widget.LinearLayout[1]/android.widget.EditText");
    private final By minuteFiledInput = By.xpath("//android.widget.LinearLayout[2]/android.widget.EditText");
    private final By doneButton = By.id("button1");
    private final By saveButton = By.xpath("//android.widget.Button[@content-desc=\"Save\"]/android.view.ViewGroup/android.widget.TextView");
    private final String dayIconPattern = "new UiSelector().text(\"%s\")";
    private final By eventName = MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"com.samsung.android.calendar:id/title\")");
    private final By eventTime = By.id("timeTextView");
    private final By deleteButton = By.xpath("//android.widget.Button[@content-desc=\"Delete\"]/android.widget.ImageView");
    private final By noEventsText = By.id("no_events_or_tasks_text");
    private final By locationField = By.id("location");

    public AndroidCalendar(AppiumDriver driver) {
        super(driver);
    }

    public void clickPlusButton() {
        findWithWait(plusButton).click();
    }

    public void enterEventName(String eventName)  {
        findWithWait(titleField).sendKeys(eventName);
    }

    public void setStartTime(int hours, int minutes)  {
        findWithWait(start).click();
        findWithWait(hour).click();
        findWithWait(hourFiledInput).sendKeys(String.valueOf(hours));
        findWithWait(minuteFiledInput).click();
        findWithWait(minuteFiledInput).sendKeys(String.valueOf(minutes));
        findWithWait(doneButton).click();
    }

   public void setEndTime(int hours, int minutes) {
       findWithWait(end).click();
       new WebDriverWait (driver, 5).until(ExpectedConditions.elementToBeClickable(hour));
       driver.findElement(hour).click();
       findWithWait(hourFiledInput).sendKeys(String.valueOf(hours));
       findWithWait(minuteFiledInput).click();
       findWithWait(minuteFiledInput).sendKeys(String.valueOf(minutes));
       findWithWait(doneButton).click();
   }

    public void setLocation(String location) {
        findWithWait(locationField).click();
        findWithWait(locationField).sendKeys(location);
    }

    public void saveEvent() {
        findWithWait(saveButton).click();
    }

    public void openEvent(LocalDateTime localDateTime) {
        String date = String.format("%s-%s-%s", localDateTime.getYear(), localDateTime.getMonthValue(), localDateTime.getDayOfMonth());
        findWithWait(MobileBy.AndroidUIAutomator(String.format(dayIconPattern, date))).click();
    }

    public String getEventName() {
        return findWithWait(eventName).getText();
    }

    public String getEventTimes() {
        return findWithWait(eventTime).getText();
    }

    public String getLocationName() {
        return findWithWait(By.id("location")).getText();
    }

    public void deleteEvent() {

        touchAction.longPress(LongPressOptions.longPressOptions().withElement(element(findWithWait(eventName)))).release().perform();
        findWithWait(By.xpath("//android.widget.TextView[@text = 'Delete']")).click();
        findWithWait(By.id("android:id/button1")).click();


        /* driver.findElement(eventName).click();
        Thread.sleep(500);
        driver.findElement(deleteButton).click();
        Thread.sleep(500);
        driver.findElement(doneButton).click(); */
    }

    public String getTimes(LocalDateTime start, LocalDateTime end) {
        DateTimeFormatter.ofPattern("mm");
        return start.getHour() + ":" + start.format(DateTimeFormatter.ofPattern("mm")) + " - " + end.getHour() + ":" + end.format(DateTimeFormatter.ofPattern("mm"));
    }
}

