package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;



    // Регрессионные тесты старых багов
    // Все тесты с groups = {"regression"}



public class Bugfixes extends TestBase {


    // шаблон для тестов
    @Test (
            description = "Test description",
            groups = {"regression"},
            priority = 999
    )
    public void testName(){
        app.getShoppingHelper().searchItem("смысл жизни");

        // Проверяем что-нибудь
        Assert.assertTrue(app.getShoppingHelper().isSearchResultsEmpty(),
                "Result is not expected\n");
    }


}
