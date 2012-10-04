package eu.swiec.bearballin.extensions.selenium;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import eu.swiec.bearballin.model.exceptions.SeleniumStepExecutionException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class LoggingSeleniumWebDriver implements WebDriver {

    private static WebDriver driver;

    public WebDriver getDriver() {
        return driver;
    }

    public LoggingSeleniumWebDriver(WebDriver wd) {
        driver = wd;
    }

    public void get(String url) {
        driver.get(url);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public List<LoggingSeleniumWebElement> findVerbosedElements(By elementsLocator) {
        try {
            List<WebElement> elemList = driver.findElements(elementsLocator);
            List<LoggingSeleniumWebElement> verElemsList = new ArrayList<LoggingSeleniumWebElement>(elemList.size());
            for (WebElement elem : elemList) {
                verElemsList.add(new LoggingSeleniumWebElement(elem, elementsLocator));
            }
            return verElemsList;

        } catch (Exception e) {
            throw new IllegalStateException("findElements method thrown some exception" + e.getMessage(), e.getCause());
            //throw new SeleniumStepExecutionException("Unknown Exception in findVerbosedElements. " + e.getMessage(), e.getCause());
        }
    }

    @Deprecated
    public List<WebElement> findElements(By by) {
        try {
            return driver.findElements(by);
        } catch (Exception e) {
            throw new SeleniumStepExecutionException("Unknown Exception in findElements. " + e.getMessage(), e.getCause());
        }
    }

    public LoggingSeleniumWebElement findElement(By by) throws SeleniumStepExecutionException {
        try {
            WebElement we = driver.findElement(by);
            return new LoggingSeleniumWebElement(we, by);
        } catch (Exception e) {
            throw new SeleniumStepExecutionException("NoSuchElementException", e.getCause());
        }
    }

    public String getPageSource() {
        return driver.getPageSource();
    }

    public void close() {

        driver.close();
    }

    public void quit() {
        driver.quit();
    }

    public Set<String> getWindowHandles() {
        return driver.getWindowHandles();
    }

    public String getWindowHandle() {
        return driver.getWindowHandle();
    }

    public TargetLocator switchTo() {
        return driver.switchTo();
    }

    public Navigation navigate() {
        return driver.navigate();
    }

    public Options manage() {
        return driver.manage();
    }

    @Override
    protected void finalize() throws Throwable {
        driver.close();
        driver = null;
        super.finalize();
    }
}
