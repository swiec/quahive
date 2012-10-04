package eu.swiec.bearballin.model.steps;

import static org.junit.Assert.fail;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import eu.swiec.bearballin.common.io.Environment;
import eu.swiec.bearballin.common.io.FileIO;
import eu.swiec.bearballin.extensions.selenium.LoggingSeleniumWebDriver;
import eu.swiec.bearballin.extensions.selenium.LoggingSeleniumWebElement;
import eu.swiec.bearballin.model.ITestData;
import eu.swiec.bearballin.model.Step;
import eu.swiec.bearballin.model.exceptions.SeleniumException;
import eu.swiec.bearballin.model.exceptions.SeleniumStepStaleElementRefException;
import eu.swiec.bearballin.model.exceptions.SeleniumStepVerificationException;
import eu.swiec.bearballin.tools.LogFormatter;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.gargoylesoftware.htmlunit.WebClient;



public abstract class SeleniumStep extends Step {
    protected final static Robot ROBO = initialiseRobo();
    protected final static webDriverTypes driverType = webDriverTypes.FIREFOX_WEBDRIVER;
    protected final static LoggingSeleniumWebDriver driver = driverInitialistaion(driverType);
    public final static int EXPLICIT_CHECK_ERRORPAGES_INTERVAL = 30;
    protected final static int EXPLICIT_TIMEOUT = 120; // sec.
    protected final static int IMPLICIT_TIMEOUT = 1; // sec.
    public final static int SELENIUM_STEP_DELAY = 900; // milisec.

    private final static Logger LOGGER = LoggerFactory.getLogger("");

    protected final static String GUICSSID_STEP_TITILE_SELECTOR = "legend.fieldsetLabel";
    protected final static String DEFGID_NEXT_BTN = "ApplicationForm:next";
    protected final static String DEFGID_NEXT_BTN_APPROVE = "nextPanelForm:yes";
    protected final static String DEFGUI_LISTBOX_FIRST_OPTIONS_LETTER = "N";

    private enum webDriverTypes {
        FIREFOX_WEBDRIVER, HTMLUNIT_WEBDRIVER
    }

    public static LoggingSeleniumWebDriver getWebDriver() {
        return driver;
    }

    public SeleniumStep(String stepId) {
        super(stepId);
    }

    public static void savePage() {
        LOGGER.info("Saving error page for analysis...");
        FileIO.writeStrigToFile("ErrorPageReport.html", driver.getPageSource());
    }

    @Override
    public String verifyAndPerform(ITestData testDataCollector) throws SeleniumStepVerificationException {
        if (checkAccesibility()) {
            return defaultAction(testDataCollector);
        } else {
            throw new SeleniumStepVerificationException("Step:" + stepName() + " couldn't be verified");
        }
    }

    private static LoggingSeleniumWebDriver driverInitialistaion(webDriverTypes drivertype) {
        LoggingSeleniumWebDriver localDriver;

        switch (driverType) {
            case FIREFOX_WEBDRIVER:
                String profilePath = Environment.getFirefoxProfilePath();
                File profileDir = new File(profilePath);
                FirefoxProfile fireFoxProf = new FirefoxProfile(profileDir);
                localDriver = new LoggingSeleniumWebDriver(new FirefoxDriver(fireFoxProf));
                break;

            case HTMLUNIT_WEBDRIVER:
                localDriver = new LoggingSeleniumWebDriver(createHtmlUnitWebDriver());
                break;
            default:
                throw new IllegalStateException("Unkonwn WEBDRIVER type exception");
        }

        localDriver.manage().timeouts().implicitlyWait(IMPLICIT_TIMEOUT, TimeUnit.SECONDS);
        return localDriver;
    }

    public static WebDriver createHtmlUnitWebDriver() {
        return new HtmlUnitDriver(true) {
            @Override
            protected WebClient modifyWebClient(WebClient client) {
                client.addRequestHeader("Accept-Language", "pl,en-us,en;q=0.5");
                return client;
            }
        };
    }

    private static Robot initialiseRobo() {
        Robot rob = null;
        try {
            rob = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
            LOGGER.error("Couldn't initialise java.awt.Robot object.");
        }
        return rob;
    }

    protected void saveFile() {

        waitStatic(5);
        ROBO.keyPress(KeyEvent.VK_ALT);
        ROBO.keyPress(KeyEvent.VK_S);
        ROBO.keyRelease(KeyEvent.VK_S);
        ROBO.keyRelease(KeyEvent.VK_ALT);
        ROBO.keyPress(KeyEvent.VK_ENTER);
        ROBO.keyRelease(KeyEvent.VK_ENTER);
    }

    public static void close() {
        driver.close();
    }

    public static void quit() {
        driver.quit();
    }

    protected void clickClearSendKeys(By by, String keys) {
        driver.findElement(by).click();
        driver.findElement(by).clear();
        driver.findElement(by).sendKeys(keys);
    }

    protected void pageDown() {
        ((JavascriptExecutor) driver.getDriver()).executeScript("if (window.screen){window.scrollTo(0,window.screen.availHeight);};");
    }

    /**
     * @param by
     * @return text of the element or null
     */
    protected String getTextIfOccure(By by) {
        String text = "";

        LoggingSeleniumWebElement we;
        if (isPresent(by)) {
            if ((text = (we = driver.findElement(by)).getText()).isEmpty()) {
                text = we.getAttribute("value");
                if (text.isEmpty())
                    LOGGER.error(LogFormatter.arrangeLongLogString("NO_VALUE", "'" + by + "' - empty"));
                return null;
            }
            LOGGER.info(LogFormatter.arrangeLongLogString("GOT_VALUE", "'" + by + "' - " + text));
            return text;
        } else {
            LOGGER.error(LogFormatter.arrangeLongLogString("NO_ELEMT", "'" + by + "' - Element wasn't found"));
        }
        return null;
    }

    protected boolean isPresent(By webElementLocator) {
        return isPresent(webElementLocator, null);
    }

    protected boolean isPresent(By webElemLocator, List<LoggingSeleniumWebElement> webElementsLstToBeSet) {
        webElementsLstToBeSet = driver.findVerbosedElements(webElemLocator);
        return (webElementsLstToBeSet != null && webElementsLstToBeSet.size() > 0);
    }

    private boolean strContains(String str, String contains) {
        if (contains == null)
            fail("Bad comparisment. Test implementation error.");
        return str != null && str.contains(contains);
    }

    protected boolean waitForTextOrValue(String what) {
        return waitForTextOrValue(what, By.cssSelector(GUICSSID_STEP_TITILE_SELECTOR));
    }

    protected boolean waitForTextOrValue(String what, int timeout) {
        return waitForTextOrValue(what, By.cssSelector(GUICSSID_STEP_TITILE_SELECTOR), timeout);
    }

    protected boolean waitForTextOrValue(String what, By whereToLookfor) {
        return waitForTextOrValue(what, whereToLookfor, EXPLICIT_TIMEOUT);
    }

    protected boolean waitForTextOrValue(String what, By whereToLookfor, int timeout) {
        for (int second = 0; second < timeout; second++) {
            if (second % EXPLICIT_CHECK_ERRORPAGES_INTERVAL == 0) {
                LOGGER.info("Check for known errorpages...");
                SeleniumException.checkKnownErrors();
            }
            if (isPresent(whereToLookfor)) {

                try {
                    if (strContains(driver.findElement(whereToLookfor).getText(), what)) {
                        LOGGER.info(LogFormatter.arrangeLongLogString("TXT_FOUND", what));
                        return true;

                    } else if (strContains(driver.findElement(whereToLookfor).getAttribute("value"), what)) {
                        LOGGER.info(LogFormatter.arrangeLongLogString("VAL_FOUND", what));
                        return true;
                    }

                    LOGGER.info(LogFormatter.arrangeLongLogString("SEEK_VAL", "'" + what + "' vs getText:'" + driver.findElement(whereToLookfor).getText()
                            + "' getAttribute('value'):" + driver.findElement(whereToLookfor).getAttribute("value") + " TIMEOUT:" + (timeout - second)));
                } catch (SeleniumStepStaleElementRefException e) {
                    LOGGER.info(LogFormatter.arrangeLogString("SEEK_VAL", "Page was reloaded..."));
                }

            } else {
                LOGGER.info(LogFormatter.arrangeLongLogString("WAIT_TX&VL", "'" + what + "'at'" + whereToLookfor.toString() + " WebElem n-found. TIMEOUT:" + (timeout - second)));
            }
            waitStatic(1);
        }
        LOGGER.info(LogFormatter.arrangeLongLogString("NOT_FOUND", "'" + what + "' not on the screen, for " + timeout + " seconds(timeout)."));
        return false;
    }

    protected boolean waitForExist(By findElementBy) {
        return waitForExist(findElementBy, EXPLICIT_TIMEOUT);
    }

    protected boolean waitForExist(By findElementBy, int timeout) {
        return waitAppear(findElementBy, timeout, false);
    }

    protected boolean waitForDisappear(By findElementBy) {
        return waitForDisappear(findElementBy, EXPLICIT_TIMEOUT);
    }

    protected boolean waitForDisappear(By findElementBy, int timeout) {
        return waitAppear(findElementBy, timeout, true);
    }

    private boolean waitAppear(By findElementBy, int timeout, boolean waitsForDisappear) {
        for (int second = 0; second < timeout; second++) {
            if (second % EXPLICIT_CHECK_ERRORPAGES_INTERVAL == 0) {
                SeleniumException.checkKnownErrors();
            }
            if (isPresent(findElementBy) ^ waitsForDisappear) {
                LOGGER.info(LogFormatter.arrangeLogString("WAIT_APPEA", "Found " + findElementBy));
                return true;
            }
            LOGGER.info(LogFormatter.arrangeLogString("WAIT_APPEA", "Looking for " + findElementBy));
            waitStatic(1);
        }
        LOGGER.error(LogFormatter.arrangeLogString("WAIT_APPEA", "Not found " + findElementBy));
        return false;
    }

    protected void waitEnabled(By whereToLookfor) {
        waitEnabled(whereToLookfor, EXPLICIT_TIMEOUT);
    }

    protected void waitEnabled(By findElementBy, int timeout) {
        for (int second = 0; second < timeout; second++) {
            if (second % EXPLICIT_CHECK_ERRORPAGES_INTERVAL == 0) {
                SeleniumException.checkKnownErrors();
            }
            if (isPresent(findElementBy)) {
                try {
                    if (driver.findElement(findElementBy).getAttribute("disabled") != null) {
                        driver.findElement(findElementBy).verboseMethod("WAIT_ENBLD", findElementBy.toString() + " disabled");
                    } else {
                        driver.findElement(findElementBy).verboseMethod("ELEM_ENBLD", findElementBy.toString());
                        return;
                    }
                } catch (SeleniumStepStaleElementRefException e) {
                    LOGGER.info(LogFormatter.arrangeLogString("WAIT_ENBLD", "Page was reloaded..."));
                }
            } else {
                LOGGER.info(LogFormatter.arrangeLongLogString("WAIT_ENBLD", findElementBy.toString() + " wasn't found"));
            }
            waitStatic(1);
        }
        fail("Couldn't find '" + findElementBy.toString() + "' on the screen, for " + EXPLICIT_TIMEOUT + " seconds(timeout).");
    }

    /**
     * Clicks Element; Then selects first option by sending specified
     * 'firstOption'. Then sends ARROW_DOWN to WebElement for 'index' times.
     * then presses 'ENTER' on Webelement.
     *
     * @param firstOption
     * @param index
     */
    public void selectOptionSlowly(By by, String firstOption, int index) {
        waitStatic(1);
        driver.findElement(by).click();
        waitStatic(1);
        driver.findElement(by).sendKeys(firstOption);
        waitStatic(1);
        for (int i = 0; i < index; i++) {
            driver.findElement(by).sendKey(Keys.ARROW_DOWN);
            waitStatic(1);
        }
        driver.findElement(by).sendKey(Keys.TAB);
    }

    protected void waitStatic(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            LOGGER.error("InterruptedEsception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
