package ru.instamart.reforged.admin.page.retailers.rank_list_sidebar;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;

public interface RankListSidebarCheck extends Check, RankListSidebarElements {

    @Step("Проверяем, что отображается сайдбар")
    default void checkSidebarVisible() {
        drawer.should().visible();
    }

    @Step("Проверяем, что залоговок сайдбара: '{expectedTitle}'")
    default void checkDrawerTitle(final String expectedTitle) {
        Assert.assertEquals(drawerTitle.getText(), expectedTitle, "Заголовок сайдбара отличается от ожидаемого");
    }

    @Step("Проверяем, что отображается список ритейлеров")
    default void checkRetailersListVisible() {
        retailers.should().visible();
    }
}
