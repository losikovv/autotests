package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.instamart.autotests.models.RetailerData;
import ru.instamart.autotests.testdata.Generate;

public class RegistrationTest extends TestBase {

    @Test
    // тест успешной регистрации на лендинге
    public void testRegistrationOnLandingPage() throws Exception {
        // идем на витрину
        app.getNavigationHelper().getLandingPage();
        // регаем нового тестового юзера
        app.getSessionHelper().regNewUser(Generate.testUserData());
        // идем на в профиль и на главную
        app.getNavigationHelper().getProfilePage();
        app.getNavigationHelper().goToHomepage();
        // проверияем авторизованность
        Assert.assertTrue(app.getSessionHelper().isUserAuthorised(), "Can't approve the registration is performed correctly"+"\n");
        // разлогиниваемся
        app.getSessionHelper().doLogout();
    }

    @Test
    // тест успешной регистрации на витрине ритейлера
    public void testRegistrationOnRetailerPage() throws Exception {
        // идем на витрину
        app.getNavigationHelper().getRetailerPage(new RetailerData("metro"));
        // регаем нового тестового юзера
        app.getSessionHelper().regNewUser(Generate.testUserData());
        // идем на в профиль и на главную
        app.getNavigationHelper().getProfilePage();
        app.getNavigationHelper().goToHomepage();
        // проверияем авторизованность
        Assert.assertTrue(app.getSessionHelper().isUserAuthorised(),"Can't approve the registration is performed correctly"+"\n");
        // разлогиниваемся
        app.getSessionHelper().doLogout();
    }

}
