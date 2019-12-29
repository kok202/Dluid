package org.kok202.dluid.ai.singleton.structure;

import lombok.Builder;
import lombok.Getter;
import org.deeplearning4j.nn.graph.ComputationGraph;

@Getter
public class Model {
    private long inputLayerId;
    private boolean isTestModel;
    private ComputationGraph computationGraph;

    @Builder
    public Model(boolean isTestModel, long inputLayerId, ComputationGraph computationGraph) {
        this.isTestModel = isTestModel;
        this.inputLayerId = inputLayerId;
        this.computationGraph = computationGraph;
    }

}
