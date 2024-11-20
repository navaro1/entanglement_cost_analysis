package com.ug.zik.constanalysis.model.network.worldcities.graph;

import com.ug.zik.constanalysis.model.network.Edge;

import java.util.Objects;

public class CityEdge implements Edge<CityVertex> {
    private final CityVertex start;
    private final CityVertex end;

    public CityEdge(CityVertex start, CityVertex end) {
        Objects.requireNonNull(start, "start must not be null");
        Objects.requireNonNull(end, "end must not be null");
        this.start = start;
        this.end = end;
    }


    @Override
    public CityVertex getStart() {
        return this.start;
    }

    @Override
    public CityVertex getEnd() {
        return this.end;
    }
}
