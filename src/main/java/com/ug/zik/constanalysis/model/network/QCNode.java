package com.ug.zik.constanalysis.model.network;

import java.util.Objects;

public class QCNode {

    private final Vertex vertex;
    private final double bellMeasurementProbability;

    public QCNode(final Vertex vertex, double bellMeasurementProbability) {
        this.vertex = vertex;
        this.bellMeasurementProbability = bellMeasurementProbability;
    }

    public double getBellMeasurementProbability() {
        return bellMeasurementProbability;
    }

    public Vertex getVertex() {
        return vertex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QCNode qcNode = (QCNode) o;
        return Double.compare(qcNode.bellMeasurementProbability, bellMeasurementProbability) == 0 && Objects.equals(vertex, qcNode.vertex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vertex, bellMeasurementProbability);
    }

    @Override
    public String toString() {
        return "QCNode{" +
                "name='" + vertex.name() + '\'' +
                ", bellMeasurementProbability=" + bellMeasurementProbability +
                '}';
    }
}
