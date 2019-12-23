package ru.instamart.application;

import ru.instamart.application.models.ServerData;

public class Servers {

    public static ServerData instamart_staging() {
        return new ServerData(
                Tenants.metro(), "staging", "storefront.staging.instamart.ru" , "babur:hex78%2EBerwyn");
    }

    public static ServerData instamart_production() {
        return new ServerData(
                Tenants.metro(),"production", "instamart.ru" , "");
    }

    public static ServerData metro_staging() {
        return new ServerData(
                Tenants.metro(),"staging", "storefront.staging.instamart.ru" , "babur:hex78%2EBerwyn");
    }

    public static ServerData metro_production() {
        return new ServerData(
                Tenants.metro(),"production", "delivery.metro-cc.ru" , "");
    }

    public static ServerData sbermarket_staging() {
        return new ServerData(
                Tenants.sbermarket(),"staging", "storefront.staging.instamart.ru" , "babur:hex78%2EBerwyn");
    }

    public static ServerData sbermarket_production() {
        return new ServerData(
                Tenants.sbermarket(),"production", "sbermarket.ru" , "");
    }
}
