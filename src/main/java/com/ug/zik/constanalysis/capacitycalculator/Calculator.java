package com.ug.zik.constanalysis.capacitycalculator;

import ch.obermuhlner.math.big.BigDecimalMath;
import com.ug.zik.constanalysis.model.network.QCNode;
import com.ug.zik.constanalysis.model.network.Vertex;
import com.ug.zik.constanalysis.model.result.AmGmResult;
import com.ug.zik.constanalysis.model.result.CalculationResult;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.BhandariKDisjointShortestPaths;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Calculator {

    public static AmGmResult calculateAmGmCapacities(
            final Graph<QCNode, DefaultWeightedEdge> graph,
            final Vertex source,
            final Vertex target,
            final MathContext mathContext
    ) {
        validateParams(graph, source, target, mathContext);
        final QCNode sourceNode = getNodeOrThrow(graph, source, "source must be a vertex in the graph");
        final QCNode targetNode = getNodeOrThrow(graph, target, "target must be a vertex in the graph");
        if (graph.edgesOf(sourceNode).isEmpty() || graph.edgesOf(targetNode).isEmpty()) {
            return new AmGmResult(List.of());
        }
        final var maximalPossibleDisjointPaths = graph.edgesOf(sourceNode)
                .stream().filter(e -> graph.getEdgeSource(e) == sourceNode)
                .count();
        final var shortestPathsAlgorithms = new BhandariKDisjointShortestPaths<>(graph);
        final var entries = IntStream.rangeClosed(1, (int) maximalPossibleDisjointPaths)
                .mapToObj(k -> shortestPathsAlgorithms.getPaths(sourceNode, targetNode, k))
                .map(paths -> {
                    final var am = calculateAmCapacity(graph, mathContext, paths.size(), paths);
                    final var gm = calculateGmCapacity(graph, mathContext, paths.size(), paths);
                    return new AmGmResult.Entry(Set.copyOf(paths), gm, am);
                })
                .collect(Collectors.toList());
        return new AmGmResult(entries);
    }

    /**
     * Calculate the capacity of source-target pair within a graph based on the formula:
     * D_k -- set of sets R of k disjoint paths
     * k -- number of disjoint paths in R
     * V -- set of vertices in the graph
     * q(v) -- probability value on vertex (Bell measurement probability of vertex v)
     * \min_{R \in D_k} -log(\sqrt[k]{\prod_{v \in V} q(v)})
     */
    public static CalculationResult calculateCapacityBasedOnGm(
            final Graph<QCNode, DefaultWeightedEdge> graph,
            final Vertex source,
            final Vertex target,
            final MathContext mathContext
    ) {
        validateParams(graph, source, target, mathContext);
        final QCNode sourceNode = getNodeOrThrow(graph, source, "source must be a vertex in the graph");
        final QCNode targetNode = getNodeOrThrow(graph, target, "target must be a vertex in the graph");
        if (graph.edgesOf(sourceNode).isEmpty() || graph.edgesOf(targetNode).isEmpty()) {
            return new CalculationResult(List.of());
        }
        final var maximalPossibleDisjointPaths = graph.edgesOf(sourceNode)
                .stream().filter(e -> graph.getEdgeSource(e) == sourceNode)
                .count();

        final var shortestPathsAlgorithms = new BhandariKDisjointShortestPaths<>(graph);
        final var capacities = IntStream.rangeClosed(1, (int) maximalPossibleDisjointPaths).mapToObj(i -> {
            List<GraphPath<QCNode, DefaultWeightedEdge>> shortestPaths = shortestPathsAlgorithms.getPaths(sourceNode, targetNode, i);
            final var pathsCapacity = calculateGmCapacity(graph, mathContext, i, shortestPaths);
            return new CalculationResult.Entry(Set.copyOf(shortestPaths), pathsCapacity);
        }).collect(Collectors.toList());
        return new CalculationResult(capacities);

    }

    /**
     * Calculate the capacity of source-target pair within a graph based on the formula:
     * D_k -- set of sets R of k disjoint paths
     * k -- number of disjoint paths in R
     * V -- set of vertices in the graph
     * q(v) -- probability value on vertex (Bell measurement probability of vertex v)
     * \min_{R \in D_k} -log(\frac{1}{k}{\sum{v \in V} q(v)})
     */
    public static CalculationResult calculateCapacitiesBasedOnAm(
            final Graph<QCNode, DefaultWeightedEdge> graph,
            final Vertex source,
            final Vertex target,
            final MathContext mathContext
    ) {
        validateParams(graph, source, target, mathContext);
        final QCNode sourceNode = getNodeOrThrow(graph, source, "source must be a vertex in the graph");
        final QCNode targetNode = getNodeOrThrow(graph, target, "target must be a vertex in the graph");
        if (graph.edgesOf(sourceNode).isEmpty() || graph.edgesOf(targetNode).isEmpty()) {
            return new CalculationResult(List.of());
        }
        final var maximalPossibleDisjointPaths = graph.edgesOf(sourceNode)
                .stream().filter(e -> graph.getEdgeSource(e) == sourceNode)
                .count();

        final var shortestPathsAlgorithms = new BhandariKDisjointShortestPaths<>(graph);
        final var capacities = IntStream.rangeClosed(1, (int) maximalPossibleDisjointPaths).mapToObj(i -> {
            List<GraphPath<QCNode, DefaultWeightedEdge>> shortestPaths = shortestPathsAlgorithms.getPaths(sourceNode, targetNode, i);
            final var pathsCapacity = calculateAmCapacity(graph, mathContext, i, shortestPaths);
            return new CalculationResult.Entry(Set.copyOf(shortestPaths), pathsCapacity);
        }).collect(Collectors.toList());
        return new CalculationResult(capacities);
    }

    private static BigDecimal calculateAmCapacity(Graph<QCNode, DefaultWeightedEdge> graph, MathContext mathContext, int k, List<GraphPath<QCNode, DefaultWeightedEdge>> shortestPaths) {
        return shortestPaths
                .stream()
                .parallel()
                .map(path -> calculatePathCapacity(graph, path, mathContext))
                .reduce((bd1, bd2) -> bd1.add(bd2, mathContext))
                .stream()
                .map(bd -> bd.divide(BigDecimal.valueOf(k), mathContext))
                .map(bd -> BigDecimalMath.log(bd, mathContext))
                .map(BigDecimal::negate)
                .findFirst()
                .orElseThrow();
    }

    private static void validateParams(Graph<QCNode, DefaultWeightedEdge> graph, Vertex source, Vertex target, MathContext mathContext) {
        Objects.requireNonNull(graph, "graph must not be null");
        Objects.requireNonNull(source, "source must not be null");
        Objects.requireNonNull(target, "target must not be null");
        Objects.requireNonNull(mathContext, "mathContext must not be null");
        if (source == target) {
            throw new IllegalArgumentException("source and target must be different vertices");
        }
    }

    private static QCNode getNodeOrThrow(Graph<QCNode, DefaultWeightedEdge> graph, Vertex source, String s) {
        return graph.vertexSet()
                .stream().filter(v -> v.getVertex() == source)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(s));
    }

    private static BigDecimal calculatePathCapacity(
            Graph<QCNode, DefaultWeightedEdge> graph, final GraphPath<QCNode, DefaultWeightedEdge> path, final MathContext mc) {
        if (path.getVertexList().isEmpty()) {
            return BigDecimal.ZERO;
        } else if (path.getVertexList().size() <= 2) {
            return BigDecimal.ONE;
        }
        return path.getEdgeList().stream().map(graph::getEdgeWeight).map(BigDecimal::valueOf)
                .reduce(BigDecimal.ONE, BigDecimal::multiply);
    }


    private static BigDecimal calculateGmCapacity(Graph<QCNode, DefaultWeightedEdge> graph, MathContext mathContext, int k, List<GraphPath<QCNode, DefaultWeightedEdge>> shortestPaths) {
        return shortestPaths
                .stream()
                .parallel()
                .map(path -> calculatePathCapacity(graph, path, mathContext))
                .reduce((bd1, bd2) -> bd1.multiply(bd2, mathContext))
                .stream()
                .map(bd -> {
                    var rootPow = BigDecimal.valueOf(k);
                    return BigDecimalMath.root(bd, rootPow, mathContext);
                })
                .map(bd -> BigDecimalMath.log(bd, mathContext))
                .map(BigDecimal::negate)
                .findFirst()
                .orElseThrow();
    }
}
