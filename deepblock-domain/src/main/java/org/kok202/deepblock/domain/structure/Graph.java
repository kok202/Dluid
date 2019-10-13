package org.kok202.deepblock.domain.structure;

import org.kok202.deepblock.domain.exception.CanNotFindGraphNodeException;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class Graph<T> {
    private GraphNode<T> root;
    private Map<T, GraphNode<T>> graphNodeMap;

    public Graph(T rootData) {
        setRoot(rootData);
    }

    public Graph(GraphNode<T> rootData) {
        setRoot(rootData);
    }

    public void setRoot(T rootData){
        root = new GraphNode<>();
        root.setData(rootData);
        graphNodeMap = new HashMap<>();
    }

    public void setRoot(GraphNode<T> rootData){
        this.root = rootData;
    }

    public GraphNode<T> getRoot() {
        return root;
    }

    public GraphNode<T> attachTo(GraphNode<T> targetGraphNode, T data){
        GraphNode<T> newGraphNode = new GraphNode<>(data);
        graphNodeMap.put(data, newGraphNode);
        targetGraphNode.attach(newGraphNode);
        return newGraphNode;
    }

    public GraphNode dettachFrom(GraphNode<T> targetGraphNode, T data){
        return targetGraphNode.dettach(data);
    }

    public GraphNode<T> find(T node){
        GraphNode<T> graphNode = graphNodeMap.get(node);
        if(graphNode != null)
            return graphNode;
        throw new CanNotFindGraphNodeException(node.toString());
    }

    public GraphNode<T> find(Predicate predicate){
        for(GraphNode<T> graphNode : graphNodeMap.values()){
            if(predicate.test(graphNode) == true)
                return graphNode;
        }
        throw new CanNotFindGraphNodeException("");
    }
}