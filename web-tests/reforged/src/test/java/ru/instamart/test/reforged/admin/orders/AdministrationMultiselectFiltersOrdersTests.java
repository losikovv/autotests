package ru.instamart.test.reforged.admin.orders;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.api.model.shopper.app.ShipmentSHP;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.Arrays;
import java.util.List;

import static ru.instamart.reforged.admin.AdminRout.login;
import static ru.instamart.reforged.admin.AdminRout.orders;
import static ru.instamart.reforged.admin.enums.PaymentMethods.*;
import static ru.instamart.reforged.admin.enums.PaymentStatuses.*;
import static ru.instamart.reforged.admin.enums.ShipmentStatuses.*;

@Epic("Админка STF")
@Feature("Заказы")
@Story("Страница 'Список заказов' admin/spa/orders. Фильтры с множественным выбором")
public final class AdministrationMultiselectFiltersOrdersTests {

    private final ApiHelper helper = new ApiHelper();
    private ShipmentSHP.Data shipment;

    /*@BeforeClass(alwaysRun = true, description = "Получаем оформленный заказ из подготовленных ранее")
    public void getOrder() {
        shipment = helper.getShipmentByComment("UI-TEST-SINGLE");
        Assert.assertNotNull(shipment, "Не удалось получить заказ");
    }*/

    @CaseId(1517)
    @Test(description = "Фильтр Номер заказа выдает список заказов по номеру заказа и по номеру шипмента", groups = "regression")
    public void orderNumberByShipmentNumberFilterTest() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        orders().goToPage();
        orders().checkShipmentListNotEmpty();
        orders().checkOrdersLoaded();
        orders().checkLoadingLabelNotVisible();

        //TODO На кракене есть 1 заказ без 'H' в номере, шаг кейса с поиском по 'H' не проходит проверка по кол-ву. Сравнить с другими стейджами
        //var allOrdersCount = orders().getShipmentsCountFromTableHeader();
        orders().fillShipmentNumber(shipment.getAttributes().getNumber());
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();
        orders().checkShipmentsCountInTableHeader(1);
        orders().checkItemsCountInTable(1);
        orders().checkAllShipmentNumbersContains(shipment.getAttributes().getNumber());

        orders().resetFilters();
        orders().checkLoadingLabelNotVisible();


        orders().fillShipmentNumber(shipment.getAttributes().getNumber().substring(0, 5));
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();
        orders().checkAllShipmentNumbersContains(shipment.getAttributes().getNumber().substring(0, 5));

        orders().resetFilters();
        orders().checkLoadingLabelNotVisible();

        orders().fillShipmentNumber(shipment.getAttributes().getNumber().substring(0, 1));
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();
        orders().checkAllShipmentNumbersContains(shipment.getAttributes().getNumber().substring(0, 1));
        //TODO На кракене есть 1 заказ без 'H' в номере, шаг кейса с поиском по 'H' не проходит проверка по кол-ву. Сравнить с другими стейджами
        //orders().checkShipmentsCountInTableHeader(allOrdersCount);
        //orders().checkItemsCountInTable(allOrdersCount);

        orders().resetFilters();
        orders().checkLoadingLabelNotVisible();

        orders().fillShipmentNumber(shipment.getAttributes().getNumber().substring(1));
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

        orders().fillShipmentNumber(shipment.getAttributes().getNumber().substring(2));
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();
        orders().checkShipmentListEmpty();
        orders().checkShipmentsCountInTableHeader(0);

        orders().resetFilters();
        orders().checkLoadingLabelNotVisible();
    }

    @CaseId(2071)
    @Test(description = "Фильтр Статус заказа - выпадающий список с множественным выбором", groups = "regression")
    public void orderStatusFilterTest() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        orders().goToPage();
        orders().checkShipmentListNotEmpty();
        orders().checkOrdersLoaded();
        orders().checkLoadingLabelNotVisible();

        orders().clickShipmentStatusFilterSelector();
        orders().checkShipmentStatusDropdownItemsVisible();
        orders().checkShipmentStatusSelectorListEquals(Arrays.asList(
                SHIPMENT_PENDING.getName(),
                SHIPMENT_READY.getName(),
                DISPATCH_NEW.getName(),
                DISPATCH_POSTPONED.getName(),
                DISPATCH_AUTOMATIC_ROUTING.getName(),
                DISPATCH_MANUAL_ROUTING.getName(),
                DISPATCH_OFFERING.getName(),
                DISPATCH_OFFERED.getName(),
                DISPATCH_DECLINED.getName(),
                DISPATCH_REDISPATCH.getName(),
                DISPATCH_CANCELED.getName(),
                DISPATCH_SHIPPED.getName(),
                SHIPMENT_COLLECTING.getName(),
                SHIPMENT_READY_TO_SHIP.getName(),
                SHIPMENT_SHIPPING.getName(),
                SHIPMENT_CANCELED.getName()));

        orders().addStatusFilterItem(SHIPMENT_PENDING.getName());
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();
        orders().checkAllShipmentInTableHasStatusIn(List.of(SHIPMENT_PENDING.getName()));

        orders().clickShipmentStatusFilterSelector();
        orders().checkShipmentStatusDropdownItemsVisible();
        orders().addStatusFilterItem(SHIPMENT_READY.getName());
        orders().addStatusFilterItem(DISPATCH_NEW.getName());
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();
        orders().checkAllShipmentInTableHasStatusIn(Arrays.asList(SHIPMENT_PENDING.getName(), SHIPMENT_READY.getName(), DISPATCH_NEW.getName()));

        orders().removeShipmentStatusFilterItem(SHIPMENT_READY.getName());
        orders().checkShipmentStatusSelectedFilterList(Arrays.asList(SHIPMENT_PENDING.getName(), DISPATCH_NEW.getName()));
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();
        orders().checkAllShipmentInTableHasStatusIn(Arrays.asList(SHIPMENT_PENDING.getName(), DISPATCH_NEW.getName()));

        orders().clearShipmentStatusFilters();
        orders().checkShipmentStatusFiltersNotSelected();
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();
    }

    @CaseId(1523)
    @Test(description = "Фильтр Платформа -выпадающий список с множественным выбором", groups = "regression")
    public void platformFilterTest() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        orders().goToPage();
        orders().checkShipmentListNotEmpty();
        orders().checkOrdersLoaded();
        orders().checkLoadingLabelNotVisible();

        orders().clickPlatformFilterSelector();
        orders().checkPlatformDropdownItemsVisible();
        orders().addPlatformFilterItem("sber_devices");
        orders().addPlatformFilterItem("auchan");
        orders().addPlatformFilterItem("metro_marketplace");
        orders().checkPlatformSelectedFilterList(Arrays.asList("sber_devices", "auchan", "metro_marketplace"));
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();

        orders().checkAllShipmentInTableHasPlatformIn(Arrays.asList("sber_devices", "auchan", "metro_marketplace"));
        orders().removePlatformFilterItem("metro_marketplace");

        orders().checkPlatformSelectedFilterList(Arrays.asList("sber_devices", "auchan"));
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();

        orders().checkAllShipmentInTableHasPlatformIn(Arrays.asList("sber_devices", "auchan"));
        orders().clearPlatformFilters();
        orders().checkPlatformFiltersNotSelected();
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();
    }

    @CaseId(1524)
    @Test(description = "Фильтр Ритейлер - выпадающий список с множественным выбором", groups = "regression")
    public void retailerFilterTest() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        orders().goToPage();
        orders().checkShipmentListNotEmpty();
        orders().checkOrdersLoaded();
        orders().checkLoadingLabelNotVisible();

        orders().clickRetailerFilterSelector();
        orders().checkRetailerDropdownItemsVisible();
        orders().addRetailerFilterItem("METRO");
        orders().addRetailerFilterItem("Ашан");
        orders().addRetailerFilterItem("SELGROS");
        orders().checkRetailerSelectedFilterList(Arrays.asList("METRO", "Ашан", "SELGROS"));
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();

        orders().checkAllShipmentInTableHasRetailerIn(Arrays.asList("METRO", "Ашан", "SELGROS"));
        orders().removeRetailerFilterItem("SELGROS");

        orders().checkRetailerSelectedFilterList(Arrays.asList("METRO", "Ашан"));
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();

        orders().checkAllShipmentInTableHasRetailerIn(Arrays.asList("METRO", "Ашан"));
        orders().clearRetailerFilters();
        orders().checkRetailerFiltersNotSelected();
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();
    }

    @CaseId(2044)
    @Test(description = "Фильтр Базовый магазин - выпадающий список с множественным выбором", groups = "regression")
    public void basicStoreFilterTest() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        orders().goToPage();
        orders().checkShipmentListNotEmpty();
        orders().checkOrdersLoaded();
        orders().checkLoadingLabelNotVisible();

        orders().clickBasicStoreFilterSelector();
        orders().checkBasicStoreDropdownItemsVisible();
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
        orders().checkBasicStoreFiltersNotSelected();
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();
    }

    @CaseId(1526)
    @Test(description = "Фильтр Магазин -выпадающий список с множественным выбором", groups = "regression")
    public void storeFilterTest() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        orders().goToPage();
        orders().checkShipmentListNotEmpty();
        orders().checkOrdersLoaded();
        orders().checkLoadingLabelNotVisible();

        orders().clickStoreFilterSelector();
        orders().checkStoreDropdownItemsVisible();
        orders().addStoreFilterItem("Севастопольский просп");
        orders().addStoreFilterItem("Щелковская");
        orders().addStoreFilterItem("Обучение");
        orders().checkStoreSelectedFilterList(Arrays.asList("Ашан, Москва, Севастопольский просп., 11Е", "METRO, Москва, Щелковская, 6", "METRO, Москва, Обучение, 1"));
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();

        orders().removeStoreFilterItem("METRO, Москва, Щелковская, 6");

        orders().checkStoreSelectedFilterList(Arrays.asList("Ашан, Москва, Севастопольский просп., 11Е", "METRO, Москва, Обучение, 1"));
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();

        orders().clearStoreFilters();
        orders().checkStoreFiltersNotSelected();
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();
    }

    @CaseId(1527)
    @Test(description = "Фильтр Способ оплаты - список с множественным выбором", groups = "regression")
    public void paymentMethodsFilterTest() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        orders().goToPage();
        orders().checkShipmentListNotEmpty();
        orders().checkOrdersLoaded();
        orders().checkLoadingLabelNotVisible();

        orders().clickPaymentMethodFilterSelector();
        orders().checkPaymentMethodDropdownItemsVisible();
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
        orders().checkPaymentMethodFiltersNotSelected();
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();
    }

    @CaseId(2073)
    @Test(description = "Фильтр Статус оплаты - список с множественным выбором", groups = "regression")
    public void paymentStatusFilterTest() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        orders().goToPage();
        orders().waitPageLoad();
        orders().checkShipmentListNotEmpty();
        orders().checkOrdersLoaded();
        orders().checkLoadingLabelNotVisible();

        orders().clickPaymentStatusFilterSelector();
        orders().checkPaymentStatusDropdownItemsVisible();
        orders().checkPaymentStatusSelectorListEquals(Arrays.asList(
                PAID.getName().toLowerCase(),
                NOT_PAID.getName().toLowerCase(),
                BALANCE_DUE.getName().toLowerCase(),
                OVERPAID.getName().toLowerCase(),
                FAILED.getName().toLowerCase()
        ));

        orders().addPaymentStatusFilterItem(NOT_PAID.getName().toLowerCase());
        orders().checkPaymentStatusSelectedFilterList(List.of(NOT_PAID.getName().toLowerCase()));
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();

        orders().checkAllShipmentInTableHasPaymentStatusIn(List.of(NOT_PAID.getName()));

        orders().clickPaymentStatusFilterSelector();
        orders().addPaymentStatusFilterItem(BALANCE_DUE.getName().toLowerCase());
        orders().checkPaymentStatusSelectedFilterList(Arrays.asList(NOT_PAID.getName().toLowerCase(), BALANCE_DUE.getName().toLowerCase()));
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();

        orders().checkAllShipmentInTableHasPaymentStatusIn(Arrays.asList(NOT_PAID.getName(), BALANCE_DUE.getName()));

        orders().clickPaymentStatusFilterSelector();
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

        orders().clickPaymentStatusFilterSelector();
        orders().addPaymentStatusFilterItem(NOT_PAID.getName().toLowerCase());
        orders().checkPaymentStatusSelectedFilterList(Arrays.asList(
                NOT_PAID.getName().toLowerCase(),
                PAID.getName().toLowerCase(),
                OVERPAID.getName().toLowerCase(),
                FAILED.getName().toLowerCase(),
                NOT_PAID.getName().toLowerCase()));

        orders().clearPaymentStatusFilters();
        orders().checkPaymentStatusFiltersNotSelected();
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();
    }

    //TODO SA-1970 Фильтр 'Статус сборки', SA-1971 Фильтр 'Статус доставки' не реализованы. Сроков нет
}
