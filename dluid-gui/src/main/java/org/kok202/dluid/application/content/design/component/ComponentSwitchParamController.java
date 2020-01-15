package org.kok202.dluid.application.content.design.component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.kok202.dluid.AppConstant;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.application.adapter.MenuAdapter;
import org.kok202.dluid.application.singleton.AppPropertiesSingleton;
import org.kok202.dluid.application.util.TextFieldUtil;

public class ComponentSwitchParamController extends AbstractLayerComponentController {

    @FXML private Label labelInputOutput1DWidth;
    @FXML private Label labelInputOutput2DWidth;
    @FXML private Label labelInputOutput2DChannel;
    @FXML private Label labelInputOutput3DWidth;
    @FXML private Label labelInputOutput3DHeight;
    @FXML private Label labelInputOutput3DChannel;
    @FXML private Label labelInput;
    @FXML private Label labelOutput;
    @FXML private Label labelInputOutputDimension;

    @FXML private AnchorPane anchorPaneInputOutputLabel1D;
    @FXML private AnchorPane anchorPaneInputOutputLabel2D;
    @FXML private AnchorPane anchorPaneInputOutputLabel3D;

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

    public ComponentSwitchParamController(Layer layer) {
        super(layer);
    }

    @Override
    public AnchorPane createView() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/design/component/switch_param.fxml"));
        fxmlLoader.setController(this);
        return fxmlLoader.load();
    }

    @Override
    protected void initialize() throws Exception {
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
        titledPane.setText(AppPropertiesSingleton.getInstance().get("frame.component.default.title"));
        labelInputOutput1DWidth.setText(AppPropertiesSingleton.getInstance().get("frame.component.width"));
        labelInputOutput2DWidth.setText(AppPropertiesSingleton.getInstance().get("frame.component.width"));
        labelInputOutput2DChannel.setText(AppPropertiesSingleton.getInstance().get("frame.component.height"));
        labelInputOutput3DWidth.setText(AppPropertiesSingleton.getInstance().get("frame.component.width"));
        labelInputOutput3DHeight.setText(AppPropertiesSingleton.getInstance().get("frame.component.height"));
        labelInputOutput3DChannel.setText(AppPropertiesSingleton.getInstance().get("frame.component.channel"));
        labelInput.setText(AppPropertiesSingleton.getInstance().get("frame.component.default.inputSize"));
        labelOutput.setText(AppPropertiesSingleton.getInstance().get("frame.component.default.outputSize"));
        labelInputOutputDimension.setText(AppPropertiesSingleton.getInstance().get("frame.component.switch.inputOutputDimension"));
    }

    private void setTextFieldByLayerProperties(){
        detachTextChangedListener(
                textFieldInputSize1D,
                textFieldInputSize2DX, textFieldInputSize2DY,
                textFieldInputSize3DX, textFieldInputSize3DY, textFieldInputSize3DZ);
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
                textFieldInputSize3DX, textFieldInputSize3DY, textFieldInputSize3DZ);
    }

    @Override
    protected void textFieldChangedHandler(){
        refreshInputOutputSize();
        notifyLayerDataChanged();
    }

    private void initializeMenuButtonInputDimension(){
        MenuAdapter<Integer> menuAdapter = new MenuAdapter<>(menuButtonInputDimension);
        menuAdapter.setMenuItemChangedListener(dimension -> {
            layer.getProperties().setInputDimension(dimension);
            anchorPaneInputOutputLabel1D.setVisible(dimension == 1);
            anchorPaneInputOutputLabel2D.setVisible(dimension == 2);
            anchorPaneInputOutputLabel3D.setVisible(dimension == 3);
            anchorPaneInputSize1D.setVisible(dimension == 1);
            anchorPaneInputSize2D.setVisible(dimension == 2);
            anchorPaneInputSize3D.setVisible(dimension == 3);
            anchorPaneOutputSize1D.setVisible(dimension == 1);
            anchorPaneOutputSize2D.setVisible(dimension == 2);
            anchorPaneOutputSize3D.setVisible(dimension == 3);
            refreshInputOutputSize();
            notifyLayerDataChanged();
        });
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.component.reshape.1d"), 1);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.component.reshape.2d"), 2);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.component.reshape.3d"), 3);
        menuAdapter.setDefaultMenuItem((Integer) layer.getProperties().getInputDimension());
    }

    private void refreshInputOutputSize(){
        if(layer.getProperties().getInputDimension() == 1){
            layer.getProperties().setInputSize(
                    TextFieldUtil.parseInteger(textFieldInputSize1D));
            layer.getProperties().setOutputSize(
                    TextFieldUtil.parseInteger(textFieldInputSize1D));
            textFieldOutputSize1D.setText(textFieldInputSize1D.getText());

        }
        else if(layer.getProperties().getInputDimension() == 2){
            layer.getProperties().setInputSize(
                    TextFieldUtil.parseInteger(textFieldInputSize2DX),
                    TextFieldUtil.parseInteger(textFieldInputSize2DY));
            layer.getProperties().setOutputSize(
                    TextFieldUtil.parseInteger(textFieldInputSize2DX),
                    TextFieldUtil.parseInteger(textFieldInputSize2DY));
            textFieldOutputSize2DX.setText(textFieldInputSize2DX.getText());
            textFieldOutputSize2DY.setText(textFieldInputSize2DY.getText());
        }
        else{
            layer.getProperties().setInputSize(
                    TextFieldUtil.parseInteger(textFieldInputSize3DX),
                    TextFieldUtil.parseInteger(textFieldInputSize3DY),
                    TextFieldUtil.parseInteger(textFieldInputSize3DZ));
            layer.getProperties().setOutputSize(
                    TextFieldUtil.parseInteger(textFieldInputSize3DX),
                    TextFieldUtil.parseInteger(textFieldInputSize3DY),
                    TextFieldUtil.parseInteger(textFieldInputSize3DZ));
            textFieldOutputSize3DX.setText(textFieldInputSize3DX.getText());
            textFieldOutputSize3DY.setText(textFieldInputSize3DY.getText());
            textFieldOutputSize3DZ.setText(textFieldInputSize3DZ.getText());
        }
    }
}
