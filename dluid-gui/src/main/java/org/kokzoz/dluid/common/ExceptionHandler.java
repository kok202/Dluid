package org.kokzoz.dluid.common;

import javafx.scene.control.Alert;
import org.deeplearning4j.exception.DL4JInvalidInputException;
import org.kokzoz.dluid.domain.exception.*;
import org.kokzoz.dluid.singleton.AppPropertiesSingleton;
import org.kokzoz.dluid.util.DialogUtil;

public class ExceptionHandler {
    public static void catchException(Thread thread, Throwable exception) {
        System.err.println("Exception handler catch : " + exception.getMessage());
        exception.printStackTrace();

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
                    .contentText(String.format(AppPropertiesSingleton.getInstance().get("frame.dialog.excel.outOfTable.content"), exception.getMessage()))
                    .build()
                    .showAndWait();
        }
        else if(exception instanceof ConvolutionOutputIsNegativeException){
            ConvolutionOutputIsNegativeException convolutionOutputIsNegativeException = ((ConvolutionOutputIsNegativeException) exception);
            DialogUtil.builder()
                    .alertType(Alert.AlertType.ERROR)
                    .title(AppPropertiesSingleton.getInstance().get("frame.dialog.convolutionNegativeSize.title"))
                    .headerText(String.format(AppPropertiesSingleton.getInstance().get("frame.dialog.convolutionNegativeSize.header"),convolutionOutputIsNegativeException.getOutputSize()))
                    .contentText(AppPropertiesSingleton.getInstance().get("frame.dialog.convolutionNegativeSize.content"))
                    .build()
                    .showAndWait();
        }
        else if(exception instanceof RecurrentLayerOutputExceedInputException){
            RecurrentLayerOutputExceedInputException recurrentLayerOutputExceedInputException = ((RecurrentLayerOutputExceedInputException) exception);
            DialogUtil.builder()
                    .alertType(Alert.AlertType.ERROR)
                    .title(AppPropertiesSingleton.getInstance().get("frame.dialog.recurrentLayerOutputExceedInputException.title"))
                    .headerText(String.format(AppPropertiesSingleton.getInstance().get("frame.dialog.recurrentLayerOutputExceedInputException.header"),
                            recurrentLayerOutputExceedInputException.getInputSize(),
                            recurrentLayerOutputExceedInputException.getOutputSize()))
                    .contentText(AppPropertiesSingleton.getInstance().get("frame.dialog.recurrentLayerOutputExceedInputException.content"))
                    .build()
                    .showAndWait();
        }
        else if(exception instanceof VolumeUnmatchedException){
            VolumeUnmatchedException volumeUnmatchedException = ((VolumeUnmatchedException) exception);
            DialogUtil.builder()
                    .alertType(Alert.AlertType.ERROR)
                    .title(AppPropertiesSingleton.getInstance().get("frame.dialog.volumeUnmatched.title"))
                    .headerText(AppPropertiesSingleton.getInstance().get("frame.dialog.volumeUnmatched.header"))
                    .contentText(String.format(AppPropertiesSingleton.getInstance().get("frame.dialog.volumeUnmatched.content"),
                            volumeUnmatchedException.getSourceLayerId(), volumeUnmatchedException.getSourceLayerOutputSize(), volumeUnmatchedException.getSourceLayerOutputVolume(),
                            volumeUnmatchedException.getDestinationLayerId(), volumeUnmatchedException.getDestinationInputSize(), volumeUnmatchedException.getDestinationInputVolume()))
                    .build()
                    .showAndWait();
        }
        else if(exception instanceof DimensionUnmatchedException){
            DimensionUnmatchedException dimensionUnmatchedException = ((DimensionUnmatchedException) exception);
            DialogUtil.builder()
                    .alertType(Alert.AlertType.ERROR)
                    .title(AppPropertiesSingleton.getInstance().get("frame.dialog.dimensionUnmatched.title"))
                    .headerText(AppPropertiesSingleton.getInstance().get("frame.dialog.dimensionUnmatched.header"))
                    .contentText(String.format(AppPropertiesSingleton.getInstance().get("frame.dialog.dimensionUnmatched.content"),
                            dimensionUnmatchedException.getSourceLayerId(), dimensionUnmatchedException.getSourceLayerOutputSize(), dimensionUnmatchedException.getSourceLayerOutputDimension(),
                            dimensionUnmatchedException.getDestinationLayerId(), dimensionUnmatchedException.getDestinationInputSize(), dimensionUnmatchedException.getSourceLayerInputDimension()))
                    .build()
                    .showAndWait();
        }
        else if(exception instanceof CanNotFindFeatureSetException){
            CanNotFindFeatureSetException canNotFindFeatureSetException = ((CanNotFindFeatureSetException) exception);
            DialogUtil.builder()
                    .alertType(Alert.AlertType.ERROR)
                    .title(AppPropertiesSingleton.getInstance().get("frame.dialog.featureSetNotExist.title"))
                    .headerText(AppPropertiesSingleton.getInstance().get("frame.dialog.featureSetNotExist.header"))
                    .contentText(String.format(AppPropertiesSingleton.getInstance().get("frame.dialog.featureSetNotExist.content"), canNotFindFeatureSetException.getLayerId()))
                    .build()
                    .showAndWait();
        }
        else if(exception instanceof CanNotFindResultSetException){
            CanNotFindResultSetException canNotFindResultSetException = ((CanNotFindResultSetException) exception);
            DialogUtil.builder()
                    .alertType(Alert.AlertType.ERROR)
                    .title(AppPropertiesSingleton.getInstance().get("frame.dialog.resultSetNotExist.title"))
                    .headerText(AppPropertiesSingleton.getInstance().get("frame.dialog.resultSetNotExist.header"))
                    .contentText(String.format(AppPropertiesSingleton.getInstance().get("frame.dialog.resultSetNotExist.content"), canNotFindResultSetException.getLayerId()))
                    .build()
                    .showAndWait();
        }
        else if(exception instanceof FeatureSetVolumeUnmatchedException){
            FeatureSetVolumeUnmatchedException featureSetVolumeUnmatchedException = ((FeatureSetVolumeUnmatchedException) exception);
            DialogUtil.builder()
                    .alertType(Alert.AlertType.ERROR)
                    .title(AppPropertiesSingleton.getInstance().get("frame.dialog.volumeUnmatched.featureSet.title"))
                    .headerText(AppPropertiesSingleton.getInstance().get("frame.dialog.volumeUnmatched.featureSet.header"))
                    .contentText(String.format(AppPropertiesSingleton.getInstance().get("frame.dialog.volumeUnmatched.featureSet.content"),
                            featureSetVolumeUnmatchedException.getInputLayerId(),
                            featureSetVolumeUnmatchedException.getInputLayerDimension(),
                            featureSetVolumeUnmatchedException.getInputLayerId(),
                            featureSetVolumeUnmatchedException.getFeatureSetDimension()))
                    .build()
                    .showAndWait();
        }
        else if(exception instanceof ResultSetVolumeUnmatchedException){
            ResultSetVolumeUnmatchedException resultSetVolumeUnmatchedException = ((ResultSetVolumeUnmatchedException) exception);
            DialogUtil.builder()
                    .alertType(Alert.AlertType.ERROR)
                    .title(AppPropertiesSingleton.getInstance().get("frame.dialog.volumeUnmatched.resultSet.title"))
                    .headerText(AppPropertiesSingleton.getInstance().get("frame.dialog.volumeUnmatched.resultSet.header"))
                    .contentText(String.format(AppPropertiesSingleton.getInstance().get("frame.dialog.volumeUnmatched.resultSet.content"),
                            resultSetVolumeUnmatchedException.getOutputLayerId(),
                            resultSetVolumeUnmatchedException.getOutputLayerDimension(),
                            resultSetVolumeUnmatchedException.getInputLayerId(),
                            resultSetVolumeUnmatchedException.getResultSetDimension()))
                    .build()
                    .showAndWait();
        }
        else if(exception instanceof FeatureSetResultSetUnmatchedException){
            FeatureSetResultSetUnmatchedException featureSetResultSetUnmatchedException = ((FeatureSetResultSetUnmatchedException) exception);
            DialogUtil.builder()
                    .alertType(Alert.AlertType.ERROR)
                    .title(AppPropertiesSingleton.getInstance().get("frame.dialog.recordSizeUnmatched.title"))
                    .headerText(AppPropertiesSingleton.getInstance().get("frame.dialog.recordSizeUnmatched.header"))
                    .contentText(String.format(AppPropertiesSingleton.getInstance().get("frame.dialog.recordSizeUnmatched.content"),
                            featureSetResultSetUnmatchedException.getFeatureSetRowMax(),
                            featureSetResultSetUnmatchedException.getResultSetRowMax()))
                    .build()
                    .showAndWait();
        }
        else if(exception instanceof InvalidParameterException){
            InvalidParameterException invalidParameterException = ((InvalidParameterException) exception);
            DialogUtil.builder()
                    .alertType(Alert.AlertType.ERROR)
                    .title(AppPropertiesSingleton.getInstance().get("frame.dialog.paramError.title"))
                    .headerText(AppPropertiesSingleton.getInstance().get("frame.dialog.paramError.header"))
                    .contentText(invalidParameterException.getMessage())
                    .build()
                    .showAndWait();
        }
        else if(exception instanceof MultiInputLayerException){
            DialogUtil.builder()
                    .alertType(Alert.AlertType.INFORMATION)
                    .title(AppPropertiesSingleton.getInstance().get("frame.dialog.multiInputLayer.title"))
                    .headerText(AppPropertiesSingleton.getInstance().get("frame.dialog.multiInputLayer.header"))
                    .contentText(AppPropertiesSingleton.getInstance().get("frame.dialog.multiInputLayer.content"))
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
        else if(exception instanceof CanNotFindLayerException){
            CanNotFindLayerException canNotFindLayerException = ((CanNotFindLayerException) exception);
            DialogUtil.builder()
                    .alertType(Alert.AlertType.ERROR)
                    .title(AppPropertiesSingleton.getInstance().get("frame.dialog.layerNotExist.title"))
                    .headerText(AppPropertiesSingleton.getInstance().get("frame.dialog.layerNotExist.header"))
                    .contentText(String.format(AppPropertiesSingleton.getInstance().get("frame.dialog.layerNotExist.content"), canNotFindLayerException.getLayerId()))
                    .build()
                    .showAndWait();
        }
        else if(exception instanceof UnreachableOutputLayerException){
            UnreachableOutputLayerException unreachableOutputLayerException = ((UnreachableOutputLayerException) exception);
            DialogUtil.builder()
                    .alertType(Alert.AlertType.ERROR)
                    .title(AppPropertiesSingleton.getInstance().get("frame.dialog.unreachableOutput.title"))
                    .headerText(AppPropertiesSingleton.getInstance().get("frame.dialog.unreachableOutput.header"))
                    .contentText(String.format(AppPropertiesSingleton.getInstance().get("frame.dialog.unreachableOutput.content"), unreachableOutputLayerException.getLayerId()))
                    .build()
                    .showAndWait();
        }
        else if(exception instanceof InvalidMergeConnectionExistException){
            InvalidMergeConnectionExistException invalidMergeConnectionExistException = ((InvalidMergeConnectionExistException) exception);
            DialogUtil.builder()
                    .alertType(Alert.AlertType.ERROR)
                    .title(AppPropertiesSingleton.getInstance().get("frame.dialog.mergeConnectionImpossible.title"))
                    .headerText(AppPropertiesSingleton.getInstance().get("frame.dialog.mergeConnectionImpossible.header"))
                    .contentText(String.format(AppPropertiesSingleton.getInstance().get("frame.dialog.mergeConnectionImpossible.content"),invalidMergeConnectionExistException.getLayerId()))
                    .build()
                    .showAndWait();
        }
        else if(exception instanceof InvalidSwitchConnectionExistException){
            InvalidSwitchConnectionExistException invalidSwitchConnectionExistException = ((InvalidSwitchConnectionExistException) exception);
            DialogUtil.builder()
                    .alertType(Alert.AlertType.ERROR)
                    .title(AppPropertiesSingleton.getInstance().get("frame.dialog.switchConnectionImpossible.title"))
                    .headerText(AppPropertiesSingleton.getInstance().get("frame.dialog.switchConnectionImpossible.header"))
                    .contentText(String.format(AppPropertiesSingleton.getInstance().get("frame.dialog.switchConnectionImpossible.content"), invalidSwitchConnectionExistException.getLayerId()))
                    .build()
                    .showAndWait();
        }
        else if(exception instanceof ModelIsChangedException){
            DialogUtil.builder()
                    .alertType(Alert.AlertType.ERROR)
                    .title(AppPropertiesSingleton.getInstance().get("frame.dialog.modelIsChanged.title"))
                    .headerText(AppPropertiesSingleton.getInstance().get("frame.dialog.modelIsChanged.header"))
                    .contentText(AppPropertiesSingleton.getInstance().get("frame.dialog.modelIsChanged.content"))
                    .build()
                    .showAndWait();
        }
        else if(exception instanceof ModelIsNotInitializeException){
            DialogUtil.builder()
                    .alertType(Alert.AlertType.ERROR)
                    .title(AppPropertiesSingleton.getInstance().get("frame.dialog.modelIsNotInitialize.title"))
                    .headerText(AppPropertiesSingleton.getInstance().get("frame.dialog.modelIsNotInitialize.header"))
                    .contentText(AppPropertiesSingleton.getInstance().get("frame.dialog.modelIsNotInitialize.content"))
                    .build()
                    .showAndWait();
        }
        else if(exception instanceof DL4JInvalidInputException){
            DialogUtil.builder()
                    .alertType(Alert.AlertType.ERROR)
                    .title(AppPropertiesSingleton.getInstance().get("frame.dialog.dl4jInvalidInputException.title"))
                    .headerText(AppPropertiesSingleton.getInstance().get("frame.dialog.dl4jInvalidInputException.header"))
                    .contentText(AppPropertiesSingleton.getInstance().get(String.format(AppPropertiesSingleton.getInstance().get("frame.dialog.dl4jInvalidInputException.content"), exception.getMessage())))
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
        }
    }
}
