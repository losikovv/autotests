package ru.instamart.tests.orders;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.application.AppManager;
import ru.instamart.application.Elements;
import ru.instamart.application.Servers;
import ru.instamart.application.lib.*;
import ru.instamart.application.Config;
import ru.instamart.application.platform.modules.Shop;
import ru.instamart.application.platform.modules.User;
import ru.instamart.application.models.PaymentCardData;
import ru.instamart.application.models.JuridicalData;
import ru.instamart.application.models.UserData;
import ru.instamart.application.rest.RestAddresses;
import ru.instamart.testdata.generate;
import ru.instamart.tests.TestBase;

import static ru.instamart.application.Config.TestsConfiguration.OrdersTests.enableOrderRepeatTests;

public class BasicOrdersTests extends TestBase {

    // TODO переделать в тесты заказа новым пользоватеем (генерим нового, делаем заказ с новым телом, с привязкой новой карты + повтор, заказ с новым юрлицом + повтор и все что тут есть)

    // TODO successOrderWithCash

    // TODO successOrderWithNewBankCard

    // TODO successOrderWithCardCourier

    // TODO successOrderWithNewJuridical

    // TODO successOrderWithBonus

    // TODO successOrderWithPromocode

    // TODO successOrderWithDocumentsNeeded

    @BeforeMethod(alwaysRun = true)
    public void preconditions() {
        kraken.get().baseUrl();
        User.Do.loginAs(AppManager.session.admin);

        kraken.rest().dropCart(AppManager.session.admin, RestAddresses.Moscow.defaultAddress());
    }
    // TODO Тесты на изменение телефона и контактов

    // TODO Тесты на добавление и изменение карт оплаты


    @Test(
            description = "Тест заказа с добавлением нового юр. лица",
            groups = {"sbermarket-regression"},
            priority = 2000
    )
    public void successCompleteCheckoutWithNewJuridical() {
        kraken.rest().fillCart(AppManager.session.admin, RestAddresses.Moscow.defaultAddress());

        JuridicalData juridicalData = new JuridicalData(
                "ООО \"Новый Пользователь\"",
                generate.string(8),
                generate.digitalString(13),
                generate.digitalString(9),
                generate.digitalString(20),
                generate.digitalString(9),
                generate.string(8),
                generate.digitalString(20)
        );
        kraken.reach().checkout();
        kraken.checkout().complete(PaymentTypes.bankTransfer(), true, juridicalData);

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не удалось оформить заказ с добавлением нового юр. лица\n");

        String number = kraken.grab().currentOrderNumber();
        kraken.perform().cancelLastOrder();
        kraken.reach().admin(Pages.Admin.Order.requisites(number));

        Assert.assertEquals(kraken.grab().value(Elements.Administration.ShipmentsSection.Order.Requisites.innField()), juridicalData.getInn(),
                "Данные юр. лица не совпадают с указанными пользователем\n"
        );
    }

    @Test(
            description = "Тест заказа с изменением юр. лица",
            groups = {"sbermarket-regression"},
            priority = 2001
    )
    public void successCompleteCheckoutWithChangeJuridical() {
        kraken.rest().fillCart(AppManager.session.admin, RestAddresses.Moscow.defaultAddress());

        JuridicalData juridicalData = new JuridicalData(
                "ООО \"Измененный Пользователь\"",
                generate.string(8),
                generate.digitalString(13),
                generate.digitalString(9),
                generate.digitalString(20),
                generate.digitalString(9),
                generate.string(8),
                generate.digitalString(20)
        );
        kraken.reach().checkout();
        kraken.checkout().complete(PaymentTypes.bankTransfer(), false, juridicalData);

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не удалось оформить заказ с с изменением юр. лица\n");

        String number = kraken.grab().currentOrderNumber();
        kraken.perform().cancelLastOrder();
        kraken.reach().admin(Pages.Admin.Order.requisites(number));

        Assert.assertEquals(kraken.grab().value(Elements.Administration.ShipmentsSection.Order.Requisites.innField()), juridicalData.getInn(),
                "Данные юр. лица не совпадают с указанными пользователем\n"
        );
    }

    @Test(
            description = "Тест заказа с новой картой оплаты",
            groups = {"sbermarket-regression"},
            priority = 2002
    )
    public void successCompleteCheckoutWithNewPaymentCard() {
        kraken.rest().fillCart(AppManager.session.admin, RestAddresses.Moscow.defaultAddress());

        runTestOnlyOn(Servers.sbermarket_staging());
        PaymentCardData creditCardData = Config.TestVariables.testOrderDetails().getPaymentDetails().getCreditCard();

        kraken.reach().checkout();
        kraken.checkout().complete(PaymentTypes.cardOnline(), true, creditCardData);

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не удалось оформить заказ с новой картой оплаты\n");
    }

    @Test(
            description = "Тест заказа с любимыми товарами",
            groups = {"sbermarket-regression"},
            priority = 2003
    )
    public void successOrderWithFavProducts() {
        Shop.Catalog.Item.addToFavorites();
        kraken.get().favoritesPage();

        Shop.Cart.collect();
        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не оформляется заказ с любимыми товарами\n");
    }

    @Test(
            description = "Повтор крайнего заказа c новым номером телефона",
            groups = {"sbermarket-regression"},
            priority = 2004
    )
    public void successRepeatLastOrderWithNewPhone() {
        SoftAssert softAssert = new SoftAssert();
        UserData userData = generate.testCredentials("user");

        User.Logout.quickly();
        User.Do.registration(userData);
        kraken.perform().order();

        String order1 = kraken.grab().currentOrderNumber();
        kraken.perform().cancelLastOrder();
        kraken.reach().admin(Pages.Admin.Order.requisites(order1));

        softAssert.assertEquals(kraken.grab().strippedPhoneNumber(Elements.Administration.ShipmentsSection.Order.Requisites.phoneField()), Config.TestVariables.testOrderDetails().getContactsDetails().getPhone(),
                "Номер телефона в админке не совпадает с указанным номером во время заказа");

        User.Logout.quickly();
        User.Auth.withEmail(userData);
        kraken.perform().repeatLastOrder();
        String phone = generate.digitalString(10);
        kraken.reach().checkout();
        kraken.checkout().complete(true, phone);

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не оформляется повторный заказ с новым номером телефона\n");

        String order2 = kraken.grab().currentOrderNumber();
        kraken.perform().cancelLastOrder();
        kraken.reach().admin(Pages.Admin.Order.requisites(order2));

        softAssert.assertEquals(kraken.grab().strippedPhoneNumber(Elements.Administration.ShipmentsSection.Order.Requisites.phoneField()), phone,
                "Номер телефона в админке не совпадает с указанным номером во время заказа");

        softAssert.assertAll();
    }

    @Test(enabled = enableOrderRepeatTests,
            description = "Повтор крайнего заказа и оплата картой онлайн",
            groups = {"sbermarket-regression"},
            priority = 2005
    )
    public void successRepeatLastOrderAndPayWithCardOnline() {
        kraken.perform().repeatLastOrder();
        Shop.Cart.collect();
        Shop.Cart.proceedToCheckout();

        kraken.checkout().complete(PaymentTypes.cardOnline());

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не оформляется повторный заказ с оплатой картой онлайн\n");
    }

    @Test(enabled = enableOrderRepeatTests,
            description = "Повтор крайнего заказа и оплата картой курьеру",
            groups = {"sbermarket-regression"},
            priority = 2006
    )
    public void successRepeatLastOrderAndPayWithCardCourier() {
        kraken.perform().repeatLastOrder();
        Shop.Cart.collect();
        Shop.Cart.proceedToCheckout();

        kraken.checkout().complete(PaymentTypes.cardCourier());

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не оформляется повторный заказ с оплатой картой курьеру\n");
    }

    @Test(enabled = enableOrderRepeatTests,
            description = "Повтор крайнего заказа и оплата наличными",
            groups = {"sbermarket-regression"},
            priority = 2007
    )
    public void successRepeatLastOrderAndPayWithCash() {
        kraken.perform().repeatLastOrder();
        Shop.Cart.collect();
        Shop.Cart.proceedToCheckout();

        kraken.checkout().complete(PaymentTypes.cash());

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не оформляется повторный заказ с оплатой наличными\n");
    }

    @Test(enabled = enableOrderRepeatTests,
            description = "Повтор крайнего заказа и оплата банковским переводом",
            groups = {"sbermarket-regression"},
            priority = 2008
    )
    public void successRepeatLastOrderAndPayWithBank() {
        kraken.perform().repeatLastOrder();
        Shop.Cart.collect();
        Shop.Cart.proceedToCheckout();

        kraken.checkout().complete(PaymentTypes.bankTransfer());

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не оформляется повторный заказ с оплатой банковским переводом\n");
    }

    @AfterMethod(alwaysRun = true)
    public void cancelLastOrder() {
        kraken.perform().cancelLastOrder();
    }
}
