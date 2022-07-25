package ru.instamart.test.reforged.stf.shopping;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import ru.instamart.kraken.data.Addresses;
import ru.instamart.reforged.core.CookieProvider;
import ru.instamart.reforged.core.enums.ShopUrl;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Проверка каталога товаров")
public final class ShoppingCatalogTests {

    @CaseId(1595)
    @Test(description = "Тест работы cо шторкой каталога", groups = "regression")
    public void successValidateCatalogDrawer() {
        shop().goToPage();
        shop().checkFirstProductCardIsVisible();

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
        shop().checkFirstProductCardIsVisible();

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
        shop().checkFirstProductCardIsVisible();

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

        shop().checkFirstProductCardIsVisible();
        shop().openFirstProductCard();
        shop().interactProductCard().checkProductCardVisible();
        shop().interactProductCard().clickOnClose();
        shop().interactProductCard().checkProductCardIsNotVisible();
    }

    @CaseId(1599)
    @Test(description = "Тест открывания/закрывания карточки продукта в department-категории", groups = "regression")
    public void successOperateItemCardOnDepartmentPage() {
        shop().goToPage();
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
        seo().interactProductCard().clickOnClose();
        seo().interactProductCard().checkProductCardIsNotVisible();
    }

    @CaseId(1600)
    @Test(description = "Тест открывания/закрывания карточки продукта в taxon-категории", groups = "regression")
    public void successOperateItemCardOnTaxonPage() {
        shop().goToPage();
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
        seo().interactProductCard().clickOnClose();
        seo().interactProductCard().checkProductCardIsNotVisible();
    }

    @CaseId(1601)
    @Test(description = "Тест открывания/закрывания карточки продукта в выдаче поиска", groups = "regression")
    public void successOperateItemCardOnSearchPage() {
        shop().goToPage();
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
        search().interactProductCard().clickOnClose();
        search().interactProductCard().checkProductCardIsNotVisible();
    }

    @CaseId(3519)
    @Test(description = "Проверка открытия модального окна карточки товара при переходе по прямой ссылке", groups = "regression")
    public void openProductCardByLink() {
        shop().goToPage();
        shop().checkFirstProductCardIsVisible();
        shop().openFirstProductCard();
        shop().interactProductCard().checkProductCardVisible();

        String productLink = shop().interactProductCard().getProductPermalink();
        shop().interactProductCard().clickOnClose();
        shop().interactProductCard().checkProductCardIsNotVisible();

        shop().openProductCardByLink(ShopUrl.DEFAULT, productLink);
        shop().interactProductCard().checkProductCardVisible();
        shop().interactProductCard().clickOnClose();

        seo().checkProductGridVisible();
    }

    @CaseId(3520)
    @Test(description = "Проверка корректного открытия карточки товара при обновлении страницы", groups = "regression")
    public void openProductCardAfterRefresh() {
        shop().goToPage();
        shop().checkFirstProductCardIsVisible();
        shop().openFirstProductCard();
        shop().interactProductCard().checkProductCardVisible();

        shop().refresh();
        shop().interactProductCard().checkProductCardVisible();
        shop().interactProductCard().clickOnClose();

        seo().checkProductGridVisible();
    }

    @CaseId(2578)
    //TODO fixedUUID - костыль для обхода невыпиленного АБ-теста с новыми ЯндексКартами https://jira.sbmt.io/browse/DVR-4901
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_REFERENCE"})
    @Test(description = "Переход в витрину магазина с главной страницы сайта", groups = "regression")
    public void successShowcaseTransitionFromMainLanding() {
        home().goToPage();
        home().clickToSetAddress();
        home().interactAddressModal().checkYmapsReady();
        home().interactAddressModal().fillAddress(Addresses.Moscow.defaultAddress());
        home().interactAddressModal().selectFirstAddress();
        home().interactAddressModal().clickFindStores();
        home().interactAddressModal().checkAddressModalNotVisible();

        home().clickOnFirstStore();
        shop().interactHeader().checkLoginIsVisible();
    }

    @CaseId(2593)
    @Test(description = "Корректное отображение информации о товаре (основные элементы)", groups = "regression")
    public void productSnippetAndCardInfo() {
        shop().goToPage();
        shop().checkItemNameDisplayed();
        shop().checkItemImageDisplayed();
        shop().checkItemPackageSizeDisplayed();

        shop().openFirstProductCard();
        shop().interactProductCard().checkBreadscrumbsVisible();
        shop().interactProductCard().checkProductImageDisplayed();
        shop().interactProductCard().checkNameDisplayed();
        shop().interactProductCard().checkPackageSizeDisplayed();
        shop().interactProductCard().checkBuyButton();
        shop().interactProductCard().checkFavoriteButtonDisplayed();
        shop().interactProductCard().checkDescriptionDisplayed();
        shop().interactProductCard().checkGeneralInfoDisplayed();
    }

    //@CaseId(2593)
    @Test(description = "Корректное отображение информации о товаре (элементы товара без скидки)", groups = "regression")
    public void productSnippetAndCardInfoItemWithoutDiscount() {
        shop().goToPage();
        shop().checkSnippet();
        shop().checkItemWithoutDiscountPricesCount();
        var snippetPrice = shop().getPrice();

        shop().openFirstProductCardWithoutDiscount();
        shop().interactProductCard().checkProductCardVisible();
        shop().interactProductCard().checkDiscountLabelNotDisplayed();
        shop().interactProductCard().checkItemWithoutDiscountPricesCount();
        shop().interactProductCard().checkFullPrice(snippetPrice, shop().interactProductCard().getPrice());
    }

    //@CaseId(2593)
    @Test(description = "Корректное отображение информации о товаре (элементы товара со скидкой)", groups = "regression")
    public void productSnippetAndCardInfoItemWithDiscount() {
        shop().goToPage();
        shop().checkSnippet();
        shop().checkItemWithDiscountPricesCount();
        var snippetPriceWithoutDiscount = shop().getFullPrice();
        var snippetPriceWithDiscount = shop().getPriceWithDiscount();

        shop().openFirstProductCardWithDiscount();
        shop().interactProductCard().checkProductCardVisible();
        shop().interactProductCard().checkDiscountLabelDisplayed();
        shop().interactProductCard().checkItemWithDiscountPricesCount();
        shop().interactProductCard().checkFullPrice(snippetPriceWithoutDiscount, shop().interactProductCard().getPrice());
        shop().interactProductCard().checkDiscountPrice(snippetPriceWithDiscount, shop().interactProductCard().getPriceWithDiscount());
    }
}
