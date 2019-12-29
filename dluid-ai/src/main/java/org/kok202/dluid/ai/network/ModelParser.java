package org.kok202.dluid.ai.network;

import org.deeplearning4j.nn.graph.ComputationGraph;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.ai.singleton.structure.Model;
import org.kok202.dluid.domain.structure.GraphManager;

import java.util.ArrayList;
import java.util.List;

public class ModelParser {
    public static List<Model> parse(GraphManager<Layer> layerGraphManager){
        GraphManagerToComputationGraph.convert(layerGraphManager);
        return new ArrayList<>();
    }

    private static ComputationGraph createChunk(){
return null;
        // TODO
    }

    private static Model assembleChunk(){
return null;
        // TODO
    }

}
