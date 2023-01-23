package ru.instamart.api.common;

import io.qameta.allure.Allure;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.instamart.api.helper.KafkaHelper;
import ru.instamart.grpc.helper.GrpcHelper;
import ru.instamart.jdbc.dao.shippingcalc.*;
import ru.instamart.jdbc.entity.shippingcalc.RetailersEntity;
import ru.instamart.jdbc.entity.shippingcalc.SurgeParametersEntity;
import ru.instamart.jdbc.entity.shippingcalc.SwitchbacksEntity;

import java.util.List;
import java.util.UUID;

import static org.apache.commons.lang3.RandomUtils.nextInt;
import static ru.instamart.kraken.helper.LogbackLogBuffer.clearLogbackLogBuffer;
import static ru.instamart.kraken.helper.LogbackLogBuffer.getLogbackBufferLog;
import static ru.instamart.kraken.util.TimeUtil.getDateWithoutTimezone;
import static ru.instamart.kraken.util.TimeUtil.getZonedUTCDatePlusMinutes;

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
    protected List<Integer> bindingRulesList;
    protected List<SwitchbacksEntity> switchbacksList;
    protected List<Integer> globalStrategiesList;
    protected final int SURGE_LEVEL = 5;
    protected final int SURGE_LEVEL_ADDITION_DEFAULT = 10000;
    protected final int SURGE_LEVEL_PERCENT_ADDITION = 10;
    protected final int SURGE_PARAMETERS_REGION_ADDITION_DIFF = 1;
    protected final int SURGE_PARAMETERS_VERTICAL_ADDITION_DIFF = SURGE_PARAMETERS_REGION_ADDITION_DIFF + 1;
    protected final int SURGE_PARAMETERS_ADDITION_DIFF = SURGE_PARAMETERS_VERTICAL_ADDITION_DIFF + 1;
    protected final int SURGE_SWITCHBACK_REGION_ADDITION_DIFF = SURGE_PARAMETERS_ADDITION_DIFF + 1;
    protected final int SURGE_SWITCHBACK_VERTICAL_ADDITION_DIFF = SURGE_SWITCHBACK_REGION_ADDITION_DIFF + 1;
    protected final int SURGE_SWITCHBACK_ADDITION_DIFF = SURGE_SWITCHBACK_VERTICAL_ADDITION_DIFF + 1;
    protected final int SURGE_SWITCHBACK_FUTURE_ADDITION_DIFF = SURGE_SWITCHBACK_ADDITION_DIFF + 1;
    protected static final int REGION_ID_WITH_SWITCHBACK = nextInt(100000, 150000);
    protected static final int REGION_ID_WITH_FUTURE_SWITCHBACK = REGION_ID_WITH_SWITCHBACK + 1;
    protected static final int REGION_ID_WITH_PARAMETERS = nextInt(150000, 200000);
    protected static final int REGION_ID_WITHOUT_PARAMETERS = REGION_ID_WITH_PARAMETERS + 1;
    protected static final int RETAILER_ID_WITH_SWITCHBACK = 1;
    protected static final int RETAILER_ID_WITHOUT_SWITCHBACK = REGION_ID_WITH_FUTURE_SWITCHBACK;
    protected static final int RETAILER_ID_WITH_PARAMETERS = 2;
    protected static final int RETAILER_ID_WITHOUT_PARAMETERS = REGION_ID_WITHOUT_PARAMETERS;
    protected static final int VERTICAL = 0;
    protected int surgeParameterId;
    protected int surgeParameterVerticalId;
    protected int surgeParameterRegionId;
    protected final String FIXED_SCRIPT_NAME = "Фиксированная цена, с подсказками и объяснением";
    protected final String COMPLEX_SCRIPT_NAME = "Цена с учётом сложности, с подсказками и объяснением";
    protected final String FIXED_SCRIPT_PARAMS = "{\"basicPrice\": \"%s\", \"bagIncrease\": \"0\", \"assemblyIncrease\": \"0\"}";
    protected final String COMPLEX_SCRIPT_PARAMS = "{\"baseMass\": \"30000\", \"basicPrice\": \"%s\", \"bagIncrease\": \"0\", \"basePositions\": \"30\", \"additionalMass\": \"1000\", \"assemblyIncrease\": \"0\", \"additionalPositions\": \"5\", \"additionalMassIncrease\": \"500\", \"additionalPositionsIncrease\": \"0\"}";
    protected final String REDIS_VALUE = "{\"StoreID\":\"%s\",\"Method\":1,\"PastSurgeLever\":%d,\"PresentSurgeLevel\":%d,\"FutureSurgeLevel\":%d,\"StartedAt\":\"%s\",\"StepSurgeLevel\":1}";
    protected final String SURGE_PARAMETERS = "{\"intervals\": [{\"left_boundary\": 0, \"right_boundary\": 1, \"price_addition\": 0, \"percent_addition\": 0, \"min_cart_addition\": 0}, {\"left_boundary\": 1, \"right_boundary\": %s, \"price_addition\": %s, \"percent_addition\": %s, \"min_cart_addition\": %s}, {\"left_boundary\": %s, \"right_boundary\": 10, \"price_addition\": 20000, \"percent_addition\": 20, \"min_cart_addition\": 20000}]}";
    protected final String SURGE_PLANNED_PARAMETERS = "{\"intervals\": [{\"left_boundary\": 0, \"right_boundary\": 1, \"price_addition\": 0, \"percent_addition\": 0, \"min_cart_addition\": 0}, {\"left_boundary\": 1, \"right_boundary\": %s, \"price_addition\": %s, \"percent_addition\": %s, \"min_cart_addition\": %s}, {\"left_boundary\": %s, \"right_boundary\": 10, \"price_addition\": 20000, \"percent_addition\": 20, \"min_cart_addition\": 20000}], \"allow_min_cart_for_planned\": true}";

    @BeforeSuite(alwaysRun = true, description = "Убираем автобиндер")
    public void deactivateBindingRules() {
        bindingRulesList = BindingRulesDao.INSTANCE.getActiveBindingRulesList();
        if (!bindingRulesList.isEmpty()) {
            BindingRulesDao.INSTANCE.updateBindingRulesState(getDateWithoutTimezone(), bindingRulesList);
        }
    }

    @BeforeSuite(alwaysRun = true, description = "Убираем свитчбеки")
    public void deactivateSwitchbacks() {
        switchbacksList = SwitchbacksDao.INSTANCE.getSwitchbacks();
        if (!switchbacksList.isEmpty()) {
            var currentTime = getDateWithoutTimezone();
            for (SwitchbacksEntity switchbacksEntity : switchbacksList) {
                SwitchbacksDao.INSTANCE.updateSwitchbackState(currentTime, switchbacksEntity.getId());
            }
        }
    }

    @BeforeSuite(alwaysRun = true, description = "Выключаем все глобальные стратегии")
    public void disableGlobalStrategies() {
        globalStrategiesList = StrategiesDao.INSTANCE.getGlobalStrategies();
        if (!globalStrategiesList.isEmpty()) {
            StrategiesDao.INSTANCE.updateStrategiesGlobalFlag(globalStrategiesList, false);
        }
    }

    @BeforeSuite(alwaysRun = true, description = "Добавляем тестовые данные")
    public void setSurgeParameters() {
        boolean retailerForParameters = RetailersDao.INSTANCE.addRetailer(
                RetailersEntity.builder()
                        .id(RETAILER_ID_WITH_PARAMETERS)
                        .uuid(UUID.randomUUID().toString())
                        .vertical(VERTICAL)
                        .build()
        );
        boolean retailerForSwitchback = RetailersDao.INSTANCE.addRetailer(
                RetailersEntity.builder()
                        .id(RETAILER_ID_WITH_SWITCHBACK)
                        .uuid(UUID.randomUUID().toString())
                        .vertical(VERTICAL + 1)
                        .build()
        );
        int globalSurgeParameterId = SurgeParametersDao.INSTANCE.setSurgeParameters(
                SurgeParametersEntity.builder()
                        .regionId(-1)
                        .vertical(-1)
                        .parameters(String.format(SURGE_PARAMETERS,
                                SURGE_LEVEL, SURGE_LEVEL_ADDITION_DEFAULT, SURGE_LEVEL_PERCENT_ADDITION, SURGE_LEVEL_ADDITION_DEFAULT, SURGE_LEVEL))
                        .build());
        surgeParameterRegionId = SurgeParametersDao.INSTANCE.setSurgeParameters(
                SurgeParametersEntity.builder()
                        .regionId(REGION_ID_WITH_PARAMETERS)
                        .vertical(-1)
                        .parameters(String.format(SURGE_PARAMETERS,
                                SURGE_LEVEL, SURGE_LEVEL_ADDITION_DEFAULT + SURGE_PARAMETERS_REGION_ADDITION_DIFF, SURGE_LEVEL_PERCENT_ADDITION, SURGE_LEVEL_ADDITION_DEFAULT + SURGE_PARAMETERS_REGION_ADDITION_DIFF, SURGE_LEVEL))
                        .build());
        surgeParameterVerticalId = SurgeParametersDao.INSTANCE.setSurgeParameters(
                SurgeParametersEntity.builder()
                        .regionId(-1)
                        .vertical(VERTICAL)
                        .parameters(String.format(SURGE_PARAMETERS,
                                SURGE_LEVEL, SURGE_LEVEL_ADDITION_DEFAULT + SURGE_PARAMETERS_VERTICAL_ADDITION_DIFF, SURGE_LEVEL_PERCENT_ADDITION, SURGE_LEVEL_ADDITION_DEFAULT + SURGE_PARAMETERS_VERTICAL_ADDITION_DIFF, SURGE_LEVEL))
                        .build());
        surgeParameterId = SurgeParametersDao.INSTANCE.setSurgeParameters(
                SurgeParametersEntity.builder()
                        .regionId(REGION_ID_WITH_PARAMETERS)
                        .vertical(VERTICAL)
                        .parameters(String.format(SURGE_PARAMETERS,
                                SURGE_LEVEL, SURGE_LEVEL_ADDITION_DEFAULT + SURGE_PARAMETERS_ADDITION_DIFF, SURGE_LEVEL_PERCENT_ADDITION, SURGE_LEVEL_ADDITION_DEFAULT + SURGE_PARAMETERS_ADDITION_DIFF, SURGE_LEVEL))
                        .build());
        boolean switchbackRegion = SwitchbacksDao.INSTANCE.setSwitchbacks(
                SwitchbacksEntity.builder()
                        .startDateTime(getZonedUTCDatePlusMinutes(0))
                        .endDateTime(getZonedUTCDatePlusMinutes(20))
                        .regionId(REGION_ID_WITH_SWITCHBACK)
                        .vertical(-1)
                        .testGroup("test")
                        .parameters(String.format(SURGE_PLANNED_PARAMETERS,
                                SURGE_LEVEL, SURGE_LEVEL_ADDITION_DEFAULT + SURGE_SWITCHBACK_REGION_ADDITION_DIFF, SURGE_LEVEL_PERCENT_ADDITION, SURGE_LEVEL_ADDITION_DEFAULT + SURGE_SWITCHBACK_REGION_ADDITION_DIFF, SURGE_LEVEL))
                        .build());
        boolean switchbackVertical = SwitchbacksDao.INSTANCE.setSwitchbacks(
                SwitchbacksEntity.builder()
                        .startDateTime(getZonedUTCDatePlusMinutes(0))
                        .endDateTime(getZonedUTCDatePlusMinutes(20))
                        .regionId(-1)
                        .vertical(VERTICAL + 1)
                        .testGroup("test")
                        .parameters(String.format(SURGE_PLANNED_PARAMETERS,
                                SURGE_LEVEL, SURGE_LEVEL_ADDITION_DEFAULT + SURGE_SWITCHBACK_VERTICAL_ADDITION_DIFF, SURGE_LEVEL_PERCENT_ADDITION, SURGE_LEVEL_ADDITION_DEFAULT + SURGE_SWITCHBACK_VERTICAL_ADDITION_DIFF, SURGE_LEVEL))
                        .build());
        boolean switchback = SwitchbacksDao.INSTANCE.setSwitchbacks(
                SwitchbacksEntity.builder()
                        .startDateTime(getZonedUTCDatePlusMinutes(0))
                        .endDateTime(getZonedUTCDatePlusMinutes(20))
                        .regionId(REGION_ID_WITH_SWITCHBACK)
                        .vertical(VERTICAL + 1)
                        .testGroup("test")
                        .parameters(String.format(SURGE_PLANNED_PARAMETERS,
                                SURGE_LEVEL, SURGE_LEVEL_ADDITION_DEFAULT + SURGE_SWITCHBACK_ADDITION_DIFF, SURGE_LEVEL_PERCENT_ADDITION, SURGE_LEVEL_ADDITION_DEFAULT + SURGE_SWITCHBACK_ADDITION_DIFF, SURGE_LEVEL))
                        .build());
        boolean futureSwitchback = SwitchbacksDao.INSTANCE.setSwitchbacks(
                SwitchbacksEntity.builder()
                        .startDateTime(getZonedUTCDatePlusMinutes(20))
                        .endDateTime(getZonedUTCDatePlusMinutes(30))
                        .regionId(REGION_ID_WITH_FUTURE_SWITCHBACK)
                        .vertical(VERTICAL + 1)
                        .testGroup("test")
                        .parameters(String.format(SURGE_PARAMETERS,
                                SURGE_LEVEL, SURGE_LEVEL_ADDITION_DEFAULT + SURGE_SWITCHBACK_FUTURE_ADDITION_DIFF, SURGE_LEVEL_PERCENT_ADDITION, SURGE_LEVEL_ADDITION_DEFAULT + SURGE_SWITCHBACK_FUTURE_ADDITION_DIFF, SURGE_LEVEL))
                        .build());

        Allure.step("Смогли установить ритейлер для проверки параметров = " + retailerForParameters);
        Allure.step("Смогли установить ритейлер для проверки свитчбеков = " + retailerForSwitchback);
        Allure.step("Смогли установить глобальные сюрдж-параметры = " + (globalSurgeParameterId > 0));
        Allure.step("Смогли установить сюрдж-параметр для региона = " + (surgeParameterRegionId > 0));
        Allure.step("Смогли установить сюрдж-параметр для вертикали = " + (surgeParameterVerticalId > 0));
        Allure.step("Смогли установить сюрдж-параметр = " + (surgeParameterId > 0));
        Allure.step("Смогли установить свитчбек для региона = " + switchbackRegion);
        Allure.step("Смогли установить свитчбек для вертикали = " + switchbackVertical);
        Allure.step("Смогли установить свитчбек = " + switchback);
        Allure.step("Смогли установить свитчбек в будущем = " + futureSwitchback);
    }

    @AfterMethod(alwaysRun = true, description = "Добавляем системный лог к тесту")
    public void captureFinish() {
        final String result = getLogbackBufferLog();
        clearLogbackLogBuffer();
        Allure.addAttachment("Системный лог теста", result);
    }

    @AfterSuite(alwaysRun = true, description = "Возвращаем автобиндер")
    public void returnBindingRules() {
        if (!bindingRulesList.isEmpty()) {
            BindingRulesDao.INSTANCE.updateBindingRulesState(null, bindingRulesList);
        }
    }

    @AfterSuite(alwaysRun = true, description = "Возвращаем свитчбеки")
    public void returnSwitchbacks() {
        if (!switchbacksList.isEmpty()) {
            for (SwitchbacksEntity switchbacksEntity : switchbacksList) {
                //SwitchbacksDao.INSTANCE.updateSwitchbackState(switchbacksEntity.getEndDateTime(), switchbacksEntity.getId());
                SwitchbacksDao.INSTANCE.setSwitchbacks(switchbacksEntity);
                //После SwitchbacksTest сносятся все свитчбеки, поэтому здесь они заново инсертятся,
                //если свитчбеки сноситься не будут, то можно использовать строку выше
            }
        }
    }

    @AfterSuite(alwaysRun = true, description = "Возвращаем глобальные стратегии")
    public void returnGlobalStrategies() {
        if (!globalStrategiesList.isEmpty()) {
            StrategiesDao.INSTANCE.updateStrategiesGlobalFlag(globalStrategiesList, true);
        }
    }

    @AfterSuite(alwaysRun = true, description = "Удаляем Surge Parameters")
    public void deleteSurgeThresholds() {
        if (surgeParameterId > 0 || surgeParameterRegionId > 0 || surgeParameterVerticalId > 0) {
            SurgeParametersDao.INSTANCE.deleteSurgeParameters(
                    List.of(surgeParameterId, surgeParameterRegionId, surgeParameterVerticalId)
            );
        }
    }
}
