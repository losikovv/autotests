package ru.instamart.api.common;


import io.qameta.allure.Allure;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import ru.instamart.api.helper.*;
import ru.instamart.jdbc.dao.PhoneTokensDao;
import ru.instamart.kraken.config.EnvironmentProperties;

import static ru.instamart.kraken.helper.LogbackLogBuffer.clearLogbackLogBuffer;
import static ru.instamart.kraken.helper.LogbackLogBuffer.getLogbackBufferLog;

public class RestBase {
           // public static final PortForward.PortForwardResult connectionMySql = K8sPortForward.getInstance().portForwardMySQL();
//    public static final PortForward.PortForwardResult connectionPgSql = K8sPortForward.getInstance().portForwardPgSQL();
    protected static final InstamartApiHelper apiV2 = new InstamartApiHelper();
    protected static final ShopperAppApiHelper shopperApp = new ShopperAppApiHelper();
    protected static final ApiV3Helper apiV3 = new ApiV3Helper();
    protected static final DeliveryClubHelper dc = new DeliveryClubHelper();
    protected static final AdminHelper admin = new AdminHelper();
    protected final ShopperAdminApiHelper shopperAdmin = new ShopperAdminApiHelper();
    protected Response response;

    @AfterClass(alwaysRun = true)
    public void clearData() {
        if(!EnvironmentProperties.SERVER.equals("production")) {
            PhoneTokensDao.INSTANCE.deleteQAPhones();
        }
    }

    @AfterMethod(alwaysRun = true, description = "Добавляем системный лог к тесту")
    public void captureFinish() {
        final String result = getLogbackBufferLog();
        clearLogbackLogBuffer();
        Allure.addAttachment("Системный лог теста", result);
    }
}
