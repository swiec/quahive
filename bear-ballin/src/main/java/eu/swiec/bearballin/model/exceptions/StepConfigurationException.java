package eu.swiec.bearballin.model.exceptions;

import eu.swiec.bearballin.model.steps.SeleniumStep;

public class StepConfigurationException extends RuntimeException {

    public StepConfigurationException(String message) {
        super(message);
        SeleniumStep.quit();
    }

    public StepConfigurationException(String message, Throwable cause) {
        super(message, cause);
        SeleniumStep.quit();
    }

    /**
     *
     */
    private static final long serialVersionUID = 6757117514636515362L;

}
