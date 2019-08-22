package ru.instamart.autotests.application;

import ru.instamart.autotests.models.TenantData;

public class Tenants {

    public static TenantData instamart() {
        return new TenantData("Инстамарт", "instamart");
    }

    public static TenantData metro() {
        return new TenantData("METRO Delivery CC", "metro");
    }

    public static TenantData sbermarket() {
        return new TenantData("СберМаркет", "sbermarket");
    }

}
