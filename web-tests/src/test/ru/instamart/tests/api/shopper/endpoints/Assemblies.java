package ru.instamart.tests.api.shopper.endpoints;

import instamart.api.common.RestBase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Assemblies extends RestBase {

    @BeforeMethod(alwaysRun = true)
    public void preconditions() {
        kraken.shopperApi().authorisation("testshopper1","888888");
    }

    @Test(  description = "Получаем сборки",
            groups = {"rest-smoke","rest-shopper-smoke"},
            priority = 1)
    public void getAssemblies() {
        System.out.println("тут будет тест");
    }
}
