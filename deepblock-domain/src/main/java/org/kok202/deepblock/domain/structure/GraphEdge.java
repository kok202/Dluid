package org.kok202.deepblock.domain.structure;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
// Directional edge
public class GraphEdge<T> {
    private GraphNode<T> sourceGraphNode;
    private GraphNode<T> destinationGraphNode;
}