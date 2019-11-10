package org.kok202.deepblock.application.content.material.list;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import org.kok202.deepblock.application.content.material.block.MaterialInputController;
import org.kok202.deepblock.application.content.material.block.MaterialOutputController;
import org.kok202.deepblock.application.content.material.insertion.MaterialInsertionFollower;
import org.kok202.deepblock.application.content.material.insertion.MaterialInsertionManager;

@Getter
public class MaterialListAssistantLayer extends AbstractMaterialList {
    @FXML
    private VBox layerAssistantListBox;

    private MaterialInsertionManager materialInsertionManager;

    public MaterialListAssistantLayer(MaterialInsertionManager materialInsertionManager) {
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
        addAbstractMaterialController(new MaterialInputController());
        addAbstractMaterialController(new MaterialOutputController());
        addAbstractMaterialControllerToVBox(layerAssistantListBox, materialInsertionManager);
    }
}
