package org.kokzoz.dluid.model;

import org.kokzoz.dluid.AppFacade;
import org.kokzoz.dluid.ai.AIFacade;
import org.kokzoz.dluid.canvas.CanvasFacade;
import org.kokzoz.dluid.canvas.block.BlockNode;
import org.kokzoz.dluid.domain.action.ActionType;
import org.kokzoz.dluid.domain.entity.Layer;
import org.kokzoz.dluid.domain.exception.ModelIsChangedException;
import org.kokzoz.dluid.domain.exception.ModelIsNotInitializeException;
import org.kokzoz.dluid.domain.structure.GraphManager;
import org.kokzoz.dluid.domain.structure.GraphNode;

public class ModelStateManager {

    public static final long MODEL_IS_NOT_INITIALIZED = -1;
    private static long previousGraphHashCode = MODEL_IS_NOT_INITIALIZED;

    // 0
    public static void initializeModel() {
        ModelGraphValidator.validateModelIsCorrect();
        previousGraphHashCode = CanvasFacade.getGraphManagerHashCode();

        GraphNode<BlockNode> outputGraphNode = CanvasFacade.findOutputLayer().get();
        GraphManager<Layer> layerGraphManager = ModelGraphConverter.convertToLayerGraph(outputGraphNode);
        AIFacade.initializeModel(layerGraphManager);
        AIFacade.setModelLearnedEpochNumber(0);
        AppFacade.dispatchAction(ActionType.REFRESH_MODEL_INFORMATION);
        AppFacade.dispatchAction(ActionType.REFRESH_TRAINING_FILE_LOADER);
        AppFacade.dispatchAction(ActionType.REFRESH_TRAINING_LOG);
        AppFacade.dispatchAction(ActionType.REFRESH_TEST_LOG);
        AppFacade.dispatchAction(ActionType.REFRESH_TEST_INPUT_LATYER_INFORMATION);
        AppFacade.dispatchAction(ActionType.MODEL_ENABLE);
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
