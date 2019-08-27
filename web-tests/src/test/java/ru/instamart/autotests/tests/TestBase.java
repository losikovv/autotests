package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.*;
import ru.instamart.autotests.application.Config;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.appmanager.ApplicationManager;
import ru.instamart.autotests.appmanager.models.*;
import ru.instamart.autotests.testdata.generate;

import static ru.instamart.autotests.application.Config.CoreSettings.doCleanupAfterTestRun;
import static ru.instamart.autotests.appmanager.HelperBase.message;
import static ru.instamart.autotests.appmanager.HelperBase.verboseMessage;

public class TestBase {

    protected static final ApplicationManager kraken = new ApplicationManager(Config.CoreSettings.browser);

    @BeforeSuite(alwaysRun = true)
    public void start() throws Exception {
        kraken.rise();
    }

    @AfterSuite(alwaysRun = true)
    public void cleanup() {
        if(doCleanupAfterTestRun) {
            kraken.cleanup().all();
        }
        kraken.stop();
    }
    /** Метод-обертка для красивого вывода ошибок зафейленных тестов */
    protected String failMessage(String text) {
        return "\n\n> " + text;
    }

    /** Проверить наличие и видимость элемента на странице */
    public void assertElementPresence(ElementData element) {
        verboseMessage("Проверяем наличие элемента: " + element.getDescription() + "\n> " + element.getLocator());

        Assert.assertTrue(kraken.detect().isElementPresent(element),
                "Отсутствует " + element.getDescription() + " на странице " + kraken.grab().currentURL() + "\n");
        verboseMessage("✓ Успешно\n");
    }

    /** Проверить что поле пустое */
    public void assertFieldIsEmpty(ElementData element) {
        Assert.assertTrue(kraken.detect().isFieldEmpty(element),
                "Не пустое " + element.getDescription() + " >");
    }

    /** Проверить что поле не пустое */
    public void assertFieldIsNotEmpty(ElementData element) {
        Assert.assertFalse(kraken.detect().isFieldEmpty(element),
                "Пустое " + element.getDescription() + " >");
    }

    /** Проверить что поле заполнено ожидаемым текстом */
    public void assertFieldIsFilled(ElementData element, String expectedText) {
        Assert.assertEquals(kraken.grab().value(element), expectedText,
                "Некорректно заполнено " + element.getDescription() + " >");
    }

    /** Проверить что чекбокс проставлен */
    public void assertCheckboxIsSet(ElementData element) {
        Assert.assertTrue(kraken.detect().isCheckboxSet(element),
                "Не проставлен " + element.getDescription() + " >");
    }

    /** Проверить что чекбокс не проставлен */
    public void assertCheckboxIsNotSet(ElementData element) {
        Assert.assertFalse(kraken.detect().isCheckboxSet(element),
                "Проставлен " + element.getDescription() + " >");
    }

    /** Проверить что радиокнопка выбрана */
    public void assertRadioButtonIsSelected(ElementData element) {
        Assert.assertTrue(kraken.detect().isRadioButtonSelected(element),
                "Не выбрана " + element.getDescription() + " >");
    }

    /** Проверить что радиокнопка не выбрана */
    public void assertRadioButtonIsNotSelected(ElementData element) {
        Assert.assertFalse(kraken.detect().isRadioButtonSelected(element),
                "Выбрана " + element.getDescription() + " >");
    }

    /** Проверить валидность элемента (преход работает + целевая страница доступна) */
    public void validateTransition(ElementData element) {
        assertTransition(element);
        assertPageIsAvailable();
    }

    /** Проверить работоспособность перехода по ссылке элемента */
    public void assertTransition(ElementData element) {
        verboseMessage("Валидируем работу элемента: " + element.getDescription());
        String startPage = kraken.grab().currentURL();
        kraken.perform().click(element);
        // Todo придумать кондиционную задержку
        Assert.assertFalse(
                kraken.grab().currentURL().equalsIgnoreCase(startPage),
                    "\n\n > Не работает " + element.getDescription()
                                + "\n > " + element.getLocator().toString().substring(3) + " на странице " + startPage
                                    + "\n > Нет перехода на целевую страницу\n\n");
        verboseMessage("✓ Успешный переход");
        // TODO добавить проверку на соответствие currentURL и targetURL, для этого добавить targetURL в ElementData
    }

    //todo переименовать
    /**  Проверить возможность перехода на страницу */
    public void assertTransition(PageData page) {
        assertTransition(kraken.grab().fullBaseUrl + page.getPath());
    }

    //todo переименовать
    /** Проверить возможность перехода на страницу по указанному url */
    public void assertTransition(String URL) {
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
    public void assertPageIsAvailable(PageData page) {
        assertPageIsAvailable(kraken.grab().fullBaseUrl + page.getPath());
    }

    /** Проверить возможность перехода на страницу по указанному url и ее доступность */
    public void assertPageIsAvailable(String URL) {
        assertTransition(URL);
        assertPageIsAvailable();
    }

    /** Проверить доступность текущей страницы */
    public void assertPageIsAvailable() throws AssertionError {
        String page = kraken.grab().currentURL();
        Assert.assertFalse(kraken.detect().is404(), "\n\n> Ошибка 404 на странице " + page);
        Assert.assertFalse(kraken.detect().is500(), "\n\n> Ошибка 500 на странице " + page);
        Assert.assertFalse(kraken.detect().is502(), "\n\n> Ошибка 502 на странице " + page);
        verboseMessage("✓ Страница " + page + " доступна\n");
    }

    /** Проверить что текущая страница недоступна с ошибкой 404 */
    public void assertPageIs404() throws AssertionError {
        Assert.assertTrue(kraken.detect().is404(), "\n\n> Нет ожидаемой ошибки 404 на странице " + kraken.grab().currentURL() + "\n");
    }

    /** Проверить возможность перехода на страницу и ее недоступность с ошибкой 404 */
    public void assertPageIs404(PageData page)  {
        assertPageIs404(kraken.grab().fullBaseUrl + page.getPath());
    }

    /** Проверить возможность перехода на страницу по указанному url и ее недоступность с ошибкой 404 */
    public void assertPageIs404(String URL) {
        assertTransition(URL);
        assertPageIs404();
    }

    /** Проверка недоступности страницы для перехода */
    public void assertPageIsUnavailable(PageData page) {
        assertPageIsUnavailable(kraken.grab().fullBaseUrl + page.getPath());
    }

    /** Проверка недоступности страницы для перехода по прямой ссылке */
    public void assertPageIsUnavailable(String URL) {
        message("Проверяем недоступность перехода на страницу " + URL);
        kraken.get().url(URL);
        Assert.assertFalse(
                kraken.grab().currentURL().equalsIgnoreCase(URL),
                    "\n\n> Можно попасть на страницу " + kraken.grab().currentURL() + " по прямой ссылке\n");
    }

    /** Проверка доступности зокументации заказа */
    public void assertOrderDocumentsAreAvailable() {
        assertOrderDocumentsAreDownloadable();
        assertPageIsAvailable();
    }

    /** Пропуск теста */
    public void skipTest() throws SkipException{
        message("Пропускаем тест");
            throw new SkipException("Пропускаем тест");
    }

    /** Пропуск теста на окружении */
    public void skipTestOn(EnvironmentData environment) throws SkipException{
        if (kraken.detect().environment(environment)) {
            message("Пропускаем тест на окружении " + environment.getName());
                throw new SkipException("Пропускаем тест");
        }
    }

    /** Пропуск теста на тенанте */
    public void skipTestOn(TenantData tenant) throws SkipException {
        if (kraken.detect().tenant(tenant)) {
            message("Пропускаем тест для тенанта " + tenant.getName());
                throw new SkipException("Пропускаем тест");
        }
    }

    /** Проведение теста только на указанном окружении */
    public void runTestOnlyOn(EnvironmentData environment) throws SkipException {
        if (!kraken.detect().environment(environment)) {
            message("Тест только для окружения " + environment.getName());
            throw new SkipException("Пропускаем тест");
        }
    }

    /** Проведение теста только на указанном тенанте */
    public void runTestOnlyOn(TenantData tenant) {
        if (!kraken.detect().tenant(tenant)) {
            message("Тест только для тенанта " + tenant.getName());
            throw new SkipException("Пропускаем тест");
        }
    }

    @DataProvider
    Object[][] generateUserData() {
    UserData testuser = generate.testCredentials("user");
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
        message("Скачиваем : " + docname);
        if (kraken.detect().isElementPresent(docLink)) {
            kraken.perform().click(docLink);
        } else
            throw new AssertionError("Документ \"" + docname + "\" недоступен для скачивания\nна странице " + kraken.grab().currentURL());
    }
}
