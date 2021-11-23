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
        waitAction().shouldBeVisible(sendSms);
    }

    @Step("Проверяем, что лейбл чекбокса отправки смс отображается")
    default void checkSendSmsLabelVisible() {
        waitAction().shouldBeVisible(sendSmsLabel);
    }

    @Step("Проверяем, что чекбокс начала отправки смс отображается")
    default void checkSendSmsStartVisible() {
        waitAction().shouldBeVisible(sendSmsStart);
    }

    @Step("Проверяем, что лейбл чекбокса начала отправки смс отображается")
    default void checkSendSmsStartLabelVisible() {
        waitAction().shouldBeVisible(sendSmsStartLabel);
    }

    @Step("Проверяем, что чекбокс отправки смс с извинениями отображается")
    default void checkSendSmsApologiesVisible() {
        waitAction().shouldBeVisible(sendSmsApologies);
    }

    @Step("Проверяем, что Лейбл чекбокса отправки смс с извинениями отображается")
    default void checkSendSmsApologiesLabelVisible() {
        waitAction().shouldBeVisible(sendSmsApologiesLabel);
    }

    @Step("Проверяем, что заголовок sbertips отображается")
    default void checkSendSmsTipsHeaderVisible() {
        waitAction().shouldBeVisible(sendSmsTipsHeader);
    }

    @Step("Проверяем, что чекбокс начала отправки смс о чаевых отображается")
    default void checkSendSmsTipsVisible() {
        waitAction().shouldBeVisible(sendSmsTips);
    }

    @Step("Проверяем, что лейбл чекбокса начала отправки смс о чаевых отображается")
    default void checkSendSmsTipsLabelVisible() {
        waitAction().shouldBeVisible(sendSmsTipsLabel);
    }

    @Step("Проверяем, что ID магазина для тестирования отображается")
    default void checkStoreIdForTestVisible() {
        waitAction().shouldBeVisible(storeIdForTest);
    }

    @Step("Проверяем, что лейбл ID магазина для тестирования отображается")
    default void checkStoreIdForTestLabelVisible() {
        waitAction().shouldBeVisible(storeIdForTestLabel);
    }

    @Step("Проверяем, что шаблон смс о чаевых отображается")
    default void checkSendSmsTipsTemplateVisible() {
        waitAction().shouldBeVisible(sendSmsTipsTemplate );
    }

    @Step("Проверяем, что лейбл шаблона смс о чаевых отображается")
    default void checkSendSmsTipsTemplateLabelVisible() {
        waitAction().shouldBeVisible(sendSmsTipsTemplateLabel);
    }

    @Step("Проверяем, что заголовок smsimple отображается")
    default void checkSmsimpleHeaderVisible() {
        waitAction().shouldBeVisible(smsimpleHeader);
    }

    @Step("Проверяем, что логин смс сервиса отображается")
    default void checkSmsLoginVisible() {
        waitAction().shouldBeVisible(smsLogin);
    }

    @Step("Проверяем, что лейбл логина смс сервиса отображается")
    default void checkSmsLoginLabelVisible() {
        waitAction().shouldBeVisible(smsLoginLabel);
    }

    @Step("Проверяем, что пароль смс сервиса отображается")
    default void checkSmsPasswordVisible() {
        waitAction().shouldBeVisible(smsPassword);
    }

    @Step("Проверяем, что лейбл пароля смс сервиса отображается")
    default void checkSmsPasswordLabelVisible() {
        waitAction().shouldBeVisible(smsPasswordLabel);
    }

    @Step("Проверяем, что номер смс сервиса отображается")
    default void checkSmsNumberVisible() {
        waitAction().shouldBeVisible(smsNumber);
    }

    @Step("Проверяем, что лейбл номера смс сервиса отображается")
    default void checkSmsNumberLabelVisible() {
        waitAction().shouldBeVisible(smsNumberLabel);
    }

    @Step("Проверяем, что заголовок Infobip отображается")
    default void checkInfobipHeaderVisible() {
        waitAction().shouldBeVisible(infobipHeader);
    }

    @Step("Проверяем, что токен сервиса infobip отображается")
    default void checkInfobipTokenVisible() {
        waitAction().shouldBeVisible(infobipToken);
    }

    @Step("Проверяем, что лейбл токена сервиса infobip отображается")
    default void checkInfobitTokenLabelVisible() {
        waitAction().shouldBeVisible(infobitTokenLabel);
    }

    @Step("Проверяем, что номер подписи сервиса infobip отображается")
    default void checkInfobitNumberVisible() {
        waitAction().shouldBeVisible(infobitNumberTest);
    }

    @Step("Проверяем, что лейбл номера подписи сервиса infobip отображается")
    default void checkInfobitNumberLabelVisible() {
        waitAction().shouldBeVisible(infobitNumberLabel);
    }

    @Step("Проверяем, что кнопка сохранения изменений отображается")
    default void checkSaveButtonVisible() {
        waitAction().shouldBeVisible(saveButton);
    }

}
