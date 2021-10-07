package ru.instamart.test.reforged.stf.order;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.testdata.*;
import ru.instamart.reforged.CookieFactory;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.stf.page.StfRouter.*;
import static ru.instamart.reforged.stf.page.StfRouter.userShipments;

@Epic("STF UI")
@Feature("Покупка товара")
public class
OrdersPaymentsTests extends BaseTest {

    private final ApiHelper helper = new ApiHelper();

    @Test(
            description = "Тест заказа с оплатой картой онлайн",
            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance", "sbermarket-regression"
            }
    )
    public void successOrderWithCardOnline() {
        final UserData ordersUser = UserManager.getUser();
        var company = UserManager.juridical();
        var card = PaymentCards.testCardNo3ds();

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

        checkout().setPayment().clickToByCardOnline();
        checkout().setPayment().clickToAddNewPaymentCard();

        checkout().interactEditPaymentCardModal().fillCardNumber(card.getCardNumber());
        checkout().interactEditPaymentCardModal().fillExpMonth(card.getExpiryMonth());
        checkout().interactEditPaymentCardModal().fillExpYear(card.getExpiryYear());
        checkout().interactEditPaymentCardModal().fillCvv(card.getCvvNumber());
        checkout().interactEditPaymentCardModal().fillHolderName(card.getCardholderName());
        checkout().interactEditPaymentCardModal().clickToSaveModal();

        checkout().setPayment().clickToSubmitFromCheckoutColumn();

        userShipments().checkPageContains(userShipments().pageUrl());
        userShipments().checkStatusShipmentReady();
        userShipments().checkPaymentMethodCardOnline();

        helper.cancelAllActiveOrders(ordersUser);
    }

    @Test(
            description = "Тест заказа с оплатой картой курьеру",
            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance", "sbermarket-regression"
            }
    )
    public void successOrderWithCardCourier() {
        final UserData ordersUser = UserManager.getUser();
        var company = UserManager.juridical();

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
        checkout().setPayment().clickToSubmitFromCheckoutColumn();

        userShipments().checkPageContains(userShipments().pageUrl());

        userShipments().checkStatusShipmentReady();
        userShipments().checkPaymentMethodCardToCourier();

        helper.cancelAllActiveOrders(ordersUser);
    }

    @Test(
            description = "Тест заказа с оплатой банковским переводом",
            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance", "sbermarket-regression"
            }
    )
    public void successOrderWithBankTransfer() {
        final UserData ordersUser = UserManager.getUser();
        var company = UserManager.juridical();

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

        checkout().setPayment().clickToByBusinessAccount();
        checkout().setSlot().setAnotherSlot();
        checkout().setSlot().setFirstActiveSlot();

        checkout().editCompany().fillName(company.getJuridicalName());
        checkout().editCompany().fillAddress(company.getJuridicalAddress());
        checkout().editCompany().fillKpp(company.getKpp());
        checkout().editCompany().save();

        checkout().setPayment().clickToSubmitFromCheckoutColumn();

        userShipments().checkPageContains(userShipments().pageUrl());

        userShipments().checkStatusShipmentReady();
        userShipments().checkPaymentMethodForBusiness();

        helper.cancelAllActiveOrders(ordersUser);
    }
}
