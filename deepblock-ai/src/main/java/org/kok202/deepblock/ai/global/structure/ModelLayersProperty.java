package org.kok202.deepblock.ai.global.structure;

import lombok.Data;
import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.domain.structure.GraphManager;

@Data
public class ModelLayersProperty {
    private GraphManager<Layer> layerGraphManager;

    public ModelLayersProperty() {
        layerGraphManager = new GraphManager<>();
    }
}
