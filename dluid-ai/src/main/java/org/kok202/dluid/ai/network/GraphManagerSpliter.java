package org.kok202.dluid.ai.network;

import org.deeplearning4j.nn.conf.ComputationGraphConfiguration.GraphBuilder;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.ai.network.layer.LayerBinder;
import org.kok202.dluid.domain.structure.GraphManager;

import java.util.List;

public class GraphManagerSpliter {

    public static List<GraphManager<Layer>> split(GraphManager<Layer> layerGraphManager){
        GraphBuilder graphBuilder = createDefaultConfiguration();
        layerGraphManager.getGraphNodes().forEach(currentLayerGraphNode -> LayerBinder.bind(currentLayerGraphNode, graphBuilder));
        return new ComputationGraph(graphBuilder.build());
    }

}
