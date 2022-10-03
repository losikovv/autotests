package ru.instamart.test.reforged.stf.checkout_new;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.core.CookieProvider;
import ru.instamart.reforged.core.config.UiProperties;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.helper.ApiV3Helper.addFlipperActor;
import static ru.instamart.kraken.util.TimeUtil.getPastZoneDbDate;
import static ru.instamart.reforged.Group.REGRESSION_STF;
import static ru.instamart.reforged.stf.page.StfRouter.checkoutNew;
import static ru.instamart.reforged.stf.page.StfRouter.shop;

@Epic("STF UI")
@Feature("Чекаут [NEW]")
public final class CheckoutPromocodesTests {

    private final ApiHelper helper = new ApiHelper();

    @CaseId(3612)
    @Test(description = "Применение промокода на бесплатную доставку и сборку при методе Доставка", groups = {REGRESSION_STF, "checkout_web_new"})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "RETAILERS_REMINDER_MODAL", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testApplyFreeDeliveryPromo() {
        var promo = "test_prefix" + Generate.literalString(5) + Generate.string(1);

        final var userData = UserManager.getQaUser();
        addFlipperActor("checkout_web_new", userData.getId());
        addFlipperActor("checkout_web_force_all", userData.getId());
        addFlipperActor("tmp_b2c_9162_spree_shipment_changes", userData.getId());

        final String yesterday = getPastZoneDbDate(1L);

        this.helper.createPromotionCode(promo, 2757, yesterday, yesterday, 100);
        this.helper.dropAndFillCartWithoutSetAddress(userData, UiProperties.DEFAULT_METRO_MOSCOW_SID);
        this.helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().submitOrder();

        checkoutNew().checkSpinnerNotVisible();

        checkoutNew().clickFirstSlot();
        checkoutNew().checkSlotActive(1);

        checkoutNew().checkDeliveryIsNotFree();

        var amountWithoutPromo = checkoutNew().getOrderAmount();

        checkoutNew().fillPromoCode(promo);
        checkoutNew().clickApplyPromoCode();

        checkoutNew().checkSpinnerNotVisible();

        checkoutNew().checkDeliveryIsFree();
        checkoutNew().checkAmountDecreaseAfterApplyPromo(amountWithoutPromo);
    }

    @CaseId(3612)
    @Test(description = "Применение промокода на бесплатную доставку и сборку при методе Самовывоз", groups = {REGRESSION_STF, "checkout_web_new"})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "RETAILERS_REMINDER_MODAL", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testApplyFreeDeliveryPromoOnPickup() {
        var promo = "test_prefix" + Generate.literalString(5) + Generate.string(1);

        final var userData = UserManager.getQaUser();
        addFlipperActor("checkout_web_new", userData.getId());
        addFlipperActor("checkout_web_force_all", userData.getId());
        addFlipperActor("tmp_b2c_9162_spree_shipment_changes", userData.getId());

        final String yesterday = getPastZoneDbDate(1L);

        this.helper.createPromotionCode(promo, 2757, yesterday, yesterday, 100);
        this.helper.dropAndFillCartWithoutSetAddress(userData, UiProperties.DEFAULT_METRO_MOSCOW_SID);
        this.helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().submitOrder();

        checkoutNew().checkSpinnerNotVisible();

        checkoutNew().switchToPickup();

        checkoutNew().checkSpinnerNotVisible();

        checkoutNew().clickFirstSlot();
        checkoutNew().checkSlotActive(1);

        checkoutNew().checkDeliveryIsNotFree();

        var amountWithoutPromo = checkoutNew().getOrderAmount();

        checkoutNew().fillPromoCode(promo);
        checkoutNew().clickApplyPromoCode();

        checkoutNew().checkSpinnerNotVisible();

        checkoutNew().checkDeliveryIsFree();
        checkoutNew().checkAmountDecreaseAfterApplyPromo(amountWithoutPromo);
    }

    @CaseIDs(value = {@CaseId(3689), @CaseId(3646), @CaseId(3781)})
    @Test(description = "Проверка отображения примененного промокода после рефреша", groups = {REGRESSION_STF, "checkout_web_new"})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "RETAILERS_REMINDER_MODAL", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testSuccessApplyPromo() {
        var promo = "test_prefix" + Generate.literalString(5) + Generate.string(1);

        final var userData = UserManager.getQaUser();
        addFlipperActor("checkout_web_new", userData.getId());
        addFlipperActor("checkout_web_force_all", userData.getId());
        addFlipperActor("tmp_b2c_9162_spree_shipment_changes", userData.getId());

        final String yesterday = getPastZoneDbDate(1L);

        this.helper.createPromotionCode(promo, 2761, yesterday, yesterday, 100);
        this.helper.dropAndFillCartWithoutSetAddress(userData, UiProperties.DEFAULT_METRO_MOSCOW_SID);
        this.helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().submitOrder();

        checkoutNew().checkSpinnerNotVisible();

        checkoutNew().clickFirstSlot();
        checkoutNew().checkSlotActive(1);

        checkoutNew().checkDeliveryIsNotFree();

        var amountWithoutPromo = checkoutNew().getOrderAmount();

        checkoutNew().fillPromoCode(promo);
        checkoutNew().clickApplyPromoCode();

        checkoutNew().checkPromoAppliedLabelVisible();
        checkoutNew().checkAmountDecreaseAfterApplyPromo(amountWithoutPromo);

        checkoutNew().refresh();

        checkoutNew().checkPromoAppliedLabelVisible();
    }

    @CaseId(3645)
    @Test(description = "Проверка применения несуществующего промокода", groups = {REGRESSION_STF, "checkout_web_new"})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "RETAILERS_REMINDER_MODAL", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testApplyNonExistPromo() {
        var promo = "test_prefix" + Generate.literalString(5) + Generate.string(1);

        final var userData = UserManager.getQaUser();
        addFlipperActor("checkout_web_new", userData.getId());
        addFlipperActor("checkout_web_force_all", userData.getId());
        addFlipperActor("tmp_b2c_9162_spree_shipment_changes", userData.getId());

        this.helper.dropAndFillCartWithoutSetAddress(userData, UiProperties.DEFAULT_METRO_MOSCOW_SID);
        this.helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().submitOrder();

        checkoutNew().checkSpinnerNotVisible();

        checkoutNew().clickFirstSlot();
        checkoutNew().checkSlotActive(1);

        checkoutNew().checkDeliveryIsNotFree();

        var amountWithoutPromo = checkoutNew().getOrderAmount();

        checkoutNew().fillPromoCode(promo);
        checkoutNew().clickApplyPromoCode();

        checkoutNew().checkPromoCodeErrorVisible();
        checkoutNew().checkAmountNotChanged(amountWithoutPromo);
    }
}
