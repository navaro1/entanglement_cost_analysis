package com.ug.zik.constanalysis.model.result;

import com.ug.zik.constanalysis.model.network.QCNode;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class AmGmResult {

    private final List<AmGmResult.Entry> results;

    public AmGmResult(List<AmGmResult.Entry> results) {
        Objects.requireNonNull(results, "results must not be null");
        results.sort(Comparator.comparing(AmGmResult.Entry::getAmCapacity).reversed());
        this.results = results;
    }

    public List<AmGmResult.Entry> getResults() {
        return List.copyOf(results);
    }

    public String toString() {
        return this.results.stream().map(AmGmResult.Entry::toString).reduce((s1, s2) -> s1 + "\n" + s2).orElse("");
    }

    public static class Entry {
        private final Set<GraphPath<QCNode, DefaultWeightedEdge>> paths;
        private final BigDecimal gmCapacity;
        private final BigDecimal amCapacity;

        public Entry(final Set<GraphPath<QCNode, DefaultWeightedEdge>> paths, final BigDecimal gmCapacity, BigDecimal amCapacity) {
            Objects.requireNonNull(paths, "path must not be null");
            Objects.requireNonNull(gmCapacity, "gmCapacity must not be null");
            Objects.requireNonNull(amCapacity, "amCapacity must not be null");
            this.paths = paths;
            this.gmCapacity = gmCapacity;
            this.amCapacity = amCapacity;
        }

        public BigDecimal getGmCapacity() {
            return gmCapacity;
        }

        public BigDecimal getAmCapacity() {
            return amCapacity;
        }

        public String toString() {
            final var prettyPaths = this.paths.stream().map(path -> {
                final var prettyPath = path.getVertexList().stream().map(node -> node.getVertex().name()).reduce((s1, s2) -> s1 + " -> " + s2).orElse("");
                return "Path: " + prettyPath;
            }).reduce((s1, s2) -> s1 + ", " + s2).orElse("");
            return "GM Capacity: " + this.gmCapacity + ", AM Capacity: " + this.amCapacity + ", Paths [" + this.paths.size() + "]: " + prettyPaths;
        }
    }
}
