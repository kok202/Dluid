package org.kok202.deepblock.application.content.material.list;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.kok202.deepblock.application.common.AbstractController;
import org.kok202.deepblock.application.content.material.block.AbstractMaterialController;
import org.kok202.deepblock.application.content.material.insertion.MaterialInsertionManager;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMaterialList extends AbstractController {
    private List<AbstractMaterialController> abstractMaterialControllerList;
    abstract public AnchorPane createView() throws Exception;

    protected List<AbstractMaterialController> getAbstractMaterialControllerList(){
        if(abstractMaterialControllerList == null)
            abstractMaterialControllerList = new ArrayList<>();
        return abstractMaterialControllerList;
    }

    protected void addAbstractMaterialController(AbstractMaterialController abstractMaterialController){
        getAbstractMaterialControllerList().add(abstractMaterialController);
    }

    protected void addAbstractMaterialControllerToVBox(VBox vBox, MaterialInsertionManager materialInsertionManager) throws Exception {
        for(AbstractMaterialController abstractMaterialController : getAbstractMaterialControllerList()){
            vBox.getChildren().add(abstractMaterialController.createView());
            abstractMaterialController.setOnDragDetected(materialInsertionManager.startBlockInsertion(abstractMaterialController.getLayerType()));
        }
    }
}
