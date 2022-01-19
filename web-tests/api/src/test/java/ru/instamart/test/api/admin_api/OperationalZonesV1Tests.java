package ru.instamart.test.api.admin_api;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.model.v1.OperationalZoneV1;
import ru.instamart.api.request.v1.OperationalZonesV1Request;
import ru.instamart.api.response.v1.OperationalZoneV1Response;
import ru.instamart.kraken.data.Generate;
import ru.sbermarket.qase.annotation.CaseId;

import static org.testng.Assert.assertEquals;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;

@Epic("ApiV1")
@Feature("Регионы (операционные зоны)")
public class OperationalZonesV1Tests extends RestBase {
    private Integer zoneId;

    @BeforeMethod(alwaysRun = true)
    public void preconditions() {
        admin.authAdminApi();
    }

    @CaseId(1505)
    @Story("Создание региона (операционной зоны)")
    @Test(description = "Успешное создание региона (операционной зоны)",
            groups = {"api-instamart-regress"})
    public void createOperationalZone() {
        String name = "тест-" + Generate.literalCyrillicString(10);
        Response response = OperationalZonesV1Request.POST(name);

        checkStatusCode200(response);

        OperationalZoneV1 operationalZone = response.as(OperationalZoneV1Response.class).getOperationalZone();

        assertEquals(operationalZone.getName(), name);
        checkResponseJsonSchema(response, OperationalZoneV1Response.class);

        zoneId = operationalZone.getId();
    }

    @CaseId(1506)
    @Story("Создание региона (операционной зоны)")
    @Test(description = "Создание региона с некорректным названием",
            groups = {"api-instamart-regress"},
            dataProviderClass = RestDataProvider.class,
            dataProvider = "negativeOperationalZonesNames")
    public void createOperationalZoneWithWrongName(Integer statusCode, String name) {
        Response response = OperationalZonesV1Request.POST(name);

        checkStatusCode(response, statusCode);
    }

    @CaseId(1507)
    @Story("Редактирование региона (операционной зоны)")
    @Test(description = "Успешное редактирование региона (операционной зоны)",
            groups = {"api-instamart-regress"},
            dependsOnMethods = "createOperationalZone")
    public void updateOperationalZone() {
        String name = "тест-" + Generate.literalCyrillicString(10);
        Response response = OperationalZonesV1Request.PUT(zoneId, name);

        checkStatusCode200(response);

        OperationalZoneV1 operationalZone = response.as(OperationalZoneV1Response.class).getOperationalZone();

        assertEquals(operationalZone.getName(), name);
        assertEquals(operationalZone.getId(), zoneId);
        checkResponseJsonSchema(response, OperationalZoneV1Response.class);
    }

    @CaseId(1508)
    @Story("Редактирование региона (операционной зоны)")
    @Test(description = "Редактирование региона с некорректным названием",
            groups = {"api-instamart-regress"},
            dataProviderClass = RestDataProvider.class,
            dataProvider = "negativeOperationalZonesNames")
    public void updateOperationalZoneWithWrongName(Integer statusCode, String name) {
        Response response = OperationalZonesV1Request.PUT(1, name);

        checkStatusCode(response, statusCode);
    }

    @CaseId(1509)
    @Story("Редактирование региона (операционной зоны)")
    @Test(description = "Редактирование несуществующего региона",
            groups = {"api-instamart-regress"})
    public void updateUnknownOperationalZone() {
        Response response = OperationalZonesV1Request.PUT(0, "test");

        checkStatusCode404(response);
    }
}
