package ru.instamart.test.api.shopper.admin.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.shopper.admin.ShopperAdminRequest;
import ru.instamart.api.response.shopper.admin.OperationalZoneWorkflowSettingsResponse;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserManager;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("Shopper Admin Panel API")
@Feature("Endpoints")
public class WorkflowSettingsTest extends RestBase {

    @BeforeClass (alwaysRun = true)
    public void preconditions() {
        SessionFactory.createSessionToken(SessionType.SHOPPER_ADMIN, UserManager.getDefaultAdmin());
    }

    @Story("WorkflowSettings")
    @Test(description = "Получение настроек сервиса Workflow", groups = {"api-shopper-regress"})
    public void getWorkflowSettings() {
        response = ShopperAdminRequest.OperationalZones.WorkflowSettings.GET(EnvironmentProperties.DEFAULT_ID_ZONE);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, OperationalZoneWorkflowSettingsResponse.class);
    }
}
