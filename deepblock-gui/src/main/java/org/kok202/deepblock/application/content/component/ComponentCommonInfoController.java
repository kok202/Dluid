package org.kok202.deepblock.application.content.component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.kok202.deepblock.CanvasFacade;
import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.application.singleton.AppPropertiesSingleton;
import org.kok202.deepblock.application.singleton.AppWidgetSingleton;

public class ComponentCommonInfoController extends AbstractLayerComponentController {
    @FXML private Label labelLayerId;
    @FXML private Label labelLayerType;
    @FXML private TextField textFieldLayerId;
    @FXML private TextField textFieldLayerType;
    @FXML private Button buttonDelete;

    public ComponentCommonInfoController(Layer layer) {
        super(layer);
    }

    @Override
    public AnchorPane createView() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/component/common_info.fxml"));
        fxmlLoader.setController(this);
        return fxmlLoader.load();
    }

    @Override
    protected void initialize() throws Exception {
        textFieldLayerId.setText(String.valueOf(layer.getId()));
        textFieldLayerType.setText(layer.getType().toString());
        buttonDelete.setOnAction((event -> {
            CanvasFacade.removeGraphNode(layer.getId());
            AppWidgetSingleton.getInstance()
                    .getComponentContainerController()
                    .getComponentManager()
                    .clearComponentContainer();
            CanvasFacade.alignBlockNode();
        }));
        titledPane.setText(AppPropertiesSingleton.getInstance().get("frame.component.common.title"));
        labelLayerId.setText(AppPropertiesSingleton.getInstance().get("frame.component.common.layerId"));
        labelLayerType.setText(AppPropertiesSingleton.getInstance().get("frame.component.common.layerType"));
        buttonDelete.setText(AppPropertiesSingleton.getInstance().get("frame.component.common.delete"));
    }
}
