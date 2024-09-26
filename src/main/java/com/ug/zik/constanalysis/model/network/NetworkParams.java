package com.ug.zik.constanalysis.model.network;

import java.util.*;

public class NetworkParams<V extends Vertex, E extends Edge<V>> {
    private final Map<V, Double> bsmProbabilities;
    private final Map<E, Double> entanglementGenerationProbabilities;

    public static <V extends Enum<V> & Vertex, E extends Enum<E> & Edge<V>> NetworkParams<V, E> build(
            final Map<V, Double> bsmProbabilities,
            final Map<E, Double> entanglementGenerationProbabilities,
            final Class<V> vertexClass,
            final Class<E> edgeClass
    ) {
        Objects.requireNonNull(bsmProbabilities, "bsm probabilities cannot be null");
        Objects.requireNonNull(entanglementGenerationProbabilities, "entanglement generation probabilities cannot be null");
        if (bsmProbabilities.size() != EnumSet.allOf(vertexClass).size()) {
            throw new IllegalArgumentException("bsm probabilities must have the same size as the number of vertices");
        }
        if (entanglementGenerationProbabilities.size() != EnumSet.allOf(edgeClass).size()) {
            throw new IllegalArgumentException("entanglement generation probabilities must have the same size as the number of edges");
        }
        if (bsmProbabilities.values().stream().anyMatch(prob -> prob < 0 || prob > 1)) {
            throw new IllegalArgumentException("bsm probabilities must be in the range [0, 1]");
        }
        if (entanglementGenerationProbabilities.values().stream().anyMatch(prob -> prob < 0 || prob > 1)) {
            throw new IllegalArgumentException("entanglement generation probabilities must be in the range [0, 1]");
        }
        return new NetworkParams<V, E>(Map.copyOf(bsmProbabilities), Map.copyOf(entanglementGenerationProbabilities));
    }

    public static <V extends Enum<V> & Vertex, E extends Enum<E> & Edge<V>> NetworkParams<V, E> random(
            final Random random,
            final Class<V> vertexClass,
            final Class<E> edgeClass
    ) {
        final var bsmProbabilities = new HashMap<V, Double>();
        for (final var vertex : EnumSet.allOf(vertexClass)) {
            bsmProbabilities.put(vertex, random.nextDouble());
        }
        final var entanglementGenerationProbabilities = new HashMap<E, Double>();
        for (final var edge : EnumSet.allOf(edgeClass)) {
            entanglementGenerationProbabilities.put(edge, random.nextDouble());
        }

        return new NetworkParams<>(bsmProbabilities, entanglementGenerationProbabilities);

    }

    private NetworkParams(Map<V, Double> bsmProbabilities, Map<E, Double> entanglementGenerationProbabilities) {
        this.bsmProbabilities = bsmProbabilities;
        this.entanglementGenerationProbabilities = entanglementGenerationProbabilities;
    }

    public Map<V, Double> getBsmProbabilities() {
        return Map.copyOf(bsmProbabilities);
    }

    public Map<E, Double> getEntanglementGenerationProbabilities() {
        return Map.copyOf(entanglementGenerationProbabilities);
    }
}
