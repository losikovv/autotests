package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.instamart.autotests.models.UserData;

public class SmokeTestSite extends TestBase {

    @Test
    // тест успешной авторизации на лендинге
    public void smokeTestSite() throws Exception {
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
        // идем в Профиль
        app.getSiteNavHelper().goToProfileAccount();
        // идем в Заказы
        app.getSiteNavHelper().goToProfileOrders();
        // идем в Адреса
        app.getSiteNavHelper().goToProfileAddresses();
        // TODO добавить переходы по сайту в смоук-тест
        // возвращаемся на лендинг
        app.getSiteNavHelper().goToLandingPage();
        // разлогиниваемся
        app.getAuthorisationHelper().doLogout();
    }

}
