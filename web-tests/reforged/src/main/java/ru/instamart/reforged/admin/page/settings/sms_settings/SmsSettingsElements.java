package ru.instamart.reforged.admin.page.settings.sms_settings;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.*;

public interface SmsSettingsElements {
    Element pageTitle = new Element(By.xpath("//h1[@class='page-title ≈']"), "Заголовок страницы");
    Checkbox sendSms = new Checkbox(By.id("send_sms"), "Чекбокс отправки смс");
    Element sendSmsLabel = new Element(By.xpath("//label[@for='send_sms']"), "Лейбл чекбокса отправки смс");

    Checkbox sendSmsStart = new Checkbox(By.id("send_sms"), "Чекбокс начала отправки смс");
    Element sendSmsStartLabel = new Element(By.xpath("//label[@for='send_sms']"), "Лейбл чекбокса начала отправки смс");

    Checkbox sendSmsApologies = new Checkbox(By.id("send_shipment_collecting_violation_apologies_sms"), "Чекбокс отправки смс с извинениями");
    Element sendSmsApologiesLabel = new Element(By.xpath("//label[@for='send_shipment_collecting_violation_apologies_sms']"), "Лейбл чекбокса отправки смс с извинениями");

    Element sendSmsTipsHeader = new Element(By.xpath("//h2[contains(text(),'Sbertips')]"), "Заголовок sbertips");
    Checkbox sendSmsTips = new Checkbox(By.id("send_sbertips_sms"), "Чекбокс начала отправки смс о чаевых");
    Element sendSmsTipsLabel = new Element(By.xpath("//label[@for='send_sbertips_sms']"), "Лейбл чекбокса начала отправки смс о чаевых");

    Input storeIdForTest = new Input(By.id("sbertips_sms_testing_store_ids"), "ID магазина для тестирования");
    Element storeIdForTestLabel = new Element(By.xpath("//label[@for='sbertips_sms_testing_store_ids']"), "Лейбл ID магазина для тестирования");

    Input sendSmsTipsTemplate = new Input(By.id("sbertips_sms_link_template"), "Шаблон смс о чаевых");
    Element sendSmsTipsTemplateLabel = new Element(By.xpath("//label[@for='sbertips_sms_link_template']"), "Лейбл шаблона смс о чаевых");

    Element smsimpleHeader = new Element(By.xpath("//h2[contains(text(),'Smsimple')]"), "Заголовок smsimple");
    Input smsLogin = new Input(By.id("sms_login"), "Логин смс сервиса");
    Element smsLoginLabel = new Element(By.xpath("//label[@for='sms_login']"), "Лейбл логина смс сервиса");

    Input smsPassword = new Input(By.id("sms_login"), "Пароль смс сервиса");
    Element smsPasswordLabel = new Element(By.xpath("//label[@for='sms_login']"), "Лейбл пароля смс сервиса");

    Input smsNumber = new Input(By.id("sms_or_id"), "Номер смс сервиса");
    Element smsNumberLabel = new Element(By.xpath("//label[@for='sms_or_id']"), "Лейбл номера смс сервиса");

    Element infobipHeader = new Element(By.xpath("//h2[contains(text(),'Infobip')]"), "Заголовок Infobip");
    Input infobipToken = new Input(By.id("alt_sms_token"), "Токен сервиса infobip");
    Element infobitTokenLabel = new Element(By.xpath("//label[@for='alt_sms_token']"), "Лейбл токена сервиса infobip");

    Input infobitNumberTest = new Input(By.id("alt_sms_or_id"), "Номер подписи сервиса infobip");
    Element infobitNumberLabel = new Element(By.xpath("//label[@for='alt_sms_or_id']"), "Лейбл номера подписи сервиса infobip");

    Button saveButton = new Button(By.xpath("//button[@type='submit']"), "Кнопка сохранения изменений");
}
