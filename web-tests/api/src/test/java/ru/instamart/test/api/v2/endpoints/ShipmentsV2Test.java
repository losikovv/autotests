package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.enums.v2.StateV2;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.AddressV2;
import ru.instamart.api.request.v2.ShipmentsV2Request;
import ru.instamart.api.request.v2.StoresV2Request;
import ru.instamart.api.response.v2.*;
import ru.instamart.kraken.config.EnvironmentProperties;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static org.testng.Assert.*;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.errorAssert;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.*;
import static ru.instamart.kraken.helper.DateTimeHelper.getDateFromMSK;

@Epic("ApiV2")
@Feature("Оформление заказа")
public class ShipmentsV2Test extends RestBase {

    private final String today = getDateFromMSK().toString();

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        SessionFactory.makeSession(SessionType.API_V2_FB);
        apiV2.fillCart(SessionFactory.getSession(SessionType.API_V2_FB).getUserData(), EnvironmentProperties.DEFAULT_SID);
    }

    @CaseId(339)
    @Story("Получить время доставки")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"},
            description = "Получить время доставки с существующим id")
    public void getDeliveryWindows200() {
        Integer shipmentId = apiV2.getShippingWithOrder().getId();
        Response response = ShipmentsV2Request.DeliveryWindows.GET(shipmentId.toString(), today);
        checkStatusCode200(response);
        assertNotNull(response.as(DeliveryWindowsV2Response.class).getDeliveryWindows(), "Нет окон доставки");
    }

    @CaseId(340)
    @Story("Получить время доставки")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"},
            description = "Получить время доставки с несуществующим id")
    public void getDeliveryWindows404() {
        Response response = ShipmentsV2Request.DeliveryWindows.GET("failedShipmentId", today);
        checkStatusCode404(response);
        errorAssert(response, "Доставка не существует");
    }

    @Deprecated
    @Story("Детализацию стоимости доставки")
    @Test(groups = {},
            dataProvider = "shipmentsServiceRateData",
            dataProviderClass = RestDataProvider.class,
            description = "Детализацию стоимости доставки с несуществующим shipmentNumber и отсутствующим delivery_window_id")
    public void serviceRate500(String shipmentNumber, String deliveryWindowId) {
        response = ShipmentsV2Request.ServiceRate.GET(shipmentNumber, deliveryWindowId);
        checkStatusCode500(response);
    }

    @Deprecated
    @Story("Детализацию стоимости доставки")
    @Test(groups = {},
            description = "Детализацию стоимости доставки с существующим shipmentNumber и отсутствующим delivery_window_id")
    public void serviceRate404() {
        response = ShipmentsV2Request.ServiceRate.GET(apiV2.getShipmentsNumber(), null);
        checkStatusCode400(response);
        errorAssert(response, "Отсутствует обязательный параметр 'delivery_window_id'");
    }

    @Deprecated
    @Story("Детализацию стоимости доставки")
    @Test(groups = {},
            description = "Детализацию стоимости доставки с существующим shipmentNumber и delivery_window_id")
    public void serviceRateWithDeliveryWindow200() {
        Integer deliveryWindowsID = apiV2.getOpenOrder().getShipments().get(0).getNextDeliveries().get(0).getId();
        response = ShipmentsV2Request.ServiceRate.GET(apiV2.getShipmentsNumber(), deliveryWindowsID.toString());
        checkStatusCode200(response);
        ServiceRateV2Response serviceRateV2Response = response.as(ServiceRateV2Response.class);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(serviceRateV2Response.getServiceRate().getTotal(), "total is empty");
        softAssert.assertFalse(serviceRateV2Response.getServiceRate().getRates().isEmpty(), "rates is empty");
        softAssert.assertAll();
    }

    @Deprecated
    @Story("Получить окно доставки для подзаказа")
    @Test(groups = {},
            description = "Получить окно доставки для подзаказа с существующим id")
    public void deliveryWindow() {
        Integer shipmentId = apiV2.getOpenOrder().getShipments().get(0).getId();
        response = ShipmentsV2Request.DeliveryWindows.GET(shipmentId.toString(), "");
        checkStatusCode200(response);
        DeliveryWindowsV2Response deliveryWindowsV2 = response.as(DeliveryWindowsV2Response.class);
        assertNotEquals(deliveryWindowsV2.getDeliveryWindows().size(), 0, "delivery windows is empty");
    }

    @Deprecated
    @Story("Получить окно доставки для подзаказа")
    @Test(groups = {},
            description = "Получить окно доставки для подзаказа с несуществующим id")
    public void deliveryWindow404() {
        response = ShipmentsV2Request.DeliveryWindows.GET("", today);
        checkStatusCode500(response);
    }

    @Deprecated
    @Story("Получить окно доставки для подзаказа")
    @Test(groups = {},
            description = "Получить окно доставки для подзаказа с существующим id и указанием необязательного параметра date")
    public void deliveryWindowWithDate200() {
        Integer shipmentId = apiV2.getOpenOrder().getShipments().get(0).getId();
        response = ShipmentsV2Request.DeliveryWindows.GET(shipmentId.toString(), today);
        checkStatusCode200(response);
        DeliveryWindowsV2Response deliveryWindowsV2 = response.as(DeliveryWindowsV2Response.class);
        assertNotEquals(deliveryWindowsV2.getDeliveryWindows().size(), 0, "delivery windows is empty");
    }

    @Deprecated
    @Story("Получить окно доставки для подзаказа")
    @Test(groups = {},
            description = "Получить окно доставки для подзаказа с существующим id и указанием необязательного параметра date")
    public void deliveryWindowWithoutDate200() {
        Integer shipmentId = apiV2.getOpenOrder().getShipments().get(0).getId();
        response = ShipmentsV2Request.DeliveryWindows.GET(shipmentId.toString(), null);
        checkStatusCode200(response);
        DeliveryWindowsV2Response deliveryWindowsV2 = response.as(DeliveryWindowsV2Response.class);
        assertNotEquals(deliveryWindowsV2.getDeliveryWindows().size(), 0, "delivery windows is empty");
    }

    @Deprecated
    @Story("Получить окно доставки для подзаказа")
    @Test(groups = {},
            dataProvider = "dateFormats",
            dataProviderClass = RestDataProvider.class,
            description = "Получить окно доставки для подзаказа с существующим id и указанием необязательного параметра date")
    public void deliveryWindowWithIncorrectDate200(DateTimeFormatter formatter) {
        Integer shipmentId = apiV2.getOpenOrder().getShipments().get(0).getId();
        ZonedDateTime localDate = ZonedDateTime.now().plusDays(1);
        String formattedDate = localDate.format(formatter);
        response = ShipmentsV2Request.DeliveryWindows.GET(shipmentId.toString(), formattedDate);
        checkStatusCode200(response);
        DeliveryWindowsV2Response deliveryWindowsV2 = response.as(DeliveryWindowsV2Response.class);
        assertNotEquals(deliveryWindowsV2.getDeliveryWindows().size(), 0, "delivery windows is empty");
    }

    @CaseId(363)
    @Story("Получить окно доставки для подзаказа для указанного дня")
    @Test(groups = {"api-instamart-regress"},
            description = "Получить окно доставки для подзаказа для указанного дня с существующим id")
    public void shippingRates200() {
        response = ShipmentsV2Request.ShippingRates.GET(apiV2.getShipmentsNumber(), getDateFromMSK().plusDays(1).toString());
        checkStatusCode200(response);
        ShippingRatesV2Response shippingRatesV2Response = response.as(ShippingRatesV2Response.class);
        assertNotEquals(shippingRatesV2Response.getShippingRates().size(), 0, "delivery rates is empty");
    }

    @CaseId(364)
    @Story("Получить окно доставки для подзаказа для указанного дня")
    @Test(groups = {"api-instamart-regress"},
            description = "Получить окно доставки для подзаказа для указанного дня с несуществующим id")
    public void shippingRates404() {
        response = ShipmentsV2Request.ShippingRates.GET("failedShippingNumber", today);
        checkStatusCode404(response);
        errorAssert(response, "Доставка не существует");
    }

    @Deprecated
    @Story("Получить окно доставки для подзаказа для указанного дня")
    @Test(groups = {},
            description = "Получить окно доставки для подзаказа для указанного дня с существующим id и указанием необязательного параметра date")
    public void shippingRatesWithDate200() {
        String localDate = LocalDate.now().plusDays(1).toString();
        response = ShipmentsV2Request.ShippingRates.GET(apiV2.getShipmentsNumber(), localDate);
        checkStatusCode200(response);
        ShippingRatesV2Response shippingRatesV2Response = response.as(ShippingRatesV2Response.class);
        assertNotEquals(shippingRatesV2Response.getShippingRates().size(), 0, "delivery rates is empty");
    }

    @Deprecated
    @Story("Получить окно доставки для подзаказа для указанного дня")
    @Test(groups = {},
            description = "Получить окно доставки для подзаказа для указанного дня с существующим id и без указанием необязательного параметра date")
    public void shippingRatesWithoutDate200() {
        response = ShipmentsV2Request.ShippingRates.GET(apiV2.getShipmentsNumber(), null);
        checkStatusCode200(response);
        ShippingRatesV2Response shippingRatesV2Response = response.as(ShippingRatesV2Response.class);
        assertNotEquals(shippingRatesV2Response.getShippingRates().size(), 0, "delivery rates is empty");
        final SoftAssert softAssert = new SoftAssert();
        shippingRatesV2Response.getShippingRates().forEach(shipRate -> {
            assertNotNull(shipRate.getTotalValue(), "total_value is null");
            assertNotNull(shipRate.getDeliveryWindow().getId(), "id delivery Window is null");
            assertNotNull(shipRate.getDeliveryWindow().getStartsAt(), "start at is null");
            assertNotNull(shipRate.getDeliveryWindow().getEndsAt(), "ends at is null");
            assertNotNull(shipRate.getDeliveryWindow().getKind(), "kind is null");
        });
        List<String> availableDays = shippingRatesV2Response.getMeta().getAvailableDays();
        IntStream.range(0, availableDays.size())
                .forEach(index -> softAssert.assertEquals(availableDays.get(index), getDateFromMSK().plusDays(index).toString(), "Невалидное значение"));
        softAssert.assertAll();
    }

    @CaseId(367)
    @Story("Получить окно доставки для подзаказа для указанного дня")
    @Test(groups = {"api-instamart-regress"},
            dataProvider = "dateFormats",
            dataProviderClass = RestDataProvider.class,
            description = "Получить окно доставки для подзаказа для указанного дня с существующим id и без указанием необязательного параметра date")
    public void shippingRatesWithOtherDate200(DateTimeFormatter formatter) {
        ZonedDateTime localDate = ZonedDateTime.now().plusDays(1);
        String formattedDate = localDate.format(formatter);
        response = ShipmentsV2Request.ShippingRates.GET(apiV2.getShipmentsNumber(), formattedDate);
        ShippingRatesV2Response shippingRatesV2Response = response.as(ShippingRatesV2Response.class);
        assertNotEquals(shippingRatesV2Response.getShippingRates().size(), 0, "delivery windows is empty");
    }

    @CaseId(368)
    @Story("Получить ближайшие окна доставки")
    @Test(groups = {"api-instamart-regress"},
            description = "Получить ближайшие окна доставки с существующим id")
    public void nextDeliver200() {
        response = StoresV2Request.NextDeliveries.GET(EnvironmentProperties.DEFAULT_SID);
        NextDeliveriesV2Response nextDeliveriesV2Response = response.as(NextDeliveriesV2Response.class);
        assertNotEquals(nextDeliveriesV2Response.getNextDeliveries().size(), 0, "next delivery is empty");
    }

    @CaseId(369)
    @Story("Получить ближайшие окна доставки")
    @Test(groups = {"api-instamart-regress"},
            description = "Получить ближайшие окна доставки с несуществующим id")
    public void nextDeliver404() {
        response = StoresV2Request.NextDeliveries.GET(0);
        checkStatusCode404(response);
        errorAssert(response, "Магазин не существует");
    }

    @CaseId(370)
    @Story("Получить ближайшие окна доставки")
    @Test(groups = {"api-instamart-regress"},
            description = "Получить ближайшие окна доставки со всеми необязательными полями с корректными данными")
    public void nextDeliverWithAllData() {
        AddressV2 address = apiV2.getAddressBySid(EnvironmentProperties.DEFAULT_SID);

        Map<String, String> params = new HashMap<>();
        params.put("cargo", "false");
        params.put("shipping_method", "by_courier");
        params.put("lat", address.getLat().toString());
        params.put("lon", address.getLon().toString());

        response = StoresV2Request.NextDeliveries.GET(EnvironmentProperties.DEFAULT_SID, params);
        checkStatusCode200(response);
        NextDeliveriesV2Response nextDeliveriesV2Response = response.as(NextDeliveriesV2Response.class);
        assertNotEquals(nextDeliveriesV2Response.getNextDeliveries().size(), 0, "next delivery is empty");
    }

    @CaseId(371)
    @Story("Получить ближайшие окна доставки")
    @Test(groups = {"api-instamart-regress"},
            description = "Получить ближайшие окна доставки со всеми необязательными полями с корректными данными")
    public void nextDeliverWithAllIncorrectData() {
        Map<String, String> params = new HashMap<>();
        params.put("cargo", "notValidData");
        params.put("shipping_method", "notValidData");
        params.put("lat", "notValidData");
        params.put("lon", "notValidData");

        response = StoresV2Request.NextDeliveries.GET(EnvironmentProperties.DEFAULT_SID, params);
        checkStatusCode200(response);
        NextDeliveriesV2Response nextDeliveriesV2Response = response.as(NextDeliveriesV2Response.class);
        assertEquals(nextDeliveriesV2Response.getNextDeliveries().size(), 0, "next delivery is not empty");
    }

    @CaseId(785)
    @Story("Получения статуса шипмента")
    @Test(groups = {"api-instamart-regress"},
            description = "Получения статуса шипмента с существующим shipmentNumber")
    public void getShipmentState200() {
        String number = apiV2.getShipmentsNumber();
        final Response response = ShipmentsV2Request.State.GET(number);
        checkStatusCode200(response);
        assertEquals(response.as(StateV2Response.class).getState(), StateV2.PENDING.getValue(), "State shipment not mismatch");
    }

    @CaseId(786)
    @Story("Получения статуса шипмента")
    @Test(groups = {"api-instamart-regress"},
            description = "Получения статуса шипмента с несуществующим shipmentNumber")
    public void getShipmentState404() {
        final Response response = ShipmentsV2Request.State.GET("failedShipmentNumber");
        checkStatusCode404(response);
        errorAssert(response, "Доставка не существует");
    }

    @CaseId(470)
    @Story("Получение списка возможных проблем для отзыва о заказе")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение списка возможных проблем для отзыва о существующем заказе")
    public void getListIssues200() {
        String shipmentsNumber = apiV2.getShipmentsNumber();
        final Response response = ShipmentsV2Request.ReviewIssues.GET(shipmentsNumber);
        checkStatusCode200(response);
        ReviewIssuesV2Response reviewIssuesV2Response = response.as(ReviewIssuesV2Response.class);
        assertFalse(reviewIssuesV2Response.getReviewIssues().isEmpty(), "list issues is empty");
    }

    @CaseId(471)
    @Story("Получение списка возможных проблем для отзыва о заказе")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение списка возможных проблем для отзыва о не существующем заказе")
    public void getListIssues404() {
        final Response response = ShipmentsV2Request.ReviewIssues.GET("failedShipmentNumber");
        checkStatusCode404(response);
        errorAssert(response, "Доставка не существует");
    }
}
