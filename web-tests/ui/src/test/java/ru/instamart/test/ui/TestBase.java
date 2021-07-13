package ru.instamart.test.ui;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import ru.instamart.kraken.helper.LogAttachmentHelper;
import ru.instamart.ui.manager.AppManager;
import ru.instamart.ui.report.CustomReport;

@Slf4j
public class TestBase {

    protected static final AppManager kraken = new AppManager();

    @BeforeMethod(alwaysRun = true)
    public void captureStart() {
        LogAttachmentHelper.start();
        log.info("Browser session id: {}", ((RemoteWebDriver) AppManager.getWebDriver()).getSessionId());
    }

    @AfterMethod(alwaysRun = true, description = "Вспомогательные отчеты")
    public void captureFinish(final ITestResult result) {
        CustomReport.addSystemLog();
        CustomReport.addCookieLog(result.getName());
        CustomReport.takeScreenshot();
        CustomReport.addLocalStorage();
        if (!result.isSuccess()) {
            CustomReport.addSourcePage();
            CustomReport.addBrowserLog();

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
}
