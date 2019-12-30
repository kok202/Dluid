package org.kok202.dluid.ai.singleton.structure;

import lombok.Data;
import org.deeplearning4j.nn.weights.WeightInit;
import org.kok202.dluid.ai.AIConstant;
import org.kok202.dluid.ai.entity.enumerator.Optimizer;
import org.kok202.dluid.ai.entity.enumerator.WeightInitilaizer;
import org.nd4j.linalg.learning.config.IUpdater;
import org.nd4j.linalg.lossfunctions.LossFunctions;

@Data
public class ModelParameter {
    private Optimizer optimizer;
    private WeightInitilaizer weightInitializer;
    private LossFunctions.LossFunction lossFunction;
    private double learningRate;
    private double learningMomentum;
    private int batchSize;
    private int epoch;

    public ModelParameter() {
        optimizer = Optimizer.SGD;
        weightInitializer = WeightInitilaizer.ONES;
        lossFunction = LossFunctions.LossFunction.MSE;
        batchSize = AIConstant.DEFAULT_BATCH_SIZE;
        learningRate = AIConstant.DEFAULT_LEARNING_RATE;
        epoch = AIConstant.DEFAULT_EPOCH_SIZE;
    }

    public IUpdater getIUpdater(){
        return optimizer.getIUpdater(learningRate);
    }

    public WeightInit getWeightInit(){
        return weightInitializer.getWeightInit();
    }
}
