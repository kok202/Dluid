package org.kok202.deepblock.ai.util;

import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.ConvolutionLayer;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.FeedForwardLayer.Builder;
import org.deeplearning4j.nn.conf.layers.LSTM;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.ai.entity.enumerator.LayerType;
import org.kok202.deepblock.domain.exception.EmptyNodeException;
import org.kok202.deepblock.domain.structure.TreeNode;

public class LayerBuildingUtil {
    public static NeuralNetConfiguration.ListBuilder implementsLayers(NeuralNetConfiguration.ListBuilder neuralNetLayerBuilder, TreeNode<Layer> node){
        // TODO : 레이어가 모델에 따라서 동적으로 생성될 수 있도록
        neuralNetLayerBuilder = addLayer(neuralNetLayerBuilder, node.getData());

        // TODO : ex. splitter material, dummy material
        if(node.isSingleChild())
            implementsLayers(neuralNetLayerBuilder, node.getFirstChild());
        return neuralNetLayerBuilder;
    }

    private static NeuralNetConfiguration.ListBuilder addLayer(NeuralNetConfiguration.ListBuilder neuralNetLayerBuilder, Layer layer){
        if(layer == null)
            throw new EmptyNodeException();

        // Input mono is just indicating main model. it just empty shell.
        if(layer.getType() == LayerType.INPUT_LAYER)
            return neuralNetLayerBuilder;

        Builder layerBuilder = BlockLayerUtil.getLayerBuilder(layer);
        setCommonProperties(layer, layerBuilder);
        setAddOnProperties(layer, layerBuilder);
        return neuralNetLayerBuilder.layer(layerBuilder.build());
    }

    private static Builder setCommonProperties(Layer layer, Builder layerBuilder){
        if(layer.getProperties().getInputSize() != null)
            layerBuilder.nIn(layer.getProperties().getInputSize()[0] * layer.getProperties().getInputSize()[1]);
        if(layer.getProperties().getOutputSize() != null)
            layerBuilder.nOut(layer.getProperties().getOutputSize()[0] * layer.getProperties().getOutputSize()[1]);
        if(layer.getProperties().getWeightInit() != null)
            layerBuilder.weightInit(layer.getProperties().getWeightInit());
        if(layer.getProperties().getActivationFunction() != null)
            layerBuilder.activation(layer.getProperties().getActivationFunction());
        if(layer.getProperties().getDropout() != 0)
            layerBuilder.dropOut(layer.getProperties().getDropout());
        return layerBuilder;
    }

    private static Builder setAddOnProperties(Layer layer, Builder layerBuilder){
        switch(layer.getType()) {
            case DENSE_LAYER:
                DenseLayer.Builder denseLayerBuilder = (DenseLayer.Builder) layerBuilder;
                break;
            case CONVOLUTION_1D_LAYER:
            case CONVOLUTION_2D_LAYER:
            case DECONVOLUTION_2D_LAYER:
                ConvolutionLayer.Builder convolutionLayerBuilder = (ConvolutionLayer.Builder) layerBuilder;
                if(layer.getProperties().getKernelSize() != null)
                    convolutionLayerBuilder.setKernelSize(layer.getProperties().getKernelSize());
                if(layer.getProperties().getPaddingSize() != null)
                    convolutionLayerBuilder.setPadding(layer.getProperties().getPaddingSize());
                if(layer.getProperties().getStrideSize() != null)
                    convolutionLayerBuilder.setStride(layer.getProperties().getStrideSize());
                break;
            case OUTPUT_LAYER:
                OutputLayer.Builder outputLayerBuilder = (OutputLayer.Builder) layerBuilder;
                break;
            case LSTM:
                // BaseRecurrentLayer.Builder recurrentLayerBuilder = (BaseRecurrentLayer.Builder) layerBuilder;
                LSTM.Builder lstmLayerBuilder = (LSTM.Builder) layerBuilder;
                break;
        }
        return layerBuilder;
    }
}
