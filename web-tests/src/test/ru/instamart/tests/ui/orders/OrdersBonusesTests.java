package ru.instamart.tests.ui.orders;

import instamart.api.common.RestAddresses;
import instamart.core.testdata.UserManager;
import instamart.core.testdata.ui.BonusPrograms;
import instamart.ui.common.lib.Pages;
import instamart.ui.modules.Administration;
import instamart.ui.modules.Checkout;
import instamart.ui.modules.User;
import instamart.ui.objectsmap.Elements;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.tests.ui.TestBase;

public class OrdersBonusesTests extends TestBase {
    @BeforeClass(alwaysRun = true)
    public void setup() {
        User.Do.loginAs(UserManager.getDefaultAdmin());
    }

    @BeforeMethod(alwaysRun = true,
            description ="Проверяем залогинен ли пользователь, если да то завершаем сессию")
    public void preconditions() {
        kraken.apiV2().fillCart(UserManager.getDefaultAdmin(), RestAddresses.Moscow.defaultAddress());
        kraken.reach().checkout();
        Checkout.Bonuses.deleteAll();
    }
    @AfterMethod(alwaysRun = true,
            description ="Очищаем окружение после теста")
    public void afterTest(ITestResult result){
        kraken.apiV2().cancelCurrentOrder();
    }

    @Test(  description = "Тест заказа с добавлением бонусов Много.ру",
            groups = {"sbermarket-acceptance", "sbermarket-regression"},
            priority = 2501
    )
    public void successOrderWithMnogoRuBonus() {
        Checkout.Bonuses.add(BonusPrograms.mnogoru());
        kraken.checkout().complete();

        String number = kraken.grab().shipmentNumber();
        kraken.reach().admin();
        Administration.Orders.searchOrder(number);

        Assert.assertTrue(
                kraken.detect().isElementPresent(Elements.Administration.ShipmentsSection.OrderDetailsPage.Details.loyaltyProgram()),
                    "В заказе не применилась бонусная программа Много.ру\n");
    }

    @Test(  description = "Тест заказа с добавлением бонусов Аерофлот Бонус",
            groups = {"sbermarket-acceptance", "sbermarket-regression"},
            priority = 2502

    )
    public void successOrderWithAeroflotBonus() {
        Checkout.Bonuses.add(BonusPrograms.aeroflot());
        kraken.checkout().complete();

        String number = kraken.grab().shipmentNumber();
        kraken.reach().admin(Pages.Admin.Order.details(number));

        Assert.assertTrue(
                kraken.detect().isElementPresent(Elements.Administration.ShipmentsSection.OrderDetailsPage.Details.loyaltyProgram()),
                    "В заказе не применилась бонусная программа Аерофлот Бонус\n");
    }

}
