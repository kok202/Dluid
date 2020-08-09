package org.kokzoz.dluid.content.design.material.list;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;
import org.kokzoz.dluid.content.design.material.block.Material1DConvolutionController;
import org.kokzoz.dluid.content.design.material.block.Material2DConvolutionController;
import org.kokzoz.dluid.content.design.material.block.Material2DDeconvolutionController;
import org.kokzoz.dluid.content.design.material.insertion.MaterialInsertionManager;
import org.kokzoz.dluid.singleton.AppPropertiesSingleton;

@Getter
public class MaterialListCNNLayer extends AbstractMaterialList {

    private MaterialInsertionManager materialInsertionManager;

    public MaterialListCNNLayer(MaterialInsertionManager materialInsertionManager) {
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
        addAbstractMaterialController(new Material1DConvolutionController());
        addAbstractMaterialController(new Material2DConvolutionController());
        addAbstractMaterialController(new Material2DDeconvolutionController());
        addAbstractMaterialControllerToVBox(getLayerListBox(), materialInsertionManager);
        getTitledPane().setText(AppPropertiesSingleton.getInstance().get("frame.material.layers.cnn.title"));
    }
}
