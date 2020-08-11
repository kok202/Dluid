package org.kokzoz.dluid.ai.network.layer.applier;

import org.deeplearning4j.nn.conf.layers.Layer.Builder;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.kokzoz.dluid.domain.entity.LayerProperties;
import org.nd4j.linalg.lossfunctions.LossFunctions;

public class LossFunctionApplier extends AbstractApplier {

    @Override
    public boolean support(Builder builder, LayerProperties layerProperties){
        return builder instanceof OutputLayer.Builder && layerProperties.getLossFunction() != null;
    }

    @Override
    public void apply(Builder builder, LayerProperties layerProperties){
        OutputLayer.Builder outputBuilder = ((OutputLayer.Builder) builder);
        switch (layerProperties.getLossFunction()){
            case MSE:
                outputBuilder.setLossFn(LossFunctions.LossFunction.MSE.getILossFunction());
                break;
            case L1:
                outputBuilder.setLossFn(LossFunctions.LossFunction.L1.getILossFunction());
                break;
            case XENT:
                outputBuilder.setLossFn(LossFunctions.LossFunction.XENT.getILossFunction());
                break;
            case MCXENT:
                outputBuilder.setLossFn(LossFunctions.LossFunction.MCXENT.getILossFunction());
                break;
            case SPARSE_MCXENT:
                outputBuilder.setLossFn(LossFunctions.LossFunction.SPARSE_MCXENT.getILossFunction());
                break;
            case SQUARED_LOSS:
                outputBuilder.setLossFn(LossFunctions.LossFunction.SQUARED_LOSS.getILossFunction());
                break;
            case RECONSTRUCTION_CROSSENTROPY:
                outputBuilder.setLossFn(LossFunctions.LossFunction.RECONSTRUCTION_CROSSENTROPY.getILossFunction());
                break;
            case NEGATIVELOGLIKELIHOOD:
                outputBuilder.setLossFn(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD.getILossFunction());
                break;
            case COSINE_PROXIMITY:
                outputBuilder.setLossFn(LossFunctions.LossFunction.COSINE_PROXIMITY.getILossFunction());
                break;
            case HINGE:
                outputBuilder.setLossFn(LossFunctions.LossFunction.HINGE.getILossFunction());
                break;
            case SQUARED_HINGE:
                outputBuilder.setLossFn(LossFunctions.LossFunction.SQUARED_HINGE.getILossFunction());
                break;
            case KL_DIVERGENCE:
                outputBuilder.setLossFn(LossFunctions.LossFunction.KL_DIVERGENCE.getILossFunction());
                break;
            case MEAN_ABSOLUTE_ERROR:
                outputBuilder.setLossFn(LossFunctions.LossFunction.MEAN_ABSOLUTE_ERROR.getILossFunction());
                break;
            case L2:
                outputBuilder.setLossFn(LossFunctions.LossFunction.MEAN_ABSOLUTE_ERROR.getILossFunction());
                break;
            case MEAN_ABSOLUTE_PERCENTAGE_ERROR:
                outputBuilder.setLossFn(LossFunctions.LossFunction.MEAN_ABSOLUTE_PERCENTAGE_ERROR.getILossFunction());
                break;
            case MEAN_SQUARED_LOGARITHMIC_ERROR:
                outputBuilder.setLossFn(LossFunctions.LossFunction.MEAN_SQUARED_LOGARITHMIC_ERROR.getILossFunction());
                break;
            case POISSON:
                outputBuilder.setLossFn(LossFunctions.LossFunction.POISSON.getILossFunction());
                break;
            case WASSERSTEIN:
                outputBuilder.setLossFn(LossFunctions.LossFunction.WASSERSTEIN.getILossFunction());
                break;
            default:
                outputBuilder.setLossFn(LossFunctions.LossFunction.MSE.getILossFunction());
                break;
        }
    }
}
