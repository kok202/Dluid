package org.kok202.dluid.ai.singleton.structure;

import lombok.Data;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.domain.structure.GraphManager;

@Data
public class ModelLayersProperty {
    private GraphManager<Layer> layerGraphManager;

    public ModelLayersProperty() {
        layerGraphManager = new GraphManager<>();
    }
}
