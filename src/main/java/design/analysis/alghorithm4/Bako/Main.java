package design.analysis.alghorithm4.Bako;

import design.analysis.alghorithm4.Bako.graph.dagsp.DagPathFinder;
import design.analysis.alghorithm4.Bako.graph.dagsp.PathResult;
import design.analysis.alghorithm4.Bako.graph.scc.CondensationGraphBuilder;
import design.analysis.alghorithm4.Bako.graph.DirectedGraph;
import design.analysis.alghorithm4.Bako.graph.scc.TarjanSCC;
import design.analysis.alghorithm4.Bako.graph.topo.TopologicalSort;
import design.analysis.alghorithm4.Bako.util.GraphData;
import design.analysis.alghorithm4.Bako.util.GraphLoader;
import design.analysis.alghorithm4.Bako.graph.TaskNode;
import design.analysis.alghorithm4.Bako.graph.TaskEdge;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class Main {

    public static void main(String[] args) {
        File dataDir = new File("data");
        File[] files = dataDir.listFiles((dir, name) -> name.endsWith(".json"));

        if (files == null) {
            System.err.println("Data directory not found or is empty.");
            return;
        }

        for (File file : files) {
            runAnalysis(file.getPath());
        }
    }

    private static void runAnalysis(String filePath) {
        System.out.println("\n\n========================================================");
        System.out.println("Running Analysis for: " + filePath);
        System.out.println("========================================================");

        long startTime;
        long endTime;

        GraphData data = GraphLoader.loadGraph(filePath);
        if (data == null) return;
        DirectedGraph graph = new DirectedGraph();
        for (TaskNode node : data.getNodes()) graph.addNode(node.getId(), node.getDuration());
        for (TaskEdge edge : data.getEdges()) graph.addEdge(edge.getSource(), edge.getDestination());
        System.out.println("Graph: " + data.getGraphName() + ", Nodes: " + graph.getNodes().size() + ", Edges: " + data.getEdges().size());

        startTime = System.nanoTime();
        TarjanSCC sccFinder = new TarjanSCC(graph);
        List<List<String>> sccs = sccFinder.findSCCs();
        endTime = System.nanoTime();
        System.out.println("\n--- SCC Results ---");
        System.out.println("Execution Time: " + (endTime - startTime) / 1_000_000 + " ms");
        System.out.println("Operation Count (DFS visits): " + sccFinder.operationCount);
        System.out.println("Found " + sccs.size() + " SCCs.");

        CondensationGraphBuilder builder = new CondensationGraphBuilder(graph, sccs);
        DirectedGraph condensationGraph = builder.build();

        startTime = System.nanoTime();
        TopologicalSort sorter = new TopologicalSort(condensationGraph);
        List<String> topologicalOrder = sorter.getOrder();
        endTime = System.nanoTime();
        System.out.println("\n--- Topological Sort Results ---");
        System.out.println("Execution Time: " + (endTime - startTime) / 1_000_000 + " ms");
        System.out.println("Operation Count (Queue ops): " + sorter.operationCount);
        System.out.println("Topological Order: " + topologicalOrder);

        startTime = System.nanoTime();
        DagPathFinder pathFinder = new DagPathFinder(condensationGraph, topologicalOrder);
        PathResult criticalPath = pathFinder.findLongestPath();
        endTime = System.nanoTime();
        System.out.println("\n--- Longest Path (Critical Path) Results ---");
        System.out.println("Execution Time: " + (endTime - startTime) / 1_000_000 + " ms");
        System.out.println("Operation Count (Edge relaxations): " + pathFinder.operationCount);
        System.out.println("Critical Path Duration: " + criticalPath.getTotalDuration());
        System.out.println("Critical Path: " + criticalPath.getPath());
    }
}