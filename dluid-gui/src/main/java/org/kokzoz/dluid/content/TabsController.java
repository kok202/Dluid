package org.kokzoz.dluid.content;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;
import org.kokzoz.dluid.AppFacade;
import org.kokzoz.dluid.common.AbstractController;
import org.kokzoz.dluid.reducer.ModelEnableReducer;
import org.kokzoz.dluid.reducer.RefreshTrainingFileLoaderReducer;

@Getter
public class TabsController extends AbstractController {
    @FXML TabPane tabPane;
    private TabModelDesignController tabModelDesignController;
    private TabModelTrainController TabModelTrainController;
    private TabModelTestController TabModelTestController;

    public AnchorPane createView() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/tabs.fxml"));
        fxmlLoader.setController(this);
        return fxmlLoader.load();
    }

    @Override
    protected void initialize() throws Exception {
        tabModelDesignController = new TabModelDesignController();
        TabModelTrainController = new TabModelTrainController();
        TabModelTestController = new TabModelTestController();
        tabPane.getTabs().add(tabModelDesignController.createView());
        tabPane.getTabs().add(TabModelTrainController.createView());
        tabPane.getTabs().add(TabModelTestController.createView());
        AppFacade.addReducer(new ModelEnableReducer(this));
        AppFacade.addReducer(new RefreshTrainingFileLoaderReducer(this));
    }

}
