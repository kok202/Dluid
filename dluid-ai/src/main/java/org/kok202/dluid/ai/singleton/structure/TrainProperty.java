package org.kok202.dluid.ai.singleton.structure;

import lombok.Data;
import org.kok202.dluid.ai.entity.enumerator.Optimizer;
import org.kok202.dluid.ai.entity.enumerator.WeightInitWrapper;
import org.nd4j.linalg.lossfunctions.LossFunctions;

@Data
public class TrainProperty {
    private Optimizer optimizer = Optimizer.SGD;
    private WeightInitWrapper weightInit = WeightInitWrapper.ONES;
    private LossFunctions.LossFunction lossFunction = LossFunctions.LossFunction.MSE;
    private double learningRate;
    private double learningMomentum;
    private int batchSize;
    private int totalRecordSize;
    private int epoch;

    private DataSetManager dataSetManager;

    public TrainProperty() {
        batchSize = -1;
        totalRecordSize = -1;
        learningRate = 0.005;
        epoch = 5;
        dataSetManager = new DataSetManager();
    }
}
