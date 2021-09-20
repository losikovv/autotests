package ru.instamart.reforged.stf.page.user.profile;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface UserProfileCheck extends UserProfileElement, Check {

    @Step("Проверяем, что у нового пользователя нет заказов")
    default void checkTextOnThePage() {
        waitAction().shouldBeVisible(textNoOrders);
    }

    @Step("Проверяем, что в разделе заказов отображается кнопка Все заказы")
    default void checkAllOrdersButton() {
        waitAction().shouldBeVisible(allOrders);
    }

    @Step("Проверяем, что в разделе заказов отображается кнопка Активные заказы")
    default void checkActiveOrdersButton() {
        waitAction().shouldBeVisible(activeOrders);
    }

    @Step("Проверяем, что в разделе заказов отображается кнопка Завершенные заказы")
    default void checkFinishedOrdersButton() {
        waitAction().shouldBeVisible(finishedOrders);
    }

    @Step("Проверяем, что в разделе заказов отображается кнопка Перейти к покупкам")
    default void checkGoToShoppingButton() {
        waitAction().shouldBeVisible(goToShopping);
    }
}
