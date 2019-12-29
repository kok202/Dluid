package org.kok202.dluid.ai.singleton.structure;

import lombok.Getter;
import org.deeplearning4j.nn.graph.ComputationGraph;

@Getter
public class Model {
    private long inputLayerId;
    private ComputationGraph computationGraph;

    public Model(long inputLayerId, ComputationGraph computationGraph) {
        this.inputLayerId = inputLayerId;
        this.computationGraph = computationGraph;
    }

}
