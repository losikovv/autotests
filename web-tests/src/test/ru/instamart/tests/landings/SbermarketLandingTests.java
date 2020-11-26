package ru.instamart.tests.landings;

import instamart.ui.checkpoints.BaseUICheckpoints;
import instamart.ui.modules.User;
import instamart.ui.objectsmap.Elements;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.instamart.tests.TestBase;

import static instamart.core.common.AppManager.session;

public class SbermarketLandingTests extends TestBase {
    BaseUICheckpoints baseChecks = new BaseUICheckpoints();

    @Test(
            description = "Тест валидности и наличия элементов лендинга Сбермаркета",
            priority = 51,
            groups = {"sbermarket-smoke","sbermarket-acceptance","sbermarket-regression"}
    )
    public void successValidateSbermarketLanding() {
        baseChecks.checkPageIsAvailable();

        baseChecks.checkIsElementPresent(Elements.Landings.SbermarketLanding.Header.container());
        baseChecks.checkIsElementPresent(Elements.Landings.SbermarketLanding.Header.logo());
        baseChecks.checkIsElementPresent(Elements.Landings.SbermarketLanding.Header.loginButton());

        baseChecks.checkIsElementPresent(Elements.Landings.SbermarketLanding.MainBlock.container());
        baseChecks.checkIsElementPresent(Elements.Landings.SbermarketLanding.MainBlock.illustration());
        baseChecks.checkIsElementPresent(Elements.Landings.SbermarketLanding.MainBlock.title());
        baseChecks.checkIsElementPresent(Elements.Landings.SbermarketLanding.MainBlock.text());

        baseChecks.checkIsElementPresent(Elements.Landings.SbermarketLanding.MainBlock.Stores.list());
        baseChecks.checkIsElementPresent(Elements.Landings.SbermarketLanding.MainBlock.Stores.button(1));

        baseChecks.checkIsElementPresent(Elements.Landings.SbermarketLanding.AdvantagesBlock.container());
        baseChecks.checkIsElementPresent(Elements.Landings.SbermarketLanding.ZonesBlock.container());
        baseChecks.checkIsElementPresent(Elements.Landings.SbermarketLanding.OrderBlock.container());
        baseChecks.checkIsElementPresent(Elements.Landings.SbermarketLanding.AppsBlock.container());

        baseChecks.checkIsElementPresent(Elements.Landings.SbermarketLanding.AppsBlock.appstoreButton());
        baseChecks.checkIsElementPresent(Elements.Landings.SbermarketLanding.AppsBlock.googleplayButton());

        baseChecks.checkIsElementPresent(Elements.Footer.container());
    }

    @Test(
            description = "Тест перехода в каталог магазина с лендинга Сбермаркета",
            priority = 52,
            groups = {"sbermarket-smoke","sbermarket-acceptance","sbermarket-regression"}
    )
    public void successGoToCatalogFromSbermarketLanding() {
        kraken.perform().click(Elements.Landings.SbermarketLanding.MainBlock.Stores.button(1));

        Assert.assertFalse(
                kraken.detect().isOnLanding(),
                    failMessage("Не работает переход в каталог магазина с лендинга Сбермаркета"));
    }

    @Test(
            description = "Тест авторизации на лендинге Сбермаркета",
            priority = 53,
            groups = {"sbermarket-smoke","sbermarket-acceptance","sbermarket-regression"}
    )
    public void successAuthorizationOnSbermarketLanding() {
        kraken.perform().click(Elements.Landings.SbermarketLanding.Header.loginButton());
        User.Do.loginAs(session.user);
    }
}
