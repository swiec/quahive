package eu.swiec.bearballin.runtime.steps.sikuli.soldier;

import eu.swiec.bearballin.model.ITestData;
import eu.swiec.bearballin.model.exceptions.StepExecutionException;
import eu.swiec.bearballin.model.steps.SikuliStep;

import org.jbehave.core.annotations.Then;
import org.junit.Assert;

/**
 * Created by IntelliJ IDEA.
 * User: uhc
 * Date: 24.09.12
 * Time: 08:20
 * To change this template use File | Settings | File Templates.
 */
public class AllCorrectVerification extends SikuliStep {
    public AllCorrectVerification(String stepId) {
        super(stepId);
    }

    public AllCorrectVerification(String stepId, ITestData testData) {
        super(stepId, testData);
    }

    @Then("\"All correct\" massage will be displayed")
    public void resulMessage() {
        if (!checkAccesibility()) {
            Assert.fail("Wrong message displayed.");
        }
        driver.tryClick("QuitFirefox.png");
    }

    @Override
    public boolean checkAccesibility() {
        return driver.exists("AllCorrectMessage.png");
    }


    @Override
    public String defaultAction(ITestData testDataCollector) throws StepExecutionException {
        return null;
    }
}
