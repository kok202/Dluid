package org.kok202.deepblock.domain.structure;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class GraphNode<T> {
    @Getter
    private T data;
    private List<GraphEdge<T>> edges;

    public GraphNode(T data) {
        this.data = data;
        this.edges = new ArrayList<>();
    }

    public boolean isNoWay(){
        return getOutgoingEdges().isEmpty();
    }

    public boolean isSingleWay(){
        return getOutgoingEdges().size() == 1;
    }

    public List<GraphEdge<T>> getOutgoingEdges(){
        return edges.stream()
                .filter(graphEdge -> graphEdge.getSourceGraphNode() == this)
                .collect(toList());
    }

    public List<GraphEdge<T>> getIncomingEdges(){
        return edges.stream()
                .filter(graphEdge -> graphEdge.getDestinationGraphNode() == this)
                .collect(toList());
    }

    public List<GraphNode<T>> getAdjacentNodes(){
        return Stream.of(getOutgoingNodes(), getIncomingNodes())
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<GraphNode<T>> getOutgoingNodes(){
        return edges.stream()
                .filter(graphEdge -> graphEdge.getSourceGraphNode() == this)
                .map(GraphEdge::getDestinationGraphNode)
                .collect(toList());
    }

    public List<GraphNode<T>> getIncomingNodes(){
        return edges.stream()
                .filter(graphEdge -> graphEdge.getDestinationGraphNode() == this)
                .map(GraphEdge::getSourceGraphNode)
                .collect(toList());
    }

    void createEdgeTo(GraphNode<T> to){
        // TODO : For safe it needs for cycle validation.
        GraphEdge<T> newEdge = new GraphEdge<>(this, to);
        this.edges.add(newEdge);
        to.edges.add(newEdge);
    }

    void createEdgeFrom(GraphNode<T> from){
        // TODO : For safe it needs for cycle validation.
        GraphEdge<T> newEdge = new GraphEdge<>(from, this);
        this.edges.add(newEdge);
        from.edges.add(newEdge);
    }

    void removeEdge(T data){
        edges.stream()
            .filter(graphEdge ->
                    graphEdge.getSourceGraphNode().getData() == data ||
                    graphEdge.getDestinationGraphNode().getData() == data)
            .forEach(this::removeEdge);
    }

    void removeEdge(GraphEdge<T> graphEdge){
        graphEdge.getSourceGraphNode().edges.remove(graphEdge);
        graphEdge.getDestinationGraphNode().edges.remove(graphEdge);
        graphEdge.setSourceGraphNode(null);
        graphEdge.setDestinationGraphNode(null);
    }

    void remove(){
        edges.stream().forEach(this::removeEdge);
        edges = null;
        data = null;
    }

    // TODO : IMPORTANT : if it possible, it must not have cycle
    public List<GraphNode<T>> getAllLinkedNodes(){
        List<GraphNode<T>> result = new ArrayList<>();
        result.add(this);
        getAllLinkedNodes(result);
        return result;
    }

    private void getAllLinkedNodes(List<GraphNode<T>> result){
        List<GraphNode<T>> getAdjacentNodes = getAdjacentNodes();
        for(GraphNode<T> adjacentNode : getAdjacentNodes){
            if(result.contains(adjacentNode))
                continue;
            result.add(adjacentNode);
            adjacentNode.getAllLinkedNodes(result);
        }
    }
}