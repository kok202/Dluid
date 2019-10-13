package org.kok202.deepblock.ai.global.structure;

import lombok.Data;
import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.domain.structure.Graph;

@Data
public class ModelLayersProperty {
    private Graph<Layer> layerGraph;

    public ModelLayersProperty() {
        layerGraph = new Graph<>(null);
    }
}
