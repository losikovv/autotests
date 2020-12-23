package instamart.ui.checkpoints;

import instamart.ui.common.pagesdata.ElementData;
import instamart.ui.common.pagesdata.EnvironmentData;
import instamart.ui.common.pagesdata.PageData;
import instamart.ui.objectsmap.Elements;
import io.qameta.allure.Step;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import static instamart.ui.modules.Base.kraken;

public class BaseUICheckpoints {

    private static final Logger log = LoggerFactory.getLogger(BaseUICheckpoints.class);

    private SoftAssert softAssert = new SoftAssert();

    /** Метод-обертка для красивого вывода ошибок зафейленных тестов */
    protected String failMessage(String text) {
        return "\n\n> " + text + "\n\n";
    }

    /** Функция проверяет, что на модальном окне авторизации/регистрации присутствуют сообщения об ошибках*/
    @Step("Проверяем, что элемент: {0} отображается на экране")
    public void checkIsErrorMessageElementPresent(String successMessage,
                                                     String errorMessage){
        log.info("> проверяем, что текст ошибки: {}  отображается на экране", successMessage);
        softAssert.assertTrue(
                kraken.detect().isElementPresent(
                        Elements.Modals.AuthModal.errorMessage(successMessage)),
                errorMessage+"\n");
        softAssert.assertAll();
        log.info("✓ Успешно");
    }

    /** Функция проверяет, что на модальном окне регистрации по номеру телефона присутствуют сообщения об ошибке*/
    @Step("Проверяем, что сообщение: {0} отображается на экране регистрации через мобилку")
    public void checkIsErrorMessageElementPresentByPhone(String successMessage, String errorMessage){
        log.info("> проверяем, что текст ошибки: {} отображается на экране", successMessage);
        softAssert.assertTrue(
                kraken.detect().isElementPresent(
                        Elements.Modals.AuthModal.errorPhoneMessage(successMessage)),
                errorMessage+"\n");
        softAssert.assertAll();
        log.info("✓ Успешно");
    }

    /**Функция проверяет, что модальное окно авторизации закрыто*/
    @Step("Проверяем, что модалка авторизации закрыта")
    public void checkIsAuthModalClosed(){
        log.info("> рроверяем, что модалка авторизации закрыта");
        softAssert.assertFalse(
                kraken.detect().isAuthModalOpen(),
                "Не закрывается заполненная авторизационная модалка");
        softAssert.assertAll();
        log.info("✓ Успешно");
    }

    /**Функция проверяет, что модальное окно авторизации открыто*/
    @Step("Проверяем, что модалка авторизации открыта")
    public void checkIsAuthModalOpen(String errorMessage){
        log.info("> проверяем, что модалка авторизации открыта");
        softAssert.assertTrue(
                kraken.detect().isAuthModalOpen(),
                "\n"+errorMessage);
        softAssert.assertAll();
        log.info("✓ Успешно");
    }

    /** Проверить возможность перехода на страницу по указанному url и ее доступность */
    @Step("Проверить возможность перехода на страницу по указанному url и ее доступность")
    public void checkPageIsAvailable(String URL) {
        checkTransition(URL);
        checkPageIsAvailable();
    }

    /**  Проверить возможность перехода на страницу */
    public void checkTransition(PageData page) {
        checkTransition(EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth() + page.getPath());
    }

    /** Проверить возможность перехода на страницу по указанному url */
    @Step("Проверить возможность перехода на страницу по указанному url: {0}")
    public void checkTransition(String URL) {
        log.info("> переход по прямой ссылке {}", URL);
        kraken.get().url(URL);
        kraken.await().simply(1);
        Assert.assertTrue(
                kraken.grab().currentURL().equalsIgnoreCase(URL),
                "Невозможно перейти на страницу " + URL + " по прямой ссылке\n"
                        + "Вместо нее попадаем на " + kraken.grab().currentURL() + "\n"
        );
        log.info("✓ Успешно");
    }

    /** Проверить работоспособность перехода по ссылке элемента */
    @Step("Проверить работоспособность перехода по ссылке элемента")
    public void checkTransition(ElementData element) {
        log.info("> валидируем работу элемента: {}", element.getDescription());
        String startPage = kraken.grab().currentURL();
        kraken.perform().click(element);

        kraken.await().fluently(ExpectedConditions.not(ExpectedConditions.urlToBe(startPage)),
                "\n\n > Не работает " + element.getDescription()
                        + "\n > " + element.getLocator().toString().substring(3) + " на странице " + startPage
                        + "\n > Нет перехода на целевую страницу\n\n");

        log.info("✓ Успешный переход");
        // TODO добавить проверку на соответствие currentURL и targetURL, для этого добавить targetURL в ElementData
    }

    /** Проверить доступность текущей страницы */
    @Step("Проверяем доступность текущей страницы")
    public void checkPageIsAvailable() throws AssertionError {
        log.info("> проверяем доступность текущей страницы");
        String page = kraken.grab().currentURL();
        Assert.assertFalse(
                kraken.detect().is404(),
                failMessage("Ошибка 404 на странице " + page)
        );
        Assert.assertFalse(
                kraken.detect().is500(),
                failMessage("Ошибка 500 на странице " + page)
        );
        Assert.assertFalse(
                kraken.detect().is502(),
                failMessage("Ошибка 502 на странице " + page )
        );
        log.info("✓ Страница {} доступна", page);
    }

    /**Проверяем присутсвие элемента на странице*/
    @Step("Проверяем присутсвие элемента на странице: {element.description}")
    public void checkIsElementPresent(ElementData element){
        log.info("> проверяем наличие элемента на странице {}> {}", kraken.grab().currentURL(), element.getLocator());
        Assert.assertTrue(
                kraken.detect().isElementPresent(element),
                    failMessage("Отсутствует " + element.getDescription() + " на странице " + kraken.grab().currentURL()
                            + "\n> " + element.getLocator()));
        log.info("✓ Успешно: {}", element.getDescription());
    }

    /** Проверяем отображение элемента на странице*/
    @Step("Проверяем отображаение элемента на странице: {element.description}")
    public void checkIsElementDisplayed(ElementData element, String errorMessage){
        log.info("> проверяем отображение элемента на странице {}> {}", kraken.grab().currentURL(), element.getLocator());
        Assert.assertTrue(
                kraken.detect().isElementDisplayed(element),
                errorMessage);
        log.info("✓ Успешно: {}", element.getDescription());
    }

    @Step("Проверяем {2}")
    public void checkIsStringValuesNotEquals(String firstString, String secondString,
                                             String stepDescription, String errorMessage){
        log.info("> {}", stepDescription);
        softAssert.assertNotEquals(
                firstString, secondString,
                "\n> "+errorMessage);
        softAssert.assertAll();
        log.info("✓ Успешно");
    }

    /** Проверить валидность элемента (преход работает + целевая страница доступна) */
    public void checkTransitionValidation(ElementData element) {
        checkTransition(element);
        checkPageIsAvailable();
    }

    /** Проверить возможность перехода на страницу и ее доступность */
    public void checkPageIsAvailable(PageData page) {
        checkPageIsAvailable(EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth() + page.getPath());
    }

    /** Проверить доступность ретейлера*/
    @Step("Проверить доступность ретейлера")
    public void checkRetailerIsAvailable(String retailer) {
        checkPageIsAvailable(EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth() + retailer);
    }
    /** Проверить не доступность ретейлера*/
    @Step("Проверить не доступность ретейлера")
    public void checkRetailerIsUnavailable(String retailer) {
        checkPageIs404(EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth() + retailer);
    }

    /** Проверить возможность перехода на страницу по указанному url и ее недоступность с ошибкой 404 */
    public void checkPageIs404(String URL) {
        checkTransition(URL);
        checkPageIs404();
    }

    /** Проверить что текущая страница недоступна с ошибкой 404 */
    @Step("Проверить что текущая страница недоступна с ошибкой 404")
    public void checkPageIs404() throws AssertionError {
        log.info("> проверяем появление ошибки 404 на странице");
        Assert.assertTrue(kraken.detect().is404(), "\n\n> Нет ожидаемой ошибки 404 на странице " + kraken.grab().currentURL() + "\n");
    }

    /** Проверить возможность перехода на страницу и ее недоступность с ошибкой 404 */
    @Step("Проверить возможность перехода на страницу и ее недоступность с ошибкой 404")
    public void checkPageIs404(PageData page)  {
        checkPageIs404(EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth() + page.getPath());
    }

    /** Проверить возможность перехода в каталог магазина с лендинга Сбермаркета */
    @Step("Проверить возможность перехода в каталог магазина с лендинга Сбермаркета")
    public void checkIsOnLanding(){
        log.info("> проверяем возможность перехода в каталог выбранного магазина");
        Assert.assertFalse(
                kraken.detect().isOnLanding(),
                failMessage("Не работает переход в каталог магазина с лендинга Сбермаркета"));
        log.info("✓ Успешно");
    }

    /** Проверить отсутствие элемента на странице */
    @Step("Проверить отсутствие элемента на странице")
    public void checkElementAbsence(ElementData element) {
        log.info("> проверяем отсутствие элемента на странице {}> {}", kraken.grab().currentURL(), element.getLocator());
        Assert.assertFalse(
                kraken.detect().isElementPresent(element),
                failMessage("Присутствует " + element.getDescription() + " на странице " + kraken.grab().currentURL()
                        + "\n> " + element.getLocator()));
        log.info("✓ {} отсутствует", element.getDescription());
    }

    /** Проверить что чекбокс проставлен */
    @Step("Проверить что чекбокс проставлен")
    public void checkCheckboxIsSet(ElementData element) {
        log.info("> проверяем что чекбокс на странице проставлен {}", kraken.grab().currentURL());
        Assert.assertTrue(
                kraken.detect().isCheckboxSet(element),
                failMessage("Не проставлен " + element.getDescription()));
        log.info("✓ Успешно");
    }

    /** Проверить что чекбокс не проставлен */
    @Step("Проверить что чекбокс не проставлен")
    public void checkCheckboxIsNotSet(ElementData element) {
        log.info("> проверяем что чекбокс на странице проставлен {}", kraken.grab().currentURL());
        Assert.assertFalse(
                kraken.detect().isCheckboxSet(element),
                failMessage("Проставлен " + element.getDescription()));
        log.info("✓ Успешно");
    }

    /** Проверить что радиокнопка выбрана */
    @Step("Проверить что радиокнопка выбрана")
    public void checkRadioButtonIsSelected(ElementData element) {
        log.info("> проверяем что радиобатон на странице выбран {}", kraken.grab().currentURL());
        Assert.assertTrue(
                kraken.detect().isRadioButtonSelected(element),
                failMessage("Не выбрана " + element.getDescription()));
        log.info("✓ Успешно");
    }

    /** Проверить что радиокнопка не выбрана */
    @Step("Проверить что радиокнопка не выбрана")
    public void checkRadioButtonIsNotSelected(ElementData element) {
        log.info("> проверяем что радиобатон на странице не выбран {}", kraken.grab().currentURL());
        Assert.assertFalse(
                kraken.detect().isRadioButtonSelected(element),
                failMessage("Выбрана " + element.getDescription()));
        log.info("✓ Успешно");
    }

    /** Проверка недоступности страницы для перехода */
    public void checkPageIsUnavailable(PageData page) {
        checkPageIsUnavailable(EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth() + page.getPath());
    }

    /** Проверка недоступности страницы для перехода по прямой ссылке */
    @Step("Проверка недоступности страницы для перехода по прямой ссылке")
    public void checkPageIsUnavailable(String URL) {
        log.info("> проверяем недоступность перехода на страницу {}", URL);
        kraken.get().url(URL);
        Assert.assertFalse(
                kraken.grab().currentURL().equalsIgnoreCase(URL),
                "\n\n> Можно попасть на страницу " + kraken.grab().currentURL() + " по прямой ссылке\n");
        log.info("✓ Успешно");
    }

    /** Проверить что поле пустое */
    @Step("Проверить что поле пустое")
    public void checkFieldIsEmpty(ElementData element) {
        log.info("> проверяем что поле для ввода пустое на странице {}", kraken.grab().currentURL());
        Assert.assertTrue(
                kraken.detect().isFieldEmpty(element),
                failMessage("Не пустое " + element.getDescription()));
        log.info("✓ Успешно");
    }

    /** Проверить что поле не пустое */
    @Step("Проверить что поле не пустое")
    public void checkFieldIsNotEmpty(ElementData element) {
        log.info("> проверяем что поле для ввода не пустое на странице {}", kraken.grab().currentURL());
        Assert.assertFalse(
                kraken.detect().isFieldEmpty(element),
                failMessage("Пустое " + element.getDescription()));
        log.info("✓ Успешно");
    }

    /** Проверить что поле заполнено ожидаемым текстом */
    @Step("Проверить что поле заполнено ожидаемым текстом")
    public void checkFieldIsFilled(ElementData element, String expectedText) {
        log.info("> проверяем что поле для ввода заполненно ожидаемым текстом на странице {}", kraken.grab().currentURL());
        Assert.assertEquals(
                kraken.grab().value(element), expectedText,
                failMessage("Некорректно заполнено " + element.getDescription()));
        log.info("✓ Успешно");
    }
}
