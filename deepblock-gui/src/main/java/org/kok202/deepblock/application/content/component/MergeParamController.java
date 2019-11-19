package org.kok202.deepblock.application.content.component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.application.Util.MathUtil;

import java.util.List;

public class MergeParamController extends AbstractLayerComponentController {

    @FXML private Label labelWidth;
    @FXML private Label labelHeight;
    @FXML private Label labelOutputSize;

    @FXML private TextField textFieldInputSize;
    @FXML private TextField textFieldOutputSizeValueX;
    @FXML private TextField textFieldOutputSizeValueY;
    @FXML private Button buttonOutputSizeChangeUp;
    @FXML private Button buttonOutputSizeChangeDown;

    public MergeParamController(Layer layer) {
        super(layer);
    }

    @Override
    public AnchorPane createView() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/component/merge_param.fxml"));
        fxmlLoader.setController(this);
        return fxmlLoader.load();
    }

    @Override
    protected void initialize() throws Exception {
        buttonOutputSizeChangeUp.setOnAction(actionEvent -> {
            int inputSize = layer.getProperties().getInputSize()[0] * layer.getProperties().getInputSize()[1];
            List<Integer> recommendedDivisors = MathUtil.getRecommendedDivisors(inputSize);
            recommendedDivisors.
                    // TODO : Button up down 으로 output size 변경
        });
        buttonOutputSizeChangeDown.setOnAction(actionEvent -> {});
    }

    public void changedListener(Layer layer){
        int inputSize = layer.getProperties().getInputSize()[0] * layer.getProperties().getInputSize()[1];
        textFieldInputSize.setText(String.valueOf(inputSize));
    }

    @Override
    protected void textFieldChangedHandler(){
        // TODO : Output size 자동 추천
        int[] outputSize= getOutputSize();
        if(outputSize[0] <= 0 || outputSize[1] <= 0){
            setTextFieldByLayerProperties();
            showOutputSizeErrorDialog(outputSize);
            return;
        }

        int[] recommendedDivisors = MathUtil.getRecommendedDivisors();
        textFieldOutputSizeValueX.setText(String.valueOf(outputSize[0]));
        textFieldOutputSizeValueY.setText(String.valueOf(outputSize[1]));
        layer.getProperties().setOutputSize(outputSize[0], outputSize[1]);
        notifyLayerDataChanged();
    }
}
