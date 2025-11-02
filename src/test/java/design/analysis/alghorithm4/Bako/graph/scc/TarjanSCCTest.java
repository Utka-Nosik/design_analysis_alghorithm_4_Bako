package design.analysis.alghorithm4.Bako.graph.scc;

import design.analysis.alghorithm4.Bako.graph.DirectedGraph;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class TarjanSCCTest {

    @Test
    void testFindSCCs_onSingleCycleGraph() {
        DirectedGraph graph = new DirectedGraph();
        graph.addNode("A", 10);
        graph.addNode("B", 5);
        graph.addNode("C", 8);
        graph.addNode("D", 6);

        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "A");
        graph.addEdge("B", "D");

        TarjanSCC sccFinder = new TarjanSCC(graph);
        List<List<String>> sccs = sccFinder.findSCCs();

        assertNotNull(sccs);
        assertEquals(2, sccs.size(), "Should find 2 SCCs: {A,B,C} and {D}");

        boolean foundCycleScc = false;
        boolean foundSingleNodeScc = false;

        for (List<String> scc : sccs) {
            if (scc.size() == 3) {
                foundCycleScc = true;
                assertTrue(scc.contains("A") && scc.contains("B") && scc.contains("C"));
            }
            if (scc.size() == 1) {
                foundSingleNodeScc = true;
                assertTrue(scc.contains("D"));
            }
        }

        assertTrue(foundCycleScc, "The 3-node SCC {A,B,C} was not found.");
        assertTrue(foundSingleNodeScc, "The single-node SCC {D} was not found.");
    }
}