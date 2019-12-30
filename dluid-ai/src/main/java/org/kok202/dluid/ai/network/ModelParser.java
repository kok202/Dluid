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
        ComputationGraphChunkSet computationGraphChunkSet = ComputationGraphChunkSet.builder()
                .managersMap(graphManagerSplitter.getSplittedLayerGraphManager()
                                .entrySet()
                                .stream()
                                .collect(Collectors.toMap(
                                        entry -> entry.getKey(),
                                        entry -> GraphManagerConverter.convert(entry.getValue()))))
                .outputLayerId(graphManagerSplitter.getOutputLayerId())
                .testInputLayerId(graphManagerSplitter.getTestInputLayerId())
                .linkageFromTo(graphManagerSplitter.getLinkageFromTo())
                .build();

        // Link converted computation graph.
        ComputationGraphChunkLinker computationGraphChunkLinker = new ComputationGraphChunkLinker();
        List<LinkedComputationGraph> linkedComputationGraphs = computationGraphChunkLinker.link(computationGraphChunkSet);

        // Convert linked computation graph as model.
        LinkedComputationGraphConverter linkedComputationGraphConverter = new LinkedComputationGraphConverter();
        return linkedComputationGraphConverter.convert(linkedComputationGraphs);
    }
}
