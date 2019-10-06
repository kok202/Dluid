package org.kok202.deepblock.application.content;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;
import org.kok202.deepblock.application.common.AbstractController;
import org.kok202.deepblock.application.content.material.insertion.MaterialInsertionManager;

@Getter
public class ContentRootController extends AbstractController {
    private SplitPane mainSplitter;
    private TabsController tabsController;
    private MaterialContainerController materialContainerController;
    private ComponentContainerController componentContainerController;

    private TabPane tabContent;
    private AnchorPane materialContent;
    private AnchorPane detailContent;

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
        MaterialInsertionManager materialInsertionManager = new MaterialInsertionManager();
        mainSplitter = (SplitPane) rootPane.lookup("#mainSplitter");

        materialContainerController = new MaterialContainerController(materialInsertionManager);
        materialContent = materialContainerController.createView();

        tabsController = new TabsController(materialInsertionManager);
        tabContent = tabsController.createView();

        componentContainerController = new ComponentContainerController();
        detailContent = componentContainerController.createView();

        materialInsertionManager.setRootPane(rootPane);
        materialInsertionManager.setMainSplitter(mainSplitter);
        materialInsertionManager.setMaterialContent(materialContent);
        materialInsertionManager.setTabContent(tabContent);
        materialInsertionManager.initialize();

        mainSplitter.getItems().add(materialContent);
        mainSplitter.getItems().add(tabContent);
        mainSplitter.getItems().add(detailContent);
        mainSplitter.setDividerPositions(0.18, 0.8, 0.3);
        SplitPane.setResizableWithParent(materialContent, false);
        SplitPane.setResizableWithParent(detailContent, false);
    }


}
