package ru.instamart.test.reforged.stf_prod.order;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.JuridicalData;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.listener.Skip;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.Group.STF_PROD_S;
import static ru.instamart.reforged.core.config.UiProperties.*;
import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Покупка товара")
public final class BasicOrdersTests {

    private final ApiHelper helper = new ApiHelper();
    private UserData userData;

    @AfterMethod(alwaysRun = true, description = "Отмена ордера")
    public void afterTest() {
        helper.cancelAllActiveOrders(userData);
    }

    @CaseId(1674)
    @Test(description = "Тест заказа с добавлением нового юр. лица", groups = {STF_PROD_S})
    public void successCompleteCheckoutWithNewJuridical() {
        userData = UserManager.getQaUser();
        helper.dropAndFillCart(userData, DEFAULT_SID);

        var company = JuridicalData.juridical();

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

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

        checkout().setPayment().clickToByCardToCourier();
        checkout().setPayment().clickToSubmitFromCheckoutColumn();

        userShipments().checkPageContains(userShipments().pageUrl());
    }

    @CaseId(1681)
    @Test(description = "Тест заказа с любимыми товарами", groups = {STF_PROD_S})
    public void successOrderWithFavProducts() {
        userData = UserManager.getQaUser();
        helper.addFavorites(userData, DEFAULT_SID, 1);
        helper.dropAndFillCartFromFavorites(userData, DEFAULT_SID);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        checkout().goToPage();
        checkout().setDeliveryOptions().clickToForSelf();

        checkout().setDeliveryOptions().fillApartment(Generate.digitalString(3));
        checkout().setDeliveryOptions().clickToSubmitForDelivery();

        checkout().checkCheckoutLoaderNotVisible();

        checkout().setContacts().fillContactInfo();
        checkout().setContacts().clickToSubmit();

        checkout().setReplacementPolicy().clickToSubmit();

        checkout().setSlot().setLastActiveSlot();

        checkout().setPayment().clickToByCardToCourier();

        checkout().setPayment().clickToSubmitFromCheckoutColumn();

        userShipments().checkPageContains(userShipments().pageUrl());
    }

    @CaseId(1673)
    @Test(description = "Тест успешного заказа с оплатой картой курьеру", groups = {STF_PROD_S})
    public void successOrderWithCardCourier() {
        userData = UserManager.getQaUser();
        helper.dropAndFillCart(userData, DEFAULT_SID);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        checkout().goToPage();
        checkout().setDeliveryOptions().clickToForSelf();

        checkout().setDeliveryOptions().fillApartment(Generate.digitalString(3));
        checkout().setDeliveryOptions().clickToSubmitForDelivery();

        checkout().checkCheckoutLoaderNotVisible();

        checkout().setContacts().fillContactInfo();
        checkout().setContacts().clickToSubmit();

        checkout().setReplacementPolicy().clickToSubmit();

        checkout().setSlot().setLastActiveSlot();

        checkout().setPayment().clickToByCardToCourier();
        checkout().setPayment().clickToSubmitFromCheckoutColumn();

        userShipments().checkPageContains(userShipments().pageUrl());
    }

    @CaseId(2623)
    @Story("Отмена заказа")
    @Test(description = "Отмена заказа", groups = {STF_PROD_S})
    public void successOrderCancel() {
        userData = UserManager.getQaUser();
        helper.makeOrder(userData, DEFAULT_SID, 1);
        helper.setAddress(userData, RestAddresses.Moscow.defaultProdAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        userShipments().goToPage();
        userShipments().clickToFirstShipment();
        userShipments().clickToCancelFromOrder();

        userShipments().interactShipmentCancelModal().shipmentCancelModalVisible();
        userShipments().interactShipmentCancelModal().clickToAcceptProd();
        userShipments().interactShipmentCancelModal().shipmentCancelModalNotVisible();

        userShipments().checkStatusWasCanceledProd();
    }

    @CaseId(2624)
    @Story("Заказ")
    @Test(description = "Добавление товаров в активный заказ", groups = {STF_PROD_S})
    public void successAddItemsInActiveOrder() {
        userData = UserManager.getQaUser();

        helper.makeOrderOnTomorrowByQa(userData, DEFAULT_SID, 1);
        helper.setAddress(userData, RestAddresses.Moscow.defaultProdAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        final var itemName = shop().getFirstProductTitleProd();
        shop().plusFirstItemToCartProd();
        shop().interactHeader().checkCartNotificationIsVisible();

        shop().goToPage();
        shop().interactHeader().clickToCart();
        shop().interactCart().getFirstRetailer().mergeProducts();
        shop().interactCart().clickToViewOrder();

        userShipments().checkProductListContains(itemName);
    }

    @Skip
    @CaseId(2626)
    @Story("Заказ")
    @Test(description = "Отмена всего мультизаказа при отмене одного из входящих в него заказов", groups = {STF_PROD_S})
    public void successCancelMultiOrderViaCancelOneOrder() {
        userData = UserManager.getQaUser();
        helper.makeMultipleOrderByQa(userData, RestAddresses.Moscow.defaultProdAddress(), DEFAULT_METRO_MOSCOW_SID, DEFAULT_AUCHAN_SID);
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        userShipments().goToPage();
        userShipments().checkPageContains(userShipments().pageUrl());
        userShipments().clickToFirstShipment();
        userShipments().clickToCancelFromOrder();
        userShipments().interactShipmentCancelModal().shipmentCancelModalVisible();
        userShipments().interactShipmentCancelModal().clickToAcceptProd();
        userShipments().checkStatusWasCanceledProd();

        userShipments().goToPage();
        userShipments().checkAllOrderStatusWasCanceled();
    }
}
