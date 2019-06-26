package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.*;
import ru.instamart.autotests.application.Config;
import ru.instamart.autotests.appmanager.ApplicationManager;
import ru.instamart.autotests.application.Pages;
import ru.instamart.autotests.models.ElementData;
import ru.instamart.autotests.models.EnvironmentData;
import ru.instamart.autotests.models.UserData;
import ru.instamart.autotests.testdata.generate;

import static ru.instamart.autotests.application.Config.doCleanupAfterTestRun;
import static ru.instamart.autotests.appmanager.HelperBase.message;
import static ru.instamart.autotests.appmanager.HelperBase.verboseMessage;

public class TestBase {

    protected static final ApplicationManager kraken = new ApplicationManager(Config.browser);

    @BeforeSuite(alwaysRun = true)
    public void start() throws Exception {
        kraken.rise();
    }

    @AfterSuite(alwaysRun = true)
    public void cleanup() throws Exception {
        if(doCleanupAfterTestRun) {
            kraken.cleanup().all();
        }
        kraken.stop();
    }

    /** Проверить валидность элемента (преход работает + целевая страница доступна) */
    void validateTransition(ElementData element) {
        assertTransition(element);
        assertPageIsAvailable();
    }

    /** Проверить работоспособность перехода по ссылке элемента */
    void assertTransition(ElementData element) {
        verboseMessage("Проверяем > " + element.getDescription());
        String startPage = kraken.grab().currentURL();
        kraken.perform().click(element);
        Assert.assertFalse(
                kraken.grab().currentURL().equalsIgnoreCase(startPage),
                    "Не работает " + element.getDescription() + " (" + element.getLocator().toString().substring(3) + ") на странице " + startPage
                            + "\n Нет перехода, остались на той же странице");
        verboseMessage("✓ Успешный переход");
        // TODO добаить проверку на соответствие currentURL и targetURL, для этого добавить targetURL в ElementData
    }

    /**  Проверить возможность перехода на страницу */
    void assertTransition(Pages page) {
        assertTransition(kraken.grab().fullBaseUrl + Pages.getPagePath());
    }

    /** Проверить возможность перехода на страницу по указанному url */
    void assertTransition(String URL) {
        message("Переход по прямой ссылке " + URL);
        kraken.get().url(URL);
        Assert.assertTrue(
                kraken.grab().currentURL().equalsIgnoreCase(URL),
                    "Невозможно перейти на страницу " + URL + " по прямой ссылке\n"
                        + "Вместо нее попадаем на " + kraken.grab().currentURL() + "\n"
        );
        message("✓ Успешно");
    }

    /** Проверить возможность перехода на страницу и ее доступность */
    void assertPageIsAvailable(Pages page) throws AssertionError {
        assertPageIsAvailable(kraken.grab().fullBaseUrl + Pages.getPagePath());
    }

    /** Проверить возможность перехода на страницу по указанному url и ее доступность */
    void assertPageIsAvailable(String URL) throws AssertionError {
        assertTransition(URL);
        assertPageIsAvailable();
    }

    /** Проверить доступность текущей страницы */
    void assertPageIsAvailable() throws AssertionError {
        String page = kraken.grab().currentURL();
        Assert.assertFalse(kraken.detect().is404(), "Ошибка 404 на странице " + page + "\n");
        Assert.assertFalse(kraken.detect().is500(), "Ошибка 500 на странице " + page + "\n");
        Assert.assertFalse(kraken.detect().is502(), "Ошибка 502 на странице " + page + "\n");
        verboseMessage("✓ Страница доступна\n(" + page + ")\n");
    }

    /** Проверить что текущая страница недоступна с ошибкой 404 */
    void assertPageIs404() throws AssertionError {
        Assert.assertTrue(kraken.detect().is404(), "Нет ожидаемой ошибки 404 на странице " + kraken.grab().currentURL() + "\n");
    }

    /** Проверить возможность перехода на страницу и ее недоступность с ошибкой 404 */
    void assertPageIs404(Pages page) throws AssertionError {
        assertPageIs404(kraken.grab().fullBaseUrl + Pages.getPagePath());
    }

    /** Проверить возможность перехода на страницу по указанному url и ее недоступность с ошибкой 404 */
    void assertPageIs404(String URL) throws AssertionError {
        assertTransition(URL);
        assertPageIs404();
    }

    /** Проверка недоступности страницы для перехода */
    void assertPageIsUnavailable(Pages page) throws AssertionError {
        assertPageIsUnavailable(kraken.grab().fullBaseUrl + Pages.getPagePath());
    }

    /** Проверка недоступности страницы для перехода по прямой ссылке */
    void assertPageIsUnavailable(String URL) throws AssertionError {
        message("Проверяем недоступность перехода на страницу " + URL);
        kraken.get().url(URL);
        Assert.assertFalse(
                kraken.grab().currentURL().equalsIgnoreCase(URL),
                    "Можно попасть на страницу " + kraken.grab().currentURL() + " по прямой ссылке\n");
    }

    /** Проверка доступности зокументации заказа */
    void assertOrderDocumentsAvailable() {
        kraken.check().orderDocuments();
        assertPageIsAvailable();
    }

    /** Пропуск теста */
    void skip() throws SkipException{
        message("Пропускаем тест");
            throw new SkipException("Пропускаем тест");
    }

    /** Пропуск теста на окружении */
    void skipOn(EnvironmentData environment) throws SkipException{
        if (kraken.detect().environment(environment)) {
            message("Пропускаем тест на окружении " + environment.getName());
                throw new SkipException("Пропускаем тест");
        }
    }

    /** Пропуск теста на тенанте */
    void skipOn(String tenant) throws SkipException {
        if (kraken.detect().tenant(tenant)) {
            message("Пропускаем тест для тенанта " + tenant);
                throw new SkipException("Пропускаем тест");
        }
    }

    /** Прогон теста только на указанном окружении */
    void testOn(EnvironmentData environment) throws SkipException{
        if (!kraken.detect().environment(environment)) {
            message("Тест только для окружения " + environment.getName());
            throw new SkipException("Пропускаем тест");
        }
    }

    // TODO testOn(tenant)

    @DataProvider
    Object[][] generateUserData() {
    UserData testuser = generate.testCredentials("user");
    return new Object[][] {{testuser}};
    }
}
