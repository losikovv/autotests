package ru.instamart.ui.administration;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.core.testdata.UserManager;
import ru.instamart.ui.TestBase;
import ru.instamart.ui.checkpoint.BaseUICheckpoints;
import ru.instamart.core.testdata.UserData;
import ru.instamart.ui.module.Administration;
import ru.instamart.ui.module.User;
import ru.instamart.ui.module.shop.Order;
import ru.instamart.ui.Elements;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

@Epic("Админка STF")
@Feature("Управление заказами")
public class AdministrationShipmentsSectionTests extends TestBase {
    
    private final BaseUICheckpoints baseChecks = new BaseUICheckpoints();
    // TODO актуализировать тесты и зарезолвить тудушки - ATST-234

    @BeforeClass(alwaysRun = true,
            description ="Выполняем шаги предусловий для теста")
    public void beforeTest() {
        User.Logout.quicklyAdmin();
//        kraken.reach().admin();
        User.Auth.withEmail(UserManager.getDefaultAdmin());
        kraken.get().adminPage("shipments");
    }

    @CaseId(175)
    @Story("Тест на корректное отображение элементов страницы со списком заказов в админке")
    @Test(  description = "Тест на корректное отображение элементов страницы со списком заказов в админке",
            groups = {"sbermarket-acceptance","sbermarket-regression","admin-ui-smoke"}
    )
    public void validateDefaultAdminShipmentsPage() {
        kraken.get().adminPage("shipments");
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

    @CaseId(182)
    @Story("Тест поиска заказа по номеру заказа в админке")
    @Test(  description = "Тест поиска заказа по номеру заказа в админке",
            groups = {"sbermarket-acceptance","sbermarket-regression","admin-ui-smoke"}
    )
    public void successSearchOrderByOrderNumber() {
        final String orderNumber = kraken.grab()
                .text(Elements.Administration.ShipmentsSection.OrdersSearchPage.OrdersTable.orderRow.orderNumber());
        Administration.Orders.searchOrder(orderNumber);
        baseChecks.checkElementAbsence(Elements.Administration.ShipmentsSection.OrdersSearchPage.OrdersTable.placeholder(),
                "Не работает поиск заказа в админке, пустой результат поиска\n");
        baseChecks.checkTextIsCorrectInElement(Elements.Administration.ShipmentsSection.OrdersSearchPage.OrdersTable.orderRow.orderNumber(),
                orderNumber,
                "Не работает поиск заказа в админке, найден не тот ордер\n");
    }

    @CaseId(445)
    @Story("Тест поиска заказа по номеру шипмента в админке")
    @Test(  description = "Тест поиска заказа по номеру шипмента в админке",
            groups = {"sbermarket-acceptance","sbermarket-regression","admin-ui-smoke"}
    )
    public void successSearchOrderByShipmentNumber() {
        String shipmentNumber = kraken.grab().text(Elements.Administration.ShipmentsSection.OrdersSearchPage.OrdersTable.orderRow.shipmentNumber());
        Administration.Orders.searchOrder(shipmentNumber);
        baseChecks.checkElementAbsence(Elements.Administration.ShipmentsSection.OrdersSearchPage.OrdersTable.placeholder(),
                "Не работает поиск шипмента в админке, пустой результат поиска\n");
        baseChecks.checkTextIsCorrectInElement(Elements.Administration.ShipmentsSection.OrdersSearchPage.OrdersTable.orderRow.shipmentNumber(),
                shipmentNumber,
                "Не работает поиск шипмента в админке, найден не тот шипмент\n");
    }

    // TODO тест можно ускорить - использовать тестовый заказ из конфига
    // TODO поправить тест после того как починб тест заказа
    @Story("Тест возобновления и отмены заказа через админку")
    @Test(  description = "Тест возобновления и отмены заказа через админку",
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    )
    public void successResumeAndCancelOrder() {
        kraken.apiV2().fillCart(UserManager.getDefaultAdmin(), RestAddresses.Moscow.defaultAddress());
        kraken.reach().checkout();
        kraken.checkout().complete();
        //final String shipment = kraken.grab().currentOrderNumber();
        Order.cancelOrder();

        //kraken.get().adminOrderDetailsPage(shipment);
        assertTrue(
                kraken.detect().isOrderCanceled(),
                    "\nНе выполнились предусловия - заказ уже активен");

        Administration.Orders.resumeOrder();
        assertFalse(
                kraken.detect().isOrderCanceled(),
                    "\nНе возобновляется заказ через админку");

        Administration.Orders.cancelOrder();
        assertTrue(
                kraken.detect().isOrderCanceled(),
                    "\nНе отменяется заказ через админку");
    }

    // Нужен юзер
    @Story("Тест поиска B2B заказа в админке")
    @Test(  description = "Тест поиска B2B заказа в админке",
            groups = {"sbermarket-regression"}
    )
    public void successSearchB2BOrder() {
        final UserData testuser = UserManager.getUser();
        User.Logout.quickly();
        User.Do.registration(testuser);

        Administration.Users.editUser(testuser);
        Administration.Users.grantB2B();
        User.Logout.quickly();

        User.Auth.withEmail(testuser);
        Order.order();
        String number = kraken.grab().shipmentNumber();

        Administration.Orders.searchOrder(number,true);

        Assert.assertEquals(
                kraken.grab().text(Elements.Administration.ShipmentsSection.OrdersSearchPage.OrdersTable.orderRow.orderNumber()), number,
                    "Не работает поиск B2B заказа в админке");

        Administration.Orders.cancelOrder(number);
    }
}
