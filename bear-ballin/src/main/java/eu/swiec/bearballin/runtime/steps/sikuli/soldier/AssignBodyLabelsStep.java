package eu.swiec.bearballin.runtime.steps.sikuli.soldier;

import eu.swiec.bearballin.model.ITestData;
import eu.swiec.bearballin.model.exceptions.StepExecutionException;
import eu.swiec.bearballin.model.steps.SikuliStep;

import org.jbehave.core.annotations.When;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: uhc
 * Date: 23.09.12
 * Time: 13:45
 * To change this template use File | Settings | File Templates.
 */
public class AssignBodyLabelsStep extends SikuliStep {
    private class BodyLabel {
        public final String pictureFrom;
        public final String pictureTo;

        public BodyLabel(String pictrureDragFrom, String pictureDragTo) {
            this.pictureFrom = pictrureDragFrom;
            this.pictureTo = pictureDragTo;
        }
    }

    private final List<BodyLabel> bodyPartsList;

    public AssignBodyLabelsStep(String stepId) {
        this(stepId, null);
    }

    public AssignBodyLabelsStep(String stepId, ITestData testData) {
        super(stepId, testData);
        bodyPartsList = new ArrayList<BodyLabel>(8);
        bodyPartsList.add(new BodyLabel("LeftFootLabel.png", "LeftFootPoster.png"));
        bodyPartsList.add(new BodyLabel("RightFootLabel.png", "RightFootPoster.png"));
        bodyPartsList.add(new BodyLabel("LeftHandLabel.png", "LeftHandPoster.png"));
        bodyPartsList.add(new BodyLabel("RightHandLabel.png", "RightHandPoster.png"));
        bodyPartsList.add(new BodyLabel("StomachLabel.png", "StomachPoster.png"));
        bodyPartsList.add(new BodyLabel("HeadLabel.png", "HeadPoster.png"));
    }

    @Override
    public boolean checkAccesibility() {
        return driver.exists("DragDropMessage.png");
    }

    @When("I will assign $partNum body labels correctly")
    public void whenAssignBodyParts(String partNum) {
        if (checkAccesibility()) {
            defaultAction(null);
            Integer i = 0;
            for (BodyLabel label : bodyPartsList) {
                if (i.toString().equals(partNum)) break;
                i++;
                driver.tryDragDrop(label.pictureFrom, label.pictureTo);
            }
        }
    }

    @Override
    public String defaultAction(ITestData testDataCollector) throws StepExecutionException {
        driver.tryClick("DragDropMessage.png");
        return "";
    }
}
