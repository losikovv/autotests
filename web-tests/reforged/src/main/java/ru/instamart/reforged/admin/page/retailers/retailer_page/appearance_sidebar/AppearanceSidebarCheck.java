package ru.instamart.reforged.admin.page.retailers.retailer_page.appearance_sidebar;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface AppearanceSidebarCheck extends Check, AppearanceSidebarElements {

    @Step("Проверяем, что отображается сайдбар")
    default void checkSidebarVisible() {
        waitAction().shouldBeVisible(drawer);
    }

    @Step("Проверяем, что залоговок сайдбара: '{expectedTitle}'")
    default void checkDrawerTitle(final String expectedTitle) {
        Assert.assertEquals(drawerTitle.getText(), expectedTitle, "Заголовок сайдбара отличается от ожидаемого");
    }

    @Step("Проверяем, что отображается кнопка 'Загрузить файл' (лого)")
    default void checkUploadLogoButtonVisible() {
        waitAction().shouldBeVisible(uploadLogo);
    }
}
