package org.kokzoz.dluid.content.test;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import lombok.Data;
import org.kokzoz.dluid.adapter.NumericTableViewAdapter;
import org.kokzoz.dluid.adapter.file.TestResultDocumentFileSaver;
import org.kokzoz.dluid.ai.AIFacade;
import org.kokzoz.dluid.content.TabModelTestController;
import org.kokzoz.dluid.domain.stream.NumericRecordSet;
import org.kokzoz.dluid.singleton.AppPropertiesSingleton;

@Data
public class ModelTestTestingResultTableController extends AbstractModelTestController {

    @FXML private TitledPane titledPane;
    @FXML private TableView tableViewResultSet;
    @FXML private CheckBox checkBoxHighlight;
    @FXML private Button buttonLoadOnTable;
    @FXML private Button buttonExportAsDocument;
    private NumericTableViewAdapter numericTableViewAdapter;
    private TestResultDocumentFileSaver testResultDocumentFileSaver;

//    // TODO[v2.0.0] : Not a scope of first milestone [v1.0.0]
//    @FXML private Button buttonExportAsImage;
//    private DirectoryChooserAdapter testResultImageDirectoryChooser;

    public ModelTestTestingResultTableController(TabModelTestController tabModelTestController) {
        super(tabModelTestController);
    }

    public AnchorPane createView() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/test/testing_result_table.fxml"));
        fxmlLoader.setController(this);
        AnchorPane content = fxmlLoader.load();
        return content;
    }

    @Override
    protected void initialize() throws Exception {
        tableViewResultSet.setDisable(true);
        checkBoxHighlight.setSelected(false);
        checkBoxHighlight.selectedProperty().addListener((observable, oldValue, newValue) -> numericTableViewAdapter.setClassificationStyle(newValue));
        numericTableViewAdapter = NumericTableViewAdapter.builder()
                .tableView(tableViewResultSet)
                .editable(false)
                .build();
        setButtonActionHandler();
        titledPane.setText(AppPropertiesSingleton.getInstance().get("frame.testTab.testTask.result.title"));
        checkBoxHighlight.setText(AppPropertiesSingleton.getInstance().get("frame.testTab.testTask.result.classificationFilter"));
        buttonLoadOnTable.setText(AppPropertiesSingleton.getInstance().get("frame.testTab.testTask.result.loadOnTable"));
//        // TODO[v2.0.0] : Not a scope of first milestone [v1.0.0]
//        buttonExportAsImage.setText(AppPropertiesSingleton.getInstance().get("frame.testTab.testTask.result.saveAsImage"));
        buttonExportAsDocument.setText(AppPropertiesSingleton.getInstance().get("frame.testTab.testTask.result.saveAsDocument"));
    }

    private void setButtonActionHandler(){
        buttonLoadOnTable.setOnAction(event -> {
            NumericRecordSet testNumericRecordSet = AIFacade.getTestResultSet().getNumericRecordSet();
            numericTableViewAdapter.setRecordSetAndRefresh(testNumericRecordSet);
            checkBoxHighlight.setSelected(false);
            tableViewResultSet.setDisable(false);
        });
//        // TODO[v2.0.0] : Not a scope of first milestone [v1.0.0]
//        testResultImageDirectoryChooser = new DirectoryChooserAdapter(buttonExportAsImage);
//        testResultImageDirectoryChooser.setCallbackAfterFind(file -> new TestResultImageSaver().popUpWindow(file));
//        testResultImageDirectoryChooser.initialize();
        testResultDocumentFileSaver = new TestResultDocumentFileSaver(buttonExportAsDocument);
        testResultDocumentFileSaver.initialize();
    }

    public void clearTestResultTableView() {
        tableViewResultSet.setDisable(true);
        tableViewResultSet.getItems().clear();
    }
}
