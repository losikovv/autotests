package ru.instamart.reforged.admin.page.shoppers;


import io.qameta.allure.Step;
import org.testng.Assert;

import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.instamart.reforged.core.Check.krakenAssert;

public interface ShoppersCheck extends ShoppersElement {

    @Step("Проверяем, что спиннер отображается")
    default void checkSpinnerVisible() {
        spinner.should().visible();
    }

    @Step("Проверяем, что спиннер не отображается")
    default void checkSpinnerNotVisible() {
        spinner.should().invisible();
    }

    @Step("Проверяем, что партнер c именем {0} найден")
    default void checkShopperWasFound(String name) {
        Assert.assertEquals(shoppersTable.getPartnersNames().get(1), name, "Искомый партнер не найден");
    }

    @Step("Проверяем, что теги в списке тегов первого партнера отображаются по алфавиту")
    default void compareSelectedTagsWithActual(Set<String> namesSetExpected) {
        Assert.assertEquals(shoppersTable.getAllTagsTextFromLine(1), namesSetExpected.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList()), "Теги выбранные в списке отображаются не по алфавиту");
    }

    @Step("Проверяем, что количество тегов в списке соответствует ожидаемому - {0}")
    default void compareSelectedTagsQuantityWithActual(int tagsCount) {
        Assert.assertEquals(shoppersTable.getAllTagsFromLine(1).size(), tagsCount, "Количество тегов не соответствует ожидаемому");
    }

    @Step("Проверяем, что тег с именем: {0} отображается в списке тегов")
    default void checkTagWithNameVisible(String name) {
        Assert.assertTrue(shoppersTable.getAllTagsTextFromLine(1).contains(name), "Ожидаемый тег не отображается");
    }

    @Step("Проверяем, что тег с именем: {0} отображается в списке тегов")
    default void checkTagWithNameNotVisible(String name) {
        Assert.assertFalse(shoppersTable.getAllTagsTextFromLine(1).contains(name), "Ожидаемый тег не отображается");
    }

    @Step("Проверить, что текст на кнопке разворачивания списка тегов - {0}")
    default void checkCollapseTagListButtonText(String text) {
        Assert.assertEquals(shoppersTable.getExpandButtonText(1), text, "Текст на кнопке раскрытия списков тегов не '" + text + "'");
    }

    @Step("Проверяем, что кнопка 'Добавить тег' отображается")
    default void checkAddTagButtonVisible() {
        Assert.assertTrue(shoppersTable.checkAddTagButtonVisible(1), "Кнопка 'Добавить тег' не отображается");
    }

    @Step("Проверяем, что теги в списке тегов скрыты")
    default void checkTagsDropdownInvisible() {
        tagsInList.should().invisible();
    }

    @Step("Проверяем, что теги в списке тегов показаны")
    default void checkTagsDropdownVisible() {
        tagsInList.should().visible();
    }

    @Step("Проверяем, что кнопки удаления в списке тегов показаны")
    default void checkTagsInFieldHaveRemoveButtons() {
        Assert.assertEquals(shoppersTable.getAllTagsFromLine(1).size(), shoppersTable.getAllTagsDeleteButtons(1).size(), "Количество кнопок удаления не соответствует количеству тегов");
    }

    @Step("Проверяем, что тег: {0} выбран")
    default void checkTagSelected(String name) {
        selectedTagInList.should().visible(name);
    }

    @Step("Проверяем, что тег: {0} отображается у каждого отфильтрованного партнера в списке")
    default void checkTagSelectedInFilterVisibleOnAllPartners(String name) {
        for (int i = 1; i < shoppersTable.getRowsCount(); i++) {
            krakenAssert.assertTrue(shoppersTable.getAllTagsTextFromLine(i).contains(name));
        }
        krakenAssert.assertAll();
    }

    @Step("Проверяем, что теги в списке тегов показаны")
    default void compareSelectedTagsInFieldWithActual(Set<String> namesSetExpected) {
        Assert.assertTrue(namesSetExpected.containsAll(shoppersTable.getAllTagsTextFromLine(1)), "Теги выбранные в списке не соответствуют отображаемым в поле");
    }
}
