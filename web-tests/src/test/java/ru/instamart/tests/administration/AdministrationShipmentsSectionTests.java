package ru.instamart.tests.administration;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.application.Config;
import ru.instamart.application.Elements;
import ru.instamart.application.models.UserData;
import ru.instamart.application.platform.modules.Administration;
import ru.instamart.application.platform.modules.User;
import ru.instamart.testdata.generate;
import ru.instamart.tests.TestBase;

public class AdministrationShipmentsSectionTests extends TestBase {

    @BeforeMethod(alwaysRun = true)
    public void reachAdministrationPanel() {
        kraken.reach().admin();
    }

    @Test(  enabled = Config.TestsConfiguration.AdministrationTests.enableShipmentsSectionTests,
            description = "Тест валидации дефолтной странгицы списка заказаов в админке",
            groups = {"smoke","acceptance","regression"},
            priority = 10100
    )
    public void validateDefaultAdminShipmentsPage() {
        assertElementPresence(Elements.Administration.ShipmentsSection.title());

        assertElementPresence(Elements.Administration.ShipmentsSection.Filters.orderDateFrom());
        assertElementPresence(Elements.Administration.ShipmentsSection.Filters.orderDateTo());
        assertElementPresence(Elements.Administration.ShipmentsSection.Filters.customerName());
        assertElementPresence(Elements.Administration.ShipmentsSection.Filters.customerSurname());
        assertElementPresence(Elements.Administration.ShipmentsSection.Filters.juridicalNameContains());
        assertElementPresence(Elements.Administration.ShipmentsSection.Filters.shopperLogin());
        assertElementPresence(Elements.Administration.ShipmentsSection.Filters.driverLogin());

        assertElementPresence(Elements.Administration.ShipmentsSection.Filters.deliveryTimeFrom());
        assertElementPresence(Elements.Administration.ShipmentsSection.Filters.deliveryTimeTo());
        assertElementPresence(Elements.Administration.ShipmentsSection.Filters.phoneNumberContains());
        assertElementPresence(Elements.Administration.ShipmentsSection.Filters.email());
        assertElementPresence(Elements.Administration.ShipmentsSection.Filters.innNumber());
        assertElementPresence(Elements.Administration.ShipmentsSection.Filters.invoiceNumber());

        assertElementPresence(Elements.Administration.ShipmentsSection.Filters.orderNumber());
        assertElementPresence(Elements.Administration.ShipmentsSection.Filters.orderStatus());
        assertElementPresence(Elements.Administration.ShipmentsSection.Filters.retailer());
        assertElementPresence(Elements.Administration.ShipmentsSection.Filters.store());
        assertElementPresence(Elements.Administration.ShipmentsSection.Filters.paymentMethod());
        assertElementPresence(Elements.Administration.ShipmentsSection.Filters.paymentStatus());

        assertElementPresence(Elements.Administration.ShipmentsSection.Filters.promocode());
        assertElementPresence(Elements.Administration.ShipmentsSection.Filters.itemsFrom());
        assertElementPresence(Elements.Administration.ShipmentsSection.Filters.itemsTo());
        assertElementPresence(Elements.Administration.ShipmentsSection.Filters.weightFrom());
        assertElementPresence(Elements.Administration.ShipmentsSection.Filters.weightTo());

        assertElementPresence(Elements.Administration.ShipmentsSection.Checkboxes.completedOnly());
        assertElementPresence(Elements.Administration.ShipmentsSection.Checkboxes.b2bOnly());
        assertElementPresence(Elements.Administration.ShipmentsSection.Checkboxes.metroOnly());
        assertElementPresence(Elements.Administration.ShipmentsSection.Checkboxes.deliveryChangedOnly());

        assertElementPresence(Elements.Administration.ShipmentsSection.applyFilterButton());
        assertElementPresence(Elements.Administration.ShipmentsSection.clearFilterButton());

        assertElementPresence(Elements.Administration.ShipmentsSection.table());
        assertElementPresence(Elements.Administration.ShipmentsSection.tableTitle("Номер"));
        assertElementPresence(Elements.Administration.ShipmentsSection.tableTitle("Сумма"));
        assertElementPresence(Elements.Administration.ShipmentsSection.tableTitle("Состояние оплаты"));
        assertElementPresence(Elements.Administration.ShipmentsSection.tableTitle("Состояние доставки"));
        assertElementPresence(Elements.Administration.ShipmentsSection.tableTitle("Ритейлер"));
        assertElementPresence(Elements.Administration.ShipmentsSection.tableTitle("Дата и время доставки"));
        assertElementPresence(Elements.Administration.ShipmentsSection.tableTitle("Комментарий"));
        assertElementPresence(Elements.Administration.ShipmentsSection.tableTitle("Куда"));
        assertElementPresence(Elements.Administration.ShipmentsSection.tableTitle("Сборщик"));
        assertElementPresence(Elements.Administration.ShipmentsSection.tableTitle("Водитель"));
        assertElementPresence(Elements.Administration.ShipmentsSection.tableTitle("Общий вес"));
        assertElementPresence(Elements.Administration.ShipmentsSection.tableTitle("Документация"));
        assertElementPresence(Elements.Administration.ShipmentsSection.tableTitle("Магазин сборки"));

        //todo валидить элементы строки первого заказа
    }

    // TODO test shipmentsTableNotEmptyByDefault

    // TODO test successShowEmptySearchPlaceholder

    @Test(  enabled = Config.TestsConfiguration.AdministrationTests.enableShipmentsSectionTests,
            description = "Тест поиска заказа по номеру заказа в админке",
            groups = {"acceptance","regression"},
            priority = 10101
    )
    public void successSearchOrderByOrderNumber() {
        Administration.Orders.searchOrder(Config.TestVariables.TestParams.testOrder);

        Assert.assertFalse(
                kraken.detect().isElementPresent(Elements.Administration.ShipmentsSection.placeholder()),
                    "Не работает поиск заказа в админке, пустой результат поиска\n");

        Assert.assertEquals(
                kraken.grab().text(Elements.Administration.ShipmentsSection.firstOrderNumberInTable()), Config.TestVariables.TestParams.testOrder,
                    "Не работает поиск заказа в админке, найден не тот шипмент\n");
    }

    @Test(  enabled = Config.TestsConfiguration.AdministrationTests.enableShipmentsSectionTests,
            description = "Тест поиска заказа по номеру шипмента в админке",
            groups = {"acceptance","regression"},
            priority = 10102
    )
    public void successSearchOrderByShipmentNumber() {
        Administration.Orders.searchOrder(Config.TestVariables.TestParams.testShipment);

        Assert.assertFalse(
                kraken.detect().isElementPresent(Elements.Administration.ShipmentsSection.placeholder()),
                    "Не работает поиск шипмента в админке, пустой результат поиска\n");

        Assert.assertEquals(
                kraken.grab().text(Elements.Administration.ShipmentsSection.firstShipmentNumberInTable()), Config.TestVariables.TestParams.testShipment,
                    "Не работает поиск шипмента в админке, найден не тот шипмент\n");
    }

    // TODO тест можно ускорить - использовать тестовый заказ из конфига
    @Test(  enabled = Config.TestsConfiguration.AdministrationTests.enableShipmentsSectionTests,
            description = "Тест возобновления и отмены заказа через админку",
            groups = {"acceptance","regression"},
            priority = 10103
    )
    public void successResumeAndCancelOrder() {
        SoftAssert softAssert = new SoftAssert();

        kraken.get().ordersPage();
        if(kraken.detect().isOrdersHistoryEmpty()) {
            kraken.get().page("metro");
            kraken.perform().order();
            kraken.get().ordersPage();
        }
        if(kraken.detect().isLastOrderActive()) {
                kraken.perform().click(Elements.UserProfile.OrdersHistoryPage.order.cancelButton());
        }
        kraken.perform().click(Elements.UserProfile.OrdersHistoryPage.order.detailsButton());
        kraken.get().adminOrderDetailsPage(kraken.grab().currentOrderNumber());
        softAssert.assertTrue(
                kraken.detect().isOrderCanceled(),
                    "\nНе выполнились предусловия - заказ уже активен");

        Administration.Orders.resumeOrder();
        softAssert.assertFalse(
                kraken.detect().isOrderCanceled(),
                    "\nНе возобновляется заказ через админку");

        Administration.Orders.cancelOrder();
        softAssert.assertTrue(
                kraken.detect().isOrderCanceled(),
                    "\nНе отменяется заказ через админку");

        softAssert.assertAll();
    }

    @Test(  enabled = Config.TestsConfiguration.AdministrationTests.enableShipmentsSectionTests,
            description = "Тест поиска B2B заказа в админке",
            groups = {"regression"},
            priority = 10104
    )
    public void successSearchB2BOrder() {
        UserData testuser = generate.testCredentials("user");
        User.Do.quickLogout();
        User.Do.registration(testuser);

        Administration.Users.editUser(testuser);
        Administration.Users.grantB2B();
        User.Do.quickLogout();

        User.Do.login(testuser);
        kraken.perform().order();
        String number = kraken.grab().currentOrderNumber();

        Administration.Orders.searchOrder(number,true);

        Assert.assertEquals(
                kraken.grab().text(Elements.Administration.ShipmentsSection.firstOrderNumberInTable()), number,
                    "Не работает поиск B2B заказа в админке");

        Administration.Orders.cancelOrder(number);
    }
}
