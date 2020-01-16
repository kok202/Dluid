package org.kok202.dluid.application.content.design.material.list;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;
import org.kok202.dluid.application.content.design.material.block.*;
import org.kok202.dluid.application.content.design.material.insertion.MaterialInsertionFollower;
import org.kok202.dluid.application.content.design.material.insertion.MaterialInsertionManager;
import org.kok202.dluid.application.singleton.AppPropertiesSingleton;

@Getter
public class MaterialListAdvancedLayer extends AbstractMaterialList {

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
        addAbstractMaterialController(new MaterialReshapeController());
        //addAbstractMaterialController(new MaterialMergeController());
        addAbstractMaterialController(new MaterialPooling1DController());
        addAbstractMaterialController(new MaterialPooling2DController());
        addAbstractMaterialController(new MaterialBatchNormalizationController());
        addAbstractMaterialController(new MaterialSwitchController());
        addAbstractMaterialControllerToVBox(getLayerListBox(), materialInsertionManager);
        getTitledPane().setText(AppPropertiesSingleton.getInstance().get("frame.material.layers.advanced.title"));
    }
}
