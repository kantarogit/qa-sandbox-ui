package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import utils.TestCaseDialogAction;

import java.util.List;

public class TestCasesPage extends BasePage {
    private static final String PAGE_URL = "https://qa-sandbox.ni.htec.rs/testcases";

    @FindBy(how = How.CLASS_NAME, using = "portrait-grid")
    private WebElement testCasesDiv;

    @FindBy(how = How.XPATH, using = "//*[@id=\"email\"]")
    private WebElement emailField;

    @FindBy(how = How.XPATH, using = "//*[@id=\"login\"]/div[2]/input")
    private WebElement passwordField;

    @FindBy(how = How.CLASS_NAME, using = "fa-trash-alt")
    private WebElement removeBtn;

    @FindBy(how = How.CLASS_NAME, using = "confirmation-dialog--buttons")
    private WebElement removeConfirmationDialog;

    public TestCasesPage() {
        super(PAGE_URL);
    }

    public List<WebElement> getAllTestCases() {
        waitUntilElementIsVisible(testCasesDiv);
        return testCasesDiv.findElements(By.className("preview-card-title"));
    }

    public void deleteTestCase(WebElement testCase) {
        waitUntilElementIsClickableAndClick(testCase);
        removeBtn.click();
        removeTestCaseDialog(TestCaseDialogAction.CONFIRM);
    }

    private void removeTestCaseDialog(TestCaseDialogAction action) {
        switch (action) {
            case CANCEL:
                waitUntilElementIsClickableAndClick(removeConfirmationDialog
                        .findElement(By.className("confirmation-dialog--buttons--reject")));
            case CONFIRM:
                waitUntilElementIsClickableAndClick(removeConfirmationDialog
                        .findElement(By.className("confirmation-dialog--buttons--confirm")));

        }
    }
}
