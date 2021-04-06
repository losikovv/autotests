package ru.instamart.tests.api.v1.contracts;

import instamart.api.common.RestBase;
import instamart.api.objects.v1.OperationalZone;
import instamart.api.requests.ApiV1Requests;
import instamart.core.testdata.dataprovider.RestDataProvider;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static instamart.api.checkpoints.InstamartApiCheckpoints.checkStatusCode200;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class OperationalZonesV1Tests extends RestBase {

    @CaseId(112)
    @Test(  description = "Контрактный тест списка операционных зон",
            groups = {"api-instamart-regress", "api-instamart-prod"})
    public void getOperationalZones() {
        Response response = ApiV1Requests.OperationalZones.GET();
        checkStatusCode200(response);
        response.then().body(matchesJsonSchemaInClasspath("schemas/api_v1/OperationalZones.json"));
    }

    @CaseId(113)
    @Test(  description = "Контрактный тест операционной зоны",
            groups = {"api-instamart-regress", "api-instamart-prod"},
            dataProviderClass = RestDataProvider.class,
            dataProvider = "operationalZones")
    public void getOperationalZone(OperationalZone operationalZone) {
        Response response = ApiV1Requests.OperationalZones.GET(operationalZone.getId());
        checkStatusCode200(response);
        response.then().body(matchesJsonSchemaInClasspath("schemas/api_v1/OperationalZone.json"));
    }
}
