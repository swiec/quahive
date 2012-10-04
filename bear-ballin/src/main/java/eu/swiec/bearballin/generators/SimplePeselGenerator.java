/*
 * Bear Ballin - Testing framework
 *
 * Copyright 2010 Grzegorz Swiec (swiec.eu).
 * https://github.com/swiec/bear-ballin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package eu.swiec.bearballin.generators;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimplePeselGenerator {

    /**
     * @param moduloMinBirthYear - dwucyfrowy, minimalny rok 19[00-99] urodzenia.
     * @param moduloMaxBirthYear - dwucyfrowy, maksymalny rok 19[00-99]urodzenia.
     * @return - pesel
     */
    public static String generatePesel(final int minBirthYear, final int maxBirthYear, final boolean male) {


        final int moduloMinYear = minBirthYear % 100;
        final int moduloMaxYear = maxBirthYear % 100;

        final int birthYearSpread = (moduloMaxYear - moduloMinYear) % 100;

        final Random rand = new Random();
        final StringBuilder stringBld = new StringBuilder();
        final int year = rand.nextInt(birthYearSpread) + moduloMinYear; // limit to age
        // minimum 26
        final int month = rand.nextInt(12) + 1;
        final int day = rand.nextInt(27) + 1;
        int serial = (rand.nextInt(999) + 1) * 10 + rand.nextInt(5) * 2;

        if (male) {
            serial++;
        }

        int control = 1 * year / 10 + (year % 10) * 3;
        control += 7 * (month / 10) + 9 * (month % 10);
        control += 1 * (day / 10 + 3 * (day % 10));
        control += 7 * (serial / 1000) + 9 * ((serial / 100) % 10) + 1 * ((serial / 10) % 10) + 3 * (serial % 10);
        control = 10 - (control % 10);
        control = control % 10;

        stringBld.append(String.format("%02d", year));         // year
        stringBld.append(String.format("%02d", month));     // month
        stringBld.append(String.format("%02d", day));         // day
        stringBld.append(String.format("%04d", serial));     // serial
        stringBld.append(String.format("%d", control));

        //System.out.println(sb.toString() + ",Piotr");

        return stringBld.toString();
    }

    /**
     * @return - pesel
     */
    public static String generatePesel() {
        return generatePesel(00, 85, true);
    }

    public static void main(final String[] args) {
        final Logger LOGGER = LoggerFactory.getLogger("");
        for (int i = 0; i < 200; i++) {
            LOGGER.info("gswiec,Nowe123!," + SimplePeselGenerator.generatePesel(45, 46, true) + ",Tomasz,3500,2500");
        }
    }
}
