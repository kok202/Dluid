package org.kokzoz.dluid.content.design.component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.kokzoz.dluid.AppConstant;
import org.kokzoz.dluid.adapter.MenuAdapter;
import org.kokzoz.dluid.domain.entity.Layer;
import org.kokzoz.dluid.domain.entity.enumerator.Dimension;
import org.kokzoz.dluid.singleton.AppPropertiesSingleton;
import org.kokzoz.dluid.util.TextFieldUtil;

public class ComponentDelegateParamController extends AbstractLayerComponentController {

    @FXML private Label labelInputOutput1DX;
    @FXML private Label labelInputOutput2DX;
    @FXML private Label labelInputOutput2DY;
    @FXML private Label labelInputOutput3DX;
    @FXML private Label labelInputOutput3DY;
    @FXML private Label labelInputOutput3DZ;
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

    public ComponentDelegateParamController(Layer layer) {
        super(layer);
    }

    @Override
    public AnchorPane createView() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/design/component/delegate_param.fxml"));
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
        labelInputOutput1DX.setText(AppPropertiesSingleton.getInstance().get("frame.component.width"));
        labelInputOutput2DX.setText(AppPropertiesSingleton.getInstance().get("frame.component.width"));
        labelInputOutput2DY.setText(AppPropertiesSingleton.getInstance().get("frame.component.height"));
        labelInputOutput3DX.setText(AppPropertiesSingleton.getInstance().get("frame.component.width"));
        labelInputOutput3DY.setText(AppPropertiesSingleton.getInstance().get("frame.component.height"));
        labelInputOutput3DZ.setText(AppPropertiesSingleton.getInstance().get("frame.component.channel"));
        labelInput.setText(AppPropertiesSingleton.getInstance().get("frame.component.default.inputSize"));
        labelOutput.setText(AppPropertiesSingleton.getInstance().get("frame.component.default.outputSize"));
        labelInputOutputDimension.setText(AppPropertiesSingleton.getInstance().get("frame.component.switch.inputOutputDimension"));
        changeInputOutputLabelByChannelExist(layer.getProperties().getInputDimension().isHasChannel());
    }

    private void changeInputOutputLabelByChannelExist(boolean isChannel) {
        if(isChannel) {
            labelInputOutput2DY.setText(AppPropertiesSingleton.getInstance().get("frame.component.channel"));
            labelInputOutput3DZ.setText(AppPropertiesSingleton.getInstance().get("frame.component.channel"));
        }
        else{
            labelInputOutput2DY.setText(AppPropertiesSingleton.getInstance().get("frame.component.height"));
            labelInputOutput3DZ.setText(AppPropertiesSingleton.getInstance().get("frame.component.height"));
        }
    }
    private void setTextFieldByLayerProperties(){
        detachTextChangedListener(
                textFieldInputSize1D,
                textFieldInputSize2DX, textFieldInputSize2DY,
                textFieldInputSize3DX, textFieldInputSize3DY, textFieldInputSize3DZ);
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
                textFieldInputSize3DX, textFieldInputSize3DY, textFieldInputSize3DZ);
    }

    @Override
    protected void textFieldChangedHandler(){
        refreshInputOutputSize();
        reshapeBlock();
    }

    private void initializeMenuButtonInputDimension(){
        MenuAdapter<Dimension> menuAdapter = new MenuAdapter<>(menuButtonInputDimension);
        menuAdapter.setMenuItemChangedListener(dimension -> {
            layer.getProperties().setInputDimension(dimension);
            anchorPaneInputOutputLabel1D.setVisible(dimension.getDimension() == 1);
            anchorPaneInputOutputLabel2D.setVisible(dimension.getDimension() == 2);
            anchorPaneInputOutputLabel3D.setVisible(dimension.getDimension() == 3);
            anchorPaneInputSize1D.setVisible(dimension.getDimension() == 1);
            anchorPaneInputSize2D.setVisible(dimension.getDimension() == 2);
            anchorPaneInputSize3D.setVisible(dimension.getDimension() == 3);
            anchorPaneOutputSize1D.setVisible(dimension.getDimension() == 1);
            anchorPaneOutputSize2D.setVisible(dimension.getDimension() == 2);
            anchorPaneOutputSize3D.setVisible(dimension.getDimension() == 3);
            refreshInputOutputSize();
            reshapeBlock();
            changeInputOutputLabelByChannelExist(layer.getProperties().getInputDimension().isHasChannel());
        });
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.component.reshape.1d"), Dimension.ONE_DIMENSION);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.component.reshape.2d"), Dimension.TWO_DIMENSION);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.component.reshape.2d.withChannel"), Dimension.TWO_DIMENSION_WITH_CHANNEL);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.component.reshape.3d"), Dimension.THREE_DIMENSION);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.component.reshape.3d.withChannel"), Dimension.THREE_DIMENSION_WITH_CHANNEL);
        menuAdapter.setDefaultMenuItem(layer.getProperties().getInputDimension());
    }

    private void refreshInputOutputSize(){
        if(layer.getProperties().getInputDimension().getDimension() == 1){
            layer.getProperties().setInputSize(
                    TextFieldUtil.parseInteger(textFieldInputSize1D));
            layer.getProperties().setOutputSize(
                    TextFieldUtil.parseInteger(textFieldInputSize1D));
            textFieldOutputSize1D.setText(textFieldInputSize1D.getText());

        }
        else if(layer.getProperties().getInputDimension().getDimension() == 2){
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
