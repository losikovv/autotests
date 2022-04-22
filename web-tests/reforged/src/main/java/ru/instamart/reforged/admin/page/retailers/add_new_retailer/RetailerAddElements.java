package ru.instamart.reforged.admin.page.retailers.add_new_retailer;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Input;

public interface RetailerAddElements {

    Input nameInput = new Input(By.xpath("//input[@data-qa='retailer_create_name']"), "Инпут 'Наименование' нового ретейлера на странице создания ретейлера");
    Element nameInputError = new Element(By.xpath("//div[@data-qa='retailer_create_name_error']"), "Ошибка ввода поля 'Наименование'");
    Input shortNameInput = new Input(By.xpath("//input[@data-qa='retailer_create_short_name']"), "Инпут 'Короткое наименование' нового ретейлера на странице создания ретейлера");
    Element shortNameInputError = new Element(By.xpath("//div[@data-qa='retailer_create_short_name_error']"), "Ошибка ввода поля 'Короткое наименование'");
    Input descriptionInput = new Input(By.xpath("//input[@data-qa='retailer_create_description']"), "Инпут 'Короткое описание' нового ретейлера на странице создания ретейлера");
    Input urlInput = new Input(By.xpath("//input[@data-qa='retailer_create_slug']"), "Инпут 'Короткий URL' нового ретейлера на странице создания ретейлера");
    Element urlInputError = new Element(By.xpath("//div[@data-qa='retailer_create_slug_error']"), "Ошибка ввода поля 'Короткий URL'");
    Input juridicalNameInput = new Input(By.xpath("//input[@data-qa='retailer_create_legal_name']"), "Инпут 'Юридическое имя' нового ретейлера на странице создания ретейлера");
    Input innInput = new Input(By.xpath("//input[@data-qa='retailer_create_inn']"), "Инпут 'ИНН' нового ретейлера на странице создания ретейлера");
    Input phoneInput = new Input(By.xpath("//input[@data-qa='retailer_create_phone']"), "Инпут 'Телефон для связи' нового ретейлера на странице создания ретейлера");
    Element phoneInputError = new Element(By.xpath("//div[@data-qa='retailer_create_phone_error']"), "Ошибка ввода поля 'Телефон для связи'");
    Input categoriesDepthInput = new Input(By.xpath("//input[@data-qa='retailer_create_departments_depth']"), "Инпут 'Глубина влодения категорий на главной' нового ретейлера на странице создания ретейлера");
    Element categoriesDepthInputError = new Element(By.xpath("//div[@data-qa='retailer_create_department_depth_error']"), "Ошибка ввода поля 'Глубина вложения категорий на главной'");
    Input importKeyInput = new Input(By.xpath("//input[@data-qa='retailer_create_import_key']"), "Инпут 'Ключ в файле импорта' нового ретейлера на странице создания ретейлера");
    Element importKeyInputError = new Element(By.xpath("//div[@data-qa='retailer_create_import_key_error']"), "Ошибка ввода поля 'Ключ в файле импорта'");

    Button cancel = new Button(By.xpath("//button[@data-qa='retailer_cancel_create_btn']"), "Кнопка 'Отменить'");
    Button submit = new Button(By.xpath("//button[@data-qa='retailer_submit_create_btn']"), "Кнопка 'Сохранить'");
}
