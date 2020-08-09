package org.kokzoz.dluid.domain.entity;

import lombok.Data;
import org.kokzoz.dluid.domain.entity.enumerator.*;

import java.util.Arrays;

@Data
public class LayerProperties {
    // common properties
    // In usual case dimension is not changed. But when you use reshape or switch layer, it can be changed.
    private int[] inputSize;
    private int[] outputSize;
    private Dimension inputDimension;
    private Dimension outputDimension;
    private BiasInitializer biasInitializer;
    private WeightInitializer weightInitializer;
    private ActivationWrapper activationFunction;
    private double dropout;

    // for convolution type
    private int[] kernelSize;
    private int[] strideSize;
    private int[] paddingSize;

    // for output type
    private LossFunctionWrapper lossFunction;

    // for pooling layer
    private PoolingTypeWrapper poolingType;

    public int getInputVolume(){
        int product = 1;
        for(int input : inputSize)
            product *= input;
        return Math.max(product, 1);
    }

    public int getOutputVolume(){
        int product = 1;
        for(int output : outputSize)
            product *= output;
        return Math.max(product, 1);
    }

    public void setInputSize(int inputSize) {
        this.inputSize = new int[]{inputSize};
    }

    public void setInputSize(int inputSizeX, int inputSizeY) {
        this.inputSize = new int[]{inputSizeX,inputSizeY};
    }

    public void setInputSize(int inputSizeX, int inputSizeY, int inputSizeZ) {
        this.inputSize = new int[]{inputSizeX,inputSizeY,inputSizeZ};
    }

    public void setOutputSize(int outputSize) {
        this.outputSize = new int[]{outputSize};
    }

    public void setOutputSize(int outputSizeX, int outputSizeY) {
        this.outputSize = new int[]{outputSizeX,outputSizeY};
    }

    public void setOutputSize(int outputSizeX, int outputSizeY, int outputSizeZ) {
        this.outputSize = new int[]{outputSizeX,outputSizeY,outputSizeZ};
    }

    public int getInputSizeX(){
        return inputSize[0];
    }

    public int getInputSizeY(){
        return (inputDimension.getDimension() >= 2)? inputSize[1] : 1;
    }

    public int getInputSizeZ(){
        return (inputDimension.getDimension() >= 3)? inputSize[2] : 1;
    }

    public int getOutputSizeX(){
        return outputSize[0];
    }

    public int getOutputSizeY(){
        return (outputDimension.getDimension() >= 2)? outputSize[1] : 1;
    }

    public int getOutputSizeZ(){
        return (outputDimension.getDimension() >= 3)? outputSize[2] : 1;
    }

    public void setInputDimension(Dimension inputDimension) {
        this.inputDimension = inputDimension;
        while(inputDimension.getDimension() > inputSize.length) {
            inputSize = append(inputSize, 1);
        }
    }

    public void setOutputDimension(Dimension outputDimension) {
        this.outputDimension = outputDimension;
        while(outputDimension.getDimension() > outputSize.length) {
            outputSize = append(outputSize, 1);
        }
    }

    LayerProperties(LayerType layerType) {
        weightInitializer = WeightInitializer.FOLLOW_GLOBAL_SETTING;
        activationFunction = ActivationWrapper.IDENTITY;
        dropout = 0;
        lossFunction = (layerType != LayerType.RNN_OUTPUT_LAYER)? LossFunctionWrapper.MSE : LossFunctionWrapper.MCXENT;
        poolingType = PoolingTypeWrapper.MAX;

        switch (layerType){
            case DENSE_LAYER:
            case INPUT_LAYER:
            case OUTPUT_LAYER:
            case RESHAPE_LAYER:
            case SWITCH_LAYER:
            case BATCH_NORMALIZATION:
                inputSize = new int[]{10};
                outputSize = new int[]{10};
                inputDimension = Dimension.ONE_DIMENSION;
                outputDimension = Dimension.ONE_DIMENSION;
                break;
            case BASE_RECURRENT_LAYER:
            case RNN_OUTPUT_LAYER:
            case LSTM:
            case PIPE_LAYER:
                inputSize = new int[]{10, 1};
                outputSize = new int[]{10, 1};
                inputDimension = Dimension.TWO_DIMENSION;
                outputDimension = Dimension.TWO_DIMENSION;
                break;
            case CONVOLUTION_1D_LAYER:
            case POOLING_1D:
                inputSize = new int[]{10, 1};
                outputSize = new int[]{10, 1};
                kernelSize = new int[]{1};
                strideSize = new int[]{1};
                paddingSize = new int[]{0};
                inputDimension = Dimension.TWO_DIMENSION_WITH_CHANNEL;
                outputDimension = Dimension.TWO_DIMENSION_WITH_CHANNEL;
                break;
            case CONVOLUTION_2D_LAYER:
            case DECONVOLUTION_2D_LAYER:
            case POOLING_2D:
                inputSize = new int[]{10, 1, 1};
                outputSize = new int[]{10, 1, 1};
                kernelSize = new int[]{1, 1};
                strideSize = new int[]{1, 1};
                paddingSize = new int[]{0, 0};
                inputDimension = Dimension.THREE_DIMENSION_WITH_CHANNEL;
                outputDimension = Dimension.THREE_DIMENSION_WITH_CHANNEL;
                break;
            case MERGE_LAYER:
                inputSize = new int[]{1, 1, 1};
                outputSize = new int[]{1, 1, 1};
                break;
        }
    }

    private int[] append(int[] array, int element) {
        array  = Arrays.copyOf(array, array.length + 1);
        array[array.length - 1] = element;
        return array;
    }
}
