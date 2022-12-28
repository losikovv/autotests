package ru.instamart.test.api.shopper.admin.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.checkpoint.BaseApiCheckpoints;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.shopper.admin.ShopperAdminRequest;
import ru.instamart.api.response.shopper.admin.OperationalZoneEstimatorSettingResponse;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserManager;

import static ru.instamart.api.Group.API_SHOPPER_REGRESS;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("Shopper Admin Panel API")
@Feature("Endpoints")
public class EstimatorSettingsTest extends RestBase {

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        SessionFactory.createSessionToken(SessionType.SHOPPER_ADMIN, UserManager.getDefaultAdminOld());
    }

    @Story("EstimatorSettings")
    @Test(description = "Получение настроек сервиса RES для региона", groups = {API_SHOPPER_REGRESS})
    public void getEstimatorSettings() {
        Response response = ShopperAdminRequest.OperationalZones.EstimatorSettings.GET(EnvironmentProperties.DEFAULT_ID_ZONE);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, OperationalZoneEstimatorSettingResponse.class);
    }

    @Story("EstimatorSettings")
    @Test(description = "Изменение настроек сервиса RES для региона", groups = {API_SHOPPER_REGRESS})
    public void PutRegionEstimatorSettings() {
        var parameters = ShopperAdminRequest.PutRegionEstimatorSettings.builder()
                .settingsResRegion(ShopperAdminRequest.SettingsResRegion.builder()
                        .estimatorRegionParameters(ShopperAdminRequest.EstimatorRegionParameters.builder()
                                .operationalZoneId(EnvironmentProperties.DEFAULT_ID_ZONE)
                                .correctionFactor(Math.floor(Math.random() * 10))
                                .increasingFactor(RandomUtils.nextInt(10, 100))
                                .minimumSegmentLength(RandomUtils.nextInt(10, 100))
                                .build())
                        .build())
                .build();

        Response response = ShopperAdminRequest.OperationalZones.EstimatorSettings.PUT(parameters);
        checkStatusCode200(response);

        Response responseGet = ShopperAdminRequest.OperationalZones.EstimatorSettings.GET(EnvironmentProperties.DEFAULT_ID_ZONE);

        BaseApiCheckpoints.compareTwoObjects(responseGet.as(OperationalZoneEstimatorSettingResponse.class)
                .getOperationalZoneEstimatorSettings().getCorrectionFactor(),
                parameters.getSettingsResRegion().getEstimatorRegionParameters().getCorrectionFactor());
        BaseApiCheckpoints.compareTwoObjects(responseGet.as(OperationalZoneEstimatorSettingResponse.class)
                .getOperationalZoneEstimatorSettings().getIncreasingFactor(),
                parameters.getSettingsResRegion().getEstimatorRegionParameters().getIncreasingFactor());
        BaseApiCheckpoints.compareTwoObjects(responseGet.as(OperationalZoneEstimatorSettingResponse.class)
                .getOperationalZoneEstimatorSettings().getMinimumSegmentLength(),
                parameters.getSettingsResRegion().getEstimatorRegionParameters().getMinimumSegmentLength());
    }
}
