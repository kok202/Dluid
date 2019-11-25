package org.kok202.dluid.ai.util;

import org.nd4j.linalg.lossfunctions.LossFunctions.LossFunction;

import java.util.HashMap;

public class LossFunctionUtil {
    private static HashMap<String, LossFunction> lossFunctionHashMap;
    private static HashMap<String, LossFunction> getLossFunctionHashMap(){
        if(lossFunctionHashMap == null){
            lossFunctionHashMap = new HashMap<>();
            lossFunctionHashMap.put("mse", LossFunction.MSE);
            lossFunctionHashMap.put("l1", LossFunction.L1);
            lossFunctionHashMap.put("l2", LossFunction.L2);
            lossFunctionHashMap.put("squaredLoss", LossFunction.SQUARED_LOSS);
            lossFunctionHashMap.put("reconstructionCrossEntropy", LossFunction.RECONSTRUCTION_CROSSENTROPY);
            lossFunctionHashMap.put("klDivergence", LossFunction.KL_DIVERGENCE);

            lossFunctionHashMap.put("xent", LossFunction.XENT);
            lossFunctionHashMap.put("mcxent", LossFunction.MCXENT);
            lossFunctionHashMap.put("negativeLikelihood", LossFunction.NEGATIVELOGLIKELIHOOD);
            lossFunctionHashMap.put("cosineProximity", LossFunction.COSINE_PROXIMITY);
            lossFunctionHashMap.put("hinge", LossFunction.HINGE);
            lossFunctionHashMap.put("squaredHinge", LossFunction.SQUARED_HINGE);
            lossFunctionHashMap.put("meanAbsoluteError", LossFunction.MEAN_ABSOLUTE_ERROR);
            lossFunctionHashMap.put("meanAbsolutePercentageError", LossFunction.MEAN_ABSOLUTE_PERCENTAGE_ERROR);
            lossFunctionHashMap.put("meanSquaredLogarithmicError", LossFunction.MEAN_SQUARED_LOGARITHMIC_ERROR);
            lossFunctionHashMap.put("poisson", LossFunction.POISSON);
            lossFunctionHashMap.put("wasserstein", LossFunction.WASSERSTEIN);
        }
        return lossFunctionHashMap;
    }

    public static LossFunction convertToLossFunction(String lossFunctionString){
        return getLossFunctionHashMap().get(lossFunctionString);
    }
}
