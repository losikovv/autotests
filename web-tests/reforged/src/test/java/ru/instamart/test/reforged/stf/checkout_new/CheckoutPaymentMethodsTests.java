package ru.instamart.test.reforged.stf.checkout_new;

import io.qameta.allure.*;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.PaymentCards;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.core.CookieProvider;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.helper.ApiV3Helper.addFlipperActor;
import static ru.instamart.kraken.config.EnvironmentProperties.DEFAULT_CHECKOUT_SID;
import static ru.instamart.reforged.Group.CHECKOUT_WEB_NEW;
import static ru.instamart.reforged.Group.REGRESSION_STF;
import static ru.instamart.reforged.business.page.BusinessRouter.business;
import static ru.instamart.reforged.stf.enums.PaymentMethods.*;
import static ru.instamart.reforged.stf.page.StfRouter.checkoutNew;
import static ru.instamart.reforged.stf.page.StfRouter.shop;

@Epic("STF UI")
@Feature("Чекаут V3")
public final class CheckoutPaymentMethodsTests {
    // Для включения нового чекаута необходимо, чтобы были включены ФФ checkout_web_new, checkout_web_force_all, tmp_b2c_9162_spree_shipment_changes (постчекаут)
    // Пользователь должен быть добавлен в А/Б-тесты:
    // 2ae723fe-fdc0-4ab6-97ee-7692d2a19c90 группу new_checkout_web
    // 7cb891fd-a69d-4aef-854e-09b0da121536 группу w_changing_details
    // 7be2e177-5ce6-4769-b04e-c794633076e8 группу w_new_statuses

    private final ApiHelper helper = new ApiHelper();

    @Issue("B2C-9776")
    @CaseId(3616)
    @Story("Способы оплаты")
    @Test(description = "Сброс способа оплаты 'Картой курьеру' при выборе 'Бесконтактная доставка'", groups = {REGRESSION_STF, CHECKOUT_WEB_NEW})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "RETAILERS_REMINDER_MODAL", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testPayByCardCourierDeliveryToDoor() {
        final var userData = UserManager.getQaUser();
        addFlipperActor("checkout_web_new", userData.getId());
        addFlipperActor("checkout_web_force_all", userData.getId());
        this.helper.dropAndFillCartWithoutSetAddress(userData, DEFAULT_CHECKOUT_SID);
        this.helper.setAddress(userData, RestAddresses.Moscow.checkoutAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().submitOrder();

        checkoutNew().checkSpinnerNotVisible();

        checkoutNew().openPaymentMethodModal();
        checkoutNew().interactPaymentMethodsModal().checkModalVisible();
        checkoutNew().interactPaymentMethodsModal().selectPaymentMethod(BY_CARD_TO_COURIER.getName());
        checkoutNew().interactPaymentMethodsModal().clickConfirm();
        checkoutNew().interactPaymentMethodsModal().checkModalNotVisible();

        checkoutNew().checkSelectedPaymentMethodContains(BY_CARD_TO_COURIER.getName());

        checkoutNew().clickDeliveryToDoor();

        checkoutNew().checkSpinnerVisible();
        checkoutNew().checkSpinnerNotVisible();

        checkoutNew().checkPaymentMethodEmpty();

        checkoutNew().checkNotificationVisible();
        checkoutNew().checkNotificationTitle("Оплата картой курьеру недоступна");
        checkoutNew().checkNotificationText("Вы выбрали бесконтактную доставку, заказ нужно оплатить онлайн");
    }

    @Issue("B2C-9777")
    @CaseId(3637)
    @Story("Способы оплаты")
    @Test(description = "Проверка предвыбора метода 'Оплатить онлайн' при выборе самовывоза", groups = {REGRESSION_STF, CHECKOUT_WEB_NEW})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "RETAILERS_REMINDER_MODAL", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testCheckPayOnlineSelectedByDefaultInPickup() {
        final var userData = UserManager.getQaUser();
        addFlipperActor("checkout_web_new", userData.getId());
        addFlipperActor("checkout_web_force_all", userData.getId());
        this.helper.dropAndFillCartWithoutSetAddress(userData, DEFAULT_CHECKOUT_SID);
        this.helper.setAddress(userData, RestAddresses.Moscow.checkoutAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().submitOrder();

        checkoutNew().checkSpinnerNotVisible();

        checkoutNew().switchToPickup();

        checkoutNew().checkPickupTabOpened();

        checkoutNew().checkSelectedPaymentMethodContains("Оплатить онлайн");
    }

    @Issues({@Issue("B2C-9732"), @Issue("B2C-9730")})
    @CaseId(3649)
    @Story("Способы оплаты")
    @Test(description = "Проверка добавления новой карты оплаты", groups = {REGRESSION_STF, CHECKOUT_WEB_NEW})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "RETAILERS_REMINDER_MODAL", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testAddNewPaymentCard() {
        final var userData = UserManager.getQaUser();
        addFlipperActor("checkout_web_new", userData.getId());
        addFlipperActor("checkout_web_force_all", userData.getId());
        var card = PaymentCards.testCardNo3ds();
        this.helper.dropAndFillCartWithoutSetAddress(userData, DEFAULT_CHECKOUT_SID);
        this.helper.setAddress(userData, RestAddresses.Moscow.checkoutAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().submitOrder();

        checkoutNew().checkSpinnerNotVisible();

        checkoutNew().openPaymentMethodModal();
        checkoutNew().interactPaymentMethodsModal().checkModalVisible();
        checkoutNew().interactPaymentMethodsModal().selectPaymentMethod(BY_CARD_ONLINE.getName());
        checkoutNew().interactPaymentMethodsModal().clickConfirm();
        checkoutNew().interactAddPaymentCardModal().checkModalVisible();

        checkoutNew().interactAddPaymentCardModal().fillCardData(card);
        //B2C-9732

        checkoutNew().interactAddPaymentCardModal().clickAdd();
        checkoutNew().interactAddPaymentCardModal().checkModalNotVisible();

        checkoutNew().checkSelectedPaymentMethodContains(card.getCardNumber().substring(card.getCardNumber().length() - 4));
        //B2C-9730
    }

    @Issue("B2C-9732")
    @CaseId(3642)
    @Story("Способы оплаты")
    @Test(description = "Проверка появления кнопки 'Оплатить' при способе оплаты 'Картой онлайн'", groups = {REGRESSION_STF, CHECKOUT_WEB_NEW})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "RETAILERS_REMINDER_MODAL", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testPayButtonDisplayedWithSelectOnlinePaymentMethod() {
        final var userData = UserManager.getQaUser();
        addFlipperActor("checkout_web_new", userData.getId());
        addFlipperActor("checkout_web_force_all", userData.getId());
        var card = PaymentCards.testCardNo3ds();
        this.helper.dropAndFillCartWithoutSetAddress(userData, DEFAULT_CHECKOUT_SID);
        this.helper.setAddress(userData, RestAddresses.Moscow.checkoutAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().submitOrder();

        checkoutNew().checkSpinnerNotVisible();

        checkoutNew().openPaymentMethodModal();
        checkoutNew().interactPaymentMethodsModal().checkModalVisible();
        checkoutNew().interactPaymentMethodsModal().selectPaymentMethod(BY_CARD_ONLINE.getName());
        checkoutNew().interactPaymentMethodsModal().clickConfirm();
        checkoutNew().interactAddPaymentCardModal().checkModalVisible();

        checkoutNew().interactAddPaymentCardModal().fillCardData(card);
        //B2C-9732

        checkoutNew().interactAddPaymentCardModal().clickAdd();
        checkoutNew().interactAddPaymentCardModal().checkModalNotVisible();

        checkoutNew().checkConfirmPayVisible();
        checkoutNew().checkConfirmPayActive();
    }

    @CaseId(3643)
    @Story("Способы оплаты")
    @Test(description = "Проверка появления кнопки 'Заказать' при способе оплаты 'Картой курьеру'", groups = {REGRESSION_STF, CHECKOUT_WEB_NEW})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "RETAILERS_REMINDER_MODAL", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testOrderConfirmButtonDisplayedWithSelectCardToCourierPaymentMethod() {
        final var userData = UserManager.getQaUser();
        addFlipperActor("checkout_web_new", userData.getId());
        addFlipperActor("checkout_web_force_all", userData.getId());
        this.helper.dropAndFillCartWithoutSetAddress(userData, DEFAULT_CHECKOUT_SID);
        this.helper.setAddress(userData, RestAddresses.Moscow.checkoutAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().submitOrder();

        checkoutNew().checkSpinnerNotVisible();

        checkoutNew().openPaymentMethodModal();
        checkoutNew().interactPaymentMethodsModal().checkModalVisible();
        checkoutNew().interactPaymentMethodsModal().selectPaymentMethod(BY_CARD_TO_COURIER.getName());
        checkoutNew().interactPaymentMethodsModal().clickConfirm();

        checkoutNew().checkConfirmOrderVisible();
        checkoutNew().checkConfirmOrderActive();
    }

    @CaseId(3822)
    @Story("Способы оплаты")
    @Test(description = "Проверка редиректа на см-бизнес при способе оплаты 'По счету для бизнеса' (Доставка)", groups = {REGRESSION_STF, CHECKOUT_WEB_NEW})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "RETAILERS_REMINDER_MODAL", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testTransitionOnB2BIfSelectBusinessPaymentMethodFromDelivery() {
        final var userData = UserManager.getQaUser();
        addFlipperActor("checkout_web_new", userData.getId());
        addFlipperActor("checkout_web_force_all", userData.getId());
        this.helper.dropAndFillCartWithoutSetAddress(userData, DEFAULT_CHECKOUT_SID);
        this.helper.setAddress(userData, RestAddresses.Moscow.checkoutAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().submitOrder();

        checkoutNew().checkSpinnerNotVisible();

        checkoutNew().openPaymentMethodModal();
        checkoutNew().interactPaymentMethodsModal().checkModalVisible();
        checkoutNew().interactPaymentMethodsModal().selectPaymentMethod(BY_BUSINESS_ACCOUNT.getName());
        checkoutNew().interactPaymentMethodsModal().clickConfirm();

        checkoutNew().checkSelectCompanyButtonDisplayed();
        checkoutNew().clickSelectCompany();

        checkoutNew().interactB2BOrderModal().checkModalVisible();
        checkoutNew().interactB2BOrderModal().clickConfirm();
        checkoutNew().interactB2BOrderModal().checkModalNotVisible();

        checkoutNew().switchToNextWindow();
        business().checkPageContains("smbusiness");
    }

    @CaseId(3827)
    @Story("Способы оплаты")
    @Test(description = "Проверка редиректа на см-бизнес при способе оплаты 'По счету для бизнеса' (Самовывоз)", groups = {REGRESSION_STF, CHECKOUT_WEB_NEW})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "RETAILERS_REMINDER_MODAL", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testTransitionOnB2BIfSelectBusinessPaymentMethodFromPickup() {
        final var userData = UserManager.getQaUser();
        addFlipperActor("checkout_web_new", userData.getId());
        addFlipperActor("checkout_web_force_all", userData.getId());
        this.helper.dropAndFillCartWithoutSetAddress(userData, DEFAULT_CHECKOUT_SID);
        this.helper.setAddress(userData, RestAddresses.Moscow.checkoutAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().submitOrder();

        checkoutNew().checkSpinnerNotVisible();

        checkoutNew().switchToPickup();
        checkoutNew().checkPickupTabOpened();

        checkoutNew().openPaymentMethodModal();
        checkoutNew().interactPaymentMethodsModal().checkModalVisible();
        checkoutNew().interactPaymentMethodsModal().selectPaymentMethod(BY_BUSINESS_ACCOUNT.getName());
        checkoutNew().interactPaymentMethodsModal().clickConfirm();

        checkoutNew().checkSelectCompanyButtonDisplayed();
        checkoutNew().clickSelectCompany();

        checkoutNew().interactB2BOrderModal().checkModalVisible();
        checkoutNew().interactB2BOrderModal().clickConfirm();
        checkoutNew().interactB2BOrderModal().checkModalNotVisible();

        checkoutNew().switchToNextWindow();
        business().checkPageContains("smbusiness");
    }
}