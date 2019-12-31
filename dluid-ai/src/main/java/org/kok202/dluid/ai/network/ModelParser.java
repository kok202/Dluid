package org.kok202.dluid.ai.network;

import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.domain.structure.GraphManager;

import java.util.List;
import java.util.stream.Collectors;

public class ModelParser {
    public static List<Model> parse(GraphManager<Layer> layerGraphManager){
        // Split layer graph by [input | switch] layer. But retain linkage
        GraphManagerSplitter graphManagerSplitter = new GraphManagerSplitter();
        graphManagerSplitter.split(layerGraphManager);

        // Convert computation graph by splitted layer graph.
        MultiLayerNetworkChunkSet multiLayerNetworkChunkSet = MultiLayerNetworkChunkSet.builder()
                .managersMap(graphManagerSplitter.getSplittedLayerGraphManager()
                                .entrySet()
                                .stream()
                                .collect(Collectors.toMap(
                                        entry -> entry.getKey(),
                                        entry -> GraphManagerConverter.convert(entry.getValue()))))
                .outputLayerId(graphManagerSplitter.getOutputLayerId())
                .linkageFromTo(graphManagerSplitter.getLinkageFromTo())
                .build();

        // Link converted computation graph.
        MultiLayerNetworkChunkLinker multiLayerNetworkChunkLinker = new MultiLayerNetworkChunkLinker();
        List<LinkedMultiLayerNetwork> linkedMultiLayerNetworks = multiLayerNetworkChunkLinker.link(multiLayerNetworkChunkSet);

        // Convert linked computation graph as model.
        LinkedMultiLayerNetworkConverter linkedMultiLayerNetworkConverter = new LinkedMultiLayerNetworkConverter();
        return linkedMultiLayerNetworkConverter.convert(linkedMultiLayerNetworks);
    }
}
