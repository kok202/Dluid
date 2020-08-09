package org.kokzoz.dluid.common;

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
