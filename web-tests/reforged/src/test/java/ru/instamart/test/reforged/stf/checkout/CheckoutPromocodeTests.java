package ru.instamart.test.reforged.stf.checkout;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.Promos;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.core.CookieProvider;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.stf.page.StfRouter.checkout;
import static ru.instamart.reforged.stf.page.StfRouter.shop;

@Epic("STF UI")
@Feature("Промокоды")
public final class CheckoutPromocodeTests {

    private final ApiHelper helper = new ApiHelper();
    private final String promoCode = Promos.getFreeOrderDeliveryPromo().getCode();
    private UserData userData;

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod() {
        this.userData = UserManager.getQaUser();
        this.helper.dropAndFillCart(userData, EnvironmentProperties.DEFAULT_SID);
    }

    @AfterMethod(alwaysRun = true, description = "Отмена ордера")
    public void afterTest() {
        helper.cancelAllActiveOrders(userData);
    }

    @CaseId(2638)
    @Story("Добавление промокода к заказу")
    @Test(description = "Тест успешного применения промокода в чекауте", groups = {"production", "regression", "smoke"})
    public void successAddPromocode() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        checkout().goToPage();
        checkout().clickToAddPromoCode();
        checkout().interactEditPromoCodeModal().enterPromoCode(promoCode);
        checkout().interactEditPromoCodeModal().applyPromoCode();
        checkout().checkPromoCodeApplied();
        checkout().clickToDeletePromoCode();
        checkout().checkPromoCodeNotApplied();
    }

    @CaseId(2639)
    @Story("Удаление промокода из заказа")
    @Test(description = "Тест удаления промокода в чекауте", groups = "regression")
    public void successDeletePromocode() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        checkout().goToPage();
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
    @Test(description = "Тест недобавления промокода при нажатии кнопки Отмена", groups = "regression")
    public void noPromocodeAddedOnCancel() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        checkout().goToPage();
        checkout().clickToAddPromoCode();
        checkout().interactEditPromoCodeModal().enterPromoCode(promoCode);
        checkout().interactEditPromoCodeModal().cancelPromoCode();
        checkout().checkPromoCodeNotApplied();
        checkout().checkAddPromoCodeVisible();
    }

    @CaseId(1730)
    @Story("Добавление промокода к заказу")
    @Test(description = "Тест не добавления промокода при закрытии модалки промокода", groups = "regression")
    public void noPromocodeAddedOnModalClose() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        checkout().goToPage();
        checkout().clickToAddPromoCode();
        checkout().interactEditPromoCodeModal().enterPromoCode(promoCode);
        checkout().interactEditPromoCodeModal().closeByEsc();
        checkout().checkPromoCodeNotApplied();
        checkout().checkAddPromoCodeVisible();
    }
}

