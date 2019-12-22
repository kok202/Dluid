package org.kok202.dluid.application.common;

import javafx.scene.control.Alert;
import org.kok202.dluid.application.Util.DialogUtil;
import org.kok202.dluid.application.singleton.AppPropertiesSingleton;
import org.kok202.dluid.domain.exception.*;

public class ExceptionHandler {
    public static void catchException(Thread thread, Throwable exception) {
        System.err.println("Exception handler catch : " + exception.getMessage());

        if(exception instanceof IllegalConnectionRequest){
            DialogUtil.builder()
                    .alertType(Alert.AlertType.INFORMATION)
                    .title(AppPropertiesSingleton.getInstance().get("frame.dialog.illegalConnectionRequest.title"))
                    .headerText(AppPropertiesSingleton.getInstance().get("frame.dialog.illegalConnectionRequest.header"))
                    .contentText(AppPropertiesSingleton.getInstance().get("frame.dialog.illegalConnectionRequest.content"))
                    .build()
                    .showAndWait();
        }
        else if(exception instanceof BlockConnectionImpossibleException){
            DialogUtil.builder()
                    .alertType(Alert.AlertType.INFORMATION)
                    .title(AppPropertiesSingleton.getInstance().get("frame.dialog.blockConnectionFail.title"))
                    .headerText(AppPropertiesSingleton.getInstance().get("frame.dialog.blockConnectionFail.header"))
                    .contentText(AppPropertiesSingleton.getInstance().get("frame.dialog.blockConnectionFail.content"))
                    .build()
                    .showAndWait();
        }
        else if(exception instanceof ExcelIllegalDataFormatException){
            DialogUtil.builder()
                    .alertType(Alert.AlertType.ERROR)
                    .title(AppPropertiesSingleton.getInstance().get("frame.dialog.excel.invalidValue.title"))
                    .headerText(AppPropertiesSingleton.getInstance().get("frame.dialog.excel.invalidValue.header"))
                    .contentText(AppPropertiesSingleton.getInstance().get("frame.dialog.excel.invalidValue.content"))
                    .build()
                    .showAndWait();
        }
        else if(exception instanceof ExcelPositionOutOfTableException){
            DialogUtil.builder()
                    .alertType(Alert.AlertType.ERROR)
                    .title(AppPropertiesSingleton.getInstance().get("frame.dialog.excel.outOfTable.title"))
                    .headerText(AppPropertiesSingleton.getInstance().get("frame.dialog.excel.outOfTable.header"))
                    .contentText(AppPropertiesSingleton.getInstance().get("frame.dialog.excel.outOfTable.content") + exception.getMessage())
                    .build()
                    .showAndWait();
        }
        else if(exception instanceof ConvolutionOutputIsNegativeException){
            ConvolutionOutputIsNegativeException convolutionOutputIsNegativeException = ((ConvolutionOutputIsNegativeException) exception);
            DialogUtil.builder()
                    .alertType(Alert.AlertType.ERROR)
                    .title(AppPropertiesSingleton.getInstance().get("frame.dialog.convolutionNegativeSize.title"))
                    .headerText(AppPropertiesSingleton.getInstance().get("frame.dialog.convolutionNegativeSize.header") + convolutionOutputIsNegativeException.getOutputSize())
                    .contentText(AppPropertiesSingleton.getInstance().get("frame.dialog.convolutionNegativeSize.content"))
                    .build()
                    .showAndWait();
        }
        else if(exception instanceof DimensionUnmatchedException){
            DimensionUnmatchedException dimensionUnmatchedException = ((DimensionUnmatchedException) exception);
            DialogUtil.builder()
                    .alertType(Alert.AlertType.ERROR)
                    .title(AppPropertiesSingleton.getInstance().get("frame.dialog.dimensionUnmatched.title"))
                    .headerText(AppPropertiesSingleton.getInstance().get("frame.dialog.dimensionUnmatched.header"))
                    .contentText(AppPropertiesSingleton.getInstance().get("frame.dialog.dimensionUnmatched.content") + "\n"
                        + dimensionUnmatchedException.getSourceLayerId() + " : " + dimensionUnmatchedException.getSourceLayerOutputSize() + "\n"
                        + dimensionUnmatchedException.getDestinationLayerId() + " : " + dimensionUnmatchedException.getDestinationInputSize())
                    .build()
                    .showAndWait();
        }
        else if(exception instanceof FeatureSetDimensionUnmatchedException){
            FeatureSetDimensionUnmatchedException featureSetDimensionUnmatchedException = ((FeatureSetDimensionUnmatchedException) exception);
            DialogUtil.builder()
                    .alertType(Alert.AlertType.ERROR)
                    .title(AppPropertiesSingleton.getInstance().get("frame.dialog.dimensionUnmatched.featureSet.title"))
                    .headerText(AppPropertiesSingleton.getInstance().get("frame.dialog.dimensionUnmatched.featureSet.header"))
                    .contentText(AppPropertiesSingleton.getInstance().get("frame.dialog.dimensionUnmatched.featureSet.content") + "\n"
                            + featureSetDimensionUnmatchedException.getInputLayerId() + "\n"
                            + "Layer : " + featureSetDimensionUnmatchedException.getInputLayerDimension() + "\n"
                            + "Data : " + featureSetDimensionUnmatchedException.getFeatureSetDimension())
                    .build()
                    .showAndWait();
        }
        else if(exception instanceof ResultSetDimensionUnmatchedException){
            ResultSetDimensionUnmatchedException resultSetDimensionUnmatchedException = ((ResultSetDimensionUnmatchedException) exception);
            DialogUtil.builder()
                    .alertType(Alert.AlertType.ERROR)
                    .title(AppPropertiesSingleton.getInstance().get("frame.dialog.dimensionUnmatched.resultSet.title"))
                    .headerText(AppPropertiesSingleton.getInstance().get("frame.dialog.dimensionUnmatched.resultSet.header"))
                    .contentText(AppPropertiesSingleton.getInstance().get("frame.dialog.dimensionUnmatched.resultSet.content") + "\n"
                            + resultSetDimensionUnmatchedException.getOutputLayerId() + "\n"
                            + "Layer : " + resultSetDimensionUnmatchedException.getOutputLayerDimension() + "\n"
                            + "Data : " + resultSetDimensionUnmatchedException.getResultSetDimension())
                    .build()
                    .showAndWait();
        }
        else if(exception instanceof InvalidParameterException){
            InvalidParameterException invalidParameterException = ((InvalidParameterException) exception);
            DialogUtil.builder()
                    .alertType(Alert.AlertType.ERROR)
                    .title(AppPropertiesSingleton.getInstance().get("frame.dialog.parameterUnset.title"))
                    .headerText(AppPropertiesSingleton.getInstance().get("frame.dialog.parameterUnset.header"))
                    .contentText(AppPropertiesSingleton.getInstance().get("frame.dialog.parameterUnset.content") + invalidParameterException.getMessage())
                    .build()
                    .showAndWait();
        }
        else if(exception instanceof InvalidBatchSize){
            InvalidBatchSize invalidBatchSize = ((InvalidBatchSize) exception);
            DialogUtil.builder()
                    .alertType(Alert.AlertType.ERROR)
                    .title(AppPropertiesSingleton.getInstance().get("frame.dialog.invalidBatchSize.title"))
                    .headerText(AppPropertiesSingleton.getInstance().get("frame.dialog.invalidBatchSize.header"))
                    .contentText(AppPropertiesSingleton.getInstance().get("frame.dialog.invalidBatchSize.content") + invalidBatchSize.getRecommendedSize())
                    .build()
                    .showAndWait();
        }
        else if(exception instanceof MultiOutputLayerException){
            DialogUtil.builder()
                    .alertType(Alert.AlertType.INFORMATION)
                    .title(AppPropertiesSingleton.getInstance().get("frame.dialog.multiOutputLayer.title"))
                    .headerText(AppPropertiesSingleton.getInstance().get("frame.dialog.multiOutputLayer.header"))
                    .contentText(AppPropertiesSingleton.getInstance().get("frame.dialog.multiOutputLayer.content"))
                    .build()
                    .showAndWait();
        }
        else if(exception instanceof MultiTestInputLayerException){
            DialogUtil.builder()
                    .alertType(Alert.AlertType.INFORMATION)
                    .title(AppPropertiesSingleton.getInstance().get("frame.dialog.multiTestInputLayer.title"))
                    .headerText(AppPropertiesSingleton.getInstance().get("frame.dialog.multiTestInputLayer.header"))
                    .contentText(AppPropertiesSingleton.getInstance().get("frame.dialog.multiTestInputLayer.content"))
                    .build()
                    .showAndWait();
        }
        else if(exception instanceof CanNotFindInputLayerException){
            DialogUtil.builder()
                    .alertType(Alert.AlertType.ERROR)
                    .title(AppPropertiesSingleton.getInstance().get("frame.dialog.canNotFindInputLayer.title"))
                    .headerText(AppPropertiesSingleton.getInstance().get("frame.dialog.canNotFindInputLayer.header"))
                    .contentText(AppPropertiesSingleton.getInstance().get("frame.dialog.canNotFindInputLayer.content"))
                    .build()
                    .showAndWait();
        }
        else if(exception instanceof CanNotFindOutputLayerException){
            DialogUtil.builder()
                    .alertType(Alert.AlertType.ERROR)
                    .title(AppPropertiesSingleton.getInstance().get("frame.dialog.canNotFindOutputLayer.title"))
                    .headerText(AppPropertiesSingleton.getInstance().get("frame.dialog.canNotFindOutputLayer.header"))
                    .contentText(AppPropertiesSingleton.getInstance().get("frame.dialog.canNotFindOutputLayer.content"))
                    .build()
                    .showAndWait();
        }
        else if(exception instanceof InvalidMergeConnectionExistException){
            InvalidMergeConnectionExistException invalidMergeConnectionExistException = ((InvalidMergeConnectionExistException) exception);
            DialogUtil.builder()
                    .alertType(Alert.AlertType.ERROR)
                    .title(AppPropertiesSingleton.getInstance().get("frame.dialog.mergeConnectionImpossible.title"))
                    .headerText(AppPropertiesSingleton.getInstance().get("frame.dialog.mergeConnectionImpossible.header"))
                    .contentText(AppPropertiesSingleton.getInstance().get("frame.dialog.mergeConnectionImpossible.content") + invalidMergeConnectionExistException.getLayerId())
                    .build()
                    .showAndWait();
        }
        else if(exception instanceof InvalidSwitchConnectionExistException){
            InvalidSwitchConnectionExistException invalidSwitchConnectionExistException = ((InvalidSwitchConnectionExistException) exception);
            DialogUtil.builder()
                    .alertType(Alert.AlertType.ERROR)
                    .title(AppPropertiesSingleton.getInstance().get("frame.dialog.switchConnectionImpossible.title"))
                    .headerText(AppPropertiesSingleton.getInstance().get("frame.dialog.switchConnectionImpossible.header"))
                    .contentText(AppPropertiesSingleton.getInstance().get("frame.dialog.switchConnectionImpossible.content") + invalidSwitchConnectionExistException.getLayerId())
                    .build()
                    .showAndWait();
        }
        else {
            DialogUtil.builder()
                    .alertType(Alert.AlertType.ERROR)
                    .title(AppPropertiesSingleton.getInstance().get("frame.dialog.default.error.title"))
                    .headerText(exception.getClass().getName())
                    .contentText(exception.getMessage())
                    .build()
                    .showAndWait();
            exception.printStackTrace();
        }
    }
}
