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
            DialogUtil.builder()
                    .alertType(Alert.AlertType.ERROR)
                    .title(AppPropertiesSingleton.getInstance().get("frame.dialog.convolutionNegativeSize.title"))
                    .headerText(AppPropertiesSingleton.getInstance().get("frame.dialog.convolutionNegativeSize.header") + ((ConvolutionOutputIsNegativeException) exception).getOutputSize())
                    .contentText(AppPropertiesSingleton.getInstance().get("frame.dialog.convolutionNegativeSize.content"))
                    .build()
                    .showAndWait();
        }
        else if(exception instanceof MultiInputOutputLayerException){
            DialogUtil.builder()
                    .alertType(Alert.AlertType.INFORMATION)
                    .title(AppPropertiesSingleton.getInstance().get("frame.dialog.multiInOutLayer.title"))
                    .headerText(AppPropertiesSingleton.getInstance().get("frame.dialog.multiInOutLayer.header"))
                    .contentText(AppPropertiesSingleton.getInstance().get("frame.dialog.multiInOutLayer.content"))
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
