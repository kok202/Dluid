package org.kok202.dluid.application.content.design.material.list;

import javafx.fxml.FXML;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import org.kok202.dluid.application.common.AbstractController;
import org.kok202.dluid.application.content.design.material.block.AbstractMaterialController;
import org.kok202.dluid.application.content.design.material.insertion.MaterialInsertionManager;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMaterialList extends AbstractController {
    @FXML
    @Getter
    private TitledPane titledPane;
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
