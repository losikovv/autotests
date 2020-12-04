package ru.instamart.tests;

import com.google.common.collect.ImmutableMap;
import instamart.api.common.RestBase;
import instamart.core.common.AppManager;
import instamart.core.helpers.ConsoleOutputCapturerHelper;
import instamart.core.helpers.HelperBase;
import instamart.core.listeners.TmsListener;
import instamart.core.settings.Config;
import instamart.core.testdata.ui.Generate;
import instamart.ui.common.pagesdata.ElementData;
import instamart.ui.common.pagesdata.PageData;
import instamart.ui.common.pagesdata.UserData;
import instamart.ui.objectsmap.Elements;
import io.qameta.allure.Allure;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.*;

import static instamart.core.helpers.AllureHelper.allureEnvironmentWriter;
import static instamart.core.helpers.HelperBase.verboseMessage;

public class TestBase {

    protected static final AppManager kraken = new AppManager();

    private static ConsoleOutputCapturerHelper capture = new ConsoleOutputCapturerHelper();
    @BeforeSuite(groups = {
            "testing","sbermarket-Ui-smoke","sbermarket-acceptance","sbermarket-regression",
            "metro-smoke","metro-acceptance","metro-regression"},
            description = "Выпускаем Кракена")
    public void start() throws Exception {
        kraken.rise();
        new RestBase().initSpec();
        allureEnvironmentWriter(
                ImmutableMap.<String, String>builder()
                        .put("Browser", Config.DEFAULT_BROWSER)
                        .put("Browser.Version", AppManager.browserData.getVersion())
                        .put("Operation system", AppManager.browserData.getOs())
                        .put("Tenant", AppManager.environment.getTenant())
                        .put("URL", AppManager.environment.getBasicUrl())
                        .put("Administration", AppManager.environment.getAdminUrl())
                        .put("Shopper", AppManager.environment.getShopperUrl())
                        .build(), System.getProperty("user.dir")
                        + "/build/allure-results/");
        TmsListener.setProjectCode("STF");
    }

    @AfterSuite(groups = {
            "testing", "sbermarket-Ui-smoke","sbermarket-acceptance","sbermarket-regression",
            "metro-smoke","metro-acceptance","metro-regression"},
            description = "Очищаем окружение от артефактов после тестов, завершаем процессы браузеров")
    public void cleanup() {
        if(Config.DO_CLEANUP_AFTER_TEST_RUN) {
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
    public void captureStart(){
        capture.start();
    }
    @AfterMethod(alwaysRun = true,description = "Добавляем системный лог к тесту")
    public void captureFinish(){
        String value = capture.stop();
        Allure.addAttachment("Системный лог теста",value);
    }

    @AfterMethod(alwaysRun = true,description = "Прикрепляем скриншот интерфейса, если UI тест упал")
    public void screenShot(ITestResult result){
        if(!result.isSuccess()){
            HelperBase.takeScreenshot();
        }
    }

    /** Метод-обертка для красивого вывода ошибок зафейленных тестов */
    protected String failMessage(String text) {
        return "\n\n> " + text + "\n\n";
    }

    /** Проверить отсутствие элемента на странице */
    public void assertAbsence(ElementData element) {
        verboseMessage("Проверяем отсутствие элемента на странице " + kraken.grab().currentURL() + "\n> " + element.getLocator());

        Assert.assertFalse(
                kraken.detect().isElementPresent(element),
                    failMessage("Присутствует " + element.getDescription() + " на странице " + kraken.grab().currentURL()
                        + "\n> " + element.getLocator()));

        verboseMessage("✓ " + element.getDescription() + " отсутствует\n");
    }

    /** Проверить что поле пустое */
    public void assertFieldIsEmpty(ElementData element) {
        Assert.assertTrue(
                kraken.detect().isFieldEmpty(element),
                    failMessage("Не пустое " + element.getDescription()));
    }

    /** Проверить что поле не пустое */
    public void assertFieldIsNotEmpty(ElementData element) {
        Assert.assertFalse(
                kraken.detect().isFieldEmpty(element),
                    failMessage("Пустое " + element.getDescription()));
    }

    /** Проверить что поле заполнено ожидаемым текстом */
    public void assertFieldIsFilled(ElementData element, String expectedText) {
        Assert.assertEquals(
                kraken.grab().value(element), expectedText,
                    failMessage("Некорректно заполнено " + element.getDescription()));
    }

    /** Проверить что чекбокс проставлен */
    public void assertCheckboxIsSet(ElementData element) {
        Assert.assertTrue(
                kraken.detect().isCheckboxSet(element),
                    failMessage("Не проставлен " + element.getDescription()));
    }

    /** Проверить что чекбокс не проставлен */
    public void assertCheckboxIsNotSet(ElementData element) {
        Assert.assertFalse(
                kraken.detect().isCheckboxSet(element),
                    failMessage("Проставлен " + element.getDescription()));
    }

    /** Проверить что радиокнопка выбрана */
    public void assertRadioButtonIsSelected(ElementData element) {
        Assert.assertTrue(
                kraken.detect().isRadioButtonSelected(element),
                    failMessage("Не выбрана " + element.getDescription()));
    }

    /** Проверить что радиокнопка не выбрана */
    public void assertRadioButtonIsNotSelected(ElementData element) {
        Assert.assertFalse(
                kraken.detect().isRadioButtonSelected(element),
                    failMessage("Выбрана " + element.getDescription()));
    }

    /** Проверка недоступности страницы для перехода */
    public void assertPageIsUnavailable(PageData page) {
        assertPageIsUnavailable(kraken.environment.getBasicUrlWithHttpAuth() + page.getPath());
    }

    /** Проверка недоступности страницы для перехода по прямой ссылке */
    public void assertPageIsUnavailable(String URL) {
        verboseMessage("Проверяем недоступность перехода на страницу " + URL);
        kraken.get().url(URL);
        Assert.assertFalse(
                kraken.grab().currentURL().equalsIgnoreCase(URL),
                    "\n\n> Можно попасть на страницу " + kraken.grab().currentURL() + " по прямой ссылке\n");
    }

    /** Проверка доступности документации заказа */
    public void assertOrderDocumentsAreAvailable() {
        assertOrderDocumentsAreDownloadable();
        //checkPageIsAvailable();
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


    /** Проверка скачивания документации на странице деталей заказа */
    public void assertOrderDocumentsAreDownloadable() {
        String paymentType = kraken.grab().shipmentPayment();
        orderDocuments(paymentType);
    }

    private void orderDocuments(String paymentType) {
        switch (paymentType) {
            case "Переводом":
                assertOrderDocumentIsDownloadable("Универсальный передаточный документ");
                kraken.await().simply(1);
                assertOrderDocumentIsDownloadable("Счет");
                kraken.await().simply(1);
                assertOrderDocumentIsDownloadable("Счет-фактура");
                kraken.await().simply(1);
                assertOrderDocumentIsDownloadable("Товарная накладная");
                break;
            default:
                assertOrderDocumentIsDownloadable("Универсальный передаточный документ");
                kraken.await().simply(1);
                assertOrderDocumentIsDownloadable("Счет-фактура");
                kraken.await().simply(1);
                assertOrderDocumentIsDownloadable("Товарная накладная");
                kraken.await().simply(1);
                break;
        }
    }

    public void assertOrderDocumentIsDownloadable(String docname) {
        ElementData docLink = Elements.UserProfile.OrderDetailsPage.document(docname);
        verboseMessage("Скачиваем : " + docname);
        if (kraken.detect().isElementPresent(docLink)) {
            kraken.perform().click(docLink);
        } else
            throw new AssertionError("Документ \"" + docname + "\" недоступен для скачивания\nна странице " + kraken.grab().currentURL());
    }
}
