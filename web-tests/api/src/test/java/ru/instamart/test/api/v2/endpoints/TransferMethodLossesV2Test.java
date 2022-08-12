package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.enums.v2.ShippingMethodV2;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.LineItemV2;
import ru.instamart.api.model.v2.LossV2;
import ru.instamart.api.model.v2.ZoneV2;
import ru.instamart.api.request.v2.OrdersV2Request;
import ru.instamart.api.response.v2.TransferMethodLossesV2Response;
import ru.instamart.jdbc.dao.stf.SpreeOrdersDao;
import ru.instamart.jdbc.dao.stf.SpreeProductsDao;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.enums.Server;
import ru.instamart.kraken.listener.Skip;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.List;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.*;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkLosses;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode422;

@Epic("ApiV2")
@Feature("Проверка потерь")
public class TransferMethodLossesV2Test extends RestBase {

    private UserData userData;
    private String currentOrderNumber;
    private LineItemV2 lineItem;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        SessionFactory.makeSession(SessionType.API_V2);
        userData = SessionFactory.getSession(SessionType.API_V2).getUserData();
        lineItem = apiV2.dropAndFillCart(userData, EnvironmentProperties.DEFAULT_SID).get(0);
        currentOrderNumber = apiV2.createOrder().getNumber();
    }

    @CaseIDs(value = {@CaseId(1566), @CaseId(1567), @CaseId(1568)})
    @Story("Трансфер доставка-доставка")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверяем потери для магазина, где доступна только доставка",
            dataProvider = "storesForCourierWithoutLosses",
            dataProviderClass = RestDataProvider.class)
    public void analyzeLossesForStoreWoPickupNoLosses(Integer storeId) {
        ZoneV2 zone = apiV2.getCoordinates(storeId);
        final Response response = OrdersV2Request.TransferMethod.Losses.GET(OrdersV2Request.TransferMethodParams.builder()
                .lat(zone.getLat())
                .lon(zone.getLon())
                .shippingMethod(ShippingMethodV2.BY_COURIER.getMethod())
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        compareTwoObjects(response.as(TransferMethodLossesV2Response.class).getLosses().size(), 0);
    }

    @CaseIDs(value = {@CaseId(1569), @CaseId(1570)})
    @Story("Трансфер доставка-доставка")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверяем потери для магазина, где доступна только доставка",
            dataProvider = "storesForCourierWithLosses",
            dataProviderClass = RestDataProvider.class)
    public void analyzeLossesForStoreWoPickupLosses(Integer storeId) {
        ZoneV2 zone = apiV2.getCoordinates(storeId);
        final Response response = OrdersV2Request.TransferMethod.Losses.GET(OrdersV2Request.TransferMethodParams.builder()
                .lat(zone.getLat())
                .lon(zone.getLon())
                .shippingMethod(ShippingMethodV2.BY_COURIER.getMethod())
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkLosses(response, lineItem);
    }

    @CaseIDs(value = {@CaseId(1571), @CaseId(1572)})
    @Story("Трансфер доставка-самовывоз")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверяем потери для магазина, где доступна только доставка",
            dataProvider = "negativeStoresForPickupTransferMethodOnlyCourier",
            dataProviderClass = RestDataProvider.class)
    public void analyzeNegativePickupLossesForStoreWoPickup(Integer storeId) {
        final Response response = OrdersV2Request.TransferMethod.Losses.GET(OrdersV2Request.TransferMethodParams.builder()
                        .shippingMethod(ShippingMethodV2.PICKUP.getMethod())
                        .pickupStoreId(storeId)
                        .build(), currentOrderNumber);
        checkStatusCode422(response);
        checkError(response, "Самовывоз из магазина не работает. Пожалуйста, выберите другой");
    }

    @CaseIDs(value = {@CaseId(1573), @CaseId(1574)})
    @Story("Трансфер доставка-самовывоз")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверяем потери для магазина, где доступна только доставка",
            dataProvider = "storesForPickupWithoutLosses",
            dataProviderClass = RestDataProvider.class)
    public void analyzePickupLossesForStoreWoPickupNoLosses(Integer storeId) {
        final Response response = OrdersV2Request.TransferMethod.Losses.GET(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.PICKUP.getMethod())
                .pickupStoreId(storeId)
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        compareTwoObjects(response.as(TransferMethodLossesV2Response.class).getLosses().size(), 0);
    }

    @CaseId(1575)
    @Story("Трансфер доставка-самовывоз")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверяем потери для магазина, где доступна только доставка")
    public void analyzePickupLossesForStoreWoPickupAnotherRetailer() {
        final Response response = OrdersV2Request.TransferMethod.Losses.GET(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.PICKUP.getMethod())
                .pickupStoreId(89)
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkLosses(response, lineItem);
    }

    @CaseId(1576)
    @Story("Трансфер доставка-доставка")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверяем потери c отсуствующим обязательным параметром")
    public void analyzeCourierLossesWoRequiredParams() {
        final Response response = OrdersV2Request.TransferMethod.Losses.GET(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.BY_COURIER.getMethod())
                .build(), currentOrderNumber);
        checkStatusCode422(response);
        checkError(response, "Недопустимые параметры запроса: address_params Для способа доставки курьером, требуется указать address_params");
    }

    @CaseId(1577)
    @Story("Трансфер доставка-самовывоз")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверяем потери c отсуствующим обязательным параметром",
            priority = 1)
    public void analyzePickupLossesWoRequiredParams() {
        final Response response = OrdersV2Request.TransferMethod.Losses.GET(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.PICKUP.getMethod())
                .build(), currentOrderNumber);
        checkStatusCode422(response);
        checkError(response, "Недопустимые параметры запроса: pickup_store_id Для способа доставки самовывоз, требуется указать pickup_store_id");

        apiV2.dropCart(userData, apiV2.getAddressBySid(EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID));
        currentOrderNumber = apiV2.createOrder().getNumber();
        lineItem = apiV2.addItemToCart(apiV2.getProductsFromEachDepartmentOnMainPage(EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID).get(0).getId(), 1);
    }

    @CaseIDs(value = {@CaseId(1578), @CaseId(1579), @CaseId(1580)})
    @Story("Трансфер доставка-доставка")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверяем потери для магазина, где доступны и доставка, и самовывоз",
            priority = 2,
            dataProvider = "storesForCouriersWithoutLosses",
            dataProviderClass = RestDataProvider.class)
    public void analyzeLossesForStoreWithPickupAndCourierNoLosses(Integer storeId) {
        ZoneV2 zone = apiV2.getCoordinates(storeId);

        final Response response = OrdersV2Request.TransferMethod.Losses.GET(OrdersV2Request.TransferMethodParams.builder()
                .lat(zone.getLat())
                .lon(zone.getLon())
                .shippingMethod(ShippingMethodV2.BY_COURIER.getMethod())
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        compareTwoObjects(response.as(TransferMethodLossesV2Response.class).getLosses().size(), 0);
    }

    @CaseIDs(value = {@CaseId(1581), @CaseId(1582)})
    @Story("Трансфер доставка-доставка")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверяем потери для магазина, где доступны и доставка, и самовывоз",
            priority = 2,
            dataProvider = "storesForCourierWithLosses",
            dataProviderClass = RestDataProvider.class)
    public void analyzeLossesForStoreWithPickupAndCourierLosses(Integer storeId) {
        ZoneV2 zone = apiV2.getCoordinates(storeId);

        final Response response = OrdersV2Request.TransferMethod.Losses.GET(OrdersV2Request.TransferMethodParams.builder()
                .lat(zone.getLat())
                .lon(zone.getLon())
                .shippingMethod(ShippingMethodV2.BY_COURIER.getMethod())
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkLosses(response, lineItem);
    }

    @CaseIDs(value = {@CaseId(1653), @CaseId(1654), @CaseId(1655)})
    @Skip(onServer = Server.STAGING)
    @Story("Трансфер доставка-самовывоз")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверяем потери для магазина, где доступны и доставка, и самовывоз",
            priority = 3,
            dataProvider = "storesForPickupAndCourierWithoutLosses",
            dataProviderClass = RestDataProvider.class)
    public void analyzePickupLossesForStoreWithAllNoLossees(Integer storeId) {
        final Response response = OrdersV2Request.TransferMethod.Losses.GET(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.PICKUP.getMethod())
                .pickupStoreId(storeId)
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        compareTwoObjects(response.as(TransferMethodLossesV2Response.class).getLosses().size(), 0);
    }

    @CaseId(1656)
    @Story("Трансфер доставка-самовывоз")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверяем потери для магазина, где доступна только доставка",
            priority = 3)
    public void analyzeNegativePickupLossesForStoreWithAll() {
        final Response response = OrdersV2Request.TransferMethod.Losses.GET(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.PICKUP.getMethod())
                .pickupStoreId(EnvironmentProperties.DEFAULT_SID)
                .build(), currentOrderNumber);
        checkStatusCode422(response);
        checkError(response, "Самовывоз из магазина не работает. Пожалуйста, выберите другой");
    }

    @CaseId(1657)
    @Story("Трансфер доставка-самовывоз")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверяем потери для магазина, где доступны и доставка, и самовывоз (другой ритейлер)",
            priority = 3)
    public void analyzePickupLossesForStoreWithAll() {
        final Response response = OrdersV2Request.TransferMethod.Losses.GET(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.PICKUP.getMethod())
                .pickupStoreId(89)
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkLosses(response, lineItem);
    }

    @CaseId(1658)
    @Story("Трансфер доставка-доставка")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверяем потери для несуществующей зоны",
            priority = 3)
    public void analyzeTransferForNotExistingZone() {
        final Response response = OrdersV2Request.TransferMethod.Losses.GET(OrdersV2Request.TransferMethodParams.builder()
                .lat(69.353499)
                .lon(88.205530)
                .shippingMethod(ShippingMethodV2.BY_COURIER.getMethod())
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkLosses(response, lineItem);
    }

    @CaseIDs(value = {@CaseId(1659), @CaseId(1660), @CaseId(1661)})
    @Story("Трансфер самовывоз-доставка")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверяем потери для магазина, где доступны и доставка, и самовывоз",
            priority = 4,
            dataProvider = "storesForCouriersWithoutLosses",
            dataProviderClass = RestDataProvider.class)
    public void analyzeCourierPickupLossesForStoreWithAllNoLosses(Integer storeId) {
        SpreeOrdersDao.INSTANCE.updateShippingKind(currentOrderNumber, ShippingMethodV2.PICKUP.getMethod());
        ZoneV2 zone = apiV2.getCoordinates(storeId);

        final Response response = OrdersV2Request.TransferMethod.Losses.GET(OrdersV2Request.TransferMethodParams.builder()
                .lat(zone.getLat())
                .lon(zone.getLon())
                .shippingMethod(ShippingMethodV2.BY_COURIER.getMethod())
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        compareTwoObjects(response.as(TransferMethodLossesV2Response.class).getLosses().size(), 0);
    }

    @CaseIDs(value = {@CaseId(1662), @CaseId(1663)})
    @Story("Трансфер самовывоз-доставка")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверяем потери для магазина, где доступны и доставка, и самовывоз",
            priority = 4,
            dataProvider = "storesForCourierWithLosses",
            dataProviderClass = RestDataProvider.class)
    public void analyzeCourierPickupLossesForStoreWithAll(Integer storeId) {
        SpreeOrdersDao.INSTANCE.updateShippingKind(currentOrderNumber, ShippingMethodV2.PICKUP.getMethod());
        ZoneV2 zone = apiV2.getCoordinates(storeId);

        final Response response = OrdersV2Request.TransferMethod.Losses.GET(OrdersV2Request.TransferMethodParams.builder()
                .lat(zone.getLat())
                .lon(zone.getLon())
                .shippingMethod(ShippingMethodV2.BY_COURIER.getMethod())
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkLosses(response, lineItem);
    }

    @CaseIDs(value = {@CaseId(1665), @CaseId(1666), @CaseId(1667)})
    @Skip(onServer = Server.STAGING)
    @Story("Трансфер самовывоз-самовывоз")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверяем потери для магазина, где доступны и доставка, и самовывоз",
            priority = 5,
            dataProvider = "storesForPickupAndCourierWithoutLosses",
            dataProviderClass = RestDataProvider.class)
    public void analyzePickupsLossesForStoreWithAll(Integer storeId) {
        final Response response = OrdersV2Request.TransferMethod.Losses.GET(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.PICKUP.getMethod())
                .pickupStoreId(storeId)
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        compareTwoObjects(response.as(TransferMethodLossesV2Response.class).getLosses().size(), 0);
    }

    @CaseId(1669)
    @Story("Трансфер самовывоз-самовывоз")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверяем потери для магазина, где доступны и доставка, и самовывоз (другой ритейлер)",
            priority = 5)
    public void analyzePickupsLossesForStoreWithAll() {
        final Response response = OrdersV2Request.TransferMethod.Losses.GET(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.PICKUP.getMethod())
                .pickupStoreId(89)
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkLosses(response, lineItem);
    }

    @CaseId(1668)
    @Story("Трансфер самовывоз-самовывоз")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверяем потери для магазина, где доступна только доставка",
            priority = 5)
    public void analyzeNegativePickupsLossesForStoreWithAll() {
        final Response response = OrdersV2Request.TransferMethod.Losses.GET(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.PICKUP.getMethod())
                .pickupStoreId(EnvironmentProperties.DEFAULT_SID)
                .build(), currentOrderNumber);
        checkStatusCode422(response);
        checkError(response, "Самовывоз из магазина не работает. Пожалуйста, выберите другой");
    }

    @CaseId(1670)
    @Story("Трансфер доставка-доставка")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверяем потери для пустой корзины",
            priority = 6)
    public void analyzeLossesForEmptyCart() {
        apiV2.dropCart(userData, apiV2.getAddressBySid(22));
        currentOrderNumber = apiV2.createOrder().getNumber();
        ZoneV2 zone = apiV2.getCoordinates(EnvironmentProperties.DEFAULT_SID);

        final Response response = OrdersV2Request.TransferMethod.Losses.GET(OrdersV2Request.TransferMethodParams.builder()
                .lat(zone.getLat())
                .lon(zone.getLon())
                .shippingMethod(ShippingMethodV2.BY_COURIER.getMethod())
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        compareTwoObjects(response.as(TransferMethodLossesV2Response.class).getLosses().size(), 0);

        SpreeOrdersDao.INSTANCE.updateShippingKind(currentOrderNumber, ShippingMethodV2.PICKUP.getMethod());
        lineItem = apiV2.addItemToCart(apiV2.getProductsFromEachDepartmentOnMainPage(22).get(0).getId(), 1);
    }

    @CaseIDs(value = {@CaseId(1674), @CaseId(1675)})
    @Story("Трансфер самовывоз-доставка")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверяем потери для магазина, где доступен только самовывоз",
            priority = 7,
            dataProvider = "storesForPickupCourierWithoutLosses",
            dataProviderClass = RestDataProvider.class)
    public void analyzeCourierLossesForStoreWithСourierNoLosses(Integer storeId) {
        ZoneV2 zone = apiV2.getCoordinates(storeId);

        final Response response = OrdersV2Request.TransferMethod.Losses.GET(OrdersV2Request.TransferMethodParams.builder()
                .lat(zone.getLat())
                .lon(zone.getLon())
                .shippingMethod(ShippingMethodV2.BY_COURIER.getMethod())
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        compareTwoObjects(response.as(TransferMethodLossesV2Response.class).getLosses().size(), 0);
    }

    @CaseIDs(value = {@CaseId(1671), @CaseId(1672), @CaseId(1673)})
    @Story("Трансфер самовывоз-доставка")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверяем потери для магазина, где доступен только самовывоз",
            priority = 7,
            dataProvider = "storesForPickupCourierWithLosses",
            dataProviderClass = RestDataProvider.class)
    public void analyzeCourierLossesForStoreWithСourier(Integer storeId) {
        ZoneV2 zone = apiV2.getCoordinates(storeId);

        final Response response = OrdersV2Request.TransferMethod.Losses.GET(OrdersV2Request.TransferMethodParams.builder()
                .lat(zone.getLat())
                .lon(zone.getLon())
                .shippingMethod(ShippingMethodV2.BY_COURIER.getMethod())
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkLosses(response, lineItem);
    }

    @CaseIDs(value = {@CaseId(1676), @CaseId(1677), @CaseId(1678)})
    @Skip(onServer = Server.STAGING)
    @Story("Трансфер самовывоз-самовывоз")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверяем потери для магазина, где доступен только самовывоз",
            priority = 7,
            dataProvider = "storeForPickupsWithoutLosses",
            dataProviderClass = RestDataProvider.class)
    public void analyzePickupLossesForStoreWithСourierNoLosses(Integer storeId) {
        final Response response = OrdersV2Request.TransferMethod.Losses.GET(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.PICKUP.getMethod())
                .pickupStoreId(storeId)
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        compareTwoObjects(response.as(TransferMethodLossesV2Response.class).getLosses().size(), 0);
    }

    @CaseId(1680)
    @Story("Трансфер самовывоз-самовывоз")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверяем потери для магазина, где доступен только самовывоз (другой ритейлер)",
            priority = 7)
    public void analyzePickupLossesForStoreWithСourier() {
        final Response response = OrdersV2Request.TransferMethod.Losses.GET(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.PICKUP.getMethod())
                .pickupStoreId(89)
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkLosses(response, lineItem);
    }

    @CaseId(1679)
    @Story("Трансфер самовывоз-самовывоз")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверяем потери для магазина, где доступна только доставка",
            priority = 7)
    public void analyzeNegativePickupsLossesForStoreWithPickup() {
        final Response response = OrdersV2Request.TransferMethod.Losses.GET(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.PICKUP.getMethod())
                .pickupStoreId(EnvironmentProperties.DEFAULT_SID)
                .build(), currentOrderNumber);
        checkStatusCode422(response);
        checkError(response, "Самовывоз из магазина не работает. Пожалуйста, выберите другой");
    }

    @CaseId(1683)
    @Story("Трансфер самовывоз-самовывоз")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверяем потери для пустой корзины",
            priority = 8)
    public void analyzePickupsTransferForEmptyCart() {
        apiV2.dropCart(userData, apiV2.getAddressBySid(1));
        currentOrderNumber = apiV2.createOrder().getNumber();

        final Response response = OrdersV2Request.TransferMethod.Losses.GET(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.PICKUP.getMethod())
                .pickupStoreId(EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID)
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        compareTwoObjects(response.as(TransferMethodLossesV2Response.class).getLosses().size(), 0);

        SpreeOrdersDao.INSTANCE.updateShippingKind(currentOrderNumber, ShippingMethodV2.PICKUP.getMethod());
        lineItem = apiV2.addItemToCart(SpreeProductsDao.INSTANCE.getOfferIdForAlcohol(EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID), 1);
    }

    @CaseIDs(value = {@CaseId(1686), @CaseId(1687), @CaseId(1688)})
    @Story("Трансфер доставка, алкоголь")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверяем потери для магазина c разными способами доставки",
            priority = 9,
            dataProvider = "storesDataForCourierAlcoholTransferMethod",
            dataProviderClass = RestDataProvider.class)
    public void analyzeCourierLossesForAlcohol(Integer storeId) {
        ZoneV2 zone = apiV2.getCoordinates(storeId);
        final Response response = OrdersV2Request.TransferMethod.Losses.GET(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.BY_COURIER.getMethod())
                .lat(zone.getLat())
                .lon(zone.getLon())
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkLosses(response, lineItem);
    }

    @CaseId(1689)
    @Story("Трансфер доставка, алкоголь")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверяем потери для несуществующего адреса",
            priority = 9)
    public void analyzeCourierTransferForAlcoholWoStore() {
        final Response response = OrdersV2Request.TransferMethod.Losses.GET(OrdersV2Request.TransferMethodParams.builder()
                .lat(69.353499)
                .lon(88.205530)
                .shippingMethod(ShippingMethodV2.BY_COURIER.getMethod())
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkLosses(response, lineItem);
    }

    @CaseIDs(value = {@CaseId(1691), @CaseId(1692)})
    @Skip(onServer = Server.STAGING)
    @Story("Трансфер самовывоз, алкоголь")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверяем потери для магазина c разными способами доставки",
            priority = 9,
            dataProvider = "storeForPickupAlcoholWithoutLosses",
            dataProviderClass = RestDataProvider.class)
    public void analyzePickupTransferForAlcohol(Integer storeId) {
        final Response response = OrdersV2Request.TransferMethod.Losses.GET(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.PICKUP.getMethod())
                .pickupStoreId(storeId)
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        compareTwoObjects(response.as(TransferMethodLossesV2Response.class).getLosses().size(), 0);
    }

    @CaseId(1693)
    @Skip(onServer = Server.STAGING)
    @Story("Трансфер самовывоз, алкоголь")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверяем потери для магазина c разными способами доставки (другой ритейлер)",
            priority = 9)
    public void analyzePickupLossesForAlcohol() {
        final Response response = OrdersV2Request.TransferMethod.Losses.GET(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.PICKUP.getMethod())
                .pickupStoreId(89)
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkLosses(response, lineItem);
    }

    @CaseId(1690)
    @Story("Трансфер самовывоз, алкоголь")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверяем потери для магазина без самовывоза",
            priority = 9)
    public void analyzePickupTransferForAlcoholWoPickup() {
        final Response response = OrdersV2Request.TransferMethod.Losses.GET(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.PICKUP.getMethod())
                .pickupStoreId(EnvironmentProperties.DEFAULT_SID)
                .build(), currentOrderNumber);
        checkStatusCode422(response);
        checkError(response, "Самовывоз из магазина не работает. Пожалуйста, выберите другой");
    }

    @CaseId(1684)
    @Story("Мультиритейлерная корзина")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверяем потери при смене ритейлера на одного из списка",
            priority = 10)
    public void analyzePickupTransferForMultiRetailers() {
        apiV2.dropCart(userData, apiV2.getAddressBySid(1));
        currentOrderNumber = apiV2.createOrder().getNumber();
        SpreeOrdersDao.INSTANCE.updateShippingKind(currentOrderNumber, ShippingMethodV2.BY_COURIER.getMethod());
        lineItem = apiV2.addItemToCart(apiV2.getProductsFromEachDepartmentOnMainPage(EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID).get(0).getId(), 1);
        apiV2.addItemToCart(apiV2.getProductsFromEachDepartmentOnMainPage(72).get(0).getId(), 1);
        final Response response = OrdersV2Request.TransferMethod.Losses.GET(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.PICKUP.getMethod())
                .pickupStoreId(72)
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkLosses(response, lineItem);
    }

    @CaseId(1685)
    @Story("Мультиритейлерная корзина")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверяем потери при смене ритейлера на стороннего",
            priority = 11)
    public void analyzeCourierTransferForMultiRetailers() {
        final Response response = OrdersV2Request.TransferMethod.Losses.GET(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.PICKUP.getMethod())
                .pickupStoreId(128)
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, TransferMethodLossesV2Response.class);
        List<LossV2> losses = response.as(TransferMethodLossesV2Response.class).getLosses();
        compareTwoObjects(losses.size(), 2);
    }
}
