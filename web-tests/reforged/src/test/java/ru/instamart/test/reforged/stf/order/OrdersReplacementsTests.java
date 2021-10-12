package ru.instamart.test.reforged.stf.order;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.testdata.Generate;
import ru.instamart.kraken.testdata.UserData;
import ru.instamart.kraken.testdata.UserManager;
import ru.instamart.kraken.testdata.lib.Promos;
import ru.instamart.kraken.testdata.lib.ReplacementPolicies;
import ru.instamart.reforged.CookieFactory;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.stf.page.StfRouter.*;
import static ru.instamart.reforged.stf.page.StfRouter.userShipments;

@Epic("STF UI")
@Feature("Покупка товара")
public class OrdersReplacementsTests extends BaseTest {

    private final ApiHelper helper = new ApiHelper();
    private UserData ordersUser;

    @AfterMethod(alwaysRun = true, description = "Отмена ордера")
    public void afterTest() {
        helper.cancelAllActiveOrders(ordersUser);
    }

    @CaseId(1624)
    @Story("Тест заказа с оплатой картой онлайн")
    @Test(description = "Тест заказа с политикой Звонить / Заменять",
            groups = {
                    "lenta-acceptance", "lenta-regression",
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance", "sbermarket-regression"
            }
    )
    public void successOrderWithCallAndReplacePolicy() {
        var company = UserManager.juridical();

        ordersUser = UserManager.getUser();
        helper.dropAndFillCart(ordersUser, 1);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(ordersUser);
        shop().interactHeader().checkProfileButtonVisible();
        shop().addCookie(CookieFactory.COOKIE_ALERT);

        checkout().goToPage();
        checkout().setDeliveryOptions().clickToForBusiness();
        checkout().setDeliveryOptions().clickToAddCompany();

        checkout().interactAddCompanyModal().fillInn(company.getInn());
        checkout().interactAddCompanyModal().clickToSubmit();
        checkout().interactAddCompanyModal().fillName(company.getJuridicalName());
        checkout().interactAddCompanyModal().clickToSubmit();
        checkout().interactAddCompanyModal().clickToOkButton();

        checkout().setDeliveryOptions().fillApartment(company.getJuridicalAddress());
        checkout().setDeliveryOptions().clickToSubmitForDelivery();

        checkout().setContacts().fillFirstName(Generate.literalString(8));
        checkout().setContacts().fillLastName(Generate.literalString(8));
        checkout().setContacts().fillEmail(Generate.email());
        checkout().setContacts().clickToSubmit();

        checkout().setReplacementPolicy().clickToPolicy("Позвонить мне. Подобрать замену, если не смогу ответить");
        checkout().setReplacementPolicy().clickToSubmit();

        checkout().setSlot().setFirstActiveSlot();

        checkout().setPayment().clickToByCardToCourier();

        checkout().setPayment().clickToSubmitFromCheckoutColumn();

        userShipments().checkPageContains(userShipments().pageUrl());

        userShipments().checkStatusShipmentReady();
        userShipments().checkPaymentMethodCardToCourier();
    }
//@CaseId(1624)
//    @Story("Тест заказа с оплатой картой онлайн")
//    @Test(description = "Тест заказа с политикой Звонить / Убирать",
//            groups = {
//                    "lenta-acceptance", "lenta-regression",
//                    "metro-acceptance", "metro-regression",
//                    "sbermarket-acceptance", "sbermarket-regression"
//            }
//    )
//    public void successOrderWithCallAndRemovePolicy() {
//        kraken.checkout().complete(ReplacementPolicies.callAndRemove());
//
//        Assert.assertTrue(
//                kraken.detect().isOrderPlaced(),
//                failMessage("Не удалось оформить заказ с политикой \"Звонить / Убирать\""));
//
//        Assert.assertEquals(
//                kraken.grab().shipmentReplacementPolicy(),
//                ReplacementPolicies.callAndRemove().getUserDescription(),
//                failMessage("Текст инструкции по сборке не совпадает с выбранной политикой замен"));
//    }
////
//    @CaseId(1624)
//    @Story("Тест заказа с оплатой картой онлайн")
//    @Test(description = "Тест заказа с политикой Не звонить / Заменять",
//            groups = {
//                    "lenta-acceptance", "lenta-regression",
//                    "metro-acceptance", "metro-regression",
//                    "sbermarket-acceptance", "sbermarket-regression"
//            }
//    )
//    public void successOrderWithReplacePolicy() {
//        kraken.checkout().complete(ReplacementPolicies.replace());
//
//        Assert.assertTrue(
//                kraken.detect().isOrderPlaced(),
//                failMessage("Не удалось оформить заказ с политикой \"Не звонить / Заменять\""));
//
//        Assert.assertEquals(
//                kraken.grab().shipmentReplacementPolicy(),
//                ReplacementPolicies.replace().getUserDescription(),
//                failMessage("Текст инструкции по сборке не совпадает с выбранной политикой замен"));
//    }
//@CaseId(1624)
//    @Story("Тест заказа с оплатой картой онлайн")
//    @Test(description = "Тест заказа с политикой Не звонить / Убирать",
//            groups = {
//                    "lenta-acceptance", "lenta-regression",
//                    "metro-acceptance", "metro-regression",
//                    "sbermarket-acceptance", "sbermarket-regression"
//            }
//    )
//    public void successOrderWithRemovePolicy() {
//        kraken.checkout().complete(ReplacementPolicies.remove());
//
//        Assert.assertTrue(
//                kraken.detect().isOrderPlaced(),
//                failMessage("Не удалось оформить заказ с политикой \"Не звонить / Убирать\""));
//
//        Assert.assertEquals(
//                kraken.grab().shipmentReplacementPolicy(),
//                ReplacementPolicies.remove().getUserDescription(),
//                failMessage("Текст инструкции по сборке не совпадает с выбранной политикой замен"));
//    }
//
}
