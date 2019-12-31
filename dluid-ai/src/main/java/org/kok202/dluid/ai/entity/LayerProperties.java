package org.kok202.dluid.ai.entity;

import lombok.Data;
import org.kok202.dluid.ai.entity.enumerator.ActivationWrapper;
import org.kok202.dluid.ai.entity.enumerator.LayerType;
import org.kok202.dluid.ai.entity.enumerator.WeightInitilaizer;
import org.nd4j.linalg.lossfunctions.LossFunctions.LossFunction;

@Data
public class LayerProperties {
    // common properties
    private int[] inputSize;
    private int[] outputSize;
    private WeightInitilaizer weightInit;
    private ActivationWrapper activationFunction;
    private double dropout;

    // for convolution type
    private int[] kernelSize;
    private int[] strideSize;
    private int[] paddingSize;

    // for output type
    private LossFunction lossFunction;

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
        weightInit = WeightInitilaizer.ONES;
        activationFunction = ActivationWrapper.IDENTITY;
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
            case MERGE_LAYER:
                inputSize = new int[]{1, 1};
                outputSize = new int[]{1, 1};
                break;
            case OUTPUT_LAYER:
                lossFunction = LossFunction.MSE;
                break;
        }
    }
}
