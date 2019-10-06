package org.kok202.deepblock.application.adapter.file;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.DirectoryChooser;
import lombok.Data;
import org.kok202.deepblock.application.global.AppWidgetSingleton;

import java.io.File;
import java.util.function.Consumer;

@Data
public class DirectoryChooserAdapter {
    private Button button;
    private Runnable callbackBeforeFind;
    private Consumer<File> callbackAfterFind;

    public DirectoryChooserAdapter(Button button) {
        this.button = button;
    }

    public void initialize(){
        setOnButtonListener();
    }

    private void setOnButtonListener(){
        button.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg0) {
                runCallbackBeforeFind();
                DirectoryChooser directoryChooser = new DirectoryChooser();
                directoryChooser.setTitle("Select folder");
                directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));

                File file = directoryChooser.showDialog(AppWidgetSingleton.getInstance().getPrimaryStage());
                if(file != null){
                    runCallbackAfterFind(file);
                }
            }
        });
    }

    public void runCallbackBeforeFind() {
        if(callbackBeforeFind == null)
            return;
        callbackBeforeFind.run();
    }

    public void runCallbackAfterFind(File file) {
        if(callbackAfterFind == null)
            return;
        callbackAfterFind.accept(file);
    }
}
