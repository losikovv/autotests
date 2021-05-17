package ru.instamart.core.testdata;

import ru.instamart.core.testdata.pagesdata.TenantData;

public class Tenants {

    public static TenantData metro() {
        return new TenantData("METRO Delivery CC", "metro");
    }

    public static TenantData sbermarket() {
        return new TenantData("СберМАРКЕТ", "sbermarket");
    }

    public static TenantData lenta() {
        return new TenantData("ЛЕНТА", "lenta");
    }

}
