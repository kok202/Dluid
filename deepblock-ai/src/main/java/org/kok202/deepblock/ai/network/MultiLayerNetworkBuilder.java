package org.kok202.deepblock.ai.network;

import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration.ListBuilder;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.ai.entity.enumerator.Optimizer;
import org.kok202.deepblock.ai.global.AIPropertiesSingleton;
import org.kok202.deepblock.ai.util.LayerBuildingUtil;
import org.kok202.deepblock.ai.util.OptimizerUtil;
import org.kok202.deepblock.domain.structure.GraphNode;
import org.kok202.deepblock.domain.util.RandomUtil;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.IUpdater;

public class MultiLayerNetworkBuilder {
    public static MultiLayerNetwork build(){
        IUpdater optimizer = initOptimizer();
        WeightInit weightInit = initWeightInitializer();
        ListBuilder neuralNetLayerBuilder = initModelConfiguration(optimizer, weightInit);
        MultiLayerConfiguration multiLayerConfiguration = createNeuralNetLayer(neuralNetLayerBuilder);
        return buildMultiLayerNetwork(multiLayerConfiguration);
    }

    private static IUpdater initOptimizer(){
        Optimizer optimizer = AIPropertiesSingleton.getInstance().getTrainProperty().getOptimizer();
        double learningRate = AIPropertiesSingleton.getInstance().getTrainProperty().getLearningRate();
        return OptimizerUtil.createIUpdaterFromString(optimizer, learningRate);
    }

    private static WeightInit initWeightInitializer(){
        WeightInit weightInit = AIPropertiesSingleton.getInstance().getTrainProperty().getWeightInit();
        return weightInit;
    }

    private static ListBuilder initModelConfiguration(IUpdater optimizer, WeightInit weightInit){
        Nd4j.getRandom().setSeed(RandomUtil.getLong());
        return new NeuralNetConfiguration.Builder()
                    .seed(RandomUtil.getLong())
                    .updater(optimizer)
                    .weightInit(weightInit)
                    // .l2(1e-4) // FIXME : 지원해야할까?
                    .list();
    }

    private static MultiLayerConfiguration createNeuralNetLayer(ListBuilder neuralNetLayerBuilder){
        GraphNode<Layer> rootNode = AIPropertiesSingleton.getInstance()
                .getModelLayersProperty()
                .getLayerGraph()
                .getRoot();
        return LayerBuildingUtil.implementsLayers(neuralNetLayerBuilder, rootNode).build();
    }

    private static MultiLayerNetwork buildMultiLayerNetwork(MultiLayerConfiguration multiLayerConfiguration){
        return new MultiLayerNetwork(multiLayerConfiguration);
    }
}
