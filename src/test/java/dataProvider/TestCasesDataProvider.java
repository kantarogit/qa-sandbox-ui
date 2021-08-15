package dataProvider;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TestCasesDataProvider {

    @DataProvider(name = "testCasesValidData")
    public static Iterator<Object[]> testCasesValidData() throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        List<Object[]> returnData = new ArrayList<>();

        mapper.readValue(
                new File("src/test/resources/testCasesValidData.json"), TestCasesRootJsonModel.class)
                .getTestCases()
                .forEach(testCase -> {
                    Object[] dataRow = new Object[4];
                    dataRow[0] = testCase.getTitle();
                    dataRow[1] = testCase.getDescription();
                    dataRow[2] = testCase.getExpectedResult();
                    dataRow[3] = testCase.getTestSteps();
                    returnData.add(dataRow);
                });


        return returnData.iterator();
    }
}
