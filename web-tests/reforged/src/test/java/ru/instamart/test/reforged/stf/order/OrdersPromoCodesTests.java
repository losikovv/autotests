package ru.instamart.test.reforged.stf.order;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.JuridicalData;
import ru.instamart.kraken.data.PromoData;
import ru.instamart.kraken.data.Promos;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.CookieFactory;
import ru.instamart.reforged.core.data_provider.PromoCodeProvider;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Покупка товара")
public final class OrdersPromoCodesTests extends BaseTest {

    private final ApiHelper helper = new ApiHelper();
    private UserData ordersUser;

    @AfterMethod(alwaysRun = true, description = "Отмена ордера")
    public void afterTest() {
        helper.cancelAllActiveOrders(ordersUser);
    }

    @CaseIDs(value = {@CaseId(1638), @CaseId(1639), @CaseId(1640),
            @CaseId(1645), @CaseId(1646), @CaseId(1647),
            @CaseId(1648), @CaseId(1649)})
    @Story("Тест применения промокода со скидкой")
    @Test(description = "Тест применения различных промокодов",
            dataProviderClass = PromoCodeProvider.class,
            dataProvider = "promo_code",
            groups = "regression")
    public void successOrderWithPromoCode(final PromoData data) {
        var company = JuridicalData.juridical();

        ordersUser = UserManager.getQaUser();
        helper.dropAndFillCart(ordersUser, EnvironmentProperties.DEFAULT_SID);

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

        checkout().setSlot().setFirstActiveSlot();

        checkout().setPayment().clickToByCardToCourier();

        checkout().clickToAddPromoCode();
        checkout().interactEditPromoCodeModal().enterPromoCode(data.getCode());
        checkout().interactEditPromoCodeModal().applyPromoCode();
        checkout().checkPromoCodeApplied();

        checkout().setPayment().clickToSubmitFromCheckoutColumn();

        userShipments().checkPageContains(userShipments().pageUrl());

        userShipments().checkStatusShipmentReady();
        userShipments().checkUserShipmentPromocodeVisible();
    }

    @CaseId(1641)
    @Story("Тест применения промокода")
    @Test(description = "Тест применения промокода со фиксированной ограниченной скидкой",
            groups = "regression")
    public void successOrderWithPromoCodeFixedDiscountWithBorders() {
        var company = JuridicalData.juridical();
        var promo = Promos.fixedDiscountForAllOrdersInPercentsBorders().getCode();

        ordersUser = UserManager.getQaUser();
        helper.dropAndFillCart(ordersUser, EnvironmentProperties.DEFAULT_SID);

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

        checkout().setSlot().setFirstActiveSlot();

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

    @CaseId(1642)
    @Story("Тест применения промокода")
    @Test(description = "Тест применения промокода на первый заказ старым пользователем",
            groups = "regression")
    public void failedOrderForOldUserWithFirstOrderPromo() {
        var company = JuridicalData.juridical();
        var promo = Promos.discountOnFirstOrder().getCode();

        ordersUser = UserManager.getQaUser();
        helper.makeOrder(ordersUser, EnvironmentProperties.DEFAULT_SID, 1);
        helper.dropAndFillCart(ordersUser, EnvironmentProperties.DEFAULT_SID);

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

        checkout().setSlot().setFirstActiveSlot();

        checkout().setPayment().clickToByCardToCourier();

        checkout().clickToAddPromoCode();
        checkout().interactEditPromoCodeModal().enterPromoCode(promo);
        checkout().interactEditPromoCodeModal().applyPromoCode();
        checkout().interactEditPromoCodeModal().checkFirstOrderAlertVisible();
        checkout().interactEditPromoCodeModal().cancelPromoCode();
        checkout().checkPromoCodeNotApplied();
    }

    @CaseId(1844)
    @Story("Тест применения промокода")
    @Test(description = "Тест применения несуществующего промокода",
            groups = "regression")
    public void failedOrderWithNonExistingPromo() {
        var company = JuridicalData.juridical();
        var promo = Generate.string(8);

        ordersUser = UserManager.getQaUser();
        helper.makeOrder(ordersUser, EnvironmentProperties.DEFAULT_SID, 1);
        helper.dropAndFillCart(ordersUser, EnvironmentProperties.DEFAULT_SID);

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

        checkout().setSlot().setFirstActiveSlot();

        checkout().setPayment().clickToByCardToCourier();

        checkout().clickToAddPromoCode();
        checkout().interactEditPromoCodeModal().enterPromoCode(promo);
        checkout().interactEditPromoCodeModal().applyPromoCode();
        checkout().interactEditPromoCodeModal().checkNonExistAlertVisible();
        checkout().interactEditPromoCodeModal().cancelPromoCode();
        checkout().checkPromoCodeNotApplied();
    }
}




