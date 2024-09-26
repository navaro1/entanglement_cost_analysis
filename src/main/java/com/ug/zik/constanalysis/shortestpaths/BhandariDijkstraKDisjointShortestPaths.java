package com.ug.zik.constanalysis.shortestpaths;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.BhandariKDisjointShortestPaths;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

public class BhandariDijkstraKDisjointShortestPaths<V, E> extends BhandariKDisjointShortestPaths<V, E> {
    /**
     * Creates a new instance of the algorithm.
     *
     * @param graph graph on which shortest paths are searched.
     * @throws IllegalArgumentException if the graph is null.
     * @throws IllegalArgumentException if the graph is undirected.
     * @throws IllegalArgumentException if the graph is not simple.
     */
    public BhandariDijkstraKDisjointShortestPaths(Graph<V, E> graph) {
        super(graph);
    }

    @Override
    // TODO: fix negative weight
    protected GraphPath<V, E> calculateShortestPath(V startVertex, V endVertex) {
        return new DijkstraShortestPath<V, E>(this.workingGraph).getPath(startVertex, endVertex);
    }
}
