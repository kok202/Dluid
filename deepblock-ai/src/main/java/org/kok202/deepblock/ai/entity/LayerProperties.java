package org.kok202.deepblock.ai.entity;

import lombok.Data;
import org.deeplearning4j.nn.weights.WeightInit;
import org.kok202.deepblock.ai.entity.enumerator.LayerType;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.lossfunctions.LossFunctions.LossFunction;

@Data
public class LayerProperties {
    // common properties
    protected int[] inputSize;
    protected int[] outputSize;
    protected WeightInit weightInit;
    protected Activation activationFunction;
    protected double dropout;

    // for convolution type
    protected int[] kernelSize;
    protected int[] strideSize;
    protected int[] paddingSize;

    // for output type
    protected LossFunction lossFunction;

    public void setInputSize(int inputSize) {
        this.inputSize = new int[]{inputSize,1};
    }

    public void setInputSize(int inputSizeX, int inputSizeY) {
        this.inputSize = new int[]{inputSizeX,inputSizeY};
    }

    public void setOutputSize(int outputSize) {
        this.outputSize = new int[]{outputSize,1};
    }

    public void setOutputSize(int outputSizeX, int outputSizeY) {
        this.outputSize = new int[]{outputSizeX,outputSizeY};
    }

    LayerProperties(LayerType layerType) {
        inputSize = new int[]{10, 1};
        outputSize = new int[]{10, 1};
        weightInit = WeightInit.ONES;
        activationFunction = Activation.IDENTITY;
        dropout = 0;

        switch (layerType){
            case CONVOLUTION_1D_LAYER:
                kernelSize = new int[]{1};
                strideSize = new int[]{1};
                paddingSize = new int[]{0};
                break;
            case CONVOLUTION_2D_LAYER:
            case DECONVOLUTION_2D_LAYER:
                kernelSize = new int[]{1,1};
                strideSize = new int[]{1,1};
                paddingSize = new int[]{0,0};
                break;
            case OUTPUT_LAYER:
                lossFunction = LossFunction.MSE;
                break;
        }
    }
}
