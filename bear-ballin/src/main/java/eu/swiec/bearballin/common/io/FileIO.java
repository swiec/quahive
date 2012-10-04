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
package eu.swiec.bearballin.common.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileIO {
    private final static Logger LOGGER = LoggerFactory.getLogger("");

    public static String[] getLoginPassFromCSVFile(String fileName) throws IOException {
        File loginPassFile = new File(fileName);
        return getLoginPassFromCSVFile(loginPassFile);
    }

    static public String[] getLoginPassFromCSVString(String inputString) throws IOException {
        return getLoginPassFromCSV(new StringReader(inputString));
    }

    static String[] getLoginPassFromCSVFile(File loginPassFile) throws IOException {
        return getLoginPassFromCSV(new FileReader(loginPassFile));
    }

    private static String[] getLoginPassFromCSV(Reader in) throws IOException {
        BufferedReader bufRdr;
        bufRdr = new BufferedReader(in);

        String line = null;
        String[] loginPassPair = new String[7];
        int i = 0;
        while ((line = bufRdr.readLine()) != null) {
            loginPassPair[i] = line;
            i++;
        }

        if (i < 1)
            throw new IOException("Invalid login/password file structure. First line must conatin login, second line - password");
        bufRdr.close();
        return loginPassPair;
    }

    public static void getTestParamsFromCSVFile(File paramsFile, List<List<String>> paramsList, List<String> jobsNames) throws IOException {

        BufferedReader bufRdr;

        try {
            bufRdr = new BufferedReader(new FileReader(paramsFile));
            String TestType = paramsFile.getParentFile().getName();
            List<String> singleTestCaseParameters;

            String line = null;
            String[] splitedLine;
            while ((line = bufRdr.readLine()) != null) {
                splitedLine = line.split(",");
                singleTestCaseParameters = new ArrayList<String>(10);
                singleTestCaseParameters.add(TestType);
                for (String sp : splitedLine) {
                    singleTestCaseParameters.add(sp);
                }
                jobsNames.add(singleTestCaseParameters.get(0) + "_" + singleTestCaseParameters.get(1));
                paramsList.add(singleTestCaseParameters);
            }
            bufRdr.close();
        } catch (Exception e) {
            e.printStackTrace();
            AssertionError ae = new AssertionError("RB system failed to generate end-user message");
            ae.initCause(e);
            throw ae;
        }
    }

    @Deprecated
    public static void writePageSource(String fileName, String pageSource) throws IOException {
        File pageFile = new File(fileName);
        if (pageFile.createNewFile()) {
            OutputStream outStrem = new FileOutputStream(pageFile);
            outStrem.write(pageSource.getBytes("UTF-16"));
            // outStrem.write(pageSource.getBytes(), 0, pageSource.length());
            outStrem.close();
        }

    }

    @Deprecated
    public static void writeStringtoFileSource(String fileName, String stringToWrite) throws IOException {
        writeStringtoFileSource(fileName, stringToWrite, "UTF-16");
    }

    @Deprecated
    public static void writeStringtoFileSource(String fileName, String stringToWrite, String encoding) throws IOException {

        File outputFile = new File(fileName);

        OutputStream outStream = new FileOutputStream(outputFile);
        OutputStreamWriter osw = new OutputStreamWriter(outStream, encoding);

        osw.write(stringToWrite);
        osw.close();
        outStream.close();
    }

    public static void writeStrigToFile(String fileName, String conent) {
        String encoding;
        switch (Environment.osType) {
            case LINUX:
                encoding = "UTF-8";
                break;

            case WINDOWS:
                encoding = "UTF-16";
                break;

            default:
                throw new IllegalStateException("Unknown System");
        }

        try {
            FileUtils.writeStringToFile(new File(fileName), conent, encoding);
        } catch (IOException e) {
            LOGGER.info("File writing error: " + e.getMessage());
        }
    }

    public static List<String> getTestParamsFromCSVFile(String paramsString) throws IOException {
        return getTestParamsFromCSVFile(new StringReader(paramsString));
    }

    public static List<String> getTestParamsFromCSVFile(File testParamsFile) throws IOException {
        return getTestParamsFromCSVFile(new FileReader(testParamsFile));
    }

    public static List<String> getTestParamsFromCSVFile(Reader input) throws IOException {
        BufferedReader bufRdr;
        bufRdr = new BufferedReader(input);

        String line = null;
        List<String> paramsList = new ArrayList<String>(10);

        String[] params;

        while ((line = bufRdr.readLine()) != null) {
            params = line.split(",");
            for (String param : params) {
                paramsList.add(param);
            }
        }
        bufRdr.close();
        return paramsList;
    }

    public static void copy(File input, File output) throws IOException {
        InputStream inStream;

        inStream = new FileInputStream(input);

        OutputStream outStrem = new FileOutputStream(output);

        byte[] buf = new byte[1024];
        int len;

        while ((len = inStream.read(buf)) > 0) {
            outStrem.write(buf, 0, len);
        }

        inStream.close();
        outStrem.close();
    }

    public static void copyKgoPdfs(String numerWniosku) throws IOException {
        File wniosekInputFile = new File(Environment.getDownloadsPath() + "wniosek(" + numerWniosku + ").pdf");
        File cashAtDistanceInputFile = new File(Environment.getDownloadsPath() + "cashAtDistance_" + numerWniosku + ".pdf");

        File wniosekOutputFile = new File(System.getenv("WORKSPACE") + "/wniosek(" + numerWniosku + ").pdf");
        File cashAtDistanceOutputFile = new File(System.getenv("WORKSPACE") + "/cashAtDistance_" + numerWniosku + ".pdf");

        copy(wniosekInputFile, wniosekOutputFile);
        copy(cashAtDistanceInputFile, cashAtDistanceOutputFile);

    }
}
