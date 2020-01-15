package org.kok202.dluid.ai.entity;

import lombok.Data;
import org.deeplearning4j.nn.conf.layers.PoolingType;
import org.kok202.dluid.ai.entity.enumerator.ActivationWrapper;
import org.kok202.dluid.ai.entity.enumerator.LayerType;
import org.kok202.dluid.ai.entity.enumerator.WeightInitializer;
import org.nd4j.linalg.lossfunctions.LossFunctions.LossFunction;

@Data
public class LayerProperties {
    // common properties
    // In usual case dimension is not changed. But when you use reshape or switch layer, it can be changed.
    private int[] inputSize;
    private int[] outputSize;
    private int inputDimension;
    private int outputDimension;
    private WeightInitializer weightInitializer;
    private ActivationWrapper activationFunction;
    private double dropout;

    // for convolution type
    private int[] kernelSize;
    private int[] strideSize;
    private int[] paddingSize;

    // for output type
    private LossFunction lossFunction;

    // for pooling layer
    private PoolingType poolingType;

    private boolean channelExist;

    public int getInputVolume(){
        return Math.max(inputSize[0] * inputSize[1] * inputSize[2], 1);
    }

    public int getOutputVolume(){
        return Math.max(outputSize[0] * outputSize[1] * outputSize[2], 1);
    }

    public void setInputSize(int inputSize) {
        this.inputSize = new int[]{inputSize,1,1};
    }

    public void setInputSize(int inputSizeX, int inputSizeY) {
        this.inputSize = new int[]{inputSizeX,inputSizeY,1};
    }

    public void setInputSize(int inputSizeX, int inputSizeY, int inputSizeZ) {
        this.inputSize = new int[]{inputSizeX,inputSizeY,inputSizeZ};
    }

    public void setOutputSize(int outputSize) {
        this.outputSize = new int[]{outputSize,1,1};
    }

    public void setOutputSize(int outputSizeX, int outputSizeY) {
        this.outputSize = new int[]{outputSizeX,outputSizeY,1};
    }

    public void setOutputSize(int outputSizeX, int outputSizeY, int outputSizeZ) {
        this.outputSize = new int[]{outputSizeX,outputSizeY,outputSizeZ};
    }

    LayerProperties(LayerType layerType) {
        inputSize = new int[]{10, 1, 1};
        outputSize = new int[]{10, 1, 1};
        weightInitializer = WeightInitializer.FOLLOW_GLOBAL_SETTING;
        activationFunction = ActivationWrapper.IDENTITY;
        dropout = 0;
        lossFunction = LossFunction.MSE;
        poolingType = PoolingType.MAX;

        switch (layerType){
            case DENSE_LAYER:
            case INPUT_LAYER:
            case OUTPUT_LAYER:
            case RESHAPE_LAYER:
            case SWITCH_LAYER:
                inputDimension = 1;
                outputDimension = 1;
                channelExist = false;
                break;
            case PIPE_LAYER:
                inputDimension = 2;
                outputDimension = 2;
                channelExist = false;
                break;
            case CONVOLUTION_1D_LAYER:
            case POOLING_1D:
                kernelSize = new int[]{1};
                strideSize = new int[]{1};
                paddingSize = new int[]{0};
                inputDimension = 2;
                outputDimension = 2;
                channelExist = true;
                break;
            case CONVOLUTION_2D_LAYER:
            case DECONVOLUTION_2D_LAYER:
            case POOLING_2D:
                kernelSize = new int[]{1, 1};
                strideSize = new int[]{1, 1};
                paddingSize = new int[]{0, 0};
                inputDimension = 3;
                outputDimension = 3;
                channelExist = true;
                break;
            case MERGE_LAYER:
                inputSize = new int[]{1, 1, 1};
                outputSize = new int[]{1, 1, 1};
                channelExist = false;
                break;
        }
    }
}
