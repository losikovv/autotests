package ru.instamart.test.reforged.stf.order;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.testdata.Generate;
import ru.instamart.kraken.testdata.UserData;
import ru.instamart.kraken.testdata.UserManager;
import ru.instamart.reforged.core.data_provider.StoreProvider;
import ru.instamart.reforged.core.enums.ShopUrl;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Проверка заказа в разных ретейлерах")
public class OrdersRetailersTests extends BaseTest {

    private final ApiHelper helper = new ApiHelper();
    private UserData userData;

    @AfterMethod(alwaysRun = true, description = "Отмена ордера")
    public void afterTest() {
        helper.cancelAllActiveOrders(userData);
    }

    @CaseId(1627)
    @Test(description = "Тестовый заказ в Метро Москва",
            groups = "",
            dataProviderClass = StoreProvider.class,
            dataProvider = "metro"
    )
    public void successOrderInMetro(int storeId, ShopUrl shopUrl) {
        userData = UserManager.getUser();
        helper.dropAndFillCart(userData, storeId);

        shop().goToPage(shopUrl);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        checkout().goToPage();
        checkout().setDeliveryOptions().clickToSubmitForDelivery();
        checkout().setContacts().fillFirstName(Generate.literalString(8));
        checkout().setContacts().fillLastName(Generate.literalString(8));
        checkout().setContacts().fillEmail(Generate.email());
        checkout().setContacts().clickToSubmit();
        checkout().setReplacementPolicy().clickToSubmit();
        checkout().setSlot().setFirstActiveSlot();
        checkout().setPayment().clickToSubmitFromCheckoutColumn();

        userShipments().checkPageContains(userShipments().pageUrl());
        userShipments().checkStatusShipmentReady();
    }

    @CaseId(1631)
    @Test(description = "Тестовый заказ в Лента Москва",
            groups = "",
            dataProviderClass = StoreProvider.class,
            dataProvider = "lenta")
    public void successOrderInLenta(int storeId, ShopUrl shopUrl) {
        userData = UserManager.getUser();
        helper.dropAndFillCart(userData, storeId);

        shop().goToPage(shopUrl);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        checkout().goToPage();
        checkout().setDeliveryOptions().clickToSubmitForDelivery();
        checkout().setContacts().fillFirstName(Generate.literalString(8));
        checkout().setContacts().fillLastName(Generate.literalString(8));
        checkout().setContacts().fillEmail(Generate.email());
        checkout().setContacts().clickToSubmit();
        checkout().setReplacementPolicy().clickToSubmit();
        checkout().setSlot().setFirstActiveSlot();
        checkout().setPayment().clickToSubmitFromCheckoutColumn();

        userShipments().checkPageContains(userShipments().pageUrl());
        userShipments().checkStatusShipmentReady();
    }

    @CaseId(1628)
    @Test(description = "Тестовый заказ в Ашан Москва",
            groups = "",
            dataProviderClass = StoreProvider.class,
            dataProvider = "auchan")
    public void successOrderInAuchan(int storeId, ShopUrl shopUrl) {
        userData = UserManager.getUser();
        helper.dropAndFillCart(userData, storeId);

        shop().goToPage(shopUrl);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        checkout().goToPage();
        checkout().setDeliveryOptions().clickToSubmitForDelivery();
        checkout().setContacts().fillFirstName(Generate.literalString(8));
        checkout().setContacts().fillLastName(Generate.literalString(8));
        checkout().setContacts().fillEmail(Generate.email());
        checkout().setContacts().clickToSubmit();
        checkout().setReplacementPolicy().clickToSubmit();
        checkout().setSlot().setFirstActiveSlot();
        checkout().setPayment().clickToSubmitFromCheckoutColumn();

        userShipments().checkPageContains(userShipments().pageUrl());
        userShipments().checkStatusShipmentReady();
    }

    @CaseId(1629)
    @Test(description = "Тестовый заказ в Азбука Вкуса Москва",
            groups = "",
            dataProviderClass = StoreProvider.class,
            dataProvider = "azbukavkusa")
    public void successOrderInAzbukaVkusa(int storeId, ShopUrl shopUrl) {
        userData = UserManager.getUser();
        helper.dropAndFillCart(userData, storeId);

        shop().goToPage(shopUrl);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        checkout().goToPage();
        checkout().setDeliveryOptions().clickToSubmitForDelivery();
        checkout().setContacts().fillFirstName(Generate.literalString(8));
        checkout().setContacts().fillLastName(Generate.literalString(8));
        checkout().setContacts().fillEmail(Generate.email());
        checkout().setContacts().clickToSubmit();
        checkout().setReplacementPolicy().clickToSubmit();
        checkout().setSlot().setFirstActiveSlot();
        checkout().setPayment().clickToSubmitFromCheckoutColumn();

        userShipments().checkPageContains(userShipments().pageUrl());
        userShipments().checkStatusShipmentReady();
    }

    @CaseId(1630)
    @Test(description = "Тестовый заказ в Вкусвилл Москва",
            groups = "",
            dataProviderClass = StoreProvider.class,
            dataProvider = "vkusvill")
    public void successOrderInVkusvill(int storeId, ShopUrl shopUrl) {
        userData = UserManager.getUser();
        helper.dropAndFillCart(userData, storeId);

        shop().goToPage(shopUrl);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        checkout().goToPage();
        checkout().setDeliveryOptions().clickToSubmitForDelivery();
        checkout().setContacts().fillFirstName(Generate.literalString(8));
        checkout().setContacts().fillLastName(Generate.literalString(8));
        checkout().setContacts().fillEmail(Generate.email());
        checkout().setContacts().clickToSubmit();
        checkout().setReplacementPolicy().clickToSubmit();
        checkout().setSlot().setFirstActiveSlot();
        checkout().setPayment().clickToSubmitFromCheckoutColumn();

        userShipments().checkPageContains(userShipments().pageUrl());
        userShipments().checkStatusShipmentReady();
    }
}
