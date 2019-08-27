package ru.instamart.application.lib;

import ru.instamart.application.models.EnvironmentData;

public class Environments {

    public static EnvironmentData instamart_production() {
        return new EnvironmentData(Tenants.instamart(), "production", "instamart.ru" , "");
    }

    public static EnvironmentData instamart_staging() {
        return new EnvironmentData(Tenants.instamart(),"staging", "staging.instamart.ru" , "babur:hex78%2EBerwyn@");
    }

    public static EnvironmentData metro_production() {
        return new EnvironmentData(Tenants.metro(),"production", "delivery.metro-cc.ru" , "");
    }

    public static EnvironmentData metro_staging() {
        return new EnvironmentData(Tenants.metro(),"staging", "staging.instamart.ru" , "babur:hex78%2EBerwyn@");
    }

    public static EnvironmentData sbermarket_production() {
        return new EnvironmentData(Tenants.sbermarket(),"production", "sbermarket.ru" , "");
    }

    public static EnvironmentData sbermarket_staging() {
        return new EnvironmentData(Tenants.sbermarket(),"staging", "staging.instamart.ru" , "babur:hex78%2EBerwyn@");
    }

    public static EnvironmentData sbermarket_cloud_staging() {
        return new EnvironmentData(Tenants.sbermarket(),"staging", "sb.staging.instamart.ru" , "sbermart:JBWFKC35d4cp@");
    }
}
