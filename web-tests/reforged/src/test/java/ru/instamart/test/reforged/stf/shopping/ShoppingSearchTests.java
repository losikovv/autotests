package ru.instamart.test.reforged.stf.shopping;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.kraken.testdata.Generate;
import ru.instamart.kraken.testdata.lib.Addresses;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.stf.page.StfRouter.search;
import static ru.instamart.reforged.stf.page.StfRouter.shop;

@Epic("STF UI")
@Feature("Поиск товаров")
public final class ShoppingSearchTests extends BaseTest {

    @CaseId(1609)
    @Story("Проверка наличия элементов")
    @Test(
            description = "Тест валидации элементов поиска",
            groups = {"sbermarket-Ui-smoke", "ui-smoke-production"}
    )
    public void successValidateSearch() {
        shop().goToPage();
        shop().interactHeader().checkSearchContainerVisible();
        shop().interactHeader().checkSearchInputVisible();
        shop().interactHeader().checkSearchButtonVisible();
    }

    @CaseId(1177)
    @Story("Негативные сценарии")
    @Test(
            description = "Тест поиска по запросу, не возвращающему результатов",
            groups = {"sbermarket-Ui-smoke", "ui-smoke-production"}
    )
    public void successSearchForNonexistingItem() {
        shop().goToPage();
        shop().interactHeader().fillSearch("смысл жизни");
        shop().interactHeader().clickSearchButton();
        search().checkEmptySearchPlaceholderVisible();
    }

    @CaseId(1178)
    @Story("Позитивные сценарии")
    @Test(
            description = "Тест успешного поиска товаров",
            groups = {"sbermarket-Ui-smoke", "ui-smoke-production"}
    )
    public void successSearchItem() {
        shop().goToPage();
        shop().interactHeader().fillSearch("молоко");
        shop().interactHeader().clickSearchButton();
        search().checkPageIsAvailable();
        search().checkSearchProductGridVisible();
    }

    @CaseId(1179)
    @Story("Позитивные сценарии")
    @Test(
            description = "Тест успешного поиска товаров c использованием категорийных саджестов",
            groups = {"sbermarket-Ui-smoke", "ui-smoke-production"}
    )
    public void successSearchItemUsingCategorySuggests() {
        shop().goToPage();
        shop().interactHeader().fillSearch("сыры");
        shop().interactHeader().checkTaxonCategoriesVisible();
        shop().interactHeader().clickOnFirstTaxonCategory();
        search().checkPageIsAvailable();
        search().checkTaxonTitle("Сыры");
    }

    @CaseId(1615)
    @Story("Негативные сценарии")
    @Test(
            description = "Тест поиска по очень длинному запросу, не возвращающему результатов",
            groups = {"sbermarket-Ui-smoke", "ui-smoke-production"}
    )
    public void successSearchItemWithLongQuery() {
        shop().goToPage();
        shop().interactHeader().fillSearch(Generate.string(1000));
        shop().interactHeader().clickSearchButton();
        search().checkEmptySearchPlaceholderVisible();
    }

    @CaseId(1581)
    @Test(
            description = "Добавление товара в корзину из поиска товаров",
            groups = {"sbermarket-regression"}
    )
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
}
