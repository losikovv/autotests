package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.Promo;
import ru.instamart.autotests.appmanager.CheckoutHelper;

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

        CheckoutHelper.Promocode.add(Promo.freeOrderDelivery());

        Assert.assertTrue(
                kraken.detect().isPromocodeApplied(),
                    "Не применяется промокод в чекауте\n");
    }

    @Test(
            description = "Тест удаления промокода в чекауте",
            groups = {"acceptance","regression"},
            priority = 762
    )
    public void successDeletePromocode(){
        kraken.reach().checkout();
        if(!kraken.detect().isPromocodeApplied()) {
            CheckoutHelper.Promocode.add(Promo.freeOrderDelivery());
        }

        CheckoutHelper.Promocode.delete();

        Assert.assertFalse(
                kraken.detect().isPromocodeApplied(),
                    "Не удаляется промокод в чекауте\n");
    }

    @Test(
            description = "Тест недобавления промокода при нажатии кнопки Отмена",
            groups = {"acceptance","regression"},
            priority = 763
    )
    public void noPromocodeAddedOnCancel(){
        kraken.reach().checkout();

        CheckoutHelper.Promocode.Modal.open();
        CheckoutHelper.Promocode.Modal.fill(Promo.freeOrderDelivery().getCode());
        CheckoutHelper.Promocode.Modal.cancel();

        Assert.assertFalse(
                kraken.detect().isPromocodeApplied(),
                    "При отмене добавления промокода, промокод все равно применяется\n");
    }

    @Test(
            description = "Тест недобавления промокода при закрытии модалки промокода",
            groups = {"acceptance","regression"},
            priority = 764
    )
    public void noPromocodeAddedOnModalClose(){
        kraken.reach().checkout();

        CheckoutHelper.Promocode.Modal.open();
        CheckoutHelper.Promocode.Modal.fill(Promo.freeOrderDelivery().getCode());
        CheckoutHelper.Promocode.Modal.close();

        Assert.assertFalse(
                kraken.detect().isPromocodeApplied(),
                    "При закрытии модалки добавления промокода, промокод все равно применяется\n");
    }
}
