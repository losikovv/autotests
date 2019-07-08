package ru.instamart.autotests.models;

public class RegionData {

    private final String alias;
    private final String name;
    private final String description;

    public RegionData(String alias, String name, String description) {
        this.alias = alias;
        this.name = name;
        this.description = description;
    }

    public String getAlias() {
        return alias;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
