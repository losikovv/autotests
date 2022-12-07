package ru.instamart.test.reforged.stf.checkout;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.PaymentCardData;
import ru.instamart.kraken.data.PaymentCards;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.Group.REGRESSION_STF;
import static ru.instamart.reforged.core.config.UiProperties.DEFAULT_AUCHAN_SID;
import static ru.instamart.reforged.stf.page.StfRouter.*;
import static ru.instamart.reforged.stf.page.StfRouter.checkoutNew;

@Epic("STF UI")
@Feature("Чекаут. Шаг #5. Оплата")
public final class CheckoutPaymentStepTests {

    private final ApiHelper helper = new ApiHelper();
    private PaymentCardData card;
    private UserData userData;

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod() {
        userData = UserManager.getQaUser();
        card = PaymentCards.testCardNo3dsWithSpasibo();
        helper.bindCardToUser(userData, DEFAULT_AUCHAN_SID, card);
        helper.dropAndFillCart(userData, DEFAULT_AUCHAN_SID);
    }

    @AfterMethod(alwaysRun = true, description = "Отмена ордера")
    public void afterTest() {
        helper.cancelAllActiveOrders(userData);
    }

    @CaseId(1678)
    @Story("Корзина")
    @Test(description = "Тест удаления карты оплаты", groups = REGRESSION_STF)
    public void successDeleteSavedCard() {

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
        checkout().setDeliveryOptions().clickToForSelf();

        checkout().setDeliveryOptions().fillApartment(Generate.digitalString(3));
        checkout().setDeliveryOptions().clickToSubmitForDelivery();

        checkout().checkCheckoutLoaderNotVisible();

        checkout().setContacts().fillContactInfo();
        checkout().setContacts().clickToSubmit();

        checkout().setReplacementPolicy().clickToSubmit();

        checkout().setSlot().setLastActiveSlot();

        checkout().setPayment().clickToByCardOnline();

        checkout().setPayment().clickToChangePaymentCard(card);

        checkout().interactEditPaymentCardModal().checkModalNotAnimated();
        checkout().interactEditPaymentCardModal().clickToDeleteModal();
        checkout().setPayment().checkSubmitOrderButtonNotClickable();
    }
}
