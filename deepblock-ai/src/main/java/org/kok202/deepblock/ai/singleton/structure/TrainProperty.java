package org.kok202.deepblock.ai.singleton.structure;

import lombok.Data;
import org.deeplearning4j.nn.weights.WeightInit;
import org.kok202.deepblock.ai.entity.enumerator.Optimizer;
import org.nd4j.linalg.lossfunctions.LossFunctions;

@Data
public class TrainProperty {
    private Optimizer optimizer = Optimizer.SGD;
    private WeightInit weightInit = WeightInit.ONES;
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
