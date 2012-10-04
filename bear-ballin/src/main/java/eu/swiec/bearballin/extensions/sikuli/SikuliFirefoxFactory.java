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

package eu.swiec.bearballin.extensions.sikuli;

import eu.swiec.bearballin.common.io.Environment;

import org.sikuli.script.App;

public class SikuliFirefoxFactory {

    private final static App firefox = initFirefoxApp();

    public static App getSikuliFirefoxApp() {
        return firefox;
    }

    private static App initFirefoxApp() {
        //App.focus("firefox")
        App firefox = null;
        switch (Environment.osType) {
            case WINDOWS:
                firefox = App.open("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
                break;
            case LINUX:
                firefox = App.open("firefox");
                break;
        }

        return firefox;
    }


}
