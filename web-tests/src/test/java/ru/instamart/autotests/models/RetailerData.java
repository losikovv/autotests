package ru.instamart.autotests.models;

public class RetailerData {
    private final String name;
    private final String path;

    public RetailerData(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getLogin() {
        return name;
    }

    public String getPath() {
        return path;
    }
}
