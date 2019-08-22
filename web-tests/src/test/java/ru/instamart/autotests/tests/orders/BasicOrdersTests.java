package ru.instamart.autotests.tests.orders;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.autotests.application.*;
import ru.instamart.autotests.appmanager.ShopHelper;
import ru.instamart.autotests.models.CreditCardData;
import ru.instamart.autotests.models.JuridicalData;
import ru.instamart.autotests.models.UserData;
import ru.instamart.autotests.testdata.generate;
import ru.instamart.autotests.tests.TestBase;

import static ru.instamart.autotests.application.Config.TestsConfiguration.OrdersTests.enableOrderRepeatTests;

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
        kraken.perform().loginAs(kraken.session.admin);
        ShopHelper.ShippingAddress.change(Addresses.Moscow.testAddress());
        ShopHelper.Cart.drop();
    }
    // TODO Тесты на изменение телефона и контактов

    // TODO Тесты на добавление и изменение карт оплаты


    @Test(
            description = "Тест заказа с добавлением нового юр. лица",
            groups = {"regression"},
            priority = 2000
    )
    public void successCompleteCheckoutWithNewJuridical() {
        JuridicalData juridicalData = new JuridicalData(
                "ООО \"Новый Пользователь\"",
                generate.string(8),
                generate.digitString(13),
                generate.digitString(9),
                generate.digitString(20),
                generate.digitString(9),
                generate.string(8),
                generate.digitString(20)
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
            groups = {"regression"},
            priority = 2001
    )
    public void successCompleteCheckoutWithChangeJuridical() {
        JuridicalData juridicalData = new JuridicalData(
                "ООО \"Измененный Пользователь\"",
                generate.string(8),
                generate.digitString(13),
                generate.digitString(9),
                generate.digitString(20),
                generate.digitString(9),
                generate.string(8),
                generate.digitString(20)
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
            groups = {"regression"},
            priority = 2002
    )
    public void successCompleteCheckoutWithNewPaymentCard() {
        runTestOnlyOn(Environments.instamart_staging());
        CreditCardData creditCardData = Config.TestVariables.testOrderDetails().getPaymentDetails().getCreditCard();

        kraken.reach().checkout();
        kraken.checkout().complete(PaymentTypes.cardOnline(), true, creditCardData);

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не удалось оформить заказ с новой картой оплаты\n");
    }

    @Test(
            description = "Тест заказа с любимыми товарами",
            groups = {"regression"},
            priority = 2003
    )
    public void successOrderWithFavProducts() {
        ShopHelper.Catalog.Item.addToFavorites();
        kraken.get().favoritesPage();

        ShopHelper.Cart.collect();
        ShopHelper.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не оформляется заказ с любимыми товарами\n");
    }

    @Test(
            description = "Повтор крайнего заказа c новым номером телефона",
            groups = {"regression"},
            priority = 2004
    )
    public void successRepeatLastOrderWithNewPhone() {
        SoftAssert softAssert = new SoftAssert();
        UserData userData = generate.testCredentials("user");

        kraken.perform().quickLogout();
        kraken.perform().registration(userData);
        kraken.perform().order();

        String order1 = kraken.grab().currentOrderNumber();
        kraken.perform().cancelLastOrder();
        kraken.reach().admin(Pages.Admin.Order.requisites(order1));

        softAssert.assertEquals(kraken.grab().strippedPhoneNumber(Elements.Administration.ShipmentsSection.Order.Requisites.phoneField()), Config.TestVariables.testOrderDetails().getContactsDetails().getPhone(),
                "Номер телефона в админке не совпадает с указанным номером во время заказа");

        kraken.perform().quickLogout();
        kraken.perform().authorisation(userData);
        kraken.perform().repeatLastOrder();
        String phone = generate.digitString(10);
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
            groups = {"acceptance","regression"},
            priority = 2005
    )
    public void successRepeatLastOrderAndPayWithCardOnline() {
        kraken.perform().repeatLastOrder();
        ShopHelper.Cart.collect();
        ShopHelper.Cart.proceedToCheckout();

        kraken.checkout().complete(PaymentTypes.cardOnline());

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не оформляется повторный заказ с оплатой картой онлайн\n");
    }

    @Test(enabled = enableOrderRepeatTests,
            description = "Повтор крайнего заказа и оплата картой курьеру",
            groups = {"regression"},
            priority = 2006
    )
    public void successRepeatLastOrderAndPayWithCardCourier() {
        kraken.perform().repeatLastOrder();
        ShopHelper.Cart.collect();
        ShopHelper.Cart.proceedToCheckout();

        kraken.checkout().complete(PaymentTypes.cardCourier());

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не оформляется повторный заказ с оплатой картой курьеру\n");
    }

    @Test(enabled = enableOrderRepeatTests,
            description = "Повтор крайнего заказа и оплата наличными",
            groups = {"regression"},
            priority = 2007
    )
    public void successRepeatLastOrderAndPayWithCash() {
        kraken.perform().repeatLastOrder();
        ShopHelper.Cart.collect();
        ShopHelper.Cart.proceedToCheckout();

        kraken.checkout().complete(PaymentTypes.cash());

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не оформляется повторный заказ с оплатой наличными\n");
    }

    @Test(enabled = enableOrderRepeatTests,
            description = "Повтор крайнего заказа и оплата банковским переводом",
            groups = {"regression"},
            priority = 2008
    )
    public void successRepeatLastOrderAndPayWithBank() {
        kraken.perform().repeatLastOrder();
        ShopHelper.Cart.collect();
        ShopHelper.Cart.proceedToCheckout();

        kraken.checkout().complete(PaymentTypes.bankTransfer());

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не оформляется повторный заказ с оплатой банковским переводом\n");
    }

    @AfterMethod(alwaysRun = true)
    public void cancelLastOrder() {
        kraken.perform().cancelLastOrder();
    }
}
