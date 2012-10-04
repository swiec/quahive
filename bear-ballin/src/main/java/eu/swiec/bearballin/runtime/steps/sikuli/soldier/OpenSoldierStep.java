package eu.swiec.bearballin.runtime.steps.sikuli.soldier;

import eu.swiec.bearballin.model.ITestData;
import eu.swiec.bearballin.model.exceptions.StepExecutionException;
import eu.swiec.bearballin.model.steps.SikuliStep;

import org.jbehave.core.annotations.Given;
import org.sikuli.script.Key;
import org.sikuli.script.KeyModifier;

import static org.junit.Assert.fail;

/**
 * Created by IntelliJ IDEA.
 * User: uhc
 * Date: 23.09.12
 * Time: 13:10
 * To change this template use File | Settings | File Templates.
 */
public class OpenSoldierStep extends SikuliStep {

    public OpenSoldierStep(String stepId) {
        super(stepId);
    }


    public OpenSoldierStep(String stepId, ITestData testData) {
        super(stepId, testData);
    }

    @Override
    public boolean checkAccesibility() {
        return driver.exists("firefoxOpened.png");
    }

    @Given("soldier example poster")
    public boolean givenSoldierExamplePoster() {
        if (checkAccesibility()) {
            defaultAction(null);
            return true;
        } else fail();
        return false;
    }

    @Override
    public String defaultAction(ITestData testDataCollector) throws StepExecutionException {
        driver.tryType("l", KeyModifier.CTRL);
        driver.tryType("file:///C:/workspace/testExample/soldier.swf");
        driver.tryType(Key.ENTER);

        return "";
    }
}
