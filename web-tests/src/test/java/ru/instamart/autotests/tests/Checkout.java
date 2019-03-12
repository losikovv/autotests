package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.*;
import ru.instamart.autotests.models.BonusProgramData;
import ru.instamart.autotests.models.CreditCardData;
import ru.instamart.autotests.models.JuridicalData;
import ru.instamart.autotests.testdata.generate;

import static ru.instamart.autotests.application.Environments.*;

// Тесты чекаута


public class Checkout extends TestBase {

    @BeforeMethod(alwaysRun = true)
    public void preparingForCheckout() throws Exception {
        kraken.get().page("metro");
        kraken.perform().loginAs(Users.superadmin()); // TODO поменять на session.admin когда будут методы добавления новых банк.карт/юрлиц
    }

    // TODO Тесты на изменение телефона и контактов

    // TODO Тесты на добавление и изменение карт оплаты


    @Test(
            description = "Тест применения промокода в чекауте",
            groups = {"acceptance","regression"},
            priority = 701
    )
    public void successAddPromocode(){
        kraken.perform().reachCheckout();
        kraken.checkout().addPromocode("unicorn");

        Assert.assertTrue(kraken.detect().isPromocodeApplied(),
                "Не применяется промокод в чекауте\n");
    }


    @Test(
            description = "Тест удаления промокода в чекауте",
            groups = {"acceptance","regression"},
            priority = 702
    )
    public void successClearPromocode(){
        kraken.perform().reachCheckout();
        kraken.checkout().clearPromocode();

        Assert.assertFalse(kraken.detect().isPromocodeApplied(),
                "Не удаляется промокод в чекауте\n");
    }


    @Test(
            description = "Тест недобавления промокода при нажатии кнопки Отмена",
            groups = {"regression"},
            priority = 703
    )
    public void noPromocodeAddedOnCancel(){
        kraken.perform().reachCheckout();
        kraken.perform().click(Elements.Site.Checkout.addPromocodeButton());
        kraken.perform().fillField(Elements.Site.Checkout.PromocodeModal.field(), "unicorn");
        kraken.perform().click(Elements.Site.Checkout.PromocodeModal.cancelButton());

        Assert.assertFalse(kraken.detect().isPromocodeApplied(),
                "При отмене добавления промокода, промокод все равно применяется\n");
    }

    @Test(
            description = "Тест недобавления промокода при закрытии модалки промокода",
            groups = {"regression"},
            priority = 704
    )
    public void noPromocodeAddedOnClose(){
        kraken.perform().reachCheckout();
        kraken.perform().click(Elements.Site.Checkout.addPromocodeButton());
        kraken.perform().fillField(Elements.Site.Checkout.PromocodeModal.field(), "unicorn");
        kraken.perform().click(Elements.Site.Checkout.PromocodeModal.closeButton());

        Assert.assertFalse(kraken.detect().isPromocodeApplied(),
                "При закрытии модалки добавления промокода, промокод все равно применяется\n");
    }


    @Test(
            description = "Тест добавления программ лояльности в чекауте",
            groups = {"acceptance","regression"},
            priority = 705
    )
    public void successAddBonusPrograms(){
        BonusProgramData mnogoru = BonusPrograms.mnogoru();
        BonusProgramData aeroflot = BonusPrograms.aeroflot();
        kraken.perform().reachCheckout();

        kraken.checkout().addBonus(mnogoru);

        Assert.assertTrue(kraken.detect().isBonusAdded(mnogoru),
                "Не добавляется бонусная программа " + mnogoru.getName() + " в чекауте\n");

        kraken.checkout().addBonus(aeroflot);

        Assert.assertTrue(kraken.detect().isBonusAdded(aeroflot),
                "Не добавляется бонусная программа " + aeroflot.getName() + " в чекауте\n");
    }


    @Test(
            description = "Тест выбора программы лояльности в чекауте",
            groups = {"regression"},
            priority = 706
    )
    public void successSelectBonusPrograms(){
        kraken.perform().reachCheckout();
        kraken.checkout().selectBonus(BonusPrograms.mnogoru());
        kraken.checkout().selectBonus(BonusPrograms.aeroflot());
        // TODO добавить проверки на наличие модалок после выбора
    }


    @Test(
            description = "Тест удаления программ лояльности в чекауте",
            groups = {"acceptance","regression"},
            priority = 707
    )
    public void successClearBonusPrograms(){
        BonusProgramData mnogoru = BonusPrograms.mnogoru();
        BonusProgramData aeroflot = BonusPrograms.aeroflot();
        kraken.perform().reachCheckout();

        kraken.checkout().clearBonus(mnogoru);
        Assert.assertFalse(kraken.detect().isBonusAdded(mnogoru),
                "Не удаляется бонусная программа " + mnogoru.getName() + " в чекауте");

        kraken.checkout().clearBonus(aeroflot);
        Assert.assertFalse(kraken.detect().isBonusAdded(aeroflot),
                "Не удаляется бонусная программа " + aeroflot.getName() + " в чекауте");
    }


    @Test(
            description = "Тест оформления заказа с оплатой наличными",
            groups = {"acceptance","regression"},
            priority = 708
    )
    public void successCompleteCheckoutAndPayWithCash() throws Exception {
        kraken.perform().reachCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не удалось оформить заказ с оплатой наличными\n");

        String number = kraken.grab().currentOrderNumber();
        kraken.perform().checkOrderDocuments();
        assertPageIsAvailable();

        kraken.perform().cancelLastOrder();
        /*
        kraken.perform().reachAdmin(Pages.Admin.Order.payments(number));

        Assert.assertEquals(
                kraken.grab().text(Elements.Admin.Shipments.Order.Payments.paymentType()),
                PaymentTypes.cash().getDescription(),
                "Название способа оплаты в админке не совпадает с выбранным способом оплаты"
        );
        */
    }


    @Test(
            description = "Тест оформления заказа с оплатой картой онлайн",
            groups = {"regression"},
            priority = 709
    )
    public void successCompleteCheckoutAndPayWithCardOnline() throws Exception {
        kraken.perform().reachCheckout();

        kraken.checkout().complete(PaymentTypes.cardOnline());

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не удалось оформить заказ с оплатой онлайн\n");

        String number = kraken.grab().currentOrderNumber();
        kraken.perform().checkOrderDocuments();
        assertPageIsAvailable();

        kraken.perform().cancelLastOrder();
        // Доп проверка наименования платежа в админке
        /*
        kraken.perform().reachAdmin(Pages.Admin.Order.payments(number));

        Assert.assertEquals(
                kraken.grab().text(Elements.Admin.Shipments.Order.Payments.paymentType()),
                PaymentTypes.cardOnline().getDescription(),
                "Название способа оплаты в админке не совпадает с выбранным способом оплаты"
        );
        */
    }


    @Test(
            description = "Тест оформления заказа с оплатой картой курьеру",
            groups = {"regression"},
            priority = 710
    )
    public void successCompleteCheckoutAndPayWithCardCourier() throws Exception {
        kraken.perform().reachCheckout();
        kraken.checkout().complete(PaymentTypes.cardCourier());

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не удалось оформить заказ с оплатой картой курьеру\n");

        String number = kraken.grab().currentOrderNumber();
        kraken.perform().checkOrderDocuments();
        assertPageIsAvailable();

        kraken.perform().cancelLastOrder();
        // Доп проверка наименования платежа в админке
        /*
        kraken.perform().reachAdmin(Pages.Admin.Order.payments(number));

        Assert.assertEquals(
                kraken.grab().text(Elements.Admin.Shipments.Order.Payments.paymentType()),
                PaymentTypes.cardCourier().getDescription(),
                "Название способа оплаты в админке не совпадает с выбранным способом оплаты"
        );
        */
    }


    @Test(
            description = "Тест оформления заказа с оплатой банковским переводом",
            groups = {"regression"},
            priority = 711
    )
    public void successCompleteCheckoutAndPayWithBank() throws Exception {
        kraken.perform().reachCheckout();
        kraken.checkout().complete(PaymentTypes.bankTransfer());

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не удалось оформить заказ с оплатой банковским переводом\n");

        String number = kraken.grab().currentOrderNumber();
        kraken.perform().checkOrderDocuments();
        assertPageIsAvailable();

        kraken.perform().cancelLastOrder();
        // Доп проверка наименования платежа в админке
        /*
        kraken.perform().reachAdmin(Pages.Admin.Order.payments(number));

        Assert.assertEquals(
                kraken.grab().text(Elements.Admin.Shipments.Order.Payments.paymentType()),
                PaymentTypes.bankTransfer().getDescription(),
                "Название способа оплаты в админке не совпадает с выбранным способом оплаты"
        );
        */
    }


    @Test(
            description = "Тест оформления заказа с политикой Звонить / Заменять",
            groups = {"regression"},
            priority = 712
    )
    public void successCompleteCheckoutWithCallAndReplacePolicy() throws Exception {
        kraken.perform().reachCheckout();
        kraken.checkout().complete(ReplacementPolicies.callAndReplace());

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не удалось оформить заказ с политикой Звонить / Заменять\n");

        String number = kraken.grab().currentOrderNumber();
        kraken.perform().cancelLastOrder();
        kraken.perform().reachAdmin(Pages.Admin.Order.details(number));

        Assert.assertEquals(
                kraken.grab().text(Elements.Admin.Shipments.Order.Details.replacementPolicy()),
                ReplacementPolicies.callAndReplace().getInstruction(),
                "Текст инструкции по сборке не совпадает с выбранной политикой замен"
        );
    }


    @Test(
            description = "Тест оформления заказа с политикой Звонить / Убирать",
            groups = {"regression"},
            priority = 713
    )
    public void successCompleteCheckoutWithCallAndRemovePolicy() throws Exception {
        kraken.perform().reachCheckout();
        kraken.checkout().complete(ReplacementPolicies.callAndRemove());

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не удалось оформить заказ с политикой Звонить / Убирать\n");

        String number = kraken.grab().currentOrderNumber();
        kraken.perform().cancelLastOrder();
        kraken.perform().reachAdmin(Pages.Admin.Order.details(number));

        Assert.assertEquals(
                kraken.grab().text(Elements.Admin.Shipments.Order.Details.replacementPolicy()),
                ReplacementPolicies.callAndRemove().getInstruction(),
                "Текст инструкции по сборке не совпадает с выбранной политикой замен"
        );
    }


    @Test(
            description = "Тест оформления заказа с политикой Не звонить / Заменять",
            groups = {"regression"},
            priority = 714
    )
    public void successCompleteCheckoutWithReplacePolicy() throws Exception {
        kraken.perform().reachCheckout();
        kraken.checkout().complete(ReplacementPolicies.replace());

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не удалось оформить заказ с политикой Не звонить / Заменять\n");

        String number = kraken.grab().currentOrderNumber();
        kraken.perform().cancelLastOrder();
        kraken.perform().reachAdmin(Pages.Admin.Order.details(number));

        Assert.assertEquals(
                kraken.grab().text(Elements.Admin.Shipments.Order.Details.replacementPolicy()),
                ReplacementPolicies.replace().getInstruction(),
                "Текст инструкции по сборке не совпадает с выбранной политикой замен"
        );
    }


    @Test(
            description = "Тест оформления заказа с политикой Не звонить / Убирать",
            groups = {"regression"},
            priority = 715
    )
    public void successCompleteCheckoutWithRemovePolicy() throws Exception {
        kraken.perform().reachCheckout();
        kraken.checkout().complete(ReplacementPolicies.remove());

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не удалось оформить заказ с политикой Не звонить / Убирать\n");

        String number = kraken.grab().currentOrderNumber();
        kraken.perform().cancelLastOrder();
        kraken.perform().reachAdmin(Pages.Admin.Order.details(number));

        Assert.assertEquals(
                kraken.grab().text(Elements.Admin.Shipments.Order.Details.replacementPolicy()),
                ReplacementPolicies.remove().getInstruction(),
                "Текст инструкции по сборке не совпадает с выбранной политикой замен"
        );
    }


    @Test(
            description = "Тест оформления заказа с добавлением нового юр. лица",
            groups = {"acceptance","regression"},
            priority = 716
    )
    public void successCompleteCheckoutWithNewJuridical() throws Exception {
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
        kraken.perform().reachCheckout();
        kraken.checkout().complete(PaymentTypes.bankTransfer(), true, juridicalData);

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не удалось оформить заказ с добавлением нового юр. лица\n");

        String number = kraken.grab().currentOrderNumber();
        kraken.perform().cancelLastOrder();
        kraken.perform().reachAdmin(Pages.Admin.Order.requisites(number));

        Assert.assertEquals(kraken.grab().value(Elements.Admin.Shipments.Order.Requisites.innField()), juridicalData.getInn(),
                "Данные юр. лица не совпадают с указанными пользователем\n"
        );
    }


    @Test(
            description = "Тест оформления заказа с изменением юр. лица",
            groups = {"regression"},
            priority = 717
    )
    public void successCompleteCheckoutWithChangeJuridical() throws Exception {
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
        kraken.perform().reachCheckout();
        kraken.checkout().complete(PaymentTypes.bankTransfer(), false, juridicalData);

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не удалось оформить заказ с с изменением юр. лица\n");

        String number = kraken.grab().currentOrderNumber();
        kraken.perform().cancelLastOrder();
        kraken.perform().reachAdmin(Pages.Admin.Order.requisites(number));

        Assert.assertEquals(kraken.grab().value(Elements.Admin.Shipments.Order.Requisites.innField()), juridicalData.getInn(),
                "Данные юр. лица не совпадают с указанными пользователем\n"
        );
    }


    @Test(
            description = "Тест оформления заказа с новой картой оплаты",
            groups = {"regression"},
            priority = 718
    )
    public void successCompleteCheckoutWithNewPaymentCard() throws Exception {
        testOn(instamart_staging());
        CreditCardData creditCardData = Config.testOrderDetails().getPaymentDetails().getCreditCard();

        kraken.perform().reachCheckout();
        kraken.checkout().complete(PaymentTypes.cardOnline(), true, creditCardData);

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не удалось оформить заказ с добавлением новой карты\n");

        String number = kraken.grab().currentOrderNumber();
        kraken.perform().cancelLastOrder();
        // Доп проверка наименования платежа в админке
        /*
        kraken.perform().reachAdmin(Pages.Admin.Order.payments(number));

        Assert.assertEquals(kraken.grab().text(Elements.Admin.Shipments.Order.Payments.paymentType()),
                PaymentTypes.cardOnline().getDescription(),
                "Название способа оплаты в админке не совпадает с выбранным способом оплаты\n"
        );
        */
    }

}
