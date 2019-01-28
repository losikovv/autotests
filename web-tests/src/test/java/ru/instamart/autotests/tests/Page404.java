package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Pages;

public class Page404 extends TestBase {

    @BeforeMethod(alwaysRun = true)
    public void get404Page() {
        kraken.get().page(Pages.page404());
    }

    @Test(
            description = "Тест на просмотр котомудрости на странице 404",
            groups = {"regression"},
            priority = 9000
    )
    public void successLearnCatWisdomOnPage404() throws AssertionError {
        kraken.jivosite().open();
        kraken.perform().click(Elements.Page404.catWisdomButton());

        Assert.assertTrue(kraken.detect().isElementPresent(Elements.Page404.quote()),
                "Не работает просмотр котомудрости на странице 404");
    }

    @Test(
            description = "Тест перехода на главную по одноименной кнопке на странице 404",
            groups = {"regression"},
            priority = 9001
    )
    public void successTransitionToHomepage() throws AssertionError {
        kraken.jivosite().open();
        kraken.perform().click(Elements.Page404.homePageButton());

        assertPageIsAvailable(Pages.Site.Retailers.metro());
    }

    @Test(
            description = "Тест перехода на главную по одноименной кнопке на странице 404 после позанания котомудрости",
            groups = {"regression"},
            priority = 9002
    )
    public void successTransitionToHomepageAfterCatWisdom() throws AssertionError {
        kraken.jivosite().open();
        kraken.perform().click(Elements.Page404.catWisdomButton());
        kraken.perform().click(Elements.Page404.homePageButton());

        assertPageIsAvailable(Pages.Site.Retailers.metro());
    }

    @Test(
            description = "Тест перехода в каталог по кнопке 'Познать цены' на странице 404 после позанания котомудрости",
            groups = {"regression"},
            priority = 9003
    )
    public void successTransitionToCatalogAfterCatWisdom() throws AssertionError {
        kraken.jivosite().open();
        kraken.perform().click(Elements.Page404.catWisdomButton());
        kraken.jivosite().close();
        kraken.perform().click(Elements.Page404.learnPricesButton());

        assertPageIsAvailable(Pages.Site.Retailers.metro());
    }

    @Test(
            description = "Тест познания новой котомудрости на странице 404 после познания первой ",
            groups = {"regression"},
            priority = 9004
    )
    public void successLearnNewWisdomCat() throws AssertionError {
        kraken.jivosite().open(); // для стабильности работы
        kraken.perform().click(Elements.Page404.catWisdomButton());
        kraken.jivosite().close(); // для стабильности работы
        kraken.perform().waitingFor(1);


        String catWisdom = kraken.detect().catWisdom();
        kraken.perform().click(Elements.Page404.showMoreWisdomCat());

        Assert.assertNotEquals(catWisdom, kraken.detect().catWisdom(),"Текст котомудрости одинаковый! Похоже," +
                "что кнопка познания новой котомудрости не работает ");

    }
}
