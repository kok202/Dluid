package org.kok202.deepblock.ai.global.structure;

import lombok.Data;
import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.domain.structure.Tree;

@Data
public class ModelProperty {
    private Tree<Layer> layerTree;

    public ModelProperty() {
        layerTree = new Tree<>(null);
    }
}
