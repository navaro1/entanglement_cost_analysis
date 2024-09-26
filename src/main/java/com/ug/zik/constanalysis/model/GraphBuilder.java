package com.ug.zik.constanalysis.model;

import com.ug.zik.constanalysis.model.network.Edge;
import com.ug.zik.constanalysis.model.network.NetworkParams;
import com.ug.zik.constanalysis.model.network.QCNode;
import com.ug.zik.constanalysis.model.network.Vertex;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class GraphBuilder {

    public static <V extends Vertex, E extends Edge<V>> Graph<QCNode, DefaultWeightedEdge> buildGraphNaive(final NetworkParams<V, E> params) {
        Objects.requireNonNull(params, "params must not be null");
        final var graph = new SimpleDirectedWeightedGraph<QCNode, DefaultWeightedEdge>(DefaultWeightedEdge.class);
        params.getBsmProbabilities().forEach((vertex, probability) -> {
            final var node = new QCNode(vertex, probability);
            graph.addVertex(node);
        });
        Map<Vertex, QCNode> vertexMap = graph.vertexSet().stream().collect(Collectors.toMap(QCNode::getVertex, node -> node));
        params.getEntanglementGenerationProbabilities().forEach((edge, probability) -> {
            final var v1 = vertexMap.get(edge.getStart());
            final var v2 = vertexMap.get(edge.getEnd());
            addBiDirectionEdges(graph, v1, v2, probability);
        });
        return graph;
    }

    public static <V extends Vertex, E extends Edge<V>> Graph<QCNode, DefaultWeightedEdge> buildGraphWithCalculatedProbability(final NetworkParams<V, E> params) {
        Objects.requireNonNull(params, "params must not be null");
        final var graph = new SimpleDirectedWeightedGraph<QCNode, DefaultWeightedEdge>(DefaultWeightedEdge.class);
        params.getBsmProbabilities().forEach((vertex, probability) -> {
            final var node = new QCNode(vertex, probability);
            graph.addVertex(node);
        });
        Map<Vertex, QCNode> vertexMap = graph.vertexSet().stream().collect(Collectors.toMap(QCNode::getVertex, node -> node));
        params.getEntanglementGenerationProbabilities().forEach((edge, probability) -> {
            final var v1 = vertexMap.get(edge.getStart());
            final var v2 = vertexMap.get(edge.getEnd());
            final var v1v2Weight = probability * v1.getBellMeasurementProbability();
            final var v2v1Weight = probability * v2.getBellMeasurementProbability();
            graph.setEdgeWeight(graph.addEdge(v1, v2), v1v2Weight);
            graph.setEdgeWeight(graph.addEdge(v2, v1), v2v1Weight);
        });
        return graph;
    }

    private static <V> void addBiDirectionEdges(Graph<V, DefaultWeightedEdge> graph, V v1, V v2, double weight) {
        graph.setEdgeWeight(graph.addEdge(v1, v2), weight);
        graph.setEdgeWeight(graph.addEdge(v2, v1), weight);
    }
}
