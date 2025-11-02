package design.analysis.alghorithm4.Bako;

import design.analysis.alghorithm4.Bako.graph.scc.CondensationGraphBuilder;
import design.analysis.alghorithm4.Bako.graph.DirectedGraph;
import design.analysis.alghorithm4.Bako.graph.scc.TarjanSCC;
import design.analysis.alghorithm4.Bako.graph.topo.TopologicalSort;
import design.analysis.alghorithm4.Bako.util.GraphData;
import design.analysis.alghorithm4.Bako.util.GraphLoader;
import design.analysis.alghorithm4.Bako.graph.TaskNode;
import design.analysis.alghorithm4.Bako.graph.TaskEdge;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String filePath = "data/small_cyclic_graph.json";

        GraphData data = GraphLoader.loadGraph(filePath);
        if (data == null) { return; }
        DirectedGraph graph = new DirectedGraph();
        for (TaskNode node : data.getNodes()) { graph.addNode(node.getId(), node.getDuration()); }
        for (TaskEdge edge : data.getEdges()) { graph.addEdge(edge.getSource(), edge.getDestination()); }
        System.out.println("Graph '" + data.getGraphName() + "' loaded successfully.");

        System.out.println("\n--- Finding Strongly Connected Components (SCCs) ---");
        TarjanSCC sccFinder = new TarjanSCC(graph);
        List<List<String>> sccs = sccFinder.findSCCs();
        System.out.println("Found " + sccs.size() + " SCCs.");

        System.out.println("\n--- Building Condensation Graph ---");
        CondensationGraphBuilder builder = new CondensationGraphBuilder(graph, sccs);
        DirectedGraph condensationGraph = builder.build();
        System.out.println("Condensation graph (DAG) built successfully!");

        System.out.println("\n--- Performing Topological Sort on Condensation Graph ---");
        TopologicalSort sorter = new TopologicalSort(condensationGraph);
        List<String> topologicalOrder = sorter.getOrder();
        System.out.println("Topological Order of SCCs: " + topologicalOrder);
    }
}