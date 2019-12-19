package org.kok202.dluid.model;

import org.kok202.dluid.AppFacade;

public class ModelInitializeHandler {

    public static void initializeModel(){
        AppFacade.clearTrainingLog();
        AppFacade.appendTextOnTrainingLog("Try to create model.");
        // TODO
//        GraphManager<Layer> layerGraphManager = LayerGraphManagerUtil.convertToLayerGraph(CanvasFacade.findTrainInputGraphNode());
//        AIFacade.initializeModel(layerGraphManager);
        AppFacade.appendTextOnTrainingLog("Try to create model. [done]");
    }
}
