package com.ug.zik.constanalysis.model.network.worldcities.csv;


import java.util.Objects;

public class City {
    private final String name;
    private final String countryIso2;
    private final double lat;
    private final double lng;

    private final int population;

    City(String name, String countryIso2, double lat, double lng, int population) {
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(countryIso2, "countryIso2 must not be null");
        if (lat < -90 || lat > 90) {
            throw new IllegalArgumentException("lat must be in range [-90, 90]");
        }
        if (lng < -180 || lng > 180) {
            throw new IllegalArgumentException("lng must be in range [-180, 180]");
        }
        if (population < 0) {
            throw new IllegalArgumentException("population must be non-negative");
        }
        this.name = name;
        this.countryIso2 = countryIso2;
        this.lat = lat;
        this.lng = lng;
        this.population = population;
    }


    public int getPopulation() {
        return population;
    }

    public double getLng() {
        return lng;
    }

    public double getLat() {
        return lat;
    }

    public String getCountryIso2() {
        return countryIso2;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", countryIso2='" + countryIso2 + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", population=" + population +
                '}';
    }
}
