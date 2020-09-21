package ru.instamart.tests.shopping;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import instamart.ui.objectsmap.Elements;
import instamart.ui.modules.Shop;
import instamart.core.testdata.ui.generate;
import ru.instamart.tests.TestBase;

public class ShoppingSearchTests extends TestBase {

    @BeforeMethod(alwaysRun = true)
    public void preconditions() {
        kraken.get().page("metro");
    }

    @Test(
            description = "Тест валидации элементлов поиска",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 500
    )
    public void successValidateSearch() {
        assertPresence(Elements.Header.Search.container());
        assertPresence(Elements.Header.Search.inputField());
        assertPresence(Elements.Header.Search.sendButton());
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
        Shop.Search.item("говно жопа");

        assertPageIsAvailable();

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

        assertPageIsAvailable();

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
        kraken.get().page("metro");
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
        Shop.Search.item(generate.string(1000));

        assertPageIsAvailable();

        Assert.assertTrue(kraken.detect().isSearchResultsEmpty(),
                "Показаны результаты для длинного поискового запроса, не возвращающего результатов\n");
    }
}
