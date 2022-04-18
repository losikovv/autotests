package ru.instamart.api.common;

import io.grpc.ManagedChannel;
import io.qameta.allure.Allure;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import ru.instamart.api.helper.*;
import ru.instamart.grpc.helper.GrpcHelper;
import ru.instamart.jdbc.dao.stf.SpreeUsersDao;
import ru.instamart.kafka.helper.KafkaHelper;
import ru.instamart.kafka.helper.LogHelper;
import ru.instamart.kraken.config.EnvironmentProperties;

import static ru.instamart.kraken.helper.LogbackLogBuffer.clearLogbackLogBuffer;
import static ru.instamart.kraken.helper.LogbackLogBuffer.getLogbackBufferLog;

public class RestBase {
    protected static final ApiV1Helper apiV1 = new ApiV1Helper();
    protected static final ApiV2Helper apiV2 = new ApiV2Helper();
    protected static final ShopperAppApiHelper shopperApp = new ShopperAppApiHelper();
    protected static final ApiV3Helper apiV3 = new ApiV3Helper();
    protected static final DeliveryClubHelper dc = new DeliveryClubHelper();
    protected static final AdminHelper admin = new AdminHelper();
    protected final ShopperAdminApiHelper shopperAdmin = new ShopperAdminApiHelper();
    protected Response response;
    protected ManagedChannel channel;
    protected GrpcHelper grpc = new GrpcHelper();
    protected static final KafkaHelper kafka = new KafkaHelper();
    protected static final LogHelper kubeLog = new LogHelper();
    protected static final ShiftsApiHelper shiftsApi = new ShiftsApiHelper();

    @AfterSuite(alwaysRun = true)
    public void clearData() {
        if (!EnvironmentProperties.SERVER.equals("production")) {
            SpreeUsersDao.INSTANCE.deleteQAUsers();
        }
    }

    @AfterMethod(alwaysRun = true, description = "Добавляем системный лог к тесту")
    public void captureFinish() {
        final String result = getLogbackBufferLog();
        clearLogbackLogBuffer();
        Allure.addAttachment("Системный лог теста", result);
    }
}
