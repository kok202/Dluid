package org.kokzoz.dluid.menu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;
import org.kokzoz.dluid.common.AbstractController;

public class MenuBarController extends AbstractController {
    @FXML
    @Getter
    private MenuBar menuBar;

    public AnchorPane createView() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/menu/menu_bar.fxml"));
        fxmlLoader.setController(this);
        return fxmlLoader.load();
    }

    @Override
    protected void initialize() throws Exception {
        menuBar.getMenus().add(new MenuFileController().createView());
        menuBar.getMenus().add(new MenuSettingController().createView());
    }
}
