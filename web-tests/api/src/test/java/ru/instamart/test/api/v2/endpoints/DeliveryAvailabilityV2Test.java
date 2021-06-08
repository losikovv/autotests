package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.v2.DeliveryAvailabilityV2Request;
import ru.instamart.api.response.v2.DeliveryAvailabilityV2Response;
import ru.instamart.api.dataprovider.RestDataProvider;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode200;

@Epic("ApiV2")
@Feature("Проверка доступности магазина по координатам")
public class DeliveryAvailabilityV2Test extends RestBase {

    @BeforeMethod
    public void testUp() {
        SessionFactory.makeSession(SessionType.API_V2_FB);
    }

    @CaseId(200)
    @Parameters({"lat", "lon"})
    @Test(dataProvider = "deliveryAvailabilityV2TestData",
            dataProviderClass = RestDataProvider.class,
            groups = {"api-instamart-regress"},
            description = "Негативный тест без указания lan или lon, или обеих")
    public void testWithoutLatAndLon(String lat, String lon) {
        final Response response = DeliveryAvailabilityV2Request.GET(lat, lon);
        checkStatusCode200(response);
        final DeliveryAvailabilityV2Response deliveryAvailabilityV2Response = response.as(DeliveryAvailabilityV2Response.class);
        assertFalse(deliveryAvailabilityV2Response.getDeliveryAvailability().isAvailable());
    }

    @CaseId(202)
    @Test(groups = {"api-instamart-regress"}, description = "Указаны координаты")
    public void testWithLatAndLon() {
        final Response response = DeliveryAvailabilityV2Request.GET("55.658228", "37.748818"); //55.658228, 37.748818
        checkStatusCode200(response);
        final DeliveryAvailabilityV2Response deliveryAvailabilityV2Response = response.as(DeliveryAvailabilityV2Response.class);
        assertTrue(deliveryAvailabilityV2Response.getDeliveryAvailability().isAvailable());
    }


}
