package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.autotests.testdata.Generate;


// Тесты поиска товаров


public class SearchProducts extends TestBase {

    @BeforeMethod(alwaysRun = true)
    public void preconditions() throws Exception {
        kraken.get().page("metro");
    }


    @Test(
            description = "Тест отправки пустого поискового запроса",
            groups = {"acceptance","regression"},
            priority = 300
    )
    public void noSendEmptySearch(){
        kraken.search().item("");

        Assert.assertFalse(kraken.detect().isSearchResultsEmpty(),
                "Search results are shown when it's not supposed to be\n");
    }


    @Test (
            description = "Тест поиска по запросу, не возвращающему результатов",
            groups = {"acceptance","regression"},
            priority = 301
    )
    public void successSearchForNonexistingItem(){
        kraken.search().item("смысл жизни");

        Assert.assertTrue(kraken.detect().isSearchResultsEmpty(),
                "Search result is not empty when it's supposed to be\n");
    }


    @Test (
            description = "Тест упешного поиска товаров",
            groups = {"acceptance","regression"},
            priority = 302
    )
    public void successItemSearch(){
        kraken.search().item("шоколад");

        Assert.assertFalse(kraken.detect().isSearchResultsEmpty(),
                "Search result is empty, so can't assert search is working correctly, check manually\n");

        Assert.assertTrue(kraken.shopping().isProductAvailable(),
                "Can't assert search is working correctly, check manually\n");
    }


    @Test (
            description = "Тест упешного поиска товаров c использованием категорийных саджестов",
            groups = {"regression"},
            priority = 303
    )
    public void successItemSearchUsingCategorySuggests(){
        kraken.search().fillSearchFieldWith("Мороженое");

        Assert.assertTrue(kraken.search().isCategorySuggestsPresent(), "No category suggests shown\n");

        kraken.search().hitCategorySuggest();

        Assert.assertFalse(kraken.detect().isSearchResultsEmpty(),
                "Search result is empty, so can't assert search is working correctly, check manually\n");

        Assert.assertTrue(kraken.shopping().isProductAvailable(),
                "Can't assert search is working correctly, check manually\n");
    }


    @Test (
            description = "Тест упешного поиска товаров c использованием товарных саджестов",
            groups = {"regression"},
            priority = 304
    )
    public void successItemSearchUsingProductSuggests(){
        kraken.get().page("metro");
        kraken.search().fillSearchFieldWith("Мороженое");

        Assert.assertTrue(kraken.search().isProductSuggestsPresent(),
                "No product suggest shown\n");

        kraken.search().hitProductSuggest();

        Assert.assertTrue(kraken.shopping().isItemCardOpen(),
                "Can't approve successful open item card from search product suggest\n");
    }

    @Test (
            description = "Тест поиска по очень длинному запросу, не возвращающему результатов",
            groups = {"acceptance","regression"},
            priority = 305
    )
    public void successSearchWithLongQuery(){
        kraken.search().item(Generate.randomString(1000));

        assertPageIsAvailable();

        Assert.assertTrue(kraken.detect().isSearchResultsEmpty(),
                "Search result is not empty when it's supposed to be\n");
    }

}
