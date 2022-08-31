package ru.instamart.test.reforged.admin.orders;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static ru.instamart.reforged.admin.AdminRout.login;
import static ru.instamart.reforged.admin.AdminRout.orders;
import static ru.instamart.reforged.admin.enums.PaymentMethods.*;
import static ru.instamart.reforged.admin.enums.PaymentStatuses.*;
import static ru.instamart.reforged.admin.enums.QuickFilters.*;
import static ru.instamart.reforged.admin.enums.ShipmentStatuses.*;

@Epic("Админка STF")
@Feature("Заказы")
@Story("Страница 'Список заказов' admin/spa/orders. Фильтры с множественным выбором")
public final class AdministrationMultiselectFiltersOrdersTests {


    @CaseId(1517)
    @Test(description = "Фильтр Номер заказа выдает список заказов по номеру заказа и по номеру шипмента",
            groups = {"regression", "ondemand_orders_regression", "ondemand_orders_smoke"})
    public void orderNumberByShipmentNumberFilterTest() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        orders().goToPage();
        orders().checkShipmentListNotEmpty();
        orders().checkOrdersLoaded();
        orders().checkLoadingLabelNotVisible();
        var shipmentNumber = orders().getAnyShipmentNumber();

        //TODO На кракене есть 1 заказ без 'H' в номере, шаг кейса с поиском по 'H' не проходит проверка по кол-ву. Сравнить с другими стейджами
        //var allOrdersCount = orders().getShipmentsCountFromTableHeader();
        orders().fillShipmentNumber(shipmentNumber);
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();
        orders().checkShipmentsCountInTableHeader(1);
        orders().checkItemsCountInTable(1);
        orders().checkAllShipmentNumbersContains(shipmentNumber);

        orders().resetFilters();
        orders().checkLoadingLabelNotVisible();

        orders().fillShipmentNumber(shipmentNumber.substring(0, 5));
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();
        orders().checkAllShipmentNumbersContains(shipmentNumber.substring(0, 5));

        orders().resetFilters();
        orders().checkLoadingLabelNotVisible();

        orders().fillShipmentNumber(shipmentNumber.substring(0, 1));
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();
        orders().checkAllShipmentNumbersContains(shipmentNumber.substring(0, 1));
        //TODO На кракене есть 1 заказ без 'H' в номере, шаг кейса с поиском по 'H' не проходит проверка по кол-ву. Сравнить с другими стейджами
        //orders().checkShipmentsCountInTableHeader(allOrdersCount);
        //orders().checkItemsCountInTable(allOrdersCount);

        orders().resetFilters();
        orders().checkLoadingLabelNotVisible();

        orders().fillShipmentNumber(shipmentNumber.substring(1));
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();
        orders().checkShipmentsCountInTableHeader(1);
        orders().checkItemsCountInTable(1);

        orders().resetFilters();
        orders().checkLoadingLabelNotVisible();

        orders().fillShipmentNumber(Generate.literalString(5));
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();
        orders().checkShipmentListEmpty();
        orders().checkShipmentsCountInTableHeader(0);

        orders().resetFilters();
        orders().checkLoadingLabelNotVisible();

        orders().fillShipmentNumber(shipmentNumber.substring(2));
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();
        orders().checkShipmentListEmpty();
        orders().checkShipmentsCountInTableHeader(0);

        orders().resetFilters();
        orders().checkLoadingLabelNotVisible();
    }

    @CaseId(2071)
    @Test(description = "Фильтр Статус заказа - выпадающий список с множественным выбором",
            groups = {"regression", "ondemand_orders_regression", "ondemand_orders_smoke"})
    public void orderStatusFilterTest() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        orders().goToPage();
        orders().checkShipmentListNotEmpty();
        orders().checkOrdersLoaded();
        orders().checkLoadingLabelNotVisible();

        orders().addStatusFilterItem(SHIPMENT_PENDING.getName());
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();
        orders().checkAllShipmentInTableHasSingleStatusIn(List.of(SHIPMENT_PENDING.getName()));

        orders().addStatusFilterItem(SHIPMENT_READY.getName());
        orders().addStatusFilterItem(DISPATCH_NEW.getName());
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();
        orders().checkAllShipmentInTableHasSingleStatusIn(Arrays.asList(SHIPMENT_PENDING.getName(), SHIPMENT_READY.getName(), DISPATCH_NEW.getName()));

        orders().removeShipmentStatusFilterItem(SHIPMENT_READY.getName());
        orders().checkShipmentStatusSelectedFilterList(Arrays.asList(SHIPMENT_PENDING.getName(), DISPATCH_NEW.getName()));
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();
        orders().checkAllShipmentInTableHasSingleStatusIn(Arrays.asList(SHIPMENT_PENDING.getName(), DISPATCH_NEW.getName()));

        orders().clearShipmentStatusFilters();
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();
    }

    @CaseId(1523)
    @Test(description = "Фильтр Платформа -выпадающий список с множественным выбором",
            groups = {"regression", "ondemand_orders_regression"})
    public void platformFilterTest() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        orders().goToPage();
        orders().checkShipmentListNotEmpty();
        orders().checkOrdersLoaded();
        orders().checkLoadingLabelNotVisible();

        orders().addPlatformFilterItem("SbermarketAndroid");
        orders().addPlatformFilterItem("InstamartApp");
        orders().addPlatformFilterItem("MetroWeb");
        orders().checkPlatformSelectedFilterList(Arrays.asList("SbermarketAndroid", "InstamartApp", "MetroWeb"));
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();

        orders().checkAllShipmentInTableHasPlatformIn(Arrays.asList("SbermarketAndroid", "InstamartApp", "MetroWeb"));
        orders().removePlatformFilterItem("SbermarketAndroid");

        orders().checkPlatformSelectedFilterList(Arrays.asList("InstamartApp", "MetroWeb"));
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();

        orders().checkAllShipmentInTableHasPlatformIn(Arrays.asList("InstamartApp", "MetroWeb"));
        orders().clearPlatformFilters();
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();
    }

    @CaseId(1524)
    @Test(description = "Фильтр Ритейлер - выпадающий список с множественным выбором",
            groups = {"regression", "ondemand_orders_regression"})
    public void retailerFilterTest() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        orders().goToPage();
        orders().checkShipmentListNotEmpty();
        orders().checkOrdersLoaded();
        orders().checkLoadingLabelNotVisible();

        orders().addRetailerFilterItem("METRO");
        orders().addRetailerFilterItem("Ашан");
        orders().addRetailerFilterItem("Лента");
        orders().checkRetailerSelectedFilterList(Arrays.asList("METRO", "Ашан", "Лента"));
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();

        orders().checkAllShipmentInTableHasRetailerIn(Arrays.asList("METRO", "Ашан", "Лента"));
        orders().removeRetailerFilterItem("Лента");

        orders().checkRetailerSelectedFilterList(Arrays.asList("METRO", "Ашан"));
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();

        orders().checkAllShipmentInTableHasRetailerIn(Arrays.asList("METRO", "Ашан"));
        orders().clearRetailerFilters();
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();
    }

    @CaseId(2044)
    @Test(description = "Фильтр Базовый магазин - выпадающий список с множественным выбором",
            groups = {"regression", "ondemand_orders_regression", "ondemand_orders_smoke"})
    public void basicStoreFilterTest() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        orders().goToPage();
        orders().checkShipmentListNotEmpty();
        orders().checkOrdersLoaded();
        orders().checkLoadingLabelNotVisible();

        orders().addBasicStoreFilterItem("Ашан, Севастопольский просп.");
        orders().addBasicStoreFilterItem("METRO, Дмитровское ш");
        //TODO В списке базовых магазинов мало-мало записей, возможно, узкое место при прогоне на стейджах
        orders().checkBasicStoreSelectedFilterList(Arrays.asList("Ашан, Севастопольский просп.", "METRO, Дмитровское ш"));
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();

        orders().checkAllShipmentInTableHasBasicStoreIn(Arrays.asList("Ашан, Севастопольский просп.", "METRO, Дмитровское ш"));
        orders().removeBasicStoreFilterItem("METRO, Дмитровское ш");

        orders().checkBasicStoreSelectedFilterList(List.of("Ашан, Севастопольский просп."));
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();

        orders().checkAllShipmentInTableHasBasicStoreIn(List.of("Ашан, Севастопольский просп."));
        orders().clearBasicStoreFilters();
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();
    }

    @CaseId(1526)
    @Test(description = "Фильтр Магазин -выпадающий список с множественным выбором",
            groups = {"regression", "ondemand_orders_regression", "ondemand_orders_smoke"})
    public void storeFilterTest() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        orders().goToPage();
        orders().checkShipmentListNotEmpty();
        orders().checkOrdersLoaded();
        orders().checkLoadingLabelNotVisible();

        orders().addStoreFilterItem("Севастопольский просп");
        orders().addStoreFilterItem("Щелковская");
        orders().addStoreFilterItem("Дмитровское");
        orders().checkStoreSelectedFilterList(Arrays.asList("Ашан, Москва, Севастопольский просп., 11Е", "METRO, Москва, Щелковская, 6", "METRO, Москва, Дмитровское ш, 165Б"));
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();

        orders().removeStoreFilterItem("METRO, Москва, Щелковская, 6");

        orders().checkStoreSelectedFilterList(Arrays.asList("Ашан, Москва, Севастопольский просп., 11Е", "METRO, Москва, Дмитровское ш, 165Б"));
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();

        orders().clearStoreFilters();
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();
    }

    @CaseId(1527)
    @Test(description = "Фильтр Способ оплаты - список с множественным выбором",
            groups = {"regression", "ondemand_orders_regression"})
    public void paymentMethodsFilterTest() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        orders().goToPage();
        orders().checkShipmentListNotEmpty();
        orders().checkOrdersLoaded();
        orders().checkLoadingLabelNotVisible();

        orders().addPaymentMethodFilterItem(BY_CASH.getName());
        orders().addPaymentMethodFilterItem(BY_CARD_TO_COURIER.getName());
        orders().addPaymentMethodFilterItem(AT_CHECKOUT.getName());
        orders().checkPaymentMethodSelectedFilterList(Arrays.asList(BY_CASH.getName(), BY_CARD_TO_COURIER.getName(), AT_CHECKOUT.getName()));
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();

        orders().checkAllShipmentInTableHasPaymentMethodIn(Arrays.asList(BY_CASH.getName(), BY_CARD_TO_COURIER.getName(), AT_CHECKOUT.getName()));
        orders().removePaymentMethodFilterItem(BY_CASH.getName());

        orders().checkPaymentMethodSelectedFilterList(Arrays.asList(BY_CARD_TO_COURIER.getName(), AT_CHECKOUT.getName()));
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();

        orders().checkAllShipmentInTableHasPaymentMethodIn(Arrays.asList(BY_CARD_TO_COURIER.getName(), AT_CHECKOUT.getName()));
        orders().clearPaymentMethodFilters();
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();
    }

    @CaseId(2073)
    @Test(description = "Фильтр Статус оплаты - список с множественным выбором",
            groups = {"regression", "ondemand_orders_regression"})
    public void paymentStatusFilterTest() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        orders().goToPage();
        orders().checkShipmentListNotEmpty();
        orders().checkOrdersLoaded();
        orders().checkLoadingLabelNotVisible();

        orders().addPaymentStatusFilterItem(NOT_PAID.getName().toLowerCase());
        orders().checkPaymentStatusSelectedFilterList(List.of(NOT_PAID.getName().toLowerCase()));
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();

        orders().checkAllShipmentInTableHasPaymentStatusIn(List.of(NOT_PAID.getName()));

        orders().addPaymentStatusFilterItem(BALANCE_DUE.getName().toLowerCase());
        orders().checkPaymentStatusSelectedFilterList(Arrays.asList(NOT_PAID.getName().toLowerCase(), BALANCE_DUE.getName().toLowerCase()));
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();

        orders().checkAllShipmentInTableHasPaymentStatusIn(Arrays.asList(NOT_PAID.getName(), BALANCE_DUE.getName()));

        orders().addPaymentStatusFilterItem(PAID.getName().toLowerCase());
        orders().addPaymentStatusFilterItem(OVERPAID.getName().toLowerCase());
        orders().addPaymentStatusFilterItem(FAILED.getName().toLowerCase());

        orders().checkPaymentStatusSelectedFilterList(Arrays.asList(
                NOT_PAID.getName().toLowerCase(),
                BALANCE_DUE.getName().toLowerCase(),
                PAID.getName().toLowerCase(),
                OVERPAID.getName().toLowerCase(),
                FAILED.getName().toLowerCase()));
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();

        orders().checkAllShipmentInTableHasPaymentStatusIn(Arrays.asList(
                NOT_PAID.getName(),
                BALANCE_DUE.getName(),
                PAID.getName(),
                OVERPAID.getName(),
                FAILED.getName()));
        orders().removePaymentStatusFilterItem(NOT_PAID.getName().toLowerCase());

        orders().checkPaymentStatusSelectedFilterList(Arrays.asList(
                BALANCE_DUE.getName().toLowerCase(),
                PAID.getName().toLowerCase(),
                OVERPAID.getName().toLowerCase(),
                FAILED.getName().toLowerCase()));
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();

        orders().checkAllShipmentInTableHasPaymentStatusIn(Arrays.asList(
                BALANCE_DUE.getName(),
                PAID.getName(),
                OVERPAID.getName(),
                FAILED.getName()));

        orders().addPaymentStatusFilterItem(NOT_PAID.getName().toLowerCase());
        orders().checkPaymentStatusSelectedFilterList(Arrays.asList(
                BALANCE_DUE.getName().toLowerCase(),
                PAID.getName().toLowerCase(),
                OVERPAID.getName().toLowerCase(),
                FAILED.getName().toLowerCase(),
                NOT_PAID.getName().toLowerCase()));

        orders().clearPaymentStatusFilters();
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();
    }

    //TODO SA-1970 Фильтр 'Статус сборки', SA-1971 Фильтр 'Статус доставки' не реализованы. Сроков нет

    @CaseId(2063)
    @Test(description = "Быстрый фильтр: Без назначений",
            groups = {"regression", "ondemand_orders_regression"})
    public void quickFiltersNotAssignedTest() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        orders().goToPage();
        orders().checkShipmentListNotEmpty();
        orders().checkOrdersLoaded();
        orders().checkLoadingLabelNotVisible();

        orders().addQuickFilterItem(NOT_ASSIGNED.getName());
        orders().checkQuickFiltersSelectedFilterList(Collections.singletonList(NOT_ASSIGNED.getName()));

        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();

        orders().checkAllShipmentInTableHasCollectorIn(Collections.singletonList("Нет исполнителя"));
        orders().checkAllShipmentInTableHasCourierIn(Collections.singletonList("Нет исполнителя"));
    }

    @CaseId(2064)
    @Test(description = "Быстрый фильтр: Неоплаченные",
            groups = {"regression", "ondemand_orders_regression"})
    public void quickFiltersNotPaidTest() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        orders().goToPage();
        orders().checkShipmentListNotEmpty();
        orders().checkOrdersLoaded();
        orders().checkLoadingLabelNotVisible();

        orders().addQuickFilterItem(UNPAID.getName());
        orders().checkQuickFiltersSelectedFilterList(Collections.singletonList(UNPAID.getName()));

        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();

        orders().checkAllShipmentInTableHasPaymentStatusIn(Collections.singletonList(NOT_PAID.getName()));
    }

    @CaseId(2066)
    @Test(description = "Быстрый фильтр: Завершенные",
            groups = {"regression", "ondemand_orders_regression"})
    public void quickFiltersCompletedTest() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        orders().goToPage();
        orders().checkShipmentListNotEmpty();
        orders().checkOrdersLoaded();
        orders().checkLoadingLabelNotVisible();

        orders().addQuickFilterItem(COMPLETED.getName());
        orders().checkQuickFiltersSelectedFilterList(Collections.singletonList(COMPLETED.getName()));

        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();

        orders().checkAllShipmentInTableHasSingleStatusIn(Arrays.asList(SHIPMENT_SHIPPED.getName(), SHIPMENT_CANCELED.getName(), CLIENT_CANCELLED.getName()));
        orders().checkAllShipmentInTableHasCollectingStatusIn(Arrays.asList(SHIPMENT_READY_TO_SHIP.getName(), CLIENT_CANCELLED.getName()));
        orders().checkAllShipmentInTableHasShippingStatusIn(Arrays.asList(SHIPMENT_SHIPPED.getName(), CLIENT_CANCELLED.getName()));
    }

    @CaseId(2067)
    @Test(description = "Быстрый фильтр: Незавершенные",
            groups = {"regression", "ondemand_orders_regression"})
    public void quickFiltersNotCompletedTest() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        orders().goToPage();
        orders().checkShipmentListNotEmpty();
        orders().checkOrdersLoaded();
        orders().checkLoadingLabelNotVisible();

        orders().addQuickFilterItem(NOT_COMPLETED.getName());
        orders().checkQuickFiltersSelectedFilterList(Collections.singletonList(NOT_COMPLETED.getName()));
        orders().checkShipmentStatusSelectedFilterList(Arrays.asList(
                SHIPMENT_READY.getName(),
                SHIPMENT_COLLECTING.getName(),
                SHIPMENT_READY_TO_SHIP.getName(),
                SHIPMENT_SHIPPING.getName()));

        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();

        orders().checkAllShipmentInTableHasSingleStatusIn(Collections.singletonList(SHIPMENT_READY.getName()));
        orders().checkAllShipmentInTableHasCollectingStatusIn(Arrays.asList(SHIPMENT_COLLECTING.getName(), SHIPMENT_READY_TO_SHIP.getName()));
        orders().checkAllShipmentInTableHasShippingStatusIn(Collections.singletonList(SHIPMENT_SHIPPING.getName()));
    }

    @CaseId(2068)
    @Test(description = "Быстрый фильтр: B2B клиенты",
            groups = {"regression", "ondemand_orders_regression"})
    public void quickFiltersB2BTest() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        orders().goToPage();
        orders().checkShipmentListNotEmpty();
        orders().checkOrdersLoaded();
        orders().checkLoadingLabelNotVisible();

        orders().addQuickFilterItem(B2B_CLIENTS.getName());
        orders().checkQuickFiltersSelectedFilterList(Collections.singletonList(B2B_CLIENTS.getName()));

        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();

        //TODO В заказе никак не обозначенo что он B2B. Признак B2B можно проверить только у пользовател, сделавшего заказ в профиле
        //Для stf-6 основные B2B-клиенты от которых сыпятся заказы - Ivan Petrov, Pavel Nep.
        orders().checkAllShipmentInTableHasCustomerIn(Arrays.asList("Ivan Petrov", "Pavel Nep"));
    }

    @CaseId(2070)
    @Test(description = "Быстрые фильтры - множественный выбор",
            groups = {"regression", "ondemand_orders_regression"})
    public void quickFiltersComplexUsingTest() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        orders().goToPage();
        orders().checkShipmentListNotEmpty();
        orders().checkOrdersLoaded();
        orders().checkLoadingLabelNotVisible();

        orders().addQuickFilterItem(UNPAID.getName());
        orders().addQuickFilterItem(COMPLETED.getName());
        orders().checkQuickFiltersSelectedFilterList(Arrays.asList(UNPAID.getName(), COMPLETED.getName()));

        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();

        orders().checkAllShipmentInTableHasSingleStatusIn(Arrays.asList(UNPAID.getName(), SHIPMENT_SHIPPED.getName(), SHIPMENT_CANCELED.getName(), CLIENT_CANCELLED.getName()));
        orders().checkAllShipmentInTableHasShippingStatusIn(Arrays.asList(SHIPMENT_SHIPPED.getName(), CLIENT_CANCELLED.getName()));

        orders().removeQuickFilterItem(UNPAID.getName());
        orders().addQuickFilterItem(NOT_COMPLETED.getName());

        orders().checkQuickFiltersSelectedFilterList(Collections.singletonList(NOT_COMPLETED.getName()));
        orders().checkShipmentStatusFiltersNotSelected();

        orders().addQuickFilterItem(UNPAID.getName());
        orders().addQuickFilterItem(NOT_ASSIGNED.getName());
        orders().addQuickFilterItem(LONG_DISPATCH.getName());
        orders().addQuickFilterItem(QUICK_DELIVERY.getName());
        orders().addQuickFilterItem(B2B_CLIENTS.getName());
        orders().addQuickFilterItem(DELIVERY_TIME_CHANGED.getName());

        orders().checkQuickFiltersSelectedFilterList(Arrays.asList(
                NOT_COMPLETED.getName(),
                UNPAID.getName(),
                NOT_ASSIGNED.getName(),
                LONG_DISPATCH.getName(),
                QUICK_DELIVERY.getName(),
                B2B_CLIENTS.getName(),
                DELIVERY_TIME_CHANGED.getName()
        ));

        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();
        orders().checkOrdersLoaded();
    }
}
