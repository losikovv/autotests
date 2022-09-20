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
import ru.instamart.api.response.v1.OperationalZonesV1Response;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.enums.Server;
import ru.instamart.kraken.listener.Skip;
import ru.sbermarket.qase.annotation.CaseId;

import static org.testng.Assert.assertEquals;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;

@Epic("ApiV1")
@Feature("Регионы (операционные зоны)")
public class OperationalZonesV1Test extends RestBase {
    private Integer zoneId;

    @BeforeMethod(alwaysRun = true)
    public void preconditions() {
        admin.authApi();
    }

    @CaseId(1505)
    @Skip(onServer = Server.STAGING) //TODO: на стейджах подключен КЛАДР и не регион сравнивается с ним. Тест падает
    @Story("Создание региона (операционной зоны)")
    @Test(description = "Успешное создание региона (операционной зоны)",
            groups = {"api-instamart-regress", "api-v1"})
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
    @Skip(onServer = Server.STAGING) //TODO: на стейджах подключен КЛАДР и не регион сравнивается с ним. Тест падает
    @Story("Создание региона (операционной зоны)")
    @Test(description = "Создание региона с некорректным названием",
            groups = {"api-instamart-regress", "api-v1"},
            dataProviderClass = RestDataProvider.class,
            dataProvider = "negativeOperationalZonesNames")
    public void createOperationalZoneWithWrongName(Integer statusCode, String name) {
        Response response = OperationalZonesV1Request.POST(name);

        checkStatusCode(response, statusCode);
    }

    @CaseId(1507)
    @Skip(onServer = Server.STAGING) //TODO: на стейджах подключен КЛАДР и не регион сравнивается с ним. Тест падает
    @Story("Редактирование региона (операционной зоны)")
    @Test(description = "Успешное редактирование региона (операционной зоны)",
            groups = {"api-instamart-regress", "api-v1"},
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
    @Skip(onServer = Server.STAGING) //TODO: на стейджах подключен КЛАДР и не регион сравнивается с ним. Тест падает
    @Story("Редактирование региона (операционной зоны)")
    @Test(description = "Редактирование региона с некорректным названием",
            groups = {"api-instamart-regress", "api-v1"},
            dataProviderClass = RestDataProvider.class,
            dataProvider = "negativeOperationalZonesNames",
            dependsOnMethods = "createOperationalZone")
    public void updateOperationalZoneWithWrongName(Integer statusCode, String name) {
        Response response = OperationalZonesV1Request.PUT(zoneId, name);

        checkStatusCode(response, statusCode);
    }

    @CaseId(1509)
    @Skip(onServer = Server.STAGING) //TODO: на стейджах подключен КЛАДР и не регион сравнивается с ним. Тест падает
    @Story("Редактирование региона (операционной зоны)")
    @Test(description = "Редактирование несуществующего региона",
            groups = {"api-instamart-regress", "api-v1"})
    public void updateUnknownOperationalZone() {
        Response response = OperationalZonesV1Request.PUT(0, "test");

        checkStatusCode404(response);
    }

    @CaseId(2514)
    @Story("Список регионов (операционные зоны)")
    @Test(description = "Получение списка регионов",
            groups = {"api-instamart-regress", "api-v1"})
    public void getOperationalZones() {
        final Response response = OperationalZonesV1Request.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, OperationalZonesV1Response.class);
    }
}
