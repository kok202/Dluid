package org.kok202.dluid.application.menu;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuBar;
import org.kok202.dluid.application.common.AbstractController;

public class MenuController extends AbstractController {
    public MenuBar createView() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/menu/menu.fxml"));
        fxmlLoader.setController(this);
        return fxmlLoader.load();
    }

    @Override
    protected void initialize() throws Exception {

    }
}
