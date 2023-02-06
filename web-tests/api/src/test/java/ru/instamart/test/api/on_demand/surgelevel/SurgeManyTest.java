package ru.instamart.test.api.on_demand.surgelevel;

import events.CandidateChangesOuterClass;
import io.qameta.allure.*;
import order.OrderChanged;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.SurgeLevelBase;
import ru.instamart.api.helper.SurgeLevelHelper;
import ru.instamart.jdbc.dao.surgelevel.*;
import ru.instamart.kraken.util.ThreadUtil;
import surgelevel.Surgelevel;

import java.util.Objects;
import java.util.UUID;

import static org.testng.Assert.*;
import static ru.instamart.api.helper.SurgeLevelHelper.*;
import static ru.instamart.api.helper.WaitHelper.withRetriesAsserted;
import static ru.instamart.kraken.enums.ScheduleType.DISPATCH;
import static ru.instamart.kraken.util.DistanceUtil.distance;
import static ru.instamart.kraken.util.TimeUtil.getTimestamp;
import static ru.instamart.kraken.util.TimeUtil.getZonedUTCDate;

@Epic("Surgelevel")
@Feature("Автоматический расчет surge.many")
public class SurgeManyTest extends SurgeLevelBase {

    private float currentSurgeLevelFirstStore = BASE_SURGELEVEL;
    private float currentSurgeLevelSecondStore = BASE_SURGELEVEL;
    private float currentSurgeLevelThirdStore = BASE_SURGELEVEL;
    private int surgeEventOutdate = BASE_SURGE_OUTDATE;
    private final String FAR_STORE_ID = UUID.randomUUID().toString();
    private final String FIRST_STORE_ID = UUID.randomUUID().toString();
    private final String SECOND_STORE_ID = UUID.randomUUID().toString();
    private final String THIRD_STORE_ID = UUID.randomUUID().toString();
    private final String CANDIDATE_UUID_WORKFLOW = UUID.randomUUID().toString();
    private final String CANDIDATE_UUID_WITH_SHIFT = UUID.randomUUID().toString();
    private final int FIRST_WORKFLOW_ID = FIRST_DELIVERY_AREA_ID;
    private final int SECOND_WORKFLOW_ID = SECOND_DELIVERY_AREA_ID;
    private Surgelevel.Location farStoreLocation;
    private Surgelevel.Location firstStoreLocation;
    private Surgelevel.Location secondStoreLocation;
    private Surgelevel.Location thirdStoreLocation;
    private Surgelevel.Location workflowPlanEndLocation;
    private double distFirstSecond;
    private double distFirstThird;

    @BeforeClass(alwaysRun = true)
    public void preConditions() {
        //стор1 видит оба
        //стор2 видит только стор1 (примерно 1,3КМ от стор1)
        //стор3 видит только стор1 (примерно 2,9КМ  стор1)
        //дальний стор не видит никого
        firstStoreLocation = Surgelevel.Location.newBuilder().setLat(27.034817f).setLon(14.426422f).build();
        secondStoreLocation = Surgelevel.Location.newBuilder().setLat(27.046055f).setLon(14.421101f).build();
        thirdStoreLocation = Surgelevel.Location.newBuilder().setLat(27.008896f).setLon(14.431057f).build();
        farStoreLocation = Surgelevel.Location.newBuilder().setLat(26.9993f).setLon(14.4002f).build();
        workflowPlanEndLocation = Surgelevel.Location.newBuilder().setLat(27.006144f).setLon(14.435349f).build();
        distFirstSecond = distance(firstStoreLocation.getLat(), firstStoreLocation.getLon(), secondStoreLocation.getLat(), secondStoreLocation.getLon(), 'K') * 1000;
        distFirstThird = distance(firstStoreLocation.getLat(), firstStoreLocation.getLon(), thirdStoreLocation.getLat(), thirdStoreLocation.getLon(), 'K') * 1000;

        addStore(FIRST_STORE_ID, UUID.randomUUID().toString(), null, true, firstStoreLocation.getLat(), firstStoreLocation.getLon(), FORMULA_ID, 1000, SECOND_DELIVERY_AREA_ID, null);
        addStore(SECOND_STORE_ID, UUID.randomUUID().toString(), null, true, secondStoreLocation.getLat(), secondStoreLocation.getLon(), FORMULA_ID, 1000, SECOND_DELIVERY_AREA_ID, null);
        addStore(THIRD_STORE_ID, UUID.randomUUID().toString(), null, true, thirdStoreLocation.getLat(), thirdStoreLocation.getLon(), FORMULA_ID, 1000, 0, null);
        addStore(FAR_STORE_ID, UUID.randomUUID().toString(), false, true, farStoreLocation.getLat(), farStoreLocation.getLon(), FORMULA_ID, 1000, FIRST_DELIVERY_AREA_ID, null);

        final var surgeEventOutdateFromK8s = SurgeLevelHelper.getInstance().getSurgeEventOutdate();
        if (Objects.nonNull(surgeEventOutdateFromK8s)) {
            surgeEventOutdate = surgeEventOutdateFromK8s;
        }
    }

    @AfterMethod(alwaysRun = true)
    public void waitForOutdate() {
        ThreadUtil.simplyAwait(surgeEventOutdate);
    }

    @TmsLinks({@TmsLink("133"), @TmsLink("135"), @TmsLink("43"), @TmsLink("165"), @TmsLink("44")})
    @Story("Demand")
    @Test(description = "Добавление demand для нескольких магазинов при получении события с новым ON_DEMAND заказом",
            groups = "ondemand-surgelevel")
    public void surgeProduceEventOrderMultipleStores() {
        String shipmentUuid = UUID.randomUUID().toString();
        publishEventOrderStatus(UUID.randomUUID().toString(), FIRST_STORE_ID, OrderChanged.EventOrderChanged.OrderStatus.NEW, OrderChanged.EventOrderChanged.ShipmentType.ON_DEMAND, shipmentUuid, 0);

        currentSurgeLevelFirstStore++;
        currentSurgeLevelSecondStore++;
        currentSurgeLevelThirdStore++;

        Allure.step("Проверка добавления demand и корректной записи расстояния для inner и outer магазинов", () -> withRetriesAsserted(() -> {
            final var firstStoreDemand = DemandDao.INSTANCE.findDemand(FIRST_STORE_ID, shipmentUuid);
            assertNotNull(firstStoreDemand, "Заказ не добавился в demand");
            final var secondStoreDemand = DemandDao.INSTANCE.findDemand(SECOND_STORE_ID, shipmentUuid);
            assertNotNull(secondStoreDemand, "Заказ не добавился в demand");
            final var thirdStoreDemand = DemandDao.INSTANCE.findDemand(THIRD_STORE_ID, shipmentUuid);
            assertNotNull(thirdStoreDemand, "Заказ не добавился в demand");

            assertEquals(firstStoreDemand.getDistance(), 0d, 5d, "Не верное расстояние для inner магазина");
            assertEquals(secondStoreDemand.getDistance(), distFirstSecond, 10d, "Не верное расстояние для outer магазина");
            assertEquals(thirdStoreDemand.getDistance(), distFirstThird, 10d, "Не верное расстояние для outer магазина");
        }, LONG_TIMEOUT));
        Allure.step("Проверка корректного расчета surgelevel", () -> withRetriesAsserted(() -> {
            final var firstStoreResult = ResultDao.INSTANCE.findResult(FIRST_STORE_ID);
            assertNotNull(firstStoreResult, "Для магазина не посчитан результат");
            final var secondStoreResult = ResultDao.INSTANCE.findResult(SECOND_STORE_ID);
            assertNotNull(secondStoreResult, "Для магазина не посчитан результат");
            final var thirdStoreResult = ResultDao.INSTANCE.findResult(THIRD_STORE_ID);
            assertNotNull(thirdStoreResult, "Для магазина не посчитан результат");

            assertEquals(firstStoreResult.getSurgeLevel().floatValue(), currentSurgeLevelFirstStore, "Не верный surgelevel 1");
            assertEquals(secondStoreResult.getSurgeLevel().floatValue(), currentSurgeLevelSecondStore, "Не верный surgelevel 2");
            assertEquals(thirdStoreResult.getSurgeLevel().floatValue(), currentSurgeLevelThirdStore, "Не верный surgelevel 3");
        }, LONG_TIMEOUT));
    }

    @TmsLink("141")
    @Story("Demand")
    @Test(description = "Отсутствие добавления demand для outer-магазинов при distance > STORE_RADIUS",
            groups = "ondemand-surgelevel",
            dependsOnMethods = "surgeProduceEventOrderMultipleStores")
    public void surgeProduceEventOrderGreaterStoreRadius() {
        String shipmentUuid = UUID.randomUUID().toString();
        publishEventOrderStatus(UUID.randomUUID().toString(), SECOND_STORE_ID, OrderChanged.EventOrderChanged.OrderStatus.NEW, OrderChanged.EventOrderChanged.ShipmentType.ON_DEMAND, shipmentUuid, 0);

        currentSurgeLevelFirstStore++;
        currentSurgeLevelSecondStore++;

        Allure.step("Проверка добавления demand", () -> withRetriesAsserted(() -> {
            final var firstStoreDemand = DemandDao.INSTANCE.findDemand(FIRST_STORE_ID, shipmentUuid);
            assertNotNull(firstStoreDemand, "Заказ не добавился в demand");
            final var secondStoreDemand = DemandDao.INSTANCE.findDemand(SECOND_STORE_ID, shipmentUuid);
            assertNotNull(secondStoreDemand, "Заказ не добавился в demand");
            final var thirdStoreDemand = DemandDao.INSTANCE.findDemand(THIRD_STORE_ID, shipmentUuid);
            assertNull(thirdStoreDemand, "Заказ добавился в demand при distance > STORE_RADIUS");
        }, LONG_TIMEOUT));
        Allure.step("Проверка корректного расчета surgelevel", () -> withRetriesAsserted(() -> {
            final var firstStoreResult = ResultDao.INSTANCE.findResult(FIRST_STORE_ID);
            assertNotNull(firstStoreResult, "Для магазина не посчитан результат");
            final var secondStoreResult = ResultDao.INSTANCE.findResult(SECOND_STORE_ID);
            assertNotNull(secondStoreResult, "Для магазина не посчитан результат");
            final var thirdStoreResult = ResultDao.INSTANCE.findResult(THIRD_STORE_ID);
            assertNotNull(thirdStoreResult, "Для магазина не посчитан результат");

            assertEquals(firstStoreResult.getSurgeLevel().floatValue(), currentSurgeLevelFirstStore, "Не верный surgelevel 1");
            assertEquals(secondStoreResult.getSurgeLevel().floatValue(), currentSurgeLevelSecondStore, "Не верный surgelevel 2");
            assertEquals(thirdStoreResult.getSurgeLevel().floatValue(), currentSurgeLevelThirdStore, "Не верный surgelevel 3");
        }, LONG_TIMEOUT));
    }

    @TmsLinks({@TmsLink("41"), @TmsLink("136"), @TmsLink("45")})
    @Story("Radius Supply")
    @Test(description = "Добавление supply для нескольких магазинов при получении кандидата в близи от этих магазинов",
            groups = "ondemand-surgelevel")
    public void surgeProduceEventCandidateLocationMultipleStores() {
        String candidateUuid = UUID.randomUUID().toString();
        publishEventCandidateStatus(candidateUuid, CandidateChangesOuterClass.CandidateChanges.Role.UNIVERSAL, CandidateChangesOuterClass.CandidateChanges.Status.FREE, 0, 0, false, FIRST_STORE_ID, DISPATCH.getName(), SHORT_TIMEOUT);
        publishEventLocation(candidateUuid, firstStoreLocation.getLat(), firstStoreLocation.getLon(), false, 0);

        currentSurgeLevelFirstStore--;
        currentSurgeLevelSecondStore--;
        currentSurgeLevelThirdStore--;

        Allure.step("Проверка добавления supply", () -> withRetriesAsserted(() -> {
            final var firstStoreSupply = SupplyDao.INSTANCE.findSupply(FIRST_STORE_ID, candidateUuid);
            assertNotNull(firstStoreSupply, "Кандидат не добавился в supply");
            final var secondStoreSupply = SupplyDao.INSTANCE.findSupply(SECOND_STORE_ID, candidateUuid);
            assertNotNull(secondStoreSupply, "Кандидат не добавился в supply");
            final var thirdStoreSupply = SupplyDao.INSTANCE.findSupply(THIRD_STORE_ID, candidateUuid);
            assertNotNull(thirdStoreSupply, "Кандидат не добавился в supply");

            assertEquals(firstStoreSupply.getDistance(), 0d, 5d, "Не верное расстояние для inner магазина");
            assertEquals(secondStoreSupply.getDistance(), distFirstSecond, 10d, "Не верное расстояние для outer магазина");
            assertEquals(thirdStoreSupply.getDistance(), distFirstThird, 10d, "Не верное расстояние для outer магазина");
        }, LONG_TIMEOUT));
        Allure.step("Проверка корректного расчета surgelevel", () -> withRetriesAsserted(() -> {
            final var firstStoreResult = ResultDao.INSTANCE.findResult(FIRST_STORE_ID);
            assertNotNull(firstStoreResult, "Для магазина не посчитан результат");
            final var secondStoreResult = ResultDao.INSTANCE.findResult(SECOND_STORE_ID);
            assertNotNull(secondStoreResult, "Для магазина не посчитан результат");
            final var thirdStoreResult = ResultDao.INSTANCE.findResult(THIRD_STORE_ID);
            assertNotNull(thirdStoreResult, "Для магазина не посчитан результат");

            assertEquals(firstStoreResult.getSurgeLevel().floatValue(), currentSurgeLevelFirstStore, "Не верный surgelevel 1");
            assertEquals(secondStoreResult.getSurgeLevel().floatValue(), currentSurgeLevelSecondStore, "Не верный surgelevel 2");
            assertEquals(thirdStoreResult.getSurgeLevel().floatValue(), currentSurgeLevelThirdStore, "Не верный surgelevel 3");
        }, LONG_TIMEOUT));
    }

    @TmsLink("137")
    @Story("Delivery Area Supply")
    @Test(description = "Добавление supply для нескольких магазинов при получении кандидата по delivery_area",
            groups = "ondemand-surgelevel",
            dependsOnMethods = "surgeProduceEventCandidateLocationMultipleStores")
    public void surgeProduceEventCandidateMultipleStoresDeliveryArea() {
        String candidateUuid = UUID.randomUUID().toString();
        publishEventCandidateStatus(candidateUuid, CandidateChangesOuterClass.CandidateChanges.Role.UNIVERSAL, CandidateChangesOuterClass.CandidateChanges.Status.FREE, 0, SECOND_DELIVERY_AREA_ID, true, FIRST_STORE_ID, DISPATCH.getName(), SHORT_TIMEOUT);
        publishEventLocation(candidateUuid, 15.0f, 15.0f, false, 0);

        currentSurgeLevelFirstStore--;
        currentSurgeLevelSecondStore--;

        Allure.step("Проверка добавления supply", () -> withRetriesAsserted(() -> {
            final var firstStoreSupply = SupplyDao.INSTANCE.findSupply(FIRST_STORE_ID, candidateUuid);
            assertNotNull(firstStoreSupply, "Кандидат не добавился в supply");
            final var secondStoreSupply = SupplyDao.INSTANCE.findSupply(SECOND_STORE_ID, candidateUuid);
            assertNotNull(secondStoreSupply, "Кандидат не добавился в supply");
            final var thirdStoreSupply = SupplyDao.INSTANCE.findSupply(THIRD_STORE_ID, candidateUuid);
            assertNull(thirdStoreSupply, "Кандидат добавился в supply");
        }, LONG_TIMEOUT));
        Allure.step("Проверка корректного расчета surgelevel", () -> withRetriesAsserted(() -> {
            final var firstStoreResult = ResultDao.INSTANCE.findResult(FIRST_STORE_ID);
            assertNotNull(firstStoreResult, "Для магазина не посчитан результат");
            final var secondStoreResult = ResultDao.INSTANCE.findResult(SECOND_STORE_ID);
            assertNotNull(secondStoreResult, "Для магазина не посчитан результат");
            final var thirdStoreResult = ResultDao.INSTANCE.findResult(THIRD_STORE_ID);
            assertNotNull(thirdStoreResult, "Для магазина не посчитан результат");

            assertEquals(firstStoreResult.getSurgeLevel().floatValue(), currentSurgeLevelFirstStore, "Не верный surgelevel 1");
            assertEquals(secondStoreResult.getSurgeLevel().floatValue(), currentSurgeLevelSecondStore, "Не верный surgelevel 2");
            assertEquals(thirdStoreResult.getSurgeLevel().floatValue(), currentSurgeLevelThirdStore, "Не верный surgelevel 3");
        }, LONG_TIMEOUT));
    }

    @TmsLinks({@TmsLink("157"), @TmsLink("142")})
    @Story("Workflow")
    @Test(description = "Проверка supply при получении in_progress МЛ",
            groups = "ondemand-surgelevel")
    public void surgeProduceEventCandidateWithWorkflow() {
        publishEventCandidateStatus(CANDIDATE_UUID_WORKFLOW, CandidateChangesOuterClass.CandidateChanges.Role.UNIVERSAL, CandidateChangesOuterClass.CandidateChanges.Status.FREE, 0, 0, false, SECOND_STORE_ID, DISPATCH.getName(), SHORT_TIMEOUT);
        publishEventLocation(CANDIDATE_UUID_WORKFLOW, secondStoreLocation.getLat(), secondStoreLocation.getLon(), false, SHORT_TIMEOUT);
        publishEventCandidateStatusWithWorkflow(CANDIDATE_UUID_WORKFLOW, CandidateChangesOuterClass.CandidateChanges.Role.UNIVERSAL, CandidateChangesOuterClass.CandidateChanges.Status.BUSY, 0, 0, false, SECOND_STORE_ID, DISPATCH.getName(), FIRST_WORKFLOW_ID, CandidateChangesOuterClass.WorkflowStatus.IN_PROGRESS, getTimestamp(), workflowPlanEndLocation.getLat(), workflowPlanEndLocation.getLon(), 0);

        Allure.step("Проверка сохранения supply", () -> withRetriesAsserted(() -> {
            final var workflow = WorkflowDao.INSTANCE.findWorkflowByCandidateId(CANDIDATE_UUID_WORKFLOW);
            assertNotNull(workflow, "Не записался workflow");
            final var firstStoreSupply = SupplyDao.INSTANCE.findSupply(FIRST_STORE_ID, CANDIDATE_UUID_WORKFLOW);
            assertNotNull(firstStoreSupply, "Пропал supply");
            final var secondStoreSupply = SupplyDao.INSTANCE.findSupply(SECOND_STORE_ID, CANDIDATE_UUID_WORKFLOW);
            assertNotNull(secondStoreSupply, "Пропал supply");
        }, LONG_TIMEOUT));
    }

    @TmsLinks({@TmsLink("158"), @TmsLink("159")})
    @Story("Workflow")
    @Test(description = "Удаление supply при уходе от изначальной точки и отсутствие добавления к магазинам по пути, если далеко от точки прибытия in_progress МЛ",
            groups = "ondemand-surgelevel",
            dependsOnMethods = "surgeProduceEventCandidateWithWorkflow")
    public void surgeProduceEventCandidateWithWorkflowStartMoving() {
        publishEventLocation(CANDIDATE_UUID_WORKFLOW, farStoreLocation.getLat(), farStoreLocation.getLon(), false, 0);

        Allure.step("Проверка удаления supply", () -> withRetriesAsserted(() -> {
            final var firstStoreSupply = SupplyDao.INSTANCE.findSupply(FIRST_STORE_ID, CANDIDATE_UUID_WORKFLOW);
            assertNull(firstStoreSupply, "Supply не удалился");
            final var secondStoreSupply = SupplyDao.INSTANCE.findSupply(SECOND_STORE_ID, CANDIDATE_UUID_WORKFLOW);
            assertNull(secondStoreSupply, "Supply не удалился");
            final var storeSupply = SupplyDao.INSTANCE.findSupply(FAR_STORE_ID, CANDIDATE_UUID_WORKFLOW);
            assertNull(storeSupply, "Supply добавился для магазина по пути");
        }, LONG_TIMEOUT));
    }

    @TmsLink("160")
    @Story("Workflow")
    @Test(description = "Добавление supply при вхождении в радиус к точке прибытия in_progress МЛ",
            groups = "ondemand-surgelevel",
            dependsOnMethods = "surgeProduceEventCandidateWithWorkflowStartMoving")
    public void surgeProduceEventCandidateWithWorkflowFinishing() {
        publishEventLocation(CANDIDATE_UUID_WORKFLOW, thirdStoreLocation.getLat(), thirdStoreLocation.getLon(), false, 0);

        Allure.step("Проверка добавления supply в магазины в store_radius от точки прибытия", () -> withRetriesAsserted(() -> {
            final var thirdStoreSupply = SupplyDao.INSTANCE.findSupply(THIRD_STORE_ID, CANDIDATE_UUID_WORKFLOW);
            assertNotNull(thirdStoreSupply, "Supply не добавился");
            final var firstStoreSupply = SupplyDao.INSTANCE.findSupply(FIRST_STORE_ID, CANDIDATE_UUID_WORKFLOW);
            assertNull(firstStoreSupply, "Supply добавился в магазин > store_radius от точки прибытия");
        }, LONG_TIMEOUT));
    }

    @TmsLink("162")
    @Story("Workflow")
    @Test(description = "Отсутствие влияния in_progress МЛ на кандидата с delivery_area",
            groups = "ondemand-surgelevel")
    public void surgeProduceEventCandidateWithWorkflowAndDeliveryArea() {
        String candidateUuid = UUID.randomUUID().toString();
        publishEventCandidateStatus(candidateUuid, CandidateChangesOuterClass.CandidateChanges.Role.UNIVERSAL, CandidateChangesOuterClass.CandidateChanges.Status.FREE, 0, SECOND_DELIVERY_AREA_ID, true, SECOND_STORE_ID, DISPATCH.getName(), SHORT_TIMEOUT);
        publishEventLocation(candidateUuid, secondStoreLocation.getLat(), secondStoreLocation.getLon(), false, SHORT_TIMEOUT);
        publishEventCandidateStatusWithWorkflow(candidateUuid, CandidateChangesOuterClass.CandidateChanges.Role.UNIVERSAL, CandidateChangesOuterClass.CandidateChanges.Status.BUSY, 0, SECOND_DELIVERY_AREA_ID, true, SECOND_STORE_ID, DISPATCH.getName(), SECOND_WORKFLOW_ID, CandidateChangesOuterClass.WorkflowStatus.IN_PROGRESS, getTimestamp(), workflowPlanEndLocation.getLat(), workflowPlanEndLocation.getLon(), SHORT_TIMEOUT);
        publishEventLocation(candidateUuid, workflowPlanEndLocation.getLat(), workflowPlanEndLocation.getLon(), false, MEDIUM_TIMEOUT);

        Allure.step("Проверка отсутствия применения логики workflow для deliveryarea кандидата", () -> {
            final var firstStoreSupply = SupplyDao.INSTANCE.findSupply(FIRST_STORE_ID, candidateUuid);
            assertNotNull(firstStoreSupply, "Пропал supply от deliveryarea");
            final var secondStoreSupply = SupplyDao.INSTANCE.findSupply(SECOND_STORE_ID, candidateUuid);
            assertNotNull(secondStoreSupply, "Пропал supply от deliveryarea");
            final var thirdStoreSupply = SupplyDao.INSTANCE.findSupply(THIRD_STORE_ID, candidateUuid);
            assertNull(thirdStoreSupply, "Supply добавился по workflow");
        });
    }

    @TmsLink("161")
    @Story("Workflow")
    @Test(description = "Отсутствие влияния не in_progress МЛ на кандидата",
            groups = "ondemand-surgelevel")
    public void surgeProduceEventCandidateWithQueuedWorkflow() {
        String candidateUuid = UUID.randomUUID().toString();
        publishEventCandidateStatus(candidateUuid, CandidateChangesOuterClass.CandidateChanges.Role.UNIVERSAL, CandidateChangesOuterClass.CandidateChanges.Status.FREE, 0, 0, false, SECOND_STORE_ID, DISPATCH.getName(), SHORT_TIMEOUT);
        publishEventLocation(candidateUuid, secondStoreLocation.getLat(), secondStoreLocation.getLon(), false, SHORT_TIMEOUT);
        publishEventCandidateStatusWithWorkflow(candidateUuid, CandidateChangesOuterClass.CandidateChanges.Role.UNIVERSAL, CandidateChangesOuterClass.CandidateChanges.Status.BUSY, 0, 0, false, SECOND_STORE_ID, DISPATCH.getName(), SECOND_WORKFLOW_ID + 1, CandidateChangesOuterClass.WorkflowStatus.QUEUED, getTimestamp(), workflowPlanEndLocation.getLat(), workflowPlanEndLocation.getLon(), SHORT_TIMEOUT);
        publishEventLocation(candidateUuid, thirdStoreLocation.getLat(), thirdStoreLocation.getLon(), false, 0);

        Allure.step("Проверка добавления supply только по radius", () -> withRetriesAsserted(() -> {
            final var secondStoreSupply = SupplyDao.INSTANCE.findSupply(SECOND_STORE_ID, candidateUuid);
            assertNull(secondStoreSupply, "Не удалился supply по radius");
            final var thirdStoreSupply = SupplyDao.INSTANCE.findSupply(THIRD_STORE_ID, candidateUuid);
            assertNotNull(thirdStoreSupply, "Удалился supply по radius");
            final var firstStoreSupply = SupplyDao.INSTANCE.findSupply(FIRST_STORE_ID, candidateUuid);
            assertNotNull(firstStoreSupply, "Удалился supply по radius");
        }, LONG_TIMEOUT));
    }

    @TmsLinks({@TmsLink("188"), @TmsLink("193"), @TmsLink("196")})
    @Story("Demand")
    @Test(description = "Добавление supply onshift кандидату",
            groups = "ondemand-surgelevel")
    public void surgeProduceEventCandidateWithShift() {
        publishEventCandidateStatus(CANDIDATE_UUID_WITH_SHIFT, CandidateChangesOuterClass.CandidateChanges.Role.UNIVERSAL, CandidateChangesOuterClass.CandidateChanges.Status.FREE, 0, 0, false, SECOND_STORE_ID, DISPATCH.getName(), SHORT_TIMEOUT);
        publishEventLocation(CANDIDATE_UUID_WITH_SHIFT, secondStoreLocation.getLat(), secondStoreLocation.getLon(), false, 0);

        currentSurgeLevelFirstStore--;
        currentSurgeLevelSecondStore--;

        Allure.step("Проверка сохранения supply", () -> withRetriesAsserted(() -> {
            final var candidate = CandidateDao.INSTANCE.findCandidate(CANDIDATE_UUID_WITH_SHIFT);
            assertNotNull(candidate, "Не добавился кандидат");
            final var firstStoreSupply = SupplyDao.INSTANCE.findSupply(FIRST_STORE_ID, CANDIDATE_UUID_WITH_SHIFT);
            assertNotNull(firstStoreSupply, "Не добавился supply");
            final var secondStoreSupply = SupplyDao.INSTANCE.findSupply(SECOND_STORE_ID, CANDIDATE_UUID_WITH_SHIFT);
            assertNotNull(secondStoreSupply, "Не добавился supply");

            assertTrue(candidate.getOnshift(), "У кандидата не проставлен onshift");
            assertNull(candidate.getExpiredAt(), "У кандидата при регистрации expired_at != null");
        }, LONG_TIMEOUT));
    }

    @TmsLinks({@TmsLink("190"), @TmsLink("195")})
    @Story("Demand")
    @Test(description = "Удаление всего supply при смене onshift на false",
            groups = "ondemand-surgelevel",
            dependsOnMethods = "surgeProduceEventCandidateWithShift")
    public void surgeEventCandidateWithShiftChangeToFalse() {
        publishEventShift(CANDIDATE_UUID_WITH_SHIFT, getZonedUTCDate(), "completed", 0);

        currentSurgeLevelFirstStore++;
        currentSurgeLevelSecondStore++;

        Allure.step("Проверка удаления supply", () -> withRetriesAsserted(() -> {
            final var firstStoreSupply = SupplyDao.INSTANCE.findSupply(FIRST_STORE_ID, CANDIDATE_UUID_WITH_SHIFT);
            assertNull(firstStoreSupply, "Не пропал supply");
            final var secondStoreSupply = SupplyDao.INSTANCE.findSupply(SECOND_STORE_ID, CANDIDATE_UUID_WITH_SHIFT);
            assertNull(secondStoreSupply, "Не пропал supply");

            final var candidate = CandidateDao.INSTANCE.findCandidate(CANDIDATE_UUID_WITH_SHIFT);
            assertFalse(candidate.getOnshift(), "У кандидата не изменился onshift");
            assertNotNull(candidate.getExpiredAt(), "У кандидата не добавилось expired_at");
        }, LONG_TIMEOUT));
    }

    @TmsLink("189")
    @Story("Demand")
    @Test(description = "Отсутствие добавления supply не onshift кандидату",
            groups = "ondemand-surgelevel",
            dependsOnMethods = "surgeEventCandidateWithShiftChangeToFalse")
    public void surgeEventCandidateWithShiftFalseNoSupplyAdded() {
        publishEventLocation(CANDIDATE_UUID_WITH_SHIFT, 0f, 0f, false, SHORT_TIMEOUT);
        publishEventLocation(CANDIDATE_UUID_WITH_SHIFT, secondStoreLocation.getLat(), secondStoreLocation.getLon(), false, MEDIUM_TIMEOUT);

        Allure.step("Проверка отсутствия сохранения supply", () ->{
            final var firstStoreSupply = SupplyDao.INSTANCE.findSupply(FIRST_STORE_ID, CANDIDATE_UUID_WITH_SHIFT);
            assertNull(firstStoreSupply, "Добавился supply");
            final var secondStoreSupply = SupplyDao.INSTANCE.findSupply(SECOND_STORE_ID, CANDIDATE_UUID_WITH_SHIFT);
            assertNull(secondStoreSupply, "Добавился supply");
        });
    }

    @TmsLink("191")
    @Story("Demand")
    @Test(description = "Добавление supply при смене onshift на true",
            groups = "ondemand-surgelevel",
            dependsOnMethods = "surgeEventCandidateWithShiftFalseNoSupplyAdded")
    public void surgeProduceEventCandidateWithShiftTrue() {
        publishEventShift(CANDIDATE_UUID_WITH_SHIFT, getZonedUTCDate(), "in_progress", 0);

        currentSurgeLevelFirstStore--;
        currentSurgeLevelSecondStore--;

        Allure.step("Проверка добавления supply", () -> withRetriesAsserted(() -> {
            final var firstStoreSupply = SupplyDao.INSTANCE.findSupply(FIRST_STORE_ID, CANDIDATE_UUID_WITH_SHIFT);
            assertNotNull(firstStoreSupply, "Не добавился supply");
            final var secondStoreSupply = SupplyDao.INSTANCE.findSupply(SECOND_STORE_ID, CANDIDATE_UUID_WITH_SHIFT);
            assertNotNull(secondStoreSupply, "Не добавился supply");
            final var candidate = CandidateDao.INSTANCE.findCandidate(CANDIDATE_UUID_WITH_SHIFT);
            assertTrue(candidate.getOnshift(), "У кандидата не изменился onshift");
        }, LONG_TIMEOUT));
    }

    @TmsLink("192")
    @Story("Demand")
    @Test(description = "Проверка отсутствия реакции на события смены с fact_started_at=null",
            groups = "ondemand-surgelevel",
            dependsOnMethods = "surgeProduceEventCandidateWithShiftTrue")
    public void surgeEventCandidateWithShiftNoFactStarted() {
        publishEventShift(CANDIDATE_UUID_WITH_SHIFT, "0001-01-01T00:00:00Z", "completed", 0);

        Allure.step("Проверка отсутствия реакции", () -> withRetriesAsserted(() -> {
            final var candidate = CandidateDao.INSTANCE.findCandidate(CANDIDATE_UUID_WITH_SHIFT);
            assertTrue(candidate.getOnshift(), "У кандидата изменился onshift");
            final var firstStoreSupply = SupplyDao.INSTANCE.findSupply(FIRST_STORE_ID, CANDIDATE_UUID_WITH_SHIFT);
            assertNotNull(firstStoreSupply, "Удалился supply");
            final var secondStoreSupply = SupplyDao.INSTANCE.findSupply(SECOND_STORE_ID, CANDIDATE_UUID_WITH_SHIFT);
            assertNotNull(secondStoreSupply, "Удалился supply");
        }, LONG_TIMEOUT));
    }

    @AfterClass(alwaysRun = true)
    public void postConditions() {
        deleteStore(FAR_STORE_ID);
        deleteStore(FIRST_STORE_ID);
        deleteStore(SECOND_STORE_ID);
        deleteStore(THIRD_STORE_ID);
    }
}
