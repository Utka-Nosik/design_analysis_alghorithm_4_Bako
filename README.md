# Assignment 4: Smart City Scheduling (SCC & DAG Paths)

This project implements a task scheduling pipeline for a smart city scenario. It involves detecting cyclic dependencies using Tarjan's algorithm for Strongly Connected Components (SCCs), creating a condensation graph (DAG), performing a topological sort, and finding the critical path (longest path) to determine the minimum project completion time.

## 1. Data Summary

The algorithms were tested on 9 datasets of varying sizes and structures. The chosen weight model is **node durations**, representing the time each task takes to complete.

| Category | Filename                     | Nodes (n) | Edges (e) | Structure                    |
|----------|------------------------------|-----------|-----------|------------------------------|
| Small    | `small_dag.json`             | 6         | 6         | Acyclic (DAG)                |
| Small    | `small_single_scc.json`      | 6         | 6         | 1 SCC (size 3)               |
| Small    | `small_two_sccs.json`        | 6         | 6         | 2 SCCs (sizes 2, 3)          |
| Medium   | `medium_dag.json`            | 10        | 11        | Acyclic (DAG)                |
| Medium   | `medium_mixed_sccs.json`     | 10        | 11        | 3 SCCs (sizes 3, 3, 3)       |
| Medium   | `medium_sparse_cyclic.json`  | 12        | 12        | 1 large SCC (size 12)        |
| Large    | `large_dag.json`             | 20        | 22        | Acyclic (DAG)                |
| Large    | `large_dense_cyclic.json`    | 15        | 19        | 1 large SCC (size 15)        |
| Large    | `large_many_sccs.json`       | 16        | 19        | 6 SCCs + single nodes        |

## 2. Results Analysis

The performance of the pipeline was measured across all datasets. Key metrics include execution time (ms), operation counts for each major algorithm, and the final critical path duration.

| Graph Name                   | SCC Time (ms) | SCC Ops | TopoSort Time (ms) | TopoSort Ops | LongestPath Time (ms) | LongestPath Ops | Critical Path Duration |
|------------------------------|---------------|---------|--------------------|--------------|-----------------------|-----------------|------------------------|
| Small Acyclic Graph (DAG)    | 0             | 6       | 0                  | 7            | 0                     | 5               | 24                     |
| Small Graph with One SCC     | 0             | 6       | 0                  | 5            | 0                     | 3               | 48                     |
| Small Graph with Two SCCs    | 0             | 6       | 0                  | 4            | 0                     | 3               | 35                     |
| Medium Acyclic Graph (DAG)   | 0             | 10      | 0                  | 11           | 0                     | 11              | 47                     |
| Medium Graph with Mixed SCCs | 1             | 10      | 0                  | 5            | 0                     | 4               | 52                     |
| Medium Sparse Cyclic Graph   | 0             | 12      | 0                  | 2            | 0                     | 0               | 48                     |
| Large Acyclic Graph (DAG)    | 1             | 20      | 0                  | 21           | 0                     | 22              | 33                     |
| Large Dense Cyclic Graph     | 2             | 15      | 0                  | 2            | 0                     | 0               | 75                     |
| Large Graph with Many SCCs   | 1             | 16      | 0                  | 9            | 0                     | 8               | 27                     |

### Observations

*   **SCC Detection (Tarjan's):** The execution time and operation count scale linearly with the number of nodes and edges (V+E), as expected. The algorithm is highly efficient even on graphs where all nodes form a single, large SCC (`large_dense_cyclic.json`).
*   **Topological Sort (Kahn's):** This step is consistently fast, as it operates on the much smaller condensation graph. The operation count is proportional to the number of SCCs and the edges between them.
*   **Longest Path in DAG:** This algorithm is also very efficient, with its performance dependent on the number of nodes and edges in the condensation graph, not the original graph. For graphs that condense into a single SCC, there are no edges to relax, resulting in 0 operations.
*   **Bottlenecks:** The primary performance bottleneck for the entire pipeline is the initial SCC detection phase. The subsequent steps on the condensed DAG are significantly faster. The complexity of the original graph (density, number of cycles) directly impacts the time taken by Tarjan's algorithm.

## 3. Conclusions

1.  **Methodology:** The implemented pipeline (SCC -> Condensation -> TopoSort -> DAG Path) is a robust and efficient method for analyzing task dependency graphs, even those with complex cyclic dependencies. It successfully transforms an intractable cyclic problem into a solvable acyclic one.
2.  **Practical Recommendations:** For real-world project scheduling, the results highlight the importance of minimizing cyclic dependencies. Each SCC represents a tightly coupled block of tasks that cannot be linearized easily and increases the overall project duration. Breaking these cycles by re-evaluating task dependencies is the most effective strategy for simplifying the project plan and shortening the critical path.
