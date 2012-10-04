package eu.swiec.bearballin.model.steps;

import eu.swiec.bearballin.model.ITestData;
import eu.swiec.bearballin.model.Step;
import eu.swiec.bearballin.model.exceptions.StepExecutionException;

public class DbStep extends Step {

    public DbStep(String stepId) {
        super(stepId);
    }

    @Override
    public boolean checkAccesibility() {
        return false;
    }

    @Override
    public String defaultAction(ITestData testDataCollector) throws StepExecutionException {

        return null;
    }


}
