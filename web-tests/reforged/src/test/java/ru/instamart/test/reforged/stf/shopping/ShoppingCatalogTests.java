package ru.instamart.test.reforged.stf.shopping;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.kraken.data.Addresses;

import ru.instamart.kraken.listener.Skip;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.stf.page.StfRouter.*;
import static ru.instamart.reforged.stf.page.StfRouter.search;

@Epic("STF UI")
@Feature("Проверка каталога товаров")
public final class ShoppingCatalogTests extends BaseTest {

    @CaseId(1595)
    @Test(description = "Тест работы cо шторкой каталога", groups = "regression")
    public void successValidateCatalogDrawer() {
        shop().goToPage();
        shop().checkSpinnerIsNotVisible();
        shop().interactHeader().clickToSelectAddress();
        shop().interactAddress().checkYmapsReady();
        shop().interactAddress().fillAddress(Addresses.Moscow.defaultAddress());
        shop().interactAddress().selectFirstAddress();
        shop().interactAddress().checkMarkerOnMapInAdviceIsNotVisible();
        shop().interactAddress().clickOnSave();
        shop().interactAddress().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().interactHeader().clickToCategoryMenu();
        shop().interactCategoryMenu().checkCatalogMenuIsOpen();
        shop().interactCategoryMenu().checkFirstLevelElementsVisible();
        shop().interactCategoryMenu().clickToClose();
    }

    @CaseId(2583)
    @Test(description = "Тест открытия категории 1 уровня из шторки каталога", groups = {"MRAutoCheck", "regression"})
    public void successGoToDepartmentFromCatalogDrawer() {
        shop().goToPage();
        shop().checkSpinnerIsNotVisible();
        shop().interactHeader().clickToSelectAddress();
        shop().interactAddress().checkYmapsReady();
        shop().interactAddress().fillAddress(Addresses.Moscow.defaultAddress());
        shop().interactAddress().selectFirstAddress();
        shop().interactAddress().checkMarkerOnMapInAdviceIsNotVisible();
        shop().interactAddress().clickOnSave();
        shop().interactAddress().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();
        shop().goToPage();

        shop().interactHeader().clickToCategoryMenu();
        shop().interactCategoryMenu().checkCatalogMenuIsOpen();
        shop().interactCategoryMenu().clickToFirstLevelCategoryByName("Бакалея");

        seo().checkCatalogTitleVisible();
        seo().checkPageIsAvailable();
    }

    @CaseId(2584)
    @Test(description = "Тест открытия категории 2 уровня из шторки каталога", groups = "regression")
    public void successGoToTaxonFromCatalogDrawer() {
        shop().goToPage();
        shop().checkSpinnerIsNotVisible();
        shop().interactHeader().clickToSelectAddress();
        shop().interactAddress().checkYmapsReady();
        shop().interactAddress().fillAddress(Addresses.Moscow.defaultAddress());
        shop().interactAddress().selectFirstAddress();
        shop().interactAddress().checkMarkerOnMapInAdviceIsNotVisible();
        shop().interactAddress().clickOnSave();
        shop().interactAddress().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();
        shop().goToPage();

        shop().interactHeader().clickToCategoryMenu();
        shop().interactCategoryMenu().checkCatalogMenuIsOpen();
        shop().interactCategoryMenu().moveOnFirstLevelCategory("Молочные продукты");
        shop().interactCategoryMenu().clickToSecondLevelCategoryByName("Молоко");

        seo().checkCatalogTitleVisible();
        seo().checkPageIsAvailable();
    }

    @CaseId(2582)
    @Test(description = "Тест открывания/закрывания карточки продукта на главной", groups = "regression")
    public void successOperateItemCardOnRetailerPage() {
        shop().goToPage();
        shop().checkSpinnerIsNotVisible();
        shop().interactHeader().clickToSelectAddress();
        shop().interactAddress().checkYmapsReady();
        shop().interactAddress().fillAddress(Addresses.Moscow.defaultAddress());
        shop().interactAddress().selectFirstAddress();
        shop().interactAddress().checkMarkerOnMapInAdviceIsNotVisible();
        shop().interactAddress().clickOnSave();
        shop().interactAddress().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().goToPage();
        shop().refreshWithoutBasicAuth();

        shop().checkSpinnerIsNotVisible();
        shop().checkFirstProductCardIsVisible();
        shop().openFirstProductCard();
        shop().interactProductCard().checkProductCardVisible();
        shop().interactProductCard().close();
        shop().interactProductCard().checkProductCardIsNotVisible();
    }

    @CaseId(1599)
    @Test(description = "Тест открывания/закрывания карточки продукта в department-категории", groups = "regression")
    public void successOperateItemCardOnDepartmentPage() {
        shop().goToPage();
        shop().checkSpinnerIsNotVisible();
        shop().interactHeader().clickToSelectAddress();
        shop().interactAddress().checkYmapsReady();
        shop().interactAddress().fillAddress(Addresses.Moscow.defaultAddress());
        shop().interactAddress().selectFirstAddress();
        shop().interactAddress().checkMarkerOnMapInAdviceIsNotVisible();
        shop().interactAddress().clickOnSave();
        shop().interactAddress().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();
        shop().goToPage();

        shop().interactHeader().clickToCategoryMenu();
        shop().interactCategoryMenu().checkCatalogMenuIsOpen();
        shop().interactCategoryMenu().clickToFirstLevelCategoryByName("Бакалея");

        seo().checkCatalogTitleVisible();
        seo().checkSpinnerIsNotVisible();
        seo().refreshWithoutBasicAuth();
        seo().checkCatalogTitleVisible();
        seo().checkSpinnerIsNotVisible();

        seo().openFirstProductCardOnDepartment();
        seo().interactProductCard().checkProductCardVisible();
        seo().interactProductCard().close();
        seo().interactProductCard().checkProductCardIsNotVisible();
    }

    @CaseId(1600)
    @Test(description = "Тест открывания/закрывания карточки продукта в taxon-категории", groups = "regression")
    public void successOperateItemCardOnTaxonPage() {
        shop().goToPage();
        shop().checkSpinnerIsNotVisible();
        shop().interactHeader().clickToSelectAddress();
        shop().interactAddress().checkYmapsReady();
        shop().interactAddress().fillAddress(Addresses.Moscow.defaultAddress());
        shop().interactAddress().selectFirstAddress();
        shop().interactAddress().checkMarkerOnMapInAdviceIsNotVisible();
        shop().interactAddress().clickOnSave();
        shop().interactAddress().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();
        shop().goToPage();

        shop().interactHeader().clickToCategoryMenu();
        shop().interactCategoryMenu().checkCatalogMenuIsOpen();
        shop().interactCategoryMenu().moveOnFirstLevelCategory("Бакалея");
        shop().interactCategoryMenu().clickToSecondLevelCategoryByName("Крупы");

        seo().checkCatalogTitleVisible();
        seo().checkSpinnerIsNotVisible();
        seo().clickOnSubCategory("Гречневая");
        seo().checkSpinnerIsNotVisible();
        seo().refreshWithoutBasicAuth();

        seo().openFirstProductCardOnTaxon();
        seo().interactProductCard().checkProductCardVisible();
        seo().interactProductCard().close();
        seo().interactProductCard().checkProductCardIsNotVisible();
    }

    @CaseId(1601)
    @Test(description = "Тест открывания/закрывания карточки продукта в выдаче поиска", groups = "regression")
    public void successOperateItemCardOnSearchPage() {
        shop().goToPage();
        shop().checkSpinnerIsNotVisible();
        shop().interactHeader().clickToSelectAddress();
        shop().interactAddress().checkYmapsReady();
        shop().interactAddress().fillAddress(Addresses.Moscow.defaultAddress());
        shop().interactAddress().selectFirstAddress();
        shop().interactAddress().checkMarkerOnMapInAdviceIsNotVisible();
        shop().interactAddress().clickOnSave();
        shop().interactAddress().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().goToPage();
        shop().interactHeader().checkEnteredAddressIsVisible();
        shop().interactHeader().fillSearch("молоко");
        shop().interactHeader().clickSearchButton();
        shop().interactHeader().checkEnteredAddressIsVisible();
        search().checkAddToCartButtonVisible();
        search().refreshWithoutBasicAuth();
        search().checkAddToCartButtonVisible();
        search().openFirstProductCard();
        search().interactProductCard().checkProductCardVisible();
        search().interactProductCard().close();
        search().interactProductCard().checkProductCardIsNotVisible();
    }

    //АБ по главной отключили, нужно переписать ATST-872
    @Skip
    @CaseId(2578)
    @Test(description = "Переход в витрину магазина с главной страницы сайта", groups = "regression")
    public void successShowcaseTransitionFromMainLanding() {
        home().goToPage();
        home().clickToSetAddress();
        home().interactAddressModal().fillAddress(Addresses.Moscow.defaultAddress());
        home().interactAddressModal().selectFirstAddress();
        home().interactAddressModal().clickOnSave();
        home().clickToStoreCard();
        shop().interactHeader().checkLoginIsVisible();
    }
}
