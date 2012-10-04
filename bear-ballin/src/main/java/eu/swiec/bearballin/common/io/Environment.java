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

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Environment {

    public final static OsTypes osType = setOS();

    private final static String homePath = initHomePath();

    public static String initHomePath() {
        String homePath = "";

        switch (osType) {
            case LINUX:
                homePath = System.getenv("HOME");
                break;
            case WINDOWS:
                homePath = System.getenv("HOMEDRIVE") + System.getenv("HOMEPATH");
                break;
        }

        return homePath;
    }

    private static OsTypes setOS() {
        Map<String, String> envs = System.getenv();
        if (envs.containsKey("OS") && envs.get("OS").equals("Windows_NT")) {
            return OsTypes.WINDOWS;
        } else
            return OsTypes.LINUX;
    }

    public static String getDownloadsPath() {
        switch (osType) {
            case LINUX:
                return homePath + "/Pobrane/";
            case WINDOWS:
                return homePath + "/Downloads/";
            default:
                return "";
        }
    }

    public static String getResourcePath() {
        switch (osType) {
            case LINUX:
                return getRelativePathFromProperty() + "/sikuliGraphics/Linux/";
            case WINDOWS:
                return getRelativePathFromProperty() + "/sikuliGraphics/Windows/";
            default:
                return "";
        }
    }

    public static String getRelativePathFromProperty() {
        String path = "";
        final String command = getPropOrVar("sun.java.command");
        final Pattern pattern = Pattern.compile("(.*?)themis-slave.jar");

        final Matcher matcher = pattern.matcher(command);
        if (matcher.find()) {
            path = matcher.group(1);
        }
        return path.isEmpty() ? "." : path;
    }

    public static String getPropOrVar(final String name) {
        String tempVar = System.getenv(name) == null ? "" : System.getenv(name);

        if (tempVar.isEmpty()) {
            tempVar = System.getProperty(name) == null ? "" : System.getProperty(name);
        }
        return tempVar;
    }

    public static String getFirefoxProfilePath() {
        String firefoxProfilePath = "";
        try {
            switch (osType) {
                case LINUX:
                    firefoxProfilePath = homePath + "/.mozilla/firefox/selen/";
                    break;
                case WINDOWS:
                    firefoxProfilePath = homePath + "\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles\\selen\\";
                    //firefoxProfilePath = homePath + "\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles\\selen2\\";
                    break;
                default:
                    return "";
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return firefoxProfilePath;
    }

    public static String getPkiFilePath() {
        String pkiFilePath = "";

        try {
            switch (osType) {
                case LINUX:
                    pkiFilePath = homePath + "/pki/slavekey";
                    break;
                case WINDOWS:
                    pkiFilePath = homePath + "\\!pki\\sftp\\sftpPublicOpenSSH_key";
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        System.out.println("Identity taken from: " + pkiFilePath);
        return pkiFilePath;
    }

}
