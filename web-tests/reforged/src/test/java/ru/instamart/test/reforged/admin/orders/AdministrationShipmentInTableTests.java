package ru.instamart.test.reforged.admin.orders;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.admin.AdminRout.*;
import static ru.instamart.reforged.admin.enums.ShipmentStatuses.SHIPMENT_READY;

@Epic("Админка STF")
@Feature("Заказы")
@Story("Страница 'Список заказов' admin/spa/orders. Карточка заказа в таблице ")
public final class AdministrationShipmentInTableTests {

    @CaseId(1502)
    @Test(description = "Ссылка в название магазина, столбец ритейлер, переводит на страницу магазина",
            groups = {"regression", "ondemand_orders_regression", "ondemand_orders_smoke"})
    public void storeNameLinkLeadsToStorePageTest() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        orders().goToPage();
        orders().checkLoadingLabelNotVisible();
        orders().checkOrdersLoaded();

        var retailerName = orders().getRetailerName(1);
        var storeName = orders().getStoreName(1);
        orders().clickStoreLinkInShipment(1);
        orders().switchToNextWindow();

        store().waitPageLoad();
        store().checkStoreNameContains(retailerName);
        store().checkStoreNameContains(storeName);
    }

    @CaseId(1510)
    @Test(description = "Ссылка в номере заказа переводит на страницу Карточка заказа",
            groups = {"regression", "ondemand_orders_regression", "ondemand_orders_smoke"})
    public void orderNumberLinkLeadsToOrderEditPageTest() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        orders().goToPage();
        orders().checkLoadingLabelNotVisible();
        orders().checkOrdersLoaded();

        var orderNumber = orders().getOrderNumber(1);
        orders().clickOrderNumberInShipment(1);
        orders().switchToNextWindow();

        shipmentPage().waitPageLoad();
        shipmentPage().checkPageContains(orderNumber);
    }

    @CaseId(1512)
    @Test(description = "Клик по номеру шипмента разворачивает доп. меню",
            groups = {"regression", "ondemand_orders_regression", "ondemand_orders_smoke"})
    public void shipmentDropdownMenuTestShipmentInfo() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        orders().goToPage();
        orders().checkLoadingLabelNotVisible();
        orders().checkOrdersLoaded();

        var orderNumber = orders().getOrderNumber(1);
        orders().clickShipmentNumberInShipment(1);
        orders().checkShipmentDropdownMenuVisible();

        orders().clickShipmentDropdownMenu("Подробнее о заказе");
        orders().switchToNextWindow();

        shipmentPage().waitPageLoad();
        shipmentPage().checkPageContains(orderNumber);
    }

    @CaseId(1512)
    @Test(description = "Клик по номеру шипмента разворачивает доп. меню",
            groups = {"regression", "ondemand_orders_regression", "ondemand_orders_smoke"})
    public void shipmentDropdownMenuTestAssignment() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        orders().goToPage();
        orders().checkLoadingLabelNotVisible();
        orders().checkOrdersLoaded();
        orders().addStatusFilterItem(SHIPMENT_READY.getName());
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();

        orders().clickShipmentNumberInShipment(1);
        orders().checkShipmentDropdownMenuVisible();

        orders().clickShipmentDropdownMenu("Назначить исполнителя");

        orders().checkManualAssignmentModalVisible();
    }

    @CaseId(1513)
    @Test(description = "Ссылка в статусе оплаты, столбец оплата, переводит на страницу платежей заказа /admin/orders/nomber/payments",
            groups = {"regression", "ondemand_orders_regression", "ondemand_orders_smoke"})
    public void shipmentPaymentStatusLeadsPaymentsTest() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        orders().goToPage();
        orders().checkLoadingLabelNotVisible();
        orders().checkOrdersLoaded();

        var orderNumber = orders().getOrderNumber(1);
        orders().clickPaymentStatusInShipment(1);

        orders().switchToNextWindow();

        shipmentPagePayments().waitPageLoad();
        shipmentPagePayments().checkPageContains(orderNumber);
    }

    @CaseId(1514)
    @Test(description = "Ссылка во времени заказа переводит на страницу Магазин и время доставки /admin/orders/nomber/delivery_windows?shipment=nomber",
            groups = {"regression", "ondemand_orders_regression", "ondemand_orders_smoke"})
    public void shipmentDeliveryTimeLeadsShipmentPageDeliveryTest() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        orders().goToPage();
        orders().checkLoadingLabelNotVisible();
        orders().checkOrdersLoaded();

        orders().clickDeliveryTimeInShipment(1);

        orders().switchToNextWindow();

        shipmentPageDelivery().waitPageLoad();
        shipmentPageDelivery().checkSavedDeliveryIntervalVisible();
    }

    @CaseId(1515)
    @Test(description = "Ссылка в ФИ клиента, столбец Заказчик, переводит на страницу Реквизиты клиента /admin/orders/nomber/customer",
            groups = {"regression", "ondemand_orders_regression", "ondemand_orders_smoke"})
    public void shipmentClientNameLeadsClientEditPageTest() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        orders().goToPage();
        orders().checkLoadingLabelNotVisible();
        orders().checkOrdersLoaded();

        var orderNumber = orders().getOrderNumber(1);
        orders().clickCustomerName(1);

        orders().switchToNextWindow();

        shipmentPageCustomer().waitPageLoad();
        shipmentPageCustomer().checkPageContains(orderNumber);
    }
}
