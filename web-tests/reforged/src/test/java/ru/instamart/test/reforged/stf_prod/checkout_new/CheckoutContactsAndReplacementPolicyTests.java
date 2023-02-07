package ru.instamart.test.reforged.stf_prod.checkout_new;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;

import static ru.instamart.reforged.Group.STF_PROD_S;
import static ru.instamart.reforged.core.config.UiProperties.DEFAULT_SID;
import static ru.instamart.reforged.stf.enums.PaymentMethods.BY_CARD_TO_COURIER;
import static ru.instamart.reforged.stf.enums.ReplacementPolicies.NOT_SELECTED;
import static ru.instamart.reforged.stf.page.StfRouter.checkoutNew;
import static ru.instamart.reforged.stf.page.StfRouter.shop;

@Epic("STF UI")
@Feature("Чекаут V3")
public final class CheckoutContactsAndReplacementPolicyTests {

    private static final ThreadLocal<UserData> ordersUser = new ThreadLocal<>();
    private final ApiHelper helper = new ApiHelper();

    @BeforeMethod(alwaysRun = true, description = "Шаги предусловия")
    public void beforeTest() {
        ordersUser.set(UserManager.getQaUser());
        this.helper.dropAndFillCart(ordersUser.get(), DEFAULT_SID);
    }

    @AfterMethod(alwaysRun = true, description = "Отмена ордера")
    public void afterTest() {
        this.helper.cancelAllActiveOrders(ordersUser.get());
    }

    @TmsLink("3691")
    @Test(description = "Проверка отсутствия предвыбранного способа замены товара при первом чекауте", groups = {STF_PROD_S})
    public void testCheckReplacementPolicyNotSelectedFirstTime() {
        shop().goToPage(DEFAULT_SID);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(ordersUser.get());
        shop().interactHeader().checkProfileButtonVisible();
        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().submitOrder();
        checkoutNew().checkSpinnerNotVisible();

        checkoutNew().checkSelectedReplacementPolicy(NOT_SELECTED.getName());
    }

    @TmsLink("3632")
    @Test(description = "Проверка что поле 'Замена товара' является обязательным при первом заказе", groups = {STF_PROD_S})
    public void testCheckReplacementPolicyRequired() {
        shop().goToPage(DEFAULT_SID);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(ordersUser.get());
        shop().interactHeader().checkProfileButtonVisible();
        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().submitOrder();
        checkoutNew().checkSpinnerNotVisible();

        checkoutNew().fillApartment("1");
        checkoutNew().fillComment("test");

        checkoutNew().checkDeliverySlotsVisible();
        checkoutNew().clickFirstSlot();
        checkoutNew().checkSlotActive(1);

        checkoutNew().openPaymentMethodModal();
        checkoutNew().interactPaymentMethodsModal().checkModalVisible();
        checkoutNew().interactPaymentMethodsModal().selectPaymentMethod(BY_CARD_TO_COURIER.getName());
        checkoutNew().interactPaymentMethodsModal().clickConfirm();
        checkoutNew().interactPaymentMethodsModal().checkModalNotVisible();

        checkoutNew().clickConfirmOrder();
        checkoutNew().checkSelectedReplacementPolicyInvalid();
        checkoutNew().checkSelectedReplacementPolicy(NOT_SELECTED.getName());
        checkoutNew().checkReplacementPolicyErrorDescriptionVisible();
        checkoutNew().checkReplacementPolicyErrorDescriptionContains("Выберите вариант замены");
    }

    @TmsLink("3629")
    @Test(description = "Проверка что поля телефон и e-mail обязательны и валидируются при потере фокуса", groups = {STF_PROD_S})
    public void testCheckPhoneAndEmailRequiredFields() {
        shop().goToPage(DEFAULT_SID);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(ordersUser.get());
        shop().interactHeader().checkProfileButtonVisible();
        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().submitOrder();
        checkoutNew().checkSpinnerNotVisible();

        checkoutNew().clickEditContacts();
        checkoutNew().clearPhone();
        checkoutNew().clickAddressTitle();

        checkoutNew().checkPhoneInvalid();
        checkoutNew().checkPhoneErrorDescriptionVisible();
        checkoutNew().checkPhoneErrorDescriptionContains("Обязательное поле");

        checkoutNew().fillPhone(ordersUser.get().getPhone().substring(0, 5));
        checkoutNew().clickAddressTitle();
        checkoutNew().checkPhoneInvalid();
        checkoutNew().checkPhoneErrorDescriptionVisible();
        checkoutNew().checkPhoneErrorDescriptionContains("Минимальная длина 11 цифр");

        checkoutNew().clearEmail();

        checkoutNew().clickAddressTitle();
        checkoutNew().checkEmailInvalid();
        checkoutNew().checkEmailErrorDescriptionVisible();
        checkoutNew().checkEmailErrorDescriptionContains("Обязательное поле");

        checkoutNew().fillEmail(ordersUser.get().getEmail().substring(0, 5));
        checkoutNew().clickAddressTitle();
        checkoutNew().checkEmailInvalid();
        checkoutNew().checkEmailErrorDescriptionVisible();
        checkoutNew().checkEmailErrorDescriptionContains("E-mail написан неверно");
    }
}
