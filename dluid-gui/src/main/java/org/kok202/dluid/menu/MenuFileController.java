package org.kok202.dluid.menu;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import lombok.Getter;
import org.kok202.dluid.AppFacade;
import org.kok202.dluid.canvas.CanvasFacade;
import org.kok202.dluid.common.AbstractMenuController;
import org.kok202.dluid.domain.action.ActionType;
import org.kok202.dluid.singleton.AppPropertiesSingleton;

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
            CanvasFacade.removeAllGraphNode();
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
