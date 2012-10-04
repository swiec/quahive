package eu.swiec.bearballin.model;

import eu.swiec.bearballin.model.exceptions.EntityExistanceException;
import eu.swiec.bearballin.model.exceptions.StepVerificationException;
import eu.swiec.bearballin.model.steps.SeleniumStep;

import org.jbehave.core.annotations.When;

import java.util.ArrayList;
import java.util.List;


public abstract class Process {
    private List<Step> testSteps = new ArrayList<Step>(10);
    private List<String> testStepsIds = new ArrayList<String>(10);

    public List<Object> getStepInstanceList() {
        List<Object> stepsList = new ArrayList<Object>(10);
        stepsList.add(this);
        for (Step singleStep : testSteps) {
            stepsList.add(singleStep);
        }
        return stepsList;
    }


    public void addStep(final Step testStep) throws RuntimeException {
        if (!testSteps.contains(testStep)) {
            testSteps.add(testStep);
            testStepsIds.add(testStep.getStepId());
        } else
            throw new EntityExistanceException("StepId: '" + testStep + "' already exists in this process. StepId has to be unique.");
    }

    public void replaceStep(final Step testStep) throws RuntimeException {
        if (testSteps.contains(testStep)) {
            final int stepIndex = testSteps.indexOf(testStep);
            testSteps.set(stepIndex, testStep);
            testStepsIds.set(stepIndex, testStep.getStepId());
        } else
            throw new EntityExistanceException("StepId: '" + testStep + "' doesnt' exists in this process. Step couldn't be replaced.");
    }

    public boolean contains(Step step) {
        return testSteps.contains(step);
    }

    public List<Object> getBddStepsInstances() {
        List<Object> stepInstances = new ArrayList<Object>(testSteps.size() + 2);
        stepInstances.add(this);
        stepInstances.addAll(testSteps);

        return stepInstances;
    }

    @When("process will be executed")
    protected void executeProcess(TestDataCollector testDataCollector) throws StepVerificationException {
        for (Step step : testSteps) {
            step.verifyAndPerform(testDataCollector);
        }
    }

    public void executeProcess() throws StepVerificationException {
        executeProcess(null);
    }

    public List<String> getStepsIds() {
        return testStepsIds;
    }


    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        SeleniumStep.close();
    }

    /**
     * @return the testSteps
     */
    protected List<Step> getTestSteps() {
        return testSteps;
    }
}
