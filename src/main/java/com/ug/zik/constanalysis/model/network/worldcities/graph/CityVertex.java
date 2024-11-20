package com.ug.zik.constanalysis.model.network.worldcities.graph;

import com.ug.zik.constanalysis.model.network.Vertex;

import java.util.Objects;

public class CityVertex implements Vertex {
    private final String name;

    public CityVertex(String name) {
        Objects.requireNonNull(name, "name must not be null");
        this.name = name;
    }

    @Override
    public String name() {
        return this.name;
    }
}
