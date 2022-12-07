package ru.instamart.test.reforged.stf.checkout;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.Promos;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.core.config.UiProperties;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.Group.REGRESSION_STF;
import static ru.instamart.reforged.stf.page.StfRouter.*;
import static ru.instamart.reforged.stf.page.StfRouter.checkoutNew;

@Epic("STF UI")
@Feature("Промокоды")
public final class CheckoutPromoCodeTests {

    private final ApiHelper helper = new ApiHelper();
    private final String promoCode = Promos.getFreeOrderDeliveryPromo().getCode();
    private UserData userData;

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod() {
        this.userData = UserManager.getQaUser();
        this.helper.dropAndFillCart(userData, UiProperties.DEFAULT_AUCHAN_SID);
    }

    @AfterMethod(alwaysRun = true, description = "Отмена ордера")
    public void afterTest() {
        helper.cancelAllActiveOrders(userData);
    }

    @CaseId(2638)
    @Story("Добавление промокода к заказу")
    @Test(description = "Тест успешного применения промокода в чекауте", groups = {REGRESSION_STF})
    public void successAddPromoCode() {
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
        checkout().clickToAddPromoCode();
        checkout().interactEditPromoCodeModal().enterPromoCode(promoCode);
        checkout().interactEditPromoCodeModal().applyPromoCode();
        checkout().checkPromoCodeApplied();
        checkout().clickToDeletePromoCode();
        checkout().checkPromoCodeNotApplied();
    }

    @CaseId(2639)
    @Story("Удаление промокода из заказа")
    @Test(description = "Тест удаления промокода в чекауте", groups = REGRESSION_STF)
    public void successDeletePromoCode() {
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
        checkout().clickToAddPromoCode();
        checkout().interactEditPromoCodeModal().enterPromoCode(promoCode);
        checkout().interactEditPromoCodeModal().applyPromoCode();
        checkout().checkPromoCodeApplied();
        checkout().clickToDeletePromoCode();
        checkout().checkPromoCodeNotApplied();
        checkout().checkAddPromoCodeVisible();
    }

    @CaseId(1729)
    @Story("Добавление промокода к заказу")
    @Test(description = "Тест недобавления промокода при нажатии кнопки Отмена", groups = REGRESSION_STF)
    public void noPromoCodeAddedOnCancel() {
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
        checkout().clickToAddPromoCode();
        checkout().interactEditPromoCodeModal().enterPromoCode(promoCode);
        checkout().interactEditPromoCodeModal().cancelPromoCode();
        checkout().checkPromoCodeNotApplied();
        checkout().checkAddPromoCodeVisible();
    }

    @CaseId(1730)
    @Story("Добавление промокода к заказу")
    @Test(description = "Тест не добавления промокода при закрытии модалки промокода", groups = REGRESSION_STF)
    public void noPromoCodeAddedOnModalClose() {
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
        checkout().clickToAddPromoCode();
        checkout().interactEditPromoCodeModal().enterPromoCode(promoCode);
        checkout().interactEditPromoCodeModal().closeByEsc();
        checkout().checkPromoCodeNotApplied();
        checkout().checkAddPromoCodeVisible();
    }
}

