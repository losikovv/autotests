package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.instamart.autotests.models.RetailerData;
import ru.instamart.autotests.models.UserData;

public class RegistrationTest extends TestBase {


    @Test
    // тест успешной регистрации на лендинге
    // TODO запилить автогенерацию логина
    public void testRegistrationOnLandingPage() throws Exception {
        // идем на витрину
        app.getNavigationHelper().getLandingPage();
        // регаемся
        app.getSessionHelper().regUser(new UserData("autotestX14Y07LRT@example.com","instamart", "Автотест Юзер"));
        // идем в профиль
        app.getNavigationHelper().goToProfile();
        // идем на главную
        app.getNavigationHelper().goToHome();
        // проверияем авторизованность
        Assert.assertTrue(app.getSessionHelper().userIsAuthorised);
        // разлогиниваемся
        app.getSessionHelper().doLogout();
    }

    @Test
    // тест успешной регистрации на витрине ритейлера
    // TODO запилить автогенерацию логина
    public void testRegistrationOnRetailerPage() throws Exception {
        // идем на витрину
        app.getNavigationHelper().getRetailerPage(new RetailerData("metro"));
        // регаемся
        app.getSessionHelper().regUser(new UserData("autotestX14Y07LRT@example.com","instamart", "Автотест Юзер"));
        // идем в профиль
        app.getNavigationHelper().goToProfile();
        // идем на главную
        app.getNavigationHelper().goToHome();
        // проверияем авторизованность
        Assert.assertTrue(app.getSessionHelper().userIsAuthorised);
        // разлогиниваемся
        app.getSessionHelper().doLogout();
    }
}
