package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.instamart.autotests.models.UserData;

public class SmokeTestSite extends TestBase {

    @Test
    // тест успешной авторизации на лендинге
    public void smokeTestSite() throws Exception {
        // TODO запилить проверки на корректное отображение страниц
        // TODO разнести группы страниц по отдельным тестам
        // TODO сделать переходы по статическим страницам циклом по списку страниц
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

        // идем в страницы раздела Профиль
        app.getSiteNavHelper().goToProfileAccount();
        app.getSiteNavHelper().goToProfileOrders();
        app.getSiteNavHelper().goToProfileAddresses();
        // идем по статическим страницам
        app.getSiteNavHelper().goToAbout();
        app.getSiteNavHelper().goToDelivery();
        app.getSiteNavHelper().goToRules();
        app.getSiteNavHelper().goToPayment();
        app.getSiteNavHelper().goToFAQ();
        app.getSiteNavHelper().goToTerms();
        app.getSiteNavHelper().goToContacts();
        // идем на лендинг "Много.ру"
        app.getSiteNavHelper().goToMnogoruLandingPage();

        // возвращаемся на лендинг
        app.getSiteNavHelper().goToLandingPage();
        // разлогиниваемся
        app.getAuthorisationHelper().doLogout();
    }

}
