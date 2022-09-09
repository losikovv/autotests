package ru.instamart.test.reforged.admin.orders;

import io.qameta.allure.*;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.enums.Server;
import ru.instamart.kraken.listener.Skip;
import ru.instamart.reforged.core.config.UiProperties;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.List;
import java.util.Set;

import static ru.instamart.reforged.admin.AdminRout.*;
import static ru.instamart.reforged.admin.enums.CollectingStatus.*;
import static ru.instamart.reforged.admin.enums.PaymentMethods.*;
import static ru.instamart.reforged.admin.enums.PaymentStatuses.*;
import static ru.instamart.reforged.admin.enums.QuickFilters.*;
import static ru.instamart.reforged.admin.enums.ShipmentStatuses.*;

@Epic("Админка STF")
@Feature("Заказы")
@Story("Страница 'Список заказов' admin/spa/orders. Фильтры с множественным выбором")
public final class AdministrationMultiselectFiltersOrdersTests {

    private final ApiHelper helper = new ApiHelper();

    @CaseId(2116)
    @Test(description = "Поиск заказа по фильтру Номер заказа",
            groups = {"ondemand_orders_regression", "ondemand_orders_smoke", "admin_ondemand_smoke", "admin_ondemand_regression"})
    public void orderNumberByShipmentNumberFilterTest() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        orders().goToPage();
        orders().checkShipmentListNotEmpty();
        orders().checkOrdersLoaded();
        orders().checkLoadingLabelNotVisible();
        var shipmentNumber = orders().getAnyShipmentNumber();

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

    @Skip
    //TODO: сделать датапикер
    @CaseId(2117)
    @Test(description = "Поиск заказа по дате создания заказа, фильтр Создание заказа",
            groups = {"ondemand_orders_regression", "ondemand_orders_smoke", "admin_ondemand_smoke", "admin_ondemand_regression"})
    public void orderFilterByCreateDate() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        orders().goToPage();
        orders().checkShipmentListNotEmpty();
        orders().checkOrdersLoaded();
        orders().checkLoadingLabelNotVisible();


    }

    @Skip
    //TODO: сделать датапикер
    @CaseId(2118)
    @Test(description = "Поиск заказа по дате доставки, фильтр Доставка заказа",
            groups = {"ondemand_orders_regression", "ondemand_orders_smoke", "admin_ondemand_smoke", "admin_ondemand_regression"})
    public void orderFilterByDeliveryDate() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        orders().goToPage();
        orders().checkShipmentListNotEmpty();
        orders().checkOrdersLoaded();
        orders().checkLoadingLabelNotVisible();


    }

    @CaseId(2119)
    @Test(description = "Поиск заказов по весу, фильтр Вес заказа",
            groups = {"ondemand_orders_regression", "ondemand_orders_smoke", "admin_ondemand_smoke", "admin_ondemand_regression"})
    public void orderFilterByWeight() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        orders().goToPage();
        orders().checkOrdersLoaded();
        orders().checkLoadingLabelNotVisible();
        orders().checkShipmentListNotEmpty();

        orders().addOrderWeightFrom("пппп");
        orders().addOrderWeightTo("ffff");
        orders().applyFilters();
        orders().checkAlertErrorWeightFromVisible();
        orders().checkAlertErrorWeightToVisible();

        orders().addOrderWeightFrom("1,5");
        orders().addOrderWeightTo("2,6");
        orders().applyFilters();
        orders().checkAlertErrorWeightFromVisible();
        orders().checkAlertErrorWeightToVisible();

        orders().addOrderWeightFrom(" ");
        orders().addOrderWeightTo(" ");
        orders().applyFilters();
        orders().checkAlertErrorWeightFromVisible();
        orders().checkAlertErrorWeightToVisible();

        orders().addOrderWeightFrom("2");
        orders().addOrderWeightTo("4");

        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();

        orders().checkAllShipmentInTableBetweenWeight(2.0, 4.0);

        orders().addOrderWeightFrom("1.2");
        orders().addOrderWeightTo("10.5");

        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();

        orders().checkAllShipmentInTableBetweenWeight(1.2, 10.5);
    }

    @CaseId(2120)
    @Test(description = "Поиск заказов по фильтру Платформа",
            groups = {"ondemand_orders_regression", "ondemand_orders_smoke", "admin_ondemand_smoke", "admin_ondemand_regression"})
    public void platformFilterTest() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        orders().goToPage();
        orders().checkShipmentListNotEmpty();
        orders().checkOrdersLoaded();
        orders().checkLoadingLabelNotVisible();

        orders().addPlatformFilterItem("MetroWeb");
        orders().checkPlatformSelectedFilterList(List.of("MetroWeb"));
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();
        orders().checkAllShipmentInTableHasPlatformIn(Set.of("MetroWeb"));

        orders().addPlatformFilterItem("SbermarketAndroid");
        orders().checkPlatformSelectedFilterList(List.of("MetroWeb", "SbermarketAndroid"));
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();
        orders().checkAllShipmentInTableHasPlatformIn(Set.of("SbermarketAndroid", "MetroWeb"));

        orders().removePlatformFilterItem("MetroWeb");
        orders().checkPlatformSelectedFilterList(List.of("SbermarketAndroid"));

        orders().clearPlatformFilters();
        orders().checkPlatformFiltersNotSelected();
    }

    @CaseId(2121)
    @Test(description = "Фильтрация заказов по ритейлеру",
            groups = {"ondemand_orders_regression", "ondemand_orders_smoke", "admin_ondemand_smoke", "admin_ondemand_regression"})
    public void retailerFilterTest() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        orders().goToPage();
        orders().checkShipmentListNotEmpty();
        orders().checkOrdersLoaded();
        orders().checkLoadingLabelNotVisible();

        orders().addRetailerFilterItem("Лен");
        orders().checkRetailerSelectedFilterList(List.of("Лента"));
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();
        orders().checkAllShipmentInTableHasRetailerIn(Set.of("Лента"));

        orders().addRetailerFilterItem("METRO");
        orders().checkRetailerSelectedFilterList(List.of("Лента", "METRO"));
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();
        orders().checkAllShipmentInTableHasRetailerIn(Set.of("Лента", "METRO"));

        orders().removeRetailerFilterItem("Лента");
        orders().checkRetailerSelectedFilterList(List.of("METRO"));

        orders().clearRetailerFilters();
        orders().checkRetailerFiltersNotSelected();
    }

    @CaseId(2143)
    @Test(description = "Фильтрация заказов по базовому магазину",
            groups = {"ondemand_orders_regression", "ondemand_orders_smoke", "admin_ondemand_smoke", "admin_ondemand_regression"})
    public void basicStoreFilterTest() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        orders().goToPage();
        orders().checkShipmentListNotEmpty();
        orders().checkOrdersLoaded();
        orders().checkLoadingLabelNotVisible();

        orders().addBasicStoreFilterItem("Ашан, Севастопольский просп.", "METRO, Дмитровское ш");
        //TODO В списке базовых магазинов мало-мало записей, возможно, узкое место при прогоне на стейджах
        orders().checkBasicStoreSelectedFilterList(List.of("Ашан, Севастопольский просп.", "METRO, Дмитровское ш"));
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();
        orders().checkAllShipmentInTableHasBasicStoreIn(Set.of("Ашан, Севастопольский просп.", "METRO, Дмитровское ш"));

        orders().removeBasicStoreFilterItem("METRO, Дмитровское ш");
        orders().checkBasicStoreSelectedFilterList(List.of("Ашан, Севастопольский просп."));
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();

        orders().checkAllShipmentInTableHasBasicStoreIn(Set.of("Ашан, Севастопольский просп."));
        orders().clearBasicStoreFilters();
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();
    }

    @CaseId(2122)
    @Test(description = "Фильтрация заказов по магазину",
            groups = {"ondemand_orders_regression", "ondemand_orders_smoke", "admin_ondemand_smoke", "admin_ondemand_regression"})
    public void storeFilterTest() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        orders().goToPage();
        orders().checkShipmentListNotEmpty();
        orders().checkOrdersLoaded();
        orders().checkLoadingLabelNotVisible();

        orders().addStoreFilterItem("Севастопольский просп", "Щелковская", "Дмитровское");
        orders().checkStoreSelectedFilterList(List.of("Ашан, Москва, Севастопольский просп., 11Е", "METRO, Москва, Щелковская, 6", "METRO, Москва, Дмитровское ш, 165Б"));
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();
        orders().checkAllShipmentInTableHasBasicStoreIn(Set.of("Ашан, Севастопольский просп.", "METRO, Москва, Щелковская", "METRO, Дмитровское ш"));

        orders().removeStoreFilterItem("METRO, Москва, Щелковская, 6");
        orders().checkStoreSelectedFilterList(List.of("Ашан, Москва, Севастопольский просп., 11Е", "METRO, Москва, Дмитровское ш, 165Б"));
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();

        orders().clearStoreFilters();
        orders().checkStoreFiltersNotSelected();
    }

    @CaseId(2123)
    @Test(description = "Фильтрация заказов по Способу оплаты",
            groups = {"ondemand_orders_regression", "ondemand_orders_smoke", "admin_ondemand_smoke", "admin_ondemand_regression"})
    public void paymentMethodsFilterTest() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        orders().goToPage();
        orders().checkShipmentListNotEmpty();
        orders().checkOrdersLoaded();
        orders().checkLoadingLabelNotVisible();

        orders().addPaymentMethodFilterItem(BY_CASH.getName(), BY_CARD_TO_COURIER.getName(), AT_CHECKOUT.getName());
        orders().checkPaymentMethodSelectedFilterList(List.of(BY_CASH.getName(), BY_CARD_TO_COURIER.getName(), AT_CHECKOUT.getName()));
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();
        orders().checkAllShipmentInTableHasPaymentMethodIn(Set.of(BY_CASH.getName(), BY_CARD_TO_COURIER.getName(), AT_CHECKOUT.getName()));

        orders().removePaymentMethodFilterItem(BY_CASH.getName());
        orders().checkPaymentMethodSelectedFilterList(List.of(BY_CARD_TO_COURIER.getName(), AT_CHECKOUT.getName()));
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();
        orders().checkAllShipmentInTableHasPaymentMethodIn(Set.of(BY_CARD_TO_COURIER.getName(), AT_CHECKOUT.getName()));

        orders().clearPaymentMethodFilters();
        orders().checkPaymentMethodFiltersNotSelected();
    }

    @Skip(onServer = Server.PRODUCTION)
    //Заказы отображаются не сразу в админке
    @Flaky
    @CaseId(2124)
    @Test(description = "Фильтрация заказов по промокоду",
            groups = {"ondemand_orders_regression", "ondemand_orders_smoke", "admin_ondemand_smoke", "admin_ondemand_regression"})
    public void promoCodeFiltersTest() {
        final var userData = UserManager.getQaUser();
        final var order = helper.makeAndCancelOrder(userData, UiProperties.DEFAULT_SID, 3, true);
        final var promoCode = order.getPromotionCodes().get(0).getCode();

        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        orders().goToPage();
        orders().checkShipmentListNotEmpty();
        orders().checkOrdersLoaded();
        orders().checkLoadingLabelNotVisible();

        orders().addPromoCodeFilter(promoCode);
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();
        orders().clickOrderNumberInShipment(1);
        orders().switchToNextWindow();

        shipmentPage().waitPageLoad();
        shipmentPage().checkPromoCodeData(promoCode);
    }

    @CaseId(2161)
    // Все из-за того, что планировали все увести на диспач
    // и потом выпилить этот фильтр и перей/ти на статусы джобов
    @Flaky
    @Test(description = "Фильтрация заказа по статусу сборки",
            groups = {"ondemand_orders_regression", "ondemand_orders_smoke", "admin_ondemand_smoke", "admin_ondemand_regression"})
    public void collectingStatusFilterTest() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        orders().goToPage();
        orders().checkShipmentListNotEmpty();
        orders().checkOrdersLoaded();
        orders().checkLoadingLabelNotVisible();

        orders().addStoreFilterItem("Екатеринбург Металлургов");

        orders().addCollectingStatusFilter(AUTOMATIC_DISPATCHING.getName(), MANUAL_DISPATCHING.getName(), OFFER_SENT.getName());
        orders().checkCollectingStatusSelectedFilterList(List.of(AUTOMATIC_DISPATCHING.getName(), MANUAL_DISPATCHING.getName(), OFFER_SENT.getName()));
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();
        orders().checkAllShipmentInTableHasCollectingStatusIn(Set.of(AUTOMATIC_DISPATCHING.getName(), MANUAL_DISPATCHING.getName(), OFFER_SENT.getName(), CANCELLED.getName()));

        orders().removeCollectingStatusFilterItem(AUTOMATIC_DISPATCHING.getName());
        orders().checkCollectingStatusSelectedFilterList(List.of(MANUAL_DISPATCHING.getName(), OFFER_SENT.getName()));
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();
        orders().checkAllShipmentInTableHasCollectingStatusIn(Set.of(MANUAL_DISPATCHING.getName(), OFFER_SENT.getName(), CANCELLED.getName()));

        orders().clearCollectingStatusFilters();
        orders().checkCollectingStatusNotSelected();
    }

    @CaseId(2137)
    // Все из-за того, что планировали все увести на диспач
    // и потом выпилить этот фильтр и перей/ти на статусы джобов
    @Flaky
    @Test(description = "Фильтрация заказа по статусу доставки",
            groups = {"ondemand_orders_regression", "ondemand_orders_smoke", "admin_ondemand_smoke", "admin_ondemand_regression"})
    public void deliveryStatusFilterTest() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        orders().goToPage();
        orders().checkShipmentListNotEmpty();
        orders().checkOrdersLoaded();
        orders().checkLoadingLabelNotVisible();

        orders().addStoreFilterItem("Екатеринбург Металлургов");

        orders().addDeliveryStatusFilter(AUTOMATIC_DISPATCHING.getName(), MANUAL_DISPATCHING.getName(), OFFER_SENT.getName());
        orders().checkDeliveryStatusSelectedFilterList(List.of(AUTOMATIC_DISPATCHING.getName(), MANUAL_DISPATCHING.getName(), OFFER_SENT.getName()));
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();
        orders().checkAllShipmentInTableHasShippingStatusIn(Set.of(AUTOMATIC_DISPATCHING.getName(), MANUAL_DISPATCHING.getName(), OFFER_SENT.getName(), CANCELLED.getName()));

        orders().removeDeliveryStatusFilterItem(AUTOMATIC_DISPATCHING.getName());
        orders().checkDeliveryStatusSelectedFilterList(List.of(MANUAL_DISPATCHING.getName(), OFFER_SENT.getName()));
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();
        orders().checkAllShipmentInTableHasShippingStatusIn(Set.of(MANUAL_DISPATCHING.getName(), OFFER_SENT.getName(), CANCELLED.getName()));

        orders().clearDeliveryStatusFilters();
        orders().checkDeliveryStatusNotSelected();
    }

    @CaseId(2159)
    @Test(description = "Фильтрация заказов по кол-ву позиций в заказе",
            groups = {"ondemand_orders_regression", "ondemand_orders_smoke", "admin_ondemand_smoke", "admin_ondemand_regression"})
    public void orderFilterByItems() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        orders().goToPage();
        orders().checkOrdersLoaded();
        orders().checkLoadingLabelNotVisible();
        orders().checkShipmentListNotEmpty();

        orders().addOrderItemsFrom("пппп");
        orders().addOrderItemsTo("ffff");
        orders().applyFilters();
        orders().checkAlertErrorItemsFromVisible();
        orders().checkAlertErrorItemsToVisible();

        orders().addOrderItemsFrom("1,5");
        orders().addOrderItemsTo("2,6");
        orders().applyFilters();
        orders().checkAlertErrorItemsFromVisible();
        orders().checkAlertErrorItemsToVisible();

        orders().addOrderItemsFrom(" ");
        orders().addOrderItemsTo(" ");
        orders().applyFilters();
        orders().checkAlertErrorItemsFromVisible();
        orders().checkAlertErrorItemsToVisible();

        orders().addOrderItemsFrom("2");
        orders().addOrderItemsTo("4");

        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();

        orders().checkAllShipmentInTableBetweenItems(2.0, 4.0);

        orders().addOrderItemsFrom("1.2");
        orders().addOrderItemsTo("10.5");

        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();

        orders().checkAllShipmentInTableBetweenItems(1, 10);
    }

    @CaseId(2160)
    @Test(description = "Фильтрация заказов по статусу оплаты",
            groups = {"ondemand_orders_regression", "ondemand_orders_smoke", "admin_ondemand_smoke", "admin_ondemand_regression"})
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
        orders().checkAllShipmentInTableHasPaymentStatusIn(Set.of(NOT_PAID.getName()));

        orders().addPaymentStatusFilterItem(BALANCE_DUE.getName().toLowerCase());
        orders().checkPaymentStatusSelectedFilterList(List.of(NOT_PAID.getName().toLowerCase(), BALANCE_DUE.getName().toLowerCase()));
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();
        orders().checkAllShipmentInTableHasPaymentStatusIn(Set.of(NOT_PAID.getName(), BALANCE_DUE.getName()));

        orders().addPaymentStatusFilterItem(PAID.getName().toLowerCase(), OVERPAID.getName().toLowerCase(), FAILED.getName().toLowerCase());
        orders().checkPaymentStatusSelectedFilterList(List.of(
                NOT_PAID.getName().toLowerCase(),
                BALANCE_DUE.getName().toLowerCase(),
                PAID.getName().toLowerCase(),
                OVERPAID.getName().toLowerCase(),
                FAILED.getName().toLowerCase()));
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();
        orders().checkAllShipmentInTableHasPaymentStatusIn(Set.of(
                NOT_PAID.getName(),
                BALANCE_DUE.getName(),
                PAID.getName(),
                OVERPAID.getName(),
                FAILED.getName()));

        orders().removePaymentStatusFilterItem(NOT_PAID.getName().toLowerCase());
        orders().checkPaymentStatusSelectedFilterList(List.of(
                BALANCE_DUE.getName().toLowerCase(),
                PAID.getName().toLowerCase(),
                OVERPAID.getName().toLowerCase(),
                FAILED.getName().toLowerCase()));
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();
        orders().checkAllShipmentInTableHasPaymentStatusIn(Set.of(
                BALANCE_DUE.getName(),
                PAID.getName(),
                OVERPAID.getName(),
                FAILED.getName()));

        orders().addPaymentStatusFilterItem(NOT_PAID.getName().toLowerCase());
        orders().checkPaymentStatusSelectedFilterList(List.of(
                BALANCE_DUE.getName().toLowerCase(),
                PAID.getName().toLowerCase(),
                OVERPAID.getName().toLowerCase(),
                FAILED.getName().toLowerCase(),
                NOT_PAID.getName().toLowerCase()));

        orders().clearPaymentStatusFilters();
        orders().checkPaymentStatusFiltersNotSelected();
    }

    @CaseId(2136)
    @Test(description = "Фильтрация заказов с помощью быстрого фильтра",
            groups = {"ondemand_orders_regression", "ondemand_orders_smoke", "admin_ondemand_smoke", "admin_ondemand_regression"})
    public void quickFiltersTest() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        orders().goToPage();
        orders().checkShipmentListNotEmpty();
        orders().checkOrdersLoaded();
        orders().checkLoadingLabelNotVisible();

        orders().addQuickFilterItem(UNPAID.getName());
        orders().checkQuickFiltersSelectedFilterList(List.of(UNPAID.getName()));
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();
        orders().checkAllShipmentInTableHasPaymentStatusIn(Set.of(NOT_PAID.getName()));

        orders().removeQuickFilterItem(UNPAID.getName());
        orders().checkQuickFiltersNotSelected();

        orders().addQuickFilterItem(UNPAID.getName());
        orders().addQuickFilterItem(QUICK_DELIVERY.getName());
        orders().checkQuickFiltersSelectedFilterList(List.of(UNPAID.getName(), QUICK_DELIVERY.getName()));
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();
        orders().checkAllShipmentInTableHasPaymentStatusIn(Set.of(NOT_PAID.getName()));

        orders().clearQuickFilters();
        orders().checkQuickFiltersNotSelected();
    }

    @CaseId(2071)
    @Test(description = "Фильтр Статус заказа - выпадающий список с множественным выбором",
            groups = {"ondemand_orders_regression", "admin_ondemand_regression"})
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
        orders().checkAllShipmentInTableHasSingleStatusIn(Set.of(SHIPMENT_PENDING.getName()));

        orders().addStatusFilterItem(SHIPMENT_READY.getName(), DISPATCH_NEW.getName());
        //если магаз без диспача и джобов то ищет корректно. Все из-за того,
        // что планировали все увести на диспач и потом выпилить этот фильтр и перейти на статусы джобов
        orders().addStoreFilterItem("Мещерский бульвар");
        orders().applyFilters();
        orders().waitPageLoad();
        orders().checkLoadingLabelNotVisible();
        orders().checkAllShipmentInTableHasSingleStatusIn(Set.of(SHIPMENT_PENDING.getName(), SHIPMENT_READY.getName(), DISPATCH_NEW.getName()));

        orders().removeShipmentStatusFilterItem(SHIPMENT_READY.getName());
        orders().checkShipmentStatusSelectedFilterList(List.of(SHIPMENT_PENDING.getName(), DISPATCH_NEW.getName()));
        orders().applyFilters();
        orders().waitPageLoad();
        orders().checkLoadingLabelNotVisible();
        orders().checkAllShipmentInTableHasSingleStatusIn(Set.of(SHIPMENT_PENDING.getName(), DISPATCH_NEW.getName()));

        orders().clearShipmentStatusFilters();
        orders().applyFilters();
        orders().waitPageLoad();
        orders().checkLoadingLabelNotVisible();
    }

    //TODO SA-1970 Фильтр 'Статус сборки', SA-1971 Фильтр 'Статус доставки' не реализованы. Сроков нет
    @CaseId(2063)
    @Issues(value = {@Issue("SA-1970"), @Issue("SA-1971")})
    @Test(description = "Быстрый фильтр: Без назначений",
            groups = {"ondemand_orders_regression", "admin_ondemand_regression"})
    public void quickFiltersNotAssignedTest() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        orders().goToPage();
        orders().checkShipmentListNotEmpty();
        orders().checkOrdersLoaded();
        orders().checkLoadingLabelNotVisible();

        orders().addQuickFilterItem(NOT_ASSIGNED.getName());
        orders().checkQuickFiltersSelectedFilterList(List.of(NOT_ASSIGNED.getName()));

        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();

        orders().checkAllShipmentInTableHasCollectorIn(Set.of("Нет исполнителя"));
        orders().checkAllShipmentInTableHasCourierIn(Set.of("Нет исполнителя"));
    }

    @CaseId(2064)
    @Test(description = "Быстрый фильтр: Неоплаченные",
            groups = {"ondemand_orders_regression", "admin_ondemand_regression"})
    public void quickFiltersNotPaidTest() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        orders().goToPage();
        orders().checkShipmentListNotEmpty();
        orders().checkOrdersLoaded();
        orders().checkLoadingLabelNotVisible();

        orders().addQuickFilterItem(UNPAID.getName());
        orders().checkQuickFiltersSelectedFilterList(List.of(UNPAID.getName()));

        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();

        orders().checkAllShipmentInTableHasPaymentStatusIn(Set.of(NOT_PAID.getName()));
    }

    @CaseId(2066)
    @Test(description = "Быстрый фильтр: Завершенные",
            groups = {"ondemand_orders_regression", "admin_ondemand_regression"})
    public void quickFiltersCompletedTest() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        orders().goToPage();
        orders().checkShipmentListNotEmpty();
        orders().checkOrdersLoaded();
        orders().checkLoadingLabelNotVisible();

        orders().addQuickFilterItem(COMPLETED.getName());
        orders().checkQuickFiltersSelectedFilterList(List.of(COMPLETED.getName()));

        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();

        orders().checkAllShipmentInTableHasSingleStatusIn(Set.of(SHIPMENT_SHIPPED.getName(), SHIPMENT_CANCELED.getName(), CLIENT_CANCELLED.getName()));
        orders().checkAllShipmentInTableHasCollectingStatusIn(Set.of(SHIPMENT_READY_TO_SHIP.getName(), CLIENT_CANCELLED.getName()));
        orders().checkAllShipmentInTableHasShippingStatusIn(Set.of(SHIPMENT_SHIPPED.getName(), CLIENT_CANCELLED.getName()));
    }

    @CaseId(2067)
    @Test(description = "Быстрый фильтр: Незавершенные",
            groups = {"ondemand_orders_regression", "admin_ondemand_regression"})
    public void quickFiltersNotCompletedTest() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        orders().goToPage();
        orders().checkShipmentListNotEmpty();
        orders().checkOrdersLoaded();
        orders().checkLoadingLabelNotVisible();

        orders().addQuickFilterItem(NOT_COMPLETED.getName());
        orders().checkQuickFiltersSelectedFilterList(List.of(NOT_COMPLETED.getName()));
        orders().checkShipmentStatusSelectedFilterList(List.of(
                SHIPMENT_READY.getName(),
                SHIPMENT_COLLECTING.getName(),
                SHIPMENT_READY_TO_SHIP.getName(),
                SHIPMENT_SHIPPING.getName()));

        orders().addBasicStoreFilterItem("METRO, Дубровка");
        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();

        orders().checkAllShipmentInTableHasSingleStatusIn(Set.of(SHIPMENT_READY.getName()));
        orders().checkAllShipmentInTableHasCollectingStatusIn(Set.of(SHIPMENT_COLLECTING.getName(), SHIPMENT_READY_TO_SHIP.getName()));
        orders().checkAllShipmentInTableHasShippingStatusIn(Set.of(SHIPMENT_SHIPPING.getName()));
    }

    @CaseId(2068)
    @Test(description = "Быстрый фильтр: B2B клиенты",
            groups = {"ondemand_orders_regression", "admin_ondemand_regression"})
    public void quickFiltersB2BTest() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        orders().goToPage();
        orders().checkShipmentListNotEmpty();
        orders().checkOrdersLoaded();
        orders().checkLoadingLabelNotVisible();

        orders().addQuickFilterItem(B2B_CLIENTS.getName());
        orders().checkQuickFiltersSelectedFilterList(List.of(B2B_CLIENTS.getName()));

        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();

        //TODO В заказе никак не обозначенo что он B2B. Признак B2B можно проверить только у пользовател, сделавшего заказ в профиле
        //Для stf-6 основные B2B-клиенты от которых сыпятся заказы - Ivan Petrov, Pavel Nep.
        orders().checkAllShipmentInTableHasCustomerIn(Set.of("Ivan Petrov", "Pavel Nep", "Ольга Медведева", "йй йй"));
    }

    @CaseId(2070)
    @Test(description = "Быстрые фильтры - множественный выбор", groups = {"ondemand_orders_regression", "admin_ondemand_regression"})
    public void quickFiltersComplexUsingTest() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        orders().goToPage();
        orders().checkShipmentListNotEmpty();
        orders().checkOrdersLoaded();
        orders().checkLoadingLabelNotVisible();

        orders().addQuickFilterItem(UNPAID.getName());
        orders().addQuickFilterItem(COMPLETED.getName());
        orders().checkQuickFiltersSelectedFilterList(List.of(UNPAID.getName(), COMPLETED.getName()));

        orders().applyFilters();
        orders().checkLoadingLabelNotVisible();

        orders().checkAllShipmentInTableHasSingleStatusIn(Set.of(UNPAID.getName(), SHIPMENT_SHIPPED.getName(), SHIPMENT_CANCELED.getName(), CLIENT_CANCELLED.getName()));
        orders().checkAllShipmentInTableHasShippingStatusIn(Set.of(SHIPMENT_SHIPPED.getName(), CLIENT_CANCELLED.getName()));

        orders().removeQuickFilterItem(UNPAID.getName());
        orders().checkShipmentStatusFiltersNotSelected();

        orders().addQuickFilterItem(NOT_COMPLETED.getName());
        orders().checkQuickFiltersSelectedFilterList(List.of(NOT_COMPLETED.getName()));

        orders().addQuickFilterItem(UNPAID.getName());
        orders().addQuickFilterItem(NOT_ASSIGNED.getName());
        orders().addQuickFilterItem(LONG_DISPATCH.getName());
        orders().addQuickFilterItem(QUICK_DELIVERY.getName());
        orders().addQuickFilterItem(B2B_CLIENTS.getName());
        orders().addQuickFilterItem(DELIVERY_TIME_CHANGED.getName());

        orders().checkQuickFiltersSelectedFilterList(List.of(
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
