package eu.swiec.bearballin.tools;

import org.openqa.selenium.By;

public class LogFormatter {
    private final static int METHOD_DESC_LENGHT = 10;
    private final static int ARG_LENGHT = 20;
    private final static int TARGET_IDENT_LENGHT = 65;


    public static String arrangeLogString(String messageWhatMethodDid, By actionPlace, CharSequence... arguments) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("|%1$-" + METHOD_DESC_LENGHT + "s|", messageWhatMethodDid));
        sb.append(String.format("%1$-" + ARG_LENGHT + "s|", rightSubstring(arguments[0].toString(), ARG_LENGHT)));
        sb.append(rightSubstring(actionPlace.toString(), TARGET_IDENT_LENGHT));
        return sb.toString();
    }

    public static String arrangeLogString(String messageWhatMethodDid, CharSequence... arguments) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("|%1$-" + METHOD_DESC_LENGHT + "s|", messageWhatMethodDid));
        sb.append(String.format("%1$-" + ARG_LENGHT + "s|", rightSubstring(arguments[0].toString(), ARG_LENGHT)));
        return sb.toString();
    }

    public static String arrangeLongLogString(String messageWhatMethodDid, String longMessage) {
        int longMessageLenght = ARG_LENGHT + TARGET_IDENT_LENGHT;
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("|%1$-" + METHOD_DESC_LENGHT + "s|", messageWhatMethodDid));
        sb.append(String.format("%1$-" + longMessageLenght + "s|", rightSubstring(longMessage, longMessageLenght)));
        return sb.toString();
    }

    private static String rightSubstring(String processedString, int lenght) {
        if (processedString.length() > lenght) {
            return "(..)" + processedString.substring(processedString.length() - lenght + 4);
        }
        return processedString;
    }


}
