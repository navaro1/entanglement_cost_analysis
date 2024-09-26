package com.ug.zik.constanalysis;

import com.ug.zik.constanalysis.capacitycalculator.Calculator;
import com.ug.zik.constanalysis.model.GraphBuilder;
import com.ug.zik.constanalysis.model.network.NetworkParams;
import com.ug.zik.constanalysis.model.network.abilene.AbileneEdge;
import com.ug.zik.constanalysis.model.network.abilene.AbileneVertex;
import com.ug.zik.constanalysis.model.network.nsfnet.NSFNetEdge;
import com.ug.zik.constanalysis.model.network.nsfnet.NSFNetVertex;

import java.math.MathContext;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) {
        final MathContext mc = new MathContext(25);
        Map<AbileneVertex, Double> abileneVertices = Arrays
                .stream(AbileneVertex.values())
                .collect(Collectors.toMap(
                        Function.identity(),
                        v -> 1.0d
                ));
        NetworkParams<AbileneVertex, AbileneEdge> abileneParams = NetworkParams.build(
                abileneVertices,
                AbileneEdge.VardoyanPaperWeights(),
                AbileneVertex.class,
                AbileneEdge.class
        );
        Map<NSFNetVertex, Double> nsfnetVertices = Arrays
                .stream(NSFNetVertex.values())
                .collect(Collectors.toMap(
                        Function.identity(),
                        v -> 1.0d
                ));
        NetworkParams<NSFNetVertex, NSFNetEdge> nsfnetParams = NetworkParams.build(
                nsfnetVertices,
                NSFNetEdge.VardoyanPaperWeights(),
                NSFNetVertex.class,
                NSFNetEdge.class
        );
        var abileneGraph = GraphBuilder.buildGraphWithCalculatedProbability(abileneParams);
        var nsfnetGraph = GraphBuilder.buildGraphWithCalculatedProbability(nsfnetParams);

        final var abileneResults = Calculator.calculateAmGmCapacities(abileneGraph, AbileneVertex.v0, AbileneVertex.v10, mc);
        final var nsfnetResults = Calculator.calculateAmGmCapacities(nsfnetGraph, NSFNetVertex.v_0, NSFNetVertex.v_13, mc);
        System.out.println("Abilene results:");
        System.out.println(abileneResults);
        System.out.println("NSFNet results:");
        System.out.println(nsfnetResults);
    }


}
