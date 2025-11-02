package design.analysis.alghorithm4.Bako.graph.dagsp;

import java.util.Collections;
import java.util.List;

public class PathResult {
    private final List<String> path;
    private final int totalDuration;

    public PathResult(List<String> path, int totalDuration) {
        this.path = path;
        this.totalDuration = totalDuration;
    }

    public List<String> getPath() {
        return Collections.unmodifiableList(path);
    }

    public int getTotalDuration() {
        return totalDuration;
    }

    @Override
    public String toString() {
        return "Path: " + path + ", Total Duration: " + totalDuration;
    }
}