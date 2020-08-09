package org.kokzoz.dluid.adapter.file;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import lombok.Data;
import org.kokzoz.dluid.singleton.AppSingleton;

import java.io.File;
import java.util.ArrayList;

@Data
public abstract class FileFinder {
    private boolean textFieldEditable = false;
    private TextField textField;
    private Button button;
    private Runnable callbackBeforeFind;
    private Runnable callbackAfterFind;
    private ArrayList<FileChooser.ExtensionFilter> extensionFilter;

    public FileFinder(TextField textField, Button button) {
        this.textField = textField;
        this.button = button;
        extensionFilter = new ArrayList<>();
    }

    public void initialize(){
        textField.setEditable(false);
        setOnButtonListener();
    }

    private void setOnButtonListener(){
        button.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg0) {
                runCallbackBeforeFind();
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Select data set.");
                fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
                fileChooser.getExtensionFilters().addAll(extensionFilter);

                File file = fileChooser.showOpenDialog(AppSingleton.getInstance().getPrimaryStage());
                if(file != null){
                    textField.clear();
                    textField.setText(file.getAbsolutePath());
                    runCallbackAfterFind();
                }
            }
        });
    }

    public void setText(String path){
        textFieldEditable = true;
        textField.setText(path);
        textFieldEditable = false;
    }

    public void runCallbackBeforeFind() {
        if(callbackBeforeFind == null)
            return;
        callbackBeforeFind.run();
    }

    public void runCallbackAfterFind() {
        if(callbackAfterFind == null)
            return;
        callbackAfterFind.run();
    }
}
