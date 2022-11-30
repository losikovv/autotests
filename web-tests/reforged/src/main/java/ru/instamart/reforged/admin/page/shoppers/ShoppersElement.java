package ru.instamart.reforged.admin.page.shoppers;

import org.openqa.selenium.By;
import ru.instamart.reforged.admin.page.shoppers.tags.add_tag.AddTagModal;
import ru.instamart.reforged.admin.page.shoppers.tags.delete_tag.DeleteTagModal;
import ru.instamart.reforged.admin.table.ShoppersTable;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;
import ru.instamart.reforged.core.component.Input;

public interface ShoppersElement {

    AddTagModal addTagModal = new AddTagModal();
    DeleteTagModal deleteTagModal = new DeleteTagModal();

    ShoppersTable shoppersTable = new ShoppersTable();

    Button createShoppersButton = new Button(By.xpath("//button/span[@aria-label='user-add']"), "кнопка 'Добавить сотрудника'");
    Input nameInput = new Input(By.xpath("//input[@name='name']"), "Инпут имени");
    Element spinner = new Element(By.xpath("//div[contains(@class,'ant-spin-spinning')]"), "Спиннер");
    Element tagFilterField = new Element(By.xpath("//div[@name='tagIds']"), "Поле выбора тегов");

    Element modal = new Element(By.xpath("//div[@data-qa='add_tags_select']/ancestor::div[@class='ant-modal']"), "Модалка выбора тегов");
    ElementCollection tagsInList = new ElementCollection(By.xpath("//div[contains(@class,' ant-select-item-option')]"), "Теги в списке тегов");
    Element selectedTagInList = new Element(ByKraken.xpathExpression("//span[text()='%s']/ancestor::div[@aria-selected='true']"),"Выбранный тег в списке тегов");

    Element shopper = new Element(ByKraken.xpathExpression("//a[text()='%s']"), 20, "Тестовый партнер в списке партнеров");

}
