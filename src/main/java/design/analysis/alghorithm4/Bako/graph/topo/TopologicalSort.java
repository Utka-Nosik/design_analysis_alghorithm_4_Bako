package design.analysis.alghorithm4.Bako.graph.topo;

import design.analysis.alghorithm4.Bako.graph.DirectedGraph;

import java.util.*;

public class TopologicalSort {

    private final DirectedGraph graph;

    public TopologicalSort(DirectedGraph graph) {
        this.graph = graph;
    }

    public List<String> getOrder() {
        Map<String, Integer> inDegree = new HashMap<>();
        for (String node : graph.getNodes()) {
            inDegree.put(node, 0);
        }

        for (String node : graph.getNodes()) {
            for (String neighbor : graph.getNeighbors(node)) {
                inDegree.put(neighbor, inDegree.get(neighbor) + 1);
            }
        }

        Queue<String> queue = new LinkedList<>();
        for (Map.Entry<String, Integer> entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.add(entry.getKey());
            }
        }

        List<String> topologicalOrder = new ArrayList<>();
        while (!queue.isEmpty()) {
            String node = queue.poll();
            topologicalOrder.add(node);

            for (String neighbor : graph.getNeighbors(node)) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.add(neighbor);
                }
            }
        }

        if (topologicalOrder.size() != graph.getNodes().size()) {
            throw new IllegalStateException("The graph has a cycle, topological sort is not possible.");
        }

        return topologicalOrder;
    }
}