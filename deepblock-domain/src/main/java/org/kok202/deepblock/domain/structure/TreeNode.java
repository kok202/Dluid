package org.kok202.deepblock.domain.structure;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class TreeNode<T> {
    @Getter
    private T data;
    private List<TreeEdge<T>> edges;

    public TreeNode(T data) {
        this.data = data;
        this.edges = new ArrayList<>();
    }

    public boolean isNoWay(){
        return getOutgoingEdges().isEmpty();
    }

    public boolean isSingleWay(){
        return getOutgoingEdges().size() == 1;
    }

    public List<TreeEdge<T>> getOutgoingEdges(){
        return edges.stream()
                .filter(treeEdge -> treeEdge.getSourceTreeNode() == this)
                .collect(toList());
    }

    public List<TreeEdge<T>> getIncomingEdges(){
        return edges.stream()
                .filter(treeEdge -> treeEdge.getDestinationTreeNode() == this)
                .collect(toList());
    }

    public List<TreeNode<T>> getAdjacentNodes(){
        return edges.stream()
                .map(TreeEdge::getDestinationTreeNode)
                .collect(toList());
    }

    public List<TreeNode<T>> getOutgoingNodes(){
        return edges.stream()
                .filter(treeEdge -> treeEdge.getSourceTreeNode() == this)
                .map(TreeEdge::getDestinationTreeNode)
                .collect(toList());
    }

    public List<TreeNode<T>> getIncomingNodes(){
        return edges.stream()
                .filter(treeEdge -> treeEdge.getSourceTreeNode() == this)
                .map(TreeEdge::getSourceTreeNode)
                .collect(toList());
    }

    void createEdgeTo(TreeNode<T> to){
        // TODO : For safe it needs for cycle validation.
        TreeEdge<T> newEdge = new TreeEdge<>(this, to);
        this.edges.add(newEdge);
        to.edges.add(newEdge);
    }

    void createEdgeFrom(TreeNode<T> from){
        // TODO : For safe it needs for cycle validation.
        TreeEdge<T> newEdge = new TreeEdge<>(from, this);
        this.edges.add(newEdge);
        from.edges.add(newEdge);
    }

    void removeEdge(T data){
        edges.stream()
            .filter(treeEdge ->
                    treeEdge.getSourceTreeNode().getData() == data ||
                    treeEdge.getDestinationTreeNode().getData() == data)
            .forEach(this::removeEdge);
    }

    void removeEdge(TreeEdge<T> treeEdge){
        treeEdge.getSourceTreeNode().edges.remove(treeEdge);
        treeEdge.getDestinationTreeNode().edges.remove(treeEdge);
        treeEdge.setSourceTreeNode(null);
        treeEdge.setDestinationTreeNode(null);
    }

    void remove(){
        edges.stream().forEach(this::removeEdge);
        edges = null;
        data = null;
    }

    // TODO : IMPORTANT : if it possible, it must not have cycle
    void removeAllWithReachableNode(){
        getOutgoingNodes().forEach(TreeNode::remove);
        remove();
    }

    // TODO : IMPORTANT : if it possible, it must not have cycle
    public List<TreeNode<T>> getAllReachableNodes(){
        List<TreeNode<T>> result = new ArrayList<>();
        getAllReachableNodes(result);
        return result;
    }

    // TODO : IMPORTANT : if it possible, it must not have cycle
    public List<TreeNode<T>> getAllLinkedNodes(){
        List<TreeNode<T>> result = new ArrayList<>();
        getAllLinkedNodes(result);
        return result;
    }

    private void getAllReachableNodes(List<TreeNode<T>> result){
        for(TreeNode<T> outgoingNodes : getOutgoingNodes()){
            result.add(outgoingNodes);
            outgoingNodes.getAllReachableNodes(result);
        }
    }

    private void getAllLinkedNodes(List<TreeNode<T>> result){
        for(TreeNode<T> adjacentNode : getAdjacentNodes()){
            result.add(adjacentNode);
            adjacentNode.getAllLinkedNodes(result);
        }
    }
}