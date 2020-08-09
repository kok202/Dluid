package org.kokzoz.dluid.content.train;

import javafx.fxml.FXML;
import javafx.scene.control.TitledPane;
import lombok.Data;
import org.kokzoz.dluid.common.AbstractController;

@Data
public abstract class AbstractModelTrainController extends AbstractController {
    @FXML
    private TitledPane titledPane;
}
