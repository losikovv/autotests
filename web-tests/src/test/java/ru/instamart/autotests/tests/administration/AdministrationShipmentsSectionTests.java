package ru.instamart.autotests.tests.administration;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.appmanager.AdministrationHelper;
import ru.instamart.autotests.appmanager.User;
import ru.instamart.autotests.models.UserData;
import ru.instamart.autotests.testdata.generate;
import ru.instamart.autotests.tests.TestBase;

import static ru.instamart.autotests.application.Config.TestsConfiguration.AdministrationTests.enableShipmentsSectionTests;
import static ru.instamart.autotests.application.Config.TestVariables.TestParams.testOrder;
import static ru.instamart.autotests.application.Config.TestVariables.TestParams.testShipment;

public class AdministrationShipmentsSectionTests extends TestBase {

    @BeforeMethod(alwaysRun = true)
    public void reachAdministrationPanel() {
        kraken.reach().admin();
    }

    @Test(  enabled = enableShipmentsSectionTests,
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

    @Test(  enabled = enableShipmentsSectionTests,
            description = "Тест поиска заказа по номеру заказа в админке",
            groups = {"acceptance","regression"},
            priority = 10101
    )
    public void successSearchOrderByOrderNumber() {
        AdministrationHelper.Orders.searchOrder(testOrder);

        Assert.assertFalse(
                kraken.detect().isElementPresent(Elements.Administration.ShipmentsSection.placeholder()),
                    "Не работает поиск заказа в админке, пустой результат поиска\n");

        Assert.assertEquals(
                kraken.grab().text(Elements.Administration.ShipmentsSection.firstOrderNumberInTable()), testOrder,
                    "Не работает поиск заказа в админке, найден не тот шипмент\n");
    }

    @Test(  enabled = enableShipmentsSectionTests,
            description = "Тест поиска заказа по номеру шипмента в админке",
            groups = {"acceptance","regression"},
            priority = 10102
    )
    public void successSearchOrderByShipmentNumber() {
        AdministrationHelper.Orders.searchOrder(testShipment);

        Assert.assertFalse(
                kraken.detect().isElementPresent(Elements.Administration.ShipmentsSection.placeholder()),
                    "Не работает поиск шипмента в админке, пустой результат поиска\n");

        Assert.assertEquals(
                kraken.grab().text(Elements.Administration.ShipmentsSection.firstShipmentNumberInTable()), testShipment,
                    "Не работает поиск шипмента в админке, найден не тот шипмент\n");
    }

    // TODO тест можно ускорить - использовать тестовый заказ из конфига
    @Test(  enabled = enableShipmentsSectionTests,
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

    @Test(  enabled = enableShipmentsSectionTests,
            description = "Тест поиска B2B заказа в админке",
            groups = {"regression"},
            priority = 10104
    )
    public void successSearchB2BOrder() {
        UserData testuser = generate.testCredentials("user");
        User.Do.quickLogout();
        User.Do.registration(testuser);

        AdministrationHelper.Users.editUser(testuser);
        AdministrationHelper.Users.grantB2B();
        User.Do.quickLogout();

        User.Do.login(testuser);
        kraken.perform().order();
        String number = kraken.grab().currentOrderNumber();

        AdministrationHelper.Orders.searchOrder(number,true);

        Assert.assertEquals(
                kraken.grab().text(Elements.Administration.ShipmentsSection.firstOrderNumberInTable()), number,
                    "Не работает поиск B2B заказа в админке");

        AdministrationHelper.Orders.cancelOrder(number);
    }
}
