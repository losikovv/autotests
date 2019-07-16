package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Pages;
import ru.instamart.autotests.appmanager.ShopHelper;

import static ru.instamart.autotests.application.Config.testPage404;

public class Page404 extends TestBase {

    @BeforeMethod(alwaysRun = true)
    public void getPage404() {
        kraken.get().page(Pages.page404());
    }

    @Test(  enabled = testPage404,
            description = "Тест познания котомудрости на странице 404",
            groups = {"acceptance","regression"},
            priority = 10001
    )
    public void successLearnCatWisdom() {
        ShopHelper.Jivosite.open();
        kraken.perform().click(Elements.Page404.catWisdomButton());

        Assert.assertTrue(
                kraken.detect().isElementPresent(Elements.Page404.quote()),
                    "Не работает познание котомудрости на странице 404");
    }

    @Test(  enabled = testPage404,
            description = "Тест перехода на главную по одноименной кнопке на странице 404",
            groups = {"acceptance","regression"},
            priority = 10002
    )
    public void successGoToHomepage() {
        //ShopHelper.Jivosite.open();

        validateTransition(Elements.Page404.toHomeButton());
    }

    @Test(  enabled = testPage404,
            description = "Тест перехода на главную по одноименной кнопке на странице 404 после познания котомудрости",
            groups = {"acceptance","regression"},
            priority = 10003
    )
    public void successGoToHomepageAfterLearningCatWisdom() {
        ShopHelper.Jivosite.open();
        kraken.perform().click(Elements.Page404.catWisdomButton());

        validateTransition(Elements.Page404.toHomeButton());
    }

    @Test(  enabled = testPage404,
            description = "Тест перехода в каталог по кнопке 'Познать цены' на странице 404 после познания котомудрости",
            groups = {"acceptance","regression"},
            priority = 10004
    )
    public void successGoToCatalogAfterLearningCatWisdom() {
        ShopHelper.Jivosite.open();
        kraken.perform().click(Elements.Page404.catWisdomButton());
        ShopHelper.Jivosite.close();

        validateTransition(Elements.Page404.learnPricesButton());
    }

    @Test(  enabled = testPage404,
            description = "Тест познания новой котомудрости на странице 404 после познания первой",
            groups = {"acceptance","regression"},
            priority = 10005
    )
    public void successLearnMoreCatWisdom() {
        kraken.perform().refresh();
        ShopHelper.Jivosite.open(); // для стабильности

        kraken.perform().click(Elements.Page404.catWisdomButton());
        kraken.await().implicitly(1); // Ожидание котомудрости
        String firstWisdom = kraken.grab().catWisdom();

        kraken.perform().click(Elements.Page404.showMoreWisdomButton());
        kraken.await().implicitly(1); // Ожидание новой котомудрости
        String anotherWisdom = kraken.grab().catWisdom();

        Assert.assertNotEquals(
                firstWisdom, anotherWisdom,
                    "Не работает познание новой котомудрости на странице 404");
    }
}
