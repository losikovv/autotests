package ru.instamart.tests.shopping;

import instamart.core.settings.Config;
import instamart.ui.checkpoints.BaseUICheckpoints;
import instamart.ui.checkpoints.users.ItemCardAndCatalogCheckpoints;
import instamart.ui.common.lib.Addresses;
import instamart.ui.modules.Shop;
import instamart.ui.modules.User;
import instamart.ui.objectsmap.Elements;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.tests.TestBase;

public class ShoppingCatalogTests extends TestBase {
    BaseUICheckpoints baseChecks = new BaseUICheckpoints();
    ItemCardAndCatalogCheckpoints itemChecks = new ItemCardAndCatalogCheckpoints();
    @BeforeClass(alwaysRun = true,
            description = "Подготавливаем тестовое окружение к тестовому прогону")
    public void setup() {
        User.Logout.quickly();
        kraken.get().page(Config.DEFAULT_RETAILER);
        User.ShippingAddress.set(Addresses.Moscow.defaultAddress(),true);
    }

    @CaseId(1595)
    @Test(
            description = "Тест работы cо шторкой каталога",
            groups = {"testing","sbermarket-Ui-smoke"}
    )
    public void successValidateCatalogDrawer() {
        Shop.CatalogDrawer.open();
        itemChecks.checkIsCatalogDrawerOpen("Не открывается шторка каталога\n");
        baseChecks.checkIsElementPresent(Elements.CatalogDrawer.category("Бакалея"));
        baseChecks.checkIsElementPresent(Elements.CatalogDrawer.closeButton());
        Shop.CatalogDrawer.close();
        itemChecks.checkIsCatalogDrawerClosed("Не закрывается шторка каталога\n");
    }

    @CaseId(1596)
    @Test(
            description = "Тест открытия категории 1 уровня из шторки каталога",
            groups = {"testing","sbermarket-Ui-smoke","MRAutoCheck"}
    )
    public void successGoToDepartmentFromCatalogDrawer() {
        Shop.CatalogDrawer.open();
        Shop.CatalogDrawer.goToDepartment("Овощи и фрукты");
        baseChecks.checkPageIsAvailable();
        baseChecks.checkIsElementDisplayed(Elements.CategoryPage.title(),
                "Не открывается страница категории 1 уровня из шторки каталога");
    }

    @CaseId(1597)
    @Test(
            description = "Тест открытия категории 2 уровня из шторки каталога",
            groups = {"testing","sbermarket-Ui-smoke"}
    )
    public void successGoToTaxonFromCatalogDrawer() {
        Shop.CatalogDrawer.open();
        Shop.CatalogDrawer.goToTaxon("Грибы");
        baseChecks.checkPageIsAvailable();
        baseChecks.checkIsElementDisplayed(Elements.CategoryPage.filters(),
                "Не открывается страница категории 2 уровня из шторки каталога");
    }

    // TODO добавить тест successValidateItemCard

    @CaseId(1598)
    @Test(
            description = "Тест открывания/закрывания карточки продукта на главной",
            groups = {"testing","sbermarket-Ui-smoke"}
    )
    public void successOperateItemCardOnRetailerPage() {
        Shop.Catalog.Item.open();
        itemChecks.checkIsItemCardOpen("Не открывается карточка продукта на главной\n");
        Shop.ItemCard.close();
        itemChecks.checkIsItemCardClosed("Не закрывается карточка продукта на главной\n");
    }

    @CaseId(1599)
    @Test(
            description = "Тест открывания/закрывания карточки продукта в department-категории",
            groups = {"testing","sbermarket-Ui-smoke"}
    )
    public void successOperateItemCardOnDepartmentPage() {
        Shop.CatalogDrawer.open();
        Shop.CatalogDrawer.goToDepartment("Яйца");
        Shop.Catalog.Item.open();
        itemChecks.checkIsItemCardOpen("Не открывается карточка продукта в department-категории\n");
        Shop.ItemCard.close();
        itemChecks.checkIsItemCardClosed("Не закрывается карточка продукта в department-категории\n");
    }
    @CaseId(1600)
    @Test(
            description = "Тест открывания/закрывания карточки продукта в taxon-категории",
            groups = {"testing","sbermarket-Ui-smoke"}
    )
    public void successOperateItemCardOnTaxonPage() {
        Shop.CatalogDrawer.open();
        Shop.CatalogDrawer.goToTaxon("Крупы");
        Shop.Catalog.Item.open();
        itemChecks.checkIsItemCardOpen("Не открывается карточка продукта в taxon-категории\n");
        Shop.ItemCard.close();
        itemChecks.checkIsItemCardClosed("Не закрывается карточка продукта в taxon-категории\n");
    }

    @CaseId(1601)
    @Test(
            description = "Тест открывания/закрывания карточки продукта в выдаче поиска",
            groups = {"testing","sbermarket-Ui-smoke"}
    )
    public void successOperateItemCardOnSearchPage() {
        Shop.Search.Field.fill("хлеб");
        //kraken.get().page("metro/search?keywords=хлеб");
        //https://instamart.atlassian.net/browse/STF-6766
        Shop.Catalog.Item.open();
        itemChecks.checkIsItemCardOpen("Не открывается карточка продукта в выдаче поиска\n");
        Shop.ItemCard.close();
        itemChecks.checkIsItemCardClosed("Не закрывается карточка продукта в выдаче поиска\n");
    }
}
