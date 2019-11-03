package org.kok202.deepblock.ai.util;

import org.kok202.deepblock.ai.entity.enumerator.Optimizer;
import org.nd4j.linalg.learning.config.*;

import java.util.HashMap;

public class OptimizerUtil {
    private static HashMap<String, Optimizer> optimizerHashMap;

    private static HashMap<String, Optimizer> getOptimizerHashMap(){
        if(optimizerHashMap == null){
            optimizerHashMap = new HashMap<>();
            optimizerHashMap.put("adam", Optimizer.ADAM);
            optimizerHashMap.put("adaDelta", Optimizer.ADA_DELTA);
            optimizerHashMap.put("adaGrad", Optimizer.ADA_GRAD);
            optimizerHashMap.put("adaMax", Optimizer.ADA_MAX);
            optimizerHashMap.put("amsGrad", Optimizer.AMS_GRAD);
            optimizerHashMap.put("nAdam", Optimizer.NADAM);
            optimizerHashMap.put("nesterovs", Optimizer.NESTEROVS);
            optimizerHashMap.put("noop", Optimizer.NOOP);
            optimizerHashMap.put("rmsProp", Optimizer.RMS_PROP);
            optimizerHashMap.put("sgd", Optimizer.SGD);
        }
        return optimizerHashMap;
    }

    public static Optimizer convertToOptimizer(String optimizerString){
        return getOptimizerHashMap().get(optimizerString);
    }

    private static IUpdater createIUpdater(Optimizer optimizer, double learningRate){
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

    public static IUpdater createIUpdaterFromString(Optimizer optimizer, double learningRate){
        return createIUpdater(optimizer, learningRate);
    }
}
