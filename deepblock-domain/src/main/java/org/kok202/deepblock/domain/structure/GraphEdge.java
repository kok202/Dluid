package org.kok202.deepblock.domain.structure;

import lombok.Data;

@Data
// Directional edge
public class GraphEdge<T> {
    private GraphNode<T> sourceGraphNode;
    private GraphNode<T> destinationGraphNode;
}