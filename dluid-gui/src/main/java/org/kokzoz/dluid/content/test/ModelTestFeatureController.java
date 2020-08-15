package org.kokzoz.dluid.content.test;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.Data;
import org.kokzoz.dluid.content.TabModelTestController;

@Data
public class ModelTestFeatureController extends AbstractModelTestController {
    @FXML private Accordion accordion;
    @FXML private VBox container;

    private ModelTestFeatureFileLoaderController modelTestFeatureFileLoaderController;
    private ModelTestFeatureRandomGeneratorController modelTestFeatureRandomGeneratorController;
    private ModelTestFeatureTableController modelTestFeatureTableController;

    public ModelTestFeatureController(TabModelTestController tabModelTestController) {
        super(tabModelTestController);
    }

    public AnchorPane createView() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/test/feature.fxml"));
        fxmlLoader.setController(this);
        AnchorPane content = fxmlLoader.load();
        return content;
    }

    @Override
    protected void initialize() throws Exception {
        modelTestFeatureFileLoaderController = new ModelTestFeatureFileLoaderController(getTabModelTestController());
        modelTestFeatureRandomGeneratorController = new ModelTestFeatureRandomGeneratorController(getTabModelTestController());
        modelTestFeatureTableController = new ModelTestFeatureTableController(getTabModelTestController());

        modelTestFeatureFileLoaderController.createView();
        modelTestFeatureRandomGeneratorController.createView();
        modelTestFeatureTableController.createView();

        accordion.getPanes().add(modelTestFeatureFileLoaderController.getTitledPane());
        container.getChildren().add(modelTestFeatureTableController.getTitledPane());
    }
}
