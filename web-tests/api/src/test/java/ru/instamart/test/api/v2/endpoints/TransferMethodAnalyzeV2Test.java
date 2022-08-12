package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.enums.SessionProvider;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.enums.v2.AnalyzeResultV2;
import ru.instamart.api.enums.v2.ShippingMethodV2;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.ZoneV2;
import ru.instamart.api.request.v2.OrdersV2Request;
import ru.instamart.api.response.v2.TransferMethodAnalyzeV2Response;
import ru.instamart.jdbc.dao.stf.SpreeOrdersDao;
import ru.instamart.jdbc.dao.stf.SpreeProductsDao;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.enums.Server;
import ru.instamart.kraken.listener.Skip;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkError;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode422;

@Epic("ApiV2")
@Feature("Проверка потерь")
public class TransferMethodAnalyzeV2Test extends RestBase {

    private UserData userData;
    private String currentOrderNumber;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        SessionFactory.makeSession(SessionType.API_V2, SessionProvider.PHONE);
        userData = SessionFactory.getSession(SessionType.API_V2).getUserData();
        apiV2.dropAndFillCart(userData, EnvironmentProperties.DEFAULT_SID);
        currentOrderNumber = apiV2.createOrder().getNumber();
    }

    @CaseIDs(value = {@CaseId(994), @CaseId(995), @CaseId(996), @CaseId(997), @CaseId(998)})
    @Story("Трансфер доставка-доставка")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверяем потери для магазина, где доступна только доставка",
            dataProvider = "storesDataForTransferMethodOnlyCourier",
            dataProviderClass = RestDataProvider.class)
    public void analyzeTransferForStoreWoPickup(Integer storeId, AnalyzeResultV2 analyzeResult) {
        ZoneV2 zone = apiV2.getCoordinates(storeId);
        final Response response = OrdersV2Request.TransferMethod.Analyze.GET(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.BY_COURIER.getMethod())
                .lat(zone.getLat())
                .lon(zone.getLon())
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        compareTwoObjects(analyzeResult.getValue(), response.as(TransferMethodAnalyzeV2Response.class).getResult());
    }

    @CaseIDs(value = {@CaseId(1048), @CaseId(1050)})
    @Story("Трансфер доставка-самовывоз")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверяем потери для магазина, где доступна только доставка",
            dataProvider = "negativeStoresForPickupTransferMethodOnlyCourier",
            dataProviderClass = RestDataProvider.class)
    public void analyzeNegativePickupTransferForStoreWoPickup(Integer storeId) {
        final Response response = OrdersV2Request.TransferMethod.Analyze.GET(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.PICKUP.getMethod())
                .pickupStoreId(storeId)
                .build(), currentOrderNumber);
        checkStatusCode422(response);
        checkError(response, "Самовывоз из магазина не работает. Пожалуйста, выберите другой");
    }

    @CaseIDs(value = {@CaseId(1049), @CaseId(1051), @CaseId(1052)})
    @Story("Трансфер доставка-самовывоз")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверяем потери для магазина, где доступна только доставка",
            dataProvider = "storesDataForPickupTransferMethodOnlyCourier",
            dataProviderClass = RestDataProvider.class)
    public void analyzePickupTransferForStoreWoPickup(Integer storeId, AnalyzeResultV2 analyzeResult) {
        final Response response = OrdersV2Request.TransferMethod.Analyze.GET(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.PICKUP.getMethod())
                .pickupStoreId(storeId)
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        compareTwoObjects(analyzeResult.getValue(), response.as(TransferMethodAnalyzeV2Response.class).getResult());
    }

    @CaseId(1167)
    @Story("Трансфер доставка-доставка")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверяем потери c отсуствующим обязательным параметром")
    public void analyzeTransferWoRequiredParams() {
        final Response response = OrdersV2Request.TransferMethod.Analyze.GET(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.BY_COURIER.getMethod())
                .build(), currentOrderNumber);
        checkStatusCode422(response);
        checkError(response, "Недопустимые параметры запроса: address_params Для способа доставки курьером, требуется указать address_params");
    }

    @CaseId(1168)
    @Story("Трансфер доставка-самовывоз")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверяем потери c отсуствующим обязательным параметром",
            priority = 1)
    public void analyzePickupTransferWoRequiredParams() {
        final Response response = OrdersV2Request.TransferMethod.Analyze.GET(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.PICKUP.getMethod())
                .build(), currentOrderNumber);
        checkStatusCode422(response);
        checkError(response, "Недопустимые параметры запроса: pickup_store_id Для способа доставки самовывоз, требуется указать pickup_store_id");

        apiV2.dropCart(userData, apiV2.getAddressBySid(1));
        currentOrderNumber = apiV2.createOrder().getNumber();
        apiV2.addItemToCart(apiV2.getProductsFromEachDepartmentOnMainPage(1).get(0).getId(), 1);
    }

    @CaseIDs(value = {@CaseId(959), @CaseId(960), @CaseId(991), @CaseId(992), @CaseId(993)})
    @Story("Трансфер доставка-доставка")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверяем потери для магазина, где доступны и доставка, и самовывоз",
            priority = 2,
            dataProvider = "storesDataForTransferMethodAllShipping",
            dataProviderClass = RestDataProvider.class)
    public void analyzeTransferForStoreWithPickupAndCourier(Integer storeId, AnalyzeResultV2 analyzeResult) {
        ZoneV2 zone = apiV2.getCoordinates(storeId);

        final Response response = OrdersV2Request.TransferMethod.Analyze.GET(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.BY_COURIER.getMethod())
                .lat(zone.getLat())
                .lon(zone.getLon())
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        compareTwoObjects(analyzeResult.getValue(), response.as(TransferMethodAnalyzeV2Response.class).getResult());
    }

    @CaseIDs(value = {@CaseId(1042), @CaseId(1043), @CaseId(1045), @CaseId(1046)})
    @Skip(onServer = Server.STAGING)
    @Story("Трансфер доставка-самовывоз")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверяем потери для магазина, где доступны и доставка, и самовывоз",
            priority = 3,
            dataProvider = "storesDataForPickupTransferMethodAll",
            dataProviderClass = RestDataProvider.class)
    public void analyzePickupTransferForStoreWithAll(Integer storeId, AnalyzeResultV2 analyzeResult) {
        final Response response = OrdersV2Request.TransferMethod.Analyze.GET(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.PICKUP.getMethod())
                .pickupStoreId(storeId)
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        compareTwoObjects(analyzeResult.getValue(), response.as(TransferMethodAnalyzeV2Response.class).getResult());
    }

    @CaseId(1044)
    @Story("Трансфер доставка-самовывоз")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверяем потери для магазина, где доступна только доставка",
            priority = 3)
    public void analyzeNegativePickupTransferForStoreWithAll() {
        final Response response = OrdersV2Request.TransferMethod.Analyze.GET(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.PICKUP.getMethod())
                .pickupStoreId(2)
                .build(), currentOrderNumber);
        checkStatusCode422(response);
        checkError(response, "Самовывоз из магазина не работает. Пожалуйста, выберите другой");
    }

    @CaseId(1001)
    @Story("Трансфер доставка-доставка")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверяем потери для несуществующей зоны",
            priority = 3)
    public void analyzeTransferForNotExistingZone() {
        final Response response = OrdersV2Request.TransferMethod.Analyze.GET(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.BY_COURIER.getMethod())
                .lat(69.353499)
                .lon(88.205530)
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        compareTwoObjects(AnalyzeResultV2.ALL_PRODUCTS_DISAPPEARS.getValue(), response.as(TransferMethodAnalyzeV2Response.class).getResult());
    }

    @CaseIDs(value = {@CaseId(1053), @CaseId(1055), @CaseId(1056), @CaseId(1057), @CaseId(1058)})
    @Story("Трансфер самовывоз-доставка")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверяем потери для магазина, где доступны и доставка, и самовывоз",
            priority = 4,
            dataProvider = "storesDataForTransferMethodAllShipping",
            dataProviderClass = RestDataProvider.class)
    public void analyzeCourierTransferForStoreWithAll(Integer storeId, AnalyzeResultV2 analyzeResult) {
        SpreeOrdersDao.INSTANCE.updateShippingKind(currentOrderNumber, ShippingMethodV2.PICKUP.getMethod());
        ZoneV2 zone = apiV2.getCoordinates(storeId);

        final Response response = OrdersV2Request.TransferMethod.Analyze.GET(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.BY_COURIER.getMethod())
                .lat(zone.getLat())
                .lon(zone.getLon())
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        compareTwoObjects(analyzeResult.getValue(), response.as(TransferMethodAnalyzeV2Response.class).getResult());
    }

    @CaseIDs(value = {@CaseId(1065), @CaseId(1066), @CaseId(1068), @CaseId(1069)})
    @Skip(onServer = Server.STAGING)
    @Story("Трансфер самовывоз-самовывоз")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверяем потери для магазина, где доступны и доставка, и самовывоз",
            priority = 5,
            dataProvider = "storesDataForPickupTransferMethodAll",
            dataProviderClass = RestDataProvider.class)
    public void analyzePickupsTransferForStoreWithAll(Integer storeId, AnalyzeResultV2 analyzeResult) {
        final Response response = OrdersV2Request.TransferMethod.Analyze.GET(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.PICKUP.getMethod())
                .pickupStoreId(storeId)
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        compareTwoObjects(analyzeResult.getValue(), response.as(TransferMethodAnalyzeV2Response.class).getResult());
    }

    @CaseId(1067)
    @Story("Трансфер самовывоз-самовывоз")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверяем потери для магазина, где доступна только доставка",
            priority = 5)
    public void analyzeNegativePickupsTransferForStoreWithAll() {
        final Response response = OrdersV2Request.TransferMethod.Analyze.GET(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.PICKUP.getMethod())
                .pickupStoreId(EnvironmentProperties.DEFAULT_SID)
                .build(), currentOrderNumber);
        checkStatusCode422(response);
        checkError(response, "Самовывоз из магазина не работает. Пожалуйста, выберите другой");
    }

    @CaseId(1000)
    @Story("Трансфер доставка-доставка")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверяем потери для пустой корзины",
            priority = 6)
    public void analyzeTransferForEmptyCart() {
        apiV2.dropCart(userData, apiV2.getAddressBySid(22));
        currentOrderNumber = apiV2.createOrder().getNumber();
        ZoneV2 zone = apiV2.getCoordinates(EnvironmentProperties.DEFAULT_SID);

        final Response response = OrdersV2Request.TransferMethod.Analyze.GET(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.BY_COURIER.getMethod())
                .lat(zone.getLat())
                .lon(zone.getLon())
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        compareTwoObjects(AnalyzeResultV2.OK.getValue(), response.as(TransferMethodAnalyzeV2Response.class).getResult());

        SpreeOrdersDao.INSTANCE.updateShippingKind(currentOrderNumber, ShippingMethodV2.PICKUP.getMethod());
        apiV2.addItemToCart(apiV2.getProductsFromEachDepartmentOnMainPage(22).get(0).getId(), 1);
    }

    @CaseIDs(value = {@CaseId(1059), @CaseId(1060), @CaseId(1061), @CaseId(1062), @CaseId(1063)})
    @Story("Трансфер самовывоз-доставка")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверяем потери для магазина, где доступен только самовывоз",
            priority = 7,
            dataProvider = "storesDataForCourierTransferMethod",
            dataProviderClass = RestDataProvider.class)
    public void analyzeCourierTransferForStoreWithСourier(Integer storeId, AnalyzeResultV2 analyzeResult) {
        ZoneV2 zone = apiV2.getCoordinates(storeId);

        final Response response = OrdersV2Request.TransferMethod.Analyze.GET(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.BY_COURIER.getMethod())
                .lat(zone.getLat())
                .lon(zone.getLon())
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        compareTwoObjects(analyzeResult.getValue(), response.as(TransferMethodAnalyzeV2Response.class).getResult());
    }

    @CaseIDs(value = {@CaseId(1070), @CaseId(1074), @CaseId(1071), @CaseId(1073)})
    @Skip(onServer = Server.STAGING)
    @Story("Трансфер самовывоз-самовывоз")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверяем потери для магазина, где доступен только самовывоз",
            priority = 7,
            dataProvider = "storesDataForPickupTransferMethod",
            dataProviderClass = RestDataProvider.class)
    public void analyzePickupTransferForStoreWithСourier(Integer storeId, AnalyzeResultV2 analyzeResult) {
        final Response response = OrdersV2Request.TransferMethod.Analyze.GET(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.PICKUP.getMethod())
                .pickupStoreId(storeId)
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        compareTwoObjects(analyzeResult.getValue(), response.as(TransferMethodAnalyzeV2Response.class).getResult());
    }

    @CaseId(1067)
    @Story("Трансфер самовывоз-самовывоз")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверяем потери для магазина, где доступна только доставка",
            priority = 7)
    public void analyzeNegativePickupsTransferForStoreWithPickup() {
        final Response response = OrdersV2Request.TransferMethod.Analyze.GET(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.PICKUP.getMethod())
                .pickupStoreId(EnvironmentProperties.DEFAULT_SID)
                .build(), currentOrderNumber);
        checkStatusCode422(response);
        checkError(response, "Самовывоз из магазина не работает. Пожалуйста, выберите другой");
    }

    @CaseId(1075)
    @Story("Трансфер самовывоз-самовывоз")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверяем потери для пустой корзины",
            priority = 8)
    public void analyzePickupsTransferForEmptyCart() {
        apiV2.dropCart(userData, apiV2.getAddressBySid(1));
        currentOrderNumber = apiV2.createOrder().getNumber();

        final Response response = OrdersV2Request.TransferMethod.Analyze.GET(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.PICKUP.getMethod())
                .pickupStoreId(EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID)
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        compareTwoObjects(AnalyzeResultV2.OK.getValue(), response.as(TransferMethodAnalyzeV2Response.class).getResult());

        SpreeOrdersDao.INSTANCE.updateShippingKind(currentOrderNumber, ShippingMethodV2.PICKUP.getMethod());
        apiV2.addItemToCart(SpreeProductsDao.INSTANCE.getOfferIdForAlcohol(1), 1);
    }

    @CaseIDs(value = {@CaseId(1080), @CaseId(1081), @CaseId(1082)})
    @Story("Трансфер доставка, алкоголь")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверяем потери для магазина c разными способами доставки",
            priority = 9,
            dataProvider = "storesDataForCourierAlcoholTransferMethod",
            dataProviderClass = RestDataProvider.class)
    public void analyzeCourierTransferForAlcohol(Integer storeId) {
        ZoneV2 zone = apiV2.getCoordinates(storeId);
        final Response response = OrdersV2Request.TransferMethod.Analyze.GET(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.BY_COURIER.getMethod())
                .lat(zone.getLat())
                .lon(zone.getLon())
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        compareTwoObjects(AnalyzeResultV2.OK.getValue(), response.as(TransferMethodAnalyzeV2Response.class).getResult());
    }

    @CaseId(1083)
    @Story("Трансфер доставка, алкоголь")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверяем потери для несуществующего адреса",
            priority = 9)
    public void analyzeCourierTransferForAlcoholWoStore() {
        final Response response = OrdersV2Request.TransferMethod.Analyze.GET(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.BY_COURIER.getMethod())
                .lat(69.353499)
                .lon(88.205530)
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        compareTwoObjects(AnalyzeResultV2.ALCOHOL_DISAPPEARS.getValue(), response.as(TransferMethodAnalyzeV2Response.class).getResult());
    }

    @CaseIDs(value = {@CaseId(1077), @CaseId(1078), @CaseId(1079)})
    @Story("Трансфер самовывоз, алкоголь")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверяем потери для магазина c разными способами доставки",
            priority = 9,
            dataProvider = "storesDataForPickupAlcoholTransferMethod",
            dataProviderClass = RestDataProvider.class)
    public void analyzePickupTransferForAlcohol(Integer storeId, AnalyzeResultV2 analyzeResult) {
        final Response response = OrdersV2Request.TransferMethod.Analyze.GET(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.PICKUP.getMethod())
                .pickupStoreId(storeId)
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        compareTwoObjects(analyzeResult.getValue(), response.as(TransferMethodAnalyzeV2Response.class).getResult());
    }

    @CaseId(1076)
    @Story("Трансфер самовывоз, алкоголь")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверяем потери для магазина без самовывоза",
            priority = 9)
    public void analyzePickupTransferForAlcoholWoPickup() {
        final Response response = OrdersV2Request.TransferMethod.Analyze.GET(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.PICKUP.getMethod())
                .pickupStoreId(EnvironmentProperties.DEFAULT_SID)
                .build(), currentOrderNumber);
        checkStatusCode422(response);
        checkError(response, "Самовывоз из магазина не работает. Пожалуйста, выберите другой");
    }

    @CaseId(1084)
    @Story("Мультиритейлерная корзина")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверяем потери при смене ритейлера на одного из списка",
            priority = 10)
    public void analyzePickupTransferForMultiRetailers() {
        apiV2.dropCart(userData, apiV2.getAddressBySid(1));
        currentOrderNumber = apiV2.createOrder().getNumber();
        SpreeOrdersDao.INSTANCE.updateShippingKind(currentOrderNumber, ShippingMethodV2.BY_COURIER.getMethod());
        apiV2.addItemToCart(apiV2.getProductsFromEachDepartmentOnMainPage(1).get(0).getId(), 1);
        apiV2.addItemToCart(apiV2.getProductsFromEachDepartmentOnMainPage(72).get(0).getId(), 1);
        final Response response = OrdersV2Request.TransferMethod.Analyze.GET(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.PICKUP.getMethod())
                .pickupStoreId(EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID)
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        compareTwoObjects(AnalyzeResultV2.OTHER_RETAILER_SHIPMENTS_DISAPPEARS.getValue(), response.as(TransferMethodAnalyzeV2Response.class).getResult());
    }

    @CaseId(1085)
    @Story("Мультиритейлерная корзина")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверяем потери при смене ритейлера на стороннего",
            priority = 11)
    public void analyzeCourierTransferForMultiRetailers() {
        final Response response = OrdersV2Request.TransferMethod.Analyze.GET(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.PICKUP.getMethod())
                .pickupStoreId(128)
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        compareTwoObjects(AnalyzeResultV2.ALL_PRODUCTS_DISAPPEARS.getValue(), response.as(TransferMethodAnalyzeV2Response.class).getResult());
    }
}
