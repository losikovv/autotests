package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.instamart.autotests.models.UserData;

public class SmokeTestAdmin extends TestBase {

    @Test
    public void testAccessAdministration(){
        // TODO заменить на метод accessAdmin
        // идем на лендинг
        app.getNavigationHelper().goToLandingPage();
        // логинимся на лендинге
        app.getAuthorisationHelper().doLogin(new UserData("stanislav.klimov@instamart.ru", "allhailinstamart"));
        // идем в админку
        app.getAdminNavigationHelper().goToAdmin();
        // проверяем что есть доступ в админку
        Assert.assertTrue(app.getAuthorisationHelper().itsInAdmin());
        // разлогиниваемся
        app.getAuthorisationHelper().doLogout();
    }

    @Test
    public void smoketestLogistics() throws InterruptedException {
        // идем в админку
        app.getAdminNavigationHelper().goToAdmin();
        // проверяем на авторизованность и если нет - логинимся
        // TODO написать метод accessAdmin в хелпере админки, который идет и логигнится в админку
        // идем в раздел Logistics
        app.getAdminNavigationHelper().goToAdminLogistics();
        // проверяем что раздел работает
        Assert.assertTrue(app.getAuthorisationHelper().itsInAdmin());
    }
}
