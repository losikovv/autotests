package ru.instamart.test.reforged.stf.checkout;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.testdata.UserData;
import ru.instamart.kraken.testdata.UserManager;
import ru.instamart.kraken.testdata.lib.Promos;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Промокоды")
public class CheckoutPromocodeTests extends BaseTest {

    private final ApiHelper helper = new ApiHelper();
    private final UserData checkoutUser = UserManager.checkoutUser();
    private final String promoCode = Promos.freeOrderDelivery().getCode();

    @BeforeClass(alwaysRun = true)
    public void prepareForCheckout() {
        helper.dropAndFillCart(checkoutUser, 1);
    }

    @CaseId(1727)
    @Story("Добавление промокода к заказу")
    @Test(
            description = "Тест успешного применения промокода в чекауте",
            groups = {"sbermarket-acceptance", "sbermarket-regression", "sbermarket-Ui-smoke"}
    )
    public void successAddPromocode() {

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().authViaPhone(checkoutUser);
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
    @Test(
            description = "Тест удаления промокода в чекауте",
            groups = {"sbermarket-acceptance", "sbermarket-regression", "sbermarket-Ui-smoke"}
    )
    public void successDeletePromocode() {

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().authViaPhone(checkoutUser);
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
    @Test(
            description = "Тест недобавления промокода при нажатии кнопки Отмена",
            groups = {"sbermarket-acceptance", "sbermarket-regression", "sbermarket-Ui-smoke"}
    )
    public void noPromocodeAddedOnCancel() {

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().authViaPhone(checkoutUser);
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
    @Test(
            description = "Тест не добавления промокода при закрытии модалки промокода",
            groups = {"sbermarket-acceptance", "sbermarket-regression", "sbermarket-Ui-smoke"}
    )
    public void noPromocodeAddedOnModalClose() {

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().authViaPhone(checkoutUser);
        shop().interactHeader().checkProfileButtonVisible();
        checkout().goToPage();
        checkout().clickToAddPromoCode();
        checkout().interactEditPromoCodeModal().enterPromoCode(promoCode);
        checkout().interactEditPromoCodeModal().close();
        checkout().checkPromoCodeNotApplied();
        checkout().checkAddPromoCodeVisible();
    }
}

