package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;



    // Тесты поиска товаров



public class SearchProducts extends TestBase {

    /*
    @BeforeMethod(alwaysRun = true)
    public void getAuth() throws Exception {
        app.getNavigationHelper().getLandingPage();
        app.getSessionHelper().doLoginAs("admin");
    }
    */


    @Test(
            description = "Тест отправки пустого поискового запроса",
            groups = {"acceptance","regression"},
            priority = 305
    )
    public void sendEmptySearch(){
        app.getNavigationHelper().getRetailerPage("metro");
        app.search().item("");

        // Проверяем что поисковый запрос не отправился
        Assert.assertFalse(app.search().isResultsEmpty(),
                "Search results are shown when it's not supposed to be\n");
    }


    @Test (
            description = "Тест поиска по запросу, не возвращающему результатов",
            groups = {"acceptance","regression"},
            priority = 306
    )
    public void successSearchForNonexistingItem(){
        app.getNavigationHelper().getRetailerPage("metro");
        app.search().item("смысл жизни");

        // Проверяем что поиск дал пустой результат
        Assert.assertTrue(app.search().isResultsEmpty(),
                "Search result is not empty when it's supposed to be\n");
    }


    @Test (
            description = "Тест упешного поиска товаров",
            groups = {"acceptance","regression"},
            priority = 307
    )
    public void successItemSearch(){
        app.getNavigationHelper().getRetailerPage("metro");
        app.search().item("шоколад");

        // Проверяем что поиск не дал пустой результат
        Assert.assertFalse(app.search().isResultsEmpty(),
                "Search result is empty, so can't assert search is working correctly, check manually\n");

        // Проверяем что по поисковому запросу нашлись продукты
        Assert.assertTrue(app.getShoppingHelper().isProductAvailable(),
                "Can't assert search is working correctly, check manually\n");
    }


    @Test (
            description = "Тест упешного поиска товаров c использованием категорийных саджестов",
            groups = {"regression"},
            priority = 308
    )
    public void successItemSearchUsingCategorySuggests(){
        app.getNavigationHelper().getRetailerPage("metro");
        app.search().fillQueryField("Мороженое");

        // Проверяем что появились категорийные подсказки
        Assert.assertTrue(app.search().isCategorySuggestsPresent(),
                "No category suggest shown\n");

        // Нажимаем на категорийную посдказку
        app.search().hitSuggest("category");

        // Проверяем что поиск не дал пустой результат
        Assert.assertFalse(app.search().isResultsEmpty(),
                "Search result is empty, so can't assert search is working correctly, check manually\n");

        // Проверяем что по поисковому запросу нашлись продукты
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
        app.search().fillQueryField("Мороженое");

        // Проверяем что появились товарные подсказки
        Assert.assertTrue(app.search().isProductSuggestsPresent(),
                "No product suggest shown\n");

        // Нажимаем на твоарную подсказку
        app.search().hitSuggest("product");

        // Проверяем что открылась карточка товара
        Assert.assertTrue(app.getShoppingHelper().isItemCardOpen(),
                "Can't approve successful open item card from search product suggest\n");
    }

}
