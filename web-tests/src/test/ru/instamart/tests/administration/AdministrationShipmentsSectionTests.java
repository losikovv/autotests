package ru.instamart.tests.administration;

import instamart.api.common.RestAddresses;
import instamart.core.common.AppManager;
import instamart.core.testdata.ui.Generate;
import instamart.ui.checkpoints.BaseUICheckpoints;
import instamart.ui.common.pagesdata.UserData;
import instamart.ui.modules.Administration;
import instamart.ui.modules.User;
import instamart.ui.objectsmap.Elements;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.tests.TestBase;

public class AdministrationShipmentsSectionTests extends TestBase {
    
    private final BaseUICheckpoints baseChecks = new BaseUICheckpoints();
    // TODO актуализировать тесты и зарезолвить тудушки - ATST-234

    @BeforeMethod(alwaysRun = true,
            description ="Выполняем шаги предусловий для теста")
    public void beforeTest() {
        kraken.reach().admin();
        kraken.get().adminPage("shipments");
    }

    @Test(  description = "Тест валидации дефолтной странгицы списка заказаов в админке",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 10100
    )
    public void validateDefaultAdminShipmentsPage() {
        baseChecks.checkIsElementPresent(Elements.Administration.ShipmentsSection.title());

        baseChecks.checkIsElementPresent(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.orderDateFrom());
        baseChecks.checkIsElementPresent(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.orderDateTo());
        baseChecks.checkIsElementPresent(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.customerName());
        baseChecks.checkIsElementPresent(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.customerSurname());
        baseChecks.checkIsElementPresent(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.juridicalNameContains());
        baseChecks.checkIsElementPresent(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.shopperLogin());
        baseChecks.checkIsElementPresent(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.driverLogin());

        baseChecks.checkIsElementPresent(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.deliveryTimeFrom());
        baseChecks.checkIsElementPresent(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.deliveryTimeTo());
        baseChecks.checkIsElementPresent(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.phoneNumberContains());
        baseChecks.checkIsElementPresent(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.email());
        baseChecks.checkIsElementPresent(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.innNumber());
        baseChecks.checkIsElementPresent(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.invoiceNumber());

        baseChecks.checkIsElementPresent(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.orderNumber());
        baseChecks.checkIsElementPresent(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.orderStatus());
        baseChecks.checkIsElementPresent(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.retailer());
        baseChecks.checkIsElementPresent(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.store());
        baseChecks.checkIsElementPresent(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.paymentMethod());
        baseChecks.checkIsElementPresent(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.paymentStatus());

        baseChecks.checkIsElementPresent(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.promocode());
        baseChecks.checkIsElementPresent(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.itemsFrom());
        baseChecks.checkIsElementPresent(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.itemsTo());
        baseChecks.checkIsElementPresent(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.weightFrom());
        baseChecks.checkIsElementPresent(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.weightTo());

        baseChecks.checkIsElementPresent(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.completedOnly());
        baseChecks.checkIsElementPresent(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.b2bOnly());
        // todo переделано на мультиселетор, обновить baseChecks.checkIsElementPresent(Elements.Administration.ShipmentsSection.Checkboxes.metroOnly());
        baseChecks.checkIsElementPresent(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.deliveryChangedOnly());

        baseChecks.checkIsElementPresent(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.applyFilterButton());
        baseChecks.checkIsElementPresent(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.clearFilterButton());

        baseChecks.checkIsElementPresent(Elements.Administration.ShipmentsSection.OrdersSearchPage.OrdersTable.container());
        baseChecks.checkIsElementPresent(Elements.Administration.ShipmentsSection.OrdersSearchPage.OrdersTable.column("Номер"));
        baseChecks.checkIsElementPresent(Elements.Administration.ShipmentsSection.OrdersSearchPage.OrdersTable.column("Сумма"));
        baseChecks.checkIsElementPresent(Elements.Administration.ShipmentsSection.OrdersSearchPage.OrdersTable.column("Состояние оплаты"));
        baseChecks.checkIsElementPresent(Elements.Administration.ShipmentsSection.OrdersSearchPage.OrdersTable.column("Состояние доставки"));
        baseChecks.checkIsElementPresent(Elements.Administration.ShipmentsSection.OrdersSearchPage.OrdersTable.column("Ритейлер"));
        baseChecks.checkIsElementPresent(Elements.Administration.ShipmentsSection.OrdersSearchPage.OrdersTable.column("Дата и время доставки"));
        baseChecks.checkIsElementPresent(Elements.Administration.ShipmentsSection.OrdersSearchPage.OrdersTable.column("Комментарий"));
        baseChecks.checkIsElementPresent(Elements.Administration.ShipmentsSection.OrdersSearchPage.OrdersTable.column("Куда"));
        baseChecks.checkIsElementPresent(Elements.Administration.ShipmentsSection.OrdersSearchPage.OrdersTable.column("Сборщик"));
        baseChecks.checkIsElementPresent(Elements.Administration.ShipmentsSection.OrdersSearchPage.OrdersTable.column("Водитель"));
        baseChecks.checkIsElementPresent(Elements.Administration.ShipmentsSection.OrdersSearchPage.OrdersTable.column("Общий вес"));
        baseChecks.checkIsElementPresent(Elements.Administration.ShipmentsSection.OrdersSearchPage.OrdersTable.column("Документация"));
        baseChecks.checkIsElementPresent(Elements.Administration.ShipmentsSection.OrdersSearchPage.OrdersTable.column("Магазин сборки"));

        //todo валидить элементы строки первого заказа
    }

    // TODO test shipmentsTableNotEmptyByDefault

    // TODO test successShowEmptySearchPlaceholder

    @Test(  description = "Тест поиска заказа по номеру заказа в админке",
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

    @Test(  description = "Тест поиска заказа по номеру шипмента в админке",
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
    @Test(  description = "Тест возобновления и отмены заказа через админку",
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

    @Test(  description = "Тест поиска B2B заказа в админке",
            groups = {"sbermarket-regression"},
            priority = 10104
    )
    public void successSearchB2BOrder() {
        UserData testuser = Generate.testCredentials("user");
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
