package org.kok202.dluid.ai.singleton.structure;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.kok202.dluid.ai.AIConstant;
import org.kok202.dluid.ai.entity.enumerator.Optimizer;
import org.kok202.dluid.ai.entity.enumerator.WeightInitWrapper;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.util.HashMap;
import java.util.Map;

@Data
public class TrainProperty {
    private Optimizer optimizer;
    private WeightInitWrapper weightInit;
    private LossFunctions.LossFunction lossFunction;
    private double learningRate;
    private double learningMomentum;
    private int batchSize;
    private int totalRecordSize;
    private int epoch;

    @Setter(AccessLevel.PRIVATE)
    private Map<Long, DataSetManager> dataSetManagerMap;

    public TrainProperty() {
        optimizer = Optimizer.SGD;
        weightInit = WeightInitWrapper.ONES;
        lossFunction = LossFunctions.LossFunction.MSE;
        batchSize = AIConstant.DEFAULT_BATCH_SIZE;
        totalRecordSize = AIConstant.DEFAULT_RECORD_SIZE;
        learningRate = AIConstant.DEFAULT_LEARNING_RATE;
        epoch = AIConstant.DEFAULT_EPOCH_SIZE;
        dataSetManagerMap = new HashMap<>();
    }

    public DataSetManager getDataSetManager(long inputLayerId) {
        DataSetManager dataSetManager = dataSetManagerMap.get(inputLayerId);
        if(dataSetManager == null){
            DataSetManager newDataSetManager = new DataSetManager();
            dataSetManagerMap.put(inputLayerId, newDataSetManager);
            return newDataSetManager;
        }
        return dataSetManager;
    }

    public DataSetManager createDataSetManager(long inputLayerId) {
        return getDataSetManager(inputLayerId);
    }
}
