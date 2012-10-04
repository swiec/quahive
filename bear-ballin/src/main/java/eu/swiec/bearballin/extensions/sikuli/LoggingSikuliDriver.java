package eu.swiec.bearballin.extensions.sikuli;

import eu.swiec.bearballin.common.io.Environment;
import eu.swiec.bearballin.tools.LogFormatter;

import org.sikuli.script.App;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.KeyModifier;
import org.sikuli.script.Screen;
import org.sikuli.script.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;


public class LoggingSikuliDriver {

    private final static int TIMEOUT = 10;
    private final static String DEFAULT_RES_PATH = Environment.getResourcePath();

    private static final Logger LOGGER = LoggerFactory.getLogger("");

    public LoggingSikuliDriver(Screen screen) {
        this.screen = screen;
        this.firefox = SikuliFirefoxFactory.getSikuliFirefoxApp();
        Settings.ActionLogs = false;
    }

    private Screen screen;
    private App firefox;

    private void verboseMethod(String messageWhatMethodDid, CharSequence... arguments) {
        LOGGER.info(LogFormatter.arrangeLogString(messageWhatMethodDid, arguments));
    }

    public void tryType(String stringToType) {
        tryType(stringToType, stringToType);
    }

    public void tryTypePassword(String stringToType) {
        tryType(stringToType, "*******");
    }

    public void tryType(String stringToType, int keyModifier) {
        try {
            String logInfo = "";
            screen.type(firefox, stringToType, keyModifier);
            if (keyModifier == KeyModifier.ALT)
                logInfo = "ALT";
            verboseMethod("SendKeys()", logInfo + "+" + stringToType);
        } catch (FindFailed e) {
            e.printStackTrace();
            verboseMethod("Couldn't type.Probably firefox wasn't found on the screen. Proceeding without it...");
        }
    }

    public void tryType(String stringToType, String logInfo) {
        try {
            screen.type(firefox, stringToType);

            verboseMethod("SendKeys()", keyToString(logInfo));
        } catch (FindFailed e) {
            e.printStackTrace();
            verboseMethod("Couldn't type. Probably firefox wasn't found on the screen. Proceeding without it...");
        }
    }

    private String keyToString(String key) {
        String keyStr = "";

        if (key.equals(Key.UP))
            keyStr = "UP_ARROW";
        else if (key.equals(Key.DOWN))
            keyStr = "DOWN_ARROW";
        else if (key.equals(Key.ENTER))
            keyStr = "ENTER";
        else if (key.equals(Key.SPACE))
            keyStr = "SPACEBAR";
        else if (key.equals(Key.TAB))
            keyStr = "TAB";
        else if (key.equals(Key.SHIFT))
            keyStr = "SHIFT";

        return keyStr;
    }

    public boolean tryWait(String pictureFileName) {
        return tryWait(DEFAULT_RES_PATH, pictureFileName);
    }

    public boolean tryWait(String pictureFilePath, String pictureFileName) {
        try {
            screen.wait(pictureFilePath + pictureFileName, TIMEOUT);
            verboseMethod("WAIT FOR..", pictureFileName, " ");
            return true;
        } catch (FindFailed e) {
            verboseMethod("DIDNT FOUND", "Picture '" + pictureFileName + "' wasn't found on the screen. Proceeding without it...", "Was waiting for...");
        }

        return false;
    }

    public <PSRML> boolean tryDragDrop(PSRML from, PSRML to) throws FindFailed {
        screen.dragDrop(from, to);
        return true;
    }

    public boolean tryDragDrop(String pictureFileDragFrom, String pictureFileDragTo) {
        return tryDragDrop(DEFAULT_RES_PATH, pictureFileDragFrom, pictureFileDragTo);

    }

    public boolean tryDragDrop(String pictureFilePath, String pictureFileDragFrom, String pictureFileDragTo) {
        try {
            screen.dragDrop(pictureFilePath + pictureFileDragFrom, pictureFilePath + pictureFileDragTo, TIMEOUT);
            verboseMethod("DragDrop", pictureFileDragFrom, pictureFileDragTo);

        } catch (FindFailed findFailed) {
            verboseMethod("DragDropFailed", pictureFileDragFrom, pictureFileDragTo);
            return false;
        }
        return true;
    }

    public boolean tryWaitVanish(String pictureFile) {
        return tryWaitVanish(DEFAULT_RES_PATH, pictureFile);
    }

    public boolean tryWaitVanish(String pictureFilePath, String pictureFile) {
        screen.waitVanish(pictureFilePath + pictureFile, TIMEOUT);
        verboseMethod("VanishWait", pictureFile, " ");
        return true;
    }

    public boolean exists(String pictureFile) {
        return exists(DEFAULT_RES_PATH, pictureFile);
    }

    public boolean exists(String pictureFilePath, String pictureFile) {
        if (screen.exists(pictureFilePath + pictureFile) == null) {
            verboseMethod("Exists()", " false: " + pictureFile);
            return false;
        }
        verboseMethod("Exists()", "true: " + pictureFile);
        return true;
    }

    public void keyDown(String keyString) {
        screen.keyDown(keyString);
        verboseMethod("KeyDown()", keyToString(keyString));
    }

    public void keyUp(String keyString) {
        screen.keyUp(keyString);
        verboseMethod("KeyUp()", keyToString(keyString));
    }

    public void keyHit(String keyString) {
        keyDown(keyString);
        keyUp(keyString);
    }

    public boolean tryClick(String pictureFile) {
        return tryClick(DEFAULT_RES_PATH, pictureFile);
    }

    public boolean tryClick(String pictureFilePath, String pictureFile) {
        return tryClick(pictureFilePath, pictureFile, "Picture '" + pictureFile + "' wasn't found on the screen");
    }

    public int clickUnderReference(String reference, String target) throws FindFailed {
        return clickUnderReference(DEFAULT_RES_PATH + reference, DEFAULT_RES_PATH + target, true);
    }

    public <PSRML> int clickUnderReference(PSRML reference, PSRML target, boolean whatTodo) throws FindFailed {
        screen.wait(reference).below().highlight(5);
        int value = screen.wait(reference).below().click(target);
        verboseMethod("clickUnder()", reference.toString());
        return value;
    }

//    public <PSRML> int click(PSRML PSRML) throws FindFailed {
//        return click(PSRML);
//
//    }

    public boolean tryClick(String pictureFilePath, String pictureFile, String errorMessage) {
        try {
            screen.wait(pictureFilePath + pictureFile, TIMEOUT);

            screen.click(pictureFilePath + pictureFile);
            verboseMethod("Click()", pictureFile, "");
            return true;
        } catch (FindFailed e) {
            e.printStackTrace();
            verboseMethod("DIDNT FOUND", errorMessage, "Was trying to click...");
        }
        return false;
    }

    public void close() {
        try {
            screen.type(firefox, Key.F4, KeyModifier.ALT);
        } catch (FindFailed e) {
            e.printStackTrace();
            verboseMethod("DIDNT FOUND", "Firefox.App");
        }
        verboseMethod("CloseFox()", "Firefox closed", "");
    }

}
