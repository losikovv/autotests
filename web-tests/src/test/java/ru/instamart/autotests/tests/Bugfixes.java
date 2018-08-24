package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;



    // Регрессионные тесты старых багов и редких кейсов
    // Все тесты с groups = {"regression"}



public class Bugfixes extends TestBase {


    // шаблон для тестов
    @Test (
            description = "Test description",
            groups = {"regression"},
            priority = 1000
    )
    public void testName(){
        app.getShoppingHelper().searchItem("смысл жизни");

        // Проверяем что-нибудь
        Assert.assertTrue(app.getShoppingHelper().isSearchResultsEmpty(),
                "Result is not expected\n");
    }


    @Test (
            description = "Тест возможности открыть авторизационную модалку после запроса восстановления пароля",
            groups = {"regression"},
            priority = 1001
    )
    public void STF987(){
        app.getNavigationHelper().getBaseUrl();
        app.getSessionHelper().dropAuth();
        app.getSessionHelper().recoverPassword("instatestuser@yandex.ru");
        app.getSessionHelper().closeAuthModal();
        app.getSessionHelper().openAuthModal();

        Assert.assertFalse(app.getSessionHelper().isRecoverySent(),
                "Can't open auth modal right after sending password recovery request \n");
    }

}