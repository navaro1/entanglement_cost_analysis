package com.ug.zik.constanalysis.model.network.nsfnet;

import com.ug.zik.constanalysis.model.network.Edge;

import java.util.HashMap;
import java.util.Map;

public enum NSFNetEdge implements Edge<NSFNetVertex> {

    v0_v1(NSFNetVertex.v_0, NSFNetVertex.v_1),
    v0_v2(NSFNetVertex.v_0, NSFNetVertex.v_2),
    v0_v3(NSFNetVertex.v_0, NSFNetVertex.v_3),
    v1_v2(NSFNetVertex.v_1, NSFNetVertex.v_2),
    v1_v7(NSFNetVertex.v_1, NSFNetVertex.v_7),
    v2_v5(NSFNetVertex.v_2, NSFNetVertex.v_5),
    v3_v4(NSFNetVertex.v_3, NSFNetVertex.v_4),
    v3_v10(NSFNetVertex.v_3, NSFNetVertex.v_10),
    v4_v5(NSFNetVertex.v_4, NSFNetVertex.v_5),
    v4_v6(NSFNetVertex.v_4, NSFNetVertex.v_6),
    v5_v8(NSFNetVertex.v_5, NSFNetVertex.v_8),
    v5_v12(NSFNetVertex.v_5, NSFNetVertex.v_12),
    v6_v7(NSFNetVertex.v_6, NSFNetVertex.v_7),
    v7_v9(NSFNetVertex.v_7, NSFNetVertex.v_9),
    v8_v9(NSFNetVertex.v_8, NSFNetVertex.v_9),
    v9_v11(NSFNetVertex.v_9, NSFNetVertex.v_11),
    v9_v13(NSFNetVertex.v_9, NSFNetVertex.v_13),
    v10_v11(NSFNetVertex.v_10, NSFNetVertex.v_11),
    v10_v13(NSFNetVertex.v_10, NSFNetVertex.v_13),
    v11_v12(NSFNetVertex.v_11, NSFNetVertex.v_12),
    v12_v13(NSFNetVertex.v_12, NSFNetVertex.v_13);

    public final NSFNetVertex start;
    public final NSFNetVertex end;

    NSFNetEdge(NSFNetVertex start, NSFNetVertex end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public NSFNetVertex getStart() {
        return this.start;
    }

    @Override
    public NSFNetVertex getEnd() {
        return this.end;
    }

    public static Map<NSFNetEdge, Double> VardoyanPaperWeights() {
        var weights = new HashMap<NSFNetEdge, Double>();
        weights.put(NSFNetEdge.v0_v1, 0.5423);
        weights.put(NSFNetEdge.v0_v2, 0.6827);
        weights.put(NSFNetEdge.v0_v3, 0.5679);
        weights.put(NSFNetEdge.v1_v2, 0.4308);
        weights.put(NSFNetEdge.v1_v7, 0.2479);
        weights.put(NSFNetEdge.v2_v5, 0.3583);
        weights.put(NSFNetEdge.v3_v4, 0.6827);
        weights.put(NSFNetEdge.v3_v10, 0.2980);
        weights.put(NSFNetEdge.v4_v5, 0.5423);
        weights.put(NSFNetEdge.v4_v6, 0.6226);
        weights.put(NSFNetEdge.v5_v8, 0.5179);
        weights.put(NSFNetEdge.v5_v12, 0.3583);
        weights.put(NSFNetEdge.v6_v7, 0.6520);
        weights.put(NSFNetEdge.v7_v9, 0.6520);
        weights.put(NSFNetEdge.v8_v9, 0.5946);
        weights.put(NSFNetEdge.v9_v11, 0.7149);
        weights.put(NSFNetEdge.v9_v13, 0.7149);
        weights.put(NSFNetEdge.v10_v11, 0.6226);
        weights.put(NSFNetEdge.v10_v13, 0.5679);
        weights.put(NSFNetEdge.v11_v12, 0.7149);
        weights.put(NSFNetEdge.v12_v13, 0.7839);
        return weights;
    }
}
