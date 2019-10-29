package ru.instamart.application.models;

public class CityData {

    private final String name;
    private final String prepositionalName;
    private final String[] addresses;


    public CityData(String name, String prepositionalName, String[] addresses) {
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

    public String[] getAddresses() {
        return addresses;
    }
}
