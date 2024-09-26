package com.ug.zik.constanalysis.model.network.abilene;

import com.ug.zik.constanalysis.model.network.Edge;

import java.util.HashMap;
import java.util.Map;


public enum AbileneEdge implements Edge<AbileneVertex> {

    v0_v1(AbileneVertex.v0, AbileneVertex.v1),
    v0_v3(AbileneVertex.v0, AbileneVertex.v3),
    v1_v2(AbileneVertex.v1, AbileneVertex.v2),
    v1_v3(AbileneVertex.v1, AbileneVertex.v3),
    v2_v5(AbileneVertex.v2, AbileneVertex.v5),
    v3_v4(AbileneVertex.v3, AbileneVertex.v4),
    v4_v5(AbileneVertex.v4, AbileneVertex.v5),
    v4_v7(AbileneVertex.v4, AbileneVertex.v7),
    v5_v8(AbileneVertex.v5, AbileneVertex.v8),
    v6_v7(AbileneVertex.v6, AbileneVertex.v7),
    v6_v10(AbileneVertex.v6, AbileneVertex.v10),
    v7_v8(AbileneVertex.v7, AbileneVertex.v8),
    v8_v9(AbileneVertex.v8, AbileneVertex.v9),
    v9_v10(AbileneVertex.v9, AbileneVertex.v10);

    public final AbileneVertex start;
    public final AbileneVertex end;

    AbileneEdge(final AbileneVertex start, final AbileneVertex end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public AbileneVertex getStart() {
        return this.start;
    }

    @Override
    public AbileneVertex getEnd() {
        return this.end;
    }

    public static Map<AbileneEdge, Double> VardoyanPaperWeights() {
        var weights = new HashMap<AbileneEdge, Double>();
        weights.put(AbileneEdge.v0_v1, 0.8540);
        weights.put(AbileneEdge.v0_v3, 0.8345);
        weights.put(AbileneEdge.v1_v2, 0.8794);
        weights.put(AbileneEdge.v1_v3, 0.8398);
        weights.put(AbileneEdge.v2_v5, 0.8131);
        weights.put(AbileneEdge.v3_v4, 0.8636);
        weights.put(AbileneEdge.v4_v5, 0.8579);
        weights.put(AbileneEdge.v4_v7, 0.8704);
        weights.put(AbileneEdge.v5_v8, 0.8544);
        weights.put(AbileneEdge.v6_v7, 0.8891);
        weights.put(AbileneEdge.v6_v10, 0.8538);
        weights.put(AbileneEdge.v7_v8, 0.8719);
        weights.put(AbileneEdge.v8_v9, 0.8646);
        weights.put(AbileneEdge.v9_v10, 0.8865);
        return weights;
    }
}