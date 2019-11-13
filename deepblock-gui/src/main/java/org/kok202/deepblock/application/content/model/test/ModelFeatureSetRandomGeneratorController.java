package org.kok202.deepblock.application.content.model.test;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import lombok.Data;
import org.kok202.deepblock.application.content.model.TabModelTestController;

@Data
public class ModelFeatureSetRandomGeneratorController extends AbstractModelTestController {
    @FXML private TitledPane tiltedPane;
    @FXML private Label labelRandomInputWidth;
    @FXML private Label labelRandomInputHeight;
    @FXML private TextField textFieldRandomInputSizeX;
    @FXML private TextField textFieldRandomInputSizeY;
    @FXML private Label labelRandomMin;
    @FXML private Label labelRandomMax;
    @FXML private TextField textFieldRandomInputRangeMin;
    @FXML private TextField textFieldRandomInputRangeMax;
    @FXML private Button buttonGenerateRandomData;
    // 랜덤으로 데이터 불러오는 버튼이 필요

    public ModelFeatureSetRandomGeneratorController(TabModelTestController tabModelTestController) {
        super(tabModelTestController);
    }

    public AnchorPane createView() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/model/test/featureset_random_generator.fxml"));
        fxmlLoader.setController(this);
        AnchorPane content = fxmlLoader.load();
        return content;
    }

    @Override
    protected void initialize() throws Exception {
    }
}
