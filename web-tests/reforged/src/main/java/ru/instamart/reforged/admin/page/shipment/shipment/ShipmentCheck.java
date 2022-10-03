package ru.instamart.reforged.admin.page.shipment.shipment;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface ShipmentCheck extends Check, ShipmentElement {

    @Step("Проверяем, что отобразилась информация о заказе")
    default void checkOrderInfoVisible() {
        waitAction().shouldBeVisible(orderInfo);
    }

    @Step("Проверяем, что заказ оформлен с промокодом '{promoCode}'")
    default void checkPromoCodeData(final String promoCode) {
        final var code = promoCodeData.getText().trim();
        Assert.assertEquals(promoCode, code, String.format("Промокод '%s' не соответствует ожидаемому '%s'", promoCode, code));
    }

    @Step("Проверяем, что отображается промо в статусе 'Скоро отправим'")
    default void checkCompensationPromoPending() {
        compensationPromoStatusPending.should().visible();
    }

    @Step("Проверяем, что отображается промо в статусе 'На согласовании'")
    default void checkCompensationPromoOnApprove() {
        compensationPromoStatusOnApprove.should().visible();
    }

    @Step("Проверяем, что отображается текст промокода")
    default void checkCompensationPromoText() {
        krakenAssert.assertTrue(compensationPromoText.is().displayed(), "Не отображается текст промокода");
    }

    @Step("Проверяем, что отображается статус промо")
    default void checkCompensationPromoStatus() {
        krakenAssert.assertTrue(compensationPromoStatus.is().displayed(), "Не отображается статус промо");
    }

    @Step("Проверяем, что отображается кнопка 'Удалить промокод'")
    default void checkCompensationPromoDeleteButton() {
        krakenAssert.assertTrue(compensationPromoDeleteButton.is().displayed(), "Не отображается кнопка удалить промо");
    }

    @Step("Проверяем, что отображается кнопка 'Развернуть'")
    default void checkCompensationPromoExpandButton() {
        krakenAssert.assertTrue(compensationPromoExpandButton.is().displayed(), "Не отображается кнопка развернуть");
    }

    @Step("Проверяем, что отображается срок действия промокода")
    default void checkCompensationPromoExpirationDate() {
        krakenAssert.assertTrue(compensationPromoExpirationDate.is().displayed(), "Не отображается срок действия промо");
    }

    @Step("Проверяем, что отображается причина выдачи промокода")
    default void checkCompensationPromoReason() {
        krakenAssert.assertTrue(compensationPromoReason.is().displayed(), "Не отображается причина выдачи промокода");
    }

    @Step("Проверяем, что отображается дата выдачи промокода")
    default void checkCompensationPromoIssueDate() {
        krakenAssert.assertTrue(compensationPromoIssueDate.is().displayed(), "Не отображается дата выдачи промокода");
    }

    @Step("Проверяем, что отображается оператор, выдавший промокод")
    default void checkCompensationPromoOperatorName() {
        krakenAssert.assertTrue(compensationPromoOperatorName.is().displayed(), "Не отображается оператор, выдавший промо");
    }

    @Step("Проверяем, что отображается комментарий к промокоду")
    default void checkCompensationPromoComment() {
        krakenAssert.assertTrue(compensationPromoComment.is().displayed(), "Не отображается комментарий к промо");
    }
    @Step("Проверяем, что отображается таблица с возвратными товарами")
    default void checkCompensationPromoReturnItems() {
        krakenAssert.assertTrue(compensationPromoReturnItems.is().displayed(), "Не отображается таблица товаров на возврата");
    }
}
