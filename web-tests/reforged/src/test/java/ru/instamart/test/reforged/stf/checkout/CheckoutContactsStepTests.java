package ru.instamart.test.reforged.stf.checkout;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.util.StringUtil;
import ru.instamart.reforged.core.config.UiProperties;

import static ru.instamart.reforged.Group.REGRESSION_STF;
import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Чекаут. Шаг #2. Контакты")
public final class CheckoutContactsStepTests {

    private static final ThreadLocal<UserData> userData = new ThreadLocal<>();
    private final ApiHelper helper = new ApiHelper();

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod() {
        userData.set(UserManager.getQaUser());
    }

    @AfterMethod(alwaysRun = true, description = "Отмена ордера")
    public void afterTest() {
        helper.cancelAllActiveOrders(userData.get());
    }

    @TmsLink("1679")
    @Story("Корзина")
    @Test(description = "Тест на изменение телефона и контактов", groups = REGRESSION_STF)
    public void successChangePhoneAndContacts() {
        helper.makeOrder(userData.get(), UiProperties.DEFAULT_AUCHAN_SID, 1);
        helper.setAddress(userData.get(), RestAddresses.Moscow.defaultAddress());

        helper.dropAndFillCart(userData.get(), UiProperties.DEFAULT_AUCHAN_SID);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData.get());
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().submitOrder();

        checkoutNew().waitPageLoad();
        checkoutNew().clickOrderForBusiness();
        checkoutNew().interactB2BOrderModal().checkModalVisible();
        checkoutNew().interactB2BOrderModal().clickConfirm();
        checkoutNew().interactB2BOrderModal().checkModalNotVisible();

        checkout().setDeliveryOptions().clickToForSelf();

        checkout().setDeliveryOptions().fillApartment(Generate.digitalString(3));
        checkout().setDeliveryOptions().clickToSubmitForDelivery();

        checkout().checkCheckoutLoaderNotVisible();

        userData.get().setEmail(Generate.email());

        checkout().setContacts().fillContactInfo(userData.get());

        checkout().setContacts().clickToChangePhoneWithText(userData.get().getPhone());

        userData.get().setPhone(Generate.phoneNumber());

        checkout().interactEditPhoneNumberModal().fillPhoneNumber(userData.get().getPhone());
        checkout().interactEditPhoneNumberModal().clickToSaveModal();
        checkout().interactEditPhoneNumberModal().checkPhoneEditModalClosed();

        checkout().setContacts().clickToSubmit();

        checkout().setReplacementPolicy().clickToSubmit();

        checkout().setSlot().setLastActiveSlot();

        checkout().setPayment().clickToByCardToCourier();
        checkout().setPayment().clickToSubmitFromCheckoutColumn();

        userShipment().waitPageLoad();
        userShipment().checkUserPhone(StringUtil.convertDigitsStringToPhoneNumber(userData.get().getPhone()));
    }
}

