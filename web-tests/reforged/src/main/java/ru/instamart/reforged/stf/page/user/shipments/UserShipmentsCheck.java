package ru.instamart.reforged.stf.page.user.shipments;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.kraken.util.CollectionUtil;
import ru.instamart.kraken.util.StringUtil;
import ru.instamart.reforged.core.Check;

import java.util.List;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface UserShipmentsCheck extends Check, UserShipmentsElement {

    @Step("Проверка отмены статуса")
    default void checkStatusWasCanceled() {
        waitAction().shouldBeVisible(shipmentStatusCancel);
    }

    @Step("Проверка статусов 'Отменено' для всех заказов")
    default void checkAllOrderStatusWasCanceled() {
        Assert.assertEquals(shipments.elementCount(), orderCancelStatuses.elementCount());
    }

    @Step("Проверка статуса заказа 'Ожидает отправки'")
    default void checkStatusShipmentReady() {
        waitAction().shouldBeVisible(shipmentStatusShipmentReady);
    }

    @Step("Проверка введенного промокода на странице статуса заказа")
    default void checkUserShipmentPromocodeVisible() {
        waitAction().shouldBeVisible(userShipmentPromocode);
    }

    @Step("Проверка соответствия телефона юзера ожидаемому {0}")
    default void checkUserPhoneCorrect(final String phone) {
        krakenAssert.assertEquals(userPhone.getText(), StringUtil.convertDigitsStringToPhoneNumber(phone),
                "Номер телефона пользователя не соответствует ожидаемому");
    }

    @Step("Проверка соответствия емейла юзера ожидаемому {0}")
    default void checkUserEmailCorrect(final String email) {
        krakenAssert.assertEquals(userEmail.getText(), email,
                "Емейл пользователя не соответствует ожидаемому");
    }

    @Step("Проверка суммы скидки: {0}")
    default void checkUserShipmentPromocodeCorrect(double summ) {
        Assert.assertEquals(summ, StringUtil.stringToDouble(discountSumm.getText()));
    }

    @Step("Проверяем, что среди товаров в заказе есть искомое {0}")
    default void checkProductListContains(final String productNameExpected) {
        Assert.assertTrue(productsInOrder.isElementWithTextPresent(productNameExpected),
                String.format("Среди списка товаров в заказе не найдено '%s'", productNameExpected));
    }

    @Step("Проверяем, что список продуктов соответствует ожидаемому {0}")
    default void checkProductListsEquals(final List<String> expectedProductListNames) {
        Assert.assertEquals(CollectionUtil.cropItemsByLengthAndSort(productInOrderNames.getTextFromAllElements(), 59), expectedProductListNames,
                "Список продуктов не соответствует ожидаемому");
    }

    @Step("Проверка метода оплаты 'Картой онлайн'")
    default void checkPaymentMethodCardOnline() {
        waitAction().shouldBeVisible(paymentMethodCardOnline);
    }

    @Step("Проверка метода оплаты 'Картой курьеру'")
    default void checkPaymentMethodCardToCourier() {
        waitAction().shouldBeVisible(paymentMethodCardToCourier);
    }

    @Step("Проверка метода оплаты 'По счёту для бизнеса'")
    default void checkPaymentMethodForBusiness() {
        waitAction().shouldBeVisible(paymentMethodForBusiness);
    }

    @Step("Проверка метода политики замен {0}")
    default void checkReplacementMethod(final String data) {
        waitAction().shouldBeVisible(replacementPolicy, data);
    }

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
