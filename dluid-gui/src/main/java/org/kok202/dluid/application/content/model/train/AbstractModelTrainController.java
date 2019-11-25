package org.kok202.dluid.application.content.model.train;

import javafx.fxml.FXML;
import javafx.scene.control.TitledPane;
import lombok.Data;
import org.kok202.dluid.application.common.AbstractController;

@Data
public abstract class AbstractModelTrainController extends AbstractController {
    @FXML
    private TitledPane titledPane;
}
