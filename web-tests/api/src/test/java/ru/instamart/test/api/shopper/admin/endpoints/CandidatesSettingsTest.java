package ru.instamart.test.api.shopper.admin.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.shopper.admin.ShopperAdminRequest;
import ru.instamart.api.response.shopper.admin.OperationalZoneCandidatesSettingResponse;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserManager;

import static org.testng.Assert.assertEquals;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;


@Epic("Shopper Admin Panel API")
@Feature("Endpoints")
public class CandidatesSettingsTest extends RestBase {
    @Story("Candidates settings")
    @Test(description = "Получение настроек кандидатов с валидным токеном", groups = {"api-shopper-regress"})
    public void candidatesSettings() {
        Integer zoneId = EnvironmentProperties.DEFAULT_ID_ZONE;
        SessionFactory.createSessionToken(SessionType.SHOPPER_ADMIN, UserManager.getDefaultAdmin());
        response = ShopperAdminRequest.OperationalZones.CandidatesSettings.GET(zoneId);

        checkStatusCode200(response);

        OperationalZoneCandidatesSettingResponse candidateSettings =  response.as(OperationalZoneCandidatesSettingResponse.class);

        assertEquals(candidateSettings.getOperationalZoneId(),zoneId, "Вернулся не тот id Зоны");
        checkResponseJsonSchema(response, OperationalZoneCandidatesSettingResponse.class);
    }
}
