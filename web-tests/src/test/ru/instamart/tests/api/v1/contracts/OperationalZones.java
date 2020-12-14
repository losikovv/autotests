package ru.instamart.tests.api.v1.contracts;

import instamart.api.common.RestBase;
import instamart.api.objects.v1.OperationalZone;
import instamart.api.requests.ApiV1Requests;
import instamart.core.testdata.dataprovider.RestDataProvider;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static instamart.api.checkpoints.InstamartApiCheckpoints.assertStatusCode200;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class OperationalZones extends RestBase {

    @Test(  description = "Контрактный тест списка операционных зон",
            groups = "api-v2-regress")
    public void getOperationalZones() {
        Response response = ApiV1Requests.OperationalZones.GET();
        assertStatusCode200(response);
        response.then().body(matchesJsonSchemaInClasspath("schemas/OperationalZones.json"));
    }

    @Test(  description = "Контрактный тест операционной зоны",
            groups = "api-v2-regress",
            dataProviderClass = RestDataProvider.class,
            dataProvider = "operationalZones")
    public void getOperationalZone(OperationalZone operationalZone) {
        Response response = ApiV1Requests.OperationalZones.GET(operationalZone.getId());
        assertStatusCode200(response);
        response.then().body(matchesJsonSchemaInClasspath("schemas/OperationalZone.json"));
    }
}
