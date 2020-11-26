package ru.instamart.tests.shopping;

import instamart.core.settings.Config;
import instamart.ui.checkpoints.BaseUICheckpoints;
import instamart.ui.common.lib.Addresses;
import instamart.ui.modules.Shop;
import instamart.ui.modules.User;
import instamart.ui.objectsmap.Elements;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.tests.TestBase;

public class ShoppingCatalogTests extends TestBase {
    BaseUICheckpoints baseChecks = new BaseUICheckpoints();
    @BeforeClass(alwaysRun = true,
            description = "Подготавливаем тестовое окружение к тестовому прогону")
    public void setup() {
        User.Logout.quickly();
        kraken.get().page(Config.CoreSettings.defaultRetailer);
        User.ShippingAddress.set(Addresses.Moscow.defaultAddress(),true);
        kraken.get().page(Config.CoreSettings.defaultRetailer);
    }

    @Test(
            description = "Тест работы cо шторкой каталога",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 601
    )
    public void successValidateCatalogDrawer() {
        Shop.CatalogDrawer.open();

        Assert.assertTrue(
                kraken.detect().isCatalogDrawerOpen(),
                    "Не открывается шторка каталога\n");

        baseChecks.checkIsElementPresent(Elements.CatalogDrawer.category("Бакалея"));
        baseChecks.checkIsElementPresent(Elements.CatalogDrawer.closeButton());

        Shop.CatalogDrawer.close();

        Assert.assertFalse(
                kraken.detect().isCatalogDrawerOpen(),
                    "Не закрывается шторка каталога\n");
    }

    @Test(
            description = "Тест открытия категории 1 уровня из шторки каталога",
            groups = {"sbermarket-smoke","sbermarket-acceptance","sbermarket-regression"},
            priority = 602
    )
    public void successGoToDepartmentFromCatalogDrawer() {
        Shop.CatalogDrawer.open();
        Shop.CatalogDrawer.goToDepartment("Овощи и фрукты");
        baseChecks.checkPageIsAvailable();
        Assert.assertTrue(
                kraken.detect().isElementDisplayed(Elements.CategoryPage.title()),
                    "Не открывается страница категории 1 уровня из шторки каталога");
    }

    @Test(
            description = "Тест открытия категории 2 уровня из шторки каталога",
            groups = {"sbermarket-smoke","sbermarket-acceptance","sbermarket-regression"},
            priority = 603
    )
    public void successGoToTaxonFromCatalogDrawer() {
        Shop.CatalogDrawer.open();
        Shop.CatalogDrawer.goToTaxon("Грибы");
        baseChecks.checkPageIsAvailable();
        Assert.assertTrue(
                kraken.detect().isElementDisplayed(Elements.CategoryPage.filters()),
                    "Не открывается страница категории 2 уровня из шторки каталога");
    }

    // TODO добавить тест successValidateItemCard

    @Test(
            description = "Тест открывания/закрывания карточки продукта на главной",
            groups = {"sbermarket-smoke","sbermarket-acceptance","sbermarket-regression"},
            priority = 604
    )
    public void successOperateItemCardOnRetailerPage() {
        SoftAssert softAssert = new SoftAssert();
        kraken.get().page(Config.CoreSettings.defaultRetailer);

        Shop.Catalog.Item.open();

        softAssert.assertTrue(
                kraken.detect().isItemCardOpen(),
                    "Не открывается карточка продукта на главной\n");

        Shop.ItemCard.close();

        softAssert.assertFalse(
                kraken.detect().isItemCardOpen(),
                    "Не закрывается карточка продукта на главной\n");

        softAssert.assertAll();
    }

    // TODO починить
    @Test( enabled = false,
            description = "Тест открывания/закрывания карточки продукта в department-категории",
            groups = {"sbermarket-smoke","sbermarket-acceptance","sbermarket-regression"},
            priority = 605
    )
    public void successOperateItemCardOnDepartmentPage() {
        SoftAssert softAssert = new SoftAssert();

        kraken.get().page(Config.CoreSettings.defaultRetailer);
        Shop.CatalogDrawer.open();
        Shop.CatalogDrawer.goToDepartment("Овощи и фрукты");

        Shop.Catalog.Item.open();

        softAssert.assertTrue(
                kraken.detect().isItemCardOpen(),
                    "Не открывается карточка продукта в department-категории\n");

        Shop.ItemCard.close();

        softAssert.assertFalse(
                kraken.detect().isItemCardOpen(),
                    "Не закрывается карточка продукта в department-категории\n");

        softAssert.assertAll();
    }

    // TODO починить
    @Test(enabled = false,
            description = "Тест открывания/закрывания карточки продукта в taxon-категории",
            groups = {"sbermarket-smoke","sbermarket-acceptance","sbermarket-regression"},
            priority = 606
    )
    public void successOperateItemCardOnTaxonPage() {
        SoftAssert softAssert = new SoftAssert();

        kraken.get().page(Config.CoreSettings.defaultRetailer);
        Shop.CatalogDrawer.open();
        Shop.CatalogDrawer.goToTaxon("Бакалея");

        Shop.Catalog.Item.open();

        softAssert.assertTrue(
                kraken.detect().isItemCardOpen(),
                    "Не открывается карточка продукта в taxon-категории\n");

        Shop.ItemCard.close();

        softAssert.assertFalse(
                kraken.detect().isItemCardOpen(),
                    "Не закрывается карточка продукта в taxon-категории\n");

        softAssert.assertAll();
    }

    @Test(
            description = "Тест открывания/закрывания карточки продукта в выдаче поиска",
            groups = {"sbermarket-smoke","sbermarket-acceptance","sbermarket-regression"},
            priority = 607
    )
    public void successOperateItemCardOnSearchPage() {
        SoftAssert softAssert = new SoftAssert();
        kraken.get().page("metro/search?keywords=хлеб");

        Shop.Catalog.Item.open();

        softAssert.assertTrue(
                kraken.detect().isItemCardOpen(),
                    "Не открывается карточка продукта в выдаче поиска\n");

        Shop.ItemCard.close();

        softAssert.assertFalse(
                kraken.detect().isItemCardOpen(),
                    "Не закрывается карточка продукта в выдаче поиска\n");

        softAssert.assertAll();
    }
}
