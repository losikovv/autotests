package ru.instamart.test.ui.shopping;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.BeforeClass;
import ru.instamart.kraken.setting.Config;
import ru.instamart.kraken.testdata.TestVariables;
import ru.instamart.kraken.testdata.Generate;
import ru.instamart.test.ui.TestBase;
import ru.instamart.ui.checkpoint.BaseUICheckpoints;
import ru.instamart.ui.checkpoint.search.SearchResultCheckpoints;
import ru.instamart.ui.module.Shop;
import ru.instamart.ui.Elements;
import org.testng.annotations.Test;

@Epic("STF UI")
@Feature("Поиск товаров")
public class ShoppingSearchTests extends TestBase {
    BaseUICheckpoints baseChecks = new BaseUICheckpoints();
    SearchResultCheckpoints searchChecks = new SearchResultCheckpoints();

    @BeforeClass(alwaysRun = true,
            description ="Выполняем шаги предусловий для теста")
    public void beforeTest() {
        kraken.get().page(Config.DEFAULT_RETAILER);
    }

    @CaseId(1609)
    @Story("Проверка наличия элементов")
    @Test(
            description = "Тест валидации элементлов поиска",
            groups = {"sbermarket-Ui-smoke","ui-smoke-production"}
    )
    public void successValidateSearch() {
        baseChecks.checkIsElementPresent(Elements.Header.Search.container());
        baseChecks.checkIsElementPresent(Elements.Header.Search.inputField());
        baseChecks.checkIsElementPresent(Elements.Header.Search.sendButton());
    }

    @CaseId(1177)
    @Story("Негативные сценарии")
    @Test (
            description = "Тест поиска по запросу, не возвращающему результатов",
            groups = {"sbermarket-Ui-smoke","ui-smoke-production"}
    )
    public void successSearchForNonexistingItem() {
        Shop.Search.searchItem(TestVariables.TestParams.ItemSearch.emptyResultsQuery);
        baseChecks.checkPageIsAvailable();
        searchChecks.checkIsSearchResultEmpty(
                "Показаны результаты для поискового запроса, не возвращающего результатов\n");
    }

    @CaseId(1178)
    @Story("Позитивные сценарии")
    @Test (
            description = "Тест упешного поиска товаров",
            groups = {"sbermarket-Ui-smoke","ui-smoke-production"}
    )
    public void successSearchItem() {
        Shop.Search.searchItem("шоколад");
        baseChecks.checkPageIsAvailable();
        searchChecks.checkIsSearchResultNotEmpty("Отсутствуют результаты поиска");
        searchChecks.checkIsProductAvailable("Отсутствуют продукты в результатах поиска");
    }

    @CaseId(1179)
    @Story("Позитивные сценарии")
    @Test (
            description = "Тест упешного поиска товаров c использованием категорийных саджестов",
            groups = {"sbermarket-Ui-smoke","ui-smoke-production"}
    )
    public void successSearchItemUsingCategorySuggests() {
        kraken.getWebDriver().get("https://stf-kraken.k-stage.sbermarket.tech/auchan");//костыль из-за бейсик авторизации
        Shop.Search.searchField("Сыры",false);
        searchChecks.checkIsSearchSuggestPresent("Отсутствуют категорийные подсказки\n");
        Shop.Search.selectCategorySuggest();
        searchChecks.checkIsSearchResultNotEmpty("Отсутствуют результаты поиска по категорийной подсказке");
        searchChecks.checkIsTaxonRedirectCorrect("Сыр","Отсутствуют продукты в результатах поиска по категорийной подсказке");
    }

    @CaseId(1180)
    @Story("Позитивные сценарии")
    @Test (
            description = "Тест упешного поиска товаров c использованием товарных саджестов",
            groups = {"sbermarket-Ui-smoke","ui-smoke-production"}
    )
    public void successSearchItemUsingProductSuggests() {
        kraken.getWebDriver().get("https://stf-kraken.k-stage.sbermarket.tech/auchan");//костыль из-за бейсик авторизации
        Shop.Search.searchField("Мороженое",false);
        searchChecks.checkIsSearchSuggestPresent("Отсутствуют категорийные подсказки\n");
        searchChecks.checkIsProductSuggestPresent("Отсутствуют товарные подсказки\n");
        Shop.Search.selectProductSuggest();
        baseChecks.checkIsItemCardOpen("Не открыта карточка товара из товарной подсказки\n");
        Shop.ItemCard.close();
    }

    @CaseId(1615)
    @Story("Негативные сценарии")
    @Test (
            description = "Тест поиска по очень длинному запросу, не возвращающему результатов",
            groups = {"sbermarket-Ui-smoke","ui-smoke-production"}
    )
    public void successSearchItemWithLongQuery() {
        Shop.Search.searchItem(Generate.string(1000));
        baseChecks.checkPageIsAvailable();
        searchChecks.checkIsSearchResultEmpty(
                "Показаны результаты для длинного поискового запроса, не возвращающего результатов\n");
    }
}
