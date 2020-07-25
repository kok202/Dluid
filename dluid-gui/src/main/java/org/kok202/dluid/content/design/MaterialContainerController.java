package org.kok202.dluid.content.design;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import org.kok202.dluid.common.AbstractController;
import org.kok202.dluid.content.design.material.insertion.MaterialInsertionFollower;
import org.kok202.dluid.content.design.material.insertion.MaterialInsertionManager;
import org.kok202.dluid.content.design.material.list.*;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MaterialContainerController extends AbstractController {
    @FXML private VBox container;
    private MaterialInsertionManager materialInsertionManager;
    private List<AbstractMaterialList> abstractMaterialLists;

    public MaterialContainerController(MaterialInsertionManager materialInsertionManager) {
        this.materialInsertionManager = materialInsertionManager;
        this.abstractMaterialLists = new ArrayList<>();
    }

    public AnchorPane createView() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/design/material_container.fxml"));
        fxmlLoader.setController(this);
        return fxmlLoader.load();
    }

    @Override
    protected void initialize() throws Exception {
        ((AnchorPane)itself).getChildren().add(new MaterialInsertionFollower().createView());
        abstractMaterialLists.add(new MaterialListAssistantLayer(materialInsertionManager));
        abstractMaterialLists.add(new MaterialListFCNNLayer(materialInsertionManager));
        abstractMaterialLists.add(new MaterialListCNNLayer(materialInsertionManager));
        abstractMaterialLists.add(new MaterialListRNNLayer(materialInsertionManager));
        abstractMaterialLists.add(new MaterialListAdvancedLayer(materialInsertionManager));

        for (AbstractMaterialList abstractMaterialList : abstractMaterialLists) {
            container.getChildren().add(abstractMaterialList.createView());
            abstractMaterialList.getTitledPane().setExpanded(false);
        }
        abstractMaterialLists.get(0).getTitledPane().setExpanded(true);
    }
}
