package instamart.ui.checkpoints;

import instamart.core.common.AppManager;
import instamart.ui.common.pagesdata.ElementData;
import instamart.ui.common.pagesdata.EnvironmentData;
import instamart.ui.common.pagesdata.PageData;
import instamart.ui.objectsmap.Elements;
import io.qameta.allure.Step;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import static instamart.core.helpers.HelperBase.verboseMessage;
import static instamart.ui.modules.Base.kraken;

public class BaseUICheckpoints {
    private SoftAssert softAssert = new SoftAssert();

    /** Метод-обертка для красивого вывода ошибок зафейленных тестов */
    protected String failMessage(String text) {
        return "\n\n> " + text + "\n\n";
    }

    /** Функция проверяет, что на модальном окне авторизации/регистрации присутствуют сообщения об ошибках*/
    @Step("Проверяем, что элемент: {0} отображается на экране")
    public void checkIsErrorMessageElementPresent(String successMessage,
                                                     String errorMessage){
        verboseMessage("> проверяем, что текст ошибки: " +successMessage+
                    " отображается на экране\n");
        softAssert.assertTrue(
                kraken.detect().isElementPresent(
                        Elements.Modals.AuthModal.errorMessage(successMessage)),
                errorMessage+"\n");
        softAssert.assertAll();
        verboseMessage("✓ Успешно");
    }

    /** Функция проверяет, что на модальном окне регистрации по номеру телефона присутствуют сообщения об ошибке*/
    @Step("Проверяем, что сообщение: {0} отображается на экране регистрации через мобилку")
    public void checkIsErrorMessageElementPresentByPhone(String successMessage, String errorMessage){
        verboseMessage("> проверяем, что текст ошибки: " +successMessage+
                " отображается на экране\n");
        softAssert.assertTrue(
                kraken.detect().isElementPresent(
                        Elements.Modals.AuthModal.errorPhoneMessage(successMessage)),
                errorMessage+"\n");
        softAssert.assertAll();
        verboseMessage("✓ Успешно");
    }

    /**Функция проверяет, что модальное окно авторизации закрыто*/
    @Step("Проверяем, что модалка авторизации закрыта")
    public void checkIsAuthModalClosed(){
        verboseMessage("> рроверяем, что модалка авторизации закрыта\n");
        softAssert.assertFalse(
                kraken.detect().isAuthModalOpen(),
                "Не закрывается заполненная авторизационная модалка\n");
        softAssert.assertAll();
        verboseMessage("✓ Успешно");
    }

    /**Функция проверяет, что модальное окно авторизации открыто*/
    @Step("Проверяем, что модалка авторизации открыта")
    public void checkIsAuthModalOpen(String errorMessage){
        verboseMessage("> проверяем, что модалка авторизации открыта\n");
        softAssert.assertTrue(
                kraken.detect().isAuthModalOpen(),
                "\n"+errorMessage);
        softAssert.assertAll();
        verboseMessage("✓ Успешно");
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
        verboseMessage("> переход по прямой ссылке " + URL);
        kraken.get().url(URL);
        kraken.await().simply(1);
        Assert.assertTrue(
                kraken.grab().currentURL().equalsIgnoreCase(URL),
                "Невозможно перейти на страницу " + URL + " по прямой ссылке\n"
                        + "Вместо нее попадаем на " + kraken.grab().currentURL() + "\n"
        );
        verboseMessage("✓ Успешно");
    }

    /** Проверить работоспособность перехода по ссылке элемента */
    @Step("Проверить работоспособность перехода по ссылке элемента")
    public void checkTransition(ElementData element) {
        verboseMessage("> валидируем работу элемента: " + element.getDescription());
        String startPage = kraken.grab().currentURL();
        kraken.perform().click(element);

        kraken.await().fluently(ExpectedConditions.not(ExpectedConditions.urlToBe(startPage)),
                "\n\n > Не работает " + element.getDescription()
                        + "\n > " + element.getLocator().toString().substring(3) + " на странице " + startPage
                        + "\n > Нет перехода на целевую страницу\n\n");

        verboseMessage("✓ Успешный переход");
        // TODO добавить проверку на соответствие currentURL и targetURL, для этого добавить targetURL в ElementData
    }

    /** Проверить доступность текущей страницы */
    @Step("Проверяем доступность текущей страницы")
    public void checkPageIsAvailable() throws AssertionError {
        verboseMessage("> проверяем доступность текущей страницы");
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
        verboseMessage("✓ Страница " + page + " доступна\n");
    }

    /**Проверяем присутсвие элемента на странице*/
    @Step("Проверяем присутсвие элемента на странице: {element.description}")
    public void checkIsElementPresent(ElementData element){
        verboseMessage("> проверяем наличие элемента на странице " + kraken.grab().currentURL() + "\n> " + element.getLocator());
        Assert.assertTrue(
                kraken.detect().isElementPresent(element),
                    failMessage("Отсутствует " + element.getDescription() + " на странице " + kraken.grab().currentURL()
                            + "\n> " + element.getLocator()));
        verboseMessage("✓ Успешно: " + element.getDescription() + "\n");
    }

    /** Проверяем отображение элемента на странице*/
    @Step("Проверяем отображаение элемента на странице: {element.description}")
    public void checkIsElementDisplayed(ElementData element, String errorMessage){
        verboseMessage("> проверяем отображение элемента на странице " + kraken.grab().currentURL() + "\n> " + element.getLocator());
        Assert.assertTrue(
                kraken.detect().isElementDisplayed(element),
                errorMessage);
        verboseMessage("✓ Успешно: " + element.getDescription() + "\n");
    }

    @Step("Проверяем {2}")
    public void checkIsStringValuesNotEquals(String firstString, String secondString,
                                             String stepDescription, String errorMessage){
        verboseMessage("> "+stepDescription);
        softAssert.assertNotEquals(
                firstString, secondString,
                "\n> "+errorMessage);
        softAssert.assertAll();
        verboseMessage("✓ Успешно");
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
        verboseMessage("> проверяем появление ошибки 404 на странице");
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
        verboseMessage("> проверяем возможность перехода в каталог выбранного магазина");
        Assert.assertFalse(
                kraken.detect().isOnLanding(),
                failMessage("Не работает переход в каталог магазина с лендинга Сбермаркета"));
        verboseMessage("✓ Успешно");
    }
}
