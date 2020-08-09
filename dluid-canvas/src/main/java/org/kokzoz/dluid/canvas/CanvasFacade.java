package org.kokzoz.dluid.canvas;

import lombok.Data;
import org.kokzoz.dluid.canvas.block.BlockNode;
import org.kokzoz.dluid.canvas.singleton.CanvasSingleton;
import org.kokzoz.dluid.domain.action.Action;
import org.kokzoz.dluid.domain.action.ActionType;
import org.kokzoz.dluid.domain.action.Reducer;
import org.kokzoz.dluid.domain.entity.Layer;
import org.kokzoz.dluid.domain.entity.enumerator.LayerType;
import org.kokzoz.dluid.domain.structure.GraphNode;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Data
public class CanvasFacade {
    /*************************************************************************************************
     /* Canvas
     *************************************************************************************************/
    public static void resizingCanvas(double canvasWidth, double canvasHeight){
        CanvasSingleton.getInstance().getMainCanvas().getMainScene().setSceneWidth(canvasWidth);
        CanvasSingleton.getInstance().getMainCanvas().getMainScene().refreshSceneSize();
        CanvasSingleton.getInstance().getMainCanvas().getMainScene().setSceneHeight(canvasHeight);
        CanvasSingleton.getInstance().getMainCanvas().getMainScene().refreshSceneSize();
    }

    /*************************************************************************************************
     /* State machine
     *************************************************************************************************/
    public static void dispatchAction(ActionType actionType){
        CanvasSingleton.getInstance().getStateMachine().dispatchAction(actionType);
    }

    public static void dispatchAction(ActionType actionType, Object payload){
        CanvasSingleton.getInstance().getStateMachine().dispatchAction(actionType, payload);
    }

    public static void dispatchAction(Action action){
        CanvasSingleton.getInstance().getStateMachine().dispatchAction(action);
    }

    public static void addReducer(Reducer reducer){
        CanvasSingleton.getInstance().getStateMachine().addReducer(reducer);
    }

    /*************************************************************************************************
    /* BlockNodeManager
    *************************************************************************************************/
    public static long getGraphManagerHashCode(){
        return CanvasSingleton.getInstance().getBlockNodeManager().getHashCode();
    }

    public static List<GraphNode<BlockNode>> findAllReachableNode(String layerId){
        return CanvasSingleton.getInstance().getBlockNodeManager().findAllReachableNode(layerId);
    }

    public static List<Layer> findIncomingLayers(String layerId){
        return CanvasSingleton.getInstance()
                .getBlockNodeManager()
                .findGraphNodeByLayerId(layerId)
                .getIncomingNodes()
                .stream()
                .map(incomingGraphNode -> {
                    if(incomingGraphNode.getData().getBlockLayer().getType() == LayerType.PIPE_LAYER)
                        return incomingGraphNode.getIncomingNode().get().getData().getBlockLayer();
                    return incomingGraphNode.getData().getBlockLayer();
                })
                .collect(Collectors.toList());
    }

    public static String findStartLayerIdConnectedWithLayerId(String layerId){
        GraphNode<BlockNode> sourceLayerGraphNode = CanvasSingleton.getInstance().getBlockNodeManager().findStartBlockConnectedWithLayerId(layerId);
        return (sourceLayerGraphNode != null)? sourceLayerGraphNode.getData().getBlockLayer().getId() : "";
    }

    public static GraphNode<BlockNode> findGraphNodeByLayerId(String layerId){
        return CanvasSingleton.getInstance().getBlockNodeManager().findGraphNodeByLayerId(layerId);
    }

    public static List<GraphNode<BlockNode>> findAllInputLayer(){
        return findAllGraphNode(blockNodeGraphNode -> blockNodeGraphNode.getData().getBlockLayer().getType().isInputLayerType());
    }

    public static Optional<GraphNode<BlockNode>> findOutputLayer(){
        List<GraphNode<BlockNode>> outputNodes = findAllGraphNode(blockNodeGraphNode -> blockNodeGraphNode.getData().getBlockLayer().getType().isOutputLayerType());
        return outputNodes.isEmpty()? Optional.empty() : Optional.of(outputNodes.get(0));
    }

    public static List<GraphNode<BlockNode>> findAllGraphNode(Predicate<? super GraphNode<BlockNode>> predicate){
        return CanvasSingleton.getInstance().getBlockNodeManager().findAllGraphNode(predicate);
    }
}
