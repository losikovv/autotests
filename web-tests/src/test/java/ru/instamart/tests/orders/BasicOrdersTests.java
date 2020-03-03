package ru.instamart.tests.orders;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.application.AppManager;
import ru.instamart.application.Elements;
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
        kraken.rest().fillCart(AppManager.session.admin, RestAddresses.Moscow.learningCenter());

        JuridicalData company = generate.juridical();
        kraken.reach().checkout();
        kraken.checkout().complete(PaymentTypes.bankTransfer(), true, company);

        Assert.assertTrue(kraken.detect().isOrderPlaced(),
                failMessage("Не удалось оформить заказ с добавлением нового юр. лица"));

        String number = kraken.grab().shipmentNumber();
        kraken.perform().cancelOrder();
        kraken.reach().admin(Pages.Admin.Order.requisites(number));

        Assert.assertEquals(kraken.grab().value(Elements.Administration.ShipmentsSection.Order.Requisites.innField()), company.getInn(),
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

        JuridicalData newCompany = generate.juridical();
        kraken.reach().checkout();
        kraken.checkout().complete(PaymentTypes.bankTransfer(), false, newCompany);

        Assert.assertTrue(kraken.detect().isOrderPlaced(),
                "Не удалось оформить заказ с с изменением юр. лица\n");

        String number = kraken.grab().shipmentNumber();
        kraken.perform().cancelOrder();
        kraken.reach().admin(Pages.Admin.Order.requisites(number));

        Assert.assertEquals(kraken.grab().value(Elements.Administration.ShipmentsSection.Order.Requisites.innField()), newCompany.getInn(),
                "Данные юр. лица не совпадают с указанными пользователем\n"
        );
    }

    @Test(
            description = "Тест заказа с новой картой оплаты",
            groups = {"sbermarket-regression"},
            priority = 2002
    )
    public void successCompleteCheckoutWithNewPaymentCard() {
        runTestOnlyOnServer("staging");
        kraken.rest().fillCart(AppManager.session.admin, RestAddresses.Moscow.defaultAddress());

        PaymentCardData creditCardData = Config.TestVariables.testOrderDetails().getPaymentDetails().getCreditCard();

        kraken.reach().checkout();
        kraken.checkout().complete(PaymentTypes.cardOnline(), true, creditCardData);

        Assert.assertTrue(kraken.detect().isOrderPlaced(),
                "Не удалось оформить заказ с новой картой оплаты\n");
    }

    @Test(
            description = "Тест заказа с любимыми товарами",
            groups = {"sbermarket-regression"},
            priority = 2003
    )
    public void successOrderWithFavProducts() {
        Shop.Catalog.Item.addToFavorites();
        kraken.get().userFavoritesPage();

        Shop.Cart.collect();
        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(kraken.detect().isOrderPlaced(),
                "Не оформляется заказ с любимыми товарами\n");
    }

    @AfterMethod(alwaysRun = true)
    public void cancelOrder() {
        kraken.perform().cancelLastActiveOrder();
    }
}
