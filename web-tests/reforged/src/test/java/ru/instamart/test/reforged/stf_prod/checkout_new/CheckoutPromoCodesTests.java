package ru.instamart.test.reforged.stf_prod.checkout_new;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import io.qameta.allure.TmsLinks;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.Promos;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.listener.Skip;
import ru.instamart.reforged.core.config.UiProperties;

import static ru.instamart.reforged.Group.STF_PROD_S;
import static ru.instamart.reforged.stf.page.StfRouter.checkoutNew;
import static ru.instamart.reforged.stf.page.StfRouter.shop;

@Epic("STF UI")
@Feature("Чекаут V3")
public final class CheckoutPromoCodesTests {

    private static final ThreadLocal<UserData> userData = new ThreadLocal<>();
    private final ApiHelper helper = new ApiHelper();
    private final String promoCode = Promos.getFreeOrderDeliveryPromo().getCode();

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod() {
        userData.set(UserManager.getQaUser());
        //Промокод хочет сумму заказа от 1000 рублей
        this.helper.dropAndFillCartWithAmount(userData.get(), UiProperties.DEFAULT_SID, 1100);
    }

    @AfterMethod(alwaysRun = true, description = "Отмена ордера")
    public void afterTest() {
        helper.cancelAllActiveOrders(userData.get());
    }

    @TmsLink("3612")
    @Skip
    //выключено так как sbervesna истек
    @Test(description = "Применение промокода на бесплатную доставку и сборку при методе Доставка", groups = {STF_PROD_S})
    public void testApplyFreeDeliveryPromo() {
        shop().goToPageProd();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData.get());
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().submitOrder();

        checkoutNew().checkSpinnerNotVisible();

        checkoutNew().clickFirstSlot();
        checkoutNew().checkSlotActive(1);

        checkoutNew().checkDeliveryIsNotFree();

        var amountWithoutPromo = checkoutNew().getOrderAmount();

        checkoutNew().fillPromoCode(promoCode);
        checkoutNew().clickApplyPromoCode();

        checkoutNew().checkSpinnerNotVisible();

        checkoutNew().checkDeliveryIsFree();
        checkoutNew().checkAmountDecreaseAfterApplyPromo(amountWithoutPromo);
    }

    @TmsLinks(value = {@TmsLink("3689"), @TmsLink("3646"), @TmsLink("3781")})
    @Test(description = "Проверка отображения примененного промокода после рефреша", groups = {STF_PROD_S})
    public void testSuccessApplyPromo() {
        shop().goToPageProd();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData.get());
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().submitOrder();

        checkoutNew().checkSpinnerNotVisible();

        checkoutNew().clickFirstSlot();
        checkoutNew().checkSlotActive(1);

        checkoutNew().checkDeliveryIsNotFree();

        var amountWithoutPromo = checkoutNew().getOrderAmount();

        checkoutNew().fillPromoCode(promoCode);
        checkoutNew().clickApplyPromoCode();

        checkoutNew().checkPromoCancelButtonVisible();
        checkoutNew().checkAmountDecreaseAfterApplyPromo(amountWithoutPromo);

        checkoutNew().refresh();

        checkoutNew().checkPromoCancelButtonVisible();
        checkoutNew().checkPromoCodeValue(promoCode);
    }

    @TmsLink("3645")
    @Test(description = "Проверка применения несуществующего промокода", groups = {STF_PROD_S})
    public void testApplyNonExistPromo() {
        var promo = "test_prefix" + Generate.literalString(5) + Generate.string(1);

        shop().goToPageProd();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData.get());
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
