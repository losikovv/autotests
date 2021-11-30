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
import ru.instamart.api.request.v2.OrdersV2Request;
import ru.instamart.api.response.v2.OrderV2Response;
import ru.instamart.api.response.v2.TransferMethodV2Response;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
    public void analyzeTransferForStoreWithoutPickup(Integer storeId, AnalyzeResultV2 analyzeResult) {
        ZoneV2 zone = apiV2.getCoordinates(storeId);
        final Response response = OrdersV2Request.TransferMethod.GET(ShippingMethodsV2.BY_COURIER.getMethod(), zone, order.getNumber());
        checkStatusCode200(response);
        compareTwoObjects(analyzeResult.getValue(), response.as(TransferMethodV2Response.class).getResult());
    }

    @CaseIDs(value = {@CaseId(959), @CaseId(960), @CaseId(991), @CaseId(992), @CaseId(993)})
    @Story("Трансфер доставка-доставка")
    @Test(groups = {"api-instamart-regress"},
            description = "Проверяем потери для магазина, где доступны и доставка, и самовывоз",
            dependsOnMethods = "analyzeTransferForStoreWithoutPickup",
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

    @CaseId(1001)
    @Story("Трансфер доставка-доставка")
    @Test(groups = {"api-instamart-regress"},
            description = "Проверяем потери для несуществующей зоны",
            dependsOnMethods = "analyzeTransferForStoreWithPickupAndCourier")
    public void analyzeTransferForNotExistingZone() {
        final Response response = OrdersV2Request.TransferMethod.GET(ShippingMethodsV2.BY_COURIER.getMethod(), new ZoneV2(69.353499, 88.205530), order.getNumber());
        checkStatusCode200(response);
        compareTwoObjects(AnalyzeResultV2.ALL_PRODUCTS_DISAPPEARS.getValue(), response.as(TransferMethodV2Response.class).getResult());
    }

    @CaseId(1000)
    @Story("Трансфер доставка-доставка")
    @Test(groups = {"api-instamart-regress"},
            description = "Проверяем потери для пустой корзины",
            dependsOnMethods = "analyzeTransferForNotExistingZone")
    public void analyzeTransferForEmptyCart() {
        apiV2.dropCart(userData, apiV2.getAddressBySid(1));
        OrderV2 order = OrdersV2Request.POST().as(OrderV2Response.class).getOrder();
        ZoneV2 zone = apiV2.getCoordinates(EnvironmentProperties.DEFAULT_SID);

        final Response response = OrdersV2Request.TransferMethod.GET(ShippingMethodsV2.BY_COURIER.getMethod(), zone, order.getNumber());
        checkStatusCode200(response);
        compareTwoObjects(AnalyzeResultV2.OK.getValue(), response.as(TransferMethodV2Response.class).getResult());
    }

    @CaseId(1167)
    @Story("Трансфер доставка-доставка")
    @Test(groups = {"api-instamart-regress"},
            description = "Проверяем потери для пустой корзины")
    public void analyzeTransferWithoutRequiredParams() {
        final Response response = OrdersV2Request.TransferMethod.GET(ShippingMethodsV2.BY_COURIER.getMethod(), (ZoneV2) null, order.getNumber());
        checkStatusCode422(response);
        checkError(response, "Недопустимые параметры запроса: address_params Для способа доставки курьером, требуется указать address_params");
    }
}
