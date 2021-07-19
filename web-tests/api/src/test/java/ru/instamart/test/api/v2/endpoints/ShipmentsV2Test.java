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
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.v2.ShipmentsV2Request;
import ru.instamart.api.response.ErrorResponse;
import ru.instamart.api.response.v2.DeliveryWindowsV2Response;
import ru.instamart.api.response.v2.ServiceRateV2Response;
import ru.instamart.kraken.testdata.pagesdata.EnvironmentData;

import java.time.LocalDate;

import static org.testng.Assert.assertNotNull;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.*;

@Epic("ApiV2")
@Feature("Оформление заказа")
public class ShipmentsV2Test extends RestBase {

    private final String today = LocalDate.now().toString();

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        SessionFactory.makeSession(SessionType.API_V2_FB);
        apiV2.fillCart(SessionFactory.getSession(SessionType.API_V2_FB).getUserData(), EnvironmentData.INSTANCE.getDefaultSid());
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
        ErrorResponse error = response.as(ErrorResponse.class);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(error.getErrors().getBase(), "Доставка не существует");
        softAssert.assertEquals(error.getErrorMessages().get(0).getField(), "base");
        softAssert.assertEquals(error.getErrorMessages().get(0).getMessage(), "Доставка не существует");
        softAssert.assertEquals(error.getErrorMessages().get(0).getHumanMessage(), "Доставка не существует");
        softAssert.assertAll();
    }

    @CaseId(354)
    @Story("Детализацию стоимости доставки")
    @Test(groups = {"api-instamart-regress"},
            dataProvider = "shipmentsServiceRateData",
            dataProviderClass = RestDataProvider.class,
            description = "Детализацию стоимости доставки с несуществующим shipmentNumber и отсутствующим delivery_window_id")
    public void serviceRate500(String shipmentNumber, String deliveryWindowId) {
        response = ShipmentsV2Request.ServiceRate.GET(shipmentNumber, deliveryWindowId);
        checkStatusCode500(response);
    }

    @CaseId(353)
    @Story("Детализацию стоимости доставки")
    @Test(groups = {"api-instamart-regress"},
            description = "Детализацию стоимости доставки с существующим shipmentNumber и отсутствующим delivery_window_id")
    public void serviceRate404() {
        response = ShipmentsV2Request.ServiceRate.GET(apiV2.getShipmentsNumber(), null);
        checkStatusCode400(response);
        ErrorResponse error = response.as(ErrorResponse.class);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(error.getErrors().getBase(), "Отсутствует обязательный параметр 'delivery_window_id'");
        softAssert.assertEquals(error.getErrorMessages().get(0).getField(), "base");
        softAssert.assertEquals(error.getErrorMessages().get(0).getMessage(), "Отсутствует обязательный параметр 'delivery_window_id'");
        softAssert.assertEquals(error.getErrorMessages().get(0).getHumanMessage(), "Отсутствует обязательный параметр 'delivery_window_id'");
        softAssert.assertAll();
    }

    @CaseId(355)
    @Test(groups = {"api-instamart-regress"},
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
}
