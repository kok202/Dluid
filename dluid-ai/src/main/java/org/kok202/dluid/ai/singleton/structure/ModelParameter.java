package org.kok202.dluid.ai.singleton.structure;

import lombok.Data;
import org.kok202.dluid.ai.AIConstant;
import org.kok202.dluid.ai.entity.enumerator.Optimizer;
import org.kok202.dluid.ai.entity.enumerator.WeightInitWrapper;
import org.nd4j.linalg.lossfunctions.LossFunctions;

@Data
public class ModelParameter {
    private Optimizer optimizer;
    private WeightInitWrapper weightInit;
    private LossFunctions.LossFunction lossFunction;
    private double learningRate;
    private double learningMomentum;
    private int batchSize;
    private int totalRecordSize;
    private int epoch;

    public ModelParameter() {
        optimizer = Optimizer.SGD;
        weightInit = WeightInitWrapper.ONES;
        lossFunction = LossFunctions.LossFunction.MSE;
        batchSize = AIConstant.DEFAULT_BATCH_SIZE;
        totalRecordSize = AIConstant.DEFAULT_RECORD_SIZE;
        learningRate = AIConstant.DEFAULT_LEARNING_RATE;
        epoch = AIConstant.DEFAULT_EPOCH_SIZE;
    }
}
