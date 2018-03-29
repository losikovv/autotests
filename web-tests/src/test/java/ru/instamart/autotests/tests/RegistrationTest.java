package ru.instamart.autotests.tests;

import org.testng.annotations.Test;
import ru.instamart.autotests.models.RetailerData;

public class RegistrationTest extends TestBase {

    @Test
    // тест успешной регистрации на лендинге
    public void testRegistrationOnLandingPage() throws Exception {
        // идем на витрину
        app.getNavigationHelper().getLandingPage();
        // регаем нового юзера
        app.getSessionHelper().regNewAutotestUser();
        // идем в профиль
        app.getNavigationHelper().goToProfile();
        // идем на главную
        app.getNavigationHelper().goToHomepage();
        // проверияем авторизованность
        //Assert.assertTrue(app.getSessionHelper().userIsAuthorised);
        // разлогиниваемся
        app.getSessionHelper().doLogout();
    }

    @Test
    // тест успешной регистрации на витрине ритейлера
    public void testRegistrationOnRetailerPage() throws Exception {
        // идем на витрину
        app.getNavigationHelper().getRetailerPage(new RetailerData("metro"));
        // регаем нового юзера
        app.getSessionHelper().regNewAutotestUser();
        // идем в профиль
        app.getNavigationHelper().goToProfile();
        // идем на главную
        app.getNavigationHelper().goToHomepage();
        // проверияем авторизованность
        //Assert.assertTrue(app.getSessionHelper().userIsAuthorised);
        // разлогиниваемся
        app.getSessionHelper().doLogout();
    }

}
