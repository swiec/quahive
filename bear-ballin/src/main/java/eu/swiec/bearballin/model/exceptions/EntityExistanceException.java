package eu.swiec.bearballin.model.exceptions;

public class EntityExistanceException extends StepConfigurationException {
    public EntityExistanceException() {
        this("");
    }

    public EntityExistanceException(String message) {
        super(message);
    }

    /**
     *
     */
    private static final long serialVersionUID = 835388986961283820L;

}
