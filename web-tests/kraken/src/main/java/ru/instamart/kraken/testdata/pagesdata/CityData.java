package ru.instamart.kraken.testdata.pagesdata;

public class CityData {

    private final String name;
    private final String prepositionalName;
    private final Object addresses;


    public CityData(String name, String prepositionalName, Object addresses) {
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

    public Object getAddresses() {
        return addresses;
    }
}
