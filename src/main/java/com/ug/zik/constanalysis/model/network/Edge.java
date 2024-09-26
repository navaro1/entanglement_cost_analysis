package com.ug.zik.constanalysis.model.network;

public interface Edge<T extends Vertex> {
    T getStart();

    T getEnd();
}
