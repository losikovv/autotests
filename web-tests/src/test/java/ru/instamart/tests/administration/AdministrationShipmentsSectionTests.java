package ru.instamart.tests.administration;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.application.AppManager;
import ru.instamart.application.Elements;
import ru.instamart.application.models.UserData;
import ru.instamart.application.platform.modules.Administration;
import ru.instamart.application.platform.modules.User;
import ru.instamart.application.rest.RestAddresses;
import ru.instamart.testdata.generate;
import ru.instamart.tests.TestBase;

import static ru.instamart.application.Config.TestsConfiguration.AdministrationTests.enableShipmentsSectionTests;

public class AdministrationShipmentsSectionTests extends TestBase {

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

        assertPresence(Elements.Administration.ShipmentsSection.Filters.orderDateFrom());
        assertPresence(Elements.Administration.ShipmentsSection.Filters.orderDateTo());
        assertPresence(Elements.Administration.ShipmentsSection.Filters.customerName());
        assertPresence(Elements.Administration.ShipmentsSection.Filters.customerSurname());
        assertPresence(Elements.Administration.ShipmentsSection.Filters.juridicalNameContains());
        assertPresence(Elements.Administration.ShipmentsSection.Filters.shopperLogin());
        assertPresence(Elements.Administration.ShipmentsSection.Filters.driverLogin());

        assertPresence(Elements.Administration.ShipmentsSection.Filters.deliveryTimeFrom());
        assertPresence(Elements.Administration.ShipmentsSection.Filters.deliveryTimeTo());
        assertPresence(Elements.Administration.ShipmentsSection.Filters.phoneNumberContains());
        assertPresence(Elements.Administration.ShipmentsSection.Filters.email());
        assertPresence(Elements.Administration.ShipmentsSection.Filters.innNumber());
        assertPresence(Elements.Administration.ShipmentsSection.Filters.invoiceNumber());

        assertPresence(Elements.Administration.ShipmentsSection.Filters.orderNumber());
        assertPresence(Elements.Administration.ShipmentsSection.Filters.orderStatus());
        assertPresence(Elements.Administration.ShipmentsSection.Filters.retailer());
        assertPresence(Elements.Administration.ShipmentsSection.Filters.store());
        assertPresence(Elements.Administration.ShipmentsSection.Filters.paymentMethod());
        assertPresence(Elements.Administration.ShipmentsSection.Filters.paymentStatus());

        assertPresence(Elements.Administration.ShipmentsSection.Filters.promocode());
        assertPresence(Elements.Administration.ShipmentsSection.Filters.itemsFrom());
        assertPresence(Elements.Administration.ShipmentsSection.Filters.itemsTo());
        assertPresence(Elements.Administration.ShipmentsSection.Filters.weightFrom());
        assertPresence(Elements.Administration.ShipmentsSection.Filters.weightTo());

        assertPresence(Elements.Administration.ShipmentsSection.Checkboxes.completedOnly());
        assertPresence(Elements.Administration.ShipmentsSection.Checkboxes.b2bOnly());
        // todo переделано на мультиселетор, обновить assertPresence(Elements.Administration.ShipmentsSection.Checkboxes.metroOnly());
        assertPresence(Elements.Administration.ShipmentsSection.Checkboxes.deliveryChangedOnly());

        assertPresence(Elements.Administration.ShipmentsSection.applyFilterButton());
        assertPresence(Elements.Administration.ShipmentsSection.clearFilterButton());

        assertPresence(Elements.Administration.ShipmentsSection.table());
        assertPresence(Elements.Administration.ShipmentsSection.tableTitle("Номер"));
        assertPresence(Elements.Administration.ShipmentsSection.tableTitle("Сумма"));
        assertPresence(Elements.Administration.ShipmentsSection.tableTitle("Состояние оплаты"));
        assertPresence(Elements.Administration.ShipmentsSection.tableTitle("Состояние доставки"));
        assertPresence(Elements.Administration.ShipmentsSection.tableTitle("Ритейлер"));
        assertPresence(Elements.Administration.ShipmentsSection.tableTitle("Дата и время доставки"));
        assertPresence(Elements.Administration.ShipmentsSection.tableTitle("Комментарий"));
        assertPresence(Elements.Administration.ShipmentsSection.tableTitle("Куда"));
        assertPresence(Elements.Administration.ShipmentsSection.tableTitle("Сборщик"));
        assertPresence(Elements.Administration.ShipmentsSection.tableTitle("Водитель"));
        assertPresence(Elements.Administration.ShipmentsSection.tableTitle("Общий вес"));
        assertPresence(Elements.Administration.ShipmentsSection.tableTitle("Документация"));
        assertPresence(Elements.Administration.ShipmentsSection.tableTitle("Магазин сборки"));

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
        final String orderNumber = kraken.grab().text(Elements.Administration.ShipmentsSection.firstOrderNumberInTable());

        Administration.Orders.searchOrder(orderNumber);

        Assert.assertFalse(
                kraken.detect().isElementPresent(Elements.Administration.ShipmentsSection.placeholder()),
                    "Не работает поиск заказа в админке, пустой результат поиска\n");

        Assert.assertEquals(
                kraken.grab().text(Elements.Administration.ShipmentsSection.firstOrderNumberInTable()), orderNumber,
                    "Не работает поиск заказа в админке, найден не тот ордер\n");
    }

    @Test(  enabled = enableShipmentsSectionTests,
            description = "Тест поиска заказа по номеру шипмента в админке",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 10102
    )
    public void successSearchOrderByShipmentNumber() {
        String shipmentNumber = kraken.grab().text(Elements.Administration.ShipmentsSection.firstShipmentNumberInTable());

        Administration.Orders.searchOrder(shipmentNumber);

        Assert.assertFalse(
                kraken.detect().isElementPresent(Elements.Administration.ShipmentsSection.placeholder()),
                    "Не работает поиск шипмента в админке, пустой результат поиска\n");

        Assert.assertEquals(
                kraken.grab().text(Elements.Administration.ShipmentsSection.firstShipmentNumberInTable()), shipmentNumber,
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

        kraken.rest().fillCart(AppManager.session.admin, RestAddresses.Moscow.defaultAddress());
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
                kraken.grab().text(Elements.Administration.ShipmentsSection.firstOrderNumberInTable()), number,
                    "Не работает поиск B2B заказа в админке");

        Administration.Orders.cancelOrder(number);
    }
}
