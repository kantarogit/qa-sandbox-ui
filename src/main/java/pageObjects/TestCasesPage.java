package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import utils.MandatoryField;
import utils.TestCaseDialogAction;

import java.util.ArrayList;
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

    @FindBy(how = How.XPATH, using = "//*[text()='New Test Case']")
    private WebElement newTestCaseButton;

    @FindBy(how = How.NAME, using = "title")
    private WebElement testCaseTitle;

    @FindBy(how = How.XPATH, using = "//textarea[@placeholder='Description']")
    private WebElement testDescription;

    @FindBy(how = How.NAME, using = "expected_result")
    private WebElement expectedResult;

    @FindBy(how = How.CSS, using = "[placeholder='Test step']")
    private WebElement testStep;

    @FindBy(how = How.XPATH, using = "//*[text()='Add Test Step']")
    private WebElement newTestStep;

    @FindBy(how = How.CLASS_NAME, using = "submit-button")
    private WebElement submitButton;

    //needed for navigating to the validation-msg element
    @FindBy(how = How.XPATH, using = "//*[text()='Title*']")
    private WebElement labelTitleMandatory;

    @FindBy(how = How.XPATH, using = "//*[text()='Expected Result*']")
    private WebElement labelExpectedResultMandatory;

    @FindBy(how = How.XPATH, using = "//*[text()='Test steps*']")
    private WebElement labelTestStepsMandatory;

    @FindBy(how = How.CLASS_NAME, using = "react-switch-handle")
    private WebElement automatedSwitch;

    public TestCasesPage() {
        super(PAGE_URL);
    }

    public List<WebElement> getAllTestCases() {
        try {
            waitUntilElementIsVisible(testCasesDiv);
        } catch (TimeoutException e) {
            return new ArrayList<>();
        }

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

    public TestCaseSyntax createNewTestCase() {
        newTestCaseButton.click();
        return new TestCaseSyntax();
    }

    public String validationError(MandatoryField mandatoryField) {
        switch (mandatoryField) {
            case TITLE:
                return labelTitleMandatory
                        .findElement(By.xpath("../parent::*"))
                        .findElement(By.id("validation-msg")).getText();

            case EXPECTED_RESULT:
                return labelExpectedResultMandatory
                        .findElement(By.xpath("../parent::*"))
                        .findElement(By.id("validation-msg")).getText();

            case TEST_STEPS:
                return labelTestStepsMandatory
                        .findElement(By.xpath("../parent::*"))
                        .findElement(By.id("validation-msg")).getText();

            default:
                return "No validations found";
        }
    }

    public class TestCaseSyntax {

        //prevent new TestCasesPage().new TestCaseSyntax();
        private TestCaseSyntax() {
        }

        public TestCaseSyntax withTitle(String title) {
            testCaseTitle.sendKeys(title);
            return this;
        }

        public TestCaseSyntax withDescription(String description) {
            testDescription.click();
            testDescription.sendKeys(description);
            return this;
        }

        public TestCaseSyntax withExpectedResult(String expected) {
            expectedResult.sendKeys(expected);
            return this;
        }

        public TestCaseSyntax withSteps(List<String> testSteps) {
            if (testSteps.size() == 1) {
                testStep.sendKeys(testSteps.get(0));
            } else {
                for (int i = 0; i < testSteps.size(); i++) {
                    driver.findElement(By.id("step-" + i)).sendKeys(testSteps.get(i));
                    newTestStep.click();
                }
            }
            return this;
        }

        //checks if unchecked, uncheck if checked :)
        public TestCaseSyntax isAutomated(boolean isAutomated) {

            //true for unchecked
            boolean state = automatedSwitch.getAttribute("style").contains("translateX(1px)");

            if (isAutomated && state) {
                automatedSwitch.click();
            }

            if (!isAutomated && !state) {
                automatedSwitch.click();
            }

            return this;
        }

        public void submit() {
            submitButton.findElement(By.xpath("//button[text()='Submit']")).click();
        }
    }
}
