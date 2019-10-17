package org.kok202.deepblock.domain.structure;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
// Directional edge
public class TreeEdge<T> {
    private TreeNode<T> sourceTreeNode;
    private TreeNode<T> destinationTreeNode;
}