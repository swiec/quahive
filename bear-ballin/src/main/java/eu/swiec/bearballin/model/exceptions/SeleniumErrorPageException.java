package eu.swiec.bearballin.model.exceptions;

import eu.swiec.bearballin.model.steps.SeleniumStep;

public class SeleniumErrorPageException extends RuntimeException {
    private static final long serialVersionUID = 2L;

    public SeleniumErrorPageException(String errorMessage, SeleniumStep step) {
        super(errorMessage + step.getStepId());
        SeleniumStep.quit();
    }
}
