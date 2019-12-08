package org.kok202.dluid.application.content.design.component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.kok202.dluid.CanvasFacade;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.application.Util.MathUtil;
import org.kok202.dluid.application.singleton.AppPropertiesSingleton;
import org.kok202.dluid.canvas.entity.MergeBlockProperty;

import java.util.List;

public class ComponentMergeParamController extends AbstractLayerComponentController {

    @FXML private Label labelWidth;
    @FXML private Label labelHeight;
    @FXML private Label labelInputSize;
    @FXML private Label labelOutputSize;

    @FXML private TextField textFieldInputSizeX;
    @FXML private TextField textFieldInputSizeY;
    @FXML private TextField textFieldOutputSizeX;
    @FXML private TextField textFieldOutputSizeY;
    @FXML private Button buttonOutputSizeChangeUp;
    @FXML private Button buttonOutputSizeChangeDown;

    public ComponentMergeParamController(Layer layer) {
        super(layer);
    }

    @Override
    public AnchorPane createView() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/design/component/merge_param.fxml"));
        fxmlLoader.setController(this);
        return fxmlLoader.load();
    }

    @Override
    protected void initialize() throws Exception {
        buttonOutputSizeChangeUp.setOnAction(actionEvent -> {
            MergeBlockProperty mergeBlockProperty = (MergeBlockProperty) layer.getExtra();
            mergeBlockProperty.setPointingIndex(mergeBlockProperty.getPointingIndex() + 1);
            refreshInputOutputSize();
        });
        buttonOutputSizeChangeDown.setOnAction(actionEvent -> {
            MergeBlockProperty mergeBlockProperty = (MergeBlockProperty) layer.getExtra();
            mergeBlockProperty.setPointingIndex(mergeBlockProperty.getPointingIndex() - 1);
            refreshInputOutputSize();
        });
        titledPane.setText(AppPropertiesSingleton.getInstance().get("frame.component.default.title"));
        labelWidth.setText(AppPropertiesSingleton.getInstance().get("frame.component.2d.width"));
        labelHeight.setText(AppPropertiesSingleton.getInstance().get("frame.component.2d.height"));
        labelInputSize.setText(AppPropertiesSingleton.getInstance().get("frame.component.default.inputSize"));
        labelOutputSize.setText(AppPropertiesSingleton.getInstance().get("frame.component.default.outputSize"));

        textFieldInputSizeX.setText(String.valueOf(layer.getProperties().getInputSize()[0]));
        textFieldInputSizeY.setText(String.valueOf(layer.getProperties().getInputSize()[1]));
        textFieldOutputSizeX.setText(String.valueOf(layer.getProperties().getOutputSize()[0]));
        textFieldOutputSizeY.setText(String.valueOf(layer.getProperties().getOutputSize()[1]));
    }

    public void refreshInputOutputSize(){
        int inputSize = 0;
        List<Layer> incomingLayers = CanvasFacade.findIncomingLayers(layer.getId());
        for (Layer incomingLayer : incomingLayers) {
            inputSize += incomingLayer.getProperties().getOutputSize()[0] * incomingLayer.getProperties().getOutputSize()[1];
        }
        inputSize = Math.max(inputSize, 1);
        List<Integer> recommendedDivisors = MathUtil.getDivisors(inputSize);
        MergeBlockProperty mergeBlockProperty = (MergeBlockProperty) layer.getExtra();
        int outputSizeY = recommendedDivisors.get(mergeBlockProperty.getPointingIndex(recommendedDivisors.size()));
        int outputSizeX = inputSize / outputSizeY;
        layer.getProperties().setInputSize(outputSizeX, outputSizeY);
        layer.getProperties().setOutputSize(outputSizeX, outputSizeY);
        textFieldInputSizeX.setText(String.valueOf(outputSizeX));
        textFieldInputSizeY.setText(String.valueOf(outputSizeY));
        textFieldOutputSizeX.setText(String.valueOf(outputSizeX));
        textFieldOutputSizeY.setText(String.valueOf(outputSizeY));
        notifyLayerDataChanged();
    }
}
