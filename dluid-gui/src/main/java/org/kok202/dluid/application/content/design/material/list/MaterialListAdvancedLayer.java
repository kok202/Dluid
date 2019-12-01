package org.kok202.dluid.application.content.design.material.list;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import org.kok202.dluid.application.content.design.material.block.MaterialMergeController;
import org.kok202.dluid.application.content.design.material.block.MaterialReshapeController;
import org.kok202.dluid.application.content.design.material.block.MaterialTestInputController;
import org.kok202.dluid.application.content.design.material.block.MaterialTrainInputController;
import org.kok202.dluid.application.content.design.material.insertion.MaterialInsertionFollower;
import org.kok202.dluid.application.content.design.material.insertion.MaterialInsertionManager;
import org.kok202.dluid.application.singleton.AppPropertiesSingleton;

@Getter
public class MaterialListAdvancedLayer extends AbstractMaterialList {
    @FXML
    private VBox layerAdvancedListBox;

    private MaterialInsertionManager materialInsertionManager;

    public MaterialListAdvancedLayer(MaterialInsertionManager materialInsertionManager) {
        this.materialInsertionManager = materialInsertionManager;
    }

    @Override
    public AnchorPane createView() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/design/material/list/layer_advanced_list.fxml"));
        fxmlLoader.setController(this);
        return fxmlLoader.load();
    }

    @Override
    protected void initialize() throws Exception {
        ((AnchorPane)itself).getChildren().add(new MaterialInsertionFollower().createView());
        addAbstractMaterialController(new MaterialTrainInputController());
        addAbstractMaterialController(new MaterialTestInputController());
        addAbstractMaterialController(new MaterialReshapeController());
        addAbstractMaterialController(new MaterialMergeController());
        addAbstractMaterialControllerToVBox(layerAdvancedListBox, materialInsertionManager);
        getTitledPane().setText(AppPropertiesSingleton.getInstance().get("frame.material.layers.advanced.title"));
    }
}
