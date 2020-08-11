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
        changeInputLabelByChannelExist(layer.getProperties().getInput().isHasChannel());
        changeOutputLabelByChannelExist(layer.getProperties().getOutput().isHasChannel());
    }

    private void changeInputLabelByChannelExist(boolean isHasChannel) {
        if(isHasChannel) {
            labelInput2DY.setText(AppPropertiesSingleton.getInstance().get("frame.component.channel"));
            labelInput3DZ.setText(AppPropertiesSingleton.getInstance().get("frame.component.channel"));
        }
        else{
            labelInput2DY.setText(AppPropertiesSingleton.getInstance().get("frame.component.height"));
            labelInput3DZ.setText(AppPropertiesSingleton.getInstance().get("frame.component.height"));
        }
    }

    private void changeOutputLabelByChannelExist(boolean isHasChannel) {
        if(isHasChannel) {
            labelOutput2DY.setText(AppPropertiesSingleton.getInstance().get("frame.component.channel"));
            labelOutput3DZ.setText(AppPropertiesSingleton.getInstance().get("frame.component.channel"));
        }
        else{
            labelOutput2DY.setText(AppPropertiesSingleton.getInstance().get("frame.component.depth"));
            labelOutput3DZ.setText(AppPropertiesSingleton.getInstance().get("frame.component.depth"));
        }
    }

    private void setTextFieldByLayerProperties(){
        detachTextChangedListener(
                textFieldInputSize1D,
                textFieldInputSize2DX, textFieldInputSize2DY,
                textFieldInputSize3DX, textFieldInputSize3DY, textFieldInputSize3DZ,
                textFieldOutputSize3DZ);
        textFieldInputSize1D.setText(String.valueOf(layer.getProperties().getInput().getX()));
        textFieldInputSize2DX.setText(String.valueOf(layer.getProperties().getInput().getX()));
        textFieldInputSize2DY.setText(String.valueOf(layer.getProperties().getInput().getY()));
        textFieldInputSize3DX.setText(String.valueOf(layer.getProperties().getInput().getX()));
        textFieldInputSize3DY.setText(String.valueOf(layer.getProperties().getInput().getY()));
        textFieldInputSize3DZ.setText(String.valueOf(layer.getProperties().getInput().getZ()));
        textFieldOutputSize1D.setText(String.valueOf(layer.getProperties().getOutput().getX()));
        textFieldOutputSize2DX.setText(String.valueOf(layer.getProperties().getOutput().getX()));
        textFieldOutputSize2DY.setText(String.valueOf(layer.getProperties().getOutput().getY()));
        textFieldOutputSize3DX.setText(String.valueOf(layer.getProperties().getOutput().getX()));
        textFieldOutputSize3DY.setText(String.valueOf(layer.getProperties().getOutput().getY()));
        textFieldOutputSize3DZ.setText(String.valueOf(layer.getProperties().getOutput().getZ()));
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
            Dimension inputDimension = layer.getProperties().getInput();
            anchorPaneInputLabel1D.setVisible(inputDimension.getDimension() == 1);
            anchorPaneInputLabel2D.setVisible(inputDimension.getDimension() == 2);
            anchorPaneInputLabel3D.setVisible(inputDimension.getDimension() == 3);
            anchorPaneInputSize1D.setVisible(inputDimension.getDimension() == 1);
            anchorPaneInputSize2D.setVisible(inputDimension.getDimension() == 2);
            anchorPaneInputSize3D.setVisible(inputDimension.getDimension() == 3);
            refreshInputSize();
            refreshOutputSize();
            reshapeBlock();
            changeInputLabelByChannelExist(layer.getProperties().getInput().isHasChannel());
        });
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.component.reshape.1d"), DimensionType.ONE_DIMENSION);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.component.reshape.1d.withChannel"), DimensionType.ONE_DIMENSION_WITH_CHANNEL);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.component.reshape.2d"), DimensionType.TWO_DIMENSION);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.component.reshape.2d.withChannel"), DimensionType.TWO_DIMENSION_WITH_CHANNEL);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.component.reshape.3d"), DimensionType.THREE_DIMENSION);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.component.reshape.3d.withChannel"), DimensionType.THREE_DIMENSION_WITH_CHANNEL);
        menuAdapter.setDefaultMenuItem(layer.getProperties().getInput().getDimensionType());
    }

    private void initializeMenuButtonOutputDimension(){
        MenuAdapter<DimensionType> menuAdapter = new MenuAdapter<>(menuButtonOutputDimension);
        menuAdapter.setMenuItemChangedListener(dimensionType -> {
            layer.getProperties().getInput().changeDimensionByType(dimensionType);
            Dimension inputDimension = layer.getProperties().getInput();
            anchorPaneOutputLabel1D.setVisible(inputDimension.getDimension() == 1);
            anchorPaneOutputLabel2D.setVisible(inputDimension.getDimension() == 2);
            anchorPaneOutputLabel3D.setVisible(inputDimension.getDimension() == 3);
            anchorPaneOutputSize1D.setVisible(inputDimension.getDimension() == 1);
            anchorPaneOutputSize2D.setVisible(inputDimension.getDimension() == 2);
            anchorPaneOutputSize3D.setVisible(inputDimension.getDimension() == 3);
            hBoxOutputSizeChange.setVisible(inputDimension.getDimension() != 1);
            refreshOutputSize();
            reshapeBlock();
            changeOutputLabelByChannelExist(layer.getProperties().getOutput().isHasChannel());
        });
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.component.reshape.1d"), DimensionType.ONE_DIMENSION);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.component.reshape.1d.withChannel"), DimensionType.ONE_DIMENSION_WITH_CHANNEL);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.component.reshape.2d"), DimensionType.TWO_DIMENSION);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.component.reshape.2d.withChannel"), DimensionType.TWO_DIMENSION_WITH_CHANNEL);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.component.reshape.3d"), DimensionType.THREE_DIMENSION);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.component.reshape.3d.withChannel"), DimensionType.THREE_DIMENSION_WITH_CHANNEL);
        menuAdapter.setDefaultMenuItem(layer.getProperties().getOutput().getDimensionType());
    }

    private void refreshInputSize(){
        if(layer.getProperties().getInput().getDimension() == 1){
            layer.getProperties().getInput().setX(TextFieldUtil.parseInteger(textFieldInputSize1D));
        }
        else if(layer.getProperties().getInput().getDimension() == 2){
            layer.getProperties().getInput().setX(TextFieldUtil.parseInteger(textFieldInputSize2DX));
            layer.getProperties().getInput().setY(TextFieldUtil.parseInteger(textFieldInputSize2DY));
        }
        else{
            layer.getProperties().getInput().setX(TextFieldUtil.parseInteger(textFieldInputSize3DX));
            layer.getProperties().getInput().setY(TextFieldUtil.parseInteger(textFieldInputSize3DY));
            layer.getProperties().getInput().setZ(TextFieldUtil.parseInteger(textFieldInputSize3DZ));
        }
    }

    private void refreshOutputSize(){
        int inputVolume = layer.getProperties().getInput().getVolume();
        if(layer.getProperties().getOutput().getDimension() == 1) {
            layer.getProperties().getOutput().setX(inputVolume);;
            textFieldOutputSize1D.setText(String.valueOf(layer.getProperties().getOutput().getVolume()));
        }
        else if(layer.getProperties().getOutput().getDimension() == 2){
            List<Integer> recommendedDivisors = MathUtil.getDivisors(inputVolume);
            int outputSizeY = recommendedDivisors.get(getReshapeBlockProperty().getPointingIndex(recommendedDivisors.size()));
            int outputSizeX = inputVolume / outputSizeY;
            layer.getProperties().getOutput().setX(outputSizeX);
            layer.getProperties().getOutput().setY(outputSizeY);

            textFieldOutputSize2DX.setText(String.valueOf(outputSizeX));
            textFieldOutputSize2DY.setText(String.valueOf(outputSizeY));
        }
        else{
            int outputSizeZ = TextFieldUtil.parseInteger(textFieldOutputSize3DZ);
            if(outputSizeZ != layer.getProperties().getOutput().getZ()){
                // textFieldOutputSize3DZ changed!
                if(inputVolume % outputSizeZ != 0)
                    outputSizeZ = 1;
                getReshapeBlockProperty().setPointingIndex(0);
            }

            inputVolume /= outputSizeZ;
            List<Integer> recommendedDivisors = MathUtil.getDivisors(inputVolume);
            int outputSizeY = recommendedDivisors.get(getReshapeBlockProperty().getPointingIndex(recommendedDivisors.size()));
            int outputSizeX = inputVolume / outputSizeY;
            layer.getProperties().getOutput().setX(outputSizeX);
            layer.getProperties().getOutput().setY(outputSizeY);
            layer.getProperties().getOutput().setZ(outputSizeZ);

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
