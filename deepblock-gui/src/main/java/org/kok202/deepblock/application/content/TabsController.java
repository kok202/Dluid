package org.kok202.deepblock.application.content;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.TabPane;
import org.kok202.deepblock.application.common.AbstractController;
import org.kok202.deepblock.application.content.material.insertion.MaterialInsertionManager;
import org.kok202.deepblock.application.content.model.TabModelDesignController;
import org.kok202.deepblock.application.content.model.TabModelTestController;
import org.kok202.deepblock.application.content.model.TabModelTrainController;

public class TabsController extends AbstractController {
    private MaterialInsertionManager materialInsertionManager;

    public TabsController(MaterialInsertionManager materialInsertionManager) {
        this.materialInsertionManager = materialInsertionManager;
    }

    public TabPane createView() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/tabs.fxml"));
        fxmlLoader.setController(this);
        TabPane tabPane = fxmlLoader.load();

        tabPane.getTabs().add(new TabModelDesignController().createView());
        tabPane.getTabs().add(new TabModelTrainController().createView());
        tabPane.getTabs().add(new TabModelTestController().createView());
        return tabPane;
    }

    @Override
    protected void initialize() throws Exception {
    }
}
