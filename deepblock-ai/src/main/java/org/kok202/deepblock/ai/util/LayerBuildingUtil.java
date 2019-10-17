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
import org.kok202.deepblock.domain.structure.TreeManager;
import org.kok202.deepblock.domain.structure.TreeNode;

import java.util.List;

public class LayerBuildingUtil {
    public static GraphBuilder implementsLayers(GraphBuilder neuralNetLayerBuilder, TreeManager<Layer> layerTreeManager){
        for(TreeNode<Layer> layerTreeNode : layerTreeManager.getTreeNodeMap().values()){
            neuralNetLayerBuilder = addLayer(neuralNetLayerBuilder, layerTreeNode);
        }
        return neuralNetLayerBuilder;
    }

    private static GraphBuilder addLayer(GraphBuilder neuralNetLayerBuilder, TreeNode<Layer> layerTreeNode){
        Builder layerBuilder;
        switch(layerTreeNode.getData().getType()){
            case INPUT_LAYER:
            case TRAIN_INPUT_LAYER:
            case TEST_INPUT_LAYER:
                String currentInput = getCurrentNodeId(layerTreeNode);
                neuralNetLayerBuilder.addInputs(currentInput);
                break;
            case SPLIT_IN_LAYER:
                String currentVertex = getCurrentNodeId(layerTreeNode);
                String mergeFromA = String.valueOf(layerTreeNode.getIncomingNodes().get(0).getData().getId());
                String mergeFromB = String.valueOf(layerTreeNode.getIncomingNodes().get(0).getData().getId());
                neuralNetLayerBuilder.addVertex(currentVertex, new MergeVertex(), mergeFromA, mergeFromB);
                break;
            case SPLIT_OUT_LAYER:
                break;
            case OUTPUT_LAYER:
                String output = getCurrentNodeId(layerTreeNode);
                String outputFrom = getFromNodeId(layerTreeNode);
                layerBuilder = BlockLayerUtil.getLayerBuilder(layerTreeNode.getData());
                setCommonProperties(layerTreeNode.getData(), layerBuilder);
                setAddOnProperties(layerTreeNode.getData(), layerBuilder);
                neuralNetLayerBuilder.addLayer(output, layerBuilder.build(), outputFrom);
                neuralNetLayerBuilder.setOutputs(String.valueOf(layerTreeNode.getData().getId()));
                break;
            default:
                String currentLayer = getCurrentNodeId(layerTreeNode);
                String currentLayerFrom = getFromNodeId(layerTreeNode);
                layerBuilder = BlockLayerUtil.getLayerBuilder(layerTreeNode.getData());
                setCommonProperties(layerTreeNode.getData(), layerBuilder);
                setAddOnProperties(layerTreeNode.getData(), layerBuilder);
                neuralNetLayerBuilder.addLayer(currentLayer, layerBuilder.build(), currentLayerFrom);
                break;
        }
        return neuralNetLayerBuilder;
    }

    private static String getCurrentNodeId(TreeNode<Layer> layerTreeNode){
        return String.valueOf(layerTreeNode.getData().getId());
    }

    private static String getFromNodeId(TreeNode<Layer> layerTreeNode){
        List<TreeNode<Layer>> incomingNodes = layerTreeNode.getIncomingNodes();
        if(incomingNodes.isEmpty())
            return "";
        TreeNode<Layer> parentLayerTreeNode = incomingNodes.get(0);
        if(parentLayerTreeNode.getData().getType() == LayerType.SPLIT_OUT_LAYER){
            return getFromNodeId(parentLayerTreeNode);
        }
        return String.valueOf(parentLayerTreeNode.getData().getId());
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
