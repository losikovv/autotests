package ru.instamart.autotests.tests.addons;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Pages;
import ru.instamart.autotests.appmanager.platform.Shop;
import ru.instamart.autotests.tests.TestBase;

import static ru.instamart.autotests.application.Config.TestsConfiguration.AddonsTests.enablePage404test;

public class Page404Tests extends TestBase {

    @BeforeMethod(alwaysRun = true)
    public void getPage404() {
        kraken.get().page(Pages.page404());
    }

    @Test(  enabled = enablePage404test,
            description = "Тест познания котомудрости на странице 404",
            groups = {"acceptance","regression"},
            priority = 11001
    )
    public void successLearnCatWisdom() {
        Shop.Jivosite.open();
        kraken.perform().click(Elements.Page404.catWisdomButton());

        Assert.assertTrue(
                kraken.detect().isElementPresent(Elements.Page404.quote()),
                    "Не работает познание котомудрости на странице 404");
    }

    @Test(  enabled = enablePage404test,
            description = "Тест перехода на главную по одноименной кнопке на странице 404",
            groups = {"acceptance","regression"},
            priority = 11002
    )
    public void successGoToHomepage() {
        //Shop.Jivosite.open();

        validateTransition(Elements.Page404.toHomeButton());
    }

    @Test(  enabled = enablePage404test,
            description = "Тест перехода на главную по одноименной кнопке на странице 404 после познания котомудрости",
            groups = {"acceptance","regression"},
            priority = 11003
    )
    public void successGoToHomepageAfterLearningCatWisdom() {
        Shop.Jivosite.open();
        kraken.perform().click(Elements.Page404.catWisdomButton());

        validateTransition(Elements.Page404.toHomeButton());
    }

    @Test(  enabled = enablePage404test,
            description = "Тест перехода в каталог по кнопке 'Познать цены' на странице 404 после познания котомудрости",
            groups = {"acceptance","regression"},
            priority = 11004
    )
    public void successGoToCatalogAfterLearningCatWisdom() {
        Shop.Jivosite.open();
        kraken.perform().click(Elements.Page404.catWisdomButton());
        Shop.Jivosite.close();

        validateTransition(Elements.Page404.learnPricesButton());
    }

    @Test(  enabled = enablePage404test,
            description = "Тест познания новой котомудрости на странице 404 после познания первой",
            groups = {"acceptance","regression"},
            priority = 11005
    )
    public void successLearnMoreCatWisdom() {
        kraken.perform().refresh();
        Shop.Jivosite.open(); // для стабильности

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
