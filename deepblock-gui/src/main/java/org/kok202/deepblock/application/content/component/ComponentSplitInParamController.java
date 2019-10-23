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
import org.kok202.deepblock.canvas.entity.SplitBlockProperty;

public class ComponentSplitInParamController extends AbstractLayerComponentController {

    @FXML private Label labelWidth;
    @FXML private Label labelHeight;
    @FXML private Label labelLeftInput;
    @FXML private Label labelRightInput;
    @FXML private Label labelOutput;

    @FXML private TextField textFieldLeftInputX;
    @FXML private TextField textFieldLeftInputY;
    @FXML private TextField textFieldRightInputX;
    @FXML private TextField textFieldRightInputY;
    @FXML private TextField textFieldOutputX;
    @FXML private TextField textFieldOutputY;

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
        TextFieldUtil.applyPositiveIntegerFilter(textFieldOutputX, AppConstant.DEFAULT_OUTPUT_SIZE);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldOutputY, AppConstant.DEFAULT_OUTPUT_SIZE);
        setTextFieldByLayerProperties();
    }

    private void setTextFieldByLayerProperties(){
        SplitBlockProperty splitBlockProperty = (SplitBlockProperty) layer.getExtra();
        textFieldRightInputX.setText(String.valueOf(splitBlockProperty.getSplitRightSize()[0]));
        textFieldRightInputY.setText(String.valueOf(splitBlockProperty.getSplitRightSize()[1]));
        textFieldLeftInputX.setText(String.valueOf(splitBlockProperty.getSplitLeftSize()[0]));
        textFieldLeftInputY.setText(String.valueOf(splitBlockProperty.getSplitLeftSize()[1]));
        textFieldOutputX.setText(String.valueOf(layer.getProperties().getOutputSize()[0]));
        textFieldOutputY.setText(String.valueOf(layer.getProperties().getOutputSize()[1]));
        attachTextChangedListener(textFieldLeftInputX, textFieldLeftInputY, textFieldRightInputX, textFieldRightInputY);
    }

    @Override
    protected void textFieldChangedHandler(){
        SplitBlockProperty splitBlockProperty = (SplitBlockProperty) layer.getExtra();
        splitBlockProperty.setSplitLeftSize(new int[]{
                TextFieldUtil.parseInteger(textFieldLeftInputX),
                TextFieldUtil.parseInteger(textFieldLeftInputY)});
        splitBlockProperty.setSplitRightSize(new int[]{
                TextFieldUtil.parseInteger(textFieldRightInputX),
                TextFieldUtil.parseInteger(textFieldRightInputY)});

        int concatenatedSize =
                splitBlockProperty.getSplitLeftSize()[0] * splitBlockProperty.getSplitLeftSize()[1] +
                splitBlockProperty.getSplitRightSize()[0] * splitBlockProperty.getSplitRightSize()[1];
        int[] splitSize = MathUtil.getRecommendedDivisors(concatenatedSize);
        textFieldOutputX.setText(String.valueOf(splitSize[0]));
        textFieldOutputY.setText(String.valueOf(splitSize[1]));
        layer.getProperties().setOutputSize(splitSize[0], splitSize[1]);
        notifyLayerDataChanged();
    }
}
