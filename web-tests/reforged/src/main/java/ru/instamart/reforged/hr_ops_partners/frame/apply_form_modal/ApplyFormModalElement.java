package ru.instamart.reforged.hr_ops_partners.frame.apply_form_modal;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.*;

public interface ApplyFormModalElement {

    Button close = new Button(By.xpath("//button[contains(@class,'Modal_closeButton_')]"), "Кнопка 'X' (закрыть)");
    Element modalTitle = new Element(By.xpath("//div[contains(@class,'ApplyForm_title')]"), "Заголовок формы отклика");
    Input nameInput = new Input(By.xpath("//input[@id='nameModal']"), "Поле ввода 'Имя'");
    Element nameInputLabel = new Element(By.xpath("//label[contains(@class,'FormGroup_label')][@for='nameModal']"), "Имя");
    Element nameInputError = new Element(By.xpath("//input[@id='nameModal']/../following-sibling::div[contains(@class,'ApplyForm_error_')]"), "Ошибка в поле ввода 'Имя'");
    Input surnameInput = new Input(By.xpath("//input[@id='surnameModal']"), "Поле ввода 'Фамилия'");
    Element surnameInputLabel = new Element(By.xpath("//label[contains(@class,'FormGroup_label')][@for='surnameModal']"), "Фамилия");
    Element surnameInputError = new Element(By.xpath("//input[@id='surnameModal']/../following-sibling::div[contains(@class,'ApplyForm_error_')]"), "Ошибка в поле ввода 'Фамилия'");
    Input regionInput = new Input(By.xpath("//input[@id='cityModal']"), "Поле ввода 'Город'");
    Element regionInputLabel = new Element(By.xpath("//label[contains(@class,'FormGroup_label')][@for='cityModal']"), "Город");
    Element regionInputError = new Element(By.xpath("//input[@id='cityModal']/../following-sibling::div[contains(@class,'ApplyForm_error_')]"), "Ошибка в поле ввода 'Город'");
    ElementCollection regionsList = new ElementCollection(By.xpath("//li[contains(@class,'ApplyForm_item_')]"), "Список городов");
    Element emptyListPlaceholder = new Element(By.xpath("//div[contains(@class,'ApplyForm_empty_')]"), "Плейсхолдер пустого списка регионов");
    Input phoneInput = new Input(By.xpath("//input[@id='phoneModal']"), "Поле ввода 'Телефон'");
    Element phoneInputLabel = new Element(By.xpath("//label[contains(@class,'FormGroup_label')][@for='phoneModal']"), "Номер телефона");
    Element phoneInputError = new Element(By.xpath("//input[@id='phoneModal']/../following-sibling::div[contains(@class,'ApplyForm_error_')]"), "Ошибка в поле ввода 'Телефон'");

    Button sendSMS = new Button(By.xpath("//button[contains(@class,'ApplyForm_button')]"), "Кнопка 'Получить код СМС'");
    Checkbox personalDataConfirmCheckbox = new Checkbox(By.xpath("//input[contains(@class,'ApplyForm_checkbox')]"), "Чекбокс согласия на обработку персональных данных");
    Link personalDataConfirmInfo = new Link(By.xpath("//a[contains(@class,'ApplyForm_link_')]"), "Ссылка на форму согласия на обработку персональных данных");
}
