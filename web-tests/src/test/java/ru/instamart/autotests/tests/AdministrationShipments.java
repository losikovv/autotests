package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.appmanager.AdministrationHelper;
import ru.instamart.autotests.models.UserData;
import ru.instamart.autotests.testdata.generate;

import static ru.instamart.autotests.application.Config.testAdministration;
import static ru.instamart.autotests.application.Config.testOrder;
import static ru.instamart.autotests.application.Config.testShipment;

public class AdministrationShipments extends TestBase {

    @BeforeMethod(alwaysRun = true)
    public void reachAdministrationPanel() {
        kraken.reach().admin();
    }

    @Test(  enabled = testAdministration,
            description = "Тест поиска заказа по номеру заказа в админке",
            groups = {"smoke","acceptance","regression"},
            priority = 2100
    )
    public void validateAdminShipmentsElements() {
        kraken.check().elementPresence(Elements.Admin.Shipments.title());

        kraken.check().elementPresence(Elements.Admin.Shipments.Filters.orderDateFrom());
        kraken.check().elementPresence(Elements.Admin.Shipments.Filters.orderDateTo());
        kraken.check().elementPresence(Elements.Admin.Shipments.Filters.customerName());
        kraken.check().elementPresence(Elements.Admin.Shipments.Filters.customerSurname());
        kraken.check().elementPresence(Elements.Admin.Shipments.Filters.juridicalNameContains());
        kraken.check().elementPresence(Elements.Admin.Shipments.Filters.shopperLogin());
        kraken.check().elementPresence(Elements.Admin.Shipments.Filters.driverLogin());

        kraken.check().elementPresence(Elements.Admin.Shipments.Filters.deliveryTimeFrom());
        kraken.check().elementPresence(Elements.Admin.Shipments.Filters.deliveryTimeTo());
        kraken.check().elementPresence(Elements.Admin.Shipments.Filters.phoneNumberContains());
        kraken.check().elementPresence(Elements.Admin.Shipments.Filters.email());
        kraken.check().elementPresence(Elements.Admin.Shipments.Filters.innNumber());
        kraken.check().elementPresence(Elements.Admin.Shipments.Filters.invoiceNumber());

        kraken.check().elementPresence(Elements.Admin.Shipments.Filters.orderNumber());
        kraken.check().elementPresence(Elements.Admin.Shipments.Filters.orderStatus());
        kraken.check().elementPresence(Elements.Admin.Shipments.Filters.retailer());
        kraken.check().elementPresence(Elements.Admin.Shipments.Filters.store());
        kraken.check().elementPresence(Elements.Admin.Shipments.Filters.paymentMethod());
        kraken.check().elementPresence(Elements.Admin.Shipments.Filters.paymentStatus());

        kraken.check().elementPresence(Elements.Admin.Shipments.Filters.promocode());
        kraken.check().elementPresence(Elements.Admin.Shipments.Filters.itemsFrom());
        kraken.check().elementPresence(Elements.Admin.Shipments.Filters.itemsTo());
        kraken.check().elementPresence(Elements.Admin.Shipments.Filters.weightFrom());
        kraken.check().elementPresence(Elements.Admin.Shipments.Filters.weightTo());

        kraken.check().elementPresence(Elements.Admin.Shipments.Checkboxes.completedOnly());
        kraken.check().elementPresence(Elements.Admin.Shipments.Checkboxes.b2bOnly());
        kraken.check().elementPresence(Elements.Admin.Shipments.Checkboxes.metroOnly());
        kraken.check().elementPresence(Elements.Admin.Shipments.Checkboxes.deliveryChangedOnly());

        kraken.check().elementPresence(Elements.Admin.Shipments.applyFilterButton());
        kraken.check().elementPresence(Elements.Admin.Shipments.clearFilterButton());

        kraken.check().elementPresence(Elements.Admin.Shipments.table());
        kraken.check().elementPresence(Elements.Admin.Shipments.tableTitle("Номер"));
        kraken.check().elementPresence(Elements.Admin.Shipments.tableTitle("Сумма"));
        kraken.check().elementPresence(Elements.Admin.Shipments.tableTitle("Состояние оплаты"));
        kraken.check().elementPresence(Elements.Admin.Shipments.tableTitle("Состояние доставки"));
        kraken.check().elementPresence(Elements.Admin.Shipments.tableTitle("Ритейлер"));
        kraken.check().elementPresence(Elements.Admin.Shipments.tableTitle("Дата и время доставки"));
        kraken.check().elementPresence(Elements.Admin.Shipments.tableTitle("Комментарий"));
        kraken.check().elementPresence(Elements.Admin.Shipments.tableTitle("Куда"));
        kraken.check().elementPresence(Elements.Admin.Shipments.tableTitle("Сборщик"));
        kraken.check().elementPresence(Elements.Admin.Shipments.tableTitle("Водитель"));
        kraken.check().elementPresence(Elements.Admin.Shipments.tableTitle("Общий вес"));
        kraken.check().elementPresence(Elements.Admin.Shipments.tableTitle("Документация"));
        kraken.check().elementPresence(Elements.Admin.Shipments.tableTitle("Магазин сборки"));

        //todo валидить элементы строки первого заказа
    }

    // TODO test shipmentsTableNotEmptyByDefault

    // TODO test successShowEmptySearchPlaceholder

    @Test(  enabled = testAdministration,
            description = "Тест поиска заказа по номеру заказа в админке",
            groups = {"acceptance","regression"},
            priority = 2101
    )
    public void successSearchOrderByOrderNumber() {
        AdministrationHelper.Orders.searchOrder(testOrder);

        Assert.assertFalse(
                kraken.detect().isElementPresent(Elements.Admin.Shipments.placeholder()),
                    "Не работает поиск заказа в админке, пустой результат поиска\n");

        Assert.assertEquals(
                kraken.grab().text(Elements.Admin.Shipments.firstOrderNumberInTable()), testOrder,
                    "Не работает поиск заказа в админке, найден не тот шипмент\n");
    }

    @Test(  enabled = testAdministration,
            description = "Тест поиска заказа по номеру шипмента в админке",
            groups = {"acceptance","regression"},
            priority = 2102
    )
    public void successSearchOrderByShipmentNumber() {
        AdministrationHelper.Orders.searchOrder(testShipment);

        Assert.assertFalse(
                kraken.detect().isElementPresent(Elements.Admin.Shipments.placeholder()),
                    "Не работает поиск шипмента в админке, пустой результат поиска\n");

        Assert.assertEquals(
                kraken.grab().text(Elements.Admin.Shipments.firstShipmentNumberInTable()), testShipment,
                    "Не работает поиск шипмента в админке, найден не тот шипмент\n");
    }

    // TODO тест можно ускорить - использовать тестовый заказ из конфига
    @Test(  enabled = testAdministration,
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
        softAssert.assertTrue(
                kraken.detect().isOrderCanceled(),
                    "\nНе выполнились предусловия - заказ уже активен");

        AdministrationHelper.Orders.resumeOrder();
        softAssert.assertFalse(
                kraken.detect().isOrderCanceled(),
                    "\nНе возобновляется заказ через админку");

        AdministrationHelper.Orders.cancelOrder();
        softAssert.assertTrue(
                kraken.detect().isOrderCanceled(),
                    "\nНе отменяется заказ через админку");

        softAssert.assertAll();
    }

    @Test(  enabled = testAdministration,
            description = "Тест поиска B2B заказа в админке",
            groups = {"regression"},
            priority = 2104
    )
    public void successSearchB2BOrder() {
        kraken.perform().quickLogout();
        UserData testuser = generate.testCredentials("user");
        kraken.perform().registration(testuser);

        AdministrationHelper.Users.editUser(testuser);
        AdministrationHelper.Users.grantB2B();
        kraken.perform().quickLogout();

        kraken.perform().authorisation(testuser);
        kraken.perform().order();
        String number = kraken.grab().currentOrderNumber();

        AdministrationHelper.Orders.searchOrder(number,true);

        Assert.assertEquals(
                kraken.grab().text(Elements.Admin.Shipments.firstOrderNumberInTable()), number,
                    "Не работает поиск B2B заказа в админке");

        AdministrationHelper.Orders.cancelOrder(number);
    }
}
