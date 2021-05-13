package ru.instamart.tests.ui.orders;

import ru.instamart.api.common.RestAddresses;
import ru.instamart.core.testdata.UserManager;
import ru.instamart.ui.module.testdata.BonusPrograms;
import ru.instamart.ui.common.lib.Pages;
import ru.instamart.ui.module.Administration;
import ru.instamart.ui.module.User;
import ru.instamart.ui.module.checkout.BonusesActions;
import ru.instamart.ui.Elements;
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
        BonusesActions.deleteAll();
    }
    @AfterMethod(alwaysRun = true,
            description ="Очищаем окружение после теста")
    public void afterTest(ITestResult result){
        kraken.apiV2().cancelCurrentOrder();
    }

    @Test(  description = "Тест заказа с добавлением бонусов Много.ру",
            groups = {"sbermarket-acceptance", "sbermarket-regression"}
    )
    public void successOrderWithMnogoRuBonus() {
        BonusesActions.add(BonusPrograms.mnogoru());
        kraken.checkout().complete();

        String number = kraken.grab().shipmentNumber();
        kraken.reach().admin();
        User.Auth.withEmail(UserManager.getDefaultAdmin());
        Administration.Orders.searchOrder(number);

        Assert.assertTrue(
                kraken.detect().isElementPresent(Elements.Administration.ShipmentsSection.OrderDetailsPage.Details.loyaltyProgram()),
                    "В заказе не применилась бонусная программа Много.ру\n");
    }

    @Test(  description = "Тест заказа с добавлением бонусов Аерофлот Бонус",
            groups = {"sbermarket-acceptance", "sbermarket-regression"}

    )
    public void successOrderWithAeroflotBonus() {
        BonusesActions.add(BonusPrograms.aeroflot());
        kraken.checkout().complete();

        String number = kraken.grab().shipmentNumber();
        kraken.reach().admin(Pages.Admin.Order.details(number));

        Assert.assertTrue(
                kraken.detect().isElementPresent(Elements.Administration.ShipmentsSection.OrderDetailsPage.Details.loyaltyProgram()),
                    "В заказе не применилась бонусная программа Аерофлот Бонус\n");
    }

}
