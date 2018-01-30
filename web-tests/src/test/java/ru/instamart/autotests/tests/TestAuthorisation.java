package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.instamart.autotests.models.RetailerData;
import ru.instamart.autotests.models.UserData;

public class TestAuthorisation extends TestBase {

    @Test
    // тест авторизации на лендинге
    public void testAuthOnLanding() throws Exception {
        // идем на лендинг
        app.getSiteNavHelper().goToLandingPage();
        // проверка на авторизованность
        if (app.getAuthorisationHelper().userIsAuthorised) {
            app.getAuthorisationHelper().doLogout();
            app.getSiteNavHelper().goToLandingPage();
        }
        // логинимся на лендинге
        app.getAuthorisationHelper().doLogin(new UserData("instatestuser@yandex.ru", "instamart"));
        // проверяем что авторизованы
        Assert.assertTrue(app.getAuthorisationHelper().userIsAuthorised());
        // разлогиниваемся
        app.getAuthorisationHelper().doLogout();
    }

    @Test
    // тест авторизации на витрине
    public void testAuthOnRetailerPage() throws Exception {
        // идем на витрину ретейлера
        app.getSiteNavHelper().goToRetailerPage(new RetailerData("vkusvill"));
        // проверка на авторизованность
        if (app.getAuthorisationHelper().userIsAuthorised) {
            app.getAuthorisationHelper().doLogout();
            app.getSiteNavHelper().goToRetailerPage(new RetailerData("vkusvill"));
        }
        // логинимся на витрине
        app.getAuthorisationHelper().doLogin(new UserData("instatestuser@yandex.ru", "instamart"));
        // проверяем что авторизованы
        Assert.assertTrue(app.getAuthorisationHelper().userIsAuthorised());
        // разлогиниваемся
        app.getAuthorisationHelper().doLogout();
    }

    @Test
    // негативный тест авторизации с неверным паролем
    public void testAuthWithWrongPassword() throws Exception {
        // идем на лендинг
        app.getSiteNavHelper().goToLandingPage();
        // проверка на авторизованность
        if (app.getAuthorisationHelper().userIsAuthorised) {
            app.getAuthorisationHelper().doLogout();
            app.getSiteNavHelper().goToLandingPage();
        }
        // логинимся на лендинге
        app.getAuthorisationHelper().doLogin(new UserData("instatestuser@yandex.ru", "wrongpassword"));
        // проверяем что неавторизованы
        Assert.assertFalse(app.getAuthorisationHelper().userIsAuthorised());
    }

}
