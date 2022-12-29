package ru.instamart.test.reforged.admin.orders;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Test;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.listener.Skip;

import static ru.instamart.reforged.Group.*;
import static ru.instamart.reforged.admin.AdminRout.*;
import static ru.instamart.reforged.admin.enums.ShipmentStatuses.SHIPMENT_READY;

/**
 * Сейчас нет пагинации и в ближайшее время не предвидится GARM-839
 */
@Epic("Админка STF")
@Feature("Заказы")
@Story("Страница 'Список заказов' admin/spa/orders. Карточка заказа в таблице ")
public final class AdministrationShipmentInTableTests {

    @TmsLink("2101")
    @Test(description = "Переход в карточку магазина со страницы Список заказов",
            groups = {OD_ORDERS_REGRESS, OD_ORDERS_SMOKE, OD_SMOKE, OD_REGRESS})
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

    @TmsLink("2109")
    @Test(description = "Быстрый переход в карточку заказа со страницы Список заказов",
            groups = {OD_ORDERS_REGRESS, OD_ORDERS_SMOKE, OD_SMOKE, OD_REGRESS})
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

        shipmentPage().closeAndSwitchToPrevWindow();

        var orderNumberSecond = orders().getOrderNumber(2);
        orders().clickOrderNumberInShipment(2);
        orders().switchToNextWindow();

        shipmentPage().waitPageLoad();
        shipmentPage().checkPageContains(orderNumberSecond);
    }

    @Skip
    //Скачивание 500 на стейджах
    @TmsLink("2110")
    @Test(description = "Скачать накладную заказа",
            groups = {OD_ORDERS_REGRESS, OD_ORDERS_SMOKE, OD_SMOKE, OD_REGRESS})
    public void downloadAnOrderWaybillTest() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        orders().goToPage();
        orders().checkLoadingLabelNotVisible();
        orders().checkOrdersLoaded();
    }

    @TmsLink("2111")
    @Test(description = "Переход в карточку заказа через дополнительное меню на странице Список заказов",
            groups = {OD_ORDERS_REGRESS, OD_ORDERS_SMOKE, OD_SMOKE, OD_REGRESS})
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

    @TmsLink("2112")
    @Test(description = "Переход в платежи заказа через дополнительное меню",
            groups = {OD_ORDERS_REGRESS, OD_ORDERS_SMOKE, OD_SMOKE, OD_REGRESS})
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

    //TODO: Магазин = Диспатч 2.0, статус заказа = оформлен, статус сборки = собирается и назначен на исполнителя
    //Нужно оформлять такой заказ по другому не найти
    @Skip
    @TmsLink("2162")
    @Test(description = "Переход на просмотр местоположения курьера на карте",
            groups = {OD_ORDERS_REGRESS, OD_ORDERS_SMOKE, OD_SMOKE, OD_REGRESS})
    public void shipmentDropdownMenuTestShowOnMap() {
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

        orders().clickShipmentDropdownMenu("Показать на карте");

    }

    @TmsLink("2113")
    @Test(description = "Переход на страницу переноса слота заказа из таблица заказов",
            groups = {OD_ORDERS_REGRESS, OD_ORDERS_SMOKE, OD_SMOKE, OD_REGRESS})
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

        shipmentPageDelivery().selectFirstAvailableDeliveryInterval();
        shipmentPageDelivery().clickDeliveryChangeInputReason();
        shipmentPageDelivery().selectFirstDeliveryChangeReason();
        shipmentPageDelivery().clickUpdateDelivery();
        shipmentPageDelivery().interactAlert().checkSuccessFlashVisible();
    }

    @TmsLink("2114")
    @Test(description = "Переход к реквизитам клиента со страницы Список заказов",
            groups = {OD_ORDERS_REGRESS, OD_ORDERS_SMOKE, OD_SMOKE, OD_REGRESS})
    public void shipmentClientNameLeadsClientEditPageTest() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        orders().goToPage();
        orders().checkLoadingLabelNotVisible();
        orders().checkOrdersLoaded();
        orders().checkRequestsWasLoad();

        var orderNumber = orders().getOrderNumber(1);
        orders().clickCustomerName(1);
        orders().switchToNextWindow();
        shipmentPageCustomer().waitPageLoad();
        shipmentPageCustomer().checkPageContains(orderNumber);
    }

    @Skip
    //TODO: создать заказ в магазине с включенным Диспатч 2.0
    //у заказов созданных без включенного диспатча будет выдавать ошибку
    //вывести партнера на смену в магазин где собран заказ
    @TmsLink("2129")
    @Test(description = "Удаление назначенного универсала",
            groups = {OD_ORDERS_REGRESS, OD_ORDERS_SMOKE, OD_SMOKE, OD_REGRESS})
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
}
