package org.kok202.deepblock.application.content.component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.kok202.deepblock.CanvasFacade;
import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.application.Util.MathUtil;
import org.kok202.deepblock.application.singleton.AppPropertiesSingleton;
import org.kok202.deepblock.canvas.entity.MergeBlockProperty;

import java.util.List;

public class MergeParamController extends AbstractLayerComponentController {

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
        int outputSizeX = recommendedDivisors.get(mergeBlockProperty.getPointingIndex(recommendedDivisors.size()));
        int outputSizeY = inputSize / outputSizeX;
        layer.getProperties().setInputSize(outputSizeX, outputSizeY);
        layer.getProperties().setOutputSize(outputSizeX, outputSizeY);
        textFieldInputSizeX.setText(String.valueOf(outputSizeX));
        textFieldInputSizeY.setText(String.valueOf(outputSizeY));
        textFieldOutputSizeX.setText(String.valueOf(outputSizeX));
        textFieldOutputSizeY.setText(String.valueOf(outputSizeY));
        notifyLayerDataChanged();
    }
}
