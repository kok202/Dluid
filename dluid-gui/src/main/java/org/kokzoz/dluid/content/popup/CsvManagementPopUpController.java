package org.kokzoz.dluid.content.popup;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.kokzoz.dluid.adapter.file.ExtendedFileFinder;
import org.kokzoz.dluid.domain.stream.NumericRecordSet;
import org.kokzoz.dluid.domain.stream.StringRecordSet;
import org.kokzoz.dluid.domain.stream.csv.CsvReader;
import org.kokzoz.dluid.singleton.AppPropertiesSingleton;
import org.kokzoz.dluid.util.TextFieldUtil;

public class CsvManagementPopUpController extends FileManagementPopUpController {
    @FXML private CheckBox checkBoxHeaderExist;
    @FXML private Label labelDataStart;
    @FXML private Label labelDataEnd;

    @FXML private TextField textFieldDataStart;
    @FXML private TextField textFieldDataEnd;
    @FXML private Button buttonLoadData;
    @FXML private Button buttonCancel;

    public CsvManagementPopUpController(ExtendedFileFinder extendedFileFinder) {
        super(extendedFileFinder);
    }

    public AnchorPane createView() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/popup/file_load_setting_csv.fxml"));
        fxmlLoader.setController(this);
        return fxmlLoader.load();
    }

    @Override
    protected void initialize() throws Exception {
        TextFieldUtil.applyPositiveIntegerFilter(textFieldDataStart, 1);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldDataEnd, 1);

        boolean hasHeader = getExtendedFileFinder().getManagedRecordSet().isHasHeader();
        int dataCellStart = getExtendedFileFinder().getManagedRecordSet().getCsvManagedData().getDataColStart();
        int dataCellEnd = getExtendedFileFinder().getManagedRecordSet().getCsvManagedData().getDataColEnd();

        checkBoxHeaderExist.setSelected(hasHeader);
        textFieldDataStart.setText(String.valueOf(dataCellStart));
        textFieldDataEnd.setText(String.valueOf(dataCellEnd));
        buttonLoadData.setOnAction(event -> buttonLoadActionHandler());
        buttonCancel.setOnAction(event -> buttonCancelHandler());

        checkBoxHeaderExist.setText(AppPropertiesSingleton.getInstance().get("frame.file.load.headerExist"));
        labelDataStart.setText(AppPropertiesSingleton.getInstance().get("frame.file.load.dataStart"));
        labelDataEnd.setText(AppPropertiesSingleton.getInstance().get("frame.file.load.dataEnd"));
        buttonLoadData.setText(AppPropertiesSingleton.getInstance().get("frame.file.load.load"));
        buttonCancel.setText(AppPropertiesSingleton.getInstance().get("frame.file.load.cancel"));
    }

    private void buttonLoadActionHandler(){
        int dataColStart = TextFieldUtil.parseInteger(textFieldDataStart, 1);
        int dataColEnd = TextFieldUtil.parseInteger(textFieldDataEnd, dataColStart);
        boolean hasHeader = checkBoxHeaderExist.isSelected();

        String filePath = getExtendedFileFinder().getManagedRecordSet().getFilePath();
        CsvReader csvDataReader = new CsvReader(dataColStart, dataColEnd, hasHeader);
        StringRecordSet stringRecordSet = csvDataReader.read(filePath);
        NumericRecordSet numericRecordSet = NumericRecordSet.convertFrom(stringRecordSet);

        getExtendedFileFinder().getManagedRecordSet().setHasHeader(hasHeader);
        getExtendedFileFinder().getManagedRecordSet().getCsvManagedData().setDataColStart(dataColStart);
        getExtendedFileFinder().getManagedRecordSet().getCsvManagedData().setDataColEnd(dataColEnd);
        getExtendedFileFinder().getManagedRecordSet().setRecordSize(numericRecordSet.getRecords().size());
        getExtendedFileFinder().getManagedRecordSet().setNumericRecordSet(numericRecordSet);
        getExtendedFileFinder().closePopUpExtensionWithLoad();
    }

    private void buttonCancelHandler() {
        getExtendedFileFinder().closePopUpExtension();
    }
}
