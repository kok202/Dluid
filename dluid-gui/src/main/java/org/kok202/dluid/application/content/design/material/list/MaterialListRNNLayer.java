package org.kok202.dluid.application.content.design.material.list;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;
import org.kok202.dluid.application.content.design.material.block.MaterialLSTMController;
import org.kok202.dluid.application.content.design.material.block.MaterialRNNController;
import org.kok202.dluid.application.content.design.material.insertion.MaterialInsertionManager;
import org.kok202.dluid.application.singleton.AppPropertiesSingleton;

@Getter
public class MaterialListRNNLayer extends AbstractMaterialList {

    private MaterialInsertionManager materialInsertionManager;

    public MaterialListRNNLayer(MaterialInsertionManager materialInsertionManager) {
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
        addAbstractMaterialController(new MaterialRNNController());
        addAbstractMaterialController(new MaterialLSTMController());
        addAbstractMaterialControllerToVBox(getLayerListBox(), materialInsertionManager);
        getTitledPane().setText(AppPropertiesSingleton.getInstance().get("frame.material.layers.rnn.title"));
    }
}
