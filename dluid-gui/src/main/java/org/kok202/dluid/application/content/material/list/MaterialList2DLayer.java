package org.kok202.dluid.application.content.material.list;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import org.kok202.dluid.application.content.material.block.Material2DConvolutionController;
import org.kok202.dluid.application.content.material.insertion.MaterialInsertionFollower;
import org.kok202.dluid.application.content.material.insertion.MaterialInsertionManager;
import org.kok202.dluid.application.singleton.AppPropertiesSingleton;

@Getter
public class MaterialList2DLayer extends AbstractMaterialList {
    @FXML
    private VBox layer2DListBox;

    private MaterialInsertionManager materialInsertionManager;

    public MaterialList2DLayer(MaterialInsertionManager materialInsertionManager) {
        this.materialInsertionManager = materialInsertionManager;
    }

    @Override
    public AnchorPane createView() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/material/list/layer_2d_list.fxml"));
        fxmlLoader.setController(this);
        return fxmlLoader.load();
    }

    @Override
    protected void initialize() throws Exception {
        ((AnchorPane)itself).getChildren().add(new MaterialInsertionFollower().createView());
        addAbstractMaterialController(new Material2DConvolutionController());
        addAbstractMaterialController(new Material2DConvolutionController());
        addAbstractMaterialController(new Material2DConvolutionController());
        addAbstractMaterialController(new Material2DConvolutionController());
        addAbstractMaterialController(new Material2DConvolutionController());
        addAbstractMaterialControllerToVBox(layer2DListBox, materialInsertionManager);
        titledPane.setText(AppPropertiesSingleton.getInstance().get("frame.material.layers.2d.title"));
    }
}
