package ru.instamart.reforged.admin.page.pages.new_page;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Checkbox;
import ru.instamart.reforged.core.component.Input;

public interface NewPageElement {

    Input title = new Input(By.xpath("//input[@data-qa='pages_form_title_input']"), "Инпут заголовка страницы");
    Input slug = new Input(By.xpath("//input[@data-qa='pages_form_slug_input']"), "Инпут постоянной ссылки страницы");
    Input body = new Input(By.xpath("//html/body"), "Тело текста страницы");
    Input metaTitle = new Input(By.xpath("//input[@data-qa='pages_form_metaTitle_input']"), "Инпут мета тайтла страницы");
    Input metaKeywords = new Input(By.xpath("//input[@data-qa='pages_form_keywords_input']"), "Инпут ключевых слов страницы");
    Input metaDescription = new Input(By.xpath("//input[@data-qa='pages_form_description_input']"), "Инпут описания страницы");
    Input pageLayout = new Input(By.xpath("//input[@data-qa='pages_form_layout_input']"), "Инпут макета страницы");
    Input foreignLink = new Input(By.xpath("//input[@data-qa='pages_form_foreignLink_input']"), "Инпут внешней ссылки страницы");
    Input pagePosition = new Input(By.xpath("//input[@data-qa='pages_form_title_input']"), "Инпут заголовка страницы");

    Checkbox sidebar = new Checkbox(By.xpath("//input[@data-qa='pages_form_showInSidebar_checkbox']"), "Чекбокс отображения в боковом меню");
    Checkbox header = new Checkbox(By.xpath("//input[@data-qa='pages_form_showInHeader_checkbox']"), "Чекбокс отображения в верхнем меню");
    Checkbox footer = new Checkbox(By.xpath("//input[@data-qa='pages_form_showInFooter_checkbox']"), "Чекбокс отображения в нижнем меню");
    Checkbox pageVisible = new Checkbox(By.xpath("//input[@data-qa='pages_form_visible_checkbox']"), "Чекбокс публикации страницы");
    Checkbox renderLayoutAsPartial = new Checkbox(By.xpath("//input[@data-qa='pages_form_renderLayoutAsPartial_checkbox']"), "Чекбокс отрисовки страницы");

    Button submit = new Button(By.xpath("//button[@data-qa='pages_form_submit_button']"), "Кнопка подтверждения изменений");
}
