package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Promo;

public class CheckoutPromocode extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void prepareForCheckout() {
        kraken.get().baseUrl();
        kraken.perform().loginAs(kraken.session.admin);
    }

    @Test(
            description = "Тест успешного применения промокода в чекауте",
            groups = {"acceptance","regression"},
            priority = 761
    )
    public void successAddPromocode(){
        kraken.reach().checkout();

        kraken.checkout().addPromocode(Promo.freeOrderDelivery());

        Assert.assertTrue(
                kraken.detect().isPromocodeApplied(),
                "Не применяется промокод в чекауте\n");
    }

    @Test(
            description = "Тест удаления промокода в чекауте",
            groups = {"acceptance","regression"},
            priority = 762
    )
    public void successClearPromocode(){
        kraken.reach().checkout();
        kraken.checkout().clearPromocode();

        Assert.assertFalse(
                kraken.detect().isPromocodeApplied(),
                "Не удаляется промокод в чекауте\n");
    }

    @Test(
            description = "Тест недобавления промокода при нажатии кнопки Отмена",
            groups = {"regression"},
            priority = 763
    )
    public void noPromocodeAddedOnCancel(){
        kraken.reach().checkout();
        kraken.perform().click(Elements.Checkout.Promocode.addButton());
        kraken.perform().fillField(Elements.Checkout.Promocode.Modal.inputField(), "unicorn");
        kraken.perform().click(Elements.Checkout.Promocode.Modal.cancelButton());

        Assert.assertFalse(
                kraken.detect().isPromocodeApplied(),
                "При отмене добавления промокода, промокод все равно применяется\n");
    }

    @Test(
            description = "Тест недобавления промокода при закрытии модалки промокода",
            groups = {"regression"},
            priority = 764
    )
    public void noPromocodeAddedOnModalClose(){
        kraken.reach().checkout();
        kraken.perform().click(Elements.Checkout.Promocode.addButton());
        kraken.perform().fillField(Elements.Checkout.Promocode.Modal.inputField(), "unicorn");
        kraken.perform().click(Elements.Checkout.Promocode.Modal.closeButton());

        Assert.assertFalse(
                kraken.detect().isPromocodeApplied(),
                "При закрытии модалки добавления промокода, промокод все равно применяется\n");
    }
}
