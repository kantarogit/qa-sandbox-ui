package dataProvider;

import java.util.List;

public class TestCaseModel {

    private String title;
    private String description;
    private String expectedResult;
    private List<String> testSteps;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getExpectedResult() {
        return expectedResult;
    }

    public List<String> getTestSteps() {
        return testSteps;
    }
}
