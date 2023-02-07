package ru.instamart.api.common;

import io.grpc.ManagedChannel;
import io.qameta.allure.Allure;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import ru.instamart.api.helper.KafkaHelper;
import ru.instamart.grpc.checkpoints.GrpcCheckpoints;
import ru.instamart.grpc.helper.GrpcHelper;

import static org.apache.commons.lang3.RandomUtils.nextInt;
import static ru.instamart.api.helper.SurgeLevelHelper.*;
import static ru.instamart.kraken.helper.LogbackLogBuffer.clearLogbackLogBuffer;
import static ru.instamart.kraken.helper.LogbackLogBuffer.getLogbackBufferLog;

@Slf4j
public class SurgeLevelBase {
    protected ManagedChannel channel;
    protected GrpcHelper grpc = new GrpcHelper();
    protected GrpcCheckpoints check = new GrpcCheckpoints();
    protected static final KafkaHelper kafka = new KafkaHelper();
    protected static final int FIRST_DELIVERY_AREA_ID = nextInt(100000, 150000);
    protected static final int SECOND_DELIVERY_AREA_ID = FIRST_DELIVERY_AREA_ID + 1;
    protected final String FORMULA_ID = "10000000-1000-1000-1000-100000000000";
    protected final int SHORT_TIMEOUT = 3;
    protected final int MEDIUM_TIMEOUT = 10;
    protected final int LONG_TIMEOUT = 30;
    protected final float BASE_SURGELEVEL = 100; //см. формулу, которую используем для тестов
    protected final int BASE_SURGE_OUTDATE = 10;

    @BeforeSuite(alwaysRun = true, description = "Добавляем общие тестовые данные")
    public void addTestData() {
        checkFormula(FORMULA_ID);
    }

    @AfterMethod(alwaysRun = true, description = "Добавляем системный лог к тесту")
    public void captureFinish() {
        final String result = getLogbackBufferLog();
        clearLogbackLogBuffer();
        Allure.addAttachment("Системный лог теста", result);
    }
}
