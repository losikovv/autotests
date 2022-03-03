package ru.instamart.test.api.v1.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.enums.v2.ShippingMethodV2;
import ru.instamart.api.model.v1.LineItemV1;
import ru.instamart.api.model.v1.MergeShipmentV1;
import ru.instamart.api.request.v1.MultiretailerOrderV1Request;
import ru.instamart.api.request.v1.OrdersV1Request;
import ru.instamart.api.request.v1.ShipmentsV1Request;
import ru.instamart.api.request.v1.StoresV1Request;
import ru.instamart.api.response.v1.MergeStatusV1Response;
import ru.instamart.api.response.v1.MultiretailerOrderV1Response;
import ru.instamart.api.response.v1.ShipmentV1Response;
import ru.instamart.api.response.v1.ShippingRatesV1Response;
import ru.instamart.api.response.v2.NextDeliveriesV2Response;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.*;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode404;
import static ru.instamart.kraken.helper.DateTimeHelper.getDateFromMSK;
import static ru.instamart.kraken.util.TimeUtil.getFutureDateWithoutTime;

@Epic("ApiV1")
@Feature("Подзаказы")
public class ShipmentsV1Tests extends RestBase {
    private LineItemV1 lineItem;
    private MultiretailerOrderV1Response order;

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        if (EnvironmentProperties.SERVER.equals("production")) {
            admin.authApi();
        } else {
            apiV1.authByPhone(UserManager.getQaUser());
        }
        apiV1.changeAddress(apiV2.getAddressBySidMy(EnvironmentProperties.DEFAULT_SID), ShippingMethodV2.BY_COURIER.getMethod());
        lineItem = apiV1.addItemToCart(apiV2.getProductFromEachDepartmentInStore(EnvironmentProperties.DEFAULT_SID).get(0).getId());
    }


    @CaseId(1390)
    @Story("Окна доставки")
    @Test(groups = {"api-instamart-smoke", "api-instamart-prod"},
            description = "Получение окна доставки для подзаказа для указанного дня с существующим id")
    public void getShippingRates() {
        final Response response = ShipmentsV1Request.ShippingRates.GET(lineItem.getShipmentNumber(), getFutureDateWithoutTime(1L));
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ShippingRatesV1Response.class);
    }

    @CaseId(1391)
    @Story("Окна доставки")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"},
            description = "Получение окна доставки для несуществующего подзаказа для указанного дня")
    public void getShippingRatesForNonExistentShipment() {
        final Response response = ShipmentsV1Request.ShippingRates.GET("failedShippingNumber", getDateFromMSK());
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @CaseIDs(value = {@CaseId(1392), @CaseId(1393), @CaseId(1394), @CaseId(1395), @CaseId(1396), @CaseId(1397), @CaseId(1400), @CaseId(1398), @CaseId(1399)})
    @Story("Окна доставки")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"},
            dataProvider = "dateFormats",
            dataProviderClass = RestDataProvider.class,
            description = "Получение окна доставки для подзаказа для указанного дня с неверным форматом даты")
    public void getShippingRatesWithOtherDate(DateTimeFormatter formatter) {
        ZonedDateTime localDate = ZonedDateTime.now().plusDays(1);
        String formattedDate = localDate.format(formatter);
        final Response response = ShipmentsV1Request.ShippingRates.GET(lineItem.getShipmentNumber(), formattedDate);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ShippingRatesV1Response.class);
    }

    @CaseId(1402)
    @Story("Окна доставки")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"},
            description = "Получение ближайших окон доставки для несуществующего магазина")
    public void getNextDeliveriesForNonexistentStore() {
        final Response response = StoresV1Request.NextDeliveries.GET(0, new StoresV1Request.NextDeliveriesParams());
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @CaseIDs(value = {@CaseId(1403), @CaseId(1404), @CaseId(1405)})
    @Story("Окна доставки")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"},
            description = "Получение ближайших окон доставки с корректными данными",
            dataProvider = "nextDeliveriesParams",
            dataProviderClass = RestDataProvider.class)
    public void getNextDeliverWithAllData(StoresV1Request.NextDeliveriesParams params) {
        final Response response = StoresV1Request.NextDeliveries.GET(EnvironmentProperties.DEFAULT_SID, params);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, NextDeliveriesV2Response.class);
    }

    @Story("Заказы")
    @CaseId(43)
    @Test(description = "Получение информации о мультиритейлерном заказе",
            groups = {"api-instamart-regress", "api-instamart-prod"})
    public void getMultireteilerOrder() {
        final Response response = MultiretailerOrderV1Request.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, MultiretailerOrderV1Response.class);
        order = response.as(MultiretailerOrderV1Response.class);
    }

    @Story("Заказы")
    @CaseId(2133)
    @Test(description = "Изменение подзаказа",
            groups = {"api-instamart-regress"},
            dependsOnMethods = "getMultireteilerOrder")
    public void changeOrder() {
        OrdersV1Request.Shipment shipment = OrdersV1Request.Shipment.builder()
                .shipment(OrdersV1Request.ShipmentParams.builder()
                        .storeId(EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID)
                        .assemblyComment("Autotest-" + Generate.literalString(6))
                        .build())
                .build();
        final Response response = OrdersV1Request.PUT(order.getNumber(), order.getShipments().get(0).getNumber(), shipment);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ShipmentV1Response.class);
        compareTwoObjects(response.as(ShipmentV1Response.class).getShipment().getStoreId(), EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID);
    }

    @CaseId(1557)
    @Story("Мерж заказов")
    @Test(groups = {"api-instamart-regression", "api-instamart-prod"},
            description = "Получение статуса мержа",
            dependsOnMethods = "getMultireteilerOrder")
    public void getMergeStatus() {
        final Response response = OrdersV1Request.MergeStatus.GET(order.getNumber());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, MergeStatusV1Response.class);
        MergeShipmentV1 mergeShipment = response.as(MergeStatusV1Response.class).getShipments().get(0);
        compareTwoObjects(mergeShipment.getShipmentId(), (long) lineItem.getShipmentId());
    }

    @CaseId(1558)
    @Story("Мерж заказов")
    @Test(groups = {"api-instamart-regression", "api-instamart-prod"},
            description = "Получение статуса мержа для несуществующего заказа")
    public void getMergeStatusForNonexistentOrder() {
        final Response response = OrdersV1Request.MergeStatus.GET("failedOrderNumber");
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @CaseId(1559)
    @Story("Удаление подзаказа")
    @Test(groups = {"api-instamart-regression", "api-instamart-prod"},
            description = "Удаление существующего подзаказа",
            dependsOnMethods = {"getMultireteilerOrder", "getMergeStatus"})
    public void deleteShipment() {
        final Response response = ShipmentsV1Request.DELETE(lineItem.getShipmentNumber(), order.getToken());
        checkStatusCode200(response);
        final Response responseAfterRemoval = ShipmentsV1Request.GET(lineItem.getShipmentNumber());
        checkStatusCode404(responseAfterRemoval);
    }

    @CaseId(1560)
    @Story("Удаление подзаказа")
    @Test(groups = {"api-instamart-regression", "api-instamart-prod"},
            description = "Удаление несуществующего подзаказа без токена заказа",
            dependsOnMethods = "getMultireteilerOrder")
    public void deleteNonexistentShipment() {
        final Response response = ShipmentsV1Request.DELETE("failedShipmentNumber", order.getToken());
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }
}
