package ru.instamart.autotests.tests;

import org.testng.annotations.Test;
import ru.instamart.autotests.models.UserData;

public class TestAuthorisation extends TestBase {

    @Test
    public void testAuthOnLanding() throws Exception {
        //логинимся на лендинге
        app.doLoginOnLanding(new UserData("instatestuser@yandex.ru", "instamart"));
        // проверяем что на странице есть кнопка Профиль
        app.assertAuthorised();
        //разлогиниваемся
        app.doLogout();
    }

    @Test
    public void testAuthOnRetailerPageMetro() throws Exception {
        //идем на витрину
        app.goToRetailerPage();
        //логинимся на витрине
        app.doLoginOnRetailerPage(new UserData("instatestuser@yandex.ru", "instamart"));
        // проверяем что на странице есть кнопка Профиль
        app.assertAuthorised();
        //разлогиниваемся
        app.doLogout();
    }

}
