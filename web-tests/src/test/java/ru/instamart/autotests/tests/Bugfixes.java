package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;



    // Регрессионные тесты старых багов и редких кейсов
    // Все тесты с groups = {"regression"}



public class Bugfixes extends TestBase {


    // шаблон для тестов
    @Test ( enabled = false,
            description = "Test description",
            groups = {"regression"},
            priority = 1000
    )
    public void testName(){
        kraken.search().item("смысл жизни");

        Assert.assertTrue(kraken.detect().isSearchResultsEmpty(),
                "Result is not expected\n");
    }


    @Test (
            description = "Тест возможности открыть авторизационную модалку после запроса восстановления пароля",
            groups = {"regression"},
            priority = 1001
    )
    public void STF987() throws Exception {
        kraken.getNavigationHelper().getBaseUrl();
        kraken.getSessionHelper().dropAuth();
        kraken.getSessionHelper().recoverPassword("instatestuser@yandex.ru");
        kraken.getSessionHelper().closeAuthModal();
        kraken.getSessionHelper().openAuthModal();

        Assert.assertFalse(kraken.getSessionHelper().isRecoverySent(),
                "Can't open auth modal right after sending password recovery request \n");
    }

}