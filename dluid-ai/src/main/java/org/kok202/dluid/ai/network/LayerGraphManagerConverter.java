package org.kok202.dluid.ai.network;

import org.kok202.dluid.domain.entity.Layer;
import org.kok202.dluid.domain.structure.GraphManager;

import java.util.List;

public class LayerGraphManagerConverter {
    public static List<Model> convert(GraphManager<Layer> layerGraphManager){
        LayerGraphManagerAnalyzer layerGraphManagerAnalyzer = new LayerGraphManagerAnalyzer();
        layerGraphManagerAnalyzer.analyze(layerGraphManager);

        // Convert linked computation graph as model.
        ModelConverter modelConverter = new ModelConverter();
        return modelConverter.convert(layerGraphManagerAnalyzer);
    }
}
