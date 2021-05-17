package ru.instamart.core.testdata.pagesdata;

public class WidgetData {
    private String id;
    private String name;
    private String provider;

    public WidgetData(String provider, String name,String id) {
        this.id = id;
        this.name = name;
        this.provider = provider;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProvider() {
        return provider;
    }
}
