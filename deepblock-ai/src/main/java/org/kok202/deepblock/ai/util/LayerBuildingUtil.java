package org.kok202.deepblock.ai.util;

import org.deeplearning4j.nn.conf.ComputationGraphConfiguration.GraphBuilder;
import org.deeplearning4j.nn.conf.graph.MergeVertex;
import org.deeplearning4j.nn.conf.layers.ConvolutionLayer;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.FeedForwardLayer.Builder;
import org.deeplearning4j.nn.conf.layers.LSTM;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.ai.entity.enumerator.LayerType;
import org.kok202.deepblock.domain.structure.GraphManager;
import org.kok202.deepblock.domain.structure.GraphNode;

import java.util.List;

public class LayerBuildingUtil {
    public static GraphBuilder implementsLayers(GraphBuilder neuralNetLayerBuilder, GraphManager<Layer> layerGraphManager){
        for(GraphNode<Layer> layerGraphNode : layerGraphManager.getGraphNodeMap().values()){
            neuralNetLayerBuilder = addLayer(neuralNetLayerBuilder, layerGraphNode);
        }
        return neuralNetLayerBuilder;
    }

    private static GraphBuilder addLayer(GraphBuilder neuralNetLayerBuilder, GraphNode<Layer> layerGraphNode){
        Builder layerBuilder;
        switch(layerGraphNode.getData().getType()){
            case INPUT_LAYER:
            case TRAIN_INPUT_LAYER:
            case TEST_INPUT_LAYER:
                String currentInput = getCurrentNodeId(layerGraphNode);
                neuralNetLayerBuilder.addInputs(currentInput);
                break;
            case SPLIT_IN_LAYER:
                String currentVertex = getCurrentNodeId(layerGraphNode);
                String mergeFromA = String.valueOf(layerGraphNode.getIncomingNodes().get(0).getData().getId());
                String mergeFromB = String.valueOf(layerGraphNode.getIncomingNodes().get(0).getData().getId());
                neuralNetLayerBuilder.addVertex(currentVertex, new MergeVertex(), mergeFromA, mergeFromB);
                break;
            case SPLIT_OUT_LAYER:
                break;
            case OUTPUT_LAYER:
                String output = getCurrentNodeId(layerGraphNode);
                String outputFrom = getFromNodeId(layerGraphNode);
                layerBuilder = BlockLayerUtil.getLayerBuilder(layerGraphNode.getData());
                setCommonProperties(layerGraphNode.getData(), layerBuilder);
                setAddOnProperties(layerGraphNode.getData(), layerBuilder);
                neuralNetLayerBuilder.addLayer(output, layerBuilder.build(), outputFrom);
                neuralNetLayerBuilder.setOutputs(output);
                break;
            default:
                String currentLayer = getCurrentNodeId(layerGraphNode);
                String currentLayerFrom = getFromNodeId(layerGraphNode);
                layerBuilder = BlockLayerUtil.getLayerBuilder(layerGraphNode.getData());
                setCommonProperties(layerGraphNode.getData(), layerBuilder);
                setAddOnProperties(layerGraphNode.getData(), layerBuilder);
                neuralNetLayerBuilder.addLayer(currentLayer, layerBuilder.build(), currentLayerFrom);
                break;
        }
        return neuralNetLayerBuilder;
    }

    private static String getCurrentNodeId(GraphNode<Layer> layerGraphNode){
        return String.valueOf(layerGraphNode.getData().getId());
    }

    private static String getFromNodeId(GraphNode<Layer> layerGraphNode){
        List<GraphNode<Layer>> incomingNodes = layerGraphNode.getIncomingNodes();
        if(incomingNodes.isEmpty())
            return "";
        GraphNode<Layer> parentLayerGraphNode = incomingNodes.get(0);
        if(parentLayerGraphNode.getData().getType() == LayerType.SPLIT_OUT_LAYER){
            return getFromNodeId(parentLayerGraphNode);
        }
        return String.valueOf(parentLayerGraphNode.getData().getId());
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
