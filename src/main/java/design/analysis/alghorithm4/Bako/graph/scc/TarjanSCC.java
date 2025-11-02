package design.analysis.alghorithm4.Bako.graph.scc;

import design.analysis.alghorithm4.Bako.graph.DirectedGraph;
import java.util.*;

public class TarjanSCC {

    private final DirectedGraph graph;
    private final Map<String, Integer> ids = new HashMap<>();
    private final Map<String, Integer> lowLinks = new HashMap<>();
    private final Deque<String> stack = new ArrayDeque<>();
    private final Set<String> onStack = new HashSet<>();
    private final List<List<String>> sccs = new ArrayList<>();
    private int timer = 0;
    public long operationCount = 0;

    public TarjanSCC(DirectedGraph graph) {
        this.graph = graph;
    }

    public List<List<String>> findSCCs() {
        for (String node : graph.getNodes()) {
            if (!ids.containsKey(node)) {
                dfs(node);
            }
        }
        return sccs;
    }

    private void dfs(String at) {
        operationCount++;
        stack.push(at);
        onStack.add(at);
        ids.put(at, timer);
        lowLinks.put(at, timer);
        timer++;

        for (String to : graph.getNeighbors(at)) {
            if (!ids.containsKey(to)) {
                dfs(to);
                lowLinks.put(at, Math.min(lowLinks.get(at), lowLinks.get(to)));
            } else if (onStack.contains(to)) {
                lowLinks.put(at, Math.min(lowLinks.get(at), ids.get(to)));
            }
        }

        if (ids.get(at).equals(lowLinks.get(at))) {
            List<String> scc = new ArrayList<>();
            while (true) {
                String node = stack.pop();
                onStack.remove(node);
                scc.add(node);
                if (node.equals(at)) break;
            }
            sccs.add(scc);
        }
    }
}