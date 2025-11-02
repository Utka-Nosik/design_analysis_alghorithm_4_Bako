package design.analysis.alghorithm4.Bako;

import design.analysis.alghorithm4.Bako.graph.DirectedGraph;
import design.analysis.alghorithm4.Bako.graph.TaskNode;
import design.analysis.alghorithm4.Bako.graph.TaskEdge;
import design.analysis.alghorithm4.Bako.graph.scc.TarjanSCC;
import design.analysis.alghorithm4.Bako.util.GraphData;
import design.analysis.alghorithm4.Bako.util.GraphLoader;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String filePath = "data/small_cyclic_graph.json";

        GraphData data = GraphLoader.loadGraph(filePath);
        if (data == null) {
            System.err.println("Failed to load graph data.");
            return;
        }
        DirectedGraph graph = new DirectedGraph();
        for (TaskNode node : data.getNodes()) {
            graph.addNode(node.getId(), node.getDuration());
        }
        for (TaskEdge edge : data.getEdges()) {
            graph.addEdge(edge.getSource(), edge.getDestination());
        }
        System.out.println("Graph '" + data.getGraphName() + "' loaded successfully.");

        System.out.println("\n--- Finding Strongly Connected Components (SCCs) ---");
        TarjanSCC sccFinder = new TarjanSCC(graph);
        List<List<String>> sccs = sccFinder.findSCCs();

        System.out.println("Found " + sccs.size() + " SCCs:");
        int sccIndex = 1;
        for (List<String> scc : sccs) {
            System.out.println("SCC " + sccIndex + " (size " + scc.size() + "): " + scc);
            sccIndex++;
        }
    }
}