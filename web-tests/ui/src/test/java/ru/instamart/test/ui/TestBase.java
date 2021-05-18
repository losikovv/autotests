package ru.instamart.test.ui;

import io.qameta.allure.Allure;
import lombok.extern.slf4j.Slf4j;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import ru.instamart.ui.manager.AppManager;
import ru.instamart.kraken.helper.LogAttachmentHelper;
import ru.instamart.kraken.testdata.UserManager;
import ru.instamart.ui.report.CustomReport;

@Slf4j
public class TestBase {

    protected static final AppManager kraken = new AppManager();

    @BeforeMethod(alwaysRun = true,description = "Стартуем запись системного лога")
    public void captureStart() {
        LogAttachmentHelper.start();
    }

    @AfterMethod(alwaysRun = true,description = "Добавляем системный лог к тесту")
    public void captureFinish() {
        final String result = LogAttachmentHelper.getContent();
        LogAttachmentHelper.stop();
        Allure.addAttachment("Системный лог теста", result);
    }

    @AfterMethod(alwaysRun = true, description = "Прикрепляем скриншот интерфейса, если UI тест упал")
    public void screenShot(final ITestResult result) {
        if (!result.isSuccess()) {
            CustomReport.takeScreenshot();
        }
    }

    @AfterMethod(alwaysRun = true, description = "Прикрепляем логи браузера, если UI тест упал")
    public void browserLog(final ITestResult result) {
        if (!result.isSuccess()) {
            Allure.addAttachment("Браузерный лог теста", CustomReport.browserLog());
        }
    }

    @AfterMethod(alwaysRun = true, description = "Прикрепляем source страницы, если UI тест упал")
    public void sourcePage(final ITestResult result) {
        if (!result.isSuccess()) {
            Allure.addAttachment("Html со страницы", CustomReport.sourcePage());
        }
    }

    /** Метод-обертка для красивого вывода ошибок зафейленных тестов */
    protected String failMessage(String text) {
        return "\n\n> " + text + "\n\n";
    }

    /** Пропуск теста */
    public void skipTest() throws SkipException{
        log.warn("Пропускаем тест");
        throw new SkipException("Пропускаем тест");
    }

    /** Пропуск теста на окружении */
    public void skipTestOnEnvironment(String environment) throws SkipException {
        if (kraken.detect().environment(environment)) {
            log.warn("Пропускаем тест на окружении {}", environment);
            throw new SkipException("Пропускаем тест");
        }
    }

    /** Пропуск теста на тенанте */
    public void skipTestOnTenant(String tenant) throws SkipException {
        if (kraken.detect().tenant(tenant)) {
            log.warn("Пропускаем тест для тенанта {}", tenant);
            throw new SkipException("Пропускаем тест");
        }
    }

    /** Пропуск теста на сервере */
    public void skipTestOnServer(String server) throws SkipException {
        if (kraken.detect().server(server)) {
            log.warn("Пропускаем тест на {}", server);
            throw new SkipException("Пропускаем тест");
        }
    }

    /** Проведение теста только на указанном окружении */
    public void runTestOnlyOnEnvironment(String environment) throws SkipException {
        if (!kraken.detect().environment(environment)) {
            log.warn("Тест только для окружения {}", environment);
            throw new SkipException("Пропускаем тест");
        }
    }

    /** Проведение теста только на указанном тенанте */
    public void runTestOnlyOnTenant(String tenant) {
        if (!kraken.detect().tenant(tenant)) {
            log.warn("Тест только для тенанта {}", tenant);
            throw new SkipException("Пропускаем тест");
        }
    }

    /** Проведение теста только на указанном сервере */
    public void runTestOnlyOnServer(String server) {
        if (!kraken.detect().server(server)) {
            log.warn("Тест только для {}", server);
            throw new SkipException("Пропускаем тест");
        }
    }

    @DataProvider
    Object[][] generateUserData() {
    return new Object[][] {{UserManager.getUser()}};
    }
}
