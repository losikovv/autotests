package ru.instamart.api.common;

import io.qameta.allure.Allure;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import ru.instamart.jdbc.dao.stf.SpreeUsersDao;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserManager;

import static ru.instamart.kraken.helper.LogbackLogBuffer.clearLogbackLogBuffer;
import static ru.instamart.kraken.helper.LogbackLogBuffer.getLogbackBufferLog;

@Slf4j
public class ShadowcatRestBase {

    @AfterSuite(alwaysRun = true)
    public void clearData() {
        if (!EnvironmentProperties.Env.isProduction() && !UserManager.isUserDataEmpty()) {
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
