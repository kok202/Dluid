package org.kok202.deepblock.application.content.model.test;

import javafx.fxml.FXML;
import javafx.scene.control.TitledPane;
import lombok.Data;
import org.kok202.deepblock.application.common.AbstractController;
import org.kok202.deepblock.application.content.model.TabModelTestController;

@Data
public abstract class AbstractModelTestController extends AbstractController {
    @FXML
    private TitledPane tiltedPane;

    private TabModelTestController tabModelTestController;

    public AbstractModelTestController(TabModelTestController tabModelTestController) {
        this.tabModelTestController = tabModelTestController;
    }
}
