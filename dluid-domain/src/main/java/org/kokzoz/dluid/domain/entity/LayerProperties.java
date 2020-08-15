package org.kokzoz.dluid.domain.entity;

import lombok.Data;
import org.kokzoz.dluid.domain.entity.enumerator.*;

import java.util.Arrays;

@Data
public class LayerProperties {
    // common properties
    // In usual case dimension is not changed. But when you use reshape or switch layer, it can be changed.
    private Dimension input;
    private Dimension output;
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
                input = Dimension.builder().x(10).build();
                output = Dimension.builder().x(10).build();
                break;
            case BASE_RECURRENT_LAYER:
            case RNN_OUTPUT_LAYER:
            case LSTM:
            case PIPE_LAYER:
                input = Dimension.builder().x(10).y(1).build();
                output = Dimension.builder().x(10).y(1).build();
                break;
            case CONVOLUTION_1D_LAYER:
            case POOLING_1D:
                input = Dimension.builder().x(10).channel(1).build();
                output = Dimension.builder().x(10).channel(1).build();
                kernelSize = new int[]{1};
                strideSize = new int[]{1};
                paddingSize = new int[]{0};
                break;
            case CONVOLUTION_2D_LAYER:
            case DECONVOLUTION_2D_LAYER:
            case POOLING_2D:
                input = Dimension.builder().x(10).y(1).channel(1).build();
                output = Dimension.builder().x(10).y(1).channel(1).build();
                kernelSize = new int[]{1, 1};
                strideSize = new int[]{1, 1};
                paddingSize = new int[]{0, 0};
                break;
            case MERGE_LAYER:
                input = Dimension.builder().x(1).y(1).z(1).build();
                output = Dimension.builder().x(1).y(1).z(1).build();
                break;
        }
    }
}
