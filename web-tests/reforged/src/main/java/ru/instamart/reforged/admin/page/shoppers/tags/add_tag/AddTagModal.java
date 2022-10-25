package ru.instamart.reforged.admin.page.shoppers.tags.add_tag;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.Assert;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;

import java.util.Collections;
import java.util.Set;

public final class AddTagModal {

    private final Element modal = new Element(By.xpath("//div[@data-qa='add_tags_select']/ancestor::div[@class='ant-modal']"), "Модалка выбора тегов");
    private final Button addTagsButtonInactive = new Button(By.xpath("//button[@data-qa='add_tags_button' and(@disabled)]"), "Кнопка добавления тегов");
    private final Button addTagsButtonActive = new Button(By.xpath("//button[@data-qa='add_tags_button']"), "Кнопка добавления тегов");
    private final Element tagsSelector = new Element(By.xpath("//div[@data-qa='add_tags_select']"), "Селектор тегов");
    private final ElementCollection tagsInList = new ElementCollection(By.xpath("//div[contains(@class,' ant-select-item-option')]"), "Теги в списке тегов");
    private final Element selectedTagInList = new Element(ByKraken.xpathExpression("//span[text()='%s']/ancestor::div[@aria-selected='true']"),"Выбранный тег в списке тегов");
    private final ElementCollection selectedTagsInField = new ElementCollection(By.xpath("//span[@class='ant-select-selection-item-content']/span"),"Коллекция выбранных тегов в поле");
    private final ElementCollection selectedTagsInFieldRemoveButtons = new ElementCollection(By.xpath("//span[@class='ant-select-selection-item']//span[contains(@class,'anticon-close')]"),"Коллекция кнопок удаления выбранных тегов в поле");

    @Step("Проверяем, что модальное окно выбора тега отображается")
    public void checkModalVisible() {
        modal.should().visible();
    }

    @Step("Проверяем, что модальное окно выбора тега не отображается")
    public void checkModalNotVisible() {
        modal.should().invisible();
    }

    @Step("Проверяем, что кнопка добавления тегов отображается и задизейблена")
    public void checkAddTagsButtonInactive() {
        addTagsButtonInactive.should().visible();
    }


    @Step("Проверяем, что кнопка добавления тегов отображается и доступна")
    public void checkAddTagsButtonActive() {
        addTagsButtonActive.should().visible();
    }

    @Step("Проверяем, что теги в списке тегов скрыты")
    public void checkTagsDropdownInvisible() {
        tagsInList.should().invisible();
    }

    @Step("Проверяем, что теги в списке тегов показаны")
    public void checkTagsDropdownVisible() {
        tagsInList.should().visible();
    }

    @Step("Проверяем, что кнопки удаления в списке тегов показаны")
    public void checkTagsInFieldHaveRemoveButtons() {
        Assert.assertEquals(selectedTagsInField.elementCount(), selectedTagsInFieldRemoveButtons.elementCount(), "Количество кнопок удаления не соответствует количеству тегов");
    }

    @Step("Проверяем, что тег {0} выбран")
    public void checkTagSelected(String name) {
        selectedTagInList.should().visible(name);
    }

    @Step("Проверяем, что выбранные теги не отображаются в поле")
    public void checkTagSelectedExcludeInTagsList(Set<String> namesSetExcluded) {
        Assert.assertTrue(Collections.disjoint(tagsInList.getTextFromAllElements(), namesSetExcluded), "Теги выбранные в списке отображаются в поле для выбора");
    }

    @Step("Проверяем, что поле выбранных тегов пустое")
    public void checkSelectedTagsInFieldEmpty() {
        selectedTagsInField.should().invisible();
    }

    @Step("Проверяем, что теги в списке тегов показаны")
    public void compareSelectedTagsWithActual(Set<String> namesSetExpected) {
        Assert.assertTrue(namesSetExpected.containsAll(selectedTagsInField.getTextFromAllElements()), "Теги выбранные в списке не соответствуют отображаемым в поле");
    }

    @Step("Нажимаем на кнопку добавления тегов")
    public void clickOnAddTagsButton() {
        addTagsButtonActive.click();
    }

    @Step("Нажимаем на селектор добавления тегов")
    public void clickOnTagsSelector() {
        tagsSelector.click();
    }

    @Step("Клик на {0}-й элемент списка тегов")
    public void clickOnTagInList(int elementOrder) {
        tagsInList.getElements().get(elementOrder - 1).click();
    }

    @Step("Получить название {0}-го элемента списка тегов")
    public String getTagNameFromList(int elementOrder) {
        return tagsInList.getElements().get(elementOrder - 1).getText();
    }
}