package ru.instamart.test.reforged.stf.checkout;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.Promos;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Промокоды")
public final class CheckoutPromocodeTests extends BaseTest {

    private final ApiHelper helper = new ApiHelper();
    private final String promoCode = Promos.freeOrderDelivery().getCode();
    private UserData userData;

    @AfterMethod(alwaysRun = true, description = "Отмена ордера")
    public void afterTest() {
        helper.cancelAllActiveOrders(userData);
    }

    @CaseId(1727)
    @Story("Добавление промокода к заказу")
    @Test(description = "Тест успешного применения промокода в чекауте", groups = {"acceptance", "regression", "smoke"})
    public void successAddPromocode() {
        userData = UserManager.getQaUser();
        helper.dropAndFillCart(userData, EnvironmentProperties.DEFAULT_SID);

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();
        checkout().goToPage();
        checkout().clickToAddPromoCode();
        checkout().interactEditPromoCodeModal().enterPromoCode(promoCode);
        checkout().interactEditPromoCodeModal().applyPromoCode();
        checkout().checkPromoCodeApplied();
        checkout().clickToDeletePromoCode();
    }

    @CaseId(1208)
    @Story("Удаление промокода из заказа")
    @Test(description = "Тест удаления промокода в чекауте", groups = {"acceptance", "regression"})
    public void successDeletePromocode() {
        userData = UserManager.getQaUser();
        helper.dropAndFillCart(userData, EnvironmentProperties.DEFAULT_SID);

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().authViaPhone(userData);
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
    @Test(description = "Тест недобавления промокода при нажатии кнопки Отмена", groups = {"acceptance", "regression"})
    public void noPromocodeAddedOnCancel() {
        userData = UserManager.getQaUser();
        helper.dropAndFillCart(userData, EnvironmentProperties.DEFAULT_SID);

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().authViaPhone(userData);
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
    @Test(description = "Тест не добавления промокода при закрытии модалки промокода", groups = {"acceptance", "regression"})
    public void noPromocodeAddedOnModalClose() {
        userData = UserManager.getQaUser();
        helper.dropAndFillCart(userData, EnvironmentProperties.DEFAULT_SID);

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();
        checkout().goToPage();
        checkout().clickToAddPromoCode();
        checkout().interactEditPromoCodeModal().enterPromoCode(promoCode);
        checkout().interactEditPromoCodeModal().close();
        checkout().checkPromoCodeNotApplied();
        checkout().checkAddPromoCodeVisible();
    }
}

