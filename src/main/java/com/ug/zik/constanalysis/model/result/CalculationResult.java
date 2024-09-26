package com.ug.zik.constanalysis.model.result;

import com.ug.zik.constanalysis.model.network.QCNode;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class CalculationResult {

    private final List<Entry> results;

    public CalculationResult(List<Entry> results) {
        Objects.requireNonNull(results, "results must not be null");
        results.sort(Comparator.comparing(Entry::getCapacity).reversed());
        this.results = results;
    }

    public List<Entry> getResults() {
        return List.copyOf(results);
    }

    public String toString() {
        return this.results.stream().map(Entry::toString).reduce((s1, s2) -> s1 + "\n" + s2).orElse("");
    }


    public static class Entry {
        private final Set<GraphPath<QCNode, DefaultWeightedEdge>> paths;
        private final BigDecimal capacity;

        public Entry(final Set<GraphPath<QCNode, DefaultWeightedEdge>> paths, final BigDecimal capacity) {
            Objects.requireNonNull(paths, "path must not be null");
            Objects.requireNonNull(capacity, "capacity must not be null");
            this.paths = paths;
            this.capacity = capacity;
        }

        public BigDecimal getCapacity() {
            return capacity;
        }

        public String toString() {
            final var prettyPaths = this.paths.stream().map(path -> {
                final var prettyPath = path.getVertexList().stream().map(node -> node.getVertex().name()).reduce((s1, s2) -> s1 + " -> " + s2).orElse("");
                return "Path: " + prettyPath;
            }).reduce((s1, s2) -> s1 + ", " + s2).orElse("");
            return "Capacity: " + this.capacity +", Paths [" + this.paths.size() + "]: " + prettyPaths;
        }
    }
}
