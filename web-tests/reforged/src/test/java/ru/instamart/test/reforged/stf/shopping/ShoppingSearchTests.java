package ru.instamart.test.reforged.stf.shopping;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.kraken.testdata.Generate;
import ru.instamart.kraken.testdata.lib.Addresses;
import ru.instamart.reforged.core.enums.ShopUrl;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.stf.page.StfRouter.search;
import static ru.instamart.reforged.stf.page.StfRouter.shop;

@Epic("STF UI")
@Feature("Поиск товаров")
public final class ShoppingSearchTests extends BaseTest {

    @CaseId(1609)
    @Story("Проверка наличия элементов")
    @Test(description = "Тест валидации элементов поиска", groups = "regression")
    public void successValidateSearch() {
        shop().goToPage();
        shop().interactHeader().checkSearchContainerVisible();
        shop().interactHeader().checkSearchInputVisible();
        shop().interactHeader().checkSearchButtonVisible();
    }

    @CaseId(1177)
    @Story("Негативные сценарии")
    @Test(description = "Тест поиска по запросу, не возвращающему результатов", groups = "regression")
    public void successSearchForNonExistingItem() {
        shop().goToPage();
        shop().interactHeader().fillSearch("смысл жизни");
        shop().interactHeader().clickSearchButton();
        search().checkEmptySearchPlaceholderVisible();
    }

    @CaseId(1178)
    @Story("Позитивные сценарии")
    @Test(description = "Тест успешного поиска товаров", groups = "regression")
    public void successSearchItem() {
        shop().goToPage();
        shop().interactHeader().fillSearch("молоко");
        shop().interactHeader().clickSearchButton();
        search().checkPageIsAvailable();
        search().checkSearchProductGridVisible();
    }

    @CaseId(1179)
    @Story("Позитивные сценарии")
    @Test(description = "Тест успешного поиска товаров c использованием категорийных саджестов", groups = {"smoke", "regression"})
    public void successSearchItemUsingCategorySuggests() {
        shop().goToPage();
        shop().interactHeader().fillSearch("сыры");
        shop().interactHeader().checkTaxonCategoriesVisible();
        shop().interactHeader().clickOnFirstTaxonCategory();
        search().checkPageIsAvailable();
        search().checkTaxonTitle("Сыры");
    }

    @CaseId(2588)
    @Story("Позитивные сценарии")
    @Test(
            description = "Тест успешного поиска товаров c использованием товарных саджестов",
            groups = {"sbermarket-Ui-smoke", "ui-smoke-production"}
    )
    public void successSearchItemUsingSuggests() {
        shop().goToPage();
        shop().interactHeader().fillSearch("шоколад");
        shop().interactHeader().checkSearchSuggestsVisible();
        shop().interactHeader().clickOnFirstSearchSuggest();
        search().interactProductCard().checkProductCardVisible();
    }

    @CaseId(1615)
    @Story("Негативные сценарии")
    @Test(description = "Тест поиска по очень длинному запросу, не возвращающему результатов", groups = "regression")
    public void successSearchItemWithLongQuery() {
        shop().goToPage();
        shop().interactHeader().fillSearch(Generate.string(1000));
        shop().interactHeader().clickSearchButton();
        search().checkEmptySearchPlaceholderVisible();
    }

    @CaseId(1581)
    @Test(description = "Добавление товара в корзину из поиска товаров", groups = "regression")
    public void successAddItemToCartFromSearchResults() {
        shop().goToPage();
        shop().interactHeader().clickToSelectAddressFirstTime();
        shop().interactAddress().checkYmapsReady();
        shop().interactAddress().setAddress(Addresses.Moscow.defaultAddress());
        shop().interactAddress().selectFirstAddress();
        shop().interactAddress().checkMarkerOnMapInAdviceIsNotVisible();
        shop().interactAddress().clickOnSave();
        shop().interactAddress().checkAddressModalIsNotVisible();

        shop().interactHeader().fillSearch("сыры");
        shop().interactHeader().checkTaxonCategoriesVisible();
        shop().interactHeader().clickOnFirstTaxonCategory();
        search().checkPageIsAvailable();
        search().checkTaxonTitle("Сыры");
        search().clickAddToCartFirstSearchResult();
        search().interactHeader().clickToCart();
        search().interactCart().checkCartOpen();
        search().interactCart().checkCartNotEmpty();
    }


    @CaseId(2589)
    @Test(
            description = "Работоспособность сортировки товаров",
            groups = {"regression"}
    )
    public void successApplySort() {
        shop().goToPage();
        shop().checkSpinnerIsNotVisible();
        shop().interactHeader().clickToSelectAddress();
        shop().interactAddress().checkYmapsReady();
        shop().interactAddress().setAddress(Addresses.Moscow.defaultAddress());
        shop().interactAddress().selectFirstAddress();
        shop().interactAddress().checkMarkerOnMapInAdviceIsNotVisible();
        shop().interactAddress().clickOnSave();
        shop().interactAddress().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().goToPage();
        shop().interactHeader().checkEnteredAddressIsVisible();
        shop().interactHeader().fillSearch("кофе");
        shop().interactHeader().clickSearchButton();
        shop().interactHeader().checkEnteredAddressIsVisible();

        search().selectSort("Сначала дешевые");
        search().checkSearchProductsSkeletonVisible();
        search().checkSearchProductsSkeletonNotVisible();

        final double firstPrice = search().returnProductPrice(0);
        final double secondPrice = search().returnProductPrice(1);

        search().checkPriceAscSortCorrect(firstPrice, secondPrice);
        search().assertAll();
    }

    @CaseId(2590)
    @Test(
            description = "Фильтрация товаров",
            groups = {"regression"}
    )
    public void successApplyFilters() {
        shop().openSitePage(ShopUrl.METRO.getUrl() + "/c/new-molochnyie-produkty/moloko/korovie");

        search().checkSearchProductsSpinnerVisible();
        search().checkSearchProductsSpinnerNotVisible();

        final double startProductsQuantity = search().returnSearchProductsQuantity();

        search().clickToDiscountFilter();
        search().checkSearchProductsSkeletonVisible();
        search().checkSearchProductsSkeletonNotVisible();

        final double discountFilterProductsQuantity = search().returnSearchProductsQuantity();

        search().checkQuantitiesNotEquals(startProductsQuantity, discountFilterProductsQuantity);
        search().clickOnFilter("Ультрапастеризованное");

        search().checkSearchProductsSkeletonVisible();
        search().checkSearchProductsSkeletonNotVisible();

        final double someFilterProductsQuantity = search().returnSearchProductsQuantity();
        search().checkQuantitiesNotEquals(discountFilterProductsQuantity, someFilterProductsQuantity);

        search().assertAll();
    }

    @CaseId(2591)
    @Test(
            description = "Сортировка + фильтрация товаров",
            groups = {"regression"}
    )
    public void successApplyFiltersAndSort() {
        double ascFirstPrice, ascSecondPrice,
               descFirstPrice, descSecondPrice;
        shop().goToPage();
        shop().interactHeader().fillSearch("кофе");
        shop().interactHeader().clickSearchButton();

        search().checkSearchProductsSpinnerVisible();
        search().checkSearchProductsSpinnerNotVisible();

        search().selectSort("Сначала дешевые");

        ascFirstPrice = search().returnProductPrice(0);
        ascSecondPrice = search().returnProductPrice(1);

        search().checkPriceAscSortCorrect(ascFirstPrice, ascSecondPrice);

        search().scrollDown();

        search().checkPageIsAvailable();

        search().scrollUp();

        search().selectSort("По популярности");

        search().scrollDown();
        search().checkPageIsAvailable();

        search().scrollUp();

        search().selectSort("Сначала дорогие");

        descFirstPrice = search().returnProductPrice(0);
        descSecondPrice = search().returnProductPrice(1);

        search().checkPriceDescSortCorrect(descFirstPrice, descSecondPrice);

        search().scrollDown();
        search().checkPageIsAvailable();

        search().scrollUp();

        search().clickToDiscountFilter();

        descFirstPrice = search().returnProductPrice(0);
        descSecondPrice = search().returnProductPrice(1);

        search().checkPriceDescSortCorrect(descFirstPrice, descSecondPrice);

        search().clickOnFilter("Jacobs");
        search().checkSearchProductsSkeletonVisible();
        search().checkSearchProductsSkeletonNotVisible();

        search().selectSort("По популярности");

        search().scrollDown();
        search().checkPageIsAvailable();
    }

    @CaseId(2592)
    @Test(
            description = "При применении фильтра для выданных товаров блокируются другие фильтры (неприменимые к ним)",
            groups = {"regression"}
    )
    public void successApplyOtherFilters() {
        shop().openSitePage(ShopUrl.METRO.getUrl() + "/c/new-molochnyie-produkty/moloko/korovie");

        search().checkSearchProductsSpinnerVisible();
        search().checkSearchProductsSpinnerNotVisible();

        search().clickToDiscountFilter();
        search().checkSearchProductsSkeletonVisible();
        search().checkSearchProductsSkeletonNotVisible();

        search().checkFilterDisabled("Стерилизованное");
    }

    @CaseId(2737)
    @Test(
            description = "Отображение алкоголя в результатах поиска при неподтверждении возраста: нажатие за пределы модального окна",
            groups = {"regression"}
    )
    public void alcoholSearchModalClose() {
        shop().goToPage();
        shop().checkSpinnerIsNotVisible();
        shop().interactHeader().clickToSelectAddress();
        shop().interactAddress().checkYmapsReady();
        shop().interactAddress().setAddress(Addresses.Moscow.defaultAddress());
        shop().interactAddress().selectFirstAddress();
        shop().interactAddress().checkMarkerOnMapInAdviceIsNotVisible();
        shop().interactAddress().clickOnSave();
        shop().interactAddress().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().goToPage();
        shop().interactHeader().fillSearch("вино");
        shop().interactHeader().checkTaxonCategoriesVisible();
        search().interactHeader().checkAlcoStubInSuggest();
        search().interactHeader().checkAlcoStubInCategories();
        search().interactHeader().clickSearchButton();

        search().checkSearchProductGridVisible();
        search().checkSearchProductsSkeletonNotVisible();
        search().checkSearchProductsSpinnerNotVisible();

        search().checkAlcoStubInProductsSearch();
        search().clickAddToCartFirstSearchResult();
        search().interactProductCard().checkProductCardVisible();

        search().interactProductCard().checkAlcoAlertVisible();
        search().interactProductCard().checkReserveButtonVisible();
        search().interactProductCard().clickOnReserve();

        search().interactDisclaimerModal().checkApproveButtonVisible();
        search().interactProductCard().addToFavoriteToCloseDisclaimer();

        search().interactProductCard().checkAlcoStubVisible();
        search().interactProductCard().close();

        search().checkAlcoStubInProductsSearch();
        search().clickAddToCartFirstSearchResult();
        search().interactProductCard().checkProductCardVisible();
        search().interactProductCard().checkAlcoStubVisible();
    }
}
