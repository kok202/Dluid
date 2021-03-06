package org.kokzoz.dluid.menu;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import lombok.Getter;
import org.kokzoz.dluid.AppFacade;
import org.kokzoz.dluid.canvas.CanvasFacade;
import org.kokzoz.dluid.common.AbstractMenuController;
import org.kokzoz.dluid.domain.action.ActionType;
import org.kokzoz.dluid.singleton.AppPropertiesSingleton;

@Getter
public class MenuFileController extends AbstractMenuController {
    @FXML
    private MenuItem menuItemFileNew;
    @FXML
    private MenuItem menuItemFileExit;

    public Menu createView() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/menu/menu_file.fxml"));
        fxmlLoader.setController(this);
        return fxmlLoader.load();
    }

    @Override
    protected void initialize() throws Exception {
        menuItemFileNew.setOnAction(event->{
            CanvasFacade.dispatchAction(ActionType.REMOVE_ALL_GRAPH_NODE);
            AppFacade.dispatchAction(ActionType.REFRESH_COMPONENT_LIST);
        });
        menuItemFileExit.setOnAction(event->{
            Platform.exit();
            System.exit(0);
        });

        itself.setText(AppPropertiesSingleton.getInstance().get("frame.menu.file"));
        menuItemFileNew.setText(AppPropertiesSingleton.getInstance().get("frame.menu.file.new"));
        menuItemFileExit.setText(AppPropertiesSingleton.getInstance().get("frame.menu.file.exit"));

    }
}
