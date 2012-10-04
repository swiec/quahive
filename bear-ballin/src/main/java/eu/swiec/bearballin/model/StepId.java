package eu.swiec.bearballin.model;

public class StepId {
    public StepId(String stepId) {
        this.stepId = stepId;
    }

    private final String stepId;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Step) {
            return ((Step) obj).getStepId().equals(stepId);
        } else {
            return super.equals(obj);
        }
    }
}
