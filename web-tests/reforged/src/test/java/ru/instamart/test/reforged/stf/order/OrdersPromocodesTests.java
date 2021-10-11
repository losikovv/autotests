package ru.instamart.test.reforged.stf.order;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.testdata.Generate;
import ru.instamart.kraken.testdata.UserData;
import ru.instamart.kraken.testdata.UserManager;
import ru.instamart.kraken.testdata.lib.Promos;
import ru.instamart.reforged.CookieFactory;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.stf.page.StfRouter.*;
import static ru.instamart.reforged.stf.page.StfRouter.userShipments;

@Epic("STF UI")
@Feature("Покупка товара")
public class OrdersPromocodesTests extends BaseTest {

    private final ApiHelper helper = new ApiHelper();
    private UserData ordersUser;

    @AfterMethod(alwaysRun = true, description = "Отмена ордера")
    public void afterTest() {
        helper.cancelAllActiveOrders(ordersUser);
    }

    @CaseId(1645)
    @Story("Тест применения промокода со скидкой на первый заказ")
    @Test(
            description = "Тест применения промокода со скидкой на первый заказ",
            groups = {"sbermarket-regression"}
    )
    public void successOrderWithFirstOrderPromo() {
        var company = UserManager.juridical();
        final String promoCode = Promos.fixedDiscountOnFirstOrder().getCode();

        ordersUser = UserManager.getUser();
        helper.dropAndFillCart(ordersUser, 1);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(ordersUser);
        shop().interactHeader().checkProfileButtonVisible();
        shop().addCookie(CookieFactory.COOKIE_ALERT);

        checkout().goToPage();
        checkout().setDeliveryOptions().clickToForBusiness();
        checkout().setDeliveryOptions().clickToAddCompany();

        checkout().interactAddCompanyModal().fillInn(company.getInn());
        checkout().interactAddCompanyModal().clickToSubmit();
        checkout().interactAddCompanyModal().fillName(company.getJuridicalName());
        checkout().interactAddCompanyModal().clickToSubmit();
        checkout().interactAddCompanyModal().clickToOkButton();

        checkout().setDeliveryOptions().fillApartment(company.getJuridicalAddress());
        checkout().setDeliveryOptions().clickToSubmitForDelivery();

        checkout().setContacts().fillFirstName(Generate.literalString(8));
        checkout().setContacts().fillLastName(Generate.literalString(8));
        checkout().setContacts().fillEmail(Generate.email());
        checkout().setContacts().clickToSubmit();

        checkout().setReplacementPolicy().clickToSubmit();

        checkout().setSlot().setFirstActiveSlot();

        checkout().setPayment().clickToByCardToCourier();

        checkout().clickToAddPromoCode();
        checkout().interactEditPromoCodeModal().enterPromoCode(promoCode);
        checkout().interactEditPromoCodeModal().applyPromoCode();
        checkout().checkPromoCodeApplied();

        checkout().setPayment().clickToSubmitFromCheckoutColumn();

        userShipments().checkPageContains(userShipments().pageUrl());

        userShipments().checkStatusShipmentReady();
        userShipments().checkUserShipmentPromocodeVisible();
    }

    @CaseId(1646)
    @Story("Тест заказа с промокодом на скидку для ритейлера")
    @Test(
            description = "Тест заказа с промокодом на скидку для ритейлера",
            groups = {"sbermarket-regression"}
    )
    public void successOrderWithRetailerPromo() {
        var company = UserManager.juridical();
        final String promoCode = Promos.fixedDiscountForRetailer("metro").getCode();

        ordersUser = UserManager.getUser();
        helper.dropAndFillCart(ordersUser, 1);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(ordersUser);
        shop().interactHeader().checkProfileButtonVisible();
        shop().addCookie(CookieFactory.COOKIE_ALERT);

        checkout().goToPage();
        checkout().setDeliveryOptions().clickToForBusiness();
        checkout().setDeliveryOptions().clickToAddCompany();

        checkout().interactAddCompanyModal().fillInn(company.getInn());
        checkout().interactAddCompanyModal().clickToSubmit();
        checkout().interactAddCompanyModal().fillName(company.getJuridicalName());
        checkout().interactAddCompanyModal().clickToSubmit();
        checkout().interactAddCompanyModal().clickToOkButton();

        checkout().setDeliveryOptions().fillApartment(company.getJuridicalAddress());
        checkout().setDeliveryOptions().clickToSubmitForDelivery();

        checkout().setContacts().fillFirstName(Generate.literalString(8));
        checkout().setContacts().fillLastName(Generate.literalString(8));
        checkout().setContacts().fillEmail(Generate.email());
        checkout().setContacts().clickToSubmit();

        checkout().setReplacementPolicy().clickToSubmit();

        checkout().setSlot().setFirstActiveSlot();

        checkout().setPayment().clickToByCardToCourier();

        checkout().clickToAddPromoCode();
        checkout().interactEditPromoCodeModal().enterPromoCode(promoCode);
        checkout().interactEditPromoCodeModal().applyPromoCode();
        checkout().checkPromoCodeApplied();

        checkout().setPayment().clickToSubmitFromCheckoutColumn();

        userShipments().checkPageContains(userShipments().pageUrl());


        userShipments().checkStatusShipmentReady();
        userShipments().checkUserShipmentPromocodeVisible();
    }

    @CaseId(1647)
    @Story("Тест заказа с промокодом на скидку для новых пользователей")
    @Test(
            description = "Тест заказа с промокодом на скидку для новых пользователей",
            groups = {"sbermarket-regression"}
    )
    public void successOrderWithNewUserPromo() {
        var company = UserManager.juridical();
        final String promoCode = Promos.fixedDiscountForNewUser().getCode();

        ordersUser = UserManager.getUser();
        helper.dropAndFillCart(ordersUser, 1);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(ordersUser);
        shop().interactHeader().checkProfileButtonVisible();
        shop().addCookie(CookieFactory.COOKIE_ALERT);

        checkout().goToPage();
        checkout().setDeliveryOptions().clickToForBusiness();
        checkout().setDeliveryOptions().clickToAddCompany();

        checkout().interactAddCompanyModal().fillInn(company.getInn());
        checkout().interactAddCompanyModal().clickToSubmit();
        checkout().interactAddCompanyModal().fillName(company.getJuridicalName());
        checkout().interactAddCompanyModal().clickToSubmit();
        checkout().interactAddCompanyModal().clickToOkButton();

        checkout().setDeliveryOptions().fillApartment(company.getJuridicalAddress());
        checkout().setDeliveryOptions().clickToSubmitForDelivery();

        checkout().setContacts().fillFirstName(Generate.literalString(8));
        checkout().setContacts().fillLastName(Generate.literalString(8));
        checkout().setContacts().fillEmail(Generate.email());
        checkout().setContacts().clickToSubmit();

        checkout().setReplacementPolicy().clickToSubmit();

        checkout().setSlot().setFirstActiveSlot();

        checkout().setPayment().clickToByCardToCourier();

        checkout().clickToAddPromoCode();
        checkout().interactEditPromoCodeModal().enterPromoCode(promoCode);
        checkout().interactEditPromoCodeModal().applyPromoCode();
        checkout().checkPromoCodeApplied();

        checkout().setPayment().clickToSubmitFromCheckoutColumn();

        userShipments().checkPageContains(userShipments().pageUrl());


        userShipments().checkStatusShipmentReady();
        userShipments().checkUserShipmentPromocodeVisible();
    }

    @CaseId(1648)
    @Story("Тест применения промокода на бесплатную доставку на первый заказ")
    @Test(
            description = "Тест применения промокода на бесплатную доставку на первый заказ",
            groups = {"sbermarket-regression"}
    )
    public void successOrderWithCertainOrderPromo() {
        var company = UserManager.juridical();
        final String promoCode = Promos.freeDeliveryOnFirstOrder().getCode();

        ordersUser = UserManager.getUser();
        helper.dropAndFillCart(ordersUser, 1);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(ordersUser);
        shop().interactHeader().checkProfileButtonVisible();
        shop().addCookie(CookieFactory.COOKIE_ALERT);

        checkout().goToPage();
        checkout().setDeliveryOptions().clickToForBusiness();
        checkout().setDeliveryOptions().clickToAddCompany();

        checkout().interactAddCompanyModal().fillInn(company.getInn());
        checkout().interactAddCompanyModal().clickToSubmit();
        checkout().interactAddCompanyModal().fillName(company.getJuridicalName());
        checkout().interactAddCompanyModal().clickToSubmit();
        checkout().interactAddCompanyModal().clickToOkButton();

        checkout().setDeliveryOptions().fillApartment(company.getJuridicalAddress());
        checkout().setDeliveryOptions().clickToSubmitForDelivery();

        checkout().setContacts().fillFirstName(Generate.literalString(8));
        checkout().setContacts().fillLastName(Generate.literalString(8));
        checkout().setContacts().fillEmail(Generate.email());
        checkout().setContacts().clickToSubmit();

        checkout().setReplacementPolicy().clickToSubmit();

        checkout().setSlot().setFirstActiveSlot();

        checkout().setPayment().clickToByCardToCourier();

        checkout().clickToAddPromoCode();
        checkout().interactEditPromoCodeModal().enterPromoCode(promoCode);
        checkout().interactEditPromoCodeModal().applyPromoCode();
        checkout().checkPromoCodeApplied();

        checkout().setPayment().clickToSubmitFromCheckoutColumn();

        userShipments().checkPageContains(userShipments().pageUrl());

        userShipments().checkStatusShipmentReady();
        userShipments().checkUserShipmentPromocodeVisible();
    }

    @CaseId(1649)
    @Story("Тест применения промокода на скидку на второй заказ")
    @Test(
            description = "Тест применения промокода на скидку на второй заказ",
            groups = {"sbermarket-regression"}
    )
    public void successOrderWithSeriesOfOrdersPromo() {
        var company = UserManager.juridical();
        final String promoCode = Promos.fixedDiscountForSerialOrder().getCode();

        ordersUser = UserManager.getUser();
        helper.makeOrder(ordersUser, EnvironmentProperties.DEFAULT_SID, 2);
        helper.dropAndFillCart(ordersUser, 1);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(ordersUser);
        shop().interactHeader().checkProfileButtonVisible();
        shop().addCookie(CookieFactory.COOKIE_ALERT);

        checkout().goToPage();
        checkout().setDeliveryOptions().clickToForBusiness();
        checkout().setDeliveryOptions().clickToAddCompany();

        checkout().interactAddCompanyModal().fillInn(company.getInn());
        checkout().interactAddCompanyModal().clickToSubmit();
        checkout().interactAddCompanyModal().fillName(company.getJuridicalName());
        checkout().interactAddCompanyModal().clickToSubmit();
        checkout().interactAddCompanyModal().clickToOkButton();

        checkout().setDeliveryOptions().fillApartment(company.getJuridicalAddress());
        checkout().setDeliveryOptions().clickToSubmitForDelivery();

        checkout().setContacts().fillFirstName(Generate.literalString(8));
        checkout().setContacts().fillLastName(Generate.literalString(8));
        checkout().setContacts().fillEmail(Generate.email());
        checkout().setContacts().clickToSubmit();

        checkout().setReplacementPolicy().clickToSubmit();

        checkout().setSlot().setFirstActiveSlot();

        checkout().setPayment().clickToByCardToCourier();

        checkout().clickToAddPromoCode();
        checkout().interactEditPromoCodeModal().enterPromoCode(promoCode);
        checkout().interactEditPromoCodeModal().applyPromoCode();
        checkout().checkPromoCodeApplied();

        checkout().setPayment().clickToSubmitFromCheckoutColumn();

        userShipments().checkPageContains(userShipments().pageUrl());

        userShipments().checkStatusShipmentReady();
        userShipments().checkUserShipmentPromocodeVisible();
    }
}




