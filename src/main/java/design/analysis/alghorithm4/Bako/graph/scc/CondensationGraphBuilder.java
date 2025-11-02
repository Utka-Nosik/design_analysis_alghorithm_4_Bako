package design.analysis.alghorithm4.Bako.graph.scc;

import design.analysis.alghorithm4.Bako.graph.DirectedGraph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CondensationGraphBuilder {

    private final DirectedGraph originalGraph;
    private final List<List<String>> sccs;
    private final Map<String, String> nodeToSccNodeIdMap = new HashMap<>();

    public CondensationGraphBuilder(DirectedGraph originalGraph, List<List<String>> sccs) {
        this.originalGraph = originalGraph;
        this.sccs = sccs;
        mapNodesToSccIds();
    }

    private void mapNodesToSccIds() {
        for (int i = 0; i < sccs.size(); i++) {
            String sccNodeId = "SCC_" + i;
            for (String node : sccs.get(i)) {
                nodeToSccNodeIdMap.put(node, sccNodeId);
            }
        }
    }

    public DirectedGraph build() {
        DirectedGraph condensationGraph = new DirectedGraph();

        for (int i = 0; i < sccs.size(); i++) {
            List<String> scc = sccs.get(i);
            int totalDuration = scc.stream().mapToInt(originalGraph::getDuration).sum();
            String sccNodeId = "SCC_" + i;
            condensationGraph.addNode(sccNodeId, totalDuration);
        }

        for (String sourceNode : originalGraph.getNodes()) {
            String sourceSccId = nodeToSccNodeIdMap.get(sourceNode);

            for (String destNode : originalGraph.getNeighbors(sourceNode)) {
                String destSccId = nodeToSccNodeIdMap.get(destNode);

                if (!sourceSccId.equals(destSccId)) {
                    if (!condensationGraph.getNeighbors(sourceSccId).contains(destSccId)) {
                        condensationGraph.addEdge(sourceSccId, destSccId);
                    }
                }
            }
        }
        return condensationGraph;
    }
}