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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class LayerBuildingUtil {
    public static GraphBuilder implementsLayers(GraphBuilder graphBuilder, GraphManager<Layer> layerGraphManager){
        Collection<GraphNode<Layer>> values = layerGraphManager.getGraphNodes();
        for(GraphNode<Layer> layerGraphNode : values){
            graphBuilder = addLayer(graphBuilder, layerGraphNode);
        }
        return graphBuilder;
    }

    private static GraphBuilder addLayer(GraphBuilder graphBuilder, GraphNode<Layer> layerGraphNode){
        Builder layerBuilder;
        switch(layerGraphNode.getData().getType()){
            case RESHAPE_LAYER:
                break;
            case INPUT_LAYER:
            case TRAIN_INPUT_LAYER:
            case TEST_INPUT_LAYER:
                String currentInput = getCurrentNodeId(layerGraphNode);
                graphBuilder.addInputs(currentInput);
                break;
            case OUTPUT_LAYER:
                layerBuilder = BlockLayerUtil.getLayerBuilder(layerGraphNode.getData());
                setCommonProperties(layerGraphNode.getData(), layerBuilder);
                setAddOnProperties(layerGraphNode.getData(), layerBuilder);
                addLayerToBuilder(graphBuilder, layerGraphNode, layerBuilder);
                graphBuilder.setOutputs(getCurrentNodeId(layerGraphNode));
                break;
            default:
                layerBuilder = BlockLayerUtil.getLayerBuilder(layerGraphNode.getData());
                setCommonProperties(layerGraphNode.getData(), layerBuilder);
                setAddOnProperties(layerGraphNode.getData(), layerBuilder);
                addLayerToBuilder(graphBuilder, layerGraphNode, layerBuilder);
                break;
        }
        return graphBuilder;
    }

    private static void addLayerToBuilder(GraphBuilder graphBuilder, GraphNode<Layer> layerGraphNode, Builder layerBuilder){
        String currentLayer = getCurrentNodeId(layerGraphNode);
        List<String> layersFrom = getFromNodeIdStrings(layerGraphNode);
        if(layersFrom.size() > 1){
            String mergeVertexId = currentLayer + "-merged";
            String[] mergesFrom = layersFrom.toArray(new String[0]);
            graphBuilder.addVertex(mergeVertexId, new MergeVertex(), mergesFrom);
            graphBuilder.addLayer(currentLayer, layerBuilder.build(), mergeVertexId);
        }
        else if(layersFrom.size() == 1){
            String layerFrom = layersFrom.get(0);
            graphBuilder.addLayer(currentLayer, layerBuilder.build(), layerFrom);
        }
    }

    private static List<String> getFromNodeIdStrings(GraphNode<Layer> layerGraphNode){
        List<GraphNode<Layer>> incomingNodes = layerGraphNode.getIncomingNodes();
        if(incomingNodes.isEmpty())
            return Collections.emptyList();

        List<String> resultStrings = new ArrayList<>();
        for (GraphNode<Layer> incomingNode : incomingNodes) {
            if(incomingNode.getData().getType() == LayerType.RESHAPE_LAYER){
                resultStrings.addAll(getFromNodeIdStrings(incomingNode));
            }
            else {
                String nodeId = String.valueOf(incomingNode.getData().getId());
                resultStrings.add(nodeId);
            }
        }
        return resultStrings;
    }

    private static String getCurrentNodeId(GraphNode<Layer> layerGraphNode){
        return String.valueOf(layerGraphNode.getData().getId());
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
