package ru.instamart.tests.ui.orders;

import instamart.api.common.RestAddresses;
import instamart.core.testdata.TestVariables;
import instamart.core.testdata.UserManager;
import instamart.core.testdata.ui.Generate;
import instamart.core.testdata.ui.PaymentTypes;
import instamart.ui.common.lib.Pages;
import instamart.ui.common.pagesdata.JuridicalData;
import instamart.ui.common.pagesdata.PaymentCardData;
import instamart.ui.modules.Shop;
import instamart.ui.modules.User;
import instamart.ui.objectsmap.Elements;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.tests.ui.TestBase;

public class BasicOrdersTests extends TestBase {

    // TODO переделать в тесты заказа новым пользоватеем (генерим нового, делаем заказ с новым телом, с привязкой новой карты + повтор, заказ с новым юрлицом + повтор и все что тут есть)

    // TODO successOrderWithCash

    // TODO successOrderWithNewBankCard

    // TODO successOrderWithCardCourier

    // TODO successOrderWithNewJuridical

    // TODO successOrderWithBonus

    // TODO successOrderWithPromocode

    // TODO successOrderWithDocumentsNeeded


    @BeforeMethod(alwaysRun = true,
            description ="Проверяем залогинен ли пользователь, если да то завершаем сессию")
    public void preconditions() {
        kraken.get().baseUrl();
        //User.Do.loginAs(AppManager.session.admin);
        String phone;
        phone = Generate.phoneNumber();
        User.Do.registration(
                "Test User",
                "test@example.com",
                "12345678",
                "12345678",
                phone,
                "1111"
        );
        kraken.apiV2().dropCart(UserManager.getDefaultAdmin(), RestAddresses.Moscow.defaultAddress());
    }

    @AfterMethod(alwaysRun = true,
            description ="Очистка окружения после теста")
    public void afterTest(ITestResult result){
        kraken.perform().cancelLastActiveOrder();
    }
    // TODO Тесты на изменение телефона и контактов

    // TODO Тесты на добавление и изменение карт оплаты


    @Test(
            description = "Тест заказа с добавлением нового юр. лица",
            groups = {"sbermarket-regression",},
            priority = 2000
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
            groups = {"sbermarket-regression",},
            priority = 2001
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

    @Test(
            description = "Тест заказа с новой картой оплаты",
            groups = {"sbermarket-regression",},
            priority = 2002
    )
    public void successCompleteCheckoutWithNewPaymentCard() {
        runTestOnlyOnServer("staging");
        kraken.apiV2().fillCart(UserManager.getDefaultAdmin(), RestAddresses.Moscow.defaultAddress());

        PaymentCardData creditCardData = TestVariables.testOrderDetails().getPaymentDetails().getCreditCard();

        kraken.reach().checkout();
        kraken.checkout().complete(PaymentTypes.cardOnline(), true, creditCardData);

        Assert.assertTrue(kraken.detect().isOrderPlaced(),
                "Не удалось оформить заказ с новой картой оплаты\n");
    }

    @Test(
            description = "Тест заказа с любимыми товарами",
            groups = {"sbermarket-regression","testing"},
            priority = 2003
    )
    public void successOrderWithFavProducts() {
        Shop.Catalog.Item.addToFavorites();
        kraken.get().userFavoritesPage();
        Shop.Cart.collectFirstTime();
        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(kraken.detect().isOrderPlaced(),
                "Не оформляется заказ с любимыми товарами\n");
    }

}
