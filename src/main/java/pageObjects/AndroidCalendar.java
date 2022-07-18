package pageObjects;

import core.TimeUtils;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;


public class AndroidCalendar {
    AndroidDriver driver;
    TimeUtils timeUtils = new TimeUtils();
    String[] time;


    public By plusButton = By.id("floating_action_button");
    public By titleField = By.id("title");
    public By start = By.id("start_label");
    public By end = By.id("end_label");
    public By hour = By.xpath("//android.widget.NumberPicker[1]/android.widget.EditText");
    public By hourFiledInput = By.xpath("//android.widget.LinearLayout[1]/android.widget.EditText");
    public By minuteFiledInput = By.xpath("//android.widget.LinearLayout[2]/android.widget.EditText");
    public By doneButton = By.id("button1");
    public By saveButton = By.xpath("//android.widget.Button[@content-desc=\"Save\"]/android.view.ViewGroup/android.widget.TextView");
    public String dayIconXpathPattern = "//android.view.View[@text=\"%s\"]";
    public By eventName = By.id("title");
    public By eventTime = By.id("timeTextView");
    public By deleteButton = By.xpath("//android.widget.Button[@content-desc=\"Delete\"]/android.widget.ImageView");
    public By noEventsText = By.id("no_events_or_tasks_text");

    public AndroidCalendar (AndroidDriver driver) {
        this.driver = driver;
        this.time = timeUtils.getTimeForEventStartAndEnd();

    }

    public void clickPlusButton() throws InterruptedException {
       driver.findElement(plusButton).click();
        Thread.sleep(500);
    }

    public void enterEventName(String enentName) throws InterruptedException {
        driver.findElement(titleField).sendKeys(enentName);
        Thread.sleep(1000);
    }

   public void setStartTime() throws InterruptedException {
       driver.findElement(start).click();
       Thread.sleep(2000);
       driver.findElement(hour).click();
       Thread.sleep(500);
       driver.findElement(hourFiledInput).sendKeys(time[0]);
       driver.findElement(minuteFiledInput).click();
       driver.findElement(minuteFiledInput).sendKeys(time[1]);
       Thread.sleep(500);
       driver.findElement(doneButton).click();
       Thread.sleep(1000);
   }

   public void setEndTime() throws InterruptedException {
       driver.findElement(end).click();
       Thread.sleep(2000);
       driver.findElement(hour).click();
       Thread.sleep(1000);
       driver.findElement(hourFiledInput).sendKeys(time[2]);
       driver.findElement(minuteFiledInput).click();
       driver.findElement(minuteFiledInput).sendKeys(time[3]);
       Thread.sleep(500);
       driver.findElement(doneButton).click();
       Thread.sleep(500);
   }

   public void saveEvent() throws InterruptedException {
       driver.findElement(saveButton).click();
       Thread.sleep(500);
   }

   public void openEvent() {
       driver.findElement(By.xpath(String.format(dayIconXpathPattern, timeUtils.getDate()))).click();
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

    public String getTimes () {
        return time[0] + ":" + time[1] + " - " + time[2] + ":" + time[3];
    }
}

