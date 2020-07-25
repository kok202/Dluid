package org.kok202.dluid.content.design.component;

import javafx.fxml.FXML;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import org.kok202.dluid.common.AbstractController;

public abstract class AbstractComponentController extends AbstractController {
    @FXML
    protected TitledPane titledPane;

    public abstract AnchorPane createView() throws Exception;
}
