package org.kok202.deepblock.application.content.material.list;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import org.kok202.deepblock.application.content.material.block.*;
import org.kok202.deepblock.application.content.material.insertion.MaterialInsertionFollower;
import org.kok202.deepblock.application.content.material.insertion.MaterialInsertionManager;

import java.util.ArrayList;

@Getter
public class MaterialListAdvancedLayer extends AbstractMaterialList {
    @FXML
    private VBox layerAssistantListBox;
    private ArrayList<AbstractMaterialController> layerAssistantList;

    private MaterialInsertionManager materialInsertionManager;

    public MaterialListAdvancedLayer(MaterialInsertionManager materialInsertionManager) {
        this.materialInsertionManager = materialInsertionManager;
    }

    @Override
    public AnchorPane createView() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/material/list/layer_assistant_list.fxml"));
        fxmlLoader.setController(this);
        return fxmlLoader.load();
    }

    @Override
    protected void initialize() throws Exception {
        ((AnchorPane)itself).getChildren().add(new MaterialInsertionFollower().createView());
        addBaseBlockToContentList();
        addBaseBlockToContentBoxWithEvent();
    }

    @Override
    protected void addBaseBlockToContentList(){
        layerAssistantList = new ArrayList<>();
        layerAssistantList.add(new MaterialTrainInputController());
        layerAssistantList.add(new MaterialTestInputController());
        layerAssistantList.add(new MaterialSplitInController());
        layerAssistantList.add(new MaterialSplitOutController());
    }

    @Override
    protected void addBaseBlockToContentBoxWithEvent() throws Exception {
        for(AbstractMaterialController baseBlockController : layerAssistantList){
            layerAssistantListBox.getChildren().add(baseBlockController.createView());
            baseBlockController.setOnDragDetected(materialInsertionManager.startBlockInsertion(baseBlockController.getLayerType()));
        }
    }
}
