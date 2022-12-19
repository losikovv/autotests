package ru.instamart.test.reforged.stf.order;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.*;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.listener.Skip;
import ru.instamart.kraken.util.StringUtil;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.Group.REGRESSION_STF;
import static ru.instamart.reforged.core.config.BasicProperties.RBSUAT_PAYMENTS_URL;
import static ru.instamart.reforged.core.config.UiProperties.*;
import static ru.instamart.reforged.sber_payments.SberPaymentsPageRouter.sberPayments;
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
    @Test(description = "Тест заказа с добавлением нового юр. лица", groups = {REGRESSION_STF})
    public void successCompleteCheckoutWithNewJuridical() {
        userData = UserManager.getQaUser();
        helper.dropAndFillCart(userData, DEFAULT_AUCHAN_SID);

        var company = JuridicalData.juridical();

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
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

        checkout().checkCheckoutLoaderNotVisible();

        checkout().setContacts().fillContactInfo();
        checkout().setContacts().clickToSubmit();

        checkout().setReplacementPolicy().clickToSubmit();

        checkout().setSlot().setLastActiveSlot();

        checkout().setPayment().clickToByCardToCourier();
        checkout().setPayment().clickToSubmitFromCheckoutColumn();

        userShipments().checkPageContains(userShipments().pageUrl());
    }

    @CaseIDs(value = {@CaseId(1672), @CaseId(2627)})
    @Test(description = "Тест заказа с новой картой оплаты c 3ds", groups = {REGRESSION_STF})
    public void successCompleteCheckoutWithNewPaymentCard() {
        userData = UserManager.getQaUser();
        helper.dropAndFillCart(userData, DEFAULT_AUCHAN_SID);

        var card = PaymentCards.testCard();

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
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

        checkout().setDeliveryOptions().fillApartment(Generate.digitalString(3));
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

        sberPayments().checkFrameOpened();
        sberPayments().checkPageContains(RBSUAT_PAYMENTS_URL + "acs");
        sberPayments().checkPasswordInputVisible();
        sberPayments().fillPassword(card.getPassword());

        userShipments().checkPageContains(userShipments().pageUrl());
    }

    @CaseIDs(value = {@CaseId(2066), @CaseId(3043), @CaseId(2641)})
    @Test(description = "Тест заказа с новой картой оплаты без 3ds", groups = REGRESSION_STF)
    public void successCompleteCheckoutWithNewNoSecurePaymentCard() {
        userData = UserManager.getQaUser();
        helper.dropAndFillCart(userData, DEFAULT_AUCHAN_SID);

        var card = PaymentCards.testCardNo3dsWithSpasibo();

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();
        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().submitOrder();

        checkoutNew().waitPageLoad();
        checkoutNew().clickOrderForBusiness();
        checkoutNew().interactB2BOrderModal().checkModalVisible();
        checkoutNew().interactB2BOrderModal().clickConfirm();
        checkoutNew().interactB2BOrderModal().checkModalNotVisible();

        checkout().waitPageLoad();
        checkout().setDeliveryOptions().clickToForSelf();

        checkout().setDeliveryOptions().fillApartment(Generate.digitalString(3));
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

        userShipments().checkPageContains(userShipments().pageUrl());
    }

    @CaseId(1681)
    @Test(description = "Тест заказа с любимыми товарами", groups = REGRESSION_STF)
    public void successOrderWithFavProducts() {
        userData = UserManager.getQaUser();
        helper.addFavorites(userData, DEFAULT_AUCHAN_SID, 1);
        helper.dropAndFillCartFromFavorites(userData, DEFAULT_AUCHAN_SID);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().submitOrder();

        checkoutNew().waitPageLoad();
        checkoutNew().clickOrderForBusiness();
        checkoutNew().interactB2BOrderModal().clickConfirm();
        checkoutNew().interactB2BOrderModal().checkModalNotVisible();

        checkout().waitPageLoad();
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
    @Test(description = "Тест успешного заказа с оплатой картой курьеру", groups = {REGRESSION_STF})
    public void successOrderWithCardCourier() {
        userData = UserManager.getQaUser();
        helper.dropAndFillCart(userData, DEFAULT_AUCHAN_SID);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().submitOrder();

        checkoutNew().waitPageLoad();
        checkoutNew().clickOrderForBusiness();
        checkoutNew().interactB2BOrderModal().clickConfirm();
        checkoutNew().interactB2BOrderModal().checkModalNotVisible();

        checkout().waitPageLoad();
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

    @CaseId(2558)
    @Story("Данные профиля пользователя")
    @Test(description = "Автоматическая подстановка данных в аккаунт после прохождения чекаута", groups = REGRESSION_STF)
    public void updateUserDataAfterCheckout() {
        final AddressDetailsData data = TestVariables.testAddressData();
        //Тут используется не qa ручка, потому что в ней уже задано имя для пользователя
        userData = UserManager.getUser();
        helper.dropAndFillCart(userData, DEFAULT_AUCHAN_SID, helper.getAddressBySid(DEFAULT_AUCHAN_SID));

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
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
        checkout().setDeliveryOptions().clickToForSelf();
        checkout().setDeliveryOptions().fillApartment(data.getApartment());
        checkout().setDeliveryOptions().fillFloor(data.getFloor());
        checkout().setDeliveryOptions().checkElevator();
        checkout().setDeliveryOptions().fillEntrance(data.getEntrance());
        checkout().setDeliveryOptions().fillDoorPhone(data.getDomofon());
        checkout().setDeliveryOptions().fillComments(data.getComments());
        checkout().setDeliveryOptions().clickToSubmitForDelivery();

        checkout().setContacts().fillContactInfo(userData);
        checkout().setContacts().clickToSubmit();

        checkout().setReplacementPolicy().clickToSubmit();

        checkout().setSlot().setLastActiveSlot();

        checkout().setPayment().clickToByCardToCourier();
        checkout().setPayment().clickToSubmitFromCheckoutColumn();

        userShipments().checkFrameOpened();
        userShipments().checkPageContains(userShipments().pageUrl());

        userEdit().checkFrameOpened();
        userEdit().goToPage();

        userEdit().checkFullName(userEdit().getName(), userData.getName());
    }

    @CaseId(2623)
    @Issue("B2C-12077")
    @Story("Отмена заказа")
    @Test(description = "Отмена заказа", groups = REGRESSION_STF)
    public void successOrderCancel() {
        userData = UserManager.getQaUser();
        helper.makeOrder(userData, DEFAULT_AUCHAN_SID, 1);
        helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        userShipments().goToPage();
        userShipments().clickToFirstShipment();
        userShipment().clickToCancelFromOrder();

        userShipments().interactShipmentCancelModal().shipmentCancelModalVisible();
        userShipment().interactShipmentCancelModal().clickToAccept();
        userShipment().interactShipmentCancelModal().shipmentCancelModalNotVisible();

        userShipment().checkShipmentStateCancelled();
    }

    @CaseId(2624)
    @Story("Заказ")
    @Test(description = "Добавление товаров в активный заказ", groups = REGRESSION_STF)
    public void successAddItemsInActiveOrder() {
        userData = UserManager.getQaUser();

        helper.makeOrderOnTomorrow(userData, DEFAULT_AUCHAN_SID, 1);
        helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        final var itemName = shop().getProductTitleByPosition(1);
        shop().plusFirstItemToCart();
        shop().interactHeader().checkCartNotificationIsVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().getFirstRetailer().mergeProducts();
        shop().interactCart().clickToViewOrder();

        userShipments().waitPageLoad();
        userShipment().checkProductListContains(StringUtil.cutThreeDotsFromEnd(itemName));
    }

    @CaseId(2625)
    @Story("Заказ")
    @Skip
    @Test(description = "Успешное оформление мультизаказа", groups = REGRESSION_STF)
    public void successMultiOrder() {
        //пока выключено, для некста отключен мультизаказ на данный момент
        userData = UserManager.getQaUser();

        helper.dropAndFillCartMultiple(userData, RestAddresses.Moscow.defaultAddress(), DEFAULT_METRO_MOSCOW_SID, DEFAULT_AUCHAN_SID);

        var card = PaymentCards.testCard();

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().submitOrder();

        checkout().goToPage();
        checkout().setDeliveryOptions().clickToForSelf();

        checkout().setDeliveryOptions().fillApartment(Generate.digitalString(3));
        checkout().setDeliveryOptions().clickToSubmitForDelivery();

        checkout().checkCheckoutLoaderNotVisible();

        checkout().setContacts().fillContactInfo();
        checkout().setContacts().clickToSubmit();

        checkout().setReplacementPolicy().clickToSubmit();
        checkout().setReplacementPolicy().checkReplacementSpinnerVisible();
        checkout().setReplacementPolicy().checkReplacementSpinnerNotVisible();

        checkout().setSlot().checkSlotsSpinnerIsVisible();
        checkout().setSlot().checkSlotsSpinnerIsNotVisible();
        checkout().setSlot().setLastActiveSlot();

        checkout().setSlot().checkSlotsSpinnerIsVisible();
        checkout().setSlot().checkSlotsSpinnerIsNotVisible();
        checkout().setSlot().setFirstActiveSlotSecondRetailer();

        checkout().setPayment().clickToByCardOnline();
        checkout().setPayment().clickToAddNewPaymentCard();

        checkout().interactAddPaymentCardModal().fillCardData(card);
        checkout().interactAddPaymentCardModal().clickToSaveModal();

        checkout().setPayment().clickToSubmitFromCheckoutColumn();

        sberPayments().checkFrameOpened();
        sberPayments().checkPageContains(RBSUAT_PAYMENTS_URL + "acs");
        sberPayments().checkPasswordInputVisible();
        sberPayments().fillPassword(card.getPassword());

        userShipments().checkPageContains(userShipments().pageUrl());
    }

    @CaseId(2626)
    @Story("Заказ")
    @Skip
    @Test(description = "Отмена всего мультизаказа при отмене одного из входящих в него заказов", groups = REGRESSION_STF)
    public void successCancelMultiOrderViaCancelOneOrder() {
        userData = UserManager.getQaUser();
        helper.makeMultipleOrder(userData, RestAddresses.Moscow.defaultAddress(), DEFAULT_METRO_MOSCOW_SID, DEFAULT_AUCHAN_SID);
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        userShipments().goToPage();
        userShipments().checkPageContains(userShipments().pageUrl());
        userShipments().clickToFirstShipment();
        userShipments().clickToCancelFromOrder();
        userShipments().interactShipmentCancelModal().shipmentCancelModalVisible();
        userShipments().interactShipmentCancelModal().clickToAccept();
        userShipments().checkStatusWasCanceled();

        userShipments().goToPage();
        userShipments().checkAllOrderStatusWasCanceled();
    }

    @CaseId(2628)
    @Story("Заказ")
    @Test(description = "Тест полного оформления заказа с оплатой картой онлайн (добавлена карта c 3ds)", groups = REGRESSION_STF)
    public void successCompleteCheckoutWithNewPaymentCard3DSAlreadyIn() {
        //TODO: после починки addCreditCard() нужно добавить сюда добавление карты через апи
        userData = UserManager.getQaUser();
        helper.dropAndFillCart(userData, DEFAULT_AUCHAN_SID);

        var card = PaymentCards.testCard();

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
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
        checkout().setDeliveryOptions().clickToForSelf();
        checkout().setDeliveryOptions().fillApartment(Generate.digitalString(3));
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
        checkout().interactAddPaymentCardModal().checkModalWindowNotVisible();

        checkout().setPayment().checkSubmitOrderButtonClickable();
        checkout().setPayment().clickToSubmitFromCheckoutColumn();

        sberPayments().checkFrameOpened();
        sberPayments().checkPageContains(RBSUAT_PAYMENTS_URL + "acs");
        sberPayments().checkPasswordInputVisible();
        sberPayments().fillPassword(card.getPassword());

        userShipments().checkPageContains(userShipments().pageUrl());

        helper.dropAndFillCart(userData, DEFAULT_SID);

        checkout().goToPage();
        checkout().setDeliveryOptions().clickToForSelf();

        checkout().setDeliveryOptions().fillApartment(Generate.digitalString(3));
        checkout().setDeliveryOptions().clickToSubmitForDelivery();

        checkout().checkCheckoutLoaderNotVisible();

        checkout().setContacts().fillContactInfo();
        checkout().setContacts().clickToSubmit();

        checkout().setReplacementPolicy().clickToSubmit();

        checkout().setSlot().setLastActiveSlot();

        checkout().setPayment().clickToByCardOnline();
        checkout().setPayment().clickToSubmitFromCheckoutColumn();

        userShipments().checkPageContains(userShipments().pageUrl());
    }
}
