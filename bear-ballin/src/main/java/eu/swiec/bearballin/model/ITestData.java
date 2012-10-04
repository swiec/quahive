package eu.swiec.bearballin.model;

public interface ITestData {
    public void collect(final String stepId, final String stepClassName,
                        final String dataName, final Object data);
}
