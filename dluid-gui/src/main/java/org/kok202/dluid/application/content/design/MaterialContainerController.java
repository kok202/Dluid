package org.kok202.dluid.application.content.design;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import org.kok202.dluid.application.common.AbstractController;
import org.kok202.dluid.application.content.design.material.insertion.MaterialInsertionFollower;
import org.kok202.dluid.application.content.design.material.insertion.MaterialInsertionManager;
import org.kok202.dluid.application.content.design.material.list.MaterialList1DLayer;
import org.kok202.dluid.application.content.design.material.list.MaterialList2DLayer;
import org.kok202.dluid.application.content.design.material.list.MaterialListAdvancedLayer;
import org.kok202.dluid.application.content.design.material.list.MaterialListAssistantLayer;

@Getter
public class MaterialContainerController extends AbstractController {
    @FXML
    private VBox content;

    private MaterialInsertionManager materialInsertionManager;

    public MaterialContainerController(MaterialInsertionManager materialInsertionManager) {
        this.materialInsertionManager = materialInsertionManager;
    }

    public AnchorPane createView() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/design/material_container.fxml"));
        fxmlLoader.setController(this);
        return fxmlLoader.load();
    }

    @Override
    protected void initialize() throws Exception {
        ((AnchorPane)itself).getChildren().add(new MaterialInsertionFollower().createView());
        content.getChildren().add(new MaterialListAssistantLayer(materialInsertionManager).createView());
        content.getChildren().add(new MaterialList1DLayer(materialInsertionManager).createView());
        content.getChildren().add(new MaterialList2DLayer(materialInsertionManager).createView());
        content.getChildren().add(new MaterialListAdvancedLayer(materialInsertionManager).createView());
    }
}
