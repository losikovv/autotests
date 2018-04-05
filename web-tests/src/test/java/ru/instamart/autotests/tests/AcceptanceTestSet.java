package ru.instamart.autotests.tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AcceptanceTestSet extends TestBase {



    // Тест сайта и админки для CI
    // TODO запилить тестовый набор в TestRail с признаком automated
    // TODO запилить автоматическое создание и заполнение рана в TestRail результатами теста
    // TODO добавить тесты на недоступность админки чувакам без админ прав, в т ч авторизованных через соцсети
    // TODO сделать тест-сет с вызовом по порядку нужных тестовых классов, функциональные тесты должны работать независимо друг от друга и запускаться в любом порядке



    @Test(priority = 0)
    //регистрация пользователя
    public void registration() throws Exception {
        app.getNavigationHelper().getBaseUrl();
        // регаем нового тестового юзера
        app.getSessionHelper().regNewAutotestUser();
        // проверияем авторизованность
        Assert.assertTrue(app.getSessionHelper().userIsAuthorised());
        // разлогиниваемся
        app.getSessionHelper().doLogout();
    }

    @Test(priority = 1)
    public void authorisation() throws Exception {
        // идем и чекаем лендинг
        getAndAssertPageIsAvailable("https://instamart.ru/");
        // логинимся юзером для автотестов с админскими правами
        app.getSessionHelper().doLoginAsAdmin();
        // проверяем что авторизованы
        Assert.assertTrue(app.getSessionHelper().userIsAuthorised(), "User wasn't successfully authorised"+"\n");
    }

    @Test(priority = 2)
    // чекаем страницы ритейлеров
    // TODO переделать чек страниц всех ретейлеров по списку ретейлеров
    // TODO забирать список ритейлеров из БД или из админки с признаком активности
    // TODO проверять витрины активных ритейлеров на доступность, витрины неактивных - на недоступность
    public void checkRetailerPages() throws Exception {
        getAndAssertPageIsAvailable("https://instamart.ru/metro");
        getAndAssertPageIsAvailable("https://instamart.ru/lenta");

        // неактивный ритейлер
        getAndAssertPageIs404("https://instamart.ru/selgros");

        getAndAssertPageIsAvailable("https://instamart.ru/vkusvill");
        getAndAssertPageIsAvailable("https://instamart.ru/karusel");
    }

    @Test(priority = 3)
    // чекаем недоступность пустого чекаута при ненабранной корзине
    public void emptyCheckoutUnreachable() throws Exception {
        // TODO добавить проверку на текущую сумму корзины и если она выше суммы минимального заказа - очищать корзину
        assertPageIsUnreachable("https://instamart.ru/checkout/edit?");
    }

    @Test(priority = 4)
    // чекаем страницы профиля
    // TODO переделать чек страниц циклом по списку
    public void checkProfilePages() throws Exception {
        getAndAssertPageIsAvailable("https://instamart.ru/user/edit");
        getAndAssertPageIsAvailable("https://instamart.ru/user/orders");
        getAndAssertPageIsAvailable("https://instamart.ru/user/addresses");
    }

    @Test(priority = 5)
    // чекаем статические страницы
    // TODO переделать чек страниц циклом по списку
    // TODO забирать список страниц из БД или из админки
    public void checkStaticPages() throws Exception {
        getAndAssertPageIsAvailable("https://instamart.ru/about");
        getAndAssertPageIsAvailable("https://instamart.ru/delivery");
        getAndAssertPageIsAvailable("https://instamart.ru/rules");
        getAndAssertPageIsAvailable("https://instamart.ru/payment");
        getAndAssertPageIsAvailable("https://instamart.ru/return");
        getAndAssertPageIsAvailable("https://instamart.ru/faq");
        getAndAssertPageIsAvailable("https://instamart.ru/terms");
        getAndAssertPageIsAvailable("https://instamart.ru/contacts");
    }

    @Test(priority = 6)
    // чекаем лендинги
    // TODO переделать чек лендингов циклом по списку
    // TODO забирать список лендингов из БД или из админки
    public void checkLandings() throws Exception {
        getAndAssertPageIsAvailable("https://instamart.ru/mnogoru");
        getAndAssertPageIsAvailable("https://instamart.ru/sovest");
        getAndAssertPageIsAvailable("https://instamart.ru/halva");
        getAndAssertPageIsAvailable("https://instamart.ru/landings/feedback");
    }

    @Test(priority = 7)
    // чекаем корневые страницы админки
    // TODO переделать чек лендингов циклом по списку
    public void checkAdminPages() throws AssertionError {
        getAndAssertPageIsAvailable("https://instamart.ru/admin/shipments");
        getAndAssertPageIsAvailable("https://instamart.ru/admin/retailers");
        getAndAssertPageIsAvailable("https://instamart.ru/admin/products");
        getAndAssertPageIsAvailable("https://instamart.ru/admin/imports");
        getAndAssertPageIsAvailable("https://instamart.ru/admin/reports");
        getAndAssertPageIsAvailable("https://instamart.ru/admin/general_settings/edit");
        getAndAssertPageIsAvailable("https://instamart.ru/admin/promo_cards");
        getAndAssertPageIsAvailable("https://instamart.ru/admin/users");
        getAndAssertPageIsAvailable("https://instamart.ru/admin/pages");
    }

    @Test(priority = 8)
    public void cleanupAutotestUsers() throws Exception {
        app.getSessionHelper().deleteAllAutotestUsers();
        app.getNavigationHelper().getAdminPage("users?q%5Bemail_cont%5D=%40example.com");
        Assert.assertFalse(app.getNavigationHelper().isElementPresent(By.xpath("//*[@id='content']/div/table/tbody/tr")),"Seems like there are some autotest users left in admin panel");
    }

    @Test(priority = 9)
    // логаут
    public void logout() throws Exception {
        app.getSessionHelper().doLogout();
        assertPageIsAvailable();
    }

}

