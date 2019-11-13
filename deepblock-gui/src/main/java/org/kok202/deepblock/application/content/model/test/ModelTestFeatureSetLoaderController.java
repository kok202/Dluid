package org.kok202.deepblock.application.content.model.test;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import lombok.Data;
import org.kok202.deepblock.application.content.model.TabModelTestController;

@Data
public class ModelTestFeatureSetLoaderController extends AbstractModelTestController {
    @FXML private TitledPane tiltedPane;
    @FXML private Accordion accordion;

    public ModelTestFeatureSetLoaderController(TabModelTestController tabModelTestController) {
        super(tabModelTestController);
    }

    public AnchorPane createView() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/model/test/featureset_loader.fxml"));
        fxmlLoader.setController(this);
        AnchorPane content = fxmlLoader.load();
        return content;
    }

    @Override
    protected void initialize() throws Exception {
        ModelTestFeatureSetFileLoaderController modelTestFeatureSetFileLoaderController = new ModelTestFeatureSetFileLoaderController(getTabModelTestController());
        ModelTestFeatureSetRandomGeneratorController modelTestFeatureSetRandomGeneratorController = new ModelTestFeatureSetRandomGeneratorController(getTabModelTestController());
        modelTestFeatureSetFileLoaderController.createView();
        modelTestFeatureSetRandomGeneratorController.createView();
        accordion.getPanes().add(modelTestFeatureSetFileLoaderController.getTiltedPane());
        accordion.getPanes().add(modelTestFeatureSetRandomGeneratorController.getTiltedPane());
    }
}
