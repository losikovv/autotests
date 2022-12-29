package ru.instamart.test.reforged.stf.checkout_new;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.core.annotation.CookieProvider;
import ru.instamart.reforged.core.enums.ShopUrl;

import static ru.instamart.kraken.config.EnvironmentProperties.DEFAULT_CHECKOUT_SID;
import static ru.instamart.reforged.Group.*;
import static ru.instamart.reforged.core.config.UiProperties.ALCOHOL_CATEGORY_LINK;
import static ru.instamart.reforged.stf.enums.ReplacementPolicies.CALL_AND_REMOVE;
import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Чекаут V3")
public final class CheckoutSwitchTabsTests {

    private final ApiHelper helper = new ApiHelper();

    @TmsLink("3596")
    @Story("Переключение доставка/самовывоз")
    @Test(description = "Замена метода 'Доставка' на метод 'Самовывоз'", groups = {REGRESSION_STF, CHECKOUT_WEB_NEW, JOTUNHEIMR})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testSwitchDeliveryToPickupInCheckout() {
        final var userData = UserManager.getQaUser();
        this.helper.dropAndFillCartWithoutSetAddress(userData, DEFAULT_CHECKOUT_SID);
        this.helper.setAddress(userData, RestAddresses.Moscow.checkoutAddress());

        final var fullAddress = RestAddresses.Moscow.checkoutAddress().fullAddress().toString();

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().submitOrder();

        checkoutNew().checkSpinnerNotVisible();

        checkoutNew().clickReplacementPolicy();
        checkoutNew().selectReplacementPolicyByName(CALL_AND_REMOVE.getName());

        checkoutNew().switchToPickup();

        checkoutNew().checkPickupStoreAddress(fullAddress);

        checkoutNew().checkContactsSummary(userData.getPhone(), userData.getEmail());
        checkoutNew().clickEditContacts();
        checkoutNew().checkContactsPhone(userData.getPhone());
        checkoutNew().checkContactsEmail(userData.getEmail());

        checkoutNew().checkSelectedReplacementPolicy(CALL_AND_REMOVE.getName());
    }

    @TmsLink("3597")
    @Story("Переключение доставка/самовывоз")
    @Test(description = "Замена метода 'Самовывоз' на метод 'Доставка'", groups = {REGRESSION_STF, CHECKOUT_WEB_NEW, JOTUNHEIMR})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testSwitchPickupToDeliveryInCheckout() {
        final var userData = UserManager.getQaUser();
        this.helper.dropAndFillCartWithoutSetAddress(userData, DEFAULT_CHECKOUT_SID);
        this.helper.setAddress(userData, RestAddresses.Moscow.checkoutAddress());

        final var fullAddress = RestAddresses.Moscow.checkoutAddress().fullAddress().toString();

        shop().goToPage(ShopUrl.METRO);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToPickup();
        shop().interactAddressLarge().checkYmapsReady();
        shop().interactAddressLarge().clickTakeFromHere();
        shop().interactAddressLarge().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().submitOrder();

        checkoutNew().checkSpinnerNotVisible();

        checkoutNew().clickReplacementPolicy();
        checkoutNew().selectReplacementPolicyByName(CALL_AND_REMOVE.getName());

        checkoutNew().switchToDelivery();

        checkoutNew().checkDeliveryAddress(fullAddress);

        checkoutNew().checkContactsSummary(userData.getPhone(), userData.getEmail());
        checkoutNew().clickEditContacts();
        checkoutNew().checkContactsPhone(userData.getPhone());
        checkoutNew().checkContactsEmail(userData.getEmail());

        checkoutNew().checkSelectedReplacementPolicy(CALL_AND_REMOVE.getName());
    }

    @TmsLink("3610")
    @Story("Переключение доставка/самовывоз")
    @Test(description = "Попытка переключения с самовывоза на доставку при заказе имеющем алкоголь", groups = {REGRESSION_STF, CHECKOUT_WEB_NEW, JOTUNHEIMR})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testSwitchPickupToDeliveryInCheckoutWithAlcohol() {
        final var userData = UserManager.getQaUser();
        final var fullAddress = RestAddresses.Moscow.checkoutAddress().fullAddress().toString();

        shop().goToPage(ShopUrl.METRO);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToPickup();
        shop().interactAddressLarge().checkYmapsReady();
        shop().interactAddressLarge().clickShowAsList();
        shop().interactAddressLarge().checkPickupStoresModalVisible();
        shop().interactAddressLarge().selectRetailerByName("METRO");
        shop().interactAddressLarge().selectStoreByAddress(fullAddress);
        shop().interactAddressLarge().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().goToPage(ShopUrl.METRO.getUrl() + ALCOHOL_CATEGORY_LINK);

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
