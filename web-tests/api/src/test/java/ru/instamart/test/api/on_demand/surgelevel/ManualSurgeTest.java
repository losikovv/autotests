package ru.instamart.test.api.on_demand.surgelevel;

import events.CandidateChangesOuterClass;
import io.grpc.StatusRuntimeException;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.helper.KafkaHelper;
import ru.instamart.grpc.common.GrpcBase;
import ru.instamart.grpc.common.GrpcContentHosts;
import ru.instamart.jdbc.dao.surgelevel.ResultDao;
import ru.instamart.jdbc.dao.surgelevel.StoreDao;
import ru.instamart.jdbc.entity.surgelevel.ResultEntity;
import ru.instamart.kraken.util.ThreadUtil;
import io.qameta.allure.TmsLinks;
import io.qameta.allure.TmsLink;
import surgelevel.ServiceGrpc;
import surgelevel.Surgelevel;
import surgelevelevent.Surgelevelevent;
import surgelevelevent.Surgelevelevent.SurgeEvent.Method;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.apache.commons.lang3.RandomUtils.nextInt;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.helper.SurgeLevelHelper.*;
import static ru.instamart.kraken.enums.ScheduleType.DISPATCH;
import static ru.instamart.kraken.util.TimeUtil.getDateMinusSec;
import static ru.instamart.kraken.util.TimeUtil.getDatePlusSec;

@Epic("Surgelevel")
@Feature("Manual Surge")
public class ManualSurgeTest extends GrpcBase {

    private ServiceGrpc.ServiceBlockingStub client;
    protected static final KafkaHelper kafka = new KafkaHelper();
    private final String FIRST_STORE_ID = UUID.randomUUID().toString();
    private final String SECOND_STORE_ID = UUID.randomUUID().toString();
    private final int DELIVERY_AREA_ID = nextInt(150000, 200000);
    private final int LONG_TIMEOUT = 10;
    private final float SURGE_LEVEL = 5;
    private String expiredAt;

    @BeforeClass(alwaysRun = true)
    public void preConditions() {
        channel = grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_OPERATIONS_SURGELEVEL);
        client = ServiceGrpc.newBlockingStub(channel);
        addStore(FIRST_STORE_ID, UUID.randomUUID().toString(), null, true, 1f, 1f, null, 1, DELIVERY_AREA_ID, null);
        addStore(SECOND_STORE_ID, UUID.randomUUID().toString(), null, true, 1f, 1f, null, 1, DELIVERY_AREA_ID, null);
    }

    @TmsLink("146")
    @Story("Save Result")
    @Test(description = "Ручное выставление surgelevel",
            groups = "ondemand-surgelevel")
    public void saveResult() {
        var request = Surgelevel.SaveResultRequest.newBuilder()
                .addStore(Surgelevel.Store.Option.newBuilder()
                        .setId(FIRST_STORE_ID)
                        .build())
                .setResult(Surgelevel.Result.Option.newBuilder()
                        .setSurgeLevel(SURGE_LEVEL)
                        .setExpiredAt(getDatePlusSec(LONG_TIMEOUT))
                        .build())
                .build();
        client.saveResult(request);

        ThreadUtil.simplyAwait(LONG_TIMEOUT);

        List<Surgelevelevent.SurgeEvent> surgeLevels = kafka.waitDataInKafkaTopicSurgeLevel(FIRST_STORE_ID, 3L);
        checkSurgeLevelProduce(surgeLevels, surgeLevels.size(), FIRST_STORE_ID, SURGE_LEVEL, SURGE_LEVEL, 0, 0, Method.MANUAL);
    }

    @TmsLinks({@TmsLink("155"), @TmsLink("42")})
    @Story("Manual Surge")
    @Test(description = "Перерасчет surgelevel при наступлении expiredAt",
            groups = "ondemand-surgelevel",
            dependsOnMethods = "saveResult")
    public void manualSurgeRecalculate() {
        ThreadUtil.simplyAwait(60);

        List<Surgelevelevent.SurgeEvent> surgeLevels = kafka.waitDataInKafkaTopicSurgeLevel(FIRST_STORE_ID, 3L);
        checkSurgeLevelProduce(surgeLevels, surgeLevels.size(), FIRST_STORE_ID, SURGE_LEVEL, SURGE_LEVEL - 1, 0, 0, Method.ACTUAL);
    }

    @TmsLink("147")
    @Story("Save Result")
    @Test(description = "Ручное выставление surgelevel нескольким магазинам",
            groups = "ondemand-surgelevel",
            dependsOnMethods = "manualSurgeRecalculate")
    public void saveResultMultipleStores() {
        var request = Surgelevel.SaveResultRequest.newBuilder()
                .addStore(Surgelevel.Store.Option.newBuilder()
                        .setId(FIRST_STORE_ID)
                        .build())
                .addStore(Surgelevel.Store.Option.newBuilder()
                        .setId(SECOND_STORE_ID)
                        .build())
                .setResult(Surgelevel.Result.Option.newBuilder()
                        .setSurgeLevel(SURGE_LEVEL)
                        .setExpiredAt(getDatePlusSec(1200))
                        .build())
                .build();
        client.saveResult(request);

        ThreadUtil.simplyAwait(LONG_TIMEOUT);

        Allure.step("Проверка surgelevel", () -> {
            ResultEntity firstResult = ResultDao.INSTANCE.findResult(FIRST_STORE_ID);
            ResultEntity secondResult = ResultDao.INSTANCE.findResult(SECOND_STORE_ID);
            compareTwoObjects(SURGE_LEVEL, firstResult.getSurgeLevel().floatValue());
            compareTwoObjects(SURGE_LEVEL, secondResult.getSurgeLevel().floatValue());
            expiredAt = secondResult.getExpiredAt();
        });
    }

    @TmsLink("156")
    @Story("Manual Surge")
    @Test(description = "Отсутствие перерасчета surgelevel, если не наступило expiredAt",
            groups = "ondemand-surgelevel",
            dependsOnMethods = "saveResultMultipleStores")
    public void manualSurgeNotRecalculate() {
        publishEventCandidateStatus(UUID.randomUUID().toString(), CandidateChangesOuterClass.CandidateChanges.Role.UNIVERSAL, CandidateChangesOuterClass.CandidateChanges.Status.BUSY, 0, DELIVERY_AREA_ID, true, SECOND_STORE_ID, DISPATCH.getName(), LONG_TIMEOUT);

        Allure.step("Проверка отсутствия изменения surgelevel", () -> {
            ResultEntity result = ResultDao.INSTANCE.findResult(SECOND_STORE_ID);
            compareTwoObjects(expiredAt, result.getExpiredAt());
            compareTwoObjects(SURGE_LEVEL, result.getSurgeLevel().floatValue());
        });
    }

    @TmsLink("148")
    @Story("Save Result")
    @Test(description = "Получение ошибки при выставлении expiredAt в прошлом",
            groups = "ondemand-surgelevel",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: invalid result expired_at")
    public void saveResultExpiredTime() {
        var request = Surgelevel.SaveResultRequest.newBuilder()
                .addStore(Surgelevel.Store.Option.newBuilder()
                        .setId(FIRST_STORE_ID)
                        .build())
                .setResult(Surgelevel.Result.Option.newBuilder()
                        .setSurgeLevel(SURGE_LEVEL)
                        .setExpiredAt(getDateMinusSec(1))
                        .build())
                .build();
        client.saveResult(request);
    }

    @TmsLink("149")
    @Story("Save Result")
    @Test(description = "Получение ошибки при выставлении expiredAt больше чем на 7 дней",
            groups = "ondemand-surgelevel",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: invalid result expired_at")
    public void saveResultTooLong() {
        var request = Surgelevel.SaveResultRequest.newBuilder()
                .addStore(Surgelevel.Store.Option.newBuilder()
                        .setId(FIRST_STORE_ID)
                        .build())
                .setResult(Surgelevel.Result.Option.newBuilder()
                        .setSurgeLevel(SURGE_LEVEL)
                        .setExpiredAt(getDatePlusSec(620000))
                        .build())
                .build();
        client.saveResult(request);
    }

    @TmsLink("150")
    @Story("Save Result")
    @Test(description = "Получение ошибки при пустом store",
            groups = "ondemand-surgelevel",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: empty stores")
    public void saveResultEmptyStore() {
        var request = Surgelevel.SaveResultRequest.newBuilder()
                .setResult(Surgelevel.Result.Option.newBuilder()
                        .setSurgeLevel(SURGE_LEVEL)
                        .setExpiredAt(getDatePlusSec(1200))
                        .build())
                .build();
        client.saveResult(request);
    }

    @TmsLink("151")
    @Story("Save Result")
    @Test(description = "Получение ошибки при пустом result",
            groups = "ondemand-surgelevel",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: empty result")
    public void saveResultEmptyResult() {
        var request = Surgelevel.SaveResultRequest.newBuilder()
                .addStore(Surgelevel.Store.Option.newBuilder()
                        .setId(FIRST_STORE_ID)
                        .build())
                .build();
        client.saveResult(request);
    }

    @TmsLink("152")
    @Story("Save Result")
    @Test(description = "Получение ошибки с невалидным store.id",
            groups = "ondemand-surgelevel",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: invalid store id:.*")
    public void saveResultInvalidStoreId() {
        var request = Surgelevel.SaveResultRequest.newBuilder()
                .addStore(Surgelevel.Store.Option.newBuilder()
                        .setId("test")
                        .build())
                .setResult(Surgelevel.Result.Option.newBuilder()
                        .setSurgeLevel(SURGE_LEVEL)
                        .setExpiredAt(getDatePlusSec(1200))
                        .build())
                .build();
        client.saveResult(request);
    }

    @AfterClass(alwaysRun = true)
    public void postConditions() {
        if (Objects.nonNull(FIRST_STORE_ID)) {
            StoreDao.INSTANCE.delete(FIRST_STORE_ID);
        }
        if (Objects.nonNull(SECOND_STORE_ID)) {
            StoreDao.INSTANCE.delete(SECOND_STORE_ID);
        }
    }
}
