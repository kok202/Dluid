package org.kok202.dluid.ai.network;

import org.deeplearning4j.nn.conf.ComputationGraphConfiguration.GraphBuilder;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.nn.weights.WeightInit;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.ai.entity.enumerator.Optimizer;
import org.kok202.dluid.ai.network.layer.LayerBinder;
import org.kok202.dluid.ai.singleton.AISingleton;
import org.kok202.dluid.domain.structure.GraphManager;
import org.kok202.dluid.domain.util.RandomUtil;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.IUpdater;

public class GraphManagerToComputationGraph {

    public static ComputationGraph convert(GraphManager<Layer> layerGraphManager){
        GraphBuilder graphBuilder = createDefaultConfiguration();
        layerGraphManager.getGraphNodes().forEach(currentLayerGraphNode -> LayerBinder.bind(currentLayerGraphNode, graphBuilder));
        return new ComputationGraph(graphBuilder.build());
    }

    private static GraphBuilder createDefaultConfiguration(){
        Nd4j.getRandom().setSeed(RandomUtil.getLong());
        return new NeuralNetConfiguration.Builder()
                .seed(RandomUtil.getLong())
                .updater(getOptimizer())
                .weightInit(getWeightInitializer())
                // .l2(1e-4) // FIXME : 지원해야할까?
                .graphBuilder();
    }

    private static IUpdater getOptimizer(){
        Optimizer optimizer = AISingleton.getInstance().getModelManager().getModelParameter().getOptimizer();
        double learningRate = AISingleton.getInstance().getModelManager().getModelParameter().getLearningRate();
        return optimizer.getIUpdater(learningRate);
    }

    private static WeightInit getWeightInitializer(){
        return AISingleton.getInstance().getModelManager().getModelParameter().getWeightInit().getWeightInit();
    }
}
