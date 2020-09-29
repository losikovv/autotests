package instamart.ui.checkpoints;

import instamart.ui.objectsmap.Elements;
import io.qameta.allure.Step;
import org.testng.asserts.SoftAssert;

import static instamart.core.helpers.HelperBase.verboseMessage;
import static instamart.ui.modules.Base.kraken;

public class BaseUICheckpoints {
    private SoftAssert softAssert = new SoftAssert();


    /** Функция проверяет, что на модальном окне авторизации/регистрации присутствуют сообщения об ошибках*/
    @Step("Проверяем, что текст ошибки: {0} отображается на экране")
    public void checkIsErrorMessageElementPresent(String successMessage,
                                                     String errorMessage){
        verboseMessage("Проверяем, что текст ошибки: " +successMessage+
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
        verboseMessage("Проверяем, что модалка авторизации закрыта\n");
        softAssert.assertFalse(
                kraken.detect().isAuthModalOpen(),
                "Не закрывается заполненная авторизационная модалка\n");
        softAssertAll();
    }

    /**Функция проверяет, что модальное окно авторизации открыто*/
    @Step("Проверяем, что модалка авторизации открыта")
    public void checkIsAuthModalOpen(String errorMessage){
        verboseMessage("Проверяем, что модалка авторизации открыта\n");
        softAssert.assertTrue(
                kraken.detect().isAuthModalOpen(),
                "\n"+errorMessage);
        softAssertAll();
    }
    @Step("Проверяем, что корзина не пуста после действия: {0}")
    public void checkIsCartEmpty(String action,String errorMessage){
        verboseMessage("Проверяем, что корзина не пуста после действия: "+action+"\n");
        softAssert.assertFalse(
                kraken.detect().isCartEmpty(),
                "\n"+errorMessage);
        softAssertAll();
    }

    public void softAssertAll(){
        //todo прикрутить сюда создание скриншотов
        softAssert.assertAll();
    }
}
