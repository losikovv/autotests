package ru.instamart.api.common;

import io.qameta.allure.Allure;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.AfterMethod;
import ru.instamart.api.helper.AdminHelper;

import static ru.instamart.kraken.helper.LogbackLogBuffer.clearLogbackLogBuffer;
import static ru.instamart.kraken.helper.LogbackLogBuffer.getLogbackBufferLog;

@Slf4j
public class SelfFeeBase {

    protected static final AdminHelper admin = new AdminHelper();

    @AfterMethod(alwaysRun = true, description = "Добавляем системный лог к тесту")
    public void captureFinish() {
        final String result = getLogbackBufferLog();
        clearLogbackLogBuffer();
        Allure.addAttachment("Системный лог теста", result);
    }
}
