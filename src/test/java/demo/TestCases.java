package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.logging.Level;
import demo.wrappers.Wrappers;

public class TestCases {
    ChromeDriver driver;

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
    }

    @Test
    public void testCase01() {
        System.out.println("Starting testCase01: Automate Google Forms submission");

        // 1. Navigate to Google Form
        String formUrl = "https://docs.google.com/forms/d/e/1FAIpQLSep9LTMntH5YqIXa5nkiPKSs283kdwitBBhXWyZdAS-e4CxBQ/viewform";
        driver.get(formUrl);
        System.out.println("Navigated to: " + formUrl);

        // 2. Fill in "Crio Learner" in the 1st text box (Name)
        // Matches any question container containing "Name" and targets its input field
        By nameInputLocator = By.xpath("(//input[@type='text'])[1] | //div[@role='listitem'][.//*[text()='Name']]//input");
        Wrappers.enterText(driver, nameInputLocator, "Crio Learner");
        System.out.println("Entered Name: Crio Learner");

        // 3. Write down "I want to be the best QA Engineer! <epoch_time>"
        String currentEpoch = Wrappers.getCurrentEpochTime();
        String reasonMessage = "I want to be the best QA Engineer! " + currentEpoch;
        // Resilient locator targeting either textarea or text input for question 2
        By reasonTextAreaLocator = By.xpath("//div[@role='listitem'][.//*[contains(text(),'Why are you practicing Automation')]]//textarea | //textarea | (//input[@type='text'])[2]");
        Wrappers.enterText(driver, reasonTextAreaLocator, reasonMessage);
        System.out.println("Entered Reason: " + reasonMessage);

        // 4. Select Automation Testing experience radio button ("0 - 2")
        By experienceRadioLocator = By.xpath("//div[@role='radio' and (@data-value='0 - 2' or .//span[text()='0 - 2'])] | //span[text()='0 - 2']");
        Wrappers.clickElement(driver, experienceRadioLocator);
        System.out.println("Selected Experience: 0 - 2");

        // 5. Select Java, Selenium and TestNG checkboxes
        By javaCheckboxLocator = By.xpath("//div[@role='checkbox' and (@aria-label='Java' or .//span[text()='Java'])] | //span[text()='Java']");
        By seleniumCheckboxLocator = By.xpath("//div[@role='checkbox' and (@aria-label='Selenium' or .//span[text()='Selenium'])] | //span[text()='Selenium']");
        By testngCheckboxLocator = By.xpath("//div[@role='checkbox' and (@aria-label='TestNG' or .//span[text()='TestNG'])] | //span[text()='TestNG']");

        Wrappers.clickElement(driver, javaCheckboxLocator);
        Wrappers.clickElement(driver, seleniumCheckboxLocator);
        Wrappers.clickElement(driver, testngCheckboxLocator);
        System.out.println("Selected checkboxes: Java, Selenium, TestNG");

        // 6. Select "Mr" from dropdown
        By dropdownLocator = By.xpath("//div[@role='listbox'] | //div[contains(@class,'jgvuAb')] | //span[text()='Choose']");
        Wrappers.selectDropdownOption(driver, dropdownLocator, "Mr");
        System.out.println("Selected dropdown option: Mr");

        // 7. Dynamic past date (7 days ago) in dd/MM/yyyy
        String pastDate = Wrappers.getPastDateFormatted(7, "dd/MM/yyyy");
        By dateInputLocator = By.xpath("//input[@type='date']");
        Wrappers.enterText(driver, dateInputLocator, pastDate);
        System.out.println("Entered past date: " + pastDate);

        // 8. Provide time 07:30
        By hourInputLocator = By.xpath("//input[@aria-label='Hour' or @aria-label='hour'] | (//div[@role='listitem'][.//span[contains(text(),'time')]]//input)[1]");
        By minuteInputLocator = By.xpath("//input[@aria-label='Minute' or @aria-label='minute'] | (//div[@role='listitem'][.//span[contains(text(),'time')]]//input)[2]");
        Wrappers.enterText(driver, hourInputLocator, "07");
        Wrappers.enterText(driver, minuteInputLocator, "30");
        System.out.println("Entered Time: 07:30");

        // Submit form
        By submitButtonLocator = By.xpath("//span[text()='Submit']/ancestor::div[@role='button'] | //span[text()='Submit']");
        Wrappers.clickElement(driver, submitButtonLocator);
        System.out.println("Submitted the form");

        // 9. Fetch and print the success message
        By successMessageLocator = By.xpath("//div[contains(text(),'Thanks for your response')] | //div[contains(@class,'freebirdFormviewerViewResponseConfirmationMessage')]");
        String confirmationText = Wrappers.getElementText(driver, successMessageLocator);
        System.out.println(confirmationText);

        System.out.println("testCase01 execution completed.");
    }

    @AfterTest
    public void endTest() {
        driver.close();
        driver.quit();
    }
}