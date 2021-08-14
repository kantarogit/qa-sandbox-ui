package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import utils.WebDriverFactory;

public class BasePage {
    protected WebDriver driver;
    private String pageUrl;

    @FindBy(how = How.CLASS_NAME, using = "navbar-nav")
    private WebElement leftSideMenu;

    public BasePage(String pageUrl) {
        driver = WebDriverFactory.getDriver();
        PageFactory.initElements(driver, this);
        this.pageUrl = pageUrl;
    }

    public void navigateToAnotherPage(String pageLinkText) {
        leftSideMenu.findElement(By.xpath("//a[contains(text(),'" + pageLinkText + "')]")).click();
    }

    public void goTo() {
        driver.navigate().to(pageUrl);
    }

    public boolean isAt() {
       return driver.getCurrentUrl().equalsIgnoreCase(pageUrl);
    }
}