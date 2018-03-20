package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.instamart.autotests.models.RetailerData;
import ru.instamart.autotests.models.UserData;

public class AuthorisationTest extends TestBase {

    @Test
    // тест успешной авторизации на лендинге
    public void testAuthOnLanding() throws Exception {
        // идем на лендинг
        app.getNavigationHelper().getLandingPage();
        // проверка на авторизованность
        if (app.getSessionHelper().userIsAuthorised) {
            app.getSessionHelper().doLogout();
            app.getNavigationHelper().getLandingPage();
        }
        // логинимся на лендинге
        app.getSessionHelper().doLogin(new UserData("instatestuser@yandex.ru", "instamart", null));
        // проверяем что авторизованы
        Assert.assertTrue(app.getSessionHelper().userIsAuthorised());
        // разлогиниваемся
        app.getSessionHelper().doLogout();
    }

    @Test
    // тест успешной авторизации на витрине
    public void testAuthOnRetailerPage() throws Exception {
        // идем на витрину ретейлера
        app.getNavigationHelper().getRetailerPage(new RetailerData("vkusvill"));
        // проверка на авторизованность
        if (app.getSessionHelper().userIsAuthorised) {
            app.getSessionHelper().doLogout();
            app.getNavigationHelper().getRetailerPage(new RetailerData("vkusvill"));
        }
        // логинимся на витрине
        app.getSessionHelper().doLogin(new UserData("instatestuser@yandex.ru", "instamart", null));
        // проверяем что авторизованы
        Assert.assertTrue(app.getSessionHelper().userIsAuthorised());
        // разлогиниваемся
        app.getSessionHelper().doLogout();
    }

    @Test
    // негативный тест попытки авторизации с неверным паролем
    public void testAuthWithWrongPassword() throws Exception {
        // идем на лендинг
        app.getNavigationHelper().getLandingPage();
        // проверка на авторизованность
        if (app.getSessionHelper().userIsAuthorised) {
            app.getSessionHelper().doLogout();
            app.getNavigationHelper().getLandingPage();
        }
        // логинимся на лендинге
        app.getSessionHelper().doLogin(new UserData("instatestuser@yandex.ru", "wrongpassword", null));
        // проверяем что неавторизованы
        Assert.assertFalse(app.getSessionHelper().userIsAuthorised());
    }

    @Test
    // негативный тест попытки авторизации без пароля
    public void testAuthWithoutPassword() throws Exception {
        // идем на лендинг
        app.getNavigationHelper().getLandingPage();
        // проверка на авторизованность
        if (app.getSessionHelper().userIsAuthorised) {
            app.getSessionHelper().doLogout();
            app.getNavigationHelper().getLandingPage();
        }
        // логинимся на лендинге
        app.getSessionHelper().doLogin(new UserData("instatestuser@yandex.ru", "", null));
        // проверяем что неавторизованы
        Assert.assertFalse(app.getSessionHelper().userIsAuthorised());
    }

    @Test
    // негативный тест попытки авторизации без email
    public void testAuthWithoutEmail() throws Exception {
        // идем на лендинг
        app.getNavigationHelper().getLandingPage();
        // проверка на авторизованность
        if (app.getSessionHelper().userIsAuthorised) {
            app.getSessionHelper().doLogout();
            app.getNavigationHelper().getLandingPage();
        }
        // логинимся на лендинге
        app.getSessionHelper().doLogin(new UserData("", "instamart", null));
        // проверяем что неавторизованы
        Assert.assertFalse(app.getSessionHelper().userIsAuthorised());
    }

}
