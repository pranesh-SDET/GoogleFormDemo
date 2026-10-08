package demo.wrappers;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Wrappers {

    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(10);

    /**
     * Waits for element visibility and enters text after clearing the field.
     */
    public static boolean enterText(ChromeDriver driver, By locator, String text) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            element.clear();
            element.sendKeys(text);
            return true;
        } catch (Exception e) {
            System.out.println("Failed to enter text into: " + locator + " | Exception: " + e.getMessage());
            return false;
        }
    }

    /**
     * Waits for element to be clickable and performs a click.
     * Falls back to JavaScript click if intercepted.
     */
    public static boolean clickElement(ChromeDriver driver, By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            element.click();
            return true;
        } catch (Exception e) {
            try {
                WebElement element = driver.findElement(locator);
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].click();", element);
                return true;
            } catch (Exception jsEx) {
                System.out.println("Failed to click element: " + locator + " | Exception: " + jsEx.getMessage());
                return false;
            }
        }
    }

    /**
     * Opens Google Forms dropdown and selects the specified option.
     */
    public static boolean selectDropdownOption(ChromeDriver driver, By dropdownLocator, String optionText) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(dropdownLocator));
            dropdown.click();

            By optionLocator = By.xpath("//div[@role='option']//span[text()='" + optionText + "'] | //div[@role='option' and @data-value='" + optionText + "']");
            WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(optionLocator));

            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", option);
            return true;
        } catch (Exception e) {
            System.out.println("Failed to select dropdown option: " + optionText + " | Exception: " + e.getMessage());
            return false;
        }
    }

    /**
     * Returns the current epoch time in seconds as a String.
     */
    public static String getCurrentEpochTime() {
        return String.valueOf(Instant.now().getEpochSecond());
    }

    /**
     * Dynamically calculates past date and formats as dd/MM/yyyy.
     */
    public static String getPastDateFormatted(int daysAgo, String pattern) {
        LocalDate pastDate = LocalDate.now().minusDays(daysAgo);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return pastDate.format(formatter);
    }

    /**
     * Waits for element visibility and retrieves text.
     */
    public static String getElementText(ChromeDriver driver, By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return element.getText();
        } catch (Exception e) {
            System.out.println("Failed to fetch text from: " + locator + " | Exception: " + e.getMessage());
            return "";
        }
    }
}