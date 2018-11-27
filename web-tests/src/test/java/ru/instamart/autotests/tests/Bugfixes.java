package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;



    // Регрессионные тесты старых багов и редких кейсов
    // Все тесты с groups = {"regression"}



public class Bugfixes extends TestBase {


    // Шаблон для тестов

    @Test ( enabled = false,
            description = "Название теста",
            groups = {"regression"},
            priority = 1000
    )
    public void testName() throws Exception{
        kraken.perform().dropAuth();                                    // 1 - предусловия

        kraken.search().item("смысл жизни");                     // 2 - шаги теста

        Assert.assertTrue(kraken.detect().isSearchResultsEmpty(),       // 3 - проверка
                "Result is not expected\n");

        kraken.get().baseUrl();                                         // 4 - уборка (опционально)
    }


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

        Assert.assertFalse(kraken.detect().isRecoverySent(),
                "Невозможно открыть авторизационную модалку после отправки формы восстановления пароля\n");
    }

}