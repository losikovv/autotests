package ru.instamart.test.api.on_demand.surgelevel;

import io.qameta.allure.*;
import order_enrichment.OrderEnrichment;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.jdbc.dao.surgelevel.DemandDao;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.util.ThreadUtil;
import ru.sbermarket.qase.annotation.CaseId;
import surgelevelevent.Surgelevelevent;

import java.util.List;
import java.util.UUID;

import static org.testng.Assert.assertEquals;
import static ru.instamart.api.helper.K8sHelper.getPaasServiceEnvProp;
import static ru.instamart.api.helper.SurgeLevelHelper.*;
import static ru.instamart.kafka.configs.KafkaConfigs.configCmdOrderEnrichment;
import static ru.instamart.kraken.util.DistanceUtil.distance;
import static ru.instamart.kraken.util.StringUtil.matchWithRegex;

@Epic("On Demand")
@Feature("Расчет surgelevel")
public class KafkaTest extends RestBase {

    private final String STORE_ID = UUID.randomUUID().toString();
    private final String FIRST_STORE_ID = UUID.randomUUID().toString();
    private final String SECOND_STORE_ID = UUID.randomUUID().toString();
    private final String THIRD_STORE_ID = UUID.randomUUID().toString();
    private final String DEFAULT_CONFIG_ID = "00000000-1000-0000-0000-000000000000";
    private final String SHIPMENT_UUID = UUID.randomUUID().toString();
    private final String ORDER_UUID = UUID.randomUUID().toString();
    private final int FIRST_DELIVERY_AREA_ID = 333; //пока просто рандомная цифра
    private final int SECOND_DELIVERY_AREA_ID = 999; //пока просто рандомная цифра
    private float currentSurgeLevel;
    private int currentDemandAmount;
    private int currentSupplyAmount;
    private float pastSurgeLevel;
    private int surgeEventsAmount;
    private boolean surgeEventProduceUnchanged;
    private int surgeEventOutdate;
    private double distFirstSecond;
    private double distFirstThird;

    @BeforeClass(alwaysRun = true)
    public void preConditions() {
        //пока прибил гвоздями
        //стор1 видит оба
        //стор2 видит только стор1 (примерно 1,3КМ от стор1)
        //стор3 видит только стор1 (примерно 2,9КМ  стор1)
        addStore(STORE_ID, UUID.randomUUID().toString(), true, 20.0f, 20.0f, DEFAULT_CONFIG_ID, 1);
        addStore(FIRST_STORE_ID, UUID.randomUUID().toString(), true, 27.034817f, 14.426422f, DEFAULT_CONFIG_ID, 1);
        addStore(SECOND_STORE_ID, UUID.randomUUID().toString(), true, 27.046055f, 14.421101f, DEFAULT_CONFIG_ID, 1);
        addStore(THIRD_STORE_ID, UUID.randomUUID().toString(), true, 27.008896f, 14.431057f, DEFAULT_CONFIG_ID, 1);

        distFirstSecond = distance(27.034817f, 14.426422f, 27.046055f, 14.421101f, 'K') * 1000;
        distFirstThird = distance(27.034817f, 14.426422f, 27.008896f, 14.431057f, 'K') * 1000;

        List<String> serviceEnvProperties = getPaasServiceEnvProp(EnvironmentProperties.Env.SURGELEVEL_NAMESPACE, " | grep -e SURGEEVENT_PRODUCE_UNCHANGED -e SURGEEVENT_OUTDATE ");
        String envPropsStr = String.join("\n", serviceEnvProperties);
        String surgeEventProduceUnchangedStr = matchWithRegex("^SURGEEVENT_PRODUCE_UNCHANGED=(.\\w+)$", envPropsStr, 1);
        String surgeEventOutdateStr = matchWithRegex("^SURGEEVENT_OUTDATE=(.\\d+)s$", envPropsStr, 1);

        surgeEventProduceUnchanged = surgeEventProduceUnchangedStr.equals("true");
        surgeEventOutdate = !surgeEventOutdateStr.isBlank() ? Integer.parseInt(surgeEventOutdateStr) : 10;
    }

    @CaseId(14)
    @Story("Order Event")
    @Test(description = "Расчет surgelevel при получении события EventOrder с новым ON_DEMAND заказом в активном статусе",
            groups = "ondemand-surgelevel-smoke")
    public void surgeProduceEventOrderActive() {
        OrderEnrichment.EventOrderEnrichment eventOrder = getEventOrderEnrichment(FIRST_DELIVERY_AREA_ID, ORDER_UUID, STORE_ID, OrderEnrichment.EventOrderEnrichment.ShipmentStatus.NEW, "ON_DEMAND", SHIPMENT_UUID);
        kafka.publish(configCmdOrderEnrichment(), eventOrder);

        ThreadUtil.simplyAwait(surgeEventOutdate);

        List<Surgelevelevent.SurgeEvent> surgeLevels = kafka.waitDataInKafkaTopicSurgeLevel(STORE_ID);
        surgeEventsAmount = surgeLevels.size();

        pastSurgeLevel = currentSurgeLevel;
        currentSurgeLevel++;
        currentDemandAmount++;

        checkSurgeLevelProduce(surgeLevels, surgeEventsAmount, STORE_ID, pastSurgeLevel, currentSurgeLevel, currentDemandAmount, currentSupplyAmount);
    }

    @CaseId(38)
    @Story("Order Event")
    @Test(description = "Расчет surgelevel при получении события EventOrder с ON_DEMAND заказом, который перестал быть активным",
            groups = "ondemand-surgelevel-regress",
            dependsOnMethods = "surgeProduceEventOrderRepeatActive")
    public void surgeProduceEventOrderNoLongerActive() {
        OrderEnrichment.EventOrderEnrichment eventOrder = getEventOrderEnrichment(FIRST_DELIVERY_AREA_ID, ORDER_UUID, STORE_ID, OrderEnrichment.EventOrderEnrichment.ShipmentStatus.CANCELED, "ON_DEMAND", SHIPMENT_UUID);
        kafka.publish(configCmdOrderEnrichment(), eventOrder);

        ThreadUtil.simplyAwait(surgeEventOutdate);

        List<Surgelevelevent.SurgeEvent> surgeLevels = kafka.waitDataInKafkaTopicSurgeLevel(STORE_ID);
        surgeEventsAmount = surgeLevels.size();

        pastSurgeLevel = currentSurgeLevel;
        currentSurgeLevel--;
        currentDemandAmount--;

        checkSurgeLevelProduce(surgeLevels, surgeEventsAmount, STORE_ID, pastSurgeLevel, currentSurgeLevel, currentDemandAmount, currentSupplyAmount);
    }

    @CaseId(40)
    @Story("Order Event")
    @Test(description = "Отсутствие расчета surgelevel при получении события EventOrder с ранее полученным ON_DEMAND заказом в активном статусе",
            groups = "ondemand-surgelevel-regress",
            dependsOnMethods = "surgeProduceEventOrderActive")
    public void surgeProduceEventOrderRepeatActive() {
        OrderEnrichment.EventOrderEnrichment eventOrder = getEventOrderEnrichment(FIRST_DELIVERY_AREA_ID, ORDER_UUID, STORE_ID, OrderEnrichment.EventOrderEnrichment.ShipmentStatus.AUTOMATIC_ROUTING, "ON_DEMAND", SHIPMENT_UUID);
        kafka.publish(configCmdOrderEnrichment(), eventOrder);

        ThreadUtil.simplyAwait(surgeEventOutdate);

        List<Surgelevelevent.SurgeEvent> surgeLevels = kafka.waitDataInKafkaTopicSurgeLevel(STORE_ID);

        Allure.step("Проверка отсутствия нового события surgelevel в kafka", () -> assertEquals(surgeLevels.size(), surgeEventsAmount, "Поступило новое событие"));
    }

    @CaseId(15)
    @Story("Order Event")
    @Test(description = "Отсутствие расчета surgelevel при получении события EventOrder с новым ON_DEMAND заказом в не активном статусе",
            groups = "ondemand-surgelevel-regress")
    public void surgeProduceEventOrderNotActive() {
        OrderEnrichment.EventOrderEnrichment eventOrder = getEventOrderEnrichment(FIRST_DELIVERY_AREA_ID, UUID.randomUUID().toString(), STORE_ID, OrderEnrichment.EventOrderEnrichment.ShipmentStatus.CANCELED, "ON_DEMAND", UUID.randomUUID().toString());
        kafka.publish(configCmdOrderEnrichment(), eventOrder);

        ThreadUtil.simplyAwait(surgeEventOutdate);

        List<Surgelevelevent.SurgeEvent> surgeLevels = kafka.waitDataInKafkaTopicSurgeLevel(STORE_ID);

        Allure.step("Проверка отсутствия нового события surgelevel в kafka", () -> assertEquals(surgeLevels.size(), surgeEventsAmount, "Поступило новое событие"));
    }

    @CaseId(16)
    @Story("Order Event")
    @Test(description = "Отсутствие расчета surgelevel при получении события EventOrder с новым не ON_DEMAND заказом",
            groups = "ondemand-surgelevel-smoke")
    public void surgeProduceEventOrderNotOnDemand() {
        OrderEnrichment.EventOrderEnrichment eventOrder = getEventOrderEnrichment(FIRST_DELIVERY_AREA_ID, UUID.randomUUID().toString(), STORE_ID, OrderEnrichment.EventOrderEnrichment.ShipmentStatus.NEW, "PLANNED", UUID.randomUUID().toString());
        kafka.publish(configCmdOrderEnrichment(), eventOrder);

        ThreadUtil.simplyAwait(surgeEventOutdate);

        List<Surgelevelevent.SurgeEvent> surgeLevels = kafka.waitDataInKafkaTopicSurgeLevel(STORE_ID);

        Allure.step("Проверка отсутствия нового события surgelevel в kafka", () -> assertEquals(surgeLevels.size(), surgeEventsAmount, "Поступило новое событие"));
    }

    @CaseId(133)
    @Story("Order Event")
    @Test(description = "Расчет surgelevel для нескольких магазинов при получении события EventOrder с новым ON_DEMAND заказом в активном статусе",
            groups = "ondemand-surgelevel-smoke")
    public void surgeProduceEventOrderMultipleStores() {
        String shipmentUUid = UUID.randomUUID().toString();
        OrderEnrichment.EventOrderEnrichment eventOrder = getEventOrderEnrichment(SECOND_DELIVERY_AREA_ID, UUID.randomUUID().toString(), FIRST_STORE_ID, OrderEnrichment.EventOrderEnrichment.ShipmentStatus.NEW, "ON_DEMAND", shipmentUUid);
        kafka.publish(configCmdOrderEnrichment(), eventOrder);

        ThreadUtil.simplyAwait(surgeEventOutdate);

        List<Surgelevelevent.SurgeEvent> surgeLevelFirstStore = kafka.waitDataInKafkaTopicSurgeLevel(FIRST_STORE_ID);
        List<Surgelevelevent.SurgeEvent> surgeLevelSecondStore = kafka.waitDataInKafkaTopicSurgeLevel(SECOND_STORE_ID);
        List<Surgelevelevent.SurgeEvent> surgeLevelThirdStore = kafka.waitDataInKafkaTopicSurgeLevel(THIRD_STORE_ID);

        Allure.step("Проверка отправка нового события surgelevel в kafka для нескольких магазинов", () -> {
            checkSurgeLevelProduce(surgeLevelFirstStore, surgeLevelFirstStore.size(), FIRST_STORE_ID, 0, 1, 1, 0);
            checkSurgeLevelProduce(surgeLevelSecondStore, surgeLevelSecondStore.size(), SECOND_STORE_ID, 0, 1, 1, 0);
            checkSurgeLevelProduce(surgeLevelThirdStore, surgeLevelThirdStore.size(), THIRD_STORE_ID, 0, 1, 1, 0);
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
    @Story("Order Event")
    @Test(description = "Отсутствие расчета surgelevel для outer-магазинов при distance > STORE_RADIUS",
            groups = "ondemand-surgelevel-smoke",
            dependsOnMethods = "surgeProduceEventOrderMultipleStores")
    public void surgeProduceEventOrderGreaterStoreRadius() {
        String shipmentUUid = UUID.randomUUID().toString();
        OrderEnrichment.EventOrderEnrichment eventOrder = getEventOrderEnrichment(SECOND_DELIVERY_AREA_ID, UUID.randomUUID().toString(), SECOND_STORE_ID, OrderEnrichment.EventOrderEnrichment.ShipmentStatus.NEW, "ON_DEMAND", shipmentUUid);
        kafka.publish(configCmdOrderEnrichment(), eventOrder);

        ThreadUtil.simplyAwait(surgeEventOutdate);

        List<Surgelevelevent.SurgeEvent> surgeLevelFirstStore = kafka.waitDataInKafkaTopicSurgeLevel(FIRST_STORE_ID);
        List<Surgelevelevent.SurgeEvent> surgeLevelSecondStore = kafka.waitDataInKafkaTopicSurgeLevel(SECOND_STORE_ID);
        List<Surgelevelevent.SurgeEvent> surgeLevelThirdStore = kafka.waitDataInKafkaTopicSurgeLevel(THIRD_STORE_ID);

        Allure.step("Проверка отправка нового события surgelevel в kafka для нескольких магазинов", () -> {
            checkSurgeLevelProduce(surgeLevelFirstStore, surgeLevelFirstStore.size(), FIRST_STORE_ID, 1, 2, 2, 0);
            checkSurgeLevelProduce(surgeLevelSecondStore, surgeLevelSecondStore.size(), SECOND_STORE_ID, 1, 2, 2, 0);
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

    @AfterClass(alwaysRun = true)
    public void postConditions() {
        deleteStore(STORE_ID);
        deleteStore(FIRST_STORE_ID);
        deleteStore(SECOND_STORE_ID);
        deleteStore(THIRD_STORE_ID);
    }
}
