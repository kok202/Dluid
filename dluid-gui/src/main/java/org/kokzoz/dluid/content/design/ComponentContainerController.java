package org.kokzoz.dluid.content.design;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import org.kokzoz.dluid.AppFacade;
import org.kokzoz.dluid.canvas.CanvasFacade;
import org.kokzoz.dluid.common.AbstractController;
import org.kokzoz.dluid.content.design.component.ComponentManager;
import org.kokzoz.dluid.reducer.RefreshComponentListReducer;

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
        AppFacade.addReducer(new RefreshComponentListReducer(this));
        CanvasFacade.addReducer(new RefreshComponentListReducer(this));
    }
}
