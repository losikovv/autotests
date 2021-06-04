package ru.instamart.test.ui.checkout;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import ru.instamart.kraken.setting.Config;
import ru.instamart.kraken.testdata.Generate;
import ru.instamart.kraken.testdata.lib.Addresses;
import ru.instamart.kraken.testdata.lib.Promos;
import ru.instamart.ui.checkpoint.promocode.PromoCodesCheckpoints;
import ru.instamart.ui.module.Shop;
import ru.instamart.ui.module.User;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.test.ui.TestBase;
import ru.instamart.ui.module.checkout.PromocodeActions;
import ru.instamart.ui.module.shop.ShippingAddressModal;

@Epic("STF UI")
@Feature("Промокоды")
public class CheckoutPromocodeTests extends TestBase {
    private static String phone;
    PromoCodesCheckpoints promoChecks = new PromoCodesCheckpoints();
    @BeforeClass(alwaysRun = true)
    public void prepareForCheckout() {
        phone = Generate.phoneNumber();
        kraken.get().page(Config.DEFAULT_RETAILER);
        Shop.AuthModal.openAuthRetailer();
        User.Do.registration(phone,true);
        User.Do.sendSms(Config.DEFAULT_SMS);
        ShippingAddressModal.open();
        ShippingAddressModal.fill(Addresses.Moscow.defaultAddress());
        ShippingAddressModal.selectAddressSuggest();
        ShippingAddressModal.submit();
//        kraken.apiV2().fillCart(UserManager.getDefaultAdmin(), RestAddresses.Moscow.defaultAddress());
        Shop.Cart.collectFirstTime();
        Shop.Cart.proceedToCheckout();
    }

    @CaseId(1729)
    @Story("Добавление промокода к заказу")
    @Test(
            description = "Тест успешного применения промокода в чекауте",
            groups = {"sbermarket-acceptance","sbermarket-regression","sbermarket-Ui-smoke"}
    )
    public void successAddPromocode(){
        kraken.reach().checkout();
        PromocodeActions.add(Promos.freeOrderDelivery());
        promoChecks.checkIsPromoCodeApplied();
    }

    @CaseId(1208)
    @Story("Удаление промокода из заказа")
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
        promoChecks.checkIsPromoCodeNotApplied();
    }

    @CaseId(1729)
    @Story("Добавление промокода к заказу")
    @Test(
            description = "Тест недобавления промокода при нажатии кнопки Отмена",
            groups = {"sbermarket-acceptance","sbermarket-regression","sbermarket-Ui-smoke"}
    )
    public void noPromocodeAddedOnCancel(){
        kraken.reach().checkout();

        if(kraken.detect().isPromocodeApplied()) {
            PromocodeActions.delete();
        }

        PromocodeActions.Modal.open();
        PromocodeActions.Modal.fill(Promos.freeOrderDelivery().getCode());
        PromocodeActions.Modal.cancel();
        promoChecks.checkIsPromoCodeNotApplied();
    }

    @CaseId(1730)
    @Story("Добавление промокода к заказу")
    @Test(
            description = "Тест недобавления промокода при закрытии модалки промокода",
            groups = {"sbermarket-acceptance","sbermarket-regression","sbermarket-Ui-smoke"}
    )
    public void noPromocodeAddedOnModalClose(){
        kraken.reach().checkout();
        if(kraken.detect().isPromocodeApplied()) {
            PromocodeActions.delete();
        }
        PromocodeActions.Modal.open();
        PromocodeActions.Modal.fill(Promos.freeOrderDelivery().getCode());
        PromocodeActions.Modal.close();
        promoChecks.checkIsPromoCodeNotApplied();
    }
}
