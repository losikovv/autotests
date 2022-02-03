package ru.instamart.reforged.admin.page.settings.sms_settings;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface SmsSettingsCheck extends Check, SmsSettingsElements {

    @Step("Проверяем, что заголовок страницы отображается")
    default void checkPageTitleVisible() {
        waitAction().shouldBeVisible(pageTitle);
    }

    @Step("Проверяем, что чекбокс отправки смс отображается")
    default void checkSendSmsVisible() {
        waitAction().isElementsExist(sendSms);
    }

    @Step("Проверяем, что чекбокс начала отправки смс отображается")
    default void checkSendSmsStartVisible() {
        waitAction().isElementsExist(sendSmsStart);
    }

    @Step("Проверяем, что чекбокс отправки смс с извинениями отображается")
    default void checkSendSmsApologiesVisible() {
        waitAction().isElementsExist(sendSmsApologies);
    }

    @Step("Проверяем, что заголовок sbertips отображается")
    default void checkSendSmsTipsHeaderVisible() {
        waitAction().shouldBeVisible(sendSmsTipsHeader);
    }

    @Step("Проверяем, что чекбокс начала отправки смс о чаевых отображается")
    default void checkSendSmsTipsVisible() {
        waitAction().isElementsExist(sendSmsTips);
    }

    @Step("Проверяем, что ID магазина для тестирования отображается")
    default void checkStoreIdForTestVisible() {
        waitAction().shouldBeVisible(storeIdForTest);
    }

    @Step("Проверяем, что шаблон смс о чаевых отображается")
    default void checkSendSmsTipsTemplateVisible() {
        waitAction().shouldBeVisible(sendSmsTipsTemplate );
    }

    @Step("Проверяем, что заголовок smsimple отображается")
    default void checkSmsimpleHeaderVisible() {
        waitAction().shouldBeVisible(smsimpleHeader);
    }

    @Step("Проверяем, что логин смс сервиса отображается")
    default void checkSmsLoginVisible() {
        waitAction().shouldBeVisible(smsLogin);
    }

    @Step("Проверяем, что пароль смс сервиса отображается")
    default void checkSmsPasswordVisible() {
        waitAction().shouldBeVisible(smsPassword);
    }

    @Step("Проверяем, что номер смс сервиса отображается")
    default void checkSmsNumberVisible() {
        waitAction().shouldBeVisible(smsNumber);
    }

    @Step("Проверяем, что заголовок Infobip отображается")
    default void checkInfobipHeaderVisible() {
        waitAction().shouldBeVisible(infobipHeader);
    }

    @Step("Проверяем, что токен сервиса infobip отображается")
    default void checkInfobipTokenVisible() {
        waitAction().shouldBeVisible(infobipToken);
    }

    @Step("Проверяем, что номер подписи сервиса infobip отображается")
    default void checkInfobitNumberVisible() {
        waitAction().shouldBeVisible(infobitNumberTest);
    }

    @Step("Проверяем, что кнопка сохранения изменений отображается")
    default void checkSaveButtonVisible() {
        waitAction().shouldBeVisible(saveButton);
    }
}
