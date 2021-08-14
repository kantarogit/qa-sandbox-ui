package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.WebDriverFactory;

public class BasePage {
    protected WebDriver driver;
    private String pageUrl;

    @FindBy(how = How.CLASS_NAME, using = "navbar-nav")
    private WebElement leftSideMenu;

    public BasePage(String pageUrl) {
        driver = WebDriverFactory.getDriver();
        PageFactory.initElements(driver, this);
        driver.manage().window().maximize();
        this.pageUrl = pageUrl;
    }

    public void navigateToAnotherPage(String pageLinkText) {
        leftSideMenu.findElement(By.xpath("//a[contains(text(),'" + pageLinkText + "')]")).click();
    }

    public void goTo() {
        driver.navigate().to(pageUrl);
    }

    public static void endSession() {
        WebDriverFactory.closeBrowser();
    }

    public boolean isAt() {
        return new WebDriverWait(driver, 20).until(ExpectedConditions.urlToBe(pageUrl));
    }

    protected WebElement waitUntilElementIsVisible(WebElement elem) {
        return new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOf(elem));
    }

    protected void waitUntilElementIsClickableAndClick(WebElement elem) {
        new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(elem)).click();
    }
}