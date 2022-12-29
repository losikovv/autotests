package ru.instamart.test.reforged.stf.order;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.JuridicalData;
import ru.instamart.kraken.data.PromoData;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.core.data_provider.PromoCodeProvider;
import io.qameta.allure.TmsLinks;
import io.qameta.allure.TmsLink;

import static ru.instamart.kraken.util.TimeUtil.getPastZoneDbDate;
import static ru.instamart.reforged.Group.REGRESSION_STF;
import static ru.instamart.reforged.core.config.UiProperties.DEFAULT_AUCHAN_SID;
import static ru.instamart.reforged.core.config.UiProperties.DEFAULT_SID;
import static ru.instamart.reforged.stf.enums.ShipmentStates.ACCEPTED_STATE;
import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Покупка товара")
public final class OrdersPromoCodesTests {

    private final ApiHelper helper = new ApiHelper();
    private UserData ordersUser;

    @AfterMethod(alwaysRun = true, description = "Отмена ордера")
    public void afterTest() {
        helper.cancelAllActiveOrders(ordersUser);
    }

    @TmsLinks(value = {@TmsLink("1638"), @TmsLink("1639"), @TmsLink("1640"),
            @TmsLink("1645"), @TmsLink("1646"), @TmsLink("1647"),
            @TmsLink("1648"), @TmsLink("1649")})
    @Story("Тест применения промокода со скидкой")
    @Test(description = "Тест применения различных промокодов",
            dataProviderClass = PromoCodeProvider.class,
            dataProvider = "promo_code",
            groups = REGRESSION_STF)
    public void successOrderWithPromoCode(final PromoData data) {
        var company = JuridicalData.juridical();

        ordersUser = UserManager.getQaUser();
        helper.dropAndFillCart(ordersUser, DEFAULT_AUCHAN_SID);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(ordersUser);
        shop().interactHeader().checkProfileButtonVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().submitOrder();

        checkoutNew().waitPageLoad();
        checkoutNew().clickOrderForBusiness();
        checkoutNew().interactB2BOrderModal().clickConfirm();
        checkoutNew().interactB2BOrderModal().checkModalNotVisible();

        checkout().waitPageLoad();
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

        checkout().clickToAddPromoCode();
        checkout().interactEditPromoCodeModal().enterPromoCode(data.getCode());
        checkout().interactEditPromoCodeModal().applyPromoCode();
        checkout().checkPromoCodeApplied();

        checkout().setPayment().clickToSubmitFromCheckoutColumn();

        userShipment().waitPageLoad();
        userShipment().checkActiveShipmentState(ACCEPTED_STATE.getName());
        userShipment().checkPromoCodeVisible();
    }

    @TmsLink("1641")
    @Story("Тест применения промокода")
    @Test(description = "Тест применения промокода со фиксированной ограниченной скидкой", groups = REGRESSION_STF)
    public void successOrderWithPromoCodeFixedDiscountWithBorders() {
        var company = JuridicalData.juridical();
        var promo = "test_prefix" + Generate.literalString(5) + Generate.string(1);
        final String yesterday = getPastZoneDbDate(1L);

        helper.createPromotionCode(promo, 2760, yesterday, yesterday, 100);

        ordersUser = UserManager.getQaUser();
        helper.dropAndFillCart(ordersUser, DEFAULT_AUCHAN_SID);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(ordersUser);
        shop().interactHeader().checkProfileButtonVisible();

        checkout().goToPage();
        checkout().checkCheckoutButtonIsVisible();
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

        checkout().clickToAddPromoCode();
        checkout().interactEditPromoCodeModal().enterPromoCode(promo);
        checkout().interactEditPromoCodeModal().applyPromoCode();
        checkout().checkPromoCodeApplied();

        checkout().setPayment().clickToSubmitFromCheckoutColumn();

        userShipments().checkPageContains(userShipments().pageUrl());

        userShipments().checkStatusShipmentReady();
        userShipments().checkUserShipmentPromocodeVisible();
        userShipments().checkUserShipmentPromocodeCorrect(100.0);
    }

    @TmsLink("1642")
    @Story("Тест применения промокода")
    @Test(description = "Тест применения промокода на первый заказ старым пользователем", groups = REGRESSION_STF)
    public void failedOrderForOldUserWithFirstOrderPromo() {
        var company = JuridicalData.juridical();
        var promo = "test_prefix" + Generate.literalString(5) + Generate.string(1);
        final String yesterday = getPastZoneDbDate(1L);

        ordersUser = UserManager.getQaUser();

        helper.makeOrder(ordersUser, DEFAULT_AUCHAN_SID, 1);
        helper.createPromotionCode(promo, 2761, yesterday, yesterday, 100);
        helper.dropAndFillCart(ordersUser, DEFAULT_AUCHAN_SID);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(ordersUser);
        shop().interactHeader().checkProfileButtonVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().submitOrder();

        checkoutNew().waitPageLoad();
        checkoutNew().clickOrderForBusiness();
        checkoutNew().interactB2BOrderModal().clickConfirm();
        checkoutNew().interactB2BOrderModal().checkModalNotVisible();

        checkout().waitPageLoad();
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

        checkout().clickToAddPromoCode();
        checkout().interactEditPromoCodeModal().enterPromoCode(promo);
        checkout().interactEditPromoCodeModal().applyPromoCode();
        checkout().interactEditPromoCodeModal().checkFirstOrderAlertVisible();
        checkout().interactEditPromoCodeModal().cancelPromoCode();
        checkout().checkPromoCodeNotApplied();
    }

    @TmsLink("2640")
    @Story("Тест применения промокода")
    @Test(description = "Тест применения несуществующего промокода", groups = REGRESSION_STF)
    public void failedOrderWithNonExistingPromo() {
        var company = JuridicalData.juridical();
        var promo = Generate.string(8);

        ordersUser = UserManager.getQaUser();
        helper.dropAndFillCart(ordersUser, DEFAULT_AUCHAN_SID);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(ordersUser);
        shop().interactHeader().checkProfileButtonVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().submitOrder();

        checkoutNew().waitPageLoad();
        checkoutNew().clickOrderForBusiness();
        checkoutNew().interactB2BOrderModal().clickConfirm();
        checkoutNew().interactB2BOrderModal().checkModalNotVisible();

        checkout().waitPageLoad();
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

        checkout().clickToAddPromoCode();
        checkout().interactEditPromoCodeModal().enterPromoCode(promo);
        checkout().interactEditPromoCodeModal().applyPromoCode();
        checkout().interactEditPromoCodeModal().checkNonExistAlertVisible();
        checkout().interactEditPromoCodeModal().cancelPromoCode();
        checkout().checkPromoCodeNotApplied();
    }
}

