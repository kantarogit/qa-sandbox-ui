import org.hamcrest.Matchers;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageObjects.DashboardPage;
import pageObjects.LoginPage;
import pageObjects.TestCasesPage;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestCasesTest {

    private TestCasesPage testCasesPage;
    private LoginPage loginPage;
    private DashboardPage dashboardPage;

    @BeforeClass
    public void setup() {
        loginPage = new LoginPage();
        testCasesPage = new TestCasesPage();
        dashboardPage = new DashboardPage();
    }

    @AfterClass
    public void teardown() {
//        endSession();
    }

    @BeforeMethod
    public void beforeEachTest() {
        loginPage.goTo();
        loginPage.submitCredentials("kantarofilip@gmail.com", "htec123");
        assertThat(dashboardPage.isAt(), Matchers.is(true));
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

    @Test
    public void shouldAddNewTestCase() {
        String testCaseName = "asdasd1e1d";

        testCasesPage.createNewTestCase()
                .withTitle(testCaseName)
                .withDescription("desc")
                .withExpectedResult("1")
                .withSteps(asList("123", "123134"))
                .submit();

        List<String> testCasesNames = testCasesPage.getAllTestCases()
                .stream().map(WebElement::getText)
                .collect(Collectors.toList());

        assertThat(testCasesNames, Matchers.hasItem(testCaseName));
    }
}
