package org.kok202.deepblock.application.content.material.list;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import org.kok202.deepblock.application.content.material.block.AbstractMaterialController;
import org.kok202.deepblock.application.content.material.block.Material1DConvolutionController;
import org.kok202.deepblock.application.content.material.block.Material1DFeedForwardController;
import org.kok202.deepblock.application.content.material.insertion.MaterialInsertionFollower;
import org.kok202.deepblock.application.content.material.insertion.MaterialInsertionManager;

import java.util.ArrayList;

@Getter
public class MaterialList1DLayer extends AbstractMaterialList {
    @FXML
    private VBox layer1DListBox;
    private ArrayList<AbstractMaterialController> layer1DList;

    private MaterialInsertionManager materialInsertionManager;

    public MaterialList1DLayer(MaterialInsertionManager materialInsertionManager) {
        this.materialInsertionManager = materialInsertionManager;
    }

    @Override
    public AnchorPane createView() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/material/list/layer_1d_list.fxml"));
        fxmlLoader.setController(this);
        return fxmlLoader.load();
    }

    @Override
    protected void initialize() throws Exception {
        ((AnchorPane)itself).getChildren().add(new MaterialInsertionFollower().createView());
        addBaseBlockToContentList();
        addBaseBlockToContentBoxWithEvent();
    }

    @Override
    protected void addBaseBlockToContentList(){
        layer1DList = new ArrayList<>();
        layer1DList.add(new Material1DConvolutionController());
        layer1DList.add(new Material1DFeedForwardController());
    }

    @Override
    protected void addBaseBlockToContentBoxWithEvent() throws Exception {
        for(AbstractMaterialController baseBlockController : layer1DList){
            layer1DListBox.getChildren().add(baseBlockController.createView());
            baseBlockController.setOnDragDetected(materialInsertionManager.startBlockInsertion(baseBlockController.getLayerType()));
        }
    }
}
