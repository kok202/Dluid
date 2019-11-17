package org.kok202.deepblock.application.content.model.test;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import lombok.Data;
import org.kok202.deepblock.ai.AIFacade;
import org.kok202.deepblock.application.adapter.NumericTableViewAdapter;
import org.kok202.deepblock.application.adapter.file.DirectoryChooserAdapter;
import org.kok202.deepblock.application.adapter.file.TestResultDocumentFileSaver;
import org.kok202.deepblock.application.adapter.file.TestResultImageSaver;
import org.kok202.deepblock.application.content.model.TabModelTestController;
import org.kok202.deepblock.domain.stream.NumericRecordSet;
import org.kok202.deepblock.domain.stream.StringRecordSet;

@Data
public class ModelTestResultSetController extends AbstractModelTestController {

    @FXML private TitledPane tiltedPane;
    @FXML private TableView tableViewResultSet;
    @FXML private Button buttonExportAsImage;
    @FXML private Button buttonExportAsDocument;
    private NumericTableViewAdapter numericTableViewAdapter;
    private DirectoryChooserAdapter testResultImageDirectoryChooser;
    private TestResultDocumentFileSaver testResultDocumentFileSaver;

    public ModelTestResultSetController(TabModelTestController tabModelTestController) {
        super(tabModelTestController);
    }

    public AnchorPane createView() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/model/test/resultset.fxml"));
        fxmlLoader.setController(this);
        AnchorPane content = fxmlLoader.load();
        return content;
    }

    @Override
    protected void initialize() throws Exception {
        numericTableViewAdapter = new NumericTableViewAdapter(tableViewResultSet);
        setButtonSaverActionHandler();
        // FIXME : just for test. need to delete
        forTestData();
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
    }

    private void convertTableViewToResultDataSet(){
        AIFacade.getTestResultSet().setNumericRecordSet(numericTableViewAdapter.toNumericRecordSet());
    }

    @Deprecated
    public void forTestData(){
        StringRecordSet stringRecordSet = new StringRecordSet();
        stringRecordSet.setHeader(
                "(0,0)r", "(0,0)g", "(0,0)b", "(1,0)r", "(1,0)g", "(1,0)b", "(2,0)r", "(2,0)g", "(2,0)b",
                "(0,1)r", "(0,1)g", "(0,1)b", "(1,1)r", "(1,1)g", "(1,1)b", "(2,1)r", "(2,1)g", "(2,1)b",
                "(0,2)r", "(0,2)g", "(0,2)b", "(1,2)r", "(1,2)g", "(1,2)b", "(2,2)r", "(2,2)g", "(2,2)b");
        stringRecordSet.addRecord(
                "1", "0", "0", "0", "1", "0", "0", "0", "1",
                "0.5", "0", "0", "0", "0.5", "0", "0", "0", "0.5",
                "0.1", "0", "0", "0", "0.1", "0", "0", "0", "0.1");
        stringRecordSet.addRecord(
                "0.5", "0", "0", "0", "0.5", "0", "0", "0", "0.5",
                "1", "0", "0", "0", "1", "0", "0", "0", "1",
                "0.1", "0", "0", "0", "0.1", "0", "0", "0", "0.1");
        stringRecordSet.addRecord(
                "0.1", "0", "0", "0", "0.1", "0", "0", "0", "0.1",
                "1", "0", "0", "0", "1", "0", "0", "0", "1",
                "0.5", "0", "0", "0", "0.5", "0", "0", "0", "0.5");
        NumericRecordSet numericRecordSet = NumericRecordSet.convertFrom(stringRecordSet);
        numericTableViewAdapter.setRecordSetAndRefresh(numericRecordSet);
    }
}
