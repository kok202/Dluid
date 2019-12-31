package org.kok202.dluid.ai.network;

import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration.ListBuilder;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.ai.network.layer.LayerBinder;
import org.kok202.dluid.ai.singleton.AISingleton;
import org.kok202.dluid.domain.structure.GraphManager;
import org.kok202.dluid.domain.util.RandomUtil;
import org.nd4j.linalg.factory.Nd4j;

public class GraphManagerConverter {

    public static MultiLayerNetwork convert(GraphManager<Layer> layerGraphManager){
        ListBuilder neuralNetListBuilder = createDefaultConfiguration();
        layerGraphManager.getGraphNodes().forEach(currentLayerGraphNode -> LayerBinder.bind(currentLayerGraphNode, neuralNetListBuilder));
        return new MultiLayerNetwork(neuralNetListBuilder.build());
    }

    private static ListBuilder createDefaultConfiguration(){
        Nd4j.getRandom().setSeed(RandomUtil.getLong());
        return new NeuralNetConfiguration.Builder()
                .seed(RandomUtil.getLong())
                .updater(AISingleton.getInstance().getModelManager().getModelParameter().getIUpdater())
                .weightInit(AISingleton.getInstance().getModelManager().getModelParameter().getWeightInit())
                .miniBatch(false)
                .list();
    }
}
