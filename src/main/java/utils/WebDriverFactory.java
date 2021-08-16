package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebDriverFactory {
    public static WebDriver driver = null;

    public static WebDriver getDriver() {

        if (driver == null) {
            // set this according to the local setup or copy chromedriver version as per Chrome browser version
            // https://chromedriver.chromium.org/downloads
            System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
            driver = new ChromeDriver();
        }

        return driver;
    }

    public static void closeBrowser() {
        driver.close();
    }
}