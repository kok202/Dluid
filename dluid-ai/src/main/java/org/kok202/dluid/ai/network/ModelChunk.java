package org.kok202.dluid.ai.network;

import lombok.Getter;
import org.deeplearning4j.nn.graph.ComputationGraph;

@Getter
public class ModelChunk {
    private long inputLayerId;
    private ComputationGraph computationGraph;

    public ModelChunk(long inputLayerId, ComputationGraph computationGraph) {
        this.inputLayerId = inputLayerId;
        this.computationGraph = computationGraph;
    }

}
