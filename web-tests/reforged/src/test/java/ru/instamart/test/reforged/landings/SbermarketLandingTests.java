package ru.instamart.test.reforged.landings;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;

import org.testng.annotations.Test;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.stf.page.StfRouter.home;

@Epic("STF UI")
@Feature("Проверка лендингов")
public class SbermarketLandingTests extends BaseTest {

    @CaseId(1687)
    @Test(
            description = "Тест валидности и наличия элементов лендинга Сбермаркета",
            groups = {"sbermarket-Ui-smoke"}
    )
    public void successValidateSbermarketLanding() {
        home().goToPage();
        home().checkHeaderContainerIsVisible();
        home().checkHeaderLogoIsVisible();
        home().checkLoginButtonIsVisible();

        home().checkMainBlockContainerIsVisible();
        home().checkMainBlockIllustrationIsVisible();
        home().checkMainBlockAddressButtonIsVisible();
        home().checkMainBlockTextIsVisible();

        home().checkStoresListIsVisible();
        home().checkStoresButtonIsVisible();
        home().checkShowAllRetailerslIsVisible();
        home().clickToShowAllRetailers();
        home().checkStoresButtonAuchanIsVisible();
        home().checkStoresButtonMetroIsVisible();
        home().checkStoresButtonLentaIsVisible();

        home().checkAdvantagesBlockContainerIsVisible();
        home().checkDeliveryAdvIsVisible();
        home().checkHeavyToDoorAdvIsVisible();
        home().checkGoodQualityAdvIsVisible();
        home().checkSaleAdvIsVisible();

        home().checkZonesBlockContainerIsVisible();
        home().checkZonesBlockShowAllButtonIsVisible();

        home().checkOrderBlockContainerIsVisible();
        home().checkOrderBlockStepFirstIsVisible();
        home().checkOrderBlockStepSecondIsVisible();
        home().checkOrderBlockStepThirdIsVisible();


        home().checkAppsBlockContainerIsVisible();
        home().checkAppStoreIsVisible();
        home().checkGooglePlayIsVisible();
        home().checkAppGalleryContainerIsVisible();

        home().checkFooterContainerIsVisible();
    }

    @CaseId(1683)
    @Test(
            description = "Тест перехода в каталог магазина с лендинга Сбермаркета",
            groups = {"sbermarket-Ui-smoke"}
    )
    public void successGoToCatalogFromSbermarketLanding() {
//        ShippingAddressModal.selectFirstRetailer();
//        ShippingAddressModal.fill(Addresses.Moscow.defaultAddress());
//        ShippingAddressModal.selectAddressSuggest();
//        ShippingAddressModal.findShops();
//        baseChecks.checkIsOnLanding();
    }

    @CaseId(2042)
    @Test(
            description = "Кнопка СберБизнес ID при выбранном чекбоксе \"Хочу заказывать для бизнеса\"",
            groups = {"sbermarket-Ui-smoke", "testing"}
    )
    public void EnabledSberBussinesIdButton() {
//        Shop.AuthModal.open();
//        step("Выбор чекбокса В2В", () -> kraken.perform().click(Elements.Modals.AuthModal.checkB2B()));
//        step("Проверка наличия кнопки авторизации через СберБизнес ID", () -> {
//            kraken.await().fluently(ExpectedConditions
//                    .visibilityOfElementLocated(Elements.Modals.AuthModal.sberBussinesIdButton().getLocator()));
//            baseChecks.checkIsElementEnabled(Elements.Modals.AuthModal.sberBussinesIdButton());
//        });
//        step("Снятие чекбокса В2В", () -> kraken.perform().click(Elements.Modals.AuthModal.checkB2B()));
//        kraken.await().fluently(ExpectedConditions
//                .visibilityOfElementLocated(Elements.Modals.AuthModal.sberButton().getLocator()));
//        step("Проверка доступности кнопок авторизации провайдера", () -> {
//            baseChecks.checkIsElementEnabled(Elements.Modals.AuthModal.sberButton());
//            baseChecks.checkIsElementEnabled(Elements.Modals.AuthModal.vkontakteButton());
//            baseChecks.checkIsElementEnabled(Elements.Modals.AuthModal.facebookButton());
//            baseChecks.checkIsElementEnabled(Elements.Modals.AuthModal.mailruButton());
//        });
//    }
    }
}
