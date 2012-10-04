package eu.swiec.bearballin.runtime.processes;

import eu.swiec.bearballin.runtime.steps.sikuli.soldier.AllCorrectVerification;
import eu.swiec.bearballin.runtime.steps.sikuli.soldier.AssignBodyLabelsStep;
import eu.swiec.bearballin.runtime.steps.sikuli.soldier.ClickMarkAnswersStep;
import eu.swiec.bearballin.runtime.steps.sikuli.soldier.OpenSoldierStep;

/**
 * Created by IntelliJ IDEA.
 * User: uhc
 * Date: 23.09.12
 * Time: 13:32
 * To change this template use File | Settings | File Templates.
 */
public class SoldierProcess extends eu.swiec.bearballin.model.Process {
    public SoldierProcess() {
        addStep(new OpenSoldierStep("Open Soldier in Browser Step"));
        addStep(new AssignBodyLabelsStep("Assign bodyparts labels"));
        addStep(new ClickMarkAnswersStep("Mark Steps Button Click"));
        addStep(new AllCorrectVerification("Verify Result Message Step"));
    }
}
