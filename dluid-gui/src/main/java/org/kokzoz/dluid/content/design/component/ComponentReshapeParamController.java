package org.kokzoz.dluid.content.design.component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import org.kokzoz.dluid.AppConstant;
import org.kokzoz.dluid.adapter.MenuAdapter;
import org.kokzoz.dluid.canvas.CanvasFacade;
import org.kokzoz.dluid.canvas.entity.ReshapeBlockProperty;
import org.kokzoz.dluid.domain.entity.Dimension;
import org.kokzoz.dluid.domain.entity.Layer;
import org.kokzoz.dluid.domain.entity.enumerator.DimensionType;
import org.kokzoz.dluid.domain.util.MathUtil;
import org.kokzoz.dluid.singleton.AppPropertiesSingleton;
import org.kokzoz.dluid.util.TextFieldUtil;

import java.util.List;

public class ComponentReshapeParamController extends AbstractLayerComponentController {

    // X = width
    // Y = height or channel
    // Z = channel or [depth:notUsed]
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
            reshapeBlock();
        });
        buttonOutputSizeChangeDown.setOnAction(actionEvent -> {
            ReshapeBlockProperty reshapeBlockProperty = getReshapeBlockProperty();
            reshapeBlockProperty.setPointingIndex(reshapeBlockProperty.getPointingIndex() - 1);
            refreshOutputSize();
            reshapeBlock();
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
        refreshInputComponent(layer.getProperties().getInput());
        refreshOutputComponent(layer.getProperties().getOutput());
    }

    private void setTextFieldByLayerProperties(){
        detachTextChangedListener(
                textFieldInputSize1D,
                textFieldInputSize2DX, textFieldInputSize2DY,
                textFieldInputSize3DX, textFieldInputSize3DY, textFieldInputSize3DZ,
                textFieldOutputSize3DZ);
        initializeInputText();
        initializeOutputText();
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
        reshapeBlock();
    }

    private void initializeMenuButtonInputDimension(){
        MenuAdapter<DimensionType> menuAdapter = new MenuAdapter<>(menuButtonInputDimension);
        menuAdapter.setMenuItemChangedListener(dimensionType -> {
            layer.getProperties().getInput().changeDimensionByType(dimensionType);
            refreshInputComponent(layer.getProperties().getInput());
            refreshInputSize();
            refreshOutputSize();
            reshapeBlock();
        });
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.component.reshape.1d"), DimensionType.ONE_DIMENSION);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.component.reshape.1d.withChannel"), DimensionType.ONE_DIMENSION_WITH_CHANNEL);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.component.reshape.2d"), DimensionType.TWO_DIMENSION);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.component.reshape.2d.withChannel"), DimensionType.TWO_DIMENSION_WITH_CHANNEL);
        menuAdapter.setDefaultMenuItem(layer.getProperties().getInput().getDimensionType());
    }

    private void initializeMenuButtonOutputDimension(){
        MenuAdapter<DimensionType> menuAdapter = new MenuAdapter<>(menuButtonOutputDimension);
        menuAdapter.setMenuItemChangedListener(dimensionType -> {
            layer.getProperties().getOutput().changeDimensionByType(dimensionType);
            refreshOutputComponent(layer.getProperties().getOutput());
            refreshOutputSize();
            reshapeBlock();
        });
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.component.reshape.1d"), DimensionType.ONE_DIMENSION);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.component.reshape.1d.withChannel"), DimensionType.ONE_DIMENSION_WITH_CHANNEL);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.component.reshape.2d"), DimensionType.TWO_DIMENSION);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.component.reshape.2d.withChannel"), DimensionType.TWO_DIMENSION_WITH_CHANNEL);
        menuAdapter.setDefaultMenuItem(layer.getProperties().getOutput().getDimensionType());
    }

    private void initializeInputText(){
        switch (layer.getProperties().getInput().getDimensionType()){
            case ONE_DIMENSION:
                textFieldInputSize1D.setText(String.valueOf(layer.getProperties().getInput().getX()));
                break;
            case ONE_DIMENSION_WITH_CHANNEL:
                textFieldInputSize2DX.setText(String.valueOf(layer.getProperties().getInput().getX()));
                textFieldInputSize2DY.setText(String.valueOf(layer.getProperties().getInput().getChannel()));
                break;
            case TWO_DIMENSION:
                textFieldInputSize2DX.setText(String.valueOf(layer.getProperties().getInput().getX()));
                textFieldInputSize2DY.setText(String.valueOf(layer.getProperties().getInput().getY()));
                break;
            case TWO_DIMENSION_WITH_CHANNEL:
                textFieldInputSize3DX.setText(String.valueOf(layer.getProperties().getInput().getX()));
                textFieldInputSize3DY.setText(String.valueOf(layer.getProperties().getInput().getY()));
                textFieldInputSize3DZ.setText(String.valueOf(layer.getProperties().getInput().getChannel()));
                break;
        }
    }

    private void initializeOutputText(){
        switch (layer.getProperties().getOutput().getDimensionType()){
            case ONE_DIMENSION:
                textFieldOutputSize1D.setText(String.valueOf(layer.getProperties().getOutput().getX()));
                break;
            case ONE_DIMENSION_WITH_CHANNEL:
                textFieldOutputSize2DX.setText(String.valueOf(layer.getProperties().getOutput().getX()));
                textFieldOutputSize2DY.setText(String.valueOf(layer.getProperties().getOutput().getChannel()));
                break;
            case TWO_DIMENSION:
                textFieldOutputSize2DX.setText(String.valueOf(layer.getProperties().getOutput().getX()));
                textFieldOutputSize2DY.setText(String.valueOf(layer.getProperties().getOutput().getY()));
                break;
            case TWO_DIMENSION_WITH_CHANNEL:
                textFieldOutputSize3DX.setText(String.valueOf(layer.getProperties().getOutput().getX()));
                textFieldOutputSize3DY.setText(String.valueOf(layer.getProperties().getOutput().getY()));
                textFieldOutputSize3DZ.setText(String.valueOf(layer.getProperties().getOutput().getChannel()));
                break;
        }
    }

    private void refreshInputSize(){
        switch (layer.getProperties().getInput().getDimensionType()){
            case ONE_DIMENSION:
                layer.getProperties().getInput().setX(TextFieldUtil.parseInteger(textFieldInputSize1D));
                break;
            case ONE_DIMENSION_WITH_CHANNEL:
                layer.getProperties().getInput().setX(TextFieldUtil.parseInteger(textFieldInputSize2DX));
                layer.getProperties().getInput().setChannel(TextFieldUtil.parseInteger(textFieldInputSize2DY));
                break;
            case TWO_DIMENSION:
                layer.getProperties().getInput().setX(TextFieldUtil.parseInteger(textFieldInputSize2DX));
                layer.getProperties().getInput().setY(TextFieldUtil.parseInteger(textFieldInputSize2DY));
                break;
            case TWO_DIMENSION_WITH_CHANNEL:
                layer.getProperties().getInput().setX(TextFieldUtil.parseInteger(textFieldInputSize3DX));
                layer.getProperties().getInput().setY(TextFieldUtil.parseInteger(textFieldInputSize3DY));
                layer.getProperties().getInput().setChannel(TextFieldUtil.parseInteger(textFieldInputSize3DZ));
                break;
        }
    }

    private void refreshOutputSize(){
        int inputVolume = layer.getProperties().getInput().getVolume();
        List<Integer> recommendedDivisors;
        int outputSizeY;
        int outputSizeX;

        switch (layer.getProperties().getOutput().getDimensionType()){
            case ONE_DIMENSION:
                layer.getProperties().getOutput().setX(inputVolume);
                textFieldOutputSize1D.setText(String.valueOf(layer.getProperties().getOutput().getVolume()));
                break;
            case ONE_DIMENSION_WITH_CHANNEL:
                recommendedDivisors = MathUtil.getDivisors(inputVolume);
                outputSizeY = recommendedDivisors.get(getReshapeBlockProperty().getPointingIndex(recommendedDivisors.size()));
                outputSizeX = inputVolume / outputSizeY;
                layer.getProperties().getOutput().setX(outputSizeX);
                layer.getProperties().getOutput().setChannel(outputSizeY);
                textFieldOutputSize2DX.setText(String.valueOf(outputSizeX));
                textFieldOutputSize2DY.setText(String.valueOf(outputSizeY));
                break;
            case TWO_DIMENSION:
                recommendedDivisors = MathUtil.getDivisors(inputVolume);
                outputSizeY = recommendedDivisors.get(getReshapeBlockProperty().getPointingIndex(recommendedDivisors.size()));
                outputSizeX = inputVolume / outputSizeY;
                layer.getProperties().getOutput().setX(outputSizeX);
                layer.getProperties().getOutput().setY(outputSizeY);
                textFieldOutputSize2DX.setText(String.valueOf(outputSizeX));
                textFieldOutputSize2DY.setText(String.valueOf(outputSizeY));
                break;
            case TWO_DIMENSION_WITH_CHANNEL:
                int outputSizeZ = TextFieldUtil.parseInteger(textFieldOutputSize3DZ);
                if(outputSizeZ != layer.getProperties().getOutput().getChannel()){
                    // textFieldOutputSize3DZ changed!
                    if(inputVolume % outputSizeZ != 0)
                        outputSizeZ = 1;
                    getReshapeBlockProperty().setPointingIndex(0);
                }
                inputVolume /= outputSizeZ;
                recommendedDivisors = MathUtil.getDivisors(inputVolume);
                outputSizeY = recommendedDivisors.get(getReshapeBlockProperty().getPointingIndex(recommendedDivisors.size()));
                outputSizeX = inputVolume / outputSizeY;
                layer.getProperties().getOutput().setX(outputSizeX);
                layer.getProperties().getOutput().setY(outputSizeY);
                layer.getProperties().getOutput().setChannel(outputSizeZ);
                detachTextChangedListener(textFieldOutputSize3DZ);
                textFieldOutputSize3DX.setText(String.valueOf(outputSizeX));
                textFieldOutputSize3DY.setText(String.valueOf(outputSizeY));
                textFieldOutputSize3DZ.setText(String.valueOf(outputSizeZ));
                attachTextChangedListener(textFieldOutputSize3DZ);
                break;
        }
    }

    private void refreshInputComponent(Dimension inputDimension) {
        set1DInputComponentVisible(false);
        set2DInputComponentVisible(false);
        set3DInputComponentVisible(false);
        switch (inputDimension.getDimensionType()){
            case ONE_DIMENSION:
                set1DInputComponentVisible(true);
                labelInput1DX.setText(AppPropertiesSingleton.getInstance().get("frame.component.width"));
                break;
            case ONE_DIMENSION_WITH_CHANNEL:
                set2DInputComponentVisible(true);
                labelInput2DX.setText(AppPropertiesSingleton.getInstance().get("frame.component.width"));
                labelInput2DY.setText(AppPropertiesSingleton.getInstance().get("frame.component.channel"));
                break;
            case TWO_DIMENSION:
                set2DInputComponentVisible(true);
                labelInput2DX.setText(AppPropertiesSingleton.getInstance().get("frame.component.width"));
                labelInput2DY.setText(AppPropertiesSingleton.getInstance().get("frame.component.height"));
                break;
            case TWO_DIMENSION_WITH_CHANNEL:
                set3DInputComponentVisible(true);
                labelInput3DX.setText(AppPropertiesSingleton.getInstance().get("frame.component.width"));
                labelInput3DY.setText(AppPropertiesSingleton.getInstance().get("frame.component.height"));
                labelInput3DZ.setText(AppPropertiesSingleton.getInstance().get("frame.component.channel"));
                break;
        }
    }

    private void set1DInputComponentVisible(boolean visible){
        anchorPaneInputLabel1D.setVisible(visible);
        anchorPaneInputSize1D.setVisible(visible);
    }

    private void set2DInputComponentVisible(boolean visible){
        anchorPaneInputLabel2D.setVisible(visible);
        anchorPaneInputSize2D.setVisible(visible);
    }

    private void set3DInputComponentVisible(boolean visible){
        anchorPaneInputLabel3D.setVisible(visible);
        anchorPaneInputSize3D.setVisible(visible);
    }

    private void refreshOutputComponent(Dimension outputDimension) {
        set1DOutputComponentVisible(false);
        set2DOutputComponentVisible(false);
        set3DOutputComponentVisible(false);
        switch (outputDimension.getDimensionType()){
            case ONE_DIMENSION:
                set1DOutputComponentVisible(true);
                labelOutput1DX.setText(AppPropertiesSingleton.getInstance().get("frame.component.width"));
                break;
            case ONE_DIMENSION_WITH_CHANNEL:
                set2DOutputComponentVisible(true);
                labelOutput2DX.setText(AppPropertiesSingleton.getInstance().get("frame.component.width"));
                labelOutput2DY.setText(AppPropertiesSingleton.getInstance().get("frame.component.channel"));
                break;
            case TWO_DIMENSION:
                set2DOutputComponentVisible(true);
                labelOutput2DX.setText(AppPropertiesSingleton.getInstance().get("frame.component.width"));
                labelOutput2DY.setText(AppPropertiesSingleton.getInstance().get("frame.component.height"));
                break;
            case TWO_DIMENSION_WITH_CHANNEL:
                set3DOutputComponentVisible(true);
                labelOutput3DX.setText(AppPropertiesSingleton.getInstance().get("frame.component.width"));
                labelOutput3DY.setText(AppPropertiesSingleton.getInstance().get("frame.component.height"));
                labelOutput3DZ.setText(AppPropertiesSingleton.getInstance().get("frame.component.channel"));
                break;
        }
    }

    private void set1DOutputComponentVisible(boolean visible){
        anchorPaneOutputLabel1D.setVisible(visible);
        anchorPaneOutputSize1D.setVisible(visible);
    }

    private void set2DOutputComponentVisible(boolean visible){
        anchorPaneOutputLabel2D.setVisible(visible);
        anchorPaneOutputSize2D.setVisible(visible);
    }

    private void set3DOutputComponentVisible(boolean visible){
        anchorPaneOutputLabel3D.setVisible(visible);
        anchorPaneOutputSize3D.setVisible(visible);
    }

    private ReshapeBlockProperty getReshapeBlockProperty(){
        return (ReshapeBlockProperty) CanvasFacade.findGraphNodeByLayerId(layer.getId()).getData().getBlockInfo().getExtra();
    }
}
