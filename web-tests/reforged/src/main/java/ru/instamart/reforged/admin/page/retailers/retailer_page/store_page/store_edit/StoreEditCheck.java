package ru.instamart.reforged.admin.page.retailers.retailer_page.store_page.store_edit;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface StoreEditCheck extends Check, StoreEditElements {

    @Step("Проверяем, что в списке групп магазинов есть группа: '{groupName}'")
    default void checkStoreGroupsContains(final String groupName) {
        Assert.assertTrue(storeGroups.getTextFromAllElements().contains(groupName.toUpperCase()), "В списке групп магазина не найдена группа " + groupName);
    }

    @Step("Проверяем, что в списке групп магазинов нет группы: '{groupName}'")
    default void checkStoreGroupsNotContains(final String groupName) {
        Assert.assertFalse(storeGroups.getTextFromAllElements().contains(groupName.toUpperCase()), "В списке групп магазина найдена группа " + groupName);
    }

    @Step("Проверяем, что дропдаун выбора региона отображается")
    default void checkRegionDropdownVisible() {
        waitAction().shouldBeVisible(regionsDropdown);
    }

    @Step("Проверяем, что группа выбрана")
    default void checkGroupSelected(final String groupName) {
        Assert.assertTrue(selectedStoreGroups.getTextFromAllElements().contains(groupName.toUpperCase()), "Среди присвоенных магазину групп не найдена группа " + groupName);
    }
}
