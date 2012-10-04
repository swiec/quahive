package eu.swiec.bearballin.model.exceptions;


public class SeleniumStepExecutionException extends SeleniumException {

    private static final long serialVersionUID = 4L;

    public SeleniumStepExecutionException(String message) {
        super(message);
    }

    public SeleniumStepExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

}
