package ru.instamart.test.reforged.stf.shopping;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.kraken.testdata.lib.Addresses;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.stf.page.StfRouter.shop;

@Epic("STF UI")
@Feature("Проверка каталога товаров")
public final class ShoppingCatalogTests extends BaseTest {

    @CaseId(1595)
    @Test(
            description = "Тест работы cо шторкой каталога",
            groups = {"sbermarket-Ui-smoke","ui-smoke-production"}
    )
    public void successValidateCatalogDrawer() {
        shop().goToPage();
        shop().checkSpinnerIsNotVisible();
        shop().interactHeader().clickToSelectAddress();
        shop().interactAddress().setAddress(Addresses.Moscow.defaultAddress());
        shop().interactAddress().selectFirstAddress();
        shop().interactAddress().checkMarkerOnMapInAdviceIsNotVisible();
        shop().interactAddress().clickOnSave();
        shop().interactAddress().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().interactHeader().clickToCategoryMenu();
        shop().interactCategoryMenu().checkCatalogMenuIsOpen();
        shop().interactCategoryMenu().clickToCategoryByName("Бакалея");
        shop().interactCategoryMenu().clickToClose();
    }

//    @CaseId(1596)
//    @Test(
//            description = "Тест открытия категории 1 уровня из шторки каталога",
//            groups = {"sbermarket-Ui-smoke","MRAutoCheck","ui-smoke-production"}
//    )
//    public void successGoToDepartmentFromCatalogDrawer() {
//        shop().goToPage();
//        shop().checkSpinnerIsNotVisible();
//        shop().interactHeader().clickToSelectAddress();
//        shop().interactAddress().setAddress(Addresses.Moscow.defaultAddress());
//        shop().interactAddress().selectFirstAddress();
//        shop().interactAddress().checkMarkerOnMapInAdviceIsNotVisible();
//        shop().interactAddress().clickOnSave();
//        shop().interactAddress().checkAddressModalIsNotVisible();
//        shop().interactHeader().checkEnteredAddressIsVisible();
//
//        Shop.CatalogDrawer.open();
//        Shop.CatalogDrawer.goToDepartment("Овощи и фрукты");
//        baseChecks.checkPageIsAvailable();
//        baseChecks.checkIsElementDisplayed(Elements.CategoryPage.title(),
//                "Не открывается страница категории 1 уровня из шторки каталога");
//    }
//
//    @CaseId(1597)
//    @Test(
//            description = "Тест открытия категории 2 уровня из шторки каталога",
//            groups = {"sbermarket-Ui-smoke","ui-smoke-production"}
//    )
//    public void successGoToTaxonFromCatalogDrawer() {
//        shop().goToPage();
//        shop().checkSpinnerIsNotVisible();
//        shop().interactHeader().clickToSelectAddress();
//        shop().interactAddress().setAddress(Addresses.Moscow.defaultAddress());
//        shop().interactAddress().selectFirstAddress();
//        shop().interactAddress().checkMarkerOnMapInAdviceIsNotVisible();
//        shop().interactAddress().clickOnSave();
//        shop().interactAddress().checkAddressModalIsNotVisible();
//        shop().interactHeader().checkEnteredAddressIsVisible();
//
//        Shop.CatalogDrawer.open();
//        Shop.CatalogDrawer.goToTaxon("Грибы");
//        baseChecks.checkPageIsAvailable();
//        baseChecks.checkIsElementDisplayed(Elements.CategoryPage.title(),
//                "Не открывается страница категории 2 уровня из шторки каталога");
//    }
//
//    // TODO добавить тест successValidateItemCard
//
//    @CaseId(1598)
//    @Test(
//            description = "Тест открывания/закрывания карточки продукта на главной",
//            groups = {"sbermarket-Ui-smoke","ui-smoke-production"}
//    )
//    public void successOperateItemCardOnRetailerPage() {
//        shop().goToPage();
//        shop().checkSpinnerIsNotVisible();
//        shop().interactHeader().clickToSelectAddress();
//        shop().interactAddress().setAddress(Addresses.Moscow.defaultAddress());
//        shop().interactAddress().selectFirstAddress();
//        shop().interactAddress().checkMarkerOnMapInAdviceIsNotVisible();
//        shop().interactAddress().clickOnSave();
//        shop().interactAddress().checkAddressModalIsNotVisible();
//        shop().interactHeader().checkEnteredAddressIsVisible();
//
//        AppManager.getWebDriver().get("https://stf-kraken.k-stage.sbermarket.tech/metro");//костыль из-за бейсик авторизации
//        Shop.Catalog.Item.open();
//        itemChecks.checkIsItemCardOpen("Не открывается карточка продукта на главной\n");
//        Shop.ItemCard.close();
//        itemChecks.checkIsItemCardClosed("Не закрывается карточка продукта на главной\n");
//    }
//
//    @CaseId(1599)
//    @Test(
//            description = "Тест открывания/закрывания карточки продукта в department-категории",
//            groups = {"sbermarket-Ui-smoke","ui-smoke-production"}
//    )
//    public void successOperateItemCardOnDepartmentPage() {
//        shop().goToPage();
//        shop().checkSpinnerIsNotVisible();
//        shop().interactHeader().clickToSelectAddress();
//        shop().interactAddress().setAddress(Addresses.Moscow.defaultAddress());
//        shop().interactAddress().selectFirstAddress();
//        shop().interactAddress().checkMarkerOnMapInAdviceIsNotVisible();
//        shop().interactAddress().clickOnSave();
//        shop().interactAddress().checkAddressModalIsNotVisible();
//        shop().interactHeader().checkEnteredAddressIsVisible();
//
//        Shop.CatalogDrawer.open();
//        Shop.CatalogDrawer.goToDepartment("Яйца");
//        Shop.Search.open();
//        itemChecks.checkIsItemCardOpen("Не открывается карточка продукта в department-категории\n");
//        Shop.ItemCard.close();
//        itemChecks.checkIsItemCardClosed("Не закрывается карточка продукта в department-категории\n");
//    }
//
//    @CaseId(1600)
//    @Test(
//            description = "Тест открывания/закрывания карточки продукта в taxon-категории",
//            groups = {"sbermarket-Ui-smoke","ui-smoke-production"}
//    )
//    public void successOperateItemCardOnTaxonPage() {
//        shop().goToPage();
//        shop().checkSpinnerIsNotVisible();
//        shop().interactHeader().clickToSelectAddress();
//        shop().interactAddress().setAddress(Addresses.Moscow.defaultAddress());
//        shop().interactAddress().selectFirstAddress();
//        shop().interactAddress().checkMarkerOnMapInAdviceIsNotVisible();
//        shop().interactAddress().clickOnSave();
//        shop().interactAddress().checkAddressModalIsNotVisible();
//        shop().interactHeader().checkEnteredAddressIsVisible();
//
//        Shop.CatalogDrawer.open();
//        Shop.CatalogDrawer.goToTaxon("Крупы");
//        Shop.Search.open();
//        itemChecks.checkIsItemCardOpen("Не открывается карточка продукта в taxon-категории\n");
//        Shop.ItemCard.close();
//        itemChecks.checkIsItemCardClosed("Не закрывается карточка продукта в taxon-категории\n");
//    }
//
//    @CaseId(1601)
//    @Test(
//            description = "Тест открывания/закрывания карточки продукта в выдаче поиска",
//            groups = {"sbermarket-Ui-smoke","ui-smoke-production"}
//    )
//    public void successOperateItemCardOnSearchPage() {
//        shop().goToPage();
//        shop().checkSpinnerIsNotVisible();
//        shop().interactHeader().clickToSelectAddress();
//        shop().interactAddress().setAddress(Addresses.Moscow.defaultAddress());
//        shop().interactAddress().selectFirstAddress();
//        shop().interactAddress().checkMarkerOnMapInAdviceIsNotVisible();
//        shop().interactAddress().clickOnSave();
//        shop().interactAddress().checkAddressModalIsNotVisible();
//        shop().interactHeader().checkEnteredAddressIsVisible();
//
//        Shop.Search.searchField("сыр");
//        Shop.Search.openCard();
//        itemChecks.checkIsItemCardOpen("Не открывается карточка продукта в выдаче поиска\n");
//        Shop.ItemCard.close();
//        itemChecks.checkIsItemCardClosed("Не закрывается карточка продукта в выдаче поиска\n");
//    }
}
