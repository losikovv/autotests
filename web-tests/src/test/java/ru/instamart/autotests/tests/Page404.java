package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Pages;

public class Page404 extends TestBase {


    @BeforeMethod(alwaysRun = true)
    public void getPage404() {
        kraken.get().page(Pages.page404());
    }


    @Test(
            description = "Тест познания котомудрости на странице 404",
            groups = {"regression"},
            priority = 10001
    )
    public void successLearnCatWisdom() throws AssertionError {
        kraken.jivosite().open();
        kraken.perform().click(Elements.Page404.catWisdomButton());

        kraken.perform().printMessage("\nКотомудрость: " + kraken.grab().catWisdom());

        Assert.assertTrue(kraken.detect().isElementPresent(Elements.Page404.quote()),
                "Не работает познание котомудрости на странице 404");
    }


    @Test(
            description = "Тест перехода на главную по одноименной кнопке на странице 404",
            groups = {"regression"},
            priority = 10002
    )
    public void successGoToHomepageFromDefaultPage404() throws AssertionError {
        kraken.jivosite().open();

        assertTransition(Elements.Page404.homePageButton());
        assertPageIsAvailable();
    }


    @Test(
            description = "Тест перехода на главную по одноименной кнопке на странице 404 после познания котомудрости",
            groups = {"regression"},
            priority = 10003
    )
    public void successGoToHomepageAfterLearningCatWisdom() throws AssertionError {
        kraken.jivosite().open();
        kraken.perform().click(Elements.Page404.catWisdomButton());

        assertTransition(Elements.Page404.homePageButton());
        assertPageIsAvailable();
    }


    @Test(
            description = "Тест перехода в каталог по кнопке 'Познать цены' на странице 404 после познания котомудрости",
            groups = {"regression"},
            priority = 10004
    )
    public void successGoToCatalogAfterLearningCatWisdom() throws AssertionError {
        kraken.jivosite().open();
        kraken.perform().click(Elements.Page404.catWisdomButton());
        kraken.jivosite().close();

        assertTransition(Elements.Page404.learnPricesButton());
        assertPageIsAvailable();
    }


    @Test(
            description = "Тест познания новой котомудрости на странице 404 после познания первой",
            groups = {"acceptance","regression"},
            priority = 10005
    )
    public void successLearnMoreCatWisdom() throws AssertionError {
        kraken.jivosite().open(); // для стабильности работы
        kraken.perform().click(Elements.Page404.catWisdomButton());
        kraken.jivosite().close(); // для стабильности работы
        kraken.perform().waitingFor(1);

        String firstWisdom = kraken.grab().catWisdom();
        kraken.perform().click(Elements.Page404.showMoreCatWisdom());
        String anotherWisdom = kraken.grab().catWisdom();

        Assert.assertNotEquals(firstWisdom, anotherWisdom,
                "Не работает познание новой котомудрости на странице 404");
    }
}
