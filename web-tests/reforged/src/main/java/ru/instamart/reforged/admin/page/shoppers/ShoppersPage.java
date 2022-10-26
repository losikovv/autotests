package ru.instamart.reforged.admin.page.shoppers;

import io.qameta.allure.Step;
import ru.instamart.reforged.admin.AdminPage;
import ru.instamart.reforged.admin.page.shoppers.tags.add_tag.AddTagModal;
import ru.instamart.reforged.admin.page.shoppers.tags.delete_tag.DeleteTagModal;

public final class ShoppersPage implements AdminPage, ShoppersCheck {

    public AddTagModal interactAddTagModal() {
        return addTagModal;
    }

    public DeleteTagModal interactDeleteTagModal() {
        return deleteTagModal;
    }

    @Step("Нажать на кнопку 'Добавить сотрудника'")
    public void clickToCreateShoppers() {
        createShoppersButton.click();
    }

    @Override
    public String pageUrl() {
        return "shoppers";
    }

    @Step("Ввести имя сотрудника - {0}")
    public void fillName(String name) {
        nameInput.fill(name);
    }

    @Step("Кликнуть на кнопку 'Добавить тег' у первого партнера")
    public void clickOnFirstAddTagButton() {
        shoppersTable.clickOnAddTag(1);
    }

    @Step("Кликнуть на 'Еще' у первого партнера")
    public void clickOnCollapseTagListButton() {
        shoppersTable.clickOnExpandButton(1);
    }

    @Step("Кликнуть на фильтр тегов")
    public void clickOnTagsFilterSelector() {
        tagFilterField.click();
    }

    @Step("Нажать на кнопку 'Удалить' у тега под номером: {0}")
    public void clickToDeleteTag(int tagOrder) {
        shoppersTable.getAllTagsDeleteButtons(1).get(tagOrder - 1).click();
    }

    @Step("Клик на {0}-й элемент списка тегов")
    public void clickOnTagInList(int elementOrder) {
        tagsInList.getElements().get(elementOrder - 1).click();
    }

    @Step("Развернуть все списки тегов")
    public void expandAllTags() {
        for (int i = 0; i < shoppersTable.getRowsCount(); i++) {
            if (shoppersTable.checkExpandButtonVisible(i)) {
                shoppersTable.clickOnExpandButton(i);
            }
        }
    }

    @Step("Получить название {0}-го элемента списка тегов")
    public String getTagNameFromList(int elementOrder) {
        return tagsInList.getElements().get(elementOrder - 1).getText();
    }
}
