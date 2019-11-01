package ru.instamart.application.models;

import java.util.List;

public class CityData {

    private final String name;
    private final String prepositionalName;
    private final List<String> addresses;


    public CityData(String name, String prepositionalName, List<String> addresses) {
        this.name = name;
        this.prepositionalName = prepositionalName;
        this.addresses = addresses;
    }

    public String getName() {
        return name;
    }

    public String getPrepositionalName() {
        return prepositionalName;
    }

    public List<String> getAddresses() {
        return addresses;
    }
}
