package ru.instamart.reforged.admin.page.settings.sms_settings;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.*;

public interface SmsSettingsElements {

    Element pageTitle = new Element(By.xpath("//h4[text()='Настройки отправки SMS']"), "Заголовок страницы");

    Checkbox sendSms = new Checkbox(By.xpath("//input[@data-qa='sms_settings_sendSms']"), "Чекбокс 'Отправлять sms'");
    Checkbox sendSmsStart = new Checkbox(By.xpath("//input[@data-qa='sms_settings_sendStartShippingSms']"), "Чекбокс 'Отправлять sms о начале доставки'");
    Checkbox sendSmsApologies = new Checkbox(By.xpath("//input[@data-qa='sms_settings_sendShipmentCollectingViolationApologiesSms']"), "Чекбокс 'Отправлять sms с извинениями о задержке сборки'");

    Element sendSmsTipsHeader = new Element(By.xpath("//span[contains(text(),'Sbertips')]"), "Заголовок sbertips");

    Checkbox sendSmsTips = new Checkbox(By.xpath("//input[@data-qa='sms_settings_sendSbertipsSms']"), "Чекбокс 'Отправлять sms про чаевые'");

    Input storeIdForTest = new Input(By.xpath("//input[@data-qa='sms_settings_sbertipsSmsTestingStoreIds']"), "ID магазина для тестирования");
    Input sendSmsTipsTemplate = new Input(By.xpath("//input[@data-qa='sms_settings_sbertipsSmsLinkTemplate']"), "Шаблон ссылки на sbertips");

    Element smsimpleHeader = new Element(By.xpath("//span[contains(text(),'Smsimple')]"), "Заголовок smsimple");

    Input smsLogin = new Input(By.xpath("//input[@data-qa='sms_settings_smsLogin']"), "Логин смс сервиса");
    Input smsPassword = new Input(By.xpath("//input[@data-qa='sms_settings_smsPassword']"), "Пароль смс сервиса");
    Input smsNumber = new Input(By.xpath("//input[@data-qa='sms_settings_smsOrId']"), "Номер смс сервиса");

    Element infobipHeader = new Element(By.xpath("//span[contains(text(),'Infobip')]"), "Заголовок Infobip");

    Input infobipToken = new Input(By.xpath("//input[@data-qa='sms_settings_altSmsToken']"), "Токен сервиса infobip");
    Input infobitNumberTest = new Input(By.xpath("//input[@data-qa='sms_settings_altSmsOrId']"), "Номер подписи сервиса infobip");

    Button saveButton = new Button(By.xpath("//button[@data-qa='sms_settings_submitBtn']"), "Кнопка сохранения изменений");
}
