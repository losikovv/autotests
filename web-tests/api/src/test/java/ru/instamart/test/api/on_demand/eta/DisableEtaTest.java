package ru.instamart.test.api.on_demand.eta;

import com.google.protobuf.Timestamp;
import eta.Eta;
import eta.PredEtaGrpc;
import io.grpc.StatusRuntimeException;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.grpc.common.GrpcContentHosts;
import ru.instamart.jdbc.dao.eta.DisableEtaIntervalsDao;
import ru.instamart.jdbc.entity.eta.DisableEtaIntervalsEntity;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.List;
import java.util.UUID;

import static org.testng.Assert.*;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.kraken.util.TimeUtil.getDatePlusSec;

@Epic("On Demand")
@Feature("ETA")
public class DisableEtaTest extends RestBase {

    private PredEtaGrpc.PredEtaBlockingStub clientEta;
    private final String STORE_UUID_FIRST = UUID.randomUUID().toString();
    private final String STORE_UUID_SECOND = UUID.randomUUID().toString();
    private final Timestamp LEFT_BORDER = getDatePlusSec(1200);
    private final Timestamp RIGHT_BORDER = getDatePlusSec(1300);

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        clientEta = PredEtaGrpc.newBlockingStub(grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_OPERATIONS_ETA));
    }

    @CaseId(240)
    @Story("Disable ETA")
    @Test(description = "Загрузка корректных интервалов отключения ЕТА",
            groups = "dispatch-eta-smoke")
    public void disableEta() {
        var request = Eta.DisableEtaRequest.newBuilder()
                .addStoreUuids(STORE_UUID_FIRST)
                .setLeftBorder(LEFT_BORDER)
                .setRightBorder(RIGHT_BORDER)
                .setDisableEta(true)
                .setDisableSlot(false)
                .build();
        var response = clientEta.disableEta(request);

        Allure.step("Проверка успешного выполнения запроса", () -> compareTwoObjects(response.toString(), ""));
    }

    @CaseId(255)
    @Story("Disable ETA")
    @Test(description = "Загрузка корректных интервалов отключения слота",
            groups = "dispatch-eta-smoke")
    public void disableEtaSlot() {
        var request = Eta.DisableEtaRequest.newBuilder()
                .addStoreUuids(STORE_UUID_FIRST)
                .setLeftBorder(LEFT_BORDER)
                .setRightBorder(RIGHT_BORDER)
                .setDisableEta(false)
                .setDisableSlot(true)
                .build();
        var response = clientEta.disableEta(request);

        Allure.step("Проверка успешного выполнения запроса", () -> compareTwoObjects(response.toString(), ""));
    }

    @CaseId(256)
    @Story("Disable ETA")
    @Test(description = "Загрузка корректных интервалов отключения ЕТА и слота",
            groups = "dispatch-eta-smoke")
    public void disableEtaAndSlot() {
        var request = Eta.DisableEtaRequest.newBuilder()
                .addStoreUuids(STORE_UUID_SECOND)
                .setLeftBorder(LEFT_BORDER)
                .setRightBorder(RIGHT_BORDER)
                .setDisableEta(true)
                .setDisableSlot(true)
                .build();
        var response = clientEta.disableEta(request);

        Allure.step("Проверка успешного сохранения интервалов", () -> {
            compareTwoObjects(response.toString(), "");
            List<DisableEtaIntervalsEntity> intervals =  DisableEtaIntervalsDao.INSTANCE.getIntervals(STORE_UUID_SECOND);
            assertTrue(intervals.size() >= 2, "Сохранены не все интервалы");
        });
    }

    @CaseId(257)
    @Story("Disable ETA")
    @Test(description = "Загрузка корректных интервалов в несколько магазинов",
            groups = "dispatch-eta-smoke")
    public void disableEtaMultipleStores() {
        var request = Eta.DisableEtaRequest.newBuilder()
                .addStoreUuids(STORE_UUID_FIRST)
                .addStoreUuids(STORE_UUID_SECOND)
                .setLeftBorder(getDatePlusSec(2200))
                .setRightBorder(getDatePlusSec(2300))
                .setDisableEta(true)
                .setDisableSlot(true)
                .build();
        var response = clientEta.disableEta(request);

        Allure.step("Проверка успешного выполнения запроса", () -> compareTwoObjects(response.toString(), ""));
    }

    @CaseId(243)
    @Story("Disable ETA")
    @Test(description = "Удаление старых интервалов при загрузке новых, которые пересекаются по времени",
            groups = "dispatch-eta-smoke",
            dependsOnMethods = "disableEtaAndSlot")
    public void disableEtaReplace() {
        var request = Eta.DisableEtaRequest.newBuilder()
                .addStoreUuids(STORE_UUID_SECOND)
                .setLeftBorder(LEFT_BORDER)
                .setRightBorder(RIGHT_BORDER)
                .setDisableEta(true)
                .setDisableSlot(true)
                .build();
        var response = clientEta.disableEta(request);

        Allure.step("Проверка успешного сохранения новых интервалов и удаления пересекающихся", () -> {
            compareTwoObjects(response.toString(), "");
            List<DisableEtaIntervalsEntity> intervals =  DisableEtaIntervalsDao.INSTANCE.getIntervals(STORE_UUID_SECOND);
            assertTrue(intervals.size() >= 2, "Сохранены не все интервалы");
            List<DisableEtaIntervalsEntity> intervalsDeleted =  DisableEtaIntervalsDao.INSTANCE.getDeletedIntervals(STORE_UUID_SECOND);
            assertEquals(intervalsDeleted.size(), 2, "Не были удалены пересекающиеся интервалы");
        });
    }

    @CaseId(241)
    @Story("Disable ETA")
    @Test(description = "Получение ошибки при загрузке интервалов c пустым списком магазинов",
            groups = "dispatch-eta-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: invalid request: empty store uuids")
    public void disableEtaEmptyStores() {
        var request = Eta.DisableEtaRequest.newBuilder()
                .setLeftBorder(LEFT_BORDER)
                .setRightBorder(RIGHT_BORDER)
                .setDisableEta(true)
                .setDisableSlot(true)
                .build();
        clientEta.disableEta(request);
    }

    @CaseId(258)
    @Story("Disable ETA")
    @Test(description = "Получение ошибки при загрузке интервалов с не валидным store.uuid",
            groups = "dispatch-eta-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: invalid request: invalid store uuid test:.*")
    public void disableEtaInvalidStores() {
        var request = Eta.DisableEtaRequest.newBuilder()
                .addStoreUuids("test")
                .setLeftBorder(LEFT_BORDER)
                .setRightBorder(RIGHT_BORDER)
                .setDisableEta(true)
                .setDisableSlot(true)
                .build();
        clientEta.disableEta(request);
    }

    @CaseId(242)
    @Story("Disable ETA")
    @Test(description = "Получение ошибки при загрузке интервалов без указания временных границ",
            groups = "dispatch-eta-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: invalid request: left border and right border are required")
    public void disableEtaNoBorder() {
        var request = Eta.DisableEtaRequest.newBuilder()
                .addStoreUuids(STORE_UUID_SECOND)
                .setRightBorder(RIGHT_BORDER)
                .setDisableEta(true)
                .setDisableSlot(true)
                .build();
        clientEta.disableEta(request);
    }

    @CaseId(254)
    @Story("Disable ETA")
    @Test(description = "Получение ошибки при загрузке интервалов без отключения ЕТА и слота",
            groups = "dispatch-eta-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: invalid request: at least one of disable_eta or disable_slot must be true")
    public void disableEtaFalse() {
        var request = Eta.DisableEtaRequest.newBuilder()
                .addStoreUuids(STORE_UUID_SECOND)
                .setLeftBorder(LEFT_BORDER)
                .setRightBorder(RIGHT_BORDER)
                .setDisableEta(false)
                .setDisableSlot(false)
                .build();
        clientEta.disableEta(request);
    }

    @CaseId(259)
    @Story("Check Slot Disable")
    @Test(description = "Получение списка выключенных слотов в магазине",
            groups = "dispatch-eta-smoke",
            dependsOnMethods = "disableEtaSlot")
    public void checkSlotDisable() {
        var request = Eta.CheckSlotDisableRequest.newBuilder()
                .addStoreUuids(STORE_UUID_FIRST)
                .build();
        var response = clientEta.checkSlotDisable(request);

        Allure.step("Проверка получения списка выключенных слотов", () -> {
            assertTrue(response.getDisableIntervalsByStoreCount() > 0, "Пустой ответ");
            assertTrue(response.getDisableIntervalsByStoreMap().containsKey(STORE_UUID_FIRST), "Магазин отсутствует в ответе");
            assertTrue(response.getDisableIntervalsByStoreMap().get(STORE_UUID_FIRST).getDisableIntervalsCount() > 0, "Пустой список интервалов");
            assertNotNull(response.getDisableIntervalsByStoreMap().get(STORE_UUID_FIRST).getDisableIntervals(0).getStartAt(), "Пустой left border");
            assertNotNull(response.getDisableIntervalsByStoreMap().get(STORE_UUID_FIRST).getDisableIntervals(0).getEndAt(), "Пустой right border");
        });
    }

    @CaseId(260)
    @Story("Check Slot Disable")
    @Test(description = "Получение списка выключенных слотов в нескольких магазинах",
            groups = "dispatch-eta-smoke",
            dependsOnMethods = "disableEtaMultipleStores")
    public void checkSlotDisableMultipleStores() {
        var request = Eta.CheckSlotDisableRequest.newBuilder()
                .addStoreUuids(STORE_UUID_FIRST)
                .addStoreUuids(STORE_UUID_SECOND)
                .build();
        var response = clientEta.checkSlotDisable(request);

        Allure.step("Проверка получения списка выключенных слотов", () -> {
            assertTrue(response.getDisableIntervalsByStoreCount() > 0, "Пустой ответ");
            assertTrue(response.getDisableIntervalsByStoreMap().containsKey(STORE_UUID_FIRST), "Магазин отсутствует в ответе");
            assertTrue(response.getDisableIntervalsByStoreMap().get(STORE_UUID_FIRST).getDisableIntervalsCount() > 0, "Пустой список интервалов");
            assertNotNull(response.getDisableIntervalsByStoreMap().get(STORE_UUID_FIRST).getDisableIntervals(0).getStartAt(), "Пустой left border");
            assertNotNull(response.getDisableIntervalsByStoreMap().get(STORE_UUID_FIRST).getDisableIntervals(0).getEndAt(), "Пустой right border");
            assertTrue(response.getDisableIntervalsByStoreMap().containsKey(STORE_UUID_SECOND), "Магазин отсутствует в ответе");
            assertTrue(response.getDisableIntervalsByStoreMap().get(STORE_UUID_SECOND).getDisableIntervalsCount() > 0, "Пустой список интервалов");
            assertNotNull(response.getDisableIntervalsByStoreMap().get(STORE_UUID_SECOND).getDisableIntervals(0).getStartAt(), "Пустой left border");
            assertNotNull(response.getDisableIntervalsByStoreMap().get(STORE_UUID_SECOND).getDisableIntervals(0).getEndAt(), "Пустой right border");
        });
    }

    @CaseId(265)
    @Story("Check Slot Disable")
    @Test(description = "Получение пустого списка при отсутствии интервалов выключенных слотов в магазине",
            groups = "dispatch-eta-regress")
    public void checkSlotDisableEmpty() {
        var request = Eta.CheckSlotDisableRequest.newBuilder()
                .addStoreUuids(UUID.randomUUID().toString())
                .build();
        var response = clientEta.checkSlotDisable(request);

        Allure.step("Проверка получения пустого списка выключенных слотов", () -> assertEquals(response.getDisableIntervalsByStoreCount(), 0, "Не пустой список"));
    }

    @CaseId(263)
    @Story("Check Slot Disable")
    @Test(description = "Получение ошибки при отправке запроса с пустым списком магазинов",
            groups = "dispatch-eta-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: invalid request: empty store uuids")
    public void checkSlotDisableEmptyStores() {
        var request = Eta.CheckSlotDisableRequest.newBuilder().build();
        clientEta.checkSlotDisable(request);
    }

    @CaseId(264)
    @Story("Check Slot Disable")
    @Test(description = "Получение ошибки при отправке запроса с не валидным store.uuid",
            groups = "dispatch-eta-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: invalid request: invalid store uuid test:.*")
    public void checkSlotDisableInvalidStores() {
        var request = Eta.CheckSlotDisableRequest.newBuilder()
                .addStoreUuids("test")
                .build();
        clientEta.checkSlotDisable(request);
    }
}
