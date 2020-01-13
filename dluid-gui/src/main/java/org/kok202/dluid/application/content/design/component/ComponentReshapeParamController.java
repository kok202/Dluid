package org.kok202.dluid.application.content.design.component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import org.kok202.dluid.AppConstant;
import org.kok202.dluid.CanvasConstant;
import org.kok202.dluid.CanvasFacade;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.application.adapter.MenuAdapter;
import org.kok202.dluid.application.singleton.AppPropertiesSingleton;
import org.kok202.dluid.application.util.MathUtil;
import org.kok202.dluid.application.util.TextFieldUtil;
import org.kok202.dluid.canvas.entity.ReshapeBlockProperty;

import java.util.List;

public class ComponentReshapeParamController extends AbstractLayerComponentController {

    @FXML private Label labelInputDimension;
    @FXML private Label labelInput;
    @FXML private Label labelOutputDimension;
    @FXML private Label labelOutput;

    @FXML private AnchorPane anchorPaneInputSize1D;
    @FXML private AnchorPane anchorPaneInputSize2D;
    @FXML private AnchorPane anchorPaneInputSize3D;

    @FXML private TextField textFieldInputSize1D;
    @FXML private TextField textFieldInputSize2DX;
    @FXML private TextField textFieldInputSize2DY;
    @FXML private TextField textFieldInputSize3DX;
    @FXML private TextField textFieldInputSize3DY;
    @FXML private TextField textFieldInputSize3DZ;

    @FXML private AnchorPane anchorPaneOutputSize1D;
    @FXML private AnchorPane anchorPaneOutputSize2D;
    @FXML private AnchorPane anchorPaneOutputSize3D;

    @FXML private TextField textFieldOutputSize1D;
    @FXML private TextField textFieldOutputSize2DX;
    @FXML private TextField textFieldOutputSize2DY;
    @FXML private TextField textFieldOutputSize3DX;
    @FXML private TextField textFieldOutputSize3DY;
    @FXML private TextField textFieldOutputSize3DZ;

    @FXML private MenuButton menuButtonInputDimension;
    @FXML private MenuButton menuButtonOutputDimension;

    @FXML private HBox hBoxOutputSizeChange;
    @FXML private Button buttonOutputSizeChangeUp;
    @FXML private Button buttonOutputSizeChangeDown;


    public ComponentReshapeParamController(Layer layer) {
        super(layer);
    }

    @Override
    public AnchorPane createView() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/design/component/reshape_param.fxml"));
        fxmlLoader.setController(this);
        return fxmlLoader.load();
    }

    @Override
    protected void initialize() throws Exception {
        buttonOutputSizeChangeUp.setOnAction(actionEvent -> {
            ReshapeBlockProperty reshapeBlockProperty = getReshapeBlockProperty();
            reshapeBlockProperty.setPointingIndex(reshapeBlockProperty.getPointingIndex() + 1);
            refreshOutputSize();
            notifyLayerDataChanged();
        });
        buttonOutputSizeChangeDown.setOnAction(actionEvent -> {
            ReshapeBlockProperty reshapeBlockProperty = getReshapeBlockProperty();
            reshapeBlockProperty.setPointingIndex(reshapeBlockProperty.getPointingIndex() - 1);
            refreshOutputSize();
            notifyLayerDataChanged();
        });
        TextFieldUtil.applyPositiveIntegerFilter(AppConstant.DEFAULT_INPUT_SIZE,
                textFieldInputSize1D,
                textFieldInputSize2DX, textFieldInputSize2DY,
                textFieldInputSize3DX, textFieldInputSize3DY, textFieldInputSize3DZ);
        TextFieldUtil.applyPositiveIntegerFilter(AppConstant.DEFAULT_OUTPUT_SIZE,
                textFieldOutputSize1D,
                textFieldOutputSize2DX, textFieldOutputSize2DY,
                textFieldOutputSize3DX, textFieldOutputSize3DY, textFieldOutputSize3DZ);
        setTextFieldByLayerProperties();

        initializeMenuButtonInputDimension();
        initializeMenuButtonOutputDimension();
        titledPane.setText(AppPropertiesSingleton.getInstance().get("frame.component.default.title"));
        labelInput.setText(AppPropertiesSingleton.getInstance().get("frame.component.default.inputSize"));
        labelOutput.setText(AppPropertiesSingleton.getInstance().get("frame.component.default.outputSize"));
        labelInputDimension.setText(AppPropertiesSingleton.getInstance().get("frame.component.reshape.inputDimension"));
        labelOutputDimension.setText(AppPropertiesSingleton.getInstance().get("frame.component.reshape.outputDimension"));
    }

    private void setTextFieldByLayerProperties(){
        detachTextChangedListener(
                textFieldInputSize1D,
                textFieldInputSize2DX, textFieldInputSize2DY,
                textFieldInputSize3DX, textFieldInputSize3DY, textFieldInputSize3DZ,
                textFieldOutputSize3DZ);
        textFieldInputSize1D.setText(String.valueOf(layer.getProperties().getInputSize()[0]));
        textFieldInputSize2DX.setText(String.valueOf(layer.getProperties().getInputSize()[0]));
        textFieldInputSize2DY.setText(String.valueOf(layer.getProperties().getInputSize()[1]));
        textFieldInputSize3DX.setText(String.valueOf(layer.getProperties().getInputSize()[0]));
        textFieldInputSize3DY.setText(String.valueOf(layer.getProperties().getInputSize()[1]));
        textFieldInputSize3DZ.setText(String.valueOf(layer.getProperties().getInputSize()[2]));
        textFieldOutputSize1D.setText(String.valueOf(layer.getProperties().getOutputSize()[0]));
        textFieldOutputSize2DX.setText(String.valueOf(layer.getProperties().getOutputSize()[0]));
        textFieldOutputSize2DY.setText(String.valueOf(layer.getProperties().getOutputSize()[1]));
        textFieldOutputSize3DX.setText(String.valueOf(layer.getProperties().getOutputSize()[0]));
        textFieldOutputSize3DY.setText(String.valueOf(layer.getProperties().getOutputSize()[1]));
        textFieldOutputSize3DZ.setText(String.valueOf(layer.getProperties().getOutputSize()[2]));
        attachTextChangedListener(
                textFieldInputSize1D,
                textFieldInputSize2DX, textFieldInputSize2DY,
                textFieldInputSize3DX, textFieldInputSize3DY, textFieldInputSize3DZ,
                textFieldOutputSize3DZ);
    }

    @Override
    protected void textFieldChangedHandler(){
        refreshInputSize();
        refreshOutputSize();
        notifyLayerDataChanged();
    }

    private void initializeMenuButtonInputDimension(){
        MenuAdapter<Integer> menuAdapter = new MenuAdapter<>(menuButtonInputDimension);
        menuAdapter.setMenuItemChangedListener(dimension -> {
            layer.getProperties().setInputDimension(dimension);
            anchorPaneInputSize1D.setVisible(dimension == 1);
            anchorPaneInputSize2D.setVisible(dimension == 2);
            anchorPaneInputSize3D.setVisible(dimension == 3);
            refreshInputSize();
            refreshOutputSize();
            notifyLayerDataChanged();
        });
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.component.reshape.1d"), 1);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.component.reshape.2d"), 2);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.component.reshape.3d"), 3);
        menuAdapter.setDefaultMenuItem((Integer) layer.getProperties().getInputDimension());
    }

    private void initializeMenuButtonOutputDimension(){
        MenuAdapter<Integer> menuAdapter = new MenuAdapter<>(menuButtonOutputDimension);
        menuAdapter.setMenuItemChangedListener(dimension -> {
            layer.getProperties().setOutputDimension(dimension);
            anchorPaneOutputSize1D.setVisible(dimension == 1);
            anchorPaneOutputSize2D.setVisible(dimension == 2);
            anchorPaneOutputSize3D.setVisible(dimension == 3);
            hBoxOutputSizeChange.setVisible(dimension != 1);
            refreshOutputSize();
            notifyLayerDataChanged();
        });
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.component.reshape.1d"), 1);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.component.reshape.2d"), 2);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.component.reshape.3d"), 3);
        menuAdapter.setDefaultMenuItem((Integer) layer.getProperties().getOutputDimension());
    }

    private void refreshInputSize(){
        if(layer.getProperties().getInputDimension() == 1){
            layer.getProperties().setInputSize(
                    TextFieldUtil.parseInteger(textFieldInputSize1D));
        }
        else if(layer.getProperties().getInputDimension() == 2){
            layer.getProperties().setInputSize(
                    TextFieldUtil.parseInteger(textFieldInputSize2DX),
                    TextFieldUtil.parseInteger(textFieldInputSize2DY));
        }
        else{
            layer.getProperties().setInputSize(
                    TextFieldUtil.parseInteger(textFieldInputSize3DX),
                    TextFieldUtil.parseInteger(textFieldInputSize3DY),
                    TextFieldUtil.parseInteger(textFieldInputSize3DZ));
        }
    }

    private void refreshOutputSize(){
        int inputVolume = layer.getProperties().getInputVolume();
        if(layer.getProperties().getOutputDimension() == 1) {
            layer.getProperties().setOutputSize(inputVolume);
            textFieldOutputSize1D.setText(String.valueOf(layer.getProperties().getOutputVolume()));
        }
        else if(layer.getProperties().getOutputDimension() == 2){
            List<Integer> recommendedDivisors = MathUtil.getDivisors(inputVolume);
            int outputSizeY = recommendedDivisors.get(getReshapeBlockProperty().getPointingIndex(recommendedDivisors.size()));
            int outputSizeX = inputVolume / outputSizeY;
            layer.getProperties().setOutputSize(outputSizeX, outputSizeY);

            textFieldOutputSize2DX.setText(String.valueOf(outputSizeX));
            textFieldOutputSize2DY.setText(String.valueOf(outputSizeY));
        }
        else{
            int outputSizeZ = TextFieldUtil.parseInteger(textFieldOutputSize3DZ);
            if(outputSizeZ != layer.getProperties().getOutputSize()[2]){
                // textFieldOutputSize3DZ changed!
                if(inputVolume % outputSizeZ != 0)
                    outputSizeZ = 1;
                getReshapeBlockProperty().setPointingIndex(0);
            }

            inputVolume /= outputSizeZ;
            List<Integer> recommendedDivisors = MathUtil.getDivisors(inputVolume);
            int outputSizeY = recommendedDivisors.get(getReshapeBlockProperty().getPointingIndex(recommendedDivisors.size()));
            int outputSizeX = inputVolume / outputSizeY;
            layer.getProperties().setOutputSize(outputSizeX, outputSizeY, outputSizeZ);

            detachTextChangedListener(textFieldOutputSize3DZ);
            textFieldOutputSize3DX.setText(String.valueOf(outputSizeX));
            textFieldOutputSize3DY.setText(String.valueOf(outputSizeY));
            textFieldOutputSize3DZ.setText(String.valueOf(outputSizeZ));
            attachTextChangedListener(textFieldOutputSize3DZ);
        }
        CanvasFacade.findGraphNodeByLayerId(layer.getId())
                .getData()
                .setHeight(layer.getProperties().getOutputSize()[2] * CanvasConstant.NODE_DEFAULT_HEIGHT);
    }

    private ReshapeBlockProperty getReshapeBlockProperty(){
        return (ReshapeBlockProperty) CanvasFacade.findGraphNodeByLayerId(layer.getId()).getData().getBlockInfo().getExtra();
    }
}
