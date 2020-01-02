package org.kok202.dluid.ai.singleton.structure;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.deeplearning4j.nn.weights.WeightInit;
import org.kok202.dluid.ai.AIConstant;
import org.kok202.dluid.ai.entity.enumerator.Optimizer;
import org.kok202.dluid.ai.entity.enumerator.WeightInitilaizer;
import org.kok202.dluid.domain.util.RandomUtil;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.IUpdater;
import org.nd4j.linalg.lossfunctions.LossFunctions;

@Data
public class ModelParameter {
    @Setter(AccessLevel.NONE)
    private long seed;
    private Optimizer optimizer;
    private WeightInitilaizer weightInitializer;
    private LossFunctions.LossFunction lossFunction;
    private double learningRate;
    private double learningMomentum;
    private int batchSize;
    private int epoch;
    private int listeningPeriod;

    public ModelParameter() {
        optimizer = Optimizer.SGD;
        weightInitializer = WeightInitilaizer.ONES;
        lossFunction = LossFunctions.LossFunction.MSE;
        batchSize = AIConstant.DEFAULT_BATCH_SIZE;
        learningRate = AIConstant.DEFAULT_LEARNING_RATE;
        epoch = AIConstant.DEFAULT_EPOCH_SIZE;
        listeningPeriod = AIConstant.DEFAULT_LISTENING_PERIOD;
        seed = RandomUtil.getLong();
        Nd4j.getRandom().setSeed(seed);
    }

    public IUpdater getIUpdater(){
        return optimizer.getIUpdater(learningRate);
    }

    public WeightInit getWeightInit(){
        return weightInitializer.getWeightInit();
    }
}
