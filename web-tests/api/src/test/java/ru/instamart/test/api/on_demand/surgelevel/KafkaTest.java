package ru.instamart.test.api.on_demand.surgelevel;

import io.qameta.allure.*;
import order.OrderChanged.EventOrderChanged.OrderStatus;
import order.OrderChanged.EventOrderChanged.ShipmentType;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.jdbc.dao.surgelevel.DemandDao;
import ru.instamart.jdbc.dao.surgelevel.ResultDao;
import ru.instamart.jdbc.dao.surgelevel.SupplyDao;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.listener.Skip;
import ru.instamart.kraken.util.ThreadUtil;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;
import surgelevel.Surgelevel;
import surgelevelevent.Surgelevelevent;

import java.util.List;
import java.util.UUID;

import static events.CandidateChangesOuterClass.*;
import static norns.Norns.*;
import static order.OrderChanged.*;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.testng.Assert.*;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.helper.K8sHelper.getPaasServiceEnvProp;
import static ru.instamart.api.helper.SurgeLevelHelper.*;
import static ru.instamart.kafka.configs.KafkaConfigs.*;
import static ru.instamart.kraken.enums.ScheduleType.*;
import static ru.instamart.kraken.util.DistanceUtil.distance;
import static ru.instamart.kraken.util.StringUtil.matchWithRegex;
import static ru.instamart.kraken.util.TimeUtil.getTimestamp;

@Epic("On Demand")
@Feature("Расчет surgelevel")
public class KafkaTest extends RestBase {

    private final String STORE_ID = UUID.randomUUID().toString();
    private final String FIRST_STORE_ID = UUID.randomUUID().toString();
    private final String SECOND_STORE_ID = UUID.randomUUID().toString();
    private final String THIRD_STORE_ID = UUID.randomUUID().toString();
    private final String DEFAULT_CONFIG_ID = "20000000-2000-2000-2000-200000000000";
    private final String SHIPMENT_UUID = UUID.randomUUID().toString();
    private final String ORDER_UUID = UUID.randomUUID().toString();
    private final String CANDIDATE_UUID = UUID.randomUUID().toString();
    private final String CANDIDATE_UUID_DELIVERY_AREA = UUID.randomUUID().toString();
    private final String CANDIDATE_UUID_WORKFLOW = UUID.randomUUID().toString();
    private final int FIRST_DELIVERY_AREA_ID = nextInt(100000, 150000);
    private final int SECOND_DELIVERY_AREA_ID = FIRST_DELIVERY_AREA_ID + 1;
    private final int FIRST_WORKFLOW_ID = FIRST_DELIVERY_AREA_ID;
    private final int SECOND_WORKFLOW_ID = SECOND_DELIVERY_AREA_ID;
    private float currentSurgeLevel = 100; // см. формулу, которую используем для тестов (в конфиге DEFAULT_CONFIG_ID)
    private float currentSurgeLevelMultipleStores = 100; // см. формулу, которую используем для тестов (в конфиге DEFAULT_CONFIG_ID)
    private int currentDemandAmount;
    private int currentSupplyAmount;
    private float pastSurgeLevel;
    private int surgeEventsAmount;
    private boolean surgeEventProduceUnchanged;
    private int surgeEventOutdate;
    private double distFirstSecond;
    private double distFirstThird;
    private Surgelevel.Location firstStoreLocation;
    private Surgelevel.Location secondStoreLocation;
    private Surgelevel.Location thirdStoreLocation;
    private Surgelevel.Location fourthStoreLocation;
    private Surgelevel.Location workflowPlanEnd;

    @BeforeClass(alwaysRun = true)
    public void preConditions() {

        firstStoreLocation = Surgelevel.Location.newBuilder()
                .setLat(27.034817f)
                .setLon(14.426422f)
                .build();

        secondStoreLocation = Surgelevel.Location.newBuilder()
                .setLat(27.046055f)
                .setLon(14.421101f)
                .build();

        thirdStoreLocation = Surgelevel.Location.newBuilder()
                .setLat(27.008896f)
                .setLon(14.431057f)
                .build();

        fourthStoreLocation = Surgelevel.Location.newBuilder()
                .setLat(26.9993f)
                .setLon(14.4002f)
                .build();

        workflowPlanEnd = Surgelevel.Location.newBuilder()
                .setLat(27.006144f)
                .setLon(14.435349f)
                .build();

        //пока прибил гвоздями
        //стор1 видит оба
        //стор2 видит только стор1 (примерно 1,3КМ от стор1)
        //стор3 видит только стор1 (примерно 2,9КМ  стор1)
        addStore(FIRST_STORE_ID, UUID.randomUUID().toString(), true, firstStoreLocation.getLat(), firstStoreLocation.getLon(), DEFAULT_CONFIG_ID, 1, SECOND_DELIVERY_AREA_ID);
        addStore(SECOND_STORE_ID, UUID.randomUUID().toString(), true, secondStoreLocation.getLat(), secondStoreLocation.getLon(), DEFAULT_CONFIG_ID, 1, SECOND_DELIVERY_AREA_ID);
        addStore(THIRD_STORE_ID, UUID.randomUUID().toString(), true, thirdStoreLocation.getLat(), thirdStoreLocation.getLon(), DEFAULT_CONFIG_ID, 1, 0);
        addStore(STORE_ID, UUID.randomUUID().toString(), true, fourthStoreLocation.getLat(), fourthStoreLocation.getLon(), DEFAULT_CONFIG_ID, 1, FIRST_DELIVERY_AREA_ID);

        distFirstSecond = distance(firstStoreLocation.getLat(), firstStoreLocation.getLon(), secondStoreLocation.getLat(), secondStoreLocation.getLon(), 'K') * 1000;
        distFirstThird = distance(firstStoreLocation.getLat(), firstStoreLocation.getLon(), thirdStoreLocation.getLat(), thirdStoreLocation.getLon(), 'K') * 1000;

        List<String> serviceEnvProperties = getPaasServiceEnvProp(EnvironmentProperties.Env.SURGELEVEL_NAMESPACE, " | grep -e SURGEEVENT_PRODUCE_UNCHANGED -e SURGEEVENT_OUTDATE ");
        String envPropsStr = String.join("\n", serviceEnvProperties);
        String surgeEventProduceUnchangedStr = matchWithRegex("^SURGEEVENT_PRODUCE_UNCHANGED=(.\\w+)$", envPropsStr, 1);
        String surgeEventOutdateStr = matchWithRegex("^SURGEEVENT_OUTDATE=(\\d+)s$", envPropsStr, 1);

        surgeEventProduceUnchanged = surgeEventProduceUnchangedStr.equals("true");
        if (!surgeEventOutdateStr.isBlank()) {
            surgeEventOutdate = Integer.parseInt(surgeEventOutdateStr);
            if (surgeEventOutdate < 5) {
                surgeEventOutdate += 5;
            }
        } else {
            surgeEventOutdate = 10;
        }
    }

    @CaseIDs({@CaseId(14), @CaseId(23), @CaseId(25)})
    @Story("Demand")
    @Test(description = "Расчет surgelevel при получении события EventOrder с новым ON_DEMAND заказом в активном статусе",
            groups = "ondemand-surgelevel-smoke",
            priority = -1)
    public void surgeProduceEventOrderActive() {
        EventOrderChanged eventOrder = getEventOrderStatus(ORDER_UUID, STORE_ID, OrderStatus.NEW, ShipmentType.ON_DEMAND, SHIPMENT_UUID);
        kafka.publish(configOrderStatusChanged(), eventOrder);

        ThreadUtil.simplyAwait(surgeEventOutdate);

        List<Surgelevelevent.SurgeEvent> surgeLevels = kafka.waitDataInKafkaTopicSurgeLevel(STORE_ID);
        surgeEventsAmount = surgeLevels.size();

        pastSurgeLevel = currentSurgeLevel;
        currentSurgeLevel++;
        currentDemandAmount++;

        checkSurgeLevelProduce(surgeLevels, surgeEventsAmount, STORE_ID, 0f, currentSurgeLevel, currentDemandAmount, currentSupplyAmount);
    }

    @CaseId(38)
    @Story("Demand")
    @Test(description = "Расчет surgelevel при получении события EventOrder с ON_DEMAND заказом, который перестал быть активным",
            groups = "ondemand-surgelevel-regress",
            dependsOnMethods = "surgeProduceEventOrderRepeatActive")
    public void surgeProduceEventOrderNoLongerActive() {
        EventOrderChanged eventOrder = getEventOrderStatus(ORDER_UUID, STORE_ID, OrderStatus.SHIPPING, ShipmentType.ON_DEMAND, SHIPMENT_UUID);
        kafka.publish(configOrderStatusChanged(), eventOrder);

        ThreadUtil.simplyAwait(surgeEventOutdate);

        List<Surgelevelevent.SurgeEvent> surgeLevels = kafka.waitDataInKafkaTopicSurgeLevel(STORE_ID);
        surgeEventsAmount = surgeLevels.size();

        pastSurgeLevel = currentSurgeLevel;
        currentSurgeLevel--;

        checkSurgeLevelProduce(surgeLevels, surgeEventsAmount, STORE_ID, pastSurgeLevel, currentSurgeLevel, currentDemandAmount, currentSupplyAmount);
    }

    @CaseId(40)
    @Story("Demand")
    @Test(description = "Отсутствие расчета surgelevel при получении события EventOrder с ранее полученным ON_DEMAND заказом в активном статусе",
            groups = "ondemand-surgelevel-regress",
            dependsOnMethods = "surgeProduceEventOrderActive")
    public void surgeProduceEventOrderRepeatActive() {
        EventOrderChanged eventOrder = getEventOrderStatus(ORDER_UUID, STORE_ID, OrderStatus.ROUTING, ShipmentType.ON_DEMAND, SHIPMENT_UUID);
        kafka.publish(configOrderStatusChanged(), eventOrder);

        ThreadUtil.simplyAwait(5);

        Allure.step("Проверка отсутствия изменения surgelevel и/или добавления demand/supply", () -> compareTwoObjects(currentSurgeLevel, ResultDao.INSTANCE.findResult(STORE_ID).getSurgeLevel().floatValue()));
    }

    @CaseId(15)
    @Story("Demand")
    @Test(description = "Отсутствие расчета surgelevel при получении события EventOrder с новым ON_DEMAND заказом в не активном статусе",
            groups = "ondemand-surgelevel-regress")
    public void surgeProduceEventOrderNotActive() {
        EventOrderChanged eventOrder = getEventOrderStatus(UUID.randomUUID().toString(), STORE_ID, OrderStatus.SHIPPING, ShipmentType.ON_DEMAND, UUID.randomUUID().toString());
        kafka.publish(configOrderStatusChanged(), eventOrder);

        ThreadUtil.simplyAwait(5);

        currentDemandAmount++;

        Allure.step("Проверка отсутствия изменения surgelevel и/или добавления demand/supply", () -> compareTwoObjects(currentSurgeLevel, ResultDao.INSTANCE.findResult(STORE_ID).getSurgeLevel().floatValue()));
    }

    @CaseId(16)
    @Story("Demand")
    @Test(description = "Отсутствие расчета surgelevel при получении события EventOrder с новым не ON_DEMAND заказом",
            groups = "ondemand-surgelevel-smoke")
    public void surgeProduceEventOrderNotOnDemand() {
        String shipmentUuid = UUID.randomUUID().toString();

        EventOrderChanged eventOrder = getEventOrderStatus(UUID.randomUUID().toString(), STORE_ID, OrderStatus.NEW, ShipmentType.PLANNED, shipmentUuid);
        kafka.publish(configOrderStatusChanged(), eventOrder);

        ThreadUtil.simplyAwait(5);

        Allure.step("Проверка отсутствия изменения surgelevel и/или добавления demand/supply", () -> {
            assertNull(DemandDao.INSTANCE.findDemand(STORE_ID, shipmentUuid), "Заказ добавился в demand");
            compareTwoObjects(currentSurgeLevel, ResultDao.INSTANCE.findResult(STORE_ID).getSurgeLevel().floatValue());
        });
    }

    @CaseId(133)
    @Story("Demand")
    @Test(description = "Расчет surgelevel для нескольких магазинов при получении события EventOrder с новым ON_DEMAND заказом в активном статусе",
            groups = "ondemand-surgelevel-smoke")
    public void surgeProduceEventOrderMultipleStores() {
        String shipmentUUid = UUID.randomUUID().toString();
        EventOrderChanged eventOrder = getEventOrderStatus(UUID.randomUUID().toString(), FIRST_STORE_ID, OrderStatus.NEW, ShipmentType.ON_DEMAND, shipmentUUid);
        kafka.publish(configOrderStatusChanged(), eventOrder);

        ThreadUtil.simplyAwait(surgeEventOutdate);

        List<Surgelevelevent.SurgeEvent> surgeLevelFirstStore = kafka.waitDataInKafkaTopicSurgeLevel(FIRST_STORE_ID);
        List<Surgelevelevent.SurgeEvent> surgeLevelSecondStore = kafka.waitDataInKafkaTopicSurgeLevel(SECOND_STORE_ID);
        List<Surgelevelevent.SurgeEvent> surgeLevelThirdStore = kafka.waitDataInKafkaTopicSurgeLevel(THIRD_STORE_ID);

        currentSurgeLevelMultipleStores++;

        Allure.step("Проверка отправка нового события surgelevel в kafka для нескольких магазинов", () -> {
            checkSurgeLevelProduce(surgeLevelFirstStore, surgeLevelFirstStore.size(), FIRST_STORE_ID, 0, currentSurgeLevelMultipleStores, 1, 0);
            checkSurgeLevelProduce(surgeLevelSecondStore, surgeLevelSecondStore.size(), SECOND_STORE_ID, 0, currentSurgeLevelMultipleStores, 1, 0);
            checkSurgeLevelProduce(surgeLevelThirdStore, surgeLevelThirdStore.size(), THIRD_STORE_ID, 0, currentSurgeLevelMultipleStores, 1, 0);
        });
        Allure.step("Проверка корректной записи расстояния для inner и outer магазинов", () -> {
            double firstStoreDist = DemandDao.INSTANCE.findDemand(FIRST_STORE_ID, shipmentUUid).getDistance();
            double secondStoreDist = DemandDao.INSTANCE.findDemand(SECOND_STORE_ID, shipmentUUid).getDistance();
            double thirdStoreDist = DemandDao.INSTANCE.findDemand(THIRD_STORE_ID, shipmentUUid).getDistance();

            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(firstStoreDist, 0d, "Не верное расстояние для inner магазина");
            softAssert.assertEquals(secondStoreDist, distFirstSecond, 10d, "Не верное расстояние для outer магазина");
            softAssert.assertEquals(thirdStoreDist, distFirstThird, 10d, "Не верное расстояние для outer магазина");
            softAssert.assertAll();
        });
    }

    @CaseId(141)
    @Story("Demand")
    @Test(description = "Отсутствие расчета surgelevel для outer-магазинов при distance > STORE_RADIUS",
            groups = "ondemand-surgelevel-smoke",
            dependsOnMethods = "surgeProduceEventOrderMultipleStores")
    public void surgeProduceEventOrderGreaterStoreRadius() {
        String shipmentUUid = UUID.randomUUID().toString();
        EventOrderChanged eventOrder = getEventOrderStatus(UUID.randomUUID().toString(), SECOND_STORE_ID, OrderStatus.NEW, ShipmentType.ON_DEMAND, shipmentUUid);
        kafka.publish(configOrderStatusChanged(), eventOrder);

        ThreadUtil.simplyAwait(surgeEventOutdate);

        List<Surgelevelevent.SurgeEvent> surgeLevelFirstStore = kafka.waitDataInKafkaTopicSurgeLevel(FIRST_STORE_ID);
        List<Surgelevelevent.SurgeEvent> surgeLevelSecondStore = kafka.waitDataInKafkaTopicSurgeLevel(SECOND_STORE_ID);
        List<Surgelevelevent.SurgeEvent> surgeLevelThirdStore = kafka.waitDataInKafkaTopicSurgeLevel(THIRD_STORE_ID);

        currentSurgeLevelMultipleStores++;

        Allure.step("Проверка отправка нового события surgelevel в kafka для нескольких магазинов", () -> {
            checkSurgeLevelProduce(surgeLevelFirstStore, surgeLevelFirstStore.size(), FIRST_STORE_ID, currentSurgeLevelMultipleStores - 1, currentSurgeLevelMultipleStores, 2, 0);
            checkSurgeLevelProduce(surgeLevelSecondStore, surgeLevelSecondStore.size(), SECOND_STORE_ID, currentSurgeLevelMultipleStores - 1, currentSurgeLevelMultipleStores, 2, 0);
            assertEquals(surgeLevelThirdStore.size(), 1, "Поступило новое событие");
        });
        Allure.step("Проверка корректной записи расстояния для inner и outer магазинов", () -> {
            double firstStoreDist = DemandDao.INSTANCE.findDemand(FIRST_STORE_ID, shipmentUUid).getDistance();
            double secondStoreDist = DemandDao.INSTANCE.findDemand(SECOND_STORE_ID, shipmentUUid).getDistance();

            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(secondStoreDist, 0d, "Не верное расстояние для inner магазина");
            softAssert.assertEquals(firstStoreDist, distFirstSecond, 10d, "Не верное расстояние для outer магазина");
            softAssert.assertNull(DemandDao.INSTANCE.findDemand(THIRD_STORE_ID, shipmentUUid), "Добавился demand для дальнего магазина");
            softAssert.assertAll();
        });
    }

    @CaseIDs({@CaseId(123), @CaseId(125)})
    @Story("Delivery Area Supply")
    @Test(description = "Расчет surgelevel при получении свободного кандидата по delivery_area",
            groups = "ondemand-surgelevel-smoke")
    public void surgeProduceEventCandidate() {
        CandidateChanges eventCandidate = getEventCandidateStatus(CANDIDATE_UUID_DELIVERY_AREA, CandidateChanges.Role.UNIVERSAL, CandidateChanges.Status.FREE, 0, FIRST_DELIVERY_AREA_ID, true, STORE_ID, DISPATCH.getName());
        kafka.publish(configCandidateStatus(), eventCandidate);

        ThreadUtil.simplyAwait(1);

        EventAddLocation eventLocation = getEventLocation(CANDIDATE_UUID_DELIVERY_AREA, 15.0f, 15.0f, false);
        kafka.publish(configNorns(), eventLocation);

        ThreadUtil.simplyAwait(surgeEventOutdate);

        List<Surgelevelevent.SurgeEvent> surgeLevels = kafka.waitDataInKafkaTopicSurgeLevel(STORE_ID);
        surgeEventsAmount = surgeLevels.size();

        pastSurgeLevel = currentSurgeLevel;
        currentSurgeLevel--;
        currentSupplyAmount++;

        checkSurgeLevelProduce(surgeLevels, surgeEventsAmount, STORE_ID, pastSurgeLevel, currentSurgeLevel, currentDemandAmount, currentSupplyAmount);
    }

    @CaseId(124)
    @Story("Delivery Area Supply")
    @Test(description = "Расчет surgelevel при получении события с кандидатом, который перестал быть свободным по delivery_area",
            groups = "ondemand-surgelevel-regress",
            dependsOnMethods = "surgeProduceEventCandidate")
    public void surgeProduceEventCandidateBusy() {
        CandidateChanges eventCandidate = getEventCandidateStatus(CANDIDATE_UUID_DELIVERY_AREA, CandidateChanges.Role.UNIVERSAL, CandidateChanges.Status.BUSY, 0, FIRST_DELIVERY_AREA_ID, true, STORE_ID, DISPATCH.getName());
        kafka.publish(configCandidateStatus(), eventCandidate);

        ThreadUtil.simplyAwait(surgeEventOutdate);

        List<Surgelevelevent.SurgeEvent> surgeLevels = kafka.waitDataInKafkaTopicSurgeLevel(STORE_ID);
        surgeEventsAmount = surgeLevels.size();

        pastSurgeLevel = currentSurgeLevel;
        currentSurgeLevel++;

        checkSurgeLevelProduce(surgeLevels, surgeEventsAmount, STORE_ID, pastSurgeLevel, currentSurgeLevel, currentDemandAmount, currentSupplyAmount);
    }

    @CaseId(126)
    @Story("Delivery Area Supply")
    @Test(description = "Отсутствие расчета surgelevel при получении свободного не on-demand кандидата",
            groups = "ondemand-surgelevel-regress")
    public void surgeProduceEventCandidateNotOnDemand() {
        String candidateUuid = UUID.randomUUID().toString();

        CandidateChanges eventCandidate = getEventCandidateStatus(candidateUuid, CandidateChanges.Role.UNIVERSAL, CandidateChanges.Status.FREE, 0, FIRST_DELIVERY_AREA_ID, true, STORE_ID, YANDEX.getName());
        kafka.publish(configCandidateStatus(), eventCandidate);

        ThreadUtil.simplyAwait(1);

        EventAddLocation eventLocation = getEventLocation(candidateUuid, fourthStoreLocation.getLat(), fourthStoreLocation.getLon(), false);
        kafka.publish(configNorns(), eventLocation);

        ThreadUtil.simplyAwait(5);

        Allure.step("Проверка отсутствия изменения surgelevel и/или добавления demand/supply", () -> {
            assertNull(SupplyDao.INSTANCE.findSupply(STORE_ID, candidateUuid), "Кандидат добавился в supply");
            compareTwoObjects(currentSurgeLevel, ResultDao.INSTANCE.findResult(STORE_ID).getSurgeLevel().floatValue());
        });
    }

    @CaseId(127)
    @Story("Delivery Area Supply")
    @Test(description = "Отсутствие расчета surgelevel при получении UNCHANGED кандидата",
            groups = "ondemand-surgelevel-regress",
            dependsOnMethods = "surgeProduceEventCandidateBusy")
    public void surgeProduceEventCandidateUnchanged() {
        CandidateChanges eventCandidate = getEventCandidateStatus(CANDIDATE_UUID_DELIVERY_AREA, CandidateChanges.Role.UNIVERSAL, CandidateChanges.Status.UNCHANGED, 0, FIRST_DELIVERY_AREA_ID, true, STORE_ID, DISPATCH.getName());
        kafka.publish(configCandidateStatus(), eventCandidate);

        ThreadUtil.simplyAwait(5);

        Allure.step("Проверка отсутствия изменения surgelevel и/или добавления demand/supply", () -> compareTwoObjects(currentSurgeLevel, ResultDao.INSTANCE.findResult(STORE_ID).getSurgeLevel().floatValue()));
    }

    @CaseId(138)
    @Story("Delivery Area Supply")
    @Test(description = "Отсутствие расчета surgelevel при пустых координатах кандидата",
            groups = "ondemand-surgelevel-regress")
    public void surgeProduceEventCandidateNoCoordinates() {
        String candidateUuid = UUID.randomUUID().toString();

        CandidateChanges eventCandidate = getEventCandidateStatus(candidateUuid, CandidateChanges.Role.UNIVERSAL, CandidateChanges.Status.FREE, 0, FIRST_DELIVERY_AREA_ID, true, STORE_ID, DISPATCH.getName());
        kafka.publish(configCandidateStatus(), eventCandidate);

        ThreadUtil.simplyAwait(5);

        Allure.step("Проверка отсутствия изменения surgelevel и/или добавления demand/supply", () -> {
            assertNull(SupplyDao.INSTANCE.findSupply(STORE_ID, candidateUuid), "Кандидат добавился в supply");
            compareTwoObjects(currentSurgeLevel, ResultDao.INSTANCE.findResult(STORE_ID).getSurgeLevel().floatValue());
        });
    }

    @CaseId(137)
    @Story("Delivery Area Supply")
    @Test(description = "Расчет surgelevel для нескольких магазинов при получении свободного кандидата по delivery_area",
            groups = "ondemand-surgelevel-smoke",
            dependsOnMethods = "surgeProduceEventOrderGreaterStoreRadius")
    public void surgeProduceEventCandidateMultipleStores() {
        String candidateUUid = UUID.randomUUID().toString();

        CandidateChanges eventCandidate = getEventCandidateStatus(candidateUUid, CandidateChanges.Role.UNIVERSAL, CandidateChanges.Status.FREE, 0, SECOND_DELIVERY_AREA_ID, true, FIRST_STORE_ID, DISPATCH.getName());
        kafka.publish(configCandidateStatus(), eventCandidate);

        ThreadUtil.simplyAwait(1);

        EventAddLocation eventLocation = getEventLocation(candidateUUid, 15.0f, 15.0f, false);
        kafka.publish(configNorns(), eventLocation);

        ThreadUtil.simplyAwait(surgeEventOutdate);

        List<Surgelevelevent.SurgeEvent> surgeLevelFirstStore = kafka.waitDataInKafkaTopicSurgeLevel(FIRST_STORE_ID);
        List<Surgelevelevent.SurgeEvent> surgeLevelSecondStore = kafka.waitDataInKafkaTopicSurgeLevel(SECOND_STORE_ID);
        List<Surgelevelevent.SurgeEvent> surgeLevelThirdStore = kafka.waitDataInKafkaTopicSurgeLevel(THIRD_STORE_ID);

        currentSurgeLevelMultipleStores--;

        Allure.step("Проверка отправка нового события surgelevel в kafka для нескольких магазинов", () -> {
            checkSurgeLevelProduce(surgeLevelFirstStore, surgeLevelFirstStore.size(), FIRST_STORE_ID, currentSurgeLevelMultipleStores + 1, currentSurgeLevelMultipleStores, 2, 1);
            checkSurgeLevelProduce(surgeLevelSecondStore, surgeLevelSecondStore.size(), SECOND_STORE_ID, currentSurgeLevelMultipleStores + 1, currentSurgeLevelMultipleStores, 2, 1);
            assertEquals(surgeLevelThirdStore.size(), 1, "Поступило новое событие");
        });
    }

    @CaseId(20)
    @Story("Radius Supply")
    @Test(description = "Расчет surgelevel при получении свободного кандидата по координатам в близи от магазина",
            groups = "ondemand-surgelevel-smoke")
    public void surgeProduceEventCandidateWithLocation() {
        CandidateChanges eventCandidate = getEventCandidateStatus(CANDIDATE_UUID, CandidateChanges.Role.UNIVERSAL, CandidateChanges.Status.FREE, 0, 0, false, STORE_ID, DISPATCH.getName());
        kafka.publish(configCandidateStatus(), eventCandidate);

        ThreadUtil.simplyAwait(1);

        EventAddLocation eventLocation = getEventLocation(CANDIDATE_UUID, fourthStoreLocation.getLat(), fourthStoreLocation.getLon(), false);
        kafka.publish(configNorns(), eventLocation);

        ThreadUtil.simplyAwait(surgeEventOutdate);

        List<Surgelevelevent.SurgeEvent> surgeLevels = kafka.waitDataInKafkaTopicSurgeLevel(STORE_ID);
        surgeEventsAmount = surgeLevels.size();

        pastSurgeLevel = currentSurgeLevel;
        currentSurgeLevel--;
        currentSupplyAmount++;

        checkSurgeLevelProduce(surgeLevels, surgeEventsAmount, STORE_ID, pastSurgeLevel, currentSurgeLevel, currentDemandAmount, currentSupplyAmount);
    }

    @CaseId(21)
    @Story("Radius Supply")
    @Test(description = "Расчет surgelevel при получении события CandidateChanges с кандидатом, который перестал быть свободным, по координатам в близи от магазина",
            groups = "ondemand-surgelevel-smoke",
            dependsOnMethods = "surgeProduceEventCandidateWithLocation")
    public void surgeProduceEventCandidateWithLocationNoLongerFree() {
        CandidateChanges eventCandidate = getEventCandidateStatus(CANDIDATE_UUID, CandidateChanges.Role.UNIVERSAL, CandidateChanges.Status.BUSY, 0, 0, false, STORE_ID, DISPATCH.getName());
        kafka.publish(configCandidateStatus(), eventCandidate);

        ThreadUtil.simplyAwait(surgeEventOutdate);

        List<Surgelevelevent.SurgeEvent> surgeLevels = kafka.waitDataInKafkaTopicSurgeLevel(STORE_ID);
        surgeEventsAmount = surgeLevels.size();

        pastSurgeLevel = currentSurgeLevel;
        currentSurgeLevel++;

        checkSurgeLevelProduce(surgeLevels, surgeEventsAmount, STORE_ID, pastSurgeLevel, currentSurgeLevel, currentDemandAmount, currentSupplyAmount);
    }

    @CaseId(48)
    @Story("Radius Supply")
    @Test(description = "Расчет surgelevel при получении события CandidateChanges с кандидатом, который снова стал свободным, по координатам в близи от магазина",
            groups = "ondemand-surgelevel-smoke",
            dependsOnMethods = "surgeProduceEventCandidateWithLocationNoLongerFree")
    public void surgeProduceEventCandidateWithLocationFreeAgain() {
        CandidateChanges eventCandidate = getEventCandidateStatus(CANDIDATE_UUID, CandidateChanges.Role.UNIVERSAL, CandidateChanges.Status.FREE, 0, 0, false, STORE_ID, DISPATCH.getName());
        kafka.publish(configCandidateStatus(), eventCandidate);

        ThreadUtil.simplyAwait(surgeEventOutdate);

        List<Surgelevelevent.SurgeEvent> surgeLevels = kafka.waitDataInKafkaTopicSurgeLevel(STORE_ID);
        surgeEventsAmount = surgeLevels.size();

        pastSurgeLevel = currentSurgeLevel;
        currentSurgeLevel--;

        checkSurgeLevelProduce(surgeLevels, surgeEventsAmount, STORE_ID, pastSurgeLevel, currentSurgeLevel, currentDemandAmount, currentSupplyAmount);
    }

    @CaseId(49)
    @Story("Radius Supply")
    @Test(description = "Расчет surgelevel при уходе свободного кандидата по координатам вдаль от магазина",
            groups = "ondemand-surgelevel-smoke",
            dependsOnMethods = "surgeProduceEventCandidateWithLocationFreeAgain")
    public void surgeProduceEventCandidateWithLocationWentTooFar() {
        EventAddLocation eventLocation = getEventLocation(CANDIDATE_UUID, 15.0f, 15.0f, false);
        kafka.publish(configNorns(), eventLocation);

        ThreadUtil.simplyAwait(surgeEventOutdate);

        List<Surgelevelevent.SurgeEvent> surgeLevels = kafka.waitDataInKafkaTopicSurgeLevel(STORE_ID);
        surgeEventsAmount = surgeLevels.size();

        pastSurgeLevel = currentSurgeLevel;
        currentSurgeLevel++;
        currentSupplyAmount--;

        checkSurgeLevelProduce(surgeLevels, surgeEventsAmount, STORE_ID, pastSurgeLevel, currentSurgeLevel, currentDemandAmount, currentSupplyAmount);
    }

    @CaseId(37)
    @Story("Radius Supply")
    @Test(description = "Отсутствие расчета surgelevel при получении события CandidateChanges с занятым кандидатом по координатам в близи от магазина",
            groups = "ondemand-surgelevel-smoke")
    public void surgeProduceEventCandidateWithLocationBusy() {
        String candidateUuid = UUID.randomUUID().toString();

        CandidateChanges eventCandidate = getEventCandidateStatus(candidateUuid, CandidateChanges.Role.UNIVERSAL, CandidateChanges.Status.BUSY, 0, 0, false, STORE_ID, DISPATCH.getName());
        kafka.publish(configCandidateStatus(), eventCandidate);

        ThreadUtil.simplyAwait(1);

        EventAddLocation eventLocation = getEventLocation(candidateUuid, fourthStoreLocation.getLat(), fourthStoreLocation.getLon(), false);
        kafka.publish(configNorns(), eventLocation);

        ThreadUtil.simplyAwait(5);

        currentSupplyAmount++;

        Allure.step("Проверка отсутствия изменения surgelevel и/или добавления demand/supply", () -> compareTwoObjects(currentSurgeLevel, ResultDao.INSTANCE.findResult(STORE_ID).getSurgeLevel().floatValue()));
    }

    @CaseId(22)
    @Story("Radius Supply")
    @Test(description = "Отсутствие расчета surgelevel при получении свободного кандидата по координатам не в близи от магазина",
            groups = "ondemand-surgelevel-smoke")
    public void surgeProduceEventCandidateWithLocationFar() {
        String candidateUuid = UUID.randomUUID().toString();

        CandidateChanges eventCandidate = getEventCandidateStatus(candidateUuid, CandidateChanges.Role.UNIVERSAL, CandidateChanges.Status.FREE, 0, 0, false, STORE_ID, DISPATCH.getName());
        kafka.publish(configCandidateStatus(), eventCandidate);

        ThreadUtil.simplyAwait(1);

        EventAddLocation eventLocation = getEventLocation(candidateUuid, 15.0f, 15.0f, false);
        kafka.publish(configNorns(), eventLocation);

        ThreadUtil.simplyAwait(5);

        Allure.step("Проверка отсутствия изменения surgelevel и/или добавления demand/supply", () -> {
            assertNull(SupplyDao.INSTANCE.findSupply(STORE_ID, candidateUuid), "Кандидат добавился в supply");
            compareTwoObjects(currentSurgeLevel, ResultDao.INSTANCE.findResult(STORE_ID).getSurgeLevel().floatValue());
        });
    }

    @CaseId(41)
    @Story("Radius Supply")
    @Test(description = "Расчет surgelevel для нескольких магазинов при получении свободного кандидата",
            groups = "ondemand-surgelevel-smoke",
            dependsOnMethods = "surgeProduceEventCandidateMultipleStores")
    public void surgeProduceEventCandidateLocationMultipleStores() {
        String candidateUUid = UUID.randomUUID().toString();

        CandidateChanges eventCandidate = getEventCandidateStatus(candidateUUid, CandidateChanges.Role.UNIVERSAL, CandidateChanges.Status.FREE, 0, 0, false, FIRST_STORE_ID, DISPATCH.getName());
        kafka.publish(configCandidateStatus(), eventCandidate);

        ThreadUtil.simplyAwait(1);

        EventAddLocation eventLocation = getEventLocation(candidateUUid, firstStoreLocation.getLat(), firstStoreLocation.getLon(), false);
        kafka.publish(configNorns(), eventLocation);

        ThreadUtil.simplyAwait(surgeEventOutdate);

        List<Surgelevelevent.SurgeEvent> surgeLevelFirstStore = kafka.waitDataInKafkaTopicSurgeLevel(FIRST_STORE_ID);
        List<Surgelevelevent.SurgeEvent> surgeLevelSecondStore = kafka.waitDataInKafkaTopicSurgeLevel(SECOND_STORE_ID);
        List<Surgelevelevent.SurgeEvent> surgeLevelThirdStore = kafka.waitDataInKafkaTopicSurgeLevel(THIRD_STORE_ID);

        currentSurgeLevelMultipleStores--;

        Allure.step("Проверка отправка нового события surgelevel в kafka для нескольких магазинов", () -> {
            checkSurgeLevelProduce(surgeLevelFirstStore, surgeLevelFirstStore.size(), FIRST_STORE_ID, currentSurgeLevelMultipleStores + 1, currentSurgeLevelMultipleStores, 2, 2);
            checkSurgeLevelProduce(surgeLevelSecondStore, surgeLevelSecondStore.size(), SECOND_STORE_ID, currentSurgeLevelMultipleStores + 1, currentSurgeLevelMultipleStores, 2, 2);
            checkSurgeLevelProduce(surgeLevelThirdStore, surgeLevelThirdStore.size(), THIRD_STORE_ID, currentSurgeLevelMultipleStores + 1, currentSurgeLevelMultipleStores, 1, 1);
        });
    }

    @CaseId(157)
    @Story("Workflow")
    @Test(description = "Проверка supply при получении in_progress МЛ",
            groups = "ondemand-surgelevel-smoke",
            dependsOnMethods = "surgeProduceEventCandidateLocationMultipleStores")
    public void surgeProduceEventCandidateWithWorkflow() {
        CandidateChanges eventCandidate = getEventCandidateStatus(CANDIDATE_UUID_WORKFLOW, CandidateChanges.Role.UNIVERSAL, CandidateChanges.Status.FREE,
                0, 0, false, SECOND_STORE_ID, DISPATCH.getName());
        kafka.publish(configCandidateStatus(), eventCandidate);

        ThreadUtil.simplyAwait(1);

        EventAddLocation eventLocation = getEventLocation(CANDIDATE_UUID_WORKFLOW, secondStoreLocation.getLat(), secondStoreLocation.getLon(), false);
        kafka.publish(configNorns(), eventLocation);

        ThreadUtil.simplyAwait(1);

        CandidateChanges eventCandidateWithWorkflow = getEventCandidateStatusWithWorkflow(CANDIDATE_UUID_WORKFLOW, CandidateChanges.Role.UNIVERSAL, CandidateChanges.Status.BUSY,
                0, 0, false, SECOND_STORE_ID, DISPATCH.getName(),
                FIRST_WORKFLOW_ID, WorkflowStatus.IN_PROGRESS, getTimestamp(), workflowPlanEnd.getLat(), workflowPlanEnd.getLon());
        kafka.publish(configCandidateStatus(), eventCandidateWithWorkflow);

        ThreadUtil.simplyAwait(1);

        Allure.step("Проверка сохранения supply", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertNotNull(SupplyDao.INSTANCE.findSupply(FIRST_STORE_ID, CANDIDATE_UUID_WORKFLOW), "Пропал supply");
            softAssert.assertNotNull(SupplyDao.INSTANCE.findSupply(SECOND_STORE_ID, CANDIDATE_UUID_WORKFLOW), "Пропал supply");
            softAssert.assertAll();
        });
    }

    @CaseIDs({@CaseId(158), @CaseId(159)})
    @Story("Workflow")
    @Test(description = "Проверка удаления supply при уходе от изначальной точки с in_progress МЛ",
            groups = "ondemand-surgelevel-regress",
            dependsOnMethods = "surgeProduceEventCandidateWithWorkflow")
    public void surgeProduceEventCandidateWithWorkflowStartMoving() {
        EventAddLocation eventLocation = getEventLocation(CANDIDATE_UUID_WORKFLOW, fourthStoreLocation.getLat(), fourthStoreLocation.getLon(), false);
        kafka.publish(configNorns(), eventLocation);

        ThreadUtil.simplyAwait(1);

        Allure.step("Проверка удаления supply", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertNull(SupplyDao.INSTANCE.findSupply(FIRST_STORE_ID, CANDIDATE_UUID_WORKFLOW), "Supply не удалился");
            softAssert.assertNull(SupplyDao.INSTANCE.findSupply(SECOND_STORE_ID, CANDIDATE_UUID_WORKFLOW), "Supply не удалился");
            softAssert.assertNull(SupplyDao.INSTANCE.findSupply(STORE_ID, CANDIDATE_UUID_WORKFLOW), "Supply добавился для магазина по пути");
            softAssert.assertAll();
        });
    }

    @CaseId(160)
    @Story("Workflow")
    @Test(description = "Проверка добавления supply при вхождении в радиус к точке прибытия in_progress МЛ",
            groups = "ondemand-surgelevel-smoke",
            dependsOnMethods = "surgeProduceEventCandidateWithWorkflowStartMoving")
    public void surgeProduceEventCandidateWithWorkflowFinishing() {
        EventAddLocation eventLocation = getEventLocation(CANDIDATE_UUID_WORKFLOW, thirdStoreLocation.getLat(), thirdStoreLocation.getLon(), false);
        kafka.publish(configNorns(), eventLocation);

        ThreadUtil.simplyAwait(1);

        Allure.step("Проверка добавления supply в магазины в store_radius от точки прибытия", () -> {
            final SoftAssert softAssert = new SoftAssert();
            assertNotNull(SupplyDao.INSTANCE.findSupply(THIRD_STORE_ID, CANDIDATE_UUID_WORKFLOW), "Supply не добавился");
            assertNull(SupplyDao.INSTANCE.findSupply(FIRST_STORE_ID, CANDIDATE_UUID_WORKFLOW), "Supply добавился в магазин > store_radius от точки прибытия");
            softAssert.assertAll();
        });
    }

    @CaseId(162)
    @Story("Workflow")
    @Test(description = "Проверка отсутствия влияния in_progress МЛ на кандидата с delivery_area",
            groups = "ondemand-surgelevel-smoke",
            dependsOnMethods = "surgeProduceEventCandidateWithWorkflowFinishing")
    public void surgeProduceEventCandidateWithWorkflowAndDeliveryArea() {
        String candidateUuid = UUID.randomUUID().toString();

        CandidateChanges eventCandidate = getEventCandidateStatus(candidateUuid, CandidateChanges.Role.UNIVERSAL, CandidateChanges.Status.FREE,
                0, SECOND_DELIVERY_AREA_ID, true, SECOND_STORE_ID, DISPATCH.getName());
        kafka.publish(configCandidateStatus(), eventCandidate);

        ThreadUtil.simplyAwait(1);

        EventAddLocation initialEventLocation = getEventLocation(candidateUuid, secondStoreLocation.getLat(), secondStoreLocation.getLon(), false);
        kafka.publish(configNorns(), initialEventLocation);

        ThreadUtil.simplyAwait(1);

        CandidateChanges eventCandidateWithWorkflow = getEventCandidateStatusWithWorkflow(candidateUuid, CandidateChanges.Role.UNIVERSAL, CandidateChanges.Status.BUSY,
                0, SECOND_DELIVERY_AREA_ID, true, SECOND_STORE_ID, DISPATCH.getName(),
                SECOND_WORKFLOW_ID, WorkflowStatus.IN_PROGRESS, getTimestamp(), workflowPlanEnd.getLat(), workflowPlanEnd.getLon());
        kafka.publish(configCandidateStatus(), eventCandidateWithWorkflow);

        ThreadUtil.simplyAwait(1);

        EventAddLocation finalEventLocation = getEventLocation(candidateUuid, workflowPlanEnd.getLat(), workflowPlanEnd.getLon(), false);
        kafka.publish(configNorns(), finalEventLocation);

        ThreadUtil.simplyAwait(1);

        Allure.step("Проверка отсутствия применения логики workflow для deliveryarea кандидата", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertNotNull(SupplyDao.INSTANCE.findSupply(FIRST_STORE_ID, candidateUuid), "Пропал supply от deliveryarea");
            softAssert.assertNotNull(SupplyDao.INSTANCE.findSupply(SECOND_STORE_ID, candidateUuid), "Пропал supply от deliveryarea");
            softAssert.assertNull(SupplyDao.INSTANCE.findSupply(THIRD_STORE_ID, candidateUuid), "Supply добавился по workflow");
            softAssert.assertAll();
        });
    }

    @Skip
    @Issue("HG-1092")
    @CaseId(161)
    @Story("Workflow")
    @Test(description = "Проверка отсутствия влияния не in_progress МЛ на кандидата",
            groups = "ondemand-surgelevel-regress",
            dependsOnMethods = "surgeProduceEventCandidateWithWorkflowAndDeliveryArea")
    public void surgeProduceEventCandidateWithQueuedWorkflow() {
        String candidateUuid = UUID.randomUUID().toString();

        CandidateChanges eventCandidate = getEventCandidateStatus(candidateUuid, CandidateChanges.Role.UNIVERSAL, CandidateChanges.Status.FREE,
                0, 0, false, SECOND_STORE_ID, DISPATCH.getName());
        kafka.publish(configCandidateStatus(), eventCandidate);

        ThreadUtil.simplyAwait(1);

        EventAddLocation initialEventLocation = getEventLocation(candidateUuid, secondStoreLocation.getLat(), secondStoreLocation.getLon(), false);
        kafka.publish(configNorns(), initialEventLocation);

        ThreadUtil.simplyAwait(1);

        CandidateChanges eventCandidateWithWorkflow = getEventCandidateStatusWithWorkflow(candidateUuid, CandidateChanges.Role.UNIVERSAL, CandidateChanges.Status.FREE,
                0, 0, false, SECOND_STORE_ID, DISPATCH.getName(),
                SECOND_WORKFLOW_ID + 1, WorkflowStatus.QUEUED, getTimestamp(), workflowPlanEnd.getLat(), workflowPlanEnd.getLon());
        kafka.publish(configCandidateStatus(), eventCandidateWithWorkflow);

        ThreadUtil.simplyAwait(1);

        EventAddLocation finalEventLocation = getEventLocation(candidateUuid, thirdStoreLocation.getLat(), thirdStoreLocation.getLon(), false);
        kafka.publish(configNorns(), finalEventLocation);

        ThreadUtil.simplyAwait(1);

        Allure.step("Проверка добавления supply только по radius", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertNotNull(SupplyDao.INSTANCE.findSupply(FIRST_STORE_ID, candidateUuid), "Не добавился supply по radius1");
            softAssert.assertNotNull(SupplyDao.INSTANCE.findSupply(THIRD_STORE_ID, candidateUuid), "Не добавился supply по radius3");
            softAssert.assertAll();
        });
    }

    @AfterClass(alwaysRun = true)
    public void postConditions() {
        deleteStore(STORE_ID);
        deleteStore(FIRST_STORE_ID);
        deleteStore(SECOND_STORE_ID);
        deleteStore(THIRD_STORE_ID);
    }
}
