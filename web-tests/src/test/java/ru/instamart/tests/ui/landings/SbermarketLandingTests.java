package ru.instamart.tests.ui.landings;

import ru.instamart.ui.checkpoints.BaseUICheckpoints;
import ru.instamart.ui.common.lib.Addresses;
import ru.instamart.ui.modules.Shop;
import ru.instamart.ui.modules.User;
import ru.instamart.ui.modules.shop.ShippingAddressModal;
import ru.instamart.ui.objectsmap.Elements;
import io.qase.api.annotation.CaseId;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.tests.ui.TestBase;

import static io.qameta.allure.Allure.step;

public class SbermarketLandingTests extends TestBase {
    BaseUICheckpoints baseChecks = new BaseUICheckpoints();
    @BeforeMethod(alwaysRun = true)
    private void baseUrl(){
        User.Logout.quickly();
    }

    @CaseId(1687)
    @Test(
            description = "Тест валидности и наличия элементов лендинга Сбермаркета",

            groups = {"sbermarket-Ui-smoke"}
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
//        baseChecks.checkIsElementPresent(Elements.Landings.SbermarketLanding.MainBlock.illustration());
        baseChecks.checkIsElementPresent(Elements.Landings.SbermarketLanding.MainBlock.addressButton());
        baseChecks.checkIsElementPresent(Elements.Landings.SbermarketLanding.MainBlock.text());
        baseChecks.checkIsElementPresent(Elements.Landings.SbermarketLanding.MainBlock.Stores.list());
        baseChecks.checkIsElementPresent(Elements.Landings.SbermarketLanding.MainBlock.Stores.button());
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
            groups = {"sbermarket-Ui-smoke"}
    )
    public void successGoToCatalogFromSbermarketLanding() {
        ShippingAddressModal.selectFirstRetailer();
        ShippingAddressModal.fill(Addresses.Moscow.defaultAddress());
        ShippingAddressModal.selectAddressSuggest();
        ShippingAddressModal.findShops();
        baseChecks.checkIsOnLanding();
    }

    @CaseId(2042)
    @Test(
            description = "Тест появления кнопоки авторизации через СберБизнес для B2B",
            groups = {"sbermarket-Ui-smoke", "testing"}
    )
    public void EnabledSberBussinesIdButton(){
        Shop.AuthModal.open();
        step("Выбор чекбокса В2В", ()->kraken.perform().click(Elements.Modals.AuthModal.checkB2B()));
        step("Проверка наличия кнопки авторизации через СберБизнес ID",()->{
            kraken.await().fluently(ExpectedConditions
                    .visibilityOfElementLocated(Elements.Modals.AuthModal.sberBussinesIdButton().getLocator()));
            baseChecks.checkIsElementEnabled(Elements.Modals.AuthModal.sberBussinesIdButton());
                });
        step("Снятие чекбокса В2В", ()->kraken.perform().click(Elements.Modals.AuthModal.checkB2B()));
        kraken.await().fluently(ExpectedConditions
                .visibilityOfElementLocated(Elements.Modals.AuthModal.sberButton().getLocator()));
        step("Проверка доступности кнопок авторизации провайдера",()->{
            baseChecks.checkIsElementEnabled(Elements.Modals.AuthModal.sberButton());
            baseChecks.checkIsElementEnabled(Elements.Modals.AuthModal.vkontakteButton());
            baseChecks.checkIsElementEnabled(Elements.Modals.AuthModal.facebookButton());
            baseChecks.checkIsElementEnabled(Elements.Modals.AuthModal.mailruButton());
        });
    }
}
