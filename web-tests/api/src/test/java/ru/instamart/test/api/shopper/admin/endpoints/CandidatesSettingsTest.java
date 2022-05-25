package ru.instamart.test.api.shopper.admin.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.checkpoint.BaseApiCheckpoints;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.shopper.admin.ShopperAdminRequest;
import ru.instamart.api.response.shopper.admin.OperationalZoneCandidatesSettingResponse;
import ru.instamart.jdbc.dao.candidates.OperationalZoneSettingsDao;
import ru.instamart.jdbc.entity.candidates.OperationalZoneSettingsEntity;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserManager;

import static org.testng.Assert.assertEquals;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;


@Epic("Shopper Admin Panel API")
@Feature("Endpoints")
public class CandidatesSettingsTest extends RestBase {

    @BeforeMethod(alwaysRun = true)
    public void preconditions() {
        SessionFactory.createSessionToken(SessionType.SHOPPER_ADMIN, UserManager.getDefaultAdmin());
    }

    @Story("Candidates settings")
    @Test(description = "Получение настроек кандидатов с валидным токеном", groups = {"api-shopper-regress"})
    @Step("Получение настроек кандидатов для региона")
    public void getCandidatesSettings() {
        Integer zoneId = EnvironmentProperties.DEFAULT_ID_ZONE;
       final Response responseGET = ShopperAdminRequest.OperationalZones.CandidatesSettings.GET(zoneId);

        checkStatusCode200(responseGET);

        OperationalZoneCandidatesSettingResponse candidateSettings = responseGET.as(OperationalZoneCandidatesSettingResponse.class);

        assertEquals(candidateSettings.getOperationalZoneId(), zoneId, "Вернулся не тот id Зоны");
        checkResponseJsonSchema(responseGET, OperationalZoneCandidatesSettingResponse.class);
    }

    @Story("Candidates settings")
    @Test(description = "Сохранение настроек кандидатов с валидным токеном", groups = {"api-shopper-regress"})
    @Step("Сохранение настроек кандидатов для региона")
    public void putCandidatesSettings() {
        Integer zoneId = EnvironmentProperties.DEFAULT_ID_ZONE;
        Integer surgedShiftThreshold = 300;
        Integer normalShiftThreshold = 20;

        final Response responsePut = ShopperAdminRequest.OperationalZones.CandidatesSettings.PUT(zoneId, normalShiftThreshold, surgedShiftThreshold);
        checkStatusCode200(responsePut);

        OperationalZoneSettingsEntity opZonesFromDb = OperationalZoneSettingsDao.INSTANCE.getOperationalZoneSettings(zoneId);
        final SoftAssert softAssert = new SoftAssert();
        BaseApiCheckpoints.compareTwoObjects(opZonesFromDb.getOperationalZoneId(), zoneId, softAssert);
        BaseApiCheckpoints.compareTwoObjects(opZonesFromDb.getNormalShiftThreshold(), normalShiftThreshold, softAssert);
        BaseApiCheckpoints.compareTwoObjects(opZonesFromDb.getSurgedShiftThreshold(), surgedShiftThreshold, softAssert);
    }
}
