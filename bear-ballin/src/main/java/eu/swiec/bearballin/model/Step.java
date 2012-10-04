package eu.swiec.bearballin.model;

import eu.swiec.bearballin.model.exceptions.StepExecutionException;
import eu.swiec.bearballin.model.exceptions.StepVerificationException;

public abstract class Step {
    public Step(String stepId) {
        this.stepId = stepId;
        testData = null;
    }

    public Step(String stepId, ITestData testData) {
        this.stepId = stepId;
        this.testData = testData;
    }

    protected final ITestData testData;
    private final String stepId;

    /**
     * @return the stepId
     */
    public String getStepId() {
        return stepId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Step) {
            return ((Step) obj).getStepId().equals(getStepId());
        } else if (obj instanceof String) {
            return ((String) obj).equals(getStepId());
        } else {
            return super.equals(obj);
        }
    }

    public abstract boolean checkAccesibility();

    public boolean checkAccesibility(ITestData testDataCollector) {
        return checkAccesibility();
    }

    public abstract String defaultAction(ITestData testDataCollector) throws StepExecutionException;

    public String stepName() {
        return this.getClass().getSimpleName();
    }

    public String verifyAndPerform(ITestData testDataCollector) throws StepVerificationException {
        if (checkAccesibility(testDataCollector)) {
            return defaultAction(testDataCollector);
        } else {
            throw new StepVerificationException("Step:" + stepName() + " couldn't be verified");
        }
    }

    /**
     * @return the testData
     */
    protected ITestData getTestData() {
        return testData;
    }


    @Override
    public String toString() {
        return stepId;
    }
}
