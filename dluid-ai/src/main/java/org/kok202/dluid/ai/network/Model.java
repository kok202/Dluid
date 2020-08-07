package org.kok202.dluid.ai.network;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.deeplearning4j.nn.api.Layer;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.kok202.dluid.ai.singleton.AISingleton;
import org.kok202.dluid.ai.util.NumericRecordSetUtil;
import org.kok202.dluid.domain.stream.NumericRecordSet;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;

import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
public class Model {
    private String inputLayerId;
    private ComputationGraph computationGraph;
    private Map<String, Layer> computationGraphLayerMap;

    public void train(DataSet dataSet){
        computationGraph.fit(dataSet);

        // Propagate trained param to other layers of model which is duplicated
        AISingleton.getInstance()
                .getModelManager()
                .getModels()
                .parallelStream()
                .filter(model -> !model.getInputLayerId().equals(inputLayerId))
                .forEach(model -> updateParams(model.getComputationGraphLayerMap()));
    }

    public NumericRecordSet test(NumericRecordSet featureDataSet, String targetResultLayerId){
        INDArray feature = NumericRecordSetUtil.convertAsINDArray(featureDataSet);
        Map<String, INDArray> testResult = computationGraph.feedForward(feature, false);
        INDArray targetResultLayerOutput = testResult.get(targetResultLayerId);

        int featureDataSize = featureDataSet.getRecordsSize();
        return NumericRecordSetUtil.convertAsNumericRecordSet(targetResultLayerOutput.reshape(new int[]{featureDataSize, -1}));
    }

    public double score(){
        return computationGraph.score();
    }

    private void updateParams(Map<String, Layer> multiLayerMap){
        multiLayerMap.forEach((layerId, layer) -> {
            Layer targetLayer = this.computationGraphLayerMap.get(layerId);
            if (targetLayer != null) {
                targetLayer.setParams(layer.params());
            }
        });
    }
}
