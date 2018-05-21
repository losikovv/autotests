package ru.instamart.autotests.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Checkout extends TestBase {


    @BeforeMethod(alwaysRun = true)
    public void getAuth() throws Exception {
        app.getNavigationHelper().getRetailerPage("metro");
        if(!app.getSessionHelper().isUserAuthorised()) {
            app.getSessionHelper().doLoginAsAdmin();
        }
        app.getShoppingHelper().dropCart();
    }


    @Test(
            description = "Тест недоступности пустого чекаута при ненабранной корзине",
            groups = {"acceptance","regression"},
            priority = 401
    )
    public void emptyCheckoutUnreachable() throws Exception {
        // TODO - очищать корзину перед проверкой
        // TODO - добавить проверку на то что чекаут недоступен если сумма корзины меньше мин заказа
        assertPageIsUnreachable("https://instamart.ru/checkout/edit?");
    }

    // TODO Тесты на изменение телефона и контактов

    // TODO Тесты на добавление и изменение карт оплаты

    // TODO Тесты на добавление и изменение юрлиц

    // TODO Тесты на добавление и изменение программ лояльности

    // TODO Тесты на добавление и удаление промокодов

    // TODO Тесты на оформление заказов со всеми способы оплаты

}
