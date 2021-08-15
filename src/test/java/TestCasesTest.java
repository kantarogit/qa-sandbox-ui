import dataProvider.TestCasesDataProvider;
import org.hamcrest.Matchers;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageObjects.DashboardPage;
import pageObjects.LoginPage;
import pageObjects.TestCasesPage;
import utils.MandatoryField;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static pageObjects.BasePage.endSession;

public class TestCasesTest {

    private TestCasesPage testCasesPage;
    private LoginPage loginPage;
    private DashboardPage dashboardPage;

    @BeforeClass
    public void setup() {
        loginPage = new LoginPage();
        testCasesPage = new TestCasesPage();
        dashboardPage = new DashboardPage();

        loginPage.goTo();
        loginPage.submitCredentials("kantarofilip@gmail.com", "htec123");
        assertThat(dashboardPage.isAt(), Matchers.is(true));
    }

    @AfterClass
    public void teardown() {
        endSession();
    }

    @BeforeMethod
    public void beforeEachTest() {
        testCasesPage.goTo();
        assertThat(testCasesPage.isAt(), Matchers.is(true));
    }

    @Test(enabled = false)
    public void shouldRemoveAllTestCases() {

        List<WebElement> allTestCases = testCasesPage.getAllTestCases();
        int lastIndex = allTestCases.size();

        while (lastIndex > 0) {
            testCasesPage.deleteTestCase(allTestCases.get(lastIndex - 1));
            allTestCases = testCasesPage.getAllTestCases();
            lastIndex = allTestCases.size();
        }

        assertThat(testCasesPage.getAllTestCases().size(), Matchers.is(0));
    }

    @Test(dataProvider = "testCasesValidData", dataProviderClass = TestCasesDataProvider.class)
    public void shouldAddNewTestCase(String title, String description, String expectedResult,
                                     List<String> testSteps, boolean isAutomated) {

        testCasesPage.createNewTestCase()
                .withTitle(title)
                .withDescription(description)
                .withExpectedResult(expectedResult)
                .withSteps(testSteps)
                .isAutomated(isAutomated)
                .submit();

        List<String> testCasesNames = testCasesPage.getAllTestCases()
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        assertThat(testCasesNames, Matchers.hasItem(title));
    }

    @Test
    public void titleFieldIsMandatory() {

        testCasesPage.createNewTestCase()
                .withDescription("desc")
                .withExpectedResult("1")
                .withSteps(asList("123", "123134"))
                .submit();

        assertThat(testCasesPage.validationError(MandatoryField.TITLE), Matchers.is("Title is required"));
    }

    @Test
    public void expectedResultFieldIsMandatory() {
        String testCaseName = UUID.randomUUID().toString().replace("-", "");

        testCasesPage.createNewTestCase()
                .withTitle(testCaseName)
                .withDescription("desc")
                .withSteps(asList("123", "123134"))
                .submit();

        assertThat(testCasesPage.validationError(MandatoryField.EXPECTED_RESULT),
                Matchers.is("Expected result is required"));
    }

    @Test
    public void testStepsAreMandatory() {

        String testCaseName = UUID.randomUUID().toString().replace("-", "");

        testCasesPage.createNewTestCase()
                .withTitle(testCaseName)
                .withDescription("desc")
                .withExpectedResult("1")
                .submit();

        assertThat(testCasesPage.validationError(MandatoryField.TEST_STEPS),
                Matchers.is("There must be at least one test step"));
    }
}
