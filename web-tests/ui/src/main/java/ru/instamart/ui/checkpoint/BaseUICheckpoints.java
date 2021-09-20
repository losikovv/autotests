package ru.instamart.ui.checkpoint;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.helper.KrakenAssert;
import ru.instamart.kraken.testdata.pagesdata.PageData;
import ru.instamart.kraken.util.ThreadUtil;
import ru.instamart.ui.Elements;
import ru.instamart.ui.config.WaitProperties;
import ru.instamart.ui.data.ElementData;

import static ru.instamart.ui.module.Base.kraken;

@Slf4j
public class BaseUICheckpoints {

    private final KrakenAssert krakenAssert = new KrakenAssert();

    /**
     * Метод-обертка для красивого вывода ошибок зафейленных тестов
     */
    protected String failMessage(String text) {
        return "\n\n> " + text + "\n\n";
    }

    /**
     * Функция проверяет, что на модальном окне авторизации/регистрации присутствуют сообщения об ошибках
     */
    @Step("Проверяем, что элемент: {0} отображается на экране")
    public void checkIsErrorMessageElementPresent(String successMessage,
                                                  String errorMessage) {
        log.debug("> проверяем, что текст ошибки: {}  отображается на экране", successMessage);
        krakenAssert.assertTrue(
                kraken.detect().isElementPresent(
                        Elements.Modals.AuthModal.errorMessage(successMessage)),
                errorMessage + "\n");
        krakenAssert.assertAll();
        log.debug("✓ Успешно");
    }

    /**
     * Функция проверяет, что на модальном окне регистрации по номеру телефона присутствуют сообщения об ошибке
     */
    @Step("Проверяем, что сообщение: {0} отображается на экране регистрации через мобилку")
    public void checkIsErrorMessageElementPresentByPhone(String successMessage, String errorMessage) {
        log.debug("> проверяем, что текст ошибки: {} отображается на экране", successMessage);
        krakenAssert.assertTrue(
                kraken.detect().isElementPresent(
                        Elements.Modals.AuthModal.errorPhoneMessage(successMessage)),
                errorMessage + "\n");
        krakenAssert.assertAll();
        log.debug("✓ Успешно");
    }

    /**
     * Функция проверяет, что модальное окно авторизации закрыто
     */
    @Step("Проверяем, что модалка авторизации закрыта")
    public void checkIsAuthModalClosed() {
        log.debug("> рроверяем, что модалка авторизации закрыта");
        krakenAssert.assertFalse(
                kraken.detect().isAuthModalOpen(),
                "Не закрывается заполненная авторизационная модалка");
        krakenAssert.assertAll();
        log.debug("✓ Успешно");
    }

    /**
     * Функция проверяет, что модальное окно авторизации открыто
     */
    @Step("Проверяем, что модалка авторизации открыта")
    public void checkIsAuthModalOpen(String errorMessage) {
        log.debug("> проверяем, что модалка авторизации открыта");
        krakenAssert.assertTrue(
                kraken.detect().isAuthModalOpen(),
                "\n" + errorMessage);
        krakenAssert.assertAll();
        log.debug("✓ Успешно");
    }

    /**
     * Проверяем возможность перехода на страницу по указанному url и ее доступность
     */
    @Step("Проверяем возможность перехода на страницу по указанному url и ее доступность")
    public void checkPageIsAvailable(String URL) {
        checkTransition(URL);
        checkPageIsAvailable();
    }

    /**
     * Проверяем возможность перехода на страницу
     */
    public void checkTransition(PageData page) {
        checkTransition(EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH + page.getPath());
    }

    /**
     * Проверяем возможность перехода на страницу по указанному url
     */
    @Step("Проверяем возможность перехода на страницу по указанному url: {0}")
    public void checkTransition(String URL) {
        log.debug("> переход по прямой ссылке {}", URL);
        kraken.get().url(URL);
        kraken.await().fluently(ExpectedConditions.urlToBe(URL));
        Assert.assertTrue(
                kraken.grab().currentURL().equalsIgnoreCase(URL),
                "Невозможно перейти на страницу " + URL + " по прямой ссылке\n"
                        + "Вместо нее попадаем на " + kraken.grab().currentURL() + "\n"
        );
        log.debug("✓ Успешно");
    }

    /**
     * Проверяем работоспособность перехода по ссылке элемента
     */
    @Step("Проверяем работоспособность перехода по ссылке элемента")
    public void checkTransition(ElementData element) {
        log.debug("> валидируем работу элемента: {}", element.getDescription());
        String startPage = kraken.grab().currentURL();
        kraken.await().fluently(ExpectedConditions.elementToBeClickable(element.getLocator()),
                "элемент не доступен: " + element.getDescription(), WaitProperties.BASIC_TIMEOUT);
        kraken.perform().scrollToTheBottom(element);
        ThreadUtil.simplyAwait(0.3);
        kraken.perform().click(element);
        kraken.await().fluently(ExpectedConditions.not(ExpectedConditions.urlToBe(startPage)),
                "\n\n > Не работает " + element.getDescription()
                        + "\n > " + element.getLocator().toString().substring(3) + " на странице " + startPage
                        + "\n > Нет перехода на целевую страницу\n\n");
        log.debug("✓ Успешный переход");
    }

    /**
     * Проверяем доступность текущей страницы
     */
    @Step("Проверяем доступность текущей страницы")
    public void checkPageIsAvailable() throws AssertionError {
        log.debug("> проверяем доступность текущей страницы");
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
                failMessage("Ошибка 502 на странице " + page)
        );
        log.debug("✓ Страница {} доступна", page);
    }

    /**
     * Проверяем присутсвие элемента на странице
     */
    @Step("Проверяем присутсвие элемента на странице: {element.description}")
    public void checkIsElementPresent(ElementData element) {
        log.debug("> проверяем наличие элемента на странице {}> {}", kraken.grab().currentURL(), element.getLocator());
//        kraken.perform().scrollToTheBottom(element);
        Assert.assertTrue(
                kraken.detect().isElementPresent(element),
                failMessage("Отсутствует " + element.getDescription() + " на странице " + kraken.grab().currentURL()
                        + "\n> " + element.getLocator()));
        log.debug("✓ Успешно: {}", element.getDescription());
    }

    /**
     * Проверяем присутсвие элемента на странице
     */
    @Step("Проверяем присутсвие элемента на странице: {element.description}")
    public void checkIsElementNotPresent(ElementData element) {
        log.debug("> проверяем отсутсвие элемента на странице {}> {}", kraken.grab().currentURL(), element.getLocator());
        Assert.assertFalse(
                kraken.detect().isElementPresent(element),
                failMessage("Присутствует " + element.getDescription() + " на странице " + kraken.grab().currentURL()
                        + "\n> " + element.getLocator()));
        log.debug("✓ Успешно: {}", element.getDescription());
    }

    /**
     * Проверяем присутсвие элемента на странице, с таймаутом
     */
    @Step("Проверяем присутсвие элемента на странице: {element.description} с таймаутом: {1}")
    public void checkIsElementPresent(ElementData element, int time) {
        log.debug("> проверяем наличие элемента на странице {}> {}", kraken.grab().currentURL(), element.getLocator());
        kraken.await().fluently(ExpectedConditions.visibilityOfElementLocated(element.getLocator()),
                "Отсутствует" + element.getDescription() + " на странице " + kraken.grab().currentURL(), time);
        Assert.assertTrue(
                kraken.detect().isElementPresent(element),
                failMessage("Отсутствует " + element.getDescription() + " на странице " + kraken.grab().currentURL()
                        + "\n> " + element.getLocator()));
        log.debug("✓ Успешно: {}", element.getDescription());
    }

    /**
     * Проверяем отображение элемента на странице
     */
    @Step("Проверяем отображаение элемента на странице: {element.description}")
    public void checkIsElementDisplayed(ElementData element, String errorMessage) {
        log.debug("> проверяем отображение элемента на странице {}> {}", kraken.grab().currentURL(), element.getLocator());
        Assert.assertTrue(
                kraken.detect().isElementDisplayed(element),
                errorMessage);
        log.debug("✓ Успешно: {}", element.getDescription());
    }

    @Step("Проверяем {2}")
    public void checkIsStringValuesNotEquals(String firstString, String secondString,
                                             String stepDescription, String errorMessage) {
        log.debug("> {}", stepDescription);
        krakenAssert.assertNotEquals(
                firstString, secondString,
                "\n> " + errorMessage);
        krakenAssert.assertAll();
        log.debug("✓ Успешно");
    }

    /**
     * Проверяем валидность элемента (преход работает + целевая страница доступна)
     */
    public void checkTransitionValidation(ElementData element) {
        checkTransition(element);
        checkPageIsAvailable();
    }

    /**
     * Проверяем возможность перехода на страницу и ее доступность
     */
    public void checkPageIsAvailable(PageData page) {
        checkPageIsAvailable(EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH + page.getPath());
    }

    /**
     * Проверяем доступность ретейлера
     */
    @Step("Проверяем доступность ретейлера")
    public void checkRetailerIsAvailable(String retailer) {
        checkPageIsAvailable(EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH + retailer);
    }

    /**
     * Проверяем не доступность ретейлера
     */
    @Step("Проверяем не доступность ретейлера")
    public void checkRetailerIsUnavailable(String retailer) {
        checkPageIs404(EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH + retailer);
    }

    /**
     * Проверяем возможность перехода на страницу по указанному url и ее недоступность с ошибкой 404
     */
    public void checkPageIs404(String URL) {
        checkTransition(URL);
        checkPageIs404();
    }

    /**
     * Проверяем что текущая страница недоступна с ошибкой 404
     */
    @Step("Проверяем что текущая страница недоступна с ошибкой 404")
    public void checkPageIs404() throws AssertionError {
        log.debug("> проверяем появление ошибки 404 на странице");
        Assert.assertTrue(kraken.detect().is404(), "\n\n> Нет ожидаемой ошибки 404 на странице " + kraken.grab().currentURL() + "\n");
    }

    /**
     * Проверяем возможность перехода на страницу и ее недоступность с ошибкой 404
     */
    @Step("Проверяем возможность перехода на страницу и ее недоступность с ошибкой 404")
    public void checkPageIs404(PageData page) {
        checkPageIs404(EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH + page.getPath());
    }

    /**
     * Проверяем возможность перехода в каталог магазина с лендинга Сбермаркета
     */
    @Step("Проверяем возможность перехода в каталог магазина с лендинга Сбермаркета")
    public void checkIsOnLanding() {
        log.debug("> проверяем возможность перехода в каталог выбранного магазина");
        Assert.assertFalse(
                kraken.detect().isOnLanding(),
                failMessage("Не работает переход в каталог магазина с лендинга Сбермаркета"));
        log.debug("✓ Успешно");
    }

    /**
     * Проверяем отсутствие элемента на странице
     */
    @Step("Проверяем отсутствие элемента на странице")
    public void checkElementAbsence(ElementData element) {
        log.debug("> проверяем отсутствие элемента на странице {}> {}", kraken.grab().currentURL(), element.getLocator());
        Assert.assertFalse(
                kraken.detect().isElementPresent(element),
                failMessage("Присутствует " + element.getDescription() + " на странице " + kraken.grab().currentURL()
                        + "\n> " + element.getLocator()));
        log.debug("✓ {} отсутствует", element.getDescription());
    }

    /**
     * Проверяем отсутствие элемента на странице
     */
    @Step("Проверяем отсутствие элемента на странице")
    public void checkElementAbsence(ElementData element, String errorMessage) {
        log.debug("> проверяем отсутствие элемента на странице {}> {}", kraken.grab().currentURL(), element.getLocator());
        Assert.assertFalse(
                kraken.detect().isElementPresent(element),
                failMessage(errorMessage));
        log.debug("✓ Успешно");
    }

    /**
     * Проверяем что чекбокс проставлен
     */
    @Step("Проверяем что чекбокс проставлен")
    public void checkCheckboxIsSet(ElementData element) {
        log.debug("> проверяем что чекбокс на странице проставлен {}", kraken.grab().currentURL());
        Assert.assertTrue(
                kraken.detect().isCheckboxSet(element),
                failMessage("Не проставлен " + element.getDescription()));
        log.debug("✓ Успешно");
    }

    /**
     * Проверяем что чекбокс не проставлен
     */
    @Step("Проверяем что чекбокс не проставлен")
    public void checkCheckboxIsNotSet(ElementData element) {
        log.debug("> проверяем что чекбокс на странице проставлен {}", kraken.grab().currentURL());
        Assert.assertFalse(
                kraken.detect().isCheckboxSet(element),
                failMessage("Проставлен " + element.getDescription()));
        log.debug("✓ Успешно");
    }

    /**
     * Проверяем что радиокнопка выбрана
     */
    @Step("Проверяем что радиокнопка выбрана")
    public void checkRadioButtonIsSelected(ElementData element) {
        log.debug("> проверяем что радиобатон на странице выбран {}", kraken.grab().currentURL());
        Assert.assertTrue(
                kraken.detect().isRadioButtonSelected(element),
                failMessage("Не выбрана " + element.getDescription()));
        log.debug("✓ Успешно");
    }

    /**
     * Проверяем что радиокнопка не выбрана
     */
    @Step("Проверяем что радиокнопка не выбрана")
    public void checkRadioButtonIsNotSelected(ElementData element) {
        log.debug("> проверяем что радиобатон на странице не выбран {}", kraken.grab().currentURL());
        Assert.assertFalse(
                kraken.detect().isRadioButtonSelected(element),
                failMessage("Выбрана " + element.getDescription()));
        log.debug("✓ Успешно");
    }

    /**
     * Проверка недоступности страницы для перехода
     */
    public void checkPageIsUnavailable(PageData page) {
        checkPageIsUnavailable(EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH + page.getPath());
    }

    /**
     * Проверка недоступности страницы для перехода по прямой ссылке
     */
    @Step("Проверка недоступности страницы для перехода по прямой ссылке")
    public void checkPageIsUnavailable(String URL) {
        log.debug("> проверяем недоступность перехода на страницу {}", URL);
        kraken.get().url(URL);
        Assert.assertFalse(
                kraken.grab().currentURL().equalsIgnoreCase(URL),
                "\n\n> Можно попасть на страницу " + kraken.grab().currentURL() + " по прямой ссылке\n");
        log.debug("✓ Успешно");
    }

    /**
     * Проверяем что поле пустое
     */
    @Step("Проверяем что поле пустое")
    public void checkFieldIsEmpty(ElementData element) {
        log.debug("> проверяем что поле для ввода пустое на странице {}", kraken.grab().currentURL());
        Assert.assertTrue(
                kraken.detect().isFieldEmpty(element),
                failMessage("Не пустое " + element.getDescription()));
        log.debug("✓ Успешно");
    }

    /**
     * Проверяем что поле не пустое
     */
    @Step("Проверяем что поле не пустое")
    public void checkFieldIsNotEmpty(ElementData element) {
        log.debug("> проверяем что поле для ввода не пустое на странице {}", kraken.grab().currentURL());
        Assert.assertFalse(
                kraken.detect().isFieldEmpty(element),
                failMessage("Пустое " + element.getDescription()));
        log.debug("✓ Успешно");
    }

    /**
     * Проверяем что поле заполнено ожидаемым текстом
     */
    @Step("Проверяем что поле заполнено ожидаемым текстом")
    public void checkFieldIsFilled(ElementData element, String expectedText) {
        log.debug("> проверяем что поле для ввода заполненно ожидаемым текстом на странице {}", kraken.grab().currentURL());
        Assert.assertEquals(
                kraken.grab().value(element), expectedText,
                failMessage("Некорректно заполнено " + element.getDescription()));
        log.debug("✓ Успешно");
    }

    /**
     * Проверяем, что текст на странице соответсветсвует ожидаемому результату
     */
    @Step("Проверяем, что текст на странице соответсветсвует ожидаемому результату")
    public void checkTextIsCorrectInElement(ElementData element, String expectedText, String errorMessage) {
        log.debug("> Проверяем, что текст на странице соответсветсвует ожидаемому результату {}", kraken.grab().currentURL());
        Assert.assertEquals(
                kraken.grab().text(element), expectedText,
                failMessage(errorMessage));
        log.debug("✓ Успешно");

    }

    /**
     * Проверяем, что значение в элементе соответсветсвует ожидаемому результату
     */
    @Step("Проверяем, что значение в элементе соответсветсвует ожидаемому результату")
    public void checkValueIsCorrectInElement(ElementData element, String expectedText, String errorMessage) {
        log.debug("> Проверяем, что значение в элементе соответсветсвует ожидаемому результату {}", kraken.grab().currentURL());
        Assert.assertEquals(
                kraken.grab().value(element), expectedText,
                failMessage(errorMessage));
        log.debug("✓ Успешно");
    }

    /**
     * Проверяем, что вебэлемент заблокирован на странице
     */
    @Step("Проверяем, что веб элемент заблокирован: {element.description}")
    public void checkIsElementDisabled(ElementData element) {
        log.debug("> проверяем, что веб элемент заблокирован {}> {}", kraken.grab().currentURL(), element.getLocator());
        Assert.assertFalse(
                kraken.detect().isElementEnabled(element),
                failMessage("Не заблокирован " + element.getDescription() + " на странице " + kraken.grab().currentURL()
                        + "\n> " + element.getLocator()));
        log.debug("✓ Успешно: {}", element.getDescription());
    }

    /**
     * Проверяем, что вебэлемент доступен на странице
     */
    @Step("Проверяем, что веб элемент доступен: {element.description}")
    public void checkIsElementEnabled(ElementData element) {
        log.debug("> проверяем, что веб элемент заблокирован {}> {}", kraken.grab().currentURL(), element.getLocator());
        Assert.assertTrue(
                kraken.detect().isElementEnabled(element),
                failMessage("Не доступен " + element.getDescription() + " на странице " + kraken.grab().currentURL()
                        + "\n> " + element.getLocator()));
        log.debug("✓ Успешно: {}", element.getDescription());
    }

    @Step("Проверяем, что открылась карточка товара")
    public void checkIsItemCardOpen(String errorMessage) {
        log.debug("> проверяем, что открылась карточка товара");
        Assert.assertTrue(kraken.detect().isItemCardOpen(),
                errorMessage);
        log.debug("✓ Успешно");
    }
}
