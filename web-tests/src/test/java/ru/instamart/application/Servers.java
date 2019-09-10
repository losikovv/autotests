package ru.instamart.application;

import ru.instamart.application.models.ServerData;

public class Servers {

    public static ServerData instamart_production() {
        return new ServerData(Tenants.instamart(), "production", "instamart.ru" , "");
    }

    public static ServerData instamart_preprod() {
        return new ServerData(Tenants.instamart(),"preprod", "staging.instamart.ru" , "sbermart:JBWFKC35d4cp");
    }

    public static ServerData instamart_staging() {
        return new ServerData(Tenants.instamart(),"staging", "storefront.staging.instamart.ru" , "babur:hex78%2EBerwyn");
    }

    public static ServerData metro_production() {
        return new ServerData(Tenants.metro(),"production", "delivery.metro-cc.ru" , "");
    }

    public static ServerData metro_preprod() {
        return new ServerData(Tenants.metro(),"preprod", "staging.instamart.ru" , "sbermart:JBWFKC35d4cp");
    }

    public static ServerData metro_staging() {
        return new ServerData(Tenants.metro(),"staging", "storefront.staging.instamart.ru" , "babur:hex78%2EBerwyn");
    }

    public static ServerData sbermarket_production() {
        return new ServerData(Tenants.sbermarket(),"production", "sbermarket.ru" , "");
    }

    public static ServerData sbermarket_preprod() {
        return new ServerData(Tenants.sbermarket(),"preprod", "staging.instamart.ru" , "sbermart:JBWFKC35d4cp");
    }

    public static ServerData sbermarket_staging() {
        return new ServerData(Tenants.sbermarket(),"staging", "storefront.staging.instamart.ru" , "babur:hex78%2EBerwyn");
    }

    public static ServerData sbermarket_cloud_staging() {
        return new ServerData(Tenants.sbermarket(),"staging", "sb.staging.instamart.ru" , "sbermart:JBWFKC35d4cp");
    }
}
