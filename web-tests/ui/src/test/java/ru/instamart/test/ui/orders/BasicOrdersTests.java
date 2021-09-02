package ru.instamart.test.ui.orders;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.kraken.listener.Skip;
import ru.instamart.kraken.setting.Config;
import ru.instamart.kraken.testdata.*;
import ru.instamart.kraken.testdata.lib.Addresses;
import ru.instamart.kraken.testdata.lib.Pages;
import ru.instamart.kraken.testdata.pagesdata.PaymentCardData;
import ru.instamart.kraken.testdata.pagesdata.PaymentTypeData;
import ru.instamart.test.ui.TestBase;
import ru.instamart.ui.Elements;
import ru.instamart.ui.checkpoint.order.OrdersCheckpoints;
import ru.instamart.ui.manager.AppManager;
import ru.instamart.ui.module.Shop;
import ru.instamart.ui.module.User;
import ru.instamart.ui.module.shop.Order;
import ru.instamart.ui.module.shop.ShippingAddressModal;

import static io.qameta.allure.Allure.step;

@Epic("STF UI")
@Feature("Покупка товара")
public final class BasicOrdersTests extends TestBase {

    // TODO переделать в тесты заказа новым пользоватеем (генерим нового, делаем заказ с новым телом, с привязкой новой карты + повтор, заказ с новым юрлицом + повтор и все что тут есть)

    // TODO successOrderWithCash

    // TODO successOrderWithNewJuridical

    // TODO successOrderWithBonus

    // TODO successOrderWithPromocode

    // TODO successOrderWithDocumentsNeeded
    private final OrdersCheckpoints orderCheck = new OrdersCheckpoints();


    @BeforeMethod(alwaysRun = true,
            description ="Аутентификация и выбор адреса доставки")
    public void preconditions() {
        step("Аутентификация", ()-> {
            kraken.get().baseUrl();
            Shop.AuthModal.open();
            User.Do.registration(Generate.phoneNumber(), false);
            User.Do.sendSms(Config.DEFAULT_SMS);
        });
        step("Выбор адреса доставки", ()-> {
            ShippingAddressModal.open();
            ShippingAddressModal.fill(Addresses.Moscow.defaultAddress());
            ShippingAddressModal.selectAddressSuggest();
            ShippingAddressModal.submit();
        });
    }

    @AfterMethod(alwaysRun = true,
            description ="Завершение теста", dependsOnMethods = "captureFinish")
    public void afterTest(final ITestResult result) {
        step("Очистка окружения после теста ", ()->{
            Order.cancelLastActiveOrder();
            User.Logout.quickly();
        });
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
        Order.cancelOrder();
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
        Order.cancelOrder();
        kraken.reach().admin(Pages.Admin.Order.requisites(number));

        Assert.assertEquals(kraken.grab().value(Elements.Administration.ShipmentsSection.OrderDetailsPage.Requisites.innField()), newCompany.getInn(),
                "Данные юр. лица не совпадают с указанными пользователем\n"
        );
    }

    @Skip
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
        kraken.checkout().completeWithCreditCard(paymentMethod, true, creditCardData, false);
        orderCheck.checkOrderSuccessCreation();
        orderCheck.checkPaymentMethod(paymentMethod);
    }

    @Skip
    @CaseId(2066)
    @Test(
            description = "Тест заказа с новой картой оплаты без 3ds",
            groups = {"sbermarket-regression", "testing", "sbermarket-Ui-smoke"}
    )
    public void successCompleteCheckoutWithNewNoSecurePaymentCard() {
        PaymentCardData creditCardData = TestVariables.testOrderDetailsWithout3ds().getPaymentDetails().getCreditCard();
        PaymentTypeData paymentMethod = PaymentTypes.cardOnline();

        Shop.Cart.collectFirstTime();
        Shop.Cart.proceedToCheckout();
        kraken.checkout().completeWithCreditCard(paymentMethod, true, creditCardData, false);
        orderCheck.checkOrderSuccessCreation();
        orderCheck.checkPaymentMethod(paymentMethod);
    }

    @CaseId(1681)
    @Test(
            description = "Тест заказа с любимыми товарами",
            groups = {"sbermarket-regression","testing", "sbermarket-Ui-smoke"}
            //enabled = false
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
            description = "Тест успешного заказа с оплатой картой курьеру",
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

    @Test(
            description = "Тест повторого заказа с оплатой картой с 3ds",
            groups = {"sbermarket-regression","testing"}
    )
    public void successReorderWith3dsCard() {
        PaymentCardData CardData = TestVariables.testOrderDetails().getPaymentDetails().getCreditCard();
        PaymentTypeData paymentMethod = PaymentTypes.cardOnline();

        step("Оформление первого заказа", ()-> {
            Shop.Cart.collectFirstTime();
            Shop.Cart.proceedToCheckout();
            kraken.checkout().completeWithCreditCard(paymentMethod, true,
                    CardData, false);
            Order.cancelLastActiveOrder();
        });

        kraken.get().baseUrl();

        step("Оформление второго заказа", ()->{
            Shop.Cart.collectFirstTime();
            Shop.Cart.proceedToCheckout();
            kraken.checkout().completeWithCreditCard(paymentMethod, true,
                    CardData, true);
        });
        orderCheck.checkOrderSuccessCreation();
        orderCheck.checkPaymentMethod(paymentMethod);
    }
}
