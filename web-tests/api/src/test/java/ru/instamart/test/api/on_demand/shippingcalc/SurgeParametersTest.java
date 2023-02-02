package ru.instamart.test.api.on_demand.shippingcalc;

import io.grpc.StatusRuntimeException;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.ShippingCalcBase;
import ru.instamart.grpc.common.GrpcContentHosts;
import io.qameta.allure.TmsLink;
import ru.instamart.jdbc.dao.shippingcalc.SurgeParametersDao;
import shippingcalc.*;
import surgelevelevent.Surgelevelevent.SurgeEvent.Grade;

import java.util.List;
import java.util.Objects;

import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.testng.Assert.*;

@Epic("ShippingCalc")
@Feature("Surge Parameters")
public class SurgeParametersTest extends ShippingCalcBase {

    private ShippingcalcGrpc.ShippingcalcBlockingStub clientShippingCalc;
    private final Integer REGION_ID = REGION_ID_WITHOUT_PARAMETERS + 1;
    private final Integer VERTICAL = REGION_ID;
    private Integer firstParameterId;
    private Integer secondParameterId;
    private Integer thirdParameterId;
    private Integer firstGradeParameterId;
    private Integer secondGradeParameterId;
    private Integer thirdGradeParameterId;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        clientShippingCalc = ShippingcalcGrpc.newBlockingStub(grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_OPERATIONS_SHIPPINGCALC));
    }

    @TmsLink("548")
    @Story("Set Surge Parameters")
    @Test(description = "Добавление нового сюрдж-параметра",
            groups = "ondemand-shippingcalc")
    public void setSurgeParametersNew() {
        var request = SetSurgeParametersRequest.newBuilder()
                .addParameters(SurgeParameters.newBuilder()
                        .setRegionId(REGION_ID)
                        .setVertical(VERTICAL)
                        .setParameters(SurgeParameters.Parameters.newBuilder()
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
        var response = clientShippingCalc.setSurgeParameters(request);

        Allure.step("Проверка успешного выполнения запроса и сохранения в БД", () -> {
            assertTrue(response.toString().isEmpty(), "Не ожидаемый ответ");
            final var surgeThresholdsEntity = SurgeParametersDao.INSTANCE.getSurgeParametersByRegionIdAndVertical(REGION_ID, VERTICAL);
            assertNotNull(surgeThresholdsEntity, "Не нашли трешхолд");
            firstParameterId = surgeThresholdsEntity.getId();
            assertNotNull(surgeThresholdsEntity.getParameters(), "Пустые параметры");
            assertTrue(surgeThresholdsEntity.getParameters().contains("\"price_addition\": " + SURGE_LEVEL_ADDITION_DEFAULT), "Не верная наценка");
        });
    }

    @TmsLink("549")
    @Story("Set Surge Parameters")
    @Test(description = "Обновление сюрдж-параметра",
            groups = "ondemand-shippingcalc",
            dependsOnMethods = "setSurgeParametersNew")
    public void setSurgeParametersNotNew() {
        var request = SetSurgeParametersRequest.newBuilder()
                .addParameters(SurgeParameters.newBuilder()
                        .setRegionId(REGION_ID)
                        .setVertical(VERTICAL)
                        .setParameters(SurgeParameters.Parameters.newBuilder()
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
        var response = clientShippingCalc.setSurgeParameters(request);

        Allure.step("Проверка успешного выполнения запроса и сохранения в БД", () -> {
            assertTrue(response.toString().isEmpty(), "Не ожидаемый ответ");
            final var surgeThresholdsEntity = SurgeParametersDao.INSTANCE.getSurgeParametersByRegionIdAndVertical(REGION_ID, VERTICAL);
            assertNotNull(surgeThresholdsEntity, "Не нашли трешхолд");
            assertNotNull(surgeThresholdsEntity.getParameters(), "Пустые параметры");
            assertTrue(surgeThresholdsEntity.getParameters().contains("\"price_addition\": " + (SURGE_LEVEL_ADDITION_DEFAULT + 1)), "Не верная наценка");
        });
    }

    @TmsLink("550")
    @Story("Set Surge Parameters")
    @Test(description = "Установка нескольких сюрдж-параметров",
            groups = "ondemand-shippingcalc")
    public void setSurgeParametersMultiple() {
        var request = SetSurgeParametersRequest.newBuilder()
                .addParameters(SurgeParameters.newBuilder()
                        .setRegionId(REGION_ID + 1)
                        .setVertical(VERTICAL + 1)
                        .setParameters(SurgeParameters.Parameters.newBuilder()
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
                .addParameters(SurgeParameters.newBuilder()
                        .setRegionId(REGION_ID + 2)
                        .setVertical(VERTICAL + 2)
                        .setParameters(SurgeParameters.Parameters.newBuilder()
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
        var response = clientShippingCalc.setSurgeParameters(request);

        Allure.step("Проверка успешного выполнения запроса и сохранения в БД", () -> {
            assertTrue(response.toString().isEmpty(), "Не ожидаемый ответ");
            final var surgeThresholdsEntityFirst = SurgeParametersDao.INSTANCE.getSurgeParametersByRegionIdAndVertical(REGION_ID + 1, VERTICAL + 1);
            final var surgeThresholdsEntitySecond = SurgeParametersDao.INSTANCE.getSurgeParametersByRegionIdAndVertical(REGION_ID + 2, VERTICAL + 2);
            assertNotNull(surgeThresholdsEntityFirst, "Не нашли первый трешхолд");
            secondParameterId = surgeThresholdsEntityFirst.getId();
            assertNotNull(surgeThresholdsEntityFirst.getParameters(), "Пустые первые параметры");
            assertNotNull(surgeThresholdsEntitySecond, "Не нашли второй трешхолд");
            thirdParameterId = surgeThresholdsEntitySecond.getId();
            assertNotNull(surgeThresholdsEntitySecond.getParameters(), "Пустые вторые параметры");
        });
    }

    @TmsLink("551")
    @Story("Set Surge Parameters")
    @Test(description = "Установка глобальных сюрдж-параметров",
            groups = "ondemand-shippingcalc")
    public void setSurgeParametersGlobal() {
        var request = SetSurgeParametersRequest.newBuilder()
                .addParameters(SurgeParameters.newBuilder()
                        .setRegionId(-1)
                        .setVertical(-1)
                        .setParameters(SurgeParameters.Parameters.newBuilder()
                                .addIntervals(
                                        SurgeInterval.newBuilder()
                                                .setLeftBoundary(0)
                                                .setRightBoundary(0.5f)
                                                .setPriceAddition(0)
                                                .setPercentAddition(0)
                                                .setMinCartAddition(0)
                                                .build())
                                .addIntervals(
                                        SurgeInterval.newBuilder()
                                                .setLeftBoundary(0.5f)
                                                .setRightBoundary(1)
                                                .setPriceAddition(SURGE_LEVEL_ADDITION_DEFAULT)
                                                .setPercentAddition(SURGE_LEVEL_PERCENT_ADDITION)
                                                .setMinCartAddition(SURGE_LEVEL_ADDITION_DEFAULT)
                                                .build())
                                .build())
                        .build())
                .build();
        var response = clientShippingCalc.setSurgeParameters(request);

        Allure.step("Проверка успешного выполнения запроса", () -> assertTrue(response.toString().isEmpty(), "Не ожидаемый ответ"));
    }

    @TmsLink("556")
    @Story("Set Surge Parameters")
    @Test(description = "Получение ошибки при установлении пустого списка сюрдж-параметров",
            groups = "ondemand-shippingcalc",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: empty parameters")
    public void setSurgeParametersEmpty() {
        var request = SetSurgeParametersRequest.newBuilder().build();
        clientShippingCalc.setSurgeParameters(request);
    }

    @TmsLink("557")
    @Story("Set Surge Parameters")
    @Test(description = "Получение ошибки при установлении сюрдж-параметра с пустыми параметрами",
            groups = "ondemand-shippingcalc",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: invalid 0 entry parameters")
    public void setSurgeParametersEmptyParameters() {
        var request = SetSurgeParametersRequest.newBuilder()
                .addParameters(SurgeParameters.newBuilder()
                        .setRegionId(1)
                        .setVertical(1)
                        .build())
                .build();
        clientShippingCalc.setSurgeParameters(request);
    }

    @TmsLink("558")
    @Story("Set Surge Parameters")
    @Test(description = "Получение ошибки при установлении сюрдж-параметра с пустыми интервалами",
            groups = "ondemand-shippingcalc",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: invalid 0 entry interval parameter")
    public void setSurgeParametersEmptyIntervals() {
        var request = SetSurgeParametersRequest.newBuilder()
                .addParameters(SurgeParameters.newBuilder()
                        .setRegionId(1)
                        .setVertical(1)
                        .setParameters(SurgeParameters.Parameters.newBuilder().build())
                        .build())
                .build();
        clientShippingCalc.setSurgeParameters(request);
    }

    @TmsLink("594")
    @Story("Set Surge Parameters")
    @Test(description = "Установка сюрдж-параметра с grade из контракта surgelevelevent",
            groups = "ondemand-shippingcalc")
    public void setSurgeParametersWithGrade() {
        final var randomInt = nextInt(300000, 350000);

        var request = SetSurgeParametersRequest.newBuilder()
                .addParameters(SurgeParameters.newBuilder()
                        .setRegionId(randomInt)
                        .setVertical(randomInt)
                        .setParameters(SurgeParameters.Parameters.newBuilder()
                                .addIntervals(SurgeInterval.newBuilder()
                                        .setLeftBoundary(0)
                                        .setRightBoundary(1)
                                        .setGrade(Grade.SUPPLYMID.toString().toLowerCase())
                                        .setPriceAddition(SURGE_LEVEL_ADDITION_DEFAULT)
                                        .setPercentAddition(SURGE_LEVEL_PERCENT_ADDITION)
                                        .setMinCartAddition(SURGE_LEVEL_ADDITION_DEFAULT)
                                        .build())
                                .build())
                        .build())
                .build();
        var response = clientShippingCalc.setSurgeParameters(request);

        Allure.step("Проверка успешного выполнения запроса и сохранения в БД", () -> {
            assertTrue(response.toString().isEmpty(), "Не ожидаемый ответ");
            final var surgeThresholdsEntity = SurgeParametersDao.INSTANCE.getSurgeParametersByRegionIdAndVertical(randomInt, randomInt);
            assertNotNull(surgeThresholdsEntity, "Не нашли трешхолд");
            firstGradeParameterId = surgeThresholdsEntity.getId();
        });
    }

    @TmsLink("595")
    @Story("Set Surge Parameters")
    @Test(description = "Установка сюрдж-параметра с пустым grade",
            groups = "ondemand-shippingcalc")
    public void setSurgeParametersWithEmptyGrade() {
        final var randomInt = nextInt(300000, 350000);

        var request = SetSurgeParametersRequest.newBuilder()
                .addParameters(SurgeParameters.newBuilder()
                        .setRegionId(randomInt)
                        .setVertical(randomInt)
                        .setParameters(SurgeParameters.Parameters.newBuilder()
                                .addIntervals(SurgeInterval.newBuilder()
                                        .setLeftBoundary(0)
                                        .setRightBoundary(1)
                                        .setGrade("")
                                        .setPriceAddition(SURGE_LEVEL_ADDITION_DEFAULT)
                                        .setPercentAddition(SURGE_LEVEL_PERCENT_ADDITION)
                                        .setMinCartAddition(SURGE_LEVEL_ADDITION_DEFAULT)
                                        .build())
                                .build())
                        .build())
                .build();
        var response = clientShippingCalc.setSurgeParameters(request);

        Allure.step("Проверка успешного выполнения запроса и сохранения в БД", () -> {
            assertTrue(response.toString().isEmpty(), "Не ожидаемый ответ");
            final var surgeThresholdsEntity = SurgeParametersDao.INSTANCE.getSurgeParametersByRegionIdAndVertical(randomInt, randomInt);
            assertNotNull(surgeThresholdsEntity, "Не нашли трешхолд");
            secondGradeParameterId = surgeThresholdsEntity.getId();
        });
    }

    @TmsLink("596")
    @Story("Set Surge Parameters")
    @Test(description = "Установка сюрдж-параметра с grade без числовых интервалов",
            groups = "ondemand-shippingcalc")
    public void setSurgeParametersWithGradeAndEmptyIntervals() {
        final var randomInt = nextInt(300000, 350000);

        var request = SetSurgeParametersRequest.newBuilder()
                .addParameters(SurgeParameters.newBuilder()
                        .setRegionId(randomInt)
                        .setVertical(randomInt)
                        .setParameters(SurgeParameters.Parameters.newBuilder()
                                .addIntervals(SurgeInterval.newBuilder()
                                        .setGrade(Grade.SUPPLYMID.toString().toLowerCase())
                                        .setPriceAddition(SURGE_LEVEL_ADDITION_DEFAULT)
                                        .setPercentAddition(SURGE_LEVEL_PERCENT_ADDITION)
                                        .setMinCartAddition(SURGE_LEVEL_ADDITION_DEFAULT)
                                        .build())
                                .build())
                        .build())
                .build();
        var response = clientShippingCalc.setSurgeParameters(request);

        Allure.step("Проверка успешного выполнения запроса и сохранения в БД", () -> {
            assertTrue(response.toString().isEmpty(), "Не ожидаемый ответ");
            final var surgeThresholdsEntity = SurgeParametersDao.INSTANCE.getSurgeParametersByRegionIdAndVertical(randomInt, randomInt);
            assertNotNull(surgeThresholdsEntity, "Не нашли трешхолд");
            thirdGradeParameterId = surgeThresholdsEntity.getId();
        });
    }

    @TmsLink("597")
    @Story("Set Surge Parameters")
    @Test(description = "Получение ошибки при установке сюрдж-параметров без grade и числовых интервалов",
            groups = "ondemand-shippingcalc",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: validation failed: invalid 0 parameters: right boundary must be greater than left.*")
    public void setSurgeParametersWithEmptyGradeAndEmptyIntervals() {
        var request = SetSurgeParametersRequest.newBuilder()
                .addParameters(SurgeParameters.newBuilder()
                        .setRegionId(666)
                        .setVertical(666)
                        .setParameters(SurgeParameters.Parameters.newBuilder()
                                .addIntervals(SurgeInterval.newBuilder()
                                        .setPriceAddition(SURGE_LEVEL_ADDITION_DEFAULT)
                                        .setPercentAddition(SURGE_LEVEL_PERCENT_ADDITION)
                                        .setMinCartAddition(SURGE_LEVEL_ADDITION_DEFAULT)
                                        .build())
                                .build())
                        .build())
                .build();
        clientShippingCalc.setSurgeParameters(request);
    }

    @TmsLink("622")
    @Story("Set Surge Parameters")
    @Test(description = "Получение ошибки при установке сюрдж-параметра с не валидным grade",
            groups = "ondemand-shippingcalc",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: validation failed: invalid 0 parameters: incorrect grade test")
    public void setSurgeParametersWithNonValidGrade() {
        var request = SetSurgeParametersRequest.newBuilder()
                .addParameters(SurgeParameters.newBuilder()
                        .setRegionId(666)
                        .setVertical(666)
                        .setParameters(SurgeParameters.Parameters.newBuilder()
                                .addIntervals(SurgeInterval.newBuilder()
                                        .setLeftBoundary(0)
                                        .setRightBoundary(1)
                                        .setGrade("test")
                                        .setPriceAddition(SURGE_LEVEL_ADDITION_DEFAULT)
                                        .setPercentAddition(SURGE_LEVEL_PERCENT_ADDITION)
                                        .setMinCartAddition(SURGE_LEVEL_ADDITION_DEFAULT)
                                        .build())
                                .build())
                        .build())
                .build();
        clientShippingCalc.setSurgeParameters(request);
    }

    @TmsLink("547")
    @Story("Get Surge Parameters")
    @Test(description = "Получение списка сюрдж-параметров",
            groups = "ondemand-shippingcalc",
            dependsOnMethods = {"setSurgeParametersNotNew", "setSurgeParametersMultiple"})
    public void getSurgeParameters() {
        var request = GetSurgeParametersRequest.newBuilder().build();
        var response = clientShippingCalc.getSurgeParameters(request);

        Allure.step("Проверка списка трешхолдов в ответе", () -> {
            assertTrue(response.getParametersCount() > 0, "В ответе пустой список параметров");
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(response.getParametersCount() >= 3, "В ответе не ожидаемое кол-во параметров");
            softAssert.assertTrue(response.getParameters(0).getId() > 0, "В ответе пустое id");
            softAssert.assertNotNull(response.getParameters(0).getParameters(), "В ответе пустые параметры");
            softAssert.assertTrue(response.getParameters(0).getParameters().getIntervalsCount() > 0, "В ответе пустые интервалы");
            softAssert.assertAll();
        });
    }

    @TmsLink("552")
    @Story("Delete Surge Parameters")
    @Test(description = "Удаление сюрдж-параметров",
            groups = "ondemand-shippingcalc",
            dependsOnMethods = "getSurgeParameters")
    public void deleteSurgeParameters() {
        var request = DeleteSurgeParametersRequest.newBuilder()
                .addId(firstParameterId)
                .build();
        var response = clientShippingCalc.deleteSurgeParameters(request);

        Allure.step("Проверка успешного выполнения запроса и удаления в БД", () -> {
            assertTrue(response.toString().isEmpty(), "Не ожидаемый ответ");
            final var surgeThresholdsEntity = SurgeParametersDao.INSTANCE.getSurgeParametersByRegionIdAndVertical(REGION_ID, VERTICAL);
            assertNull(surgeThresholdsEntity, "Не пустой трешхолд");
        });
    }

    @TmsLink("553")
    @Story("Delete Surge Parameters")
    @Test(description = "Удаление нескольких сюрдж-параметров",
            groups = "ondemand-shippingcalc",
            dependsOnMethods = "getSurgeParameters")
    public void deleteSurgeParametersMultiple() {
        var request = DeleteSurgeParametersRequest.newBuilder()
                .addId(secondParameterId)
                .addId(thirdParameterId)
                .build();
        var response = clientShippingCalc.deleteSurgeParameters(request);

        Allure.step("Проверка успешного выполнения запроса и удаления в БД", () -> {
            assertTrue(response.toString().isEmpty(), "Не ожидаемый ответ");
            final var surgeThresholdsEntityFirst = SurgeParametersDao.INSTANCE.getSurgeParametersByRegionIdAndVertical(REGION_ID + 1, VERTICAL + 1);
            final var surgeThresholdsEntitySecond = SurgeParametersDao.INSTANCE.getSurgeParametersByRegionIdAndVertical(REGION_ID + 2, VERTICAL + 2);
            assertNull(surgeThresholdsEntityFirst, "Не пустой трешхолд");
            assertNull(surgeThresholdsEntitySecond, "Не пустой трешхолд");
        });
    }

    @TmsLink("554")
    @Story("Delete Surge Parameters")
    @Test(description = "Получение ошибки при удалении сюрдж-параметра без указания id",
            groups = "ondemand-shippingcalc",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "UNKNOWN: invalid id")
    public void deleteSurgeParametersNoRegionId() {
        var request = DeleteSurgeParametersRequest.newBuilder().build();
        clientShippingCalc.deleteSurgeParameters(request);
    }

    @AfterClass(alwaysRun = true)
    public void postConditions() {
        if (Objects.nonNull(firstGradeParameterId) && Objects.nonNull(secondGradeParameterId) && Objects.nonNull(thirdGradeParameterId)) {
            SurgeParametersDao.INSTANCE.deleteSurgeParameters(
                    List.of(firstGradeParameterId, secondGradeParameterId, thirdGradeParameterId)
            );
        }
    }
}