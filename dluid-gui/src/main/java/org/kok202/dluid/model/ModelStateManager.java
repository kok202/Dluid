package org.kok202.dluid.model;

import org.kok202.dluid.AppFacade;
import org.kok202.dluid.CanvasFacade;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.canvas.block.BlockNode;
import org.kok202.dluid.domain.exception.ModelIsChangedException;
import org.kok202.dluid.domain.exception.ModelIsNotInitializeException;
import org.kok202.dluid.domain.structure.GraphManager;
import org.kok202.dluid.domain.structure.GraphNode;

public class ModelStateManager {

    public static final long MODEL_IS_NOT_INITIALIZED = -1;
    private static long previousGraphHashCode = MODEL_IS_NOT_INITIALIZED;

    // 0
    public static void initializeModel() {
        ModelGraphValidator.validateModelIsCorrect();
        previousGraphHashCode = CanvasFacade.getGraphManagerHashCode();

        GraphNode<BlockNode> outputGraphNode = CanvasFacade.findOutputLayer().get();
        GraphManager<Layer> layerGraphManager = ModelGraphConverter.convertToLayerGraph(outputGraphNode);
//        TODO : AIFacade.initializeModel(layerGraphManager);
        AppFacade.refreshTrainingFileLoader();
        AppFacade.refreshTestInputLayerInformation();
        AppFacade.setTrainingAndTestSettingDisable(false);
    }

    // 1
    public static void validateTrainPossible() {
        validateModelIsChanged();
        ModelTrainValidator.validate();
    }

    // 2
    public static void validateTestPossible() {
        validateModelIsChanged();
        ModelTestValidator.validate();
    }

    private static void validateModelIsChanged() throws ModelIsNotInitializeException, ModelIsChangedException {
        if(previousGraphHashCode == MODEL_IS_NOT_INITIALIZED)
            throw new ModelIsNotInitializeException();

        long currentGraphHashCode = CanvasFacade.getGraphManagerHashCode();
        if(previousGraphHashCode != currentGraphHashCode)
            throw new ModelIsChangedException();
        previousGraphHashCode = currentGraphHashCode;
    }
}
