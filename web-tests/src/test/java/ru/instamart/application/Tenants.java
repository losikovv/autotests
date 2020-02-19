package ru.instamart.application;

import ru.instamart.application.models.TenantData;

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
