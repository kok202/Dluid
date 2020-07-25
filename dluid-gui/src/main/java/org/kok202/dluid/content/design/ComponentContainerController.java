package org.kok202.dluid.content.design;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import org.kok202.dluid.common.AbstractController;
import org.kok202.dluid.content.design.component.ComponentManager;

@Getter
public class ComponentContainerController extends AbstractController {

    @FXML private VBox container;
    private ComponentManager componentManager;

    public ComponentContainerController(){
        componentManager = new ComponentManager(this);
    }

    public AnchorPane createView() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/design/component_container.fxml"));
        fxmlLoader.setController(this);
        return fxmlLoader.load();
    }

    @Override
    protected void initialize() throws Exception {
    }
}
