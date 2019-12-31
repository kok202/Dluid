package org.kok202.dluid.ai.network;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.kok202.dluid.domain.stream.NumericRecordSet;
import org.nd4j.linalg.dataset.DataSet;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class Model {
    private long inputLayerId;
    private boolean isTestModel;
    private MultiLayerNetwork totalMultiLayerNetwork;
    private List<MultiLayerNetwork> multiLayerNetworks;

    public void train(DataSet dataSet){
        // TODO
    }

    public NumericRecordSet test(NumericRecordSet featureDataSet){
        // TODO
        return null;
    }
}
