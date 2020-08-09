package org.kokzoz.dluid.content.popup;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.kokzoz.dluid.adapter.file.ExtendedFileFinder;
import org.kokzoz.dluid.domain.exception.ExcelIllegalDataFormatException;
import org.kokzoz.dluid.domain.exception.ExcelPositionOutOfTableException;
import org.kokzoz.dluid.domain.stream.NumericRecordSet;
import org.kokzoz.dluid.domain.stream.StringRecordSet;
import org.kokzoz.dluid.domain.stream.excel.ExcelReader;
import org.kokzoz.dluid.singleton.AppPropertiesSingleton;
import org.kokzoz.dluid.util.TextFieldUtil;

public class ExcelManagementPopUpController extends FileManagementPopUpController {
    @FXML private CheckBox checkBoxHeaderExist;
    @FXML private Label labelDataStart;
    @FXML private Label labelDataEnd;

    @FXML private TextField textFieldDataStart;
    @FXML private TextField textFieldDataEnd;
    @FXML private Button buttonLoadData;
    @FXML private Button buttonCancel;

    public ExcelManagementPopUpController(ExtendedFileFinder extendedFileFinder) {
        super(extendedFileFinder);
    }

    public AnchorPane createView() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/popup/file_load_setting_excel.fxml"));
        fxmlLoader.setController(this);
        return fxmlLoader.load();
    }

    @Override
    protected void initialize() throws Exception {
        boolean hasHeader = getExtendedFileFinder().getManagedRecordSet().isHasHeader();
        String dataCellStart = getExtendedFileFinder().getManagedRecordSet().getExcelManagedData().getDataCellStart();
        String dataCellEnd = getExtendedFileFinder().getManagedRecordSet().getExcelManagedData().getDataCellEnd();

        checkBoxHeaderExist.setSelected(hasHeader);
        textFieldDataStart.setText(dataCellStart);
        textFieldDataEnd.setText(dataCellEnd);
        buttonLoadData.setOnAction(event -> buttonLoadActionHandler());
        buttonCancel.setOnAction(event -> buttonCancelHandler());

        checkBoxHeaderExist.setText(AppPropertiesSingleton.getInstance().get("frame.file.load.headerExist"));
        labelDataStart.setText(AppPropertiesSingleton.getInstance().get("frame.file.load.dataStart"));
        labelDataEnd.setText(AppPropertiesSingleton.getInstance().get("frame.file.load.dataEnd"));
        buttonLoadData.setText(AppPropertiesSingleton.getInstance().get("frame.file.load.load"));
        buttonCancel.setText(AppPropertiesSingleton.getInstance().get("frame.file.load.cancel"));
    }

    private void buttonLoadActionHandler(){
        String dataCellStart = textFieldDataStart.getText();
        String dataCellEnd = textFieldDataEnd.getText();
        boolean hasHeader = checkBoxHeaderExist.isSelected();

        if(!TextFieldUtil.validateExcelCell(dataCellStart) ||
           !TextFieldUtil.validateExcelCell(dataCellEnd)){
            throw new ExcelIllegalDataFormatException();
        }

        try{
            String filePath = getExtendedFileFinder().getManagedRecordSet().getFilePath();
            ExcelReader excelDataReader = new ExcelReader(dataCellStart, dataCellEnd, hasHeader);
            StringRecordSet stringRecordSet = excelDataReader.read(filePath);
            NumericRecordSet numericRecordSet = NumericRecordSet.convertFrom(stringRecordSet);

            getExtendedFileFinder().getManagedRecordSet().setHasHeader(hasHeader);
            getExtendedFileFinder().getManagedRecordSet().getExcelManagedData().setDataCellStart(dataCellStart);
            getExtendedFileFinder().getManagedRecordSet().getExcelManagedData().setDataCellEnd(dataCellEnd);
            getExtendedFileFinder().getManagedRecordSet().setRecordSize(numericRecordSet.getRecords().size());
            getExtendedFileFinder().getManagedRecordSet().setNumericRecordSet(numericRecordSet);
            getExtendedFileFinder().closePopUpExtensionWithLoad();
        }catch (Exception exception){
            throw new ExcelPositionOutOfTableException();
        }
    }

    private void buttonCancelHandler() {
        getExtendedFileFinder().closePopUpExtension();
    }
}
