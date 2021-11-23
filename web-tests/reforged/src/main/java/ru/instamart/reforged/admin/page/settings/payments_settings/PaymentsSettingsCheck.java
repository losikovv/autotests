package ru.instamart.reforged.admin.page.settings.payments_settings;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface PaymentsSettingsCheck extends Check, PaymentsSettingsElements {

    @Step("Проверяем, что заголовок страницы отображается")
    default void checkPageTitleVisible() {
        waitAction().shouldBeVisible(pageTitle);
    }

    @Step("Проверяем, что таблица методов оплаты отображается")
    default void checkPaymentMethodsTableVisible() {
        waitAction().shouldBeVisible(paymentListing);
    }
}
