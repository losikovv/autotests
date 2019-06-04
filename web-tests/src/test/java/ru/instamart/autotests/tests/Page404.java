package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Pages;

import static ru.instamart.autotests.application.Config.enablePage404Tests;

public class Page404 extends TestBase {

    @BeforeMethod(alwaysRun = true)
    public void getPage404() {
        kraken.get().page(Pages.page404());
    }

    @Test(  enabled = enablePage404Tests,
            description = "Тест познания котомудрости на странице 404",
            groups = {"acceptance","regression"},
            priority = 10001
    )
    public void successLearnCatWisdom() throws AssertionError {
        kraken.jivosite().open();
        kraken.perform().click(Elements.Page404.catWisdomButton());

        Assert.assertTrue(
                kraken.detect().isElementPresent(Elements.Page404.quote()),
                    "Не работает познание котомудрости на странице 404");
    }

    @Test(  enabled = enablePage404Tests,
            description = "Тест перехода на главную по одноименной кнопке на странице 404",
            groups = {"acceptance","regression"},
            priority = 10002
    )
    public void successGoToHomepage() throws AssertionError {
        kraken.jivosite().open();

        validateTransition(Elements.Page404.toHomeButton());
    }

    @Test(  enabled = enablePage404Tests,
            description = "Тест перехода на главную по одноименной кнопке на странице 404 после познания котомудрости",
            groups = {"acceptance","regression"},
            priority = 10003
    )
    public void successGoToHomepageAfterLearningCatWisdom() throws AssertionError {
        kraken.jivosite().open();
        kraken.perform().click(Elements.Page404.catWisdomButton());

        validateTransition(Elements.Page404.toHomeButton());
    }

    @Test(  enabled = enablePage404Tests,
            description = "Тест перехода в каталог по кнопке 'Познать цены' на странице 404 после познания котомудрости",
            groups = {"acceptance","regression"},
            priority = 10004
    )
    public void successGoToCatalogAfterLearningCatWisdom() throws AssertionError {
        kraken.jivosite().open();
        kraken.perform().click(Elements.Page404.catWisdomButton());
        kraken.jivosite().close();

        validateTransition(Elements.Page404.learnPricesButton());
    }

    @Test(  enabled = enablePage404Tests,
            description = "Тест познания новой котомудрости на странице 404 после познания первой",
            groups = {"acceptance","regression"},
            priority = 10005
    )
    public void successLearnMoreCatWisdom() throws AssertionError {
        kraken.jivosite().open(); // для стабильности

        kraken.perform().click(Elements.Page404.catWisdomButton());
        String firstWisdom = kraken.grab().catWisdom();

        kraken.perform().click(Elements.Page404.showMoreWisdomButton());
        String anotherWisdom = kraken.grab().catWisdom();

        Assert.assertNotEquals(
                firstWisdom, anotherWisdom,
                    "Не работает познание новой котомудрости на странице 404");
    }
}
