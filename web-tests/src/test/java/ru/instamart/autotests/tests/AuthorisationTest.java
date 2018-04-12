package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.instamart.autotests.models.RetailerData;
import ru.instamart.autotests.models.UserData;

public class AuthorisationTest extends TestBase {

    //TODO добавить тесты на авторизацию через соцсети

    @Test(description = "Тест успешной авторизации на лендинге")
    public void testAuthOnLanding() throws Exception {
        // идем на лендинг
        app.getNavigationHelper().getLandingPage();
        // деавторизуемся если уже залогигнены
        if (app.getSessionHelper().isUserAuthorised()) {
            app.getSessionHelper().doLogout();
            app.getNavigationHelper().getLandingPage();
        }
        // логинимся
        app.getSessionHelper().doLogin(new UserData("instatestuser@yandex.ru", "instamart", null));
        // проверяем что авторизованы
        Assert.assertTrue(app.getSessionHelper().isUserAuthorised(),"Can't approve the authorisation is successful"+"\n");
        // разлогиниваемся
        app.getSessionHelper().doLogout();
    }

    @Test(description = "Тест успешной авторизации на витрине")
    public void testAuthOnRetailerPage() throws Exception {
        // идем на витрину ретейлера
        app.getNavigationHelper().getRetailerPage(new RetailerData("vkusvill"));
        // деавторизуемся если уже залогигнены
        if (app.getSessionHelper().isUserAuthorised()) {
            app.getSessionHelper().doLogout();
            app.getNavigationHelper().getRetailerPage(new RetailerData("vkusvill"));
        }
        // логинимся
        app.getSessionHelper().doLogin(new UserData("instatestuser@yandex.ru", "instamart", null));
        // проверяем что авторизованы
        Assert.assertTrue(app.getSessionHelper().isUserAuthorised(),"Can't approve the authorisation is successful"+"\n");
        // разлогиниваемся
        app.getSessionHelper().doLogout();
    }

    @Test(description = "Негативный тест попытки авторизации с неверным паролем")
    public void testAuthWithWrongPassword() throws Exception {
        // идем на лендинг
        app.getNavigationHelper().getLandingPage();
        // деавторизуемся если уже залогигнены
        if (app.getSessionHelper().isUserAuthorised()) {
            app.getSessionHelper().doLogout();
            app.getNavigationHelper().getLandingPage();
        }
        // пытаемся залогиниться с неверным паролем
        app.getSessionHelper().doLogin(new UserData("instatestuser@yandex.ru", "wrongpassword", null));
        // проверяем что неавторизованы
        Assert.assertFalse(app.getSessionHelper().isUserAuthorised(), "It's possible to log-in with wrong password!"+"\n");
    }

    @Test(description = "Негативный тест попытки авторизации без пароля")
    public void testAuthWithoutPassword() throws Exception {
        // идем на лендинг
        app.getNavigationHelper().getLandingPage();
        // деавторизуемся если уже залогигнены
        if (app.getSessionHelper().isUserAuthorised()) {
            app.getSessionHelper().doLogout();
            app.getNavigationHelper().getLandingPage();
        }
        // пытаемся залогиниться без пароля
        app.getSessionHelper().doLogin(new UserData("instatestuser@yandex.ru", "", null));
        // проверяем что неавторизованы
        Assert.assertFalse(app.getSessionHelper().isUserAuthorised(),"It's possible to log-in without entering a password!"+"\n");
    }

    @Test(description = "Негативный тест попытки авторизации без email")
    public void testAuthWithoutEmail() throws Exception {
        // идем на лендинг
        app.getNavigationHelper().getLandingPage();
        // деавторизуемся если уже залогигнены
        if (app.getSessionHelper().isUserAuthorised()) {
            app.getSessionHelper().doLogout();
            app.getNavigationHelper().getLandingPage();
        }
        // пытаемся залогиниться без email
        app.getSessionHelper().doLogin(new UserData("", "instamart", null));
        // проверяем что неавторизованы
        Assert.assertFalse(app.getSessionHelper().isUserAuthorised(), "It's possible to log-in without entering an email!"+"\n");
    }

}
