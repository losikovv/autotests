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

    @BeforeClass(alwaysRun = true)
    public void prepareForCheckout() {

    }

    @CaseId(1727)
    @Story("Добавление промокода к заказу")
    @Test(
            description = "Тест успешного применения промокода в чекауте",
            groups = {"sbermarket-acceptance", "sbermarket-regression", "sbermarket-Ui-smoke"}
    )
    public void successAddPromocode() {
        helper.dropAndFillCart(checkoutUser, 1);

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().checkPhoneInputIsClickable();
        home().interactAuthModal().authViaPhone(checkoutUser);
        shop().interactHeader().checkProfileButtonVisible();
        checkout().goToPage();
        checkout().clickToAddPromoCode();
        checkout().interactEditPromoCodeModal().enterPromoCode(Promos.freeOrderDelivery().getCode());
        checkout().interactEditPromoCodeModal().applyPromoCode();
        checkout().checkPromoCodeApplied();
    }

    @CaseId(1208)
    @Story("Удаление промокода из заказа")
    @Test(
            description = "Тест удаления промокода в чекауте",
            groups = {"sbermarket-acceptance", "sbermarket-regression", "sbermarket-Ui-smoke"}
    )
    public void successDeletePromocode() {
//        kraken.reach().checkout();
//        if (!kraken.detect().isPromocodeApplied()) {
//            PromocodeActions.add(Promos.freeOrderDelivery());
//        }
//        PromocodeActions.delete();
//        promoChecks.checkIsPromoCodeNotApplied();
    }

    @CaseId(1729)
    @Story("Добавление промокода к заказу")
    @Test(
            description = "Тест недобавления промокода при нажатии кнопки Отмена",
            groups = {"sbermarket-acceptance", "sbermarket-regression", "sbermarket-Ui-smoke"}
    )
    public void noPromocodeAddedOnCancel() {
//        kraken.reach().checkout();
//
//        if (kraken.detect().isPromocodeApplied()) {
//            PromocodeActions.delete();
//        }
//
//        PromocodeActions.Modal.open();
//        PromocodeActions.Modal.fill(Promos.freeOrderDelivery().getCode());
//        PromocodeActions.Modal.cancel();
//        promoChecks.checkIsPromoCodeNotApplied();
    }

    @CaseId(1730)
    @Story("Добавление промокода к заказу")
    @Test(
            description = "Тест не добавления промокода при закрытии модалки промокода",
            groups = {"sbermarket-acceptance", "sbermarket-regression", "sbermarket-Ui-smoke"}
    )
    public void noPromocodeAddedOnModalClose() {
//        kraken.reach().checkout();
//        if (kraken.detect().isPromocodeApplied()) {
//            PromocodeActions.delete();
//        }
//        PromocodeActions.Modal.open();
//        PromocodeActions.Modal.fill(Promos.freeOrderDelivery().getCode());
//        PromocodeActions.Modal.close();
//        promoChecks.checkIsPromoCodeNotApplied();
    }
}

