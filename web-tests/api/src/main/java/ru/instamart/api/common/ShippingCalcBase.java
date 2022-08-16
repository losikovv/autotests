package ru.instamart.api.common;

import io.qameta.allure.Allure;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.instamart.api.helper.KafkaHelper;
import ru.instamart.grpc.helper.GrpcHelper;
import ru.instamart.jdbc.dao.shippingcalc.IntervalsSurgeDao;
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
    protected List<IntervalsSurgeEntity> intervalsList;

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
                }},
                new IntervalsSurgeEntity() {{
                    setLeftBoundary(1);
                    setRightBoundary(surgeLevel);
                    setPriceAddition(10000);
                    setPercentAddition(10);
                }},
                new IntervalsSurgeEntity() {{
                    setLeftBoundary(surgeLevel);
                    setRightBoundary(10);
                    setPriceAddition(20000);
                    setPercentAddition(20);
                }});

        IntervalsSurgeDao.INSTANCE.setIntervals(newIntervalsList);
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
}
