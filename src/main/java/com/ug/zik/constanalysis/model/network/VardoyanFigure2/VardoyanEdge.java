package com.ug.zik.constanalysis.model.network.VardoyanFigure2;

import com.ug.zik.constanalysis.model.network.Edge;

public enum VardoyanEdge implements Edge<VardoyanVertex> {
    s_s1(VardoyanVertex.S, VardoyanVertex.S1),
    s_s3(VardoyanVertex.S, VardoyanVertex.S3),
    s1_s2(VardoyanVertex.S1, VardoyanVertex.S2),
    s2_s3(VardoyanVertex.S2, VardoyanVertex.S3),
    s2_t(VardoyanVertex.S2, VardoyanVertex.T),
    s3_s4(VardoyanVertex.S3, VardoyanVertex.S4),
    s4_t(VardoyanVertex.S4, VardoyanVertex.T);

    private final VardoyanVertex start;
    private final VardoyanVertex end;

    VardoyanEdge(VardoyanVertex start, VardoyanVertex end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public VardoyanVertex getStart() {
        return this.start;
    }

    @Override
    public VardoyanVertex getEnd() {
        return this.end;
    }
}
