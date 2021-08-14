import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import pageObjects.LoginPage;
import pageObjects.TestCasesPage;

import static org.hamcrest.MatcherAssert.assertThat;

public class TestCasesTest {

    private TestCasesPage testCasesPage;
    private LoginPage loginPage;

    @Test
    public void shouldRemoveTestCase() {
        loginPage = new LoginPage();
        testCasesPage = new TestCasesPage();
        loginPage.goTo();
        loginPage.submitCredentials("kantarofilip@gmail.com", "htec123");

        assertThat(testCasesPage.isAt(), Matchers.is(true));

    }
}
