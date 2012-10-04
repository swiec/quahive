package eu.swiec.bearballin.model.exceptions;

import java.util.ArrayList;
import java.util.List;

import eu.swiec.bearballin.model.steps.SeleniumStep;
import eu.swiec.bearballin.runtime.steps.selenium.errorpages.ErrorStepSentToHandDecision;
import eu.swiec.bearballin.runtime.steps.selenium.errorpages.ErrorStepUpss;


public class SeleniumException extends RuntimeException {
    private static final long serialVersionUID = 6633629196775760609L;
    public final static List<SeleniumStep> knownErrorSteps = initializeKnownErrorSteps();

    public SeleniumException() {
        this("No message");
    }

    public SeleniumException(String message) {
        this(message, new Throwable());
    }

    public SeleniumException(String message, Throwable cause) {
        super(message, cause);
        SeleniumException.checkKnownErrors();
        SeleniumStep.savePage();
        SeleniumStep.quit();
    }

    private static List<SeleniumStep> initializeKnownErrorSteps() {
        List<SeleniumStep> errorSteps = new ArrayList<SeleniumStep>();
        errorSteps.add(new ErrorStepUpss("Upss! Cos sie zepsulo"));
        errorSteps.add(new ErrorStepSentToHandDecision("Przes�anie do r�cznej decyzji"));
        return errorSteps;
    }

    public static void checkKnownErrors() {
        for (SeleniumStep knownErrorPage : knownErrorSteps) {
            if (knownErrorPage.checkAccesibility()) {
                throw new SeleniumErrorPageException("Known Error Page occured: ", knownErrorPage);
            }
        }
    }
}
