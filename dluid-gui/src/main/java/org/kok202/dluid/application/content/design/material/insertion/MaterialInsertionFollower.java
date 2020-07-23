package org.kok202.dluid.application.content.design.material.insertion;

import javafx.scene.input.ClipboardContent;
import javafx.scene.input.TransferMode;
import org.kok202.dluid.application.content.design.material.block.AbstractMaterialController;
import org.kok202.dluid.domain.entity.enumerator.LayerType;

public class MaterialInsertionFollower extends AbstractMaterialController {
    @Override
    protected void initialize() throws Exception{
        changeStyle(LayerType.DENSE_LAYER);
    }

    public void setInsertionSetting(ClipboardContent clipboardContent, LayerType layerType){
        itself.startDragAndDrop(TransferMode.ANY).setContent(clipboardContent);
        itself.setMouseTransparent(true);
        changeStyle(layerType);
        setVisible(true);
    }

    private void changeStyle(LayerType layerType){
        setStyleByBlockType(layerType);
        itself.setVisible(false);
        itself.setOpacity(0.5);
    }

    public void setVisible(boolean visible){
        itself.setVisible(visible);
    }
}
