package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.model.v2.AddressV2;
import ru.instamart.api.request.v2.DeliveryAvailabilityV2Request;
import ru.instamart.api.response.v2.DeliveryAvailabilityV2Response;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV2")
@Feature("Проверка доступности магазина по координатам")
public class DeliveryAvailabilityV2Test extends RestBase {

    @CaseIDs(value = {@CaseId(1478), @CaseId(1479), @CaseId(1480)})
    @Parameters({"lat", "lon"})
    @Test(dataProvider = "deliveryAvailabilityV2TestData",
            dataProviderClass = RestDataProvider.class,
            groups = {"api-instamart-regress", "api-instamart-prod", "api-v2"},
            description = "Негативный тест без указания lan или lon, или обеих")
    public void testWithoutLatAndLon(String lat, String lon) {
        final Response response = DeliveryAvailabilityV2Request.GET(lat, lon);
        checkStatusCode200(response);
        final DeliveryAvailabilityV2Response deliveryAvailabilityV2Response = response.as(DeliveryAvailabilityV2Response.class);
        assertFalse(deliveryAvailabilityV2Response.getDeliveryAvailability().isAvailable(), "Статус доставки вне зоны доставки available равен true");
    }

    @CaseId(1477)
    @Test(groups = {"api-instamart-smoke", "api-instamart-prod", "api-v2"}, description = "Указаны координаты")
    public void testWithLatAndLon() {
        AddressV2 address = apiV2.getAddressBySid(EnvironmentProperties.DEFAULT_SID);
        final Response response = DeliveryAvailabilityV2Request.GET(address.getLat().toString(), address.getLon().toString());
        checkStatusCode200(response);
        final DeliveryAvailabilityV2Response deliveryAvailabilityV2Response = response.as(DeliveryAvailabilityV2Response.class);
        assertTrue(deliveryAvailabilityV2Response.getDeliveryAvailability().isAvailable(), "Статус доставки в зоне доставки available равен false");
    }


}
