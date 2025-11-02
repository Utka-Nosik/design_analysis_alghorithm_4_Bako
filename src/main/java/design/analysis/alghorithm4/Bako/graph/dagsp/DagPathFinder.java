package design.analysis.alghorithm4.Bako.graph.dagsp;

import design.analysis.alghorithm4.Bako.graph.DirectedGraph;
import java.util.*;

public class DagPathFinder {

    private final DirectedGraph graph;
    private final List<String> topologicalOrder;
    public long operationCount = 0;

    public DagPathFinder(DirectedGraph graph, List<String> topologicalOrder) {
        this.graph = graph;
        this.topologicalOrder = topologicalOrder;
    }

    public PathResult findLongestPath() {
        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> parent = new HashMap<>();

        for (String node : graph.getNodes()) {
            distances.put(node, Integer.MIN_VALUE);
        }

        for (String node : topologicalOrder) {
            if (distances.get(node) == Integer.MIN_VALUE) {
                distances.put(node, graph.getDuration(node));
            }
        }

        for (String u : topologicalOrder) {
            for (String v : graph.getNeighbors(u)) {
                operationCount++;
                int newDist = distances.get(u) + graph.getDuration(v);
                if (newDist > distances.get(v)) {
                    distances.put(v, newDist);
                    parent.put(v, u);
                }
            }
        }

        int maxDuration = -1;
        String endNode = null;
        for (Map.Entry<String, Integer> entry : distances.entrySet()) {
            if (entry.getValue() > maxDuration) {
                maxDuration = entry.getValue();
                endNode = entry.getKey();
            }
        }

        List<String> path = reconstructPath(parent, endNode);
        return new PathResult(path, maxDuration);
    }

    private List<String> reconstructPath(Map<String, String> parentMap, String endNode) {
        if (endNode == null) return Collections.emptyList();

        LinkedList<String> path = new LinkedList<>();
        String current = endNode;
        while (current != null) {
            path.addFirst(current);
            current = parentMap.get(current);
        }
        return path;
    }
}