package ru.instamart.test.api.v1.contracts;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.model.v1.OperationalZoneV1;
import ru.instamart.api.request.v1.OperationalZonesV1Request;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.response.v1.OfferV1Response;
import ru.instamart.api.response.v1.OperationalZoneV1Response;
import ru.instamart.api.response.v1.OperationalZonesV1Response;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV1")
@Feature("Эндпоинты, используемые шоппер бэкендом")
public class OperationalZonesV1Tests extends RestBase {

    @Story("Операционные зоны")
    @CaseId(112)
    @Test(  description = "Контрактный тест списка операционных зон",
            groups = {"api-instamart-regress", "api-instamart-prod"})
    public void getOperationalZones() {
        Response response = OperationalZonesV1Request.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, OperationalZonesV1Response.class);
    }

    @Story("Операционные зоны")
    @CaseId(113)
    @Test(  description = "Контрактный тест операционной зоны",
            groups = {"api-instamart-regress", "api-instamart-prod"},
            dataProviderClass = RestDataProvider.class,
            dataProvider = "operationalZones")
    public void getOperationalZone(OperationalZoneV1 operationalZone) {
        Response response = OperationalZonesV1Request.GET(operationalZone.getId());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, OperationalZoneV1Response.class);
    }
}
