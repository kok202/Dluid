package org.kok202.deepblock.ai.global.structure;

import lombok.Data;
import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.domain.structure.TreeManager;

@Data
public class ModelLayersProperty {
    private TreeManager<Layer> layerTreeManager;

    public ModelLayersProperty() {
        layerTreeManager = new TreeManager<>();
    }
}
