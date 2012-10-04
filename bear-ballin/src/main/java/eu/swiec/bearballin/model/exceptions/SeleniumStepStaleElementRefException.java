package eu.swiec.bearballin.model.exceptions;

public class SeleniumStepStaleElementRefException extends SeleniumException {

    private static final long serialVersionUID = -8271581105796060843L;

    public SeleniumStepStaleElementRefException(String message) {
        super(message);
    }

    public SeleniumStepStaleElementRefException(String message, Throwable cause) {
        super(message, cause);
    }
}
