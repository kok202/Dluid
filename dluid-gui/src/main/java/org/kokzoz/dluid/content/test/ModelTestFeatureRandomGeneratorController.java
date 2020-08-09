package org.kokzoz.dluid.content.test;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import lombok.Data;
import org.kokzoz.dluid.AppConstant;
import org.kokzoz.dluid.ai.AIFacade;
import org.kokzoz.dluid.content.TabModelTestController;
import org.kokzoz.dluid.domain.stream.NumericRecordSet;
import org.kokzoz.dluid.domain.util.RandomUtil;
import org.kokzoz.dluid.singleton.AppPropertiesSingleton;
import org.kokzoz.dluid.util.TextFieldUtil;

@Data
public class ModelTestFeatureRandomGeneratorController extends AbstractModelTestController {
    @FXML private TitledPane titledPane;
    @FXML private Label labelRandomMin;
    @FXML private Label labelRandomMax;
    @FXML private Label labelRandom;
    @FXML private Label labelRandomInputSize;
    @FXML private TextField textFieldRandomInputSize;
    @FXML private TextField textFieldRandomInputRangeMin;
    @FXML private TextField textFieldRandomInputRangeMax;
    @FXML private Label labelRandomRecordNumber;
    @FXML private TextField textFieldRandomRecordNumber;
    @FXML private ToggleGroup toggleGroupDataType;
    @FXML private Label labelRandomDataType;
    @FXML private RadioButton radioButtonIntegerType;
    @FXML private RadioButton radioButtonDoubleType;
    @FXML private RadioButton radioButtonGaussianType;
    @FXML private Button buttonGenerateRandomData;
    // 랜덤으로 데이터 불러오는 버튼이 필요

    public ModelTestFeatureRandomGeneratorController(TabModelTestController tabModelTestController) {
        super(tabModelTestController);
    }

    public AnchorPane createView() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/test/feature_random_generator.fxml"));
        fxmlLoader.setController(this);
        AnchorPane content = fxmlLoader.load();
        return content;
    }

    @Override
    protected void initialize() throws Exception {
        TextFieldUtil.applyPositiveIntegerFilter(textFieldRandomInputSize, AppConstant.DEFAULT_INPUT_SIZE);
        TextFieldUtil.applyPositiveDoubleFilter(textFieldRandomInputRangeMin, AppConstant.DEFAULT_RANDOM_RANGE_MIN);
        TextFieldUtil.applyPositiveDoubleFilter(textFieldRandomInputRangeMax, AppConstant.DEFAULT_RANDOM_RANGE_MAX);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldRandomRecordNumber, AppConstant.DEFAULT_RANDOM_RECORD_NUMBER);
        toggleGroupDataType.selectedToggleProperty().addListener(event -> {
            if(radioButtonGaussianType.isSelected()){
                textFieldRandomInputRangeMin.setDisable(true);
                textFieldRandomInputRangeMax.setDisable(true);
            }
            else {
                textFieldRandomInputRangeMin.setDisable(false);
                textFieldRandomInputRangeMax.setDisable(false);
            }
        });
        setGenerateButtonActionHandler();
        titledPane.setText(AppPropertiesSingleton.getInstance().get("frame.testTab.dataSetting.dataRandomGenerate.title"));
        labelRandomMin.setText(AppPropertiesSingleton.getInstance().get("frame.testTab.dataSetting.dataRandomGenerate.min"));
        labelRandomMax.setText(AppPropertiesSingleton.getInstance().get("frame.testTab.dataSetting.dataRandomGenerate.max"));
        labelRandom.setText(AppPropertiesSingleton.getInstance().get("frame.testTab.dataSetting.dataRandomGenerate.randomRange"));
        labelRandomInputSize.setText(AppPropertiesSingleton.getInstance().get("frame.testTab.dataSetting.dataRandomGenerate.inputSize"));
        labelRandomRecordNumber.setText(AppPropertiesSingleton.getInstance().get("frame.testTab.dataSetting.dataRandomGenerate.totalDataSize"));
        labelRandomDataType.setText(AppPropertiesSingleton.getInstance().get("frame.testTab.dataSetting.dataRandomGenerate.dataType"));
        radioButtonIntegerType.setText(AppPropertiesSingleton.getInstance().get("frame.testTab.dataSetting.dataRandomGenerate.integer"));
        radioButtonDoubleType.setText(AppPropertiesSingleton.getInstance().get("frame.testTab.dataSetting.dataRandomGenerate.float"));
        radioButtonGaussianType.setText(AppPropertiesSingleton.getInstance().get("frame.testTab.dataSetting.dataRandomGenerate.gaussian"));
        buttonGenerateRandomData.setText(AppPropertiesSingleton.getInstance().get("frame.testTab.dataSetting.dataRandomGenerate.generate"));
    }

    private void setGenerateButtonActionHandler(){
        buttonGenerateRandomData.setOnAction(event -> {
            int inputSize = TextFieldUtil.parseInteger(textFieldRandomInputSize);
            int recordNumber = TextFieldUtil.parseInteger(textFieldRandomRecordNumber);
            double randomRangeMin = TextFieldUtil.parseDouble(textFieldRandomInputRangeMin);
            double randomRangeMax = TextFieldUtil.parseDouble(textFieldRandomInputRangeMax);

            NumericRecordSet numericRecordSet = new NumericRecordSet();
            for(int i = 0; i < recordNumber; i++){
                double[] values = new double[inputSize];
                if(radioButtonIntegerType.isSelected()){
                    for(int j = 0; j < inputSize; j++)
                        values[j] = (double) RandomUtil.getInt(randomRangeMin, randomRangeMax);
                }
                else if(radioButtonDoubleType.isSelected()){
                    for(int j = 0; j < inputSize; j++)
                        values[j] = RandomUtil.getDouble(randomRangeMin, randomRangeMax);
                }
                else if(radioButtonGaussianType.isSelected()){
                    for(int j = 0; j < inputSize; j++)
                        values[j] = RandomUtil.getGaussian();
                }
                numericRecordSet.addRecord(values);
            }

            String[] headers = new String[inputSize];
            for(int i = 0; i < inputSize; i++)
                headers[i] = "Column " + i;

            numericRecordSet.setHeader(headers);
            AIFacade.getTestFeatureSet().setNumericRecordSet(numericRecordSet);
            getTabModelTestController()
                    .getModelTestFeatureController()
                    .getModelTestFeatureTableController()
                    .refreshTableView();
        });
    }
}
