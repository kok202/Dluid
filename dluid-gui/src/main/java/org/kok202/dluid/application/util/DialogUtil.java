package org.kok202.dluid.application.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import lombok.Builder;

public class DialogUtil {
    private Dialog dialog;

    @Builder
    public DialogUtil(Alert.AlertType alertType,
                      String title,
                      String headerText,
                      String contentText){
        dialog = new Alert(alertType);
        dialog.setTitle(title);
        dialog.setHeaderText(headerText);
        dialog.setContentText(contentText);
    }

    public void showAndWait(){
        dialog.showAndWait();
    }
}
