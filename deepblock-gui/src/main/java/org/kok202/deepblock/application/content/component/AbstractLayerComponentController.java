package org.kok202.deepblock.application.content.component;

import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.canvas.singleton.CanvasSingleton;

public abstract class AbstractLayerComponentController extends AbstractComponentController {

    protected Layer layer;

    protected AbstractLayerComponentController(Layer layer) {
        this.layer = layer;
    }

    protected void notifyLayerDataChanged(){
        CanvasSingleton.getInstance().getBlockNodeManager().notifyLayerDataChanged(layer.getId());
    }
}
