package ru.instamart.test.api.on_demand.routeestimator;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.apache.commons.lang3.RandomUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.grpc.common.GrpcBase;
import ru.instamart.jdbc.dao.routeestimator.RegionSettingsDao;
import ru.instamart.jdbc.dao.routeestimator.RouteEstimatorSettingsDao;
import ru.instamart.jdbc.entity.routeestimator.RegionSettingsEntity;
import ru.instamart.jdbc.entity.routeestimator.RouteEstimatorSettingsEntity;
import ru.sbermarket.qase.annotation.CaseId;
import settings.Settings;
import settings.SettingsServiceGrpc;


import static org.testng.Assert.assertEquals;
import static ru.instamart.grpc.common.GrpcContentHosts.PAAS_CONTENT_OPERATIONS_ROUTE_ESTIMATOR;
import static ru.instamart.kraken.util.TimeUtil.getTimestamp;

@Epic("On Demand")
@Feature("RES")

public class GrpcSettingsTest extends GrpcBase {
    private SettingsServiceGrpc.SettingsServiceBlockingStub clientRes;
    private final Integer regionId = 1;
    private final String placeUUID = "684609ad-6360-4bae-9556-03918c1e41c1";
    private final double correctionFactorDefault = 1.0;
    private final int increasingFactorDefault = 300;
    private final int minimumSegmentLengthDefault = 300;

    @BeforeClass(alwaysRun = true)
    public void createClient() {
        channel = grpc.createChannel(PAAS_CONTENT_OPERATIONS_ROUTE_ESTIMATOR);
        clientRes = settings.SettingsServiceGrpc.newBlockingStub(channel);
    }

    @CaseId(29)
    @Test(description = "Получение коэффициентов RES по региону",
            groups = "ondemand-res-smoke")
    public void getRegionSettings() {
        var request = Settings.GetResOperationalZoneSettingsRequest.newBuilder()
                .setOperationalZoneId(regionId)
                .build();
        Settings.GetResOperationalZoneSettingsReply settingsReply = clientRes.getOperationalZoneSettings(request);
        RegionSettingsEntity regionSettingsEntity = RegionSettingsDao.INSTANCE.getSettings(regionId);
        Allure.step("Проверка полученных значений по GRPC и в базе", () -> {
            assertEquals(settingsReply.getResOperationalZoneSettings().getBicycleLinerModelRatios().getCorrectionFactor(), regionSettingsEntity.getBicycleCorrectionFactor(), "В БД не то значение для корректирующего коэффициента для велосипеда");
            assertEquals(settingsReply.getResOperationalZoneSettings().getBicycleLinerModelRatios().getIncreasingFactor(), regionSettingsEntity.getBicycleIncreasingFactorSec(), "В БД не то значение для увеличивающего коэффициента для велосипеда");
            assertEquals(settingsReply.getResOperationalZoneSettings().getBicycleLinerModelRatios().getMinimumSegmentLength(), regionSettingsEntity.getBicycleMinimumSegmentLengthSec(), "В БД не то значение для минимальной длительности сегмента для велосипеда");
            assertEquals(settingsReply.getResOperationalZoneSettings().getAutoLinerModelRatios().getCorrectionFactor(), regionSettingsEntity.getAutoCorrectionFactor(), "В БД не то значение для корректирующего коэффициента для автомобиля");
            assertEquals(settingsReply.getResOperationalZoneSettings().getAutoLinerModelRatios().getIncreasingFactor(), regionSettingsEntity.getAutoIncreasingFactorSec(), "В БД не то значение для увеличивающего коэффициента для автомобиля");
            assertEquals(settingsReply.getResOperationalZoneSettings().getAutoLinerModelRatios().getMinimumSegmentLength(), regionSettingsEntity.getAutoMinimumSegmentLengthSec(), "В БД не то значение для минимальной длительности сегмента для автомобиля");
            assertEquals(settingsReply.getResOperationalZoneSettings().getPedestrianLinerModelRatios().getCorrectionFactor(), regionSettingsEntity.getPedestrianCorrectionFactor(), "В БД не то значение для корректирующего коэффициента для пешехода");
            assertEquals(settingsReply.getResOperationalZoneSettings().getPedestrianLinerModelRatios().getIncreasingFactor(), regionSettingsEntity.getPedestrianIncreasingFactorSec(), "В БД не то значение для увеличивающего коэффициента для пешехода");
            assertEquals(settingsReply.getResOperationalZoneSettings().getPedestrianLinerModelRatios().getMinimumSegmentLength(), regionSettingsEntity.getPedestrianMinimumSegmentLengthSec(), "В БД не то значение для минимальной длительности сегмента для пешехода");
        });
    }

    @CaseId(30)
    @Test(description = "Получение коэффициентов RES для региона, которого нет в базе",
            groups = "ondemand-res-smoke")
    public void getNewRegionSettings() {
        var request = Settings.GetResOperationalZoneSettingsRequest.newBuilder()
                .setOperationalZoneId(1003)
                .build();
        Settings.GetResOperationalZoneSettingsReply settingsReply = clientRes.getOperationalZoneSettings(request);
        RegionSettingsEntity regionSettingsEntity = RegionSettingsDao.INSTANCE.getSettings(regionId);
        Allure.step("Сравнение полученных значений по GRPC и дефолтов", () -> {
            assertEquals(settingsReply.getResOperationalZoneSettings().getBicycleLinerModelRatios().getCorrectionFactor(), correctionFactorDefault, "Вернулось не дефолтное значение для корректирующего коэффициента для велосипеда");
            assertEquals(settingsReply.getResOperationalZoneSettings().getBicycleLinerModelRatios().getIncreasingFactor(), increasingFactorDefault, "Вернулось не дефолтное значение значение для увеличивающего коэффициента для велосипеда");
            assertEquals(settingsReply.getResOperationalZoneSettings().getBicycleLinerModelRatios().getMinimumSegmentLength(), minimumSegmentLengthDefault, "Вернулось не дефолтное значение для минимальной длительности сегмента для велосипеда");
            assertEquals(settingsReply.getResOperationalZoneSettings().getAutoLinerModelRatios().getCorrectionFactor(), correctionFactorDefault, "Вернулось не дефолтное значение для корректирующего коэффициента для автомобиля");
            assertEquals(settingsReply.getResOperationalZoneSettings().getAutoLinerModelRatios().getIncreasingFactor(), increasingFactorDefault, "Вернулось не дефолтное значение для увеличивающего коэффициента для автомобиля");
            assertEquals(settingsReply.getResOperationalZoneSettings().getAutoLinerModelRatios().getMinimumSegmentLength(), minimumSegmentLengthDefault, "Вернулось не дефолтное значение для минимальной длительности сегмента для автомобиля");
            assertEquals(settingsReply.getResOperationalZoneSettings().getPedestrianLinerModelRatios().getCorrectionFactor(), correctionFactorDefault, "Вернулось не дефолтное значение для корректирующего коэффициента для пешехода");
            assertEquals(settingsReply.getResOperationalZoneSettings().getPedestrianLinerModelRatios().getIncreasingFactor(), increasingFactorDefault, "Вернулось не дефолтное значение для увеличивающего коэффициента для пешехода");
            assertEquals(settingsReply.getResOperationalZoneSettings().getPedestrianLinerModelRatios().getMinimumSegmentLength(), minimumSegmentLengthDefault, "Вернулось не дефолтное значение для минимальной длительности сегмента для пешехода");
        });
    }

    @CaseId(30)
    @Test(description = "Изменение коэффициентов RES по региону",
            groups = "ondemand-res-smoke")
    public void putRegionSettings() {
        var request = Settings.PutResOperationalZoneSettingsRequest.newBuilder()
                .setResOperationalZoneSettings(Settings.ResOperationalZoneSettings.newBuilder()
                        .setOperationalZoneId(regionId)
                        .setCorrectionFactor(0)
                        .setIncreasingFactor(increasingFactorDefault)
                        .setMinimumSegmentLength(minimumSegmentLengthDefault)
                        .setUpdatedAt(getTimestamp())
                        .setBicycleLinerModelRatios(Settings.LinerModelRatios.newBuilder()
                                .setCorrectionFactor((double)(Math.round(RandomUtils.nextDouble(0.5, 1.5)*100))/100)
                                .setIncreasingFactor(RandomUtils.nextInt(200, 300))
                                .setMinimumSegmentLength(RandomUtils.nextInt(200, 300))
                                .build())
                        .setAutoLinerModelRatios(Settings.LinerModelRatios.newBuilder()
                                .setCorrectionFactor((double)(Math.round(RandomUtils.nextDouble(0.5, 1.5)*100))/100)
                                .setIncreasingFactor(RandomUtils.nextInt(200, 300))
                                .setMinimumSegmentLength(RandomUtils.nextInt(200, 300))
                                .build())
                        .setPedestrianLinerModelRatios(Settings.LinerModelRatios.newBuilder()
                                .setCorrectionFactor((double)(Math.round(RandomUtils.nextDouble(0.5, 1.5)*100))/100)
                                .setIncreasingFactor(RandomUtils.nextInt(200, 300))
                                .setMinimumSegmentLength(RandomUtils.nextInt(200, 300))
                                .build())
                        .build())
                .build();
                clientRes.putOperationalZoneSettings(request);

        var requestGet = Settings.GetResOperationalZoneSettingsRequest.newBuilder()
                .setOperationalZoneId(regionId)
                .build();
        Settings.GetResOperationalZoneSettingsReply settingsGetReply = clientRes.getOperationalZoneSettings(requestGet);
        Allure.step("Проверка полученных значений после изменения через GRPC", () -> {
            assertEquals(settingsGetReply.getResOperationalZoneSettings().getBicycleLinerModelRatios().getCorrectionFactor(), request.getResOperationalZoneSettings().getBicycleLinerModelRatios().getCorrectionFactor(), "Не обновилось значение для корректирующего коэффициента для велосипеда");
            assertEquals(settingsGetReply.getResOperationalZoneSettings().getBicycleLinerModelRatios().getIncreasingFactor(), request.getResOperationalZoneSettings().getBicycleLinerModelRatios().getIncreasingFactor(), "Не обновилось значение для увеличивающего коэффициента для велосипеда");
            assertEquals(settingsGetReply.getResOperationalZoneSettings().getBicycleLinerModelRatios().getMinimumSegmentLength(), request.getResOperationalZoneSettings().getBicycleLinerModelRatios().getMinimumSegmentLength(), "Не обновилось значение для минимальной длительности сегмента для велосипеда");
            assertEquals(settingsGetReply.getResOperationalZoneSettings().getAutoLinerModelRatios().getCorrectionFactor(), request.getResOperationalZoneSettings().getAutoLinerModelRatiosOrBuilder().getCorrectionFactor(), "Не обновилось значение для корректирующего коэффициента для автомобиля");
            assertEquals(settingsGetReply.getResOperationalZoneSettings().getAutoLinerModelRatios().getIncreasingFactor(), request.getResOperationalZoneSettings().getAutoLinerModelRatiosOrBuilder().getIncreasingFactor(), "Не обновилось значение для увеличивающего коэффициента для автомобиля");
            assertEquals(settingsGetReply.getResOperationalZoneSettings().getAutoLinerModelRatios().getMinimumSegmentLength(), request.getResOperationalZoneSettings().getAutoLinerModelRatiosOrBuilder().getMinimumSegmentLength(), "Не обновилось значение для минимальной длительности сегмента для автомобиля");
            assertEquals(settingsGetReply.getResOperationalZoneSettings().getPedestrianLinerModelRatios().getCorrectionFactor(), request.getResOperationalZoneSettings().getPedestrianLinerModelRatios().getCorrectionFactor(), "Не обновилось значение для корректирующего коэффициента для пешехода");
            assertEquals(settingsGetReply.getResOperationalZoneSettings().getPedestrianLinerModelRatios().getIncreasingFactor(), request.getResOperationalZoneSettings().getPedestrianLinerModelRatios().getIncreasingFactor(), "Не обновилось значение для увеличивающего коэффициента для пешехода");
            assertEquals(settingsGetReply.getResOperationalZoneSettings().getPedestrianLinerModelRatios().getMinimumSegmentLength(), request.getResOperationalZoneSettings().getPedestrianLinerModelRatios().getMinimumSegmentLength(), "Не обновилось значение для минимальной длительности сегмента для пешехода");
        });
    }

    @CaseId(31)
    @Test(description = "Получение настроек RES по магазину",
            groups = "ondemand-res-smoke")
    public void getStoreSettings() {
        var request = Settings.GetResSettingsRequest.newBuilder()
                .setPlaceUuid(placeUUID)
                .build();
        Settings.GetResSettingsReply settingsReply = clientRes.getResSettings(request);
        RouteEstimatorSettingsEntity routeEstimatorSettingsEntity = RouteEstimatorSettingsDao.INSTANCE.findByStoreUuid(placeUUID);
        Allure.step("Проверка полученных значений по GRPC и в базе", () -> {
            assertEquals(settingsReply.getResSettings().getPlaceUuid(), routeEstimatorSettingsEntity.getPlace_uuid(), "В БД не то значение для place_uuid");
            assertEquals(settingsReply.getResSettings().getAvgParkingMinVehicle(), routeEstimatorSettingsEntity.getAvgParkingMinVehicle(), "В БД не то значение для времени на парковку");
            assertEquals(settingsReply.getResSettings().getAverageSpeedForStraightDistanceToClientMin(), routeEstimatorSettingsEntity.getAverageSpeedForStraightDistanceToClientMin(), "В БД не то значение для средней скорости по прямой");
            assertEquals(settingsReply.getResSettings().getAdditionalFactorForStraightDistanceToClientMin(), routeEstimatorSettingsEntity.getAdditionalFactorForStraightDistanceToClientMin(), "В БД не то значение для увеличивающего коэффициента по прямой");
            assertEquals(settingsReply.getResSettings().getOrderTransferTimeFromAssemblyToDeliveryMin(), routeEstimatorSettingsEntity.getOrderTransferTimeFromAssemblyToDeliveryMin(), "В БД не то значение для времени на передачу заказа от сборки в доставку");
            assertEquals(settingsReply.getResSettings().getAvgToPlaceMinExternal(), routeEstimatorSettingsEntity.getAvgToPlaceMinExternal(), "В БД не то значение для времени на подлёт такси");
            assertEquals(settingsReply.getResSettings().getOrderTransferTimeFromDeliveryToClientMin(), routeEstimatorSettingsEntity.getOrderTransferTimeFromDeliveryToClientMin(), "В БД не то значение для времени на передачу заказа из доставки клиенту");
            assertEquals(settingsReply.getResSettings().getOrderReceiveTimeFromAssemblyToDeliveryMin(), routeEstimatorSettingsEntity.getOrderReceiveTimeFromAssemblyToDeliveryMin(), "В БД не то значение для времени на получение заказа из сборки в доставку");
        });
    }

    @CaseId(33)
    @Test(description = "Изменение настроек RES для магазина",
            groups = "ondemand-res-smoke")
    public void putStoreSettings() {
        var request = Settings.PutResSettingsRequest.newBuilder()
                .setResSettings(Settings.ResSettings.newBuilder()
                        .setPlaceUuid(placeUUID)
                        .setAvgParkingMinVehicle(RandomUtils.nextInt(1, 20))
                        .setAverageSpeedForStraightDistanceToClientMin(RandomUtils.nextInt(1, 20))
                        .setAdditionalFactorForStraightDistanceToClientMin(RandomUtils.nextInt(1, 20))
                        .setOrderTransferTimeFromAssemblyToDeliveryMin(RandomUtils.nextInt(1, 20))
                        .setAvgToPlaceMinExternal(RandomUtils.nextInt(1, 20))
                        .setOrderTransferTimeFromDeliveryToClientMin(RandomUtils.nextInt(1, 20))
                        .setOrderReceiveTimeFromAssemblyToDeliveryMin(RandomUtils.nextInt(1, 20))
                        .build())
        .build();
        clientRes.putResSettings(request);

        var requestGet = Settings.GetResSettingsRequest.newBuilder()
                .setPlaceUuid(placeUUID)
                .build();
        Settings.GetResSettingsReply settingsGetReply = clientRes.getResSettings(requestGet);
        Allure.step("Проверка полученных значений после изменения через GRPC", () -> {
            assertEquals(settingsGetReply.getResSettings().getPlaceUuid(), request.getResSettings().getPlaceUuid(), "Не обновилось значение для корректирующего коэффициента для велосипеда");
            assertEquals(settingsGetReply.getResSettings().getAvgParkingMinVehicle(), request.getResSettings().getAvgParkingMinVehicle(), "Не обновилось значение для увеличивающего коэффициента для велосипеда");
            assertEquals(settingsGetReply.getResSettings().getAverageSpeedForStraightDistanceToClientMin(), request.getResSettings().getAverageSpeedForStraightDistanceToClientMin(), "Не обновилось значение для минимальной длительности сегмента для велосипеда");
            assertEquals(settingsGetReply.getResSettings().getAdditionalFactorForStraightDistanceToClientMin(), request.getResSettings().getAdditionalFactorForStraightDistanceToClientMin(), "Не обновилось значение для корректирующего коэффициента для автомобиля");
            assertEquals(settingsGetReply.getResSettings().getOrderTransferTimeFromAssemblyToDeliveryMin(), request.getResSettings().getOrderTransferTimeFromAssemblyToDeliveryMin(), "Не обновилось значение для увеличивающего коэффициента для автомобиля");
            assertEquals(settingsGetReply.getResSettings().getAvgToPlaceMinExternal(), request.getResSettings().getAvgToPlaceMinExternal(), "Не обновилось значение для минимальной длительности сегмента для автомобиля");
            assertEquals(settingsGetReply.getResSettings().getOrderTransferTimeFromDeliveryToClientMin(), request.getResSettings().getOrderTransferTimeFromDeliveryToClientMin(), "Не обновилось значение для корректирующего коэффициента для пешехода");
            assertEquals(settingsGetReply.getResSettings().getOrderReceiveTimeFromAssemblyToDeliveryMin(), request.getResSettings().getOrderReceiveTimeFromAssemblyToDeliveryMin(), "Не обновилось значение для увеличивающего коэффициента для пешехода");
        });
    }

    @AfterClass(alwaysRun = true)
    public void postConditions() {
        var requestStoreSettings = Settings.PutResSettingsRequest.newBuilder()
                .setResSettings(Settings.ResSettings.newBuilder()
                        .setPlaceUuid(placeUUID)
                        .setAvgParkingMinVehicle(5)
                        .setAverageSpeedForStraightDistanceToClientMin(5)
                        .setAdditionalFactorForStraightDistanceToClientMin(5)
                        .setOrderTransferTimeFromAssemblyToDeliveryMin(6)
                        .setAvgToPlaceMinExternal(10)
                        .setOrderTransferTimeFromDeliveryToClientMin(7)
                        .setOrderReceiveTimeFromAssemblyToDeliveryMin(4)
                        .build())
                .build();
        clientRes.putResSettings(requestStoreSettings);

        var requestRegionSettings = Settings.PutResOperationalZoneSettingsRequest.newBuilder()
                .setResOperationalZoneSettings(Settings.ResOperationalZoneSettings.newBuilder()
                        .setOperationalZoneId(regionId)
                        .setCorrectionFactor(0)
                        .setIncreasingFactor(increasingFactorDefault)
                        .setMinimumSegmentLength(minimumSegmentLengthDefault)
                        .setUpdatedAt(getTimestamp())
                        .setBicycleLinerModelRatios(Settings.LinerModelRatios.newBuilder()
                                .setCorrectionFactor(correctionFactorDefault)
                                .setIncreasingFactor(increasingFactorDefault)
                                .setMinimumSegmentLength(minimumSegmentLengthDefault)
                                .build())
                        .setAutoLinerModelRatios(Settings.LinerModelRatios.newBuilder()
                                .setCorrectionFactor(correctionFactorDefault)
                                .setIncreasingFactor(increasingFactorDefault)
                                .setMinimumSegmentLength(minimumSegmentLengthDefault)
                                .build())
                        .setPedestrianLinerModelRatios(Settings.LinerModelRatios.newBuilder()
                                .setCorrectionFactor(correctionFactorDefault)
                                .setIncreasingFactor(increasingFactorDefault)
                                .setMinimumSegmentLength(minimumSegmentLengthDefault)
                                .build())
                        .build())
                .build();
        clientRes.putOperationalZoneSettings(requestRegionSettings);
    }
}
