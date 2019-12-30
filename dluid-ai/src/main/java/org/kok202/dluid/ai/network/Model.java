package org.kok202.dluid.ai.network;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.kok202.dluid.domain.stream.NumericRecordSet;
import org.nd4j.linalg.dataset.api.MultiDataSet;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class Model {
    private long inputLayerId;
    private boolean isTestModel;
    private ComputationGraph totalComputationGraph;
    private List<ComputationGraph> computationGraphs;

    public void train(MultiDataSet multiDataSet){
        // TODO
    }

    public NumericRecordSet test(NumericRecordSet featureDataSet){
        // TODO
        return null;
    }
}
