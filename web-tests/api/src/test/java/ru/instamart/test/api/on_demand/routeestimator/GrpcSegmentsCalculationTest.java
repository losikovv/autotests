package ru.instamart.test.api.on_demand.routeestimator;

import com.google.protobuf.Timestamp;
import estimator.Estimator;
import estimator.RouteEstimatorGrpc;
import io.qameta.allure.Allure;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.grpc.common.GrpcBase;
import ru.instamart.jdbc.dao.routeestimator.RouteEstimatorSettingsDao;
import ru.instamart.jdbc.entity.routeestimator.RouteEstimatorSettingsEntity;
import ru.sbermarket.qase.annotation.CaseId;

import static com.google.protobuf.util.Durations.toSeconds;
import static org.testng.Assert.assertEquals;
import static ru.instamart.api.helper.RouteEstimatorHelper.getEstimateRequest;
import static ru.instamart.grpc.common.GrpcContentHosts.PAAS_CONTENT_OPERATIONS_ROUTE_ESTIMATOR;
import static ru.instamart.kraken.util.TimeUtil.*;

public class GrpcSegmentsCalculationTest extends GrpcBase {

    private RouteEstimatorGrpc.RouteEstimatorBlockingStub clientRes;
    private final Timestamp timeToCalc = getTimestamp();
    private final long zeroTime = 0;
    private final String performerUuid = "e86263fd-73f3-4973-83b1-c957bd00acba";
    private final String placeUUID = "684609ad-6360-4bae-9556-03918c1e41c1";
    private final int numberOfPositionsInOrder = 3;


    @BeforeClass(alwaysRun = true)
    public void createClient() {
        channel = grpc.createChannel(PAAS_CONTENT_OPERATIONS_ROUTE_ESTIMATOR);
        clientRes = estimator.RouteEstimatorGrpc.newBlockingStub(channel);
    }

    @CaseId(1)
    @Test(description = "Расчёт времени подлёта через 2GIS для пешего универсала")
    public void estimateArriveSegmentPedestrian() {
        final var routeEstimationResponse = clientRes.getRouteEstimation(getEstimateRequest(performerUuid, Estimator.PerformerType.UNIVERSAL, Estimator.PerformerVehicle.PEDESTRIAN,55.698762, 37.728196, timeToCalc, Estimator.SegmentType.ARRIVE, 55.700683, 37.726683, placeUUID, numberOfPositionsInOrder));
        Allure.step("Проверка полученных значений по GRPC", () -> {
            assertEquals(routeEstimationResponse.getPerformers(0).getSegments(0).getEstimateType(), Estimator.SegmentTime.EstimateType.TWO_GIS, "Для расстояния больше 50 метров ответ вернул не 2GIS");
            assertEquals(routeEstimationResponse.getPerformers(0).getSegments(0).getEndTime(), addSecondsTimestamp(timeToCalc, toSeconds(routeEstimationResponse.getPerformers(0).getSegments(0).getDuration())), "Время завершения сегмента не равно сумме начального времени и времени выполнения сегмента");
            assertEquals(toSeconds(routeEstimationResponse.getPerformers(0).getSegments(0).getTimeLag()), zeroTime, "Для пешего универсала добавилось время на парковку при подлёте");
        });
    }

    @CaseId(9)
    @Test(description = "Расчёт времени подлёта через 2GIS для авто универсала")
    public void estimateArriveSegmentCar() {
        Estimator.GetRouteEstimationResponse routeEstimationResponseCar = clientRes.getRouteEstimation(getEstimateRequest(performerUuid, Estimator.PerformerType.UNIVERSAL, Estimator.PerformerVehicle.AUTO, 55.698762, 37.728196, timeToCalc, Estimator.SegmentType.ARRIVE, 55.700683, 37.726683, placeUUID, numberOfPositionsInOrder));
        RouteEstimatorSettingsEntity routeEstimatorSettingsEntity = RouteEstimatorSettingsDao.INSTANCE.findByStoreUuid(placeUUID);
        Allure.step("Проверка полученных значений по GRPC", () -> {
            assertEquals(routeEstimationResponseCar.getPerformers(0).getSegments(0).getEstimateType(), Estimator.SegmentTime.EstimateType.TWO_GIS, "Для расстояния больше 50 метров ответ вернул не 2GIS");
            assertEquals(routeEstimationResponseCar.getPerformers(0).getSegments(0).getEndTime(), addSecondsTimestamp(timeToCalc, toSeconds(routeEstimationResponseCar.getPerformers(0).getSegments(0).getDuration())), "Время завершения сегмента не равно сумме начального времени и времени выполнения сегмента");
            assertEquals(toSeconds(routeEstimationResponseCar.getPerformers(0).getSegments(0).getTimeLag()), routeEstimatorSettingsEntity.getAvgParkingMinVehicle() * 60L, "Для авто универсала время на парковку отличается от сохранённого времени в базе");
        });
    }
    @CaseId(34)
    @Test(description = "Расчёт времени подлёта через 2GIS для вело универсала")
    public void estimateArriveSegmentBike() {
        Estimator.GetRouteEstimationResponse routeEstimationResponseBike = clientRes.getRouteEstimation(getEstimateRequest(performerUuid, Estimator.PerformerType.UNIVERSAL, Estimator.PerformerVehicle.BIKE, 55.698762, 37.728196, timeToCalc, Estimator.SegmentType.ARRIVE, 55.700683, 37.726683, placeUUID, numberOfPositionsInOrder));
        Allure.step("Проверка полученных значений по GRPC", () -> {
            assertEquals(routeEstimationResponseBike.getPerformers(0).getSegments(0).getEstimateType(), Estimator.SegmentTime.EstimateType.TWO_GIS, "Для расстояния больше 50 метров ответ вернул не 2GIS");
            assertEquals(routeEstimationResponseBike.getPerformers(0).getSegments(0).getEndTime(), addSecondsTimestamp(timeToCalc, toSeconds(routeEstimationResponseBike.getPerformers(0).getSegments(0).getDuration())), "Время завершения сегмента не равно сумме начального времени и времени выполнения сегмента");
            assertEquals(toSeconds(routeEstimationResponseBike.getPerformers(0).getSegments(0).getTimeLag()), zeroTime, "Для вело универсала добавилось время на парковку при подлёте");
        });
    }

    @CaseId(11)
    @Test(description = "Расчёт времени подлёта через фоллбек (расстояние между точками меньше 50 метров)")
    public void estimateArriveSegmentFallback() {
        Estimator.GetRouteEstimationResponse routeEstimationResponse = clientRes.getRouteEstimation(getEstimateRequest(performerUuid, Estimator.PerformerType.UNIVERSAL, Estimator.PerformerVehicle.PEDESTRIAN, 55.700478, 37.727171, timeToCalc, Estimator.SegmentType.ARRIVE, 55.700683, 37.726683, placeUUID, numberOfPositionsInOrder));
        Allure.step("Проверка полученных значений по GRPC", () -> {
            assertEquals(routeEstimationResponse.getPerformers(0).getSegments(0).getEstimateType(), Estimator.SegmentTime.EstimateType.FALLBACK, "Для расстояния меньше 50 метров ответ вернул не фоллбек");
            assertEquals(routeEstimationResponse.getPerformers(0).getSegments(0).getDistance(), 0, "Для фоллбека при расстоянии < 50 метров дальность != 0");
            assertEquals(toSeconds(routeEstimationResponse.getPerformers(0).getSegments(0).getDuration()), zeroTime, "Для фоллбека при расстоянии < 50 метров длительность != 0");
            assertEquals(routeEstimationResponse.getPerformers(0).getSegments(0).getEndTime(), addSecondsTimestamp(timeToCalc, toSeconds(routeEstimationResponse.getPerformers(0).getSegments(0).getDuration())), "Время завершения сегмента не равно сумме начального времени и времени выполнения сегмента");
            assertEquals(toSeconds(routeEstimationResponse.getPerformers(0).getSegments(0).getTimeLag()), zeroTime, "Для пешего универсала добавилось время на парковку при подлёте через фоллбек");
        });
    }

    @CaseId(2)
    @Test(description = "Расчёт времени сборки для пешего универсала")
    public void estimateAssemblySegment() {
        Estimator.GetRouteEstimationResponse routeEstimationResponse = clientRes.getRouteEstimation(getEstimateRequest(performerUuid, Estimator.PerformerType.UNIVERSAL, Estimator.PerformerVehicle.PEDESTRIAN, 55.700478, 37.727171, timeToCalc, Estimator.SegmentType.ASSEMBLY, 55.700683, 37.726683, placeUUID, numberOfPositionsInOrder));
        RouteEstimatorSettingsEntity routeEstimatorSettingsEntity = RouteEstimatorSettingsDao.INSTANCE.findByStoreUuid(placeUUID);
        Allure.step("Проверка полученных значений по GRPC", () -> {
            assertEquals(routeEstimationResponse.getPerformers(0).getSegments(0).getEstimateType(), Estimator.SegmentTime.EstimateType.FALLBACK, "Для расчёта сборки ответ вернул не фоллбек");
            assertEquals(routeEstimationResponse.getPerformers(0).getSegments(0).getDistance(), 0, "Для сегмента сборки расстояние != 0");
            assertEquals(toSeconds(routeEstimationResponse.getPerformers(0).getSegments(0).getDuration()), (long) routeEstimatorSettingsEntity.getAveragePerPositionAssemblyTime() * numberOfPositionsInOrder + routeEstimatorSettingsEntity.getAdditionalAssemblyTime(), "Для сегмента сборки длительность не совпадает с расчитанной по формуле");
            assertEquals(routeEstimationResponse.getPerformers(0).getSegments(0).getEndTime(), addSecondsTimestamp(timeToCalc, toSeconds(routeEstimationResponse.getPerformers(0).getSegments(0).getDuration())), "Время завершения сегмента не равно сумме начального времени и времени выполнения сегмента");
            assertEquals(toSeconds(routeEstimationResponse.getPerformers(0).getSegments(0).getTimeLag()), zeroTime, "Для пешего универсала добавилось время на парковку при сборке");
        });
    }

    @CaseId(7)
    @Test(description = "Расчёт времени сборки для авто универсалов")
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
    @CaseId(35)
    @Test(description = "Расчёт времени сборки для вело универсалов")
    public void estimateAssemblySegmentBikeUniversal() {
        RouteEstimatorSettingsEntity routeEstimatorSettingsEntity = RouteEstimatorSettingsDao.INSTANCE.findByStoreUuid(placeUUID);
        Estimator.GetRouteEstimationResponse routeEstimationResponseBike = clientRes.getRouteEstimation(getEstimateRequest(performerUuid, Estimator.PerformerType.UNIVERSAL, Estimator.PerformerVehicle.BIKE, 55.700478, 37.727171, timeToCalc, Estimator.SegmentType.ASSEMBLY, 55.700683, 37.726683, placeUUID, numberOfPositionsInOrder));
        Allure.step("Проверка полученных значений по GRPC", () -> {
            assertEquals(routeEstimationResponseBike.getPerformers(0).getSegments(0).getEstimateType(), Estimator.SegmentTime.EstimateType.FALLBACK, "Для расчёта сборки ответ вернул не фоллбек");
            assertEquals(routeEstimationResponseBike.getPerformers(0).getSegments(0).getDistance(), 0, "Для сегмента сборки расстояние != 0");
            assertEquals(toSeconds(routeEstimationResponseBike.getPerformers(0).getSegments(0).getDuration()), (long) routeEstimatorSettingsEntity.getAveragePerPositionAssemblyTime() * numberOfPositionsInOrder + routeEstimatorSettingsEntity.getAdditionalAssemblyTime(), "Для сегмента сборки длительность не совпадает с расчитанной по формуле");
            assertEquals(routeEstimationResponseBike.getPerformers(0).getSegments(0).getEndTime(), addSecondsTimestamp(timeToCalc, toSeconds(routeEstimationResponseBike.getPerformers(0).getSegments(0).getDuration())), "Время завершения сегмента не равно сумме начального времени и времени выполнения сегмента");
            assertEquals(toSeconds(routeEstimationResponseBike.getPerformers(0).getSegments(0).getTimeLag()), zeroTime, "Для вело универсала добавилось время на парковку при сборке");
        });
    }

    @CaseId(4)
    @Test(description = "Расчёт времени передачи от сборки в доставку для пешего универсала")
    public void estimateTransferFromAssemblyToDeliverySegment() {
        Estimator.GetRouteEstimationResponse routeEstimationResponse = clientRes.getRouteEstimation(getEstimateRequest(performerUuid, Estimator.PerformerType.UNIVERSAL, Estimator.PerformerVehicle.PEDESTRIAN, 55.700478, 37.727171,timeToCalc, Estimator.SegmentType.PASS_TO_DELIVERY, 55.700683, 37.726683, placeUUID, numberOfPositionsInOrder));
        RouteEstimatorSettingsEntity routeEstimatorSettingsEntity = RouteEstimatorSettingsDao.INSTANCE.findByStoreUuid(placeUUID);
        Allure.step("Проверка полученных значений по GRPC", () -> {
            assertEquals(routeEstimationResponse.getPerformers(0).getSegments(0).getEstimateType(), Estimator.SegmentTime.EstimateType.FALLBACK, "Для расчёта времени на передачу заказа курьеру ответ вернул не фоллбек");
            assertEquals(routeEstimationResponse.getPerformers(0).getSegments(0).getDistance(), 0, "Для сегмента передачи заказа курьеру расстояние != 0");
            assertEquals(toSeconds(routeEstimationResponse.getPerformers(0).getSegments(0).getDuration()), routeEstimatorSettingsEntity.getOrderTransferTimeFromAssemblyToDeliveryMin() * 60L, "Для сегмента передачи заказа курьеру значение не совпадает со временем в БД");
            assertEquals(routeEstimationResponse.getPerformers(0).getSegments(0).getEndTime(), addSecondsTimestamp(timeToCalc, toSeconds(routeEstimationResponse.getPerformers(0).getSegments(0).getDuration())), "Время завершения сегмента не равно сумме начального времени и времени выполнения сегмента");
            assertEquals(toSeconds(routeEstimationResponse.getPerformers(0).getSegments(0).getTimeLag()), zeroTime, "Для пешего универсала добавилось время на парковку при передаче заказа курьеру");
        });
    }

    @CaseId(8)
    @Test(description = "Расчёт времени передачи от сборки в доставку для авто универсалов")
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
    @CaseId(36)
    @Test(description = "Расчёт времени передачи от сборки в доставку для вело универсалов")
    public void estimateTransferFromAssemblyToDeliverySegmentBike() {
        Estimator.GetRouteEstimationResponse routeEstimationResponseBike = clientRes.getRouteEstimation(getEstimateRequest(performerUuid, Estimator.PerformerType.UNIVERSAL, Estimator.PerformerVehicle.AUTO, 55.700478, 37.727171, timeToCalc, Estimator.SegmentType.PASS_TO_DELIVERY, 55.700683,37.726683, placeUUID, numberOfPositionsInOrder));
        RouteEstimatorSettingsEntity routeEstimatorSettingsEntity = RouteEstimatorSettingsDao.INSTANCE.findByStoreUuid(placeUUID);
        Allure.step("Проверка полученных значений по GRPC", () -> {
            assertEquals(routeEstimationResponseBike.getPerformers(0).getSegments(0).getEstimateType(), Estimator.SegmentTime.EstimateType.FALLBACK, "Для расчёта времени на передачу курьеру ответ вернул не фоллбек");
            assertEquals(routeEstimationResponseBike.getPerformers(0).getSegments(0).getDistance(), 0, "Для сегмента передачи заказа курьеру расстояние != 0");
            assertEquals(toSeconds(routeEstimationResponseBike.getPerformers(0).getSegments(0).getDuration()), routeEstimatorSettingsEntity.getOrderTransferTimeFromAssemblyToDeliveryMin() * 60L, "Для сегмента передачи заказа курьеру значение не совпадает со временем в БД");
            assertEquals(routeEstimationResponseBike.getPerformers(0).getSegments(0).getEndTime(), addSecondsTimestamp(timeToCalc, toSeconds(routeEstimationResponseBike.getPerformers(0).getSegments(0).getDuration())), "Время завершения сегмента не равно сумме начального времени и времени выполнения сегмента");
            assertEquals(toSeconds(routeEstimationResponseBike.getPerformers(0).getSegments(0).getTimeLag()), zeroTime, "Для вело универсала добавилось время на парковку при передаче заказа курьеру");
        });
    }

    @CaseId(23)
    @Test(description = "Расчёт времени получения заказа в доставку для пешего универсала")
    public void estimateReceivingForDeliverySegment() {
        Estimator.GetRouteEstimationResponse routeEstimationResponse = clientRes.getRouteEstimation(getEstimateRequest(performerUuid, Estimator.PerformerType.UNIVERSAL, Estimator.PerformerVehicle.PEDESTRIAN, 55.700478, 37.727171, timeToCalc, Estimator.SegmentType.RECEIVING_FOR_DELIVERY, 55.700683, 37.726683, placeUUID, numberOfPositionsInOrder));
        RouteEstimatorSettingsEntity routeEstimatorSettingsEntity = RouteEstimatorSettingsDao.INSTANCE.findByStoreUuid(placeUUID);
        Allure.step("Проверка полученных значений по GRPC", () -> {
            assertEquals(routeEstimationResponse.getPerformers(0).getSegments(0).getEstimateType(), Estimator.SegmentTime.EstimateType.FALLBACK, "Для расчёта времени на получение заказа в доставку ответ вернул не фоллбек");
            assertEquals(routeEstimationResponse.getPerformers(0).getSegments(0).getDistance(), 0, "Для сегмента получения заказа в доставку расстояние != 0");
            assertEquals(toSeconds(routeEstimationResponse.getPerformers(0).getSegments(0).getDuration()), routeEstimatorSettingsEntity.getOrderReceiveTimeFromAssemblyToDeliveryMin() * 60L, "Для сегмента получения заказа в доставку значение не совпадает со временем в БД");
            assertEquals(routeEstimationResponse.getPerformers(0).getSegments(0).getEndTime(), addSecondsTimestamp(timeToCalc, toSeconds(routeEstimationResponse.getPerformers(0).getSegments(0).getDuration())), "Время завершения сегмента не равно сумме начального времени и времени выполнения сегмента");
            assertEquals(toSeconds(routeEstimationResponse.getPerformers(0).getSegments(0).getTimeLag()), zeroTime, "Для пешего универсала добавилось время на парковку при получении заказа в доставку");
        });
    }

    @CaseId(3)
    @Test(description = "Расчёт времени получения заказа в доставку для авто универсалов")
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
    @CaseId(37)
    @Test(description = "Расчёт времени получения заказа в доставку для вело универсалов")
    public void estimateReceivingForDeliverySegmentBike() {
        Estimator.GetRouteEstimationResponse routeEstimationResponseBike = clientRes.getRouteEstimation(getEstimateRequest(performerUuid, Estimator.PerformerType.UNIVERSAL, Estimator.PerformerVehicle.BIKE, 55.700478, 37.727171, timeToCalc, Estimator.SegmentType.RECEIVING_FOR_DELIVERY, 55.700683, 37.726683, placeUUID, numberOfPositionsInOrder));
        RouteEstimatorSettingsEntity routeEstimatorSettingsEntity = RouteEstimatorSettingsDao.INSTANCE.findByStoreUuid(placeUUID);
        Allure.step("Проверка полученных значений по GRPC", () -> {
            assertEquals(routeEstimationResponseBike.getPerformers(0).getSegments(0).getEstimateType(), Estimator.SegmentTime.EstimateType.FALLBACK, "Для расчёта времени на получение заказа в доставку ответ вернул не фоллбек");
            assertEquals(routeEstimationResponseBike.getPerformers(0).getSegments(0).getDistance(), 0, "Для сегмента получения заказа в доставку расстояние != 0");
            assertEquals(toSeconds(routeEstimationResponseBike.getPerformers(0).getSegments(0).getDuration()), routeEstimatorSettingsEntity.getOrderReceiveTimeFromAssemblyToDeliveryMin() * 60L, "Для сегмента получения заказа в доставку значение не совпадает со временем в БД");
            assertEquals(routeEstimationResponseBike.getPerformers(0).getSegments(0).getEndTime(), addSecondsTimestamp(timeToCalc, toSeconds(routeEstimationResponseBike.getPerformers(0).getSegments(0).getDuration())), "Время завершения сегмента не равно сумме начального времени и времени выполнения сегмента");
            assertEquals(toSeconds(routeEstimationResponseBike.getPerformers(0).getSegments(0).getTimeLag()), zeroTime, "Для вело универсала добавилось время на парковку при получении заказа в доставку");
        });
    }

    @CaseId(5)
    @Test(description = "Расчёт времени доставки через 2GIS для пешего универсала")
    public void estimateDeliverySegmentPedestrian() {
        Estimator.GetRouteEstimationResponse routeEstimationResponse = clientRes.getRouteEstimation(getEstimateRequest(performerUuid, Estimator.PerformerType.UNIVERSAL, Estimator.PerformerVehicle.PEDESTRIAN, 55.700683, 37.726683, timeToCalc, Estimator.SegmentType.DELIVERY, 55.701557, 37.723044, placeUUID, numberOfPositionsInOrder));
        Allure.step("Проверка полученных значений по GRPC", () -> {
            assertEquals(routeEstimationResponse.getPerformers(0).getSegments(0).getEstimateType(), Estimator.SegmentTime.EstimateType.TWO_GIS, "Для расстояния больше 50 метров ответ вернул не 2GIS");
            assertEquals(routeEstimationResponse.getPerformers(0).getSegments(0).getEndTime(), addSecondsTimestamp(timeToCalc, toSeconds(routeEstimationResponse.getPerformers(0).getSegments(0).getDuration())), "Время завершения сегмента не равно сумме начального времени и времени выполнения сегмента");
            assertEquals(toSeconds(routeEstimationResponse.getPerformers(0).getSegments(0).getTimeLag()), zeroTime, "Для пешего универсала добавилось время на парковку при доставке");
        });
    }

    @CaseId(12)
    @Test(description = "Расчёт времени доставки через 2GIS для авто универсала")
    public void estimateDeliverySegmentCar() {
        Estimator.GetRouteEstimationResponse routeEstimationResponseCar = clientRes.getRouteEstimation(getEstimateRequest(performerUuid, Estimator.PerformerType.UNIVERSAL, Estimator.PerformerVehicle.AUTO, 55.700683, 37.726683, timeToCalc, Estimator.SegmentType.DELIVERY, 55.701557, 37.723044, placeUUID, numberOfPositionsInOrder));
        RouteEstimatorSettingsEntity routeEstimatorSettingsEntity = RouteEstimatorSettingsDao.INSTANCE.findByStoreUuid(placeUUID);
        Allure.step("Проверка полученных значений по GRPC", () -> {
            assertEquals(routeEstimationResponseCar.getPerformers(0).getSegments(0).getEstimateType(), Estimator.SegmentTime.EstimateType.TWO_GIS, "Для расстояния больше 50 метров ответ вернул не 2GIS");
            assertEquals(routeEstimationResponseCar.getPerformers(0).getSegments(0).getEndTime(), addSecondsTimestamp(timeToCalc, toSeconds(routeEstimationResponseCar.getPerformers(0).getSegments(0).getDuration())), "Время завершения сегмента не равно сумме начального времени и времени выполнения сегмента");
            assertEquals(toSeconds(routeEstimationResponseCar.getPerformers(0).getSegments(0).getTimeLag()), routeEstimatorSettingsEntity.getAvgParkingMinVehicle() * 60L, "Для автоуниверсала время на парковку отличается от сохранённого времени в базе");
        });
    }
    @CaseId(38)
    @Test(description = "Расчёт времени доставки через 2GIS для вело универсала")
    public void estimateDeliverySegmentBike() {
        Estimator.GetRouteEstimationResponse routeEstimationResponseBike = clientRes.getRouteEstimation(getEstimateRequest(performerUuid, Estimator.PerformerType.UNIVERSAL, Estimator.PerformerVehicle.BIKE, 55.700683, 37.726683, timeToCalc, Estimator.SegmentType.DELIVERY, 55.701557, 37.723044, placeUUID, numberOfPositionsInOrder));
        Allure.step("Проверка полученных значений по GRPC", () -> {
            assertEquals(routeEstimationResponseBike.getPerformers(0).getSegments(0).getEstimateType(), Estimator.SegmentTime.EstimateType.TWO_GIS, "Для расстояния больше 50 метров ответ вернул не 2GIS");
            assertEquals(routeEstimationResponseBike.getPerformers(0).getSegments(0).getEndTime(), addSecondsTimestamp(timeToCalc, toSeconds(routeEstimationResponseBike.getPerformers(0).getSegments(0).getDuration())), "Время завершения сегмента не равно сумме начального времени и времени выполнения сегмента");
            assertEquals(toSeconds(routeEstimationResponseBike.getPerformers(0).getSegments(0).getTimeLag()), zeroTime, "Для велоуниверсала добавилось время на парковку при доставке");
        });
    }

    @CaseId(11)
    @Test(description = "Расчёт времени доставки через фоллбек (расстояние меньше 50 метров)")
    public void estimateDeliverySegmentFallback() {
        Estimator.GetRouteEstimationResponse routeEstimationResponse = clientRes.getRouteEstimation(getEstimateRequest(performerUuid, Estimator.PerformerType.UNIVERSAL, Estimator.PerformerVehicle.PEDESTRIAN, 55.700478, 37.727171, timeToCalc, Estimator.SegmentType.DELIVERY, 55.700683, 37.726683, placeUUID, numberOfPositionsInOrder));
        Allure.step("Проверка полученных значений по GRPC", () -> {
            assertEquals(routeEstimationResponse.getPerformers(0).getSegments(0).getEstimateType(), Estimator.SegmentTime.EstimateType.FALLBACK, "Для расстояния меньше 50 метров ответ вернул не фоллбек");
            assertEquals(routeEstimationResponse.getPerformers(0).getSegments(0).getDistance(), 0, "Для фоллбека при расстоянии < 50 метров дальность != 0");
            assertEquals(toSeconds(routeEstimationResponse.getPerformers(0).getSegments(0).getDuration()), zeroTime, "Для фоллбека при расстоянии < 50 метров длительность != 0");
            assertEquals(routeEstimationResponse.getPerformers(0).getSegments(0).getEndTime(), addSecondsTimestamp(timeToCalc, toSeconds(routeEstimationResponse.getPerformers(0).getSegments(0).getDuration())), "Время завершения сегмента не равно сумме начального времени и времени выполнения сегмента");
            assertEquals(toSeconds(routeEstimationResponse.getPerformers(0).getSegments(0).getTimeLag()), zeroTime, "Для пешего универсала добавилось время на парковку при доставке через фоллбек");
        });
    }

    @CaseId(6)
    @Test(description = "Расчёт времени передачи заказа клиенту для пешего универсала")
    public void estimatePassToClientSegment() {
        Estimator.GetRouteEstimationResponse routeEstimationResponse = clientRes.getRouteEstimation(getEstimateRequest(performerUuid, Estimator.PerformerType.UNIVERSAL, Estimator.PerformerVehicle.PEDESTRIAN, 55.700478, 37.727171, timeToCalc, Estimator.SegmentType.PASS_TO_CLIENT, 55.700683, 37.726683, placeUUID, numberOfPositionsInOrder));
        RouteEstimatorSettingsEntity routeEstimatorSettingsEntity = RouteEstimatorSettingsDao.INSTANCE.findByStoreUuid(placeUUID);
        Allure.step("Проверка полученных значений по GRPC", () -> {
            assertEquals(routeEstimationResponse.getPerformers(0).getSegments(0).getEstimateType(), Estimator.SegmentTime.EstimateType.FALLBACK, "Для расчёта времени на передачу заказа клиенту ответ вернул не фоллбек");
            assertEquals(routeEstimationResponse.getPerformers(0).getSegments(0).getDistance(), 0, "Для сегмента передачи заказа клиенту расстояние != 0");
            assertEquals(toSeconds(routeEstimationResponse.getPerformers(0).getSegments(0).getDuration()), routeEstimatorSettingsEntity.getOrderTransferTimeFromDeliveryToClientMin() * 60L, "Для сегмента передачи заказа клиенту значение не совпадает со временем в БД");
            assertEquals(routeEstimationResponse.getPerformers(0).getSegments(0).getEndTime(), addSecondsTimestamp(timeToCalc, toSeconds(routeEstimationResponse.getPerformers(0).getSegments(0).getDuration())), "Время завершения сегмента не равно сумме начального времени и времени выполнения сегмента");
            assertEquals(toSeconds(routeEstimationResponse.getPerformers(0).getSegments(0).getTimeLag()), zeroTime, "Для пешего универсала добавилось время на парковку при передаче клиенту");
        });
    }

    @CaseId(24)
    @Test(description = "Расчёт времени передачи заказа клиенту для авто универсала")
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
    @CaseId(39)
    @Test(description = "Расчёт времени передачи заказа клиенту для вело универсала")
    public void estimatePassToClientSegmentBike() {
        Estimator.GetRouteEstimationResponse routeEstimationResponseBike = clientRes.getRouteEstimation(getEstimateRequest(performerUuid, Estimator.PerformerType.UNIVERSAL, Estimator.PerformerVehicle.BIKE, 55.700478, 37.727171, timeToCalc, Estimator.SegmentType.PASS_TO_CLIENT, 55.700683, 37.726683, placeUUID, numberOfPositionsInOrder));
        RouteEstimatorSettingsEntity routeEstimatorSettingsEntity = RouteEstimatorSettingsDao.INSTANCE.findByStoreUuid(placeUUID);
        Allure.step("Проверка полученных значений по GRPC", () -> {
            assertEquals(routeEstimationResponseBike.getPerformers(0).getSegments(0).getEstimateType(), Estimator.SegmentTime.EstimateType.FALLBACK, "Для расчёта времени на передачу заказа клиенту ответ вернул не фоллбек");
            assertEquals(routeEstimationResponseBike.getPerformers(0).getSegments(0).getDistance(), 0, "Для сегмента передачи заказа клиенту расстояние != 0");
            assertEquals(toSeconds(routeEstimationResponseBike.getPerformers(0).getSegments(0).getDuration()), routeEstimatorSettingsEntity.getOrderTransferTimeFromDeliveryToClientMin() * 60L, "Для сегмента передачи заказа клиенту значение не совпадает со временем в БД");
            assertEquals(routeEstimationResponseBike.getPerformers(0).getSegments(0).getEndTime(), addSecondsTimestamp(timeToCalc, toSeconds(routeEstimationResponseBike.getPerformers(0).getSegments(0).getDuration())), "Время завершения сегмента не равно сумме начального времени и времени выполенения сегмента");
            assertEquals(toSeconds(routeEstimationResponseBike.getPerformers(0).getSegments(0).getTimeLag()), zeroTime, "Для вело универсала добавилось время на парковку при передаче заказа клиенту");
        });
    }
}
