package org.kokzoz.dluid.content.test;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import lombok.Data;
import org.kokzoz.dluid.adapter.file.TestFeatureFileFinder;
import org.kokzoz.dluid.content.TabModelTestController;
import org.kokzoz.dluid.singleton.AppPropertiesSingleton;

@Data
public class ModelTestFeatureFileLoaderController extends AbstractModelTestController {
    @FXML private TitledPane titledPane;
    @FXML private Label labelTestDataFromFile;
    @FXML private TextField textFieldFindTestData;
    @FXML private Button buttonFindTestData;

    private TestFeatureFileFinder testFeatureFileFinder;

    public ModelTestFeatureFileLoaderController(TabModelTestController tabModelTestController) {
        super(tabModelTestController);
    }

    public AnchorPane createView() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/test/feature_file_loader.fxml"));
        fxmlLoader.setController(this);
        AnchorPane content = fxmlLoader.load();
        return content;
    }

    @Override
    protected void initialize() throws Exception {
        setButtonFinderActionHandler();
        titledPane.setText(AppPropertiesSingleton.getInstance().get("frame.testTab.dataSetting.dataLoad.title"));
        labelTestDataFromFile.setText(AppPropertiesSingleton.getInstance().get("frame.testTab.dataSetting.dataLoad.label"));
    }

    private void setButtonFinderActionHandler(){
        testFeatureFileFinder = new TestFeatureFileFinder(textFieldFindTestData, buttonFindTestData);
        testFeatureFileFinder.initialize();
        testFeatureFileFinder.setCallbackAfterLoad(() -> getTabModelTestController()
                .getModelTestFeatureController()
                .getModelTestFeatureTableController()
                .refreshTableView());
    }
}
