package org.kok202.deepblock.application.content;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.TabPane;
import lombok.Getter;
import org.kok202.deepblock.application.common.AbstractController;
import org.kok202.deepblock.application.content.model.TabModelDesignController;
import org.kok202.deepblock.application.content.model.TabModelTestController;
import org.kok202.deepblock.application.content.model.TabModelTrainController;

@Getter
public class TabsController extends AbstractController {
    private TabModelDesignController tabModelDesignController;
    private TabModelTrainController TabModelTrainController;
    private TabModelTestController TabModelTestController;

    public TabPane createView() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/tabs.fxml"));
        fxmlLoader.setController(this);
        TabPane tabPane = fxmlLoader.load();

        tabModelDesignController = new TabModelDesignController();
        TabModelTrainController = new TabModelTrainController();
        TabModelTestController = new TabModelTestController();
        tabPane.getTabs().add(tabModelDesignController.createView());
        tabPane.getTabs().add(TabModelTrainController.createView());
        tabPane.getTabs().add(TabModelTestController.createView());
        return tabPane;
    }

    @Override
    protected void initialize() throws Exception {
    }
}
