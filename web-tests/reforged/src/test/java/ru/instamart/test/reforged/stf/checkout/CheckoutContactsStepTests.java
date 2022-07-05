package ru.instamart.test.reforged.stf.checkout;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.CookieFactory;
import ru.instamart.reforged.core.CookieProvider;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Чекаут. Шаг #2. Контакты")
public final class CheckoutContactsStepTests {

    private final ApiHelper helper = new ApiHelper();
    private UserData userData;

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod() {
        this.userData = UserManager.getQaUser();
    }

    @AfterMethod(alwaysRun = true, description = "Отмена ордера")
    public void afterTest() {
        helper.cancelAllActiveOrders(userData);
    }

    @CaseId(1679)
    @Story("Корзина")
    @Test(description = "Тест на изменение телефона и контактов", groups = "regression")
    @CookieProvider(cookieFactory = CookieFactory.class, cookies = "COOKIE_ALERT")
    public void successChangePhoneAndContacts() {
        helper.makeOrder(userData, EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID, 1);
        helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        helper.dropAndFillCart(userData, EnvironmentProperties.DEFAULT_SID);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        checkout().goToPage();
        checkout().setDeliveryOptions().clickToForSelf();

        checkout().setDeliveryOptions().fillApartment(Generate.digitalString(3));
        checkout().setDeliveryOptions().clickToSubmitForDelivery();

        checkout().checkCheckoutLoaderNotVisible();

        userData.setEmail(Generate.email());

        checkout().setContacts().fillContactInfo(userData);

        checkout().setContacts().clickToChangePhoneWithText(userData.getPhone());

        userData.setPhone(Generate.phoneNumber());

        checkout().interactEditPhoneNumberModal().fillPhoneNumber(userData.getPhone());
        checkout().interactEditPhoneNumberModal().clickToSaveModal();
        checkout().interactEditPhoneNumberModal().checkPhoneEditModalClosed();

        checkout().setContacts().clickToSubmit();

        checkout().setReplacementPolicy().clickToSubmit();

        checkout().setSlot().setLastActiveSlot();

        checkout().setPayment().clickToByCardToCourier();
        checkout().setPayment().clickToSubmitFromCheckoutColumn();

        userShipments().checkPageContains(userShipments().pageUrl());
        userShipments().clickToDetails();
        userShipments().checkUserPhoneCorrect(userData.getPhone());
        userShipments().checkUserEmailCorrect(userData.getEmail());
        userShipments().assertAll();
    }
}

