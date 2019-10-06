package org.kok202.deepblock.ai.global.structure;

import lombok.Data;
import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.domain.structure.Tree;

@Data
public class ModelLayersProperty {
    private Tree<Layer> layerTree;

    public ModelLayersProperty() {
        layerTree = new Tree<>(null);
    }
}
