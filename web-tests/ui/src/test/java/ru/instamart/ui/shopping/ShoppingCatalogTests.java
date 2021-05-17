package ru.instamart.ui.shopping;

import ru.instamart.core.setting.Config;
import ru.instamart.ui.TestBase;
import ru.instamart.ui.checkpoint.BaseUICheckpoints;
import ru.instamart.ui.checkpoint.itemcard.ItemCardAndCatalogCheckpoints;
import ru.instamart.core.testdata.lib.Addresses;
import ru.instamart.ui.module.Shop;
import ru.instamart.ui.module.User;
import ru.instamart.ui.module.shop.ShippingAddressModal;
import ru.instamart.ui.Elements;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ShoppingCatalogTests extends TestBase {
    BaseUICheckpoints baseChecks = new BaseUICheckpoints();
    ItemCardAndCatalogCheckpoints itemChecks = new ItemCardAndCatalogCheckpoints();
    @BeforeClass(alwaysRun = true,
            description = "Подготавливаем тестовое окружение к тестовому прогону")
    public void setup() {
        User.Logout.quickly();
        kraken.get().page(Config.DEFAULT_RETAILER);
        ShippingAddressModal.open();
        ShippingAddressModal.fill(Addresses.Moscow.defaultAddress());
        ShippingAddressModal.selectAddressSuggest();
        ShippingAddressModal.submit();
    }

    @CaseId(1595)
    @Test(
            description = "Тест работы cо шторкой каталога",
            groups = {"sbermarket-Ui-smoke","ui-smoke-production"}
    )
    public void successValidateCatalogDrawer() {
        Shop.CatalogDrawer.open();
        itemChecks.checkIsCatalogDrawerOpen("Не открывается шторка каталога\n");
        baseChecks.checkIsElementPresent(Elements.CatalogDrawer.categoryFirstLevel("Бакалея"),5);
        baseChecks.checkIsElementPresent(Elements.CatalogDrawer.closeButton(),2);
        Shop.CatalogDrawer.close();
        itemChecks.checkIsCatalogDrawerClosed("Не закрывается шторка каталога\n");
    }

    @CaseId(1596)
    @Test(
            description = "Тест открытия категории 1 уровня из шторки каталога",
            groups = {"sbermarket-Ui-smoke","MRAutoCheck","ui-smoke-production"}
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
            groups = {"sbermarket-Ui-smoke","ui-smoke-production"}
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
            groups = {"sbermarket-Ui-smoke","ui-smoke-production"}
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
            groups = {"sbermarket-Ui-smoke","ui-smoke-production"}
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
            groups = {"sbermarket-Ui-smoke","ui-smoke-production"}
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
            groups = {"sbermarket-Ui-smoke","ui-smoke-production"}
    )
    public void successOperateItemCardOnSearchPage() {
        Shop.Search.searchField("хлеб");
        Shop.Catalog.Item.open();
        itemChecks.checkIsItemCardOpen("Не открывается карточка продукта в выдаче поиска\n");
        Shop.ItemCard.close();
        itemChecks.checkIsItemCardClosed("Не закрывается карточка продукта в выдаче поиска\n");
    }
}
