package eu.swiec.bearballin.model.exceptions;


public class StepExecutionException extends RuntimeException {

    public StepExecutionException(String message) {
        super(message);
    }

    public StepExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     *
     */
    private static final long serialVersionUID = -3833109897224065594L;

}
