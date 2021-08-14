package pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class TestCasesPage extends BasePage {
    private static final String PAGE_URL = "https://qa-sandbox.ni.htec.rs/testcases";

    @FindBy(how = How.XPATH, using = "//*[@id=\"email\"]")
    private WebElement emailField;

    @FindBy(how = How.XPATH, using = "//*[@id=\"login\"]/div[2]/input")
    private WebElement passwordField;

    public TestCasesPage() {
        super(PAGE_URL);
    }
}
