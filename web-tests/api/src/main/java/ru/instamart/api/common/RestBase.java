package ru.instamart.api.common;


import io.qameta.allure.Allure;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import ru.instamart.api.helper.*;
import ru.instamart.jdbc.dao.PhoneTokensDao;
import ru.instamart.jdbc.dao.SpreeUsersDao;
import ru.instamart.kraken.config.EnvironmentProperties;

import static java.util.Objects.nonNull;
import static ru.instamart.kraken.data.user.UserManager.getUserDataList;
import static ru.instamart.kraken.helper.LogbackLogBuffer.clearLogbackLogBuffer;
import static ru.instamart.kraken.helper.LogbackLogBuffer.getLogbackBufferLog;

public class RestBase {
    protected static final InstamartApiHelper apiV2 = new InstamartApiHelper();
    protected static final ShopperAppApiHelper shopperApp = new ShopperAppApiHelper();
    protected static final ApiV3Helper apiV3 = new ApiV3Helper();
    protected static final DeliveryClubHelper dc = new DeliveryClubHelper();
    protected static final AdminHelper admin = new AdminHelper();
    protected final ShopperAdminApiHelper shopperAdmin = new ShopperAdminApiHelper();
    protected Response response;

    @AfterSuite(alwaysRun = true)
    public void clearData() {
        if (!EnvironmentProperties.SERVER.equals("production")) {
            PhoneTokensDao.INSTANCE.deleteQAPhones();
            getUserDataList().forEach(userData -> {
                final String email = userData.getEmail();
                if (nonNull(email) && !email.isEmpty()) {
                    SpreeUsersDao.INSTANCE.deleteUserByEmail(email);
                }
            });
        }
    }

    @AfterMethod(alwaysRun = true, description = "Добавляем системный лог к тесту")
    public void captureFinish() {
        final String result = getLogbackBufferLog();
        clearLogbackLogBuffer();
        Allure.addAttachment("Системный лог теста", result);
    }
}
