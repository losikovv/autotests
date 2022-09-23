package ru.instamart.test.reforged.stf_prod.checkout;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.core.config.UiProperties;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.Group.STF_PROD_S;
import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Чекаут. Шаг #4. Время получения.")
public final class CheckoutDeliverySlotsTests {

    private final ApiHelper helper = new ApiHelper();
    private UserData userData;

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod() {
        this.userData = UserManager.getQaUser();
        this.helper.dropAndFillCart(userData, UiProperties.DEFAULT_SID);
    }

    @AfterMethod(alwaysRun = true, description = "Отмена ордера")
    public void afterTest() {
        helper.cancelAllActiveOrders(userData);
    }

    @CaseId(2648)
    @Story("Корзина")
    @Test(description = "Изменение ранее выбранного слота доставки", groups = {STF_PROD_S})
    public void successChangePreviousDeliverySlotAndOrder() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().submitOrder();

        checkout().checkCheckoutButtonIsVisible();
        checkout().setDeliveryOptions().clickToForSelf();

        checkout().setDeliveryOptions().fillApartment(Generate.digitalString(3));
        checkout().setDeliveryOptions().clickToSubmitForDelivery();

        checkout().checkCheckoutLoaderNotVisible();

        checkout().setContacts().fillContactInfo();
        checkout().setContacts().clickToSubmit();

        checkout().setReplacementPolicy().clickToSubmit();

        checkout().setSlot().setLastActiveSlot();

        shop().goToPage();
        shop().interactHeader().checkProfileButtonVisible();
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
        var deliveryDate = checkout().setSlot().getDeliveryDate();

        checkout().setPayment().clickToByCardToCourier();
        checkout().setPayment().clickToSubmitFromCheckoutColumn();

        userShipments().checkPageContains(userShipments().pageUrl());

        userShipments().checkDeliveryIntervalCorrectProd(deliveryDate, deliveryTime);
    }
}
