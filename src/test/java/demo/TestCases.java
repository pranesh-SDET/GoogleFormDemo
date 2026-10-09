package demo;

import java.time.Duration;
import java.util.List;
import java.util.logging.Level;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import demo.wrappers.Wrappers;

public class TestCases {
    ChromeDriver driver;

    @Test
    public void testCase01() throws InterruptedException {
        String formUrl = "https://docs.google.com/forms/d/e/1FAIpQLSep9LTMntH5YqIXa5nkiPKSs283kdwitBBhXWyZdAS-e4CxBQ/viewform";
        driver.get(formUrl);

        // 1. Fill Name
        By nameInputBox = By.xpath("//div[contains(@class,'k3kHxc')]//input | (//input[@type='text'])[1]");
        System.out.println("wait 1");
        Thread.sleep(2000);
        Wrappers.enterText(driver, nameInputBox, "Crio Learner");

        // 2. Fill Reason with Epoch Time
        By practicingAutomationTextArea = By.xpath("//textarea[contains(@class,'tL9Q4c')] | //textarea");
        String practisingAutomationText = "I want to be the best QA Engineer!";
        String epochTimeString = Wrappers.getEpochTimeAsString();
        Wrappers.enterText(driver, practicingAutomationTextArea, practisingAutomationText + " " + epochTimeString);

        // 3. Select radio button as per automation experience
        Thread.sleep(2000);
        System.out.println("wait 3");
        Wrappers.radioButton(driver, "0 - 2");

        // 4. Select checkboxes for skillsets
        Thread.sleep(2000);
        System.out.println("wait 4");
        Wrappers.checkBox(driver, "Java");
        Wrappers.checkBox(driver, "Selenium");
        Wrappers.checkBox(driver, "TestNG");

        // 5. Select Dropdown option
        System.out.println("wait 5");
        Thread.sleep(2000);
        WebElement dropDoWebElement = driver.findElement(By.xpath("//div[@role='listbox'] | //div[contains(@class,'DEh')]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dropDoWebElement);
        Thread.sleep(1000);
        dropDoWebElement.click(); // Real click opens the dropdown popup

        Thread.sleep(2000);
        List<WebElement> dropDownList = driver.findElements(By.xpath("//div[@role='option']//span | //div[contains(@class,'nc')]//span"));
        Wrappers.dropDownClickByLoop(dropDownList, "Mr");

        // 6. Enter 7 days ago date
        By dateInputBox = By.xpath("//div[contains(@class,'A6uyJd')]//input | //input[@type='date']");
        String sevenDaysAgoDate = Wrappers.getdateSevenDaysAgo(7);
        Thread.sleep(2000);
        System.out.println("wait 6");
        Wrappers.enterText(driver, dateInputBox, sevenDaysAgoDate);

        // 7. Enter current time in HH:MM
        By hourElement = By.xpath("//input[@aria-label='Hour'] | (//div[@role='listitem'][.//span[contains(text(),'time')]]//input)[1]");
        By minElement = By.xpath("//input[@aria-label='Minute'] | (//div[@role='listitem'][.//span[contains(text(),'time')]]//input)[2]");
        Wrappers.enterText(driver, hourElement, "07");
        Wrappers.enterText(driver, minElement, "30");

        // 8. Submit the form
        WebElement submitBtn = driver.findElement(By.xpath("//span[text()='Submit']/ancestor::div[@role='button'] | //div[@class='lRwqcd']//div[@role='button'] | //div[@class='lRwqcd']/div"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitBtn);
        Thread.sleep(1000);
        try {
            submitBtn.click();
        } catch (Exception e) {
            Wrappers.clickOnElement(driver, submitBtn);
        }

        // 9. Fetch and verify response message
        Thread.sleep(3000);
        System.out.println("wait 7");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        By successLocator = By.xpath("//div[contains(@class,'vHW')] | //div[contains(text(),'Thanks for your response')]");
        WebElement successMsgElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successLocator));
        System.out.println(successMsgElement.getText());
    }

    @BeforeTest
    public void startBrowser() {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterTest
    public void endTest() {
        if (driver != null) {
            driver.close();
            driver.quit();
        }
    }
}