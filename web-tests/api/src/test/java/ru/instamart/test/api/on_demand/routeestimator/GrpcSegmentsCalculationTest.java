package ru.instamart.test.api.on_demand.routeestimator;

import com.google.protobuf.Timestamp;
import estimator.Estimator;
import estimator.RouteEstimatorGrpc;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import ru.instamart.api.dataprovider.DispatchDataProvider;
import ru.instamart.grpc.common.GrpcBase;
import ru.instamart.jdbc.dao.routeestimator.RegionSettingsDao;
import ru.instamart.jdbc.dao.routeestimator.RouteEstimatorSettingsDao;
import ru.instamart.jdbc.entity.routeestimator.RegionSettingsEntity;
import ru.instamart.jdbc.entity.routeestimator.RouteEstimatorSettingsEntity;
import io.qameta.allure.TmsLinks;
import io.qameta.allure.TmsLink;

import static com.google.protobuf.util.Durations.toSeconds;
import static org.testng.Assert.assertEquals;
import static ru.instamart.api.helper.RouteEstimatorHelper.getEstimateRequest;
import static ru.instamart.grpc.common.GrpcContentHosts.PAAS_CONTENT_OPERATIONS_ROUTE_ESTIMATOR;
import static ru.instamart.kraken.util.TimeUtil.*;
@Epic("On Demand")
@Feature("RES")

public class GrpcSegmentsCalculationTest extends GrpcBase {
    private RouteEstimatorGrpc.RouteEstimatorBlockingStub clientRes;
    private final Timestamp timeToCalc = getTimestamp();
    private final long zeroTime = 0;
    private final String performerUuid = "e86263fd-73f3-4973-83b1-c957bd00acba";
    private final String placeUUID = "684609ad-6360-4bae-9556-03918c1e41c1";
    private final int numberOfPositionsInOrder = 3;
    private final Integer regionId = 1;



    @BeforeClass(alwaysRun = true)
    public void createClient() {
        channel = grpc.createChannel(PAAS_CONTENT_OPERATIONS_ROUTE_ESTIMATOR);
        clientRes = estimator.RouteEstimatorGrpc.newBlockingStub(channel);
    }

    @TmsLinks({@TmsLink("1"), @TmsLink("34")})
    @Test(description = "Расчёт времени подлёта через 2GIS для пешего и вело универсалов", dataProvider = "transportType", dataProviderClass = DispatchDataProvider.class,
            groups = "ondemand-res-smoke")
    @Parameters("Тип транспорта")
    public void estimateArriveSegmentPedestrianBike(Estimator.PerformerVehicle type) {
        Estimator.GetRouteEstimationResponse routeEstimationResponse = clientRes.getRouteEstimation(getEstimateRequest(performerUuid, Estimator.PerformerType.UNIVERSAL, type,55.698762, 37.728196, timeToCalc, Estimator.SegmentType.ARRIVE, 55.700683, 37.726683, placeUUID, numberOfPositionsInOrder));
        Allure.step("Проверка полученных значений по GRPC", () -> {
            assertEquals(routeEstimationResponse.getPerformers(0).getSegments(0).getEstimateType(), Estimator.SegmentTime.EstimateType.TWO_GIS, "Для расстояния больше 50 метров ответ вернул не 2GIS");
            assertEquals(routeEstimationResponse.getPerformers(0).getSegments(0).getEndTime(), addSecondsTimestamp(timeToCalc, toSeconds(routeEstimationResponse.getPerformers(0).getSegments(0).getDuration())), "Время завершения сегмента не равно сумме начального времени и времени выполнения сегмента");
            assertEquals(toSeconds(routeEstimationResponse.getPerformers(0).getSegments(0).getTimeLag()), zeroTime, "Для пешего/вело универсала добавилось время на парковку при подлёте");
        });
    }

    @TmsLink("9")
    @Test(description = "Расчёт времени подлёта через 2GIS для авто универсала",
            groups = "ondemand-res-smoke")
    public void estimateArriveSegmentCar() {
        Estimator.GetRouteEstimationResponse routeEstimationResponseCar = clientRes.getRouteEstimation(getEstimateRequest(performerUuid, Estimator.PerformerType.UNIVERSAL, Estimator.PerformerVehicle.AUTO, 55.698762, 37.728196, timeToCalc, Estimator.SegmentType.ARRIVE, 55.700683, 37.726683, placeUUID, numberOfPositionsInOrder));
        RouteEstimatorSettingsEntity routeEstimatorSettingsEntity = RouteEstimatorSettingsDao.INSTANCE.findByStoreUuid(placeUUID);
        Allure.step("Проверка полученных значений по GRPC", () -> {
            assertEquals(routeEstimationResponseCar.getPerformers(0).getSegments(0).getEstimateType(), Estimator.SegmentTime.EstimateType.TWO_GIS, "Для расстояния больше 50 метров ответ вернул не 2GIS");
            assertEquals(routeEstimationResponseCar.getPerformers(0).getSegments(0).getEndTime(), addSecondsTimestamp(timeToCalc, toSeconds(routeEstimationResponseCar.getPerformers(0).getSegments(0).getDuration())), "Время завершения сегмента не равно сумме начального времени и времени выполнения сегмента");
            assertEquals(toSeconds(routeEstimationResponseCar.getPerformers(0).getSegments(0).getTimeLag()), routeEstimatorSettingsEntity.getAvgParkingMinVehicle() * 60L, "Для авто универсала время на парковку отличается от сохранённого времени в базе");
        });
    }

    @TmsLink("11")
    @Test(description = "Расчёт времени подлёта для пешего универсала через фоллбек (расстояние между точками меньше 50 метров)",
            groups = "ondemand-res-smoke")
    public void estimateArriveSegmentFallbackPedestrian() {
        Estimator.GetRouteEstimationResponse routeEstimationResponse = clientRes.getRouteEstimation(getEstimateRequest(performerUuid, Estimator.PerformerType.UNIVERSAL, Estimator.PerformerVehicle.PEDESTRIAN, 55.700478, 37.727171, timeToCalc, Estimator.SegmentType.ARRIVE, 55.700683, 37.726683, placeUUID, numberOfPositionsInOrder));
        RegionSettingsEntity regionSettingsEntity = RegionSettingsDao.INSTANCE.getSettings(regionId);
        Allure.step("Проверка полученных значений по GRPC", () -> {
            assertEquals(routeEstimationResponse.getPerformers(0).getSegments(0).getEstimateType(), Estimator.SegmentTime.EstimateType.FALLBACK, "Для расстояния меньше 50 метров ответ вернул не фоллбек");
            assertEquals(routeEstimationResponse.getPerformers(0).getSegments(0).getDistance(), 0, "Для фоллбека при расстоянии < 50 метров дальность != 0");
            assertEquals(toSeconds(routeEstimationResponse.getPerformers(0).getSegments(0).getDuration()), regionSettingsEntity.getPedestrianMinimumSegmentLengthSec(), "Для фоллбека при расстоянии < 50 метров длительность не равняется минимальному сегменту в БД");
            assertEquals(routeEstimationResponse.getPerformers(0).getSegments(0).getEndTime(), addSecondsTimestamp(timeToCalc, toSeconds(routeEstimationResponse.getPerformers(0).getSegments(0).getDuration())), "Время завершения сегмента не равно сумме начального времени и времени выполнения сегмента");
            assertEquals(toSeconds(routeEstimationResponse.getPerformers(0).getSegments(0).getTimeLag()), zeroTime, "Для пешего универсала добавилось время на парковку при подлёте через фоллбек");
        });
    }

    @TmsLink("40")
    @Test(description = "Расчёт времени подлёта для вело универсала через фоллбек (расстояние между точками меньше 50 метров)",
            groups = "ondemand-res-smoke")
    public void estimateArriveSegmentFallbackBike() {
        Estimator.GetRouteEstimationResponse routeEstimationResponse = clientRes.getRouteEstimation(getEstimateRequest(performerUuid, Estimator.PerformerType.UNIVERSAL, Estimator.PerformerVehicle.BIKE, 55.700478, 37.727171, timeToCalc, Estimator.SegmentType.ARRIVE, 55.700683, 37.726683, placeUUID, numberOfPositionsInOrder));
        RegionSettingsEntity regionSettingsEntity = RegionSettingsDao.INSTANCE.getSettings(regionId);
        Allure.step("Проверка полученных значений по GRPC", () -> {
            assertEquals(routeEstimationResponse.getPerformers(0).getSegments(0).getEstimateType(), Estimator.SegmentTime.EstimateType.FALLBACK, "Для расстояния меньше 50 метров ответ вернул не фоллбек");
            assertEquals(routeEstimationResponse.getPerformers(0).getSegments(0).getDistance(), 0, "Для фоллбека при расстоянии < 50 метров дальность != 0");
            assertEquals(toSeconds(routeEstimationResponse.getPerformers(0).getSegments(0).getDuration()), regionSettingsEntity.getBicycleMinimumSegmentLengthSec(), "Для фоллбека при расстоянии < 50 метров длительность не равняется минимальному сегменту в БД");
            assertEquals(routeEstimationResponse.getPerformers(0).getSegments(0).getEndTime(), addSecondsTimestamp(timeToCalc, toSeconds(routeEstimationResponse.getPerformers(0).getSegments(0).getDuration())), "Время завершения сегмента не равно сумме начального времени и времени выполнения сегмента");
            assertEquals(toSeconds(routeEstimationResponse.getPerformers(0).getSegments(0).getTimeLag()), zeroTime, "Для вело универсала добавилось время на парковку при подлёте через фоллбек");
        });
    }

    @TmsLinks({@TmsLink("2"), @TmsLink("35")})
    @Test(description = "Расчёт времени сборки для пешего и вело универсалов", dataProvider = "transportType", dataProviderClass = DispatchDataProvider.class,
            groups = "ondemand-res-smoke")
    public void estimateAssemblySegmentPedestrianBike(Estimator.PerformerVehicle type) {
        Estimator.GetRouteEstimationResponse routeEstimationResponse = clientRes.getRouteEstimation(getEstimateRequest(performerUuid, Estimator.PerformerType.UNIVERSAL, type, 55.700478, 37.727171, timeToCalc, Estimator.SegmentType.ASSEMBLY, 55.700683, 37.726683, placeUUID, numberOfPositionsInOrder));
        RouteEstimatorSettingsEntity routeEstimatorSettingsEntity = RouteEstimatorSettingsDao.INSTANCE.findByStoreUuid(placeUUID);
        Allure.step("Проверка полученных значений по GRPC", () -> {
            assertEquals(routeEstimationResponse.getPerformers(0).getSegments(0).getEstimateType(), Estimator.SegmentTime.EstimateType.FALLBACK, "Для расчёта сборки ответ вернул не фоллбек");
            assertEquals(routeEstimationResponse.getPerformers(0).getSegments(0).getDistance(), 0, "Для сегмента сборки расстояние != 0");
            assertEquals(toSeconds(routeEstimationResponse.getPerformers(0).getSegments(0).getDuration()), (long) routeEstimatorSettingsEntity.getAveragePerPositionAssemblyTime() * numberOfPositionsInOrder + routeEstimatorSettingsEntity.getAdditionalAssemblyTime(), "Для сегмента сборки длительность не совпадает с расчитанной по формуле");
            assertEquals(routeEstimationResponse.getPerformers(0).getSegments(0).getEndTime(), addSecondsTimestamp(timeToCalc, toSeconds(routeEstimationResponse.getPerformers(0).getSegments(0).getDuration())), "Время завершения сегмента не равно сумме начального времени и времени выполнения сегмента");
            assertEquals(toSeconds(routeEstimationResponse.getPerformers(0).getSegments(0).getTimeLag()), zeroTime, "Для пешего/вело универсала добавилось время на парковку при сборке");
        });
    }

    @TmsLink("7")
    @Test(description = "Расчёт времени сборки для авто универсалов",
            groups = "ondemand-res-smoke")
    public void estimateAssemblySegmentCarUniversal() {
        Estimator.GetRouteEstimationResponse routeEstimationResponseCar = clientRes.getRouteEstimation(getEstimateRequest(performerUuid, Estimator.PerformerType.UNIVERSAL, Estimator.PerformerVehicle.AUTO, 55.700478, 37.727171, timeToCalc, Estimator.SegmentType.ASSEMBLY, 55.700683, 37.726683, placeUUID, numberOfPositionsInOrder));
        RouteEstimatorSettingsEntity routeEstimatorSettingsEntity = RouteEstimatorSettingsDao.INSTANCE.findByStoreUuid(placeUUID);
        Allure.step("Проверка полученных значений по GRPC", () -> {
            assertEquals(routeEstimationResponseCar.getPerformers(0).getSegments(0).getEstimateType(), Estimator.SegmentTime.EstimateType.FALLBACK, "Для расчёта сборки ответ вернул не фоллбек");
            assertEquals(routeEstimationResponseCar.getPerformers(0).getSegments(0).getDistance(), 0, "Для сегмента сборки расстояние != 0");
            assertEquals(toSeconds(routeEstimationResponseCar.getPerformers(0).getSegments(0).getDuration()), (long) routeEstimatorSettingsEntity.getAveragePerPositionAssemblyTime() * numberOfPositionsInOrder + routeEstimatorSettingsEntity.getAdditionalAssemblyTime(), "Для сегмента сборки длительность не совпадает с расчитанной по формуле");
            assertEquals(routeEstimationResponseCar.getPerformers(0).getSegments(0).getEndTime(), addSecondsTimestamp(timeToCalc, toSeconds(routeEstimationResponseCar.getPerformers(0).getSegments(0).getDuration())), "Время завершения сегмента не равно сумме начального времени и времени выполнения сегмента");
            assertEquals(toSeconds(routeEstimationResponseCar.getPerformers(0).getSegments(0).getTimeLag()), zeroTime, "Для авто универсала добавилось время на парковку при сборке");
        });
    }

    @TmsLinks({@TmsLink("4"), @TmsLink("36")})
    @Test(description = "Расчёт времени передачи от сборки в доставку для пешего и вело универсалов", dataProvider = "transportType", dataProviderClass = DispatchDataProvider.class,
            groups = "ondemand-res-smoke")
    public void estimateTransferFromAssemblyToDeliverySegment(Estimator.PerformerVehicle type) {
        Estimator.GetRouteEstimationResponse routeEstimationResponse = clientRes.getRouteEstimation(getEstimateRequest(performerUuid, Estimator.PerformerType.UNIVERSAL, type, 55.700478, 37.727171,timeToCalc, Estimator.SegmentType.PASS_TO_DELIVERY, 55.700683, 37.726683, placeUUID, numberOfPositionsInOrder));
        RouteEstimatorSettingsEntity routeEstimatorSettingsEntity = RouteEstimatorSettingsDao.INSTANCE.findByStoreUuid(placeUUID);
        Allure.step("Проверка полученных значений по GRPC", () -> {
            assertEquals(routeEstimationResponse.getPerformers(0).getSegments(0).getEstimateType(), Estimator.SegmentTime.EstimateType.FALLBACK, "Для расчёта времени на передачу заказа курьеру ответ вернул не фоллбек");
            assertEquals(routeEstimationResponse.getPerformers(0).getSegments(0).getDistance(), 0, "Для сегмента передачи заказа курьеру расстояние != 0");
            assertEquals(toSeconds(routeEstimationResponse.getPerformers(0).getSegments(0).getDuration()), routeEstimatorSettingsEntity.getOrderTransferTimeFromAssemblyToDeliveryMin() * 60L, "Для сегмента передачи заказа курьеру значение не совпадает со временем в БД");
            assertEquals(routeEstimationResponse.getPerformers(0).getSegments(0).getEndTime(), addSecondsTimestamp(timeToCalc, toSeconds(routeEstimationResponse.getPerformers(0).getSegments(0).getDuration())), "Время завершения сегмента не равно сумме начального времени и времени выполнения сегмента");
            assertEquals(toSeconds(routeEstimationResponse.getPerformers(0).getSegments(0).getTimeLag()), zeroTime, "Для пешего/вело универсала добавилось время на парковку при передаче заказа курьеру");
        });
    }

    @TmsLink("8")
    @Test(description = "Расчёт времени передачи от сборки в доставку для авто универсалов",
            groups = "ondemand-res-smoke")
    public void estimateTransferFromAssemblyToDeliverySegmentCar() {
        Estimator.GetRouteEstimationResponse routeEstimationResponseCar = clientRes.getRouteEstimation(getEstimateRequest(performerUuid, Estimator.PerformerType.UNIVERSAL, Estimator.PerformerVehicle.AUTO, 55.700478, 37.727171, timeToCalc, Estimator.SegmentType.PASS_TO_DELIVERY, 55.700683,37.726683, placeUUID, numberOfPositionsInOrder));
        RouteEstimatorSettingsEntity routeEstimatorSettingsEntity = RouteEstimatorSettingsDao.INSTANCE.findByStoreUuid(placeUUID);
        Allure.step("Проверка полученных значений по GRPC", () -> {
            assertEquals(routeEstimationResponseCar.getPerformers(0).getSegments(0).getEstimateType(), Estimator.SegmentTime.EstimateType.FALLBACK, "Для расчёта времени на передачу заказа курьеру ответ вернул не фоллбек");
            assertEquals(routeEstimationResponseCar.getPerformers(0).getSegments(0).getDistance(), 0, "Для сегмента передачи курьеру расстояние != 0");
            assertEquals(toSeconds(routeEstimationResponseCar.getPerformers(0).getSegments(0).getDuration()), routeEstimatorSettingsEntity.getOrderTransferTimeFromAssemblyToDeliveryMin() * 60L, "Для сегмента передачи заказа курьеру значение не совпадает со временем в БД");
            assertEquals(routeEstimationResponseCar.getPerformers(0).getSegments(0).getEndTime(), addSecondsTimestamp(timeToCalc, toSeconds(routeEstimationResponseCar.getPerformers(0).getSegments(0).getDuration())), "Время завершения сегмента не равно сумме начального времени и времени выполнения сегмента");
            assertEquals(toSeconds(routeEstimationResponseCar.getPerformers(0).getSegments(0).getTimeLag()), zeroTime, "Для авто универсала добавилось время на парковку при передаче заказа курьеру");
        });
    }

    @TmsLinks({@TmsLink("23"), @TmsLink("37")})
    @Test(description = "Расчёт времени получения заказа в доставку для пешего и вело универсалов", dataProvider = "transportType", dataProviderClass = DispatchDataProvider.class,
            groups = "ondemand-res-smoke")
    public void estimateReceivingForDeliverySegmentPedestrianBike(Estimator.PerformerVehicle type) {
        Estimator.GetRouteEstimationResponse routeEstimationResponse = clientRes.getRouteEstimation(getEstimateRequest(performerUuid, Estimator.PerformerType.UNIVERSAL, type, 55.700478, 37.727171, timeToCalc, Estimator.SegmentType.RECEIVING_FOR_DELIVERY, 55.700683, 37.726683, placeUUID, numberOfPositionsInOrder));
        RouteEstimatorSettingsEntity routeEstimatorSettingsEntity = RouteEstimatorSettingsDao.INSTANCE.findByStoreUuid(placeUUID);
        Allure.step("Проверка полученных значений по GRPC", () -> {
            assertEquals(routeEstimationResponse.getPerformers(0).getSegments(0).getEstimateType(), Estimator.SegmentTime.EstimateType.FALLBACK, "Для расчёта времени на получение заказа в доставку ответ вернул не фоллбек");
            assertEquals(routeEstimationResponse.getPerformers(0).getSegments(0).getDistance(), 0, "Для сегмента получения заказа в доставку расстояние != 0");
            assertEquals(toSeconds(routeEstimationResponse.getPerformers(0).getSegments(0).getDuration()), routeEstimatorSettingsEntity.getOrderReceiveTimeFromAssemblyToDeliveryMin() * 60L, "Для сегмента получения заказа в доставку значение не совпадает со временем в БД");
            assertEquals(routeEstimationResponse.getPerformers(0).getSegments(0).getEndTime(), addSecondsTimestamp(timeToCalc, toSeconds(routeEstimationResponse.getPerformers(0).getSegments(0).getDuration())), "Время завершения сегмента не равно сумме начального времени и времени выполнения сегмента");
            assertEquals(toSeconds(routeEstimationResponse.getPerformers(0).getSegments(0).getTimeLag()), zeroTime, "Для пешего/вело универсала добавилось время на парковку при получении заказа в доставку");
        });
    }

    @TmsLink("3")
    @Test(description = "Расчёт времени получения заказа в доставку для авто универсалов",
            groups = "ondemand-res-smoke")
    public void estimateReceivingForDeliverySegmentCar() {
        Estimator.GetRouteEstimationResponse routeEstimationResponseCar = clientRes.getRouteEstimation(getEstimateRequest(performerUuid, Estimator.PerformerType.UNIVERSAL, Estimator.PerformerVehicle.AUTO, 55.700478, 37.727171, timeToCalc, Estimator.SegmentType.RECEIVING_FOR_DELIVERY, 55.700683, 37.726683, placeUUID, numberOfPositionsInOrder));
        RouteEstimatorSettingsEntity routeEstimatorSettingsEntity = RouteEstimatorSettingsDao.INSTANCE.findByStoreUuid(placeUUID);
        Allure.step("Проверка полученных значений по GRPC", () -> {
            assertEquals(routeEstimationResponseCar.getPerformers(0).getSegments(0).getEstimateType(), Estimator.SegmentTime.EstimateType.FALLBACK, "Для расчёта времени на получение заказа в доставку ответ вернул не фоллбек");
            assertEquals(routeEstimationResponseCar.getPerformers(0).getSegments(0).getDistance(), 0, "Для сегмента получения заказа в доставку расстояние != 0");
            assertEquals(toSeconds(routeEstimationResponseCar.getPerformers(0).getSegments(0).getDuration()), routeEstimatorSettingsEntity.getOrderReceiveTimeFromAssemblyToDeliveryMin() * 60L, "Для сегмента получения заказа в доставку значение не совпадает со временем в БД");
            assertEquals(routeEstimationResponseCar.getPerformers(0).getSegments(0).getEndTime(), addSecondsTimestamp(timeToCalc, toSeconds(routeEstimationResponseCar.getPerformers(0).getSegments(0).getDuration())), "Время завершения сегмента не равно сумме начального времени и времени выполнения сегмента");
            assertEquals(toSeconds(routeEstimationResponseCar.getPerformers(0).getSegments(0).getTimeLag()), zeroTime, "Для авто универсала добавилось время на парковку при получении заказа в доставку");
        });
    }

    @TmsLinks({@TmsLink("5"), @TmsLink("38")})
    @Test(description = "Расчёт времени доставки через 2GIS для пешего и вело универсалов", dataProvider = "transportType", dataProviderClass = DispatchDataProvider.class,
            groups = "ondemand-res-smoke")
    public void estimateDeliverySegmentPedestrian(Estimator.PerformerVehicle type) {
        Estimator.GetRouteEstimationResponse routeEstimationResponse = clientRes.getRouteEstimation(getEstimateRequest(performerUuid, Estimator.PerformerType.UNIVERSAL, type, 55.700683, 37.726683, timeToCalc, Estimator.SegmentType.DELIVERY, 55.701557, 37.723044, placeUUID, numberOfPositionsInOrder));
        Allure.step("Проверка полученных значений по GRPC", () -> {
            assertEquals(routeEstimationResponse.getPerformers(0).getSegments(0).getEstimateType(), Estimator.SegmentTime.EstimateType.TWO_GIS, "Для расстояния больше 50 метров ответ вернул не 2GIS");
            assertEquals(routeEstimationResponse.getPerformers(0).getSegments(0).getEndTime(), addSecondsTimestamp(timeToCalc, toSeconds(routeEstimationResponse.getPerformers(0).getSegments(0).getDuration())), "Время завершения сегмента не равно сумме начального времени и времени выполнения сегмента");
            assertEquals(toSeconds(routeEstimationResponse.getPerformers(0).getSegments(0).getTimeLag()), zeroTime, "Для пешего/вело универсала добавилось время на парковку при доставке");
        });
    }

    @TmsLink("12")
    @Test(description = "Расчёт времени доставки через 2GIS для авто универсала",
            groups = "ondemand-res-smoke")
    public void estimateDeliverySegmentCar() {
        Estimator.GetRouteEstimationResponse routeEstimationResponseCar = clientRes.getRouteEstimation(getEstimateRequest(performerUuid, Estimator.PerformerType.UNIVERSAL, Estimator.PerformerVehicle.AUTO, 55.700683, 37.726683, timeToCalc, Estimator.SegmentType.DELIVERY, 55.701557, 37.723044, placeUUID, numberOfPositionsInOrder));
        RouteEstimatorSettingsEntity routeEstimatorSettingsEntity = RouteEstimatorSettingsDao.INSTANCE.findByStoreUuid(placeUUID);
        Allure.step("Проверка полученных значений по GRPC", () -> {
            assertEquals(routeEstimationResponseCar.getPerformers(0).getSegments(0).getEstimateType(), Estimator.SegmentTime.EstimateType.TWO_GIS, "Для расстояния больше 50 метров ответ вернул не 2GIS");
            assertEquals(routeEstimationResponseCar.getPerformers(0).getSegments(0).getEndTime(), addSecondsTimestamp(timeToCalc, toSeconds(routeEstimationResponseCar.getPerformers(0).getSegments(0).getDuration())), "Время завершения сегмента не равно сумме начального времени и времени выполнения сегмента");
            assertEquals(toSeconds(routeEstimationResponseCar.getPerformers(0).getSegments(0).getTimeLag()), routeEstimatorSettingsEntity.getAvgParkingMinVehicle() * 60L, "Для автоуниверсала время на парковку отличается от сохранённого времени в базе");
        });
    }

    @TmsLink("14")
    @Test(description = "Расчёт времени доставки через фоллбек для пешего универсала (расстояние меньше 50 метров)",
            groups = "ondemand-res-smoke")
    public void estimateDeliverySegmentFallbackPedestrian() {
        Estimator.GetRouteEstimationResponse routeEstimationResponse = clientRes.getRouteEstimation(getEstimateRequest(performerUuid, Estimator.PerformerType.UNIVERSAL, Estimator.PerformerVehicle.PEDESTRIAN, 55.700478, 37.727171, timeToCalc, Estimator.SegmentType.DELIVERY, 55.700683, 37.726683, placeUUID, numberOfPositionsInOrder));
        RegionSettingsEntity regionSettingsEntity = RegionSettingsDao.INSTANCE.getSettings(regionId);
        Allure.step("Проверка полученных значений по GRPC", () -> {
            assertEquals(routeEstimationResponse.getPerformers(0).getSegments(0).getEstimateType(), Estimator.SegmentTime.EstimateType.FALLBACK, "Для расстояния меньше 50 метров ответ вернул не фоллбек");
            assertEquals(routeEstimationResponse.getPerformers(0).getSegments(0).getDistance(), 0, "Для фоллбека при расстоянии < 50 метров дальность != 0");
            assertEquals(toSeconds(routeEstimationResponse.getPerformers(0).getSegments(0).getDuration()), regionSettingsEntity.getPedestrianMinimumSegmentLengthSec(), "Для фоллбека при расстоянии < 50 метров длительность != 0");
            assertEquals(routeEstimationResponse.getPerformers(0).getSegments(0).getEndTime(), addSecondsTimestamp(timeToCalc, toSeconds(routeEstimationResponse.getPerformers(0).getSegments(0).getDuration())), "Время завершения сегмента не равно сумме начального времени и времени выполнения сегмента");
            assertEquals(toSeconds(routeEstimationResponse.getPerformers(0).getSegments(0).getTimeLag()), zeroTime, "Для пешего универсала добавилось время на парковку при доставке через фоллбек");
        });
    }

    @TmsLink("42")
    @Test(description = "Расчёт времени доставки через фоллбек для вело универсала (расстояние меньше 50 метров)",
            groups = "ondemand-res-smoke")
    public void estimateDeliverySegmentFallbackBike() {
        Estimator.GetRouteEstimationResponse routeEstimationResponse = clientRes.getRouteEstimation(getEstimateRequest(performerUuid, Estimator.PerformerType.UNIVERSAL, Estimator.PerformerVehicle.BIKE, 55.700478, 37.727171, timeToCalc, Estimator.SegmentType.DELIVERY, 55.700683, 37.726683, placeUUID, numberOfPositionsInOrder));
        RegionSettingsEntity regionSettingsEntity = RegionSettingsDao.INSTANCE.getSettings(regionId);
        Allure.step("Проверка полученных значений по GRPC", () -> {
            assertEquals(routeEstimationResponse.getPerformers(0).getSegments(0).getEstimateType(), Estimator.SegmentTime.EstimateType.FALLBACK, "Для расстояния меньше 50 метров ответ вернул не фоллбек");
            assertEquals(routeEstimationResponse.getPerformers(0).getSegments(0).getDistance(), 0, "Для фоллбека при расстоянии < 50 метров дальность != 0");
            assertEquals(toSeconds(routeEstimationResponse.getPerformers(0).getSegments(0).getDuration()), regionSettingsEntity.getBicycleMinimumSegmentLengthSec(), "Для фоллбека при расстоянии < 50 метров длительность != 0");
            assertEquals(routeEstimationResponse.getPerformers(0).getSegments(0).getEndTime(), addSecondsTimestamp(timeToCalc, toSeconds(routeEstimationResponse.getPerformers(0).getSegments(0).getDuration())), "Время завершения сегмента не равно сумме начального времени и времени выполнения сегмента");
            assertEquals(toSeconds(routeEstimationResponse.getPerformers(0).getSegments(0).getTimeLag()), zeroTime, "Для пешего универсала добавилось время на парковку при доставке через фоллбек");
        });
    }

    @TmsLinks({@TmsLink("6"), @TmsLink("39")})
    @Test(description = "Расчёт времени передачи заказа клиенту для пешего универсала", dataProvider = "transportType", dataProviderClass = DispatchDataProvider.class,
            groups = "ondemand-res-smoke")
    public void estimatePassToClientSegment(Estimator.PerformerVehicle type) {
        Estimator.GetRouteEstimationResponse routeEstimationResponse = clientRes.getRouteEstimation(getEstimateRequest(performerUuid, Estimator.PerformerType.UNIVERSAL, type, 55.700478, 37.727171, timeToCalc, Estimator.SegmentType.PASS_TO_CLIENT, 55.700683, 37.726683, placeUUID, numberOfPositionsInOrder));
        RouteEstimatorSettingsEntity routeEstimatorSettingsEntity = RouteEstimatorSettingsDao.INSTANCE.findByStoreUuid(placeUUID);
        Allure.step("Проверка полученных значений по GRPC", () -> {
            assertEquals(routeEstimationResponse.getPerformers(0).getSegments(0).getEstimateType(), Estimator.SegmentTime.EstimateType.FALLBACK, "Для расчёта времени на передачу заказа клиенту ответ вернул не фоллбек");
            assertEquals(routeEstimationResponse.getPerformers(0).getSegments(0).getDistance(), 0, "Для сегмента передачи заказа клиенту расстояние != 0");
            assertEquals(toSeconds(routeEstimationResponse.getPerformers(0).getSegments(0).getDuration()), routeEstimatorSettingsEntity.getOrderTransferTimeFromDeliveryToClientMin() * 60L, "Для сегмента передачи заказа клиенту значение не совпадает со временем в БД");
            assertEquals(routeEstimationResponse.getPerformers(0).getSegments(0).getEndTime(), addSecondsTimestamp(timeToCalc, toSeconds(routeEstimationResponse.getPerformers(0).getSegments(0).getDuration())), "Время завершения сегмента не равно сумме начального времени и времени выполнения сегмента");
            assertEquals(toSeconds(routeEstimationResponse.getPerformers(0).getSegments(0).getTimeLag()), zeroTime, "Для пешего/вело универсала добавилось время на парковку при передаче клиенту");
        });
    }

    @TmsLink("24")
    @Test(description = "Расчёт времени передачи заказа клиенту для авто универсала",
            groups = "ondemand-res-smoke")
    public void estimatePassToClientSegmentCar() {
        Estimator.GetRouteEstimationResponse routeEstimationResponseCar = clientRes.getRouteEstimation(getEstimateRequest(performerUuid, Estimator.PerformerType.UNIVERSAL, Estimator.PerformerVehicle.AUTO, 55.700478, 37.727171, timeToCalc, Estimator.SegmentType.PASS_TO_CLIENT, 55.700683, 37.726683, placeUUID, numberOfPositionsInOrder));
        RouteEstimatorSettingsEntity routeEstimatorSettingsEntity = RouteEstimatorSettingsDao.INSTANCE.findByStoreUuid(placeUUID);
        Allure.step("Проверка полученных значений по GRPC", () -> {
            assertEquals(routeEstimationResponseCar.getPerformers(0).getSegments(0).getEstimateType(), Estimator.SegmentTime.EstimateType.FALLBACK, "Для расчёта времени на передачу заказа клиенту ответ вернул не фоллбек");
            assertEquals(routeEstimationResponseCar.getPerformers(0).getSegments(0).getDistance(), 0, "Для сегмента передачи заказа клиенту расстояние != 0");
            assertEquals(toSeconds(routeEstimationResponseCar.getPerformers(0).getSegments(0).getDuration()), routeEstimatorSettingsEntity.getOrderTransferTimeFromDeliveryToClientMin() * 60L, "Для сегмента передачи заказа клиенту значение не совпадает со временем в БД");
            assertEquals(routeEstimationResponseCar.getPerformers(0).getSegments(0).getEndTime(), addSecondsTimestamp(timeToCalc, toSeconds(routeEstimationResponseCar.getPerformers(0).getSegments(0).getDuration())), "Время завершения сегмента не равно сумме начального времени и времени выполнения сегмента");
            assertEquals(toSeconds(routeEstimationResponseCar.getPerformers(0).getSegments(0).getTimeLag()), zeroTime, "Для авто универсала добавилось время на парковку при передаче заказа клиенту");
        });
    }
}
