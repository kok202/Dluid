package org.kok202.dluid.application.content.model.test;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.Data;
import org.kok202.dluid.application.content.model.TabModelTestController;

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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/model/test/feature.fxml"));
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
        modelTestFeatureTableController.getTitledPane().setExpanded(false);

        accordion.getPanes().add(modelTestFeatureFileLoaderController.getTitledPane());
        accordion.getPanes().add(modelTestFeatureRandomGeneratorController.getTitledPane());
        // accordion.setExpandedPane(modelTestFeatureFileLoaderController.getTitledPane()); // Not using looks more better
        container.getChildren().add(modelTestFeatureTableController.getTitledPane());
    }
}
