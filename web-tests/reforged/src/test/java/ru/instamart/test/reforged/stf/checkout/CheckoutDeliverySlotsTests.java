package ru.instamart.test.reforged.stf.checkout;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.core.config.UiProperties;

import static ru.instamart.reforged.Group.REGRESSION_STF;
import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Чекаут. Шаг #4. Время получения.")
public final class CheckoutDeliverySlotsTests {

    private final ApiHelper helper = new ApiHelper();
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

    @TmsLink("2648")
    @Story("Корзина")
    @Test(description = "Изменение ранее выбранного слота доставки", groups = REGRESSION_STF)
    public void successChangePreviousDeliverySlotAndOrder() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().submitOrder();

        checkoutNew().waitPageLoad();
        checkoutNew().clickOrderForBusiness();
        checkoutNew().interactB2BOrderModal().checkModalVisible();
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

        shop().goToPage();
        shop().interactHeader().clickToCart();
        shop().interactCart().submitOrder();

        checkout().checkCheckoutButtonIsVisible();

        checkout().setDeliveryOptions().clickToSubmitForDelivery();
        checkout().checkCheckoutLoaderNotVisible();

        checkout().setContacts().fillContactInfo();
        checkout().setContacts().clickToSubmit();

        checkout().checkCheckoutLoaderNotVisible();
        checkout().setReplacementPolicy().clickToSubmit();

        checkout().setSlot().setNextDay();
        checkout().setSlot().setFirstActiveSlot();

        var deliveryTime = checkout().setSlot().getDeliveryTime();

        checkout().setPayment().clickToByCardToCourier();
        checkout().setPayment().clickToSubmitFromCheckoutColumn();

        userShipment().checkPageContains(userShipments().pageUrl());

        userShipment().checkDeliveryIntervalCorrect("Завтра, " + deliveryTime);
    }

    @TmsLink("2649")
    @Story("Корзина")
    @Test(description = "Невозможность завершения заказа при невыбранном слоте доставки", groups = REGRESSION_STF)
    public void failedContinueWithUnselectedDeliverySlot() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().submitOrder();

        checkoutNew().waitPageLoad();
        checkoutNew().clickOrderForBusiness();
        checkoutNew().interactB2BOrderModal().checkModalVisible();
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

        checkout().setSlot().clickToSubmit();
        checkout().setSlot().checkSlotSetFailVisible();
    }
}
