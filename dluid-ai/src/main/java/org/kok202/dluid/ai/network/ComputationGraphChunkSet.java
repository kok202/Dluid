package org.kok202.dluid.ai.network;

import lombok.Builder;
import lombok.Getter;
import org.deeplearning4j.nn.graph.ComputationGraph;

import java.util.List;
import java.util.Map;

@Getter
public class ComputationGraphChunkSet {
    private long outputLayerId;
    private long testInputLayerId;
    private Map<Long, ComputationGraph> managersMap;
    private Map<Long, List<Long>> linkageFromTo;

    @Builder
    public ComputationGraphChunkSet(long outputLayerId, long testInputLayerId, Map<Long, ComputationGraph> managersMap, Map<Long, List<Long>> linkageFromTo) {
        this.outputLayerId = outputLayerId;
        this.testInputLayerId = testInputLayerId;
        this.managersMap = managersMap;
        this.linkageFromTo = linkageFromTo;
    }
}
