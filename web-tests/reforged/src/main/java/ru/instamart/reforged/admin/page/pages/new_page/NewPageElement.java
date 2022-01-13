package ru.instamart.reforged.admin.page.pages.new_page;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Checkbox;
import ru.instamart.reforged.core.component.Input;

public interface NewPageElement {

    Input title = new Input(By.id("page_title"), "empty");
    Input slug = new Input(By.id("page_slug"), "empty");
    Input body = new Input(By.xpath("//html/body"), "empty");
    Input metaTitle = new Input(By.id("page_meta_title"), "empty");
    Input metaKeywords = new Input(By.id("page_meta_keywords"), "empty");
    Input metaDescription = new Input(By.id("page_meta_description"), "empty");
    Input pageLayout = new Input(By.id("page_layout"), "empty");
    Input foreignLink = new Input(By.id("page_foreign_link"), "empty");
    Input pagePosition = new Input(By.id("page_position"), "empty");

    Checkbox sidebar = new Checkbox(By.id("page_show_in_sidebar"), "empty");
    Checkbox header = new Checkbox(By.id("page_show_in_header"), "empty");
    Checkbox footer = new Checkbox(By.id("page_show_in_footer"), "empty");
    Checkbox pageVisible = new Checkbox(By.id("page_visible"), "empty");
    Checkbox renderLayoutAsPartial = new Checkbox(By.id("page_render_layout_as_partial"), "empty");

    Button submit = new Button(By.xpath("//button[@type='submit']"), "empty");
}
