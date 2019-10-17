package org.kok202.deepblock.domain.structure;

import lombok.Getter;
import org.kok202.deepblock.domain.exception.CanNotFindTreeNodeException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TreeManager<T> {
    @Getter
    private Map<T, TreeNode<T>> treeNodeMap;

    public TreeManager() {
        treeNodeMap = new HashMap<>();
    }

    public void registerSoloNode(T data){
        treeNodeMap.put(data, new TreeNode<>(data));
    }

    public void linkFromNewData(T sourceNewData, T destinationData){
        TreeNode<T> sourceTreeNode = new TreeNode<>(sourceNewData);
        TreeNode<T> destinationTreeNode = treeNodeMap.get(destinationData);
        if(destinationTreeNode == null)
            throw new CanNotFindTreeNodeException(destinationData.toString());
        destinationTreeNode.createEdgeFrom(sourceTreeNode);
        treeNodeMap.put(sourceNewData, new TreeNode<>(sourceNewData));
    }

    public void linkToNewData(T sourceData, T destinationNewData){
        TreeNode<T> sourceTreeNode = treeNodeMap.get(sourceData);
        TreeNode<T> destinationTreeNode = new TreeNode<>(destinationNewData);
        if(sourceTreeNode == null)
            throw new CanNotFindTreeNodeException(sourceTreeNode.toString());
        sourceTreeNode.createEdgeTo(destinationTreeNode);
    }

    public void removeReachableTreeNode(Predicate predicate) {
        for(T data : treeNodeMap.keySet()) {
            if(predicate.test(data))
                treeNodeMap.get(data).removeAllWithReachableNode();
        }
    }

    public TreeNode<T> findTreeNode(Predicate predicate) {
        for(T data : treeNodeMap.keySet()) {
            if(predicate.test(data))
                return treeNodeMap.get(data);
        }
        throw new CanNotFindTreeNodeException("predicator");
    }

    public List<T> getAllLinkedNodesData(T selectedData){
        TreeNode<T> selectedTreeNode = treeNodeMap.get(selectedData);
        if(selectedTreeNode == null)
            throw new CanNotFindTreeNodeException(selectedTreeNode.toString());
        return selectedTreeNode.getAllLinkedNodes()
                .stream()
                .map(TreeNode::getData)
                .collect(Collectors.toList());
    }

    public int getHashCode(){
        if(treeNodeMap == null)
            return 0;
        StringBuilder hashString = new StringBuilder();
        for(T data : treeNodeMap.keySet())
            hashString.append(data.hashCode());
        for(TreeNode<T> treeNode : treeNodeMap.values())
            hashString.append(treeNode.hashCode());
        return hashString.toString().hashCode();
    }
}
