package ru.instamart.api.helper;

import com.google.protobuf.Timestamp;
import events.CandidateChangesOuterClass;
import events.CandidateChangesOuterClass.CandidateChanges;
import io.qameta.allure.Step;
import lombok.Getter;
import norns.Norns.EventAddLocation;
import order.OrderChanged;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.asserts.SoftAssert;
import ru.instamart.jdbc.dao.surgelevel.ConfigDao;
import ru.instamart.jdbc.dao.surgelevel.FormulaDao;
import ru.instamart.jdbc.dao.surgelevel.StoreDao;
import ru.instamart.jdbc.entity.surgelevel.FormulaEntity;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.util.ThreadUtil;
import surgelevelevent.Surgelevelevent;

import java.util.List;
import java.util.Objects;

import static events.ShiftChangedEventOuterClass.*;
import static org.testng.Assert.assertTrue;
import static ru.instamart.api.enums.BashCommands.ServiceEnvironmentProperties.SURGE_EVENT_OUTDATE;
import static ru.instamart.api.enums.BashCommands.ServiceEnvironmentProperties.SURGE_HTTP_AUTH_TOKENS;
import static ru.instamart.api.helper.K8sHelper.getServiceEnvProp;
import static ru.instamart.kafka.configs.KafkaConfigs.*;
import static ru.instamart.kraken.util.StringUtil.matchWithRegex;
import static ru.instamart.kraken.util.TimeUtil.*;
import static ru.instamart.kraken.util.TimeUtil.getZonedUTCDate;

public class SurgeLevelHelper {

    private static final KafkaHelper kafka = new KafkaHelper();
    private static volatile SurgeLevelHelper INSTANCE;
    @Getter
    private final Integer surgeEventOutdate;
    @Getter
    private final String authToken;

    private SurgeLevelHelper() {
        this.surgeEventOutdate = getSurgeEventOutdateFromK8s();
        this.authToken = getHttpAuthTokenFromK8s();
    }

    public static SurgeLevelHelper getInstance() {
        SurgeLevelHelper RESULT = INSTANCE;
        if (RESULT != null) {
            return INSTANCE;
        }
        synchronized (SurgeLevelHelper.class) {
            if (INSTANCE == null) {
                INSTANCE = new SurgeLevelHelper();
            }
            return INSTANCE;
        }
    }

    @Step("Создаем событие заказа для магазина {storeId}")
    public static OrderChanged.EventOrderChanged getEventOrderStatus(final String orderUuid, final String storeId, final OrderChanged.EventOrderChanged.OrderStatus orderStatus, final OrderChanged.EventOrderChanged.ShipmentType shipmentType, final String shipmentUuid) {
        return OrderChanged.EventOrderChanged.newBuilder()
                .setOrderUuid(orderUuid)
                .setPlaceUuid(storeId)
                .setOrderStatus(orderStatus)
                .setShipmentType(shipmentType)
                .setShipmentUuid(shipmentUuid)
                .build();
    }

    @Step("Отправляем событие заказа для магазина {storeId} в kafka")
    public static void publishEventOrderStatus(final String orderUuid, final String storeId, final OrderChanged.EventOrderChanged.OrderStatus orderStatus, final OrderChanged.EventOrderChanged.ShipmentType shipmentType, final String shipmentUuid, final int timeout) {
        final var eventOrder = getEventOrderStatus(orderUuid, storeId, orderStatus, shipmentType, shipmentUuid);
        kafka.publish(configOrderStatusChanged(), eventOrder);
        ThreadUtil.simplyAwait(timeout);
    }

    @Step("Проверяем отправку расчета surgelevel в kafka для магазина {storeId}")
    public static void checkSurgeLevelProduce(final List<Surgelevelevent.SurgeEvent> surgeLevels, final int surgeEventsAmount, final String storeId, final float pastSurgeLevel, final float currentSurgeLevel, final int currentDemandAmount, final int currentSupplyAmount, final Surgelevelevent.SurgeEvent.Method method) {
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(surgeLevels.get(surgeEventsAmount - 1).getStoreId(), storeId, "Не верный uuid магазина");
        softAssert.assertEquals(surgeLevels.get(surgeEventsAmount - 1).getPastSurgeLevel(), pastSurgeLevel, "Не верный прошлый surgelevel");
        softAssert.assertEquals(surgeLevels.get(surgeEventsAmount - 1).getPresentSurgeLevel(), currentSurgeLevel, "Не верный surgelevel");
        softAssert.assertEquals(surgeLevels.get(surgeEventsAmount - 1).getInfo().getShipmentQuantity(), currentDemandAmount, "Не верное кол-во заказов");
        softAssert.assertEquals(surgeLevels.get(surgeEventsAmount - 1).getInfo().getCandidateQuantity(), currentSupplyAmount, "Не верное кол-во кандидатов");
        softAssert.assertEquals(surgeLevels.get(surgeEventsAmount - 1).getMethod(), method, "Не верный метод расчета");
        softAssert.assertAll();
    }

    @Step("Добавить магазин {storeId} и конфиг для него в БД")
    public static void addStore(final String storeId, final String configId, final Boolean isDisabled, final boolean isOnDemand, final float storeLat, final float storeLon, final String formulaId, final int step, final int deliveryArea, final Integer distance) {
        final var isConfigCreated = ConfigDao.INSTANCE.addConfig(configId, formulaId, step, isDisabled, distance);
        assertTrue(isConfigCreated, "Не добавился конфиг");
        final var isStoreCreated = StoreDao.INSTANCE.addStore(storeId, configId, isOnDemand, storeLat, storeLon, deliveryArea);
        assertTrue(isStoreCreated, "Не добавился магазин");
    }

    @Step("Удалить магазин {storeId} из БД")
    public static void deleteStore(final String storeId) {
        if (Objects.nonNull(storeId)) {
            StoreDao.INSTANCE.delete(storeId);
        }
    }

    @Step("Создаем событие статуса кандидата {candidateUuid}")
    public static CandidateChanges getEventCandidateStatus(final String candidateUuid, final CandidateChanges.Role candidateRole, final CandidateChanges.Status candidateStatus, final int shiftId, final int deliveryAreaId, final boolean fixedOnDeliveryAreaOrStore, final String storeId, final String storeScheduleType) {
        return CandidateChanges.newBuilder()
                .setUuid(candidateUuid)
                .setRole(candidateRole)
                .setStatus(candidateStatus)
                .setTime(getTimestamp())
                .setShift(CandidateChangesOuterClass.CandidateShift.newBuilder()
                        .setShiftId(shiftId)
                        .setDeliveryAreaId(deliveryAreaId)
                        .setFixedOnDeliveryAreaOrStore(fixedOnDeliveryAreaOrStore)
                        .setStoreUuid(storeId)
                        .build())
                .setStoreScheduleType(storeScheduleType)
                .build();
    }

    @Step("Отправляем событие статуса кандидата {candidateUuid} в kafka")
    public static void publishEventCandidateStatus(String candidateUuid, CandidateChanges.Role candidateRole, CandidateChanges.Status candidateStatus, int shiftId, int deliveryAreaId, boolean fixedOnDeliveryAreaOrStore, String storeId, String storeScheduleType, int timeout) {
        final var eventCandidate = getEventCandidateStatus(candidateUuid, candidateRole, candidateStatus, shiftId, deliveryAreaId, fixedOnDeliveryAreaOrStore, storeId, storeScheduleType);
        kafka.publish(configCandidateStatus(), eventCandidate);
        ThreadUtil.simplyAwait(timeout);
    }

    @Step("Создаем событие статуса кандидата {candidateUuid} с workflow")
    public static CandidateChanges getEventCandidateStatusWithWorkflow(final String candidateUuid, final CandidateChanges.Role candidateRole, final CandidateChanges.Status candidateStatus, final int shiftId, final int deliveryAreaId, final boolean fixedOnDeliveryAreaOrStore, final String storeId, final String storeScheduleType, final int workflowId, final CandidateChangesOuterClass.WorkflowStatus workflowStatus, final Timestamp workflowPlanEnded, final float workflowLat, final float workflowLon) {
        return CandidateChanges.newBuilder()
                .setUuid(candidateUuid)
                .setRole(candidateRole)
                .setStatus(candidateStatus)
                .setTime(getTimestamp())
                .setShift(CandidateChangesOuterClass.CandidateShift.newBuilder()
                        .setShiftId(shiftId)
                        .setDeliveryAreaId(deliveryAreaId)
                        .setFixedOnDeliveryAreaOrStore(fixedOnDeliveryAreaOrStore)
                        .setStoreUuid(storeId)
                        .build())
                .setStoreScheduleType(storeScheduleType)
                .addQueuedWorkflows(CandidateChangesOuterClass.Workflow.newBuilder()
                        .setId(workflowId)
                        .setStatus(workflowStatus)
                        .setShiftId(shiftId)
                        .setCreatedAt(getTimestamp())
                        .setUpdatedAt(getTimestamp())
                        .setPlanEndedAt(workflowPlanEnded)
                        .setCoordinatesEnd(CandidateChangesOuterClass.Location.newBuilder()
                                .setLat(workflowLat)
                                .setLon(workflowLon)
                                .build())
                        .build())
                .build();
    }

    @Step("Отправляем событие статуса кандидата {candidateUuid} c workflow в kafka")
    public static void publishEventCandidateStatusWithWorkflow(String candidateUuid, CandidateChanges.Role candidateRole, CandidateChanges.Status candidateStatus, int shiftId, int deliveryAreaId, boolean fixedOnDeliveryAreaOrStore, String storeId, String storeScheduleType, int workflowId, CandidateChangesOuterClass.WorkflowStatus workflowStatus, Timestamp workflowPlanEnded, float workflowLat, float workflowLon, int timeout) {
        final var eventCandidate = getEventCandidateStatusWithWorkflow(candidateUuid, candidateRole, candidateStatus, shiftId, deliveryAreaId, fixedOnDeliveryAreaOrStore, storeId, storeScheduleType, workflowId, workflowStatus, workflowPlanEnded, workflowLat, workflowLon);
        kafka.publish(configCandidateStatus(), eventCandidate);
        ThreadUtil.simplyAwait(timeout);
    }

    @Step("Создаем событие геолокации кандидата {candidateUuid}")
    public static EventAddLocation getEventLocation(final String userUuid, final float lat, final float lon, final boolean isFakeGpsAppDetected) {
        return EventAddLocation.newBuilder()
                .setUserUuid(userUuid)
                .setLat(lat)
                .setLon(lon)
                .setTime(getTimestamp())
                .setIsFakeGpsAppDetected(isFakeGpsAppDetected)
                .build();
    }

    @Step("Отправляем событие геолокации кандидата {candidateUuid} в kafka")
    public static void publishEventLocation(final String userUuid, final float lat, final float lon, final boolean isFakeGpsAppDetected, final int timeout) {
        final var eventLocation = getEventLocation(userUuid, lat, lon, isFakeGpsAppDetected);
        kafka.publish(configNorns(), eventLocation);
        ThreadUtil.simplyAwait(timeout);
    }

    @Step("Создаем событие о смене кандидата {candidateUuid}")
    public static ShiftChangedEvent getEventShift(final String partnerUuid, final String factStartedAt, final String shiftState) {
        return ShiftChangedEvent.newBuilder()
                .setPartnerId(partnerUuid)
                .setPlanStartedAt(getZonedUTCDate())
                .setPlanEndedAt(getZonedUTCDatePlusMinutes(60))
                .setFactStartedAt(factStartedAt)
                .setFactEndedAt("0001-01-01T00:00:00Z")
                .setState(shiftState)
                .build();
    }

    @Step("Отправляем событие о смене кандидата {candidateUuid} в kafka")
    public static void publishEventShift(final String partnerUuid, final String factStartedAt, final String shiftState, final int timeout) {
        final var eventShift = getEventShift(partnerUuid, factStartedAt, shiftState);
        kafka.publish(configShifts(), eventShift);
        ThreadUtil.simplyAwait(timeout);
    }

    @Step("Проверить наличие кастомной формулы расчета surgelevel в БД")
    public static void checkFormula(final String formulaId) {
        FormulaEntity formula = FormulaDao.INSTANCE.findFormula(formulaId);
        if (Objects.isNull(formula)) {
            final var script = "function main(arg) {\n" +
                    "    var demand = 150\n" +
                    "    var supply = 50\n" +
                    "\n" +
                    "    for (var i = 0; i < arg.supply.length; i++) { \n" +
                    "        if (!arg.supply[i].candidate.busy) {\n" +
                    "            supply++;\n" +
                    "        }\n" +
                    "    }\n" +
                    "\n" +
                    "    for (var i = 0; i < arg.demand.length; i++) { \n" +
                    "        if (arg.demand[i].shipment.status>= 0 && arg.demand[i].shipment.status<=3) {\n" +
                    "            demand++;\n" +
                    "        }\n" +
                    "    }\n" +
                    "\n" +
                    "    return demand - supply;\n" +
                    "}";
            final var isFormulaCreated = FormulaDao.INSTANCE.addFormula(formulaId, "Kraken-tests formula", script);
            assertTrue(isFormulaCreated, "Не добавилась формула");
        }
    }

    @Step("Создаем событие расчета surge магазина {storeUuid}")
    public static Surgelevelevent.SurgeEvent getEventSurge(final String storeUuid, final float surgeLevel) {
        return Surgelevelevent.SurgeEvent.newBuilder()
                .setStoreId(storeUuid)
                .setPresentSurgeLevel(surgeLevel)
                .build();
    }

    @Step("Отправляем событие расчета surge магазина {storeUuid} в kafka")
    public static void publishEventSurge(final String storeUuid, final float surgeLevel, final int timeout) {
        final var eventSurge = getEventSurge(storeUuid, surgeLevel);
        kafka.publish(configSurgeLevel(), eventSurge);
        ThreadUtil.simplyAwait(timeout);
    }

    @Step("Получаем таймаут расчета сюрджа")
    public static Integer getSurgeEventOutdateFromK8s() {
        final var envProp = getServiceEnvProp(EnvironmentProperties.Env.SURGELEVEL_NAMESPACE, SURGE_EVENT_OUTDATE.get());
        final var surgeEventOutdateStr = matchWithRegex("^\\[SURGEEVENT_OUTDATE=(\\d+)s\\]$", envProp.toString(), 1);
        if (!surgeEventOutdateStr.isBlank()) {
            final var surgeEventOutdate = Integer.parseInt(surgeEventOutdateStr);
            if (surgeEventOutdate < 3) {
                return surgeEventOutdate + 3;
            }
            return surgeEventOutdate;
        }
        return null;
    }

    @Step("Получаем auth token для /boost")
    public static String getHttpAuthTokenFromK8s() {
        final var envProps = getServiceEnvProp(EnvironmentProperties.Env.SURGELEVEL_NAMESPACE, SURGE_HTTP_AUTH_TOKENS.get());
        final var authTokens = matchWithRegex("^\\[HTTP_AUTH_TOKENS=(\\{.+\\})\\]$", envProps.toString(), 1);
        if (!authTokens.isBlank()) {
            JSONParser parser = new JSONParser();
            JSONObject json = null;
            try {
                json = (JSONObject) parser.parse(authTokens);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (Objects.nonNull(json)) {
                return json.keySet().iterator().next().toString();
            }
        }
        return null;
    }
}
