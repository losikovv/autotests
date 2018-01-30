package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.instamart.autotests.models.RetailerData;
import ru.instamart.autotests.models.UserData;

public class TestAuthorisation extends TestBase {

    @Test
    public void testAuthOnLanding() throws Exception {
        // идем на лендинг
        app.getNavigationHelper().goToLandingPage();
        // проверка на авторизованность
        if (app.getAuthorisationHelper().userIsAuthorised) { app.getAuthorisationHelper().doLogout(); }
        // логинимся на лендинге
        app.getAuthorisationHelper().doLogin(new UserData("instatestuser@yandex.ru", "instamart"));
        // проверяем что авторизованы
        Assert.assertTrue(app.getAuthorisationHelper().userIsAuthorised());
        // разлогиниваемся
        app.getAuthorisationHelper().doLogout();
    }

    @Test
    public void testAuthOnRetailerPage() throws Exception {
        // идем на витрину ретейлера
        app.getNavigationHelper().goToRetailerPage(new RetailerData("vkusvill"));
        // проверка на авторизованность
        if (app.getAuthorisationHelper().userIsAuthorised) { app.getAuthorisationHelper().doLogout(); }
        // логинимся на витрине
        app.getAuthorisationHelper().doLogin(new UserData("instatestuser@yandex.ru", "instamart"));
        // проверяем что авторизованы
        Assert.assertTrue(app.getAuthorisationHelper().userIsAuthorised());
        // разлогиниваемся
        app.getAuthorisationHelper().doLogout();
    }

}
