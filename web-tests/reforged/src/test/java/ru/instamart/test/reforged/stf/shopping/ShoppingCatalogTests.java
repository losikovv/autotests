package ru.instamart.test.reforged.stf.shopping;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Test;
import ru.instamart.kraken.data.Addresses;
import ru.instamart.reforged.core.annotation.CookieProvider;
import ru.instamart.reforged.core.enums.ShopUrl;

import static ru.instamart.reforged.Group.REGRESSION_STF;
import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Проверка каталога товаров")
public final class ShoppingCatalogTests {

    @TmsLink("1595")
    @Test(description = "Тест работы cо шторкой каталога", groups = REGRESSION_STF)
    public void successValidateCatalogDrawer() {
        shop().goToPage();

        shop().interactHeader().clickToSelectAddress();
        shop().interactAddressLarge().checkYmapsReady();
        shop().interactAddressLarge().fillAddress(Addresses.Moscow.defaultAddress());
        shop().interactAddressLarge().selectFirstAddress();
        shop().interactAddressLarge().checkMarkerOnMapInAdviceIsNotVisible();
        shop().interactAddressLarge().clickSave();
        shop().interactAddressLarge().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().interactHeader().clickToCategoryMenu();
        shop().interactCategoryMenu().checkCatalogMenuIsOpen();
        shop().interactCategoryMenu().checkFirstLevelElementsVisible();
        shop().interactCategoryMenu().clickToClose();
    }

    @TmsLink("2583")
    @Test(description = "Тест открытия категории 1 уровня из шторки каталога", groups = {"MRAutoCheck", REGRESSION_STF})
    public void successGoToDepartmentFromCatalogDrawer() {
        shop().goToPage();

        shop().interactHeader().clickToSelectAddress();
        shop().interactAddressLarge().checkYmapsReady();
        shop().interactAddressLarge().fillAddress(Addresses.Moscow.defaultAddress());
        shop().interactAddressLarge().selectFirstAddress();
        shop().interactAddressLarge().checkMarkerOnMapInAdviceIsNotVisible();
        shop().interactAddressLarge().clickSave();
        shop().interactAddressLarge().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().goToPage();
        shop().interactHeader().clickToCategoryMenu();
        shop().interactCategoryMenu().checkCatalogMenuIsOpen();
        shop().interactCategoryMenu().clickToFirstLevelCategoryByName("Бакалея");

        seo().checkCatalogTitleVisible();
        seo().checkPageIsAvailable();
    }

    @TmsLink("2584")
    @Test(description = "Тест открытия категории 2 уровня из шторки каталога", groups = REGRESSION_STF)
    public void successGoToTaxonFromCatalogDrawer() {
        shop().goToPage();

        shop().interactHeader().clickToSelectAddress();
        shop().interactAddressLarge().checkYmapsReady();
        shop().interactAddressLarge().fillAddress(Addresses.Moscow.defaultAddress());
        shop().interactAddressLarge().selectFirstAddress();
        shop().interactAddressLarge().checkMarkerOnMapInAdviceIsNotVisible();
        shop().interactAddressLarge().clickSave();
        shop().interactAddressLarge().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();
        shop().goToPage();

        shop().interactHeader().clickToCategoryMenu();
        shop().interactCategoryMenu().checkCatalogMenuIsOpen();
        shop().interactCategoryMenu().moveOnFirstLevelCategory("Молочные продукты");
        shop().interactCategoryMenu().clickToSecondLevelCategoryByName("Молоко");

        seo().checkCatalogTitleVisible();
        seo().checkPageIsAvailable();
    }

    @TmsLink("2582")
    @Test(description = "Тест открывания/закрывания карточки продукта на главной", groups = REGRESSION_STF)
    public void successOperateItemCardOnRetailerPage() {
        shop().goToPage();
        shop().interactHeader().clickToSelectAddress();
        shop().interactAddressLarge().checkYmapsReady();
        shop().interactAddressLarge().fillAddress(Addresses.Moscow.defaultAddress());
        shop().interactAddressLarge().selectFirstAddress();
        shop().interactAddressLarge().checkMarkerOnMapInAdviceIsNotVisible();
        shop().interactAddressLarge().clickSave();
        shop().interactAddressLarge().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().goToPage();
        shop().openFirstNonRecommendationsProductCard();
        shop().interactProductCard().checkProductCardVisible();
        shop().interactProductCard().clickOnClose();
        shop().interactProductCard().checkProductCardIsNotVisible();
    }

    @TmsLink("1599")
    @Test(description = "Тест открывания/закрывания карточки продукта в department-категории", groups = REGRESSION_STF)
    public void successOperateItemCardOnDepartmentPage() {
        shop().goToPage();
        shop().interactHeader().clickToSelectAddress();
        shop().interactAddressLarge().checkYmapsReady();
        shop().interactAddressLarge().fillAddress(Addresses.Moscow.defaultAddress());
        shop().interactAddressLarge().selectFirstAddress();
        shop().interactAddressLarge().checkMarkerOnMapInAdviceIsNotVisible();
        shop().interactAddressLarge().clickSave();
        shop().interactAddressLarge().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();
        shop().goToPage();

        shop().interactHeader().clickToCategoryMenu();
        shop().interactCategoryMenu().checkCatalogMenuIsOpen();
        shop().interactCategoryMenu().clickToFirstLevelCategoryByName("Бакалея");

        seo().checkCatalogTitleVisible();
        seo().checkSpinnerIsNotVisible();

        seo().openFirstProductCardOnDepartment();
        seo().interactProductCard().checkProductCardVisible();
        seo().interactProductCard().clickOnClose();
        seo().interactProductCard().checkProductCardIsNotVisible();
    }

    @TmsLink("1600")
    @Test(description = "Тест открывания/закрывания карточки продукта в taxon-категории", groups = REGRESSION_STF)
    public void successOperateItemCardOnTaxonPage() {
        shop().goToPage();
        shop().interactHeader().clickToSelectAddress();
        shop().interactAddressLarge().checkYmapsReady();
        shop().interactAddressLarge().fillAddress(Addresses.Moscow.defaultAddress());
        shop().interactAddressLarge().selectFirstAddress();
        shop().interactAddressLarge().checkMarkerOnMapInAdviceIsNotVisible();
        shop().interactAddressLarge().clickSave();
        shop().interactAddressLarge().checkAddressModalIsNotVisible();
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

        seo().openFirstProductCardOnTaxon();
        seo().interactProductCard().checkProductCardVisible();
        seo().interactProductCard().clickOnClose();
        seo().interactProductCard().checkProductCardIsNotVisible();
    }

    @TmsLink("1601")
    @Test(description = "Тест открывания/закрывания карточки продукта в выдаче поиска", groups = REGRESSION_STF)
    public void successOperateItemCardOnSearchPage() {
        shop().goToPage();
        shop().interactHeader().clickToSelectAddress();
        shop().interactAddressLarge().checkYmapsReady();
        shop().interactAddressLarge().fillAddress(Addresses.Moscow.defaultAddress());
        shop().interactAddressLarge().selectFirstAddress();
        shop().interactAddressLarge().checkMarkerOnMapInAdviceIsNotVisible();
        shop().interactAddressLarge().clickSave();
        shop().interactAddressLarge().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().interactHeader().fillSearch("молоко");
        shop().interactHeader().clickSearchButton();
        shop().interactHeader().checkEnteredAddressIsVisible();

        search().checkAddToCartButtonVisible();
        search().openFirstProductCard();
        search().interactProductCard().checkProductCardVisible();
        search().interactProductCard().clickOnClose();
        search().interactProductCard().checkProductCardIsNotVisible();
    }

    @TmsLink("3519")
    @Test(description = "Проверка открытия модального окна карточки товара при переходе по прямой ссылке", groups = REGRESSION_STF)
    public void openProductCardByLink() {
        shop().goToPage();
        shop().openFirstNonRecommendationsProductCard();
        shop().interactProductCard().checkProductCardVisible();

        String productLink = shop().interactProductCard().getProductPermalink();
        shop().interactProductCard().clickOnClose();
        shop().interactProductCard().checkProductCardIsNotVisible();

        shop().openProductCardByLink(ShopUrl.DEFAULT, productLink);
        shop().interactProductCard().checkProductCardVisible();
        shop().interactProductCard().clickOnClose();

        seo().checkProductGridVisible();
    }

    @TmsLink("3520")
    @Test(description = "Проверка корректного открытия карточки товара при обновлении страницы", groups = REGRESSION_STF)
    public void openProductCardAfterRefresh() {
        shop().goToPage();
        shop().openFirstNonRecommendationsProductCard();
        shop().interactProductCard().checkProductCardVisible();

        shop().refresh();
        shop().interactProductCard().checkProductCardVisible();
        shop().interactProductCard().clickOnClose();

        seo().checkProductGridVisible();
    }

    @TmsLink("2578")
    //TODO fixedUUID - костыль для обхода невыпиленного АБ-теста с новыми ЯндексКартами https://jira.sbmt.io/browse/DVR-4901
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_REFERENCE"})
    @Test(description = "Переход в витрину магазина с главной страницы сайта", groups = REGRESSION_STF)
    public void successShowcaseTransitionFromMainLanding() {
        home().goToPage();
        home().clickToSetAddress();
        home().interactAddressModal().checkYmapsReady();
        home().interactAddressModal().fillAddress(Addresses.Moscow.defaultAddress());
        home().interactAddressModal().selectFirstAddress();
        home().interactAddressModal().clickFindStores();
        home().interactAddressModal().checkAddressModalIsNotVisible();

        home().clickOnFirstStore();
        shop().interactHeader().checkLoginIsVisible();
    }

    @TmsLink("2593")
    @Test(description = "Корректное отображение информации о товаре (основные элементы)", groups = REGRESSION_STF)
    public void productSnippetAndCardInfo() {
        shop().goToPage();
        shop().checkItemNameDisplayed();
        shop().checkItemImageDisplayed();
        shop().checkItemPackageSizeDisplayed();

        shop().openFirstNonRecommendationsProductCard();
        shop().interactProductCard().checkBreadscrumbsVisible();
        shop().interactProductCard().checkProductImageDisplayed();
        shop().interactProductCard().checkNameDisplayed();
        shop().interactProductCard().checkPackageSizeDisplayed();
        shop().interactProductCard().checkBuyButtonVisible();
        shop().interactProductCard().checkFavoriteButtonDisplayed();
        shop().interactProductCard().checkDescriptionDisplayed();
        shop().interactProductCard().checkGeneralInfoDisplayed();
    }

    //@TmsLink("2593")
    @Test(description = "Корректное отображение информации о товаре (элементы товара без скидки)", groups = REGRESSION_STF)
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

    //@TmsLink("2593")
    @Test(description = "Корректное отображение информации о товаре (элементы товара со скидкой)", groups = REGRESSION_STF)
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
