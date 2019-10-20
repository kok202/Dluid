package org.kok202.deepblock.ai.network;

import org.deeplearning4j.nn.conf.ComputationGraphConfiguration;
import org.deeplearning4j.nn.conf.ComputationGraphConfiguration.GraphBuilder;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.nn.weights.WeightInit;
import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.ai.entity.enumerator.Optimizer;
import org.kok202.deepblock.ai.global.AIPropertiesSingleton;
import org.kok202.deepblock.ai.util.LayerBuildingUtil;
import org.kok202.deepblock.ai.util.OptimizerUtil;
import org.kok202.deepblock.domain.structure.GraphManager;
import org.kok202.deepblock.domain.util.RandomUtil;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.IUpdater;

public class NetworkBuilder {
    public static ComputationGraph build(){
        IUpdater optimizer = initOptimizer();
        WeightInit weightInit = initWeightInitializer();
        GraphBuilder neuralNetLayerBuilder = initModelConfiguration(optimizer, weightInit);
        ComputationGraphConfiguration computationGraphConfiguration = createNeuralNetLayer(neuralNetLayerBuilder);
        return buildNetwork(computationGraphConfiguration);
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

    private static GraphBuilder initModelConfiguration(IUpdater optimizer, WeightInit weightInit){
        Nd4j.getRandom().setSeed(RandomUtil.getLong());
        return new NeuralNetConfiguration.Builder()
                    .seed(RandomUtil.getLong())
                    .updater(optimizer)
                    .weightInit(weightInit)
                    // .l2(1e-4) // FIXME : 지원해야할까?
                    .graphBuilder();
    }

    private static ComputationGraphConfiguration createNeuralNetLayer(GraphBuilder neuralNetLayerBuilder){
        GraphManager<Layer> layerGraphManager = AIPropertiesSingleton.getInstance()
                .getModelLayersProperty()
                .getLayerGraphManager();
        return LayerBuildingUtil.implementsLayers(neuralNetLayerBuilder, layerGraphManager).build();
    }

    private static ComputationGraph buildNetwork(ComputationGraphConfiguration computationGraphConfiguration){
        return new ComputationGraph(computationGraphConfiguration);
    }
}
