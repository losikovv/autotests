package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.v2.ExternalPartnersV2Request;
import ru.instamart.api.response.ErrorResponse;
import static org.testng.Assert.assertTrue;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode400;

@Epic("ApiV2")
@Feature("Получение списка банеров для SberPrime")
public class ExternalPartners  extends RestBase {

    @BeforeMethod
    public void testUp(){
        SessionFactory.makeSession(SessionType.API_V2);
    }

    @CaseId(270)
    @Test(groups = {"api-instamart-smoke", "api-instamart-prod"}, description = "Отправка запроса без store_id")
    public void testWithoutShopId(){
        final Response response = ExternalPartnersV2Request.GET("");
        checkStatusCode400(response);
        final ErrorResponse errorResponse = response.as(ErrorResponse.class);
        assertTrue(errorResponse.getErrors().getBase().equals("Отсутствует обязательный параметр 'store_id'"));
    }

    @CaseId(269)
    @Test(groups = {"api-instamart-smoke", "api-instamart-prod"}, description = "Отправка запроса c store_id")
    public void testWithShopId(){
        final Response response = ExternalPartnersV2Request.GET("1");
        checkStatusCode200(response);
    }
}
