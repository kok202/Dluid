package org.kok202.dluid.ai.util;

import org.kok202.dluid.domain.entity.enumerator.LossFunctionWrapper;
import org.nd4j.linalg.lossfunctions.LossFunctions;

public class LossFunctionUtil {
    public static LossFunctions.LossFunction get(LossFunctionWrapper lossFunctionWrapper){
        switch (lossFunctionWrapper){
            case MSE:
                return LossFunctions.LossFunction.MSE;
            case L1:
                return LossFunctions.LossFunction.L1;
            case XENT:
                return LossFunctions.LossFunction.XENT;
            case MCXENT:
                return LossFunctions.LossFunction.MCXENT;
            case SPARSE_MCXENT:
                return LossFunctions.LossFunction.SPARSE_MCXENT;
            case SQUARED_LOSS:
                return LossFunctions.LossFunction.SQUARED_LOSS;
            case RECONSTRUCTION_CROSSENTROPY:
                return LossFunctions.LossFunction.RECONSTRUCTION_CROSSENTROPY;
            case NEGATIVELOGLIKELIHOOD:
                return LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD;
            case COSINE_PROXIMITY:
                return LossFunctions.LossFunction.COSINE_PROXIMITY;
            case HINGE:
                return LossFunctions.LossFunction.HINGE;
            case SQUARED_HINGE:
                return LossFunctions.LossFunction.SQUARED_HINGE;
            case KL_DIVERGENCE:
                return LossFunctions.LossFunction.KL_DIVERGENCE;
            case MEAN_ABSOLUTE_ERROR:
                return LossFunctions.LossFunction.MEAN_ABSOLUTE_ERROR;
            case L2:
                return LossFunctions.LossFunction.L2;
            case MEAN_ABSOLUTE_PERCENTAGE_ERROR:
                return LossFunctions.LossFunction.MEAN_ABSOLUTE_PERCENTAGE_ERROR;
            case MEAN_SQUARED_LOGARITHMIC_ERROR:
                return LossFunctions.LossFunction.MEAN_SQUARED_LOGARITHMIC_ERROR;
            case POISSON:
                return LossFunctions.LossFunction.POISSON;
            case WASSERSTEIN:
                return LossFunctions.LossFunction.WASSERSTEIN;
            default:
                return LossFunctions.LossFunction.MSE;
        }
    }
}
