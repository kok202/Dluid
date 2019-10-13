package org.kok202.deepblock.domain.structure;

import lombok.Data;
import org.kok202.deepblock.domain.exception.CanNotFindGraphNodeException;
import org.kok202.deepblock.domain.exception.CycleCreatedException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Data
public class GraphNode<T> {
    private T data;
    private GraphNode<T> parent;
    private List<GraphEdge<T>> edges;
    private List<GraphNode<T>> adjacentNodes;

    public GraphNode() {
        this.adjacentNodes = new ArrayList<>();
    }

    public GraphNode(T data) {
        this.adjacentNodes = new ArrayList<>();
        this.data = data;
    }

    public GraphNode<T> getChild(int index){
        return adjacentNodes.get(index);
    }

    public GraphNode<T> getFirstChild(){
        if(adjacentNodes.isEmpty())
            return null;
        return adjacentNodes.get(0);
    }

    public boolean isSingleChild(){
        return adjacentNodes.size() == 1;
    }

    public boolean isNoChild(){
        return adjacentNodes.isEmpty();
    }

    public void attach(GraphNode<T> child){
        if(child.getParent() != null)
            throw new CycleCreatedException();
        child.setParent(this);
        adjacentNodes.add(child);
    }

    public GraphNode<T> attach(T nodeData){
        GraphNode<T> graphNode = new GraphNode<>();
        graphNode.setData(nodeData);
        graphNode.setParent(this);
        adjacentNodes.add(graphNode);
        return graphNode;
    }

    public GraphNode<T> dettach(T nodeData){
        for(GraphNode<T> child : adjacentNodes){
            if(nodeData == child.getData()) {
                child.getAdjacentNodes().remove(this);
                this.getAdjacentNodes().remove(child);
                return child;
            }
        }
        throw new CanNotFindGraphNodeException(nodeData.toString());
    }

    public GraphNode<T> dettach(int index){
        GraphNode<T> branch = adjacentNodes.remove(index);
        branch.setParent(null);
        return branch;
    }

    public GraphNode<T> find(T node) {
        if(getData().equals(node))
            return this;

        for(GraphNode<T> child : adjacentNodes){
            GraphNode<T> findedGraphNode = child.find(node);
            if(findedGraphNode != null)
                return findedGraphNode;
        }
        return null;
    }

    public List<GraphNode<T>> remove(Consumer<T> consumer){
        consumer.accept(data);
        if(parent != null)
            parent.getAdjacentNodes().remove(this);
        parent = null;
        data = null;
        return adjacentNodes;
    }

    public void removeWithDescendants(Consumer<T> consumer){
        remove(consumer);
        while(adjacentNodes != null && !adjacentNodes.isEmpty()){
            adjacentNodes.get(0).removeWithDescendants(consumer);
        }
    }

    public List<GraphNode<T>> getAllDescendants(){
        List result = new ArrayList<>();
        getAllDescendants(result);
        return result;
    }

    private void getAllDescendants(List<GraphNode<T>> graphNodes){
        for(GraphNode<T> child : adjacentNodes){
            graphNodes.add(child);
            child.getAllDescendants(graphNodes);
        }
    }
}