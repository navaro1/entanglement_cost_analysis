package com.ug.zik.constanalysis.model.network.worldcities.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvParser {

    private static final String WORLD_CITIES_LOCATION = "/cities/worldcities.csv";

    // "city","city_ascii","lat","lng","country","iso2","iso3","admin_name","capital","population","id"
    public static List<City> extractCities(List<Function<City, Boolean>> filters) throws IOException {
        try (final InputStream is = CsvParser.class.getResourceAsStream(WORLD_CITIES_LOCATION);
             final BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            return reader.lines()
                    .skip(1)
                    .flatMap(line -> {
                        String[] fields = line.split(",");
                        try {
                            return Stream.of(new City(
                                    fields[1].replace("\"", ""),
                                    fields[5].replace("\"", ""),
                                    Double.parseDouble(fields[2].replace("\"", "")),
                                    Double.parseDouble(fields[3].replace("\"", "")),
                                    Integer.parseInt(fields[9].replace("\"", ""))
                            ));
                        } catch (NumberFormatException e) {
                            return Stream.empty();
                        }
                    }).filter(city -> filters.stream().allMatch(f -> f.apply(city)))
                    .collect(Collectors.toList());
        }
    }
}
