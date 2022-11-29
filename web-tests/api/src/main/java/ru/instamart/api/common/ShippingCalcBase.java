package ru.instamart.api.common;

import io.qameta.allure.Allure;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.instamart.api.helper.KafkaHelper;
import ru.instamart.grpc.helper.GrpcHelper;
import ru.instamart.jdbc.dao.shippingcalc.BindingRulesDao;
import ru.instamart.jdbc.dao.shippingcalc.StrategiesDao;
import ru.instamart.jdbc.dao.shippingcalc.SurgeThresholdsDao;
import ru.instamart.jdbc.dao.shippingcalc.SwitchbackExperimentsDao;
import ru.instamart.jdbc.entity.shippingcalc.IntervalsSurgeEntity;
import ru.instamart.jdbc.entity.shippingcalc.SurgeThresholdsEntity;
import ru.instamart.jdbc.entity.shippingcalc.SwitchbackExperimentsEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static ru.instamart.kraken.helper.LogbackLogBuffer.clearLogbackLogBuffer;
import static ru.instamart.kraken.helper.LogbackLogBuffer.getLogbackBufferLog;
import static ru.instamart.kraken.util.TimeUtil.getDateWithoutTimezone;
import static ru.instamart.kraken.util.TimeUtil.getZoneDbDatePlusMinutes;

public class ShippingCalcBase {

    protected Response response;
    protected GrpcHelper grpc = new GrpcHelper();
    protected static final KafkaHelper kafka = new KafkaHelper();
    protected Integer minCartAmountFirst = 100000;
    protected Integer minCartAmountSecond = minCartAmountFirst - 1;
    protected Integer minCartAmountThird = minCartAmountSecond - 1;
    protected Integer minCartAmountFourth = minCartAmountThird - 1;
    protected Integer minCartAmountGlobal = minCartAmountFirst + 1;
    protected Integer deliveryPriceGlobal = 200000;
    protected List<IntervalsSurgeEntity> intervalsList;
    protected List<Integer> bindingRulesList;
    protected List<Integer> globalStrategiesList;
    protected final int SURGE_LEVEL = 5;
    protected final int SURGE_LEVEL_ADDITION_DEFAULT = 10000;
    protected final int SURGE_LEVEL_PERCENT_ADDITION = 10;
    protected final int SURGE_LEVEL_THRESHOLD_ADDITION_DIFF = 1;
    protected final int SURGE_LEVEL_SWITCHBACK_ADDITION_DIFF = SURGE_LEVEL_THRESHOLD_ADDITION_DIFF + 1;
    protected static final int REGION_ID_WITH_SWITCHBACK = nextInt(100000, 150000);
    protected static final int REGION_ID_WITH_FUTURE_SWITCHBACK = REGION_ID_WITH_SWITCHBACK + 1;
    protected static final int REGION_ID_WITH_THRESHOLDS = nextInt(150000, 200000);
    protected static final int REGION_ID_WITHOUT_THRESHOLDS = REGION_ID_WITH_THRESHOLDS + 1;
    protected final String FIXED_SCRIPT_NAME = "Фиксированная цена, с подсказками и объяснением";
    protected final String COMPLEX_SCRIPT_NAME = "Цена с учётом сложности, с подсказками и объяснением";
    protected final String FIXED_SCRIPT_PARAMS = "{\"basicPrice\": \"%s\", \"bagIncrease\": \"0\", \"assemblyIncrease\": \"0\"}";
    protected final String COMPLEX_SCRIPT_PARAMS = "{\"baseMass\": \"30000\", \"basicPrice\": \"%s\", \"bagIncrease\": \"0\", \"basePositions\": \"30\", \"additionalMass\": \"1000\", \"assemblyIncrease\": \"0\", \"additionalPositions\": \"5\", \"additionalMassIncrease\": \"500\", \"additionalPositionsIncrease\": \"0\"}";
    protected final String REDIS_VALUE = "{\"StoreID\":\"%s\",\"Method\":1,\"PastSurgeLever\":%d,\"PresentSurgeLevel\":%d,\"FutureSurgeLevel\":%d,\"StartedAt\":\"%s\",\"StepSurgeLevel\":1}";
    protected final String SURGE_THRESHOLD_PARAMETERS = "{\"intervals\": [{\"left_boundary\": 0, \"right_boundary\": 1, \"price_addition\": 0, \"percent_addition\": 0, \"min_cart_addition\": 0}, {\"left_boundary\": 1, \"right_boundary\": %s, \"price_addition\": %s, \"percent_addition\": %s, \"min_cart_addition\": %s}, {\"left_boundary\": %s, \"right_boundary\": 10, \"price_addition\": 20000, \"percent_addition\": 20, \"min_cart_addition\": 20000}]}";
    protected final String SURGE_PLANNED_THRESHOLD_PARAMETERS = "{\"intervals\": [{\"left_boundary\": 0, \"right_boundary\": 1, \"price_addition\": 0, \"percent_addition\": 0, \"min_cart_addition\": 0}, {\"left_boundary\": 1, \"right_boundary\": %s, \"price_addition\": %s, \"percent_addition\": %s, \"min_cart_addition\": %s}, {\"left_boundary\": %s, \"right_boundary\": 10, \"price_addition\": 20000, \"percent_addition\": 20, \"min_cart_addition\": 20000}], \"allow_min_cart_for_planned\": true}";

//    @BeforeSuite(alwaysRun = true, description = "Устанавливаем интервалы surge")
//    public void setTestSurgeIntervals() {
//        intervalsList = IntervalsSurgeDao.INSTANCE.getIntervals();
//        IntervalsSurgeDao.INSTANCE.clearIntervals();
//
//        List<IntervalsSurgeEntity> newIntervalsList = asList(
//                new IntervalsSurgeEntity() {{
//                    setLeftBoundary(0);
//                    setRightBoundary(1);
//                    setPriceAddition(0);
//                    setPercentAddition(0);
//                    setMinCartAddition(0);
//                }},
//                new IntervalsSurgeEntity() {{
//                    setLeftBoundary(1);
//                    setRightBoundary(surgeLevel);
//                    setPriceAddition(surgeLevelAddition);
//                    setPercentAddition(surgeLevelPercentAddition);
//                    setMinCartAddition(surgeLevelAddition);
//                }},
//                new IntervalsSurgeEntity() {{
//                    setLeftBoundary(surgeLevel);
//                    setRightBoundary(10);
//                    setPriceAddition(20000);
//                    setPercentAddition(20);
//                    setMinCartAddition(20000);
//                }});
//
//        IntervalsSurgeDao.INSTANCE.setIntervals(newIntervalsList);
//    }

    @BeforeSuite(alwaysRun = true, description = "Убираем автобиндер")
    public void deactivateBindingRules() {
        bindingRulesList = BindingRulesDao.INSTANCE.getActiveBindingRulesList();
        if (!bindingRulesList.isEmpty()) {
            BindingRulesDao.INSTANCE.updateBindingRulesState(getDateWithoutTimezone(), bindingRulesList);
        }
    }

    @BeforeSuite(alwaysRun = true, description = "Устанавливаем Switchback & Surge Threshold")
    public void setSurgeThreshold() {
        boolean defaultRegion = SurgeThresholdsDao.INSTANCE.setSurgeThreshold(
                SurgeThresholdsEntity.builder()
                        .regionId(0)
                        .parameters(String.format(SURGE_THRESHOLD_PARAMETERS,
                                SURGE_LEVEL, SURGE_LEVEL_ADDITION_DEFAULT, SURGE_LEVEL_PERCENT_ADDITION, SURGE_LEVEL_ADDITION_DEFAULT, SURGE_LEVEL))
                        .build());
        boolean customRegion = SurgeThresholdsDao.INSTANCE.setSurgeThreshold(
                SurgeThresholdsEntity.builder()
                        .regionId(REGION_ID_WITH_THRESHOLDS)
                        .parameters(String.format(SURGE_THRESHOLD_PARAMETERS,
                                SURGE_LEVEL, SURGE_LEVEL_ADDITION_DEFAULT + SURGE_LEVEL_THRESHOLD_ADDITION_DIFF, SURGE_LEVEL_PERCENT_ADDITION, SURGE_LEVEL_ADDITION_DEFAULT + SURGE_LEVEL_THRESHOLD_ADDITION_DIFF, SURGE_LEVEL))
                        .build());
        boolean switchbackRegion = SwitchbackExperimentsDao.INSTANCE.setSwitchbackExperiments(
                SwitchbackExperimentsEntity.builder()
                        .startDateTime(getZoneDbDatePlusMinutes(0))
                        .endDateTime(getZoneDbDatePlusMinutes(20))
                        .regionId(REGION_ID_WITH_SWITCHBACK)
                        .group("test")
                        .parameters(String.format(SURGE_PLANNED_THRESHOLD_PARAMETERS,
                                SURGE_LEVEL, SURGE_LEVEL_ADDITION_DEFAULT + SURGE_LEVEL_SWITCHBACK_ADDITION_DIFF, SURGE_LEVEL_PERCENT_ADDITION, SURGE_LEVEL_ADDITION_DEFAULT + SURGE_LEVEL_SWITCHBACK_ADDITION_DIFF, SURGE_LEVEL))
                        .build());
        boolean futureSwitchbackRegion = SwitchbackExperimentsDao.INSTANCE.setSwitchbackExperiments(
                SwitchbackExperimentsEntity.builder()
                        .startDateTime(getZoneDbDatePlusMinutes(20))
                        .endDateTime(getZoneDbDatePlusMinutes(30))
                        .regionId(REGION_ID_WITH_FUTURE_SWITCHBACK)
                        .group("test")
                        .parameters(String.format(SURGE_THRESHOLD_PARAMETERS,
                                SURGE_LEVEL, SURGE_LEVEL_ADDITION_DEFAULT + SURGE_LEVEL_SWITCHBACK_ADDITION_DIFF, SURGE_LEVEL_PERCENT_ADDITION, SURGE_LEVEL_ADDITION_DEFAULT + SURGE_LEVEL_SWITCHBACK_ADDITION_DIFF, SURGE_LEVEL))
                        .build());
        Allure.step("Смогли установить трешхолды для дефолтного региона = " + defaultRegion);
        Allure.step("Смогли установить трешхолды для кастомного региона = " + customRegion);
        Allure.step("Смогли установить свитчбек для региона = " + switchbackRegion);
        Allure.step("Смогли установить свитчбек в будущем для кастомного региона = " + futureSwitchbackRegion);
    }

    @BeforeSuite(alwaysRun = true, description = "Выключаем все глобальные стратегии")
    public void disableGlobalStrategies() {
        globalStrategiesList = StrategiesDao.INSTANCE.getGlobalStrategies();
        if (!globalStrategiesList.isEmpty()) {
            StrategiesDao.INSTANCE.updateStrategiesGlobalFlag(globalStrategiesList, false);
        }
    }

    @AfterMethod(alwaysRun = true, description = "Добавляем системный лог к тесту")
    public void captureFinish() {
        final String result = getLogbackBufferLog();
        clearLogbackLogBuffer();
        Allure.addAttachment("Системный лог теста", result);
    }

//    @AfterSuite(alwaysRun = true, description = "Возвращаем интервалы surge")
//    public void returnSurgeIntervals() {
//        IntervalsSurgeDao.INSTANCE.clearIntervals();
//        if (!intervalsList.isEmpty()) {
//            IntervalsSurgeDao.INSTANCE.setIntervals(intervalsList);
//        }
//    }

    @AfterSuite(alwaysRun = true, description = "Возвращаем автобиндер")
    public void returnBindingRules() {
        if (!bindingRulesList.isEmpty()) {
            BindingRulesDao.INSTANCE.updateBindingRulesState(null, bindingRulesList);
        }
    }

    @AfterSuite(alwaysRun = true, description = "Удаляем Surge Thresholds")
    public void deleteSurgeThresholds() {
        SurgeThresholdsDao.INSTANCE.deleteSurgeThresholds(
                Collections.singletonList(REGION_ID_WITH_THRESHOLDS)
        );
    }

    @AfterSuite(alwaysRun = true, description = "Возвращаем глобальные стратегии")
    public void returnGlobalStrategies() {
        if (!globalStrategiesList.isEmpty()) {
            StrategiesDao.INSTANCE.updateStrategiesGlobalFlag(globalStrategiesList, true);
        }
    }
}
