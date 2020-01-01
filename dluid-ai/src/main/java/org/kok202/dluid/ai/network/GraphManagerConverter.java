package org.kok202.dluid.ai.network;

import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.domain.structure.GraphManager;

import java.util.List;

public class GraphManagerConverter {
    public static List<Model> convert(GraphManager<Layer> layerGraphManager){
        GraphManagerAnalyzer graphManagerAnalyzer = new GraphManagerAnalyzer();
        graphManagerAnalyzer.analyze(layerGraphManager);

        // Convert linked computation graph as model.
        ModelConverter modelConverter = new ModelConverter();
        return modelConverter.convert(graphManagerAnalyzer);
    }
}
