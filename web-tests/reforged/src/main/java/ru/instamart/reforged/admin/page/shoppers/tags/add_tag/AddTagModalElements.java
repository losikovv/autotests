package ru.instamart.reforged.admin.page.shoppers.tags.add_tag;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;

public interface AddTagModalElements {

    Element modal = new Element(By.xpath("//div[@data-qa='add_tags_select']/ancestor::div[@class='ant-modal']"), "Модалка выбора тегов");
    Button addTagsButtonInactive = new Button(By.xpath("//button[@data-qa='add_tags_button' and(@disabled)]"), "Кнопка добавления тегов");
    Button addTagsButtonActive = new Button(By.xpath("//button[@data-qa='add_tags_button']"), "Кнопка добавления тегов");
    Element tagsSelector = new Element(By.xpath("//div[@data-qa='add_tags_select']"), "Селектор тегов");
    ElementCollection tagsInList = new ElementCollection(By.xpath("//div[contains(@class,' ant-select-item-option')]"), "Теги в списке тегов");
    Element selectedTagInList = new Element(ByKraken.xpathExpression("//span[text()='%s']/ancestor::div[@aria-selected='true']"),"Выбранный тег в списке тегов");
    ElementCollection selectedTagsInField = new ElementCollection(By.xpath("//span[@class='ant-select-selection-item-content']/span"),"Коллекция выбранных тегов в поле");
    ElementCollection selectedTagsInFieldRemoveButtons = new ElementCollection(By.xpath("//span[@class='ant-select-selection-item']//span[contains(@class,'anticon-close')]"),"Коллекция кнопок удаления выбранных тегов в поле");
}
