package ru.instamart.tests.ui.landings;

import instamart.core.testdata.UserManager;
import instamart.ui.checkpoints.BaseUICheckpoints;
import instamart.ui.common.lib.Addresses;
import instamart.ui.modules.User;
import instamart.ui.objectsmap.Elements;
import io.qase.api.annotation.CaseId;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.tests.ui.TestBase;

public class SbermarketLandingTests extends TestBase {
    BaseUICheckpoints baseChecks = new BaseUICheckpoints();
    @BeforeMethod(alwaysRun = true)
    private void baseUrl(){
        User.Logout.quickly();
        kraken.get().baseUrl();
    }

    @CaseId(1687)
    @Test(
            description = "Тест валидности и наличия элементов лендинга Сбермаркета",

            groups = {"testing","sbermarket-Ui-smoke"}
    )
    public void successValidateSbermarketLanding() {
        baseChecks.checkPageIsAvailable();
        kraken.perform().hoverOn(Elements.Landings.SbermarketLanding.Header.container());
        baseChecks.checkIsElementPresent(Elements.Landings.SbermarketLanding.Header.container());
        baseChecks.checkIsElementPresent(Elements.Landings.SbermarketLanding.Header.logo());
        baseChecks.checkIsElementPresent(Elements.Landings.SbermarketLanding.Header.loginButton());

        kraken.await().fluently(
                ExpectedConditions.visibilityOfElementLocated(Elements.Landings.SbermarketLanding.MainBlock.container().getLocator()));
        baseChecks.checkIsElementPresent(Elements.Landings.SbermarketLanding.MainBlock.container());
        baseChecks.checkIsElementPresent(Elements.Landings.SbermarketLanding.MainBlock.illustration());
        baseChecks.checkIsElementPresent(Elements.Landings.SbermarketLanding.MainBlock.addressButton());
        baseChecks.checkIsElementPresent(Elements.Landings.SbermarketLanding.MainBlock.text());
        baseChecks.checkIsElementPresent(Elements.Landings.SbermarketLanding.MainBlock.Stores.list());
        baseChecks.checkIsElementPresent(Elements.Landings.SbermarketLanding.MainBlock.Stores.button(1));
        baseChecks.checkIsElementPresent(Elements.Landings.SbermarketLanding.MainBlock.Stores.buttonAuchan());
        baseChecks.checkIsElementPresent(Elements.Landings.SbermarketLanding.MainBlock.Stores.buttonMetro());
        baseChecks.checkIsElementPresent(Elements.Landings.SbermarketLanding.AdvantagesBlock.container());
        baseChecks.checkIsElementPresent(Elements.Landings.SbermarketLanding.AdvantagesBlock.deliveryAdv());
        baseChecks.checkIsElementPresent(Elements.Landings.SbermarketLanding.AdvantagesBlock.heavyToDoorAdv());
        baseChecks.checkIsElementPresent(Elements.Landings.SbermarketLanding.AdvantagesBlock.goodQualityAdv());
        baseChecks.checkIsElementPresent(Elements.Landings.SbermarketLanding.AdvantagesBlock.saleAdv());
        baseChecks.checkIsElementPresent(Elements.Landings.SbermarketLanding.ZonesBlock.container());
        baseChecks.checkIsElementPresent(Elements.Landings.SbermarketLanding.ZonesBlock.showAllButton());
        baseChecks.checkIsElementPresent(Elements.Landings.SbermarketLanding.OrderBlock.container());
        baseChecks.checkIsElementPresent(Elements.Landings.SbermarketLanding.OrderBlock.stepFirst());
        baseChecks.checkIsElementPresent(Elements.Landings.SbermarketLanding.OrderBlock.stepSecond());
        baseChecks.checkIsElementPresent(Elements.Landings.SbermarketLanding.OrderBlock.stepThird());
        baseChecks.checkIsElementPresent(Elements.Landings.SbermarketLanding.AppsBlock.container());

        baseChecks.checkIsElementPresent(Elements.Landings.SbermarketLanding.AppsBlock.appStoreButton());
        baseChecks.checkIsElementPresent(Elements.Landings.SbermarketLanding.AppsBlock.googlePlayButton());
        baseChecks.checkIsElementPresent(Elements.Landings.SbermarketLanding.AppsBlock.huaweiStoreButton());

        baseChecks.checkIsElementPresent(Elements.Footer.container());
    }

    @CaseId(1683)
    @Test(
            description = "Тест перехода в каталог магазина с лендинга Сбермаркета",

            groups = {"testing","sbermarket-Ui-smoke"}
    )
    public void successGoToCatalogFromSbermarketLanding() {
        User.ShippingAddress.searchShopsByAddress(Addresses.Moscow.defaultAddress());
        baseChecks.checkIsOnLanding();
    }

    @Test(
            description = "Тест авторизации на лендинге Сбермаркета",

            groups = {}
    )
    @Deprecated
    public void successAuthorizationOnSbermarketLanding() {
        kraken.perform().click(Elements.Landings.SbermarketLanding.Header.loginButton());
        User.Do.loginAs(UserManager.getDefaultUser());
    }
}
