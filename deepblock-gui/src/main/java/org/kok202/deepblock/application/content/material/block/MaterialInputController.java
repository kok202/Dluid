package org.kok202.deepblock.application.content.material.block;

import org.kok202.deepblock.ai.entity.enumerator.LayerType;
import org.kok202.deepblock.canvas.singleton.CanvasInterface;

public class MaterialInputController extends AbstractMaterialController {
    @Override
    protected void initialize() throws Exception{
        this.setLayerType(LayerType.INPUT_LAYER);
        super.setStyleByBlockType(layerType);
    }

    public boolean isUseable() {
        return CanvasInterface.getInstance().isPossibleToAddTrainInputLayer() &&
                CanvasInterface.getInstance().isPossibleToAddTestInputLayer();
    }
}
