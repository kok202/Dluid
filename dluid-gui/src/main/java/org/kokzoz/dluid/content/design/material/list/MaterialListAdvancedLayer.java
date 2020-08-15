package org.kokzoz.dluid.content.design.material.list;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;
import org.kokzoz.dluid.content.design.material.block.MaterialBatchNormalizationController;
import org.kokzoz.dluid.content.design.material.block.MaterialPooling1DController;
import org.kokzoz.dluid.content.design.material.block.MaterialPooling2DController;
import org.kokzoz.dluid.content.design.material.block.MaterialReshapeController;
import org.kokzoz.dluid.content.design.material.insertion.MaterialInsertionFollower;
import org.kokzoz.dluid.content.design.material.insertion.MaterialInsertionManager;
import org.kokzoz.dluid.singleton.AppPropertiesSingleton;

@Getter
public class MaterialListAdvancedLayer extends AbstractMaterialList {

    private MaterialInsertionManager materialInsertionManager;

    public MaterialListAdvancedLayer(MaterialInsertionManager materialInsertionManager) {
        this.materialInsertionManager = materialInsertionManager;
    }

    @Override
    public AnchorPane createView() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/design/material/material_list.fxml"));
        fxmlLoader.setController(this);
        return fxmlLoader.load();
    }

    @Override
    protected void initialize() throws Exception {
        ((AnchorPane)itself).getChildren().add(new MaterialInsertionFollower().createView());
        addAbstractMaterialController(new MaterialReshapeController());
        addAbstractMaterialController(new MaterialPooling1DController());
        addAbstractMaterialController(new MaterialPooling2DController());
        addAbstractMaterialController(new MaterialBatchNormalizationController());
        addAbstractMaterialControllerToVBox(getLayerListBox(), materialInsertionManager);
        getTitledPane().setText(AppPropertiesSingleton.getInstance().get("frame.material.layers.advanced.title"));
    }
}
