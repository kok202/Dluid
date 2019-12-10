package org.kok202.dluid.application.content;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import lombok.Getter;
import org.kok202.dluid.application.common.AbstractController;
import org.kok202.dluid.application.content.design.CanvasContainerController;
import org.kok202.dluid.application.content.design.ComponentContainerController;
import org.kok202.dluid.application.content.design.MaterialContainerController;
import org.kok202.dluid.application.content.design.material.insertion.MaterialInsertionManager;
import org.kok202.dluid.application.singleton.AppPropertiesSingleton;

@Getter
public class TabModelDesignController extends AbstractController {
    private SplitPane mainSplitter;
    private MaterialContainerController materialContainerController;
    private CanvasContainerController canvasContainerController;
    private ComponentContainerController componentContainerController;


    public Tab createView() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/tab_model_design.fxml"));
        fxmlLoader.setController(this);
        AnchorPane content = fxmlLoader.load();

        Tab instanceTab = new Tab();
        instanceTab.setText(AppPropertiesSingleton.getInstance().get("frame.tabs.model.design"));
        instanceTab.setContent(content);
        return instanceTab;
    }

    @Override
    protected void initialize() throws Exception {
        AnchorPane rootPane =  (AnchorPane) itself;
        mainSplitter = (SplitPane) rootPane.lookup("#mainSplitter");

        MaterialInsertionManager materialInsertionManager = new MaterialInsertionManager();
        materialContainerController = new MaterialContainerController(materialInsertionManager);
        canvasContainerController = new CanvasContainerController();
        componentContainerController = new ComponentContainerController();

        AnchorPane materialContent = materialContainerController.createView();
        Pane canvasContent = canvasContainerController.createView();
        AnchorPane componentContent = componentContainerController.createView();

        materialInsertionManager.setRootPane(rootPane);
        materialInsertionManager.setMainSplitter(mainSplitter);
        materialInsertionManager.setMaterialContent(materialContent);
        materialInsertionManager.setCanvasContent(canvasContent);
        materialInsertionManager.initialize();

        mainSplitter.getItems().add(materialContent);
        mainSplitter.getItems().add(canvasContent);
        mainSplitter.getItems().add(componentContent);
        mainSplitter.setDividerPositions(0.175, 0.75);
        SplitPane.setResizableWithParent(materialContent, false);
        SplitPane.setResizableWithParent(componentContent, false);
    }
}
