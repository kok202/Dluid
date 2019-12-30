package org.kok202.dluid.ai.network;

import lombok.Data;
import org.deeplearning4j.nn.graph.ComputationGraph;

import java.util.ArrayList;
import java.util.List;

@Data
public class LinkedComputationGraph {
    private long inputLayerId;
    private boolean isTestModel;
    private List<ComputationGraph> computationGraphs;

    public LinkedComputationGraph() {
        computationGraphs = new ArrayList<>();
    }

    public LinkedComputationGraph deepCopy(){
        LinkedComputationGraph linkedComputationGraph = new LinkedComputationGraph();
        linkedComputationGraph.inputLayerId = inputLayerId;
        linkedComputationGraph.computationGraphs = new ArrayList<>(computationGraphs);
        return linkedComputationGraph;
    }
}
