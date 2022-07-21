package pageObjects;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class AndroidCalendar {
    AndroidDriver driver;

    private By plusButton = By.id("floating_action_button");
    private By titleField = By.id("title");
    private By start = By.id("start_label");
    private By end = By.id("end_label");
    private By hour = By.xpath("//android.widget.NumberPicker[1]/android.widget.EditText");
    private By hourFiledInput = By.xpath("//android.widget.LinearLayout[1]/android.widget.EditText");
    private By minuteFiledInput = By.xpath("//android.widget.LinearLayout[2]/android.widget.EditText");
    private By doneButton = By.id("button1");
    private By saveButton = By.xpath("//android.widget.Button[@content-desc=\"Save\"]/android.view.ViewGroup/android.widget.TextView");
    private String dayIconXpathPattern = "//android.view.View[@text=\"%s\"]"; //android.view.View[@content-desc=" "])[26]
    private By eventName = By.id("title");
    private By eventTime = By.id("timeTextView");
    private By deleteButton = By.xpath("//android.widget.Button[@content-desc=\"Delete\"]/android.widget.ImageView");
    private By noEventsText = By.id("no_events_or_tasks_text");

    public AndroidCalendar (AndroidDriver driver) {
        this.driver = driver;
    }

    public void clickPlusButton() throws InterruptedException {
       driver.findElement(plusButton).click();
        Thread.sleep(500);
    }

    public void enterEventName(String eventName) throws InterruptedException {
        driver.findElement(titleField).sendKeys(eventName);
        Thread.sleep(1000);
    }

   public void setStartTime(int hours, int minutes) throws InterruptedException {
       driver.findElement(start).click();
       Thread.sleep(2000);
       driver.findElement(hour).click();
       Thread.sleep(500);
       driver.findElement(hourFiledInput).sendKeys(String.valueOf(hours));
       driver.findElement(minuteFiledInput).click();
       driver.findElement(minuteFiledInput).sendKeys(String.valueOf(minutes));
       Thread.sleep(500);
       driver.findElement(doneButton).click();
       Thread.sleep(1000);
   }

   public void setEndTime(int hours, int minutes) throws InterruptedException {
       driver.findElement(end).click();
       Thread.sleep(2000);
       driver.findElement(hour).click();
       Thread.sleep(1000);
       driver.findElement(hourFiledInput).sendKeys(String.valueOf(hours));
       driver.findElement(minuteFiledInput).click();
       driver.findElement(minuteFiledInput).sendKeys(String.valueOf(minutes));
       Thread.sleep(500);
       driver.findElement(doneButton).click();
       Thread.sleep(500);
   }

   public void saveEvent() throws InterruptedException {
       driver.findElement(saveButton).click();
       Thread.sleep(500);
   }

   public void openEvent(LocalDateTime localDateTime) {
       String date = String.format("%s-%s-%s", localDateTime.getYear(), localDateTime.getMonthValue(), localDateTime.getDayOfMonth());
       driver.findElement(By.xpath(String.format(dayIconXpathPattern, date))).click();
   }

   public String getEventName () {
        return driver.findElement(eventName).getText();
    }

    public String getEventTimes () {
       return driver.findElement(eventTime).getText();
    }

    public void deleteEvent() throws InterruptedException {
        driver.findElement(eventName).click();
        Thread.sleep(500);
        driver.findElement(deleteButton).click();
        Thread.sleep(500);
        driver.findElement(doneButton).click();
    }

    public String getTimes (LocalDateTime start, LocalDateTime end) {
        DateTimeFormatter.ofPattern("mm");
        return start.getHour() + ":" + start.format(DateTimeFormatter.ofPattern("mm")) + " - " + end.getHour() + ":" + end.format(DateTimeFormatter.ofPattern("mm"));
    }
}

