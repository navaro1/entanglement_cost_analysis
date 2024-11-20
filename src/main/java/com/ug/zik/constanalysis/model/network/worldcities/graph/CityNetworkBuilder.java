package com.ug.zik.constanalysis.model.network.worldcities.graph;

import com.ug.zik.constanalysis.model.network.NetworkParams;
import com.ug.zik.constanalysis.model.network.worldcities.csv.City;
import com.ug.zik.constanalysis.model.network.worldcities.csv.CsvParser;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class CityNetworkBuilder {


    public static void main(String[] args) throws IOException {
        List<Function<City, Boolean>> bigPolishCities = List.of(
                city -> city.getCountryIso2().equals("PL"),
                city -> city.getPopulation() > 100_000
        );
        List<City> lines = CsvParser.extractCities(bigPolishCities);
        System.out.println(lines.size());
    }

    public NetworkParams<CityVertex, CityEdge> buildSnapshotGraph(
            List<Function<City, Boolean>> filters
    ) throws IOException {
        List<City> cities = CsvParser.extractCities(filters);
        if (cities.isEmpty()) {
            throw new IllegalArgumentException("No cities found");
        }
        Map<CityVertex, Double> cityToSwappingProbabilities = Map.of();
        Map<CityEdge, Double> edgeToEntanglementGenerationProbabilities = Map.of();
        return new NetworkParams<>(cityToSwappingProbabilities, edgeToEntanglementGenerationProbabilities);
    }
}
