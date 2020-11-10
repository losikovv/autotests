package ru.instamart.tests.checkout;

import instamart.api.common.RestAddresses;
import instamart.core.common.AppManager;
import instamart.ui.common.lib.Promos;
import instamart.ui.modules.Checkout;
import instamart.ui.modules.User;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.tests.TestBase;

public class CheckoutPromocodeTests extends TestBase {
    @BeforeClass(alwaysRun = true)
    public void prepareForCheckout() {
        kraken.get().baseUrl();
        User.Do.loginAs(AppManager.session.admin);
        kraken.apiV2().fillCart(AppManager.session.admin, RestAddresses.Moscow.defaultAddress());
    }

    @Test(
            description = "Тест успешного применения промокода в чекауте",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 1601
    )
    public void successAddPromocode(){
        kraken.reach().checkout();

        Checkout.Promocode.add(Promos.freeOrderDelivery());

        Assert.assertTrue(
                kraken.detect().isPromocodeApplied(),
                    "Не применяется промокод в чекауте\n");
    }

    @Test(
            description = "Тест удаления промокода в чекауте",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 1602
    )
    public void successDeletePromocode(){
        kraken.reach().checkout();
        if(!kraken.detect().isPromocodeApplied()) {
            Checkout.Promocode.add(Promos.freeOrderDelivery());
        }

        Checkout.Promocode.delete();

        Assert.assertFalse(
                kraken.detect().isPromocodeApplied(),
                    "Не удаляется промокод в чекауте\n");
    }

    @Test(
            description = "Тест недобавления промокода при нажатии кнопки Отмена",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 1603
    )
    public void noPromocodeAddedOnCancel(){
        kraken.reach().checkout();

        Checkout.Promocode.Modal.open();
        Checkout.Promocode.Modal.fill(Promos.freeOrderDelivery().getCode());
        Checkout.Promocode.Modal.cancel();

        Assert.assertFalse(
                kraken.detect().isPromocodeApplied(),
                    "При отмене добавления промокода, промокод все равно применяется\n");
    }

    @Test(
            description = "Тест недобавления промокода при закрытии модалки промокода",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 1604
    )
    public void noPromocodeAddedOnModalClose(){
        kraken.reach().checkout();

        Checkout.Promocode.Modal.open();
        Checkout.Promocode.Modal.fill(Promos.freeOrderDelivery().getCode());
        Checkout.Promocode.Modal.close();

        Assert.assertFalse(
                kraken.detect().isPromocodeApplied(),
                    "При закрытии модалки добавления промокода, промокод все равно применяется\n");
    }
}
