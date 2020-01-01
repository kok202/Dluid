package org.kok202.dluid.ai.network;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.deeplearning4j.nn.api.Layer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.kok202.dluid.ai.util.DataSetConverter;
import org.kok202.dluid.domain.stream.NumericRecordSet;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;

import java.util.List;
import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
public class Model {
    private long inputLayerId;
    private MultiLayerNetwork multiLayerNetwork;
    private Map<Long, NeuralNetLayer> multiLayerConfLayerMap;

    public void train(DataSet dataSet){
        multiLayerNetwork.fit(dataSet);
        // TODO : 다른 model 에 subset 이겹치는 경우 전파
    }

    public NumericRecordSet test(NumericRecordSet featureDataSet){
        INDArray feature = DataSetConverter.convertAsINDArray(featureDataSet);
        List<INDArray> testResult = multiLayerNetwork.feedForward(feature);
        INDArray lastLayerResult = testResult.get(testResult.size() - 1);
        INDArray result = lastLayerResult.getRow(0);
        // TODO
        return null;
    }

    private void updateParams(Map<Long, Layer> multiLayerMap){
        multiLayerMap.entrySet()
                .forEach(entry ->{
                    long layerId = entry.getKey();
                    Layer layer = entry.getValue();
                    NeuralNetLayer targetLayer = this.multiLayerConfLayerMap.get(layerId);
                    if(targetLayer != null){
                        targetLayer.getLayer().setParams(layer.params());
                    }
                });
    }
}
