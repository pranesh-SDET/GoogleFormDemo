package demo.wrappers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Wrappers {

    public static void enterText(WebDriver driver, By locator, String text) {
        try {
            WebElement element = driver.findElement(locator);
            element.clear();
            element.sendKeys(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void radioButton(ChromeDriver driver, String radioButtonText) {
        try {
            WebElement element = driver.findElement(By.xpath("//span[contains(text(),'" + radioButtonText + "')]"));
            element.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void checkBox(ChromeDriver driver, String checkBoxText) {
        try {
            WebElement element = driver.findElement(By.xpath("//span[contains(text(),'" + checkBoxText + "')]"));
            element.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void dropDownClickByLoop(List<WebElement> elements, String dropDownText) {
        try {
            for (WebElement element : elements) {
                if (element.getText().trim().equalsIgnoreCase(dropDownText)) {
                    element.click();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void clickOnElement(ChromeDriver driver, WebElement element) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", element);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getdateSevenDaysAgo(int days) {
        LocalDate currentDate = LocalDate.now();
        LocalDate dateMinus7Days = currentDate.minusDays(days);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = dateMinus7Days.format(formatter);
        System.out.println(formattedDate);
        return formattedDate;
    }

    public static String getEpochTimeAsString() {
        long epochTime = System.currentTimeMillis() / 1000;
        return String.valueOf(epochTime);
    }
}