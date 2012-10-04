package eu.swiec.bearballin.runtime.graphs;

import eu.swiec.bearballin.model.Graph;
import eu.swiec.bearballin.runtime.processes.SoldierProcess;

/**
 * Created by IntelliJ IDEA.
 * User: uhc
 * Date: 23.09.12
 * Time: 13:34
 * To change this template use File | Settings | File Templates.
 */
public class SoldierGraph extends Graph {
    public SoldierGraph() {
        addProcess(new SoldierProcess());
    }
}
