package ru.instamart.tests;

import com.google.common.collect.ImmutableMap;
import instamart.core.common.AppManager;
import instamart.core.helpers.ConsoleOutputCapturerHelper;
import instamart.core.helpers.HelperBase;
import instamart.core.settings.Config;
import instamart.core.testdata.ui.Generate;
import instamart.ui.common.pagesdata.EnvironmentData;
import instamart.ui.common.pagesdata.UserData;
import io.qameta.allure.Allure;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.*;

import static instamart.core.helpers.AllureHelper.allureEnvironmentWriter;
import static instamart.core.helpers.HelperBase.verboseMessage;

public class TestBase {

    protected static final AppManager kraken = new AppManager();

    private static final ConsoleOutputCapturerHelper CAPTURE_HELPER = new ConsoleOutputCapturerHelper();

    @BeforeSuite(groups = {
            "testing","sbermarket-Ui-smoke","MRAutoCheck","sbermarket-acceptance","sbermarket-regression",
            "metro-smoke","metro-acceptance","metro-regression"},
            description = "Выпускаем Кракена")
    public void start() throws Exception {
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
            "metro-smoke","metro-acceptance","metro-regression"},
            description = "Очищаем окружение от артефактов после тестов, завершаем процессы браузеров")
    public void cleanup() {
        if (Config.DO_CLEANUP_AFTER_TEST_RUN) {
            kraken.cleanup().all();
        }
        kraken.stop();
    }
    @AfterTest(alwaysRun = true,
               description = "Завершаем процессы браузеров")
    public void cleanupAfterTest(){
        kraken.stop();
    }

    @BeforeMethod(alwaysRun = true,description = "Стартуем запись системного лога")
    public void captureStart() {
        CAPTURE_HELPER.start();
    }

    @AfterMethod(alwaysRun = true,description = "Добавляем системный лог к тесту")
    public void captureFinish() {
        Allure.addAttachment("Системный лог теста", CAPTURE_HELPER.stop());
    }

    @AfterMethod(alwaysRun = true,description = "Прикрепляем скриншот интерфейса, если UI тест упал")
    public void screenShot(final ITestResult result) {
        if (!result.isSuccess()){
            HelperBase.takeScreenshot();
        }
    }

    /** Метод-обертка для красивого вывода ошибок зафейленных тестов */
    protected String failMessage(String text) {
        return "\n\n> " + text + "\n\n";
    }

    /** Пропуск теста */
    public void skipTest() throws SkipException{
        verboseMessage("Пропускаем тест");
            throw new SkipException("Пропускаем тест");
    }

    /** Пропуск теста на окружении */
    public void skipTestOnEnvironment(String environment) throws SkipException {
        if (kraken.detect().environment(environment)) {
            verboseMessage("Пропускаем тест на окружении " + environment);
                throw new SkipException("Пропускаем тест");
        }
    }

    /** Пропуск теста на тенанте */
    public void skipTestOnTenant(String tenant) throws SkipException {
        if (kraken.detect().tenant(tenant)) {
            verboseMessage("Пропускаем тест для тенанта " + tenant);
            throw new SkipException("Пропускаем тест");
        }
    }

    /** Пропуск теста на сервере */
    public void skipTestOnServer(String server) throws SkipException {
        if (kraken.detect().server(server)) {
            verboseMessage("Пропускаем тест на " + server);
            throw new SkipException("Пропускаем тест");
        }
    }

    /** Проведение теста только на указанном окружении */
    public void runTestOnlyOnEnvironment(String environment) throws SkipException {
        if (!kraken.detect().environment(environment)) {
            verboseMessage("Тест только для окружения " + environment);
            throw new SkipException("Пропускаем тест");
        }
    }

    /** Проведение теста только на указанном тенанте */
    public void runTestOnlyOnTenant(String tenant) {
        if (!kraken.detect().tenant(tenant)) {
            verboseMessage("Тест только для тенанта " + tenant);
            throw new SkipException("Пропускаем тест");
        }
    }

    /** Проведение теста только на указанном сервере */
    public void runTestOnlyOnServer(String server) {
        if (!kraken.detect().server(server)) {
            verboseMessage("Тест только для " + server);
            throw new SkipException("Пропускаем тест");
        }
    }

    @DataProvider
    Object[][] generateUserData() {
    UserData testuser = Generate.testCredentials("user");
    return new Object[][] {{testuser}};
    }
}
