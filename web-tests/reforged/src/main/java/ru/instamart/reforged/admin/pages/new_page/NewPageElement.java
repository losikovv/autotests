package ru.instamart.reforged.admin.pages.new_page;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Checkbox;
import ru.instamart.reforged.core.component.Input;

public interface NewPageElement {

    Input title = new Input(By.id("page_title"));
    Input slug = new Input(By.id("page_slug"));
    Input body = new Input(By.xpath("//html/body"));
    Input metaTitle = new Input(By.id("page_meta_title"));
    Input metaKeywords = new Input(By.id("page_meta_keywords"));
    Input metaDescription = new Input(By.id("page_meta_description"));
    Input pageLayout = new Input(By.id("page_layout"));
    Input foreignLink = new Input(By.id("page_foreign_link"));
    Input pagePosition = new Input(By.id("page_position"));

    Checkbox sidebar = new Checkbox(By.id("page_show_in_sidebar"));
    Checkbox header = new Checkbox(By.id("page_show_in_header"));
    Checkbox footer = new Checkbox(By.id("page_show_in_footer"));
    Checkbox pageVisible = new Checkbox(By.id("page_visible"));
    Checkbox renderLayoutAsPartial = new Checkbox(By.id("page_render_layout_as_partial"));

    Button submit = new Button(By.xpath("//button[@type='submit']"));
}
