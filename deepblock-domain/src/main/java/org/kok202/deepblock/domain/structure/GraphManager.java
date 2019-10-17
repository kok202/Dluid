package org.kok202.deepblock.domain.structure;

import lombok.Getter;
import org.kok202.deepblock.domain.exception.CanNotFindGraphNodeException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

// Directed Acyclic Graph
public class GraphManager<T> {
    @Getter
    private Map<T, GraphNode<T>> graphNodeMap;

    public GraphManager() {
        graphNodeMap = new HashMap<>();
    }

    public void registerSoloNode(T data){
        graphNodeMap.put(data, new GraphNode<>(data));
    }

    public void linkFromNewData(T sourceNewData, T destinationData){
        GraphNode<T> sourceGraphNode = new GraphNode<>(sourceNewData);
        GraphNode<T> destinationGraphNode = graphNodeMap.get(destinationData);
        if(destinationGraphNode == null)
            throw new CanNotFindGraphNodeException(destinationData.toString());
        destinationGraphNode.createEdgeFrom(sourceGraphNode);
        graphNodeMap.put(sourceNewData, new GraphNode<>(sourceNewData));
    }

    public void linkToNewData(T sourceData, T destinationNewData){
        GraphNode<T> sourceGraphNode = graphNodeMap.get(sourceData);
        GraphNode<T> destinationGraphNode = new GraphNode<>(destinationNewData);
        if(sourceGraphNode == null)
            throw new CanNotFindGraphNodeException(sourceGraphNode.toString());
        sourceGraphNode.createEdgeTo(destinationGraphNode);
    }

    public void removeReachableGraphNode(Predicate predicate) {
        for(T data : graphNodeMap.keySet()) {
            if(predicate.test(data))
                graphNodeMap.get(data).removeAllWithReachableNode();
        }
    }

    public GraphNode<T> findGraphNode(Predicate predicate) {
        for(T data : graphNodeMap.keySet()) {
            if(predicate.test(data))
                return graphNodeMap.get(data);
        }
        throw new CanNotFindGraphNodeException("predicator");
    }

    public List<T> getAllLinkedNodesData(T selectedData){
        GraphNode<T> selectedGraphNode = graphNodeMap.get(selectedData);
        if(selectedGraphNode == null)
            throw new CanNotFindGraphNodeException(selectedGraphNode.toString());
        return selectedGraphNode.getAllLinkedNodes()
                .stream()
                .map(GraphNode::getData)
                .collect(Collectors.toList());
    }

    public int getHashCode(){
        if(graphNodeMap == null)
            return 0;
        StringBuilder hashString = new StringBuilder();
        for(T data : graphNodeMap.keySet())
            hashString.append(data.hashCode());
        for(GraphNode<T> graphNode : graphNodeMap.values())
            hashString.append(graphNode.hashCode());
        return hashString.toString().hashCode();
    }
}
