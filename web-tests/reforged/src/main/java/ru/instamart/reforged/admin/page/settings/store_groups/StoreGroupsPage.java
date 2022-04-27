package ru.instamart.reforged.admin.page.settings.store_groups;

import io.qameta.allure.Step;
import ru.instamart.reforged.admin.AdminPage;

public class StoreGroupsPage implements AdminPage, StoreGroupsCheck {

    @Step("Нажимаем '+ Новая группа'")
    public void clickAddNewGroup() {
        addNewGroup.click();
    }

    @Step("Нажимаем 'Редактировать' для группы {0}")
    public void editGroup(final String groupName) {
        groupsTable.clickToEdit(groupName);
    }

    @Step("Нажимаем 'Удалить' для группы {0}")
    public void removeGroup(final String groupName) {
        groupsTable.clickToRemove(groupName);
    }

    @Step("Нажимаем на кнопку 'Да' в окне подтверждения действия")
    public void clickConfirmStatusModalYes() {
        confirmActionModalYes.click();
    }

    @Override
    public String pageUrl() {
        return "store_labels";
    }
}
