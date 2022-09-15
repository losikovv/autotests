package ru.instamart.test.reforged.stf.checkout_new;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.core.CookieProvider;
import ru.instamart.reforged.core.enums.ShopUrl;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.helper.ApiV3Helper.addFlipperActor;
import static ru.instamart.kraken.config.EnvironmentProperties.DEFAULT_CHECKOUT_SID;
import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Чекаут [NEW]")
public final class CheckoutSwitchTabsTests {
    // Для включения нового чекаута необходимо, чтобы были включены ФФ checkout_web_new, checkout_web_force_all, tmp_b2c_9162_spree_shipment_changes
    // Пользователь должен быть добавлен в А/Б-тесты:
    // 2ae723fe-fdc0-4ab6-97ee-7692d2a19c90 группу new_checkout_web
    // 7cb891fd-a69d-4aef-854e-09b0da121536 группу w_changing_details
    // 7be2e177-5ce6-4769-b04e-c794633076e8 группу w_new_statuses

    private final ApiHelper helper = new ApiHelper();

    @CaseId(3596)
    @Test(description = "Замена метода 'Доставка' на метод 'Самовывоз'", groups = {"regression", "checkout_web_new"})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "RETAILERS_REMINDER_MODAL", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testSwitchDeliveryToPickupInCheckout() {
        final var userData = UserManager.getQaUser();
        addFlipperActor("checkout_web_new", userData.getId());
        addFlipperActor("checkout_web_force_all", userData.getId());
        addFlipperActor("tmp_b2c_9162_spree_shipment_changes", userData.getId());
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

        var replacementPolicy = "Позвонить мне. Убрать из заказа, если не смогу ответить";
        checkoutNew().clickReplacementPolicy();
        checkoutNew().selectReplacementPolicyByName(replacementPolicy);

        checkoutNew().switchToPickup();

        checkoutNew().checkPickupStoreAddress(RestAddresses.Moscow.checkoutAddress().getFullAddress());

        checkoutNew().checkContactsSummary(userData.getPhone(), userData.getEmail());
        checkoutNew().clickEditContacts();
        checkoutNew().checkContactsPhone(userData.getPhone());
        checkoutNew().checkContactsEmail(userData.getEmail());

        checkoutNew().checkSelectedReplacementPolicyContains(replacementPolicy);
    }

    @CaseId(3610)
    @Test(description = "Замена метода 'Самовывоз' на метод 'Доставка'", groups = {"regression", "checkout_web_new"})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "RETAILERS_REMINDER_MODAL", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testSwitchPickupToDeliveryInCheckout() {
        final var userData = UserManager.getQaUser();
        addFlipperActor("checkout_web_new", userData.getId());
        addFlipperActor("checkout_web_force_all", userData.getId());
        addFlipperActor("tmp_b2c_9162_spree_shipment_changes", userData.getId());
        this.helper.dropAndFillCartWithoutSetAddress(userData, DEFAULT_CHECKOUT_SID);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToPickup();
        shop().interactHeader().interactAddress().checkAddressModalVisible();
        shop().interactHeader().interactAddress().clickStoreWithAddress(RestAddresses.Moscow.checkoutAddress().getFullAddress());
        shop().interactHeader().interactAddress().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().submitOrder();

        checkoutNew().checkSpinnerNotVisible();

        var replacementPolicy = "Позвонить мне. Убрать из заказа, если не смогу ответить";
        checkoutNew().clickReplacementPolicy();
        checkoutNew().selectReplacementPolicyByName(replacementPolicy);

        checkoutNew().switchToDelivery();

        checkoutNew().checkDeliveryAddress(RestAddresses.Moscow.checkoutAddress().getCity() + ", " + RestAddresses.Moscow.checkoutAddress().getStreet() + ", " + RestAddresses.Moscow.checkoutAddress().getBuilding());

        checkoutNew().checkContactsSummary(userData.getPhone(), userData.getEmail());
        checkoutNew().clickEditContacts();
        checkoutNew().checkContactsPhone(userData.getPhone());
        checkoutNew().checkContactsEmail(userData.getEmail());

        checkoutNew().checkSelectedReplacementPolicyContains(replacementPolicy);
    }

    @CaseId(3610)
    @Test(description = "Попытка переключения с самовывоза на доставку при заказе имеющем алкоголь", groups = {"regression", "checkout_web_new"})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "RETAILERS_REMINDER_MODAL", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testDeliveryAlcohol() {
        final var userData = UserManager.getQaUser();
        addFlipperActor("checkout_web_new", userData.getId());
        addFlipperActor("checkout_web_force_all", userData.getId());
        addFlipperActor("tmp_b2c_9162_spree_shipment_changes", userData.getId());
        final var fullAddress = RestAddresses.Moscow.checkoutAddress().getCity() + ", " +
                RestAddresses.Moscow.checkoutAddress().getStreet() + ", " +
                RestAddresses.Moscow.checkoutAddress().getBuilding();

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToPickup();
        shop().interactHeader().interactAddress().checkAddressModalVisible();
        shop().interactHeader().interactAddress().clickStoreWithAddress(fullAddress);
        shop().interactHeader().interactAddress().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().goToPage(ShopUrl.METRO.getUrl() + "/c/alcohol/vino/krasnoie-vino?sid=14&source=category");

        shop().interactDisclaimer().checkDisclaimerModalVisible();
        shop().interactDisclaimer().agreeAndConfirm();
        shop().interactDisclaimer().checkDisclaimerModalNotVisible();

        seo().interactHeader().checkPickupSelected();
        seo().addFirstProductOnDepartmentToCart();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().increaseFirstItemCountToMin();
        shop().interactCart().submitOrder();

        checkoutNew().checkSpinnerNotVisible();

        checkoutNew().checkPickupStoreAddress(fullAddress);
        checkoutNew().checkAlcoholBannerVisible();
        checkoutNew().checkDeliveryTabNotVisible();
    }
}
