package org.kok202.deepblock.domain.structure;

import lombok.Data;
import org.kok202.deepblock.domain.exception.CycleCreatedException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Data
public class TreeNode<T> {
    private T data;
    private TreeNode<T> parent;
    private List<TreeNode<T>> children;

    public TreeNode() {
        this.children = new ArrayList<>();
    }

    public TreeNode(T data) {
        this.children = new ArrayList<>();
        this.data = data;
    }

    public TreeNode<T> getChild(int index){
        return children.get(index);
    }

    public TreeNode<T> getFirstChild(){
        if(children.isEmpty())
            return null;
        return children.get(0);
    }

    public boolean isSingleChild(){
        return children.size() == 1;
    }

    public void attach(TreeNode<T> child){
        if(child.getParent() != null)
            throw new CycleCreatedException();
        child.setParent(this);
        children.add(child);
    }

    public void attach(T nodeData){
        TreeNode<T> treeNode = new TreeNode<>();
        treeNode.setData(nodeData);
        treeNode.setParent(this);
        children.add(treeNode);
    }

    public TreeNode<T> dettach(int index){
        TreeNode<T> branch = children.remove(index);
        branch.setParent(null);
        return branch;
    }

    public TreeNode<T> find(T node) {
        if(getData().equals(node))
            return this;

        for(TreeNode<T> child : children){
            TreeNode<T> findedTreeNode = child.find(node);
            if(findedTreeNode != null)
                return findedTreeNode;
        }
        return null;
    }

    public List<TreeNode<T>> remove(Consumer<T> consumer){
        consumer.accept(data);
        if(parent != null)
            parent.getChildren().remove(this);
        parent = null;
        data = null;
        return children;
    }

    public void removeWithDescendants(Consumer<T> consumer){
        remove(consumer);
        while(children != null && !children.isEmpty()){
            children.get(0).removeWithDescendants(consumer);
        }
    }

    public List<TreeNode<T>> getAllDescendants(){
        List result = new ArrayList<>();
        getAllDescendants(result);
        return result;
    }

    private void getAllDescendants(List<TreeNode<T>> ts){
        for(TreeNode<T> child : children){
            ts.add(child);
            child.getAllDescendants(ts);
        }
    }

    public void printHierachy(){
        printHierachy(0);
    }

    public void printHierachy(int depth){
        String depthString = "";
        for(int i = 0; i< depth; i++)
            depthString += "-";

        System.out.println(depthString + data);
        children.forEach(child -> {
            child.printHierachy(depth + 1);
        });
    }
}