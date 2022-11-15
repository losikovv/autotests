package ru.instamart.reforged.admin.page.retailers.regions.add_new;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface RegionsAddCheck extends Check, RegionsAddElements {

    @Step("Проверяем, что кнопка добавления нового региона отображается и не анимирована")
    default void checkAddNewRegionModalNotAnimated() {
        Assert.assertTrue(newRegionName.is().animationFinished());
    }

    @Step("Проверяем, что модальное окно добавления региона отображается и не анимировано")
    default void checkAddNewRegionModalVisible() {
        waitAction().shouldBeVisible(addNewRegionModal);
        Assert.assertTrue(addNewRegionModal.is().animationFinished());
    }

    @Step("Проверяем, что модальное окно добавления региона не отображается")
    default void checkAddNewRegionModalNotVisible() {
        Assert.assertTrue(addNewRegionModal.is().invisible());
    }
}
