package ru.instamart.reforged.admin.page.shoppers;

import org.openqa.selenium.By;
import ru.instamart.reforged.admin.page.shoppers.tags.add_tag.AddTagModal;
import ru.instamart.reforged.admin.page.shoppers.tags.delete_tag.DeleteTagModal;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;
import ru.instamart.reforged.core.component.Input;

public interface ShoppersElement {

    AddTagModal addTagModal = new AddTagModal();
    DeleteTagModal deleteTagModal = new DeleteTagModal();

    Button createShoppersButton = new Button(By.xpath("//button/span[@aria-label='user-add']"), "кнопка 'Добавить сотрудника'");
    Input nameInput = new Input(By.xpath("//input[@name='name']"), "Инпут имени");
    Element spinner = new Element(By.xpath("//div[@class='ant-spin-nested-loading']"), "Спиннер");
    Element shopperNameInTable = new Element(ByKraken.xpathExpression("//a[contains(@href,'admin/spa/shoppers/') and (contains(text(),'%s'))]"), "Имя партнера в таблице");
    ElementCollection addTagButtons = new ElementCollection(By.xpath("//span[@data-qa='shoppers_list_add_tag']"), "Коллекция элементов кнопок 'Добавить тег'");
    ElementCollection collapseTagsButtons = new ElementCollection(By.xpath("//button[@data-qa='shoppers_list_collapse_tag_list']"), "кнопка 'Добавить сотрудника'");

    ElementCollection tags = new ElementCollection(By.xpath("//span[@data-qa='shoppers_list_add_tag']/parent::td/span[contains(@class,'ant-tag') and not(@data-qa)]"), "Теги на странице партнеров");
    ElementCollection tagsDeleteButtons = new ElementCollection(By.xpath("//span[@data-qa='shoppers_list_add_tag']/parent::td/span[contains(@class,'ant-tag') and not(@data-qa)]/span[@aria-label='close']"), "Кнопки удаления у тегов");
    Element tag = new Element(ByKraken.xpathExpression("//span[contains(@class,'ant-tag') and text()='%s']"), "Тег на странице партнеров");
    ElementCollection tagsWithName = new ElementCollection(ByKraken.xpathExpression("//span[contains(@class,'ant-tag') and text()='%s']"), "Тег на странице партнеров");

    Element tagFilterField = new Element(By.xpath("//div[@name='tagIds']"), "Поле выбора тегов");

    Element modal = new Element(By.xpath("//div[@data-qa='add_tags_select']/ancestor::div[@class='ant-modal']"), "Модалка выбора тегов");
    Button addTagsButtonActive = new Button(By.xpath("//button[@data-qa='add_tags_button']"), "Кнопка добавления тегов");
    Element tagsSelector = new Element(By.xpath("//div[@data-qa='add_tags_select']"), "Селектор тегов");
    ElementCollection tagsInList = new ElementCollection(By.xpath("//div[contains(@class,' ant-select-item-option')]"), "Теги в списке тегов");
    Element selectedTagInList = new Element(ByKraken.xpathExpression("//span[text()='%s']/ancestor::div[@aria-selected='true']"),"Выбранный тег в списке тегов");
    ElementCollection selectedTagsInField = new ElementCollection(By.xpath("//div[@name='tagIds']//span[@class='ant-select-selection-item']"),"Коллекция выбранных тегов в поле");
    ElementCollection selectedTagsInFieldRemoveButtons = new ElementCollection(By.xpath("//span[@class='ant-select-selection-item']//span[contains(@class,'anticon-close')]"),"Коллекция кнопок удаления выбранных тегов в поле");

    ElementCollection partnerEntryInTable = new ElementCollection(By.xpath("//tr[contains(@class,'ant-table-row-level-0')]"),"Записи партнеров в таблице партнеров");
}
