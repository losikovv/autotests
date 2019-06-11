package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.models.UserData;
import ru.instamart.autotests.testdata.generate;

import static ru.instamart.autotests.application.Config.enableAdministrationTests;
import static ru.instamart.autotests.application.Config.testOrder;
import static ru.instamart.autotests.application.Config.testShipment;

public class AdministrationShipments extends TestBase {

    @BeforeMethod(alwaysRun = true)
    public void reachAdministrationPanel() {
        kraken.reach().admin();
    }

    @Test(  enabled = enableAdministrationTests,
            description = "Тест поиска заказа по номеру заказа в админке",
            groups = {"acceptance","regression"},
            priority = 2101
    )
    public void successSearchOrderByOrderNumber() throws Exception {
        kraken.admin().searchOrder(testOrder);

        Assert.assertFalse(kraken.detect().isElementPresent(Elements.Admin.Shipments.placeholder()),
                "Не работает поиск заказа в админке, пустой результат поиска\n");

        Assert.assertEquals(kraken.grab().text(Elements.Admin.Shipments.firstOrderNumberInTable()), testOrder,
                "Не работает поиск заказа в админке, найден не тот шипмент\n");
    }

    @Test(  enabled = enableAdministrationTests,
            description = "Тест поиска заказа по номеру шипмента в админке",
            groups = {"acceptance","regression"},
            priority = 2102
    )
    public void successSearchOrderByShipmentNumber() throws Exception {
        kraken.admin().searchOrder(testShipment);

        Assert.assertFalse(kraken.detect().isElementPresent(Elements.Admin.Shipments.placeholder()),
                "Не работает поиск шипмента в админке, пустой результат поиска\n");

        Assert.assertEquals(kraken.grab().text(Elements.Admin.Shipments.firstShipmentNumberInTable()), testShipment,
                "Не работает поиск шипмента в админке, найден не тот шипмент\n");
    }

    // TODO тест можно ускорить - использовать тестовый заказ из конфига
    @Test(  enabled = enableAdministrationTests,
            description = "Тест возобновления и отмены заказа через админку",
            groups = {"acceptance","regression"},
            priority = 2103
    )
    public void successResumeAndCancelOrder() {
        SoftAssert softAssert = new SoftAssert();

        kraken.get().ordersPage();
        if(kraken.detect().isElementPresent(Elements.UserProfile.OrdersPage.placeholder())) {
            kraken.get().page("metro");
            kraken.perform().order();
        } else {
            if(kraken.detect().isLastOrderActive()) {
                kraken.perform().click(Elements.UserProfile.OrdersPage.lastOrderActionButton(1));
            }
            kraken.perform().click(Elements.UserProfile.OrdersPage.lastOrderDetailsButton());
        }
        kraken.get().adminOrderDetailsPage(kraken.grab().currentOrderNumber());
        softAssert.assertTrue(kraken.detect().isOrderCanceled(),
                "\nНе выполнились предусловия - заказ уже активен");

        kraken.admin().resumeOrder();
        softAssert.assertFalse(kraken.detect().isOrderCanceled(),
                "\nНе возобновляется заказ через админку");

        kraken.admin().cancelOrder();
        softAssert.assertTrue(kraken.detect().isOrderCanceled(),
                "\nНе отменяется заказ через админку");

        softAssert.assertAll();
    }

    @Test(  enabled = enableAdministrationTests,
            description = "Тест поиска B2B заказа в админке",
            groups = {"regression"},
            priority = 2104
    )
    public void successSearchB2BOrder() throws Exception {
        kraken.perform().quickLogout();
        UserData testuser = generate.testCredentials("user");
        kraken.perform().registration(testuser);

        kraken.admin().editUser(testuser);
        kraken.admin().grantB2B();
        kraken.perform().quickLogout();

        kraken.perform().authorisation(testuser);
        kraken.perform().order();
        String number = kraken.grab().currentOrderNumber();

        kraken.admin().searchOrder(number,true);

        Assert.assertEquals(kraken.grab().text(Elements.Admin.Shipments.firstOrderNumberInTable()), number,
                "Не работает поиск B2B заказа в админке");

        kraken.admin().cancelOrder(number);
    }
}
