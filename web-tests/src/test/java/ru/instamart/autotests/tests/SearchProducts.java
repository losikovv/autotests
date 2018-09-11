package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SearchProducts extends TestBase {

    @BeforeMethod(alwaysRun = true)
    public void getAuth() throws Exception {
        app.getNavigationHelper().getLandingPage();
        app.getSessionHelper().doLoginAs("admin");
    }



    @Test(
            description = "Тест отправки пустого поискового запроса",
            groups = {"acceptance","regression"},
            priority = 305
    )
    public void sendEmptySearch(){
        app.getShoppingHelper().searchItem("");

        // Проверяем что поиск не сработал
        Assert.assertFalse(app.getShoppingHelper().isSearchResultsEmpty(),
                "Search results are shown when it's not supposed to be\n");
    }


    @Test (
            description = "Тест поиска по запросу, не возвращающему результатов",
            groups = {"acceptance","regression"},
            priority = 306
    )
    public void successSearchForNonexistingItem(){
        app.getShoppingHelper().searchItem("смысл жизни");

        // Проверяем что поиск дал пустой результат
        Assert.assertTrue(app.getShoppingHelper().isSearchResultsEmpty(),
                "Search result is not empty when it's supposed to be\n");
    }


    @Test (
            description = "Тест упешного поиска товаров",
            groups = {"acceptance","regression"},
            priority = 307
    )
    public void successItemSearch(){
        app.getShoppingHelper().searchItem("шоколад");

        // Проверяем что поиск не дал пустой результат
        Assert.assertFalse(app.getShoppingHelper().isSearchResultsEmpty(),
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
        app.getShoppingHelper().fillSearchField("Мороженое");

        // Проверяем что появились категорийные подсказки
        Assert.assertTrue(app.getShoppingHelper().isCategorySuggestPresent(),
                "No category suggest shown\n");

        // Нажимаем на категорийную посдказку
        app.getShoppingHelper().hitSuggest("category");

        // Проверяем что поиск не дал пустой результат
        Assert.assertFalse(app.getShoppingHelper().isSearchResultsEmpty(),
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
        app.getShoppingHelper().fillSearchField("Мороженое");

        // Проверяем что появились товарные подсказки
        Assert.assertTrue(app.getShoppingHelper().isProductSuggestPresent(),
                "No product suggest shown\n");

        // Нажимаем на твоарную подсказку
        app.getShoppingHelper().hitSuggest("product");

        // Проверяем что открылась карточка товара
        Assert.assertTrue(app.getShoppingHelper().isItemCardOpen(),
                "Can't approve successful open item card from search product suggest\n");
    }

}
