package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.autotests.application.Addresses;
import ru.instamart.autotests.application.Elements;

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
                "Не открывается шторка каталога\n");

        kraken.shopping().closeCatalog();

        Assert.assertFalse(kraken.detect().isCatalogDrawerOpen(),
                "Не закрывается шторка каталога\n");
    }


    @Test(
            description = "Тест открытия категории 1 уровня",
            groups = {"acceptance", "regression"},
            priority = 602
    )
    public void successReachDepartmentCategory() {
        kraken.shopping().openCatalog();
        kraken.perform().click(Elements.Site.CatalogDrawer.department(1));

        Assert.assertTrue(kraken.detect().isElementDisplayed(Elements.Site.CategoryPage.title()),
                "Не открывается страница категории 1 уровня из шторки каталога");
    }


    @Test(
            description = "Тест открытия категории 2 уровня",
            groups = {"acceptance", "regression"},
            priority = 603
    )
    public void successReachTaxonCategory() {
        kraken.shopping().openCatalog();
        kraken.perform().hoverOn(Elements.Site.CatalogDrawer.department(1));
        kraken.await().simply(1); // Ждем пока развернется категория
        kraken.perform().click(Elements.Site.CatalogDrawer.taxon(1,1));

        Assert.assertTrue(kraken.detect().isElementDisplayed(Elements.Site.CategoryPage.filters()),
                "Не открывается страница категории 2 уровня из шторки каталога");
    }


    @Test(
            description = "Тест открывания/закрывания карточки продукта",
            groups = {"acceptance","regression"},
            priority = 604
    )
    public void successOperateItemCard() throws Exception {
        SoftAssert softAssert = new SoftAssert();

        kraken.shopping().openFirstItemCard();

        softAssert.assertTrue(kraken.detect().isItemCardOpen(),
                "Не открывается карточка продукта\n");

        kraken.shopping().closeItemCard();

        softAssert.assertFalse(kraken.detect().isItemCardOpen(),
                "Не закрывается карточка продукта\n");

        softAssert.assertAll();
    }

}
