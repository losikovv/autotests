package ru.instamart.ui.checkpoint.users;

import io.qameta.allure.Step;
import ru.instamart.ui.checkpoint.Checkpoint;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static ru.instamart.ui.module.Base.kraken;

public interface UsersAuthorizationCheckpoints extends Checkpoint {

    /**Проверяем, что пользователь не авторизован на сайте*/
    @Step("Проверяем, что пользователь не авторизован на сайте")
    default void checkIsUserNotAuthorized(String message){
        log.info("> проверяем, что пользователь не авторизован на сайте");
        kraken.get().baseUrl();
        assertFalse(
                kraken.detect().isUserAuthorised(),
                message+"\n");
    }

    /**Проверяем, что пользователь авторизован на сайте*/
    @Step("Проверяем, что пользователь авторизован на сайте")
    default void checkIsUserAuthorized(String message){
        log.info("> проверяем, что пользователь авторизован на сайте");
        assertTrue(
                kraken.detect().isUserAuthorisedSTF(),
                message+"\n");
    }

    default void checkIsUserAuthorized() {
        checkIsUserAuthorized("\nПользователь не авторизован");
    }

    /**Проверяем, что при авторизации из корзины происходит редирект в чекаут*/
    @Step("Проверяем, что при авторизации из корзины происходит редирект в чекаут")
    default void checkAutoCheckoutRedirect(String message){
        log.info("> проверяем, что при авторизации из корзины происходит редирект в чекаут");
        krakenAssert.assertTrue(
                kraken.detect().isOnCheckout(),
                "\n"+message);
        assertAll();
    }

    @Step("Проверка что открылось окно авторизации")
    default void checkAuthFrameOpen() {
        assertTrue(
                kraken.detect().isAuthModalOpen(),
                "\nНе открывается модалка регистрации после попытки добавления товара из каталога в любимые товары");
    }
}
