package com.ug.zik.constanalysis;

import com.ug.zik.constanalysis.capacitycalculator.Calculator;
import com.ug.zik.constanalysis.model.GraphBuilder;
import com.ug.zik.constanalysis.model.network.NetworkParams;
import com.ug.zik.constanalysis.model.network.VardoyanFigure2.VardoyanEdge;
import com.ug.zik.constanalysis.model.network.VardoyanFigure2.VardoyanVertex;
import com.ug.zik.constanalysis.model.network.abilene.AbileneEdge;
import com.ug.zik.constanalysis.model.network.abilene.AbileneVertex;

import java.math.MathContext;
import java.util.Map;
import java.util.Random;

public class App {
    public static void main(String[] args) {
        final Random random = new Random(100);
        final MathContext mc = new MathContext(25);

        NetworkParams<AbileneVertex, AbileneEdge> abileneParams = NetworkParams.build(
                Map.of(
                        AbileneVertex., 0.123d,
                        VardoyanVertex.S1, 0.345d,
                        VardoyanVertex.S2, 1d,
                        VardoyanVertex.S3, 0.123d,
                        VardoyanVertex.S4, 1d,
                        VardoyanVertex.T, 0.334d
                ),
                AbileneEdge.VardoyanPaperWeights(),
                VardoyanVertex.class,
                VardoyanEdge.class
        );
        final var results = Calculator.calculateAmGmCapacities(abileneParams, AbileneVertex.v0, AbileneVertex.v10, mc);
        System.out.println(results);
    }


}
