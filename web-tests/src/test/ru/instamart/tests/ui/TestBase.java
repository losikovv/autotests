package ru.instamart.tests.ui;

import com.google.common.collect.ImmutableMap;
import instamart.core.common.AppManager;
import instamart.core.helpers.HelperBase;
import instamart.core.helpers.LogAttachmentHelper;
import instamart.core.settings.Config;
import instamart.core.testdata.UserManager;
import instamart.ui.common.pagesdata.EnvironmentData;
import io.qameta.allure.Allure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.*;

import static instamart.core.helpers.AllureHelper.allureEnvironmentWriter;

public class TestBase {

    private static final Logger log = LoggerFactory.getLogger(TestBase.class);
    protected static final AppManager kraken = new AppManager();

    @BeforeSuite(groups = {
            "testing","sbermarket-Ui-smoke","MRAutoCheck","sbermarket-acceptance","sbermarket-regression",
            "metro-smoke","metro-acceptance","metro-regression","admin-ui-smoke"},
            description = "Выпускаем Кракена")
    public void start() {
        kraken.rise();

        allureEnvironmentWriter(
                ImmutableMap.<String, String>builder()
                        .put("Browser", Config.DEFAULT_BROWSER)
                        .put("Browser.Version", AppManager.browserData.getVersion())
                        .put("Operation system", AppManager.browserData.getOs())
                        .put("Tenant", EnvironmentData.INSTANCE.getTenant())
                        .put("URL", EnvironmentData.INSTANCE.getBasicUrl())
                        .put("Administration", EnvironmentData.INSTANCE.getAdminUrl())
                        .put("Shopper", EnvironmentData.INSTANCE.getShopperUrl())
                        .build(), System.getProperty("user.dir")
                        + "/build/allure-results/");
    }

    @AfterSuite(groups = {
            "testing", "sbermarket-Ui-smoke","MRAutoCheck","sbermarket-acceptance","sbermarket-regression",
            "metro-smoke","metro-acceptance","metro-regression","admin-ui-smoke"},
            description = "Очищаем окружение от артефактов после тестов, завершаем процессы браузеров")
    public void cleanup() {
        kraken.stop();
    }

    @AfterTest(alwaysRun = true,
               description = "Завершаем процессы браузеров")
    public void cleanupAfterTest(){
        kraken.stop();
    }

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

    @AfterMethod(alwaysRun = true,description = "Прикрепляем скриншот интерфейса, если UI тест упал")
    public void screenShot(final ITestResult result) {
        if (!result.isSuccess()) HelperBase.takeScreenshot();
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
