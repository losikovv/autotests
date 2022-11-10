package ru.instamart.api.common;

import io.qameta.allure.Allure;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.instamart.api.helper.KafkaHelper;
import ru.instamart.grpc.helper.GrpcHelper;
import ru.instamart.jdbc.dao.shippingcalc.IntervalsSurgeDao;
import ru.instamart.jdbc.dao.shippingcalc.StrategiesDao;
import ru.instamart.jdbc.entity.shippingcalc.IntervalsSurgeEntity;

import java.util.List;

import static java.util.Arrays.asList;
import static ru.instamart.kraken.helper.LogbackLogBuffer.clearLogbackLogBuffer;
import static ru.instamart.kraken.helper.LogbackLogBuffer.getLogbackBufferLog;

public class ShippingCalcBase {

    protected Response response;
    protected GrpcHelper grpc = new GrpcHelper();
    protected static final KafkaHelper kafka = new KafkaHelper();
    protected int surgeLevel = 5;
    protected int surgeLevelAddition = 10000;
    protected int surgeLevelPercentAddition = 10;
    protected List<IntervalsSurgeEntity> intervalsList;
    protected List<Integer> globalStrategiesList;
    protected final String FIXED_SCRIPT_NAME = "Фиксированная цена, с подсказками и объяснением";
    protected final String COMPLEX_SCRIPT_NAME = "Цена с учётом сложности, с подсказками и объяснением";
    protected final String FIXED_SCRIPT_PARAMS = "{\"basicPrice\": \"%s\", \"bagIncrease\": \"0\", \"assemblyIncrease\": \"0\"}";
    protected final String COMPLEX_SCRIPT_PARAMS = "{\"baseMass\": \"30000\", \"basicPrice\": \"%s\", \"bagIncrease\": \"0\", \"basePositions\": \"30\", \"additionalMass\": \"1000\", \"assemblyIncrease\": \"0\", \"additionalPositions\": \"5\", \"additionalMassIncrease\": \"500\", \"additionalPositionsIncrease\": \"0\"}";
    protected final String REDIS_VALUE = "{\"StoreID\":\"%s\",\"Method\":1,\"PastSurgeLever\":%d,\"PresentSurgeLevel\":%d,\"FutureSurgeLevel\":%d,\"StartedAt\":\"%s\",\"StepSurgeLevel\":1}";

    @BeforeSuite(alwaysRun = true, description = "Устанавливаем интервалы surge")
    public void setTestSurgeIntervals() {
        intervalsList = IntervalsSurgeDao.INSTANCE.getIntervals();
        IntervalsSurgeDao.INSTANCE.clearIntervals();

        List<IntervalsSurgeEntity> newIntervalsList = asList(
                new IntervalsSurgeEntity() {{
                    setLeftBoundary(0);
                    setRightBoundary(1);
                    setPriceAddition(0);
                    setPercentAddition(0);
                    setMinCartAddition(0);
                }},
                new IntervalsSurgeEntity() {{
                    setLeftBoundary(1);
                    setRightBoundary(surgeLevel);
                    setPriceAddition(surgeLevelAddition);
                    setPercentAddition(surgeLevelPercentAddition);
                    setMinCartAddition(surgeLevelAddition);
                }},
                new IntervalsSurgeEntity() {{
                    setLeftBoundary(surgeLevel);
                    setRightBoundary(10);
                    setPriceAddition(20000);
                    setPercentAddition(20);
                    setMinCartAddition(20000);
                }});

        IntervalsSurgeDao.INSTANCE.setIntervals(newIntervalsList);
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

    @AfterSuite(alwaysRun = true, description = "Возвращаем интервалы surge")
    public void returnSurgeIntervals() {
        IntervalsSurgeDao.INSTANCE.clearIntervals();
        if (!intervalsList.isEmpty()) {
            IntervalsSurgeDao.INSTANCE.setIntervals(intervalsList);
        }
    }

    @AfterSuite(alwaysRun = true, description = "Возвращаем глобальные стратегии")
    public void returnGlobalStrategies() {
        if (!globalStrategiesList.isEmpty()) {
            StrategiesDao.INSTANCE.updateStrategiesGlobalFlag(globalStrategiesList, true);
        }
    }
}
