package org.kokzoz.dluid.adapter.file;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import org.apache.commons.lang3.StringUtils;
import org.kokzoz.dluid.adapter.PopUpExtension;
import org.kokzoz.dluid.ai.singleton.structure.ManagedRecordSet;
import org.kokzoz.dluid.content.popup.CsvManagementPopUpController;
import org.kokzoz.dluid.content.popup.ExcelManagementPopUpController;
import org.kokzoz.dluid.singleton.AppPropertiesSingleton;

public abstract class ExtendedFileFinder extends FileFinder{

    private PopUpExtension popUpExtension;
    private Runnable callbackAfterLoad;

    public ExtendedFileFinder(TextField textField, Button button) {
        super(textField, button);
        getExtensionFilter().add(new FileChooser.ExtensionFilter("All files", "*.*"));
        getExtensionFilter().add(new FileChooser.ExtensionFilter("csv", "*.csv"));
        getExtensionFilter().add(new FileChooser.ExtensionFilter("xls", "*.xls"));
        getExtensionFilter().add(new FileChooser.ExtensionFilter("xlsx", "*.xlsx"));
        textField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if(!StringUtils.isEmpty(newValue) && !isTextFieldEditable())
                popUpWindow();
        });
    }

    private void popUpWindow(){
        try {
            String filePath = getTextField().getText();
            getManagedRecordSet().setFilePath(filePath);
            if(filePath.endsWith(".xls") || filePath.endsWith(".xlsx")){
                popUpExtension = new PopUpExtension();
                popUpExtension
                        .setTitle(AppPropertiesSingleton.getInstance().get("frame.file.load.excel.title"))
                        .setWidth(400)
                        .setHeight(200)
                        .setPopUpSceneRoot(new ExcelManagementPopUpController(this).createView())
                        .setCallbackAfterLoad(callbackAfterLoad)
                        .show();
            }
            else if(filePath.endsWith(".csv")) {
                popUpExtension = new PopUpExtension();
                popUpExtension
                        .setTitle(AppPropertiesSingleton.getInstance().get("frame.file.load.csv.title"))
                        .setWidth(400)
                        .setHeight(200)
                        .setPopUpSceneRoot(new CsvManagementPopUpController(this).createView())
                        .setCallbackAfterLoad(callbackAfterLoad)
                        .show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closePopUpExtensionWithLoad() {
        getTextField().setDisable(true);
        popUpExtension.load();
    }

    public void closePopUpExtension() {
        popUpExtension.cancel();
    }

    public abstract ManagedRecordSet getManagedRecordSet();

    public void setCallbackAfterLoad(Runnable callbackAfterLoad) {
        this.callbackAfterLoad = callbackAfterLoad;
    }
}
