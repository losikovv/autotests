package ru.instamart.test.ui.checkout;

import ru.instamart.api.common.RestAddresses;
import ru.instamart.kraken.setting.Config;
import ru.instamart.kraken.testdata.Generate;
import ru.instamart.kraken.testdata.UserManager;
import ru.instamart.kraken.testdata.lib.Promos;
import ru.instamart.ui.module.Shop;
import ru.instamart.ui.module.User;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.test.ui.TestBase;
import ru.instamart.ui.module.checkout.PromocodeActions;

public class CheckoutPromocodeTests extends TestBase {
    private static String phone;
    @BeforeClass(alwaysRun = true)
    public void prepareForCheckout() {
        phone = Generate.phoneNumber();
        kraken.get().page(Config.DEFAULT_RETAILER);
        Shop.AuthModal.openAuthRetailer();
        User.Do.registration(phone,true);
        User.Do.sendSms(Config.DEFAULT_SMS);
        kraken.apiV2().fillCart(UserManager.getDefaultAdmin(), RestAddresses.Moscow.defaultAddress());
    }

    @Test(
            description = "Тест успешного применения промокода в чекауте",
            groups = {"sbermarket-acceptance","sbermarket-regression","sbermarket-Ui-smoke"}
    )
    public void successAddPromocode(){
        kraken.reach().checkout();

        PromocodeActions.add(Promos.freeOrderDelivery());

        Assert.assertTrue(
                kraken.detect().isPromocodeApplied(),
                    "Не применяется промокод в чекауте\n");
    }

    @Test(
            description = "Тест удаления промокода в чекауте",
            groups = {"sbermarket-acceptance","sbermarket-regression","sbermarket-Ui-smoke"}
    )
    public void successDeletePromocode(){
        kraken.reach().checkout();
        if(!kraken.detect().isPromocodeApplied()) {
            PromocodeActions.add(Promos.freeOrderDelivery());
        }

        PromocodeActions.delete();

        Assert.assertFalse(
                kraken.detect().isPromocodeApplied(),
                    "Не удаляется промокод в чекауте\n");
    }

    @Test(
            description = "Тест недобавления промокода при нажатии кнопки Отмена",
            groups = {"sbermarket-acceptance","sbermarket-regression","sbermarket-Ui-smoke"}
    )
    public void noPromocodeAddedOnCancel(){
        kraken.reach().checkout();

        PromocodeActions.Modal.open();
        PromocodeActions.Modal.fill(Promos.freeOrderDelivery().getCode());
        PromocodeActions.Modal.cancel();

        Assert.assertFalse(
                kraken.detect().isPromocodeApplied(),
                    "При отмене добавления промокода, промокод все равно применяется\n");
    }

    @Test(
            description = "Тест недобавления промокода при закрытии модалки промокода",
            groups = {"sbermarket-acceptance","sbermarket-regression","sbermarket-Ui-smoke"}
    )
    public void noPromocodeAddedOnModalClose(){
        kraken.reach().checkout();

        PromocodeActions.Modal.open();
        PromocodeActions.Modal.fill(Promos.freeOrderDelivery().getCode());
        PromocodeActions.Modal.close();

        Assert.assertFalse(
                kraken.detect().isPromocodeApplied(),
                    "При закрытии модалки добавления промокода, промокод все равно применяется\n");
    }
}
