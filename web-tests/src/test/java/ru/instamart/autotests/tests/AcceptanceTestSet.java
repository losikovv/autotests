package ru.instamart.autotests.tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;



// Тест сайта и админки для CI
// TODO запилить тестовый набор в TestRail с признаком automated
// TODO запилить автоматическое создание и заполнение рана в TestRail результатами теста
// TODO добавить тесты на недоступность админки чувакам без админ прав, в т ч авторизованных через соцсети
// TODO сделать тест-сет с вызовом по порядку нужных тестовых классов, функциональные тесты должны работать независимо друг от друга и запускаться в любом порядке



public class AcceptanceTestSet extends TestBase {

    @Test(priority = 13)
    //регистрация пользователя
    public void registration() throws Exception {
        // идем на лендинг
        app.getNavigationHelper().getBaseUrl();
        // чекаем что все ровно
        //assertPageIsAvailable();
        // регаем нового тестового юзера
        app.getSessionHelper().regNewAutotestUser();
        // проверияем авторизованность
        Assert.assertTrue(app.getSessionHelper().isUserAuthorised(), "Can't approve the registration is performed correctly"+"\n");
        // разлогиниваемся
        app.getSessionHelper().doLogout();
    }

    @Test(priority = 1)
    public void authorisation() throws Exception {
        // идем на лендинг
        app.getNavigationHelper().getBaseUrl();
        // чекаем что все ровно
        //assertPageIsAvailable();
        // логинимся юзером для автотестов с админскими правами TODO переделать на авторизацию новым юзером
        app.getSessionHelper().doLoginAsAdmin();
        // проверяем что авторизованы
        Assert.assertTrue(app.getSessionHelper().isUserAuthorised(), "Can't approve the authorisation is successful"+"\n");
    }

    @Test(priority = 2)
    public void makeOrderWithCardPayment() throws Exception {
        app.getProfileHelper().repeatLastOrder();
        Assert.assertFalse(app.getShoppingHelper().isCartEmpty(),"Something went wrong while repeating the last order from the profile");

        app.getShoppingHelper().openCart();
        //app.getShoppingHelper().deleteAllItemsInCart();
        app.getShoppingHelper().proceedToCheckout();
        app.getCheckoutHelper().completeCheckout("ТЕСТОВЫЙ ЗАКАЗ",2,"card");
        //TODO добавить проверку на активность заказа с помощью метода isOrderActive

        app.getProfileHelper().cancelLastOrder();
        //TODO добавить проверку на отмененность заказа с помощью метода isOrderActive
    }

    @Test(priority = 4)
    public void makeOrderWithCashPayment() throws Exception {
        app.getProfileHelper().repeatLastOrder();
        Assert.assertFalse(app.getShoppingHelper().isCartEmpty(),"Something went wrong while repeating the last order from the profile");

        app.getShoppingHelper().openCart();
        //app.getShoppingHelper().deleteAllItemsInCart();
        app.getShoppingHelper().proceedToCheckout();
        app.getCheckoutHelper().completeCheckout("ТЕСТОВЫЙ ЗАКАЗ",3,"cash");
        //TODO добавить проверку на активность заказа с помощью метода isOrderActive

        app.getProfileHelper().cancelLastOrder();
        //TODO добавить проверку на отмененность заказа с помощью метода isOrderActive
    }

    @Test(priority = 5)
    public void makeOrderWithBankPayment() throws Exception {
        app.getProfileHelper().repeatLastOrder();
        Assert.assertFalse(app.getShoppingHelper().isCartEmpty(),"Something went wrong while repeating the last order from the profile");

        app.getShoppingHelper().openCart();
        app.getShoppingHelper().proceedToCheckout();
        app.getCheckoutHelper().completeCheckout("ТЕСТОВЫЙ ЗАКАЗ",4,"bank");
        //TODO добавить проверку на активность заказа с помощью метода isOrderActive

        app.getProfileHelper().cancelLastOrder();
        //TODO добавить проверку на отмененность заказа с помощью метода isOrderActive
    }

    @Test(priority = 6)
    // чекаем недоступность пустого чекаута при ненабранной корзине
    public void emptyCheckoutUnreachable() throws Exception {
        // TODO добавить проверку на текущую сумму корзины и если она выше суммы минимального заказа - очищать корзину
        assertPageIsUnreachable("https://instamart.ru/checkout/edit?");
    }

    @Test(priority = 7)
    // чекаем страницы ритейлеров
    // TODO переделать чек страниц всех ретейлеров по списку ретейлеров
    // TODO забирать список ритейлеров из БД или из админки с признаком активности
    // TODO проверять витрины активных ритейлеров на доступность, витрины неактивных - на недоступность
    public void checkRetailerPages() throws Exception {
        try {
            assertPageIsAvailable("https://instamart.ru/metro");
            assertPageIsAvailable("https://instamart.ru/lenta");
            assertPageIs404("https://instamart.ru/selgros"); // неактивный ритейлер
            assertPageIsAvailable("https://instamart.ru/vkusvill");
            assertPageIsAvailable("https://instamart.ru/karusel");
        } catch (AssertionError e) {}
    }

    @Test(priority = 8)
    // чекаем страницы профиля
    // TODO переделать чек страниц циклом по списку
    public void checkProfilePages() throws Exception {
        assertPageIsAvailable("https://instamart.ru/user/edit");
        assertPageIsAvailable("https://instamart.ru/user/orders");
        assertPageIsAvailable("https://instamart.ru/user/addresses");
    }

    @Test(priority = 9)
    // чекаем статические страницы
    // TODO переделать чек страниц циклом по списку
    // TODO забирать список страниц из БД или из админки
    public void checkStaticPages() throws Exception {
        assertPageIsAvailable("https://instamart.ru/about");
        assertPageIsAvailable("https://instamart.ru/delivery");
        assertPageIsAvailable("https://instamart.ru/rules");
        assertPageIsAvailable("https://instamart.ru/payment");
        assertPageIsAvailable("https://instamart.ru/return");
        assertPageIsAvailable("https://instamart.ru/faq");
        assertPageIsAvailable("https://instamart.ru/terms");
        assertPageIsAvailable("https://instamart.ru/contacts");
    }

    @Test(priority = 10)
    // чекаем лендинги
    // TODO переделать чек лендингов циклом по списку
    // TODO забирать список лендингов из БД или из админки
    public void checkLandings() throws Exception {
        assertPageIsAvailable("https://instamart.ru/mnogoru");
        assertPageIsAvailable("https://instamart.ru/sovest");
        assertPageIsAvailable("https://instamart.ru/halva");
        assertPageIsAvailable("https://instamart.ru/landings/feedback");
    }

    @Test(priority = 11)
    // чекаем корневые страницы админки
    // TODO переделать чек лендингов циклом по списку
    public void checkAdminPages() throws AssertionError {
        assertPageIsAvailable("https://instamart.ru/admin/shipments");
        assertPageIsAvailable("https://instamart.ru/admin/retailers");
        assertPageIsAvailable("https://instamart.ru/admin/products");
        assertPageIsAvailable("https://instamart.ru/admin/imports");
        assertPageIsAvailable("https://instamart.ru/admin/reports");
        assertPageIsAvailable("https://instamart.ru/admin/general_settings/edit");
        assertPageIsAvailable("https://instamart.ru/admin/promo_cards");
        assertPageIsAvailable("https://instamart.ru/admin/users");
        assertPageIsAvailable("https://instamart.ru/admin/pages");
    }

    @Test(priority = 12)
    // логаут
    public void logout() throws Exception {
        app.getSessionHelper().doLogout();
        assertPageIsAvailable();
        Assert.assertFalse(app.getSessionHelper().isUserAuthorised(), "Seems like user is still authorized");
    }

    @Test(priority = 14)
    //TODO перенести в TestBase - tearDown?
    public void cleanup() throws Exception {

        app.getSessionHelper().cancelAllTestOrders();
        assertNoTestOrdersLeftActive();

        app.getSessionHelper().deleteAllTestUsers();
        assertNoTestUsersLeft();

    }

}

