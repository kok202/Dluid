package org.kok202.dluid.application.content;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;
import org.kok202.dluid.application.common.AbstractController;
import org.kok202.dluid.application.content.material.insertion.MaterialInsertionManager;

@Getter
public class ContentRootController extends AbstractController {
    private SplitPane mainSplitter;
    private MaterialContainerController materialContainerController;
    private TabsController tabsController;
    private ComponentContainerController componentContainerController;


    public AnchorPane createView() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/content_root.fxml"));
        fxmlLoader.setController(this);
        return fxmlLoader.load();
    }

    @Override
    protected void initialize() throws Exception {
        AnchorPane rootPane = initializeRootContent();
        initializeContent(rootPane);
    }

    private AnchorPane initializeRootContent(){
        return (AnchorPane) itself;
    }

    private void initializeContent(AnchorPane rootPane) throws Exception {
        mainSplitter = (SplitPane) rootPane.lookup("#mainSplitter");

        MaterialInsertionManager materialInsertionManager = new MaterialInsertionManager();
        materialContainerController = new MaterialContainerController(materialInsertionManager);
        tabsController = new TabsController();
        componentContainerController = new ComponentContainerController();
        AnchorPane materialContent = materialContainerController.createView();
        TabPane tabContent = tabsController.createView();
        AnchorPane detailContent = componentContainerController.createView();

        materialInsertionManager.setRootPane(rootPane);
        materialInsertionManager.setMainSplitter(mainSplitter);
        materialInsertionManager.setMaterialContent(materialContent);
        materialInsertionManager.setTabContent(tabContent);
        materialInsertionManager.initialize();

        mainSplitter.getItems().add(materialContent);
        mainSplitter.getItems().add(tabContent);
        mainSplitter.getItems().add(detailContent);
        mainSplitter.setDividerPositions(0.17, 0.75, 0.28);
        SplitPane.setResizableWithParent(materialContent, false);
        SplitPane.setResizableWithParent(detailContent, false);
    }


}
