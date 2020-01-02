package org.kok202.dluid.ai.network;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.kok202.dluid.ai.singleton.AISingleton;
import org.kok202.dluid.ai.util.NumericRecordSetUtil;
import org.kok202.dluid.domain.exception.CanNotFindLayerException;
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

        // Propagate trained param to other layers of model which is duplicated
        AISingleton.getInstance()
                .getModelManager()
                .getModels()
                .parallelStream()
                .filter(model -> model.getInputLayerId() != inputLayerId)
                .forEach(model -> updateParams(model.getMultiLayerConfLayerMap()));
    }

    public NumericRecordSet test(NumericRecordSet featureDataSet, long targetResultLayerId){
        INDArray feature = NumericRecordSetUtil.convertAsINDArray(featureDataSet);
        List<INDArray> testResult = multiLayerNetwork.feedForward(feature);

        NeuralNetLayer neuralNetLayer = multiLayerConfLayerMap.get(targetResultLayerId);
        if(neuralNetLayer == null)
            throw new CanNotFindLayerException(targetResultLayerId);

        INDArray targetResultLayerOutput = testResult.get(neuralNetLayer.getSequence());
        // FIXME : Debug here!
        return NumericRecordSetUtil.convertAsNumericRecordSet(targetResultLayerOutput);
    }

    public double score(){
        return multiLayerNetwork.score();
    }

    private void updateParams(Map<Long, NeuralNetLayer> multiLayerMap){
        multiLayerMap.entrySet()
                .forEach(entry ->{
                    long layerId = entry.getKey();
                    NeuralNetLayer layer = entry.getValue();
                    NeuralNetLayer targetLayer = this.multiLayerConfLayerMap.get(layerId);
                    if(targetLayer != null){
                        targetLayer.getLayer().setParams(layer.getLayer().params());
                    }
                });
    }
}
