package ru.instamart.test.api.on_demand.surgelevel;

import io.qameta.allure.*;
import order.OrderChanged.EventOrderChanged.OrderStatus;
import order.OrderChanged.EventOrderChanged.ShipmentType;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.SurgeLevelBase;
import ru.instamart.api.helper.SurgeLevelHelper;
import ru.instamart.jdbc.dao.surgelevel.*;
import ru.instamart.kraken.util.ThreadUtil;
import surgelevel.Surgelevel;
import surgelevelevent.Surgelevelevent.SurgeEvent.Method;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static events.CandidateChangesOuterClass.CandidateChanges;
import static org.testng.Assert.*;
import static ru.instamart.api.helper.SurgeLevelHelper.*;
import static ru.instamart.api.helper.WaitHelper.withRetriesAsserted;
import static ru.instamart.kraken.enums.ScheduleType.DISPATCH;
import static ru.instamart.kraken.enums.ScheduleType.YANDEX;
import static ru.instamart.kraken.util.TimeUtil.*;

@Epic("Surgelevel")
@Feature("Автоматический расчет surge.one")
public class SurgeOneTest extends SurgeLevelBase {

    private final String STORE_ID = UUID.randomUUID().toString();
    private final String STORE_ID_NOT_ON_DEMAND = UUID.randomUUID().toString();
    private final String STORE_ID_DISABLED = UUID.randomUUID().toString();
    private final String STORE_ID_NOT_EXPIRED = UUID.randomUUID().toString();
    private final String SHIPMENT_UUID = UUID.randomUUID().toString();
    private final String ORDER_UUID = UUID.randomUUID().toString();
    private final String CANDIDATE_UUID = UUID.randomUUID().toString();
    private final String CANDIDATE_UUID_DELIVERY_AREA = UUID.randomUUID().toString();
    private final String CANDIDATE_UUID_FAKE_GPS = UUID.randomUUID().toString();
    private float currentSurgeLevel = BASE_SURGELEVEL;
    private float currentSurgeLevelNotExpired = currentSurgeLevel;
    private int currentDemandAmount;
    private int currentSupplyAmount;
    private float pastSurgeLevel;
    private final AtomicInteger SURGE_CALCULATION_COUNT = new AtomicInteger(); //костыль для понимания какой pastSurgeLevel использовать
    private int surgeEventOutdate = BASE_SURGE_OUTDATE;
    private Surgelevel.Location storeLocation;

    @BeforeClass(alwaysRun = true)
    public void preConditions() {
        storeLocation = Surgelevel.Location.newBuilder().setLat(26.9993f).setLon(14.4002f).build();
        addStore(STORE_ID, UUID.randomUUID().toString(), false, true, storeLocation.getLat(), storeLocation.getLon(), FORMULA_ID, 1000, FIRST_DELIVERY_AREA_ID, 1000);
        addStore(STORE_ID_NOT_ON_DEMAND, UUID.randomUUID().toString(), false, false, storeLocation.getLat(), storeLocation.getLon(), FORMULA_ID, 1000, FIRST_DELIVERY_AREA_ID, null);
        addStore(STORE_ID_DISABLED, UUID.randomUUID().toString(), true, true, storeLocation.getLat(), storeLocation.getLon(), FORMULA_ID, 1000, FIRST_DELIVERY_AREA_ID, null);
        addStore(STORE_ID_NOT_EXPIRED, UUID.randomUUID().toString(), false, true, storeLocation.getLat() - 1, storeLocation.getLon() - 1, FORMULA_ID, 1000, FIRST_DELIVERY_AREA_ID, null);

        final var surgeEventOutdateFromK8s = SurgeLevelHelper.getInstance().getSurgeEventOutdate();
        if (Objects.nonNull(surgeEventOutdateFromK8s)) {
            surgeEventOutdate = surgeEventOutdateFromK8s;
        }
    }

    @AfterMethod(alwaysRun = true)
    public void waitForOutdate() {
        ThreadUtil.simplyAwait(surgeEventOutdate);
    }

    @TmsLink("24")
    @Story("Surge Calculation")
    @Test(description = "Отсутствие расчета surgelevel для не ON_DEMAND магазинов",
            groups = "ondemand-surgelevel",
            dependsOnMethods = "surgeProduceEventOrderOnDemand")
    public void surgeNotOnDemandStore() {
        Allure.step("Проверка отсутствия расчета surgelevel", () -> withRetriesAsserted(() -> {
            final var store = StoreDao.INSTANCE.findById(STORE_ID_NOT_ON_DEMAND);
            assertTrue(store.isPresent(), "Не нашли магазин в БД");
            assertNull(store.get().getActualResultId(), "для магазина был посчитан surgelevel");
        }, LONG_TIMEOUT));
    }

    @TmsLink("36")
    @Story("Surge Calculation")
    @Test(description = "Отсутствие расчета surgelevel при disabled=true в конфиге",
            groups = "ondemand-surgelevel",
            dependsOnMethods = "surgeProduceEventOrderOnDemand")
    public void surgeDisabledStore() {
        Allure.step("Проверка отсутствия расчета surgelevel", () -> withRetriesAsserted(() -> {
            final var store = StoreDao.INSTANCE.findById(STORE_ID_DISABLED);
            assertTrue(store.isPresent(), "Не нашли магазин в БД");
            assertNull(store.get().getActualResultId(), "для магазина был посчитан surgelevel");
        }, LONG_TIMEOUT));
    }

    @TmsLink("121")
    @Story("Surge Calculation")
    @Test(description = "Отсутствие расчета surgelevel при не достижении expiredAt",
            groups = "ondemand-surgelevel")
    public void surgeNoRecalculationWithExpired() {
        publishEventOrderStatus(UUID.randomUUID().toString(), STORE_ID_NOT_EXPIRED, OrderStatus.NEW, ShipmentType.ON_DEMAND, UUID.randomUUID().toString(), 0);

        currentSurgeLevelNotExpired++;

        Allure.step("Выставляем expired_at у surgelevel магазина", () -> withRetriesAsserted(() -> {
            final var result = ResultDao.INSTANCE.findResult(STORE_ID_NOT_EXPIRED);

            assertNotNull(result, "Для магазина не посчитан результат");
            assertEquals(result.getSurgeLevel().floatValue(), currentSurgeLevelNotExpired, "Не верный surgelevel");
        }, LONG_TIMEOUT));

        ResultDao.INSTANCE.updateResult(STORE_ID_NOT_EXPIRED, getZonedUTCDatePlusMinutes(666));
        publishEventOrderStatus(UUID.randomUUID().toString(), STORE_ID_NOT_EXPIRED, OrderStatus.NEW, ShipmentType.ON_DEMAND, UUID.randomUUID().toString(), 0);

        Allure.step("Провер]ем отсутствие перерасчета surgelevel", () -> withRetriesAsserted(() -> assertEquals(ResultDao.INSTANCE.findResult(STORE_ID_NOT_EXPIRED).getSurgeLevel().floatValue(), currentSurgeLevelNotExpired, "Не верный surgelevel"), LONG_TIMEOUT));
    }

    @TmsLinks({@TmsLink("14"), @TmsLink("23"), @TmsLink("25"), @TmsLink("163"), @TmsLink("32")})
    @Story("Demand")
    @Test(description = "Добавление demand при получении события с новым ON_DEMAND заказом",
            groups = "ondemand-surgelevel")
    public void surgeProduceEventOrderOnDemand() {
        publishEventOrderStatus(ORDER_UUID, STORE_ID, OrderStatus.NEW, ShipmentType.ON_DEMAND, SHIPMENT_UUID, 0);

        pastSurgeLevel = currentSurgeLevel;
        currentSurgeLevel++;
        currentDemandAmount++;
        SURGE_CALCULATION_COUNT.addAndGet(1);

        Allure.step("Проверка отсутствия изменения surgelevel", () -> withRetriesAsserted(() -> {
            final var surgeLevels = kafka.waitDataInKafkaTopicSurgeLevel(STORE_ID, 1L);
            checkSurgeLevelProduce(surgeLevels, surgeLevels.size(), STORE_ID, SURGE_CALCULATION_COUNT.get() > 1 ? pastSurgeLevel : 0, currentSurgeLevel, currentDemandAmount, currentSupplyAmount, Method.ACTUAL);
        }, LONG_TIMEOUT));
    }

    @TmsLink("40")
    @Story("Demand")
    @Test(description = "Отсутствие изменения surgelevel при получении ранее полученного ON_DEMAND заказа",
            groups = "ondemand-surgelevel",
            dependsOnMethods = "surgeProduceEventOrderOnDemand")
    public void surgeProduceEventOrderRepeat() {
        publishEventOrderStatus(ORDER_UUID, STORE_ID, OrderStatus.ROUTING, ShipmentType.ON_DEMAND, SHIPMENT_UUID, 0);

        Allure.step("Проверка отсутствия изменения surgelevel", () -> withRetriesAsserted(() -> {
            final var result = ResultDao.INSTANCE.findResult(STORE_ID);

            assertNotNull(result, "Для магазина не посчитан результат");
            assertEquals(result.getSurgeLevel().floatValue(), currentSurgeLevel, "Не верный surgelevel");
        }, LONG_TIMEOUT));
    }

    @TmsLink("16")
    @Story("Demand")
    @Test(description = "Отсутствие добавления demand при получении события с новым не ON_DEMAND заказом",
            groups = "ondemand-surgelevel")
    public void surgeProduceEventOrderNotOnDemand() {
        String shipmentUuid = UUID.randomUUID().toString();
        publishEventOrderStatus(UUID.randomUUID().toString(), STORE_ID, OrderStatus.NEW, ShipmentType.PLANNED, shipmentUuid, MEDIUM_TIMEOUT);

        Allure.step("Проверка отсутствия добавления demand", () -> assertNull(DemandDao.INSTANCE.findDemand(STORE_ID, shipmentUuid), "Заказ добавился в demand"));
    }

    @TmsLink("126")
    @Story("Supply")
    @Test(description = "Отсутствие добавления supply при получении не on-demand кандидата",
            groups = "ondemand-surgelevel")
    public void surgeProduceEventCandidateNotOnDemand() {
        String candidateUuid = UUID.randomUUID().toString();
        publishEventCandidateStatus(candidateUuid, CandidateChanges.Role.UNIVERSAL, CandidateChanges.Status.FREE, 0, FIRST_DELIVERY_AREA_ID, true, STORE_ID, YANDEX.getName(), SHORT_TIMEOUT);
        publishEventLocation(candidateUuid, storeLocation.getLat(), storeLocation.getLon(), false, MEDIUM_TIMEOUT);

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

        publishEventCandidateStatus(CANDIDATE_UUID_DELIVERY_AREA, CandidateChanges.Role.UNIVERSAL, CandidateChanges.Status.UNCHANGED, 0, FIRST_DELIVERY_AREA_ID, true, STORE_ID, DISPATCH.getName(), MEDIUM_TIMEOUT);

        Allure.step("Проверка отсутствия удаления supply", () -> assertNotNull(SupplyDao.INSTANCE.findSupply(STORE_ID, CANDIDATE_UUID_DELIVERY_AREA), "Кандидат удалился из supply"));
    }

    @TmsLink("138")
    @Story("Supply")
    @Test(description = "Отсутствие добавления supply при пустых координатах кандидата",
            groups = "ondemand-surgelevel")
    public void surgeProduceEventCandidateNoCoordinates() {
        String candidateUuid = UUID.randomUUID().toString();
        publishEventCandidateStatus(candidateUuid, CandidateChanges.Role.UNIVERSAL, CandidateChanges.Status.FREE, 0, FIRST_DELIVERY_AREA_ID, true, STORE_ID, DISPATCH.getName(), MEDIUM_TIMEOUT);

        Allure.step("Проверка отсутствия добавления supply", () -> assertNull(SupplyDao.INSTANCE.findSupply(STORE_ID, candidateUuid), "Кандидат добавился в supply"));
    }

    @TmsLink("144")
    @Story("Supply")
    @Test(description = "Отсутствие добавления supply с фейковыми координатами кандидата",
            groups = "ondemand-surgelevel")
    public void surgeProduceEventCandidateFakeGps() {
        publishEventCandidateStatus(CANDIDATE_UUID_FAKE_GPS, CandidateChanges.Role.UNIVERSAL, CandidateChanges.Status.FREE, 0, 0, false, STORE_ID, DISPATCH.getName(), SHORT_TIMEOUT);
        publishEventLocation(CANDIDATE_UUID_FAKE_GPS, storeLocation.getLat(), storeLocation.getLon(), true, MEDIUM_TIMEOUT);

        Allure.step("Проверка отсутствия supply", () -> assertNull(SupplyDao.INSTANCE.findSupply(STORE_ID, CANDIDATE_UUID_FAKE_GPS), "Кандидат добавился в supply"));
    }

    @TmsLink("145")
    @Story("Supply")
    @Test(description = "Добавление supply при получении настоящих координат кандидата, когда до этого были фейковые",
            groups = "ondemand-surgelevel",
            dependsOnMethods = "surgeProduceEventCandidateFakeGps")
    public void surgeProduceEventCandidateNoLongerFakeGps() {
        publishEventLocation(CANDIDATE_UUID_FAKE_GPS, storeLocation.getLat(), storeLocation.getLon(), false, 0);

        currentSurgeLevel--;
        currentSupplyAmount++;

        Allure.step("Проверка добавления в supply", () -> withRetriesAsserted(() -> {
            assertNotNull(SupplyDao.INSTANCE.findSupply(STORE_ID, CANDIDATE_UUID_FAKE_GPS), "Кандидат не добавился в supply");

            final var result = ResultDao.INSTANCE.findResult(STORE_ID);
            assertNotNull(result, "Для магазина не посчитан результат");
            assertEquals(result.getSurgeLevel().floatValue(), currentSurgeLevel, "Не верный surgelevel");
        }, LONG_TIMEOUT));
    }

    @TmsLink("143")
    @Story("Supply")
    @Test(description = "Удаление всего supply кандидата при получении фейковых координат",
            groups = "ondemand-surgelevel",
            dependsOnMethods = "surgeProduceEventCandidateNoLongerFakeGps")
    public void surgeProduceEventCandidateNewFakeGps() {
        publishEventLocation(CANDIDATE_UUID_FAKE_GPS, storeLocation.getLat(), storeLocation.getLon(), true, 0);

        currentSurgeLevel++;
        currentSupplyAmount--;

        Allure.step("Проверка удаления supply", () -> withRetriesAsserted(() -> {
            assertNull(SupplyDao.INSTANCE.findSupply(STORE_ID, CANDIDATE_UUID_FAKE_GPS), "Кандидат не удалился из supply");

            final var result = ResultDao.INSTANCE.findResult(STORE_ID);
            assertNotNull(result, "Для магазина не посчитан результат");
            assertEquals(result.getSurgeLevel().floatValue(), currentSurgeLevel, "Не верный surgelevel");
        }, LONG_TIMEOUT));
    }

    @TmsLinks({@TmsLink("123"), @TmsLink("125")})
    @Story("Delivery Area Supply")
    @Test(description = "Добавление supply при получении кандидата по delivery_area",
            groups = "ondemand-surgelevel")
    public void surgeProduceEventCandidateDeliveryArea() {
        publishEventCandidateStatus(CANDIDATE_UUID_DELIVERY_AREA, CandidateChanges.Role.UNIVERSAL, CandidateChanges.Status.FREE, 0, FIRST_DELIVERY_AREA_ID, true, STORE_ID, DISPATCH.getName(), SHORT_TIMEOUT);
        publishEventLocation(CANDIDATE_UUID_DELIVERY_AREA, 15.0f, 15.0f, false, 0);

        pastSurgeLevel = currentSurgeLevel;
        currentSurgeLevel--;
        currentSupplyAmount++;
        SURGE_CALCULATION_COUNT.addAndGet(1);

        Allure.step("Проверка отсутствия изменения surgelevel", () -> withRetriesAsserted(() -> {
            final var surgeLevels = kafka.waitDataInKafkaTopicSurgeLevel(STORE_ID, 1L);
            checkSurgeLevelProduce(surgeLevels, surgeLevels.size(), STORE_ID, SURGE_CALCULATION_COUNT.get() > 1 ? pastSurgeLevel : 0, currentSurgeLevel, currentDemandAmount, currentSupplyAmount, Method.ACTUAL);
        }, LONG_TIMEOUT));
    }

    @TmsLinks({@TmsLink("20"), @TmsLink("166")})
    @Story("Radius Supply")
    @Test(description = "Добавление supply при получении кандидата по координатам в близи от магазина",
            groups = "ondemand-surgelevel")
    public void surgeProduceEventCandidateWithLocation() {
        publishEventCandidateStatus(CANDIDATE_UUID, CandidateChanges.Role.UNIVERSAL, CandidateChanges.Status.FREE, 0, 0, false, STORE_ID, DISPATCH.getName(), SHORT_TIMEOUT);
        publishEventLocation(CANDIDATE_UUID, storeLocation.getLat(), storeLocation.getLon(), false, 0);

        pastSurgeLevel = currentSurgeLevel;
        currentSurgeLevel--;
        currentSupplyAmount++;
        SURGE_CALCULATION_COUNT.addAndGet(1);

        Allure.step("Проверка отсутствия изменения surgelevel", () -> withRetriesAsserted(() -> {
            final var surgeLevels = kafka.waitDataInKafkaTopicSurgeLevel(STORE_ID, 1L);
            checkSurgeLevelProduce(surgeLevels, surgeLevels.size(), STORE_ID, SURGE_CALCULATION_COUNT.get() > 1 ? pastSurgeLevel : 0, currentSurgeLevel, currentDemandAmount, currentSupplyAmount, Method.ACTUAL);
        }, LONG_TIMEOUT));
    }

    @TmsLink("140")
    @Story("Radius Supply")
    @Test(description = "Отсутствие удаления supply при уходе кандидата по координатам недостаточно далеко от магазина",
            groups = "ondemand-surgelevel",
            dependsOnMethods = "surgeProduceEventCandidateWithLocation")
    public void surgeProduceEventCandidateWithLocationWentNotFar() {
        publishEventLocation(CANDIDATE_UUID, storeLocation.getLat() - 0.001f, storeLocation.getLon() - 0.001f, false, MEDIUM_TIMEOUT);

        Allure.step("Проверка supply", () -> assertNotNull(SupplyDao.INSTANCE.findSupply(STORE_ID, CANDIDATE_UUID), "Кандидат удалился из supply"));
    }

    @TmsLink("167")
    @Story("Surge Calculation")
    @Test(description = "Отсутствие расчета surgelevel с радиусом в конфиге не null",
            groups = "ondemand-surgelevel")
    public void surgeDistanceFromConfigTooFar() {
        String candidateUuid = UUID.randomUUID().toString();
        publishEventCandidateStatus(candidateUuid, CandidateChanges.Role.UNIVERSAL, CandidateChanges.Status.FREE, 0, 0, false, STORE_ID, DISPATCH.getName(), SHORT_TIMEOUT);
        publishEventLocation(candidateUuid, storeLocation.getLat() - 0.01f, storeLocation.getLon() - 0.01f, false, MEDIUM_TIMEOUT);

        Allure.step("Проверка отсутствия добавления supply", () -> assertNull(SupplyDao.INSTANCE.findSupply(STORE_ID, candidateUuid), "Кандидат добавился в supply"));
    }

    @TmsLink("187")
    @Story("Supply")
    @Test(description = "Обновление кандидата и вызов расчета surgelevel при получении новых координат не смотря на не достижение CANDIDATE_MOVEMENT_THRESHOLD",
            groups = "ondemand-surgelevel",
            dependsOnMethods = "surgeProduceEventCandidateWithLocationWentNotFar")
    public void surgeDistanceForceUpdate() {
        CandidateDao.INSTANCE.updateCandidate(CANDIDATE_UUID, true, getZonedUTCDateMinusMinutes(10));
        publishEventLocation(CANDIDATE_UUID, storeLocation.getLat() - 0.001f, storeLocation.getLon() - 0.001f, false, 0);

        pastSurgeLevel = currentSurgeLevel;
        currentSurgeLevel++;

        Allure.step("Проверка отсутствия изменения surgelevel", () -> withRetriesAsserted(() -> {
            final var surgeLevels = kafka.waitDataInKafkaTopicSurgeLevel(STORE_ID, 1L);
            checkSurgeLevelProduce(surgeLevels, surgeLevels.size(), STORE_ID, pastSurgeLevel, currentSurgeLevel, currentDemandAmount, currentSupplyAmount, Method.ACTUAL);
        }, LONG_TIMEOUT));
    }

    @TmsLink("49")
    @Story("Radius Supply")
    @Test(description = "Удаление supply при уходе кандидата по координатам вдаль от магазина",
            groups = "ondemand-surgelevel",
            dependsOnMethods = "surgeDistanceForceUpdate")
    public void surgeProduceEventCandidateWithLocationWentTooFar() {
        publishEventLocation(CANDIDATE_UUID, 15.0f, 15.0f, false, 0);

        currentSupplyAmount--;

        Allure.step("Проверка удаления supply", () -> withRetriesAsserted(() -> {
            assertNull(SupplyDao.INSTANCE.findSupply(STORE_ID, CANDIDATE_UUID), "Кандидат не удалился из supply");

            final var result = ResultDao.INSTANCE.findResult(STORE_ID);
            assertNotNull(result, "Для магазина не посчитан результат");
            assertEquals(result.getSurgeLevel().floatValue(), currentSurgeLevel, "Не верный surgelevel");
        }, LONG_TIMEOUT));
    }

    @TmsLink("22")
    @Story("Radius Supply")
    @Test(description = "Отсутствие добавления supply при получении кандидата по координатам не в близи от магазина",
            groups = "ondemand-surgelevel")
    public void surgeProduceEventCandidateWithLocationFar() {
        String candidateUuid = UUID.randomUUID().toString();
        publishEventCandidateStatus(candidateUuid, CandidateChanges.Role.UNIVERSAL, CandidateChanges.Status.FREE, 0, 0, false, STORE_ID, DISPATCH.getName(), SHORT_TIMEOUT);
        publishEventLocation(candidateUuid, 15.0f, 15.0f, false, MEDIUM_TIMEOUT);

        Allure.step("Проверка отсутствия добавления supply", () -> assertNull(SupplyDao.INSTANCE.findSupply(STORE_ID, candidateUuid), "Кандидат добавился в supply"));
    }

    @AfterClass(alwaysRun = true)
    public void postConditions() {
        deleteStore(STORE_ID);
        deleteStore(STORE_ID_NOT_ON_DEMAND);
        deleteStore(STORE_ID_DISABLED);
        deleteStore(STORE_ID_NOT_EXPIRED);
    }
}
