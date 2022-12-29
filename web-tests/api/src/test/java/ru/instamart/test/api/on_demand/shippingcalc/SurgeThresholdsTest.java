package ru.instamart.test.api.on_demand.shippingcalc;

import io.grpc.StatusRuntimeException;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.ShippingCalcBase;
import ru.instamart.grpc.common.GrpcContentHosts;
import ru.instamart.jdbc.dao.shippingcalc.SurgeThresholdsDao;
import io.qameta.allure.TmsLink;
import shippingcalc.*;

import static org.testng.Assert.*;

@Epic("ShippingCalc")
@Feature("SurgeThresholds")
public class SurgeThresholdsTest extends ShippingCalcBase {

    private ShippingcalcGrpc.ShippingcalcBlockingStub clientShippingCalc;
    private final Integer REGION_ID = REGION_ID_WITHOUT_THRESHOLDS + 1;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        clientShippingCalc = ShippingcalcGrpc.newBlockingStub(grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_OPERATIONS_SHIPPINGCALC));
    }

    @TmsLink("548")
    @Story("Set Surge Thresholds")
    @Test(description = "Добавление нового сюрдж-трешхолда для региона",
            groups = "ondemand-shippingcalc")
    public void setSurgeThresholdsNew() {
        var request = SetSurgeThresholdsRequest.newBuilder()
                .addThresholds(SurgeThreshold.newBuilder()
                        .setRegionId(REGION_ID)
                        .setParameters(SurgeThresholdParameters.newBuilder()
                                .addIntervals(SurgeInterval.newBuilder()
                                        .setLeftBoundary(0)
                                        .setRightBoundary(1)
                                        .setPriceAddition(SURGE_LEVEL_ADDITION_DEFAULT)
                                        .setPercentAddition(SURGE_LEVEL_PERCENT_ADDITION)
                                        .setMinCartAddition(SURGE_LEVEL_ADDITION_DEFAULT)
                                        .build())
                                .build())
                        .build())
                .build();
        var response = clientShippingCalc.setSurgeThresholds(request);

        Allure.step("Проверка успешного выполнения запроса", () -> {
            assertTrue(response.toString().isEmpty(), "Не ожидаемый ответ");
            var surgeThresholdsEntity = SurgeThresholdsDao.INSTANCE.getSurgeThresholdByRegionId(REGION_ID);
            assertNotNull(surgeThresholdsEntity, "Не нашли трешхолд");
            assertNotNull(surgeThresholdsEntity.getParameters(), "Пустые параметры");
            assertTrue(surgeThresholdsEntity.getParameters().contains("\"price_addition\": " + SURGE_LEVEL_ADDITION_DEFAULT), "Не верная наценка");
        });
    }

    @TmsLink("549")
    @Story("Set Surge Thresholds")
    @Test(description = "Обновление сюрдж-трешхолда для региона",
            groups = "ondemand-shippingcalc",
            dependsOnMethods = "setSurgeThresholdsNew")
    public void setSurgeThresholdsNotNew() {
        var request = SetSurgeThresholdsRequest.newBuilder()
                .addThresholds(SurgeThreshold.newBuilder()
                        .setRegionId(REGION_ID)
                        .setParameters(SurgeThresholdParameters.newBuilder()
                                .addIntervals(
                                        SurgeInterval.newBuilder()
                                                .setLeftBoundary(1)
                                                .setRightBoundary(2)
                                                .setPriceAddition(SURGE_LEVEL_ADDITION_DEFAULT + 1)
                                                .setPercentAddition(SURGE_LEVEL_PERCENT_ADDITION + 1)
                                                .setMinCartAddition(SURGE_LEVEL_ADDITION_DEFAULT + 1)
                                                .build())
                                .build())
                        .build())
                .build();
        var response = clientShippingCalc.setSurgeThresholds(request);

        Allure.step("Проверка успешного выполнения запроса", () -> {
            assertTrue(response.toString().isEmpty(), "Не ожидаемый ответ");
            var surgeThresholdsEntity = SurgeThresholdsDao.INSTANCE.getSurgeThresholdByRegionId(REGION_ID);
            assertNotNull(surgeThresholdsEntity, "Не нашли трешхолд");
            assertNotNull(surgeThresholdsEntity.getParameters(), "Пустые параметры");
            assertTrue(surgeThresholdsEntity.getParameters().contains("\"price_addition\": " + (SURGE_LEVEL_ADDITION_DEFAULT + 1)), "Не верная наценка");
        });
    }

    @TmsLink("550")
    @Story("Set Surge Thresholds")
    @Test(description = "Установка сюрдж-трешхолдов для нескольких регионов",
            groups = "ondemand-shippingcalc")
    public void setSurgeThresholdsMultiple() {
        var request = SetSurgeThresholdsRequest.newBuilder()
                .addThresholds(SurgeThreshold.newBuilder()
                        .setRegionId(REGION_ID + 1)
                        .setParameters(SurgeThresholdParameters.newBuilder()
                                .addIntervals(SurgeInterval.newBuilder()
                                        .setLeftBoundary(0)
                                        .setRightBoundary(1)
                                        .setPriceAddition(SURGE_LEVEL_ADDITION_DEFAULT)
                                        .setPercentAddition(SURGE_LEVEL_PERCENT_ADDITION)
                                        .setMinCartAddition(SURGE_LEVEL_ADDITION_DEFAULT)
                                        .build())
                                .addIntervals(SurgeInterval.newBuilder()
                                        .setLeftBoundary(1)
                                        .setRightBoundary(2)
                                        .setPriceAddition(SURGE_LEVEL_ADDITION_DEFAULT)
                                        .setPercentAddition(SURGE_LEVEL_PERCENT_ADDITION)
                                        .setMinCartAddition(SURGE_LEVEL_ADDITION_DEFAULT)
                                        .build())
                                .build())
                        .build())
                .addThresholds(SurgeThreshold.newBuilder()
                        .setRegionId(REGION_ID + 2)
                        .setParameters(SurgeThresholdParameters.newBuilder()
                                .addIntervals(SurgeInterval.newBuilder()
                                        .setLeftBoundary(0)
                                        .setRightBoundary(1)
                                        .setPriceAddition(SURGE_LEVEL_ADDITION_DEFAULT)
                                        .setPercentAddition(SURGE_LEVEL_PERCENT_ADDITION)
                                        .setMinCartAddition(SURGE_LEVEL_ADDITION_DEFAULT)
                                        .build())
                                .addIntervals(SurgeInterval.newBuilder()
                                        .setLeftBoundary(1)
                                        .setRightBoundary(2)
                                        .setPriceAddition(SURGE_LEVEL_ADDITION_DEFAULT)
                                        .setPercentAddition(SURGE_LEVEL_PERCENT_ADDITION)
                                        .setMinCartAddition(SURGE_LEVEL_ADDITION_DEFAULT)
                                        .build())
                                .build())
                        .build())
                .build();
        var response = clientShippingCalc.setSurgeThresholds(request);

        Allure.step("Проверка успешного выполнения запроса", () -> {
            assertTrue(response.toString().isEmpty(), "Не ожидаемый ответ");
            var surgeThresholdsEntityFirst = SurgeThresholdsDao.INSTANCE.getSurgeThresholdByRegionId(REGION_ID + 1);
            var surgeThresholdsEntitySecond = SurgeThresholdsDao.INSTANCE.getSurgeThresholdByRegionId(REGION_ID + 2);
            assertNotNull(surgeThresholdsEntityFirst, "Не нашли первый трешхолд");
            assertNotNull(surgeThresholdsEntityFirst.getParameters(), "Пустые первые параметры");
            assertNotNull(surgeThresholdsEntitySecond, "Не нашли второй трешхолд");
            assertNotNull(surgeThresholdsEntitySecond.getParameters(), "Пустые вторые параметры");
        });
    }

    @TmsLink("556")
    @Story("Set Surge Thresholds")
    @Test(description = "Получение ошибки при установлении пустого списка сюрдж-трешхолдов",
            groups = "ondemand-shippingcalc",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: empty thresholds")
    public void setSurgeThresholdsEmpty() {
        var request = SetSurgeThresholdsRequest.newBuilder().build();
        clientShippingCalc.setSurgeThresholds(request);
    }

    @TmsLink("557")
    @Story("Set Surge Thresholds")
    @Test(description = "Получение ошибки при установлении  сюрдж-трешхолда с пустыми параметрами",
            groups = "ondemand-shippingcalc",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: invalid 0 threshold parameters")
    public void setSurgeThresholdsEmptyParameters() {
        var request = SetSurgeThresholdsRequest.newBuilder()
                .addThresholds(SurgeThreshold.newBuilder()
                        .setRegionId(1)
                        .build())
                .build();
        clientShippingCalc.setSurgeThresholds(request);
    }

    @TmsLink("558")
    @Story("Set Surge Thresholds")
    @Test(description = "Получение ошибки при установлении  сюрдж-трешхолда с пустыми интервалами",
            groups = "ondemand-shippingcalc",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: invalid 0 threshold interval parameter")
    public void setSurgeThresholdsEmptyIntervals() {
        var request = SetSurgeThresholdsRequest.newBuilder()
                .addThresholds(SurgeThreshold.newBuilder()
                        .setRegionId(1)
                        .setParameters(SurgeThresholdParameters.newBuilder().build())
                        .build())
                .build();
        clientShippingCalc.setSurgeThresholds(request);
    }

    @TmsLink("547")
    @Story("Get Surge Thresholds")
    @Test(description = "Получение списка региональных трешхолдов сюрджа",
            groups = "ondemand-shippingcalc",
            dependsOnMethods = {"setSurgeThresholdsNotNew", "setSurgeThresholdsMultiple"})
    public void getSurgeThresholds() {
        var request = GetSurgeThresholdsRequest.newBuilder().build();
        var response = clientShippingCalc.getSurgeThresholds(request);

        Allure.step("Проверка списка трешхолдов в ответе", () -> assertTrue(response.getThresholdsCount() > 0, "В ответе пустой список правил"));
    }

    @TmsLink("552")
    @Story("Delete Surge Thresholds")
    @Test(description = "Удаление регионального сюрдж-трешхолда",
            groups = "ondemand-shippingcalc",
            dependsOnMethods = "getSurgeThresholds")
    public void deleteSurgeThresholds() {
        var request = DeleteSurgeThresholdsRequest.newBuilder()
                .addRegionId(REGION_ID)
                .build();
        var response = clientShippingCalc.deleteSurgeThresholds(request);

        Allure.step("Проверка успешного выполнения запроса и удаления в БД", () -> {
            assertTrue(response.toString().isEmpty(), "Не ожидаемый ответ");
            var surgeThresholdsEntity = SurgeThresholdsDao.INSTANCE.getSurgeThresholdByRegionId(REGION_ID);
            assertNull(surgeThresholdsEntity, "Не пустой трешхолд");
        });
    }

    @TmsLink("553")
    @Story("Delete Surge Thresholds")
    @Test(description = "Удаление нескольких региональных сюрдж-трешходов",
            groups = "ondemand-shippingcalc",
            dependsOnMethods = "getSurgeThresholds")
    public void deleteSurgeThresholdsMultiple() {
        var request = DeleteSurgeThresholdsRequest.newBuilder()
                .addRegionId(REGION_ID + 1)
                .addRegionId(REGION_ID + 2)
                .build();
        var response = clientShippingCalc.deleteSurgeThresholds(request);

        Allure.step("Проверка успешного выполнения запроса и удаления в БД", () -> {
            assertTrue(response.toString().isEmpty(), "Не ожидаемый ответ");
            var surgeThresholdsEntityFirst = SurgeThresholdsDao.INSTANCE.getSurgeThresholdByRegionId(REGION_ID + 1);
            var surgeThresholdsEntitySecond = SurgeThresholdsDao.INSTANCE.getSurgeThresholdByRegionId(REGION_ID + 2);
            assertNull(surgeThresholdsEntityFirst, "Не пустой трешхолд");
            assertNull(surgeThresholdsEntitySecond, "Не пустой трешхолд");
        });
    }

    @TmsLink("554")
    @Story("Delete Surge Thresholds")
    @Test(description = "Получение ошибки при удалении регионального сюрдж-трешхолда без указания region_id",
            groups = "ondemand-shippingcalc",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "UNKNOWN: invalid region id")
    public void deleteSurgeThresholdsNoRegionId() {
        var request = DeleteSurgeThresholdsRequest.newBuilder().build();
        clientShippingCalc.deleteSurgeThresholds(request);
    }
}
