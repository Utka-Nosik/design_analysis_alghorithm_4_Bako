package design.analysis.alghorithm4.Bako.util;

import design.analysis.alghorithm4.Bako.graph.TaskEdge;
import design.analysis.alghorithm4.Bako.graph.TaskNode;
import java.util.List;

public class GraphData {
    private String graphName;
    private List<TaskNode> nodes;
    private List<TaskEdge> edges;

    public String getGraphName() {
        return graphName;
    }

    public List<TaskNode> getNodes() {
        return nodes;
    }

    public List<TaskEdge> getEdges() {
        return edges;
    }
}