package org.kok202.dluid.application.content.test;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import lombok.Data;
import org.kok202.dluid.ai.AIFacade;
import org.kok202.dluid.application.adapter.NumericTableViewAdapter;
import org.kok202.dluid.application.adapter.file.DirectoryChooserAdapter;
import org.kok202.dluid.application.adapter.file.TestResultDocumentFileSaver;
import org.kok202.dluid.application.adapter.file.TestResultImageSaver;
import org.kok202.dluid.application.content.TabModelTestController;
import org.kok202.dluid.application.singleton.AppPropertiesSingleton;
import org.kok202.dluid.domain.stream.NumericRecordSet;

@Data
public class ModelTestTestingResultTableController extends AbstractModelTestController {

    @FXML private TitledPane titledPane;
    @FXML private TableView tableViewResultSet;
    @FXML private Button buttonExportAsImage;
    @FXML private Button buttonExportAsDocument;
    private NumericTableViewAdapter numericTableViewAdapter;
    private DirectoryChooserAdapter testResultImageDirectoryChooser;
    private TestResultDocumentFileSaver testResultDocumentFileSaver;

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
        numericTableViewAdapter = new NumericTableViewAdapter(tableViewResultSet);
        setButtonSaverActionHandler();
        titledPane.setText(AppPropertiesSingleton.getInstance().get("frame.testTab.testTask.result.title"));
        buttonExportAsImage.setText(AppPropertiesSingleton.getInstance().get("frame.testTab.testTask.result.saveAsImage"));
        buttonExportAsDocument.setText(AppPropertiesSingleton.getInstance().get("frame.testTab.testTask.result.saveAsDocument"));
    }

    private void setButtonSaverActionHandler(){
        testResultImageDirectoryChooser = new DirectoryChooserAdapter(buttonExportAsImage);
        testResultImageDirectoryChooser.setCallbackBeforeFind(this::convertTableViewToResultDataSet);
        testResultImageDirectoryChooser.setCallbackAfterFind(file -> new TestResultImageSaver().popUpWindow(file));
        testResultImageDirectoryChooser.initialize();
        testResultDocumentFileSaver = new TestResultDocumentFileSaver(buttonExportAsDocument);
        testResultDocumentFileSaver.setCallbackBeforeSave(this::convertTableViewToResultDataSet);
        testResultDocumentFileSaver.initialize();
    }

    public void refreshTableView(){
        NumericRecordSet testNumericRecordSet = AIFacade.getTestResultSet().getNumericRecordSet();
        numericTableViewAdapter.setRecordSetAndRefresh(testNumericRecordSet);
        titledPane.setExpanded(true);
    }

    private void convertTableViewToResultDataSet(){
        AIFacade.getTestResultSet().setNumericRecordSet(numericTableViewAdapter.toNumericRecordSet());
    }
}
