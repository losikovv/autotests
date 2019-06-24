package ru.instamart.autotests.tests;

// Тесты заказов со всеми бонусными программами ритейлеров

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.Addresses;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.LoyaltyPrograms;
import ru.instamart.autotests.application.Pages;

public class Order_RetailerBonuses extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() throws Exception {
        kraken.get().baseUrl();
        kraken.perform().loginAs(kraken.session.admin);
        kraken.shipAddress().change(Addresses.Moscow.testAddress());
        kraken.drop().cart();
    }

    @Test(
            description = "Тест вкус вилл",
            groups = {"acceptance", "regression"},
            priority = 1231256
    )

    public void successOrderWithVkus() throws Exception {
        kraken.get().page("vkusvill");
        kraken.shopping().collectItems();
        kraken.shopping().proceedToCheckout();

        
        kraken.checkout().addLoyalty(LoyaltyPrograms.vkusvill());

        kraken.checkout().complete();

        String number = kraken.grab().currentOrderNumber();
        kraken.reach().admin(Pages.Admin.Order.details(number));


        Assert.assertTrue(kraken.detect().isElementPresent(Elements.Admin.Shipments.Order.Details.loyaltyProgram()),
                "Бонусная программа не применилась");
    }

    // TODO Заказ c бонусной картой Вкусвилл + проверка в админке что в заказе есть бонусная программа

    // TODO Заказ c бонусной картой Метро + проверка в админке что в заказе есть бонусная программа

}
