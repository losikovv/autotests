package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.autotests.testdata.Generate;


// Тесты поиска товаров



public class SearchProducts extends TestBase {

    @BeforeMethod(alwaysRun = true)
    public void preconditions() throws Exception {
        app.getNavigationHelper().getRetailerPage("metro");
    }


    @Test(
            description = "Тест отправки пустого поискового запроса",
            groups = {"acceptance","regression"},
            priority = 305
    )
    public void cantSendEmptySearch(){
        app.search().item("");

        Assert.assertFalse(app.search().isResultsEmpty(),
                "Search results are shown when it's not supposed to be\n");
    }


    @Test (
            description = "Тест поиска по запросу, не возвращающему результатов",
            groups = {"acceptance","regression"},
            priority = 306
    )
    public void successSearchForNonexistingItem(){
        app.search().item("смысл жизни");

        Assert.assertTrue(app.search().isResultsEmpty(),
                "Search result is not empty when it's supposed to be\n");
    }


    @Test (
            description = "Тест упешного поиска товаров",
            groups = {"acceptance","regression"},
            priority = 307
    )
    public void successItemSearch(){
        app.search().item("шоколад");

        Assert.assertFalse(app.search().isResultsEmpty(),
                "Search result is empty, so can't assert search is working correctly, check manually\n");

        Assert.assertTrue(app.getShoppingHelper().isProductAvailable(),
                "Can't assert search is working correctly, check manually\n");
    }


    @Test (
            description = "Тест упешного поиска товаров c использованием категорийных саджестов",
            groups = {"regression"},
            priority = 308
    )
    public void successItemSearchUsingCategorySuggests(){
        app.search().fillSearchFieldWith("Мороженое");

        Assert.assertTrue(app.search().isCategorySuggestsPresent(), "No category suggests shown\n");

        app.search().hitCategorySuggest();

        Assert.assertFalse(app.search().isResultsEmpty(),
                "Search result is empty, so can't assert search is working correctly, check manually\n");

        Assert.assertTrue(app.getShoppingHelper().isProductAvailable(),
                "Can't assert search is working correctly, check manually\n");
    }


    @Test (
            description = "Тест упешного поиска товаров c использованием товарных саджестов",
            groups = {"regression"},
            priority = 309
    )
    public void successItemSearchUsingProductSuggests(){
        app.getNavigationHelper().getRetailerPage("metro");
        app.search().fillSearchFieldWith("Мороженое");

        Assert.assertTrue(app.search().isProductSuggestsPresent(),
                "No product suggest shown\n");

        app.search().hitProductSuggest();

        Assert.assertTrue(app.getShoppingHelper().isItemCardOpen(),
                "Can't approve successful open item card from search product suggest\n");
    }

    @Test (
            description = "Тест поиска по очень длинному запросу, не возвращающему результатов",
            groups = {"acceptance","regression"},
            priority = 310
    )
    public void successSearchWithLongQuery(){
        app.search().item(Generate.randomString(1000));

        assertPageIsAvailable();

        Assert.assertTrue(app.search().isResultsEmpty(),
                "Search result is not empty when it's supposed to be\n");
    }

}
