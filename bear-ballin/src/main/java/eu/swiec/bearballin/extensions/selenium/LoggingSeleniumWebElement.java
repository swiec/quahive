package eu.swiec.bearballin.extensions.selenium;

import java.util.ArrayList;
import java.util.List;

import eu.swiec.bearballin.model.exceptions.SeleniumStepStaleElementRefException;
import eu.swiec.bearballin.model.steps.SeleniumStep;
import eu.swiec.bearballin.tools.LogFormatter;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LoggingSeleniumWebElement implements WebElement {

    private final static int SELENIUM_STEP_DELAY = SeleniumStep.SELENIUM_STEP_DELAY;
    private final WebElement webElement;
    private static final Logger LOGGER = LoggerFactory.getLogger("");
    private By elementFoundBy;

    public LoggingSeleniumWebElement(WebElement wElement, By foundBy) {
        this.webElement = wElement;
        elementFoundBy = foundBy;
    }

    public void verboseMethod(String messageWhatMethodDid) {
        verboseMethod(messageWhatMethodDid, "");
    }

    public void verboseMethod(String messageWhatMethodDid, CharSequence... arguments) {
        try {
            Thread.sleep(SELENIUM_STEP_DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOGGER.info(LogFormatter.arrangeLogString(messageWhatMethodDid, elementFoundBy, arguments));
    }

    public void sendPasswordKeys(CharSequence... keysToSend) {
        try {
            verboseMethod("SendPass()", "******");
            webElement.sendKeys(keysToSend);
        } catch (StaleElementReferenceException se) {
            throw new SeleniumStepStaleElementRefException(se.getMessage(), se.getCause());
        }
    }

    public void click() {
        try {
            verboseMethod("Click()");
            webElement.click();
        } catch (StaleElementReferenceException se) {
            throw new SeleniumStepStaleElementRefException(se.getMessage(), se.getCause());
        }
    }

    /**
     * Clicks and sends specified keys to WebElement
     *
     * @param keysToSend
     */
    public void clickSendKeys(CharSequence... keysToSend) {
        try {
            click();
            sendKeys(keysToSend);
        } catch (StaleElementReferenceException se) {
            throw new SeleniumStepStaleElementRefException(se.getMessage(), se.getCause());
        }
    }

    /**
     * Clicks, clears and sends specified keys to WebElement
     *
     * @param keysToSend
     */
    public void clickClearSendKeys(CharSequence... keysToSend) {
        try {
            click();
            clear();
            sendKeys(keysToSend);
        } catch (StaleElementReferenceException se) {
            throw new SeleniumStepStaleElementRefException(se.getMessage(), se.getCause());
        }
    }

    /**
     * Clicks Element; Then selects first option by sending specified
     * 'firstOption'. Then sends ARROW_DOWN to WebElement for 'index' times.
     * then presses 'ENTER' on Webelement.
     *
     * @param firstOption
     * @param index
     */
    public void selectOption(String firstOption, int index) {
        try {
            click();

            sendKeys(firstOption);
            for (int i = 0; i < index; i++) {
                sendKey(Keys.ARROW_DOWN);
            }
            sendKey(Keys.TAB);
        } catch (StaleElementReferenceException se) {
            throw new SeleniumStepStaleElementRefException(se.getMessage(), se.getCause());
        }
    }

    public void nextOption() {
        try {
            click();
            sendKey(Keys.ARROW_DOWN);
            sendKey(Keys.ENTER);
        } catch (StaleElementReferenceException se) {
            throw new SeleniumStepStaleElementRefException(se.getMessage(), se.getCause());
        }
    }

    public void submit() {
        try {
            verboseMethod("Submit()");
            webElement.submit();
        } catch (StaleElementReferenceException se) {
            throw new SeleniumStepStaleElementRefException(se.getMessage(), se.getCause());
        }
    }

    public void sendKey(Keys key) {
        try {
            verboseMethod("SendKey()", key.name());
            webElement.sendKeys(key);
        } catch (StaleElementReferenceException se) {
            throw new SeleniumStepStaleElementRefException(se.getMessage(), se.getCause());
        }
    }

    public void sendKeys(CharSequence... keysToSend) {
        try {
            verboseMethod("SendKeys()", keysToSend);
            webElement.sendKeys(keysToSend);
        } catch (StaleElementReferenceException se) {
            throw new SeleniumStepStaleElementRefException(se.getMessage(), se.getCause());
        }
    }

    public void clear() {
        try {
            verboseMethod("Clear()");
            webElement.clear();
        } catch (StaleElementReferenceException se) {
            throw new SeleniumStepStaleElementRefException(se.getMessage(), se.getCause());
        }
    }

    public String getTagName() {
        try {
            return webElement.getTagName();
        } catch (StaleElementReferenceException se) {
            throw new SeleniumStepStaleElementRefException(se.getMessage(), se.getCause());
        }
    }

    public String getAttribute(String name) {
        try {
            return webElement.getAttribute(name);
        } catch (StaleElementReferenceException se) {
            throw new SeleniumStepStaleElementRefException(se.getMessage(), se.getCause());
        }
    }

    public boolean isSelected() {
        try {
            return webElement.isSelected();
        } catch (StaleElementReferenceException se) {
            throw new SeleniumStepStaleElementRefException(se.getMessage(), se.getCause());
        }
    }

    public boolean isEnabled() {
        try {
            return webElement.isEnabled();
        } catch (StaleElementReferenceException se) {
            throw new SeleniumStepStaleElementRefException(se.getMessage(), se.getCause());
        }
    }

    public String getText() {
        try {
            return webElement.getText();
        } catch (StaleElementReferenceException se) {
            throw new SeleniumStepStaleElementRefException(se.getMessage(), se.getCause());
        }
    }

    public List<LoggingSeleniumWebElement> findVerbosedElements(By elementLocator) {
        try {
            List<WebElement> elemsList = webElement.findElements(elementLocator);
            List<LoggingSeleniumWebElement> verbElemsList = new ArrayList<LoggingSeleniumWebElement>(elemsList.size());
            for (WebElement wElem : elemsList) {
                verbElemsList.add(new LoggingSeleniumWebElement(wElem, elementLocator));
            }

            return verbElemsList;
        } catch (StaleElementReferenceException se) {
            throw new SeleniumStepStaleElementRefException(se.getMessage(), se.getCause());
        }
    }

    @Deprecated
    public List<WebElement> findElements(By by) {
        try {
            return webElement.findElements(by);
        } catch (StaleElementReferenceException se) {
            throw new SeleniumStepStaleElementRefException(se.getMessage(), se.getCause());
        }
    }

    public LoggingSeleniumWebElement findElement(By by) {
        try {
            return new LoggingSeleniumWebElement(webElement.findElement(by), by);
        } catch (StaleElementReferenceException se) {
            throw new SeleniumStepStaleElementRefException(se.getMessage(), se.getCause());
        }
    }

    public boolean isDisplayed() {
        try {
            return webElement.isDisplayed();
        } catch (StaleElementReferenceException se) {
            throw new SeleniumStepStaleElementRefException(se.getMessage(), se.getCause());
        }
    }

    public Point getLocation() {
        try {
            return getLocation();
        } catch (StaleElementReferenceException se) {
            throw new SeleniumStepStaleElementRefException(se.getMessage(), se.getCause());
        }
    }

    public Dimension getSize() {
        try {
            return webElement.getSize();
        } catch (StaleElementReferenceException se) {
            throw new SeleniumStepStaleElementRefException(se.getMessage(), se.getCause());
        }
    }

    public String getCssValue(String propertyName) {
        try {
            return webElement.getCssValue(propertyName);
        } catch (StaleElementReferenceException se) {
            throw new SeleniumStepStaleElementRefException(se.getMessage(), se.getCause());
        }
    }

}
