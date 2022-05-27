package ru.instamart.test.api.shopper.admin.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.checkpoint.BaseApiCheckpoints;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.response.shopper.admin.OperationalZoneWorkflowSettingsResponse;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserManager;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.request.shopper.admin.ShopperAdminRequest.*;

@Epic("Shopper Admin Panel API")
@Feature("Endpoints")
public class WorkflowSettingsTest extends RestBase {

    private PutWorkflowSettings parameters;

    @BeforeClass (alwaysRun = true)
    public void preconditions() {
        SessionFactory.createSessionToken(SessionType.SHOPPER_ADMIN, UserManager.getDefaultAdmin());
    }

    @Story("WorkflowSettings")
    @Test(description = "Получение настроек сервиса Workflow для региона", groups = {"api-shopper-regress"})
    public void getWorkflowSettings() {
        Response response = OperationalZones.WorkflowSettings.GET(EnvironmentProperties.DEFAULT_ID_ZONE);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, OperationalZoneWorkflowSettingsResponse.class);
    }

    @Story("WorkflowSettings")
    @Test(description = "Изменение настроек сервиса Workflow для региона", groups = {"api-shopper-regress"})
    public void putWorkflowSettings() {
        parameters = PutWorkflowSettings.builder()
                .settings(Settings.builder()
                        .workflowParameters(WorkflowParameters.builder()
                                .operationalZoneId(EnvironmentProperties.DEFAULT_ID_ZONE)
                                .increasingLateAssemblyPercentage(Math.floor(Math.random() * 10))
                                .segmentAssemblyTardinessSec((int) (Math.random() * 100)+1)
                                .serverOfferStorageTimeSec((int) (Math.random() * 100)+1)
                                .timeToAcceptOfferSec((int) (Math.random() * 100)+1)
                                .timeoutSegmentPassToClientSec((int) (Math.random() * 100)+1)
                                .build())
                        .build())
                .build();

        Response response = OperationalZones.WorkflowSettings.PUT(parameters);
        checkStatusCode200(response);

        Response responseGet = OperationalZones.WorkflowSettings.GET(EnvironmentProperties.DEFAULT_ID_ZONE);

        BaseApiCheckpoints.compareTwoObjects(responseGet.as(OperationalZoneWorkflowSettingsResponse.class)
                        .getOperationalZoneWorkflowSettings().getIncreasingLateAssemblyPercentage(),
                parameters.getSettings().getWorkflowParameters().getIncreasingLateAssemblyPercentage());
        BaseApiCheckpoints.compareTwoObjects(responseGet.as(OperationalZoneWorkflowSettingsResponse.class)
                        .getOperationalZoneWorkflowSettings().getSegmentAssemblyTardinessSec(),
                parameters.getSettings().getWorkflowParameters().getSegmentAssemblyTardinessSec());
        BaseApiCheckpoints.compareTwoObjects(responseGet.as(OperationalZoneWorkflowSettingsResponse.class)
                        .getOperationalZoneWorkflowSettings().getServerOfferStorageTimeSec(),
                parameters.getSettings().getWorkflowParameters().getServerOfferStorageTimeSec());
        BaseApiCheckpoints.compareTwoObjects(responseGet.as(OperationalZoneWorkflowSettingsResponse.class)
                        .getOperationalZoneWorkflowSettings().getTimeToAcceptOfferSec(),
                parameters.getSettings().getWorkflowParameters().getTimeToAcceptOfferSec());
        BaseApiCheckpoints.compareTwoObjects(responseGet.as(OperationalZoneWorkflowSettingsResponse.class)
                        .getOperationalZoneWorkflowSettings().getTimeoutSegmentPassToClientSec(),
                parameters.getSettings().getWorkflowParameters().getTimeoutSegmentPassToClientSec());
    }
}
