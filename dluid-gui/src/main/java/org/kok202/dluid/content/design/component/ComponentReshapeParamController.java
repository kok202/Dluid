package org.kok202.dluid.content.design.component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import org.kok202.dluid.AppConstant;
import org.kok202.dluid.adapter.MenuAdapter;
import org.kok202.dluid.canvas.CanvasFacade;
import org.kok202.dluid.canvas.entity.ReshapeBlockProperty;
import org.kok202.dluid.domain.entity.Layer;
import org.kok202.dluid.domain.entity.enumerator.Dimension;
import org.kok202.dluid.domain.util.MathUtil;
import org.kok202.dluid.singleton.AppPropertiesSingleton;
import org.kok202.dluid.util.TextFieldUtil;

import java.util.List;

public class ComponentReshapeParamController extends AbstractLayerComponentController {

    @FXML private Label labelInput1DX;
    @FXML private Label labelInput2DX;
    @FXML private Label labelInput2DY;
    @FXML private Label labelInput3DX;
    @FXML private Label labelInput3DY;
    @FXML private Label labelInput3DZ;
    @FXML private Label labelOutput1DX;
    @FXML private Label labelOutput2DX;
    @FXML private Label labelOutput2DY;
    @FXML private Label labelOutput3DX;
    @FXML private Label labelOutput3DY;
    @FXML private Label labelOutput3DZ;
    @FXML private Label labelInput;
    @FXML private Label labelInputDimension;
    @FXML private Label labelOutput;
    @FXML private Label labelOutputDimension;

    @FXML private AnchorPane anchorPaneInputLabel1D;
    @FXML private AnchorPane anchorPaneInputLabel2D;
    @FXML private AnchorPane anchorPaneInputLabel3D;
    @FXML private AnchorPane anchorPaneInputSize1D;
    @FXML private AnchorPane anchorPaneInputSize2D;
    @FXML private AnchorPane anchorPaneInputSize3D;

    @FXML private TextField textFieldInputSize1D;
    @FXML private TextField textFieldInputSize2DX;
    @FXML private TextField textFieldInputSize2DY;
    @FXML private TextField textFieldInputSize3DX;
    @FXML private TextField textFieldInputSize3DY;
    @FXML private TextField textFieldInputSize3DZ;

    @FXML private AnchorPane anchorPaneOutputLabel1D;
    @FXML private AnchorPane anchorPaneOutputLabel2D;
    @FXML private AnchorPane anchorPaneOutputLabel3D;
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
        labelInput1DX.setText(AppPropertiesSingleton.getInstance().get("frame.component.width"));
        labelInput2DX.setText(AppPropertiesSingleton.getInstance().get("frame.component.width"));
        labelInput2DY.setText(AppPropertiesSingleton.getInstance().get("frame.component.height"));
        labelInput3DX.setText(AppPropertiesSingleton.getInstance().get("frame.component.width"));
        labelInput3DY.setText(AppPropertiesSingleton.getInstance().get("frame.component.height"));
        labelInput3DZ.setText(AppPropertiesSingleton.getInstance().get("frame.component.channel"));
        labelOutput1DX.setText(AppPropertiesSingleton.getInstance().get("frame.component.width"));
        labelOutput2DX.setText(AppPropertiesSingleton.getInstance().get("frame.component.width"));
        labelOutput2DY.setText(AppPropertiesSingleton.getInstance().get("frame.component.height"));
        labelOutput3DX.setText(AppPropertiesSingleton.getInstance().get("frame.component.width"));
        labelOutput3DY.setText(AppPropertiesSingleton.getInstance().get("frame.component.height"));
        labelOutput3DZ.setText(AppPropertiesSingleton.getInstance().get("frame.component.channel"));
        labelInput.setText(AppPropertiesSingleton.getInstance().get("frame.component.default.inputSize"));
        labelOutput.setText(AppPropertiesSingleton.getInstance().get("frame.component.default.outputSize"));
        labelInputDimension.setText(AppPropertiesSingleton.getInstance().get("frame.component.reshape.inputDimension"));
        labelOutputDimension.setText(AppPropertiesSingleton.getInstance().get("frame.component.reshape.outputDimension"));
        changeInputLabelByChannelExist(layer.getProperties().getInputDimension().isHasChannel());
        changeOutputLabelByChannelExist(layer.getProperties().getOutputDimension().isHasChannel());
    }

    private void changeInputLabelByChannelExist(boolean isChannel) {
        if(isChannel) {
            labelInput2DY.setText(AppPropertiesSingleton.getInstance().get("frame.component.channel"));
            labelInput3DZ.setText(AppPropertiesSingleton.getInstance().get("frame.component.channel"));
        }
        else{
            labelInput2DY.setText(AppPropertiesSingleton.getInstance().get("frame.component.height"));
            labelInput3DZ.setText(AppPropertiesSingleton.getInstance().get("frame.component.height"));
        }
    }

    private void changeOutputLabelByChannelExist(boolean isChannel) {
        if(isChannel) {
            labelOutput2DY.setText(AppPropertiesSingleton.getInstance().get("frame.component.channel"));
            labelOutput3DZ.setText(AppPropertiesSingleton.getInstance().get("frame.component.channel"));
        }
        else{
            labelOutput2DY.setText(AppPropertiesSingleton.getInstance().get("frame.component.height"));
            labelOutput3DZ.setText(AppPropertiesSingleton.getInstance().get("frame.component.height"));
        }
    }

    private void setTextFieldByLayerProperties(){
        detachTextChangedListener(
                textFieldInputSize1D,
                textFieldInputSize2DX, textFieldInputSize2DY,
                textFieldInputSize3DX, textFieldInputSize3DY, textFieldInputSize3DZ,
                textFieldOutputSize3DZ);
        textFieldInputSize1D.setText(String.valueOf(layer.getProperties().getInputSizeX()));
        textFieldInputSize2DX.setText(String.valueOf(layer.getProperties().getInputSizeX()));
        textFieldInputSize2DY.setText(String.valueOf(layer.getProperties().getInputSizeY()));
        textFieldInputSize3DX.setText(String.valueOf(layer.getProperties().getInputSizeX()));
        textFieldInputSize3DY.setText(String.valueOf(layer.getProperties().getInputSizeY()));
        textFieldInputSize3DZ.setText(String.valueOf(layer.getProperties().getInputSizeZ()));
        textFieldOutputSize1D.setText(String.valueOf(layer.getProperties().getOutputSizeX()));
        textFieldOutputSize2DX.setText(String.valueOf(layer.getProperties().getOutputSizeX()));
        textFieldOutputSize2DY.setText(String.valueOf(layer.getProperties().getOutputSizeY()));
        textFieldOutputSize3DX.setText(String.valueOf(layer.getProperties().getOutputSizeX()));
        textFieldOutputSize3DY.setText(String.valueOf(layer.getProperties().getOutputSizeY()));
        textFieldOutputSize3DZ.setText(String.valueOf(layer.getProperties().getOutputSizeZ()));
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
        MenuAdapter<Dimension> menuAdapter = new MenuAdapter<>(menuButtonInputDimension);
        menuAdapter.setMenuItemChangedListener(dimension -> {
            layer.getProperties().setInputDimension(dimension);
            anchorPaneInputLabel1D.setVisible(dimension.getDimension() == 1);
            anchorPaneInputLabel2D.setVisible(dimension.getDimension() == 2);
            anchorPaneInputLabel3D.setVisible(dimension.getDimension() == 3);
            anchorPaneInputSize1D.setVisible(dimension.getDimension() == 1);
            anchorPaneInputSize2D.setVisible(dimension.getDimension() == 2);
            anchorPaneInputSize3D.setVisible(dimension.getDimension() == 3);
            refreshInputSize();
            refreshOutputSize();
            notifyLayerDataChanged();
            changeInputLabelByChannelExist(layer.getProperties().getInputDimension().isHasChannel());
        });
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.component.reshape.1d"), Dimension.ONE_DIMENSION);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.component.reshape.2d"), Dimension.TWO_DIMENSION);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.component.reshape.2d.withChannel"), Dimension.TWO_DIMENSION_WITH_CHANNEL);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.component.reshape.3d"), Dimension.THREE_DIMENSION);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.component.reshape.3d.withChannel"), Dimension.THREE_DIMENSION_WITH_CHANNEL);
        menuAdapter.setDefaultMenuItem(layer.getProperties().getInputDimension());
    }

    private void initializeMenuButtonOutputDimension(){
        MenuAdapter<Dimension> menuAdapter = new MenuAdapter<>(menuButtonOutputDimension);
        menuAdapter.setMenuItemChangedListener(dimension -> {
            layer.getProperties().setOutputDimension(dimension);
            anchorPaneOutputLabel1D.setVisible(dimension.getDimension() == 1);
            anchorPaneOutputLabel2D.setVisible(dimension.getDimension() == 2);
            anchorPaneOutputLabel3D.setVisible(dimension.getDimension() == 3);
            anchorPaneOutputSize1D.setVisible(dimension.getDimension() == 1);
            anchorPaneOutputSize2D.setVisible(dimension.getDimension() == 2);
            anchorPaneOutputSize3D.setVisible(dimension.getDimension() == 3);
            hBoxOutputSizeChange.setVisible(dimension.getDimension() != 1);
            refreshOutputSize();
            notifyLayerDataChanged();
            changeOutputLabelByChannelExist(layer.getProperties().getOutputDimension().isHasChannel());
        });
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.component.reshape.1d"), Dimension.ONE_DIMENSION);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.component.reshape.2d"), Dimension.TWO_DIMENSION);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.component.reshape.2d.withChannel"), Dimension.TWO_DIMENSION_WITH_CHANNEL);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.component.reshape.3d"), Dimension.THREE_DIMENSION);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.component.reshape.3d.withChannel"), Dimension.THREE_DIMENSION_WITH_CHANNEL);
        menuAdapter.setDefaultMenuItem(layer.getProperties().getOutputDimension());
    }

    private void refreshInputSize(){
        if(layer.getProperties().getInputDimension().getDimension() == 1){
            layer.getProperties().setInputSize(
                    TextFieldUtil.parseInteger(textFieldInputSize1D));
        }
        else if(layer.getProperties().getInputDimension().getDimension() == 2){
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
        if(layer.getProperties().getOutputDimension().getDimension() == 1) {
            layer.getProperties().setOutputSize(inputVolume);
            textFieldOutputSize1D.setText(String.valueOf(layer.getProperties().getOutputVolume()));
        }
        else if(layer.getProperties().getOutputDimension().getDimension() == 2){
            List<Integer> recommendedDivisors = MathUtil.getDivisors(inputVolume);
            int outputSizeY = recommendedDivisors.get(getReshapeBlockProperty().getPointingIndex(recommendedDivisors.size()));
            int outputSizeX = inputVolume / outputSizeY;
            layer.getProperties().setOutputSize(outputSizeX, outputSizeY);

            textFieldOutputSize2DX.setText(String.valueOf(outputSizeX));
            textFieldOutputSize2DY.setText(String.valueOf(outputSizeY));
        }
        else{
            int outputSizeZ = TextFieldUtil.parseInteger(textFieldOutputSize3DZ);
            if(outputSizeZ != layer.getProperties().getOutputSizeZ()){
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
    }

    private ReshapeBlockProperty getReshapeBlockProperty(){
        return (ReshapeBlockProperty) CanvasFacade.findGraphNodeByLayerId(layer.getId()).getData().getBlockInfo().getExtra();
    }
}
