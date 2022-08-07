package pageObjects;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.functions.ExpectedCondition;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static io.appium.java_client.touch.offset.ElementOption.element;

public class AndroidGmailPage {
    private AndroidDriver androidDriver;
    private TouchAction touchAction;

    private By composeButton = By.id("compose_button");
    private By toField = By.xpath("//android.widget.RelativeLayout[@resource-id = 'com.google.android.gm:id/to_content']//android.widget.EditText");
    private By addRecipient = By.id("peoplekit_avatars_avatar");
    private By subjectField = By.id("com.google.android.gm:id/subject");
    private By bodyField = By.xpath("//android.widget.EditText[@text ='Compose email']");
    private By sendButton = By.id("com.google.android.gm:id/send");

    private By openFoldersButton = By.xpath("//android.widget.ImageButton[@content-desc=\"Open navigation drawer\"]");
    private By trashFolderButton = By.xpath("//android.widget.LinearLayout[@resource-id = 'com.google.android.gm:id/container']//android.widget.TextView[@text = 'Trash']");
    private By lastLetter = By.xpath("//android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout/android.support.v7.widget.RecyclerView/android.view.ViewGroup[1]");
    private By lastLetterSubject = By.xpath("//android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout/android.support.v7.widget.RecyclerView/android.view.ViewGroup[1]//android.widget.TextView[@resource-id = \"com.google.android.gm:id/subject\"]");
    private String lastLetterPattern = "//android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout/android.support.v7.widget.RecyclerView/android.view.ViewGroup[1]//android.widget.TextView[@resource-id = \"com.google.android.gm:id/subject\" and @text = '%s']";


    private By expandButton = By.id("com.google.android.gm:id/show_hide_details");
    private By sentFrom = By.id("com.google.android.gm:id/from_details");
    private By sentTime = By.id("com.google.android.gm:id/date_details");

    private By sentSubject = By.id("com.google.android.gm:id/subject_and_folder_view");
    private By sentBody = By.xpath("//android.widget.FrameLayout/android.view.ViewGroup/android.webkit.WebView/android.webkit.WebView/android.view.View[2]/android.view.View");
    private By navigateUpButton = By.xpath("//android.widget.ImageButton[@content-desc=\"Navigate up\"]");
    private By deleteButton = By.id("com.google.android.gm:id/delete");

    private By queued = By.xpath("//android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout/android.support.v7.widget.RecyclerView/android.view.ViewGroup[1]//android.widget.TextView[contains(@text, 'Queued')]");


    public AndroidGmailPage(AndroidDriver driver) {
        androidDriver = driver;
        touchAction = new TouchAction(driver);
    }

    public void tapCompose () {
        findWithWait(composeButton).click();
    }

    public void fillTo(String mail) {
        findWithWait(toField).click();
        findWithWait(toField).sendKeys(mail);
        findWithWait(addRecipient).click();
    }

    public void tapnavigateUp () {
        findWithWait(navigateUpButton).click();
    }

    public void fillSubject (String subject) {
        findWithWait(subjectField).click();
        System.out.println("SubjectClicked");
        findWithWait(subjectField).sendKeys(subject);
    }

    public void fillBody (String body) {
        findWithWait(bodyField).sendKeys(body);
    }

    public void clickSend () {
        findWithWait(sendButton).click();
    }

    public void clickCompose () {
        findWithWait(composeButton).click();
    }

    public void waitForLetterAndOpenIt(String subject) {
        By lastLetter = By.xpath(String.format(lastLetterPattern, subject));
        new WebDriverWait (androidDriver, 25).until(ExpectedConditions.presenceOfElementLocated(lastLetter));
        findWithWait(lastLetter).click();
    }

    public boolean waitQueuedAppears () {
      new WebDriverWait (androidDriver, 25).until(ExpectedConditions.presenceOfElementLocated(queued));
      return !androidDriver.findElements(queued).isEmpty();
    }

    public boolean waitQueuedDisappears () {
        new WebDriverWait (androidDriver, 25).until(ExpectedConditions.invisibilityOfElementLocated(queued));
        return androidDriver.findElements(queued).isEmpty();
    }

    public void waitForLetter (String subject) {
            By lastLetter = By.xpath(String.format(lastLetterPattern, subject));
            new WebDriverWait (androidDriver, 25).until(ExpectedConditions.presenceOfElementLocated(lastLetter));
    }

    public String getSubject () {
        return findWithWait(sentSubject).getText();

    }

    public String getMail () {
        clckExpand();
        return findWithWait(sentFrom).getText();
    }

    public void clckExpand () {
        findWithWait(expandButton).click();
    }

    public String getTime () {
        return findWithWait(sentTime).getText();
    }

    public String getBody() {
       return findWithWait(sentBody).getText();
    }

    public void longPress (By by) {
        touchAction.longPress(LongPressOptions.longPressOptions().withElement(element(findWithWait(by)))).release().perform();
    }

    public void deleteLetter (String subject) {
        By lastLetter = By.xpath(String.format(lastLetterPattern, subject));
        new WebDriverWait (androidDriver, 25).until(ExpectedConditions.presenceOfElementLocated(lastLetter));
        longPress(lastLetter);
        findWithWait(deleteButton).click();
    }

    public boolean letterPresent (String subject) {
        By lastLetter = By.xpath(String.format(lastLetterPattern, subject));
        new WebDriverWait (androidDriver, 25).until(ExpectedConditions.presenceOfElementLocated(lastLetter));
        return !androidDriver.findElements(lastLetter).isEmpty();
    }

    public boolean letterDisappeared (String subject) {
        By lastLetter = By.xpath(String.format(lastLetterPattern, subject));
        new WebDriverWait (androidDriver, 25).until(ExpectedConditions.invisibilityOfElementLocated(lastLetter));
        return androidDriver.findElements(lastLetter).isEmpty();
    }

    public void openTrash () {
        findWithWait(openFoldersButton).click();
        int height = androidDriver.manage().window().getSize().height;
        int width = androidDriver.manage().window().getSize().width;

        touchAction.longPress(PointOption.point(width - 259, height-15)).moveTo(PointOption.
                point(width - 250, height - 450)).release().perform();
        findWithWait(trashFolderButton).click();
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
        WebDriverWait webDriverWait = new WebDriverWait(androidDriver, 10);
        return webDriverWait.until(elementPresents(by));
    }

}
