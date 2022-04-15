package ru.instamart.reforged.admin.page.retailers.retailer_page.settings_sidebar;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface SettingsSidebarCheck extends Check, SettingsSidebarElements {

    @Step("Проверяем, что отображается сайдбар")
    default void checkSidebarVisible() {
        waitAction().shouldBeVisible(drawer);
    }

    @Step("Проверяем, что залоговок сайдбара: '{expectedTitle}'")
    default void checkDrawerTitle(final String expectedTitle) {
        Assert.assertEquals(drawerTitle.getText(), expectedTitle, "Заголовок сайдбара отличается от ожидаемого");
    }

    @Step("Проверяем, что текст в поле 'Наименование' соответствует ожидаемому: '{expectedRetailerName}'")
    default void checkRetailerNameInputValue(final String expectedRetailerName) {
        Assert.assertEquals(retailerName.getValue(), expectedRetailerName, "Текст в поле 'Наименование' отличается от ожидаемого");
    }
}
