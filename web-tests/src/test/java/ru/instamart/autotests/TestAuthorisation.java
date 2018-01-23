package ru.instamart.autotests;

import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestAuthorisation extends TestBase {

    @Test
    public void testAuthOnLanding() throws Exception {
        //логинимся на лендинге
        doLoginOnLanding(new UserData("instatestuser@yandex.ru", "instamart"));
        // проверяем что на странице есть кнопка Профиль
        assertAuthorised();
        //разлогиниваемся
        doLogout();
    }

    @Test
    public void testAuthOnRetailerPageMetro() throws Exception {
        //идем на витрину
        goToRetailerPage();
        //логинимся на витрине
        doLoginOnRetailerPage(new UserData("instatestuser@yandex.ru", "instamart"));
        // проверяем что на странице есть кнопка Профиль
        assertAuthorised();
        //разлогиниваемся
        doLogout();
    }

}
