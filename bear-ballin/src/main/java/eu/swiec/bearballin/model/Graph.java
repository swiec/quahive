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
package eu.swiec.bearballin.model;

import eu.swiec.bearballin.model.exceptions.StepVerificationException;

import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Given;

import java.util.*;

public abstract class Graph {

    private List<Process> processGraph = new ArrayList<Process>(10);
    Map<String, Process> processStepsMap = new HashMap<String, Process>(20);
    List<Object> bddStepsInstances = new ArrayList<Object>(10);
    public ITestData TestData;

    public void addProcess(Process process) throws RuntimeException {
        //check if steps are uniqe
        for (String currentStepId : process.getStepsIds()) {
            if (processStepsMap.containsKey(currentStepId)) {
                Set<String> graphStepsIds = processStepsMap.keySet();
                for (String stepId : process.getStepsIds()) {
                    if (graphStepsIds.contains(stepId)) {
                        throw new RuntimeException("Process graph " + getClass().getSimpleName() + " already contains " + stepId);
                    }
                }
                throw new IllegalStateException("When trying to add process:" + process.getClass().getSimpleName() + " to processGraph " + getClass().getSimpleName() + " it turnd out that graph already contains one of this process steps. Didn't found which one.");
            }
        }

        //add process to processGraph
        processGraph.add(process);

        //add process.steps to processStepsMap
        for (String stepId : process.getStepsIds()) {
            processStepsMap.put(stepId, process);
        }

        //add bddStepsInstances
        bddStepsInstances.addAll(process.getBddStepsInstances());

    }

    public List<Object> getStepsInstances() {
        List<Object> stepInstancesList = new ArrayList<Object>(10);
        stepInstancesList.add(this);
        for (Process singleProcess : processGraph) {
            stepInstancesList.addAll(singleProcess.getStepInstanceList());
        }
        return stepInstancesList;
    }

    //find apropriate process and then lead to step.
    @Given("step \"$stepId\"")
    @Alias("Step \"$stepId\"")
    public void reachStep(final String stepId) throws StepVerificationException {
        if (processStepsMap.containsKey(stepId)) {
            Process currentProcess = processStepsMap.get(stepId);
            for (Step step : currentProcess.getTestSteps()) {
                if (step.getStepId().equals(stepId)) {
                    step.checkAccesibility();
                    break;
                } else {
                    step.checkAccesibility();
                    step.defaultAction(TestData);
                }
            }
        } else {
            throw new RuntimeException("Process " + getClass().getName() + " doesn't contains stepId: " + stepId);
        }
    }
}
