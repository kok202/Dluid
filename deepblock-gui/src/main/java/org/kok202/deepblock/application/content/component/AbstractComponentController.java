package org.kok202.deepblock.application.content.component;

import javafx.fxml.FXML;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import org.kok202.deepblock.application.common.AbstractController;

public abstract class AbstractComponentController extends AbstractController {
    @FXML
    private TitledPane titledPane;

    public abstract AnchorPane createView() throws Exception;
}
