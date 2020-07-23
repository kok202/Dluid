package org.kok202.dluid.ai.util;

import org.kok202.dluid.domain.entity.enumerator.Optimizer;
import org.nd4j.linalg.learning.config.*;

public class OptimizerUtil {
    public static IUpdater getIUpdater(Optimizer optimizer, double learningRate){
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
