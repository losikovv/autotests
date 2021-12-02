package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseIDs;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.enums.SessionProvider;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.enums.v2.AnalyzeResultV2;
import ru.instamart.api.enums.v2.ShippingMethodsV2;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.model.v2.ZoneV2;
import ru.instamart.api.request.v2.LineItemsV2Request;
import ru.instamart.api.request.v2.OrdersV2Request;
import ru.instamart.api.response.v2.OrderV2Response;
import ru.instamart.api.response.v2.TransferMethodV2Response;
import ru.instamart.jdbc.dao.SpreeOrdersDao;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkError;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode422;

@Epic("ApiV2")
@Feature("Проверка потерь")
public class TransferMethodV2Test extends RestBase {

    private OrderV2 order;
    private UserData userData;

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        SessionFactory.makeSession(SessionType.API_V2, SessionProvider.PHONE);
        userData = SessionFactory.getSession(SessionType.API_V2).getUserData();
        apiV2.fillCart(userData, EnvironmentProperties.DEFAULT_SID);
        order = apiV2.getOpenOrder();
    }

    @CaseIDs(value = {@CaseId(994), @CaseId(995), @CaseId(996), @CaseId(997), @CaseId(998)})
    @Story("Трансфер доставка-доставка")
    @Test(groups = {"api-instamart-regress"},
            description = "Проверяем потери для магазина, где доступна только доставка",
            dataProvider = "storesDataForTransferMethodOnlyCourier",
            dataProviderClass = RestDataProvider.class)
    public void analyzeTransferForStoreWoPickup(Integer storeId, AnalyzeResultV2 analyzeResult) {
        ZoneV2 zone = apiV2.getCoordinates(storeId);
        final Response response = OrdersV2Request.TransferMethod.GET(ShippingMethodsV2.BY_COURIER.getMethod(), zone, order.getNumber());
        checkStatusCode200(response);
        compareTwoObjects(analyzeResult.getValue(), response.as(TransferMethodV2Response.class).getResult());
    }

    @CaseIDs(value = {@CaseId(1049), @CaseId(1051), @CaseId(1052)})
    @Story("Трансфер доставка-самовывоз")
    @Test(groups = {"api-instamart-regress"},
            description = "Проверяем потери для магазина, где доступна только доставка",
            dataProvider = "negativeStoresForPickupTransferMethodOnlyCourier",
            dataProviderClass = RestDataProvider.class)
    public void analyzeNegativePickupTransferForStoreWoPickup(Integer storeId) {
        final Response response = OrdersV2Request.TransferMethod.GET(ShippingMethodsV2.PICKUP.getMethod(), storeId, order.getNumber());
        checkStatusCode422(response);
        checkError(response, "Самовывоз из магазина не работает. Пожалуйста, выберите другой");
    }

    @CaseIDs(value = {@CaseId(1042), @CaseId(1043), @CaseId(1045), @CaseId(1046)})
    @Story("Трансфер доставка-доставка")
    @Test(groups = {"api-instamart-regress"},
            description = "Проверяем потери для магазина, где доступны и доставка, и самовывоз",
            dataProvider = "storesDataForPickupTransferMethodAll",
            dataProviderClass = RestDataProvider.class)
    public void analyzePickupTransferForStoreWithAll(Integer storeId, AnalyzeResultV2 analyzeResult) {
        final Response response = OrdersV2Request.TransferMethod.GET(ShippingMethodsV2.PICKUP.getMethod(), storeId, order.getNumber());
        checkStatusCode200(response);
        compareTwoObjects(analyzeResult.getValue(), response.as(TransferMethodV2Response.class).getResult());
    }

    @CaseId(1167)
    @Story("Трансфер доставка-доставка")
    @Test(groups = {"api-instamart-regress"},
            description = "Проверяем потери c отсуствующим обязательным параметром")
    public void analyzeTransferWoRequiredParams() {
        final Response response = OrdersV2Request.TransferMethod.GET(ShippingMethodsV2.BY_COURIER.getMethod(), (ZoneV2) null, order.getNumber());
        checkStatusCode422(response);
        checkError(response, "Недопустимые параметры запроса: address_params Для способа доставки курьером, требуется указать address_params");
    }

    @CaseId(1168)
    @Story("Трансфер доставка-доставка")
    @Test(groups = {"api-instamart-regress"},
            description = "Проверяем потери c отсуствующим обязательным параметром")
    public void analyzePickupTransferWoRequiredParams() {
        final Response response = OrdersV2Request.TransferMethod.GET(ShippingMethodsV2.PICKUP.getMethod(), (Integer) null, order.getNumber());
        checkStatusCode422(response);
        checkError(response, "Недопустимые параметры запроса: pickup_store_id Для способа доставки самовывоз, требуется указать pickup_store_id");
    }

    @CaseIDs(value = {@CaseId(959), @CaseId(960), @CaseId(991), @CaseId(992), @CaseId(993)})
    @Story("Трансфер доставка-доставка")
    @Test(groups = {"api-instamart-regress"},
            description = "Проверяем потери для магазина, где доступны и доставка, и самовывоз",
            priority = 1,
            dataProvider = "storesDataForTransferMethodAllShipping",
            dataProviderClass = RestDataProvider.class)
    public void analyzeTransferForStoreWithPickupAndCourier(Integer storeId, AnalyzeResultV2 analyzeResult) {
        apiV2.fillCart(userData, 1);
        order = apiV2.getOpenOrder();
        ZoneV2 zone = apiV2.getCoordinates(storeId);

        final Response response = OrdersV2Request.TransferMethod.GET(ShippingMethodsV2.BY_COURIER.getMethod(), zone, order.getNumber());
        checkStatusCode200(response);
        compareTwoObjects(analyzeResult.getValue(), response.as(TransferMethodV2Response.class).getResult());
    }

    @CaseIDs(value = {@CaseId(1049), @CaseId(1051), @CaseId(1052)})
    @Story("Трансфер доставка-самовывоз")
    @Test(groups = {"api-instamart-regress"},
            description = "Проверяем потери для магазина, где доступна только доставка",
            priority = 2,
            dataProvider = "storesDataForPickupTransferMethodOnlyCourier",
            dataProviderClass = RestDataProvider.class)
    public void analyzePickupTransferForStoreWoPickup(Integer storeId, AnalyzeResultV2 analyzeResult) {
        final Response response = OrdersV2Request.TransferMethod.GET(ShippingMethodsV2.PICKUP.getMethod(), storeId, order.getNumber());
        checkStatusCode200(response);
        compareTwoObjects(analyzeResult.getValue(), response.as(TransferMethodV2Response.class).getResult());
    }

    @CaseId(1044)
    @Story("Трансфер доставка-самовывоз")
    @Test(groups = {"api-instamart-regress"},
            description = "Проверяем потери для магазина, где доступна только доставка",
            priority = 2)
    public void analyzeNegativePickupTransferForStoreWithAll() {
        final Response response = OrdersV2Request.TransferMethod.GET(ShippingMethodsV2.PICKUP.getMethod(), 2, order.getNumber());
        checkStatusCode422(response);
        checkError(response, "Самовывоз из магазина не работает. Пожалуйста, выберите другой");
    }

    @CaseId(1001)
    @Story("Трансфер доставка-доставка")
    @Test(groups = {"api-instamart-regress"},
            description = "Проверяем потери для несуществующей зоны",
            priority = 2)
    public void analyzeTransferForNotExistingZone() {
        final Response response = OrdersV2Request.TransferMethod.GET(ShippingMethodsV2.BY_COURIER.getMethod(), new ZoneV2(69.353499, 88.205530), order.getNumber());
        checkStatusCode200(response);
        compareTwoObjects(AnalyzeResultV2.ALL_PRODUCTS_DISAPPEARS.getValue(), response.as(TransferMethodV2Response.class).getResult());
    }

    @CaseIDs(value = {@CaseId(1053), @CaseId(1055), @CaseId(1056), @CaseId(1057), @CaseId(1058)})
    @Story("Трансфер самовывоз-доставка")
    @Test(groups = {"api-instamart-regress"},
            description = "Проверяем потери для магазина, где доступны и доставка, и самовывоз",
            priority = 3,
            dataProvider = "storesDataForTransferMethodAllShipping",
            dataProviderClass = RestDataProvider.class)
    public void analyzeCourierTransferForStoreWithAll(Integer storeId, AnalyzeResultV2 analyzeResult) {
        SpreeOrdersDao.INSTANCE.updateShippingKind(order.getNumber(), ShippingMethodsV2.PICKUP.getMethod());
        ZoneV2 zone = apiV2.getCoordinates(storeId);

        final Response response = OrdersV2Request.TransferMethod.GET(ShippingMethodsV2.BY_COURIER.getMethod(), zone, order.getNumber());
        checkStatusCode200(response);
        compareTwoObjects(analyzeResult.getValue(), response.as(TransferMethodV2Response.class).getResult());
    }

    @CaseId(1000)
    @Story("Трансфер доставка-доставка")
    @Test(groups = {"api-instamart-regress"},
            description = "Проверяем потери для пустой корзины",
            priority = 4)
    public void analyzeTransferForEmptyCart() {
        apiV2.dropCart(userData, apiV2.getAddressBySid(1));
        order = OrdersV2Request.POST().as(OrderV2Response.class).getOrder();
        ZoneV2 zone = apiV2.getCoordinates(EnvironmentProperties.DEFAULT_SID);

        final Response response = OrdersV2Request.TransferMethod.GET(ShippingMethodsV2.BY_COURIER.getMethod(), zone, order.getNumber());
        checkStatusCode200(response);
        compareTwoObjects(AnalyzeResultV2.OK.getValue(), response.as(TransferMethodV2Response.class).getResult());

        SpreeOrdersDao.INSTANCE.updateShippingKind(order.getNumber(), ShippingMethodsV2.PICKUP.getMethod());
        final Response responseLineItems = LineItemsV2Request.POST(apiV2.getProductsFromEachDepartmentInStore(22).get(0).getId(), 1, order.getNumber());
        checkStatusCode200(responseLineItems);
    }

    @CaseIDs(value = {@CaseId(1059), @CaseId(1060), @CaseId(1061), @CaseId(1062), @CaseId(1063)})
    @Story("Трансфер самовывоз-доставка")
    @Test(groups = {"api-instamart-regress"},
            description = "Проверяем потери для магазина, где доступен только самовывоз",
            priority = 5,
            dataProvider = "storesDataForCourierTransferMethod",
            dataProviderClass = RestDataProvider.class)
    public void analyzeCourierTransferForStoreWithСourier(Integer storeId, AnalyzeResultV2 analyzeResult) {
        ZoneV2 zone = apiV2.getCoordinates(storeId);

        final Response response = OrdersV2Request.TransferMethod.GET(ShippingMethodsV2.BY_COURIER.getMethod(), zone, order.getNumber());
        checkStatusCode200(response);
        compareTwoObjects(analyzeResult.getValue(), response.as(TransferMethodV2Response.class).getResult());
    }
}
