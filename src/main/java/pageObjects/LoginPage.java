package pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class LoginPage extends BasePage {

    private static final String PAGE_URL = "https://qa-sandbox.ni.htec.rs/login";

    @FindBy(how = How.NAME, using = "email")
    private WebElement emailField;

    @FindBy(how = How.NAME, using = "password")
    private WebElement passwordField;

    @FindBy(how = How.XPATH, using = "//*[@id=\"root\"]/div/div[3]/div/div/button")
    private WebElement loginBtn;

    public LoginPage() {
        super(PAGE_URL);
    }

    public void submitCredentials(String email, String password) {
        emailField.sendKeys(email);
        passwordField.sendKeys(password);
        loginBtn.click();
    }
}
