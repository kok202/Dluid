package org.kok202.dluid.content.design.material.list;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;
import org.kok202.dluid.content.design.material.block.MaterialInputController;
import org.kok202.dluid.content.design.material.block.MaterialOutputController;
import org.kok202.dluid.content.design.material.insertion.MaterialInsertionManager;
import org.kok202.dluid.singleton.AppPropertiesSingleton;

@Getter
public class MaterialListAssistantLayer extends AbstractMaterialList {

    private MaterialInsertionManager materialInsertionManager;

    public MaterialListAssistantLayer(MaterialInsertionManager materialInsertionManager) {
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
        addAbstractMaterialController(new MaterialInputController());
        addAbstractMaterialController(new MaterialOutputController());
        addAbstractMaterialControllerToVBox(getLayerListBox(), materialInsertionManager);
        getTitledPane().setText(AppPropertiesSingleton.getInstance().get("frame.material.layers.assistant.title"));
    }
}
