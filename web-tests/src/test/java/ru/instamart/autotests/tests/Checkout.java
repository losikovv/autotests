package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.BonusPrograms;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.PaymentTypes;
import ru.instamart.autotests.models.BonusProgramData;
import ru.instamart.autotests.models.OrderDetailsData;


// Тесты чекаута


public class Checkout extends TestBase {

    @BeforeMethod(alwaysRun = true)
    public void preparingForCheckout() throws Exception {
        kraken.get().page("metro");
        kraken.perform().loginAs("admin");
    }

    // TODO Тесты на изменение телефона и контактов

    // TODO Тесты на добавление и изменение карт оплаты

    // TODO Тесты на добавление и изменение юрлиц


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
    public void successCompleteCheckoutAndPayWithCash(){
        kraken.perform().reachCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не удалось оформить заказ с оплатой наличными\n");

        kraken.perform().checkOrderDocuments();
        assertPageIsAvailable();

        kraken.perform().cancelLastOrder();
    }


    @Test(
            description = "Тест оформления заказа с оплатой картой онлайн",
            groups = {"regression"},
            priority = 709
    )
    public void successCompleteCheckoutAndPayWithCardOnline(){
        kraken.perform().reachCheckout();

        // TODO упростить до kraken.checkout().makeOrder().setPaymentDetails(PaymentTypes.cardOnline())
        OrderDetailsData details = kraken.generate().testOrderDetails();
        details.setPaymentDetails(PaymentTypes.cardOnline());

        kraken.checkout().makeOrder(details);

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не удалось оформить заказ с оплатой " + details.getPaymentDetails().getPaymentType().getDescription() + "\n");

        kraken.perform().checkOrderDocuments();
        assertPageIsAvailable();

        kraken.perform().cancelLastOrder();
    }


    @Test(
            description = "Тест оформления заказа с оплатой картой курьеру",
            groups = {"regression"},
            priority = 710
    )
    public void successCompleteCheckoutAndPayWithCardCourier(){
        kraken.perform().reachCheckout();
        kraken.checkout().complete("card-courier");

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не удалось оформить заказ с оплатой картой курьеру\n");

        kraken.perform().checkOrderDocuments();
        assertPageIsAvailable();

        kraken.perform().cancelLastOrder();
    }


    @Test(
            description = "Тест оформления заказа с оплатой банковским переводом",
            groups = {"regression"},
            priority = 711
    )
    public void successCompleteCheckoutAndPayWithBank(){
        kraken.perform().reachCheckout();
        kraken.checkout().complete("bank");

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не удалось оформить заказ с оплатой банковским переводом\n");

        kraken.perform().checkOrderDocuments();
        assertPageIsAvailable();

        kraken.perform().cancelLastOrder();
    }
}
