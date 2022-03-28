package ru.instamart.reforged.stf.frame.prereplacement_modal;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface PrereplacementModalCheck extends Check, PrereplacementModalElement {

    @Step("Проверяем что открылось модальное окно предзамен")
    default void checkPrereplacementModalVisible() {
        waitAction().shouldBeVisible(prereplacementModal);
    }

    @Step("Проверяем что модальное окно предзамен закрылось")
    default void checkPrererlacementModalNotVisible() {
        waitAction().shouldNotBeVisible(prereplacementModal);
    }

    @Step("Проверяем что кнопка 'Подойдет любой товар' отображается")
    default void checkAnyWillSuitButtonDisplayed() {
        waitAction().shouldBeVisible(anyWillSuit);
    }

    @Step("Проверяем что кнопка 'Подойдет любой товар' не отображается")
    default void checkAnyWillSuitButtonNotDisplayed() {
        waitAction().shouldNotBeVisible(anyWillSuit);
    }

    @Step("Проверяем, что количество товаров, для которых нужно выбрать предзамены: '{0}' соответсвует ожидаемому: '{1}'")
    default void checkItemsToReplaceCountEquals(final int actualItemsToReplaceCount, final int expectedItemsToReplaceCount) {
        Assert.assertEquals(actualItemsToReplaceCount, expectedItemsToReplaceCount,
                "Количество товаров, для которых нужно выбрать прездамены не соответсвтует ожидаемому");
    }

    @Step("Проверяем, что количество товаров доступных для замены: '{0}' соответствует ожидаемому: '{1}'")
    default void checkItemsForReplaceCountEquals(final int actualItemsForReplaceCount, final int expectedItemsForReplaceCount) {
        Assert.assertEquals(actualItemsForReplaceCount, expectedItemsForReplaceCount,
                "Количество товаров, доступных для прездамены не соответсвтует ожидаемому");
    }
}
