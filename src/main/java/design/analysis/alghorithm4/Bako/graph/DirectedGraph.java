package design.analysis.alghorithm4.Bako.graph;

import java.util.*;

public class DirectedGraph {

    private final Map<String, List<String>> adjacencyList = new HashMap<>();
    private final Map<String, Integer> durations = new HashMap<>();
    private final Set<String> nodes = new HashSet<>();

    public void addNode(String id, int duration) {
        nodes.add(id);
        durations.put(id, duration);
        adjacencyList.putIfAbsent(id, new ArrayList<>());
    }

    public void addEdge(String source, String destination) {
        if (!nodes.contains(source) || !nodes.contains(destination)) {
            throw new IllegalArgumentException("Source or destination node does not exist.");
        }
        adjacencyList.get(source).add(destination);
    }

    public Set<String> getNodes() {
        return Collections.unmodifiableSet(nodes);
    }

    public List<String> getNeighbors(String node) {
        return Collections.unmodifiableList(adjacencyList.getOrDefault(node, Collections.emptyList()));
    }

    public int getDuration(String node) {
        return durations.getOrDefault(node, 0);
    }
}