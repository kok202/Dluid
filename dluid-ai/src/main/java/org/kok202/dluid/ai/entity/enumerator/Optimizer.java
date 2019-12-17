package org.kok202.dluid.ai.entity.enumerator;

import org.nd4j.linalg.learning.config.*;

public enum Optimizer {
    ADAM,
    ADA_DELTA,
    ADA_GRAD,
    ADA_MAX,
    AMS_GRAD,
    NADAM,
    NESTEROVS,
    NOOP,
    RMS_PROP,
    SGD;

    public IUpdater getIUpdater(double learningRate){
        switch (this){
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
