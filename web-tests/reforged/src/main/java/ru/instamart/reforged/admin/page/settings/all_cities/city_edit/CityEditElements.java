package ru.instamart.reforged.admin.page.settings.all_cities.city_edit;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.*;

public interface CityEditElements {

    Element pageTitle = new Element(By.xpath("//h1[contains(@class, 'page-title')]"), "Название страницы");
    Link returnButton = new Link(By.xpath("//a[@class='button icon-arrow-left']"), "Кнопка вернуться назад");

    Element cityNameInputLabel = new Element(By.xpath("//label[@for='city_name']"), "Лейбл для ввода имени города в имен.падеже");
    Input cityNameInput = new Input(By.id("city_name"), "Инпут для ввода имени городав имен.падеже");

    Element cityNameInInputLabel = new Element(By.xpath("//label[@for='city_name_in']"), "Лейбл для ввода имени города в предл.падеже");
    Input cityNameInInput = new Input(By.id("city_name_in"), "Инпут ввода имени города в предл.падеже");

    Element cityNameFromInputLabel = new Element(By.xpath("//label[@for='city_name_from']"), "Лейбл для ввода имени города в родит.падеже");
    Input cityNameFromInput = new Input(By.id("city_name_from"), "Инпут ввода имени города в родит.падеже");

    Element cityNameToInputLabel = new Element(By.xpath("//label[@for='city_name_to']"), "Лейбл для ввода имени города в направит.падеже");
    Input cityNameToInput = new Input(By.id("city_name_to"), "Инпут ввода имени города в направит.падеже");

    Element cityLockedLabel = new Element(By.xpath("//label[@for='city_locked']"), "Лейбл чекбокса блокировки города");
    Checkbox cityLocked = new Checkbox(By.id("city_locked"), "Чекбокс город заблокирован для редактирования");

    Button editButton = new Button(By.xpath("//button[contains(@class,'refresh')]"), "Кнопка подтверждения добавления нового города");
    Link cancelButton = new Link(By.xpath("//a[contains(@class,'remove')]"), "Кнопка отмены добавления нового города");
}
