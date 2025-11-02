package design.analysis.alghorithm4.Bako;

import design.analysis.alghorithm4.Bako.graph.DirectedGraph;
import design.analysis.alghorithm4.Bako.graph.TaskNode;
import design.analysis.alghorithm4.Bako.graph.TaskEdge;
import design.analysis.alghorithm4.Bako.util.GraphData;
import design.analysis.alghorithm4.Bako.util.GraphLoader;

public class Main {
    public static void main(String[] args) {
        String filePath = "data/small_cyclic_graph.json";

        System.out.println("Loading graph from: " + filePath);
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

        System.out.println("Graph loaded successfully!");
        System.out.println("Total nodes: " + graph.getNodes().size());
        System.out.println("\n--- Sanity Check ---");
        String testNode = "B";
        System.out.println("Neighbors of node '" + testNode + "': " + graph.getNeighbors(testNode));
        System.out.println("Duration of node '" + testNode + "': " + graph.getDuration(testNode));
    }
}