package org.kok202.dluid.application.content.design.material.list;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import org.kok202.dluid.application.content.design.material.block.Material1DConvolutionController;
import org.kok202.dluid.application.content.design.material.block.Material1DFeedForwardController;
import org.kok202.dluid.application.content.design.material.insertion.MaterialInsertionFollower;
import org.kok202.dluid.application.content.design.material.insertion.MaterialInsertionManager;
import org.kok202.dluid.application.singleton.AppPropertiesSingleton;

@Getter
public class MaterialList1DLayer extends AbstractMaterialList {
    @FXML
    private VBox layer1DListBox;

    private MaterialInsertionManager materialInsertionManager;

    public MaterialList1DLayer(MaterialInsertionManager materialInsertionManager) {
        this.materialInsertionManager = materialInsertionManager;
    }

    @Override
    public AnchorPane createView() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/design/material/list/layer_1d_list.fxml"));
        fxmlLoader.setController(this);
        return fxmlLoader.load();
    }

    @Override
    protected void initialize() throws Exception {
        ((AnchorPane)itself).getChildren().add(new MaterialInsertionFollower().createView());
        addAbstractMaterialController(new Material1DConvolutionController());
        addAbstractMaterialController(new Material1DFeedForwardController());
        addAbstractMaterialControllerToVBox(layer1DListBox, materialInsertionManager);
        titledPane.setText(AppPropertiesSingleton.getInstance().get("frame.material.layers.1d.title"));
    }
}
