package ru.instamart.test.api.v1.contracts;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.model.v1.OperationalZoneV1;
import ru.instamart.api.request.v1.OperationalZonesV1Request;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.response.v1.OperationalZoneV1Response;
import ru.instamart.api.response.v1.OperationalZonesV1Response;
import ru.instamart.jdbc.dao.stf.OperationalZonesDao;

import java.util.List;

import static ru.instamart.api.Group.API_INSTAMART_PROD;
import static ru.instamart.api.Group.API_INSTAMART_REGRESS;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV1")
@Feature("Эндпоинты, используемые шоппер бэкендом")
public class OperationalZonesV1ContractTests extends RestBase {

    @Story("Операционные зоны")
    @TmsLink("112")
    @Test(  description = "Контрактный тест списка операционных зон",
            groups = {"api-instamart-smoke", "api-v1"})
    public void getOperationalZones() {
        final Response response = OperationalZonesV1Request.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, OperationalZonesV1Response.class);
        List<OperationalZoneV1> operationalZonesFromResponse = response.as(OperationalZonesV1Response.class).getOperationalZones();
        compareTwoObjects(operationalZonesFromResponse.size(), OperationalZonesDao.INSTANCE.getCount());
    }

    @Story("Операционные зоны")
    @TmsLink("113")
    @Test(  description = "Контрактный тест операционной зоны",
            groups = {API_INSTAMART_REGRESS, API_INSTAMART_PROD, "api-v1"},
            dataProviderClass = RestDataProvider.class,
            dataProvider = "operationalZones")
    public void getOperationalZone(OperationalZoneV1 operationalZone) {
        final Response response = OperationalZonesV1Request.GET(operationalZone.getId());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, OperationalZoneV1Response.class);
    }
}
