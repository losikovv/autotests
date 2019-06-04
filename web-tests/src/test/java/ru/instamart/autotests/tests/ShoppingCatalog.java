package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.autotests.application.Addresses;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.appmanager.ShopHelper;

public class ShoppingCatalog extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        kraken.perform().quickLogout();
        kraken.get().page("metro");
        kraken.shipAddress().set(Addresses.Moscow.defaultAddress());
    }

    @Test(
            description = "Тест работы cо шторкой каталога",
            groups = {"acceptance","regression"},
            priority = 601
    )
    public void successOperateCatalogDrawer() {
        ShopHelper.CatalogDrawer.open();

        Assert.assertTrue(kraken.detect().isCatalogDrawerOpen(),
                "Не открывается шторка каталога\n");

        ShopHelper.CatalogDrawer.close();

        Assert.assertFalse(kraken.detect().isCatalogDrawerOpen(),
                "Не закрывается шторка каталога\n");
    }

    @Test(
            description = "Тест открытия категории 1 уровня из шторки каталога",
            groups = {"smoke","acceptance","regression"},
            priority = 602
    )
    public void successReachDepartmentCategoryFromCatalogDrawer() {
        ShopHelper.CatalogDrawer.open();
        ShopHelper.CatalogDrawer.goToDepartment("Овощи и фрукты");

        Assert.assertTrue(kraken.detect().isElementDisplayed(Elements.Site.CategoryPage.title()),
                "Не открывается страница категории 1 уровня из шторки каталога");
    }

    @Test(
            description = "Тест открытия категории 2 уровня из шторки каталога",
            groups = {"smoke","acceptance","regression"},
            priority = 603
    )
    public void successReachTaxonCategoryFromCatalogDrawer() {
        ShopHelper.CatalogDrawer.open();
        ShopHelper.CatalogDrawer.goToTaxon("Грибы");

        Assert.assertTrue(kraken.detect().isElementDisplayed(Elements.Site.CategoryPage.filters()),
                "Не открывается страница категории 2 уровня из шторки каталога");
    }

    @Test(
            description = "Тест открывания/закрывания карточки продукта на главной",
            groups = {"smoke","acceptance","regression"},
            priority = 604
    )
    public void successOperateItemCardOnRetailerPage() {
        SoftAssert softAssert = new SoftAssert();
        kraken.get().page("metro");

        kraken.shopping().openFirstItemCard();

        softAssert.assertTrue(kraken.detect().isItemCardOpen(),
                "Не открывается карточка продукта на главной\n");

        ShopHelper.ItemCard.close();

        softAssert.assertFalse(kraken.detect().isItemCardOpen(),
                "Не закрывается карточка продукта на главной\n");

        softAssert.assertAll();
    }

    @Test(
            description = "Тест открывания/закрывания карточки продукта в department-категории",
            groups = {"smoke","acceptance","regression"},
            priority = 605
    )
    public void successOperateItemCardOnDepartmentCategoryPage() {
        SoftAssert softAssert = new SoftAssert();

        kraken.get().page("metro");
        ShopHelper.CatalogDrawer.open();
        ShopHelper.CatalogDrawer.goToDepartment("Овощи и фрукты");

        kraken.shopping().openFirstItemCard();
        softAssert.assertTrue(kraken.detect().isItemCardOpen(),
                "Не открывается карточка продукта в department-категории\n");

        ShopHelper.ItemCard.close();
        softAssert.assertFalse(kraken.detect().isItemCardOpen(),
                "Не закрывается карточка продукта в department-категории\n");

        softAssert.assertAll();
    }

    @Test(
            description = "Тест открывания/закрывания карточки продукта в taxon-категории",
            groups = {"smoke","acceptance","regression"},
            priority = 606
    )
    public void successOperateItemCardOnTaxonCategoryPage() {
        SoftAssert softAssert = new SoftAssert();

        kraken.get().page("metro");
        ShopHelper.CatalogDrawer.open();
        ShopHelper.CatalogDrawer.goToTaxon("Бакалея");

        kraken.shopping().openFirstItemCard();
        softAssert.assertTrue(kraken.detect().isItemCardOpen(),
                "Не открывается карточка продукта в taxon-категории\n");

        ShopHelper.ItemCard.close();
        softAssert.assertFalse(kraken.detect().isItemCardOpen(),
                "Не закрывается карточка продукта в taxon-категории\n");

        softAssert.assertAll();
    }

    @Test(
            description = "Тест открывания/закрывания карточки продукта в выдаче поиска",
            groups = {"smoke","acceptance","regression"},
            priority = 607
    )
    public void successOperateItemCardOnSearchPage() {
        SoftAssert softAssert = new SoftAssert();
        kraken.get().page("metro/search?keywords=хлеб");

        kraken.shopping().openFirstItemCard();

        softAssert.assertTrue(kraken.detect().isItemCardOpen(),
                "Не открывается карточка продукта в выдаче поиска\n");

        ShopHelper.ItemCard.close();

        softAssert.assertFalse(kraken.detect().isItemCardOpen(),
                "Не закрывается карточка продукта в выдаче поиска\n");

        softAssert.assertAll();
    }
}
