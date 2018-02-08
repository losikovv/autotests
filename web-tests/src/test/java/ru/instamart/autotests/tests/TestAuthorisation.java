package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.instamart.autotests.models.RetailerData;
import ru.instamart.autotests.models.UserData;

public class TestAuthorisation extends TestBase {

    @Test
    // тест успешной авторизации на лендинге
    public void testAuthOnLanding() throws Exception {
        // идем на лендинг
        app.getNavigationHelper().goToLandingPage();
        // проверка на авторизованность
        if (app.getAuthorisationHelper().userIsAuthorised) {
            app.getAuthorisationHelper().doLogout();
            app.getNavigationHelper().goToLandingPage();
        }
        // логинимся на лендинге
        app.getAuthorisationHelper().doLogin(new UserData("instatestuser@yandex.ru", "instamart"));
        // проверяем что авторизованы
        Assert.assertTrue(app.getAuthorisationHelper().userIsAuthorised());
        // разлогиниваемся
        app.getAuthorisationHelper().doLogout();
    }

    @Test
    // тест успешной авторизации на витрине
    public void testAuthOnRetailerPage() throws Exception {
        // идем на витрину ретейлера
        app.getNavigationHelper().goToRetailerPage(new RetailerData("vkusvill"));
        // проверка на авторизованность
        if (app.getAuthorisationHelper().userIsAuthorised) {
            app.getAuthorisationHelper().doLogout();
            app.getNavigationHelper().goToRetailerPage(new RetailerData("vkusvill"));
        }
        // логинимся на витрине
        app.getAuthorisationHelper().doLogin(new UserData("instatestuser@yandex.ru", "instamart"));
        // проверяем что авторизованы
        Assert.assertTrue(app.getAuthorisationHelper().userIsAuthorised());
        // разлогиниваемся
        app.getAuthorisationHelper().doLogout();
    }

    @Test
    // негативный тест попытки авторизации с неверным паролем
    public void testAuthWithWrongPassword() throws Exception {
        // идем на лендинг
        app.getNavigationHelper().goToLandingPage();
        // проверка на авторизованность
        if (app.getAuthorisationHelper().userIsAuthorised) {
            app.getAuthorisationHelper().doLogout();
            app.getNavigationHelper().goToLandingPage();
        }
        // логинимся на лендинге
        app.getAuthorisationHelper().doLogin(new UserData("instatestuser@yandex.ru", "wrongpassword"));
        // проверяем что неавторизованы
        Assert.assertFalse(app.getAuthorisationHelper().userIsAuthorised());
    }

    @Test
    // негативный тест попытки авторизации без пароля
    public void testAuthWithoutPassword() throws Exception {
        // идем на лендинг
        app.getNavigationHelper().goToLandingPage();
        // проверка на авторизованность
        if (app.getAuthorisationHelper().userIsAuthorised) {
            app.getAuthorisationHelper().doLogout();
            app.getNavigationHelper().goToLandingPage();
        }
        // логинимся на лендинге
        app.getAuthorisationHelper().doLogin(new UserData("instatestuser@yandex.ru", ""));
        // проверяем что неавторизованы
        Assert.assertFalse(app.getAuthorisationHelper().userIsAuthorised());
    }

    @Test
    // негативный тест попытки авторизации без email
    public void testAuthWithoutEmail() throws Exception {
        // идем на лендинг
        app.getNavigationHelper().goToLandingPage();
        // проверка на авторизованность
        if (app.getAuthorisationHelper().userIsAuthorised) {
            app.getAuthorisationHelper().doLogout();
            app.getNavigationHelper().goToLandingPage();
        }
        // логинимся на лендинге
        app.getAuthorisationHelper().doLogin(new UserData("", "instamart"));
        // проверяем что неавторизованы
        Assert.assertFalse(app.getAuthorisationHelper().userIsAuthorised());
    }

}
