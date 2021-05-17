package ru.instamart.core.testdata.pagesdata;

public class PromoData {

    private final String name;
    private final String code;
    private final String description;

    public PromoData(String name, String code, String description) {
        this.name = name;
        this.code = code;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
