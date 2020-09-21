package instamart.core.testdata.ui;

import instamart.ui.common.pagesdata.TenantData;

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
