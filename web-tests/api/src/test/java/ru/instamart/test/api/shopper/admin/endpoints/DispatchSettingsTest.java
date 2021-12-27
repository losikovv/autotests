package ru.instamart.test.api.shopper.admin.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.shopper.admin.ShopperAdminRequest;
import ru.instamart.api.response.ErrorResponse;
import ru.instamart.api.response.ErrorsListResponse;
import ru.instamart.api.response.shopper.admin.OperationalZoneDispatchSettingResponse;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;

import static org.testng.Assert.assertEquals;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;

@Epic("Shopper Admin Panel API")
@Feature("Endpoints")
public class DispatchSettingsTest extends RestBase {
    @Story("Dispatch settings")
    @CaseId(104)
    @Test(description = "Получение конфига с валидным токеном",
            groups = {"api-shopper-regress"})
    public void dispatchSettings200() {
        SessionFactory.createSessionToken(SessionType.SHOPPER_ADMIN, UserManager.getDefaultAdmin());
        response = ShopperAdminRequest.OperationalZones.DispatchSettings.GET(EnvironmentProperties.DEFAULT_ID_ZONE);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, OperationalZoneDispatchSettingResponse.class);
    }

    @Story("Dispatch settings")
    @CaseId(104)
    @Test(description = "Получение конфига с несуществующим zoneId",
            groups = {"api-shopper-regress"})
    public void dispatchSettings404() {
        SessionFactory.createSessionToken(SessionType.SHOPPER_ADMIN, UserManager.getDefaultAdmin());
        response = ShopperAdminRequest.OperationalZones.DispatchSettings.GET(9999);
        checkStatusCode404(response);
        assertEquals(response.as(ErrorsListResponse.class).getErrors().get(0).getDetail(), "OperationalZone не существует", "Error message not valid");

    }

    @Story("Dispatch settings")
    @CaseId(104)
    @Test(description = "Получение конфига с не валидным токеном",
            groups = {"api-shopper-regress"})
    public void dispatchSettings401() {
        SessionFactory.clearSession(SessionType.SHOPPER_ADMIN);
        response = ShopperAdminRequest.OperationalZones.DispatchSettings.GET(EnvironmentProperties.DEFAULT_ID_ZONE);
        checkStatusCode401(response);
        assertEquals(response.as(ErrorResponse.class).getError(), "token_missing", "Error message not valid");
    }
}
