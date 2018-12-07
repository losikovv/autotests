package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;


// Регрессионные тесты старых багов и редких кейсов
// Все тесты с groups = {"regression"}


public class Bugfixes extends TestBase {

    @Test (
            description = "Тест возможности открыть авторизационную модалку после отправки формы восстановления пароля",
            groups = {"regression"},
            priority = 1001
    )
    public void STF987() throws Exception {

        kraken.get().baseUrl();
        kraken.perform().dropAuth();

        kraken.perform().recoverPassword("instatestuser@yandex.ru");
        kraken.perform().closeAuthModal();
        kraken.perform().openAuthModal();

        Assert.assertFalse(kraken.detect().isRecoveryRequested(),
                "Невозможно открыть авторизационную модалку после отправки формы восстановления пароля\n");
    }

}