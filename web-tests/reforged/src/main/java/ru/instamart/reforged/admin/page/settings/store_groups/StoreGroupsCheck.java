package ru.instamart.reforged.admin.page.settings.store_groups;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface StoreGroupsCheck extends Check, StoreGroupsElements {

    @Step("Проверяем, что отображается кнопка '+ Новая группа'")
    default void checkAddNewGroupButtonVisible() {
        waitAction().shouldBeVisible(addNewGroup);
    }

    @Step("Проверяем, что отображается список групп")
    default void checkGroupsVisible() {
        waitAction().shouldBeVisible(groupsTable);
    }

    @Step("Проверяем, что в таблице присутствует группа: {groupName}")
    default void checkGroupExists(final String groupName) {
        Assert.assertTrue(groupsTable.getGroupNames().contains(groupName), "В таблице не найдена группа " + groupName);
    }

    @Step("Проверяем, что в таблице отсутствует группа: {groupName}")
    default void checkGroupNotExists(final String groupName) {
        Assert.assertFalse(groupsTable.getGroupNames().contains(groupName), "В таблице найдена группа " + groupName);
    }

    @Step("Проверяем, что отображается всплывающее окно подтверждения действия")
    default void checkConfirmActionModalDisplayed() {
        waitAction().shouldBeVisible(confirmActionModal);
    }

    @Step("Проверяем, что отображается всплывающее уведомление")
    default void checkNoticePopupDisplayed() {
        waitAction().shouldBeVisible(noticePopup);
    }

    @Step("Проверяем, что текст всплывающего уведомления соответствует ожидаемому: '{expectedText}'")
    default void checkNoticeTextEquals(final String expectedText) {
        Assert.assertEquals(noticePopup.getText(), expectedText, "Текст всплывающего уведомления отличается от ожидаемого");
    }
}
