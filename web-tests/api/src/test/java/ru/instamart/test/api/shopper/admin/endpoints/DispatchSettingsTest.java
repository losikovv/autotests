package ru.instamart.test.api.shopper.admin.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomUtils;
import org.testng.annotations.Test;
import ru.instamart.api.checkpoint.BaseApiCheckpoints;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.shopper.admin.StoreDispatchSettingsV1;
import ru.instamart.api.request.shopper.admin.ShopperAdminRequest;
import ru.instamart.api.response.ErrorResponse;
import ru.instamart.api.response.ErrorsListResponse;
import ru.instamart.api.response.shopper.admin.OperationalZoneDispatchSettingResponse;
import ru.instamart.api.response.shopper.admin.StoreDispatchSettingsResponse;
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
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void dispatchSettings200() {
        SessionFactory.createSessionToken(SessionType.SHOPPER_ADMIN, UserManager.getDefaultAdmin());
        response = ShopperAdminRequest.OperationalZones.DispatchSettings.GET(EnvironmentProperties.DEFAULT_ID_ZONE);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, OperationalZoneDispatchSettingResponse.class);
    }

    @Story("Dispatch settings")
    @CaseId(104)
    @Test(description = "Получение конфига с несуществующим zoneId",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void dispatchSettings404() {
        SessionFactory.createSessionToken(SessionType.SHOPPER_ADMIN, UserManager.getDefaultAdmin());
        response = ShopperAdminRequest.OperationalZones.DispatchSettings.GET(9999);
        checkStatusCode404(response);
        assertEquals(response.as(ErrorsListResponse.class).getErrors().get(0).getDetail(), "OperationalZone не существует", "Error message not valid");

    }

    @Story("Dispatch settings")
    @CaseId(104)
    @Test(description = "Получение конфига с не валидным токеном",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void dispatchSettings401() {
        SessionFactory.clearSession(SessionType.SHOPPER_ADMIN);
        response = ShopperAdminRequest.OperationalZones.DispatchSettings.GET(EnvironmentProperties.DEFAULT_ID_ZONE);
        checkStatusCode401(response);
        assertEquals(response.as(ErrorResponse.class).getError(), "token_missing", "Error message not valid");
    }

    @Story("Dispatch settings")
    @Test(description = "Получение настроек Диспетчеризация 2.0",
            groups = {"api-shopper-regress"})
    public void getDispatchSettings() {
        SessionFactory.createSessionToken(SessionType.SHOPPER_ADMIN, UserManager.getDefaultAdmin());
        String retailerUUID = "c158f834-b944-4c84-9165-65f311e6aed4";

        final Response response = ShopperAdminRequest.Stores.DispatchSettings.GET(retailerUUID);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, StoreDispatchSettingsResponse.class);
        StoreDispatchSettingsV1 parameters = response.as(StoreDispatchSettingsResponse.class).getStoreDispatchSetting();
        assertEquals(parameters.getStoreId(), Integer.valueOf(EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID), "Вернулся неверный StoreID");
    }

    @Story("Dispatch settings")
    @CaseId(189)
    @Test(description = "Изменение настроек диспатчеризации",
            groups = {"api-shopper-regress"})
    public void putDispatchSettings() {
        SessionFactory.createSessionToken(SessionType.SHOPPER_ADMIN, UserManager.getDefaultAdmin());
        String retailerUUID = "599ba7b7-0d2f-4e54-8b8e-ca5ed7c6ff8a";
        var parameters  = ShopperAdminRequest.PutDispatchSettings.builder()
                .settingsDispatch(ShopperAdminRequest.SettingsDispatch.builder()
                        .dispatchParameters(ShopperAdminRequest.DispatchParameters.builder()
                                .id(2)
                                .storeId(21)
                                .maxOrderAssignRetryCount(RandomUtils.nextInt(1, 15))
                                .avgParkingMinVehicle(RandomUtils.nextInt(1, 15))
                                .maxCurrentOrderAssignQueue(RandomUtils.nextInt(1, 15))
                                .orderWeightThresholdToAssignToVehicleGramms(RandomUtils.nextInt(1500, 15000))
                                .averageSpeedForStraightDistanceToClientMin(RandomUtils.nextInt(10, 30))
                                .additionalFactorForStraightDistanceToClientMin(RandomUtils.nextInt(1, 10))
                                .orderTransferTimeFromAssemblyToDeliveryMin(RandomUtils.nextInt(1, 20))
                                .avgToPlaceMin(RandomUtils.nextInt(1, 20))
                                .avgToPlaceMinExternal(RandomUtils.nextInt(1, 15))
                                .offerSeenTimeoutSec(RandomUtils.nextInt(1, 120))
                                .lastPositionExpire(RandomUtils.nextInt(1, 15000))
                                .taxiDeliveryOnly(false)
                                .placeLocationCenter(true)
                                .orderTransferTimeFromDeliveryToClientMin(RandomUtils.nextInt(1, 20))
                                .orderReceiveTimeFromAssemblyToDeliveryMin(RandomUtils.nextInt(1, 20))
                                .offerServerTimeoutSec(RandomUtils.nextInt(1, 150))
                                .externalAssembliersPresented(false)
                                .gapTaxiPunishMin(RandomUtils.nextInt(1, 150))
                                .taxiAvailable(false)
                                .maxWaitingTimeForCourierMin(RandomUtils.nextInt(1, 20))
                                .build())
                        .build())
                .build();
        Response response = ShopperAdminRequest.Stores.DispatchSettings.PUT(retailerUUID, parameters);
        checkStatusCode200(response);

        Response responseGet = ShopperAdminRequest.Stores.DispatchSettings.GET(retailerUUID);

        BaseApiCheckpoints.compareTwoObjects(responseGet.as(StoreDispatchSettingsResponse.class)
                        .getStoreDispatchSetting().getId(),
                parameters.getSettingsDispatch().getDispatchParameters().getId());
        BaseApiCheckpoints.compareTwoObjects(responseGet.as(StoreDispatchSettingsResponse.class)
                        .getStoreDispatchSetting().getStoreId(),
                parameters.getSettingsDispatch().getDispatchParameters().getStoreId());
        BaseApiCheckpoints.compareTwoObjects(responseGet.as(StoreDispatchSettingsResponse.class)
                        .getStoreDispatchSetting().getMaxOrderAssignRetryCount(),
                parameters.getSettingsDispatch().getDispatchParameters().getMaxOrderAssignRetryCount());
        BaseApiCheckpoints.compareTwoObjects(responseGet.as(StoreDispatchSettingsResponse.class)
                        .getStoreDispatchSetting().getAvgParkingMinVehicle(),
                parameters.getSettingsDispatch().getDispatchParameters().getAvgParkingMinVehicle());
        BaseApiCheckpoints.compareTwoObjects(responseGet.as(StoreDispatchSettingsResponse.class)
                        .getStoreDispatchSetting().getMaxCurrentOrderAssignQueue(),
                parameters.getSettingsDispatch().getDispatchParameters().getMaxCurrentOrderAssignQueue());
        BaseApiCheckpoints.compareTwoObjects(responseGet.as(StoreDispatchSettingsResponse.class)
                        .getStoreDispatchSetting().getOrderWeightThresholdToAssignToVehicleGramms(),
                parameters.getSettingsDispatch().getDispatchParameters().getOrderWeightThresholdToAssignToVehicleGramms());
        BaseApiCheckpoints.compareTwoObjects(responseGet.as(StoreDispatchSettingsResponse.class)
                        .getStoreDispatchSetting().getAverageSpeedForStraightDistanceToClientMin(),
                parameters.getSettingsDispatch().getDispatchParameters().getAverageSpeedForStraightDistanceToClientMin());
        BaseApiCheckpoints.compareTwoObjects(responseGet.as(StoreDispatchSettingsResponse.class)
                        .getStoreDispatchSetting().getAdditionalFactorForStraightDistanceToClientMin(),
                parameters.getSettingsDispatch().getDispatchParameters().getAdditionalFactorForStraightDistanceToClientMin());
        BaseApiCheckpoints.compareTwoObjects(responseGet.as(StoreDispatchSettingsResponse.class)
                        .getStoreDispatchSetting().getOrderTransferTimeFromAssemblyToDeliveryMin(),
                parameters.getSettingsDispatch().getDispatchParameters().getOrderTransferTimeFromAssemblyToDeliveryMin());
        BaseApiCheckpoints.compareTwoObjects(responseGet.as(StoreDispatchSettingsResponse.class)
                        .getStoreDispatchSetting().getAvgToPlaceMin(),
                parameters.getSettingsDispatch().getDispatchParameters().getAvgToPlaceMin());
        BaseApiCheckpoints.compareTwoObjects(responseGet.as(StoreDispatchSettingsResponse.class)
                        .getStoreDispatchSetting().getAvgToPlaceMinExternal(),
                parameters.getSettingsDispatch().getDispatchParameters().getAvgToPlaceMinExternal());
        BaseApiCheckpoints.compareTwoObjects(responseGet.as(StoreDispatchSettingsResponse.class)
                        .getStoreDispatchSetting().getOfferSeenTimeoutSec(),
                parameters.getSettingsDispatch().getDispatchParameters().getOfferSeenTimeoutSec());
        BaseApiCheckpoints.compareTwoObjects(responseGet.as(StoreDispatchSettingsResponse.class)
                        .getStoreDispatchSetting().getLastPositionExpire(),
                parameters.getSettingsDispatch().getDispatchParameters().getLastPositionExpire());
        BaseApiCheckpoints.compareTwoObjects(responseGet.as(StoreDispatchSettingsResponse.class)
                        .getStoreDispatchSetting().isTaxiDeliveryOnly(),
                parameters.getSettingsDispatch().getDispatchParameters().isTaxiDeliveryOnly());
        BaseApiCheckpoints.compareTwoObjects(responseGet.as(StoreDispatchSettingsResponse.class)
                        .getStoreDispatchSetting().isPlaceLocationCenter(),
                parameters.getSettingsDispatch().getDispatchParameters().isPlaceLocationCenter());
        BaseApiCheckpoints.compareTwoObjects(responseGet.as(StoreDispatchSettingsResponse.class)
                        .getStoreDispatchSetting().getOrderTransferTimeFromDeliveryToClientMin(),
                parameters.getSettingsDispatch().getDispatchParameters().getOrderTransferTimeFromDeliveryToClientMin());
        BaseApiCheckpoints.compareTwoObjects(responseGet.as(StoreDispatchSettingsResponse.class)
                        .getStoreDispatchSetting().getOrderReceiveTimeFromAssemblyToDeliveryMin(),
                parameters.getSettingsDispatch().getDispatchParameters().getOrderReceiveTimeFromAssemblyToDeliveryMin());
        BaseApiCheckpoints.compareTwoObjects(responseGet.as(StoreDispatchSettingsResponse.class)
                        .getStoreDispatchSetting().getOfferServerTimeoutSec(),
                parameters.getSettingsDispatch().getDispatchParameters().getOfferServerTimeoutSec());
        BaseApiCheckpoints.compareTwoObjects(responseGet.as(StoreDispatchSettingsResponse.class)
                        .getStoreDispatchSetting().isExternalAssembliersPresented(),
                parameters.getSettingsDispatch().getDispatchParameters().isExternalAssembliersPresented());
        BaseApiCheckpoints.compareTwoObjects(responseGet.as(StoreDispatchSettingsResponse.class)
                        .getStoreDispatchSetting().getGapTaxiPunishMin(),
                parameters.getSettingsDispatch().getDispatchParameters().getGapTaxiPunishMin());
        BaseApiCheckpoints.compareTwoObjects(responseGet.as(StoreDispatchSettingsResponse.class)
                        .getStoreDispatchSetting().isTaxiAvailable(),
                parameters.getSettingsDispatch().getDispatchParameters().isTaxiAvailable());
        BaseApiCheckpoints.compareTwoObjects(responseGet.as(StoreDispatchSettingsResponse.class)
                        .getStoreDispatchSetting().getMaxWaitingTimeForCourierMin(),
                parameters.getSettingsDispatch().getDispatchParameters().getMaxWaitingTimeForCourierMin());

    }
}
