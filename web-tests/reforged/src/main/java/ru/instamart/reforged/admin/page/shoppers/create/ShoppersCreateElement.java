package ru.instamart.reforged.admin.page.shoppers.create;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Input;
import ru.instamart.reforged.core.component.MultiSelector;

public interface ShoppersCreateElement {

    Input nameInput = new Input(By.xpath("//input[@name='name']"), "инпут 'Имя и фамилия'");
    Input phoneInput = new Input(By.xpath("//input[@name='phone']"), "инпут 'Телефон'");
    Input loginInput = new Input(By.xpath("//input[@name='login']"), "инпут 'Логин'");
    MultiSelector currentShopSelector = new MultiSelector(By.xpath("//div[contains(@class,'ant-form-item-label')][contains(.,'Текущий магазин')]/following-sibling::div//div[contains(@class,'ant-select ')]"), "селектор 'Текущий магазин'");
    MultiSelector shoppersRoleSelector = new MultiSelector(By.xpath("//div[contains(@class,'ant-form-item-label')][contains(.,'Роли сотрудника')]/following-sibling::div//div[contains(@class,'ant-select ')]"), "селектор 'Роли сотрудника'");
    Input passwordInput = new Input(By.xpath("//input[@name='password']"), "инпут 'Пароль'");
    Input innInput = new Input(By.xpath("//input[@name='inn']"), "инпут 'ИНН'");
    Button saveButton = new Button(By.xpath("//button[@type='submit']"), "кнопка 'Сохранить'");

    Element requiredFieldError = new Element(ByKraken.xpathExpression("//label[text()='%s']/parent::div/following-sibling::div//span[text()='Обязательное поле']"), "валидация для обязательных полей");
    Element requiredPasswordError = new Element(By.xpath("//label[text()='Пароль']/parent::div/following-sibling::div//span[text()='отсутствует']"), "валидация для поля 'Пароль'");
}
