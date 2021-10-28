package ru.instamart.test.reforged.stf.shopping;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.Addresses;
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

        final double firstPrice = search().returnFirstProductPrice();
        final double secondPrice = search().returnSecondProductPrice();

        search().checkPriceAscSortCorrect(firstPrice, secondPrice);
    }

    @CaseId(2590)
    @Test(
            description = "Фильтрация товаров",
            groups = {"regression"}
    )
    public void successApplyFilters() {
        shop().openSitePage(ShopUrl.DEFAULT + "c/new-molochnyie-produkty/moloko/korovie");
        search().clickToDiscountFilter();

    }

    @CaseId(2591)
    @Test(
            description = "Сортировка + фильтрация товаров",
            groups = {"regression"}
    )
    public void successApplyFiltersAndSort() {

    }

    @CaseId(2592)
    @Test(
            description = "При применении фильтра для выданных товаров блокируются другие фильтры (неприменимые к ним)",
            groups = {"regression"}
    )
    public void successApplyOtherFilters() {

    }

    @CaseId(2737)
    @Test(
            description = "Отображение алкоголя в результатах поиска при неподтверждении возраста: нажатие за пределы модального окна",
            groups = {"regression"}
    )
    public void alcoholSearchModalClose() {

    }
}
