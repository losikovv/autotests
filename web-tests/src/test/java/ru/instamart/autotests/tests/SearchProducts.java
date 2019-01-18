package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


// Тесты поиска товаров


public class SearchProducts extends TestBase {

    @BeforeMethod(alwaysRun = true)
    public void preconditions() throws Exception {
        kraken.get().page("metro");
    }


    @Test(
            description = "Тест отправки пустого поискового запроса",
            groups = {"acceptance","regression"},
            priority = 401
    )
    public void noSendEmptySearch(){
        kraken.search().item("");

        Assert.assertFalse(kraken.detect().isSearchResultsEmpty(),
                "Показаны результаты для пустого поискового запроса\n");
    }


    @Test (
            description = "Тест поиска по запросу, не возвращающему результатов",
            groups = {"acceptance","regression"},
            priority = 402
    )
    public void successSearchForNonexistingItem(){
        kraken.search().item("смысл жизни");

        Assert.assertTrue(kraken.detect().isSearchResultsEmpty(),
                "Показаны результаты для поискового запроса, не возвращающего результатов\n");
    }


    @Test (
            description = "Тест упешного поиска товаров",
            groups = {"acceptance","regression"},
            priority = 403
    )
    public void successItemSearch(){
        kraken.search().item("шоколад");

        Assert.assertFalse(kraken.detect().isSearchResultsEmpty(),
                "Отсутствуют результаты поиска\n");

        Assert.assertTrue(kraken.detect().isProductAvailable(),
                "Отсутствуют продукты в результатах поиска\n");
    }


    @Test (
            description = "Тест упешного поиска товаров c использованием категорийных саджестов",
            groups = {"acceptance","regression"},
            priority = 404
    )
    public void successItemSearchUsingCategorySuggests(){
        kraken.search().fillSearchFieldWith("Мороженое");

        Assert.assertTrue(kraken.search().isCategorySuggestsPresent(),
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
            priority = 405
    )
    public void successItemSearchUsingProductSuggests(){
        kraken.get().page("metro");
        kraken.search().fillSearchFieldWith("Мороженое");

        Assert.assertTrue(kraken.search().isProductSuggestsPresent(),
                "Отсутствуют товарные подсказки\n");

        kraken.search().hitProductSuggest();

        Assert.assertTrue(kraken.detect().isItemCardOpen(),
                "Не открыта карточка товара из товарной подсказки\n");
    }

    @Test (
            description = "Тест поиска по очень длинному запросу, не возвращающему результатов",
            groups = {"regression"},
            priority = 406
    )
    public void successItemSearchWithLongQuery(){
        kraken.search().item(kraken.generate().string(1000));

        assertPageIsAvailable();

        Assert.assertTrue(kraken.detect().isSearchResultsEmpty(),
                "Показаны результаты для длинного поискового запроса, не возвращающего результатов\n");
    }

}
