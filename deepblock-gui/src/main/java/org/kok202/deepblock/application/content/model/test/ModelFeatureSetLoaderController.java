package org.kok202.deepblock.application.content.model.test;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lombok.Data;
import org.kok202.deepblock.application.adapter.file.TestFeatureFileFinder;
import org.kok202.deepblock.application.content.model.TabModelTestController;

@Data
public class ModelFeatureSetLoaderController extends AbstractModelTestController {
    @FXML private Label labelTestDataFromFile;
    @FXML private TextField textFieldFindTestData;
    @FXML private Button buttonFindTestData;

    public ModelFeatureSetLoaderController(TabModelTestController tabModelTestController) {
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
        setButtonFinderActionHandler();
    }

    private void setButtonFinderActionHandler(){
        TestFeatureFileFinder testFeatureFileFinder = new TestFeatureFileFinder(textFieldFindTestData, buttonFindTestData);
        testFeatureFileFinder.initialize();
        testFeatureFileFinder.setCallbackAfterLoad(() -> getTabModelTestController().getModelFeatureSetController().refreshTableView());
    }
}
