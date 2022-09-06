package ru.instamart.test.api.on_demand.surgelevel;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import order_enrichment.OrderEnrichment;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.jdbc.dao.surgelevel.ConfigDao;
import ru.instamart.jdbc.dao.surgelevel.ConfigInheritanceDao;
import ru.instamart.jdbc.dao.surgelevel.StoreDao;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.util.ThreadUtil;
import ru.sbermarket.qase.annotation.CaseId;
import surgelevelevent.Surgelevelevent;

import java.util.List;
import java.util.UUID;

import static org.testng.Assert.assertEquals;
import static ru.instamart.api.helper.K8sHelper.getPaasServiceEnvProp;
import static ru.instamart.api.helper.SurgeLevelHelper.checkSurgeLevelProduce;
import static ru.instamart.api.helper.SurgeLevelHelper.getEventOrderEnrichment;
import static ru.instamart.kafka.configs.KafkaConfigs.configCmdOrderEnrichment;
import static ru.instamart.kraken.util.StringUtil.matchWithRegex;

@Epic("On Demand")
@Feature("SurgeLevel")
public class KafkaTest extends RestBase {

    private final String STORE_ID = UUID.randomUUID().toString();
    private final String CONFIG_ID = UUID.randomUUID().toString();
    private final String DEFAULT_CONFIG_ID = "00000000-1000-0000-0000-000000000000";
    private final String SHIPMENT_UUID = UUID.randomUUID().toString();
    private final String ORDER_UUID = UUID.randomUUID().toString();
    private final int DELIVERY_AREA_ID = 333; //пока просто рандомная цифра
    private boolean configCreated;
    private boolean storeCreated;
    private float currentSurgeLevel;
    private int currentDemandAmount;
    private int currentSupplyAmount;
    private float pastSurgeLevel;
    private int surgeEventsAmount;
    private boolean surgeEventProduceUnchanged;
    private int surgeEventOutdate;

    @BeforeClass(alwaysRun = true)
    public void preConditions() {
        configCreated = ConfigDao.INSTANCE.addConfig(CONFIG_ID);
        storeCreated = StoreDao.INSTANCE.addStore(STORE_ID, CONFIG_ID, true, 55.55f, 55.55f);
        ConfigInheritanceDao.INSTANCE.addConfigInheritance(CONFIG_ID, DEFAULT_CONFIG_ID, 1);

        List<String> serviceEnvProperties = getPaasServiceEnvProp(EnvironmentProperties.Env.SURGE_LEVEL_NAMESPACE, " | grep -e SURGEEVENT_PRODUCE_UNCHANGED -e SURGEEVENT_OUTDATE ");
        String envPropsStr = String.join("\n", serviceEnvProperties);
        String surgeEventProduceUnchangedStr = matchWithRegex("^SURGEEVENT_PRODUCE_UNCHANGED=(.\\w+)$", envPropsStr, 1);
        String surgeEventOutdateStr = matchWithRegex("^SURGEEVENT_OUTDATE=(.\\d+)s$", envPropsStr, 1);

        surgeEventProduceUnchanged = surgeEventProduceUnchangedStr.equals("true");
        surgeEventOutdate = !surgeEventOutdateStr.isBlank() ? Integer.parseInt(surgeEventOutdateStr) : 10;
    }

    @CaseId(14)
    @Story("Расчет surgelevel")
    @Test(description = "Расчет surgelevel при получении события EventOrder с новым ON_DEMAND заказом в активном статусе",
            groups = "ondemand-surgelevel-smoke")
    public void surgeProduceEventOrderActive() {
        OrderEnrichment.EventOrderEnrichment eventOrder = getEventOrderEnrichment(DELIVERY_AREA_ID, ORDER_UUID, STORE_ID, OrderEnrichment.EventOrderEnrichment.ShipmentStatus.NEW, "ON_DEMAND", SHIPMENT_UUID);
        kafka.publish(configCmdOrderEnrichment(), eventOrder.toByteArray());

        ThreadUtil.simplyAwait(surgeEventOutdate);

        List<Surgelevelevent.SurgeEvent> surgeLevels = kafka.waitDataInKafkaTopicSurgeLevel(STORE_ID);
        surgeEventsAmount = surgeLevels.size();

        pastSurgeLevel = currentSurgeLevel;
        currentSurgeLevel++;
        currentDemandAmount++;

        checkSurgeLevelProduce(surgeLevels, surgeEventsAmount, STORE_ID, pastSurgeLevel, currentSurgeLevel, currentDemandAmount, currentSupplyAmount);
    }

    @CaseId(38)
    @Story("Расчет surgelevel")
    @Test(description = "Расчет surgelevel при получении события EventOrder с ON_DEMAND заказом, который перестал быть активным",
            groups = "ondemand-surgelevel-regress",
            dependsOnMethods = "surgeProduceEventOrderActive")
    public void surgeProduceEventOrderNoLongerActive() {
        OrderEnrichment.EventOrderEnrichment eventOrder = getEventOrderEnrichment(DELIVERY_AREA_ID, ORDER_UUID, STORE_ID, OrderEnrichment.EventOrderEnrichment.ShipmentStatus.CANCELED, "ON_DEMAND", SHIPMENT_UUID);
        kafka.publish(configCmdOrderEnrichment(), eventOrder.toByteArray());

        ThreadUtil.simplyAwait(surgeEventOutdate);

        List<Surgelevelevent.SurgeEvent> surgeLevels = kafka.waitDataInKafkaTopicSurgeLevel(STORE_ID);
        surgeEventsAmount = surgeLevels.size();

        pastSurgeLevel = currentSurgeLevel;
        currentSurgeLevel--;
        currentDemandAmount--;

        checkSurgeLevelProduce(surgeLevels, surgeEventsAmount, STORE_ID, pastSurgeLevel, currentSurgeLevel, currentDemandAmount, currentSupplyAmount);
    }

    @CaseId(40)
    @Story("Расчет surgelevel")
    @Test(description = "Расчет surgelevel при получении события EventOrder с ранее полученным ON_DEMAND заказом в активном статусе",
            groups = "ondemand-surgelevel-regress")
    public void surgeProduceEventOrderRepeatActive() {

        if (!surgeEventProduceUnchanged) {
            throw new SkipException("Тест пропущен, так как SURGEEVENT_PRODUCE_UNCHANGED=false");
        }

        String orderUuid = UUID.randomUUID().toString();
        String shipmentUuid = UUID.randomUUID().toString();

        OrderEnrichment.EventOrderEnrichment eventOrder = getEventOrderEnrichment(DELIVERY_AREA_ID, orderUuid, STORE_ID, OrderEnrichment.EventOrderEnrichment.ShipmentStatus.NEW, "ON_DEMAND", shipmentUuid);
        kafka.publish(configCmdOrderEnrichment(), eventOrder.toByteArray());

        ThreadUtil.simplyAwait(surgeEventOutdate);

        List<Surgelevelevent.SurgeEvent> surgeLevels = kafka.waitDataInKafkaTopicSurgeLevel(STORE_ID);
        surgeEventsAmount = surgeLevels.size();

        pastSurgeLevel = currentSurgeLevel;
        currentSurgeLevel++;
        currentDemandAmount++;

        checkSurgeLevelProduce(surgeLevels, surgeEventsAmount, STORE_ID, pastSurgeLevel, currentSurgeLevel, currentDemandAmount, currentSupplyAmount);

        OrderEnrichment.EventOrderEnrichment eventOrderRepeatActive = getEventOrderEnrichment(DELIVERY_AREA_ID, orderUuid, STORE_ID, OrderEnrichment.EventOrderEnrichment.ShipmentStatus.AUTOMATIC_ROUTING, "ON_DEMAND", shipmentUuid);
        kafka.publish(configCmdOrderEnrichment(), eventOrderRepeatActive.toByteArray());

        ThreadUtil.simplyAwait(surgeEventOutdate);

        List<Surgelevelevent.SurgeEvent> surgeLevelsOnRepeatActive = kafka.waitDataInKafkaTopicSurgeLevel(STORE_ID);
        surgeEventsAmount = surgeLevelsOnRepeatActive.size();

        pastSurgeLevel = currentSurgeLevel;

        checkSurgeLevelProduce(surgeLevelsOnRepeatActive, surgeEventsAmount, STORE_ID, pastSurgeLevel, currentSurgeLevel, currentDemandAmount, currentSupplyAmount);
    }

    @CaseId(15)
    @Story("Расчет surgelevel")
    @Test(description = "Отсутствие расчета surgelevel при получении события EventOrder с новым ON_DEMAND заказом в не активном статусе",
            groups = "ondemand-surgelevel-regress")
    public void surgeProduceEventOrderNotActive() {
        OrderEnrichment.EventOrderEnrichment eventOrder = getEventOrderEnrichment(DELIVERY_AREA_ID, UUID.randomUUID().toString(), STORE_ID, OrderEnrichment.EventOrderEnrichment.ShipmentStatus.CANCELED, "ON_DEMAND", UUID.randomUUID().toString());
        kafka.publish(configCmdOrderEnrichment(), eventOrder.toByteArray());

        ThreadUtil.simplyAwait(surgeEventOutdate);

        List<Surgelevelevent.SurgeEvent> surgeLevels = kafka.waitDataInKafkaTopicSurgeLevel(STORE_ID);

        if (surgeEventProduceUnchanged) {
            surgeEventsAmount = surgeLevels.size();
            pastSurgeLevel = currentSurgeLevel;
            checkSurgeLevelProduce(surgeLevels, surgeEventsAmount, STORE_ID, pastSurgeLevel, currentSurgeLevel, currentDemandAmount, currentSupplyAmount);
        } else {
            Allure.step("Проверка отсутствия нового события surgelevel в kafka", () -> assertEquals(surgeLevels.size(), surgeEventsAmount, "Поступило новое событие"));
        }
    }

    @CaseId(16)
    @Story("Расчет surgelevel")
    @Test(description = "Отсутствие расчета surgelevel при получении события EventOrder с новым не ON_DEMAND заказом",
            groups = "ondemand-surgelevel-smoke")
    public void surgeProduceEventOrderNotOnDemand() {
        OrderEnrichment.EventOrderEnrichment eventOrder = getEventOrderEnrichment(DELIVERY_AREA_ID, UUID.randomUUID().toString(), STORE_ID, OrderEnrichment.EventOrderEnrichment.ShipmentStatus.NEW, "PLANNED", UUID.randomUUID().toString());
        kafka.publish(configCmdOrderEnrichment(), eventOrder.toByteArray());

        ThreadUtil.simplyAwait(surgeEventOutdate);

        List<Surgelevelevent.SurgeEvent> surgeLevels = kafka.waitDataInKafkaTopicSurgeLevel(STORE_ID);

        Allure.step("Проверка отсутствия нового события surgelevel в kafka", () -> assertEquals(surgeLevels.size(), surgeEventsAmount, "Поступило новое событие"));
    }

    @AfterClass(alwaysRun = true)
    public void postConditions() {
        if (storeCreated) {
            StoreDao.INSTANCE.delete(STORE_ID);
        }
        if (configCreated) {
            ConfigDao.INSTANCE.delete(CONFIG_ID);
        }
    }
}
