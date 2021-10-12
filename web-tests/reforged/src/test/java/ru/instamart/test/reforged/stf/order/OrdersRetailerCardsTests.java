package ru.instamart.test.reforged.stf.order;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.enums.Server;
import ru.instamart.kraken.enums.Tenant;
import ru.instamart.kraken.listener.Run;
import ru.instamart.kraken.testdata.Generate;
import ru.instamart.kraken.testdata.RetailerCards;
import ru.instamart.kraken.testdata.UserData;
import ru.instamart.kraken.testdata.UserManager;
import ru.instamart.reforged.CookieFactory;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.stf.page.StfRouter.*;
import static ru.instamart.reforged.stf.page.StfRouter.userShipments;

@Epic("STF UI")
@Feature("Покупка товара со скидочной картой")
public final class OrdersRetailerCardsTests extends BaseTest {

    private final ApiHelper helper = new ApiHelper();
    private UserData userData;

    @BeforeMethod(alwaysRun = true, description = "Наполнение корзины")
    public void beforeTest() {
        userData = UserManager.getUser();
        helper.auth(userData);
        helper.dropAndFillCart(userData, EnvironmentProperties.DEFAULT_SID);
    }

    @AfterMethod(alwaysRun = true, description = "Отмена ордера")
    public void afterTest() {
        helper.cancelAllActiveOrders(userData);
    }

    @Run(onTenant = Tenant.METRO)
    @Test(  description = "Тест заказа с картой Метро (только METRO WL)",
            groups = {"metro-acceptance","metro-regression"}
    )
    public void successOrderWithMetroRetailerCard() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();
        shop().addCookie(CookieFactory.COOKIE_ALERT);

        checkout().goToPage();
        checkout().setDeliveryOptions().fillApartment(Generate.digitalString(3));
        checkout().setDeliveryOptions().clickToSubmitForDelivery();

        checkout().setContacts().fillFirstName(Generate.literalString(8));
        checkout().setContacts().fillLastName(Generate.literalString(8));
        checkout().setContacts().fillEmail(Generate.email());
        checkout().setContacts().clickToSubmit();

        checkout().setReplacementPolicy().clickToSubmit();

        checkout().setSlot().setFirstActiveSlot();

        checkout().clickToAddRetailerCard();
        checkout().interactEditRetailerCardModal()
                .fillValue(RetailerCards.metro().getCardNumber());
        checkout().interactEditRetailerCardModal()
                .clickToSaveModal();

        checkout().setPayment().clickToSubmitFromCheckoutColumn();

        userShipments().checkPageContains(userShipments().pageUrl());
        userShipments().checkStatusShipmentReady();
    }

    @Run(onTenant = Tenant.SBERMARKET, onServer = Server.PRODUCTION)
    @Test(  description = "Тест заказа с картой Вкусвилл (только Sbermarket)",
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    )
    public void successOrderWithVkusvillRetailerCard() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();
        shop().addCookie(CookieFactory.COOKIE_ALERT);

        checkout().goToPage();
        checkout().setDeliveryOptions().fillApartment(Generate.digitalString(3));
        checkout().setDeliveryOptions().clickToSubmitForDelivery();

        checkout().setContacts().fillFirstName(Generate.literalString(8));
        checkout().setContacts().fillLastName(Generate.literalString(8));
        checkout().setContacts().fillEmail(Generate.email());
        checkout().setContacts().clickToSubmit();

        checkout().setReplacementPolicy().clickToSubmit();

        checkout().setSlot().setFirstActiveSlot();

        checkout().clickToAddRetailerCard();
        checkout().interactEditRetailerCardModal()
                .fillValue(RetailerCards.vkusvill().getCardNumber());
        checkout().interactEditRetailerCardModal()
                .clickToSaveModal();

        checkout().setPayment().clickToSubmitFromCheckoutColumn();

        userShipments().checkPageContains(userShipments().pageUrl());
        userShipments().checkStatusShipmentReady();
    }
}
