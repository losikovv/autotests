package ru.instamart.test.reforged.stf.checkout;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.JuridicalData;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.CookieFactory;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.stf.page.StfRouter.*;
import static ru.instamart.reforged.stf.page.StfRouter.userShipments;

@Epic("STF UI")
@Feature("Чекаут. Шаг #2. Контакты")
public class CheckoutContactsStepTests {

    private final ApiHelper helper = new ApiHelper();
    private UserData userData;
    private UserData changedUserData;

    @AfterMethod(alwaysRun = true, description = "Отмена ордера")
    public void afterTest() {
        helper.cancelAllActiveOrders(userData);
    }

    @CaseId(1679)
    @Story("Корзина")
    @Test(description = "Тест на изменение телефона и контактов", groups = "regression")
    public void successChangePhoneAndContacts() {
        userData = UserManager.getQaUser();
        helper.makeOrder(userData, EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID, 1);
        helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        helper.dropAndFillCart(userData, EnvironmentProperties.DEFAULT_SID);

        var company = JuridicalData.juridical();

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();
        shop().addCookie(CookieFactory.COOKIE_ALERT);

        checkout().goToPage();
        checkout().setDeliveryOptions().clickToForBusiness();
        checkout().setDeliveryOptions().clickToAddCompany();

        checkout().interactAddCompanyModal().fillCompany(company);
        checkout().interactAddCompanyModal().clickToOkButton();

        checkout().setDeliveryOptions().fillApartment(company.getJuridicalAddress());
        checkout().setDeliveryOptions().clickToSubmitForDelivery();

        checkout().checkCheckoutLoaderNotVisible();

        changedUserData = UserManager.getQaUser();

        checkout().setContacts().fillContactInfo(changedUserData);

        checkout().setContacts().clickToChangePhoneWithText(userData.getPhone());

        checkout().interactEditPhoneNumberModal().clearPhoneNumber();
        checkout().interactEditPhoneNumberModal().fillPhoneNumber(changedUserData.getPhone());
        checkout().interactEditPhoneNumberModal().clickToSaveModal();
        checkout().interactEditPhoneNumberModal().checkPhoneEditModalClosed();

        checkout().setContacts().clickToSubmit();

        checkout().setReplacementPolicy().clickToSubmit();

        checkout().setSlot().checkFirstSlotClickable();

        checkout().waitPageLoad();
        checkout().setSlot().setNextDay();

        checkout().waitPageLoad();
        checkout().setSlot().setLastActiveSlot();

        checkout().setPayment().clickToByCardToCourier();
        checkout().setPayment().clickToSubmitFromCheckoutColumn();

        userShipments().checkPageContains(userShipments().pageUrl());
        userShipments().clickToDetails();
        userShipments().checkUserPhoneCorrect(changedUserData.getPhone());
        userShipments().checkUserEmailCorrect(changedUserData.getEmail());
        userShipments().assertAll();
    }
}

