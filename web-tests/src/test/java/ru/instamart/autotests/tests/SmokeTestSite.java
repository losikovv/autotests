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

        // идем в страницы раздела Профиль
        app.getNavigationHelper().goToProfileAccount();
        app.getNavigationHelper().goToProfileOrders();
        app.getNavigationHelper().goToProfileAddresses();
        // идем по статическим страницам
        app.getNavigationHelper().goToAbout();
        app.getNavigationHelper().goToDelivery();
        app.getNavigationHelper().goToRules();
        app.getNavigationHelper().goToPayment();
        app.getNavigationHelper().goToFAQ();
        app.getNavigationHelper().goToTerms();
        app.getNavigationHelper().goToContacts();
        // идем на лендинг "Много.ру"
        app.getNavigationHelper().goToMnogoruLandingPage();

        // возвращаемся на лендинг
        app.getNavigationHelper().goToLandingPage();
        // разлогиниваемся
        app.getAuthorisationHelper().doLogout();
    }

}
