package org.kok202.dluid.ai.network;

import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.ai.singleton.structure.Model;
import org.kok202.dluid.domain.structure.GraphManager;

import java.util.ArrayList;
import java.util.List;

public class ModelParser {
    public static List<Model> parse(GraphManager<Layer> layerGraphManager){
        GraphManagerSplitter graphManagerSplitter = new GraphManagerSplitter();
        graphManagerSplitter.split(layerGraphManager);

        // TODO :
        GraphManagerToComputationGraph.convert(layerGraphManager);

        GraphManagerAssembler graphManagerAssembler = new GraphManagerAssembler();
        graphManagerAssembler.assemble();
        return new ArrayList<>();
    }
}
