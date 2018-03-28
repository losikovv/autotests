package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.instamart.autotests.models.RetailerData;
import ru.instamart.autotests.models.UserData;

import static ru.instamart.autotests.appmanager.HelperBase.randomSuffix;


public class RegistrationTest extends TestBase {


    @Test
    // тест успешной регистрации на лендинге
    public void testRegistrationOnLandingPage() throws Exception {
        // идем на витрину
        app.getNavigationHelper().getLandingPage();
        // регаемся
        app.getSessionHelper().regUser(new UserData("autotest"+randomSuffix()+"@example.com","instamart", "Автотест Юзер"));
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
        // регаемся
        app.getSessionHelper().regUser(new UserData("autotest"+randomSuffix()+"@example.com","instamart", "Автотест Юзер"));
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
