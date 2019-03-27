package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.Addresses;

public class ShoppingCatalog extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        kraken.perform().quickLogout();
        kraken.get().page("metro");
        kraken.shipAddress().set(Addresses.Moscow.defaultAddress());
    }


    @BeforeMethod(alwaysRun = true)
    public void preconditions() {
        kraken.get().page("metro");
    }


    @Test(
            description = "Тест работы cо шторкой каталога",
            groups = {"acceptance", "regression"},
            priority = 601
    )
    public void successOperateCatalogDrawer() {
        kraken.shopping().openCatalog();

        Assert.assertTrue(kraken.detect().isCatalogDrawerOpen(),
                "Не открывается шторка каталога");

        kraken.shopping().closeCatalog();

        Assert.assertFalse(kraken.detect().isCatalogDrawerOpen(),
                "Не закрывается шторка каталога");
    }


}
