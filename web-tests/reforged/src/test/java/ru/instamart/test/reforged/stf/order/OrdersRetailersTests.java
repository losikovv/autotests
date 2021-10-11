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
    @Test(  description = "Тестовый заказ в Метро Москва",
            groups = "")
    public void successOrderInMetro() {
        userData = UserManager.getUser();
        helper.dropAndFillCart(userData, 1);

        shop().goToPage();
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
    @Test(  description = "Тестовый заказ в Лента Москва",
            groups = "")
    public void successOrderInLenta() {
        userData = UserManager.getUser();
        helper.dropAndFillCart(userData, 58);

        shop().goToPage(ShopUrl.LENTA);
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
    @Test(  description = "Тестовый заказ в Ашан Москва",
            groups = "")
    public void successOrderInAuchan() {
        userData = UserManager.getUser();
        helper.dropAndFillCart(userData, 72);

        shop().goToPage(ShopUrl.AUCHAN);
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
    @Test(  description = "Тестовый заказ в Азбука Вкуса Москва",
            groups = "")
    public void successOrderInAzbukaVkusa() {
        userData = UserManager.getUser();
        helper.dropAndFillCart(userData, 99);

        shop().goToPage(ShopUrl.AZBUKAVKUSA);
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
    @Test(  description = "Тестовый заказ в Вкусвилл Москва",
            groups = "")
    public void successOrderInVkusvill() {
        userData = UserManager.getUser();
        helper.dropAndFillCart(userData, 23);

        shop().goToPage(ShopUrl.VKUSVILL);
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
