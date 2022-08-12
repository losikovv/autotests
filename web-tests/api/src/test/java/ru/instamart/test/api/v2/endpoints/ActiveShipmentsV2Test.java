package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.ActiveShipmentV2;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.request.v2.ShipmentsV2Request;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.enums.Server;
import ru.instamart.kraken.listener.Skip;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkShipmentInfo;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV2")
@Feature("Заказы (shipments)")
public class ActiveShipmentsV2Test extends RestBase {

    private OrderV2 order;
    private OrderV2 orderFromAnotherStore;

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        SessionFactory.makeSession(SessionType.API_V2);
        final UserData userData = SessionFactory.getSession(SessionType.API_V2).getUserData();
        order = apiV2.order(userData, EnvironmentProperties.DEFAULT_SID); //todo починить на стейдже GET /v2/shipments/null/shipping_rates
        if (!EnvironmentProperties.Env.isProduction()) {
            orderFromAnotherStore = apiV2.order(userData, EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID);
        }
    }

    @AfterClass(alwaysRun = true)
    public void cleanup() {
        //apiV2.cancelCurrentOrder(); //todo починить 404
    }

    @Skip(onServer = Server.STAGING)
    @CaseId(1387)
    @Story("Текущий подзаказ")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod", "api-v2"},
            description = "Получение текущего подзаказа для конкретного магазина")
    public void getActiveShipmentsForStore() {
        final Response response = ShipmentsV2Request.GET(EnvironmentProperties.DEFAULT_SID);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ActiveShipmentV2[].class);
        List<ActiveShipmentV2> shipmentsFromResponse = Arrays.asList(response.as(ActiveShipmentV2[].class));
        compareTwoObjects(shipmentsFromResponse.size(), 1);
        ActiveShipmentV2 shipmentFromResponse = shipmentsFromResponse.get(0);
        checkShipmentInfo(shipmentFromResponse, order);
    }

    @Skip(onServer = Server.STAGING)
    @CaseId(1388)
    @Story("Текущий подзаказ")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Получение текущего подзаказа")
    public void getActiveShipments() {
        final Response response = ShipmentsV2Request.GET(null);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ActiveShipmentV2[].class);
        List<ActiveShipmentV2> shipmentsFromResponse = Arrays.stream(response.as(ActiveShipmentV2[].class)).sorted(Comparator.comparing(ActiveShipmentV2::getId)).collect(Collectors.toList());
        compareTwoObjects(shipmentsFromResponse.size(), 2);
        checkShipmentInfo(shipmentsFromResponse.get(0), order);
        checkShipmentInfo(shipmentsFromResponse.get(1), orderFromAnotherStore);
    }
}
