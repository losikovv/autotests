package ru.instamart.autotests.tests.shopping;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.autotests.testdata.generate;
import ru.instamart.autotests.tests.TestBase;

public class ShoppingSearchTests extends TestBase {

    @BeforeMethod(alwaysRun = true)
    public void preconditions() {
        kraken.get().page("metro");
    }

    @Test(
            description = "Тест отправки пустого поискового запроса",
            groups = {"acceptance","regression"},
            priority = 501
    )
    public void noSendEmptySearchRequest() {
        kraken.search().item("");

        Assert.assertFalse(kraken.detect().isSearchResultsEmpty(),
                "Показаны результаты для пустого поискового запроса\n");
    }

    @Test (
            description = "Тест поиска по запросу, не возвращающему результатов",
            groups = {"acceptance","regression"},
            priority = 502
    )
    public void successSearchForNonexistingItem() {
        kraken.search().item("говно жопа");

        assertPageIsAvailable();

        Assert.assertTrue(kraken.detect().isSearchResultsEmpty(),
                "Показаны результаты для поискового запроса, не возвращающего результатов\n");
    }

    @Test (
            description = "Тест упешного поиска товаров",
            groups = {"acceptance","regression"},
            priority = 503
    )
    public void successSearchItem() {
        kraken.search().item("шоколад");

        assertPageIsAvailable();

        Assert.assertFalse(kraken.detect().isSearchResultsEmpty(),
                "Отсутствуют результаты поиска\n");

        Assert.assertTrue(kraken.detect().isProductAvailable(),
                "Отсутствуют продукты в результатах поиска\n");
    }

    @Test (
            description = "Тест упешного поиска товаров c использованием категорийных саджестов",
            groups = {"acceptance","regression"},
            priority = 504
    )
    public void successSearchItemUsingCategorySuggests() {
        kraken.search().fillSearchFieldWith("Мороженое");

        Assert.assertTrue(kraken.detect().isSearchCategorySuggestsPresent(),
                "Отсутствуют категорийные подсказки\n");

        kraken.search().hitCategorySuggest();

        Assert.assertFalse(kraken.detect().isSearchResultsEmpty(),
                "Отсутствуют результаты поиска по категорийной подсказке\n");

        Assert.assertTrue(kraken.detect().isProductAvailable(),
                "Отсутствуют продукты в результатах поиска по категорийной подсказке\n");
    }

    @Test (
            description = "Тест упешного поиска товаров c использованием товарных саджестов",
            groups = {"acceptance","regression"},
            priority = 505
    )
    public void successSearchItemUsingProductSuggests() {
        kraken.get().page("metro");
        kraken.search().fillSearchFieldWith("Мороженое");

        Assert.assertTrue(kraken.detect().isSearchProductSuggestsPresent(),
                "Отсутствуют товарные подсказки\n");

        kraken.search().hitProductSuggest();

        Assert.assertTrue(kraken.detect().isItemCardOpen(),
                "Не открыта карточка товара из товарной подсказки\n");
    }

    @Test (
            description = "Тест поиска по очень длинному запросу, не возвращающему результатов",
            groups = {"regression"},
            priority = 506
    )
    public void successSearchItemWithLongQuery() {
        kraken.search().item(generate.string(1000));

        assertPageIsAvailable();

        Assert.assertTrue(kraken.detect().isSearchResultsEmpty(),
                "Показаны результаты для длинного поискового запроса, не возвращающего результатов\n");
    }
}
