package org.kok202.dluid.model;

import org.kok202.dluid.AppFacade;
import org.kok202.dluid.application.singleton.AppWidgetSingleton;

public class ModelInitializeHandler {

    public static void initializeModel(){
        AppFacade.clearTrainingLog();
        AppFacade.appendTextOnTrainingLog("Try to create model.");
        ModelGraphValidator.validate();
        AppFacade.appendTextOnTrainingLog("Try to create model. [Successful]");
        AppWidgetSingleton.getInstance().getTabsController().getTabModelTrainController().getModelTrainFileLoaderController().refreshFileLoader();
    }

}
