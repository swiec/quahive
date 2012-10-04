package eu.swiec.bearballin.runtime.steps.sikuli.soldier;

import eu.swiec.bearballin.model.ITestData;
import eu.swiec.bearballin.model.exceptions.StepExecutionException;
import eu.swiec.bearballin.model.steps.SikuliStep;

import org.jbehave.core.annotations.When;

import static junit.framework.Assert.fail;

/**
 * Created by IntelliJ IDEA.
 * User: uhc
 * Date: 23.09.12
 * Time: 21:54
 * To change this template use File | Settings | File Templates.
 */
public class ClickMarkAnswersStep extends SikuliStep {
    public ClickMarkAnswersStep(String stepId) {
        super(stepId);
    }

    public ClickMarkAnswersStep(String stepId, ITestData testData) {
        super(stepId, testData);
    }

    @Override
    public boolean checkAccesibility() {
        return driver.exists("MarkAnsBtn.png");
    }

    @When("\"Mark Answers\" button pressed")
    public void whenMarkAnswersClick() {
        if (checkAccesibility()) {
            defaultAction(null);
        } else fail();
    }

    @Override
    public String defaultAction(ITestData testDataCollector) throws StepExecutionException {
        driver.tryClick("MarkAnsBtn.png");
        return "";
    }
}
