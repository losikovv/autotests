package ru.instamart.test.reforged.stf.shopping;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
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
            description = "Тест валидации элементлов поиска",
            groups = {"sbermarket-Ui-smoke","ui-smoke-production"}
    )
    public void successValidateSearch() {
        shop().goToPage();
        shop().interactHeader().checkSearchContainerVisible();
        shop().interactHeader().checkSearchInputVisible();
        shop().interactHeader().checkSearchButtonVisible();
    }

    @CaseId(1177)
    @Story("Негативные сценарии")
    @Test (
            description = "Тест поиска по запросу, не возвращающему результатов",
            groups = {"sbermarket-Ui-smoke","ui-smoke-production"}
    )
    public void successSearchForNonexistingItem() {
        shop().goToPage();
        shop().interactHeader().fillSearch("р");
        shop().interactHeader().clickSearchButton();
        search().checkSearchProductGridNotVisible();
    }

    @CaseId(1178)
    @Story("Позитивные сценарии")
    @Test (
            description = "Тест успешного поиска товаров",
            groups = {"sbermarket-Ui-smoke","ui-smoke-production"}
    )
    public void successSearchItem() {
        shop().goToPage();
        shop().interactHeader().fillSearch("");
        shop().interactHeader().clickSearchButton();
        search().checkPageIsAvailable();
        search().checkSearchProductGridVisible();
    }

//    @CaseId(1179)
//    @Story("Позитивные сценарии")
//    @Test (
//            description = "Тест упешного поиска товаров c использованием категорийных саджестов",
//            groups = {"sbermarket-Ui-smoke","ui-smoke-production"}
//    )
//    public void successSearchItemUsingCategorySuggests() {
//        AppManager.getWebDriver().get("https://stf-kraken.k-stage.sbermarket.tech/auchan");//костыль из-за бейсик авторизации
//        Shop.Search.searchField("Сыры",false);
//        searchChecks.checkIsSearchSuggestPresent("Отсутствуют категорийные подсказки\n");
//        Shop.Search.selectCategorySuggest();
//        searchChecks.checkIsSearchResultNotEmpty("Отсутствуют результаты поиска по категорийной подсказке");
//        searchChecks.checkIsTaxonRedirectCorrect("Сыр","Отсутствуют продукты в результатах поиска по категорийной подсказке");
//    }
//
//    @Skip
//    @CaseId(1180)
//    @Story("Позитивные сценарии")
//    @Test (
//            description = "Тест упешного поиска товаров c использованием товарных саджестов",
//            groups = {"sbermarket-Ui-smoke","ui-smoke-production"}
//    )
//    public void successSearchItemUsingProductSuggests() {
//        AppManager.getWebDriver().get("https://stf-kraken.k-stage.sbermarket.tech/auchan");//костыль из-за бейсик авторизации
//        Shop.Search.searchField("Мороженое",false);
//        searchChecks.checkIsSearchSuggestPresent("Отсутствуют категорийные подсказки\n");
//        searchChecks.checkIsProductSuggestPresent("Отсутствуют товарные подсказки\n");
//        Shop.Search.selectProductSuggest();
//        baseChecks.checkIsItemCardOpen("Не открыта карточка товара из товарной подсказки\n");
//        Shop.ItemCard.close();
//    }
//
//    @CaseId(1615)
//    @Story("Негативные сценарии")
//    @Test (
//            description = "Тест поиска по очень длинному запросу, не возвращающему результатов",
//            groups = {"sbermarket-Ui-smoke","ui-smoke-production"}
//    )
//    public void successSearchItemWithLongQuery() {
//        Shop.Search.searchItem(Generate.string(1000));
//        baseChecks.checkPageIsAvailable();
//        searchChecks.checkIsSearchResultEmpty(
//                "Показаны результаты для длинного поискового запроса, не возвращающего результатов\n");
//    }
}
