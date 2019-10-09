package org.kok202.deepblock.application.content.component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.application.Util.MathUtil;
import org.kok202.deepblock.application.Util.TextFieldUtil;
import org.kok202.deepblock.application.global.AppConstant;

public class ComponentSplitInParamController extends AbstractLayerComponentController {

    @FXML private Label labelWidth;
    @FXML private Label labelHeight;
    @FXML private Label labelLeftInput;
    @FXML private Label labelRightInput;

    @FXML private TextField textFieldLeftInputX;
    @FXML private TextField textFieldLeftInputY;
    @FXML private TextField textFieldRightInputX;
    @FXML private TextField textFieldRightInputY;

    public ComponentSplitInParamController(Layer layer) {
        super(layer);
    }

    @Override
    public AnchorPane createView() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/component/split_in_param.fxml"));
        fxmlLoader.setController(this);
        return fxmlLoader.load();
    }

    @Override
    protected void initialize() throws Exception {
        TextFieldUtil.applyPositiveIntegerFilter(textFieldLeftInputX, AppConstant.DEFAULT_INPUT_SIZE);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldLeftInputY, AppConstant.DEFAULT_INPUT_SIZE);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldRightInputX, AppConstant.DEFAULT_INPUT_SIZE);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldRightInputY, AppConstant.DEFAULT_INPUT_SIZE);
        setTextFieldByLayerProperties();
    }

    private void setTextFieldByLayerProperties(){
        textFieldLeftInputX.setText(String.valueOf(layer.getProperties().getSplitLeftSize()[0]));
        textFieldLeftInputY.setText(String.valueOf(layer.getProperties().getSplitLeftSize()[1]));
        textFieldRightInputX.setText(String.valueOf(layer.getProperties().getSplitRightSize()[0]));
        textFieldRightInputY.setText(String.valueOf(layer.getProperties().getSplitRightSize()[1]));
        attachTextChangedListener(textFieldLeftInputX, textFieldLeftInputY, textFieldRightInputX, textFieldRightInputY);
    }

    @Override
    protected void textFieldChangedHandler(){
        layer.getProperties().setSplitLeftSize(new int[]{
                TextFieldUtil.parseInteger(textFieldLeftInputX),
                TextFieldUtil.parseInteger(textFieldLeftInputY)});
        layer.getProperties().setSplitRightSize(new int[]{
                TextFieldUtil.parseInteger(textFieldRightInputX),
                TextFieldUtil.parseInteger(textFieldRightInputY)});

        int concatenatedSize =
                layer.getProperties().getSplitLeftSize()[0] * layer.getProperties().getSplitLeftSize()[1] +
                layer.getProperties().getSplitRightSize()[0] * layer.getProperties().getSplitRightSize()[1];
        int[] splitSize = MathUtil.getRecommendedDivisors(concatenatedSize);
        layer.getProperties().setOutputSize(
                splitSize[1],
                splitSize[0]);
        notifyLayerDataChanged();
    }
}
