package ru.instamart.reforged.admin.page.settings.store_groups.new_group;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Input;

public interface EditStoreGroupElements {
    Button goBack = new Button(By.xpath("//button[contains(.,'Назад')]"), "Кнопка 'Назад'");

    Input title = new Input(By.xpath("//input[@data-qa='store_label_title']"), "Поле ввода 'Название'");
    Input subtitle = new Input(By.xpath("//input[@data-qa='store_label_subtitle']"), "Поле ввода 'Слоган'");
    Input code = new Input(By.xpath("//input[@data-qa='store_label_code']"), "Поле ввода 'Код'");
    Input icon = new Input(By.xpath("//input[@data-qa='store_label_icon']"), "Поле ввода 'Иконка'");
    Input position = new Input(By.xpath("//input[@data-qa='store_label_position']"), "Поле ввода 'Позиция'");
    Input level = new Input(By.xpath("//input[@data-qa='store_label_level']"), "Поле ввода 'Уровень'");

    Button cancel = new Button(By.xpath("//button[@data-qa='store_label_cancel']"), "Кнопка 'Отменить'");
    Button submit = new Button(By.xpath("//button[@data-qa='store_label_submit']"), "Кнопка 'Сохранить'");
}
