package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.instamart.autotests.models.UserData;

public class SmokeTestAdmin extends TestBase {

    @Test
    public void testAccessAdministration(){
        // идем на лендинг
        app.getSiteNavHelper().goToLandingPage();
        // логинимся на лендинге
        app.getAuthorisationHelper().doLogin(new UserData("stanislav.klimov@instamart.ru", "allhailinstamart"));
        // идем в админку
        app.getAdminNavHelper().goToAdmin();
        // проверяем что есть доступ в админку
        Assert.assertTrue(app.getAuthorisationHelper().itsInAdmin());
        // разлогиниваемся
        app.getAuthorisationHelper().doLogout();
    }

    @Test
    public void smoketestLogistics() throws InterruptedException {
        // идем в админку
        app.getAdminNavHelper().goToAdmin();
        // проверяем на авторизованность и если нет - логинимся
        // TODO написать метод accessAdmin в хелпере админки, который идет и логигнится в админку
        // идем в раздел Logistics
        app.getAdminNavHelper().goToAdminLogistics();
        // 3 сек задержка
        app.wait(3000);
        // проверяем что раздел работает
        Assert.assertTrue(app.getAuthorisationHelper().itsInAdmin());
    }
}
