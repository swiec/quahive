package eu.swiec.bearballin.generators;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleIdSerialGenerator {
    private final static Random RAND = new Random();


    public static String nextID() {
        final StringBuilder letters = new StringBuilder(4);
        final StringBuilder digits = new StringBuilder(7);
        final StringBuilder idSerial = new StringBuilder(14);

        final int[] weights = {7, 3, 1, 7, 3, 1, 7, 3};
        int checksum = 0;
        int nextD = 0;
        char letter;
        int letterVal;

        for (int i = 0; i < 3; i++) {

            letter = (char) (RAND.nextInt('Z' - 'A') + 'A');
            letters.append(letter);
            letterVal = ((int) letter - 55);
            checksum += weights[i] * letterVal;

        }
        for (int i = 3; i < 8; i++) {
            nextD = RAND.nextInt(10);
            digits.append(nextD);
            checksum += weights[i] * nextD;
        }

        idSerial.append(letters);
        idSerial.append(checksum % 10);
        idSerial.append(digits);
        return idSerial.toString();
    }


    public static void main(final String[] args) {
        final Logger LOGGER = LoggerFactory.getLogger("");

        for (int i = 0; i < 100; i++) {
            LOGGER.info(SimpleIdSerialGenerator.nextID());
        }
    }

}
