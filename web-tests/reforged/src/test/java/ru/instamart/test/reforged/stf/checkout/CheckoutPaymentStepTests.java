package ru.instamart.test.reforged.stf.checkout;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.JuridicalData;
import ru.instamart.kraken.data.PaymentCards;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.CookieFactory;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.sber_payments.SberPaymentsPageRouter.sberPayments;
import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Чекаут. Шаг #5. Оплата")
public class CheckoutPaymentStepTests {

    private final ApiHelper helper = new ApiHelper();
    private UserData userData;

    @AfterMethod(alwaysRun = true, description = "Отмена ордера")
    public void afterTest() {
        helper.cancelAllActiveOrders(userData);
    }

    @CaseId(1678)
    @Story("Корзина")
    @Test(description = "Тест удаления карты оплаты", groups = "regression")
    public void successDeleteSavedCard() {
        //TODO: после починки addCreditCard() нужно добавить сюда добавление карты через апи
        userData = UserManager.getQaUser();
        helper.dropAndFillCart(userData, EnvironmentProperties.DEFAULT_SID);

        var company = JuridicalData.juridical();
        var card = PaymentCards.testCard();

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();
        shop().addCookie(CookieFactory.COOKIE_ALERT);

        checkout().goToPage();
        checkout().setDeliveryOptions().clickToForBusiness();
        checkout().setDeliveryOptions().clickToAddCompany();

        checkout().interactAddCompanyModal().fillCompany(company);
        checkout().interactAddCompanyModal().clickToOkButton();

        checkout().setDeliveryOptions().fillApartment(company.getJuridicalAddress());
        checkout().setDeliveryOptions().clickToSubmitForDelivery();

        checkout().checkCheckoutLoaderNotVisible();

        checkout().setContacts().fillContactInfo();
        checkout().setContacts().clickToSubmit();

        checkout().setReplacementPolicy().clickToSubmit();

        checkout().setSlot().setLastActiveSlot();

        checkout().setPayment().clickToByCardOnline();
        checkout().setPayment().clickToAddNewPaymentCard();

        checkout().interactAddPaymentCardModal().fillCardData(card);
        checkout().interactAddPaymentCardModal().clickToSaveModal();

        checkout().setPayment().clickToSubmitFromCheckoutColumn();

        sberPayments().checkPageContains(EnvironmentProperties.Env.DEMO_RBSUAT_PAYMENTS_URL + "acs");
        sberPayments().fillPassword(card.getPassword());

        userShipments().checkPageContains(userShipments().pageUrl());

        helper.dropAndFillCart(userData, EnvironmentProperties.DEFAULT_SID);

        company = JuridicalData.juridical();

        checkout().goToPage();
        checkout().setDeliveryOptions().clickToForBusiness();

        checkout().waitPageLoad();
        checkout().setDeliveryOptions().clickToAddCompany();

        checkout().interactAddCompanyModal().fillCompany(company);
        checkout().interactAddCompanyModal().clickToOkButton();

        checkout().setDeliveryOptions().fillApartment(company.getJuridicalAddress());
        checkout().setDeliveryOptions().clickToSubmitForDelivery();

        checkout().checkCheckoutLoaderNotVisible();

        checkout().setContacts().fillContactInfo();
        checkout().setContacts().clickToSubmit();

        checkout().setReplacementPolicy().clickToSubmit();

        checkout().setSlot().setLastActiveSlot();

        checkout().setPayment().clickToByCardOnline();

        checkout().setPayment().clickToChangePaymentCard(card);

        checkout().interactEditPaymentCardModal().checkModalNotAnimated();
        checkout().interactEditPaymentCardModal().clickToDeleteModal();
        checkout().setPayment().checkSubmitOrderButtonNotClickable();
    }
}
