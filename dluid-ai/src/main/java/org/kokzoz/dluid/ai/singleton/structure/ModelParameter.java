package org.kokzoz.dluid.ai.singleton.structure;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.kokzoz.dluid.ai.AIConstant;
import org.kokzoz.dluid.domain.entity.enumerator.BiasInitializer;
import org.kokzoz.dluid.domain.entity.enumerator.Optimizer;
import org.kokzoz.dluid.domain.entity.enumerator.WeightInitializer;
import org.kokzoz.dluid.domain.util.RandomUtil;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.*;
import org.nd4j.linalg.lossfunctions.LossFunctions;

@Data
public class ModelParameter {
    @Setter(AccessLevel.NONE)
    private long seed;
    private Optimizer optimizer;
    private BiasInitializer biasInitializer;
    private WeightInitializer weightInitializer;
    private LossFunctions.LossFunction lossFunction;
    private double learningRate;
    private double learningMomentum;
    private int batchSize;
    private int epoch;
    private int listeningPeriod;

    public ModelParameter() {
        optimizer = Optimizer.SGD;
        biasInitializer = BiasInitializer.ZERO;
        weightInitializer = WeightInitializer.ONES;
        lossFunction = LossFunctions.LossFunction.MSE;
        batchSize = AIConstant.DEFAULT_BATCH_SIZE;
        learningRate = AIConstant.DEFAULT_LEARNING_RATE;
        epoch = AIConstant.DEFAULT_EPOCH_SIZE;
        listeningPeriod = AIConstant.DEFAULT_LISTENING_PERIOD;
        seed = RandomUtil.getLong();
        Nd4j.getRandom().setSeed(seed);
    }

    public IUpdater getIUpdater(){
        switch (optimizer){
            case ADAM:
                return new Adam(learningRate);
            case ADA_DELTA:
                return new AdaDelta();
            case ADA_GRAD:
                return new AdaGrad(learningRate);
            case ADA_MAX:
                return new AdaMax(learningRate);
            case AMS_GRAD:
                return new AMSGrad(learningRate);
            case NADAM:
                return new Nadam(learningRate);
            case NESTEROVS:
                return new Nesterovs(learningRate);
            case NOOP:
                return new NoOp();
            case RMS_PROP:
                return new RmsProp(learningRate);
            case SGD:
            default:
                return new Sgd(learningRate);
        }
    }
}
