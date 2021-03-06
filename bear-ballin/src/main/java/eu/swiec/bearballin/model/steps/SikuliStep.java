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
package eu.swiec.bearballin.model.steps;

import eu.swiec.bearballin.extensions.sikuli.LoggingSikuliDriver;
import eu.swiec.bearballin.model.ITestData;
import eu.swiec.bearballin.model.Step;

import org.sikuli.script.Screen;

import static org.jbehave.core.steps.AbstractStepResult.failed;

public abstract class SikuliStep extends Step {

    protected static final LoggingSikuliDriver driver = initializeVerbosedSikuli();

    public SikuliStep(String stepId) {
        super(stepId);
    }

    public SikuliStep(String stepId, ITestData testData) {
        super(stepId, testData);
    }


    private static LoggingSikuliDriver initializeVerbosedSikuli() {
        return new LoggingSikuliDriver(new Screen());
    }
}
