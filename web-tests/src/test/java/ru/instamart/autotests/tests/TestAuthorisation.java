package ru.instamart.autotests.tests;

import org.testng.annotations.Test;
import ru.instamart.autotests.models.UserData;

public class TestAuthorisation extends TestBase {

    @Test
    public void testAuthOnLanding() throws Exception {
        // идем на лендинг
        app.getNavigationHelper().goToLandingPage();
        // логинимся на лендинге
        app.getAuthorisationHelper().doLoginOnLandingPage(new UserData("instatestuser@yandex.ru", "instamart"));
        // проверяем что авторизованы
        app.getAuthorisationHelper().assertAuthorised();
        // разлогиниваемся
        app.getAuthorisationHelper().doLogout();
    }

    @Test
    public void testAuthOnRetailerPageMetro() throws Exception {
        // идем на витрину ретейлера
        app.getNavigationHelper().goToRetailerPage();
        // логинимся на витрине
        app.getAuthorisationHelper().doLoginOnRetailerPage(new UserData("instatestuser@yandex.ru", "instamart"));
        // проверяем что авторизованы
        app.getAuthorisationHelper().assertAuthorised();
        // разлогиниваемся
        app.getAuthorisationHelper().doLogout();
    }

}
