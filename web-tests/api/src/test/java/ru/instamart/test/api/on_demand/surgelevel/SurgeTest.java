package ru.instamart.test.api.on_demand.surgelevel;

import io.qameta.allure.*;
import order.OrderChanged.EventOrderChanged.OrderStatus;
import order.OrderChanged.EventOrderChanged.ShipmentType;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.helper.SurgeLevelHelper;
import ru.instamart.jdbc.dao.surgelevel.*;
import ru.instamart.jdbc.entity.surgelevel.DemandEntity;
import ru.instamart.jdbc.entity.surgelevel.StoreEntity;
import ru.instamart.jdbc.entity.surgelevel.SupplyEntity;
import ru.instamart.kraken.util.ThreadUtil;
import surgelevel.Surgelevel;
import surgelevelevent.Surgelevelevent;
import surgelevelevent.Surgelevelevent.SurgeEvent.Method;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static events.CandidateChangesOuterClass.CandidateChanges;
import static events.CandidateChangesOuterClass.WorkflowStatus;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.testng.Assert.*;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.helper.SurgeLevelHelper.*;
import static ru.instamart.kraken.enums.ScheduleType.DISPATCH;
import static ru.instamart.kraken.enums.ScheduleType.YANDEX;
import static ru.instamart.kraken.util.DistanceUtil.distance;
import static ru.instamart.kraken.util.TimeUtil.*;

@Epic("Surgelevel")
@Feature("Автоматический расчет surge")
public class SurgeTest extends RestBase {

    private final String STORE_ID = UUID.randomUUID().toString();
    private final String FIRST_STORE_ID = UUID.randomUUID().toString();
    private final String SECOND_STORE_ID = UUID.randomUUID().toString();
    private final String THIRD_STORE_ID = UUID.randomUUID().toString();
    private final String STORE_ID_NOT_ON_DEMAND = UUID.randomUUID().toString();
    private final String STORE_ID_DISABLED = UUID.randomUUID().toString();
    private final String FORMULA_ID = "10000000-1000-1000-1000-100000000000";
    private final String SHIPMENT_UUID = UUID.randomUUID().toString();
    private final String ORDER_UUID = UUID.randomUUID().toString();
    private final String CANDIDATE_UUID = UUID.randomUUID().toString();
    private final String CANDIDATE_UUID_DELIVERY_AREA = UUID.randomUUID().toString();
    private final String CANDIDATE_UUID_WORKFLOW = UUID.randomUUID().toString();
    private final String CANDIDATE_UUID_FAKE_GPS = UUID.randomUUID().toString();
    private final String CANDIDATE_UUID_WITH_SHIFT = UUID.randomUUID().toString();
    private final int FIRST_DELIVERY_AREA_ID = nextInt(100000, 150000);
    private final int SECOND_DELIVERY_AREA_ID = FIRST_DELIVERY_AREA_ID + 1;
    private final int FIRST_WORKFLOW_ID = FIRST_DELIVERY_AREA_ID;
    private final int SECOND_WORKFLOW_ID = SECOND_DELIVERY_AREA_ID;
    private final int SHORT_TIMEOUT = 3;
    private final int LONG_TIMEOUT = 10;
    private float currentSurgeLevel = 100; // см. формулу, которую используем для тестов
    private float currentSurgeLevelFirstStore = currentSurgeLevel;
    private float currentSurgeLevelSecondStore = currentSurgeLevel;
    private float currentSurgeLevelThirdStore = currentSurgeLevel;
    private int currentDemandAmount;
    private int currentSupplyAmount;
    private float pastSurgeLevel;
    AtomicInteger surgeCalculationCount = new AtomicInteger(); // костыль для понимания какой pastSurgeLevel использовать
    private int surgeEventOutdate = 10;
    private double distFirstSecond;
    private double distFirstThird;
    private Surgelevel.Location storeLocation;
    private Surgelevel.Location firstStoreLocation;
    private Surgelevel.Location secondStoreLocation;
    private Surgelevel.Location thirdStoreLocation;
    private Surgelevel.Location workflowPlanEndLocation;

    @BeforeClass(alwaysRun = true)
    public void preConditions() {

        //пока прибил гвоздями
        //стор1 видит оба
        //стор2 видит только стор1 (примерно 1,3КМ от стор1)
        //стор3 видит только стор1 (примерно 2,9КМ  стор1)
        storeLocation = Surgelevel.Location.newBuilder().setLat(26.9993f).setLon(14.4002f).build();
        firstStoreLocation = Surgelevel.Location.newBuilder().setLat(27.034817f).setLon(14.426422f).build();
        secondStoreLocation = Surgelevel.Location.newBuilder().setLat(27.046055f).setLon(14.421101f).build();
        thirdStoreLocation = Surgelevel.Location.newBuilder().setLat(27.008896f).setLon(14.431057f).build();
        workflowPlanEndLocation = Surgelevel.Location.newBuilder().setLat(27.006144f).setLon(14.435349f).build();
        distFirstSecond = distance(firstStoreLocation.getLat(), firstStoreLocation.getLon(), secondStoreLocation.getLat(), secondStoreLocation.getLon(), 'K') * 1000;
        distFirstThird = distance(firstStoreLocation.getLat(), firstStoreLocation.getLon(), thirdStoreLocation.getLat(), thirdStoreLocation.getLon(), 'K') * 1000;

        checkFormula(FORMULA_ID);
        addStore(STORE_ID, UUID.randomUUID().toString(), false, true, storeLocation.getLat(), storeLocation.getLon(), FORMULA_ID, 1000, FIRST_DELIVERY_AREA_ID, 1000);
        addStore(STORE_ID_NOT_ON_DEMAND, UUID.randomUUID().toString(), false, false, storeLocation.getLat(), storeLocation.getLon(), FORMULA_ID, 1000, FIRST_DELIVERY_AREA_ID, null);
        addStore(STORE_ID_DISABLED, UUID.randomUUID().toString(), true, true, storeLocation.getLat(), storeLocation.getLon(), FORMULA_ID, 1000, FIRST_DELIVERY_AREA_ID, null);
        addStore(FIRST_STORE_ID, UUID.randomUUID().toString(), null, true, firstStoreLocation.getLat(), firstStoreLocation.getLon(), FORMULA_ID, 1000, SECOND_DELIVERY_AREA_ID, null);
        addStore(SECOND_STORE_ID, UUID.randomUUID().toString(), null, true, secondStoreLocation.getLat(), secondStoreLocation.getLon(), FORMULA_ID, 1000, SECOND_DELIVERY_AREA_ID, null);
        addStore(THIRD_STORE_ID, UUID.randomUUID().toString(), null, true, thirdStoreLocation.getLat(), thirdStoreLocation.getLon(), FORMULA_ID, 1000, 0, null);

        final var surgeEventOutdateFromK8s = SurgeLevelHelper.getInstance().getSurgeEventOutdate();
        if (Objects.nonNull(surgeEventOutdateFromK8s)) {
            surgeEventOutdate = surgeEventOutdateFromK8s;
        }
    }

    @AfterMethod(alwaysRun = true)
    public void waitForOutdate() {
        ThreadUtil.simplyAwait(surgeEventOutdate - SHORT_TIMEOUT);
    }

    @TmsLink("24")
    @Story("Surge Calculation")
    @Test(description = "Отсутствие расчета surgelevel для не ON_DEMAND магазинов",
            groups = "ondemand-surgelevel",
            dependsOnMethods = "surgeProduceEventOrderOnDemand")
    public void surgeNotOnDemandStore() {
        Allure.step("Проверка отсутствия расчета surgelevel", () -> {
            Optional<StoreEntity> store = StoreDao.INSTANCE.findById(STORE_ID_NOT_ON_DEMAND);
            assertEquals(STORE_ID_NOT_ON_DEMAND, store.get().getId());
            assertNull(store.get().getActualResultId());
        });
    }

    @TmsLink("36")
    @Story("Surge Calculation")
    @Test(description = "Отсутствие расчета surgelevel при disabled=true в конфиге",
            groups = "ondemand-surgelevel",
            dependsOnMethods = "surgeProduceEventOrderOnDemand")
    public void surgeDisabledStore() {
        Allure.step("Проверка отсутствия расчета surgelevel", () -> {
            Optional<StoreEntity> store = StoreDao.INSTANCE.findById(STORE_ID_DISABLED);
            assertEquals(STORE_ID_DISABLED, store.get().getId());
            assertNull(store.get().getActualResultId());
        });
    }

    @TmsLinks({@TmsLink("14"), @TmsLink("23"), @TmsLink("25"), @TmsLink("163"), @TmsLink("32")})
    @Story("Demand")
    @Test(description = "Добавление demand при получении события с новым ON_DEMAND заказом",
            groups = "ondemand-surgelevel")
    public void surgeProduceEventOrderOnDemand() {
        publishEventOrderStatus(ORDER_UUID, STORE_ID, OrderStatus.NEW, ShipmentType.ON_DEMAND, SHIPMENT_UUID, LONG_TIMEOUT);

        pastSurgeLevel = currentSurgeLevel;
        currentSurgeLevel++;
        currentDemandAmount++;
        surgeCalculationCount.addAndGet(1);

        List<Surgelevelevent.SurgeEvent> surgeLevels = kafka.waitDataInKafkaTopicSurgeLevel(STORE_ID, 5L);
        checkSurgeLevelProduce(surgeLevels, surgeLevels.size(), STORE_ID, surgeCalculationCount.get() > 1 ? pastSurgeLevel : 0, currentSurgeLevel, currentDemandAmount, currentSupplyAmount, Method.ACTUAL);
    }

    @TmsLink("40")
    @Story("Demand")
    @Test(description = "Отсутствие изменения surgelevel при получении ранее полученного ON_DEMAND заказа",
            groups = "ondemand-surgelevel",
            dependsOnMethods = "surgeProduceEventOrderOnDemand")
    public void surgeProduceEventOrderRepeat() {
        publishEventOrderStatus(ORDER_UUID, STORE_ID, OrderStatus.ROUTING, ShipmentType.ON_DEMAND, SHIPMENT_UUID, LONG_TIMEOUT);

        Allure.step("Проверка отсутствия изменения surgelevel", () -> compareTwoObjects(currentSurgeLevel, ResultDao.INSTANCE.findResult(STORE_ID).getSurgeLevel().floatValue()));
    }

    @TmsLink("16")
    @Story("Demand")
    @Test(description = "Отсутствие добавления demand при получении события с новым не ON_DEMAND заказом",
            groups = "ondemand-surgelevel")
    public void surgeProduceEventOrderNotOnDemand() {
        String shipmentUuid = UUID.randomUUID().toString();
        publishEventOrderStatus(UUID.randomUUID().toString(), STORE_ID, OrderStatus.NEW, ShipmentType.PLANNED, shipmentUuid, LONG_TIMEOUT);

        Allure.step("Проверка отсутствия добавления demand", () -> assertNull(DemandDao.INSTANCE.findDemand(STORE_ID, shipmentUuid), "Заказ добавился в demand"));
    }

    @TmsLinks({@TmsLink("133"), @TmsLink("135"), @TmsLink("43"), @TmsLink("165"), @TmsLink("44")})
    @Story("Demand")
    @Test(description = "Добавление demand для нескольких магазинов при получении события с новым ON_DEMAND заказом",
            groups = "ondemand-surgelevel")
    public void surgeProduceEventOrderMultipleStores() {
        String shipmentUuid = UUID.randomUUID().toString();
        publishEventOrderStatus(UUID.randomUUID().toString(), FIRST_STORE_ID, OrderStatus.NEW, ShipmentType.ON_DEMAND, shipmentUuid, LONG_TIMEOUT);

        currentSurgeLevelFirstStore++;
        currentSurgeLevelSecondStore++;
        currentSurgeLevelThirdStore++;

        DemandEntity firstStoreDemand = DemandDao.INSTANCE.findDemand(FIRST_STORE_ID, shipmentUuid);
        DemandEntity secondStoreDemand = DemandDao.INSTANCE.findDemand(SECOND_STORE_ID, shipmentUuid);
        DemandEntity thirdStoreDemand = DemandDao.INSTANCE.findDemand(THIRD_STORE_ID, shipmentUuid);

        Allure.step("Проверка добавления demand и расчета surgelevel для нескольких магазинов", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertNotNull(firstStoreDemand, "Заказ не добавился в demand");
            softAssert.assertNotNull(secondStoreDemand, "Заказ не добавился в demand");
            softAssert.assertNotNull(thirdStoreDemand, "Заказ не добавился в demand");
            softAssert.assertEquals(ResultDao.INSTANCE.findResult(FIRST_STORE_ID).getSurgeLevel().floatValue(), currentSurgeLevelFirstStore, "Не верный surgelevel1");
            softAssert.assertEquals(ResultDao.INSTANCE.findResult(SECOND_STORE_ID).getSurgeLevel().floatValue(), currentSurgeLevelSecondStore, "Не верный surgelevel2");
            softAssert.assertEquals(ResultDao.INSTANCE.findResult(THIRD_STORE_ID).getSurgeLevel().floatValue(), currentSurgeLevelThirdStore, "Не верный surgelevel3");
            softAssert.assertAll();
        });
        Allure.step("Проверка корректной записи расстояния для inner и outer магазинов", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(firstStoreDemand.getDistance(), 0d, 5d, "Не верное расстояние для inner магазина");
            softAssert.assertEquals(secondStoreDemand.getDistance(), distFirstSecond, 10d, "Не верное расстояние для outer магазина");
            softAssert.assertEquals(thirdStoreDemand.getDistance(), distFirstThird, 10d, "Не верное расстояние для outer магазина");
            softAssert.assertAll();
        });
    }

    @TmsLink("141")
    @Story("Demand")
    @Test(description = "Отсутствие добавления demand для outer-магазинов при distance > STORE_RADIUS",
            groups = "ondemand-surgelevel")
    public void surgeProduceEventOrderGreaterStoreRadius() {
        String shipmentUuid = UUID.randomUUID().toString();
        publishEventOrderStatus(UUID.randomUUID().toString(), SECOND_STORE_ID, OrderStatus.NEW, ShipmentType.ON_DEMAND, shipmentUuid, LONG_TIMEOUT);

        currentSurgeLevelFirstStore++;
        currentSurgeLevelSecondStore++;

        Allure.step("Проверка добавления demand и расчета surgelevel для нескольких магазинов", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertNotNull(DemandDao.INSTANCE.findDemand(FIRST_STORE_ID, shipmentUuid), "Заказ не добавился в demand");
            softAssert.assertNotNull(DemandDao.INSTANCE.findDemand(SECOND_STORE_ID, shipmentUuid), "Заказ не добавился в demand");
            softAssert.assertNull(DemandDao.INSTANCE.findDemand(THIRD_STORE_ID, shipmentUuid), "Заказ добавился в demand при distance > STORE_RADIUS");
            softAssert.assertEquals(ResultDao.INSTANCE.findResult(FIRST_STORE_ID).getSurgeLevel().floatValue(), currentSurgeLevelFirstStore, "Не верный surgelevel1");
            softAssert.assertEquals(ResultDao.INSTANCE.findResult(SECOND_STORE_ID).getSurgeLevel().floatValue(), currentSurgeLevelSecondStore, "Не верный surgelevel2");
            softAssert.assertEquals(ResultDao.INSTANCE.findResult(THIRD_STORE_ID).getSurgeLevel().floatValue(), currentSurgeLevelThirdStore, "Не верный surgelevel3");
            softAssert.assertAll();
        });
    }

    @TmsLink("126")
    @Story("Supply")
    @Test(description = "Отсутствие добавления supply при получении не on-demand кандидата",
            groups = "ondemand-surgelevel")
    public void surgeProduceEventCandidateNotOnDemand() {
        String candidateUuid = UUID.randomUUID().toString();
        publishEventCandidateStatus(candidateUuid, CandidateChanges.Role.UNIVERSAL, CandidateChanges.Status.FREE, 0, FIRST_DELIVERY_AREA_ID, true, STORE_ID, YANDEX.getName(), SHORT_TIMEOUT);
        publishEventLocation(candidateUuid, storeLocation.getLat(), storeLocation.getLon(), false, LONG_TIMEOUT);

        Allure.step("Проверка отсутствия добавления supply", () -> assertNull(SupplyDao.INSTANCE.findSupply(STORE_ID, candidateUuid), "Кандидат добавился в supply"));
    }

    @TmsLink("127")
    @Story("Supply")
    @Test(description = "Отсутствие удаления supply при получении события с UNCHANGED кандидатом",
            groups = "ondemand-surgelevel",
            dependsOnMethods = "surgeProduceEventCandidateDeliveryArea")
    public void surgeProduceEventCandidateUnchanged() {
        publishEventCandidateStatus(CANDIDATE_UUID_DELIVERY_AREA, CandidateChanges.Role.UNIVERSAL, CandidateChanges.Status.BUSY, 0, FIRST_DELIVERY_AREA_ID, true, STORE_ID, DISPATCH.getName(), SHORT_TIMEOUT);

        currentSurgeLevel++;

        publishEventCandidateStatus(CANDIDATE_UUID_DELIVERY_AREA, CandidateChanges.Role.UNIVERSAL, CandidateChanges.Status.UNCHANGED, 0, FIRST_DELIVERY_AREA_ID, true, STORE_ID, DISPATCH.getName(), LONG_TIMEOUT);

        Allure.step("Проверка отсутствия удаления supply", () -> assertNotNull(SupplyDao.INSTANCE.findSupply(STORE_ID, CANDIDATE_UUID_DELIVERY_AREA), "Кандидат удалился из supply"));
    }

    @TmsLink("138")
    @Story("Supply")
    @Test(description = "Отсутствие добавления supply при пустых координатах кандидата",
            groups = "ondemand-surgelevel")
    public void surgeProduceEventCandidateNoCoordinates() {
        String candidateUuid = UUID.randomUUID().toString();
        publishEventCandidateStatus(candidateUuid, CandidateChanges.Role.UNIVERSAL, CandidateChanges.Status.FREE, 0, FIRST_DELIVERY_AREA_ID, true, STORE_ID, DISPATCH.getName(), LONG_TIMEOUT);

        Allure.step("Проверка отсутствия добавления supply", () -> assertNull(SupplyDao.INSTANCE.findSupply(STORE_ID, candidateUuid), "Кандидат добавился в supply"));
    }

    @TmsLink("144")
    @Story("Supply")
    @Test(description = "Отсутствие добавления supply с фейковыми координатами кандидата",
            groups = "ondemand-surgelevel")
    public void surgeProduceEventCandidateFakeGps() {
        publishEventCandidateStatus(CANDIDATE_UUID_FAKE_GPS, CandidateChanges.Role.UNIVERSAL, CandidateChanges.Status.FREE, 0, 0, false, STORE_ID, DISPATCH.getName(), SHORT_TIMEOUT);
        publishEventLocation(CANDIDATE_UUID_FAKE_GPS, storeLocation.getLat(), storeLocation.getLon(), true, LONG_TIMEOUT);

        Allure.step("Проверка отсутствия supply", () -> assertNull(SupplyDao.INSTANCE.findSupply(STORE_ID, CANDIDATE_UUID_FAKE_GPS), "Кандидат добавился в supply"));
    }

    @TmsLink("145")
    @Story("Supply")
    @Test(description = "Добавление supply при получении настоящих координат кандидата, когда до этого были фейковые",
            groups = "ondemand-surgelevel",
            dependsOnMethods = "surgeProduceEventCandidateFakeGps")
    public void surgeProduceEventCandidateNoLongerFakeGps() {
        publishEventLocation(CANDIDATE_UUID_FAKE_GPS, storeLocation.getLat(), storeLocation.getLon(), false, LONG_TIMEOUT);

        currentSurgeLevel--;
        currentSupplyAmount++;

        Allure.step("Проверка добавления в supply", () -> {
            assertNotNull(SupplyDao.INSTANCE.findSupply(STORE_ID, CANDIDATE_UUID_FAKE_GPS), "Кандидат не добавился в supply");
            compareTwoObjects(currentSurgeLevel, ResultDao.INSTANCE.findResult(STORE_ID).getSurgeLevel().floatValue());
        });
    }

    @TmsLink("143")
    @Story("Supply")
    @Test(description = "Удаление всего supply кандидата при получении фейковых координат",
            groups = "ondemand-surgelevel",
            dependsOnMethods = "surgeProduceEventCandidateNoLongerFakeGps")
    public void surgeProduceEventCandidateNewFakeGps() {
        publishEventLocation(CANDIDATE_UUID_FAKE_GPS, storeLocation.getLat(), storeLocation.getLon(), true, LONG_TIMEOUT);

        currentSurgeLevel++;
        currentSupplyAmount--;

        Allure.step("Проверка удаления supply", () -> {
            assertNull(SupplyDao.INSTANCE.findSupply(STORE_ID, CANDIDATE_UUID_FAKE_GPS), "Кандидат не удалился в supply");
            compareTwoObjects(currentSurgeLevel, ResultDao.INSTANCE.findResult(STORE_ID).getSurgeLevel().floatValue());
        });
    }

    @TmsLinks({@TmsLink("123"), @TmsLink("125")})
    @Story("Delivery Area Supply")
    @Test(description = "Добавление supply при получении кандидата по delivery_area",
            groups = "ondemand-surgelevel")
    public void surgeProduceEventCandidateDeliveryArea() {
        publishEventCandidateStatus(CANDIDATE_UUID_DELIVERY_AREA, CandidateChanges.Role.UNIVERSAL, CandidateChanges.Status.FREE, 0, FIRST_DELIVERY_AREA_ID, true, STORE_ID, DISPATCH.getName(), SHORT_TIMEOUT);
        publishEventLocation(CANDIDATE_UUID_DELIVERY_AREA, 15.0f, 15.0f, false, LONG_TIMEOUT);

        pastSurgeLevel = currentSurgeLevel;
        currentSurgeLevel--;
        currentSupplyAmount++;
        surgeCalculationCount.addAndGet(1);

        List<Surgelevelevent.SurgeEvent> surgeLevels = kafka.waitDataInKafkaTopicSurgeLevel(STORE_ID, 5L);
        checkSurgeLevelProduce(surgeLevels, surgeLevels.size(), STORE_ID, surgeCalculationCount.get() > 1 ? pastSurgeLevel : 0, currentSurgeLevel, currentDemandAmount, currentSupplyAmount, Method.ACTUAL);
    }

    @TmsLink("137")
    @Story("Delivery Area Supply")
    @Test(description = "Добавление supply для нескольких магазинов при получении кандидата по delivery_area",
            groups = "ondemand-surgelevel")
    public void surgeProduceEventCandidateMultipleStoresDeliveryArea() {
        String candidateUuid = UUID.randomUUID().toString();
        publishEventCandidateStatus(candidateUuid, CandidateChanges.Role.UNIVERSAL, CandidateChanges.Status.FREE, 0, SECOND_DELIVERY_AREA_ID, true, FIRST_STORE_ID, DISPATCH.getName(), SHORT_TIMEOUT);
        publishEventLocation(candidateUuid, 15.0f, 15.0f, false, LONG_TIMEOUT);

        currentSurgeLevelFirstStore--;
        currentSurgeLevelSecondStore--;

        Allure.step("Проверка добавления supply и расчета surgelevel для нескольких магазинов", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertNotNull(SupplyDao.INSTANCE.findSupply(FIRST_STORE_ID, candidateUuid), "Кандидат не добавился в supply");
            softAssert.assertNotNull(SupplyDao.INSTANCE.findSupply(SECOND_STORE_ID, candidateUuid), "Кандидат не добавился в supply");
            softAssert.assertNull(SupplyDao.INSTANCE.findSupply(THIRD_STORE_ID, candidateUuid), "Кандидат добавился в supply");
            softAssert.assertEquals(ResultDao.INSTANCE.findResult(FIRST_STORE_ID).getSurgeLevel().floatValue(), currentSurgeLevelFirstStore, "Не верный surgelevel");
            softAssert.assertEquals(ResultDao.INSTANCE.findResult(SECOND_STORE_ID).getSurgeLevel().floatValue(), currentSurgeLevelSecondStore, "Не верный surgelevel");
            softAssert.assertEquals(ResultDao.INSTANCE.findResult(THIRD_STORE_ID).getSurgeLevel().floatValue(), currentSurgeLevelThirdStore, "Не верный surgelevel");
            softAssert.assertAll();
        });
    }

    @TmsLinks({@TmsLink("20"), @TmsLink("166")})
    @Story("Radius Supply")
    @Test(description = "Добавление supply при получении кандидата по координатам в близи от магазина",
            groups = "ondemand-surgelevel")
    public void surgeProduceEventCandidateWithLocation() {
        publishEventCandidateStatus(CANDIDATE_UUID, CandidateChanges.Role.UNIVERSAL, CandidateChanges.Status.FREE, 0, 0, false, STORE_ID, DISPATCH.getName(), SHORT_TIMEOUT);
        publishEventLocation(CANDIDATE_UUID, storeLocation.getLat(), storeLocation.getLon(), false, LONG_TIMEOUT);

        pastSurgeLevel = currentSurgeLevel;
        currentSurgeLevel--;
        currentSupplyAmount++;
        surgeCalculationCount.addAndGet(1);

        List<Surgelevelevent.SurgeEvent> surgeLevels = kafka.waitDataInKafkaTopicSurgeLevel(STORE_ID, 5L);
        checkSurgeLevelProduce(surgeLevels, surgeLevels.size(), STORE_ID, surgeCalculationCount.get() > 1 ? pastSurgeLevel : 0, currentSurgeLevel, currentDemandAmount, currentSupplyAmount, Method.ACTUAL);
    }

    @TmsLink("140")
    @Story("Radius Supply")
    @Test(description = "Отсутствие удаления supply при уходе кандидата по координатам недостаточно далеко от магазина",
            groups = "ondemand-surgelevel",
            dependsOnMethods = "surgeProduceEventCandidateWithLocation")
    public void surgeProduceEventCandidateWithLocationWentNotFar() {
        publishEventLocation(CANDIDATE_UUID, storeLocation.getLat() - 0.001f, storeLocation.getLon() - 0.001f, false, LONG_TIMEOUT);

        Allure.step("Проверка supply", () -> assertNotNull(SupplyDao.INSTANCE.findSupply(STORE_ID, CANDIDATE_UUID), "Кандидат удалился из supply"));
    }

    @TmsLink("167")
    @Story("Surge Calculation")
    @Test(description = "Отсутствие расчета surgelevel с радиусом в конфиге не null",
            groups = "ondemand-surgelevel")
    public void surgeDistanceFromConfigTooFar() {
        String candidateUuid = UUID.randomUUID().toString();
        publishEventCandidateStatus(candidateUuid, CandidateChanges.Role.UNIVERSAL, CandidateChanges.Status.FREE, 0, 0, false, STORE_ID, DISPATCH.getName(), SHORT_TIMEOUT);
        publishEventLocation(candidateUuid, storeLocation.getLat() - 0.01f, storeLocation.getLon() - 0.01f, false, LONG_TIMEOUT);

        Allure.step("Проверка отсутствия добавления supply", () -> assertNull(SupplyDao.INSTANCE.findSupply(STORE_ID, candidateUuid), "Кандидат добавился в supply"));
    }

    @TmsLink("167")
    @Story("Supply")
    @Test(description = "Обновление кандидата и вызов расчета surgelevel при получении новых координат не смотря на не достижение CANDIDATE_MOVEMENT_THRESHOLD",
            groups = "ondemand-surgelevel",
            dependsOnMethods = "surgeProduceEventCandidateWithLocationWentNotFar")
    public void surgeDistanceForceUpdate() {
        CandidateDao.INSTANCE.updateCandidate(CANDIDATE_UUID, true, getZonedUTCDateMinusMinutes(10));
        publishEventLocation(CANDIDATE_UUID, storeLocation.getLat() - 0.001f, storeLocation.getLon() - 0.001f, false, LONG_TIMEOUT);

        pastSurgeLevel = currentSurgeLevel;
        currentSurgeLevel++;

        List<Surgelevelevent.SurgeEvent> surgeLevels = kafka.waitDataInKafkaTopicSurgeLevel(STORE_ID, 5L);
        checkSurgeLevelProduce(surgeLevels, surgeLevels.size(), STORE_ID, pastSurgeLevel, currentSurgeLevel, currentDemandAmount, currentSupplyAmount, Method.ACTUAL);
    }

    @TmsLink("49")
    @Story("Radius Supply")
    @Test(description = "Удаление supply при уходе кандидата по координатам вдаль от магазина",
            groups = "ondemand-surgelevel",
            dependsOnMethods = "surgeDistanceForceUpdate")
    public void surgeProduceEventCandidateWithLocationWentTooFar() {
        publishEventLocation(CANDIDATE_UUID, 15.0f, 15.0f, false, LONG_TIMEOUT);

        currentSupplyAmount--;

        Allure.step("Проверка удаления supply", () -> {
            assertNull(SupplyDao.INSTANCE.findSupply(STORE_ID, CANDIDATE_UUID), "Кандидат не удалился из supply");
            compareTwoObjects(currentSurgeLevel, ResultDao.INSTANCE.findResult(STORE_ID).getSurgeLevel().floatValue());
        });
    }

    @TmsLink("22")
    @Story("Radius Supply")
    @Test(description = "Отсутствие добавления supply при получении кандидата по координатам не в близи от магазина",
            groups = "ondemand-surgelevel")
    public void surgeProduceEventCandidateWithLocationFar() {
        String candidateUuid = UUID.randomUUID().toString();
        publishEventCandidateStatus(candidateUuid, CandidateChanges.Role.UNIVERSAL, CandidateChanges.Status.FREE, 0, 0, false, STORE_ID, DISPATCH.getName(), SHORT_TIMEOUT);
        publishEventLocation(candidateUuid, 15.0f, 15.0f, false, LONG_TIMEOUT);

        Allure.step("Проверка отсутствия добавления supply", () -> assertNull(SupplyDao.INSTANCE.findSupply(STORE_ID, candidateUuid), "Кандидат добавился в supply"));
    }


    @TmsLinks({@TmsLink("41"), @TmsLink("136"), @TmsLink("45")})
    @Story("Radius Supply")
    @Test(description = "Добавление supply для нескольких магазинов при получении кандидата в близи от этих магазинов",
            groups = "ondemand-surgelevel")
    public void surgeProduceEventCandidateLocationMultipleStores() {
        String candidateUuid = UUID.randomUUID().toString();
        publishEventCandidateStatus(candidateUuid, CandidateChanges.Role.UNIVERSAL, CandidateChanges.Status.FREE, 0, 0, false, FIRST_STORE_ID, DISPATCH.getName(), SHORT_TIMEOUT);
        publishEventLocation(candidateUuid, firstStoreLocation.getLat(), firstStoreLocation.getLon(), false, LONG_TIMEOUT);

        currentSurgeLevelFirstStore--;
        currentSurgeLevelSecondStore--;
        currentSurgeLevelThirdStore--;

        SupplyEntity firstStoreSupply = SupplyDao.INSTANCE.findSupply(FIRST_STORE_ID, candidateUuid);
        SupplyEntity secondStoreSupply = SupplyDao.INSTANCE.findSupply(SECOND_STORE_ID, candidateUuid);
        SupplyEntity thirdStoreSupply = SupplyDao.INSTANCE.findSupply(THIRD_STORE_ID, candidateUuid);

        Allure.step("Проверка добавления supply и расчета surgelevel для нескольких магазинов", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertNotNull(firstStoreSupply, "Кандидат не добавился в supply");
            softAssert.assertNotNull(secondStoreSupply, "Кандидат не добавился в supply");
            softAssert.assertNotNull(thirdStoreSupply, "Кандидат не добавился в supply");
            softAssert.assertEquals(ResultDao.INSTANCE.findResult(FIRST_STORE_ID).getSurgeLevel().floatValue(), currentSurgeLevelFirstStore, "Не верный surgelevel");
            softAssert.assertEquals(ResultDao.INSTANCE.findResult(SECOND_STORE_ID).getSurgeLevel().floatValue(), currentSurgeLevelSecondStore, "Не верный surgelevel");
            softAssert.assertEquals(ResultDao.INSTANCE.findResult(THIRD_STORE_ID).getSurgeLevel().floatValue(), currentSurgeLevelThirdStore, "Не верный surgelevel");
            softAssert.assertAll();
        });
        Allure.step("Проверка корректной записи расстояния для inner и outer магазинов", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(firstStoreSupply.getDistance(), 0d, 5d, "Не верное расстояние для inner магазина");
            softAssert.assertEquals(secondStoreSupply.getDistance(), distFirstSecond, 10d, "Не верное расстояние для outer магазина");
            softAssert.assertEquals(thirdStoreSupply.getDistance(), distFirstThird, 10d, "Не верное расстояние для outer магазина");
            softAssert.assertAll();
        });
    }

    @TmsLink("157")
    @Story("Workflow")
    @Test(description = "Проверка supply при получении in_progress МЛ",
            groups = "ondemand-surgelevel")
    public void surgeProduceEventCandidateWithWorkflow() {
        publishEventCandidateStatus(CANDIDATE_UUID_WORKFLOW, CandidateChanges.Role.UNIVERSAL, CandidateChanges.Status.FREE, 0, 0, false, SECOND_STORE_ID, DISPATCH.getName(), SHORT_TIMEOUT);
        publishEventLocation(CANDIDATE_UUID_WORKFLOW, secondStoreLocation.getLat(), secondStoreLocation.getLon(), false, SHORT_TIMEOUT);
        publishEventCandidateStatusWithWorkflow(CANDIDATE_UUID_WORKFLOW, CandidateChanges.Role.UNIVERSAL, CandidateChanges.Status.BUSY, 0, 0, false, SECOND_STORE_ID, DISPATCH.getName(), FIRST_WORKFLOW_ID, WorkflowStatus.IN_PROGRESS, getTimestamp(), workflowPlanEndLocation.getLat(), workflowPlanEndLocation.getLon(), LONG_TIMEOUT);

        Allure.step("Проверка сохранения supply", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertNotNull(SupplyDao.INSTANCE.findSupply(FIRST_STORE_ID, CANDIDATE_UUID_WORKFLOW), "Пропал supply");
            softAssert.assertNotNull(SupplyDao.INSTANCE.findSupply(SECOND_STORE_ID, CANDIDATE_UUID_WORKFLOW), "Пропал supply");
            softAssert.assertAll();
        });
    }

    @TmsLinks({@TmsLink("158"), @TmsLink("159")})
    @Story("Workflow")
    @Test(description = "Удаление supply при уходе от изначальной точки и отсутствие добавления к магазинам по пути, если далеко от точки прибытия in_progress МЛ",
            groups = "ondemand-surgelevel",
            dependsOnMethods = "surgeProduceEventCandidateWithWorkflow")
    public void surgeProduceEventCandidateWithWorkflowStartMoving() {
        publishEventLocation(CANDIDATE_UUID_WORKFLOW, storeLocation.getLat(), storeLocation.getLon(), false, LONG_TIMEOUT);

        Allure.step("Проверка удаления supply", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertNull(SupplyDao.INSTANCE.findSupply(FIRST_STORE_ID, CANDIDATE_UUID_WORKFLOW), "Supply не удалился");
            softAssert.assertNull(SupplyDao.INSTANCE.findSupply(SECOND_STORE_ID, CANDIDATE_UUID_WORKFLOW), "Supply не удалился");
            softAssert.assertNull(SupplyDao.INSTANCE.findSupply(STORE_ID, CANDIDATE_UUID_WORKFLOW), "Supply добавился для магазина по пути");
            softAssert.assertAll();
        });
    }

    @TmsLink("160")
    @Story("Workflow")
    @Test(description = "Добавление supply при вхождении в радиус к точке прибытия in_progress МЛ",
            groups = "ondemand-surgelevel",
            dependsOnMethods = "surgeProduceEventCandidateWithWorkflowStartMoving")
    public void surgeProduceEventCandidateWithWorkflowFinishing() {
        publishEventLocation(CANDIDATE_UUID_WORKFLOW, thirdStoreLocation.getLat(), thirdStoreLocation.getLon(), false, LONG_TIMEOUT);

        Allure.step("Проверка добавления supply в магазины в store_radius от точки прибытия", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertNotNull(SupplyDao.INSTANCE.findSupply(THIRD_STORE_ID, CANDIDATE_UUID_WORKFLOW), "Supply не добавился");
            softAssert.assertNull(SupplyDao.INSTANCE.findSupply(FIRST_STORE_ID, CANDIDATE_UUID_WORKFLOW), "Supply добавился в магазин > store_radius от точки прибытия");
            softAssert.assertAll();
        });
    }

    @TmsLink("162")
    @Story("Workflow")
    @Test(description = "Отсутствие влияния in_progress МЛ на кандидата с delivery_area",
            groups = "ondemand-surgelevel")
    public void surgeProduceEventCandidateWithWorkflowAndDeliveryArea() {
        String candidateUuid = UUID.randomUUID().toString();
        publishEventCandidateStatus(candidateUuid, CandidateChanges.Role.UNIVERSAL, CandidateChanges.Status.FREE, 0, SECOND_DELIVERY_AREA_ID, true, SECOND_STORE_ID, DISPATCH.getName(), SHORT_TIMEOUT);
        publishEventLocation(candidateUuid, secondStoreLocation.getLat(), secondStoreLocation.getLon(), false, SHORT_TIMEOUT);
        publishEventCandidateStatusWithWorkflow(candidateUuid, CandidateChanges.Role.UNIVERSAL, CandidateChanges.Status.BUSY, 0, SECOND_DELIVERY_AREA_ID, true, SECOND_STORE_ID, DISPATCH.getName(), SECOND_WORKFLOW_ID, WorkflowStatus.IN_PROGRESS, getTimestamp(), workflowPlanEndLocation.getLat(), workflowPlanEndLocation.getLon(), SHORT_TIMEOUT);
        publishEventLocation(candidateUuid, workflowPlanEndLocation.getLat(), workflowPlanEndLocation.getLon(), false, LONG_TIMEOUT);

        Allure.step("Проверка отсутствия применения логики workflow для deliveryarea кандидата", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertNotNull(SupplyDao.INSTANCE.findSupply(FIRST_STORE_ID, candidateUuid), "Пропал supply от deliveryarea");
            softAssert.assertNotNull(SupplyDao.INSTANCE.findSupply(SECOND_STORE_ID, candidateUuid), "Пропал supply от deliveryarea");
            softAssert.assertNull(SupplyDao.INSTANCE.findSupply(THIRD_STORE_ID, candidateUuid), "Supply добавился по workflow");
            softAssert.assertAll();
        });
    }

    @TmsLink("161")
    @Story("Workflow")
    @Test(description = "Отсутствие влияния не in_progress МЛ на кандидата",
            groups = "ondemand-surgelevel")
    public void surgeProduceEventCandidateWithQueuedWorkflow() {
        String candidateUuid = UUID.randomUUID().toString();
        publishEventCandidateStatus(candidateUuid, CandidateChanges.Role.UNIVERSAL, CandidateChanges.Status.FREE, 0, 0, false, SECOND_STORE_ID, DISPATCH.getName(), SHORT_TIMEOUT);
        publishEventLocation(candidateUuid, secondStoreLocation.getLat(), secondStoreLocation.getLon(), false, SHORT_TIMEOUT);
        publishEventCandidateStatusWithWorkflow(candidateUuid, CandidateChanges.Role.UNIVERSAL, CandidateChanges.Status.BUSY, 0, 0, false, SECOND_STORE_ID, DISPATCH.getName(), SECOND_WORKFLOW_ID + 1, WorkflowStatus.QUEUED, getTimestamp(), workflowPlanEndLocation.getLat(), workflowPlanEndLocation.getLon(), SHORT_TIMEOUT);
        publishEventLocation(candidateUuid, thirdStoreLocation.getLat(), thirdStoreLocation.getLon(), false, LONG_TIMEOUT);

        Allure.step("Проверка добавления supply только по radius", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertNotNull(SupplyDao.INSTANCE.findSupply(FIRST_STORE_ID, candidateUuid), "Удалился supply по radius");
            softAssert.assertNull(SupplyDao.INSTANCE.findSupply(SECOND_STORE_ID, candidateUuid), "Не удалился supply по radius");
            softAssert.assertNotNull(SupplyDao.INSTANCE.findSupply(THIRD_STORE_ID, candidateUuid), "Не добавился supply по radius");
            softAssert.assertAll();
        });
    }

    @TmsLinks({@TmsLink("188"), @TmsLink("193")})
    @Story("Demand")
    @Test(description = "Добавление supply onshift кандидату",
            groups = "ondemand-surgelevel")
    public void surgeProduceEventCandidateWithShift() {
        publishEventCandidateStatus(CANDIDATE_UUID_WITH_SHIFT, CandidateChanges.Role.UNIVERSAL, CandidateChanges.Status.FREE, 0, 0, false, SECOND_STORE_ID, DISPATCH.getName(), SHORT_TIMEOUT);
        publishEventLocation(CANDIDATE_UUID_WITH_SHIFT, secondStoreLocation.getLat(), secondStoreLocation.getLon(), false, LONG_TIMEOUT);

        currentSurgeLevelFirstStore--;
        currentSurgeLevelSecondStore--;

        Allure.step("Проверка сохранения supply", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(CandidateDao.INSTANCE.findCandidate(CANDIDATE_UUID_WITH_SHIFT).getOnshift(), "У кандидата не проставлен onshift");
            softAssert.assertNotNull(SupplyDao.INSTANCE.findSupply(FIRST_STORE_ID, CANDIDATE_UUID_WITH_SHIFT), "Не добавился supply");
            softAssert.assertNotNull(SupplyDao.INSTANCE.findSupply(SECOND_STORE_ID, CANDIDATE_UUID_WITH_SHIFT), "Не добавился supply");
            softAssert.assertAll();
        });
    }

    @TmsLink("190")
    @Story("Demand")
    @Test(description = "Удаление всего supply при смене onshift на false",
            groups = "ondemand-surgelevel",
            dependsOnMethods = "surgeProduceEventCandidateWithShift")
    public void surgeEventCandidateWithShiftChangeToFalse() {
        publishEventShift(CANDIDATE_UUID_WITH_SHIFT, getZonedUTCDate(), "completed", LONG_TIMEOUT);

        currentSurgeLevelFirstStore++;
        currentSurgeLevelSecondStore++;

        Allure.step("Проверка удаления supply", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertFalse(CandidateDao.INSTANCE.findCandidate(CANDIDATE_UUID_WITH_SHIFT).getOnshift(), "У кандидата не изменился onshift");
            softAssert.assertNull(SupplyDao.INSTANCE.findSupply(FIRST_STORE_ID, CANDIDATE_UUID_WITH_SHIFT), "Не пропал supply");
            softAssert.assertNull(SupplyDao.INSTANCE.findSupply(SECOND_STORE_ID, CANDIDATE_UUID_WITH_SHIFT), "Не пропал supply");
            softAssert.assertAll();
        });
    }

    @TmsLink("189")
    @Story("Demand")
    @Test(description = "Отсутствие добавления supply не onshift кандидату",
            groups = "ondemand-surgelevel",
            dependsOnMethods = "surgeEventCandidateWithShiftChangeToFalse")
    public void surgeEventCandidateWithShiftFalseNoSupplyAdded() {
        publishEventLocation(CANDIDATE_UUID_WITH_SHIFT, 0f, 0f, false, SHORT_TIMEOUT);
        publishEventLocation(CANDIDATE_UUID_WITH_SHIFT, secondStoreLocation.getLat(), secondStoreLocation.getLon(), false, LONG_TIMEOUT);

        Allure.step("Проверка отсутствия сохранения supply", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertNull(SupplyDao.INSTANCE.findSupply(FIRST_STORE_ID, CANDIDATE_UUID_WITH_SHIFT), "Добавился supply");
            softAssert.assertNull(SupplyDao.INSTANCE.findSupply(SECOND_STORE_ID, CANDIDATE_UUID_WITH_SHIFT), "Добавился supply");
            softAssert.assertAll();
        });
    }

    @TmsLink("191")
    @Story("Demand")
    @Test(description = "Добавление supply при смене onshift на true",
            groups = "ondemand-surgelevel",
            dependsOnMethods = "surgeEventCandidateWithShiftFalseNoSupplyAdded")
    public void surgeProduceEventCandidateWithShiftTrue() {
        publishEventShift(CANDIDATE_UUID_WITH_SHIFT, getZonedUTCDate(), "in_progress", LONG_TIMEOUT);

        currentSurgeLevelFirstStore--;
        currentSurgeLevelSecondStore--;

        Allure.step("Проверка добавления supply", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(CandidateDao.INSTANCE.findCandidate(CANDIDATE_UUID_WITH_SHIFT).getOnshift(), "У кандидата не изменился onshift");
            softAssert.assertNotNull(SupplyDao.INSTANCE.findSupply(FIRST_STORE_ID, CANDIDATE_UUID_WITH_SHIFT), "Не добавился supply");
            softAssert.assertNotNull(SupplyDao.INSTANCE.findSupply(SECOND_STORE_ID, CANDIDATE_UUID_WITH_SHIFT), "Не добавился supply");
            softAssert.assertAll();
        });
    }

    @TmsLink("192")
    @Story("Demand")
    @Test(description = "Проверка отсутствия реакции на события смены с fact_started_at=null",
            groups = "ondemand-surgelevel",
            dependsOnMethods = "surgeProduceEventCandidateWithShiftTrue")
    public void surgeEventCandidateWithShiftNoFactStarted() {
        publishEventShift(CANDIDATE_UUID_WITH_SHIFT, "0001-01-01T00:00:00Z", "completed", LONG_TIMEOUT);

        Allure.step("Проверка сохранения supply", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(CandidateDao.INSTANCE.findCandidate(CANDIDATE_UUID_WITH_SHIFT).getOnshift(), "У кандидата изменился onshift");
            softAssert.assertNotNull(SupplyDao.INSTANCE.findSupply(FIRST_STORE_ID, CANDIDATE_UUID_WITH_SHIFT), "Удалился supply");
            softAssert.assertNotNull(SupplyDao.INSTANCE.findSupply(SECOND_STORE_ID, CANDIDATE_UUID_WITH_SHIFT), "Удалился supply");
            softAssert.assertAll();
        });
    }

    @AfterClass(alwaysRun = true)
    public void postConditions() {
        if (Objects.nonNull(STORE_ID)) {
            StoreDao.INSTANCE.delete(STORE_ID);
        }
        if (Objects.nonNull(STORE_ID_NOT_ON_DEMAND)) {
            StoreDao.INSTANCE.delete(STORE_ID_NOT_ON_DEMAND);
        }
        if (Objects.nonNull(STORE_ID_DISABLED)) {
            StoreDao.INSTANCE.delete(STORE_ID_DISABLED);
        }
        if (Objects.nonNull(FIRST_STORE_ID)) {
            StoreDao.INSTANCE.delete(FIRST_STORE_ID);
        }
        if (Objects.nonNull(SECOND_STORE_ID)) {
            StoreDao.INSTANCE.delete(SECOND_STORE_ID);
        }
        if (Objects.nonNull(THIRD_STORE_ID)) {
            StoreDao.INSTANCE.delete(THIRD_STORE_ID);
        }
    }
}
