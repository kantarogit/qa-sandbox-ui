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
    }

    @AfterClass
    public void teardown() {
        endSession();
    }

    @BeforeMethod
    public void beforeEachTest() {
        loginPage.goTo();
        loginPage.submitCredentials("kantarofilip@gmail.com", "htec123");
        assertThat(dashboardPage.isAt(), Matchers.is(true));
        testCasesPage.goTo();
        assertThat(testCasesPage.isAt(), Matchers.is(true));
    }

    @Test
    public void shouldRemoveTestCase() {

        List<WebElement> allTestCases = testCasesPage.getAllTestCases();
        int lastIndex = allTestCases.size();
        System.out.print(allTestCases.size());

        while (lastIndex > 0) {
            testCasesPage.deleteTestCase(allTestCases.get(lastIndex-1));
            allTestCases = testCasesPage.getAllTestCases();
            lastIndex = allTestCases.size();
            System.out.print(lastIndex);
        }

        assertThat(testCasesPage.getAllTestCases().size(), Matchers.is(0));
    }



}
