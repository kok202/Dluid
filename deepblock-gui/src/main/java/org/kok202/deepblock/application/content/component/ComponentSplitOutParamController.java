package org.kok202.deepblock.application.content.component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.application.Util.DialogUtil;
import org.kok202.deepblock.application.Util.MathUtil;
import org.kok202.deepblock.application.Util.TextFieldUtil;
import org.kok202.deepblock.application.global.AppConstant;

public class ComponentSplitOutParamController extends AbstractLayerComponentController {

    @FXML private Label labelWidth;
    @FXML private Label labelHeight;
    @FXML private Label labelLeftOutput;
    @FXML private Label labelRightOutput;

    @FXML private TextField textFieldInputX;
    @FXML private TextField textFieldInputY;
    @FXML private TextField textFieldLeftOutputX;
    @FXML private TextField textFieldLeftOutputY;
    @FXML private TextField textFieldRightOutputX;
    @FXML private TextField textFieldRightOutputY;

    public ComponentSplitOutParamController(Layer layer) {
        super(layer);
    }

    @Override
    public AnchorPane createView() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/component/split_out_param.fxml"));
        fxmlLoader.setController(this);
        return fxmlLoader.load();
    }

    @Override
    protected void initialize() throws Exception {
        TextFieldUtil.applyPositiveIntegerFilter(textFieldInputX, AppConstant.DEFAULT_INPUT_SIZE);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldInputY, AppConstant.DEFAULT_INPUT_SIZE);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldLeftOutputX, AppConstant.DEFAULT_OUTPUT_SIZE);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldLeftOutputY, AppConstant.DEFAULT_OUTPUT_SIZE);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldRightOutputX, AppConstant.DEFAULT_OUTPUT_SIZE);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldRightOutputY, AppConstant.DEFAULT_OUTPUT_SIZE);
        setTextFieldByLayerProperties();
    }

    private void setTextFieldByLayerProperties(){
        detachTextChangedListener(textFieldInputX, textFieldInputY, textFieldLeftOutputX, textFieldLeftOutputY);
        textFieldInputX.setText(String.valueOf(layer.getProperties().getInputSize()[0]));
        textFieldInputY.setText(String.valueOf(layer.getProperties().getInputSize()[1]));
        textFieldLeftOutputX.setText(String.valueOf(layer.getProperties().getSplitLeftSize()[0]));
        textFieldLeftOutputY.setText(String.valueOf(layer.getProperties().getSplitLeftSize()[1]));
        textFieldRightOutputX.setText(String.valueOf(layer.getProperties().getSplitRightSize()[0]));
        textFieldRightOutputY.setText(String.valueOf(layer.getProperties().getSplitRightSize()[1]));
        attachTextChangedListener(textFieldInputX, textFieldInputY, textFieldLeftOutputX, textFieldLeftOutputY);
    }

    @Override
    protected void textFieldChangedHandler(){
        int leftSize = TextFieldUtil.parseInteger(textFieldLeftOutputX) * TextFieldUtil.parseInteger(textFieldLeftOutputY);
        int inputSize = TextFieldUtil.parseInteger(textFieldInputX) * TextFieldUtil.parseInteger(textFieldInputY);
        int rightSize = inputSize - leftSize;
        if(rightSize <= 0){
            setTextFieldByLayerProperties();
            DialogUtil.builder()
                    .alertType(Alert.AlertType.ERROR)
                    .title("Negative right size!")
                    .headerText("left size can not over than input size. (" + rightSize + ")")
                    .contentText("Please make left size is lesser than input size.")
                    .build()
                    .showAndWait();
            return;
        }

        int[] rightSize2D = MathUtil.getRecommendedDivisors(rightSize);
        textFieldRightOutputX.setText(String.valueOf(rightSize2D[1]));
        textFieldRightOutputY.setText(String.valueOf(rightSize2D[0]));
        layer.getProperties().setInputSize(
                TextFieldUtil.parseInteger(textFieldInputX),
                TextFieldUtil.parseInteger(textFieldInputY));
        layer.getProperties().setSplitLeftSize(new int[]{
                TextFieldUtil.parseInteger(textFieldLeftOutputX),
                TextFieldUtil.parseInteger(textFieldLeftOutputY)});
        layer.getProperties().setSplitRightSize(new int[]{
                TextFieldUtil.parseInteger(textFieldRightOutputX),
                TextFieldUtil.parseInteger(textFieldRightOutputY)});
        notifyLayerDataChanged();
    }
}
