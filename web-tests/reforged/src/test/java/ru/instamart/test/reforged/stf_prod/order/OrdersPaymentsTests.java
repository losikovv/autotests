package ru.instamart.test.reforged.stf_prod.order;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.Juridical;
import ru.instamart.kraken.data.JuridicalData;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.core.config.UiProperties;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.Group.STF_PROD_S;
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
        this.helper.dropAndFillCart(ordersUser, UiProperties.DEFAULT_SID);
    }

    @AfterMethod(alwaysRun = true, description = "Отмена ордера")
    public void afterTest() {
        this.helper.cancelAllActiveOrders(ordersUser);
    }

    @CaseId(1625)
    @Story("Тест заказа с оплатой картой курьеру")
    @Test(description = "Тест заказа с оплатой картой курьеру", groups = {STF_PROD_S})
    public void successOrderWithCardCourier() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(ordersUser);
        shop().interactHeader().checkProfileButtonVisible();

        checkout().goToPage();
        checkout().setDeliveryOptions().clickToForSelf();
        checkout().setDeliveryOptions().fillComments("test");
        checkout().setDeliveryOptions().clickToSubmitForDelivery();

        checkout().setContacts().fillContactInfo();
        checkout().setContacts().clickToSubmit();

        checkout().setReplacementPolicy().clickToSubmit();

        checkout().setSlot().setLastActiveSlot();

        checkout().setPayment().clickToByCardToCourier();
        checkout().setPayment().clickToSubmitFromCheckoutColumn();

        userShipments().checkPageContains(userShipments().pageUrl());

        userShipments().checkStatusAcceptedProd();
        userShipments().checkPaymentMethodEquals("Картой курьеру");
    }

    @CaseId(1626)
    @Story("Тест заказа с оплатой банковским переводом")
    @Test(description = "Тест заказа с оплатой банковским переводом", groups = {STF_PROD_S})
    public void successOrderWithBankTransfer() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(ordersUser);
        shop().interactHeader().checkProfileButtonVisible();

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

        checkout().setSlot().checkSlotsSpinnerIsNotVisible();
        checkout().setSlot().setNextDay();
        checkout().setSlot().checkSlotsSpinnerIsNotVisible();
        checkout().setSlot().setLastActiveSlot();

        checkout().setPayment().clickToByBusinessAccount();

        checkout().editCompany().fillName(company.getJuridicalName());
        checkout().editCompany().fillAddress(company.getJuridicalAddress());
        checkout().editCompany().fillKpp(company.getKpp());

        checkout().editCompany().fillConsigneeName(company.getJuridicalName());
        checkout().editCompany().fillConsigneeAddress(company.getJuridicalAddress());
        checkout().editCompany().fillConsigneeKpp(company.getKpp());

        checkout().editCompany().saveCompanyInfo();

        checkout().setPayment().clickToSubmitFromCheckoutColumn();

        userShipments().checkPageContains(userShipments().pageUrl());

        userShipments().checkStatusAcceptedProd();
        userShipments().checkPaymentMethodEquals("По счёту для бизнеса");
    }
}
