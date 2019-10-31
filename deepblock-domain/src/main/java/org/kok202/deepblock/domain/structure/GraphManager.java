package org.kok202.deepblock.domain.structure;

import lombok.Getter;
import org.kok202.deepblock.domain.exception.CanNotFindGraphNodeException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

// Directed Acyclic Graph
public class GraphManager<T> {
    @Getter
    private List<GraphNode<T>> graphNodes;
    @Getter
    private List<T> dataNodes;

    public GraphManager() {
        graphNodes = new ArrayList<>();
        dataNodes = new ArrayList<>();
    }

    public void registerSoloNode(T data){
        graphNodes.add(new GraphNode<>(data));
        dataNodes.add(data);
    }

    public GraphNode<T> findGraphNode(Predicate predicate) {
        for(GraphNode<T> graphNode : graphNodes){
            if(predicate.test(graphNode.getData()))
                return graphNode;
        }
        throw new CanNotFindGraphNodeException("predicator");
    }

    public GraphNode<T> findGraphNodeByData(T data){
        for(GraphNode<T> graphNode : graphNodes){
            if(graphNode.getData() == data)
                return graphNode;
        }
        throw new CanNotFindGraphNodeException(data.toString());
    }

    public void linkFromNewData(T sourceNewData, T destinationData){
        GraphNode<T> sourceGraphNode = new GraphNode<>(sourceNewData);
        GraphNode<T> destinationGraphNode = findGraphNodeByData(destinationData);
        destinationGraphNode.createEdgeFrom(sourceGraphNode);
        graphNodes.add(sourceGraphNode);
        dataNodes.add(sourceNewData);
    }

    public void linkToNewData(T sourceData, T destinationNewData){
        GraphNode<T> sourceGraphNode = findGraphNodeByData(sourceData);
        GraphNode<T> destinationGraphNode = new GraphNode<>(destinationNewData);
        if(sourceGraphNode == null)
            throw new CanNotFindGraphNodeException(sourceData.toString());
        sourceGraphNode.createEdgeTo(destinationGraphNode);
        graphNodes.add(destinationGraphNode);
        dataNodes.add(destinationNewData);
    }

    public void link(T sourceData, T destinationData){
        GraphNode<T> sourceGraphNode = findGraphNodeByData(sourceData);
        GraphNode<T> destinationGraphNode = findGraphNodeByData(destinationData);
        if(sourceGraphNode == null)
            throw new CanNotFindGraphNodeException(sourceData.toString());
        sourceGraphNode.createEdgeTo(destinationGraphNode);
    }

    public void removeGraphNode(Predicate predicate, Consumer removeBefore) {
        List<GraphNode<T>> graphNodeClones = new ArrayList<>(graphNodes);
        for (GraphNode<T> graphNode : graphNodeClones) {
            if (predicate.test(graphNode.getData())) {
                removeBefore.accept(graphNode);
                dataNodes.remove(graphNode.getData());
                graphNode.remove();
                graphNodes.remove(graphNode);
            }
        }
    }

    public List<T> getAllLinkedNodesData(T selectedData){
        GraphNode<T> selectedGraphNode = findGraphNodeByData(selectedData);
        return selectedGraphNode.getAllLinkedNodes()
                .stream()
                .map(GraphNode::getData)
                .collect(Collectors.toList());
    }

    public int getHashCode(){
        if(graphNodes == null || dataNodes == null)
            return 0;
        StringBuilder hashString = new StringBuilder();
        for(GraphNode<T> graphNode : graphNodes)
            hashString.append(graphNode.hashCode());
        for(T data : dataNodes)
            hashString.append(data.hashCode());
        return hashString.toString().hashCode();
    }
}
