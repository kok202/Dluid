package org.kok202.deepblock.ai.util;

import org.kok202.deepblock.ai.entity.enumerator.Optimizer;
import org.nd4j.linalg.learning.config.*;

import java.util.HashMap;

public class OptimizerUtil {
    private static HashMap<String, Optimizer> optimizerHashMap;
    private static HashMap<Optimizer, IUpdater> iUpdaterHashMap;

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

    private static HashMap<Optimizer, IUpdater> getIUpdaterHashMap(){
        if(iUpdaterHashMap == null){
            iUpdaterHashMap = new HashMap<>();
            iUpdaterHashMap.put(Optimizer.ADAM, new Adam());
            iUpdaterHashMap.put(Optimizer.ADA_DELTA, new AdaDelta());
            iUpdaterHashMap.put(Optimizer.ADA_GRAD, new AdaGrad());
            iUpdaterHashMap.put(Optimizer.ADA_MAX, new AdaMax());
            iUpdaterHashMap.put(Optimizer.AMS_GRAD, new AMSGrad());
            iUpdaterHashMap.put(Optimizer.NADAM, new Nadam());
            iUpdaterHashMap.put(Optimizer.NESTEROVS, new Nesterovs());
            iUpdaterHashMap.put(Optimizer.NOOP, new NoOp());
            iUpdaterHashMap.put(Optimizer.RMS_PROP, new RmsProp());
            iUpdaterHashMap.put(Optimizer.SGD, new Sgd());
        }
        return iUpdaterHashMap;
    }

    public static IUpdater createIUpdaterFromString(Optimizer optimizer, double learningRate){
        IUpdater iUpdater = getIUpdaterHashMap().get(optimizer);
        return (iUpdater != null)? iUpdater : new Sgd(learningRate);
    }
}
