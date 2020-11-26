package ru.instamart.tests.shopping;

import instamart.core.settings.Config;
import instamart.core.testdata.ui.Generate;
import instamart.ui.checkpoints.BaseUICheckpoints;
import instamart.ui.modules.Shop;
import instamart.ui.objectsmap.Elements;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.tests.TestBase;

public class ShoppingSearchTests extends TestBase {
    BaseUICheckpoints baseChecks = new BaseUICheckpoints();

    @BeforeMethod(alwaysRun = true,
            description ="Выполняем шаги предусловий для теста")
    public void beforeTest() {
        kraken.get().page(Config.CoreSettings.defaultRetailer);
    }

    @Test(
            description = "Тест валидации элементлов поиска",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 500
    )
    public void successValidateSearch() {
        baseChecks.checkIsElementPresent(Elements.Header.Search.container());
        baseChecks.checkIsElementPresent(Elements.Header.Search.inputField());
        baseChecks.checkIsElementPresent(Elements.Header.Search.sendButton());
    }

    @Test(
            description = "Тест отправки пустого поискового запроса",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 501
    )
    public void noSendEmptySearchRequest() {
        Shop.Search.item("");
        Assert.assertFalse(kraken.detect().isSearchResultsEmpty(),
                "Показаны результаты для пустого поискового запроса\n");
    }

    @Test (
            description = "Тест поиска по запросу, не возвращающему результатов",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 502
    )
    public void successSearchForNonexistingItem() {
        Shop.Search.nonexistingItem();

        baseChecks.checkPageIsAvailable();

        Assert.assertTrue(kraken.detect().isSearchResultsEmpty(),
                "Показаны результаты для поискового запроса, не возвращающего результатов\n");
    }

    @Test (
            description = "Тест упешного поиска товаров",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 503
    )
    public void successSearchItem() {
        Shop.Search.item("шоколад");

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
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 504
    )
    public void successSearchItemUsingCategorySuggests() {
        Shop.Search.Field.fill("Мороженое");

        Assert.assertTrue(kraken.detect().isSearchCategorySuggestsPresent(),
                "Отсутствуют категорийные подсказки\n");

        Shop.Search.CategorySuggest.hit();

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
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 505
    )
    public void successSearchItemUsingProductSuggests() {
        kraken.get().page(Config.CoreSettings.defaultRetailer);
        Shop.Search.Field.fill("Мороженое");

        Assert.assertTrue(kraken.detect().isSearchProductSuggestsPresent(),
                "Отсутствуют товарные подсказки\n");

        Shop.Search.ProductSuggest.hit();

        Assert.assertTrue(kraken.detect().isItemCardOpen(),
                "Не открыта карточка товара из товарной подсказки\n");
    }

    @Test (
            description = "Тест поиска по очень длинному запросу, не возвращающему результатов",
            groups = {"sbermarket-regression"},
            priority = 506
    )
    public void successSearchItemWithLongQuery() {
        Shop.Search.item(Generate.string(1000));

        baseChecks.checkPageIsAvailable();

        Assert.assertTrue(kraken.detect().isSearchResultsEmpty(),
                "Показаны результаты для длинного поискового запроса, не возвращающего результатов\n");
    }
}
