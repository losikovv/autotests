package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.instamart.autotests.models.UserData;

import static ru.instamart.autotests.testdata.RandomDataGenerator.randomSuffix;

public class ContinuousIntegrationTest extends TestBase {



    // Тест сайта и админки для CI
    // TODO запилить тестовый набор в TestRail с признаком automated
    // TODO запилить автоматическое создание и заполнение рана в TestRail результатами теста
    // TODO добавить тесты на недоступность админки чувакам без админ прав, в т ч авторизованных через соцсети
    // TODO сделать тест-сет с вызовом по порядку нужных тестовых классов, функциональные тесты должны работать независимо друг от друга и запускаться в любом порядке



    @Test
    public void authorisation() throws Exception {
        // идем и чекаем лендинг
        getAndAssertPageIsAvailable("https://instamart.ru/");
        // логинимся
        app.getSessionHelper().doLogin(new UserData("autotestuser@instamart.ru", "DyDrasLipMeibe7", null));
        // проверяем что авторизованы
        Assert.assertTrue(app.getSessionHelper().userIsAuthorised(), "User wasn't successfully authorised"+"\n");
    }

    @Test
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

    @Test
    // чекаем недоступность пустого чекаута при ненабранной корзине
    public void emptyCheckoutUnreachable() throws Exception {
        // TODO добавить проверку на текущую сумму корзины и если она выше суммы минимального заказа - очищать корзину
        assertPageIsUnreachable("https://instamart.ru/checkout/edit?");
    }

    @Test
    // чекаем страницы профиля
    // TODO переделать чек страниц циклом по списку
    public void checkProfilePages() throws Exception {
        getAndAssertPageIsAvailable("https://instamart.ru/user/edit");
        getAndAssertPageIsAvailable("https://instamart.ru/user/orders");
        getAndAssertPageIsAvailable("https://instamart.ru/user/addresses");
    }

    @Test
    // чекаем статические страницы
    // TODO переделать чек страниц циклом по списку
    // TODO забирать список страниц из БД или из админки
    public void checkStaticPages() throws Exception {
        getAndAssertPageIsAvailable("https://instamart.ru/about");
        getAndAssertPageIsAvailable("https://instamart.ru/delivery");
        getAndAssertPageIsAvailable("https://instamart.ru/rules");
        getAndAssertPageIsAvailable("https://instamart.ru/payment");
        getAndAssertPageIsAvailable("https://instamart.ru/faq");
        getAndAssertPageIsAvailable("https://instamart.ru/terms");
        getAndAssertPageIsAvailable("https://instamart.ru/contacts");
    }

    @Test
    // чекаем партнерские лендинги
    // TODO переделать чек лендингов циклом по списку
    // TODO забирать список лендингов из БД или из админки
    public void checkPartnerLandings() throws Exception {
        getAndAssertPageIsAvailable("https://instamart.ru/mnogoru");
    }

    @Test
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

    @Test
    // логаут
    public void logout() throws Exception {
        app.getSessionHelper().doLogout();
        assertPageIsAvailable();
    }

    @Test
    //регистрация пользователя
    public void testRegistrationOnLandingPage() throws Exception {
        // идем на витрину
        app.getNavigationHelper().getLandingPage();
        // проверка на авторизованность
        if (app.getSessionHelper().userIsAuthorised) {
            app.getSessionHelper().doLogout();
            app.getNavigationHelper().getLandingPage();
        }
        // регаемся
        app.getSessionHelper().regUser(new UserData("autotest"+randomSuffix()+"@example.com","instamart", "Автотест Юзер"));
        // идем в профиль
        app.getNavigationHelper().goToProfile();
        // идем на главную
        app.getNavigationHelper().goToHomepage();
        // проверияем авторизованность
        //Assert.assertTrue(app.getSessionHelper().userIsAuthorised);
        // разлогиниваемся
        app.getSessionHelper().doLogout();
    }

}

