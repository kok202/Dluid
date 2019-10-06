package org.kok202.deepblock.ai.util;

import org.deeplearning4j.nn.conf.layers.*;
import org.deeplearning4j.nn.conf.layers.FeedForwardLayer.Builder;
import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.ai.entity.enumerator.LayerType;
import org.kok202.deepblock.domain.exception.IllegalLayerRequest;

import java.util.HashMap;

public class BlockLayerUtil {
    private static HashMap<String, LayerType> blockLayerHashMap;

    private static HashMap<String, LayerType> getBlockLayerHashMap(){
        if(blockLayerHashMap == null){
            blockLayerHashMap = new HashMap<>();
            blockLayerHashMap.put("denseLayer", LayerType.DENSE_LAYER);
            blockLayerHashMap.put("convolution1DLayer", LayerType.CONVOLUTION_1D_LAYER);
            blockLayerHashMap.put("convolution2DLayer", LayerType.CONVOLUTION_2D_LAYER);
            blockLayerHashMap.put("deconvolution2DLayer", LayerType.DECONVOLUTION_2D_LAYER);
            blockLayerHashMap.put("lstm", LayerType.LSTM);
            blockLayerHashMap.put("baseRecurrentLayer", LayerType.BASE_RECURRENT_LAYER);
            blockLayerHashMap.put("lossLayer", LayerType.LOSS_LAYER);
            blockLayerHashMap.put("cnnLossLayer", LayerType.CNN_LOSS_LAYER);
            blockLayerHashMap.put("rnnLossLayer", LayerType.RNN_LOSS_LAYER);
            blockLayerHashMap.put("baseOutputLayer", LayerType.OUTPUT_LAYER);
            blockLayerHashMap.put("basePretrainNetwork", LayerType.BASE_PRETAIN_NETWORK);
            blockLayerHashMap.put("batchNormalization", LayerType.BATCH_NORMALIZATION);
            blockLayerHashMap.put("pReLULayer", LayerType.PRELU_LAYER);
            blockLayerHashMap.put("dropoutLayer", LayerType.DROPOUT_LAYER);
            blockLayerHashMap.put("elementWiseMultiplicationLayer", LayerType.ELEMENT_WISE_MULTIPLICATION_LAYER);
            blockLayerHashMap.put("embeddingLayer", LayerType.EMBEDDING_LAYER);
            blockLayerHashMap.put("embeddingSequenceLayer", LayerType.EMBEDDING_SEQUENCE_LAYER);
            blockLayerHashMap.put("repeatVector", LayerType.REPEAT_VECTOR);
        }
        return blockLayerHashMap;
    }

    public static LayerType convertToBlockLayer(String blockLayerString){
        return getBlockLayerHashMap().get(blockLayerString);
    }

    // Because method should make new Builder, so not use hash map pattern.
    public static Builder getLayerBuilder(Layer layer){
        switch(layer.getType()){
            case DENSE_LAYER: return new DenseLayer.Builder();
            case CONVOLUTION_1D_LAYER: return new Convolution1DLayer.Builder();
            case CONVOLUTION_2D_LAYER: return new ConvolutionLayer.Builder();
            case DECONVOLUTION_2D_LAYER: return new Deconvolution2D.Builder();
            case OUTPUT_LAYER: return new OutputLayer.Builder(layer.getProperties().getLossFunction());
            case LSTM: return new LSTM.Builder();
        }
        throw new IllegalLayerRequest();
    }
}
