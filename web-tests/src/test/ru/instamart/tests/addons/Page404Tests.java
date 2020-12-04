package ru.instamart.tests.addons;

import instamart.ui.checkpoints.BaseUICheckpoints;
import instamart.ui.common.lib.Pages;
import instamart.ui.modules.Shop;
import instamart.ui.objectsmap.Elements;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.tests.TestBase;

public class Page404Tests extends TestBase {
    
    private final BaseUICheckpoints baseChecks = new BaseUICheckpoints();
    
    @BeforeMethod(alwaysRun = true,
            description ="Выполняем шаги предусловий для теста")
    public void beforeTest() {
        kraken.get().page(Pages.page404());
    }

    @Test(  description = "Тест познания котомудрости на странице 404",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 11001
    )
    public void successLearnCatWisdom() {
        //Shop.Jivosite.open();
        kraken.perform().click(Elements.Page404.catWisdomButton());

        Assert.assertTrue(
                kraken.detect().isElementPresent(Elements.Page404.quote()),
                    "Не работает познание котомудрости на странице 404");
    }

    @Test(  description = "Тест перехода на главную по одноименной кнопке на странице 404",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 11002
    )
    public void successGoToHomepage() {
        //Shop.Jivosite.open();

        baseChecks.checkTransitionValidation(Elements.Page404.toHomeButton());
    }

    @Test(  description = "Тест перехода на главную по одноименной кнопке на странице 404 после познания котомудрости",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 11003
    )
    public void successGoToHomepageAfterLearningCatWisdom() {
        //Shop.Jivosite.open();
        kraken.perform().click(Elements.Page404.catWisdomButton());

        baseChecks.checkTransitionValidation(Elements.Page404.toHomeButton());
    }

    @Test(  description = "Тест перехода в каталог по кнопке 'Познать цены' на странице 404 после познания котомудрости",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 11004
    )
    public void successGoToCatalogAfterLearningCatWisdom() {
        //Shop.Jivosite.open();
        kraken.perform().click(Elements.Page404.catWisdomButton());
        //Shop.Jivosite.close();

        baseChecks.checkTransitionValidation(Elements.Page404.learnPricesButton());
    }

    @Test(  description = "Тест познания новой котомудрости на странице 404 после познания первой",
            groups = {"sbermarket-regression"},
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
