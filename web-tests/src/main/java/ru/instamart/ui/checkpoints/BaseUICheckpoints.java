package instamart.ui.checkpoints;

import instamart.ui.objectsmap.Elements;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import static instamart.core.helpers.HelperBase.verboseMessage;
import static instamart.ui.modules.Base.kraken;

public class BaseUICheckpoints {
    private SoftAssert softAssert = new SoftAssert();


    /** Функция проверяет, что на модальном окне авторизации/регистрации присутствуют сообщения об ошибках*/
    @Step("Проверяем, что текст ошибки: {0} отображается на экране")
    public void checkIsErrorMessageElementPresent(String successMessage,
                                                     String errorMessage){
        verboseMessage("> проверяем, что текст ошибки: " +successMessage+
                    " отображается на экране\n");
        softAssert.assertTrue(
                kraken.detect().isElementPresent(
                        Elements.Modals.AuthModal.errorMessage(successMessage)),
                errorMessage+"\n");
        softAssertAll();
    }

    /**Функция проверяет, что модальное окно авторизации закрыто*/
    @Step("Проверяем, что модалка авторизации закрыта")
    public void checkIsAuthModalClosed(){
        verboseMessage("> рроверяем, что модалка авторизации закрыта\n");
        softAssert.assertFalse(
                kraken.detect().isAuthModalOpen(),
                "Не закрывается заполненная авторизационная модалка\n");
        softAssertAll();
    }

    /**Функция проверяет, что модальное окно авторизации открыто*/
    @Step("Проверяем, что модалка авторизации открыта")
    public void checkIsAuthModalOpen(String errorMessage){
        verboseMessage("> проверяем, что модалка авторизации открыта\n");
        softAssert.assertTrue(
                kraken.detect().isAuthModalOpen(),
                "\n"+errorMessage);
        softAssertAll();
    }
    @Step("Проверяем, что корзина не пуста после действия: {0}")
    public void checkIsCartEmpty(String action,String errorMessage){
        verboseMessage("> проверяем, что корзина не пуста после действия: "+action+"\n");
        softAssert.assertFalse(
                kraken.detect().isCartEmpty(),
                "\n"+errorMessage);
        softAssertAll();
    }
    @Step("Проверяем, что корзина очистилась после действия: {0}")
    public void checkIsCartNotEmpty(String action){
        verboseMessage("> проверяем, что корзина очистилась после действия: "+action+"\n");
        softAssert.assertTrue(kraken.detect().isCartEmpty(),
                    failMessage("Не сбросилась корзина после: "+action));
        softAssertAll();
    }

    /** Проверить доступность текущей страницы */
    @Step("Проверяем доступность текущей страницы")
    public void assertPageIsAvailable() throws AssertionError {
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

    /** Метод-обертка для красивого вывода ошибок зафейленных тестов */
    protected String failMessage(String text) {
        return "\n\n> " + text + "\n\n";
    }

    public void softAssertAll(){
        //todo прикрутить сюда создание скриншотов
        softAssert.assertAll();
    }
}
