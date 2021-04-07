package ru.instamart.tests.ui.orders;

import instamart.api.common.RestAddresses;
import instamart.core.settings.Config;
import instamart.core.testdata.TestVariables;
import instamart.core.testdata.UserManager;
import instamart.core.testdata.ui.Generate;
import instamart.core.testdata.ui.PaymentTypes;
import instamart.ui.checkpoints.users.OrdersCheckpoints;
import instamart.ui.common.lib.Addresses;
import instamart.ui.common.lib.Pages;
import instamart.ui.common.pagesdata.JuridicalData;
import instamart.ui.common.pagesdata.PaymentCardData;
import instamart.ui.common.pagesdata.PaymentTypeData;
import instamart.ui.modules.Shop;
import instamart.ui.modules.User;
import instamart.ui.objectsmap.Elements;
import io.qameta.allure.Issue;
import io.qase.api.annotation.CaseId;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.tests.ui.TestBase;

import static io.qameta.allure.Allure.step;

public class BasicOrdersTests extends TestBase {

    // TODO переделать в тесты заказа новым пользоватеем (генерим нового, делаем заказ с новым телом, с привязкой новой карты + повтор, заказ с новым юрлицом + повтор и все что тут есть)

    // TODO successOrderWithCash

    // TODO successOrderWithNewBankCard

    // TODO successOrderWithCardCourier

    // TODO successOrderWithNewJuridical

    // TODO successOrderWithBonus

    // TODO successOrderWithPromocode

    // TODO successOrderWithDocumentsNeeded
    OrdersCheckpoints orderCheck = new OrdersCheckpoints();


    @BeforeMethod(alwaysRun = true,
            description ="Аутентификация и выбор адреса доставки")
    public void preconditions() {
        //runTestOnlyOnServer("preprod");
        String phone;
        phone = Generate.phoneNumber();
        step("Аутентификация", ()-> {
            kraken.get().baseUrl();
            Shop.AuthModal.open();
            User.Do.registration(phone);
            User.Do.sendSms(Config.DEFAULT_SMS);
        });
        step("Выбор адреса доставки", ()-> {
            Shop.ShippingAddressModal.open();
            Shop.ShippingAddressModal.fill(Addresses.Moscow.defaultAddress());
            Shop.ShippingAddressModal.selectAddressSuggest();
            Shop.ShippingAddressModal.submit();
        });
    }

    @AfterMethod(alwaysRun = true,
            description ="Очистка окружения после теста")
    public void afterTest(){
        kraken.perform().cancelLastActiveOrder();
        User.Logout.quickly();
        kraken.perform().deleteAllCookies();
    }
    // TODO Тесты на изменение телефона и контактов

    // TODO Тесты на добавление и изменение карт оплаты


    @Test(
            description = "Тест заказа с добавлением нового юр. лица",
            groups = {"sbermarket-regression",}
    )
    public void successCompleteCheckoutWithNewJuridical() {
        kraken.apiV2().fillCart(UserManager.getDefaultAdmin(), RestAddresses.Moscow.learningCenter());

        JuridicalData company = UserManager.juridical();
        kraken.reach().checkout();
        kraken.checkout().complete(PaymentTypes.bankTransfer(), true, company);

        Assert.assertTrue(kraken.detect().isOrderPlaced(),
                failMessage("Не удалось оформить заказ с добавлением нового юр. лица"));

        String number = kraken.grab().shipmentNumber();
        kraken.perform().cancelOrder();
        kraken.reach().admin(Pages.Admin.Order.requisites(number));

        Assert.assertEquals(kraken.grab().value(Elements.Administration.ShipmentsSection.OrderDetailsPage.Requisites.innField()), company.getInn(),
                "Данные юр. лица не совпадают с указанными пользователем\n"
        );
    }

    @Test(
            description = "Тест заказа с изменением юр. лица",
            groups = {"sbermarket-regression",}
    )
    public void successCompleteCheckoutWithChangeJuridical() {
        kraken.apiV2().fillCart(UserManager.getDefaultAdmin(), RestAddresses.Moscow.defaultAddress());

        JuridicalData newCompany = UserManager.juridical();
        kraken.reach().checkout();
        kraken.checkout().complete(PaymentTypes.bankTransfer(), false, newCompany);

        Assert.assertTrue(kraken.detect().isOrderPlaced(),
                "Не удалось оформить заказ с с изменением юр. лица\n");

        String number = kraken.grab().shipmentNumber();
        kraken.perform().cancelOrder();
        kraken.reach().admin(Pages.Admin.Order.requisites(number));

        Assert.assertEquals(kraken.grab().value(Elements.Administration.ShipmentsSection.OrderDetailsPage.Requisites.innField()), newCompany.getInn(),
                "Данные юр. лица не совпадают с указанными пользователем\n"
        );
    }
    
    @CaseId(1672)
    @Test(
            description = "Тест заказа с новой картой оплаты c 3ds",
            groups = {"sbermarket-regression", "testing", "sbermarket-Ui-smoke"}
    )
    public void successCompleteCheckoutWithNewPaymentCard() {
        PaymentCardData creditCardData = TestVariables.testOrderDetails().getPaymentDetails().getCreditCard();
        PaymentTypeData paymentMethod = PaymentTypes.cardOnline();

        Shop.Cart.collectFirstTime();
        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete(paymentMethod, true, creditCardData);
        orderCheck.checkOrderSuccessCreation();
        orderCheck.checkPaymentMethod(paymentMethod);
    }

    @CaseId(2066)
    @Test(
            description = "Тест заказа с новой картой оплаты без 3ds",
            groups = {"sbermarket-regression", "testing", "sbermarket-Ui-smoke"}
            //enabled = false
    )
    public void successCompleteCheckoutWithNewNoSecurePaymentCard() {
        PaymentCardData creditCardData = TestVariables.testOrderDetailsCus().getPaymentDetails().getCreditCard();
        PaymentTypeData paymentMethod = PaymentTypes.cardOnline();

        Shop.Cart.collectFirstTime();
        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete(paymentMethod, true, creditCardData);
        orderCheck.checkOrderSuccessCreation();
        orderCheck.checkPaymentMethod(paymentMethod);
    }

    @CaseId(1681)
    @Test(
            description = "Тест заказа с любимыми товарами",
            groups = {"sbermarket-regression","testing", "sbermarket-Ui-smoke"}
    )
    public void successOrderWithFavProducts() {
        Shop.Catalog.Item.addToFavorites();
        Shop.Cart.collectFirstTime();
        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete();
        orderCheck.checkOrderSuccessCreation();
    }

    @CaseId(1673)
    @Test(
            description = "Тест заказа с оплатой курьеру",
            groups = {"sbermarket-regression","testing", "sbermarket-Ui-smoke"}
    )
    public void successOrderWithCardCourier() {
        PaymentTypeData paymentMethod = PaymentTypes.cardCourier();

        Shop.Cart.collectFirstTime();
        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete(paymentMethod);
        orderCheck.checkOrderSuccessCreation();
        orderCheck.checkPaymentMethod(paymentMethod);
    }

}
