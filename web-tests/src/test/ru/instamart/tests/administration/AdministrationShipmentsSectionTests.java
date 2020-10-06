package ru.instamart.tests.administration;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import instamart.core.common.AppManager;
import instamart.ui.objectsmap.Elements;
import instamart.ui.common.pagesdata.UserData;
import instamart.ui.modules.Administration;
import instamart.ui.modules.User;
import instamart.api.common.RestAddresses;
import instamart.core.testdata.ui.generate;
import ru.instamart.tests.TestBase;

import static instamart.core.settings.Config.TestsConfiguration.AdministrationTests.enableShipmentsSectionTests;

public class AdministrationShipmentsSectionTests extends TestBase {

    // TODO актуализировать тесты и зарезолвить тудушки - ATST-234

    @BeforeMethod(alwaysRun = true)
    public void reachAdministrationPanel() {
        kraken.reach().admin();
        kraken.get().adminPage("shipments");
    }

    @Test(  enabled = enableShipmentsSectionTests,
            description = "Тест валидации дефолтной странгицы списка заказаов в админке",
            groups = {"sbermarket-smoke","sbermarket-acceptance","sbermarket-regression"},
            priority = 10100
    )
    public void validateDefaultAdminShipmentsPage() {
        assertPresence(Elements.Administration.ShipmentsSection.title());

        assertPresence(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.orderDateFrom());
        assertPresence(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.orderDateTo());
        assertPresence(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.customerName());
        assertPresence(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.customerSurname());
        assertPresence(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.juridicalNameContains());
        assertPresence(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.shopperLogin());
        assertPresence(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.driverLogin());

        assertPresence(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.deliveryTimeFrom());
        assertPresence(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.deliveryTimeTo());
        assertPresence(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.phoneNumberContains());
        assertPresence(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.email());
        assertPresence(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.innNumber());
        assertPresence(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.invoiceNumber());

        assertPresence(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.orderNumber());
        assertPresence(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.orderStatus());
        assertPresence(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.retailer());
        assertPresence(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.store());
        assertPresence(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.paymentMethod());
        assertPresence(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.paymentStatus());

        assertPresence(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.promocode());
        assertPresence(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.itemsFrom());
        assertPresence(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.itemsTo());
        assertPresence(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.weightFrom());
        assertPresence(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.weightTo());

        assertPresence(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.completedOnly());
        assertPresence(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.b2bOnly());
        // todo переделано на мультиселетор, обновить assertPresence(Elements.Administration.ShipmentsSection.Checkboxes.metroOnly());
        assertPresence(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.deliveryChangedOnly());

        assertPresence(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.applyFilterButton());
        assertPresence(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.clearFilterButton());

        assertPresence(Elements.Administration.ShipmentsSection.OrdersSearchPage.OrdersTable.container());
        assertPresence(Elements.Administration.ShipmentsSection.OrdersSearchPage.OrdersTable.column("Номер"));
        assertPresence(Elements.Administration.ShipmentsSection.OrdersSearchPage.OrdersTable.column("Сумма"));
        assertPresence(Elements.Administration.ShipmentsSection.OrdersSearchPage.OrdersTable.column("Состояние оплаты"));
        assertPresence(Elements.Administration.ShipmentsSection.OrdersSearchPage.OrdersTable.column("Состояние доставки"));
        assertPresence(Elements.Administration.ShipmentsSection.OrdersSearchPage.OrdersTable.column("Ритейлер"));
        assertPresence(Elements.Administration.ShipmentsSection.OrdersSearchPage.OrdersTable.column("Дата и время доставки"));
        assertPresence(Elements.Administration.ShipmentsSection.OrdersSearchPage.OrdersTable.column("Комментарий"));
        assertPresence(Elements.Administration.ShipmentsSection.OrdersSearchPage.OrdersTable.column("Куда"));
        assertPresence(Elements.Administration.ShipmentsSection.OrdersSearchPage.OrdersTable.column("Сборщик"));
        assertPresence(Elements.Administration.ShipmentsSection.OrdersSearchPage.OrdersTable.column("Водитель"));
        assertPresence(Elements.Administration.ShipmentsSection.OrdersSearchPage.OrdersTable.column("Общий вес"));
        assertPresence(Elements.Administration.ShipmentsSection.OrdersSearchPage.OrdersTable.column("Документация"));
        assertPresence(Elements.Administration.ShipmentsSection.OrdersSearchPage.OrdersTable.column("Магазин сборки"));

        //todo валидить элементы строки первого заказа
    }

    // TODO test shipmentsTableNotEmptyByDefault

    // TODO test successShowEmptySearchPlaceholder

    @Test(  enabled = enableShipmentsSectionTests,
            description = "Тест поиска заказа по номеру заказа в админке",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 10101
    )
    public void successSearchOrderByOrderNumber() {
        final String orderNumber = kraken.grab().text(Elements.Administration.ShipmentsSection.OrdersSearchPage.OrdersTable.orderRow.orderNumber());

        Administration.Orders.searchOrder(orderNumber);

        Assert.assertFalse(
                kraken.detect().isElementPresent(Elements.Administration.ShipmentsSection.OrdersSearchPage.OrdersTable.placeholder()),
                    "Не работает поиск заказа в админке, пустой результат поиска\n");

        Assert.assertEquals(
                kraken.grab().text(Elements.Administration.ShipmentsSection.OrdersSearchPage.OrdersTable.orderRow.orderNumber()), orderNumber,
                    "Не работает поиск заказа в админке, найден не тот ордер\n");
    }

    @Test(  enabled = enableShipmentsSectionTests,
            description = "Тест поиска заказа по номеру шипмента в админке",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 10102
    )
    public void successSearchOrderByShipmentNumber() {
        String shipmentNumber = kraken.grab().text(Elements.Administration.ShipmentsSection.OrdersSearchPage.OrdersTable.orderRow.shipmentNumber());
        Administration.Orders.searchOrder(shipmentNumber);

        Assert.assertFalse(
                kraken.detect().isElementPresent(Elements.Administration.ShipmentsSection.OrdersSearchPage.OrdersTable.placeholder()),
                    "Не работает поиск шипмента в админке, пустой результат поиска\n");

        Assert.assertEquals(
                kraken.grab().text(Elements.Administration.ShipmentsSection.OrdersSearchPage.OrdersTable.orderRow.shipmentNumber()), shipmentNumber,
                    "Не работает поиск шипмента в админке, найден не тот шипмент\n");
    }

    // TODO тест можно ускорить - использовать тестовый заказ из конфига
    @Test(  enabled = enableShipmentsSectionTests,
            description = "Тест возобновления и отмены заказа через админку",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 10103
    )
    public void successResumeAndCancelOrder() {
        SoftAssert softAssert = new SoftAssert();

        kraken.apiV2().fillCart(AppManager.session.admin, RestAddresses.Moscow.defaultAddress());
        kraken.reach().checkout();
        kraken.checkout().complete();
        //final String shipment = kraken.grab().currentOrderNumber();
        kraken.perform().cancelOrder();

        //kraken.get().adminOrderDetailsPage(shipment);
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

    @Test(  enabled = enableShipmentsSectionTests,
            description = "Тест поиска B2B заказа в админке",
            groups = {"sbermarket-regression"},
            priority = 10104
    )
    public void successSearchB2BOrder() {
        UserData testuser = generate.testCredentials("user");
        User.Logout.quickly();
        User.Do.registration(testuser);

        Administration.Users.editUser(testuser);
        Administration.Users.grantB2B();
        User.Logout.quickly();

        User.Auth.withEmail(testuser);
        kraken.perform().order();
        String number = kraken.grab().shipmentNumber();

        Administration.Orders.searchOrder(number,true);

        Assert.assertEquals(
                kraken.grab().text(Elements.Administration.ShipmentsSection.OrdersSearchPage.OrdersTable.orderRow.orderNumber()), number,
                    "Не работает поиск B2B заказа в админке");

        Administration.Orders.cancelOrder(number);
    }
}
