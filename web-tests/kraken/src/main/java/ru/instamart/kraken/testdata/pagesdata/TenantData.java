package ru.instamart.kraken.testdata.pagesdata;

public class TenantData {

    private String name;
    private String alias;

    public TenantData(String name, String alias){
        this.name = name;
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public String getAlias() {
        return alias;
    }
}
