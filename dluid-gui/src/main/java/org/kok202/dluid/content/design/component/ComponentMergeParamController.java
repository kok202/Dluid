package org.kok202.dluid.content.design.component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.kok202.dluid.canvas.CanvasFacade;
import org.kok202.dluid.canvas.entity.MergeBlockProperty;
import org.kok202.dluid.domain.entity.Layer;
import org.kok202.dluid.domain.util.MathUtil;
import org.kok202.dluid.singleton.AppPropertiesSingleton;

import java.util.List;

public class ComponentMergeParamController extends AbstractLayerComponentController {

    @FXML private Label labelX;
    @FXML private Label labelY;
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
            MergeBlockProperty mergeBlockProperty = (MergeBlockProperty) CanvasFacade.findGraphNodeByLayerId(layer.getId()).getData().getBlockInfo().getExtra();
            mergeBlockProperty.setPointingIndex(mergeBlockProperty.getPointingIndex() + 1);
            refreshInputOutputSize();
        });
        buttonOutputSizeChangeDown.setOnAction(actionEvent -> {
            MergeBlockProperty mergeBlockProperty = (MergeBlockProperty) CanvasFacade.findGraphNodeByLayerId(layer.getId()).getData().getBlockInfo().getExtra();
            mergeBlockProperty.setPointingIndex(mergeBlockProperty.getPointingIndex() - 1);
            refreshInputOutputSize();
        });
        titledPane.setText(AppPropertiesSingleton.getInstance().get("frame.component.default.title"));
        labelX.setText(AppPropertiesSingleton.getInstance().get("frame.component.width"));
        labelY.setText(AppPropertiesSingleton.getInstance().get("frame.component.height"));
        labelInputSize.setText(AppPropertiesSingleton.getInstance().get("frame.component.default.inputSize"));
        labelOutputSize.setText(AppPropertiesSingleton.getInstance().get("frame.component.default.outputSize"));

        textFieldInputSizeX.setText(String.valueOf(layer.getProperties().getInputSizeX()));
        textFieldInputSizeY.setText(String.valueOf(layer.getProperties().getInputSizeY()));
        textFieldOutputSizeX.setText(String.valueOf(layer.getProperties().getOutputSizeX()));
        textFieldOutputSizeY.setText(String.valueOf(layer.getProperties().getOutputSizeY()));
    }

    private void refreshInputOutputSize(){
        List<Layer> incomingLayers = CanvasFacade.findIncomingLayers(layer.getId());

        int inputSize = 0;
        for (Layer incomingLayer : incomingLayers) {
            inputSize += incomingLayer.getProperties().getOutputVolume();
        }
        inputSize = Math.max(inputSize, 1);

        List<Integer> recommendedDivisors = MathUtil.getDivisors(inputSize);
        MergeBlockProperty mergeBlockProperty = (MergeBlockProperty) CanvasFacade.findGraphNodeByLayerId(layer.getId()).getData().getBlockInfo().getExtra();
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
