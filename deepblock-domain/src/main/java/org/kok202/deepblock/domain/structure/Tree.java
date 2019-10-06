package org.kok202.deepblock.domain.structure;

public class Tree<T> {
    private TreeNode<T> root;

    public Tree(T rootData) {
        setRoot(rootData);
    }

    public Tree(TreeNode<T> rootData) {
        setRoot(rootData);
    }

    public void setRoot(T rootData){
        root = new TreeNode<>();
        root.setData(rootData);
    }

    public void setRoot(TreeNode<T> rootData){
        this.root = rootData;
    }

    public TreeNode<T> getRoot() {
        return root;
    }

    public TreeNode<T> find(T node){
        return root.find(node);
    }
}