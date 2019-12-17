package org.kok202.dluid.ai.network;

import org.deeplearning4j.nn.conf.ComputationGraphConfiguration;
import org.deeplearning4j.nn.conf.ComputationGraphConfiguration.GraphBuilder;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.nn.weights.WeightInit;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.ai.entity.enumerator.Optimizer;
import org.kok202.dluid.ai.singleton.AIPropertiesSingleton;
import org.kok202.dluid.ai.util.LayerBuildingUtil;
import org.kok202.dluid.domain.structure.GraphManager;
import org.kok202.dluid.domain.util.RandomUtil;
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
        return optimizer.getIUpdater(learningRate);
    }

    private static WeightInit initWeightInitializer(){
        WeightInit weightInit = AIPropertiesSingleton.getInstance().getTrainProperty().getWeightInit().getWeightInit();
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
