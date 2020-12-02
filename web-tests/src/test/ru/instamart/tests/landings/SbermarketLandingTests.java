package ru.instamart.tests.landings;

import instamart.ui.checkpoints.BaseUICheckpoints;
import instamart.ui.modules.User;
import instamart.ui.objectsmap.Elements;
import io.qameta.allure.Flaky;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.tests.TestBase;

import static instamart.core.common.AppManager.session;

public class SbermarketLandingTests extends TestBase {
    BaseUICheckpoints baseChecks = new BaseUICheckpoints();
    @BeforeClass(alwaysRun = true)
    private void baseUrl(){
        User.Logout.quickly();
        kraken.get().baseUrl();
    }

    @Test(
            description = "Тест валидности и наличия элементов лендинга Сбермаркета",
            priority = 51,
            groups = {"testing","sbermarket-Ui-smoke"}
    )
    @Flaky
    public void successValidateSbermarketLanding() {
        baseChecks.checkPageIsAvailable();

        baseChecks.checkIsElementPresent(Elements.Landings.SbermarketLanding.Header.container());
        baseChecks.checkIsElementPresent(Elements.Landings.SbermarketLanding.Header.logo());
        baseChecks.checkIsElementPresent(Elements.Landings.SbermarketLanding.Header.loginButton());

        kraken.await().fluently(
                ExpectedConditions.visibilityOfElementLocated(Elements.Landings.SbermarketLanding.MainBlock.container().getLocator()));
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

        baseChecks.checkIsElementPresent(Elements.Landings.SbermarketLanding.AppsBlock.appStoreButton());
        baseChecks.checkIsElementPresent(Elements.Landings.SbermarketLanding.AppsBlock.googlePlayButton());
        baseChecks.checkIsElementPresent(Elements.Landings.SbermarketLanding.AppsBlock.huaweiStoreButton());

        baseChecks.checkIsElementPresent(Elements.Footer.container());
    }

    @Test(
            description = "Тест перехода в каталог магазина с лендинга Сбермаркета",
            priority = 52,
            groups = {"testing","sbermarket-Ui-smoke"}
    )
    @Flaky
    public void successGoToCatalogFromSbermarketLanding() {
        kraken.await().fluently(
                ExpectedConditions
                        .elementToBeClickable(Elements.Landings.SbermarketLanding.MainBlock.Stores.button(1).getLocator()),
                "кнопка выбора ретейлера недоступна");
        kraken.perform().click(Elements.Landings.SbermarketLanding.MainBlock.Stores.button(1));
        baseChecks.checkIsOnLanding();
    }

    @Test(
            description = "Тест авторизации на лендинге Сбермаркета",
            priority = 53,
            groups = {}
    )
    @Deprecated
    public void successAuthorizationOnSbermarketLanding() {
        //
        kraken.perform().click(Elements.Landings.SbermarketLanding.Header.loginButton());
        User.Do.loginAs(session.user);
    }
}
