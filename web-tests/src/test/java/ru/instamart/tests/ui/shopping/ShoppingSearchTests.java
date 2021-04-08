package ru.instamart.tests.ui.shopping;

import ru.instamart.core.settings.Config;
import ru.instamart.core.testdata.ui.Generate;
import ru.instamart.ui.checkpoints.BaseUICheckpoints;
import ru.instamart.ui.modules.Shop;
import ru.instamart.ui.objectsmap.Elements;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.tests.ui.TestBase;

public class ShoppingSearchTests extends TestBase {
    BaseUICheckpoints baseChecks = new BaseUICheckpoints();

    @BeforeMethod(alwaysRun = true,
            description ="Выполняем шаги предусловий для теста")
    public void beforeTest() {
        kraken.get().page(Config.DEFAULT_RETAILER);
    }

    @Test(
            description = "Тест валидации элементлов поиска",
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    )
    public void successValidateSearch() {
        baseChecks.checkIsElementPresent(Elements.Header.Search.container());
        baseChecks.checkIsElementPresent(Elements.Header.Search.inputField());
        baseChecks.checkIsElementPresent(Elements.Header.Search.sendButton());
    }

    @Test(
            description = "Тест отправки пустого поискового запроса",
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    )
    public void noSendEmptySearchRequest() {
        Shop.Search.searchItem("");
        Assert.assertFalse(kraken.detect().isSearchResultsEmpty(),
                "Показаны результаты для пустого поискового запроса\n");
    }

    @Test (
            description = "Тест поиска по запросу, не возвращающему результатов",
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    )
    public void successSearchForNonexistingItem() {
        Shop.Search.nonExistingItem();

        baseChecks.checkPageIsAvailable();

        Assert.assertTrue(kraken.detect().isSearchResultsEmpty(),
                "Показаны результаты для поискового запроса, не возвращающего результатов\n");
    }

    @Test (
            description = "Тест упешного поиска товаров",
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    )
    public void successSearchItem() {
        Shop.Search.searchItem("шоколад");

        baseChecks.checkPageIsAvailable();

        Assert.assertFalse(
                kraken.detect().isSearchResultsEmpty(),
                    failMessage("Отсутствуют результаты поиска")
        );

        Assert.assertTrue(
                kraken.detect().isProductAvailable(),
                    failMessage("Отсутствуют продукты в результатах поиска")
        );
    }

    @Test (
            description = "Тест упешного поиска товаров c использованием категорийных саджестов",
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    )
    public void successSearchItemUsingCategorySuggests() {
        Shop.Search.searchField("Мороженое");

        Assert.assertTrue(kraken.detect().isSearchCategorySuggestsPresent(),
                "Отсутствуют категорийные подсказки\n");

        Shop.Search.selectCategorySuggest();

        Assert.assertFalse(
                kraken.detect().isSearchResultsEmpty(),
                    failMessage("Отсутствуют результаты поиска по категорийной подсказке")
        );

        Assert.assertTrue(
                kraken.detect().isProductAvailable(),
                    failMessage("Отсутствуют продукты в результатах поиска по категорийной подсказке")
        );
    }

    @Test (
            description = "Тест упешного поиска товаров c использованием товарных саджестов",
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    )
    public void successSearchItemUsingProductSuggests() {
        kraken.get().page(Config.DEFAULT_RETAILER);
        Shop.Search.searchField("Мороженое");

        Assert.assertTrue(kraken.detect().isSearchProductSuggestsPresent(),
                "Отсутствуют товарные подсказки\n");

        Shop.Search.selectProductSuggest();

        Assert.assertTrue(kraken.detect().isItemCardOpen(),
                "Не открыта карточка товара из товарной подсказки\n");
    }

    @Test (
            description = "Тест поиска по очень длинному запросу, не возвращающему результатов",
            groups = {"sbermarket-regression"}
    )
    public void successSearchItemWithLongQuery() {
        Shop.Search.searchItem(Generate.string(1000));

        baseChecks.checkPageIsAvailable();

        Assert.assertTrue(kraken.detect().isSearchResultsEmpty(),
                "Показаны результаты для длинного поискового запроса, не возвращающего результатов\n");
    }
}
