package ru.instamart.tests.landings;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.application.Elements;
import ru.instamart.application.platform.modules.User;
import ru.instamart.tests.TestBase;

import static ru.instamart.application.AppManager.session;

public class SbermarketLandingTests extends TestBase {

    @BeforeMethod(alwaysRun = true)
    public void quickLogout() {
        User.Logout.quickly();
    }

    @AfterClass(alwaysRun = true)
    public void setup() {
        User.Logout.quickly();
    }

    @Test(
            description = "Тест валидности и наличия элементов лендинга Сбермаркета",
            priority = 51,
            groups = {"sbermarket-smoke","sbermarket-acceptance","sbermarket-regression"}
    )
    public void successValidateSbermarketLanding() {
        assertPageIsAvailable();

        assertPresence(Elements.Landings.SbermarketLanding.Header.container());
        assertPresence(Elements.Landings.SbermarketLanding.Header.logo());
        assertPresence(Elements.Landings.SbermarketLanding.Header.loginButton());

        assertPresence(Elements.Landings.SbermarketLanding.MainBlock.container());
        assertPresence(Elements.Landings.SbermarketLanding.MainBlock.illustration());
        assertPresence(Elements.Landings.SbermarketLanding.MainBlock.title());
        assertPresence(Elements.Landings.SbermarketLanding.MainBlock.text());

        assertPresence(Elements.Landings.SbermarketLanding.MainBlock.Stores.list());
        assertPresence(Elements.Landings.SbermarketLanding.MainBlock.Stores.button(1));

        assertPresence(Elements.Landings.SbermarketLanding.AdvantagesBlock.container());
        assertPresence(Elements.Landings.SbermarketLanding.ZonesBlock.container());
        assertPresence(Elements.Landings.SbermarketLanding.OrderBlock.container());
        assertPresence(Elements.Landings.SbermarketLanding.AppsBlock.container());

        assertPresence(Elements.Landings.SbermarketLanding.AppsBlock.appstoreButton());
        assertPresence(Elements.Landings.SbermarketLanding.AppsBlock.googleplayButton());

        assertPresence(Elements.Footer.container());
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
