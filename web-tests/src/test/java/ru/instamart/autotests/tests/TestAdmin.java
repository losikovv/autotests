package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.instamart.autotests.models.UserData;

public class TestAdmin extends TestBase {

    @Test
    public void accessAdministration(){
        // идем на лендинг
        app.getNavigationHelper().goToLandingPage();
        // логинимся на лендинге
        app.getAuthorisationHelper().doLogin(new UserData("stanislav.klimov@instamart.ru", "allhailinstamart"));
        // идем в админку
        app.getAdminHelper().goToAdmin();
        // проверяем что есть доступ в админку
        Assert.assertTrue(app.getAuthorisationHelper().userIsInAdmin());
        // разлогиниваемся
        app.getAuthorisationHelper().doLogout();
    }
}
