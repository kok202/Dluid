package org.kok202.dluid.application.common;

import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import lombok.Getter;

public abstract class AbstractMenuController {
    @FXML
    @Getter
    protected Menu itself;

    @FXML
    protected abstract void initialize() throws Exception;
}
