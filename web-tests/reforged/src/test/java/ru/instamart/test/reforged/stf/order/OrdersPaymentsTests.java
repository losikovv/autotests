package ru.instamart.test.reforged.stf.order;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.Juridical;
import ru.instamart.kraken.data.JuridicalData;
import ru.instamart.kraken.data.PaymentCards;
import ru.instamart.kraken.data.TestVariables;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.CookieFactory;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Покупка товара")
public final class OrdersPaymentsTests {

    private final ApiHelper helper = new ApiHelper();
    private UserData ordersUser;
    private Juridical company;

    @BeforeMethod(alwaysRun = true, description = "Шаги предусловия")
    public void beforeTest() {
        this.company = JuridicalData.juridical();
        this.ordersUser = UserManager.getQaUser();
        this.helper.dropAndFillCart(ordersUser, EnvironmentProperties.DEFAULT_SID);
    }

    @AfterMethod(alwaysRun = true, description = "Отмена ордера")
    public void afterTest() {
        this.helper.cancelAllActiveOrders(ordersUser);
    }

    @CaseId(1624)
    @Story("Тест заказа с оплатой картой онлайн")
    @Test(description = "Тест заказа с оплатой картой онлайн", groups = {"smoke","regression"})
    public void successOrderWithCardOnline() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(ordersUser);
        shop().interactHeader().checkProfileButtonVisible();
        shop().addCookie(CookieFactory.COOKIE_ALERT);

        checkout().goToPage();
        checkout().setDeliveryOptions().clickToForBusiness();
        checkout().setDeliveryOptions().clickToAddCompany();

        checkout().interactAddCompanyModal().fillCompany(company);
        checkout().interactAddCompanyModal().clickToOkButton();

        checkout().setDeliveryOptions().fillApartment(company.getJuridicalAddress());
        checkout().setDeliveryOptions().clickToSubmitForDelivery();

        checkout().setContacts().fillContactInfo();
        checkout().setContacts().clickToSubmit();

        checkout().setReplacementPolicy().clickToSubmit();

        checkout().setSlot().setLastActiveSlot();

        checkout().setPayment().clickToByCardOnline();
        checkout().setPayment().clickToAddNewPaymentCard();

        checkout().interactAddPaymentCardModal().fillCardData(PaymentCards.testCardNo3dsWithSpasibo());
        checkout().interactAddPaymentCardModal().clickToSaveModal();

        checkout().setPayment().clickToSubmitFromCheckoutColumn();

        userShipments().checkPageContains(userShipments().pageUrl());
        userShipments().checkStatusShipmentReady();
        userShipments().checkPaymentMethodCardOnline();
    }

    @CaseId(1625)
    @Story("Тест заказа с оплатой картой курьеру")
    @Test(description = "Тест заказа с оплатой картой курьеру", groups = {"smoke","regression"})
    public void successOrderWithCardCourier() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(ordersUser);
        shop().interactHeader().checkProfileButtonVisible();
        shop().addCookie(CookieFactory.COOKIE_ALERT);

        checkout().goToPage();
        checkout().setDeliveryOptions().clickToForBusiness();
        checkout().setDeliveryOptions().clickToAddCompany();

        checkout().interactAddCompanyModal().fillCompany(company);
        checkout().interactAddCompanyModal().clickToOkButton();

        checkout().setDeliveryOptions().fillApartment(company.getJuridicalAddress());
        checkout().setDeliveryOptions().clickToSubmitForDelivery();

        checkout().setContacts().fillContactInfo();
        checkout().setContacts().clickToSubmit();

        checkout().setReplacementPolicy().clickToSubmit();

        checkout().setSlot().setLastActiveSlot();

        checkout().setPayment().clickToByCardToCourier();
        checkout().setPayment().clickToSubmitFromCheckoutColumn();

        userShipments().checkPageContains(userShipments().pageUrl());

        userShipments().checkStatusShipmentReady();
        userShipments().checkPaymentMethodCardToCourier();
    }

    @CaseId(1626)
    @Story("Тест заказа с оплатой банковским переводом")
    @Test(description = "Тест заказа с оплатой банковским переводом", groups = "regression")
    public void successOrderWithBankTransfer() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(ordersUser);
        shop().interactHeader().checkProfileButtonVisible();
        shop().addCookie(CookieFactory.COOKIE_ALERT);

        checkout().goToPage();
        checkout().setDeliveryOptions().clickToForBusiness();
        checkout().setDeliveryOptions().clickToAddCompany();

        checkout().interactAddCompanyModal().fillCompany(company);
        checkout().interactAddCompanyModal().clickToOkButton();

        checkout().setDeliveryOptions().fillApartment(company.getJuridicalAddress());
        checkout().setDeliveryOptions().clickToSubmitForDelivery();

        checkout().setContacts().fillContactInfo();
        checkout().setContacts().clickToSubmit();

        checkout().setReplacementPolicy().clickToSubmit();

        checkout().setSlot().setLastActiveSlot();

        checkout().setPayment().clickToByBusinessAccount();

        checkout().editCompany().fillName(company.getJuridicalName());
        checkout().editCompany().fillAddress(company.getJuridicalAddress());
        checkout().editCompany().fillKpp(company.getKpp());
        checkout().editCompany().saveCompanyInfo();

        checkout().setPayment().clickToSubmitFromCheckoutColumn();

        userShipments().checkPageContains(userShipments().pageUrl());

        userShipments().checkStatusShipmentReady();
        userShipments().checkPaymentMethodForBusiness();
    }

    @CaseIDs({@CaseId(3238), @CaseId(3235)})
    @Story("Ошибки валидации")
    @Test(description = "Добавление новой карты после попытки ввода некорректного номера карты", groups = "regression")
    public void testAddPaymentCardAfterCardNumberValidation() {
        final var data = TestVariables.testAddressData();

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(ordersUser);
        shop().interactHeader().checkProfileButtonVisible();
        shop().addCookie(CookieFactory.COOKIE_ALERT);

        checkout().goToPage();
        checkout().setDeliveryOptions().clickToForSelf();
        checkout().setDeliveryOptions().fillDeliveryAddress(data);
        checkout().setDeliveryOptions().clickToSubmitForDelivery();

        checkout().setContacts().fillContactInfo(ordersUser);
        checkout().setContacts().clickToSubmit();

        checkout().setReplacementPolicy().clickToSubmit();

        checkout().setSlot().setLastActiveSlot();

        checkout().setPayment().clickToByCardOnline();
        checkout().setPayment().clickToAddNewPaymentCard();

        checkout().interactAddPaymentCardModal().fillCardNumber("1111 1111 1111 1111");
        checkout().interactAddPaymentCardModal().fillExpMonth("12");
        checkout().interactAddPaymentCardModal().fillExpYear("24");
        checkout().interactAddPaymentCardModal().fillHolderName("IVANOV IVAN");
        checkout().interactAddPaymentCardModal().fillCvv("123");
        checkout().interactAddPaymentCardModal().clickToSaveModal();
        checkout().interactAddPaymentCardModal().checkErrorAddingCardDisplayed("Ошибка добавления карты");

        checkout().interactAddPaymentCardModal().fillCardNumber("4111 1111 1111 1111");
        checkout().interactAddPaymentCardModal().clickToSaveModal();

        checkout().interactAddPaymentCardModal().checkModalWindowNotVisible();
    }

    @CaseIDs({ @CaseId(3236), @CaseId(3239)})
    @Story("Ошибки валидации")
    @Test(description = "Добавление новой карты. Некорректная дата окончания действия карты", groups = "regression")
    public void testAddPaymentCardAfterCardExpireDataValidation() {
        final var data = TestVariables.testAddressData();

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(ordersUser);
        shop().interactHeader().checkProfileButtonVisible();
        shop().addCookie(CookieFactory.COOKIE_ALERT);

        checkout().goToPage();
        checkout().setDeliveryOptions().clickToForSelf();
        checkout().setDeliveryOptions().fillDeliveryAddress(data);
        checkout().setDeliveryOptions().clickToSubmitForDelivery();

        checkout().setContacts().fillContactInfo(ordersUser);
        checkout().setContacts().clickToSubmit();

        checkout().setReplacementPolicy().clickToSubmit();

        checkout().setSlot().setLastActiveSlot();

        checkout().setPayment().clickToByCardOnline();
        checkout().setPayment().clickToAddNewPaymentCard();

        checkout().interactAddPaymentCardModal().fillCardNumber("4242 4242 4242 4242");
        checkout().interactAddPaymentCardModal().fillExpMonth("0");
        checkout().interactAddPaymentCardModal().fillExpYear("21");
        checkout().interactAddPaymentCardModal().fillCvv("1");
        checkout().interactAddPaymentCardModal().fillHolderName("TEST TEST");
        checkout().interactAddPaymentCardModal().checkValidationErrorVisible("Некорректный месяц");
        checkout().interactAddPaymentCardModal().checkValidationErrorVisible("Некорректный год");
        checkout().interactAddPaymentCardModal().checkValidationErrorVisible("Код CVV должен содержать 3 символа");

        checkout().interactAddPaymentCardModal().fillExpMonth("12");
        checkout().interactAddPaymentCardModal().fillExpYear("49");
        checkout().interactAddPaymentCardModal().fillHolderName("IVANOV IVAN");
        checkout().interactAddPaymentCardModal().fillCvv("404");
        checkout().interactAddPaymentCardModal().clickToSaveModal();

        checkout().interactAddPaymentCardModal().checkModalWindowNotVisible();
    }

    @CaseIDs({@CaseId(3239), @CaseId(3237)})
    @Story("Ошибки валидации")
    @Test(description = "Добавление новой карты после попытки ввода некорректного имя держателя", groups = "regression")
    public void testAddPaymentCardAfterCardHolderValidation() {
        final var data = TestVariables.testAddressData();

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(ordersUser);
        shop().interactHeader().checkProfileButtonVisible();
        shop().addCookie(CookieFactory.COOKIE_ALERT);

        checkout().goToPage();
        checkout().setDeliveryOptions().clickToForSelf();
        checkout().setDeliveryOptions().fillDeliveryAddress(data);
        checkout().setDeliveryOptions().clickToSubmitForDelivery();

        checkout().setContacts().fillContactInfo(ordersUser);
        checkout().setContacts().clickToSubmit();

        checkout().setReplacementPolicy().clickToSubmit();

        checkout().setSlot().setLastActiveSlot();

        checkout().setPayment().clickToByCardOnline();
        checkout().setPayment().clickToAddNewPaymentCard();

        checkout().interactAddPaymentCardModal().fillCardData(PaymentCards.testCard());
        checkout().interactAddPaymentCardModal().fillHolderName("ТЕСТ");
        checkout().interactAddPaymentCardModal().checkValidationErrorVisible("Только латинские буквы и пробел");
        checkout().interactAddPaymentCardModal().fillHolderName("");
        checkout().interactAddPaymentCardModal().checkValidationErrorVisible("Укажите имя владельца кредитной карты");

        checkout().interactAddPaymentCardModal().fillHolderName("TEST TEST");
        checkout().interactAddPaymentCardModal().clickToSaveModal();

        checkout().interactAddPaymentCardModal().checkModalWindowNotVisible();
    }
}
