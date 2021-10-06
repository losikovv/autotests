package ru.instamart.test.reforged.stf.order;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.listener.Skip;
import ru.instamart.kraken.testdata.Generate;
import ru.instamart.kraken.testdata.PaymentCards;
import ru.instamart.kraken.testdata.UserData;
import ru.instamart.kraken.testdata.UserManager;
import ru.instamart.reforged.CookieFactory;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Покупка товара")
public final class BasicOrdersTests extends BaseTest {

    private final ApiHelper helper = new ApiHelper();
    private final UserData userData = UserManager.getUser();

    @BeforeMethod(description = "Аутентификация и выбор адреса доставки")
    public void preconditions() {
        helper.auth(userData);
    }

    @AfterMethod(alwaysRun = true, description = "Отмена ордера")
    public void afterTest() {
        helper.cancelAllActiveOrders(userData);
    }

    @CaseId(1674)
    @Test(
            description = "Тест заказа с добавлением нового юр. лица",
            groups = {"sbermarket-regression"}
    )
    public void successCompleteCheckoutWithNewJuridical() {
        helper.dropAndFillCart(userData, EnvironmentProperties.DEFAULT_SID);

        var company = UserManager.juridical();

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
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

        checkout().setPayment().clickToSubmitFromCheckoutColumn();

        userShipments().checkPageContains(userShipments().pageUrl());
    }

    @CaseId(1672)
    @Test(
            description = "Тест заказа с новой картой оплаты c 3ds",
            groups = {"sbermarket-regression", "testing", "sbermarket-Ui-smoke"}
    )
    public void successCompleteCheckoutWithNewPaymentCard() {
        helper.dropAndFillCart(userData, EnvironmentProperties.DEFAULT_SID);

        var company = UserManager.juridical();
        var card = PaymentCards.testCard();

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
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

        checkout().clickToSubmitFromSidebar();

        checkout().checkPageContains("https://demo.cloudpayments.ru/acs");
    }

    @CaseId(2066)
    @Test(
            description = "Тест заказа с новой картой оплаты без 3ds",
            groups = {"sbermarket-regression", "testing", "sbermarket-Ui-smoke"}
    )
    public void successCompleteCheckoutWithNewNoSecurePaymentCard() {
        helper.dropAndFillCart(userData, EnvironmentProperties.DEFAULT_SID);

        var company = UserManager.juridical();
        var card = PaymentCards.testCardNo3ds();

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
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
    }

    @CaseId(1681)
    @Test(
            description = "Тест заказа с любимыми товарами",
            groups = {"sbermarket-regression", "sbermarket-Ui-smoke"}
    )
    public void successOrderWithFavProducts() {
        helper.addFavorites(userData, EnvironmentProperties.DEFAULT_SID, 1);
        helper.dropAndFillCartFromFavorites(userData, EnvironmentProperties.DEFAULT_SID);

        var company = UserManager.juridical();

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
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

        checkout().setPayment().clickToSubmitFromCheckoutColumn();

        userShipments().checkPageContains(userShipments().pageUrl());
    }

    @CaseId(1673)
    @Test(
            description = "Тест успешного заказа с оплатой картой курьеру",
            groups = {"sbermarket-regression","testing", "sbermarket-Ui-smoke"}
    )
    public void successOrderWithCardCourier() {
        helper.dropAndFillCart(userData, EnvironmentProperties.DEFAULT_SID);

        var company = UserManager.juridical();

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
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
    }
}
